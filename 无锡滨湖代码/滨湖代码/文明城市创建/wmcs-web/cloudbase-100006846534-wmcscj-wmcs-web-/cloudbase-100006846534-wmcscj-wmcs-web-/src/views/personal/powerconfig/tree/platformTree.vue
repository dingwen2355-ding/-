<template>
    <div class="tree-wapper-item">
        <div class="pla-title">{{ pla.name }}</div>
        <div class="flex-1" style="height:0;overflow:auto;">
            <el-tree
                ref="tree"
                :props="props"
                :data="pla.list"
                show-checkbox
                node-key="id"
                :default-checked-keys="currentKey"
                default-expand-all
            >
                <span slot-scope="{ node, data }" class="custom-tree-node">
                    <span
                        class="unline"
                        :class="{'zr-link-p': data.type == 'C', 'zr-link-i': data.type == 'M', 'zr-link-s': data.type == 'F'}"
                    >{{ node.label }}</span>
                </span>
            </el-tree>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        pla: {
            type: Object,
            default: () => {
                return {
                    appGroup: '',
                    perms: []
                }
            }
        },
        defaultKey: {
            type: Array,
            default: () => {
                return []
            }
        }
    },
    data() {
        return {
            currentKey: [],
            props: {
                label: 'name',
                children: 'children'
            }
        }
    },
    watch: {
        defaultKey(newVal, oldVal) {
            this.useDefaultKey()
        }
    },
    methods: {
        getAllSelectKey() {
            let all_ids = []
            let id1 = this.$refs.tree.getCheckedKeys()
            let id2 = this.$refs.tree.getHalfCheckedKeys()
            all_ids = all_ids.concat(id1)
            all_ids = all_ids.concat(id2)
            return all_ids
        },
        getChildIds(arr) {
            let ids = []
            arr.forEach(i => {
                ids.push(i.id)
                if (i.children.length > 0) {
                    let c_ids = this.getChildIds(i.children)
                    ids = ids.concat(c_ids)
                }
            })
            return ids
        },
        useDefaultKey() {
            // 当前平台的所有id
            let all_ids = this.getChildIds(this.pla.list)
            // 获取父数组
            let f_arr = this.defaultKey.map(i => {
                return i.menuParentId + ''
            })
            // 过滤掉父数组
            let child = this.defaultKey.filter(i => {
                return f_arr.indexOf(i.menuId) < 0
            }).map(j => { return j.menuId })
            // 过滤掉非本平台的id
            child = child.filter(i => {
                return all_ids.indexOf(i) > -1
            })
            this.currentKey = child
        }
    }
}
</script>

<style lang="scss" scoped>
.tree-wapper-item{
    padding: 10px;
    width: 330px;
    height: 450px;
    margin-top:10px;
    margin-right: 10px;
    margin-bottom: 10px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    .pla-title{
        font-weight: 500;
        text-align: center;
        color: var(--td-brand-color);
        margin-top:-10px;
        margin-bottom: 5px;
        border-bottom: 1px solid #EBEEF5;
    }
}
</style>
