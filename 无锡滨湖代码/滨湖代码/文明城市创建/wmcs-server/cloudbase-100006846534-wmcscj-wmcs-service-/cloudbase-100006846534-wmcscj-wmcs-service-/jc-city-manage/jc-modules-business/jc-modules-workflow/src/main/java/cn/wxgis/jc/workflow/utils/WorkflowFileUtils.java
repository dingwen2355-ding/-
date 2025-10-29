package cn.wxgis.jc.workflow.utils;

import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.workflow.po.WfNodeHistory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowFileUtils {
    public static boolean isImag(String type) {

        String[] imagType = {".jpg", ".png", ".bmp", ".gif",".jpeg"};

        List<String> imageTyepLists = Arrays.asList(imagType);

        if (imageTyepLists.contains(type.toLowerCase())) {

            return true;

        } else {

            return false;
        }
    }

    /**
     * 通过几点将图片分类
     * @param node
     * @param pictureList
     * @param docFileList
     */
    public static void pictureClassify(WfNodeHistory node , List<Map<String,String>> pictureList, List<Map<String,String>> docFileList){
        if(StringUtils.isNotEmpty(node.getAttchnameid())){
            String[] attchNameArr=node.getAttchnameid().split(",");
            String[] attchPathArr=node.getAttchpath().split(",");

            for(int i=0;i<attchNameArr.length;i++){
                Map<String,String> map=new HashMap<>();
                map.put("fileName",attchNameArr[i]);
                map.put("filePath",attchPathArr[i]);
                try{
                    if(WorkflowFileUtils.isImag(attchNameArr[i].substring(attchNameArr[i].lastIndexOf(".")))){
                        pictureList.add(map);
                    }else {
                        docFileList.add(map);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }



}
