<template>
	<div class="page-table">
		<div>
			<!-- 头部插槽 -->
			<slot name="head"></slot>
		</div>

		<!-- 数据表 -->
		<el-table :data="tableData" empty-text="没有可以列出的数据 :-(">
			<el-table-column label="序号" type="index" width="80">
			</el-table-column>
			<el-table-column v-for="(it, i) in dataConfig" :key="i" :label="it.label" :prop="it.prop"></el-table-column>
			<template v-if="itemOpers.length > 0">
				<el-table-column label="操作" width="100">
					<template slot-scope="{row, $index}">
						<el-dropdown size="small" trigger="click">
							<span class="el-dropdown-link">
								<i class="el-icon-more drop_operation"></i>
							</span>
							<el-dropdown-menu slot="dropdown" class="tableDrop">
								<el-dropdown-item v-for="(it, i) in itemOpers" :key="i" @click.native="it.fun(row, $index)">
									<span>{{ it.name }}</span>
								</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</template>
				</el-table-column>
			</template>
		</el-table>

		<div>
			<!-- 底部插槽-一般用来分页 -->
			<slot name="foot"></slot>
		</div>
	</div>
</template>

<script>
export default {
	name: "PageTable",
	props: {
		tableData: {
			type: Array,
			default: () => {
				return []
			}
		},
		// table数据字段翻译
		dataConfig: {
			type: Array,
			default: () => {
				return [
					// {label, prop}
				]
			}
		},
		// table数据操作
		itemOpers: {
			type: Array,
			default:
				() => {
					return [
						// {name, fun}
					]
				}
		}
	},
	data() {
		return {}
	},
}
</script>

<style lang="scss" scoped>
.page-table {
	height: 100%;

	.el-table {
		height: calc(100% - 100px);
	}
}
</style>
