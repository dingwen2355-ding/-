package com.znv.manage.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.IndividualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/individual")
@Api(tags = "单兵")
public class IndividualController {
    @Autowired
    private IndividualService individualService;

    @ApiOperation(value = "获取图片base64验证码--无效")
    @GetMapping(value = "/verificationCode")
    public String verificationCode(){
        return individualService.verificationCode();
    }


    @ApiOperation(value = "登录获取token")
    @GetMapping(value = "/token")
    public String token(){
        return individualService.token();
    }

    @ApiOperation(value = "互联网--登录获取token")
    @GetMapping(value = "/HLWtoken")
    public String HLWtoken(){
        return individualService.HLWtoken();
    }

    @ApiOperation(value = "获取成员列表")
    @GetMapping(value = "/memberList")
    public JSONObject memberList(){
        return individualService.memberList();
    }

    @ApiOperation(value = "获取单兵gps信息")
    @GetMapping(value = "/gps")
    public JSONObject gps(){
        return individualService.gps();
    }

    @ApiOperation(value = "互联网-获取单兵gps信息")
    @GetMapping(value = "/HLWgps")
    public JSONObject HLWgps(){
        return individualService.HLWgps();
    }

    @ApiOperation(value = "创建临时组")
    @PostMapping(value = "/createGroup")
    public Result createGroup(@RequestParam("name") String name, @RequestParam("deviceIds") List<String> deviceIds){
        return new Result(individualService.createGroup(name,deviceIds));
    }

    @ApiOperation(value = "互联网-创建临时组")
    @PostMapping(value = "/HLWcreateGroup")
    public JSONObject HLWcreateGroup(@RequestBody JSONObject jsonObject){
        try{
            String name = jsonObject.getString("name");
            List<String> uids = jsonObject.getJSONArray("uids").toJavaList(String.class);
            return individualService.HLWcreateGroup(name,uids);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "解散临时组")
    @GetMapping(value = "/dissolveGroup")
    public JSONObject dissolveGroup(@RequestParam("gid") String gid){
        return individualService.dissolveGroup(gid);
    }

    @ApiOperation(value = "互联网-解散临时组")
    @GetMapping(value = "/HLWdissolveGroup")
    public JSONObject HLWdissolveGroup(@RequestParam("gid") String gid){
        return individualService.HLWdissolveGroup(gid);
    }

    @ApiOperation(value = "检查临时组是否有效--无效")
    @GetMapping(value = "/checkGroup")
    public JSONObject checkGroup(@RequestParam("gid") String gid){
        return individualService.checkGroup(gid);
    }

    @ApiOperation(value = "测试udp")
    @GetMapping(value = "/udp")
    public void checkGroup(@RequestParam("message") String message,@RequestParam("ip") String ip){
        try{
            //创建数据包对象，封装要发送的数据，接受端IP,端口
            byte[] data=message.getBytes();
            //创建InetAddress对象，封装自己的IP地址
            InetAddress inet=InetAddress.getByName(ip);
            DatagramPacket dp=new DatagramPacket(data,data.length,inet,8001);
            //创建DatagramSocket对象，数据包的发送和接受对象
            DatagramSocket ds=new DatagramSocket();
            //调用ds对象的方法send，发送数据包
            ds.send(dp);
            ds.close();
            log.info("udp数据已发送");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "test")
    @GetMapping(value = "/test")
    public void test(){
//        individualService.saveStatus();
    }

    @ApiOperation(value = "getZLIndividualGPS")
    @PostMapping(value = "/getZLIndividualGPS")
    public JSONObject getZLIndividualGPS(@RequestBody JSONArray jsonArray){
        return individualService.saveInfo(jsonArray);
    }

    @ApiOperation(value = "HLWOnlineIndividual")
    @PostMapping(value = "/HLWOnlineIndividual")
    public JSONObject HLWOnlineIndividual(@RequestBody JSONArray array){
        return individualService.saveStatus(array);
    }
}
