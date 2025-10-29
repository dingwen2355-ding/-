<template>
  <el-dialog
    title="新增"
    width="1550px"
    custom-class="addCommandOrg"
    :visible.sync="dialogVisibleUser"
    :modal-append-to-body="true"
    :append-to-body="false"
    :before-close="closeDialog"
  >
    <div class="addCommandOrg-warp">
      <div class="addCommandOrg-body">
        <div class="addDialog">
          <div class="add-dialog-content">
            <div class="add-dialog-title">
              <div class="title-content">
                <i class="iconfont iconzhgl_title"></i>
                <span>新增</span>
                <i class="iconfont iconzhgl_title"></i>
              </div>
              <div class="close-icon" @click="closeAddDialog">
                <i class="el-icon-close"></i>
              </div>
            </div>
            <div class="transfer-content">
              <el-transfer
                v-model="transferValue"
                :data="transferArr"
                :titles="transferTitles"
                :props="{ key: 'id', label: 'name' }"
                :filterable="true"
                v-show="dialogType === 'enterprise'"
              ></el-transfer>
              <div class="QueryUserDialog-body" v-show="dialogType === 'cityUnit'">
                <div class="QueryUserDialog-item" style="width: 52%">
                  <div class="QueryUserDialog-item-bg"></div>
                  <div class="item-content">
                    <div class="item-content-title">
                      {{ transferTitles[0] }}
                    </div>
                    <div class="item-search-warp">
                      <el-input
                        placeholder="请输入单位名称"
                        prefix-icon="el-icon-search"
                        v-model="searchUnitText"
                      ></el-input>
                    </div>
                    <div class="item-content-list">
                      <el-tree
                        :expand-on-click-node="false"
                        :check-strictly="true"
                        show-checkbox
                        :data="transferArr"
                        node-key="id"
                        @check="handleCheckChange"
                        :filter-node-method="filterNode"
                        ref="tree"
                      >
                        <div class="list-item" style="padding-left: 0" slot-scope="{ data }">
                          <span class="name" :title="data.name">{{ data.name }}</span>
                        </div>
                      </el-tree>
                    </div>
                  </div>
                </div>
                <div class="QueryUserDialog-item" style="width: 12%">
                  <div class="item-btn-warp">
                    <div class="i-btn" @click="removeUsers()">
                      <i class="el-icon-arrow-left"></i>
                    </div>
                    <div class="i-btn" @click="addUsers()">
                      <i class="el-icon-arrow-right"></i>
                    </div>
                  </div>
                </div>
                <div class="QueryUserDialog-item" style="width: 36%">
                  <div class="QueryUserDialog-item-bg"></div>
                  <div class="item-content">
                    <div class="item-content-title">
                      {{ transferTitles[1] }}
                    </div>
                    <div class="item-content-list">
                      <div class="list-item" v-for="(item, i4) in eventSelectList" :key="i4">
                        <el-checkbox v-model="item.checked"></el-checkbox>
                        <span :title="item.name" class="name" style="max-width: 75%" @click="selectUserList(item)">{{
                          item.name
                        }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="colony-warp" style="width: 2100px" v-show="dialogType === 'colonyUnit'">
                <div class="vx-content">
                  <div class="vx-content-item" style="width: 20%">
                    <div class="vx-title"><i class="iconfont iconzhgl_title"></i><span>滨湖区</span></div>
                    <div class="vx-list">
                      <ul
                        class="vx-list-ul"
                        v-for="(item, index) in allColonyUnitList"
                        :key="index"
                        :class="{ active: item.selected }"
                      >
                        <li @click="selectAreaInfo(item)"><i class="iconfont iconzhihui_person"></i>{{ item.name }}</li>
                      </ul>
                    </div>
                  </div>
                  <div class="vx-content-item">
                    <div class="vx-title"><i class="iconfont iconzhgl_title"></i><span>镇/街道</span></div>
                    <div class="search-warp">
                      <el-input
                        placeholder="请输入镇/街道名称"
                        prefix-icon="el-icon-search"
                        @keyup.enter.native="searchFunction()"
                        v-model="searchInput"
                      >
                      </el-input>
                    </div>
                    <div class="vx-list" v-if="searchInput">
                      <ul
                        class="vx-list-ul"
                        v-for="(item, index) in colonyUnitSearchList"
                        :key="index"
                        :class="{ active: item.selected }"
                      >
                        <li @click="selectAreatopId(item)">
                          <i class="iconfont iconzhihui_person"></i>{{ item.name }}
                        </li>
                      </ul>
                    </div>
                    <div class="vx-list" v-else>
                      <ul
                        class="vx-list-ul"
                        v-for="(item, index) in colonyUnitList"
                        :key="index"
                        :class="{ active: item.selected }"
                      >
                        <li @click="selectAreatopId(item)">
                          <i class="iconfont iconzhihui_person"></i>{{ item.name }}
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="vx-content-item vx-content-margin">
                    <div class="vx-title"><i class="iconfont iconzhgl_title"></i><span>已选中单位</span></div>
                    <div class="vx-list">
                      <ul class="vx-list-ul" style="display: flex" v-for="(item, index) in selectUnitList" :key="index">
                        <li style="width: 650px"><i class="iconfont iconzhihui_person"></i>{{ item.name }}</li>
                        <li style="width: 170px">
                          <i class="el-icon-delete-solid" @click="deleteItem(selectUnitList, item, 1)"></i>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
              <div class="btn">
                <div class="item item_btn1" @click="closeAddDialog">取 消</div>
                <div class="item item_btn2" @click="submitDialog">提 交</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="addCommandOrg-foot">
        <el-button type="text" @click="closeAddDialog">取 消</el-button>
        <el-button type="primary" @click="submitDialog">提 交</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  name: 'addCommandOrg',
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
    selectAreatopId(item) {
      // if (this.selectUnitList.length > 1) {
      //   this.selectUnitList.splice(1, 1, item)
      // } else {
      //   this.selectUnitList.push(item)
      // }
      this.selectUnitList.push(item)
      this.selectUnitList = this.unique(this.selectUnitList)
      if (!this.isSelected(item)) {
        for (let i = 0; i < this.colonyUnitList.length; i++) {
          let uItem = this.colonyUnitList[i]
          uItem.selected = false
        }
        item.selected = true
      }
      this.selectJudgeList = JSON.parse(JSON.stringify(this.selectUnitList))
    },
    selectAreaInfo(item) {
      // if (item.data) {
      //   // 阻止多次请求 - 请求一次保存下来
      //   this.colonyUnitList = item.data
      // } else {
      let url = '/cdapis/communication/queryPrecinctList?upPrecinctId=' + item.areaCode
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
        .then((res) => {
          let data = res.data.data || []
          for (let i = 0; i < data.length; i++) {
            data[i].selected = false
            if (this.isSelected(data[i])) {
              data[i].selected = true
            } else {
              data[i].selected = false
            }
            data[i].user = { id: '' }
            data[i].memberList = []
            this.getUnitMemberList(data[i])
          }
          item.data = data
          this.colonyUnitList = item.data
          if (this.selectJudgeList.length > 0) {
            let list = this.selectJudgeList.filter((r) => r.areaCode === item.areaCode)
            if (list.length > 0) {
              this.selectUnitList = this.selectJudgeList
            } else {
              this.selectUnitList = this.selectJudgeList
              this.selectUnitList.push(item)
            }
          }
        })
        .catch((err) => {
          console.error(err)
        })
      // }

      if (!this.isSelected(item)) {
        this.selectUnitList = [item]
        for (let i = 0; i < this.allColonyUnitList.length; i++) {
          let uItem = this.allColonyUnitList[i]
          uItem.selected = false
        }
        item.selected = true
      }
    },
    isSelected(item) {
      let isSelected = false
      for (let i = 0; i < this.colonyUnitArr.length; i++) {
        let colonyUnit = this.colonyUnitArr[i]
        // eslint-disable-next-line eqeqeq
        if (item.areaCode == colonyUnit.areaCode) {
          isSelected = true
          break
        }
      }
      return isSelected
    },
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
      this.commanderIds_999998 = this.userList[0].list.map((item) => {
        return item.id
      })
      let commanderIds_999998 = this.commanderIds_999998
      let url = '/cdapis/Command/updateLeaderByPrecinctId'
      let param = {
        precinctId: '',
        planId: this.formInfo.id,
        linkType: 1,
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
.addCommandOrg {
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
  .addCommandOrg-bg {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #000;
    opacity: 0.5;
  }
  .addCommandOrg-warp {
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

    .addCommandOrg-head {
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

    .addCommandOrg-body {
      position: relative;
      height: calc(100% - 80px);
      overflow: hidden;

      .addDialog {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 100000;
        background: rgba(0, 0, 0, 0.5);
        .add-dialog-content {
          width: 72%;
          height: 75%;
          background: white;
          border-radius: 10px;
          margin: 6% auto 0;
          position: relative;
          .add-dialog-title {
            width: 100%;
            height: 100px;
            border-bottom: 4px solid #d9e1e5;
            display: inline-flex;
            justify-content: center;
            align-items: center;
            .title-content {
              width: 156px;
              height: 50%;
              font-size: 30px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: rgba(51, 51, 51, 1);
              line-height: 35px;
              i:nth-of-type(1) {
                font-size: 30px;
                margin: 0 25px 0 20px;
                background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
                // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
              }
              i:nth-of-type(2) {
                font-size: 30px;
                margin: 0 25px 0 20px;
                background: linear-gradient(-90deg, rgba(0, 150, 255, 1) 0%, rgba(0, 215, 254, 1) 100%);
                // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
              }
            }
            .close-icon {
              position: absolute;
              right: 38px;
              top: 38px;
              width: 48px;
              height: 48px;
              border-radius: 2px;
              i {
                font-size: 30px;
                color: rgba(182, 182, 182, 1);
              }
            }
          }
        }
        .transfer-content {
          width: 100%;
          height: 600px;
          display: inline-flex;
          justify-content: center;
          ::v-deep .el-transfer-panel {
            width: 500px;
          }
          ::v-deep .el-transfer-panel__body {
            height: 540px;
          }
          ::v-deep .el-transfer-panel__list.is-filterable {
            height: 80%;
          }
          .vx-content {
            width: 100%;
            height: 100%;
            .vx-content-item {
              height: 100%;
              width: 32%;
              float: left;
              margin: 0 2% 0 0;
              &.vx-content-margin {
                margin-left: 8%;
              }
              .vx-title {
                // width: 500px;
                height: 118px;
                background: linear-gradient(0deg, rgba(12, 153, 219, 0.2) 0%, rgba(255, 255, 255, 0) 100%);
                margin: 0 auto;
                line-height: 118px;
                text-align: left;
                i {
                  font-size: 48px;
                  margin: 0 25px 0 20px;
                  background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
                  // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
                  -webkit-background-clip: text;
                  -webkit-text-fill-color: transparent;
                }
              }
              .vx-input {
                margin: 32px 0 23px 0;
                width: 500px;
                height: 80px;
                input {
                  border: none;
                }
              }
              .vx-list {
                // width: 500px;
                height: 60%;
                margin: 0 auto;
                overflow-y: auto;
                overflow-x: hidden;
                &::-webkit-scrollbar {
                  width: 0 !important;
                }
                .vx-list-ul {
                  width: 100%;
                  height: 30px;
                  margin: 5px 0;
                  display: block;
                  &:hover {
                    background-image: url('../../../../assets/global/choice_list.png');
                    background-size: 100% 100%;
                  }
                  &.active {
                    background-image: url('../../../../assets/global/choice_list.png');
                    background-size: 100% 100%;
                  }
                  li {
                    width: 100%;
                    height: 30px;
                    font-size: 15px;
                    line-height: 30px;
                    // background: rgba(12, 153, 219, 0.1);
                    overflow: hidden;
                    cursor: pointer;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    i {
                      font-size: 15px;
                      line-height: 30px;
                      margin: 0 23px 0 46px;
                      color: #0c99db;
                    }
                  }
                }
              }

              .search-warp {
                margin-top: 20px;

                .el-input__inner {
                  padding-left: 60px;
                }
              }
            }
          }
          .btn {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 60px;
            // background-color: #0ff;
            display: inline-flex;
            justify-content: center;
            border-top: 2px solid #d6d6d6;

            .item {
              width: 125px;
              height: 40px;
              margin-top: 10px;
              text-align: center;
            }
            .item_btn1 {
              font-size: 18px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: rgba(12, 153, 219, 1);
              line-height: 45px;
              cursor: pointer;
            }
            .item_btn2 {
              font-size: 20px;
              background: linear-gradient(176deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
              border-radius: 8px;
              line-height: 45px;
              color: #fff;
              cursor: pointer;
            }
          }
        }
      }
    }

    .addCommandOrg-foot {
      height: 200px;
      text-align: center;
      margin-top: 20px;
    }
  }
}
</style>
