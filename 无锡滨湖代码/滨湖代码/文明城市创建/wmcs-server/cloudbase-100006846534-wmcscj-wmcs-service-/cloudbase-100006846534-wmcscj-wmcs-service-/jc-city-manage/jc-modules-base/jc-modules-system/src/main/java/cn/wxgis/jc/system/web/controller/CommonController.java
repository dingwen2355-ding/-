package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.domain.vo.MenusVo;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.common.FlagEnum;
import cn.wxgis.jc.common.enums.common.ShowEnum;
import cn.wxgis.jc.common.enums.common.StatusEnum;
import cn.wxgis.jc.common.enums.system.DeptStatusEnum;
import cn.wxgis.jc.common.enums.system.DeptTypeEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.system.model.DictVO;
import cn.wxgis.jc.system.web.service.SysDictDataService;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/common")
@Api(value = "CommonController", tags = "系统枚举类值API")
public class CommonController {

    private final SysDictDataService dictDataService;

    /**
     * 获取枚举类值
     * @return 用户信息
     */
    @GetMapping("/menus")
    @ApiOperation(value ="获取枚举类值", httpMethod ="GET")
    public JsonResult menusList() {
        Map<String, Object> map = new HashMap<>();
        // 通用是否
        map.put("common_flag", new MenusVo("通用是否", "common_flag", FlagEnum.toList()));
        // 是否删除
        map.put("del_flag", new MenusVo("是否删除", "del_flag", DelFlagEnum.toList()));
        // 是否显示
        map.put("show_flag", new MenusVo("是否显示", "show_flag", ShowEnum.toList()));
        // 使用状态
        map.put("use_status", new MenusVo("使用状态", "use_status", StatusEnum.toList()));

        // 部门状态
        map.put("dept_status", new MenusVo("部门状态", "dept_status", DeptStatusEnum.toList()));
        // 部门类型
        map.put("dept_type", new MenusVo("部门类型", "dept_type", DeptTypeEnum.toList()));
        // 菜单类型
        map.put("menu_type", new MenusVo("菜单类型", "menu_type", DeptTypeEnum.toList()));
        // 用户状态
        map.put("user_status", new MenusVo("用户状态", "user_status", DeptTypeEnum.toList()));
        return JsonResult.ok(map);
    }


    @ApiOperation(value ="获取字典值", httpMethod ="GET")
    @GetMapping("/dict")
    public JsonResult dict() {
        List<DictVO> list = dictDataService.dict();
        return JsonResult.ok(list);
    }

}
