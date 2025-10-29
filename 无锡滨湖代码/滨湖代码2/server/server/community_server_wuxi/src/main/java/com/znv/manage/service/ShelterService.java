package com.znv.manage.service;

import com.znv.manage.common.bean.Result;


public interface ShelterService {

    Result shelterCoordinatesList();

    Result queryCoordinatesDetails(Integer id);
}
