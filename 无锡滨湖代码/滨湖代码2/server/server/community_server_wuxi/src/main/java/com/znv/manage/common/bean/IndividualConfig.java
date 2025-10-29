package com.znv.manage.common.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IndividualConfig {
    @Value("${individual_host}")
    private String host;
    @Value("${hlw_host}")
    private String hlwHost;
    @Value("${individual_login}")
    private String login_name;
    @Value("${individual_password}")
    private String login_password;

    private String tokenUrl="/gis/auth/open/login";

    private String gpsUrl="/gis/api/location/getLatestCoords";

    private String memberUrl="/gis/api/pttUser/adminUserList";

    private String groupUrl="/gis/api/organization/getOrganizationTree2";

    private String verificationUrl="/gis/auth/open/verifyCode";

    private String createTemporaryGroupUrl="/gis/api/group/tmp/create";

    private String dissolveTemporaryGroupUrl="/gis/api/group/tmp/delete";

    private String checkTemporaryGroupUrl="/gis/api/group/tmp/checkInGrp";

    private String hlwgGpsUrl="/individual/HLWgps";

    private String hlwTokenUrl="/individual/HLWtoken";

    private String hlwCreateGroup="/individual/HLWcreateGroup";

    private String hlwDissolveGroup="/individual/HLWdissolveGroup";

    private String hlwZLgps="/individual/getZLIndividualGPS";

    public String getHlwZLgps(){
        return hlwHost+hlwZLgps;
    }

    public String getHlwGps() {
        return hlwHost+hlwgGpsUrl;
    }

    public String getHlwToken() {
        return hlwHost+hlwTokenUrl;
    }

    public String getHlwCreateGroup() {
        return hlwHost+hlwCreateGroup;
    }

    public String getHlwDissolveGroup() {
        return hlwHost+hlwDissolveGroup;
    }

    public String getLogin_name() {
        return login_name;
    }

    public String getLogin_password() {
        return login_password;
    }

    public String getTokenUrl() {
        return host+tokenUrl;
    }

    public String getGpsUrl() {
        return host+gpsUrl;
    }

    public String getMemberUrl() {
        return host+memberUrl;
    }

    public String getGroupUrl() {
        return host+groupUrl;
    }

    public String getVerificationUrl() {
        return host+verificationUrl;
    }

    public String getCreateTemporaryGroupUrl() {
        return host+createTemporaryGroupUrl;
    }

    public String getDissolveTemporaryGroupUrl() {
        return host+dissolveTemporaryGroupUrl;
    }

    public String getCheckTemporaryGroupUrl() {
        return host+checkTemporaryGroupUrl;
    }
}
