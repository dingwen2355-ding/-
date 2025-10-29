<template>
  <div
    class="sub-small-title"
    :class="{ 'small-back': size == 'small', 'big-back': size == 'big' }"
  >
    <span class="title-icon"></span>
    <span class="words-box">
      <span
        class="words"
        v-for="(item, index) in titleList"
        style="cursor: default"
        :key="index"
        :class="{ active: index === activeIndex }"
        :style="titleList.length>1?'cursor: pointer;':''"
        @click="click(item, index)"
      >{{ item }}</span>
    </span>
  </div>
</template>

<script>
export default {
  name: 'SmallTitle',
  props: {
    size: {
      type: String,
      default: 'small'
    },
    titleList: {
      type: Array,
      required: false,
      default: () => {
        return []
      }
    },
    activeIndex: {
      type: Number,
      required: false,
      default: 1
    }
  },
  data() {
    return {}
  },
  methods: {
    click(item, index) {
      // this.$emit('update:activeIndex', index)
      this.$emit('click', {
        activeTitle: item,
        activeIndex: index
      })
      this.activeIndex = index
    }
  }
}
</script>

<style scoped lang="scss">
.sub-small-title {
  width: 100%;
  height: 232rem;
  padding-top: 32rem;
  display: flex;
  align-items: center;
  margin-left: 0rem;
  margin-top: -7rem;
  padding-left: 54rem;
  .title-icon {
    display: inline-block;
    width: 124rem;
    height: 124rem;
    background: url('../assets/SmallTitle/菱形@2x.png') no-repeat;
    background-size: 100% 100%;
    background-position: center center;
  }
  .words {
    font-size: 96rem;
    font-family: 'YouShe';
    font-weight: 500;
    color: #ffffff;
    // padding-bottom: 6rem;
    cursor: pointer;
    margin-right: 20rem;
  }
  .words-box :not(:first-child) {
    //  words除开第一个外，其他都要加上左边框
    border-left: 20rem solid #0169d9;
    padding-left: 20rem;
  }
  .active {
    color: rgb(229, 239, 255);
  }
}
.sub-small-title.small-back {
  background: url('../assets/SmallTitle/title-2@2x.png') no-repeat;
  background-size: 100% 100%;
  background-position: center center;
}
.sub-small-title.big-back {
  background: url('../assets/SmallTitle/title_bg@2x.png') no-repeat;
  background-size: 100% 100%;
  background-position: center center;
}
</style>
