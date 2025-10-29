<template>
  <div></div>
</template>

<script>
import loadFromUrl from '@/utils/fileViewer'
export default {
  mounted() {
    loadFromUrl(this.$route.query.fileUrl)
  }
}
</script>

<style></style>
