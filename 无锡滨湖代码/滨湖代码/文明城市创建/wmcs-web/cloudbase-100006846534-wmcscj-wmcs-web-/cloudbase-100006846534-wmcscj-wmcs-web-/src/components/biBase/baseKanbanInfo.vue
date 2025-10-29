<template>
    <!-- kanban内部详情居中 -->
    <div class="base-kanban-info-out-wapper">
        <div class="w-100 show-flex-r kb-title-out-wapper">
            <div class="flex-1">
                <baseGoBack :title="title" />
            </div>
            <div class="show-flex-r a-c head-slot-wap">
                <slot name="headbtn" />
            </div>
        </div>
        <!-- header是固定在上方的不能滑动 -->
        <slot name="header" />
        <div class="base-kanban-info-in-wapper" :class="{'nopadding': nopadding}">
            <!-- inheader是能滑动区域的header -->
            <div :class="{'style-type-a': styleType == 'A', 'style-type-b': styleType == 'B'}">
                <slot name="body" />
            </div>
            <!-- <div v-if="showBody" class="base-info-z-wapper">
                <slot />
            </div> -->
        </div>
        <!-- 固定在下方不能滑动 -->
        <slot name="footer" />
    </div>
</template>
<script>
export default {
    props: {
        nopadding: {
            type: Boolean,
            default: false
        },
        title: {
            type: String,
            default: ''
        },
        showBody: {
            type: Boolean,
            default: true
        },
        styleType: {
            type: String,
            default: 'B' // A: 1300px B: 100%
        }
    }
}
</script>
<style lang="scss" scoped>
.base-kanban-info-out-wapper{
    height: 100%;
    // height: calc(100vh - 100px);
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: #ffffff;
    .kb-title-out-wapper{
        // background: #0052D9;
        background: #ffffff;
        // 0e55c8  O:005AFF
    }
    .head-slot-wap{
        // color: #ffffff;
        border-bottom: 1px solid var(--td-brand-color) !important;
    }
    .base-kanban-info-in-wapper{
        flex:1;
        height: 0;
        overflow: auto;
        width: 100%;
        padding: 20px;
        // max-width: 1300px;
        // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        // margin: 20px 0;
        // border: 1px solid #EBEEF5;
        // display: flex;
        // flex-direction: column;
        // align-items: center;
        // background: #ffffff;
        .style-type-a{
            // max-width: 1300px;
            margin:0 auto;
            .td-form-content-wap::v-deep{
                width: 100%;
                max-width: 100%;
            }
        }
        .style-type-b{
            width: 100%;
            display: flex;
            justify-content: center;
        }
        &.nopadding{
            padding: 0;
        }
        .base-info-z-wapper{
            padding: 20px;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
            background: #fff;
            max-width: 1000px;
            width: 70%;
            min-width: 800px;
            height: fit-content;
        }
    }
}
</style>
