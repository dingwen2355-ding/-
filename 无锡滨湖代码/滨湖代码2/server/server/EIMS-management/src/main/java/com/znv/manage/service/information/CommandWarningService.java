package com.znv.manage.service.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.WarningRelease;

public interface CommandWarningService {

    Result getWarning(Long id, String title, String deal, String type1, String type2, String level, String startDate, String endDate, String area,String flag);

    Result getWarningScreen(Long id, String title, String deal, String type1, String type2, String level, String startDate, String endDate, String area);

    Result updateWarning(WarningRelease messageRelease);

    Result insertWarning(WarningRelease messageRelease);

    Result deleteWarning(Long id);

    Result dealWarning(Long id, String content, String deal, String userId, String title);

    Result warningCount(String area, String type, String startDate, String endDate);
}
