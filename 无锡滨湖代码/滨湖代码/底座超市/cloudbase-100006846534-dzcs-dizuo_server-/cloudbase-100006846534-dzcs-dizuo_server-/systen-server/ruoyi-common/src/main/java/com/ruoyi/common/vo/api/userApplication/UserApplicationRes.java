package com.ruoyi.common.vo.api.userApplication;
import lombok.Data;
import java.util.List;

@Data
public class UserApplicationRes   {

    private String userName;

    private String account;

    private Long deptId;

    private String deptName;
    private String position;

    private List<AppInfo> appList;


    /**
     * 该职位下的应用
     */
    @Data
    public static class AppInfo {

        /**
         * 应用名称
         */
        private String name;
        private String appId;
        private String appSecret;
        /**
         * 应用描述
         */
        private String description;
        /**
         * 所属部门
         */
        private Long deptId;
        /**
         * 联系人
         */
        private String contacts;

        /**
         * 联系方式
         */
        private String telephone;
        /**
         * Url
         */
        private String url;
        /**
         * 协议类型
         */
        private String protocol;
        /**
         * 用户可展示信息
         */
        private String userColumn;
    }
}
