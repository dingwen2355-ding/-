package com.znv.manage.service;


import java.util.Date;

public interface JdjlService
{
    int getData();

    int getLettersAndVisitsCount(Date beginTime, Date endTime);
}
