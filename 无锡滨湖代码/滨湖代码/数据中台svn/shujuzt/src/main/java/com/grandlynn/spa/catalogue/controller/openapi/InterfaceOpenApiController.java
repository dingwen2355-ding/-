package com.grandlynn.spa.sol.controller.openapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.grandlynn.spa.commons.data.Payload;
import com.grandlynn.spa.sol.domain.vo.AnalysisTotalVO;
import com.grandlynn.spa.sol.domain.vo.ApplyResourceDeptRankVO;
import com.grandlynn.spa.sol.domain.vo.DataTotalSizeRankVO;
import com.grandlynn.spa.sol.domain.vo.DeptAnalysisTotalVO;
import com.grandlynn.spa.sol.domain.vo.DeptApplyResourceVO;
import com.grandlynn.spa.sol.domain.vo.HotApplyCatelogueRankVO;
import com.grandlynn.spa.sol.domain.vo.ResourceDirShareOpenTypeRateVO;
import com.grandlynn.spa.sol.domain.vo.ResourceShareVO;
import com.grandlynn.spa.sol.domain.vo.TodoWorkVO;
import com.grandlynn.spa.sol.service.AnalysisService;
import com.grandlynn.spa.sol.service.InterfaceService;
import com.grandlynn.spa.sol.service.OrgDsApplyListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping({"/open-api/v1/interfaces"})
@Api(
   tags = {"api接口管理"}
)
public class InterfaceOpenApiController {
   private static final Logger log = LoggerFactory.getLogger(InterfaceOpenApiController.class);
   @Autowired
   private InterfaceService interfaceService;
   @Autowired
   private OrgDsApplyListService orgDsApplyListService;
   @Resource
   private AnalysisService analysisService;

   @GetMapping({"/customize"})
   public Payload getCustomizeData(String appId) {
      try {
         HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
         String paramData = JSON.toJSONString(request.getParameterMap(), new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue});
         return Payload.ok(this.interfaceService.getCustomizeData(appId, paramData));
      } catch (Exception var4) {
         log.error("自定义接口查询失败：{}", var4);
         return Payload.error("500", var4.getMessage());
      }
   }

   @GetMapping({"/normal"})
   public Payload getNormalData(String appId) {
      try {
         HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
         String paramData = JSON.toJSONString(request.getParameterMap(), new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue});
         List<Map<String, Object>> normalData = this.interfaceService.getNormalData(request.getParameter("appid"), paramData);
         return Payload.ok(normalData);
      } catch (Exception var5) {
         log.error("通用接口查询失败：{}", var5);
         return Payload.error("500", var5.getMessage());
      }
   }

   @GetMapping({"/total"})
   @ApiOperation("接口调用次数，接口数")
   public Payload<AnalysisTotalVO> total() {
      AnalysisTotalVO analysisTotal = this.analysisService.total();
      return new Payload(analysisTotal);
   }

   @GetMapping({"/getTotalByDeptId/{deptId}"})
   @ApiOperation("根据登录用户部门获取资源目录数、资源信息项、涉及来源系统、资源被申请次数")
   public Payload<DeptAnalysisTotalVO> getResourceTotalByDeptId(@PathVariable("deptId") Integer deptId) {
      DeptAnalysisTotalVO analysisTotalVO = this.analysisService.getTotalByDeptId(deptId);
      return new Payload(analysisTotalVO);
   }

   @GetMapping({"/countTodoWorkNumber"})
   @ApiOperation("根据用户id和角色ids，获取部门的待办工作")
   public Payload<TodoWorkVO> countTodoWorkNumber(Integer handleUserId, @RequestParam List<Integer> roleIds, @RequestHeader("auth-token") Map<String, String> header) {
      TodoWorkVO todoWorkVO = this.interfaceService.countTodoWorkNumberByDeptId(handleUserId, roleIds, header);
      return new Payload(todoWorkVO);
   }

   @GetMapping({"/countCurrentDeptApplyResource"})
   @ApiOperation("根据部门id，本部门提交的资源申请单")
   public Payload<DeptApplyResourceVO> countCurrentDeptApplyResource(@RequestParam("deptId") Integer deptId) {
      DeptApplyResourceVO todoWorkVO = this.interfaceService.countCurrentDeptApplyResource(deptId);
      return new Payload(todoWorkVO);
   }

   @ApiOperation("热门申请目录")
   @GetMapping({"/rankHotApplyCatelogueRank"})
   public Payload<List<HotApplyCatelogueRankVO>> rankHotApplyCatelogueRankVO(Integer deptId, Integer day) {
      List<HotApplyCatelogueRankVO> hotApplyCatelogueRankVOS = this.interfaceService.rankHotApplyCatelogueRankVO(deptId, day);
      return new Payload(hotApplyCatelogueRankVOS);
   }

   @GetMapping({"/rankResourceApplyDept/{deptId}"})
   @ApiOperation("根据部门id获取资源申请部门排名top5")
   public Payload<List<ApplyResourceDeptRankVO>> rankResourceApplyDept(@PathVariable("deptId") Integer deptId) {
      List<ApplyResourceDeptRankVO> applyResourceDeptRankVOS = this.interfaceService.rankResourceApplyDept(deptId);
      return new Payload(applyResourceDeptRankVOS);
   }

   @ApiOperation("数据总量排名 top5")
   @GetMapping({"/rankDataTotalSize/{deptId}"})
   public Payload<List<DataTotalSizeRankVO>> rankDataTotalSize(@PathVariable("deptId") Integer deptId) {
      List<DataTotalSizeRankVO> dataTotalSizeRankVOS = this.interfaceService.rankDataTotalSize(deptId);
      return new Payload(dataTotalSizeRankVOS);
   }

   @ApiOperation("计算资源目录共享开放占比")
   @GetMapping({"/calResourceDirShareOpenTypeRate/{deptId}"})
   public Payload<List<ResourceDirShareOpenTypeRateVO>> calResourceDirShareOpenTypeRate(@PathVariable("deptId") Integer deptId) {
      List<ResourceDirShareOpenTypeRateVO> resourceDirShareTypeRateVOS = this.interfaceService.calResourceDirShareOpenTypeRate(deptId);
      return new Payload(resourceDirShareTypeRateVOS);
   }

   @ApiOperation("个人主页资源共享")
   @GetMapping({"/getResourceShare/{deptId}"})
   public Payload<ResourceShareVO> getResourceShare(@PathVariable("deptId") Integer deptId) {
      ResourceShareVO resourceShare = this.interfaceService.getResourceShare(deptId);
      return new Payload(resourceShare);
   }
}
