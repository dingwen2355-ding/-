package cn.wxgis.jc.screen.web.controller;

import cn.wxgis.jc.common.enums.system.DeptTypeEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.screen.domain.vo.assess.AssessVo;
import cn.wxgis.jc.screen.web.service.AssessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/assess")
@AllArgsConstructor
@Api(value = "AssessController", tags = "大屏接口-文明程度指数分析")
public class AssessController {

    private final AssessService assessService;

    @ApiOperation(value ="获取考核月份", httpMethod ="GET")
    @GetMapping("/getMonth")
    public JsonResult getMonth() {
        return JsonResult.ok(assessService.getMonth());
    }

    @ApiOperation(value ="获取考核周期", httpMethod ="GET")
    @GetMapping("/getCycle")
    public JsonResult getCycle() {
        return JsonResult.ok(assessService.getCycle());
    }

    @ApiOperation(value ="文明程度排名", httpMethod ="GET")
    @GetMapping("/assessList")
    public JsonResult assessList(String month, Integer type) {
        if(StringUtils.isNull(type)) type = DeptTypeEnum.REGION.getCode();
        BigDecimal score = BigDecimal.ZERO;
        BigDecimal lastScore = BigDecimal.ZERO;
        // 上个月分数
        String lastmonth = null;
        Map<String, Object> map = new HashMap<>();
        List<String> cycles = assessService.getCycle();
        if (StringUtils.isBlank(month)) {
            if (StringUtils.isEmpty(cycles)) return JsonResult.ok("请选择考核周期");
            month = cycles.get(0);
            for (int i = 0; i < cycles.size(); i++ ){
                if (cycles.get(i).equals(month)) {
                    if (i+1 < cycles.size()) {
                        lastmonth = cycles.get(i+1);
                    }
                }
            }
        }
        Integer last = Integer.parseInt(month.substring(month.indexOf("-")+1))-1;
        String year = month.substring(0, month.indexOf("-")+1);
        lastmonth = year + (last < 10 ? "0" + last : String.valueOf(last));
        List<AssessVo> list = assessService.assessList(month, type);
        map.put("list", list);
        if (StringUtils.isNotEmpty(list)) {
            List<BigDecimal> numbers = list.stream().map(AssessVo::getScore).collect(Collectors.toList());
            BigDecimal scoreTotal = numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            score = scoreTotal.divide(new BigDecimal(7), 2, RoundingMode.DOWN);

        }
        if (StringUtils.isNotBlank(lastmonth)) {
            List<AssessVo> lastList = assessService.assessList(lastmonth, type);
            List<BigDecimal> numbers = lastList.stream().map(AssessVo::getScore).collect(Collectors.toList());
            BigDecimal scoreTotal = numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            lastScore = scoreTotal.divide(new BigDecimal(7), 2, RoundingMode.DOWN);
        }
        map.put("score", score);
        map.put("lastScore", lastScore);
        return JsonResult.ok(map);
    }

    @ApiOperation(value ="获取文明指数程度趋势", httpMethod ="GET")
    @GetMapping("/assessTrend")
    public JsonResult assessTrend() {
        return JsonResult.ok(assessService.assessTrend());
    }

    @ApiOperation(value ="文明程度指数的涨跌数据", httpMethod ="GET")
    @GetMapping("/assessChange")
    public JsonResult assessChange() {
        return JsonResult.ok(assessService.assessChange());
    }


    public static void main(String[] args) {
        String month = "2023-07";
        System.out.println(Integer.parseInt(month.substring(month.indexOf("-")+1)));
        System.out.println(month.substring(0, month.indexOf("-")+1));
    }
}
