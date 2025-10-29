package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class VideoConferencesInfo {

    private String conferenceId;

    private String externalLinkUrl;

    private List<String> phoneNumbers;

    private String requestId;

}
