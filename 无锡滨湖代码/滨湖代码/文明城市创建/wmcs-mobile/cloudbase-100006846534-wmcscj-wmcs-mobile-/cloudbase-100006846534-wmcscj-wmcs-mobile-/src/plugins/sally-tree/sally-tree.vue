<template>
	<scroll-view class="sally-tree-view-sc" :scroll-y="true">
		<sally-tree-item :config="config" :level="0" :list="treeList" @onChange="onChildCheckbox">
			<template v-slot:right="{scope}">
				<slot name="right" :scope="scope"></slot>
			</template>
		</sally-tree-item>
	</scroll-view>
</template>

<script>
	import sallyTreeItem from "./sally-tree-item.vue"
	export default {
		name: "sally-tree",
		components: {
			sallyTreeItem
		},
		props: {
			option: {
				type: Object,
				default: () => {}
			},
			parents: {
				type: Array,
				default: () => []
			},
			parentIds: {
				type: Array,
				default: () => []
			},

			list: {
				type: Array,
				default: () => []
			},


			multiple: { // 是否可以多选
				type: Boolean,
				default: false
				// default: true
			},
			selectParent: { //是否可以选父级
				type: Boolean,
				default: false
			},

			defaultIcon: { // 折叠时候的ic
				type: String,
				default: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFEAAABRCAYAAACqj0o2AAACE0lEQVR4Xu3c200DMRCF4XEltJAOkEugA+ggpUAHoQMqiFMCdEAJUMEiS4mEELlIO7bPOeN9i6K1rG/952myyea1WiCtXmEuYBPR4RBMxInoIOCwhOtJLKVszWyXc/5y2BvNEq6I+/3+kFK6M7OHnPM7jcLKjbZAvD/uaZtzflm5P4rbWyJWgDcze1LPuzVihfxUz7sH4ilJ2bx7Isrm3RtRMu8RiHJ5j0SUyXs0okTeCIj0eSMh0uaNhkiZNyIiXd7IiDR5oyNS5M2ACJ83EyJs3myIkHkzIsLlzYwIkzc7IkTeCojD81ZCHJa3GuKQvBURu+etjNgtb3XELnlHQGyedyTEZnlHQ2ySd0RE97wjI7rlHR3RJe+JeIrbLOecD6ePpZQ6W1kn2epo4MUrPOKyLN8ppYq1+y1VStncOjIdGnFZlo+U0uOtWOeOY2TE12Ouq//pEA7xXL7XfvcufR8K0Svfv6CREN3yDYfYIt9QiK3yjYTYLF95xB75SiP2ylcZsVu+cogj8pVCHJWvEuKwfOkREfKlRkTJlxkRJl86RMR8qRBR82VChM0XHpEhX2hElnyREWnyhUNkzBcKkTVfJETafIcjKuQ7FFEl35GIMvl2R1TMtyuiar49EWXzbY5oZpv/hibXTF2h3+s60FRKeT6+3TjMS3nrA3ZFRD8xrfY3ER1kJ+JEdBBwWGKeRAfEH1wS5WFZSDB/AAAAAElFTkSuQmCC'
			},
		},
		data() {
			return {
				showTree: false,
				treeList: [],
				selectIndex: -1,
				returnedItem: [], //定义一个空数组
				pids: [],
				ancestorsIds: [],
				childNums: [],
			}
		},
		computed: {
			type() {
				return this.config.type
			},

			config() {
				const {
					type,
					idKey,
					labelKey,
					multiple
				} = this.option
				return {
					type: type || 'radio',
					idKey: idKey || 'id',
					labelKey: labelKey || "label",
					multiple: multiple,
					defaultIcon: this.defaultIcon,
					checkboxPlacement: 'left' //选择框的位置，可选 left/right
				}
			}
		},
		mounted() {
			this.treeList = this.renderTreeList(this.list);

		},
		methods: {
			onForEach(list, parentIds) {
				list.forEach(item => {
					if (!parentIds.includes(item.id)) {
						item.checked = false;
						item.checkedStatus = item.checked == false ? 0 : 2;
						if (item.children.length > 0) {
							this.onForEach(item.children, parentIds)
						}
					} else {
						item.checked = true;
						item.checkedStatus = item.checked == false ? 0 : 2;
					}
				})

			},
			onForEach2(list, is = false) {
				const arr = []
				list.forEach(item => {
					// console.log(item.label, item, item.checkedStatus, item.checked, is, arr)
					if (item.checkedStatus != 0 || item.checked || is) {
						arr.push(item.id)
						if (item.children.length > 0) {
							arr.push(...this.onForEach2(item.children, !item.isLoaded))
						}

					}

				})
				return arr;
			},
			onChildCheckbox(citem, parents, parentIds) {
				let checkedIds = []
				if (!this.config.multiple) {
					this.onForEach(this.treeList, [citem.id, ...parentIds])
					parents.forEach(item => {
						item.checked = true;
						item.checkedStatus = item.checked == false ? 0 : 2;
						checkedIds.push(item.id)
					})
					this.$emit('onChange', {
						curItem: citem,
						checkedIds
					});
				} else {
					setTimeout(() => {
						checkedIds = this.onForEach2(this.treeList)
						this.$emit('onChange', {
							curItem: citem,
							checkedIds
						});
					}, 10);
				}


			},
			renderTreeList(list = [], rank = 0, parentIds = [], parents = []) {
				const arr = []
				const {
					idKey,
					labelKey
				} = this.config
				list.forEach(item => {
					let obj = {
						id: item[idKey],
						label: item[labelKey],
						source: item,
						parentIds, // 父级id数组
						parents, // 父级id数组
						rank, // 层级
						showChild: false, //子级是否显示
						open: false, //是否打开
						show: rank === 0, // 自身是否显示
						hideArr: [],
						orChecked: item.checked ? item.checked : false,
						checked: item.checked ? item.checked : false,
						checkedStatus: 0,
						childNum: 0,
						children: item.children || [],
						isLoaded: false, //是否加载过
					}
					obj.children = this.renderTreeList(obj.children, parentIds.length, [obj.id, ...parentIds], [
						obj, ...parents
					])
					arr.push(obj)
				})
				return arr;
			},
			onShowChild(item) {
				item.showChild = !item.showChild;
			}
		},

	}
</script>

<style scoped lang="less">
	@import "./style.less";
</style>