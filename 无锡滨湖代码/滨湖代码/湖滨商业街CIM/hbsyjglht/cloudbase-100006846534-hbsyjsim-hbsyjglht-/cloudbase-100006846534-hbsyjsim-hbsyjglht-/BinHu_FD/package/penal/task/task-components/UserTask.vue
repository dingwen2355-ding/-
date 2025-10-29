<template>
  <div style="margin-top: 16px">
    <el-form-item label="处理用户">
      <el-input v-model="userTaskForm.assignee" @change="updateElementTask('assignee')"></el-input>
      <!-- <el-select
        v-model="userTaskForm.assignee"
        @change="updateElementTask('assignee')"
        @focus="getUserList"
        clearable
        filterable
        remote
        :remote-method="remoteMethod"
      >
        <el-option v-for="item in userList" :key="item.id" :label="item.realname" :value="item.username" />
      </el-select> -->
    </el-form-item>
    <el-form-item label="候选用户">
      <el-input v-model="userTaskForm.candidateUsers" @change="updateElementTask('candidateUsers')"></el-input>
      <!-- <el-select v-model="userTaskForm.candidateUsers" multiple collapse-tags
        @change="updateElementTask('candidateUsers')" @focus="getUserList" clearable filterable remote
        :remote-method="remoteMethod">
        <el-option v-for="item in userList" :key="item.id" :label="item.realname" :value="item.username" />
      </el-select> -->
    </el-form-item>
    <el-form-item label="候选分组">
      <el-input v-model="userTaskForm.candidateGroups" @change="updateElementTask('candidateGroups')"></el-input>
      <!-- <el-select v-model="userTaskForm.candidateGroups" multiple collapse-tags
        @change="updateElementTask('candidateGroups')" @focus="getRoleList" clearable>
        <el-option v-for="role in roleList" :key="role.id" :label="role.roleName" :value="role.roleCode" />
      </el-select> -->
    </el-form-item>
    <el-form-item label="到期时间">
      <el-input v-model="userTaskForm.dueDate" clearable @change="updateElementTask('dueDate')" />
    </el-form-item>
    <el-form-item label="跟踪时间">
      <el-input v-model="userTaskForm.followUpDate" clearable @change="updateElementTask('followUpDate')" />
    </el-form-item>
    <el-form-item label="优先级">
      <el-input v-model="userTaskForm.priority" clearable @change="updateElementTask('priority')" />
    </el-form-item>
  </div>
</template>

<script>
/* eslint-disable */

import axios from 'axios'
import { getAction, postAction } from '@/api/manage'
export default {
  name: 'UserTask',
  props: {
    id: String,
    type: String
  },
  data() {
    return {
      defaultTaskForm: {
        assignee: '',
        candidateUsers: '',
        candidateGroups: '',
        dueDate: '',
        followUpDate: '',
        priority: ''
      },
      userTaskForm: {},
      // todo 用户数据修改 username
      userList: [],
      roleList: [],
      url: {
        getSysUserList: '/v1/ActivitiDesignController/getSysUserList',
        getRoleList: '/v1/ActivitiDesignController/getRoleList'
      }
    }
  },
  watch: {
    id: {
      immediate: true,
      handler() {
        this.bpmnElement = window.bpmnInstances.bpmnElement
        this.$nextTick(() => this.resetTaskForm())
      }
    }
  },
  methods: {
    //用户姓名模糊查询
    remoteMethod(query) {
      console.log(query, 'query')
      // let token = this.getQueryString('token')
      // if (token === null) return
      let url = this.requestUrl + this.url.getSysUserList
      console.log(url)
      getAction(url + '?realName=' + query).then(res => {
        if (res.code == 200) {
          this.userList = res.result
        }
      })
    },
    //获取角色列表
    getRoleList() {
      // let token = this.getQueryString('token')
      // if (token === null) return

      let url = this.requestUrl + this.url.getRoleList
      getAction(url).then(res => {
        if (res.code == 200) {
          this.roleList = res.result
        }
      })
    },
    //获取用户列表
    getUserList() {
      // let token = this.getQueryString('token')
      // if (token === null) return
      let url = this.requestUrl + this.url.getSysUserList
      console.log(url)
      getAction(url).then(res => {
        if (res.code == 200) {
          this.userList = res.result
        }
      })
    },
    resetTaskForm() {
      for (let key in this.defaultTaskForm) {
        let value
        // if (key === 'candidateUsers' || key === 'candidateGroups') {
        //   value = this.bpmnElement?.businessObject[key] ? this.bpmnElement.businessObject[key].split(',') : []
        // } else {
        value = this.bpmnElement?.businessObject[key] || this.defaultTaskForm[key]
        // }
        this.$set(this.userTaskForm, key, value)
        console.log(this.userTaskForm)
      }
    },
    updateElementTask(key) {
      const taskAttr = Object.create(null)
      // if (key === 'candidateUsers' || key === 'candidateGroups') {
      //   taskAttr[key] = this.userTaskForm[key] && this.userTaskForm[key].length ? this.userTaskForm[key].join() : null
      // } else {
      taskAttr[key] = this.userTaskForm[key] || null
      // }
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, taskAttr)
    }
  },
  beforeDestroy() {
    this.bpmnElement = null
  }
}
</script>
