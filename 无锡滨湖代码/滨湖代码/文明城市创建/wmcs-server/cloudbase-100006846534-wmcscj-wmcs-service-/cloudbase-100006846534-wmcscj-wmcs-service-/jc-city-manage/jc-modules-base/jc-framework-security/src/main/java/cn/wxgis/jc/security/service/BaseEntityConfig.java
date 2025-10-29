package cn.wxgis.jc.security.service;

import cn.wxgis.jc.jpa.domain.po.BasePO;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.model.LoginUser;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class BaseEntityConfig implements MetaObjectHandler {

    /**
     * 使用mp做添加操作时候，这个方法执行
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BasePO) {
            BasePO baseEntity = (BasePO) metaObject.getOriginalObject();
//            // 创建时间为空，则以当前时间为插入时间
//            if (Objects.isNull(baseEntity.getCreateTime())) {
//                baseEntity.setCreateTime(new Date());
//            }
            // 此处写获取用户及用户名的业务代码
            LoginUser user = SecurityUtils.getLoginUser();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(user) && Objects.isNull(baseEntity.getCreateBy())) {
                baseEntity.setCreateBy(user.getUser().getUserName());
            }
        }
    }

    /**
     * 使用mp做修改操作时候，这个方法执行
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 此处写获取用户及用户名的业务代码
        LoginUser user = SecurityUtils.getLoginUser();
        if (Objects.nonNull(user)) {
            this.setFieldValByName("updateBy", user.getUser().getUpdateBy(), metaObject);
        }

    }

}
