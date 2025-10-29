package com.ruoyi.web.controller.customer.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.customer.CmBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CsBaseController<IS extends IService<T>, T extends CmBaseEntity,PK extends Serializable> extends BaseController {

    @Autowired
    private IS service;


    public IS getService(){
        return service;
    }

    @RequestMapping("/list")
    public R list(T t,Page<T> page) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        setQueryWrapperForList(wrapper,t);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        page.setSize(pageDomain.getPageSize());
        page.setCurrent(pageDomain.getPageNum());
        page = service.page(page,wrapper);
        return R.ok(page);
    }

    /**
     * 列表查询 自定义查询条件方法
     * @param wrapper
     * @param t
     */
    public abstract void setQueryWrapperForList(QueryWrapper<T> wrapper,T t);

    @GetMapping("/getDetail/{id}")
    public R<?> list(@PathVariable(value = "id") PK id) {
        T t = service.getById(id);
        if (t!=null){
            return R.ok(t);
        }
        return R.fail("该数据已被删除或不存在");
    }


    @PostMapping("/save")
    public R<?> save(@RequestBody T t) {
        Boolean b=null;
        if ( StringUtils.isNotBlank(t.getId())){
            t.setUpdateTime(new Date());
            service.updateById(t) ;
            afterUpdate(t);
        }else {
            beforeSave(t);
            b = service.save(t);
            afterSave(t);
        }
        return R.ok(b);
    }

    protected void afterUpdate(T t) {
    }

    public   void beforeSave(T t){

    }

    public void afterSave(T t) {
    }

    @PostMapping("/delete/{ids}")
    public R<?> delete(@PathVariable(value = "ids") String  ids) {
        if (StringUtils.isBlank(ids)){
            return R.fail("未选中任何数据");
        }
        String[] split = ids.split(",");
        boolean b = service.removeBatchByIds(Stream.of(split).collect(Collectors.toList()));
        return R.ok(b);
    }
}
