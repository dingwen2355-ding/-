package com.wxgis.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {
    // 递归求结果
    public static void cir(int ceng, int cengQuit, int startPiont, Double[] array,
                           Double[] cache, Double sum,List list) {
        ceng++;
        for (int i = startPiont; i < array.length; i++) {
            cache[ceng] = array[i];
            if (ceng == cengQuit) {
                if (getSum(cache)- sum==0) {
                    printcache(cache,list);
                    break;
                }
                if (getSum(cache)- sum>0) {
//					break;
                }
            }
            if (ceng < cengQuit) {
                startPiont = i + 1;
                cir(ceng, cengQuit, startPiont, array, cache, sum, list);
            }
        }
    }

    // 获取组合数字之和
    public static Double getSum(Double[] cache) {
        Double sum = 0d;
        for (int i = 0; i < cache.length; i++) {
            sum = sum + cache[i];
        }
        return sum;
    }

    // 打印组合的可能
    public static void printcache(Double[] cache, List list)  {
        String ss="";
        for (int i = 0; i < cache.length; i++) {
            ss=ss+cache[i] + ",";
            System.out.print(cache[i] + ",");
        }
        System.out.println();
        list.add(ss);

    }

    public static void main(String[] args) {
        Double[] array = { 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D, 96D };
        System.out.println(array);
        diguiSum(array, 300D);
    }

    public static List diguiSum(Double array[], Double sum) {
        Arrays.sort(array);  //进行升序排序;
        if(array[0]>sum){//如果数组中最小的值大于需要分配的数量，跳过
            return null;
        }

        List list=new ArrayList();
        for (int i = 0; i < array.length; i++) {
            Double[] cache = new Double[i + 1];
            //每次循环组合是否大于分配的数量
            Double iisum=0d;
            for(int ii=0;ii<=i;ii++){
                iisum+=array[ii];
            }
            if(iisum>sum){
                return null;
            }
            int ceng = -1;
            int cengQuit = i;
            int startPiont = 0;
            cir(ceng, cengQuit, startPiont, array, cache, sum,list);
        }
        return list;
    }
}
