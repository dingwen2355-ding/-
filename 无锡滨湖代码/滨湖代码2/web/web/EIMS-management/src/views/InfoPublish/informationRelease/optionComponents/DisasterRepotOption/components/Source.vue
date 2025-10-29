<template>
  <div class="source" v-if="dialogSourceVisible">
    <el-dialog title="选择报送科室" :modal-append-to-body="false" :before-close="cancel" :visible.sync="iSHow" :center="true">
      <div style="height: 500px; overflow: auto">
        <!-- <div style="height: 500px; overflow: auto" v-if="dialogSourceInfo.level !== '400'"> -->
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
          @check-change="handleCheckChange"
        ></el-tree>
      </div>
      <!-- <div style="height: 500px; overflow: auto" v-else>
      <el-checkbox-group v-model="checkedKeys" style="display: flex; flex-direction: column; padding-left: 30px">
        <el-checkbox
          style="height: 40px"
          v-for="item in treeData"
          :label="item.precinctId"
          :value="item.precinctId"
          :key="item.precinctId"
          >{{ item.precinctName }}</el-checkbox
        >
      </el-checkbox-group>
      </div>-->
      <div slot="footer">
        <el-button type="primary" @click="save" v-if="isCode != 1">确定</el-button>
        <el-button type="primary" plain @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  props: ['dialogSourceInfo', 'dialogSourceVisible', 'isCode', 'checkedKeys'],
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
      choosedKeys: [],
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
      // let ids = []
      // this.choosedKeys.forEach((item, index) => {
      //   // console.log('choosedKeys: ', item)
      //   ids.push(item.precinctId)
      // })
      // console.log('choosedKeys-ids: ', this.choosedKeys)

      this.$emit('choosedKeys', this.choosedKeys)
      this.$emit('cancel')
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
    handleCheckChange(data, checked, indeterminate) {
      // console.log(data, checked, indeterminate)
      if (this.checkedKeys.length > 0) {
        // console.log('checkedKeys: ', this.checkedKeys)
        this.choosedKeys = this.choosedKeys.concat(this.checkedKeys)
        // console.log('追加后的choosedKeys: ', this.choosedKeys)
        if (checked) {
          this.choosedKeys.push(data.precinctId)
          // console.log('已勾选的科室id', this.choosedKeys)
        } else {
          this.choosedKeys.forEach((item, index) => {
            // console.log('choosedKeys: ', item)
            if (item == data.precinctId) {
              // console.log('取消选择的item: ', item)
              // console.log('取消选择的item-index: ', index)
              this.choosedKeys.splice(index, 1)
              // console.log('最终的choosedKeys: ', this.choosedKeys)
            }
          })
        }
        this.choosedKeys = Array.from(new Set(this.choosedKeys))
        // console.log('最终的choosedKeys: ', this.choosedKeys)
      } else {
        // console.log('#####################################################')
        // console.log('checkedKeys: ', this.checkedKeys)
        if (checked) {
          this.choosedKeys.push(data.precinctId)
          // console.log('已勾选的科室id', this.choosedKeys)
        } else {
          this.choosedKeys.forEach((item, index) => {
            // console.log('choosedKeys: ', item)
            if (item == data.precinctId) {
              // console.log('取消选择的item: ', item)
              // console.log('取消选择的item-index: ', index)
              this.choosedKeys.splice(index, 1)
              // console.log('choosedKeys: ', this.choosedKeys)
            }
          })
        }
        this.choosedKeys = Array.from(new Set(this.choosedKeys))
      }
    },
    // handleCheck(checkedNodes, checkedKeys) {
    //   // this.checkedKeys = checkedKeys.checkedKeys
    // },
    queryUserSource() {
      let url = '/gtw/EIMS-management/getUserPricinctTree?userId=' + this.dialogSourceInfo.userId
      axios.get(url).then((res) => {
        let data = res.data.data
        // this.checkedKeys = []
        this.expandKeys = []
        data[0].listPrecinct.forEach((v) => {
          // this.checkedKeys.push(v.precinctId)
          this.expandKeys.push(v.precinctId)
        })
        // if (localStorage.getItem('roleId') !== '1') {
        //   this.dealData(this.treeData)
        // }
        // console.log(this.treeData)
      })
    }
    // dealData(data) {
    //   data.forEach((v) => {
    //     if (!this.checkedKeys.includes(v.precinctId)) {
    //       // v.disabled = true
    //       this.$set(v, 'disabled', true)
    //     }
    //     if (v.precinctSon && v.precinctSon.length !== 0) {
    //       this.dealData(v.precinctSon)
    //     }
    //   })
    // }
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
.source {
  .el-dialog__body {
    height: 535px;
    // overflow: auto;
  }
}
</style>
