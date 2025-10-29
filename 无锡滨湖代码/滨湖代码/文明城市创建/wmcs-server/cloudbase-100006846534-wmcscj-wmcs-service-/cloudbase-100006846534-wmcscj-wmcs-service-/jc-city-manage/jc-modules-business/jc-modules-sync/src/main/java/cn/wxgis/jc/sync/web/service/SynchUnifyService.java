package cn.wxgis.jc.sync.web.service;

import cn.wxgis.jc.synch.result.ResultVo;

public interface SynchUnifyService {


    ResultVo getToken();

    /**
     * 同步部门数据
     * @return
     */
    int synchDeptData(Integer noFetchChild);

    /**
     * 同步用户数据
     * @return
     */
    int synchUserData(String id);

    void syncRegion();

    /**
     * 从统一身份认证数据更新到本系统
     */
    void updateUnifyToSysUser();

}
