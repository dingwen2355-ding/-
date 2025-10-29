package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("cm_api_token")
public class ApiToken {



    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Date expired;


    private Date createTime;


    public ApiToken(String token, Date expired, Date createTime) {
        this.token = token;
        this.expired = expired;
        this.createTime=createTime;
    }
    public ApiToken() {
    }

}
