package com.znv.manage.common.bean;

import lombok.Data;

@Data
public class OperateLog {

  private long id;
  private String username;
  private String clientIp;
  private String operateTime;
  private String operateModule;
  private String operateFunction;
  private String operateType;
  private String operateDetail;
  private String operateResult;
  private String precinctId;


}
