package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class SurveyMap {

    private RestTemplate restTemplate = new RestTemplate();

    private static String defalutXMLAddress="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "    <SOAP-ENV:Envelope\n" +
            "    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "    xmlns:s=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "        <SOAP-ENV:Body>\n" +
            "            <tns:ASCH_AddressSearch xmlns:tns=\"http://tempuri.org/\">\n" +
            "                <tns:vSearch_word>############</tns:vSearch_word>\n" +
            "                <tns:vResult_count>100</tns:vResult_count>\n" +
            "            </tns:ASCH_AddressSearch>\n" +
            "        </SOAP-ENV:Body>\n" +
            "    </SOAP-ENV:Envelope>";

    private static String defaultXmlGps="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "  <soap:Body>\n" +
            "    <WGS84BLH2SH xmlns=\"SHCoordTransM1WS\">\n" +
            "      <arg0 xmlns=\"\">XXXXXXXXXX</arg0>\n" +
            "      <arg1 xmlns=\"\">YYYYYYYYYY</arg1>\n" +
            "      <arg2 xmlns=\"\">0</arg2>\n" +
            "    </WGS84BLH2SH>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>\n";

    private static String survey_token_url="http://map.smi.sh.cegn.cn/RemoteTokenServer";

    private static String survey_addressToGps_url="http://map.smi.sh.cegn.cn/OneMapServer/rest/services/address_p/Transfer";

    private static String survey_84ToGps_url="http://map.smi.sh.cegn.cn/OneMapServer/rest/services/coordtransm1ws/Transfer";

    private static String login_name="shcyzx";

    private static String login_password="Shcyzx2003";

    private static String ip="192.168.1.10";
    
    private static String token="";

    private static long invalidTime=0L;

    /**
     * 对接  测绘院地图-获取token
     * @return
     */
    public String getSurveyToken(){
        long time = new Date().getTime();
        if(StringUtils.isBlank(token)||invalidTime<(time+5*60*1000)){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
//            JSONObject map = new JSONObject();
//            map.put("username",login_name);
//            map.put("password",login_password);
//            map.put("request","getToken");
//            map.put("clientid",ip);
//            map.put("expiration","500");
            String url= survey_token_url+"?clientid="+ip+"&expiration=500&username="+login_name+"&password="+login_password+ "&request=getToken";
            HttpEntity entity = new HttpEntity<>(null,headers);
            token = restTemplate.postForObject(url, entity, String.class);
            invalidTime = DateUtil.getPreTime2(500);
        }
        return token;
    }

    /**
     * 对接测绘院--地址获取gps
     * @param address
     * @return
     */
    public Map<String, String> getSurveyGpsByAddress(String address){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","text/xml; charset=utf-8");
        headers.set("Cookie","agstoken=" + getSurveyToken());
        headers.set("referer",ip);
        String replace = defalutXMLAddress.replace("############", address);
        HttpEntity<String> entity = new HttpEntity<>(replace, headers);
        String s = restTemplate.postForObject(survey_addressToGps_url, entity, String.class);
        return XMLToGPS(s);
    }

    /**
     * 对接测绘院--84转测绘院gps
     * @param
     * @return
     */
    public Map<String, String> SurveyGpsBy84(Double gpsx, Double gpsy){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","text/xml; charset=utf-8");
        headers.set("Cookie","agstoken=" + getSurveyToken());
        headers.set("referer",ip);
        double x = gpsx*Math.PI/180;
        double y = gpsy*Math.PI/180;
        String xml = defaultXmlGps.replace("XXXXXXXXXX",y+"").replace("YYYYYYYYYY",x+"");
        HttpEntity<String> entity = new HttpEntity<>(xml, headers);
        String s = restTemplate.postForObject(survey_84ToGps_url, entity, String.class);
        return XMLToGps2(s);
    }

    private Map<String, String> XMLToGPS(String xml){
        JSONObject jsonObject = XmlUtils.xmlToJson(xml);
        System.err.println(jsonObject);
        JSONObject body = JsonUtils.getJSONObject(JsonUtils.getJSONObject(JsonUtils.getJSONObject(JsonUtils.getJSONObject(jsonObject, "soap:Body"),"ASCH_AddressSearchResponse"),"ASCH_AddressSearchResult"),"DZ_Table");
        JSONObject diffgram = JsonUtils.getJSONObject(body, "diffgr:diffgram");
        JSONObject data=null;
        if(diffgram.get("NewDataSet") instanceof JSONArray){
            JSONArray newDataSet = JsonUtils.getJSONArray(diffgram, "NewDataSet");
            data=newDataSet.getJSONObject(0);
        }else if(diffgram.get("NewDataSet") instanceof JSONObject){
            data= JsonUtils.getJSONObject(JsonUtils.getJSONObject(diffgram,"NewDataSet"),"Table");
        }
        String gpsx = JsonUtils.getJSONString(data, "POINT_X");
        String gpsy = JsonUtils.getJSONString(data, "POINT_Y");
        Map<String, String> map = new HashMap<>();
        map.put("gpsx",gpsx);
        map.put("gpsy",gpsy);
        return map;
    }

    private Map<String,String> XMLToGps2(String xml){
        try{
            //创建DOM解析器工厂实例并生成解析器
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //解析文档，并返回一个Document对象，此时xml文档已加载到内存中
            Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
            //获取文档根元素
            Element root = doc.getDocumentElement();
            NodeList list = root.getElementsByTagName("return");
            String gpsy= list.item(0).getTextContent();
            String gpsx = list.item(1).getTextContent();
            Map<String, String> map = new HashMap<>();
            map.put("gpsx",gpsx);
            map.put("gpsy",gpsy);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
