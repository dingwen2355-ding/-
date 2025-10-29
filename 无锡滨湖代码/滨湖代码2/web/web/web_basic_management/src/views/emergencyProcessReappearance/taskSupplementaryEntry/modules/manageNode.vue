<template>
    <div class="manageNode">
        <el-dialog
            :visible.sync="dialogVisible"
            width="700px"
            :modal-append-to-body="false"
            :append-to-body="false"
            :before-close="handleClose"
        >
            <div class="info_tools">
              <el-button icon="el-icon-plus" @click="addNewNode">增加节点</el-button>
            </div>
            <div class="info_body">
              <el-table
                  :data="nodeList"
                  style="width: 100%">
                <el-table-column
                    prop="id"
                    label="序号"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="nodeName"
                    label="节点名称">
                </el-table-column>
                <el-table-column
                    prop="createTime"
                    label="创建时间"
                    width="180">
                </el-table-column>
                <el-table-column
                    label="操作"
                    prop="desc">
                  <template slot-scope="scope">
                    <i class="el-icon-edit" @click="editNode(scope.row)"></i>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleClose()">取 消</el-button>
                <el-button type="primary" @click="handleOk">确 定</el-button>
            </div>
        </el-dialog>
        <add-template-node :dialogVisible.sync="addTemplateNodeVisible" :formInfo="formInfo"></add-template-node>
    </div>
</template>
<script>
import request from '@/utils/request'
import addTemplateNode from '@/views/emergencyProcessReappearance/taskSupplementaryEntry/modules/addTemplateNode'
export default {
  name: 'manageNode',
  props: ['dialogVisible'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    }
  },
  components: {
    addTemplateNode: addTemplateNode
  },
  data() {
    return {
      rules: {
      },
      nodeList: [],
      addTemplateNodeVisible: false,
      formInfo: {
      }
    }
  },
  mounted() {
    this.getEventList()
  },
  methods: {
    addNewNode() {
      this.formInfo = {}
      this.addTemplateNodeVisible = true
    },
    getEventList() {
      let url = '/binhuapis/dapeng/eventNode/query-condition?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      request.get(url).then(res => {
        this.nodeList = res.data.data.list
      })
    },
    editNode(row) {
      this.formInfo = {
        id: row.id,
        nodeName: row.nodeName
      }
      this.addTemplateNodeVisible = true
    },
    handleClose() {
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.loading = false
    },
    handleOk() {
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>
<style lang="scss" scoped>
.manageNode{
  .info_tools{
    width: 100%;
    height: 40px;
    display: flex;
    flex-direction: row-reverse;
  }
}
</style>
