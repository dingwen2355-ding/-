<template>
  <div class="avue-sidebar">
    <logo></logo>
    <el-scrollbar style="height: 100%">
      <div v-if="validatenull(menu)" class="avue-sidebar--tip">
        {{ $t("menuTip") }}
      </div>
      <el-menu
        unique-opened
        :default-active="nowTagValue"
        mode="vertical"
        :show-timeout="200"
        :collapse="keyCollapse"
      >
        <sidebar-item
          :menu="menu"
          :screen="screen"
          first
          :props="website.menu.props"
          :collapse="keyCollapse"
        ></sidebar-item>
      </el-menu>
      <div class="version">版本：v2.0</div>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import logo from "../logo";
import sidebarItem from "./sidebarItem";

export default {
  name: "sidebar",
  components: { sidebarItem, logo },
  inject: ["index"],
  data() {
    return {};
  },
  created() {
    this.index.openMenu();
  },
  computed: {
    ...mapGetters([
      "website",
      "menu",
      "tag",
      "keyCollapse",
      "screen",
      "menuId",
    ]),
    nowTagValue: function () {
      return this.$router.$avueRouter.getValue(this.$route);
    },
  },
  methods: {},
};
</script>
<style lang="scss" scoped>
.version {
  color: #6b6f75;
  z-index: 99;
  position: absolute;
  left: 0;
  top: 95%;
  width: 100%;
  text-align: center;
  font-size: 14px;
}
</style>

