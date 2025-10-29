<template>
  <el-dialog
    :title="'任务下派'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1931rem"
    height="825rem"
    class="TaskAssignDialog default-dialog-class large-screen"
    :append-to-body="true"
    :open="queryEventTask"
    :before-close="handleClose"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div class="right-container">
      <div class="content-01 con">
        <div class="con-title">
          <span class="s-title">事件基本信息</span>
        </div>
        <div class="con-detail">
          <div class="dis-group col01">
            <span class="d-label">事件类型：</span>
            <span class="c-label">{{ eventInfo.typeName }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">事件标题：</span>
            <span class="c-label">{{ eventInfo.eventTitle }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">事件级别：</span>
            <span class="c-label">{{ eventInfo.levelName }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">事件时间：</span>
            <span class="c-label">{{ eventInfo.eventTime }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">事件小类：</span>
            <span class="c-label">{{ eventInfo.smallTypeName }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">影响级别：</span>
            <span class="c-label">{{ eventInfo.impactLevel }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">监管单位：</span>
            <span class="c-label">{{ eventInfo.monitorUnit }}</span>
          </div>
          <div class="dis-group col02">
            <span class="d-label">事发地址：</span>
            <span class="c-label">{{ eventInfo.occurAddress }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">轻伤人数：</span>
            <span
              class="c-label"
              v-if="eventInfo.minorNum !== '' && eventInfo.minorNum !== null && eventInfo.minorNum !== undefined"
              >{{ eventInfo.minorNum }}人</span
            >
            <span class="c-label" v-else>{{ eventInfo.minorNum }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">重伤人数：</span>
            <span
              class="c-label"
              v-if="
                (eventInfo.seriousNum !== '' && eventInfo.seriousNum !== null) || eventInfo.seriousNum !== undefined
              "
              >{{ eventInfo.seriousNum }}人</span
            >
            <span class="c-label" v-else>{{ eventInfo.seriousNum }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">死亡人数：</span>
            <span
              class="c-label"
              v-if="eventInfo.deathNum !== '' && eventInfo.deathNum !== null && eventInfo.deathNum !== undefined"
              >{{ eventInfo.deathNum }}人</span
            >
            <span class="c-label" v-else>{{ eventInfo.deathNum }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">经济损失：</span>
            <span
              class="c-label"
              v-if="
                eventInfo.economicLoss !== '' && eventInfo.economicLoss !== null && eventInfo.economicLoss !== undefined
              "
              >{{ eventInfo.economicLoss }}万</span
            >
            <span class="c-label" v-else>{{ eventInfo.economicLoss }}</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">是否总结：</span>
            <span class="c-label">{{ eventInfo.isConclusion }}</span>
          </div>
          <div class="dis-group col05">
            <span class="d-label">事故总结：</span>
            <span class="c-label">{{ eventInfo.conclusion }}</span>
          </div>
        </div>
      </div>
      <div class="content-03 con">
        <div class="con-title">
          <span class="s-title">过程信息</span>
        </div>
        <div class="con-detail">
          <div class="tab-container tab-container-02" v-show="tabIndex === 1">
            <div class="workflow-div">
              <div class="workflow-group" v-for="(item, index) in eventInfo.eventNodeList" :key="index">
                <div class="flow-point" :class="{ 'is-active': item.isActive }" @click="clickNode(item)">
                  {{ item.nodeName }}
                </div>
                <div class="flow-icon" v-if="index != eventInfo.eventNodeList.length - 1"></div>
              </div>
            </div>
            <div class="flow-point-detail">
              <div class="flow-point-detail-group" v-for="(item, index) in eventNode.eventTaskList" :key="index">
                <div class="detail-point">
                  <div class="detail-group">
                    <div class="d-label">任务信息</div>
                    <div class="d-value"></div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">任务标题：</div>
                    <div class="d-value" :title="item.taskTitle">{{ item.taskTitle }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">下发时间：</div>
                    <div class="d-value">{{ item.issueTime }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">任务类型：</div>
                    <div class="d-value">{{ eventNode.nodeName }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">处置反馈：</div>
                    <div class="d-value">{{ statusMap[item.status] }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">反馈部门：</div>
                    <div class="d-value">{{ item.department }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">反馈时间：</div>
                    <div class="d-value">{{ item.dealTime }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">反馈内容：</div>
                    <div class="d-value" :title="item.feedback">{{ item.feedback }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">反馈附件：</div>
                    <div class="d-value">
                      <ExtraFileSegments :images="item.filePicture" :videos="item.fileVideo" :doucs="item.fileVoice" />
                      <!-- <div v-if="item.filePicture">
                        <el-image
                          style="width: 200rem; height: 100rem"
                          :src="splitMutiPictures(item.filePicture)[0]"
                          :preview-src-list="splitMutiPictures(item.filePicture)"
                        >
                          <div slot="error" class="image-slot">
                            <i class="el-icon-picture-outline"></i>
                          </div>
                        </el-image>
                      </div>
                      <span v-else>暂无附件</span> -->
                    </div>
                    <!--{{item.file}}-->
                  </div>
                  <div class="detail-group" v-if="item.file">
                    <div class="media-div" v-if="/^.+(\.mp4|\.MP4)$/.test(item.file)">
                      <znv-video class="d-video" :src="item.file" type="mp4"></znv-video>
                    </div>
                    <div class="media-div" v-else-if="item.file != ''">
                      <img class="d-img" :src="item.file" />
                    </div>
                    <div class="media-div" v-else></div>
                  </div>
                </div>
                <div class="detail-icon" v-if="index < eventNode.eventTaskList.length - 1"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import ExtraFileSegments from '../right/ExtraFileSegments'
export default {
  name: 'TaskAssignDialog',
  props: ['dialogVisibleFlag', 'infomation'],
  components: { ExtraFileSegments },
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.formInline = {
          title: '',
          precinctId: '',
          time: '',
          type: ''
        }
        this.currentPage = 1
        this.pageSize = 10
      }
    }
  },
  data() {
    return {
      statusMap: {
        0: '未处置',
        1: '已处置'
      },
      dialogVisible: false,
      taskTable: [],
      eventNode: {},
      /*eventInfo: {
        "id": 37,
        "eventTitle": "测试演练事件录入",
        "typeId": 2,
        "typeName": "自然灾害",
        "streetId": "3702030250201",
        "streetName": "大鹏街道办事处",
        "communityId": "370203025020101",
        "communityName": "王母社区",
        "levelName": "较大级别",
        "level": null,
        "eventTime": "2023-03-09 14:45:23",
        "smallTypeId": 5,
        "smallTypeName": "暴雨",
        "impactLevel": "1级",
        "monitorUnit": "测试监督单位",
        "occurAddress": "1",
        "minorNum": 3,
        "seriousNum": 2,
        "deathNum": 1,
        "economicLoss": "1",
        "isConclusion": "是",
        "summary": "1",
        "sourceName": "演练事件",
        "sourceId": "10",
        "isCommand": "是",
        "createTime": null,
        "eventNodeList": [
          {
            "id": 29,
            "eventId": 37,
            "nodeId": 2,
            "nodeName": "应急指挥",
            "isHidden": "0",
            "order": 1,
            "eventTaskList": [
              {
                "id": 23,
                "taskTitle": "应急指挥-1-图片",
                "contactId": 29,
                "issueTime": "2023-03-10 10:02:33",
                "person": "有关人员",
                "status": "0",
                "feedback": "1         ",
                "file": "http://172.16.7.11:8010/files/2023-03-13/20230313104709192-微信图片_20221230102746.jpg",
                "department": "1",
                "createTime": null,
                "dealTime": "2023-03-10 10:02:45"
              }
            ]
          },
          {
            "id": 28,
            "eventId": 37,
            "nodeId": 1,
            "nodeName": "事故接报",
            "isHidden": "0",
            "order": 2,
            "eventTaskList": [
              {
                "id": 21,
                "taskTitle": "事故接报-1-图片",
                "contactId": 28,
                "issueTime": "2023-03-10 09:54:36",
                "person": "admin",
                "status": "0",
                "feedback": "不管    ",
                "file": "",
                "department": "有关部门",
                "createTime": null,
                "dealTime": "2023-03-10 09:54:44"
              },
              {
                "id": 22,
                "taskTitle": "事故接报-2-视频",
                "contactId": 28,
                "issueTime": "2023-03-10 10:00:19",
                "person": "有关人员",
                "status": "0",
                "feedback": "1         ",
                "file": "",
                "department": "有关部门",
                "createTime": null,
                "dealTime": "2023-05-05 10:00:29"
              }
            ]
          }
        ],
        "lon": "114.3935212168",
        "lat": "22.666904393824",
        "file": "http://172.16.7.11:8010/files/2023-03-13/20230313104329295-WeChat_20230222104842.mp4",
        "conclusion": null,
        "spanStartTime": "2023-03-10 09:54:36",
        "spanEndTime": "2023-03-10 10:02:33"
      },*/
      eventInfo: {},
      tabIndex: 1, // 0 时间展示  1 过程展示 2 视频再现
      form: {
        eventTitle: '',
        eventTypeId: '',
        startTime: '',
        endTime: '',
        sourceId: '',
        streetName: '',
        communityName: '',
        page: 1,
        pageSize: 10
      }
    }
  },
  mounted() {
    this.queryEventTask()
  },
  methods: {
    splitMutiPictures(urlStr) {
      urlStr += ''
      return urlStr.split(',')
    },
    clickNode(item) {
      this.eventInfo.eventNodeList = this.eventInfo.eventNodeList.map((t) => {
        if (item.id === t.id) {
          t.isActive = !t.isActive
        } else {
          t.isActive = false
        }
        return t
      })
      this.eventNode = item
    },
    clickTab(type) {
      this.tabIndex = type
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    },
    reset() {
      this.currentPage = 1
      this.formInline = {
        title: '',
        precinctId: '',
        time: ''
      }
      // let time = new Date(new Date().toLocaleDateString())
      // let timeUnder = new Date(time.getTime() + 24 * 60 * 60 * 1000)
      this.getEventList()
    },
    queryEventTask() {
      let eventId = this.$store.getters.eventId.id
      let url = `/community/dapeng/eventTask/query-event-task?eventId=${eventId}`
      Axios.get(url).then((res) => {
        this.eventInfo = res.data.data.list.length > 0 ? res.data.data.list[0] : {}
      })
    },
    onSubmit() {
      this.currentPage = 1
      this.getEventList()
    }
  }
}
</script>

<style lang="scss" scoped>
.TaskAssignDialog {
  ::v-deep .el-dialog {
    width: 5441rem !important;
    height: 3170rem !important;
    .el-dialog__header {
      padding: 0;
      margin-top: 20rem;
    }
    .el-dialog__body {
      margin: 0;
      height: 2800rem;
      padding: 100rem 200rem;
    }
  }
  .right-container {
    margin-left: 10rem;
    min-height: 100%;
    height: auto;
    padding: 16rem;
    .content-01 {
      min-height: 838rem;
      width: 100%;
      .con-detail {
        display: flex;
        flex-wrap: wrap;
        font-size: 60rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #a1aac2;
        line-height: 100rem;
        padding: 14rem 11rem 14rem 11rem;
        .dis-group {
          position: relative;
        }
        .dis-group:nth-child(odd) {
          .d-label:before {
            position: absolute;
            content: '';
            background-image: url('../../../../assets/left/排名标签@2x.png');
            background-size: 100% 100%;
            left: 0rem;
            top: 33rem;
            width: 56rem;
            height: 60rem;
          }
        }
        .dis-group:nth-child(even) {
          .d-label:before {
            position: absolute;
            content: '';
            background-image: url('../../../../assets/left/分类@2x.png');
            background-size: 100% 100%;
            left: 0rem;
            top: 33rem;
            width: 56rem;
            height: 60rem;
          }
        }
        .d-label {
          margin-left: 86rem;
          font-size: 60rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #6fd1ff;
          line-height: 114rem;
        }
        .c-label {
          font-size: 60rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ffffff;
          line-height: 114rem;
        }
        .col01 {
          width: 1044rem;
        }
        .col02 {
          width: 1044rem;
        }
        .col05 {
          width: 5000rem;
        }
      }
    }
    .content-03 {
      min-height: 534rem;
      height: auto;
      .con-detail {
        min-height: 460rem;
        overflow: hidden;
        position: relative;
        .tab-container {
          width: 100%;
          height: 100%;
        }
        .tab-container-02 {
          height: 1826rem;
          overflow: auto;
          display: flex;
          align-items: baseline;
          flex-direction: column;
          .workflow-div {
            height: 350rem;
            padding-left: 36rem;
            display: flex;
            justify-content: space-evenly;
            flex-wrap: nowrap;
            align-items: center;
            .workflow-group {
              height: 720rem;
              width: auto;
              display: flex;
              flex-wrap: nowrap;
              align-items: center;
              .flow-point {
                cursor: pointer;

                width: 510rem;
                height: 210rem;
                background: rgba(149, 201, 255, 0.1);
                border: 1rem solid #95c9ff;
                box-shadow: 5rem 10rem 20rem skyblue, 5rem 10rem 20rem skyblue inset;
                display: flex;
                align-items: center;
                justify-content: center;

                font-size: 60rem;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #e0f3ff;
              }
              .flow-point.is-active {
                border: 1rem solid #95c9ff;
                box-shadow: 5rem 10rem 20rem skyblue, 5rem 10rem 20rem skyblue inset;

                font-size: 60rem;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #6fd1ff;
              }
              .flow-icon {
                margin-left: 52rem;
                margin-right: 52rem;
                width: 96rem;
                height: 80rem;
                background-image: url('../../../../assets/jiantou/jt.png');
                background-size: 100% 100%;
              }
            }
          }

          .flow-point-detail {
            height: 1384rem;
            padding-left: 36rem;
            display: flex;
            flex-wrap: nowrap;
            align-items: center;
            .flow-point-detail-group {
              height: 1384rem;
              display: flex;
              justify-content: space-evenly;
              flex-wrap: nowrap;
              align-items: center;
              .detail-point {
                width: 1060rem;
                height: 1184rem;
                background: rgba(255, 255, 255, 0);
                border: 2rem solid #00a8ff;
                border-radius: 6rem;
                padding: 15rem;
                .detail-group {
                  position: relative;
                  width: 100%;
                  display: flex;
                  flex-wrap: nowrap;
                  .d-label {
                    margin-left: 86rem;
                    width: 340rem;
                    font-size: 60rem;
                    font-family: Microsoft YaHei;
                    font-weight: 400;
                    color: #6fd1ff;
                    line-height: 114rem;
                  }
                  .d-value {
                    width: 638rem;
                    font-size: 60rem;
                    font-family: Microsoft YaHei;
                    font-weight: 400;
                    color: #ffffff;
                    line-height: 114rem;
                    overflow: hidden;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                  }
                  .d-img {
                    margin-top: 8rem;
                    background: #ffffff;
                    border: 2rem solid #00a8ff;
                    border-radius: 8rem;
                    width: 486rem;
                    height: 272rem;
                  }
                  .d-video {
                    margin-top: 8rem;
                    background: #000000;
                    border: 2rem solid #00a8ff;
                    border-radius: 8rem;
                    width: 486rem;
                    height: 272rem;
                  }
                }
                .detail-group:nth-child(odd) {
                  .d-label:before {
                    position: absolute;
                    content: '';
                    background-image: url('../../../../assets/left/排名标签@2x.png');
                    background-size: 100% 100%;
                    left: 0rem;
                    top: 33rem;
                    width: 56rem;
                    height: 60rem;
                  }
                }
                .detail-group:nth-child(even) {
                  .d-label:before {
                    position: absolute;
                    content: '';
                    background-image: url('../../../../assets/left/分类@2x.png');
                    background-size: 100% 100%;
                    left: 0rem;
                    top: 33rem;
                    width: 56rem;
                    height: 60rem;
                  }
                }
              }
              .detail-icon {
                margin-left: 104rem;
                margin-right: 102rem;
                width: 96rem;
                height: 80rem;
                background-image: url('../../../../assets/jiantou/jt.png');
                background-size: 100% 100%;
              }
            }
          }
        }
      }
    }
  }
  .con {
    .con-title {
      padding: 40rem 40rem 40rem 40rem;
      display: flex;
      align-items: center;
      flex-wrap: nowrap;
      width: 2040rem;
      height: 164rem;
      background: url('../../../../assets/SmallTitle/一级标题1.png') no-repeat;
      background-size: 100% 100%;
      .s-title {
        margin-left: 188rem;
        height: 80rem;
        font-size: 80rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
        line-height: 80rem;
        vertical-align: middle;
      }
      .s-tabs {
        height: 36rem;
        width: calc(100% - 100rem);
        display: flex;
        margin-left: 12rem;
        border-bottom: 1rem solid #d7dbe9;
        padding-left: 12rem;
        .s-tab {
          width: 97rem;
          height: 36rem;
          background: rgba(195, 215, 226, 0.2);
          border: 1rem solid #d7dbe9;
          border-bottom: none;
          border-radius: 10rem 10rem 0rem 0rem;

          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #9fabcc;
          line-height: 36rem;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
        }
        .s-tab.is-active {
          width: 97rem;
          height: 36rem;
          background: rgba(48, 143, 197, 0);
          border: 1rem solid #d7dbe9;

          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #1e8afc;
          line-height: 36rem;
        }
      }
    }
  }
}
</style>
