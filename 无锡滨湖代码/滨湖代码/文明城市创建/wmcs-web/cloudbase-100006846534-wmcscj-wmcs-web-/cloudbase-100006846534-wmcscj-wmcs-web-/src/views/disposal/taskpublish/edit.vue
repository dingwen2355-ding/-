<template>
    <infoData
        :useType="useType"
        @changeUseType="changeUseType"
    />
</template>
<script>
import infoData from './infoData.vue'
export default {
    components: { infoData },
    data() {
        return {
            useType: 'edit'
        }
    },
    methods: {
        changeUseType(v) {
            this.useType = v
        }
    }
}
</script>
