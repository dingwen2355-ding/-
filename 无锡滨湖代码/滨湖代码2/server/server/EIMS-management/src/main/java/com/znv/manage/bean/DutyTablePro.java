package com.znv.manage.bean;

import com.znv.manage.bean.PersonRole;
import lombok.Data;

import java.util.List;

@Data
public class DutyTablePro {

  private long id;
  private String dutyDate;
  private String dutyType;
  private String dutyModule;
  private List<PersonRole> personList;
  private String precinctId;


}

