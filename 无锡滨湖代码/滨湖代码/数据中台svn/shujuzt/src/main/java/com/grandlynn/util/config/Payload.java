package com.grandlynn.util.config;


import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.grandlynn.util.constant.BaseEnumType;
import com.grandlynn.util.constant.ResultConstant;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.AbstractObject;
import com.grandlynn.util.pojo.BeanCopierUtils;
import com.grandlynn.util.pojo.CloneDirection;
import org.apache.commons.lang3.StringUtils;

public class Payload<T> implements Serializable {
   private static final long serialVersionUID = -1549643581827130116L;
   private T payload;
   private String code;
   private String msg;

   public Payload() {
      this.code = ResultConstant.SUCCESS.getCode();
      this.msg = ResultConstant.SUCCESS.getMsg();
   }

   public Payload(String code, String msg) {
      this.code = ResultConstant.SUCCESS.getCode();
      this.msg = ResultConstant.SUCCESS.getMsg();
      this.code = code;
      this.msg = msg;
   }

   public Payload(T payload) {
      this.code = ResultConstant.SUCCESS.getCode();
      this.msg = ResultConstant.SUCCESS.getMsg();
      this.payload = payload;
   }

   public Payload(BaseEnumType be) {
      this.code = ResultConstant.SUCCESS.getCode();
      this.msg = ResultConstant.SUCCESS.getMsg();
      this.code = be.getCode();
      this.msg = be.getMsg();
   }

   public Payload(T payload, String code, String msg) {
      this.code = ResultConstant.SUCCESS.getCode();
      this.msg = ResultConstant.SUCCESS.getMsg();
      this.payload = payload;
      this.code = code;
      this.msg = msg;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getMsg() {
      return this.msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public T getPayload() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
      return this.payload;
   }

   public void setPayload(T payload) {
      this.payload = payload;
   }

   public boolean success() {
      return this.getCode().equals("0");
   }

   public T resolverPayload() {
      if (this.success()) {
         return this.payload;
      } else {
         throw new ApplicationException(this.getCode(), this.getMsg());
      }
   }

   public <T> T copyPayload(Class<T> targetClazz) {
      if (!this.success()) {
         throw new ApplicationException(this.getCode(), this.getMsg());
      } else if (this.payload == null) {
         return null;
      } else if (this.payload instanceof AbstractObject) {
         AbstractObject abstractObject = (AbstractObject)this.payload;
         return abstractObject.clone(targetClazz, CloneDirection.FORWARD);
      } else {
         T target = null;

         try {
            target = targetClazz.newInstance();
         } catch (Exception var4) {
            throw new ApplicationException("创建实例错误！", var4);
         }

         BeanCopierUtils.copyProperties(this.payload, target);
         return target;
      }
   }

   private Payload(Throwable e) {
      this.code = ResultConstant.SUCCESS.getCode();
      this.msg = ResultConstant.SUCCESS.getMsg();
      ResultConstant resultConstant = ResultConstant.ERROR;
      this.code = resultConstant.getCode();
      this.msg = resultConstant.getMsg();
      if (e != null && !StringUtils.isEmpty(e.toString())) {
         this.msg = "系统异常：" + e.toString();
      }

   }
}
