<template>
  <div class="KEditText" v-clickoutside="clickOutside">
    <div class="editing" v-if="isEditing">
      <el-input v-model="inputValue" type="textarea" :rows="2" resize="false" placeholder="请输入内容"
        @keyup.enter.native="inputValueEnter">
      </el-input>
    </div>
    <div class="default" v-else @click="isEditing = true">{{value || '暂无'}}</div>
  </div>
</template>

<script>
export default {
  name: 'KEditText',
  components: {},
  props: ['value'],
  data () {
    return {
      isEditing: false,
      inputValue: ''
    }
  },
  mounted () {
    this.inputValue = this.value
  },
  methods: {
    inputValueEnter () {
      this.isEditing = false
      this.$emit('input', this.inputValue.substring(0, this.inputValue.length - 1))
    },
    clickOutside () {
      if(this.isEditing){
      this.isEditing = false
      this.$emit('input', this.inputValue)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.KEditText {
  position: relative;
  .editing {
  }
  .default {
  }
}
</style>
