<template>
  <iframe :id="id" :src="url" frameborder="0" width="100%" height="800px" scrolling="auto"></iframe>
</template>

<script>
import { axios } from '@/utils/request'

export default {
  name: 'EpidemicPageView',
  inject: ['closeCurrent'],
  data() {
    return {
      url: '',
      id: '',
    }
  },
  created() {
    this.goUrl()
  },
  updated() {
    this.goUrl()
  },
  watch: {
    $route(to, from) {
      this.goUrl()
    },
  },
  methods: {
    goUrl() {
      const service = this.$route.path.substring(this.$route.path.lastIndexOf('/'))
      axios({
        baseURL: '/epidemic',
        url: '/login/isc',
        method: 'get',
        params: {
          param: `https://192.168.10.2:443/gptas${service}`,
        },
      }).then((res) => {
        let url = res.url
        let id = this.$route.path
        this.id = id
        console.log('------url------' + url)
        if (url !== null && url !== undefined) {
          // this.url = url
          /*update_begin  for:判断打开方式，新窗口打开时this.$route.meta.internalOrExternal==true */
          if (this.$route.meta.internalOrExternal != undefined && this.$route.meta.internalOrExternal == true) {
            this.closeCurrent()
            window.open(url, '_blank', 'noreferrer')
          }
          /*update_end  for:判断打开方式，新窗口打开时this.$route.meta.internalOrExternal==true */
        }
      })
    },
  },
}
</script>

<style></style>
