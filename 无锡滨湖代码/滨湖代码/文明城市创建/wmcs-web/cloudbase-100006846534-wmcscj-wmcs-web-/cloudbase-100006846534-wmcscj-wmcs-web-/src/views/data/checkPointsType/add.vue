<template>
    <infoDataInfo
        :useType="useType"
        @changeUseType="changeUseType"
    />
</template>
<script>
import infoDataInfo from './infoDataInfo.vue'
export default {
    components: { infoDataInfo },
    data() {
        return {
            useType: 'add'
        }
    },
    methods: {
        changeUseType(v) {
            this.useType = v
        }
    }
}
</script>
