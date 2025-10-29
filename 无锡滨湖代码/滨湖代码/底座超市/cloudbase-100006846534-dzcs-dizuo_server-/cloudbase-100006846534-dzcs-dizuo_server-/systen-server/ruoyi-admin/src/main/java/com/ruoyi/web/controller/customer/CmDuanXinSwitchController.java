package com.ruoyi.web.controller.customer;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.customer.domain.CmDuanXinSwitch;
import com.ruoyi.system.customer.service.CmDuanXinSwitchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/system/duanXinSwitch")
public class CmDuanXinSwitchController {

    private final CmDuanXinSwitchService cmDuanXinSwitchService;

    @GetMapping(value = "/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok(cmDuanXinSwitchService.list());
    }

    @PostMapping(value = "/changeStatus")
    public R save(@RequestBody CmDuanXinSwitch cmDuanXinSwitch) {
        CmDuanXinSwitch tmp = cmDuanXinSwitchService.getById(cmDuanXinSwitch.getId());
        tmp.setStatus(cmDuanXinSwitch.getStatus());
        tmp.setUpdateTime(new Date());
        tmp.setUpdateBy(SecurityUtils.getUserId());
        cmDuanXinSwitchService.saveOrUpdate(tmp);
        return R.ok();
    }
}
