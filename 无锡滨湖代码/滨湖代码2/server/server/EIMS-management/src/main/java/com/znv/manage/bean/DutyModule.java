package com.znv.manage.bean;

import lombok.Data;

@Data
public class DutyModule {

  private long moduleId;
  private String dutyType;
  private String moduleName;
  private String startTime;
  private String endTime;
  private String isEndToday;
  private String precinctId;



}
