package com.ruoyi.web.controller.houseInfo;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.customer.domain.ResidentInfo;
import com.ruoyi.system.customer.domain.RoomBaseInfo;
import com.ruoyi.system.customer.service.HouseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/house")
public class HouseInfoController
{
    private static final Logger log = LoggerFactory.getLogger(HouseInfoController.class);

    @Autowired
    private HouseInfoService houseInfoService;


    /**
     * 获取房屋信息
     *
     */
    @GetMapping("/getRoomBaseInfoByBuindingCode")
    public R getRoomBaseInfoByBuindingCode(String gmlId)
    {
        if(StringUtils.isEmpty(gmlId)){
            return R.fail("请输入地址");
        }
        List<RoomBaseInfo> roomBaseInfoByBuindingCode = houseInfoService.getRoomBaseInfoByBuindingCode(gmlId);
        return R.ok(roomBaseInfoByBuindingCode);
    }

    /**
     * 获取房屋人员信息
     *
     */
    @GetMapping("/getresidentInfoByRoomCode")
    public R getresidentInfoByRoomCode(String roomCode)
    {
        if(StringUtils.isEmpty(roomCode)){
            return R.fail("请输入房屋编号");
        }
        List<Map<String,String>> roomBaseInfoByBuindingCode = houseInfoService.getresidentInfoByRoomCode(roomCode);
        return R.ok(roomBaseInfoByBuindingCode);
    }


}
