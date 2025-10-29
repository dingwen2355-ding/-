package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

public interface EmergencySuppliesService {
    Result emergencySuppliesCoordinatesList();

    Result queryCoordinatesDetails(Integer id);
}
