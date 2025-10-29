/*
 * @Descripttion:
 * @version:
 * @Author: GM
 * @Date: 2021-04-15 18:06:49
 * @LastEditors: GM
 * @LastEditTime: 2021-08-03 11:22:40
 */
import IP from "../ipconfig";
const api = {
  //建筑物统计
  Building: {
    BuildingNum: IP.server_statistics + "/statistics/data/build", //建筑物数量
    HouseProperty: IP.server_statistics + "/statistics/data/build/byType", //   房屋性质统计
    HouseArea: IP.server_statistics + "/statistics/data/build/byArea", //房屋面积
    HouseUse: IP.server_statistics + "/statistics/data/build/byPurpose" //房屋用途
  },
  //两新组织组织
  Organization: {
    CompanyNum: IP.server_statistics + "/statistics/data/organization", //组织总数
    IndustryType:
      IP.server_statistics + "/statistics/data/organization/byIndustryType", //组织统计
    CompanyType: IP.server_statistics + "/statistics/data/organization/byType" //组织统计
  },
  // 单位
  Company: {
    CompanyNum: IP.server_statistics + "/statistics/data/company", //单位总数
    IndustryType:
      IP.server_statistics + "/statistics/data/company/byIndustryType", //单位行业类型统计
    CompanyType: IP.server_statistics + "/statistics/data/company/byType" //单位类型统计
  },
  //   人口统计
  Population: {
    PeopleNum: IP.server_statistics + "/statistics/data/population", //人口总数和男女比例
    Age: IP.server_statistics + "/statistics/data/population/byAge", //年龄统计
    Political:
      IP.server_statistics + "/statistics/data/population/byPolitical", //政治面貌统计
    Education:
      IP.server_statistics + "/statistics/data/population/byEducation", //教育程度统计
    Religious: IP.server_statistics + "/statistics/data/population/byReligious" //宗教信仰统计
  },
  // 党员统计
  dytj: {
    byParty_member_type: IP.server_statistics + "/statistics/data/party/byParty_member_type", //党员类型统计
    byParty_inParty: IP.server_statistics + "/statistics/data/party/byParty_inParty", //党员入党时间段统计
    byParty_age: IP.server_statistics + "/statistics/data/party/byParty_age", //党员年龄段统计
    byParty_nation: IP.server_statistics + "/statistics/data/party/byParty_nation", //党员民族统计
    byParty_education: IP.server_statistics + "/statistics/data/party/byParty_education", //党员文化程度
    byOrganization: IP.server_statistics + "/statistics/data/party/byOrganization", //党组织统计
  }
};
export default api;
