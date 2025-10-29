<template>
  <div class="avue-logo">
    <transition name="fade">
      <span v-if="keyCollapse" class="avue-logo_subtitle" key="0">
        <img
          :src="websitConfig.logo ? websitConfig.logo : '/img/bg/logo.png'"
        />
      </span>
    </transition>
    <transition-group name="fade">
      <template v-if="!keyCollapse">
        <span class="avue-logo_title" key="1">
          <div>
            <img
              :src="websitConfig.logo ? websitConfig.logo : '/img/bg/logo.png'"
            />
          </div>
          <div style="font-weight: 700; font-size: 16px">
            {{
              websitConfig.sysName ? websitConfig.sysName : website.indexTitle
            }}
          </div>
        </span>
      </template>
    </transition-group>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "logo",
  data() {
    return {};
  },
  created() {
    console.info(this.websitConfig);
  },
  computed: {
    ...mapGetters(["website", "keyCollapse", "websitConfig"]),
  },
  methods: {},
};
</script>

<style lang="scss">
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-active {
  transition: opacity 2.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.avue-logo {
  position: fixed;
  top: 0;
  left: 0;
  width: 240px;
  height: 64px;
  line-height: 64px;
  background-color: rgb(0, 82, 217);
  font-size: 20px;
  overflow: hidden;
  box-sizing: border-box;
  color: #fff;
  z-index: 1024;
  &_title {
    height: 64px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    img {
      width: 20px;
      margin-top: 20px;
      margin-right: 5px;
    }
  }
  &_subtitle {
    display: block;
    text-align: center;
    font-size: 18px;
    font-weight: bold;
    color: #fff;
  }
}
</style>
