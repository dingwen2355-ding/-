package com.znv.manage.bean.user;

import lombok.Data;

@Data
public class AuthToken {
    private int id;
    private String token;
    private String description;

}
