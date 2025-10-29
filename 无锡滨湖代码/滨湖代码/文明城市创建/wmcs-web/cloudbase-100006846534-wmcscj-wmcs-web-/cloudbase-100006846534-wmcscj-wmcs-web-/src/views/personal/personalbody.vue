<template>
    <div class="pe-wapper-index">
        <pheader :title="title" />
        <div class="sider-main-wapper">
            <userperson />
            <div class="main-wapper">
                <slot />
            </div>
        </div>
    </div>
</template>
<script>
import pheader from './header.vue'
import userperson from './userperson.vue'
export default {
    components: { pheader, userperson },
    props: {
        title: {
            type: String,
            default: ''
        }
    }
}
</script>

<style lang="scss" scoped>
.pe-wapper-index{
    height: 100%;
    width: 100%;
    padding: 10px 0;
    padding-top:0;
    display: flex;
    flex-direction: column;
    .sider-main-wapper{
        flex:1;
        height: 0;
        width: 100%;
        display: flex;
        flex-direction: row;
        margin-top:10px;
        .main-wapper{
            flex:1;
            padding-left:10px;
            height: 100%;
            overflow: auto;
        }
    }
}
</style>
