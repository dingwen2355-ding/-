<template>
  <section class="materiaList">
    <el-row class="header">
      <el-col>
        <el-form :inline="true">
          <el-form-item label="物资名称">
            <el-input v-model="formDetail.stuffName" clearable placeholder="请输入物资名称"></el-input>
          </el-form-item>
          <el-form-item label="物资类型">
            <el-cascader
              style="width: 200px"
              v-model="valueResource"
              :options="optionsResource"
              :props="resourceCascaderProps"
              placeholder="请选择物资所属类型"
              clearable
              filterable
              collapse-tags
            ></el-cascader>
          </el-form-item>
          <el-form-item label="可参与的救援类型">
            <el-cascader
              v-model="valueHelp"
              :options="optionsHelp"
              :props="helpCascaderProps"
              placeholder="请选择可参与的救援类型"
              clearable
              filterable
            ></el-cascader>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchRecord">查询</el-button>
            <el-button type="primary" icon="el-icon-plus" @click="increaseRecord" v-if="isCheck !== 1">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-table
      type="selection"
      ref="multipleTable"
      :data="tableData"
      border
      max-height="800px"
      :row-key="getRowKey"
      @selection-change="handleSelectionChange"
    >
      <el-table-column v-if="isCheck === 1" :reserve-selection="true" type="selection" width="55"></el-table-column>
      <el-table-column prop="index" label="序号" align="center" width="80"></el-table-column>
      <el-table-column prop="stuffName" label="物资名称" align="center" width="250"></el-table-column>
      <el-table-column prop="stuffUnit" label="计量单位" align="center" width="100"></el-table-column>
      <el-table-column prop="stuffTypeName" label="物资类型" align="center"></el-table-column>
      <el-table-column prop="helpTypeName" label="可参与的救援类型" align="center"></el-table-column>
      <el-table-column prop="isSpecial" label="是否为特种机器" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.isSpecial == 'Y'">是</el-tag>
          <el-tag type="warning" v-else>否</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="order" label="排序号" align="center"></el-table-column>
      <el-table-column label="操作" v-if="isCheck !== 1" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="recordSee(scope.row)">详情</el-button>
          <el-button type="text" size="medium" @click="recordChange(scope.row)">编辑</el-button>
          <el-button type="text" size="medium" @click="recordDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-body">
      <el-pagination
        background
        :current-page.sync="currentPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :page-sizes="[50, 100, 500, 1000]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
    <div v-if="isCheck === 1" class="bottomBtn" style="text-align: center">
      <el-button type="primary" @click="checkFinish">确定</el-button>
      <el-button @click="checkClose">取消</el-button>
      <el-button @click="checkOut">清空</el-button>
    </div>
    <DetailsDialog
      :dialogData.sync="dialogData"
      :title="title"
      :showDialog.sync="detailsDialog"
      @success="handleSuccess"
    />
  </section>
</template>
<script>
import axios from 'axios'
import qs from 'qs'
import DetailsDialog from './DetailsDialog.vue'
export default {
  props: ['isCheck', 'helpTypeList'],
  components: { DetailsDialog },
  data() {
    return {
      valueHelp: [],
      valueResource: [],
      optionsHelp: [],
      optionsResource: [],
      id: [],
      title: '增加',
      detailsDialog: false,
      dialogData: '',
      multipleSelection: '',
      tableData: [],
      options: [
        {
          value: 'Y',
          label: '是'
        },
        {
          value: 'N',
          label: '否'
        }
      ],
      currentPage: 1,
      pageSize: 50,
      total: 0,
      formDetail: {
        stuffName: ''
      },
      resourceCascaderProps: {
        multiple: true,
        checkStrictly: false,
        expandTrigger: 'hover',
        value: 'id',
        label: 'dictName',
        children: 'subList'
      },
      helpCascaderProps: {
        multiple: true,
        checkStrictly: true,
        expandTrigger: 'hover',
        value: 'id',
        label: 'dictName'
      }
    }
  },
  created() {
    this.valueHelp = this.helpTypeList || []
    this.getMateriaLiat()
    this.query()
  },
  watch: {
    helpTypeList(v) {
      this.valueHelp = v
      this.checkOut()
      this.checkFinish()
      this.getMateriaLiat()
      this.query()
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
      // console.log(this.multipleSelection)
    },
    getRowKey(row) {
      return row.id
    },
    checkFinish() {
      let ids = this.multipleSelection.map((item) => {
        return item.id
      })
      this.$emit('success', ids)
      this.$emit('close')
    },
    checkOut() {
      this.$refs.multipleTable.clearSelection()
    },
    checkClose() {
      this.$emit('close')
    },
    handleSuccess() {
      this.currentPage = 1
      this.query()
    },
    query() {
      let helpvalue = ''
      if (this.valueHelp.length > 0) {
        this.valueHelp.map((item) => {
          helpvalue += item + ','
          return helpvalue
        })
        // 判断截取最后一个逗号
        if (helpvalue.length > 0) {
          helpvalue = helpvalue.substr(0, helpvalue.length - 1)
        }
      }
      let mainTypeId = []
      // let subTypeId = null
      // console.log(this.valueResource)
      this.valueResource.forEach((item) => {
        mainTypeId.push(...item)
      })
      // console.log(mainTypeId)
      mainTypeId = [...new Set(mainTypeId)].join(',')
      let data = {
        ...{
          resourceName: this.formDetail.stuffName,
          resourceType: mainTypeId,
          helpType: helpvalue,
          page: this.currentPage,
          pageSize: this.pageSize
        }
      }
      // console.log(data, 1)
      axios
        .get('/gtw/EIMS-management/resourceList/queryResource?' + qs.stringify(data, { encode: false }))
        .then((r) => {
          this.tableData = r.data.data.list.map((e, index) => {
            e.index = (this.currentPage - 1) * this.pageSize + index + 1
            return e
          })

          this.total = r.data.data.total
          // console.log(r.data.data.list, 888)
        })
        .catch((v) => {
          // console.log(v)
        })
    },
    increaseRecord() {
      this.title = '增加'
      this.detailsDialog = true
    },
    getMateriaLiat() {
      axios
        .get('/gtw/EIMS-management/resourceList/queryResourceType?')
        .then((r) => {
          // console.log(r.data.data, 11)
          // const dataList =
          this.optionsResource = this.getTreeData(r.data.data)
          // this.optionsResource = r.data.data
          // console.log(this.optionsResource)
        })
        .catch((v) => {
          // console.log(v)
        })
      axios
        .get('/gtw/EIMS-management/resourceList/queryHelpType?')
        .then((r) => {
          // console.log(r.data.data)
          this.optionsHelp = r.data.data
        })
        .catch((v) => {
          // console.log(v)
        })
    },
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].subList.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].subList = undefined
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].subList)
        }
      }
      return data
    },
    handleSizeChange(val) {
      this.currentPage = 1
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.query()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.query()
    },
    // 搜索
    searchRecord() {
      this.currentPage = 1
      this.query()
    },
    // 详情
    recordSee(row) {
      this.title = '详情'
      this.dialogData = row
      this.detailsDialog = true
    },
    // 修改
    recordChange(row) {
      this.title = '修改'
      this.dialogData = row
      this.detailsDialog = true
    },
    // 删除
    async recordDelete(row) {
      const confirmResult = await this.$confirm('此操作将删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => {
        return err
      })
      if (confirmResult !== 'confirm') {
        return this.$message.info('已经取消了删除')
      } else {
        let data = {
          ...{
            id: row.id
          }
        }
        axios
          .get('/gtw/EIMS-management/resourceList/deleteResource?' + qs.stringify(data, { encode: false }))
          .then((res) => {
            // console.log(r)
            // console.log(this.selectedIsKeys)
            if (res.data.code === 200) {
              if (res.data.data == null) {
                this.currentPage = 1
                this.query()
                this.$message.success('删除成功！')
              } else {
                this.$message.warning(res.data.data)
              }
            } else {
              this.$message.warning(res.data.message)
            }
          })
          .catch((v) => {
            // console.log(v)
          })
      }
    }
  }
}
</script>
<style lang="scss">
</style>
