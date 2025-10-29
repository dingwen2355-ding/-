<template>
  <el-dialog
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose"
    :close-on-click-modal="false"
    title="新增预警"
    :visible.sync="dialogFormVisible"
  >
    <el-form class="addForm" ref="form" :rules="rules" :model="form" inline label-width="120px">
      <el-form-item label="预警标题" prop="title" class="whole-line">
        <el-input v-model="form.title" placeholder="请输入预警标题"></el-input>
      </el-form-item>
      <el-form-item label="发布时间" prop="date" class="whole-line">
        <el-date-picker style="width:100%" v-model="form.date" type="datetime" placeholder="选择日期时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="预警类别" prop="type" class="whole-line">
        <el-cascader
          style="width:100%"
          v-model="form.type"
          :options="typeList"
          :props="{ checkStrictly: true }"
          @change="handleChange"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="预警级别" prop="level" class="whole-line">
        <el-select style="width:100%" v-model="form.level" placeholder="请选择">
          <el-option v-for="item in levelList" :key="item" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预警内容" prop="content" class="whole-line">
        <el-input v-model="form.content" type="textarea" :rows="5" placeholder="请输入预警内容"></el-input>
      </el-form-item>
      <el-form-item label="影响范围" prop="area" class="whole-line">
        <el-input v-model="form.area" type="textarea" :rows="5" placeholder="请输入影响范围"></el-input>
      </el-form-item>
      <el-form-item label="防御建议" prop="advice" class="whole-line">
        <el-input v-model="form.advice" type="textarea" :rows="5" placeholder="请输入防御建议"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="unset">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
export default {
  props: ['dialogFormVisible'],
  data() {
    return {
      form: {
        title: '',
        date: '',
        type: '',
        content: '',
        level: '',
        area: '',
        advice: ''
      },
      levelList: ['红色预警', '橙色预警', '黄色预警', '蓝色预警'],
      typeList: [
        {
          label: '',
          value: '',
          children: []
        }
      ],
      rules: {
        date: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        type: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请填写内容', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请填写标题', trigger: 'change' }
        ]
      }
    }
  },
  watch: {
    dialogFormVisible(v) {
      if (v) {
        this.resetForm()
        this.form.date = new Date()
        this.getTypeList()
      }
    }
  },
  methods: {
    getTypeList() {
      this.typeList = [
        {
          "label": "自然灾害",
          "value": "自然灾害",
          "children": [
            {
              "label": "防汛抗旱",
              "value": "防汛抗旱"
            },
            {
              "label": "森林防火",
              "value": "森林防火"
            },
            {
              "label": "抗震救灾",
              "value": "抗震救灾"
            },
            {
              "label": "其它",
              "value": "其它"
            }
          ]
        },
        {
          "label": "事故灾难",
          "value": "事故灾难",
          "children": [
            {
              "label": "矿山救援",
              "value": "矿山救援"
            },
            {
              "label": "危化品救援",
              "value": "危化品救援"
            },
            {
              "label": "消防",
              "value": "消防"
            },
            {
              "label": "其它",
              "value": "其它"
            }
          ]
        },
        {
          "label": "气象预警",
          "value": "气象预警"
        }
      ]
      // let url = '/gtw/EIMS-management/event/queryEventType'
      // axios.get(url).then(r => {
      //   let data = r.data.data || []
      //   this.typeList = this.changeTree(data)
      //   console.log(this.typeList)
      // })
    },
    resetForm() {
      this.form = {
        title: '',
        date: '',
        type: '',
        content: '',
        level: '',
        area: '',
        advice: ''
      }
    },
    changeTree(data) {
      let arr = []
      if (typeof (data) === 'object') {
        for (let key in data) {
          let obj = {
            label: key,
            value: key,
            children: []
          }
          data[key].forEach(item => {
            let obj2 = {
              label: item,
              value: item
            }
            if (item) {
              obj.children.push(obj2)
            }
          })
          arr.push(obj)
        }
      }
      return arr
    },
    handleClose() {
      this.$emit("update:dialogFormVisible", false)
    },
    handleChange(v) {
      console.log(v)
    },
    unset() {
      this.$emit("update:dialogFormVisible", false)
    },
    submit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let param = {
            warningContent: this.form.content || null,
            warningTitle: this.form.title || null,
            warningType1: this.form.type[0] || null,
            warningType2: this.form.type[1] || null,
            releaseTime: this.changeDate(this.form.date) || null,
            warningLevel: this.form.level || null,
            warningInfluence: this.form.area || null,
            defenseAdvice: this.form.advice || null
          }
          let url = '/gtw/EIMS-management/commandWarning/insertWarning'
          axios.post(url, param).then(r => {
            console.log(r.data.data)
            this.$emit("success", r.data.data)
            this.$emit("update:dialogFormVisible", false)
          })
        }
      })
    },
    changeDate(time) {
      let year = time.getFullYear()
      let mounth = (time.getMonth() + 1) < 10 ? `0${(time.getMonth() + 1)}` : (time.getMonth() + 1)
      let day = time.getDate() < 10 ? `0${time.getDate()}` : time.getDate()
      let hour = time.getHours() < 10 ? `0${time.getHours()}` : time.getHours()
      let min = time.getMinutes() < 10 ? `0${time.getMinutes()}` : time.getMinutes()
      let second = time.getSeconds() < 10 ? `0${time.getSeconds()}` : time.getSeconds()
      let result = `${year}-${mounth}-${day} ${hour}:${min}:${second}`
      return result
    }
  }
}
</script>

<style lang="scss">
.addForm {
  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 574px;
    }
  }
}
</style>
