<!-- Created by Y on 2018-06-27 -->
<template>
  <div id="top-nav">
    <div class="left-part">
      <img src="../assets/login/logo.png" alt="" />
      <div class="title">{{ title }}</div>
    </div>
    <div class="center-part"></div>
    <div class="right-part">
      <div style="display: flex; align-items: center">
        <span style="margin-right: 10px; cursor: pointer" @click="submitSearch"><i class="el-icon-search"></i></span>
        <el-autocomplete
          class="inline-input"
          v-model="menuName"
          :fetch-suggestions="menuSearch"
          placeholder="请输入菜单"
          @select="checkMenu"
          clearable
          v-show="showSearch"
        ></el-autocomplete>
      </div>
      <div class="menu tip" v-if="todoTips.length != 0">
        <el-badge :value="todoTips.length" :max="99" class="item">
          <el-dropdown trigger="click">
            <span class="el-dropdown-link">
              <i class="el-icon-bell"></i>
            </span>
            <el-dropdown-menu slot="dropdown" style="max-height: 300px; overflow-x: hidden; overflow-y: scroll">
              <el-dropdown-item class="clearfix" v-for="(item, index) in todoTips" :key="index">
                <div
                  class="text"
                  style="max-width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"
                  @click="todoTip(item.router)"
                >
                  【{{ item.type }}】{{ item.title }}
                </div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-badge>
      </div>
      <div class="menu identity">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link"> {{ userName }}<i class="el-icon-caret-bottom el-icon--right"></i> </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item class="clearfix">
              <div class="text" @click="resetPassword">修改密码</div>
            </el-dropdown-item>
            <el-dropdown-item class="clearfix">
              <div class="text" @click="logout">注销</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <resetPwd :dialogPasswordVisible.sync="dialogPasswordVisible" />
  </div>
</template>

<script>
import axios from 'axios'
import resetPwd from '../views/SystemManagement/user/resetPwd.vue'
export default {
  name: 'TopNav',
  props: ['userName', 'userId'],
  components: { resetPwd },
  data() {
    return {
      menuName: '',
      showSearch: false,
      menuList: [],
      title: configs.beforeTitle + this.$store.getters.precinctName + configs.afterTitle,
      precinctName: this.$store.getters.precinctName,
      showLogOut: false,
      dialogPasswordVisible: false,
      todoTips: []
    }
  },

  computed: {},

  watch: {
    userId: {
      handler(v) {
        this.getTodoTip(v)
      },
      immediate: true
    }
  },

  mounted() {
    this.queryMenu()
  },

  methods: {
    submitSearch() {
      this.showSearch = true
    },
    queryMenu() {
      let list = JSON.parse(localStorage.getItem('menuList'))
      // console.log('list', list)
      let arr = []
      list.forEach((item) => {
        // console.log('list-item', item)
        if (item.path === '') {
          // console.log('list-item', item)
          arr = this.queryMenuData(item.menus, arr)
          // console.log('queryMenuData--arr1', arr)
        } else {
          let obj = {}
          obj.value = item.name
          obj.id = item.id
          obj.path = item.path
          arr = arr.concat(obj)
        }
      })
      this.menuList = arr
    },
    queryMenuData(arr, list) {
      // console.log('arr', arr)
      arr.forEach((item) => {
        let obj = {}
        obj.value = item.name
        obj.id = item.id
        obj.path = item.path
        list = list.concat(obj)
      })
      // console.log('queryMenuData---list', list)
      return list
    },
    menuSearch(queryString, cb) {
      let restaurants = this.menuList
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter(queryString) {
      return (restaurant) => {
        return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1
      }
    },
    checkMenu(v) {
      let path = v.path.split('/')
      this.$root.eventBus.$emit('openRoute', path[1])
    },
    todoTip(router) {
      this.$root.eventBus.$emit('openRoute', router)
    },
    getTodoTip(userId) {
      let url = ''
      if (userId) {
        url = `/gtw/EIMS-management/get-todo-tip?userId=${userId}`
      } else {
        url = `/gtw/EIMS-management/get-todo-tip?userId=${localStorage.getItem('userId')}`
      }
      axios.get(url).then((r) => {
        if (r.data.code === 200) {
          this.todoTips = r.data.data
        }
      })
    },
    logout() {
      localStorage.clear()
      this.$router.push('/')
    },
    resetPassword() {
      if (localStorage.user === 'admin') {
        this.$message('admin无法在此更改密码')
        return
      }
      this.dialogPasswordVisible = true
    },
    changeLogOut() {
      this.showLogOut = !this.showLogOut
    }
  }
}
</script>
<style lang='scss'>
#top-nav {
  font-family: Microsoft YaHei;
  font-weight: 400;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100%;
  padding: 0 20px;

  .left-part {
    display: flex;
    align-items: center;
    .title {
      margin-left: 20px;
      font-size: 24px;
      font-weight: bold;
    }
  }
  // .center-part {
  // }
  .right-part {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: center;
    .menu {
      margin-left: 10px;
      .el-dropdown {
        color: white;
        cursor: pointer;
      }
    }
  }
  .el-input__inner {
    background-color: rgb(1, 33, 125);
    border: none;
    color: #ffffff;
  }
}
</style>
