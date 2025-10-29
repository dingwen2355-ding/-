<!-- 智慧党建 地图上的弹窗 -->
<template>
  <div class="activityDialog" v-show="activityDialogShow == true">
    <div class="activityDialog_img">
      <div class="flex">
        <div class="title">{{ name }}</div>

        <div class="close" @click="Close()">
          <img class="close_img" src="@/assets/icon_btn_close.png" alt="close" />
        </div>
      </div>

      <div class="Img">
        <div class="Flex_">
          <div>
            <div>
              <div class="Title_">中心介绍</div>

              <div class="Text_" v-if="name == '无锡市湖滨假日商业旅游发展有限公司支部委员会'">
                &nbsp;&nbsp;无锡市湖滨假日商业旅游发展有限公司是蠡湖街道办事处下属的直属单位，湖滨假日商业旅游发展有限公司行政党支部成立于2011年，共有党员13名，以组织建设为抓手，以活动为载体，大力开展党群一体化建设，不断增强党组织的凝聚力和战斗力，先后被评为中国特色商业街“先进集体”、滨湖区先进基层党组织、滨湖区“五有”“五好”园区党组织等一系列荣誉称号。
                <br />
                &nbsp;&nbsp;自成立以来，湖滨商业街行政党支部夯实党组织基础，建立健全党支部组织机构，确保党内各项工作有序开展，不断完善组织制度，提高党员素质，加强党的理论知识学习，切实做到保证党对街区建设的领导地位，充分发挥街区党建的整体效应，积极开展服务群众工作，有效推动理论与支部标准化建设深度融合。
              </div>

              <div class="Text_" v-else-if="name == '无锡市湖滨假日物业服务有限公司'">
                &nbsp;&nbsp;无锡市湖滨假日物业服务有限公司是蠡湖街道办事处下属的直属单位，于2020年正式成立，共有党员4名，发展对象1名。
                <br />
                &nbsp;&nbsp;无锡市湖滨假日物业服务有限公司党支部以“搭建大物业、大服务”平台为使命，积极发挥战斗堡垒作用，立足服务街区群众，搭建起物业、业主、社区之间的桥梁，创新物业管理日常监督机制，规范物业费、水电费收缴，推动智能化物业建设，按照“优化班子、增强合力、发挥优势、服务人民”的原则，通过各条线统筹推进工作，落实组织生活各项制度，提高基层支部整体素质，将党支部工作与物业服务工作有机融合，把物业服务工作打造成党组织服务联系业主的重要平台，做到“民有所呼，我有所应”。
              </div>

              <div class="Text_" v-else-if="name == '无锡市湖滨商业街餐饮行业支部委员会'">
                &nbsp;&nbsp;无锡市湖滨商业街餐饮行业支部委员会隶属于无锡市湖滨假日商业旅游发展有限公司总支部委员会，成立于2014年，共有党员29名,发展对象2名。
                <br />
                &nbsp;&nbsp;餐饮党支部以餐饮管理服务为主要发展目标，深入践行“以党建促服务，以服务强党建”的工作理念，为街区业主搭建共谋发展的平台，支部采用自学、专题研讨等形式，抓实党员的整治理论学习，不断推进党支部工作创新，近年来，支部党员发挥先锋模范作用，开展为一线人员送温暖、为特殊群体送爱心等志愿服务活动，推进餐饮创新服务，带动街区餐饮品牌发展，积极营造湖滨商业街快速发展的良好氛围。
              </div>

              <div class="Text_" v-else-if="name == '无锡市蠡湖集贸市场支部委员会'">
                &nbsp;&nbsp;蠡湖集贸市场是蠡湖街道办事处下属的直属单位，2022年5月1日蠡湖市场完成改造，于7月1日正式投入使用，蠡湖集贸市场党支部成立于2007年，共有党员9名。
                <br />
                &nbsp;&nbsp;蠡湖集贸市场党支部始终坚持“管理和培育，规范和发展”的管理方针，增强党支部建设吸引力、执行力和战斗力，完善党支部规章制度，蠡湖市场结合群众所需所盼，深入推进市场管理物业化模式，实施“三级包保”责任体系和“网格化监管+落实主体责任”管理机制，建立城管、市监、环保等多部门联动管理，以优质的硬件配套和软件服务增强群众的安全感和幸福感，被评为无锡市“五星级文明诚信市场”。
              </div>

              <div class="Text_" v-else>
                &nbsp;&nbsp;无锡市蠡园供销合作社是蠡湖街道办事处下属的直属单位。作为80年代初工业品下乡、农副产品进城的主要流通桥梁，蠡湖供销合作社在促进乡镇经济繁荣的进程中发挥了不可磨灭的主力军作用。而在时代飞速发展的今天，蠡园供销合作社依然在探索新时期可持续发展之路，努力为经济发展和谐稳定，服务于民提升形象发挥着应有的作用。
                <br />
                &nbsp;&nbsp;蠡园供销社现有职工干部23人：其中在职管理人员8人、下岗职工15人；成立支部一个，现有党员7人。社属店铺二十余家，占地面积四千来余平方米，分别集中在中南西路本社所在地、北桥、稻香三个主要地段，其中以稻香租赁产出为最高。
                <br />
                &nbsp;&nbsp;近年来，蠡园供销社在蠡湖街道党工委和上级社的支持领导下，紧紧围绕“以人为本、和谐稳定、转型提升、稳中求进谋发展”这一目标，以“抓党建、促发展”为工作思路，优化客户服务，不断调整经营思路，盘活、盘大资产，力求提升资产产出率。同时我社围绕经济发展工作，不断注入党建新内涵，以创建服务型党组织建设为抓手，支部党员在各自岗位上积极做好示范，党的思想建设、组织建设、作风建设和制度建设进一步加强。供销社先后获得“先进党支部”、安全生产先进单位”等荣誉称号。
              </div>

            </div>

            <div class="block">
              <div class="block_Title">中心详情</div>
              <div class="block_Text">
                <div>
                  地址：
                  <span>{{ Address }}</span>
                </div>
                <div class="lien"></div>

                <div>
                  街道：
                  <span>蠡湖街道</span>
                </div>
                <div class="lien"></div>

                <div>
                  社工：
                  <span>{{ Count }}人</span>
                </div>
                <div class="lien"></div>
              </div>
            </div>

            <div class="block" style="margin-top: 28px">
              <div class="block_Title">服务中心</div>
              <div class="block_Text">
                <div>
                  周一到周五：
                  <span>09:00-12:00,13:00-17:00</span>
                </div>
                <!-- <div class="lien"></div> -->

                <!-- <div>
                  周六到周日：
                  <span>09：00 - 18：00</span>
                </div> -->

              </div>
            </div>
          </div>

          <div style="margin-left: 8px">
            <div class="Title_">人员信息</div>
            <div class="Block">
              <div>
                <ul class="head">
                  <li>
                    <div>
                      <span>序号</span>
                    </div>
                    <div>
                      <span>姓名</span>
                    </div>
                    <div>
                      <span>手机号</span>
                    </div>
                    <div>
                      <span>职务</span>
                    </div>
                  </li>
                </ul>

                <ul class="content">
                  <li class="" v-for="(item, index) in Data" :key="index">
                    <div>
                      <span>{{ index + 1 }}</span>
                    </div>
                    <div>
                      <span>{{ item.name }}</span>
                    </div>
                    <div>
                      <span>{{ item.phone }}</span>
                    </div>
                    <div>
                      <span>{{ item.duties || "党员" }}</span>
                    </div>
                  </li>
                </ul>

              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GetPartyOrgDetail } from "@/utils/api/partyBuilding";

export default {
  name: "ActivityDialog",

  data() {
    return {
      activityDialogShow: false,
      name: "",
      Address: "",
      Count: "",
      Data: [],
    };
  },

  computed: {},

  watch: {},

  methods: {
    Close() {
      console.log("关闭视频弹窗1");
      this.activityDialogShow = false;
    },

    getPartyOrgDetail(name = "无锡市蠡湖集贸市场支部委员会") {
      let that = this;
      GetPartyOrgDetail({
        name: name,
        pageNo: 1,
        pageSize: 1000,
      }).then((res) => {
        console.log("----智慧党建-获取点位-弹窗-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          that.Address = res.data.address;
          that.Count = res.data.count;
          that.Data = Object.freeze(res.data.data);
        }
      });
    },
  },

  mounted() { },
};
</script>

<style lang="scss" scoped>
.activityDialog {
  position: fixed;
  top: 15vh;
  left: 50%;
  transform: translateX(-50%);

  .activityDialog_img {
    width: 800px;
    height: 580px;
    background-image: url("@/assets/party-building/img_window_800_bg.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;

    .flex {
      display: flex;
      justify-content: space-between;

      .title {
        font-size: 15px;
        transform: translate(58px, 17px);
      }

      .close {
        transform: translate(0, 13px);
        margin-right: 10px;
        cursor: pointer;
        width: 22px;
        height: 22px;

        .close_img {
          width: 100%;
          height: 100%;
        }
      }
    }

    .Img {
      width: 752px;
      height: 483px;
      margin: 0 auto;
      margin-top: 6%;
      overflow-y: auto;

      .Flex_ {
        display: flex;
        justify-content: space-between;

        .Title_ {
          width: 364px;
          height: 28px;
          line-height: 28px;
          color: #00d5ff;
          font-size: 16px;
          font-weight: 700;
          padding-left: 8px;
          opacity: 1;
          background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 100%);
        }

        .Text_ {
          width: 364px;
          height: 144px;
          font-size: 16px;
          margin-top: 8px;
          padding-right: 8px;
          text-align: justify;
          box-sizing: border-box;
          overflow-y: auto;
        }

        .block {
          margin-top: 16px;

          .block_Title {
            margin: 20px 0 12px 0;
            padding-left: 12px;
            height: 23px;
            line-height: 23px;
            font-weight: 700;
            color: #00d5ff;
            font-size: 16px;
            border-left: 4px solid #00d5ff;
          }

          .block_Text {
            font-size: 16px;
            font-weight: 400;

            .lien {
              margin: 12px 0 12px 0;
              width: 100%;
              height: 1px;
              background: linear-gradient(90deg, #00d5ff 0%, #00d5ff00 101.22%);
            }
          }
        }

        .Block {
          margin-top: 8px;

          ul.head {
            margin-top: 8px;

            li {
              color: rgb(0, 213, 255);
              height: 42px;
              font-size: 16px;
              font-weight: 400;
              background-color: rgba(29, 80, 120, 0.8);
              display: flex;
              align-items: center;
            }

            div:nth-child(1) {
              margin-left: 8px;
            }

            div:nth-child(2) {
              margin-left: 23px;
            }

            div:nth-child(3) {
              margin-left: 33px;
            }

            div:nth-child(4) {
              margin-left: 62px;
            }
          }

          ul.content {
            height: 405px;
            overflow-y: auto;

            li {
              height: 42px;
              display: grid;
              grid-template-columns: 56px auto;
              align-items: center;
              font-size: 14px;
              font-weight: 400;
              background-color: rgba(31, 168, 239, 0.2);
              display: flex;

              &:nth-child(even) {
                background-color: rgba(31, 168, 239, 0.1);
              }

              div {
                &:nth-child(1) {
                  text-align: center;
                  width: 50px;
                }

                &:nth-child(2) {
                  width: 55px;
                  margin-left: 15px;
                }

                &:nth-child(3) {
                  width: 100px;
                  margin-left: 10px;
                }

                &:nth-child(4) {
                  width: 100px;
                  margin-left: 10px;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>