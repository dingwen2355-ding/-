<template>
  <div class="user-page">
    <div class="topContent">
      <el-col :span="24" style="padding: 20px 0">
        <el-input v-model="keyword" clearable placeholder="模糊搜索" style="width: 20%"> </el-input>
        <el-button type="primary" @click="query">搜索 </el-button>

        <el-button type="primary" @click="addRow">新增</el-button>
        <el-button type="primary" @click.prevent="delRow">删除 </el-button>
      </el-col>
    </div>
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
        >
        </el-input>
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
        <el-table
          :data="userData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
          highlight-current-row
          v-loading="listLoading"
          @selection-change="handleSelectionChange"
          height="760"
          border
          stripe
        >
          <el-table-column type="selection" width="80" :selectable="checkSelectable"></el-table-column>
          <el-table-column prop="userName" label="用户名"></el-table-column>
          <el-table-column prop="trueName" label="姓名"></el-table-column>
          <!--      <el-table-column prop="departmentName" label="组织"></el-table-column>-->
          <el-table-column prop="roleName" label="用户角色"></el-table-column>
          <el-table-column prop="precinctTreeId" label="管理科室">
            <template slot-scope="scope">
              <div v-if="scope.row.precinctTreeId" :class="scope.row.userName === 'admin' ? 'heightCell' : ''">
                <span v-for="(item, index) in scope.row.precinctTreeId.split(',')" :key="index">
                  {{ precinctNameList[item] }}{{ index === scope.row.precinctTreeId.split(',').length - 1 ? '' : ',' }}
                </span>
              </div>
              <div v-else>暂无</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="270">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="medium"
                @click="editSource(scope.$index, scope.row)"
                v-if="userRoleId === '1' || userRoleId === '2'"
                >资源管理
              </el-button>
              <el-button type="text" size="medium" @click="editRow(scope.$index, scope.row)">编辑 </el-button>
              <el-button type="text" size="medium" @click="editPassword(scope.$index, scope.row)">修改密码 </el-button>
              <el-button type="text" size="medium" @click="offOnUser(scope.$index, scope.row)"
                >{{ scope.row.userState === '0' ? '停用' : '启用' }}
              </el-button>
              <el-button
                type="text"
                size="medium"
                style="margin-left: 0px"
                @click="editWare(scope.$index, scope.row)"
                v-if="
                  scope.row.roles.length !== 0 && ['300', '500', '400', '600', '700'].includes(scope.row.roles[0].level)
                "
                >{{
                  scope.row.roles[0].level === wareHouseLevel
                    ? '仓库管理'
                    : scope.row.roles[0].level === '500'
                    ? '监管企业管理'
                    : scope.row.roles[0].level === '600'
                    ? '救援队伍管理'
                    : scope.row.roles[0].level === '700'
                    ? '社会力量管理'
                    : ''
                }}
              </el-button>
            </template>
          </el-table-column>
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
    <!-- 资源管理 -->
    <Source
      v-if="dialogSourceVisible"
      @success="query"
      @cancel="dialogSourceVisible = false"
      :dialogSourceInfo="dialogSourceInfo"
      :dialogSourceVisible="dialogSourceVisible"
    />
    <!-- 仓库管理 -->
    <WareHouse
      v-if="dialogWareVisible"
      @cancel="dialogWareVisible = false"
      :dialogSourceInfo="dialogSourceInfo"
      :dialogWareVisible="dialogWareVisible"
    />
    <!--新增界面-->
    <el-dialog
      title="用户信息"
      :visible.sync="dialogFormVisible"
      :modal-append-to-body="false"
      :center="dialogCenter"
      top="2vh"
      width="50%"
    >
      <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="max-height: 95vh; overflow: auto">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" auto-complete="off" :disabled="upOrInsert === 'up'"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="upOrInsert !== 'up'">
          <el-input
            type="password"
            v-model="form.password"
            auto-complete="new-password"
            :disabled="upOrInsert === 'up'"
          ></el-input>
        </el-form-item>
        <!--        <el-form-item label="所属组织" prop="departmentId">-->
        <!--          <el-select v-model="form.departmentId" placeholder="请选择部门">-->
        <!--            <el-option-->
        <!--              v-for="item in optionData"-->
        <!--              :key="item.id"-->
        <!--              :label="item.department"-->
        <!--              :value="item.id"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="姓名" prop="trueName">
          <el-input v-model="form.trueName"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
            <el-radio label="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="职务" prop="job">
          <el-input v-model="form.job"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="department">
          <el-input v-model="form.department"></el-input>
        </el-form-item>
        <el-form-item label="开通视频会商">
          <el-radio-group v-model="form.extendFlag">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户角色" prop="chooseRole">
          <template>
            <el-transfer
              :titles="titles"
              v-model="form.chooseRole"
              @change="handleTransferChoose"
              :data="form.roleData"
            ></el-transfer>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveAdd('ruleForm')">确定</el-button>
        <el-button type="primary" plain @click.native="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑界面-->
    <el-dialog
      title="用户信息"
      :visible.sync="EditdialogFormVisible"
      :modal-append-to-body="false"
      :center="dialogCenter"
      width="50%"
    >
      <el-form
        :model="form"
        :rules="editRules"
        ref="editRuleForm"
        label-width="120px"
        style="max-height: 95vh; overflow: auto"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" auto-complete="off" :disabled="upOrInsert === 'up'"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="upOrInsert !== 'up'">
          <el-input
            type="password"
            v-model="form.password"
            auto-complete="off"
            :disabled="upOrInsert === 'up'"
          ></el-input>
        </el-form-item>
        <!--        <el-form-item label="所属组织" prop="departmentId">-->
        <!--          <el-select v-model="form.departmentId" placeholder="请选择部门">-->
        <!--            <el-option-->
        <!--              v-for="item in optionData"-->
        <!--              :key="item.id"-->
        <!--              :label="item.department"-->
        <!--              :value="item.id"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="姓名" prop="trueName">
          <el-input v-model="form.trueName"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
            <el-radio label="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="职务" prop="job">
          <el-input v-model="form.job"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="department">
          <el-input v-model="form.department"></el-input>
        </el-form-item>
        <el-form-item label="开通视频会商">
          <el-radio-group v-model="form.extendFlag">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户角色" prop="chooseRole">
          <template>
            <el-transfer
              :titles="titles"
              v-model="form.chooseRole"
              @change="handleTransferChoose"
              :data="form.roleData"
            ></el-transfer>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveEdit('editRuleForm')">确定</el-button>
        <el-button type="primary" plain @click.native="EditdialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--修改密码-->
    <el-dialog
      title="修改密码"
      :visible.sync="dialogPasswordVisible"
      :modal-append-to-body="false"
      :center="dialogCenter"
      width="750px"
    >
      <el-form :model="pform" status-icon :rules="pwdrules" ref="pform" label-width="120px">
        <el-form-item label="新密码" prop="password">
          <el-input v-model="pform.password" placeholder="输入新密码" auto-complete="off" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input v-model="pform.checkPass" placeholder="再次输入新密码" type="password" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="savePwd">确定</el-button>
        <el-button type="primary" plain @click="dialogPasswordVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import axios from 'axios'
import $ from 'jquery'
import 'ztree'
import '../../../libs/jquery.ztree.exhide.min'
import { Encrypt } from '../../../utils/crypto'
import Source from './Source'
import WareHouse from './WareHouse'

export default {
  name: 'userManage',
  components: {
    Source,
    WareHouse
  },
  data() {
    var self = this
    var usernameCheck = function (rule, value, callback) {
      var specificReg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_]")
      if (specificReg.test(value)) {
        callback(new Error('用户名不能包含特殊字符'))
      } else {
        if (self.upOrInsert === 'up') {
          callback()
          return
        }
        var url = '/gtw/EIMS-management/user?userName=' + value + '&operationUserid=' + localStorage.getItem('userId')
        axios.get(url).then(function (res) {
          if (res.data.data.length !== 0) {
            callback(new Error('用户名已注册'))
          } else {
            callback()
          }
        })
      }
    }
    var validateOldpass = function (rule, value, callback) {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (self.pform.opassword !== Encrypt(value)) {
          callback(new Error('旧密码输入错误！'))
        }
        callback()
      }
    }

    var validatePass = function (rule, value, callback) {
      self.$refs.pform.validateField('checkPass')
      callback()
    }
    var validatePass2 = function (rule, value, callback) {
      if (value !== self.pform.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    var phoneCheck = function (rule, value, callback) {
      var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 手机号码
      var isMob = /^0?1[3|4|5|8][0-9]\d{8}$/ // 座机格式
      if (!(value === null || value === undefined || value === '')) {
        if (isMob.test(value) || isPhone.test(value)) {
          callback()
        } else {
          callback(new Error('非有效电话号码！'))
        }
      } else {
        callback()
      }
    }
    var emailCheck = function (rule, value, callback) {
      var reg = new RegExp('^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$')
      if (!(value === null || value === undefined || value === '')) {
        if (reg.test(value)) {
          callback()
        } else {
          callback(new Error('非有效邮箱格式！'))
        }
      } else {
        callback()
      }
    }
    const passwordCheck = (rule, value, callback) => {
      var passwordReg =
        /^(((?=.*[0-9])(?=.*[a-zA-Z])|(?=.*[0-9])(?=.*[^\s0-9a-zA-Z])|(?=.*[a-zA-Z])(?=.*[^\s0-9a-zA-Z]))[^\s]+)$/
      if (!passwordReg.test(value)) {
        callback(new Error('密码至少含有字母、数字、特殊字符中的两种'))
      } else {
        callback()
      }
    }
    const roleCheck = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('您未选择角色'))
      } else if (value.length > 1) {
        callback(new Error('您只能选择一个角色'))
      } else {
        callback()
      }
    }
    return {
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      userRoleId: localStorage.getItem('roleId'),
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      listLoading: false,
      titles: ['备选项', '已选项'],
      userData: [],
      currentRow: null,
      selectionIds: [],
      dataIs: false,
      precinctNameList: {},
      currentPage: 1,
      pageSize: 12, // 每页显示几条
      dialogPasswordVisible: false,
      dialogFormVisible: false,
      EditdialogFormVisible: false,
      dialogCenter: false,
      closeOnClickModal: false,
      upOrInsert: null,
      searchInfo: '',
      precinctId: '',
      treeObj: '',
      btnVisible: false,
      keyword: '',
      nodeList: [],
      form: {
        userId: '',
        password: '',
        userName: '',
        trueName: '',
        phone: '',
        email: '',
        address: '',
        gender: '2',
        extendFlag: '0',
        department: '',
        job: '',
        // departmentId: 33,
        roleData: [], // 所有角色
        chooseRole: [], // 选中的角色
        addRole: [], // 新增角色
        removeRole: [] // 删除角色
      },
      isClearable: true, // 可清空（可选）
      isAccordion: true, // 可收起（可选）,
      optionData: [],
      // 用于修改用户的时候临时存放原来的值，以比较哪个字段有变化，操作日志里要显示
      editUserTemp: {},
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 1,
            max: 20,
            message: '长度在 1 到 20 个字符',
            trigger: 'blur'
          },
          { validator: usernameCheck, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码为至少8位数', trigger: 'blur' },
          { validator: passwordCheck, trigger: 'blur' }
        ],
        trueName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        // departmentId: [
        //   { required: true, message: '请选择部门', trigger: 'change' }
        // ],
        phone: [{ validator: phoneCheck, trigger: 'blur' }],
        email: [{ validator: emailCheck, trigger: 'blur' }],
        chooseRole: [
          {
            required: true,
            validator: roleCheck,
            trigger: 'change'
          }
        ],
        streetId: ''
      },
      editRules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 1,
            max: 20,
            message: '长度在 1 到 20 个字符',
            trigger: 'blur'
          },
          { validator: usernameCheck, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码为至少8位数', trigger: 'blur' },
          { validator: passwordCheck, trigger: 'blur' }
        ],
        trueName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        phone: [{ validator: phoneCheck, trigger: 'blur' }],
        email: [{ validator: emailCheck, trigger: 'blur' }],
        chooseRole: [
          {
            required: true,
            validator: roleCheck,
            trigger: 'change'
          }
        ]
      },
      pform: { password: '', checkPass: '' }, // 重置密码
      pwdrules: {
        oldpassword: [{ validator: validateOldpass, trigger: 'blur' }],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码为至少8位数', trigger: 'blur' },
          { validator: passwordCheck, trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      dialogSourceVisible: false,
      dialogWareVisible: false,
      dialogSourceInfo: null
    }
  },
  methods: {
    checkPage(v) {
      // console.log(this.userData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize))
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
      if (trimtext === '') {
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
        self.btnVisible = treeNode.precinctKind === '1'
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
      axios
        .get('/gtw/EIMS-management/regional')
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
          if (self.precinctId == null || self.precinctId === undefined || self.precinctId === '') {
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
    checkSelectable(row) {
      return row.userName !== 'admin'
    },
    getDeparmentList() {
      let url = '/gtw/EIMS-management/getCommitteeVilageTree'
      axios.get(url).then((r) => {
        let data = r.data.data
        let obj = {}
        data.forEach((item) => {
          obj[item.precinctId] = item.precinctName
          item.precinctSon.forEach((i) => {
            obj[i.precinctId] = i.precinctName
          })
        })
        this.precinctNameList = obj
        // console.log(this.precinctNameList)
      })
    },
    queryRoles(v) {
      var self = this
      var url = '/gtw/EIMS-management/role/query'
      axios.get(url).then(function (res) {
        self.form.roleData = []
        var roles = res.data.data
        roles.forEach(function (role, index) {
          self.form.roleData.push({
            label: role.roleName,
            key: role.roleId
          })
        })
        if (v && v.length !== 0) {
          v.forEach(function (role, index) {
            self.form.chooseRole.push(role.roleId)
          })
        }
      })
    },
    queryGroup() {
      var self = this
      var url = '/gtw/EIMS-management/queryDepartmentInfo'
      axios.get(url).then(function (res) {
        self.optionData = res.data.data
      })
    },
    query() {
      var self = this
      let precinctId = this.precinctId === '37030' ? '' : this.precinctId
      var url = `/gtw/EIMS-management/user?operationUserid=${localStorage.getItem(
        'userId'
      )}&precinctId=${precinctId}&keyword=${this.keyword}`
      self.listLoading = true
      axios.get(url).then(function (res) {
        if (res.data.result === 0) {
          self.listLoading = false
          self.userData = res.data.data
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
    addRow(rows) {
      this.upOrInsert = 'insert'
      this.queryRoles()
      this.form.userName = ''
      this.form.password = ''
      this.form.trueName = ''
      this.form.phone = ''
      this.form.email = ''
      this.form.address = ''
      this.form.gender = '2'
      this.form.extendFlag = '0'
      this.form.job = ''
      this.form.department = ''
      // this.form.departmentId = ''
      this.form.chooseRole.splice(0)
      if (this.$refs.ruleForm) {
        this.$refs.ruleForm.resetFields()
        this.$refs.ruleForm.clearValidate()
      }
      this.dialogFormVisible = true
    },
    editRow(index, row) {
      this.EditdialogFormVisible = true
      this.upOrInsert = 'up'
      this.queryRoles(row.roles)
      this.form.userId = row.userId
      this.form.userName = row.userName
      this.form.password = row.password
      this.form.trueName = row.trueName
      this.form.phone = row.phone
      this.form.email = row.email
      this.form.address = row.address
      this.form.gender = row.gender
      this.form.extendFlag = row.extendFlag || '0'
      this.form.job = row.job
      this.form.department = row.department
      // this.form.departmentId = parseInt(row.departmentId)
      this.form.chooseRole.splice(0)
      this.form.addRole.splice(0)
      this.form.removeRole.splice(0)
      if (this.$refs.editRuleForm) {
        this.$refs.editRuleForm.clearValidate()
      }
      // 保存用户修改前的值
      this.editUserTemp.trueName = this.form.trueName
      this.editUserTemp.phone = this.form.phone
      this.editUserTemp.email = this.form.email
      this.editUserTemp.address = this.form.address
      this.editUserTemp.gender = this.form.gender
      this.editUserTemp.extendFlag = this.form.extendFlag
      this.editUserTemp.job = this.form.job
      this.editUserTemp.department = this.form.department
      // this.editUserTemp.departmentId = this.form.departmentId
      // 保存修改要传这玩意
      this.streetId = row.streetId
    },
    editPassword(index, row) {
      this.dialogPasswordVisible = true
      this.pform.userName = row.userName
      this.pform.opassword = row.password
      this.pform.userId = row.userId
      if (this.$refs.pform) {
        var self = this
        self.$refs.pform.resetFields()
      }
    },
    editSource(index, row) {
      this.dialogSourceVisible = true
      this.dialogSourceInfo = row
    },
    offOnUser(index, row) {
      let status = row.userState === '0' ? '1' : '0'
      let text = row.userState === '0' ? '停用' : '启用'
      let url = `/gtw/EIMS-management/user/changeUserState?userState=${status}&userId=${row.userId}`
      axios.post(url).then((r) => {
        if (r.data.code === 200) {
          this.$message.success(text + '成功')
          this.query()
        } else {
          this.$message.error(r.data.message)
        }
      })
    },
    editWare(index, row) {
      this.dialogWareVisible = true
      this.dialogSourceInfo = row
    },
    savePwd() {
      var self = this
      this.$refs.pform.validate(function (valid) {
        if (valid) {
          self.saveUrlpwd()
        } else {
          return false
        }
      })
    },
    saveUrlpwd() {
      var url =
        '/gtw/EIMS-management/user/changePassword?type=reset&newPassword=' +
        Encrypt(this.pform.password) +
        '&userName=' +
        this.pform.userName +
        '&userId=' +
        this.pform.userId
      var self = this
      axios.put(url).then(function (res) {
        if (res.data.result === 0) {
          self.$message({
            type: 'success',
            showClose: true,
            message: '密码重置成功！',
            onClose: function () {
              self.dialogPasswordVisible = false
            }
          })
        } else {
          self.$message({
            type: 'error',
            showClose: true,
            message: res.data.remark,
            onClose: function () {
              self.dialogPasswordVisible = false
            }
          })
        }
        self.query()
      })
    },
    handleSelectionChange(rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.userId)
      })
    },
    handleTransferChoose(current, derection, changeArr) {
      var self = this
      // 新增
      if (derection === 'right') {
        var realChangArr = []
        // 新增的和在removeRole里面的抵消后剩下的，是真正新增的
        realChangArr = changeArr.filter(function (x) {
          return self.form.removeRole.indexOf(x) === -1
        })
        // removeRole去掉抵消了的
        self.form.removeRole = self.form.removeRole.filter(function (x) {
          return changeArr.indexOf(x) === -1
        })
        // addRole添加真正新增的
        self.form.addRole = self.form.addRole.concat(realChangArr)
      } else if (derection === 'left') {
        var realChangArr2 = []
        realChangArr2 = changeArr.filter(function (x) {
          return self.form.addRole.indexOf(x) === -1
        })
        self.form.addRole = self.form.addRole.filter(function (x) {
          return changeArr.indexOf(x) === -1
        })
        self.form.removeRole = self.form.removeRole.concat(realChangArr2)
      }
    },
    updateUser() {
      var self = this
      // 修改用户基本信息
      let roleIds = ''
      roleIds = self.form.chooseRole.join()
      let streetId = self.streetId
      var urlUpdateUser =
        '/gtw/EIMS-management/user?userId=' +
        self.form.userId +
        '&userName=' +
        self.form.userName +
        '&operationUserid=' +
        localStorage.getItem('userId') +
        '&roleId=' +
        roleIds +
        '&streetId=' +
        streetId
      for (var key in self.editUserTemp) {
        if (self.editUserTemp[key] !== self.form[key]) {
          urlUpdateUser += '&' + key + '=' + self.form[key]
        }
      }
      return axios.put(urlUpdateUser)
    },
    removeRole() {
      var self = this
      // 删除角色
      if (self.form.removeRole.length !== 0) {
        var removeRoleIds = ''
        self.form.removeRole.forEach(function (roleId) {
          removeRoleIds = removeRoleIds + roleId + ','
        })
        removeRoleIds = removeRoleIds.substring(0, removeRoleIds.length - 1)
        var urlDelUserRoles = '/gtw/EIMS-management/userRole?userId=' + self.form.userId + '&roleIds=' + removeRoleIds
        return axios.delete(urlDelUserRoles)
      }
    },
    addRole() {
      var self = this
      // 添加角色
      if (self.form.addRole.length !== 0) {
        var addRoleIds = ''
        self.form.addRole.forEach(function (roleId) {
          addRoleIds = addRoleIds + roleId + ','
        })
        addRoleIds = addRoleIds.substring(0, addRoleIds.length - 1)
        var urlAddUserRoles = '/gtw/EIMS-management/userRole?userId=' + self.form.userId + '&roleIds=' + addRoleIds
        return axios.post(urlAddUserRoles)
      }
    },
    // 编辑保存
    saveEdit() {
      var self = this
      if (self.upOrInsert === 'up') {
        self.$refs.editRuleForm.clearValidate('usernameCheck')
        self.$refs.editRuleForm.validate(function (valid) {
          if (valid) {
            axios.all([self.updateUser()]).then(
              axios.spread(function (ret1) {
                // 当这三个请求都完成的时候会触发这个函数，两个参数分别代表返回的结果
                if (ret1.data.result === 0) {
                  self.$message({
                    type: 'success',
                    showClose: true,
                    message: '编辑用户成功'
                  })
                  self.EditdialogFormVisible = false
                  self.query()
                } else {
                  self.$message({
                    type: 'error',
                    showClose: true,
                    message: ret1.data.remark,
                    onClose: function () {
                      self.EditdialogFormVisible = false
                    }
                  })
                }
              })
            )
          } else {
            return false
          }
        })
      }
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
    // 新增用于保存
    saveAdd() {
      var self = this
      self.$refs.ruleForm.validate((valid) => {
        if (valid) {
          var roleIds = ''
          self.form.chooseRole.forEach(function (roleId) {
            roleIds = roleIds + roleId + ','
          })
          roleIds = roleIds.substring(0, roleIds.length - 1)
          let streetId = self.form.userName !== 'admin' ? self.$store.getters.precinctId : '1'
          var url =
            '/gtw/EIMS-management/user?userName=' +
            self.form.userName +
            '&password=' +
            Encrypt(self.form.password) +
            '&trueName=' +
            self.form.trueName +
            '&phone=' +
            self.form.phone +
            '&email=' +
            self.form.email +
            '&address=' +
            self.form.address +
            '&gender=' +
            self.form.gender +
            // '&departmentId=' +
            // self.form.departmentId +
            '&roleId=' +
            roleIds +
            '&operationUserid=' +
            localStorage.getItem('userId') +
            '&streetId=' +
            streetId +
            '&extendFlag=' +
            self.form.extendFlag +
            '&department=' +
            self.form.department +
            '&job=' +
            self.form.job
          axios.post(url).then((res) => {
            if (res.data.result === 0) {
              self.$message({
                type: 'success',
                showClose: true,
                message: '创建用户成功'
              })
              self.dialogFormVisible = false
              self.query()
              self.$router.push('/user')
            } else {
              self.$message({
                type: 'error',
                showClose: true,
                message: res.data.remark
              })
            }
          })
        }
      })
    },
    delRow() {
      var self = this
      if (self.selectionIds.length === 0) {
        self.$message({
          type: 'info',
          showClose: true,
          message: '请先勾选删除项'
        })
        return
      }
      self
        .$confirm('此操作将删除用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(function () {
          var selectionIds = ''
          self.selectionIds.forEach(function (id) {
            selectionIds = selectionIds + id + ','
          })
          selectionIds = selectionIds.substring(0, selectionIds.length - 1)
          var url = '/gtw/EIMS-management/user?userIds=' + selectionIds
          axios.delete(url).then(function (res) {
            if (res.data.result === 0) {
              self.$message({
                type: 'success',
                showClose: true,
                message: '删除用户成功',
                onClose: function () {
                  self.dialogFormVisible = false
                }
              })
            } else {
              self.$message({
                type: 'error',
                showClose: true,
                message: '删除用户失败',
                onClose: function () {
                  self.dialogFormVisible = false
                }
              })
            }
            self.query()
          })
        })
    }
  },
  mounted() {
    this.query()
    this.queryRoles()
    this.queryGroup()
    this.getDeparmentList()
    this.$nextTick(() => {
      this.initTree()
    })
  }
}
</script>

<style lang="scss">
.user-page {
  width: 100%;
  padding: 20px;
  background: #f8f9ff;
  display: flex;
  flex-direction: column;
  .topContent {
    margin-left: 360px;
  }
  .content-show-left-panel {
    width: 450px;
    margin-top: -70px;
    padding: 10px;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
  }
  .content-show-right-panel {
    width: 100%;
    margin: 0px 0 0 10px;
  }
  .heightCell {
    height: 70px;
    overflow: scroll;
  }
  .tree {
    margin-top: 12px;
    height: 770px;
    /*overflow: auto;*/
    text-align: center;
    .ztree * {
      /* font-size: 16px;*/
      /*       color: #666666;*/
    }
    .ztree li a:hover .znv-tree-edit,
    .ztree li .curSelectedNode .znv-tree-edit {
      display: inline-block;
      color: #fff;
    }
    .ztree01 li {
      line-height: 30px;
    }
  }
}
</style>
