<template>
  <!-- <div class="QueryUserDialog1" v-show="dialogVisible">
    <div class="QueryUserDialog-head">指挥长设置</div>
    <i
      class="el-icon-close close-icon"
      style="cursor: pointer; position: absolute; font-size: 30rem; right: 20rem; top: 15rem"
      @click="closeDialog"
  ></i>-->
  <el-dialog
    title="指挥长设置"
    :visible.sync="dialogVisible"
    :modal="false"
    width="3912rem"
    height="3070rem"
    class="QueryUserDialog1 default-dialog-class large-screen"
    :append-to-body="true"
    :before-close="closeDialog"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div class="QueryUserDialog-body">
      <div class="QueryUserDialog-item">
        <div class="QueryUserDialog-item-bg"></div>
        <div class="item-content">
          <div class="item-content-title">
            <div class="item-content-title-bg">指挥长类型</div>
          </div>
          <div class="item-content-list">
            <div
              class="list-item"
              v-for="(item, i1) in typeNameList"
              :key="i1"
              :class="{ active: i1 === currentTypeIndex }"
              @click="typeItemAction(i1)"
            >
              <!-- <img class="t-icon" src="@/assets/leftOne/dept.png" alt="" /> -->
              <span class="name">{{ item }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-item">
        <div class="QueryUserDialog-item-bg"></div>
        <div class="item-content">
          <div class="item-content-title">
            <div class="item-content-title-bg">单位列表</div>
          </div>
          <div class="item-search-warp">
            <el-input placeholder="请输入单位名称" prefix-icon="el-icon-search" v-model="searchUnitText"></el-input>
          </div>
          <div class="item-content-list">
            <el-tree
              :expand-on-click-node="false"
              check-on-click-node
              :data="allUnitList"
              @check="handleCheckChange"
              :filter-node-method="filterNode"
              ref="tree"
            >
              <div
                class="list-item"
                style="padding-left: 0"
                slot-scope="{ data }"
                :class="{ active: data === currentUnit }"
                @click="getUnitMemberList(data)"
              >
                <img class="t-icon" src="@/assets/leftOne/dept.png" alt />
                <span class="name" :title="data.name">
                  {{ data.name.length > 17 ? data.name.slice(0, 17) + '...' : data.name }}
                </span>
              </div>
            </el-tree>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-item">
        <div class="QueryUserDialog-item-bg"></div>
        <div class="item-content">
          <div class="item-content-title">
            <div class="item-content-title-bg">人员列表</div>
          </div>
          <div class="item-search-warp">
            <el-input
              placeholder="请输入人员名称"
              prefix-icon="el-icon-search"
              v-model="searchUnitMemberText"
              @keyup.enter.native="searchTextEnter(1)"
            ></el-input>
          </div>
          <div class="item-content-list" v-if="searchUnitMemberText">
            <div class="list-item" v-for="(item, i3) in searchUnitMemberList" :key="i3" @click="selectMember(item)">
              <el-checkbox v-model="item.checked"></el-checkbox>
              <!-- <i class="iconfont iconzhihui_person icon"></i> -->
              <img class="t-icon" src="@/assets/leftOne/people.png" alt />
              <span :title="item.name" class="name" style="max-width: 52%">{{ item.name }}</span>
            </div>
          </div>
          <div class="item-content-list" v-else>
            <div class="list-item" v-for="(item, i3) in unitMemberList" :key="i3" @click="selectMember(item)">
              <el-checkbox v-model="item.checked"></el-checkbox>
              <!-- <i class="iconfont iconzhihui_person icon"></i> -->
              <img class="t-icon" src="@/assets/leftOne/people.png" alt />
              <span :title="item.name" class="name" style="max-width: 52%">{{ item.name }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-item" style="width: 5%">
        <div class="item-btn-warp">
          <div class="i-btn" @click="addUsers()">
            <i class="el-icon-caret-right"></i>
          </div>
          <div class="i-btn" @click="removeUsers()">
            <i class="el-icon-caret-left"></i>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-item">
        <div class="QueryUserDialog-item-bg"></div>
        <div class="item-content">
          <div class="item-content-title">
            <div class="item-content-title-bg">已选列表</div>
          </div>
          <div class="item-content-list">
            <div class="list-item" v-for="(item, i4) in userList" :key="i4" @click="selectUserList(item)">
              <el-checkbox v-model="item.checked"></el-checkbox>
              <!-- <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i> -->
              <!-- <img class="t-icon" src="@/assets/leftOne/dept.png" alt="" /> -->
              <span :title="item.name" class="name">{{ item.name }}</span>
              <div class="clearfix"></div>
              <div class="list-item" v-for="(item, i5) in item.list" :key="i5" @click.stop="selectUser(item)">
                <el-checkbox v-model="item.checked"></el-checkbox>
                <!-- <i class="iconfont iconzhihui_person icon"></i> -->
                <!-- <img class="t-icon" src="@/assets/leftOne/people.png" alt="" /> -->
                <span :title="item.name + '-' + item.unit" class="name" style="max-width: 60%"
                  >{{ item.name }} - {{ item.unit }}</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-foot btn">
        <el-button type="text" @click="closeDialog" style="margin-right: 200rem">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </div>
    <!-- </div> -->
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import { mapGetters } from 'vuex'
export default {
  name: 'QueryUserDialog',
  props: ['eventInfoList'],
  data() {
    return {
      dialogVisible: false,
      typeNameList: ['市指挥长', '区指挥长'], // 指挥长类型
      currentTypeIndex: 0, // 当前选中的指挥长类型
      allUnitList: [], // 单位列表
      searchUnitText: '',
      searchUnitList: [], // 搜索的单位列表
      currentUnit: {}, // 当前选中的单位
      unitMemberList: [], // 单位人员列表
      searchUnitMemberText: '',
      searchUnitMemberList: [], // 搜索的单位人员列表
      currentMemberList: [], // 当前选中的人员列表
      userList: [
        {
          name: '市指挥长',
          list: [],
          checked: false,
          nid: '999997'
        },
        {
          name: '区指挥长',
          list: [],
          checked: false,
          nid: '999998'
        }
      ], // 已选列表
      commanderIds_999998: '',
      commanderIds_999997: '',
      eventTypeId: ''
    }
  },
  computed: {
    // 使用对象展开运算符将 getter 混入 computed 对象中
    ...mapGetters(['eventInfo'])
  },
  watch: {
    '$store.getters.eventInfo': {
      handler(v) {
        console.log('v', v)
        this.planId = v ? v.planId : ''
      },
      deep: true
    },
    searchUnitText: {
      handler(v) {
        if (!this.$refs.tree) return
        this.$refs.tree.filter(v)
      },
      immediate: true
    },
    searchUnitMemberText: {
      handler(v) {
        if (!v) this.searchUnitMemberList = []
      },
      immediate: true
    }
  },
  mounted() {
    this.$root.Bus.$on('openQueryUserDialog', (data) => {
      this.dialogVisible = true
      data[0].userList.forEach((item) => {
        let member = {
          unit: item.unit,
          phone: item.phone,
          sex: item.sex,
          name: item.leader,
          id_card: item.id_card,
          duty: item.duty,
          telephone: item.telephone,
          id: item.personId,
          age: item.age,
          account: item.account,
          fox: item.fox,
          checked: false
        }
        // if (item.nid === '999997') {
        //   if (member.id) this.userList[0].list.push(member)
        // } else if (item.nid === '999998') {
        if (member.id) this.userList[1].list.push(member)
        // }
      })
      // if (!this.$route.query.precinctId) {
      //   this.typeNameList = ['市指挥长']
      //   this.userList = [
      //     {
      //       name: '市指挥长',
      //       list: this.unique(this.userList[0].list),
      //       checked: false,
      //       nid: '999997'
      //     }
      //   ]
      // } else {
      this.typeNameList = ['区指挥长']
      this.userList = this.userList.filter((item) => item.nid !== '999997')
      // }
    })

    this.$nextTick(() => {
      this.getAllUnitList()
    })
  },
  methods: {
    handleCheckChange(data, checked) {
      console.log(data, checked)
    },
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    unique(arr) {
      for (let i = 0; i < arr.length; i++) {
        for (let j = i + 1; j < arr.length; j++) {
          if (arr[i].id === arr[j].id) {
            arr.splice(j, 1)
            j--
          }
        }
      }
      return arr
    },
    typeItemAction(index) {
      this.currentTypeIndex = index
      this.currentMemberList.forEach((item) => {
        item.checked = false
      })
      this.currentMemberList = []
    },
    getAllUnitList() {
      // let url = '/apis/communication/unitInfo?type=1,3&eventId=' + this.$store.getters.eventId.id
      // let url = '/apis/communication/unitInfo?type=1,3'
      let url = '/apis/communication/unitInfoTree?type=1,3'
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.allUnitList = data || []
      })
    },
    searchTextEnter(type) {
      if (type === 0) {
        // 搜索单位列表
        if (!this.searchUnitText) return
        this.searchUnitList = []
        this.allUnitList.forEach((item) => {
          if (item.name.indexOf(this.searchUnitText) !== -1) {
            this.searchUnitList.push(item)
          }
        })
      } else if (type === 1) {
        // 搜索人员列表
        if (!this.searchUnitMemberText) return
        this.searchUnitMemberList = []
        this.unitMemberList.forEach((item) => {
          if (item.name.indexOf(this.searchUnitMemberText) !== -1) {
            this.searchUnitMemberList.push(item)
          }
        })
      }
    },
    getUnitMemberList(unit) {
      this.currentUnit = unit
      if (unit.memberList) {
        this.unitMemberList = unit.memberList
        return
      }
      let url = '/apis/Command/unitMember?unitId=' + unit.id
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        data.forEach((item) => {
          item.checked = false
        })
        unit.memberList = data
        this.unitMemberList = unit.memberList
      })
    },
    selectMember(member) {
      if (member.checked) {
        member.checked = false
        this.currentMemberList.splice(this.currentMemberList.indexOf(member), 1)
      } else {
        member.checked = true
        this.currentMemberList.push(member)
      }
    },
    selectUserList(type) {
      type.checked = !type.checked
      type.list.forEach((item) => {
        item.checked = type.checked
      })
    },
    selectUser(user) {
      user.checked = !user.checked
    },

    removeUsers() {
      this.userList.forEach((user) => {
        let delArr = user.list.map((item) => {
          if (item.checked) return item
          else return null
        })
        delArr.forEach((item) => {
          if (item) user.list.splice(user.list.indexOf(item), 1)
        })
      })
    },
    addUsers() {
      for (let i = 0; i < this.currentMemberList.length; i++) {
        let member = {
          unit: this.currentMemberList[i].unit,
          phone: this.currentMemberList[i].phone,
          sex: this.currentMemberList[i].sex,
          name: this.currentMemberList[i].name,
          id_card: this.currentMemberList[i].id_card,
          duty: this.currentMemberList[i].duty,
          telephone: this.currentMemberList[i].telephone,
          id: this.currentMemberList[i].id,
          age: this.currentMemberList[i].age,
          account: this.currentMemberList[i].account,
          fox: this.currentMemberList[i].fox,
          checked: false
        }
        this.currentMemberList[i].checked = false
        if (!this.isHasMember(this.userList[this.currentTypeIndex].list, member)) {
          this.userList[this.currentTypeIndex].list.push(member)
        }
      }
      this.currentMemberList = []
    },

    isHasMember(arr, member) {
      let result = false
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].id === member.id) {
          result = true
          break
        }
      }
      return result
    },

    closeDialog() {
      this.dialogVisible = false
      this.currentMemberList.forEach((item) => {
        item.checked = false
      })
      this.currentMemberList = []
    },
    submit() {
      if (!this.$store.getters.eventId.id) return
      this.commanderIds_999998 = this.userList[0].list.map((item) => {
        return item.id
      })
      // if (!commanderIds_999997.length && !commanderIds_999998.length) return
      // eslint-disable-next-line camelcase
      // let commanderIds_999997 = this.commanderIds_999997
      // eslint-disable-next-line camelcase
      let commanderIds_999998 = this.commanderIds_999998
      let url = '/apis/Command/updateLeaderByPrecinctId'
      let param = {
        precinctId: '',
        linkType: 2,
        eventType: '',
        list: []
      }
      // if (this.$route.query.precinctId) {
      param.eventType = this.eventInfoList
      param.precinctId = this.$route.query.precinctId
      param.list = [
        {
          commanderId: '999998',
          commander: commanderIds_999998.join(',')
        }
      ]
      // } else {
      //   param.eventType = this.eventInfoList
      //   param.precinctId = ''
      //   param.list = [
      //     {
      //       commanderId: '999997',
      //       commander: commanderIds_999997.join(',')
      //     }
      //   ]
      // }
      param.planId = this.eventInfo.obj.planId + ''
      console.log(param)
      Axios.post(url, param).then((res) => {
        let data = res.data
        if (data.code === 200) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          })
          this.closeDialog()
          this.$root.Bus.$emit('updateLeaderAfter')
        } else {
          this.$message({
            message: '提交失败！',
            type: 'warning'
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
.QueryUserDialog1 {
  // width: 2468rem;
  // height: 970rem;
  // width: 1540rem;
  // height: 870rem;
  // position: absolute;
  // left: -4%;
  // top: -60rem;
  z-index: 111;
  // overflow: hidden;
  // background: #05192d;
  // box-sizing: border-box;
  // border: 1rem solid #2b3f65;
  .el-dialog {
    width: 5441rem !important;
    height: 3070rem !important;
  }
  z-index: 3;
  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 2930rem !important;
    overflow: hidden;
  }
  .el-tree {
    background: transparent;
    overflow: auto;
  }
  .el-tree-node__content {
    height: 180rem;
  }
  .el-tree-node__content:hover,
  .el-tree-node:focus > .el-tree-node__content {
    background-color: transparent;
  }
  .el-tree__empty-block {
    width: 97%;
    .el-tree__empty-text {
      color: #fff;
    }
  }

  .QueryUserDialog-head {
    position: relative;
    height: 35rem;
    line-height: 35rem;
    // text-align: center;
    padding-left: 38rem;
    font-size: 20rem;
    font-family: Microsoft YaHei;
    // font-weight: bold;
    background: url('../../../../assets/global/组1.png') no-repeat;
    background-size: contain;
    color: rgba(255, 255, 255, 1);
    margin-top: 20rem;
    margin-left: 10rem;
  }

  .QueryUserDialog-body {
    position: relative;
    height: calc(100% - 35rem);
    overflow: hidden;
    padding: 20rem 25rem;

    .close-icon {
      position: absolute;
      right: 10rem;
      top: 10rem;
      font-size: 30rem;
      z-index: 2;
    }

    .QueryUserDialog-item {
      position: relative;
      float: left;
      width: 1040rem;
      height: 2206rem;
      overflow: hidden;
      // width: 233rem;
      margin-right: 156rem;
      .QueryUserDialog-item-bg {
        // top: 60rem;
        left: 0;
        bottom: 0;
        right: 0rem;
        position: absolute;
        background: #082239;
      }

      .item-btn-warp {
        // top: 50%;
        // left: 0;
        width: 100%;
        height: 2210rem;
        // margin-top: -30rem;
        // position: absolute;
        text-align: center;
        // margin-left: -24rem;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        .i-btn {
          display: inline-block;
          // line-height: 60rem;
          // height: 60rem;
          // width: 60rem;
          cursor: pointer;
          // font-size: 32rem;
          // margin: 0 30rem;
          background: linear-gradient(90deg, #00a3ff 0%, #8ad4ff 100%);
          border-radius: 20rem;
          height: 124rem;
          width: 124rem;
          line-height: 124rem;
          margin: 61rem 15rem;
          font-size: 60rem;
          color: #072649;
        }
      }

      .item-content {
        position: relative;
        height: 100%;
        overflow: hidden;

        .item-content-title {
          height: 164rem;
          width: 1040rem;
          margin-bottom: 10rem;

          padding-top: 10rem;
          height: 164rem !important;
          line-height: 164rem !important;
          font-size: 80rem;
          color: #ffffff;
          background: url('../../../../assets/SmallTitle/一级标题1.png') no-repeat;
          background-size: 100% 100%;
          // background-position: center center;
          .item-content-title-bg {
            margin-left: 100rem;
          }
        }

        .item-content-list {
          position: relative;
          height: calc(100% - 261rem);
          // margin-top: 40rem;
          overflow-x: hidden;
          overflow-y: auto;
          background: linear-gradient(0deg, #1a2f52 0%, rgba(26, 47, 82, 0) 100%);
          .list-item {
            // font-size: 22rem;
            font-size: 60rem;
            font-weight: 400;
            color: rgba(255, 255, 255, 1);
            cursor: pointer;
            padding-left: 20rem;
            width: 1038rem;
            height: 180rem;
            &:before {
              content: ' ';
              display: block;
            }
            &:after {
              content: ' ';
              display: block;
              clear: both;
            }

            &.active {
              // background-image: url('../../../../assets/global/choice_list.png');
              // background-size: 100% 100%;
              background: #092a4b;
              box-sizing: border-box;
              border: 1rem solid #02a7fc;
            }

            .el-checkbox {
              float: left;
              margin-right: 10rem;
              line-height: 180rem;

              // line-height: 77rem;
              // line-height: 10rem;

              .el-checkbox__inner {
                width: 44rem;
                height: 44rem;
                border: 1rem solid #02858c;
                // line-height: 20rem;
                // line-height: 180rem;

                background: transparent;

                &:before {
                  height: 2rem !important;
                  top: 9rem !important;
                }

                &:after {
                  border: 1rem solid #fff;
                  border-left: 0;
                  border-top: 0;
                  height: 12rem;
                  left: 7rem;
                  position: absolute;
                  top: 1rem;
                  width: 6rem;
                }
              }
            }
            .el-checkbox__inner {
              width: 46rem;
              height: 46rem;
            }
            .el-checkbox__input.is-checked .el-checkbox__inner::after {
              height: 28rem;
              left: 13rem;
              top: -6rem;
              width: 14rem;
            }

            .icon {
              font-size: 32rem;
              margin-left: 40rem;
              margin-right: 10rem;
              float: left;
              display: block;
              line-height: 77rem;
              background: linear-gradient(180deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
              background-clip: text;
              -webkit-text-fill-color: transparent;
            }
            .t-icon {
              float: left;
              margin-top: 52rem;
              width: 67rem;
              margin-right: 44rem;
            }

            .el-checkbox + .t-icon {
              margin-left: 0;
            }
            .el-checkbox + .icon {
              margin-left: 0;
            }

            .name {
              float: left;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              max-width: 80%;
              height: 180rem;
              line-height: 180rem;
            }
          }
        }

        .item-search-warp {
          position: relative;
          // margin: 20rem 88rem 10rem 40rem;
          // margin: 20rem 35rem 10rem 10rem;

          .el-input {
            color: #fff;
            font-size: 60rem;
            .el-input__inner {
              width: 100%;
              // font-size: 22rem;
              // background: rgba(61, 135, 243, 0.2);
              // border: 1rem solid rgba(61, 135, 243, 1);
              // border-radius: 22rem;
              padding-left: 120rem;
              height: 124rem;
              line-height: 124rem;
              color: #fff;
              // width: 178rem;
              // height: 36rem;
              background: rgba(4, 12, 28, 0.2);
              border: 1rem solid #0d365f;
              // border: 1rem solid #02858c;
              border-radius: 2rem;
              font-size: 60rem;
            }
            .el-input__prefix {
              // margin-left: 5rem;
              margin-top: -12rem;
              color: #28feff;
            }
          }
        }

        .item-search-warp + .item-content-list {
          margin-top: 0;
          height: calc(100% - 385rem);
        }
      }
    }

    .QueryUserDialog-foot {
      width: 100%;
      height: 220rem;
      // background-color: #0ff;
      display: inline-flex;
      justify-content: center;
      // border-top: 2rem solid #d6d6d6;
      margin-top: 200rem;

      .item {
        width: 100rem;
        height: 40rem;
        margin-top: 40rem;
        text-align: center;
        font-size: 16rem;
      }
      .item_btn1 {
        font-size: 16rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #9ab1cb;
        background: #082239;
        line-height: 17rem;
        border-radius: 2rem;
        border: 1rem solid #0d365f;
        cursor: pointer;
      }
      .item_btn2 {
        background: #0d365f;
        line-height: 17rem;
        color: #fff;
        border: 1rem solid #02a7fc;
        border-radius: 2rem;
        cursor: pointer;
      }
    }
  }
}
</style>
