<template>
  <el-dialog title="组织管理" :modal-append-to-body="false" :before-close="cancel" :visible.sync="iSHow" :center="true">
    <div style="height: 500px; overflow: auto">
      <el-tree
        v-loading="loading"
        :data="treeData"
        node-key="precinctId"
        :props="defaultProps"
        show-checkbox
        ref="tree"
        :check-strictly="false"
        :default-expanded-keys="checkedKeys"
        :default-checked-keys="checkedKeys"
        @check="handleCheck"
      ></el-tree>
    </div>
    <div slot="footer">
      <el-button type="primary" @click="save">确定</el-button>
      <el-button type="primary" plain @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import axios from '@/utils/request.js'

export default {
  props: ['dialogSourceInfo', 'dialogSourceVisible'],
  data() {
    return {
      menus: configs.menus,
      menuIds: JSON.parse(sessionStorage.getItem('menuIds')),
      iSHow: false,
      loading: false,
      treeData: [],
      defaultProps: {
        children: 'precinctSon',
        label: 'precinctName'
      },
      checkedKeys: []
    }
  },
  methods: {
    cancel() {
      this.iSHow = false
      this.$emit('cancel')
    },
    save() {
      let idSet = new Set(
        this.checkedKeys.map((e) => {
          return e.substr(0, 8)
        })
      )
      if (idSet.size > 1 && this.dialogSourceInfo.userId !== '57') {
        this.$notify.error({
          title: '错误',
          message: '只能选择一个所属社区',
          duration: 1000
        })
        return false
      }
      if (this.checkedKeys.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请至少选择一个资源数',
          duration: 1000
        })
        return false
      }
      let url =
        '/auth/user?userId=' +
        this.dialogSourceInfo.userId +
        '&operationUserid=' +
        sessionStorage.getItem('userId') +
        '&precinctTreeId=' +
        this.checkedKeys.join() +
        '&streetId=' +
        (this.$refs.tree.getCheckedNodes(false, true).length === 0
          ? ''
          : this.$refs.tree.getCheckedNodes(false, true)[0].precinctId)
      axios.put(url).then((res) => {
        let data = res.data
        if (data.result === 0) {
          this.$notify({
            title: '成功',
            message: '修改成功',
            type: 'success',
            duration: 1000
          })
          this.cancel()
          this.$emit('success')
        } else {
          this.$notify.error({
            title: '错误',
            message: data.remark,
            duration: 1000
          })
        }
      })
    },
    queryTree() {
      let url = '/auth/getCommitteeVilageTree?userId=' + sessionStorage.getItem('userId')
      this.loading = true
      axios
        .get(url)
        .then((res) => {
          this.loading = false
          let data = res.data.data
          this.treeData = data
          this.queryUserSource()
        })
        .catch((_) => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '加载资源树失败',
            duration: 1000
          })
        })
    },
    handleCheck(checkedNodes, checkedKeys) {
      this.checkedKeys = checkedKeys.checkedKeys
    },
    queryUserSource() {
      let url = '/auth/getUserPricinctTree?userId=' + this.dialogSourceInfo.userId
      axios.get(url).then((res) => {
        let data = res.data.data
        this.checkedKeys = []
        data[0].listPrecinct.forEach((v) => {
          this.checkedKeys.push(v.precinctId)
        })
        // if (sessionStorage.getItem('roleId') !== '1') {
        //   this.dealData(this.treeData)
        // }
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
    this.iSHow = this.dialogSourceVisible
    this.queryTree()
    // this.queryUserSource()
  }
}
</script>
