<template>
  <el-dialog
    title="新增指令"
    :visible.sync="dialogVisible"
    :modal="false"
    height="570rem"
    class="showViewDialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div>
      <div class="form-container" style="margin-top: 20rem">
        <el-form ref="form" :model="form" label-width="100rem">
          <el-form-item label="下发部门" style="margin-bottom: 15rem">
            <el-input v-model="form.depart1" disabled></el-input>
            <div style="display: flex; margin-top: 12rem">
              <div style="position: absolute; left: -90rem; font-size: 20rem; color: #fff">部门搜索</div>
              <el-cascader
                v-model="form.depart2"
                :show-all-levels="false"
                filterable
                :options="options"
                :props="{ multiple: true, value: 'id', label: 'name' }"
                clearable
              ></el-cascader>
              <el-button
                style="
                  margin-left: 10rem;
                  border: 1rem solid #42678d;
                  background: #0d2c4c;
                  height: 40rem;
                  line-height: 9rem;
                "
                @click="sureButton"
                >确定</el-button
              >
            </div>
            <el-cascader-panel
              :options="options"
              :show-all-levels="false"
              @change="handleChange"
              ref="cascade"
              :props="{ multiple: true, value: 'id', label: 'name' }"
            ></el-cascader-panel>
          </el-form-item>
          <el-form-item label="指令内容">
            <el-input type="textarea" v-model="form.instructionContent"></el-input>
          </el-form-item>
          <el-form-item style="text-align: center; margin-left: -70rem">
            <el-button type="primary" @click="onSubmit">发送</el-button>
            <el-button @click="handleClose()">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  props: ['dialogVisibleFlag', 'depart'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.options = []
        this.form = {
          depart: [],
          depart1: '',
          depart2: '',
          instructionContent: ''
        }
        this.options = this.depart
        this.showTree = this.treeToArray(this.depart)
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        depart: [],
        depart1: '',
        instructionContent: ''
      },
      options: [],
      showTree: [],
      departs2: ''
    }
  },
  methods: {
    sureButton() {
      if (this.form.depart2.length > 0) {
        let arr = []
        if (this.form.depart1) {
          this.form.depart2.forEach((res) => {
            let acc = res.pop()
            this.showTree.forEach((item) => {
              if (acc === item.id) {
                arr.push(item.name)
              }
            })
          })
          this.form.depart1 = this.unique(this.form.depart1 + ',' + arr.join(','))
          this.departs2 = JSON.parse(JSON.stringify(this.form.depart1))
        } else {
          this.handleChange(this.form.depart2)
        }
        this.form.depart2 = []
      }
    },
    handleChange(val) {
      let arr = []
      // this.form.depart = []
      let data = JSON.parse(JSON.stringify(val))
      data.forEach((res) => {
        let acc = res.pop()
        this.showTree.forEach((item) => {
          if (acc === item.id) {
            arr.push(item.name)
          }
        })
      })
      if (this.departs2) {
        let sae = this.departs2.split(',')
        arr.forEach((item) => {
          for (let i = 0; i < sae.length; i++) {
            if (sae[i] === item) {
              delete sae[i]
            }
          }
        })
        console.log(sae)
        let newArr = []
        sae.forEach((item) => {
          if (item) {
            newArr.push(item)
          }
        })
        this.departs2 = sae.length > 0 ? sae.join(',') : ''
        this.form.depart1 = this.unique(this.departs2 ? this.departs2 + ',' + arr.join(',') : arr.join(','))
      } else {
        this.form.depart1 = arr.join(',')
      }
      console.log(this.departs2)
      console.log(arr, val)
    },
    unique(arr) {
      let data = arr.split(',')
      let newArr = data.filter(function (item, index) {
        return data.indexOf(item) === index
      })
      return newArr.join(',')
    },
    treeToArray(arr) {
      let data = JSON.parse(JSON.stringify(arr))
      let newData = []
      const callback = (item) => {
        ;(item.children || (item.children = [])).map((v) => {
          callback(v)
        })
        delete item.children
        newData.push(item)
      }
      data.map((v) => callback(v))
      return newData
    },
    noRepeat1(arr) {
      for (var i = 0; i < arr.length - 1; i++) {
        for (var j = i + 1; j < arr.length; j++) {
          if (Number(arr[i]) === Number(arr[j])) {
            arr.splice(j, 1)
            j--
          }
        }
      }
      return arr
    },
    onSubmit() {
      console.log(this.form.depart)
      if (!this.form.depart1) {
        this.$message('请选择单位')
        return
      }
      if (!this.form.instructionContent) {
        this.$message('请输入指示内容')
        return
      }
      let ss = this.form.depart1.split(',')
      let newArr = []
      ss.forEach((item) => {
        if (item) {
          newArr.push(item)
        }
      })
      this.showTree.forEach((item) => {
        newArr.forEach((res) => {
          if (res === item.name) {
            this.form.depart.push(item.id)
          }
        })
      })
      this.form.depart = this.noRepeat1(this.form.depart)
      console.log(this.form.depart)
      let data = this.$store.getters.eventInfo
      let arr = []
      if (this.form.depart.length < 2) {
        this.form.depart.forEach((item) => {
          let obj = {
            ifHead: true,
            group: item
          }
          arr.push(obj)
        })
      } else {
        for (let i = 0; i < this.form.depart.length; i++) {
          arr.push({
            ifHead: i < 2,
            group: this.form.depart[i]
          })
        }
      }
      let departments = arr.map((item) => item.group)
      let postParam = {
        flowsource: '040',
        incomingflowno: this.$store.getters.eventId.id,
        questiontitle: this.form.instructionContent,
        desc: this.form.instructionContent,
        hadress: data.area || '',
        lng: data.gpsx || '',
        lat: data.gpsy || '',
        departments: departments.join(','),
        $class_id$: '4029',
        $urgency$: '7',
        chuzhidanwei: arr
      }
      let url = '/apis/eventJoint/drawBill'
      Axios.post(url, postParam).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          this.$message({
            message: '发送成功！',
            type: 'success'
          })
          this.$root.Bus.$emit('drawBill', true)
          this.$emit('goToview', true)
          this.handleClose()
        } else {
          this.$message({
            message: data.message,
            type: 'warning'
          })
        }
      })
    },
    handleClose() {
      this.dialogVisible = false
      this.$refs.cascade.clearCheckedNodes()
      this.$refs.cascade.syncActivePath()
      this.$refs.cascade.activePath = []
      this.$refs.cascade.checkedValue = []
      this.$refs.cascade.syncActivePath()
      this.options = []
      this.form = {
        depart: [],
        depart1: '',
        depart2: '',
        instructionContent: ''
      }
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss">
.showViewDialog {
  .el-dialog__title {
    font-size: 16rem;
  }
  .el-dialog__body {
    padding: 0 30rem 30rem;
    // height: 300rem;
    overflow: hidden;
    .el-textarea__inner {
      height: 125rem;
      color: #ffffff;
      background-color: #0d2c4c;
      border: 1rem solid #42678d;
    }
    .el-cascader-panel.is-bordered {
      margin-top: 12rem;
      margin-left: -1rem;
    }
    .el-form-item__content {
      .el-button:first-of-type {
        width: 70rem;
        height: 32rem;
        line-height: 7rem;
        border-radius: 2rem;
        background: #0d365f;
        box-sizing: border-box;
        border: 1rem solid #02a7fc;
        color: #b7e6ff;
      }
      .el-button:last-of-type {
        width: 70rem;
        height: 34rem;
        line-height: 7rem;
        border-radius: 2rem;
        background: #082239;
        box-sizing: border-box;
        border: 1rem solid #0d365f;
        color: #9ab1cb;
      }
    }
  }
  .box-2 {
    ul {
      display: flex;
      float: right;
      li {
        width: 252rem;
        span {
          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          &:nth-of-type(1) {
            color: #035561;
          }
        }
      }
    }

    .time-box {
      display: flex;
      padding: 25rem 0rem 0rem 55rem;
      .time-item {
        width: 76rem;
        // height: 28rem;
        background: rgba(61, 248, 248, 0.2);
        border: 2rem solid #035561;
        font-size: 16rem;
        line-height: 28rem;
        text-align: center;
        cursor: pointer;
      }
      .active {
        border: 2rem solid #00ffff;
        // background: linear-gradient(180deg, #28d9ff 0%, rgba(40, 217, 255, 0.2) 100%);
        background-image: radial-gradient(rgba(8, 36, 36, 0.2) 40%, rgba(89, 230, 240, 0.39));
        color: #00ffff;
      }
    }
  }
  #echart {
    width: 100%;
    height: 400rem;
  }
  .el-table th {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00ffff;
    background: #1c2331;
    height: 50rem;
  }
  .el-table td {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
    height: 67rem;
    background: #171d2b;
  }
  .el-table th.is-leaf,
  .el-table th.el-table__cell.is-leaf {
    border: 0 !important;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #171d2b !important;
  }
  .el-table,
  .el-table__expanded-cell {
    background-color: #1c2331;
  }
  .el-table th.is-leaf {
    border-top: 1rem solid #28feff;
    border-bottom: 1rem solid #28feff;
  }
  .el-table td {
    border-bottom: 1rem solid #28feff;
  }
  .el-table::before {
    height: 0;
  }
  .el-table__body-wrapper {
    height: 470rem !important;
    overflow: auto;
  }
  .historyList .el-input__inner {
    font-size: 14rem;
    color: #fff;
  }
  .historyList .el-select .el-input__inner {
    width: 140rem;
    color: #fff;
  }
  .el-form--inline .el-form-item__label {
    font-size: 16rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00efff;
  }
  .el-range-editor .el-range-input {
    background: #0c223e;
    color: #fff;
    margin-left: 8rem;
  }
  .el-date-editor .el-range-separator {
    color: #fff;
  }
  .footer {
    margin-top: 27rem;
    text-align: center;
    .el-pagination__jump {
      color: #ffffff;
    }
  }
}
</style>
