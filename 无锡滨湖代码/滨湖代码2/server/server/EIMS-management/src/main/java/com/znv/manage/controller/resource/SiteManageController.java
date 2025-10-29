package com.znv.manage.controller.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.SiteManageBean;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.service.LogService;
import com.znv.manage.service.resource.SiteManageService;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.znv.manage.common.utils.CommonUtils.checkFileName;

/**
 * @author yangbo
 * @email
 * @date 2022/11/9
 */
@RestController
@Api(tags = "场所管理")
@RequestMapping("/siteManage")
public class SiteManageController {

    @Value("${filePath}")
    private String filePath;
    @Value("${urlPath}")
    private String urlPath;
    @Value("${file.name.white}")
    private String fileNameWhite;
    @Value("${file.name.white.onOff:false}")
    private boolean fileNameWhiteOnOff;

    @Autowired
    SiteManageService siteManageService;

    @Resource
    LogService logService;


    @ApiOperation("查询")
    @GetMapping("/query")
    public Result query(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "category", required = false) String category,
                        @RequestParam(value = "address", required = false) String address,
                        @RequestParam(value = "acreage", required = false) String acreage,
                        @RequestParam(value = "precinctTreeId", required = false) String precinctTreeId,
                        @RequestParam(value = "d_Id", required = false) String precinctId,
                        @RequestParam(value = "MAXGALLERYFUL", required = false) String MAXGALLERYFUL,
                        @RequestParam(value = "streetName", required = false) String streetName,
                        @RequestParam(value = "community", required = false) String community,
                        @RequestParam(value = "type", required = false) String type,
                        @RequestParam(value = "pageNum", required = false) Integer pageNum,
                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return siteManageService.queryForList(name, category, address, acreage, precinctTreeId, precinctId, MAXGALLERYFUL, streetName, community, type, pageNum, pageSize);
    }

    @ApiOperation("保存")
    @PostMapping("/save")
    public Result save(@RequestBody SiteManageBean siteManageBean) {
        return siteManageService.save(siteManageBean);
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove")
    public Result remove(@RequestParam("id") String id) {
        return siteManageService.remove(id);
    }

    @ApiOperation("文件上传")
    @PostMapping(value = "uploadFile")
    public Result uploadFile(@RequestBody MultipartFile uploadFile) {
        Result result = new Result();
        if (fileNameWhiteOnOff && !checkFileName(uploadFile, fileNameWhite)) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage("文件名称或者格式不正确");
            return result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtils.uploadFile(uploadFile, filePath, urlPath));
        result.setData(sb);
        return result;
    }

    @ApiOperation("文件删除")
    @PostMapping(value = "deleteFile")
    public Boolean deleteFile(@RequestParam(value = "fileUrl", required = false) String fileUrl) {
        Boolean s = CommonUtils.deleteFile(fileUrl, filePath, urlPath);
        return s;
    }

    /**
     * 导出场所
     *
     * @return
     */
    @ApiOperation(value = "导出场所")
    @GetMapping(value = "/exportSite")
    public Result exportSite(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "category", required = false) String category,
                             @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "acreage", required = false) String acreage,
                             @RequestParam(value = "precinctTreeId", required = false) String precinctTreeId,
                             @RequestParam(value = "d_Id", required = false) String precinctId,
                             @RequestParam(value = "MAXGALLERYFUL", required = false) String MAXGALLERYFUL,
                             @RequestParam(value = "streetName", required = false) String streetName,
                             @RequestParam(value = "community", required = false) String community,
                             @RequestParam(value = "type", required = false) String type,
                             @RequestParam(value = "ids", required = false) String ids) {
        return siteManageService.exportSite(name, category, address, acreage, precinctTreeId, precinctId, MAXGALLERYFUL, streetName, community, type, ids);
    }

    /**
     * 导入场所
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "导入场所")
    @PostMapping(value = "/importSite")
    public Result importSite(@RequestBody MultipartFile file,
                             @RequestParam(value = "userId", required = false) String userId,
                             @RequestParam(value = "userName", required = false) String userName,
                             HttpServletRequest request) {
        logService.insertLog("导入场所", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(siteManageService.importSite(file).getCode()), siteManageService.importSite(file).getMessage());
        return siteManageService.importSite(file);
    }

    @ApiOperation("预案-避难场所查询")
    @GetMapping("/queryForResource")
    public Result query(@RequestParam(value = "shelterOrga", required = false) String shelterOrga,
                        @RequestParam(value = "shelterName", required = false) String shelterName,
                        @RequestParam(value = "shelterCategory", required = false) String shelterCategory,
                        @RequestParam(value = "planId", required = false) String planId,
                        @RequestParam(value = "pageStart", required = false) Integer pageStart,
                        @RequestParam(value = "pageLen", required = false) Integer pageLen) {
        return siteManageService.queryForResource(shelterOrga, shelterName, shelterCategory, planId, pageStart, pageLen);
    }

    @ApiOperation(value = "撒点")
    @GetMapping(value = "selectBasicShelter")
    public Result selectBasicShelter(@RequestParam(value = "shelterOrga", required = false) String shelterOrga,
                                     @RequestParam(value = "shelterName", required = false) String shelterName,
                                     @RequestParam(value = "shelterCategory", required = false) String shelterCategory,
                                     @RequestParam(value = "eventId", required = false) String eventId,
                                     @RequestParam(value = "isAll", required = false) String isAll,
                                     @RequestParam(value = "pageStart", required = false) Integer pageStart,
                                     @RequestParam(value = "pageLen", required = false) Integer pageLen) {
        return siteManageService.selectBasicShelterList(shelterOrga, shelterName, shelterCategory, eventId, isAll, pageStart, pageLen);
    }
}
