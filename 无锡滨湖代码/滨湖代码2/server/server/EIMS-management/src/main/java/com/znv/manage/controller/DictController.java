package com.znv.manage.controller;

import com.znv.manage.common.bean.Dict;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TCfgDict表控制层
 *
 * @author 0049003788
 * @since 2022-11-11 13:40:05
 */
@RestController
@RequestMapping("dict")
@Api(tags = "字典表")
public class DictController {
    @Resource
    private DictService dictService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody Dict dict) {
        return dictService.insert(dict);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody Dict dict) {
        return dictService.update(dict);
    }

    @GetMapping("/del")
    @ApiOperation("删除")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return dictService.deleteById(id);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return dictService.deleteBatch(ids);
    }

    @GetMapping("/query-condition")
    @ApiOperation("查询")
    public Result queryByCondition(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "dictName", required = false) String dictName,
                                   @RequestParam(value = "dictOrder", required = false) Integer dictOrder,
                                   @RequestParam(value = "dictType", required = false) String dictType,
                                   @RequestParam(value = "parentId", required = false) Integer parentId,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return dictService.queryByCondition(id, dictName, dictOrder, dictType, parentId, page, pageSize);
    }

    @GetMapping("/dictTree")
    @ApiOperation("字典树状图")
    public Result dictTree(@RequestParam(value = "parentId",required = false,defaultValue = "0") Integer parentId,
                           @RequestParam(value = "typeId",required = false) Integer typeId){
        Result result = new Result();
        List<Dict> treeContent = dictService.dictTree(parentId,typeId);
        result.setData(treeContent);
        result.setCode(ResultCodeEnum.NORMAL.getCode());
        result.setMessage(ResultCodeEnum.NORMAL.getName());
        return result;
    }

}

