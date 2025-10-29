package cn.wxgis.jc.system.web.service.impl;

import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.system.po.SysDevice;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysDeviceBindUserRequest;
import cn.wxgis.jc.system.req.SysDeviceSaveRequest;
import cn.wxgis.jc.system.resp.SysDeviceResponse;
import cn.wxgis.jc.system.select.SysDeviceSelect;
import cn.wxgis.jc.system.web.mapper.SysDeviceMapper;
import cn.wxgis.jc.system.web.mapper.SysUserMapper;
import cn.wxgis.jc.system.web.service.SysDeviceService;
import cn.wxgis.jc.system.web.service.entity.SysDeviceEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysDeviceServiceImpl extends BaseServiceImpl<SysDeviceMapper, SysDevice> implements SysDeviceService {

    @Autowired
    private SysUserMapper userMapper;
    public SysDeviceServiceImpl(SysDeviceMapper baseDao) {
        super(baseDao);
    }

    @Transactional
    @Override
    public int save(SysDeviceSaveRequest request) {
        SysDevice device = SysDeviceEntity.getSaveDevice(request);
        return validResult(baseDao.insert(device));
    }

    @Transactional
    @Override
    public int edit(SysDeviceSaveRequest request) {
        SysDevice device = validDataNull(baseDao.selectById(request.getId()));
        device = SysDeviceEntity.getUpdateDevice(device,  request);
        return validResult(baseDao.updateById(device));
    }

    @Transactional
    @Override
    public int delete(String id) {
        return baseDao.deleteById(id);
    }

    @Transactional
    @Override
    public int bind(SysDeviceBindUserRequest request) {
        SysDevice device = validDataNull(baseDao.selectById(request.getId()));
        device.setUserId(request.getUserId());
        return validResult(baseDao.updateById(device));
    }

    @Transactional
    @Override
    public int unbind(SysDeviceBindUserRequest request) {
        SysDevice device = validDataNull(baseDao.selectById(request.getId()));
        device.setUserId("");
        return validResult(baseDao.updateById(device));
    }

    @Override
    public ExistsReturn exists(Map<String, String> existsParam) {
        ExistsReturn existsReturn = new ExistsReturn();
        boolean exists = false;
        LambdaQueryWrapper<SysDevice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(existsParam.get(ExistsParam.name)), SysDevice::getName, existsParam.get(ExistsParam.name))
                .eq(StringUtils.isNotBlank(existsParam.get(ExistsParam.code)), SysDevice::getCode, existsParam.get(ExistsParam.code))
                .ne(StringUtils.isNotBlank(existsParam.get(ExistsParam.id)), SysDevice::getId, existsParam.get(ExistsParam.id));
        List<SysDevice> list = baseDao.selectList(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            exists = true;
            existsReturn.setMessage(String.format("设备信息:%s", existsParam.get(ExistsParam.name)) + GlobalStatusEnum.DATA_EXISTE.getMsg());
        }
        return existsReturn.setExists(exists);
    }

    @Override
    public IPage<SysDeviceResponse> page(SysDeviceSelect select) {
        LambdaQueryWrapper<SysDeviceResponse> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(select.getName()), SysDeviceResponse::getName, select.getName())
                .eq(StringUtils.isNotBlank(select.getCode()), SysDeviceResponse::getCode, select.getCode())
                .eq(StringUtils.isNotBlank(select.getUserName()), SysDeviceResponse::getUserName, select.getUserName())
                .eq(StringUtils.isNotBlank(select.getPhonenumber ()), SysDeviceResponse::getPhonenumber, select.getPhonenumber())
                .eq(SysDeviceResponse::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        IPage<SysDeviceResponse> responseIPage = baseDao.selectPageCustom(getPage(select.getPageNum(), select.getPageSize(), select.getOrderField(), select.isOrderAsc()), queryWrapper);
        return responseIPage;
    }

    @Override
    public SysDeviceResponse findResponseById(String id) {
        SysDeviceResponse response = new SysDeviceResponse();
        SysDevice device = validDataNull(baseDao.selectById(id));
        if (StringUtils.isNull(device)) return response;
        BeanUtils.copyProperties(device, response);
        if (StringUtils.isNotBlank(device.getUserId())) {
            SysUser user = userMapper.selectById(device.getUserId());
            response.setUserName(user.getNickName());
            response.setPhonenumber(user.getPhonenumber());
        }
        return response;
    }
}
