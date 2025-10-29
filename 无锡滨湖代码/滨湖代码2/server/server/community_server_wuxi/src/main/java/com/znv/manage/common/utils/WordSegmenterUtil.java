package com.znv.manage.common.utils;

import org.apdplat.word.analysis.*;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import static org.apdplat.word.segmentation.SegmentationAlgorithm.MinimalWordCount;

public class WordSegmenterUtil {
    //阈值
    public static double YUZHI = 0.001 ;

    /**
     MaximumMatching("正向最大匹配算法"),
     ReverseMaximumMatching("逆向最大匹配算法"),
     MinimumMatching("正向最小匹配算法"),
     ReverseMinimumMatching("逆向最小匹配算法"),
     BidirectionalMaximumMatching("双向最大匹配算法"),
     BidirectionalMinimumMatching("双向最小匹配算法"),
     BidirectionalMaximumMinimumMatching("双向最大最小匹配算法"),
     FullSegmentation("全切分算法"),
     MinimalWordCount("最少词数算法"),
     MaxNgramScore("最大Ngram分值算法"),
     PureEnglish("针对纯英文文本的分词算法");
     */

    private static String seg(String text, SegmentationAlgorithm segmentationAlgorithm) {
        StringBuilder result = new StringBuilder();
        for(Word word : org.apdplat.word.WordSegmenter.seg(text, segmentationAlgorithm)){
            result.append(word.getText()).append(" ");
        }
        return result.toString();
    }

    /**
     * 返回百分比
     */
    public static double getSimilarity(Vector<String> T1, Vector<String> T2) throws Exception {
        int size = 0 , size2 = 0 ;
        if ( T1 != null && ( size = T1.size() ) > 0 && T2 != null && ( size2 = T2.size() ) > 0 ) {

            Map<String, double[]> T = new HashMap<String, double[]>();

            //T1和T2的并集T
            String index = null ;
            for ( int i = 0 ; i < size ; i++ ) {
                index = T1.get(i) ;
                if( index != null){
                    double[] c = T.get(index);
                    c = new double[2];
                    c[0] = 1;  //T1的语义分数Ci
                    c[1] = YUZHI;//T2的语义分数Ci
                    T.put( index, c );
                }
            }

            for ( int i = 0; i < size2 ; i++ ) {
                index = T2.get(i) ;
                if( index != null ){
                    double[] c = T.get( index );
                    if( c != null && c.length == 2 ){
                        c[1] = 1; //T2中也存在，T2的语义分数=1
                    }else {
                        c = new double[2];
                        c[0] = YUZHI; //T1的语义分数Ci
                        c[1] = 1; //T2的语义分数Ci
                        T.put( index , c );
                    }
                }
            }

            //开始计算，百分比
            Iterator<String> it = T.keySet().iterator();
            double s1 = 0 , s2 = 0, Ssum = 0;  //S1、S2
            while( it.hasNext() ){
                double[] c = T.get( it.next() );
                Ssum += c[0]*c[1];
                s1 += c[0]*c[0];
                s2 += c[1]*c[1];
            }
            //百分比
            return Ssum / Math.sqrt( s1*s2 );
        } else {
            throw new Exception("相似度计算工具类传入参数有问题！");
        }
    }


    public static double getSimilarScore(String text1,String text2){
        TextSimilarity textSimilarity = new CosineTextSimilarity();
        return  textSimilarity.similarScore(text1,text2);
    }

    public static Map<String,Object> getScoreMap(String text1,String text2){
        Map<String,Object> map = new HashMap<>();
        TextSimilarity textSimilarity1 = new CosineTextSimilarity();
        map.put("余弦相似度",textSimilarity1.similarScore(text1,text2));

        TextSimilarity textSimilarity2 = new SimpleTextSimilarity();
        map.put("简单共有词",textSimilarity2.similarScore(text1,text2));

        TextSimilarity textSimilarity3 = new EditDistanceTextSimilarity();
        map.put("编辑距离",textSimilarity3.similarScore(text1,text2));

        TextSimilarity textSimilarity4 = new SimHashPlusHammingDistanceTextSimilarity();
        map.put("SimHash + 汉明距离",textSimilarity4.similarScore(text1,text2));

        TextSimilarity textSimilarity5 = new JaccardTextSimilarity();
        map.put("Jaccard相似性系数",textSimilarity5.similarScore(text1,text2));

        TextSimilarity textSimilarity6 = new EuclideanDistanceTextSimilarity();
        map.put("欧几里得距离",textSimilarity6.similarScore(text1,text2));

        TextSimilarity textSimilarity7 = new ManhattanDistanceTextSimilarity();
        map.put("曼哈顿距离",textSimilarity7.similarScore(text1,text2));

        TextSimilarity textSimilarity8 = new JaroDistanceTextSimilarity();
        map.put("Jaro距离",textSimilarity8.similarScore(text1,text2));

        TextSimilarity textSimilarity9 = new JaroWinklerDistanceTextSimilarity();
        map.put("Jaro–Winkler距离",textSimilarity9.similarScore(text1,text2));

        TextSimilarity textSimilarity10 = new SørensenDiceCoefficientTextSimilarity();
        map.put("Sørensen–Dice系数",textSimilarity10.similarScore(text1,text2));

        return map;
    }


    public static void main(String str[]){
//        System.out.println(WordSegmenterUtil.seg("关于玉兰路丁墙路路口因轨交2号线施工发生燃气泄漏",BidirectionalMaximumMinimumMatching));
//        System.out.println(WordSegmenterUtil.seg("关于玉兰路丁墙路路口因轨交2号线施工发生燃气泄漏",MinimalWordCount));

        //分词
        String s1 = WordSegmenterUtil.seg("关于玉兰路丁墙路路口因轨交2号线施工发生燃气泄漏",MinimalWordCount);
        String s2 = WordSegmenterUtil.seg("针对北望区玉兰路、丁墙路口燃气泄漏一事，风清扬副市长致电市政府总值班室，要求燃气公司、市应急局抓紧找出泄漏点，公安、应急、消防等部门做好警戒疏散等工作，严防次生灾害，确保现场安全。祝枝山、赵薇副秘书长要求总值班室密切关注。市政府总值班室即告相关单位和区。值班员：张三、李四、王五",MinimalWordCount);
        String s3 = WordSegmenterUtil.seg("陈总，您好，听马总说您这边对 http-flv 开源版的延时不太满意，我们这边测试并修改了一个低延时的版本，经测试，在局域网内播放的延时大约可以控制在 500 毫秒左右，您这边有空的话可以试一下",MinimalWordCount);

        TextSimilarity textSimilarity = new CosineTextSimilarity();
        double score1pk1 = textSimilarity.similarScore("关于玉兰路丁墙路路口因轨交2号线施工发生燃气泄漏", "针对北望区玉兰路、丁墙路口燃气泄漏一事，风清扬副市长致电市政府总值班室，要求燃气公司、市应急局抓紧找出泄漏点，公安、应急、消防等部门做好警戒疏散等工作，严防次生灾害，确保现场安全。祝枝山、赵薇副秘书长要求总值班室密切关注。市政府总值班室即告相关单位和区。值班员：张三、李四、王五");
        double score1pk2 = textSimilarity.similarScore("关于玉兰路丁墙路路口因轨交2号线施工发生燃气泄漏", "陈总，您好，听马总说您这边对 http-flv 开源版的延时不太满意，我们这边测试并修改了一个低延时的版本，经测试，在局域网内播放的延时大约可以控制在 500 毫秒左右，您这边有空的话可以试一下");
        double score1pk3 = textSimilarity.similarScore("关于玉兰路丁墙路路口因轨交2号线施工发生燃气泄漏", "2020年5月27日晚，2号线玉兰路站、丁墙路南侧慢车道处发生一起煤气泄漏事件。具体为：19时35分左右轨交2号线施工人员闻到煤气味道，拨打96777燃气热线。20时35分北望区消防救援支队接应急联动平台报告，立即安排4辆消防救援车辆赶赴现场。20时42分左右，消防救援人员到现场开展救援处置工作，20时51分左右出水稀释气体。21时45分左右，叮当燃气公司抢修人员关闭阀门三座，专用调压器三座，该管线停气，停气时间22时10分左右影响两家工商用户：1、风火广场（已停业）；2、玉兰路1555号（酒店燃气锅炉用水），未影响居民用气，基本未影响周边交通。事件发生后，上海市市应急管理局副局长江一燕，上海市燃气中心主任郭达，北望区副区长李丹，北望区建设管理委，北望区应急管理局，玉兰路街道，宁能集团、上海燃气公司、叮当燃气公司、上海宁通地铁集团、上海轨道交通二号线发展有限公司等主要领导及相关负责人立即赶赴现场进行处置。目前，现场仍在抢修寻找漏点。");

        System.out.println("Word分词器A相似度:"+score1pk1);
        System.out.println("Word分词器B相似度:"+score1pk2);
        System.out.println("Word分词器B相似度:"+score1pk3);


//        List<String> list1=new ArrayList<String>(Arrays.asList(s1.split(" ")));
//        Vector<String> vec1=new Vector<String>(list1);
//
//        List<String> list2=new ArrayList<String>(Arrays.asList(s2.split(" ")));
//        Vector<String> vec2=new Vector<String>(list2);
//
//        List<String> list3=new ArrayList<String>(Arrays.asList(s3.split(" ")));
//        Vector<String> vec3=new Vector<String>(list3);
//
//        try {
//            System.out.println("余弦相似度算法A相似度:"+getSimilarity(vec1,vec2));
//            System.out.println("余弦相似度算法B相似度:"+getSimilarity(vec1,vec3));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
