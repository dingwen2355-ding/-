<template>
  <div class="QueryUserDialog" v-show="dialogVisible">
    <div class="QueryUserDialog-bg" @click="closeDialog"></div>
    <div class="QueryUserDialog-warp">
      <i class="el-icon-close close-icon" @click="closeDialog"></i>
      <div class="QueryUserDialog-head">
        <i class="iconfont iconzhgl_title"></i>
        指挥长设置
        <i class="iconfont iconzhgl_title"></i>
      </div>
      <div class="QueryUserDialog-body">
        <div class="QueryUserDialog-item" style="width: 14%">
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
        <div class="QueryUserDialog-item" style="width: 31%">
          <div class="QueryUserDialog-item-bg"></div>
          <div class="item-content">
            <div class="item-content-title">
              <i class="iconfont iconzhgl_title"></i>
              单位列表
              <i class="iconfont iconzhgl_title"></i>
            </div>
            <div class="item-search-warp">
              <el-input
                placeholder="请输入单位名称"
                prefix-icon="el-icon-search"
                v-model="searchUnitText"
              ></el-input>
            </div>
            <!-- <div class="item-content-list" v-if="searchUnitText">
              <div
                class="list-item"
                v-for="(item, i2) in searchUnitList"
                :key="i2"
                :class="{ active: item === currentUnit }"
                @click="getUnitMemberList(item)"
              >
                <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                <span :title="item.name" class="name">{{ item.name }}</span>
              </div>
            </div> -->
            <div class="item-content-list">
              <el-tree
              :expand-on-click-node="false"
              check-on-click-node
              :data="allUnitList"
              @check="handleCheckChange"
              :filter-node-method="filterNode"
              ref="tree"
            >
              <div class="list-item" style="padding-left: 0" slot-scope="{ data }" :class="{ active: data === currentUnit }">
                <span @click="getUnitMemberList(data)">
                  <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                  <span class="name" :title="data.name" style="width: 680px;">{{ data.name }}</span>
                </span>
              </div>
            </el-tree>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 19%">
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
              <div
                class="list-item"
                v-for="(item, i3) in searchUnitMemberList"
                :key="i3"
                @click="selectMember(item)"
              >
                <el-checkbox v-model="item.checked"></el-checkbox>
                <i class="iconfont iconzhihui_person icon"></i>
                <span :title="item.name" class="name" style="max-width: 52%;">{{ item.name }}</span>
              </div>
            </div>
            <div class="item-content-list" v-else>
              <div
                class="list-item"
                v-for="(item, i3) in unitMemberList"
                :key="i3"
                @click="selectMember(item)"
              >
                <el-checkbox v-model="item.checked"></el-checkbox>
                <i class="iconfont iconzhihui_person icon"></i>
                <span :title="item.name" class="name" style="max-width: 52%;">{{ item.name }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 15%">
          <div class="item-btn-warp">
            <div class="i-btn" @click="removeUsers()">
              <i class="el-icon-arrow-left"></i>
            </div>
            <div class="i-btn" @click="addUsers()">
              <i class="el-icon-arrow-right"></i>
            </div>
          </div>
        </div>
        <div class="QueryUserDialog-item" style="width: 21%">
          <div class="QueryUserDialog-item-bg"></div>
          <div class="item-content">
            <div class="item-content-title">
              <i class="iconfont iconzhgl_title"></i>
              已选列表
              <i class="iconfont iconzhgl_title"></i>
            </div>
            <div class="item-content-list">
              <div
                class="list-item"
                v-for="(item, i4) in userList"
                :key="i4"
                @click="selectUserList(item)"
              >
                <el-checkbox v-model="item.checked"></el-checkbox>
                <i class="iconfont iconzhihui_tongxunlu_bumen icon"></i>
                <span :title="item.name" class="name">{{ item.name }}</span>
                <div class="clearfix"></div>
                <div
                  class="list-item"
                  v-for="(item, i5) in item.list"
                  :key="i5"
                  @click.stop="selectUser(item)"
                  style="margin: 10px 40px"
                >
                  <el-checkbox v-model="item.checked"></el-checkbox>
                  <i class="iconfont iconzhihui_person icon"></i>
                  <span :title="item.name + '-' + item.unit" class="name" style="width: 62%;"
                    >{{ item.name }} - {{ item.unit }}</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="QueryUserDialog-foot">
        <el-button type="text" @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from '@/utils/request'
export default {
  name: 'QueryUserDialog',
  components: {},
  data() {
    return {
      dialogVisible: false,
      typeNameList: ['市指挥长', '区指挥长'], // 指挥长类型
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
      commanderIds_999997: ''
    }
  },
  watch: {
    searchUnitText: {
      handler(v) {
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
    this.$root.eventBus.$on('openQueryUserDialog', (data) => {
      this.dialogVisible = true
      console.log(data)
      data.forEach((itemL) => {
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
          if (itemL.id === 999997) {
            if (member.id) this.userList[0].list.push(member)
          } else
          if (itemL.id === 999998) {
            if (member.id) this.userList[1].list.push(member)
            // if (member.id) this.userList[0].list.push(member)
          }
        })
        if (!this.$store.getters.precinctId) {
          this.typeNameList = ['市指挥长']
          this.userList = [
            {
              name: '市指挥长',
              list: this.unique(this.userList[0].list),
              checked: false,
              nid: '999997'
            }]
        } else {
          this.typeNameList = ['区指挥长']
          this.userList = this.userList.filter(item => item.nid !== '999997')
        }
      })
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
      // let url =
      //   '/apis/communication/unitInfo?type=1,3' +
      //   '&eventId=' +
      //   this.$store.getters.eventId.id
      let url = '/apis/communication/unitInfoTree?type=1,2,3'
      if (this.$store.getters.eventId.id) {
        url += '&eventId=' + this.$store.getters.eventId.id
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
        this.currentMemberList.splice(
          this.currentMemberList.indexOf(member),
          1
        )
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
        if (
          !this.isHasMember(this.userList[this.currentTypeIndex].list, member)
        ) { this.userList[this.currentTypeIndex].list.push(member) }
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
      // this.userList = [
      //   {
      //     name: '市指挥长',
      //     list: [],
      //     checked: false
      //   },
      //   {
      //     name: '区指挥长',
      //     list: [],
      //     checked: false
      //   }
      // ]
    },
    submit() {
      // if (!this.$store.getters.precinctId) {
      if (!sysConfig.defaultPrecinct.eventType) return
      // }
      if (this.$store.getters.precinctId) {
        this.commanderIds_999998 = this.userList[0].list.map((item) => {
          return item.id
        })
      } else {
        // eslint-disable-next-line camelcase
        this.commanderIds_999997 = this.userList[0].list.map((item) => {
          return item.id
        })
        // eslint-disable-next-line camelcase
        // this.commanderIds_999998 = this.userList[1].list.map((item) => {
        //   return item.id
        // })
      }
      // if (!commanderIds_999997.length && !commanderIds_999998.length) return
      // eslint-disable-next-line camelcase
      let commanderIds_999997 = this.commanderIds_999997
      // eslint-disable-next-line camelcase
      let commanderIds_999998 = this.commanderIds_999998
      let url = '/apis/Command/updateLeaderByPrecinctId'
      let param = {
        precinctId: '',
        eventType: sysConfig.defaultPrecinct.eventType,
        list: []
      }
      if (this.$store.getters.precinctId) {
        param.precinctId = this.$store.getters.precinctId
        param.list = [
          {
            commanderId: '999998',
            commander: commanderIds_999998.join(',')
          }
        ]
      } else {
        param.precinctId = ''
        param.list = [
          {
            commanderId: '999997',
            commander: commanderIds_999997.join(',')
          }
        ]
      }
      console.log(param)
      Axios.post(url, param).then((res) => {
        let data = res.data
        if (data.code === 200) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          })
          this.closeDialog()
          this.$root.eventBus.$emit('updateLeaderAfter')
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
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  .el-button {
  font-size: 40px;
  line-height: 80px;
  padding: 0 40px;
}
.el-tree {
    background: transparent;
    overflow: auto;
    .el-tree-node__expand-icon {
      font-size: 30px;
    }
  }
  .el-tree-node__content {
        height: 98px;
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
    width: 2900px;
    position: absolute;
    left: 48%;
    top: 45%;
    height: 1675px;
    z-index: 111;
    overflow: hidden;
    margin-left: -1357px;
    margin-top: -640px;
    padding: 0 40px;

    background: rgba(255, 255, 255, 1);
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
        background: linear-gradient(
          -90deg,
          rgba(0, 215, 254, 1) 0%,
          rgba(0, 150, 255, 1) 100%
        );
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        font-size: 52px;
      }
      border-bottom: 4px solid #dae1e5;
    }

    .QueryUserDialog-body {
      position: relative;
      height: calc(100% - 400px);
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
            line-height: 120px;
            height: 120px;
            width: 120px;
            cursor: pointer;
            font-size: 32px;
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
            background: linear-gradient(
              0deg,
              rgba(12, 153, 219, 0.2) 0%,
              rgba(255, 255, 255, 0) 100%
            );

            i {
              background: linear-gradient(
                -90deg,
                rgba(0, 215, 254, 1) 0%,
                rgba(0, 150, 255, 1) 100%
              );
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
          }

          .item-content-list {
            position: relative;
            height: calc(100% - 80px);
            padding-top: 40px;
            overflow-x: hidden;
            overflow-y: auto;

            .list-item {
              font-size: 40px;
              font-weight: 400;
              cursor: pointer;
              color: #5b5b5b;

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

              .el-checkbox {
                float: left;
                margin-left: 40px;
                margin-right: 10px;
                margin-top: 45px;
              }

              .icon {
                font-size: 42px;
                margin-left: 40px;
                margin-right: 10px;
                float: left;
                display: block;
                line-height: 98px;
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
                line-height: 98px;
                height: 98px;
                // max-width: 82%;
                max-width: calc(100% - 148px);
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
      margin-top: 40px;
    }
  }
}
</style>
