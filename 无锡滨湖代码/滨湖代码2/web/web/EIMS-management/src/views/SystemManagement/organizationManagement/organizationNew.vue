<template>
  <div class="res-mana-container jq-page-style01">
    <div class="content-show-left-panel">
      <div class="content-tree-title">组织树</div>
      <!--      <el-input type="text" v-model="searchInfo" clearable placeholder="请输入组织名称搜索"
                      @clear="clearTreeSelect">
              <el-button slot="append" icon="el-icon-search" @click="search" style="background-color: #FFFFFF"></el-button>
            </el-input>-->
      <el-input
        type="text"
        v-model="searchInfo"
        clearable
        placeholder="请输入组织名称搜索"
        @change="searchInfoChange"
        @clear="clearTreeSelect"
        style="width: 100%; margin-right: 0px"
      >
      </el-input>
      <!--      <div class="search-icon" @click="search">-->
      <!--        <img src="../../assets/ztree/search.png" />-->
      <!--      </div>-->
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
      <el-tabs v-model="activeIndex" @tab-click="selectMenu">
        <div class="content-title">组织管理</div>
        <!--<el-tab-pane label="组织管理" name="area">-->
        <area-list-page
          ref="AreaListPage"
          :regionalTreePrecinctId="precinctId"
          @refreshTree="refreshTreeFun"
          @refreshClearTree="refreshClearTreeFun"
        ></area-list-page>
        <!--</el-tab-pane>-->
      </el-tabs>
    </div>
  </div>
</template>

<script>
import Axios from 'axios'
import $ from 'jquery'
import 'ztree'
import '../../../libs/jquery.ztree.exhide.min'
import AreaListPage from './areaOrganization'

export default {
  name: 'Organization',
  components: {
    'area-list-page': AreaListPage
  },
  data() {
    return {
      treeObj: '', // 组织树
      activeIndex: 'area',
      dataIs: true,
      treeSearch: '',
      precinctId: '', // 父组件的区域ID传给子组件
      searchInfo: '',
      nodeList: []
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initTree()
    })
  },
  methods: {
    searchInfoChange(val) {
      if (this.treeObj) {
        this.search(val)
      }
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
          // console.log(error)
        })
    },
    addDiyDom(treeId, treeNode) {
      let spaceWidth = 10
      let switchObj = $('#' + treeNode.tId + '_switch')
      let icoObj = $('#' + treeNode.tId + '_ico')
      switchObj.remove()
      icoObj.before(switchObj)
      if (treeNode.level > 0) {
        let spaceStr = "<span style='display: inline-block;width:" + spaceWidth * treeNode.level * 2 + "px'></span>"
        switchObj.before(spaceStr)
      }
    },
    selectMenu: function (key, keyPath) {
      // // console.log(key, keyPath);
      // this.activeIndex = keyPath[0];
    },
    refreshTreeFun: function () {
      this.initTree() // 暂时这样刷新吧
    },
    refreshClearTreeFun: function () {
      // this.initTree();因不适用默认选中第一个节点，所以重写方法
      this.precinctId = ''
      let self = this
      /* let loading = this.$loading({
          lock: true,
          text: "Loading",
          spinner: "el-icon-loading",
          background: "rgba(0, 0, 0, 0.7)"
        }); */
      let onClick = function (e, treeId, treeNode) {
        self.precinctId = treeNode.precinctId
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
                  color: '#4975F8',
                  'font-weight': 'bold'
                }
              : {
                  color: '#666666',
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
          let nodes = []
          let j = 0
          for (let i = 0, len = data.length; i < len; i++) {
            nodes[j] = data[i]
            nodes[j].pId = '' + data[i].upPrecinctId
            nodes[j].id = '' + data[i].precinctId
            nodes[j].name = data[i].precinctName
            // nodes[j].iconSkin = require('@/assets/ztree/paichusuo.png')
            j++
          }
          self.treeObj = $.fn.zTree.init($('#areaTree'), setting, nodes)
        })
        .catch(function (error) {
          // console.log(error)
        })
    }
  }
}
</script>

<style lang="scss">
.res-mana-container {
  width: 100%;
  height: 100%;
  .area-title {
    margin-left: 15px;
    float: left;
  }
  .refresh-icon {
    margin-right: 15px;
    float: right;
  }
  .el-icon-search1 {
    background: url('../../../assets/ztree/search.png') no-repeat;
    margin-top: 8px;
  }
  .zv-search-wrap {
    width: calc(100% - 30px);
    margin-left: 15px;
    margin-top: 20px;
  }
  .unData {
    display: inline-block;
    text-align: center;
    margin-top: 3rem;
  }
  .waring_area {
    color: #6187d8;
    font-size: 2.2rem;
  }
  .unData_text {
    font-size: 14px;
    margin-top: 1rem;
  }
  .tree {
    margin-top: 12px;
    height: 800px;
    /*overflow: auto;*/
    text-align: center;
    // .ztree * {
    //   /* font-size: 16px;*/
    //   /*       color: #666666;*/
    // }
    .ztree li a:hover .znv-tree-edit,
    .ztree li .curSelectedNode .znv-tree-edit {
      display: inline-block;
      color: #fff;
    }
    .ztree01 li {
      line-height: 30px;
    }
  }
  .line {
    width: 100%;
    /*border-top: 1px solid #1999D7;*/
    border-top: 0.1rem solid #dbdbdb;
    margin-top: 20px;
    margin-bottom: 20px;
  }
  .right-container {
    width: calc(100% - 320px);
    height: 100%;
    padding-left: 20px;
    .right-top {
      height: 60px;
      .el-menu--horizontal {
        border: 0;
        background: transparent;
      }
      .el-menu-item.is-active {
        background-color: transparent !important;
        color: #5083e4 !important;
      }
      .el-menu--horizontal > .el-menu-item {
        height: 60px;
        line-height: 60px;
        color: #555;
        &:hover {
          background: transparent !important;
          color: #5083e4;
        }
      }
    }
    .el-tabs__header {
      margin: 0px;
    }
    .right-bottom {
      height: calc(100% - 60px);
      border-top: 1px solid #d3d6d8;
    }
  }
  .search-icon {
    float: right;
    position: relative;
    width: 20px;
    height: 20px;
    cursor: pointer;
    background-color: transparent;
    margin-right: 10px;
  }

  .search-icon img {
    display: block;
    width: 18px;
    height: 18px;
    /*margin-left: 17px;*/
    margin-top: 7px;
  }
}
</style>
