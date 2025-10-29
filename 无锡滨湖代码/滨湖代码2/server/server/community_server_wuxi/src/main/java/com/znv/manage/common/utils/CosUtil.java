package com.znv.manage.common.utils;

import java.util.HashMap;

import java.util.Map;

import java.util.Set;

  

/** 

 * 字符串相似性匹配算法 

 */  

public class CosUtil {




//    public CosDemo(String string1, String string2) {
//
//        for (Character character1 : string1.toCharArray()) {
//            if (vectorMap.containsKey(character1)) {
//                vectorMap.get(character1)[0]++;
//            } else {
//                tempArray = new int[2];
//                tempArray[0] = 1;
//                tempArray[1] = 0;
//                vectorMap.put(character1, tempArray);
//            }
//        }
//        for (Character character2 : string2.toCharArray()) {
//
//            if (vectorMap.containsKey(character2)) {
//
//                vectorMap.get(character2)[1]++;
//
//            } else {
//
//                tempArray = new int[2];
//
//                tempArray[0] = 0;
//
//                tempArray[1] = 1;
//
//                vectorMap.put(character2, tempArray);
//
//            }
//
//        }
//
//    }

    // 求余弦相似度
    public static double sim(String string1, String string2) {
        //数据结构解析:<单词,二维数组>,其中单词表示公共词，
        //  二维数组一维度表示句子一的向量,另一维度表示句子二的向量
        Map<Character, int[]> vectorMap = new HashMap<Character, int[]>();
        int[] tempArray = null;
        double result = 0;

        for (Character character1 : string1.toCharArray()) {
            if (vectorMap.containsKey(character1)) {
                vectorMap.get(character1)[0]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 1;
                tempArray[1] = 0;
                vectorMap.put(character1, tempArray);
            }
        }
        for (Character character2 : string2.toCharArray()) {

            if (vectorMap.containsKey(character2)) {

                vectorMap.get(character2)[1]++;

            } else {

                tempArray = new int[2];

                tempArray[0] = 0;

                tempArray[1] = 1;

                vectorMap.put(character2, tempArray);

            }

        }

        result = pointMulti(vectorMap) / sqrtMulti(vectorMap);  

        return result;  

    }  

  

    private static double sqrtMulti(Map<Character, int[]> paramMap) {

        double result = 0;  

        result = squares(paramMap);  

        result = Math.sqrt(result);  

        return result;  

    }  

  

    // 求平方和
    private static double squares(Map<Character, int[]> paramMap) {

        double result1 = 0;  

        double result2 = 0;  

        Set<Character> keySet = paramMap.keySet();  

        for (Character character : keySet) {  

            int temp[] = paramMap.get(character);  

            result1 += (temp[0] * temp[0]);  

            result2 += (temp[1] * temp[1]);  

        }  

        return result1 * result2;  

    }  


    // 点乘法
    private static double pointMulti(Map<Character, int[]> paramMap) {

        double result = 0;  

        Set<Character> keySet = paramMap.keySet();  

        for (Character character : keySet) {  

            int temp[] = paramMap.get(character);  

            result += (temp[0] * temp[1]);  

        }  

        return result;  

    }  

  

    public  static void main(String[] args) {


        String s1 = "#襄阳城事#请注意！襄城社保办将迁入新址，10月2...";

        String s2 = "襄阳城";


        System.out.println(sim(s1,s2));

    }  

  

}