package com.znv.manage.common.utils;

import java.util.*;

public class ListUtils {

    /**
     * 字符串集合转字符
     * @param list
     * @return
     */
    public static String divisionList(Collection<String> list){
        if(list==null||list.size()==0){
            return "";
        }
        StringBuilder member=new StringBuilder();
        boolean flag=false;
        for (String string : list) {
            if (flag) {
                member.append(","); // 分隔符
            }else {
                flag=true;
            }
            member.append(string);
        }
        return member.toString();
    }

    public static List<String> converString(String s){
        String[] split = s.split(",");
        List<String> list = new ArrayList<>();
        for (String s1 : split) {
            list.add(s1);
        }
        return list;
    }

    /**
     * 判断2个数组的差异
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> difference(List<String> list1,List<String> list2){
        List<String> difference = new ArrayList<>();
        for (String s : list1) {
            boolean flag=false;
            for (String s1 : list2) {
                if(s.equals(s1)){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                difference.add(s);
            }
        }
        if(difference.size()==0){
            return null;
        }else{
            return difference;
        }
    }

    /**
     * 集合内删除
     * @param list
     * @param delList
     * @return
     */
    public static void delList(List<String> list,List<String> delList){
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            delList.forEach(del ->{
                if(next.equals(del)){
                    iterator.remove();
                }
            });
        }
    }

    /**
     * 将数据库查的list转成map
     * @param lists
     * @param key
     * @return
     */
    public static Map<String, List<Map<String, String>>>  ListToMap(List<Map<String, String>> lists, String key){
        Map<String, List<Map<String, String>>> maps = new HashMap<>();
        for (Map<String, String> c : lists) {
            String type = c.get(key);
            if(maps.containsKey(type)){
                maps.get(type).add(c);
            }else{
                List<Map<String, String>> list = new ArrayList<>();
                list.add(c);
                maps.put(type,list);
            }
        }
        return maps;
    }
}
