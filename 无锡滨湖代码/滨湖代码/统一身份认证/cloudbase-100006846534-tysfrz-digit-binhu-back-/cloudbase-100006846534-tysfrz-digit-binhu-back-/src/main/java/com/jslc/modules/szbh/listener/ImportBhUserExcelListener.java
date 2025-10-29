package com.jslc.modules.szbh.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.service.IBhDeptService;
import com.jslc.modules.szbh.service.IBhUserService;
import com.jslc.modules.szbh.vo.ImportBhUserInfoVO;
import com.jslc.modules.szbh.vo.ResultVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BladeX
 * @since
 */

@Slf4j
@NoArgsConstructor
public class ImportBhUserExcelListener extends AnalysisEventListener<ImportBhUserInfoVO> {

    String WRONG_CODE = "400";

    private int row = 1;

    @Getter
    private ResultVO resultVO = new ResultVO();

    private List<BhUser> bhUserEntity = new ArrayList<>();

    private IBhUserService bhUserService;

    private IBhDeptService iBhDeptService;

    private Long deptId;
    /**
     * 账户与数据库校验是否相同
     */
    private Map<String, Long> userAccoutMap = new HashMap<>();
    /**
     * 部门是否存在数据中
     */
    private Map<String, Long> bhDeptMap = new HashMap<>();
    /**
     * excel自检点位名称是否相同
     */
    private Map<String, Integer> nameMap = new HashMap<>();

    public ImportBhUserExcelListener(IBhUserService bhUserService,IBhDeptService iBhDeptService,Long deptId) {
        this.iBhDeptService = iBhDeptService;
        this.bhUserService = bhUserService;
        this.deptId = deptId;
        getUserAccountList();
        getDeptList();
    }

    @Override
    public void invoke(ImportBhUserInfoVO importBhUserInfo, AnalysisContext analysisContext) {
        //检测必填项是否为null
        checkNull(importBhUserInfo);

        putSiteInfo(importBhUserInfo);
        //每解析一条,行数+1
        row++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isNotEmpty(bhUserEntity)) {
            try {
                bhUserService.submitList(bhUserEntity);
//                List<SiteInfoExtra> extraList = new ArrayList<>();
//                for (SiteInfo siteInfo : siteEntity) {
//                    //点位扩展表
//                    SiteInfoExtra extra = new SiteInfoExtra();
//                    extra.setSiteInfoId(siteInfo.getId());
//                    extra.setExtraInfo("{}");
//                    extraList.add(extra);
//                }
//                iSiteInfoExtraService.saveBatch(extraList);
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resultVO.setCode(WRONG_CODE);
                resultVO.setMessage("导入失败,请稍后再试");
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        //如果excel解析失败
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{" + (excelDataConvertException.getRowIndex() + 1) + "}行," +
                    "第{" + (excelDataConvertException.getColumnIndex() + 1) + "}列,解析错误");
            throw excelDataConvertException;
        }
        throw exception;
    }

    private void checkNull(ImportBhUserInfoVO importBhUserInfo) {

        if (deptId == null || "".equals(deptId)) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,请勾选主部门导入");
            throw new RuntimeException();
        }
        //账户
        if (importBhUserInfo.getAccount() == null || "".equals(importBhUserInfo.getAccount().trim())) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,账户信息未填写,请填写后重新导入");
            throw new RuntimeException();
        } else {
            if (userAccoutMap.containsKey(importBhUserInfo.getAccount())) {
                resultVO.setCode(WRONG_CODE);
                resultVO.setMessage("导入失败,第" + row + "行,与已有的账户信息重复,请修改后重新导入");
                throw new RuntimeException();
            }
            //判断导入excel中是否有相同的账户
            if (nameMap.size() >= 1) {
                if (nameMap.containsKey(importBhUserInfo.getAccount())) {
                    resultVO.setCode(WRONG_CODE);
                    resultVO.setMessage("导入失败,第" + (nameMap.get(importBhUserInfo.getAccount())) + "行与第" + row + "行点位名称重复,请修改后重新导入");
                    throw new RuntimeException();
                }
            }
            nameMap.put(importBhUserInfo.getAccount(), row);
        }
        //账户名称
        if (importBhUserInfo.getName() == null || "".equals(importBhUserInfo.getName().trim())) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,账户名称未填写,请填写后重新导入");
            throw new RuntimeException();
        }


        //主职位
        if (importBhUserInfo.getMajorPosition() == null || "".equals(importBhUserInfo.getMajorPosition().trim())) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,主职位未填写,请填写后重新导入");
            throw new RuntimeException();
        }
        //账号状态
        if (importBhUserInfo.getIsEnableName()!= null && !("停用".equals(importBhUserInfo.getIsEnableName().trim()) ||"启用".equals(importBhUserInfo.getIsEnableName().trim()))) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,未匹配到该账号状态,请填写请填写（启用，停用）后重新导入");
            throw new RuntimeException();
        }
        //手机号
        if (importBhUserInfo.getMajorMobile() == null || "".equals(importBhUserInfo.getMajorMobile().trim())) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,未填写手机号,请填写后重新导入");
            throw new RuntimeException();
        }
        //是否开启发送短信
        if (importBhUserInfo.getIsSendSmsName()!= null && !("开启".equals(importBhUserInfo.getIsSendSmsName().trim()) ||"关闭".equals(importBhUserInfo.getIsSendSmsName().trim()))) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,是否开启关闭短信发送,请填写（开，关）两种状态后重新导入");
            throw new RuntimeException();
        }

        //性别
        if (importBhUserInfo.getGender() == null || "".equals(importBhUserInfo.getGender().trim())) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,性别信息未填写,请填写后重新导入");
            throw new RuntimeException();
        }else if (!("男".equals(importBhUserInfo.getGender().trim()) ||"女".equals(importBhUserInfo.getGender().trim()))) {
            resultVO.setCode(WRONG_CODE);
            resultVO.setMessage("导入失败,第" + row + "行,未匹配到该性别,请填写请填写（男，女）后重新导入");
            throw new RuntimeException();
        }


    }

    /**
     * @return void
     * @description 根据deptInfoId查询所有点位
     * @Param []
     * @author
     * @date 2020/12/17
     */
    private void getUserAccountList() {
        List<BhUser> siteInfos = bhUserService.list(Wrappers.<BhUser>query().lambda()
                .eq(BhUser::getIsDeleted, 0));
        if (CollectionUtils.isNotEmpty(siteInfos)) {
            userAccoutMap = siteInfos.stream().collect(Collectors.toMap(BhUser::getAccount, BhUser::getId, (oldVal, currVal) -> currVal));
        }
    }
    private void getDeptList() {
        List<BhDept> siteInfos = iBhDeptService.list(Wrappers.<BhDept>query().lambda()
                .eq(BhDept::getIsDeleted, 0));
        if (CollectionUtils.isNotEmpty(siteInfos)) {
           bhDeptMap = siteInfos.stream().collect(Collectors.toMap(BhDept::getName, BhDept::getDeptId, (oldVal, currVal) -> currVal));
        }
    }

    private void putSiteInfo(ImportBhUserInfoVO importBhUserInfo) {
        BhUser bhUser = new BhUser();
        bhUser.setAccount(importBhUserInfo.getAccount());
        bhUser.setName(importBhUserInfo.getName());
//        bhUser.setMajorDept(bhDeptMap.get(importBhUserInfo.getMajorDept()));
        bhUser.setMajorDept(deptId);
        bhUser.setMajorPosition(importBhUserInfo.getMajorPosition());
        bhUser.setIsEnable(Func.isNotEmpty(importBhUserInfo.getIsEnableName())? importBhUserInfo.getIsEnableName().equals("停用") ? 0:1 :0);
        bhUser.setTelephone(importBhUserInfo.getMajorMobile());
        bhUser.setMajorMobile(importBhUserInfo.getMajorMobile());
        bhUser.setIsSendSms(Func.isNotEmpty(importBhUserInfo.getIsSendSmsName())? importBhUserInfo.getIsSendSmsName().equals("关闭") ? 0:1 :0);

//        if (Func.isNotEmpty(importBhUserInfo.getOtherDeptName())){
//            List<Long> deptId = new ArrayList<>();
//            List<String> deptName = Func.toStrList(importBhUserInfo.getOtherDeptName());
//            for (String s : deptName) {
//                deptId.add(bhDeptMap.get(s));
//            }
//            bhUser.setOtherDept(Func.join(deptId,","));
//        }
//        bhUser.setOtherPosition(importBhUserInfo.getOtherPosition());
        bhUser.setGender(Func.isNotEmpty(importBhUserInfo.getGender())? importBhUserInfo.getGender().equals("男") ? 1:2 :null);
        bhUser.setEnglishName(importBhUserInfo.getEnglishName());
        bhUser.setEmail(importBhUserInfo.getEmail());
        bhUser.setExternalCorpName(importBhUserInfo.getExternalCorpName());
        bhUserEntity.add(bhUser);
    }
}
