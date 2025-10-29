<template>
    <!-- 首页的应用列表 -->
    <div>
        <div class="list-item block" v-for="(item, idx) in data" :key="'data' + idx">
            <div class="list-item-title">{{ item[params.name] }}</div>
            <div class="list-item-detail text-ellipsis-2">{{ item[params.detail] }}</div>
            <hr width="100%" />
            <div class="list-item-box">
                <div class="item-box-unit">{{ item[params.unit] }}</div>
                <div class="item-box-details" @click.stop="fClickAppDetails(item)">查看详情 ></div>
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
            default: () => {
                return [
                    {
                        name: '无锡市滨湖区文明城市创建平台',
                        detail: '资源介绍资源介绍资源介绍资源介绍资源介绍资源介绍介绍资源介绍资源介绍资源介绍资源介绍资源介绍资...',
                        unit: '滨湖区区委宣传部',
                    },
                    {
                        name: '无锡市滨湖区文明城市创建平台',
                        detail: '资源介绍资源介绍资源介绍资源介绍资源介绍资源介绍介绍资源介绍资源介绍资源介绍资源介绍资源介绍资...',
                        unit: '滨湖区区委宣传部',
                    },
                    {
                        name: '无锡市滨湖区文明城市创建平台',
                        detail: '资源介绍资源介绍资源介绍资源介绍资源介绍资源介绍介绍资源介绍资源介绍资源介绍资源介绍资源介绍资...',
                        unit: '滨湖区区委宣传部',
                    }, {
                        name: '无锡市滨湖区文明城市创建平台',
                        detail: '资源介绍资源介绍资源介绍资源介绍资源介绍资源介绍介绍资源介绍资源介绍资源介绍资源介绍资源介绍资...',
                        unit: '滨湖区区委宣传部',
                    },
                ];
            }
        },
        params: {
            typeof: Object,
            default: () => {
                return {
                    name: 'name',
                    detail: 'detail',
                    unit: 'unit',
                };
            }
        }
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
        // console.log(this.data)
        // console.log(this.params)
    },
    beforeDestroy() {

    },
    methods: {
        // 点击详情页面
        fClickAppDetails(data) {
            this.$emit('fClickAppDetails', data)
        },
    }
}
</script>
<style lang='less' scoped>
.list-item {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 100%;
    height: 100%;
    margin-bottom: 9px;
    padding: 0 37px 0 31px;
    background-color: #fff;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
}


.list-item-title {
    font-size: 26px;
    font-weight: 500;
    color: #000000;
}

.list-item-detail {
    font-size: 20px;
    font-weight: 300;
    color: #000000;
}

.list-item-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
}

.item-box-unit {
    color: #000000;
}

.item-box-details {
    color: #266FE8;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

}
</style>