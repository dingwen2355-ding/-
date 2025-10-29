<template>
  <div class="Right2 module">
    <Right2To1 />
    <Right2To2 />
    <Right2To3 />
    <!-- <Right2To4 /> -->
  </div>
</template>

<script>
// import SmallTitle from '@/components/SmallTitle.vue'
import Right2To1 from './Right2To1.vue'
import Right2To2 from './Right2To2.vue'
import Right2To3 from './Right2To3.vue'
// import Right2To4 from './Right2To4_demo.vue'
// import Right2To4 from './Right2To4.vue'
export default {
  name: 'Right2',
  components: { Right2To1, Right2To2, Right2To3 },
  // components: { Right2To1 },
  data() {
    return {}
  },
  mounted() {},
  methods: {}
}
</script>

<style lang="scss">
.Right2 {
  width: 2268rem;
  // height: 896rem;
  position: absolute;
  top: 430rem;
  right: 1554rem;
}
</style>
