package cn.wxgis.jc.system.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "LoginMenuResponse", description = "登录资源")
@Data
public class LoginMenuResponse {


    /**
     * 菜单列表
     */
    private List<SysMenuResponse> menuList = new ArrayList<>();

    /**
     * 功能列表
     */
    private List<String> functionList = new ArrayList<>();

}
