<template>
  <div class="user-page">
    <el-form class="znv-el-form" ref="form" :inline="true" :model="search" style="margin-top: 0">
      <el-form-item label="所属组织" style="margin-bottom: 0">
        <!-- <el-input placeholder="请输入所属社区" clearable v-model="search.departmentName"></el-input> -->
        <el-cascader
          :options="dealDeptList"
          clearable
          :props="dealDeptProps"
          v-model="search.precinctId"
          placeholder="请选择所属组织"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="角色" style="margin-bottom: 0">
        <el-select v-model="search.roleId" clearable placeholder="请选择">
          <el-option v-for="item in form.roleData" :key="item.label" :label="item.label" :value="item.key"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" style="margin-bottom: 0">
        <el-input placeholder="请输入姓名" clearable v-model="search.trueName"></el-input>
      </el-form-item>

      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" @click="querySerch()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-card class="box-card" style="margin-top: 20px; height: calc(100% - 100px)">
      <el-col :span="24" style="padding: 20px 0">
        <el-button type="primary" icon="el-icon-plus" @click="addRow">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" @click.prevent="delRow">删除</el-button>
      </el-col>
      <el-table
        :data="userData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        highlight-current-row
        v-loading="listLoading"
        @selection-change="handleSelectionChange"
        height="560px"
        border
        stripe
      >
        <el-table-column type="selection" width="80" :selectable="checkSelectable"></el-table-column>
        <el-table-column prop="userName" label="用户名"></el-table-column>
        <el-table-column prop="trueName" label="姓名"></el-table-column>
        <el-table-column prop="phone" label="手机"></el-table-column>
        <el-table-column prop="departmentName" label="所属组织"></el-table-column>
        <el-table-column prop="roleName" label="用户角色"></el-table-column>
        <el-table-column prop="userState" label="用户状态">
          <template slot-scope="scope">
            <span v-if="scope.row.userState === '0'">正常</span>
            <span v-if="scope.row.userState === '1'">已锁定</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="270">
          <template slot-scope="scope">
            <!-- :disabled="scope.row.roleName === '超级管理员'" -->
            <!-- v-if="userRoleId === '1' || userRoleId === '2'" -->
            <el-button type="text" size="medium" @click="editSource(scope.$index, scope.row)">组织管理</el-button>
            <el-button type="text" size="medium" @click="editRow(scope.$index, scope.row)">编辑</el-button>
            <el-button type="text" size="medium" @click="editPassword(scope.$index, scope.row)">修改密码</el-button>
            <el-button type="text" size="medium" @click="Unlock(scope.row)" v-if="scope.row.userState === '1'">解锁</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span
              class="total-number"
            >{{ total }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, sizes, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </div>
    </el-card>
    <!-- 资源管理 -->
    <Source
      v-if="dialogSourceVisible"
      @success="handleSuccess"
      @cancel="dialogSourceVisible = false"
      :dialogSourceInfo="dialogSourceInfo"
      :dialogSourceVisible="dialogSourceVisible"
    />
    <!--新增界面-->
    <el-dialog
      title="用户信息"
      :visible.sync="dialogFormVisible"
      :modal-append-to-body="false"
      :center="dialogCenter"
      width="50%"
    >
      <el-form :model="form" :rules="rules" ref="ruleForm" label-width="120px" style="max-height: 90vh; overflow: auto">
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
        <!--        <el-form-item label="所属社区" prop="departmentId">-->
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
        style="max-height: 90vh; overflow: auto"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" auto-complete="off" :disabled="upOrInsert === 'up'"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="upOrInsert !== 'up'">
          <el-input type="password" v-model="form.password" auto-complete="off" :disabled="upOrInsert === 'up'"></el-input>
        </el-form-item>
        <!--        <el-form-item label="所属社区" prop="departmentId">-->
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
import axios from '@/utils/request.js'
import { Encrypt } from '../../utils/crypto'
import Source from './Source'

export default {
  name: 'userManage',
  components: { Source },
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
        var url = '/auth/user?userName=' + value + '&operationUserid=' + sessionStorage.getItem('userId')
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
      var isPhone = /^(1[3456789]\d{9})$/ // 手机号码
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
      search: {
        trueName: '',
        precinctId: [],
        roleId: ''
      },
      menus: configs.menus,
      menuIds: JSON.parse(sessionStorage.getItem('menuIds')),
      userRoleId: sessionStorage.getItem('roleId'),
      listLoading: false,
      titles: ['备选项', '已选项'],
      userData: [],
      currentRow: null,
      selectionIds: [],
      currentPage: 1,
      pageSize: 10, // 每页显示几条
      total: 0,
      dialogPasswordVisible: false,
      dialogFormVisible: false,
      EditdialogFormVisible: false,
      dialogCenter: false,
      closeOnClickModal: false,
      upOrInsert: null,
      form: {
        userId: '',
        password: '',
        userName: '',
        trueName: '',
        phone: '',
        email: '',
        address: '',
        gender: '2',
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
          // {
          //   min: 4,
          //   max: 20,
          //   message: '长度在 4 到 20 个字符',
          //   trigger: 'blur'
          // },
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
        ],
        streetId: ''
      },
      editRules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 4,
            max: 20,
            message: '长度在 4 到 20 个字符',
            trigger: 'blur'
          },
          { validator: usernameCheck, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码为至少8位数', trigger: 'blur' },
          { validator: passwordCheck, trigger: 'blur' }
        ],
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
      dialogSourceInfo: null,
      dealDeptList: [],
      dealDeptProps: {
        value: 'id',
        label: 'name',
        children: 'children',
        checkStrictly: true,
        expandTrigger: 'click',
        emitPath: true
      }
    }
  },
  methods: {
    Unlock(row) {
      this.$confirm('此操作将进行解锁, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          axios.put('/auth/user/updateUserState?userId=' + row.userId).then((res) => {
            if (res.data.result === 0 && res.data.remark === 'success') {
              this.query()
              this.$notify({
                title: '成功',
                message: '解锁成功',
                type: 'success',
                duration: 1000
              })
            }
          })
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消解锁',
            duration: 1000
          })
        })
    },
    //  获取处置部门
    getDealDeptList() {
      axios
        .get(
          '/auth/v2/device/queryPrecinctTree?precinctId=' +
          sessionStorage.getItem('streetId') +
          '&userId=' +
          sessionStorage.getItem('userId')
        )
        .then((res) => {
          if (res.data.code === 200) {
            this.dealDeptList = this.getTreeData(res.data.data[0].children)
          }
        })
    },
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined
        }
      }
      return data
    },
    handleSuccess() {
      this.query()
    },
    querySerch() {
      this.query()
    },
    checkSelectable(row) {
      return row.userName !== 'admin'
    },
    queryRoles(v) {
      var self = this
      var url = '/auth/role/query'
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
      var url = '/auth/queryDepartmentInfo'
      axios.get(url).then(function (res) {
        self.optionData = res.data.data
      })
    },
    query() {
      var self = this
      let params = {
        operationUserid: sessionStorage.getItem('userId')
      }
      if (this.search.trueName) {
        params.trueName = this.search.trueName
      }
      if (this.search.precinctId && this.search.precinctId.length > 0) {
        params.precinctId = this.search.precinctId[this.search.precinctId.length - 1]
      }
      if (this.search.roleId) {
        params.roleId = this.search.roleId
      }
      var url = '/auth/user'
      self.listLoading = true
      axios.get(url, { params }).then(function (res) {
        self.listLoading = false
        self.userData = res.data.data
        self.total = self.userData.length
      })
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
        '/auth/user/changePassword?type=reset&newPassword=' +
        Encrypt(this.pform.password) +
        '&userName=' +
        this.pform.userName +
        '&userId=' +
        this.pform.userId
      var self = this
      axios.put(url).then(function (res) {
        if (res.data.result === 0) {
          self.$notify({
            title: '成功',
            message: '密码重置成功！',
            type: 'success',
            onClose: function () {
              self.dialogPasswordVisible = false
            },
            duration: 1000
          })
        } else {
          self.$notify.error({
            title: '错误',
            message: res.data.remark,
            onClose: function () {
              self.dialogPasswordVisible = false
            },
            duration: 1000
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
        '/auth/user?userId=' +
        self.form.userId +
        '&userName=' +
        self.form.userName +
        '&operationUserid=' +
        sessionStorage.getItem('userId') +
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
        var urlDelUserRoles = '/auth/userRole?userId=' + self.form.userId + '&roleIds=' + removeRoleIds
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
        var urlAddUserRoles = '/auth/userRole?userId=' + self.form.userId + '&roleIds=' + addRoleIds
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
            let roleIds = ''
            roleIds = self.form.chooseRole.join()
            let streetId = self.streetId
            var urlUpdateUser =
              '/auth/user?userId=' +
              self.form.userId +
              '&userName=' +
              self.form.userName +
              '&operationUserid=' +
              sessionStorage.getItem('userId') +
              '&roleId=' +
              roleIds +
              '&streetId=' +
              streetId
            for (var key in self.editUserTemp) {
              if (self.editUserTemp[key] !== self.form[key]) {
                urlUpdateUser += '&' + key + '=' + self.form[key]
              }
            }
            axios.put(urlUpdateUser).then((res) => {
              if (res.data.result === 0) {
                self.$notify({
                  title: '成功',
                  message: '编辑用户成功',
                  type: 'success',
                  duration: 1000
                })
                self.EditdialogFormVisible = false
                self.query()
              } else {
                self.$notify.error({
                  title: '错误',
                  message: res.data.remark,
                  onClose: function () {
                    self.EditdialogFormVisible = false
                  },
                  duration: 1000
                })
              }
            })
            // axios.all([self.updateUser()]).then(
            //   axios.spread(function (ret1) {
            //     console.log(ret1, 'ret1ret1ret1ret1')
            //     // 当这三个请求都完成的时候会触发这个函数，两个参数分别代表返回的结果
            //     if (ret1.data.result === 0) {
            //       self.$notify({
            //         title: '成功',
            //         message: '编辑用户成功',
            //         type: 'success',
            //         duration: 1000
            //       })
            //       self.EditdialogFormVisible = false
            //       self.query()
            //     } else {
            //       self.$notify.error({
            //         title: '错误',
            //         message: ret1.data.remark,
            //         onClose: function () {
            //           self.EditdialogFormVisible = false
            //         },
            //         duration: 1000
            //       })
            //     }
            //   })
            // )
          } else {
            return false
          }
        })
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
            '/auth/user?userName=' +
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
            sessionStorage.getItem('userId') +
            '&streetId=' +
            streetId
          axios.post(url).then((res) => {
            if (res.data.result === 0) {
              self.$notify({
                title: '成功',
                message: '创建用户成功',
                type: 'success',
                duration: 1000
              })
              self.dialogFormVisible = false
              self.query()
              self.$router.push('/user')
            } else {
              self.$notify.error({
                title: '错误',
                message: res.data.remark,
                duration: 1000
              })
            }
          })
        }
      })
    },
    delRow() {
      var self = this
      if (self.selectionIds.length === 0) {
        this.$notify.info({
          title: '消息',
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
          var url = '/auth/user?userIds=' + selectionIds
          axios.delete(url).then(function (res) {
            if (res.data.result === 0) {
              self.$notify({
                title: '成功',
                message: '删除用户成功',
                type: 'success',
                onClose: function () {
                  self.dialogFormVisible = false
                },
                duration: 1000
              })
            } else {
              self.$notify.error({
                title: '错误',
                message: '删除用户失败',
                onClose: function () {
                  self.dialogFormVisible = false
                },
                duration: 1000
              })
            }
            self.query()
          })
        })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    }
  },
  mounted() {
    this.getDealDeptList()
    this.query()
    this.queryRoles()
    this.queryGroup()
  }
}
</script>

<style lang="scss">
.user-page {
  width: 100%;
  height: 100%;
  // padding: 20px;
  background: #f8f9ff;
}
</style>
