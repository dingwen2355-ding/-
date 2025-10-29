<template>
  <div class="MemberSort">
    <div class="tab-container">
      <div class="tab-item" :class="{'active': currentTab === '1'}" @click="tabAction('1')">区属单位</div>
      <div class="tab-item" :class="{'active': currentTab === '3'}" @click="tabAction('3')">属地单位</div>
      <div class="tab-item" :class="{'active': currentTab === '2'}" @click="tabAction('2')">保供企业</div>
    </div>
    <div class="tab-content">
      <div class="tab-list">
        <div class="tab-list-bg"></div>
        <div class="list-content">
          <div class="list-content-title">
            <i class="iconfont iconzhgl_title"></i>
             单位列表
            <i class="iconfont iconzhgl_title"></i>
          </div>
          <div class="content-search-warp">
            <el-input
              placeholder="请输入单位名称"
              prefix-icon="el-icon-search"
              v-model="searchUnitText"
              @input="searchTextEnter(0)">
            </el-input>
          </div>
          <div class="list" v-if="searchUnitText">
            <draggable class="wrapper" v-model="searchUnitList">
              <transition-group>
                <div class="list-item" v-for="(item, i2) in searchUnitList" :key="item.id" :class="{'active': item === currentUnit}" @click="getUnitMemberList(item)">
                  <span class="index">{{i2 + 1}}</span>
                  <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                  <span :title="item.name" class="name">{{item.name}}</span>
                </div>
              </transition-group>
            </draggable>
          </div>
          <div class="list" v-else>
            <draggable class="wrapper" v-model="allUnitList">
              <transition-group>
                <div class="list-item" v-for="(item, i2) in allUnitList" :key="item.id" :class="{'active': item === currentUnit}" @click="getUnitMemberList(item)">
                  <span class="index">{{i2 + 1}}</span>
                  <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                  <span :title="item.name" class="name">{{item.name}}</span>
                </div>
              </transition-group>
            </draggable>
          </div>
        </div>
      </div>
      <div class="tab-list">
        <div class="tab-list-bg"></div>
        <div class="list-content">
          <div class="list-content-title">
            <i class="iconfont iconzhgl_title"></i>
             人员列表
            <i class="iconfont iconzhgl_title"></i>
          </div>
          <div class="content-search-warp">
            <el-input
              placeholder="请输入人员姓名"
              prefix-icon="el-icon-search"
              v-model="searchUnitMemberText"
              @input="searchTextEnter(1)">
            </el-input>
          </div>
          <div class="list" v-if="searchUnitMemberText">
            <draggable class="wrapper" v-model="searchUnitMemberList">
              <transition-group>
                <div class="list-item" v-for="(item, i3) in searchUnitMemberList" :key="item.id">
                  <span class="index">{{i3 + 1}}</span>
                  <i class="iconfont iconzhihui_person icon"></i>
                  <span :title="item.name" class="name">{{item.name}}</span>
                  <span class="duty">{{item.duty}}</span>
                  <span class="phone">{{item.phone}}</span>
                </div>
              </transition-group>
            </draggable>
          </div>
          <div class="list" v-else>
            <draggable class="wrapper" v-model="unitMemberList" :scroll-sensitivity='300' :force-fallback='true' :options="{
                animation: 100,
                ghostClass: 'ghost',
                handle: '.list-item',
                scroll: true
            }">
              <transition-group>
                <div class="list-item" v-for="(item, i3) in unitMemberList" :key="item.id">
                  <span class="index">{{i3 + 1}}</span>
                  <i class="iconfont iconzhihui_person icon"></i>
                  <span :title="item.name" class="name">{{item.name}}</span>
                  <span class="duty">{{item.duty}}</span>
                  <span class="phone">{{item.phone}}</span>
                </div>
              </transition-group>
            </draggable>
          </div>
        </div>
      </div>
    </div>
    <div class="submit-btn-warp">
      <div class="link-btn btn-item">取消</div>
      <div class="blue-btn btn-item" @click="submit">提交</div>
    </div>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import Axios from '@/utils/request'

export default {
  name: 'MemberSort',
  components: { draggable },
  data() {
    return {
      currentTab: '1',
      allUnitList: [], // 单位列表
      searchUnitText: '',
      searchUnitList: [], // 搜索的单位列表
      currentUnit: {}, // 当前选中的单位
      unitMemberList: [], // 单位人员列表
      searchUnitMemberText: '',
      searchUnitMemberList: [] // 搜索的单位人员列表
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.getAllUnitList(this.currentTab)
    })
  },
  watch: {
    searchUnitText: {
      handler (v) {
        if (!v) this.searchUnitList = []
      },
      immediate: true
    },
    searchUnitMemberText: {
      handler (v) {
        if (!v) this.searchUnitMemberList = []
      },
      immediate: true
    },
    unitMemberList: {
      handler (v) {
        console.log(v)
      },
      immediate: true
    }
  },
  methods: {
    tabAction (type) {
      this.currentTab = type
      this.getAllUnitList(type)
    },
    getAllUnitList (type) {
      let url = '/apis/communication/unitInfo?type=' + type + '&eventId=' + this.$store.getters.eventId.id
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.allUnitList = data || []
      })
    },
    searchTextEnter (type) {
      if (type === 0) { // 搜索单位列表
        if (!this.searchUnitText) return
        this.searchUnitList = []
        this.allUnitList.forEach(item => {
          if (item.name.indexOf(this.searchUnitText) !== -1) {
            this.searchUnitList.push(item)
          }
        })
      } else if (type === 1) { // 搜索人员列表
        if (!this.searchUnitMemberText) return
        this.searchUnitMemberList = []
        this.unitMemberList.forEach(item => {
          if (item.name.indexOf(this.searchUnitMemberText) !== -1) {
            this.searchUnitMemberList.push(item)
          }
        })
      }
    },
    getUnitMemberList (unit) {
      this.currentUnit = unit
      let url = '/apis/Command/unitMember?unitId=' + unit.id
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.unitMemberList = data || []
      })
    },
    submit () {
      let params = this.unitMemberList.map((item, index) => {
        return {
          id: item.id,
          sort: index + 1
        }
      })
      if (!params.length) return
      console.log(JSON.stringify(params))
      let url = '/apis/communication/communicationOrder'
      Axios.post(url, params).then(res => {
        let data = res.data
        console.log(data)
        if (data.code === 200) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          })
          this.$root.eventBus.$emit('refreshIframe')
        } else {
          this.$message({
            message: '提交失败！',
            type: 'warning'
          })
        }
      }).catch(error => {
        this.$message({
          message: error,
          type: 'warning'
        })
      })
    }
  }
}
</script>

<style lang="scss">
  .MemberSort {
    position: relative;
    width: 100%;

    .tab-container {
      position: relative;
      margin-top: 76px;
      line-height: 80px;
      margin-bottom: 40px;
      margin-left: 62px;

      .tab-item {
        display: inline-block;
        width: 234px;
        height: 80px;
        border-radius: 4px;
        text-align: center;
        cursor: pointer;

        &.active {
          color: #fff;
          background: rgba(12, 153, 219, 1);
        }
      }
    }

    .tab-content {
      margin-left: 62px;
      margin-right: 62px;
      height: 1560px;

      .tab-list {
        position: relative;
        width: 50%;
        height: 1480px;
        float: left;
        margin-bottom: 80px;
        padding-bottom: 40px;

        .tab-list-bg {
          top: 80px;
          left: 0;
          bottom: 0;
          right: 188px;
          position: absolute;
          background:rgba(255,255,255,1);
          border:1px solid rgba(218,225,229,1);
          box-shadow:0px 2px 19px 1px rgba(96,96,96,0.1);
        }

        .list-content {
          position: relative;
          height: 100%;
          overflow: hidden;

          .list-content-title {
            height: 80px;
            margin-bottom: 10px;
            line-height: 80px;
            margin-right: 188px;
            text-align: center;
            background:linear-gradient(0deg,rgba(12,153,219,0.2) 0%,rgba(255,255,255,0) 100%);

            i {
              background:linear-gradient(-90deg,rgba(0,215,254,1) 0%,rgba(0,150,255,1) 100%);
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
          }

          .list {
            position: relative;
            height: calc(100% - 80px);
            padding-top: 40px;
            overflow-x: hidden;
            overflow-y: auto;

            .list-item {
              font-size:40px;
              font-weight:400;
              cursor: pointer;
              color: #5B5B5B;

              &:before {
                content: " ";
                display: block;
              }
              &:after {
                content: " ";
                display: block;
                clear: both;
              }

              &.active {
                background-image: url("../../assets/global/choice_list.png");
                background-size: 100% 100%;
              }

              .index {
                margin-left: 40px;
                margin-right: 10px;
                float: left;
                display: block;
                line-height:98px;
                color: #0C99DB;
                width: 80px;
                text-align: center;
              }

              .icon {
                font-size: 42px;
                margin-right: 10px;
                float: left;
                display: block;
                line-height:98px;
                color: #0C99DB;
              }

              .name {
                float: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                line-height:98px;
                height: 98px;
              }

              .duty {
                margin-left: 40px;
                margin-right: 10px;
                float: left;
                display: block;
                line-height:98px;
              }

              .phone {
                margin-left: 40px;
                margin-right: 10px;
                float: left;
                display: block;
                line-height:98px;
              }
            }
          }

          .content-search-warp {
            position: relative;
            margin: 20px 228px 10px 40px;

            .el-input {
              .el-input__inner {
                width: 100%;
                border-radius: 40px;
                padding-left: 55px;
              }
              .el-input__prefix {
                margin-left: 5px;
                margin-top: 3px;
              }
            }
          }

          .content-search-warp + .list {
            padding-top: 0;
            height: calc(100% - 190px);
          }
        }
      }
    }

    .submit-btn-warp {
      position: relative;
      text-align: center;
      margin-bottom: 80px;
      margin-right: 140px;

      .btn-item {
        margin: 0 40px;
      }
    }
  }
</style>
