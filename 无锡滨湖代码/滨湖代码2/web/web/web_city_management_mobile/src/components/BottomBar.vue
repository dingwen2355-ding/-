<template>
  <van-tabbar v-model="value" @change="onChange" class="BottomBar">
    <van-tabbar-item :name="item.name" v-for="item in barList" :key="item.name">
      <span>{{item.label}}</span>
      <template #icon="props">
        <img :src="props.active ? item.iconActive : item.icon" />
      </template>
    </van-tabbar-item>
  </van-tabbar>
</template>

<script>
export default {
  data () {
    return {
      value: ''
    }
  },
  watch: {
    active: {
      handler () {
        this.value = this.active
      },
      immediate: true
    }
  },
  props: ['active', 'barList'],
  methods: {
    onChange (v) {
      this.$emit('update:active', v)
      this.$emit('change', v)
    }
  }
}
</script>

<style lang='scss'>
.BottomBar {
  z-index: 999;
  height: 100rem;

  .van-tabbar-item {
    font-size: 24rem;
    .van-tabbar-item__icon {
      margin-bottom: 10rem;
      font-size: 36rem;

      img {
        width: 36rem;
        height: 36rem;
      }
    }
  }
}
</style>
