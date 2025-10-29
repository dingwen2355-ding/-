<template>
    <personalbody title="配置中心">
        <baseKanban title="账号权限配置中心">
            <div>
                <div class="margin-top--10 margin-left--20 margin-right--20 margin-bottom-10">
                    <!-- usePower -->
                    <baseTab
                        :selected="activeName"
                        :tabs="tabs"
                        @change="changeTab"
                        @useNewTab="useNewTab"
                        usePower
                    />
                </div>
                <!-- <accountList v-if="activeName == 'AccoutSetting'" /> -->
                <roleList v-if="activeName == 'RoleConfig'" />
                <resourcesList v-if="activeName == 'Config'" />
                <dirtList v-if="activeName == 'DictConfig'" />
                <projectPublish v-if="activeName == 'projectPublish'" />
            </div>
        </baseKanban>
    </personalbody>
</template>
<script>
import personalbody from '../personalbody.vue'
// import accountList from './accountList.vue'
import roleList from './roleList.vue'
import dirtList from './dirtList.vue'
import resourcesList from './resourcesList.vue'
import projectPublish from './projectPublish.vue'
export default {
    components: { personalbody, roleList, dirtList, resourcesList, projectPublish },
    data() {
        return {
            activeName: 'Config',
            tabs: [
                { label: '资源配置', value: 'Config' },
                { label: '角色配置', value: 'RoleConfig' },
                { label: '字典配置', value: 'DictConfig' },
                { label: '系统配置', value: 'projectPublish' }
            ]
        }
    },
    methods: {
        changeTab(tab) {
            if (tab.value == this.activeName) return
            this.activeName = tab.value
            // if (this.pow(tab.value)) {
            //     this.activeName = tab.value
            // }
        },
        useNewTab(val) {
            this.activeName = val
        }
    }
}
</script>
