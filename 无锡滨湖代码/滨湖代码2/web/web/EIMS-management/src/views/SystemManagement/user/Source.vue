<template>
  <el-dialog title="资源管理" :modal-append-to-body="false" :before-close="cancel" :visible.sync="iSHow" :center="true">
    <div style="height: 500px; overflow: auto" v-if="dialogSourceInfo.roles[0].level !== '400'">
      <el-tree
        v-loading="loading"
        :data="treeData"
        node-key="precinctId"
        :props="defaultProps"
        show-checkbox
        ref="tree"
        :check-strictly="false"
        :default-expanded-keys="expandKeys"
        :default-checked-keys="checkedKeys"
        @check="handleCheck"
      ></el-tree>
    </div>
    <div style="height: 500px; overflow: auto" v-else>
      <el-checkbox-group v-model="checkedKeys" style="display: flex; flex-direction: column; padding-left: 30px">
        <el-checkbox
          style="height: 40px"
          v-for="item in treeData[0].precinctSon"
          :label="item.precinctId"
          :value="item.precinctId"
          :key="item.precinctId"
          >{{ item.precinctName }}</el-checkbox
        >
      </el-checkbox-group>
    </div>
    <div slot="footer">
      <el-button type="primary" @click="save">确定</el-button>
      <el-button type="primary" plain @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import axios from 'axios'

export default {
  props: ['dialogSourceInfo', 'dialogSourceVisible'],
  data() {
    return {
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      iSHow: false,
      loading: false,
      treeData: [],
      defaultProps: {
        children: 'precinctSon',
        label: 'precinctName'
      },
      checkedKeys: [],
      expandKeys: []
    }
  },
  computed: {},
  methods: {
    cancel() {
      this.iSHow = false
      this.$emit('cancel')
    },
    save() {
      // let idSet = new Set(
      //   this.checkedKeys.map(e => {
      //     return e.substr(0, 8)
      //   })
      // )
      // console.log(this.checkedKeys)
      // console.log(this.$refs.tree.getCheckedNodes(false, true))
      if (this.dialogSourceInfo.roles[0].level === '400' && this.checkedKeys.length > 1) {
        this.$message.error('区县管理员用户只能选择一个区县')
        return false
      }
      if (this.dialogSourceInfo.roles[0].level === '400' && this.checkedKeys.length === 0) {
        this.$message.error('请至少选择一个区')
        return false
      }
      if (this.checkedKeys.length > 1 && this.dialogSourceInfo.roleId === '2') {
        this.$message.error('普通科室用户只能选择一个科室')
        return false
      }
      if (this.checkedKeys.length === 0) {
        this.$message.error('请至少选择一个科室')
        return false
      }
      let url = ''
      if (this.dialogSourceInfo.roles[0].level !== '400') {
        url =
          '/gtw/EIMS-management/user?userId=' +
          this.dialogSourceInfo.userId +
          '&operationUserid=' +
          localStorage.getItem('userId') +
          '&precinctTreeId=' +
          this.checkedKeys.join() +
          '&streetId=' +
          (this.$refs.tree.getCheckedNodes(false, true).length === 0
            ? ''
            : this.$refs.tree.getCheckedNodes(false, true)[1].precinctId)
      } else {
        url =
          '/gtw/EIMS-management/user?userId=' +
          this.dialogSourceInfo.userId +
          '&operationUserid=' +
          localStorage.getItem('userId') +
          '&precinctTreeId=' +
          this.checkedKeys.join() +
          '&streetId=' +
          this.checkedKeys.join() +
          '&userLevel=' +
          '400'
      }

      axios.put(url).then((res) => {
        let data = res.data
        if (data.result === 0) {
          this.$message.success('修改成功')
          this.$emit('success')
          this.cancel()
        } else {
          this.$message.error(data.remark)
        }
      })
    },
    queryTree() {
      let url = '/gtw/EIMS-management/getCommitteeVilageTree?userId=' + localStorage.getItem('userId')
      this.loading = true
      axios
        .get(url)
        .then((res) => {
          this.loading = false
          let result = [
            {
              precinctId: '37030',
              precinctKind: '100',
              precinctName: '淄博市',
              precinctSon: null,
              upPrecinctId: ''
            }
          ]
          result[0].precinctSon = res.data.data
          this.treeData = result
          // console.log(this.treeData)
          this.queryUserSource()
        })
        .catch((_) => {
          this.loading = false
          this.$message.error('加载资源树失败')
        })
    },
    handleCheck(checkedNodes, checkedKeys) {
      this.checkedKeys = checkedKeys.checkedKeys
    },
    queryUserSource() {
      let url = '/gtw/EIMS-management/getUserPricinctTree?userId=' + this.dialogSourceInfo.userId
      axios.get(url).then((res) => {
        let data = res.data.data
        this.checkedKeys = []
        this.expandKeys = []
        data[0].listPrecinct.forEach((v) => {
          this.checkedKeys.push(v.precinctId)
          this.expandKeys.push(v.precinctId)
        })
        if (localStorage.getItem('roleId') !== '1') {
          this.dealData(this.treeData)
        }
        // console.log(this.treeData)
      })
    },
    dealData(data) {
      data.forEach((v) => {
        if (!this.checkedKeys.includes(v.precinctId)) {
          // v.disabled = true
          this.$set(v, 'disabled', true)
        }
        if (v.precinctSon && v.precinctSon.length !== 0) {
          this.dealData(v.precinctSon)
        }
      })
    }
  },
  mounted() {
    // console.log(this.dialogSourceInfo)
    this.iSHow = this.dialogSourceVisible
    this.queryTree()
    // this.queryUserSource()
  }
}
</script>
<style lang='scss'>
</style>
