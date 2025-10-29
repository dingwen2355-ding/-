package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.LeaderFileServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderFileController {


    @Autowired
    private LeaderFileServiceImpl leaderFileService;


    @GetMapping("/queryLeaderFileList")
    public Result queryLeaderFileList(@RequestParam(value = "title",required = false)String title,
                                      @RequestParam(value = "type",required = false)String type,
                                      @RequestParam(value = "pageStart",defaultValue = "1")Integer pageStart,
                                      @RequestParam(value = "pageLen",defaultValue = "10")Integer pageLen){
        return leaderFileService.queryLeaderFileList(title,type,pageStart,pageLen);
    }
}
