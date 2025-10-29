<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import moment from 'moment'
import { getHoliday } from '@/utils/time'
export default {
  name: 'app',
  components: {},
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {
    // 获取节假日
    getHoliday() {
      let param = {
        type: '0',
        year: moment().format('YYYY')
      }
      axios.get('/binhuapis/event/getAllHolidayDate?' + qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          console.log(res.data.data)
          getHoliday(res.data.data)
        } else {
          this.$notify({
            title: '警告',
            message: '获取节假日失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    }
  }
}

</script>

<style lang="scss">
html,
body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0 !important;
  overflow: hidden;
}
#app {
  width: 100%;
  height: 100%;
  background: #e6edf4;
  position: relative;
}
</style>
