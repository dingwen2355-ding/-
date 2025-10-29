package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.bean.user.PrecinctBean;
import com.znv.manage.bean.user.RoleEnum;
import com.znv.manage.dao.PrivilegeDao;
import com.znv.manage.dao.UserServiceMapper;
import com.znv.manage.service.PrivilegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class PrivilegeServiceImpl implements  PrivilegeService{
    @Autowired
    PrivilegeDao privilegeDao;
    @Autowired
    UserServiceMapper userServiceMapper;
    @Value("${epidemic.get.precinct.url}")
    public String url;

    @Override
    public JSONArray getCommitteeVilageTree(String userId){
        JSONArray arrMax = new JSONArray();
        String precinctTreeIds=null;

        List<Map<String, Object>> listRoleId = userServiceMapper.getUserInfoByUserId(userId,null);
        if(listRoleId.size()!=0){
            String roleId=listRoleId.get(0).get("roleId").toString();
            //超级管理员角色可以查看所有街镇数据
            if(!RoleEnum.SUPERADMIN.getId().equals(roleId) && !RoleEnum.PRECINCTADMIN.getId().equals(roleId)){
//                if(RoleEnum.PRECINCTADMIN.getId().equals(roleId)){
//                    //街镇管理员角色能看到到自己街镇所有资源树
//                    precinctTreeIds=privilegeDao.getPrecinctIds(getStreetIdByUserId(userId));
//                    if(null==precinctTreeIds){//看不到任何资源树
//                        precinctTreeIds="";
//                    }
//                }else{
                    //网格管理员和其他人员只能查询到自己有权限的资源树
                    //String operationUserid=listRoleId.get(0).get("operationUserid").toString();
                    List<Map<String, Object>> listTreeId=userServiceMapper.getUserInfoByUserId(userId,null);
                    if(listTreeId.size()!=0){
                        precinctTreeIds= listTreeId.get(0).get("precinctTreeId").toString();
                        if("".equals(precinctTreeIds)){
                            return null;
                        }else {
                            Set<String> precinctList = new HashSet<>(Arrays.asList(precinctTreeIds.split(",")));
                            Set<String> precinctTreeList = new HashSet<>();
                            for (String precinctId:precinctList){
                                String precinctIdUps = privilegeDao.queryPrecinctUp(precinctId);
                                if(!StringUtils.isEmpty(precinctIdUps)){
                                    precinctTreeList.addAll(Arrays.asList(precinctIdUps.split(",")));
                                }
                            }
                            precinctTreeIds = String.join(",",precinctTreeList);
                        }
                    }
                }
//            }
        }else{
            log.error("getCommitteeVilageTree--userId not exsists");
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("precinctKind", "100");//街道
        if(null!=precinctTreeIds){
            String jiedaoId=getStreetIdByUserId(userId);//非空时，解析只能看哪个街镇数据
            map.put("precinctTreeIds", jiedaoId);
        }
        List<Map<String, Object>>  maxList = privilegeDao.queryPrecinctListByKind(map);
        map.put("precinctKind", "110");//社区
        map.put("precinctTreeIds", precinctTreeIds);//只能查到创建该用户的创建者拥有的资源树
        List<Map<String, Object>>  committeeList = privilegeDao.queryPrecinctListByKind(map);
        map.put("precinctKind", "120");//网格
        List<Map<String, Object>>  gridList = privilegeDao.queryPrecinctListByKind(map);
        map.put("precinctKind", "130");//部门
        List<Map<String, Object>>  depList = privilegeDao.queryPrecinctListByKind(map);

        if(maxList.size()>0) {
            for(int m=0;m<maxList.size();m++){
                JSONArray arr = new JSONArray();
                JSONObject objMax = new JSONObject();
                objMax=getPrecinctObj(maxList,m);

                // 社区 网格
                for(int i=0;i<committeeList.size();i++){
                    String upPrecinctId0=committeeList.get(i).get("upPrecinctId").toString();
                    if(upPrecinctId0.equals(maxList.get(m).get("precinctId"))){
                        JSONObject obj1=getPrecinctObj(committeeList,i);
                        JSONArray arr1 = new JSONArray();
                        for(int j=0;j<gridList.size();j++){
                            String upPrecinctId=gridList.get(j).get("upPrecinctId").toString();
                            if(upPrecinctId.equals(committeeList.get(i).get("precinctId"))){
                                JSONObject obj2 = getPrecinctObj(gridList,j);
//                                JSONArray arrB = new JSONArray();
//                                for(int k=buildingList.size()-1; k>=0;k--){
//                                    String upPrecinctId2=buildingList.get(k).get("upPrecinctId").toString();
//                                    if(upPrecinctId2.equals(gridList.get(j).get("precinctId"))){
//                                        JSONObject obj3 = getPrecinctObj(buildingList,k);
//                                        arrB.add(obj3);
//                                        buildingList.remove(buildingList.get(k));
//                                    }
//                                }
//                                obj2.put("precinctSon",arrB);
                                arr1.add(obj2);
//                                gridList.remove(gridList.get(j));
                            }
                        }
                        obj1.put("precinctSon",arr1);
                        arr.add(obj1);
                    }
                }

                // 部门
                for(int i=0;i<depList.size();i++){
                    String upPrecinctId0=depList.get(i).get("upPrecinctId").toString();
                    if(upPrecinctId0.equals(maxList.get(m).get("precinctId"))){
                        JSONObject obj1=getPrecinctObj(depList,i);
                        arr.add(obj1);
                    }
                }

                objMax.put("precinctSon",arr);
                arrMax.add(objMax);
            }
        }

        return arrMax;
    }

    JSONObject getPrecinctObj(List<Map<String, Object>> list,int k){
        JSONObject obj = new JSONObject();
        obj.put("precinctName",list.get(k).get("precinctName"));
        obj.put("precinctId",list.get(k).get("precinctId"));
        obj.put("precinctKind",list.get(k).get("precinctKind"));
        obj.put("upPrecinctId",list.get(k).get("upPrecinctId"));

        return obj;
    }

    @Override
    public List<Map<String, Object>> getUserPricinctTree(String userId){
        return privilegeDao.getUserPricinctTree(userId);
    }

    @Override
    public List<Map<String, Object>> getPrecinctInfo(String precinctId){
        return privilegeDao.getPrecinctInfo(precinctId);
    }

    @Override
    public void updateUserPricinctTree(String userId,String precinctTreeId){
         privilegeDao.updateUserPricinctTree(userId, precinctTreeId);
    }

    @Override
    public void updatePersonPrecinctFromEP(){

        try{
            //1 调疫情平台库接口查询precinct表所有数据
            List<PrecinctBean> list=new ArrayList<>();
            RestTemplate restTemplate = new RestTemplate();
            JSONObject jsonObject = restTemplate.getForObject(url,JSONObject.class);
            //log.info("jsonObject = " + jsonObject.toString());
            list=jsonObject.getJSONArray("data").toJavaList(PrecinctBean.class);
            for(PrecinctBean obj:list){
                //确定是洋泾街道的数据，否则不同步
                String subStrId=obj.getPrecinctId().substring(0,8);
                if("31011521".equals(subStrId)){
                   List<Map<String, Object>> listTemp=privilegeDao.queryPrecinctExist(obj.getPrecinctId());
                   if(0==listTemp.size()){
                       //2 比对人口库precinct表中不存在的话则插入
                       privilegeDao.addPrecinctData(obj);
                       log.info("addPrecinctData["+obj+"]");
                   }
                }
            }
        }catch (Exception e){
            log.error("epidemic.get.precinctData error["+e.getMessage()+"]");
        }
    }

    @Override
    public String getUserStreetId(String userId) {
        return privilegeDao.getUserStreetId(userId);
    }

    //解析获取街镇id
    private String getJiedaoIdByPrecinctTree(String precinctTreeIds){
        String precinctId="001";//初始化为一个不存在的id
        if("".equals(precinctTreeIds)){
            return precinctId;
        }
        List<String> list= Arrays.asList(precinctTreeIds.split(","));
        if(list.size()!=0){
            String id=list.get(0).toString();
            int precinctKind = (int)privilegeDao.queryPrecinctExist(id).get(0).get("precinct_kind");
            if(100==precinctKind){
                precinctId=id;
            }else if(101==precinctKind){
                precinctId=privilegeDao.queryPrecinctExist(id).get(0).get("up_precinct_id").toString();
            }else{
            }
        }
        return precinctId;
    }

    public  String getStreetIdByUserId(String userId){
        String jiedaoId="";
        List<Map<String, Object>> listTreeId=userServiceMapper.getUserInfoByUserId(userId,null);
        if(listTreeId.size()!=0) {
            jiedaoId=listTreeId.get(0).get("streetId").toString();
        }

        return jiedaoId;
    }
}
