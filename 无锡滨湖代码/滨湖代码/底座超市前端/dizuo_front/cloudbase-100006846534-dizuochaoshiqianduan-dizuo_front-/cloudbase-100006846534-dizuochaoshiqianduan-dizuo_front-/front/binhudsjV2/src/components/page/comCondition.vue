<template>
    <!-- 筛选条件 -->
    <div class="condition">
        <div class="condition-label">
            {{ title }}:
        </div>
        <div class="condition-text-wrap">
            <div class="condition-text" :class="type == item[params.type] ? '_act' : ''" @click="fChangeClasses(item)"
                v-for="(item, idx) in data" :key="'data' + idx">
                {{ item[params.name] }}
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: '',
    components: {},
    props: {
        data: {
            typeof: Array,
        },
        title: {
            typeof: String,
        },
        type: {
            typeof: String,
        },
        params: {
            typeof: Object,
            default: () => {
                return {
                    type: 'type',
                    name: 'name',
                }
            }
        },
    },
    data() {
        return {

        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {

    },
    beforeDestroy() {

    },
    methods: {
        fChangeClasses(data) {
            this.$emit('fChangeConditionType', data)
        },
    }
}
</script>
<style lang='less' scoped>
.condition {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.condition-label {
    width: 70px;
    font-size: 14px;
    color: #1A1C20;
    letter-spacing: 0;
}

.condition-text-wrap {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    width: calc(100% - 70px);
}

.condition-text {
    // width: 88px;
    // height: 24px;
    padding: 4px 8px;
    margin-right: 10px;
    text-align: center;
    line-height: 24px;
    font-size: 14px;
    border-radius: 4px;
    color: #5C5E66;
    letter-spacing: 0;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._act {
        background: linear-gradient(140deg, #5CA4FF 0%, #2F6BFF 100%, #2F6BFF 100%);
        color: #fff;
    }
}
</style>