<template>
    <div style="height: 100%;" class="show-flex-c">
        <div class="margin-bottom-20">
            <s1 @change="changeDateType" />
        </div>
        <!-- <div class="flex-1 h-0" style="overflow: auto;">
            <s2 ref="s2" />
        </div> -->
    </div>
</template>
<script>
import s1 from './common/s1.vue'
import s2 from './common/s2.vue'
export default {
    components: {
        s1, s2
    },
    methods: {
        changeDateType(v) {
            this.$refs.s2.changeDateType(v)
        }
    }
}
</script>
