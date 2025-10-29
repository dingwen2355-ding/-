<template>
  <div class="sub-small-title" :class="{ 'small-back': size == 'small', 'big-back': size == 'big' }">
    <span
      class="words"
      v-for="(item, index) in titleList"
      :key="index"
      :class="{ active: index === activeIndex }"
      @click="click(item, index)"
    >
      {{ item }}
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
    }
  }
}
</script>

<style scoped lang="scss">
.sub-small-title {
  width: 100%;
  height: 130rem;
  display: flex;
  align-items: center;
  margin-left: 0rem;
  margin-top: -7rem;

  .words {
    font-size: 60rem;
    font-family: SourceHanSansCN;
    font-weight: 500;
    color: #ffffff;
    // padding-bottom: 6rem;
    cursor: pointer;

    &:first-of-type {
      margin-left: 120rem;
    }
  }
  .active {
    color: rgba(229, 252, 255, 1);
  }
}
.sub-small-title.small-back {
  background: url('../assets/SmallTitle/一级标题1.png') no-repeat;
  background-size: 100% 100%;
  background-position: center center;
}
.sub-small-title.big-back {
  background: url('../assets/SmallTitle/一级标题1.png') no-repeat;
  background-size: contain;
  // background-position: center center;
}
</style>
