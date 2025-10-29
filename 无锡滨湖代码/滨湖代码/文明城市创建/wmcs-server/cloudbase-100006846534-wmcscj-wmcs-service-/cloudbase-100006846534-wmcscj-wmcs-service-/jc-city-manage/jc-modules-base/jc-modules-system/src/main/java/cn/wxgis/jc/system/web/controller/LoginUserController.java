package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.Base64Utils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.UpdatePasswordRequest;
import cn.wxgis.jc.system.web.service.PermissionService;
import cn.wxgis.jc.system.web.service.SysUserRoleService;
import cn.wxgis.jc.system.web.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "LoginUserController", tags = "登陆用户相关APi")
public class LoginUserController extends BaseController {

    private final PermissionService permissionService;
    private final SysUserService userService;
    private final SysUserRoleService userRoleService;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    @ApiOperation(value ="获取登陆用户信息", httpMethod ="GET")
    public JsonResult getInfo() {
        if (StringUtils.isNull(SecurityUtils.getLoginUser())) return JsonResult.error(GlobalStatusEnum.UNAUTHORIZED);
        // 设置用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username = loginUser.getUserName();

        loginUser = userService.getLoginUser(username);
        loginUser.getUser().setPassword("******");
        return JsonResult.ok("loginInfo", loginUser);
    }

    @Log(title = "修改密码", type = OperLogTypeEnum.UPDATE, operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/updateMyPassword")
    @ApiOperation( value ="修改密码", httpMethod ="POST")
    public JsonResult updateMyPassword(@ApiParam(value = "修改密码传参") @Validated @RequestBody UpdatePasswordRequest request){
        if (StringUtils.isNull(SecurityUtils.getLoginUser())) return JsonResult.error(GlobalStatusEnum.UNAUTHORIZED);
        String oldPwd = new String(Base64Utils.decode(request.getOldPassword()));
        String newPwd = new String(Base64Utils.decode(request.getNewPassword()));
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        if (!SecurityUtils.matchesPassword(oldPwd, sysUser.getPassword())) {
            return JsonResult.error(ConstantForError.OLD_PASSWORD_ERROR);
        }
        return JsonResult.ok(userService.updateMyPassword(sysUser, newPwd));
    }

    public static void main(String[] args) {
        System.out.println(SecurityUtils.encryptPassword("admin123"));
    }

}
