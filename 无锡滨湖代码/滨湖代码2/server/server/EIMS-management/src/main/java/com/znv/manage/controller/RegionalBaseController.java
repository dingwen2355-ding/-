package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.RegionalBaseService;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dailongli
 * @Date: 2018/8/22 10:25
 */
@Slf4j
@Data
@RestController
@RequestMapping("/regionalbase")
@Api(tags = "组织管理地区管理")
public class RegionalBaseController {

    @Autowired
    RegionalBaseService regionalBaseService;

    /**
     * 获取区域编码
     *
     * @param province
     * @param city
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object getRegionalCode(@RequestParam(required = false, name = "province") String province, @RequestParam(required = false, name = "city") String city) {
        if (city != null) {
            city = city.substring(0, 4);
            List<Map<String, String>> list = regionalBaseService.getCounty(city);
            return new Result(list);
        } else if (province != null) {
            province = province.substring(0, 2);
            List<Map<String, String>> list = regionalBaseService.getCity(province);
            return new Result(list);
        } else {
            List<Map<String, String>> list = regionalBaseService.getProvince();
            return new Result(list);
        }

    }

    /**
     * 获取areaCode对应的省市区中文名称
     *
     * @param areaCode
     * @return
     */
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public Object getRegionalName(@RequestParam(required = true, name = "areaCode") String areaCode) {
        Map<String, String> m = new HashMap<>();
        String provice, city, country;
        provice = regionalBaseService.getRegionalName(areaCode.substring(0, 2) + "0000");
        city = regionalBaseService.getRegionalName(areaCode.substring(0, 4) + "00");
        country = regionalBaseService.getRegionalName(areaCode);
        m.put("provice", provice);
        m.put("city", city);
        m.put("country", country);
        return new Result(m);


    }
}
