<template>
    <div class="kanban" :class="classType">
        <div v-if="!noTitle" class="title-wapper" :class="titleType">
            <h5>{{ title }}</h5>
            <div v-if="getTetxLength" class="Prompt">
                <i class="el-icon-question" />
                <div class="PromptText">
                    <p v-for="(item, i) in promptText" :key="i" v-html="item" />
                </div>
            </div>
            <slot name="title" />
        </div>
        <div class="content-wapper" :class="{'no-padding': nopadding}">
            <slot />
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noTitle: {
            type: Boolean,
            default: false
        },
        title: {
            type: String,
            default: ''
        },
        titleType: {
            type: String,
            default: 'info'
        },
        classType: {
            type: String,
            default: ''
        },
        promptText: {
            type: Array,
            default: function() {
                return []
            }
        },
        nopadding: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        getTetxLength() {
            return this.promptText.length > 0
        }
    }
}
</script>
<style lang='scss' scoped>
.kanban {
  width: 100%;
  background: #fff;
  .title-wapper {
    display: flex;
    flex-direction: row;
    padding: 8px 0 8px 0;
    font-size: 22px;
    border-bottom: 1px solid $thGLineF;
    .Prompt {
        position: relative;
        display: inline-block;
        height: 100%;
        width: 50px;
        margin-left: 20px;
        &:hover {
            .PromptText {
                display: block;
            }
        }
        .PromptText {
            display: none;
            position: absolute;
            z-index: 999;
            left: 50%;
            padding: 10px 20px;
            background-color: rgba($color: #000000, $alpha: .7);
            p {
                font-size: 16px;
                margin-bottom: 4px;
                color: #fff;
                white-space: nowrap;
            }
        }
    }
    &.info {
      color: var(--td-brand-color);
      h5 {
        border-left: 4px solid var(--td-brand-color);
      }
    }
    &.danger {
      color: $thKBDanger;
      h5 {
        border-left: 4px solid $thKBDanger;
      }
    }
    h5 {
      display: flex;
      align-items: center;
      padding: 5px 0 5px 10px;
    }
  }
  .content-wapper {
    padding: 20px;
    &.no-padding{
        padding: 0px;
    }
  }
}
</style>
