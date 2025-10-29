package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @Description: xxx描述该类主要说明
 */
@Slf4j
@RestController
@RequestMapping("/rest")
@Api(tags = "测试REST风格接口")
public class TestRestController {

    @ApiOperation(value = "返回结果为Result")
    @GetMapping(value = "/test1")
    public Result test1(@RequestParam(name = "id") String id) {
        log.debug("-----" + id + "-------");
        return new Result(id);
    }

    @ApiOperation(value = "返回结果为String")
    @GetMapping(value = "/test2")
    public String test2(@RequestParam(name = "id", required = false) String id) {
        log.debug("-----" + id + "-------");
        return id;
    }

    @ApiOperation(value = "返回结果为List")
    @GetMapping(value = "/test3")
    public List<Map<String, Object>> test3() {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("key1", "value1");
        map1.put("key2", "value2");
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("key3", "value3");
        map2.put("key4", "value4");
        list.add(map2);

        return list;
    }
}
