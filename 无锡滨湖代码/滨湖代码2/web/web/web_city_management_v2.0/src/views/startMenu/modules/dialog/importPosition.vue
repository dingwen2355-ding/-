<template>
  <el-dialog
    title="专项指挥部"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1600rem"
    height="800rem"
    class="import-dailog default-dialog-class large-screen"
    :append-to-body="true"
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
    <div class="box-1">
      <ul>
        <li
          v-for="(item, index) in list"
          :key="item.id"
          @click="listClick(item, index)"
          :class="{ active: index === activeIndex }"
        >
          {{ item.commandName }}
        </li>
      </ul>
      <div v-if="showVisible && showContent" class="zhihui">指挥体系</div>
      <div v-if="showVisible && showContent" class="zhihui" style="width: auto;padding: 0 20rem;bottom: 32rem">文件来源：{{ sourceData }}</div>
      <vue2-org-tree
        id="data-list-content"
        v-if="showVisible && showContent"
        :data="data"
        :label-class-name="labelClassName"
        @on-node-click="NodeClick"
      />
      <div class="orgElse" v-if="!showVisible && showContent">
        <div class="org1">
          <div class="back" @click="goback">返回</div>
          <p><span class="circle"></span>职能职责</p>
          <p style="color: #b7e6ff; margin-top: 10rem; height: 257rem; overflow-y: auto">{{ infomation.content }}</p>
        </div>
        <div class="org2 ">
          <p style="margin-bottom: 10rem"><span class="circle"></span>联系人<span style="color: #02a7fc;margin-left: 20rem;font-size: 60rem;">注：'--' 表示无数据</span></p>
          <el-table :data="infomation.list" class="large-screen">
            <el-table-column label="所属部门">
             <template slot-scope="scope">{{ scope.row.position || change }}</template>
            </el-table-column>
            <el-table-column label="姓名"><template slot-scope="scope">{{ scope.row.name || change }}</template></el-table-column>
            <el-table-column label="联系方式"><template slot-scope="scope">{{ scope.row.phone || change }}</template></el-table-column>
            <el-table-column label="职位"><template slot-scope="scope">{{ scope.row.duty || change }}</template></el-table-column>
          </el-table>
        </div>
      </div>
      <div class="orgElse" style="text-align: center; line-height: 545rem; font-size: 30rem" v-if="!showContent">
        暂无数据
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  components: {},
  props: ['dialogVisibleFlag'],
  watch: {
    dialogVisibleFlag(v) {
      if (v) {
        this.dialogVisible = v
        this.activeIndex = 0
        this.showVisible = true
        this.showContent = true
        this.showList()
      }
      // this.data = query
    }
  },
  data() {
    return {
      dialogVisible: false,
      showVisible: true,
      showContent: false,
      activeIndex: 0,
      infomation: {},
      list: [],
      labelClassName: 'bg-color-orange',
      data: {},
      sourceData: '',
      change: '--'
    }
  },
  mounted() {
    // this.$nextTick(() => {
    //   var div = document.getElementById('data-list-content').getElementsByClassName('org-tree')[0]
    //   div.scrollTop = div.scrollHeight
    // })
  },
  methods: {
    showList() {
      let url = '/apis/commandDepartment/query-command'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.list = res.data.data || []
          this.sourceData = this.list[0].dataSource
          this.selectCommand(this.list[0].commandName)
        }
      })
    },
    selectCommand(v) {
      let url = '/apis/commandDepartment/query-org?commandName=' + v
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.data = res.data.data || {}
        }
      })
    },
    NodeClick(e, data) {
      console.log(data)
      this.showVisible = false
      this.infomation = {}
      this.infomation = data
    },
    goback() {
      this.showVisible = true
    },
    listClick(v, index) {
      this.activeIndex = index
      this.showVisible = true
      this.showContent = true
      this.sourceData = v.dataSource
      this.selectCommand(v.commandName)
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss">
.import-dailog {
  .el-dialog{
    width: 3912rem !important;
    height: 2804rem !important;
  }
  .box-1 {
    display: flex;
    font-family: SourceHanSansCN;
    .zhihui {
      width: 420rem;
      height: 80rem;
      text-align: center;
      line-height: 80rem;
      font-weight: bold;
      color: #02a7fc;
      //background: url('../assets/global/指挥.png') no-repeat;
      background-size: 100% 100%;
      position: absolute;
      left: 22.6%;
      font-size: 64rem;
    }
    ul {
      width: 1200rem;
      height: 1800rem;
      overflow: auto;
      li {
        font-size: 60rem;
        background: #082239;
        border: 1rem solid #0d365f;
        color: #b7e6ff;
        height: 140rem;
        line-height: 140rem;
        margin-bottom: 8rem;
        cursor: pointer;
        padding-left: 35rem;
      }
      li.active {
        background: #092a4b;
        color: #ffffff;
        border: 1rem solid #02a7fc;
      }
    }
  }
  .orgElse {
    // background: #0d365f;
    margin-left: 10rem;
    width: 100%;
    height: 1680rem;
    background: rgba(72, 154, 228, 0.08);
    border-radius: 0rem 0rem 0rem 0rem;
    border: 1rem solid rgba(21, 105, 190, 0.6);
    padding: 35rem 25rem 0;
    p {
      font-size: 64rem;
      font-family: SourceHanSansCN;
      font-weight: bold;
      color: #ffffff;
      .circle {
        width: 6rem;
        height: 6rem;
        display: inline-block;
        background: #02a7fc;
        border-radius: 5rem;
        vertical-align: inherit;
        margin-right: 15rem;
      }
    }
    .org1 {
      height: 490rem;
      // overflow-y: auto;
      margin-bottom: 10rem;
      .back {
        font-weight: bold;
        color: #feb021;
        position: absolute;
        right: 35rem;
        top: 105rem;
        cursor: pointer;
      }
    }
    .org2 {
      .el-table {
        width: 2945rem;
      }
      .el-table__body-wrapper {
        height: 860rem !important;
        overflow-y: auto;
      }
    }
  }
  ::-webkit-scrollbar {
    // 滚动条整体
    display: block !important;
  }
  .org-tree-container {
    background-color: transparent;
    width: 100%;
    height: 2080rem;
    overflow: auto;
    background: rgba(72, 154, 228, 0.08);
    border-radius: 0rem 0rem 0rem 0rem;
    border: 1rem solid rgba(21, 105, 190, 0.6);
    margin-left: 10rem;
    .org-tree {
      width: 100%;
    }
    .org-tree-node-children {
      width: 100%;
    }
    .org-tree-node-children .org-tree-node-label .org-tree-node-label-inner{
      width: 140rem;
      padding: 10rem 20rem 10rem 10rem;
      font-size: 64rem;
    }
  }
  .org-tree-node-label {
    cursor: pointer;
  }
  .org-tree-node:after, .org-tree-node:before{
    height: 119rem;
  }
  .org-tree-node{
    padding-top: 120rem;
  }

  .bg-color-orange {
    font-size: 64rem;
    color: #b7e6ff;
    background: #092a4b;
    border: 1rem solid #02a7fc;
    // background: url('../assets/global/树框.png') no-repeat;
    // background-size: 100% 100%;
    // height: 50rem;
    // line-height: 38rem;
    // white-space: nowrap;
  }
}
</style>
