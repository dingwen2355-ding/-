package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间字段为当前日期
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        // 填充创建人字段为当前登录人账号（假设从上下文获取）
        this.strictInsertFill(metaObject, "createBy", String.class, getCurrentUser());

        this.updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 填充更新时间字段为当前日期
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());

        // 填充更新人字段为当前登录人账号（假设从上下文获取）
        this.strictUpdateFill(metaObject, "updateBy", String.class, getCurrentUser());

    }

    private String getCurrentUser() {
        // 获取当前登录人账号的逻辑
        // 返回当前登录人账号
        Long userId = SecurityUtils.getUserId();
        return userId.toString();
    }
}
