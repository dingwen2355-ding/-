package com.znv.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.PublicOpinionService;
import com.znv.manage.service.impl.PublicOpinionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/*
 * 公共舆情相关接口
 */

@RestController
@RequestMapping("publicOpinion")
@Api(tags = "公共舆情")
@Slf4j
public class PublicOpinionController {

    @Autowired
    PublicOpinionServiceImpl publicOpinionService;

    @ApiOperation("搜索框")
    @GetMapping("searchAll")
    public Result searchAll(@RequestParam(value = "eventId",required = false)String eventId,
                            @RequestParam(value = "keywords",required = false)String keywords,
                            @RequestParam(value = "limitNum",defaultValue = "10")String limitNum){
        return publicOpinionService.searchAll(eventId,keywords,limitNum);
    }

    @ApiOperation("折线图")
    @GetMapping("opinionStatistics")
    public Result opinionStatistics(@RequestParam(value = "eventId",required = false)String eventId,
                            @RequestParam(value = "keywords",required = false)String keywords){
        return publicOpinionService.opinionStatistics(eventId,keywords);
    }

    @ApiOperation("媒体来源统计")
    @GetMapping("mediaTypeStatistics")
    public Result mediaTypeStatistics(@RequestParam(value = "eventId",required = false)String eventId,
                                    @RequestParam(value = "keywords",required = false)String keywords){
        return publicOpinionService.mediaTypeStatistics(eventId,keywords);
    }

    @ApiOperation("倾向性")
    @GetMapping("attitudeStatistics")
    public Result attitudeStatistics(@RequestParam(value = "eventId",required = false)String eventId,
                                      @RequestParam(value = "keywords",required = false)String keywords){
        return publicOpinionService.attitudeStatistics(eventId,keywords);
    }


//    @Value("${weibo.hot:https://m.weibo.cn/api/container/getIndex?display=0&retcode=6102&containerid=23065700598008642060000000000&page_type=01&page=}")
//    private String weiboUrl;
//    @Value("${weibo.proxy.ip:10.7.53.116}")
//    private String weiboProxyIp;
//    @Value("${weibo.proxy.port:10000}")
//    private String weiboProxyPort;


    // ******************************************真实舆情数据接口**********************************************
    // 真实舆情数据库表中包括舆情模块所需要的所有字段
//    @GetMapping("publicOpinionData")
//    public Result getRealPublicOpinionDataByEventId(@RequestParam(value = "eventId",required = false) String eventId) {
//        return publicOpinionService.getRealPublicOpinionDataByEventId(eventId);
//    }


//    @ApiOperation("大屏-根据关键字查询舆情信息")
//    @GetMapping("publicOpinionByKeyWord")
//    public Result getPublicOpinionByKeyWord(@RequestParam(value = "keyWords", required = false) String keyWords,
//                                            @RequestParam(value = "eventId",required = false) String eventId) {
//
//        return publicOpinionService.getPublicOpinionByKeyWord(keyWords, eventId);
//    }
//
//
//    @ApiOperation("大屏-事件与舆情信息绑定:type=1-绑定，type=2-解除绑定")
//    @PostMapping("boundPublicOpinion")
//    public Result boundPublicOpinionWithEvent(@RequestParam(value = "eventId") String eventId,
//                                              @RequestParam("publicOpinionIds") String publicOpinionIds,
//                                              @RequestParam("type") String type) {
//
//        return publicOpinionService.boundPublicOPinionWithEvent(eventId, publicOpinionIds, type);
//    }
//
//    @ApiOperation("跳转用")
//    @GetMapping("/getHotTipsList")
//    public Result getHotTipsList(){
//        Result result = new Result();
//        try {
//            JSONArray resultArray = new JSONArray();
//            for(int i=1;i<=1;i++){
//                String url = weiboUrl+i;
//                RestTemplate restTemplate = new RestTemplate();
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//                headers.set("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
//                headers.set("sec-fetch-dest","empty");
//                headers.set("sec-fetch-mode","same-origin");
//                headers.set("sec-fetch-site","same-origin");
//                headers.set("upgrade-insecure-requests","1");
//
////                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
////                requestFactory.setProxy(
////                        new Proxy(
////                                Proxy.Type.HTTP,
////                                new InetSocketAddress(weiboProxyIp, Integer.parseInt(weiboProxyPort))  //设置代理服务
////                        )
////                );
////                restTemplate.setRequestFactory(requestFactory);
//                HttpEntity<JSONObject> jsonObject= restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<>(headers),JSONObject.class);
//                JSONArray tempArray = jsonObject.getBody().getJSONObject("data").getJSONArray("cards").getJSONObject(1).getJSONArray("card_group");
//                resultArray.addAll(tempArray);
//            }
//            result.setData(resultArray);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//        return result;
//    }


}
