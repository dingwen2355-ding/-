<template>
  <div v-if="dialogVisible">
    <el-dialog
      title="事件详情"
      :visible.sync="dialogVisible"
      width="1300px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="EmergencyManagementView">
        <div class="container">
          <div class="f-title">
            <div class="t">基本信息</div>
          </div>
          <div class="list">
            <div class="item" v-for="(v, i) in list" :key="i" :class="'width' + v.width">
              <span class="name">{{ v.name }}: </span>
              <span class="value" v-if="v.key === 'attachmentFile'">
                <a :href="v.link">{{ v.value }}</a>
              </span>
              <span class="value" v-else>{{ v.value }}</span>
            </div>
          </div>
          <div class="f-title" v-if="eventInfo.instructions && eventInfo.instructions.length">
            <div class="t">领导指示信息</div>
          </div>
          <div class="list" v-for="(item, index) in eventInfo.instructions" :key="index + 'instructions'">
            <div class="item">
              <span class="name">指示时间: </span>
              <span class="value">{{ item.instructionsTime }}</span>
            </div>
            <div class="item width2">
              <span class="name">指示内容: </span>
              <span class="value">{{ item.content }}</span>
            </div>
            <div class="item width3">
              <span class="name">指示文件: </span>
              <span class="value">
                <a :href="item.attachment">{{ item.attachmentFile }}</a>
              </span>
            </div>
          </div>
          <div class="f-title" v-if="eventInfo.progress && eventInfo.progress.length">
            <div class="t">事件进展信息</div>
          </div>
          <div class="list" v-for="(item, index) in eventInfo.progress" :key="index + 'progress'">
            <div class="item">
              <span class="name">进展时间: </span>
              <span class="value">{{ item.progressTime }}</span>
            </div>
            <div class="item">
              <span class="name">进展类型: </span>
              <span class="value">{{ item.progressType }}</span>
            </div>
            <div class="item width3">
              <span class="name">进展描述: </span>
              <span class="value">{{ item.details }}</span>
            </div>
            <div class="item">
              <span class="name">上报人: </span>
              <span class="value">{{ item.reportPeople }}</span>
            </div>
            <div class="item">
              <span class="name">上报时间: </span>
              <span class="value">{{ item.reportTime }}</span>
            </div>
            <div class="item">
              <span class="name">联系方式: </span>
              <span class="value">{{ item.reportPeoplePhone }}</span>
            </div>
            <div class="item">
              <span class="name">办理单位: </span>
              <span class="value">{{ item.handleUnitName }}</span>
            </div>
            <div class="item width3">
              <span class="name">附件: </span>
              <span class="value">
                <a :href="item.attachment">{{ item.attachmentFile }}</a>
              </span>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'EmergencyManagementView',
  components: {},
  data() {
    return {
      list: [
        {
          name: '事件标题',
          key: 'title',
          value: ''
        },
        //  {
        //   name: '一级事件类型',
        //   key: 'type',
        //   value: ''
        // }, {
        //   name: '二级事件类型',
        //   key: 'type2',
        //   value: ''
        // },
        {
          name: '事件类别',
          key: 'typeSort',
          value: ''
        },
        {
          name: '事发时间',
          key: 'happenTime',
          value: ''
        },
        {
          name: '事发地点',
          key: 'address',
          value: '',
          width: 2
        },
        {
          name: '事件状态',
          key: 'status',
          value: ''
        },
        {
          name: '事件描述',
          key: 'details',
          value: '',
          width: 2
        },
        {
          name: '上报人',
          key: 'reportPeople',
          value: ''
        },
        {
          name: '上报时间',
          key: 'reportTime',
          value: ''
        },
        {
          name: '联系方式',
          key: 'reportPeoplePhone',
          value: ''
        },
        {
          name: '办理单位',
          key: 'handleUnitName',
          value: ''
        },
        {
          name: '附件',
          key: 'attachmentFile',
          value: '',
          link: ''
        }
      ],
      eventInfo: {
        instructions: [],
        progress: [],
        info: {}
      }
    }
  },
  props: ['dialogVisible', 'eventId'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.getEventInfo(this.eventId)
    },
    eventId(v) {
      if (v) {
        this.getEventInfo(v)
      }
    }
  },
  mounted() {},
  methods: {
    handleClose(done) {
      this.$emit('update:dialogVisible', false)
    },
    getEventInfo(eventId) {
      let url = '/gtw/EIMS-management/event/queryEventInfo?eventId=' + eventId
      axios.get(url).then((res) => {
        let data = res.data.data
        this.eventInfo = data || this.eventInfo

        this.eventInfo.info.typeSort = this.eventInfo.info.type + '/' + this.eventInfo.info.type2

        this.list.forEach((item) => {
          for (let key in this.eventInfo.info) {
            if (key === item.key) {
              item.value = this.eventInfo.info[key]
            }
            if (item.key === 'attachmentFile') {
              item.link = this.eventInfo.info.attachment
            }
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>

.el-dialog__body {
    height: 800px;
    overflow: auto;
}
.EmergencyManagementView {
  .container {
    .list {
      line-height: 3;
      display: flex;
      flex-wrap: wrap;
      margin: 0 55px 30px 55px;

      .item {
        width: 33.33333%;

        &.width2 {
          width: 66.66666%;
        }

        &.width3 {
          width: 100%;
        }

        .name {
          width: 100px;
          color: #8b93a0;
        }

        .value {
          color: #4e5a6e;
        }
      }
    }

    .f-title {
      position: relative;
      height: 30px;
      border-bottom: 2px solid #eff2f5;
      margin: 0 55px 30px 55px;

      .t {
        float: left;
        font-size: 18px;
        font-weight: bold;
        color: #4e5a6e;
        line-height: 29px;
        border-bottom: 2px solid #0093ff;
      }
    }
  }
}
</style>
