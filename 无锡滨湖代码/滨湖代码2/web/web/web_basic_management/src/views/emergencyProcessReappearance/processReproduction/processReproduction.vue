<template>
  <div class="processReproduction">
    <div class="left-container">
      <div class="menu-tools">
        <div
          class="menu-tab"
          @click="chooseEventType(item)"
          v-for="(item, index) in eventTypeArr"
          :key="index"
          :class="{ 'is-active': form.eventTypeId === item.id }"
        >
          {{ item.dictName }}
        </div>
      </div>
      <div class="menu-tools">
        <el-input class="pro-input-01" placeholder="事故名称" v-model="form.eventTitle"></el-input>

        <el-date-picker
          class="pro-datepicker-01"
          v-model="form.startTime"
          type="datetime"
          placeholder="时间"
          value-format="yyyy-MM-dd"
        ></el-date-picker>

        <div class="search-btn" style="cursor: pointer">
          <i class="el-icon-search" @click="onSubmit"></i>
        </div>
      </div>
      <div class="display-labels">
        <el-table :data="tableData" @row-click="leftRowClick" style="width: 100%">
          <el-table-column prop="eventTitle" label="事故名称"></el-table-column>
          <el-table-column prop="typeName" label="事故类型"></el-table-column>
          <el-table-column prop="eventTime" label="时间"></el-table-column>
        </el-table>
      </div>
      <div class="page-content">
        <el-pagination
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :current-page.sync="form.page"
          :page-size="form.pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <div class="right-container">
      <div class="content-01 con">
        <div class="con-title">
          <span class="s-back"></span>
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
            <span class="c-label">{{ eventInfo.minorNum }}人</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">重伤人数：</span>
            <span class="c-label">{{ eventInfo.seriousNum }}人</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">死亡人数：</span>
            <span class="c-label">{{ eventInfo.deathNum }}人</span>
          </div>
          <div class="dis-group col01">
            <span class="d-label">经济损失：</span>
            <span class="c-label">{{ eventInfo.economicLoss }}万</span>
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
      <div class="content-02 con">
        <div class="con-title">
          <span class="s-back"></span>
          <span class="s-title">应急处置回放</span>
        </div>
        <div class="con-detail">
          <el-table :data="taskTable" ref="multipleTable" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="dealTime" label="时间"></el-table-column>
            <el-table-column prop="taskTitle" label="任务标题"></el-table-column>
            <el-table-column prop="department" label="接收部门"></el-table-column>
          </el-table>
        </div>
      </div>
      <div class="content-03 con">
        <div class="con-title">
          <span class="s-back"></span>
          <span class="s-title">过程再现</span>
          <div class="s-tabs">
            <div class="s-tab" :class="{ 'is-active': tabIndex === 0 }" @click="clickTab(0)">时间展示</div>
            <div class="s-tab" :class="{ 'is-active': tabIndex === 1 }" @click="clickTab(1)">过程展示</div>
            <div class="s-tab" :class="{ 'is-active': tabIndex === 2 }" @click="clickTab(2)">视频再现</div>
          </div>
        </div>
        <div class="con-detail">
          <div class="tab-container tab-container-01" ref="scrolldiv" v-show="tabIndex === 0">
            <div class="workflow-div">
              <div class="workflow-div-group" v-for="(it, ind) in eventInfo.eventNodeList" :key="ind">
                <div class="flow-point" v-if="it.isHidden === '0'">{{ it.nodeName }}</div>
                <div class="flow-icon" v-if="it.isHidden === '0'">
                  <div class="flow-icon-start"></div>
                  <div class="flow-icon-center" :style="{ width: 108 * it.eventTaskList.length + 'px' }"></div>
                  <div class="flow-icon-end"></div>
                  <div
                    class="detail-point"
                    :class="{ top: (index + 1) % 2 > 0, bottom: (index + 1) % 2 == 0 }"
                    :style="{
                      left:
                        index % 2
                          ? -260 + 230 * Math.ceil((index + 1) / 2) + 'px'
                          : -280 + 230 * Math.ceil((index + 1) / 2) + 'px'
                    }"
                    v-for="(item, index) in it.eventTaskList"
                    :key="index"
                  >
                    <div class="detail-line" v-if="(index + 1) % 2 > 0">
                      <div class="d-circle"></div>
                      <div class="d-line"></div>
                    </div>
                    <div class="detail-line" v-if="(index + 1) % 2 == 0">
                      <div class="d-line"></div>
                      <div class="d-circle"></div>
                    </div>
                    <div class="detail-group">
                      <div class="d-label">任务信息</div>
                      <div class="d-value"></div>
                    </div>
                    <div class="detail-group">
                      <div class="d-label">任务标题：</div>
                      <div class="d-value">{{ item.taskTitle }}</div>
                    </div>
                    <div class="detail-group">
                      <div class="d-label">下发时间：</div>
                      <div class="d-value">{{ item.issueTime }}</div>
                    </div>
                    <div class="detail-group">
                      <div class="d-label">任务类型：</div>
                      <div class="d-value">{{ it.nodeName }}</div>
                    </div>
                    <!--                    <div class="detail-group">
                                            <div class="d-label">接收部门：</div>
                                            <div class="d-value">{{ item.department }}</div>
                    </div>-->
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
                      <div class="d-value">{{ item.feedback }}</div>
                    </div>
                    <div class="detail-group">
                      <div class="d-label">反馈附件：</div>
                      <div class="d-value"></div>
                      <!--{{item.file}}-->
                    </div>
                    <div class="detail-group">
                      <FileSegment :videos="item.fileVideo" :images="item.filePicture" :doucs="item.fileVoice" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div
              class="user-scroller"
              ref="userscroller"
              v-show="eventInfo && eventInfo.eventNodeList && eventInfo.eventNodeList.length > 0"
            >
              <div class="left-content">{{ eventInfo.spanStartTime }}</div>
              <el-slider
                class="center-bar"
                @change="changeSlider"
                v-model="scrollPercent"
                :format-tooltip="formatTooltip"
              ></el-slider>
              <div class="right-content">{{ eventInfo.spanEndTime }}</div>
            </div>
          </div>
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
                    <div class="d-value">{{ item.taskTitle }}</div>
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
                    <div class="d-value">{{ item.feedback }}</div>
                  </div>
                  <div class="detail-group">
                    <div class="d-label">反馈附件：</div>
                    <div class="d-value"></div>
                    <!--{{item.file}}-->
                  </div>
                  <div class="detail-group">
                    <FileSegment :videos="item.fileVideo" :images="item.filePicture" :doucs="item.fileVoice" />
                  </div>
                </div>
                <div class="detail-icon" v-if="index < eventNode.eventTaskList.length - 1"></div>
              </div>
            </div>
          </div>
          <div class="tab-container tab-container-03" v-show="tabIndex === 2">
            <znv-video v-if="src" :src="src" type="mp4" class="m-div-media"></znv-video>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import znvTime from '@/utils/znvTime'
import FileSegment from '@/components/Attachment/FileSegment'
export default {
  name: 'processReproduction',
  data() {
    return {
      src: '',
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
      },
      eventInfo: {},
      eventInfoCopy: {},
      eventInfoCopy1: {},
      total: 0,
      input: '',
      value1: '',
      tabIndex: 0, // 0 时间展示  1 过程展示 2 视频再现
      temps: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
      tableData: [],
      scrollPercent: 0,
      eventTypeArr: [],
      taskTable: [],
      eventNode: {},
      multipleSelection: [],
      statusMap: {
        0: '未处置',
        1: '已处置'
      }
    }
  },
  components: {
    FileSegment
  },
  mounted() {
    this.getDictTree(1).then((res) => {
      this.eventTypeArr = res.data.data
    })
    this.onSubmit()
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
      this.eventInfoCopy = JSON.parse(JSON.stringify(this.eventInfoCopy1))
      this.eventInfo = JSON.parse(JSON.stringify(this.eventInfoCopy1))
      let ids = this.multipleSelection.map((t) => t.id)
      console.log(ids)
      if (ids.length !== 0) {
        for (let index = 0; index < this.eventInfoCopy.eventNodeList.length; index++) {
          this.eventInfo.eventNodeList[index].eventTaskList = this.eventInfoCopy.eventNodeList[
            index
          ].eventTaskList.filter((s) => {
            let flag = false
            if (ids.includes(s.id)) {
              flag = true
            }
            return flag
          })
        }
      } else {
        this.eventInfo.eventNodeList = []
      }
      // let ids = this.multipleSelection.map(t => t.id)
      // console.log(ids, 'ooo');
      // if (ids.length !== 0) {
      //   this.eventInfo.eventNodeList = this.eventInfoCopy.eventNodeList.filter(t => {
      //     let cIds = t.eventTaskList.map(s => s.id)
      //     let flag = false
      //     cIds.forEach(c => {
      //       if (ids.includes(c)) {
      //         flag = true
      //       }
      //     })
      //     return flag
      //   })
      // } else {
      //   this.eventInfo.eventNodeList = this.eventInfoCopy.eventNodeList
      // }
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
    leftRowClick(row, column, event) {
      row.eventNodeList = row.eventNodeList.map((t) => {
        t.isActive = false
        return t
      })
      this.eventNode = {}
      this.eventInfo = row
      this.eventInfoCopy = JSON.parse(JSON.stringify(row))
      this.eventInfoCopy1 = JSON.parse(JSON.stringify(row))
      this.taskTable = []
      this.eventInfo.eventNodeList.map((t) => {
        if (t.isHidden === '0') {
          this.taskTable = this.taskTable.concat(t.eventTaskList)
        }
      })
      this.multipleSelection = this.taskTable
      this.showTag()
      this.src = this.eventInfo.file
    },
    showTag() {
      let handleSelectData = this.multipleSelection || []
      for (let i = 0; i < this.taskTable.length; i++) {
        for (let j = 0; j < handleSelectData.length; j++) {
          if (this.taskTable[i].id === handleSelectData[j].id) {
            this.$nextTick().then(() => {
              this.$refs.multipleTable.toggleRowSelection(this.taskTable[i], true)
            })
          }
        }
      }
    },
    chooseEventType(item) {
      if (this.form.eventTypeId === item.id) {
        this.form.eventTypeId = ''
      } else {
        this.form.eventTypeId = item.id
      }
      this.onSubmit()
    },
    queryEventTask() {
      this.form.endTime = this.form.startTime
      let params = this.form
      let url = `/binhuapis/dapeng/eventTask/query-event-task?eventTitle=${params.eventTitle}&eventTypeId=${params.eventTypeId}&sourceId=${params.sourceId}&streetName=${params.streetName}&communityName=${params.communityName}&page=${params.page}&pageSize=${params.pageSize}`
      if (params.startTime && params.endTime !== '') {
        url = url + '&startTime=' + params.startTime + '&endTime=' + params.endTime
      }
      return request.get(url)
    },
    getDictTree(id) {
      let url = `/binhuapis/dict/dictTree?parentId=${id}`
      return request.get(url)
    },
    onSubmit() {
      this.queryEventTask().then((res) => {
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    clickTab(type) {
      this.tabIndex = type
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.onSubmit()
    },
    handleSizeChange(val) {
      this.form.pageSize = val
      this.onSubmit()
    },
    changeSlider(e) {
      this.$refs.scrolldiv.scrollLeft = Math.ceil(
        ((this.$refs.scrolldiv.scrollWidth - this.$refs.scrolldiv.clientWidth) * e) / 100
      )
    },
    formatTooltip(value) {
      let dStart = new Date(this.eventInfo.spanStartTime)
      let dEnd = new Date(this.eventInfo.spanEndTime)
      let millisecondDStart = dStart.getTime()
      let millisecondDEnd = dEnd.getTime()
      let milliBetween = millisecondDEnd - millisecondDStart
      let middle = parseInt((milliBetween * value) / 100) + millisecondDStart
      return znvTime.getTimeByFormat(new Date(middle), 'YYYY-MM-DD hh:mm:ss')
    }
  }
}
</script>

<style lang="scss" scoped>
.ExtraFilePreviewViewer {
  .previewActive {
    color: #3388ff;
  }
}
.processReproduction {
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
  // min-height: 997px;
  // height: auto;
  background: #ffffff;
  border-radius: 10px;
  .left-container {
    width: 361px;
    min-height: 997px;
    height: 100%;
    background: rgba(255, 255, 255, 0.9);
    border: 1px solid #e9edf7;
    border-radius: 10px;

    .menu-tools {
      display: flex;
      flex-wrap: nowrap;
      height: 63px;
      width: 100%;
      justify-content: space-evenly;
      align-items: center;
      .menu-tab {
        width: 80px;
        height: 40px;
        background: rgba(255, 255, 255, 0.9);
        border-radius: 0px 12px 0px 12px;
        border: 1px solid #e9edf7;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #a1aac2;
        opacity: 0.8;

        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
      }
      .menu-tab.is-active {
        background: #1e8afc;
        border: 1px solid #02a8ff;
        border-radius: 0px 12px 0px 12px;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #ffffff;
      }
      .search-btn {
        width: 40px;
        height: 40px;
        background: rgba(255, 255, 255, 0.8);
        border: 1px solid #e9edf7;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        i {
          width: 16px;
          height: 16px;
          color: #a1aac2;
        }
      }
      .pro-input-01 {
        width: 159px;
      }
      ::v-deep .pro-input-01.el-input .el-input__inner {
        height: 40px;
        background: rgba(255, 255, 255, 0.8);
        border: 1px solid #e9edf7;
        border-radius: 8px;
      }
      .pro-datepicker-01 {
        width: 132px;
      }
      ::v-deep .pro-datepicker-01.el-date-editor.el-input .el-input__inner {
        height: 40px;
        background: rgba(255, 255, 255, 0.8);
        border: 1px solid #e9edf7;
        border-radius: 8px;
      }
    }
    .display-labels {
    }
    .page-content {
      height: 100px;
    }
  }
  .right-container {
    margin-left: 10px;
    width: calc(100% - 371px);
    min-height: 100%;
    height: auto;
    background: rgba(255, 255, 255, 0.9);
    border: 1px solid #e9edf7;
    border-radius: 10px;
    padding: 16px;
    .content-01 {
      min-height: 194px;
      width: 100%;
      border-bottom: 1px solid #e9edf7;
      .con-detail {
        display: flex;
        flex-wrap: wrap;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #a1aac2;
        line-height: 28px;
        padding: 14px 11px 14px 11px;
        .col01 {
          width: 225px;
        }
        .col02 {
          width: 450px;
        }
        .col05 {
          width: 100%;
        }
      }
    }
    .content-02 {
    }
    .content-03 {
      min-height: 534px;
      height: auto;
      .con-detail {
        min-height: 460px;
        overflow: hidden;
        position: relative;
        .tab-container {
          width: 100%;
          height: 100%;
        }
        .tab-container-01 {
          overflow-x: scroll;
          overflow-y: hidden;
          height: 100%;
          display: flex;
          align-items: baseline;
          flex-direction: column;
          .workflow-div {
            height: 100%;
            padding-left: 36px;
            padding-right: 36px;
            margin-top: 400px;
            margin-bottom: 450px;
            display: flex;
            justify-content: space-evenly;
            flex-wrap: nowrap;
            align-items: center;
            .workflow-div-group {
              height: 100%;
              display: flex;
              justify-content: space-evenly;
              flex-wrap: nowrap;
              align-items: center;
              .flow-point {
                cursor: pointer;

                width: 87px;
                height: 87px;
                background: rgba(149, 201, 255, 0.1);
                border: 1px solid #95c9ff;
                box-shadow: 0px 0px 6px 0px rgba(30, 138, 252, 0.3);
                border-radius: 0px 18px 0px 18px;
                display: flex;
                align-items: center;
                justify-content: center;

                font-size: 15px;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #1e8afc;
              }
              .flow-point.is-active {
                width: 87px;
                height: 87px;
                background: #1e8afc;
                border: 1px solid #95c9ff;
                box-shadow: 0px 0px 6px 0px rgba(30, 138, 252, 0.3);
                border-radius: 0px 18px 0px 18px;

                font-size: 15px;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #ffffff;
              }
              .flow-icon {
                margin-left: 15px;
                margin-right: 15px;
                width: auto;
                height: 19px;
                position: relative;
                display: flex;
                align-items: center;
                .flow-icon-start {
                  width: 3px;
                  height: 10px;
                  background-image: url('../../../assets/jiantou/jt_start.png');
                  background-size: 100% 100%;
                  margin-right: 2px;
                }
                .flow-icon-center {
                  width: 108px;
                  height: 10px;
                  background-image: url('../../../assets/jiantou/jt_center.png');
                  background-size: 100% 100%;
                }
                .flow-icon-end {
                  width: 8px;
                  height: 19px;
                  background-image: url('../../../assets/jiantou/jt_end.png');
                  background-size: 100% 100%;
                }
                .detail-point.bottom {
                  position: absolute;
                  top: 100px;
                  left: -50px;
                  width: 220px;
                  height: 335px;
                  background: rgba(255, 255, 255, 0);
                  border: 1px solid #aad3ff;
                  border-radius: 6px;
                  padding: 15px;
                  .detail-line {
                    position: absolute;
                    left: 110px;
                    top: -94px;
                    .d-circle {
                      position: absolute;
                      left: -2px;
                      width: 6px;
                      height: 6px;
                      background: #c9d5e0;
                      border-radius: 50%;
                    }
                    .d-line {
                      width: 1px;
                      height: 91px;
                      border: 1px solid;
                      border-image: linear-gradient(0deg, #c9d5e0, #c9d5e0) 10 10;
                    }
                  }
                  .detail-group {
                    width: 100%;
                    display: flex;
                    flex-wrap: nowrap;
                    .d-label {
                      width: 60px;
                      font-size: 12px;
                      font-family: Microsoft YaHei;
                      font-weight: 400;
                      color: #a1aac2;
                      line-height: 20px;
                    }
                    .d-value {
                      width: 120px;
                      font-size: 12px;
                      font-family: Microsoft YaHei;
                      font-weight: 400;
                      color: #7f8892;
                      line-height: 20px;
                    }
                    .media-div {
                      height: 120px;
                      .d-img {
                        margin-top: 8px;
                        background: #ffffff;
                        border: 1px solid #e9edf7;
                        border-radius: 8px;
                        width: 188px;
                        height: 118px;
                      }
                      .d-video {
                        margin-top: 8px;
                        background: #ffffff;
                        border: 1px solid #e9edf7;
                        border-radius: 8px;
                        width: 188px;
                        height: 118px;
                      }
                    }
                  }
                }
                .detail-point.top {
                  position: absolute;
                  bottom: 100px;
                  left: -70px;
                  width: 220px;
                  height: 335px;
                  background: rgba(255, 255, 255, 0);
                  border: 1px solid #aad3ff;
                  border-radius: 6px;
                  padding: 15px;
                  .detail-line {
                    position: absolute;
                    left: 80px;
                    bottom: -94px;
                    .d-circle {
                      position: absolute;
                      left: -2px;
                      width: 6px;
                      height: 6px;
                      background: #c9d5e0;
                      border-radius: 50%;
                    }
                    .d-line {
                      width: 1px;
                      height: 96px;
                      border: 1px solid;
                      border-image: linear-gradient(0deg, #c9d5e0, #c9d5e0) 10 10;
                    }
                  }
                  .detail-group {
                    width: 100%;
                    display: flex;
                    flex-wrap: nowrap;
                    .d-label {
                      width: 60px;
                      font-size: 12px;
                      font-family: Microsoft YaHei;
                      font-weight: 400;
                      color: #a1aac2;
                      line-height: 20px;
                    }
                    .d-value {
                      width: 120px;
                      font-size: 12px;
                      font-family: Microsoft YaHei;
                      font-weight: 400;
                      color: #7f8892;
                      line-height: 20px;
                    }
                    .media-div {
                      height: 120px;
                      .d-img {
                        margin-top: 8px;
                        background: #ffffff;
                        border: 1px solid #e9edf7;
                        border-radius: 8px;
                        width: 188px;
                        height: 118px;
                      }
                      .d-video {
                        margin-top: 8px;
                        background: #ffffff;
                        border: 1px solid #e9edf7;
                        border-radius: 8px;
                        width: 188px;
                        height: 118px;
                      }
                    }
                  }
                }
              }
            }
          }
          .user-scroller {
            position: absolute;
            bottom: 10px;
            width: 100%;
            height: 30px;
            display: flex;
            flex-wrap: nowrap;
            align-items: center;
            justify-content: space-between;
            .left-content {
              font-size: 14px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #9fabcc;
            }
            .center-bar {
              width: 70%;
              display: flex;
              flex-wrap: nowrap;
            }
            .right-content {
              font-size: 14px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #9fabcc;
            }
          }
        }
        .tab-container-01::-webkit-scrollbar {
          display: none;
        }
        .tab-container-02 {
          height: 542px;
          overflow: auto;
          display: flex;
          align-items: baseline;
          flex-direction: column;
          .workflow-div {
            height: 142px;
            padding-left: 36px;
            display: flex;
            justify-content: space-evenly;
            flex-wrap: nowrap;
            align-items: center;
            .workflow-group {
              height: 142px;
              width: auto;
              display: flex;
              flex-wrap: nowrap;
              align-items: center;
              .flow-point {
                cursor: pointer;

                width: 87px;
                height: 87px;
                background: rgba(149, 201, 255, 0.1);
                border: 1px solid #95c9ff;
                box-shadow: 0px 0px 6px 0px rgba(30, 138, 252, 0.3);
                border-radius: 0px 18px 0px 18px;
                display: flex;
                align-items: center;
                justify-content: center;

                font-size: 15px;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #1e8afc;
              }
              .flow-point.is-active {
                width: 87px;
                height: 87px;
                background: #1e8afc;
                border: 1px solid #95c9ff;
                box-shadow: 0px 0px 6px 0px rgba(30, 138, 252, 0.3);
                border-radius: 0px 18px 0px 18px;

                font-size: 15px;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #ffffff;
              }
              .flow-icon {
                margin-left: 15px;
                margin-right: 15px;
                width: 28px;
                height: 19px;
                background-image: url('../../../assets/jt.png');
                background-size: 100% 100%;
              }
            }
          }

          .flow-point-detail {
            height: 354px;
            padding-left: 36px;
            display: flex;
            flex-wrap: nowrap;
            align-items: center;
            .flow-point-detail-group {
              height: 354px;
              display: flex;
              justify-content: space-evenly;
              flex-wrap: nowrap;
              align-items: center;
              .detail-point {
                width: 220px;
                height: 354px;
                background: rgba(255, 255, 255, 0);
                border: 1px solid #aad3ff;
                border-radius: 6px;
                padding: 15px;
                .detail-group {
                  width: 100%;
                  display: flex;
                  flex-wrap: nowrap;
                  .d-label {
                    width: 60px;
                    font-size: 12px;
                    font-family: Microsoft YaHei;
                    font-weight: 400;
                    color: #a1aac2;
                    line-height: 20px;
                  }
                  .d-value {
                    width: 120px;
                    font-size: 12px;
                    font-family: Microsoft YaHei;
                    font-weight: 400;
                    color: #7f8892;
                    line-height: 20px;
                  }
                  .d-img {
                    margin-top: 8px;
                    background: #ffffff;
                    border: 1px solid #e9edf7;
                    border-radius: 8px;
                    width: 188px;
                    height: 118px;
                  }
                  .d-video {
                    margin-top: 8px;
                    background: #000000;
                    border: 1px solid #e9edf7;
                    border-radius: 8px;
                    width: 188px;
                    height: 118px;
                  }
                }
              }
              .detail-icon {
                margin-left: 15px;
                margin-right: 15px;
                width: 28px;
                height: 19px;
                background-image: url('../../../assets/jt.png');
                background-size: 100% 100%;
              }
            }
          }
        }
        .tab-container-03 {
          height: 542px;
          width: 100%;
          right: 20px;
          top: 20px;
          z-index: 99;
          background: #000000;
          .m-div-close {
            right: 20px;
            top: 0px;
            position: absolute;
            width: 11px;
            height: 11px;
            color: #ffffff;
          }
          .m-div-media {
            z-index: 299;
            margin-top: 20px;
            margin-bottom: 20px;
          }
        }
      }
    }
  }

  .con {
    .con-title {
      padding: 15px 16px 15px 16px;
      display: flex;
      align-items: center;
      flex-wrap: nowrap;
      .s-back {
        width: 5px;
        height: 20px;
        background: #1e8afc;
        display: inline-block;
      }
      .s-title {
        margin-left: 8px;
        height: 20px;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #7f8892;
        line-height: 20px;
        vertical-align: center;
      }
      .s-tabs {
        height: 36px;
        width: calc(100% - 100px);
        display: flex;
        margin-left: 12px;
        border-bottom: 1px solid #d7dbe9;
        padding-left: 12px;
        .s-tab {
          width: 97px;
          height: 36px;
          background: rgba(195, 215, 226, 0.2);
          border: 1px solid #d7dbe9;
          border-bottom: none;
          border-radius: 10px 10px 0px 0px;

          font-size: 16px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #9fabcc;
          line-height: 36px;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
        }
        .s-tab.is-active {
          width: 97px;
          height: 36px;
          background: rgba(48, 143, 197, 0);
          border: 1px solid #d7dbe9;

          font-size: 16px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #1e8afc;
          line-height: 36px;
        }
      }
    }
  }
}
</style>
