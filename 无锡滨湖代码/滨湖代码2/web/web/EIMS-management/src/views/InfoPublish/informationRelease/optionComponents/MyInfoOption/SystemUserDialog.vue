<template>
  <div class="systemUserDialog" v-if="dialogVisible">
    <el-dialog
      title="系统用户"
      :visible.sync="dialogVisible"
      width="1340px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="mainContent" style="display: flex">
        <div class="content-show-left-panel">
          <el-input
            type="text"
            v-model="searchInfo"
            clearable
            placeholder="请输入组织名称搜索"
            @change="searchInfoChange"
            @clear="clearTreeSelect"
            style="width: 100%; margin-right: 0px"
          ></el-input>
          <section class="tree">
            <el-scrollbar class="znv-el-scrollbar-y" v-show="!dataIs">
              <ul id="areaTree" class="lib-ztree ztree ztree01"></ul>
            </el-scrollbar>
            <div class="unData" v-show="dataIs">
              <i class="el-icon-warning waring_area"></i>
              <p class="unData_text">暂无相关数据</p>
            </div>
          </section>
        </div>
        <div class="content-show-right-panel">
          <div class="topContent">
            <el-input v-model="keyword" clearable placeholder="模糊搜索" style="width: 20%"></el-input>
            <el-button type="primary" @click="query">搜索</el-button>
          </div>
          <el-table
            :data="userData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
            highlight-current-row
            v-loading="listLoading"
            @selection-change="handleSelectionChange"
            height="680"
            border
            ref="multipleTable"
            stripe
          >
            <el-table-column type="selection" width="80"></el-table-column>
            <el-table-column prop="userName" label="用户名"></el-table-column>
            <el-table-column prop="trueName" label="姓名"></el-table-column>
            <el-table-column prop="roleName" label="用户角色"></el-table-column>
          </el-table>
          <el-pagination
            @current-change="checkPage"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="userData.length"
          ></el-pagination>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import $ from 'jquery'
import 'ztree'
export default {
  name: 'systemUserDialog',
  props: ['dialogVisible', 'getChooseSysUserId'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.$nextTick(() => {
        this.initTree()
        this.query()
      })
    },
    getChooseSysUserId(v) {
      this.getChooseSysUserIds = JSON.parse(JSON.stringify(v))
      if (this.getChooseSysUserIds) {
        this.showTag()
      }
    }
  },
  data() {
    return {
      searchInfo: '',
      dataIs: false,
      listLoading: false,
      userData: [],
      currentPage: 1,
      pageSize: 12, // 每页显示几条
      selectionIds: [],
      treeObj: '',
      btnVisible: false,
      keyword: '',
      precinctId: '',
      nodeList: [],
      getChooseSysUserIds: []
    }
  },
  mounted() { },
  methods: {
    showTag() {
      let handleSelectData = this.getChooseSysUserIds || []
      for (let i = 0; i < this.userData.length; i++) {
        for (let j = 0; j < handleSelectData.length; j++) {
          if (this.userData[i].userId === handleSelectData[j]) {
            this.$nextTick().then(() => {
              this.$refs.multipleTable.toggleRowSelection(this.userData[i], true)
            })
          }
        }
      }
    },
    searchInfoChange(val) {
      if (this.treeObj) {
        this.search(val)
      }
    },
    search: function (text) {
      text = this.searchInfo
      this.updateNodes(false)
      if (text == null) {
        return
      }
      let trimtext = $.trim(text)
      let treeObj = this.treeObj
      let allNodes = treeObj.getNodes()
      if (trimtext == '') {
        this.showAllNodes(allNodes)
        return
      }
      this.nodeList = this.treeObj.getNodesByParamFuzzy('name', trimtext) // 调用ztree的模糊查询功能，得到符合条件的节点
      this.updateNodes(true) // 更新节点
    },
    showAllNodes: function (allNodes) {
      let cnodes = allNodes
      while (cnodes && cnodes.length > 0) {
        let temNodes = []
        for (let i = 0; i < cnodes.length; i++) {
          let node = cnodes[i]
          let cnode = node.children
          this.treeObj.showNode(node)
          if (node.highlight) {
            node.highlight = false
            this.treeObj.updateNode(node)
          }
          if (cnode && cnode.length > 0) {
            temNodes = temNodes.concat(cnode)
          }
        }
        cnodes = temNodes
      }
    },
    initTree() {
      let self = this
      /* let loading = this.$loading({
          lock: true,
          text: "Loading",
          spinner: "el-icon-loading",
          background: "rgba(0, 0, 0, 0.7)"
        }); */
      let onClick = function (e, treeId, treeNode) {
        self.precinctId = treeNode.precinctId
        self.query()
        /*          self.$refs.AreaListPage.queryArea(); */
        self.btnVisible = treeNode.precinctKind == '1'
        let allNames = ''
        let getAllNames = function (node) {
          // 遍历节点获得完整的路径
          allNames = node.name + ' ' + allNames
          let parentNode = node.getParentNode()
          if (parentNode) {
            getAllNames(parentNode)
          }
        }
        getAllNames(treeNode)
      }
      let setting = {
        view: {
          fontCss: function (treeId, treeNode) {
            return treeNode.highlight
              ? {
                color: '#1E90FF',
                'font-weight': 'bold'
              }
              : {
                color: '#84868a',
                'font-weight': 'normal'
              }
          },
          // showIcon: true,
          showLine: false,
          selectedMulti: false,
          addDiyDom: this.addDiyDom
        },
        edit: {
          enable: false
        },
        data: {
          key: {
            name: 'name',
            iconSkin: 'iconCls'
          },
          simpleData: {
            enable: true,
            idKey: 'id',
            pIdKey: 'pId',
            rootPId: 0
          }
        },
        callback: {
          onClick: onClick
        }
      }
      Axios.get('/gtw/EIMS-management/regional')
        .then(function (response) {
          // loading.close();
          // self.menuIsShow = true;
          self.dataIs = false
          let data = response.data.data.precinct
          data.push({
            areaCode: '37030',
            createUser: null,
            gpsType: null,
            gpsX: null,
            gpsY: null,
            gpsZ: null,
            precinctAddr: null,
            precinctId: '37030',
            precinctKind: 100,
            precinctName: '淄博市',
            regionXY: null,
            upPrecinctId: ''
          })
          let nodes = []
          let j = 0
          for (let i = 0, len = data.length; i < len; i++) {
            nodes[j] = data[i]
            nodes[j].pId = data[i].upPrecinctId === '' || !data[i].upPrecinctId ? '37030' : data[i].upPrecinctId
            nodes[j].id = '' + data[i].precinctId
            nodes[j].name = data[i].precinctName
            // nodes[j].iconSkin = require('@/assets/ztree/paichusuo.png')
            j++
          }
          // console.log(nodes)
          self.treeObj = $.fn.zTree.init($('#areaTree'), setting, nodes)
          // 初始化选中根节点，每次新建后仍然选中当前设备树节点
          nodes = self.treeObj.getNodes()
          if (self.precinctId == null || self.precinctId == undefined || self.precinctId == '') {
            let pnode = nodes[0]
            self.treeObj.selectNode(pnode)
            self.precinctId = pnode.precinctId
          } else {
            // let nodeList = self.treeObj.getNodesByParamFuzzy("id", self.precinctId);
            var node = self.treeObj.getNodeByParam('id', self.precinctId)
            if (node) {
              //  self.treeObj.selectNode(node);
              self.treeObj.expandNode(node, true, false, false)
            } else {
              let pnode = nodes[0]
              self.treeObj.selectNode(pnode)
              self.precinctId = pnode.precinctId
            }
          }
          self.treeObj.expandAll(true) // 展开全部结点
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    checkPage(v) {
      // console.log(this.userData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize))
    },
    query() {
      var self = this
      let precinctId = this.precinctId === '37030' ? '' : this.precinctId
      var url = `/gtw/EIMS-management/user?operationUserid=${localStorage.getItem('userId')}&precinctId=${precinctId}&keyword=${this.keyword
        }`
      self.listLoading = true
      Axios.get(url).then(function (res) {
        if (res.data.result === 0) {
          self.listLoading = false
          self.userData = res.data.data
          self.showTag()
          // console.log(res.data.data)
        } else {
          self.$message(res.data.remark)
        }

      })
    },
    clearTreeSelect() {
      // 清除树的高亮显示内容
      this.updateNodes(false)
      let treeObj = this.treeObj
      let allNodes = treeObj.getNodes()
      this.showAllNodes(allNodes)
    },
    updateNodes: function (highlight) {
      let nodeList = this.nodeList
      let treeObj = this.treeObj
      let showNodes = {}
      let searchNodes = {}
      // 高亮节点
      for (let i = 0, l = nodeList.length; i < l; i++) {
        let node = nodeList[i]
        // console.log(node.name)
        node.highlight = highlight // 高亮显示搜索到的节点(highlight是自己设置的一个属性)
        let pnode = node.getParentNode()
        while (pnode != null || pnode) {
          // 父节点显示
          showNodes[pnode.id] = true
          if (pnode.highlight && !searchNodes[pnode.id]) {
            pnode.highlight = false
            treeObj.updateNode(pnode)
          }
          pnode = pnode.getParentNode()
        }
        /*
          let tcnodes = node.children;
          while (tcnodes && tcnodes.length > 0) { // 子节点显示
            let temNodes = [];
            for (let i = 0; i < tcnodes.length; i++) {
              let snode = tcnodes[i];
              let cnode = snode.children;
              showNodes[snode.nodeId] = true;
              if (snode.highlight) {
                snode.highlight = false;
                treeObj.updateNode(snode);
              }
              if (cnode && cnode.length > 0) {
                temNodes = temNodes.concat(cnode);
              }
            }
            tcnodes = temNodes;
          }
          */
        searchNodes[node.id] = true
        showNodes[node.id] = true
        // treeObj.expandNode(node.getParentNode(), true, false, false); // 将搜索到的节点的父节点展开
        treeObj.updateNode(node) // 更新节点数据，主要用于该节点显示属性的更新
      }
      // 显示隐藏节点
      let allNodes = treeObj.getNodes()
      let cnodes = allNodes
      while (cnodes && cnodes.length > 0) {
        let temNodes = []
        for (let i = 0; i < cnodes.length; i++) {
          let node = cnodes[i]
          let cnode = node.children
          if (!showNodes[node.id]) {
            treeObj.hideNode(node)
          } else {
            treeObj.showNode(node)
            if (cnode && cnode.length > 0) {
              temNodes = temNodes.concat(cnode)
            }
          }
        }
        cnodes = temNodes
      }
      treeObj.expandAll(true)
    },
    handleSelectionChange(rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.userId)
      })
    },
    // checkSelectable(row) {
    //   return row.userName !== 'admin'
    // },
    handleClose() {
      this.$emit('upfate:getChooseSysUserId', [])
      this.$emit('update:dialogVisible', false)
    },
    handleOk() {
      // console.log('提交保存 this.selectionIds', this.selectionIds)
      // this.$emit('getChooseSysUserId', this.selectionIds)
      this.$emit('upfate:getChooseSysUserId', [])
      this.$emit('success', this.selectionIds)
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>
<style lang="scss">
.systemUserDialog {
  .el-dialog__body {
    height: 800px;
  }
  .content-show-left-panel {
    width: 30%;
    height: 720px;
    padding: 10px;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
    .ztree {
      height: 665px;
    }
  }
  .content-show-right-panel {
    width: 70%;
    height: 800px;
    margin: 0px 0 0 10px;
  }
}
</style>
