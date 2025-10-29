package com.znv.manage.controller.tip;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.InformationBean;
import com.znv.manage.common.bean.tip.QueryInformationBean;
import com.znv.manage.service.tip.InformationManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资讯管理
 *
 * @author chd
 * @date 2022/8/29
 */
@RestController
@RequestMapping("/informationManage")
@Api(tags = "资讯管理")
@Slf4j
public class InformationManageController {
    @Resource
    InformationManageService informationManageService;

    /**
     * 新增资讯
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "新增资讯")
    @PostMapping(value = "/add")
    private Result add(@RequestParam(value = "informationBean") String data,
                       @RequestParam(value = "files", required = false) MultipartFile[] files) {
        InformationBean informationBean = JSONObject.parseObject(data, InformationBean.class);
        return informationManageService.add(informationBean, files);
    }


    /**
     * 编辑资讯
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "编辑资讯")
    @PostMapping(value = "/edit")
    private Result edit(@RequestParam(value = "informationBean") String data,
                        @RequestParam(value = "files", required = false) MultipartFile[] files) {
        InformationBean informationBean = JSONObject.parseObject(data, InformationBean.class);
        return informationManageService.edit(informationBean, files);
    }

    /**
     * 删除资讯
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除资讯")
    @PostMapping(value = "/del")
    private Result del(@RequestBody List<String> ids) {
        return informationManageService.del(ids);
    }

    /**
     * 资讯查询
     *
     * @param queryInformationBean
     * @return
     */
    @ApiOperation(value = "资讯查询")
    @PostMapping(value = "/query")
    private Result query(@RequestBody QueryInformationBean queryInformationBean) {
        return informationManageService.query(queryInformationBean);
    }

    /**
     * 导入资讯
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "导入资讯")
    @PostMapping(value = "/import")
    public Result importInformation(@RequestBody MultipartFile file) {
        return informationManageService.importInformation(file);
    }

    @ApiOperation(value = "置顶")
    @PostMapping(value = "/updateIsTop")
    private Result updateIsTop(@RequestParam(value = "id") String id) {
        return informationManageService.updateIsTop(id);
    }
}
