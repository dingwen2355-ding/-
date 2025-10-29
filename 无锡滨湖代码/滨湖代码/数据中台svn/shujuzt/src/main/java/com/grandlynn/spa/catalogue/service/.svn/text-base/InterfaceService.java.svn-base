package com.grandlynn.spa.sol.service;

import com.grandlynn.spa.commons.data.PageBean;
import com.grandlynn.spa.sol.domain.dto.InterfaceBaseDTO;
import com.grandlynn.spa.sol.domain.dto.InterfaceDTO;
import com.grandlynn.spa.sol.domain.dto.InterfaceDetailsDTO;
import com.grandlynn.spa.sol.domain.query.InterfaceModelRequestQuery;
import com.grandlynn.spa.sol.domain.query.InterfacesQuery;
import com.grandlynn.spa.sol.domain.vo.ApplyInterfaceVO;
import com.grandlynn.spa.sol.domain.vo.ApplyResourceDeptRankVO;
import com.grandlynn.spa.sol.domain.vo.DataTotalSizeRankVO;
import com.grandlynn.spa.sol.domain.vo.DeptApplyResourceVO;
import com.grandlynn.spa.sol.domain.vo.HotApplyCatelogueRankVO;
import com.grandlynn.spa.sol.domain.vo.IndexInterfaceVO;
import com.grandlynn.spa.sol.domain.vo.InterfaceApiListVO;
import com.grandlynn.spa.sol.domain.vo.InterfaceVO;
import com.grandlynn.spa.sol.domain.vo.OntologyModelApiVO;
import com.grandlynn.spa.sol.domain.vo.ResourceDirShareOpenTypeRateVO;
import com.grandlynn.spa.sol.domain.vo.ResourceShareVO;
import com.grandlynn.spa.sol.domain.vo.TodoWorkVO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InterfaceService {
   boolean insert(Integer var1, InterfaceDetailsDTO var2) throws IOException;

   int updateBaseInfo(InterfaceBaseDTO var1);

   int updateDetails(InterfaceDetailsDTO var1) throws IOException;

   boolean removeById(Long var1);

   InterfaceDTO getById(Long var1);

   PageBean<InterfaceVO> queryList(InterfacesQuery var1);

   InterfaceDetailsDTO queryDetails(InterfacesQuery var1, boolean var2) throws IOException;

   PageBean<InterfaceApiListVO> openApiQueryByPage(InterfacesQuery var1);

   PageBean<InterfaceVO> personalApplys(InterfacesQuery var1);

   InterfaceVO apiDocument(InterfacesQuery var1);

   OntologyModelApiVO getOntologyModel(InterfaceModelRequestQuery var1);

   List<Map<String, Object>> getCustomizeData(String var1, String var2) throws IOException;

   List<Map<String, Object>> getNormalData(String var1, String var2) throws IOException;

   boolean statusChange(InterfaceDTO var1);

   boolean desensitizationChange(InterfaceDTO var1);

   boolean apply(InterfaceDetailsDTO var1);

   InterfaceDetailsDTO getInterfaceByAppId(String var1);

   PageBean<ApplyInterfaceVO> queryApplyInterface(InterfacesQuery var1);

   boolean checkName(InterfacesQuery var1);

   IndexInterfaceVO indexData();

   List<InterfaceDTO> newest();

   List<InterfaceVO> apiTop10();

   List<InterfaceVO> lastApi();

   TodoWorkVO countTodoWorkNumberByDeptId(Integer var1, List<Integer> var2, Map<String, String> var3);

   DeptApplyResourceVO countCurrentDeptApplyResource(Integer var1);

   List<ApplyResourceDeptRankVO> rankResourceApplyDept(Integer var1);

   List<HotApplyCatelogueRankVO> rankHotApplyCatelogueRankVO(Integer var1, Integer var2);

   List<DataTotalSizeRankVO> rankDataTotalSize(Integer var1);

   List<ResourceDirShareOpenTypeRateVO> calResourceDirShareOpenTypeRate(Integer var1);

   ResourceShareVO getResourceShare(Integer var1);

   TodoWorkVO countTodoWorkNumberByDeptId();
}
