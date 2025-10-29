<template>
  <div class="AddEventDialog blank" v-if="dialogVisible">
    <el-drawer
      title="新增突发事件"
      :visible.sync="dialogVisible"
      direction="rtl"
      :modal="false"
      size="1320"
      :before-close="drawerClose"
    >
      <div class="leftForm">
        <div style="display: flex; margin-top: 5px">
          <el-input placeholder="请输入搜索内容" v-model="search"> </el-input>
          <el-button icon="el-icon-search" @click="rightSearch"></el-button>
        </div>
        <div class="mainContent" style="overflow: hidden">
          <div style="margin-left: 10px; margin-bottom: 5px">
            <el-checkbox v-model="checkAll" @change="checkAllChange">全部选择</el-checkbox>
          </div>
          <div style="height: 100%; overflow: scroll">
            <el-tree
              :data="list"
              ref="rightForm"
              node-key="id"
              :props="defaultProps"
              :expand-on-click-node="true"
              show-checkbox
              @check-change="handleCheckChange"
              v-loading="rightLoading"
            >
              <div
                style="width: 328px; height: 100%; display: flex; justify-content: space-between"
                slot-scope="{ data }"
              >
                <div
                  style="font-size: 14px; width: 228px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"
                >
                  {{ data.title }}
                </div>
              </div>
            </el-tree>
          </div>
        </div>
        <span style="margin-top: 5px; float: right; font-size: 14px; color: #606266"> 共 {{ total }} 条数据 </span>
      </div>
      <div class="footer">
        <el-button class="transfer-footer" @click="drawerClose">取消</el-button>
        <el-button class="transfer-footer" type="primary" @click="save()">保存</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script>
import Axios from 'axios'
export default {
  name: 'AddEventDialog',
  props: ['dialogVisible'],
  data() {
    return {
      search: '',
      checkAll: false,
      defaultProps: {
        id: 'id',
        label: 'title'
      },
      list: [],
      total: 0,
      rightLoading: false
    }
  },
  mounted() {
    this.queryEventList()
  },
  methods: {
    queryEventList() {
      let url = '/gtw/EIMS-management/queryEventList'
      if (this.search) {
        url = url + '?text=' + this.search
      }
      Axios.get(url).then((res) => {
        this.list = res.data.data.list
        this.total = res.data.data.total
      })
    },
    rightSearch() {
      this.queryEventList()
    },
    checkAllChange() {},
    handleCheckChange(data, checked, indeterminate) {
      // console.log('handleCheckChange: ', data, checked, indeterminate)
      //   if (checked) {
      //     this.rightForm.chooseList.push(data)
      //     console.log('rightForm.chooseList: ', this.rightForm.chooseList)
      //   } else {
      //     this.rightForm.chooseList.forEach((item, index) => {
      //       console.log('rightForm.chooseList: ', item)
      //       if (item.deviceId == data.deviceId && item.deviceName == data.deviceName) {
      //         console.log('取消选择的item: ', item)
      //         console.log('取消选择的item-index: ', index)
      //         this.rightForm.chooseList.splice(index, 1)
      //         console.log('rightForm.chooseList: ', this.rightForm.chooseList)
      //       }
      //     })
      //   }
    },
    drawerClose() {
      this.$emit('update:dialogVisible', false)
    },
    save() {}
  }
}
</script>
<style lang="scss">
// .AddEventDialog {
// }
</style>
