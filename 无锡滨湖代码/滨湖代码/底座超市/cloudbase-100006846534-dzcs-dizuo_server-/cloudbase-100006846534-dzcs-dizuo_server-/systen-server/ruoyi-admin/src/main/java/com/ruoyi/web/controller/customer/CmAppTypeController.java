package com.ruoyi.web.controller.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.customer.domain.CmAppType;
import com.ruoyi.system.customer.service.ICmAppTypeService;
import com.ruoyi.web.controller.customer.common.CsBaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "cmAppType")
public class CmAppTypeController extends CsBaseController<ICmAppTypeService, CmAppType, String> {


    @Override
    public void setQueryWrapperForList(QueryWrapper<CmAppType> wrapper, CmAppType t) {
        if (StringUtils.isNotBlank(t.getTypeName())) {
            wrapper.like("type_name", t.getTypeName());
        }

        if (t.getStatus() != null) {
            wrapper.eq("status", t.getStatus());
        }
        if (StringUtils.isNotBlank(t.getAppModule())) {
            wrapper.eq("app_module", t.getAppModule());
        }
        wrapper.orderByAsc("sort_by");
    }

    @PostMapping("/updateStatus")
    public R<?> delete(@RequestParam(value = "id") String id,
                       @RequestParam long status) {
        boolean b = getService().updateStatus(id, status) > 0;
        return R.ok(b);
    }
}
