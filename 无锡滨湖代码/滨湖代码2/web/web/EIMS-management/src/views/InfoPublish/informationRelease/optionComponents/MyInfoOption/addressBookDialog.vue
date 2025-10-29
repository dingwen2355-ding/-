<template>
  <div class="addressBookDialog" v-if="dialogVisible">
    <el-dialog
      title="通讯录"
      :visible.sync="dialogVisible"
      width="1340px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div style="display: flex">
        <div class="leftGroup boxShadow3">
          <div style="text-align: right; height: 47px">
            <el-button type="primary" @click="addGroupInfo()">新增</el-button>
          </div>
          <el-tree
            ref="docTree"
            :data="nodesData"
            node-key="id"
            default-expand-all
            :props="defaultProps"
            :expand-on-click-node="false"
            highlight-current
            @node-click="handleNodeClick"
            style="text-align: left"
          >
            <span
              class="custom-tree-node"
              style="width: 100%"
              slot-scope="{ node, data }"
              @mouseenter="mouseenter(data)"
              @mouseleave="mouseleave(data)"
            >
              <span>{{ node.label }}</span>
              <el-button
                v-show="data.id === showId"
                size="mini"
                type="text"
                icon="el-icon-edit"
                style="margin-left: 20px"
                @click="() => editGroupInfo(node, data)"
              ></el-button>
              <el-button
                v-show="data.id === showId"
                size="mini"
                type="text"
                icon="el-icon-delete"
                style="margin-left: 10px"
                @click="() => deleGroupInfo(node, data)"
              ></el-button>
            </span>
          </el-tree>
        </div>
        <div class="rightGroup" v-show="groupMemberShow">
          <div style="text-align: right; height: 75px; width: 100%">
            <el-button
              type="primary"
              @click="addGroupMember()"
              style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
            >新增</el-button>
            <el-button
              type="primary"
              @click="deleteGroupMember()"
              style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
            >删除</el-button>
          </div>
          <div style="height: 620px; overflow: scroll; width: 100%">
            <el-table
              v-loading="loading"
              class="tableInfo"
              border
              ref="multipleTable"
              :data="groupMemberData"
              @selection-change="handleMemberSelectionChange"
              style="width: 100%"
            >
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="personName" label="姓名"></el-table-column>
              <el-table-column prop="post" label="职务"></el-table-column>
              <el-table-column prop="phoneNumber" label="手机"></el-table-column>
              <el-table-column prop="office" label="办公"></el-table-column>
              <el-table-column prop="duty" label="职责"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">保 存</el-button>
      </div>
    </el-dialog>
    <add-work-group-dialog
      :dialogVisible.sync="showPlanGroupAdd"
      @success="handlePlanGroupSuccess"
      :is-code="isPlanGroupCode"
      :formInfo="formPlanGroupForm"
    ></add-work-group-dialog>
    <add-work-group-member-dialog
      :dialogVisible.sync="showGroupMemberAdd"
      @success="handlePlanGroupMemberSuccess"
      :formInfo="formPlanGroupMemberForm"
      :planGroupId="planGroupId"
    ></add-work-group-member-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import addWorkGroupDialog from '../../optionComponents/workGroupOption/addWorkGroupDialog.vue'
import addWorkGroupMemberDialog from '../../optionComponents/workGroupOption/addWorkGroupMemberDialog.vue'

export default {
  name: 'addressBookDialog',
  props: ['dialogVisible', 'getChooseGroupMemberId'],
  components: { addWorkGroupDialog, addWorkGroupMemberDialog },
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.queryGroupList()
    },
    getChooseGroupMemberId(v) {
      this.getChooseGroupMemberIds = JSON.parse(JSON.stringify(v))
      if (this.getChooseGroupMemberIds) {
        this.showTag()
      }
    }
    // },
    // formInfo(v) {
    //   if (v) {
    //     this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
    //     console.log('this.form', this.form)
    //   }
    // }
  },
  data() {
    return {
      // 工作组
      planGroupId: '',
      title: '',
      groupNum: 0,
      memberNum: 0,
      nodesData: [],
      defaultProps: {
        id: 'id',
        label: 'groupName'
      },
      showId: '',
      showPlanGroupAdd: false,
      formPlanGroupForm: {
        userId: '',
        groupName: ''
      },
      isPlanGroupCode: 0, // 0 新增 1修改
      // 工作组成员
      loading: false,
      groupMemberShow: false,
      groupMemberData: [],
      groupMemberId: [],
      showGroupMemberAdd: false,
      formPlanGroupMemberForm: {
        groupId: '', // 组id
        post: '', // 职务
        office: '', // 办公
        personName: '', // 姓名
        phoneNumber: '', // 手机
        duty: '', // 职责
        getChooseGroupMemberIds: []
      }
    }
  },
  mounted() { },
  methods: {
    showTag() {
      let handleSelectData = this.getChooseGroupMemberIds || []
      for (let i = 0; i < this.groupMemberData.length; i++) {
        for (let j = 0; j < handleSelectData.length; j++) {
          if (this.groupMemberData[i].id === handleSelectData[j]) {
            this.$nextTick().then(() => {
              this.$refs.multipleTable.toggleRowSelection(this.groupMemberData[i], true)
            })
          }
        }
      }
    },
    queryGroupList() {
      let url = '/gtw/EIMS-management/get-mes-group?userId=' + localStorage.getItem('userId')
      Axios.get(url).then((res) => {
        this.nodesData = res.data.data.list
      })
    },
    // 新增工作组
    addGroupInfo() {
      this.isPlanGroupCode = 0
      this.showPlanGroupAdd = true
      this.formPlanGroupForm = {
        userId: localStorage.getItem('userId'),
        groupName: ''
      }
    },
    // 工作组相关操作
    mouseenter(data) {
      // console.log('鼠标移到此节点', data)
      this.showId = data.id
      // console.log('鼠标移到此节点', data)
    },
    mouseleave(data) {
      // console.log('鼠标移出此节点', data)
      this.showId = -1
      // console.log('鼠标移到此节点', data)
    },
    // 新增、编辑后操作
    handlePlanGroupSuccess() {
      this.queryGroupList()
      this.groupMemberShow = false
    },
    // 修改工作组
    editGroupInfo(node, data) {
      // console.log('修改工作组', node, data)
      this.isPlanGroupCode = 1
      this.showPlanGroupAdd = true
      this.formPlanGroupForm = {
        id: data.id,
        groupName: data.groupName
      }
    },
    // 删除工作组
    deleGroupInfo(node, data) {
      // console.log('删除工作组', node, data)
      this.$confirm('此操作将删除该工作组及成员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/del-mes-group?id=' + data.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.queryGroupList()
              this.groupMemberData = []
              this.groupMemberShow = false
            } else {
              this.$message.error('删除失败')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 点击左侧工作组
    handleNodeClick(data) {
      // console.log('点击的节点：', data)
      this.planGroupId = data.id
      this.groupMemberShow = true
      this.queryGroupMemberData(data.id)
    },
    // 获取工作组对应的人员 信息
    queryGroupMemberData(id) {
      let url = '/gtw/EIMS-management/get-mes-group-person?id=' + id
      Axios.get(url).then((res) => {
        this.groupMemberData = res.data.data.list
        this.showTag()
      })
    },
    handleClose() {
      this.$emit('upfate:getChooseGroupMemberId', [])
      this.$emit('update:dialogVisible', false)
    },
    // 新增工作组成员
    addGroupMember() {
      this.showGroupMemberAdd = true
      this.formPlanGroupMemberForm = {
        groupId: '', // 组id
        post: '', // 职务
        office: '', // 办公
        personName: '', // 姓名
        phoneNumber: '', // 手机
        duty: '' // 职责
      }
    },
    // 新增后操作
    handlePlanGroupMemberSuccess(id) {
      this.queryGroupMemberData(id)
    },
    handleMemberSelectionChange(val) {
      // console.log('选择成员信息: ', val)
      for (let item of val) {
        // console.log('选择成员信息item:', item)
        this.groupMemberId.push(item.id)
      }
      this.groupMemberId = Array.from(new Set(this.groupMemberId))
      // console.log('选择成员信息id列表: ', this.groupMemberId)
    },
    // 删除工作组成员
    deleteGroupMember() {
      // console.log('删除工作组成员: ')
      this.$confirm('此操作将删除该成员信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/del-mes-group-person'
          // console.log('选择成员信息id列表: ', this.groupMemberId)
          Axios.post(url, this.groupMemberId).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.queryGroupMemberData(this.planGroupId)
            } else {
              this.$message.error('删除失败')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handleOk() {
      console.log('提交保存 this.groupMemberId', this.groupMemberId)
      this.$emit('upfate:getChooseGroupMemberId', [])
      this.$emit('success', this.groupMemberId)
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>
<style lang="scss">
.addressBookDialog {
  .boxShadow3 {
    padding: 6px;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
  }
  .leftGroup {
    width: 30%;
    height: 800px;
    margin-right: 10px;
    margin-left: 10px;
  }
  .rightGroup {
    width: 70%;
    margin-right: 10px;
    margin-left: 10px;
  }
}
</style>
<style lang="scss" scoped>
.addressBookDialog {
  .el-dialog__body {
    height: 600px;
  }
}
</style>
