<template>
    <!--
        <fragment> 这里可以通过这个插件改造,适应原生的,但是会有动画问题,后面如果有需要可以改造
    -->
    <div class="sidebar-munu-list">
        <template v-if="hasOneShowingChild(item.children,item)">
            <app-link :to="onlyOneChild.link">
                <el-menu-item :index="onlyOneChild.link" :class="{'submenu-title-noDropdown':!isNest}">
                    <item :icon="onlyOneChild.icon||''" :title="onlyOneChild.name" />
                </el-menu-item>
            </app-link>
        </template>

        <el-submenu v-else ref="subMenu" :index="item.link" popper-append-to-body>
            <template slot="title">
                <item :icon="item.icon||''" :title="item.name" />
            </template>
            <SidebarItem
                v-for="child in item.children"
                :key="child.link"
                :is-nest="true"
                :item="child"
                class="nest-menu"
            />
        </el-submenu>
    </div>
</template>

<script>
import Item from './item'
import AppLink from './link'
// import FixiOSBug from './FixiOSBug'

export default {
    name: 'SidebarItem',
    components: { Item, AppLink },
    // mixins: [FixiOSBug],
    props: {
        // 路由项
        item: {
            type: Object,
            required: true
        },
        isNest: {
            type: Boolean,
            default: false
        }
    },
    data() {
        this.onlyOneChild = null
        return {}
    },
    methods: {
        // 简化处理
        hasOneShowingChild(children = [], parent) {
            children.forEach(item => {
                this.onlyOneChild = item
            })
            if (children.length === 0) {
                this.onlyOneChild = { ... parent, noShowingChildren: true }
                return true
            }
            return false
        }
    }
}
</script>
