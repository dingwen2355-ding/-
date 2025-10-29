<template>
  <div class="tagNav" ref="tags">
    <el-tag
      type="info"
      class="tagItem"
      :class="{ active: tag.path === $route.path }"
      :key="tag.path"
      v-for="(tag, index) in tags"
      :closable="tags.length === 1 ? false : true"
      @click="clickHandel(tag, index)"
      @close="closeHandel(tag, index)"
    >
      {{ tag.name }}
    </el-tag>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentName: '',
      tags: []
    }
  },
  mounted() {
    this.$root.eventBus.$on('openTag', (e) => {
      let flag = this.tags.some((tag) => tag.path === e.path)
      if (!flag) {
        this.tags.push(e)
      }
    })
  },
  methods: {
    clickHandel(e) {
      this.$router.push(e.path)
    },
    closeHandel(e, index) {
      this.tags.splice(index, 1)
      this.clickHandel(this.tags[this.tags.length - 1])
    }
  }
}
</script>

<style lang="scss">
.tagNav {
  width: 500px;
  display: flex;
  margin: 10px 5px;
  position: absolute;
  left: 310px;
  .tagItem {
    cursor: pointer;
    margin: 0 4px;
  }
  .active {
    color: rgb(0, 147, 255) !important;
  }
}
</style>
