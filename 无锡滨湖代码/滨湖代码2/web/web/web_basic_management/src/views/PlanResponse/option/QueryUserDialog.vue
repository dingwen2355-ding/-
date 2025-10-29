<template>
  <el-dialog
    title="指挥长设置"
    width="1550px"
    custom-class="QueryUserDialog"
    :visible.sync="dialogVisibleUser"
    :modal-append-to-body="true"
    :append-to-body="false"
    :before-close="closeDialog"
  >
    <div class="QueryUserDialog-warp">
      <div class="QueryUserDialog-body">
        <div class="QueryUserDialog-item" style="width: 300px">
          <div class="QueryUserDialog-item-bg"></div>
          <div class="item-content">
            <div class="item-content-title">
              <i class="iconfont iconzhgl_title"></i>
              指挥长类型
              <i class="iconfont iconzhgl_title"></i>
            </div>
            <div class="item-content-list">
              <div
                class="list-item"
                v-for="(item, i1) in typeNameList"
                :key="i1"
                :class="{ active: i1 === currentTypeIndex }"
                @click="typeItemAction(i1)"
              >
                <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                <span class="name">{{ item }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 400px">
          <div class="QueryUserDialog-item-bg"></div>
          <div class="item-content">
            <div class="item-content-title">
              <i class="iconfont iconzhgl_title"></i>
              单位列表
              <i class="iconfont iconzhgl_title"></i>
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
                >
                  <span @click="getUnitMemberList(data)">
                    <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                    <span class="name" :title="data.name" style="width: 180px">{{ data.name }}</span>
                  </span>
                </div>
              </el-tree>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 300px">
          <div class="QueryUserDialog-item-bg"></div>
          <div class="item-content">
            <div class="item-content-title">
              <i class="iconfont iconzhgl_title"></i>
              人员列表
              <i class="iconfont iconzhgl_title"></i>
            </div>
            <div class="item-search-warp">
              <el-input
                placeholder="请输入人员名称"
                prefix-icon="el-icon-search"
                v-model="searchUnitMemberText"
                @input="searchTextEnter(1)"
              ></el-input>
            </div>
            <div class="item-content-list" v-if="searchUnitMemberText">
              <div class="list-item" v-for="(item, i3) in searchUnitMemberList" :key="i3" @click="selectMember(item)">
                <el-checkbox v-model="item.checked"></el-checkbox>
                <i class="iconfont iconzhihui_person icon"></i>
                <span :title="item.name" class="name" style="max-width: 52%">{{ item.name }}</span>
              </div>
            </div>
            <div class="item-content-list" v-else>
              <div class="list-item" v-for="(item, i3) in unitMemberList" :key="i3" @click="selectMember(item)">
                <el-checkbox v-model="item.checked"></el-checkbox>
                <i class="iconfont iconzhihui_person icon"></i>
                <span :title="item.name" class="name" style="max-width: 52%">{{ item.name }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 100px">
          <div class="item-btn-warp">
            <div class="i-btn" @click="removeUsers()">
              <i class="el-icon-arrow-left"></i>
            </div>
            <div class="i-btn" @click="addUsers()">
              <i class="el-icon-arrow-right"></i>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 300px">
          <div class="QueryUserDialog-item-bg"></div>
          <div class="item-content">
            <div class="item-content-title">
              <i class="iconfont iconzhgl_title"></i>
              已选列表
              <i class="iconfont iconzhgl_title"></i>
            </div>
            <div class="item-content-list">
              <div class="list-item" v-for="(item, i4) in userList" :key="i4" @click="selectUserList(item)">
                <el-checkbox v-model="item.checked"></el-checkbox>
                <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                <span :title="item.name" class="name">{{ item.name }}</span>
                <div class="clearfix"></div>
                <div class="list-item" v-for="(item, i5) in item.list" :key="i5" @click.stop="selectUser(item)">
                  <el-checkbox v-model="item.checked"></el-checkbox>
                  <i class="iconfont iconzhihui_person icon"></i>
                  <span :title="item.name + '-' + item.unit" class="name">{{ item.name }} - {{ item.unit }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-foot">
        <el-button type="text" @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="submit">提 交</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  name: 'QueryUserDialog',
  components: {},
  props: ['dialogVisibleUser', 'dataInfo', 'formInfo'],
  data() {
    return {
      dialogVisible2: false,
      typeNameList: ['区指挥长'], // 指挥长类型
      // typeNameList: ['区指挥长'], // 指挥长类型
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
          name: '区指挥长',
          list: [],
          checked: false,
          nid: '999998'
        }
      ], // 已选列表
      commanderIds_999998: '',
      commanderIds_999997: ''
    }
  },
  watch: {
    dialogVisibleUser(v) {
      if (v) {
        // 先清空，防止重复添加
        this.userList[0].list = []
        this.dataInfo.forEach((itemL) => {
          itemL.userList.forEach((item) => {
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
            if (itemL.id === 999998) {
              if (member.id) this.userList[0].list.push(member)
              // if (member.id) this.userList[0].list.push(member)
            }
          })
          this.typeNameList = ['区指挥长']
          this.userList = this.userList.filter((item) => item.nid !== '999997')
        })
        this.getAllUnitList()
      }
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
  mounted() {},
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
      // let url =
      //   '/cdapis/communication/unitInfo?type=1,3' +
      //   '&eventId=' +
      //   this.$store.getters.eventId.id
      let url = '/cdapis/communication/unitInfoTree?type=1,2,3'
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
      let url = '/cdapis/Command/unitMember?unitId=' + unit.id
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
      this.$emit('update:dialogVisibleUser', false)
      this.currentMemberList.forEach((item) => {
        item.checked = false
      })
      this.currentMemberList = []
    },
    submit() {
      debugger
      this.commanderIds_999998 = this.userList[0].list.map((item) => {
        return item.id
      })
      let commanderIds_999998 = this.commanderIds_999998
      let url = '/cdapis/Command/updateLeaderByPrecinctId'
      let param = {
        precinctId: '',
        planId: this.formInfo.eventPlanId,
        linkType: 2,
        list: []
      }
      param.precinctId = this.$store.getters.precinctId
      param.list = [
        {
          commanderId: '999998',
          commander: commanderIds_999998.join(',')
        }
      ]
      console.log(param)
      Axios.post(url, param).then((res) => {
        let data = res.data
        if (data.code === 200) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          })
          this.closeDialog()
          this.$root.eventBus.$emit('updateEventPlanLeaderAfter')
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
.QueryUserDialog {
  transform: scale(0.8);
  .el-button {
    font-size: 24px;
    line-height: 50px;
    padding: 0 40px;
  }
  .el-tree {
    background: transparent;
    .el-tree-node__expand-icon {
      font-size: 30px;
    }
  }
  .el-tree-node__content {
    height: 30px;
  }
  .el-tree-node__content:hover,
  .el-tree-node:focus > .el-tree-node__content {
    background-color: transparent;
  }
  .QueryUserDialog-bg {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #000;
    opacity: 0.5;
  }
  .QueryUserDialog-warp {
    // width: 1480px;
    // position: absolute;
    // left: 82%;
    // top: 72%;
    height: 800px;
    z-index: 111;
    overflow: hidden;
    // margin-left: -1357px;
    // margin-top: -640px;
    padding: 0 40px;
    background: rgb(255, 255, 255);
    border-radius: 10px;

    .close-icon {
      position: absolute;
      right: 40px;
      top: 40px;
      font-size: 48px;
      z-index: 2;
    }

    .QueryUserDialog-head {
      position: relative;
      height: 200px;
      line-height: 200px;
      text-align: center;
      font-size: 48px;
      font-family: Microsoft YaHei;
      font-weight: bold;
      margin: 0 -40px 40px;

      i {
        background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        font-size: 52px;
      }
      border-bottom: 4px solid #dae1e5;
    }

    .QueryUserDialog-body {
      position: relative;
      height: calc(100% - 80px);
      overflow: hidden;

      .QueryUserDialog-item {
        position: relative;
        float: left;
        width: 19%;
        height: 100%;
        overflow: hidden;

        .QueryUserDialog-item-bg {
          top: 80px;
          left: 0;
          bottom: 0;
          right: 48px;
          position: absolute;
          background: rgba(255, 255, 255, 1);
          border: 1px solid rgba(218, 225, 229, 1);
          box-shadow: 0px 2px 19px 1px rgba(96, 96, 96, 0.1);
        }

        .item-btn-warp {
          top: 50%;
          left: 0;
          width: 100%;
          height: 60px;
          margin-top: -30px;
          position: absolute;
          text-align: center;
          margin-left: -24px;

          .i-btn {
            display: inline-block;
            line-height: 60px;
            height: 60px;
            width: 60px;
            cursor: pointer;
            font-size: 24px;
            margin: 0 30px;
            background-color: #0c99db;
            color: #fff;
            border-radius: 10px;
          }
        }

        .item-content {
          position: relative;
          height: 100%;
          overflow: hidden;

          .item-content-title {
            height: 80px;
            margin-bottom: 10px;
            line-height: 80px;
            margin-right: 48px;
            text-align: center;
            background: linear-gradient(0deg, rgba(12, 153, 219, 0.2) 0%, rgba(255, 255, 255, 0) 100%);

            i {
              background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
          }

          .item-content-list {
            position: relative;
            height: calc(100% - 80px);
            // padding-top: 40px;
            overflow-x: hidden;
            overflow-y: auto;
            width: 80%;

            .list-item {
              font-size: 15px;
              font-weight: 400;
              cursor: pointer;
              color: #5b5b5b;

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
                background-image: url('../../../assets/global/choice_list.png');
                background-size: 100% 100%;
              }

              .el-checkbox {
                float: left;
                margin-left: 40px;
                margin-right: 10px;
                margin-top: 8px;
              }

              .icon {
                font-size: 15px;
                margin-left: 40px;
                margin-right: 10px;
                float: left;
                display: block;
                line-height: 30px;
                color: #0c99db;
              }

              .el-checkbox + .icon {
                margin-left: 0;
              }

              .name {
                float: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                line-height: 30px;
                width: 140px;
                height: 30px;
                // max-width: 82%;
                padding-left: 10px;
                // max-width: calc(100% - 148px);
              }
            }
          }

          .item-search-warp {
            position: relative;
            margin: 20px 88px 10px 40px;

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

          .item-search-warp + .item-content-list {
            padding-top: 0;
            height: calc(100% - 190px);
          }
        }
      }
    }

    .QueryUserDialog-foot {
      height: 200px;
      text-align: center;
      margin-top: 20px;
    }
  }
}
</style>
