package cn.wxgis.jc.fegin;


import cn.wxgis.jc.civilization.data.service.CheckItemService;
import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.security.annotation.InnerAuth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
public class RemoteCheckItemController extends BaseController {

    private final CheckItemService checkItemService;

    /**
     * 根据检查项id查询检查项
     */
    @InnerAuth
    @PostMapping(ProviderConstant.CHECKITEM_FIND_BY_ID)
    public JsonResult findById(@RequestParam("id") String id) {
        return JsonResult.ok(checkItemService.selectById(id));
    }

}
