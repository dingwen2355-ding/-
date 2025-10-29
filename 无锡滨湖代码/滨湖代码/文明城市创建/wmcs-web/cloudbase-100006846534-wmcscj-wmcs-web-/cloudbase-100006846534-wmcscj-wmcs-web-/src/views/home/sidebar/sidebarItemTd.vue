<template>
    <div class="sidebar-munu-list">
        <template v-if="hasOneShowingChild(item.children,item)">
            <app-link :to="onlyOneChild.link">
                <t-menu-item :value="onlyOneChild.link" :class="{'submenu-title-noDropdown':!isNest}" @click="history2url(onlyOneChild.link)">
                    <item :icon="onlyOneChild.icon||''" :title="onlyOneChild.name" />
                </t-menu-item>
            </app-link>
        </template>
        <t-submenu v-else ref="subMenu" :value="item.link">
            <template slot="icon">
                <item :icon="item.icon||''" :title="item.name" />
            </template>
            <SidebarItemTd
                v-for="child in item.children"
                :key="child.link"
                :is-nest="true"
                :item="child"
            />
        </t-submenu>
    </div>
</template>

<script>
import Item from './item'
import AppLink from './link'
// import FixiOSBug from './FixiOSBug'

export default {
    name: 'SidebarItemTd',
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
        isExternal(to) {
            return /^(https?:|mailto:|tel:)/.test(to)
        },
        history2url(to) {
            if (this.isExternal(to)) {
                window.open(to)
            } else {
                this.$router.push(to)
            }
        },
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
