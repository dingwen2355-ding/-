package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.GeometryUtil;
import com.znv.manage.common.utils.GpsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author znv
 * @Description: xxx描述该类主要说明
 */
@Slf4j
@RestController
@Api(tags = "测试REST风格接口")
public class TestRestController {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(TestRestController.class);

    @ApiOperation(value = "返回结果为Result")
    @GetMapping(value = "/test1")
    public Result test1(@RequestParam(name = "id") String id){
        log.debug("-----"+id+"-------");
        return new Result(id);
    }

    @ApiOperation(value = "返回结果为String")
    @GetMapping(value = "/test2")
    public String test2(@RequestParam(name = "id",required = false) String id){
        log.debug("-----"+id+"-------");
        return id;
    }

    @ApiOperation(value = "返回结果为List")
    @GetMapping(value = "/test3")
    public List<Map<String,Object>> test3(){

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("key1","value1");
        map1.put("key2","value2");
        list.add(map1);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("key3","value3");
        map2.put("key4","value4");
        list.add(map2);

        return list;
    }

    @ApiOperation(value = "precinctId")
    @GetMapping(value = "/test4")
    public Map<String, String> test4(@RequestParam(value = "gpsx",required = true)String gpsx,
                                     @RequestParam(value = "gpsy",required = true)String gpsy){
        return GeometryUtil.getPrecinctIdByGps(gpsx,gpsy);
    }

    @ApiOperation(value = "gps")
    @GetMapping(value = "/test5")
    public Map<String, String> test5(@RequestParam(value = "city",required = true)String city,
                                     @RequestParam(value = "address",required = true)String address){
        return GpsUtils.getGps(city,address);
    }


    @GetMapping(value = "/Lxy")
    public String day(@RequestParam(value = "day",required = false)String day){
        log.info("lxy 输入:"+day);
        logger.info("lxy 输入:"+day);
        try{
            List<String> list = Arrays.asList("中","中","早","早","夜","夜","休","休");
            String start = "2021-11-16";
            String result = "";
            if(StringUtils.isEmpty(day)){
                result = list.get(Integer.parseInt(DateUtil.getTwoDay(DateUtil.getStringDateShort(), start))%8);
            }else {
                result = list.get(Integer.parseInt(DateUtil.getTwoDay(day, start))%8);
            }
            if(!StringUtils.isEmpty(result)){
                if(!StringUtils.isEmpty(day)){
                    return "日期："+day+",班次："+ result;
                }else {
                    return "今天："+DateUtil.getStringDateShort()+",班次："+ result;
                }

            }else {
                return "结果异常,请检查输入格式是否正确,例:2021-11-22";
            }
        }catch (Exception e){
            log.info(e.getMessage());
            logger.info(e.getMessage());
            return "结果异常,请检查输入格式是否正确,例:2021-11-22,且大于2021-11-16";
        }
    }

}
