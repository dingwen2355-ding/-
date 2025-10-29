<template>
  <el-dialog
    title="配置管理"
    :modal-append-to-body="false"
    :before-close="cancel"
    :visible.sync="dialogWareVisible"
    :center="true"
  >
    <div style="height: 500px; overflow: auto">
      <!-- <el-input
        style="margin: 10px; width: 90%"
        placeholder="输入名称"
        v-model="filterText">
      </el-input> -->
      <el-tree
        v-loading="loading"
        :data="treeData"
        node-key="id"
        :props="defaultProps"
        :filter-node-method="filterNode"
        show-checkbox
        ref="tree"
        :check-strictly="false"
        :default-expanded-keys="checkedKeys"
        :default-checked-keys="newarr"
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
import axios from 'axios'

export default {
  props: ['dialogSourceInfo', 'dialogWareVisible'],
  data() {
    return {
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      iSHow: false,
      loading: false,
      treeData: [],
      newarr: [],
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      checkedKeys: []
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter()
    }
  },
  methods: {
    cancel() {
      this.iSHow = false
      this.$emit('cancel')
    },
    queryTree() {
      let url = '/gtw/EIMS-management/warehouse/warehouseDetail'
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.treeData = res.data.data || []
          this.queryChecked()
        }
      })
    },
    filterNode(value, data) {
      var nodes = this.$refs.tree.store.nodesMap
      for (var i in nodes) {
        nodes[i].expanded = true
      }
      let textKey = this.filterText === '' ? true : data.label.indexOf(this.filterText) !== -1
      return textKey
    },
    queryChecked() {
      let url = `/gtw/EIMS-management/warehouse/warehouseDetailById?userId=${this.dialogSourceInfo.userId}`
      axios.get(url).then((r) => {
        this.checkedKeys = r.data.data || []
        let temp = this.checkedKeys.map((v) => {
          return v.children
        })
        for (let i = 0; i < temp.length; i++) {
          let temp2 = temp[i].map((v) => {
            return v.id
          })
          this.newarr = this.newarr.concat(temp2)
          // console.log(this.newarr)
        }
      })
    },
    handleCheck(v) {
      // console.log(v)
    },
    save() {
      let arr = this.$refs.tree.getCheckedKeys()
      let result = ''
      // console.log(this.$refs.tree.getCheckedKeys())
      arr.forEach((i) => {
        if (typeof i === typeof 1) {
          result += ',' + i
        }
      })
      result = result.slice(1)
      // console.log(this.dialogSourceInfo)
      // console.log(localStorage.getItem('userId'))
      let url = `/gtw/EIMS-management/user?userId=${this.dialogSourceInfo.userId}&stockId=${result}`
      // console.log(url)
      axios.put(url).then((r) => {
        if (r.data.remark === 'success') {
          this.$message.success('修改成功')
          this.cancel()
        } else {
          this.$message.error(r.data.remark)
        }
      })
    }
  },
  mounted() {
    this.iSHow = this.dialogWareVisible
    this.queryTree()
    // this.queryUserSource()
  }
}
</script>
