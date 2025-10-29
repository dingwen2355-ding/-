package com.znv.manage.bean.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessagePrecinct {
    private String precinctId;

    private String precinctName;

    private String upPrecinctId;

    private int precinctKind;

    private List<MessageUser> users = new ArrayList<>();
}
