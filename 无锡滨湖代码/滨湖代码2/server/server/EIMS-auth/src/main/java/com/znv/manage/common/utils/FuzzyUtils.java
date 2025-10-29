package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * wr
 * 模糊工具类
 */
@Slf4j
public enum FuzzyUtils {
    instance;

    /**
     * 模糊方向
     */
    private final String FUZZY_DIRECTION_LEFT_2_RIGHT = "LEFTTORIGHT";
    private final String FUZZY_DIRECTION_RIGHT_2_LEFT = "RIGHTTOLEFT";
    private final String FUZZY_DIRECTION_CENTER_2_BOTHSIDE = "CENTERTOBOTHSIDE";


    //业务逻辑 模糊人名 从中间指定比例模糊
    private final String FUZZY_BUSSINESS_NAME = "NAME_FUZZY_BUSSINESS";
    //业务逻辑 模糊一些ID等尾模糊 从尾部往前模糊
    private final String FUZZY_BUSSINESS_BACKFUZZY = "FUZZY_BUSSINESS_BACKFUZZY";
    //业务逻辑身份证模糊
    private final String FUZZY_BUSSINESS_CNIDCARDFUZZY = "FUZZY_BUSSINESS_CNIDCARDFUZZY";
    //生日yyyy-MM-dd 格式加密
    private final String FUZZY_BUSSINESS_BIRTH_FORMATED = "FUZZY_BUSSINESS_BIRTH_FORMATED";


    /**
     * 默认的模糊字符串
     */
    private final String DEFAULT_FUZZY_CHARACTER = "*";

    public static class FuzzyPolicy {
        private String direction;
        private Integer length;
        private String key;
        private String fuzzyCharacter;

        /**
         *
         * @param direction 方向
         * @param length 模糊长度
         * @param key key
         */
        FuzzyPolicy(String direction, Integer length, String key) {
            this.direction = direction;
            this.length = length;
            this.key = key;
            this.fuzzyCharacter = FuzzyUtils.instance.DEFAULT_FUZZY_CHARACTER;
        }

        /**
         *
         * @param direction 方向
         * @param length 模糊长度
         * @param key key
         * @param fuzzyCharacter 模糊字符
         */
        FuzzyPolicy(String direction, Integer length, String key, String fuzzyCharacter) {
            this.direction = direction;
            this.length = length;
            this.key = key;
            this.fuzzyCharacter = fuzzyCharacter;
        }

        /**
         * 检查是否合法
         * @return
         */
        public boolean check() {
            boolean checked = length > 0 && !StringUtils.isEmpty(direction) && !StringUtils.isEmpty(key) && !StringUtils.isEmpty(fuzzyCharacter);
            if (!checked) {
                log.error("fuzzy policy uncorrect!");
            }
            return checked;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFuzzyCharacter() {
            return fuzzyCharacter;
        }

        public void setFuzzyCharacter(String fuzzyCharacter) {
            this.fuzzyCharacter = fuzzyCharacter;
        }
    }

    /**
     * 业务逻辑定制
     */
    public static class BussinessPolicy {
        private String fuzzyBussiness;
        private String key;
        private String fuzzyCharacter = FuzzyUtils.instance.DEFAULT_FUZZY_CHARACTER;

        //模糊率
        private Float radio;
        //最小模糊长度
        private Integer minLen;
        //最大模糊长度
        private Integer maxLen;
        //日期的格式
        private String formatString = "yyyy-MM-dd HH:mm:ss";

        public boolean check() {
            boolean checked = !StringUtils.isEmpty(fuzzyBussiness) && !StringUtils.isEmpty(key)
                    && ((minLen!=null && minLen>0) || minLen == null) && ((maxLen!=null && maxLen>0) || maxLen==null)
                    && !StringUtils.isEmpty(fuzzyCharacter);
            if (!checked) {
                log.error("Bussiness policy uncorrect!");
            }
            return checked;
        }

        public BussinessPolicy(String fuzzyBussiness, String key, String fuzzyCharacter, String formatString) {
            this.fuzzyBussiness = fuzzyBussiness;
            this.key = key;
            this.fuzzyCharacter = fuzzyCharacter;
            this.formatString = formatString;
        }

        public BussinessPolicy(String fuzzyBussiness, String key, String fuzzyCharacter, Float radio, Integer minLen, Integer maxLen) {
            this.fuzzyBussiness = fuzzyBussiness;
            this.key = key;
            this.fuzzyCharacter = fuzzyCharacter;
            this.radio = radio;
            this.minLen = minLen;
            this.maxLen = maxLen;
        }

        public String getFormatString() {
            return formatString;
        }

        public void setFormatString(String formatString) {
            this.formatString = formatString;
        }

        public String getFuzzyBussiness() {
            return fuzzyBussiness;
        }

        public void setFuzzyBussiness(String fuzzyBussiness) {
            this.fuzzyBussiness = fuzzyBussiness;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Float getRadio() {
            return radio;
        }

        public void setRadio(Float radio) {
            this.radio = radio;
        }

        public Integer getMinLen() {
            return minLen;
        }

        public void setMinLen(Integer minLen) {
            this.minLen = minLen;
        }

        public Integer getMaxLen() {
            return maxLen;
        }

        public void setMaxLen(Integer maxLen) {
            this.maxLen = maxLen;
        }

        public String getFuzzyCharacter() {
            return fuzzyCharacter;
        }

        public void setFuzzyCharacter(String fuzzyCharacter) {
            this.fuzzyCharacter = fuzzyCharacter;
        }
    }

    /**
     * 根据业务逻辑模糊
     */
    public void fuzzyAllWordsByBussinessPolicy(List<Map<String, Object>> list, List<BussinessPolicy> policies) {
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(policies)) {
            list.parallelStream().forEach(map -> {
                if (map != null) {
                    policies.forEach(policy -> {
                        if (policy != null && policy.check()) {
                            String key = policy.getKey();
                            if (!StringUtils.isEmpty(key)) {
                                String value = String.valueOf(map.getOrDefault(key, ""));
                                if (!StringUtils.isEmpty(value) && !"null".equals(value)) {
                                    String fuzzed = fuzzyStringByBussinessPolicy(policy, String.valueOf(value));
                                    map.put(key, fuzzed);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 根据不同的业务逻辑模糊字符串
     * @param policy
     * @param value
     * @return
     */
    protected String fuzzyStringByBussinessPolicy(BussinessPolicy policy, String value) {
        if (FUZZY_BUSSINESS_BIRTH_FORMATED.equals(policy.getFuzzyBussiness())) {
            return fuzzyBirthString(policy, value);
        }

        if (!StringUtils.isEmpty(value)) {
            if (value.length() == 1) {
                return policy.fuzzyCharacter;
            }
            int length = value.length();
            int radioLen = (int) (length * policy.radio);
            int maxLen = policy.getMaxLen();
            int minLen = policy.getMinLen();
            int targetLen = radioLen>=maxLen?maxLen:(radioLen<minLen?minLen:radioLen);
            if (FUZZY_BUSSINESS_NAME.equals(policy.getFuzzyBussiness())) {
                return fuzzyString(value, FuzzyUtils.instance.FUZZY_DIRECTION_CENTER_2_BOTHSIDE, targetLen);
            } else if (FUZZY_BUSSINESS_BACKFUZZY.equals(policy.getFuzzyBussiness())) {
                return fuzzyString(value, targetLen);
            } else if (FUZZY_BUSSINESS_CNIDCARDFUZZY.equals(policy.getFuzzyBussiness())) {
                return fuzzyString(value, FuzzyUtils.instance.FUZZY_DIRECTION_RIGHT_2_LEFT, targetLen,
                        DEFAULT_FUZZY_CHARACTER, 2);
            } else {
                log.error("no such bussiness policy");
            }

        }
        return value;
    }

    /**
     * 加密生日
     *
     * @param policy
     * @param value
     * @return
     */
    private String fuzzyBirthString(BussinessPolicy policy, String value) {
        try {
            if (StringUtils.isEmpty(value)) {
                log.error("birth day string can not be null or empty");
                return value;
            }
            String format = policy.getFormatString();
            SimpleDateFormat targetSdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat formater = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            Date date = formater.parse(value, pos);
            String dateStrStandard = targetSdf.format(date);
            //得到标准格式 yyyy-MM-dd
            return dateStrStandard+"-**-**";
        } catch (Exception e) {
            log.error("fuzzy birth error", e);
            return value;
        }
    }

    /**
     * 对关键字段模糊
     * 模糊名称
     */
    public void fuzzyAllWordsByPolicy(List<Map<String, String>> list, List<FuzzyPolicy> policies) {
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(policies)) {
            list.parallelStream().forEach(map -> {
                if (map != null) {
                    policies.forEach(policy -> {
                        if (policy != null && policy.check()) {
                            String key = policy.getKey();
                            if (!StringUtils.isEmpty(key)) {
                                String value = map.getOrDefault(key, "");
                                if (!StringUtils.isEmpty(value)) {
                                    String fuzzed = fuzzyString(String.valueOf(value), policy.getDirection(),
                                            policy.getLength(), policy.getFuzzyCharacter(), 0);
                                    map.put(key, fuzzed);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 指定方向、长度、模糊字符 模糊字符串
     * @param target notnull
     * @param fuzzyDirection nullable, default FUZZY_DIRECTION_RIGHT_2_LEFT
     * @param fuzzyLength notnull 根据比例模糊时，传入的长度为最少模糊的长度
     * @param fuzzyCharacter nullable, default DEFAULT_FUZZY_CHARACTER
     * @param protectLen 保留长度（只在左右方向的时候起作用）
     */
    protected String fuzzyString( String target, String fuzzyDirection, int fuzzyLength, String fuzzyCharacter, int protectLen) {
        if (StringUtils.isEmpty(fuzzyCharacter)) {
            fuzzyCharacter = DEFAULT_FUZZY_CHARACTER;
        }
        if (StringUtils.isEmpty(fuzzyDirection)) {
            fuzzyDirection = FUZZY_DIRECTION_RIGHT_2_LEFT;
        }

        if (StringUtils.isEmpty(target) || target.length() <= protectLen) {
            return target;
        }
        TmpParam param = new TmpParam(target, "");
        getProtectString(param, fuzzyDirection, protectLen);
        target = param.getTarget();
        String protectString = param.getProtect();
        if (!StringUtils.isEmpty(target) && fuzzyLength>0) {
            int lenDiff = target.length()-fuzzyLength;
            String replStr = timesString(fuzzyCharacter, fuzzyLength);
            if (lenDiff > 0) {
                if (FUZZY_DIRECTION_LEFT_2_RIGHT.equals(fuzzyDirection)) {
                    target = replStr.concat(target.substring(fuzzyLength));
                } else if (FUZZY_DIRECTION_RIGHT_2_LEFT.equals(fuzzyDirection)) {
                    target = target.substring(0, target.length()-fuzzyLength).concat(replStr);
                } else if (FUZZY_DIRECTION_CENTER_2_BOTHSIDE.equals(fuzzyDirection)) {
                    if (lenDiff >= 2) {
                        int startStringLen = (target.length() - fuzzyLength) / 2;
                        int endStrStartIndex = startStringLen + fuzzyLength;
                        return target.substring(0,startStringLen).concat(replStr).concat(target.substring(endStrStartIndex));
                    } else {
                        return target.substring(0, target.length()-fuzzyLength).concat(replStr);
                    }
                }
            } else {
                return timesString(fuzzyCharacter, target.length());
            }
        }
        target = addProtectStringBack(target, fuzzyDirection, protectString);
        return target;
    }

    private class TmpParam {
        String target = "";
        String protect = "";

        public TmpParam(String target, String protect) {
            this.target = target;
            this.protect = protect;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getProtect() {
            return protect;
        }

        public void setProtect(String protect) {
            this.protect = protect;
        }
    }

    /**
     * 把保留字符串还原回去
     * 将对target做修改 1234 如果 从右到左， 保留5  则target变成12345
     * @param target
     * @param fuzzyDirection
     * @param protectString
     * @return
     */
    private String addProtectStringBack(String target, String fuzzyDirection, String protectString) {
        if (!StringUtils.isEmpty(protectString)) {
            if (FUZZY_DIRECTION_RIGHT_2_LEFT.equals(fuzzyDirection)) {
                target = target + protectString;
            } else if (FUZZY_DIRECTION_LEFT_2_RIGHT.equals(fuzzyDirection)) {
                target = protectString + target;
            }
        }
        return target;
    }

    /**
     * 获取保留字符，并且对target修改为目标类型
     * 比如：12345 如果 从右到左 protectLen为1 则 返回 5，target变成1234
     * @param param
     * @param fuzzyDirection
     * @param protectLen
     * @return
     */
    private void getProtectString(TmpParam param, String fuzzyDirection, int protectLen) {
        String protectString = "";
        String target = param.getTarget();
        if (!StringUtils.isEmpty(target) && protectLen>0) {
            if (target.length() > protectLen) {
                if (FUZZY_DIRECTION_RIGHT_2_LEFT.equals(fuzzyDirection)) {
                    protectString = target.substring(target.length()-protectLen);
                    target = target.substring(0, target.length()-protectLen);
                } else if (FUZZY_DIRECTION_LEFT_2_RIGHT.equals(fuzzyDirection)) {
                    protectString = target.substring(0, protectLen);
                    target = target.substring(protectLen);
                }
            }
        }
        param.setTarget(target);
        param.setProtect(protectString);
    }

    /**
     * 指定、长度 模糊字符串 从右向左，使用“*”模糊
     * @param target notnull
     * @param fuzzyLength notnull
     */
    protected String fuzzyString( String target, int fuzzyLength) {
        return this.fuzzyString(target, FUZZY_DIRECTION_RIGHT_2_LEFT, fuzzyLength, DEFAULT_FUZZY_CHARACTER, 0);
    }
    /**
     * 指定方向、长度、模糊字符 模糊字符串
     * @param target notnull
     * @param fuzzyDirection nullable, default FUZZY_DIRECTION_RIGHT_2_LEFT
     * @param fuzzyLength notnull
     */
    protected String fuzzyString( String target, String fuzzyDirection, int fuzzyLength) {
        return this.fuzzyString(target, fuzzyDirection, fuzzyLength, DEFAULT_FUZZY_CHARACTER, 0);
    }

    /**
     * 加长
     * @param str
     * @param time
     * @return
     */
    protected String timesString(String str, int time) {
        if (time<=0) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < time; i++) {
                sb.append(str);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String s = FuzzyUtils.instance.fuzzyString("123456", FuzzyUtils.instance.FUZZY_DIRECTION_CENTER_2_BOTHSIDE,2);
        //System.out.println(s);
        String s1 = "320125196405041228";
        String s2 = "13888888888";
        String s3 = "12345";
        String s4 = "1";
        String s5 = null;
        List<Map<String, Object>> names = new ArrayList<>();
        Arrays.asList(s1,s2,s3,s4,s5).forEach(v->{
            Map<String, Object> map = new HashMap<>();
            map.put("name", v);
            names.add(map);
        });
        List<FuzzyPolicy> policies = new ArrayList<>();
        policies.add(new FuzzyPolicy(FuzzyUtils.instance.FUZZY_BUSSINESS_CNIDCARDFUZZY, 2, "name"));
        List<BussinessPolicy> bPolicies = new ArrayList<>();
        bPolicies.add(new BussinessPolicy(FuzzyUtils.instance.FUZZY_BUSSINESS_NAME, "name", "*", 0.5f, 1, 6));

        //FuzzyUtils.instance.fuzzyAllWordsByPolicy(names, policies);
        FuzzyUtils.instance.fuzzyAllWordsByBussinessPolicy(names, bPolicies);
        names.forEach(map-> System.out.println(map.get("name")));



    }

}
