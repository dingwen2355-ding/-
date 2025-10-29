package cn.wxgis.jc.system.auth.service;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.util.IpUtils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.system.po.SysLogininfor;
import cn.wxgis.jc.system.web.service.SysLogininforService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SysRecordLogService {

    private final SysLogininforService logininforService;

    public void recordLogininfor(String username, String status, String message) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr());
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constant.LOGIN_SUCCESS, Constant.LOGOUT, Constant.REGISTER)) {
            logininfor.setStatus(GlobalStatusEnum.STATUS_SUCCESS.getCode());
        } else if (Constant.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(GlobalStatusEnum.STATUS_ERROR.getCode());
        }
        logininforService.insert(logininfor);
    }
}
