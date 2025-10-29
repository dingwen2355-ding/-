<template name="sally-tree-item">
	<view>
		<view v-for="(item, index) in treeList" :key="index" :class="'sally-tree-level-'+level">
			<view class="sally-tree-node__content"
				:class="{ 'none-childern': !(item.children && item.children.length) }">
				<image @click="onShowChild(item)" :class="{ expanded: item.showChild }"
					class="sally-tree-node__expand-icon" v-if="item.children && item.children.length"
					:src="config.defaultIcon">
				</image>
				<sally-checkbox :type="config.type" :checkedStatus="item.checkedStatus"
					@change="onChange(item)"></sally-checkbox>
				<view class="sally-tree-node__label">
					<text>{{ item.label }}</text>
				</view>
				<view class="sally-tree-right">
					<slot name="right" :scope="{item,index,list:treeList}"></slot>
				</view>
			</view>
			<view v-if="item.children && item.children.length && item.isLoaded" v-show="item.showChild"
				class="sally-tree-node__children ">
				<sally-tree-item :config="config" name="sally-tree-item" @onChange="onChildCheckbox"
					:isAllchecked="item.checked" :level="level+1" :parentIds="[item.id,...parentIds]"
					:parents="[item,...parents]" :list="item.children">
					<template v-slot:right="{scope}">
						<slot name="right" :scope="scope"></slot>
					</template>
				</sally-tree-item>
			</view>
		</view>
	</view>
</template>

<script>
	import sallyCheckbox from "./sally-checkbox.vue"
	import sallyTreeItem from "./sally-tree-item.vue"
	export default {
		name: "sally-tree-item",
		components: {
			sallyTreeItem,
			sallyCheckbox
		},
		props: {
			config: {
				type: Object,
				default: () => {}
			},

			// 层级
			level: {
				type: Number,
				default: 0,
			},
			parents: {
				type: Array,
				default: () => []
			},
			parentIds: {
				type: Array,
				default: () => []
			},
			isAllchecked: {
				type: Boolean,
				default: false
			},

			lazy: {
				type: Boolean,
				default: false
			},
			list: {
				type: Array,
				default: () => []
			},
			selectParent: { //是否可以选父级
				type: Boolean,
				default: false
			},

		},
		data() {
			return {
				showTree: false,
				// treeList: [],
				selectIndex: -1,
				returnedItem: [], //定义一个空数组
				pids: [],
				ancestorsIds: [],
				childNums: [],
				checkbox1: [0],

				currentItem: null
			}
		},
		watch: {
			isAllchecked(is) {
				this.init(is)
			},

		},
		computed: {
			isMultiple() {
				return this.config.multiple
			},
			currentChild() {
				if (this.currentItem && this.currentItem.children) {
					return this.currentItem.children
				}
				return []
			},
			treeList() {
				return this.list;
			}
		},

		mounted() {
			this.init(this.isAllchecked)
		},
		methods: {
			init(is) {
				if (!this.isMultiple) return;
				this.treeList.forEach(item => {
					if (item.checkedStatus !== 1) {
						item.checked = is;
						item.checkedStatus = item.checked == false ? 0 : 2;
					}
				})
			},

			// 子组件改变；
			onChildCheckbox(item, parents) {
				this.$emit('onChange', item, parents, this.parentIds);

			},
			onChange(item) {
				if (this.isMultiple) {
					this.onCheckbox(item)
				} else {
					this.onRadioChange(item)
				}
				this.$emit('onChange', item, this.parents, this.parentIds);
			},
			onRadioChange(item) {
				item.checked = true;
				item.isAllchecked = item.checked;
				item.checkedStatus = item.checked == false ? 0 : 2
			},
			onCheckbox(item) {

				item.checked = !item.checked;

				item.checkedStatus = item.checked == false ? 0 : 2
				item.isAllchecked = item.checked;
				const preCS = item.checkedStatus
				this.currentItem = item;
				this.parents.forEach(el => {
					const obj = {
						0: [],
						1: [],
						2: []
					}
					el.children.forEach(el2 => {
						obj[el2.checkedStatus].push(el2.checkedStatus)
					})
					if (obj[0].length == el.children.length) {
						el.checkedStatus = 0
						el.isAllchecked = false
					} else if (obj[2].length == el.children.length) {
						el.checkedStatus = 2
						el.isAllchecked = true

					} else {
						el.checkedStatus = 1

					}

				})



			},

			onShowChild(item) {
				item.showChild = !item.showChild;
				item.isLoaded = true;

			}
		},

	}
</script>

<style scoped lang="less">
	@import "./style.less";
</style>