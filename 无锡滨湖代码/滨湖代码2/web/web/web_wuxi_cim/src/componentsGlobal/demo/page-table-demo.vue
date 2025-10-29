<template>
	<div class="d_container">
		<page-table :tableData="tableData" :dataConfig="dataConfig" :itemOpers="itemOpers">
			<template v-slot:head>
				<div class="head-slot">
					<div>
						<el-select v-model="monitorType" size="small" @change="handleCurrentChange(1)">
							<el-option v-for="(item, index) in monitorTypes" :key="index" :label="item.name" :value="item.id">
							</el-option>
						</el-select>
					</div>
					<div>
						<el-button type="primary" size="small" @click="handleAdd">添加</el-button>
					</div>
				</div>
			</template>

			<template v-slot:foot>
				<div class="foot-slot">
					<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
						:current-page="currentPage" :page-sizes="[10, 20, 50, 100, 200]" :page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper" :total="total">
					</el-pagination>
				</div>
			</template>
		</page-table>
		<!-- 添加告警规则 -->
		<ar-add-tmpl v-if="addVisible" :rowData="rowData" @handleClickButton="handleClickButton" />
	</div>
</template>
<script>
import { get, deletes } from "@/utils/fetch";
import arAddTmpl from "../components/arAdd.tmpl.vue";
export default {
	components: { arAddTmpl },
	name: "AlarmRules",
	data() {
		return {
			// 表数据
			tableData: [],
			dataConfig: [
				{ label: "规则名称", prop: 'title' },
				{ label: "监控类型", prop: 'monitorType' },
				{ label: "主机名称", prop: 'hostName' },
				{ label: "告警规则", prop: 'rule' },
				{ label: "添加时间", prop: 'createTime' },
			],
			itemOpers: [],

			// 分页
			currentPage: 1,
			pageSize: 20,
			total: 0,

			// 表数据 筛选
			monitorTypes: [
				{
					id: "",
					name: "全部",
				},
				{
					id: "1",
					name: "主机",
				},
				{
					id: "2",
					name: "服务",
				},
			],
			monitorType: "",

			// 新增数据
			rowData: {},
			addVisible: false,
		};
	},
	created() {
		this.itemOpers = [
			{ name: "编辑", fun: this.handleEdit },
			{ name: "删除", fun: this.handleDel },
		]
		this.getTableList();
	},
	methods: {
		handleSizeChange(val) {
			this.pageSize = val;
			this.getTableList();
		},
		handleCurrentChange(val) {
			this.currentPage = val;
			this.getTableList();
		},
		// 基本运算
		mathematical(sign) {
			switch (sign) {
				case "gt":
					return ">";
				case "lt":
					return "<";
				case "eq":
					return "=";
				case "gte":
					return ">=";
				case "lte":
					return "<=";
			}
		},
		getTableList() {
			get(`${cim_main}monitor/monitorAlarmRuleListPage`, {
				monitorType: this.monitorType, // {deving} 
				start: this.currentPage,
				limit: this.pageSize,
			}).then((res) => {
				let data = res.message.list;

				// {deving} 区分规则类型
				data.forEach((item) => {
					let { monitorType } = item;
					let rulesStr = "";
					if (monitorType == '1') {
						rulesStr = `CPU使用率${this.mathematical(item.cpuComparisonOperator)}${item.cpuComparisonValue
							}%、`;
						rulesStr += `内存使用率${this.mathematical(item.memComparisonOperator)}${item.memComparisonValue
							}%、`;
						rulesStr += `磁盘使用率${this.mathematical(item.diskComparisonOperator)}${item.diskComparisonValue
							}%、`;
						rulesStr += `出站${this.mathematical(item.outboundComparisonOperator)}${item.outboundComparisonValue
							}kb/s、`;
						rulesStr += `入站${this.mathematical(item.inboundComparisonOperator)}${item.inboundComparisonValue
							}kb/s`;
					} else {
						rulesStr += `访问频次${this.mathematical('gt')}${item.serverAlarmFrequency}次`
					}

					item.rule = rulesStr
					item.monitorType = item.monitorType == '1' ? '主机' : '服务'
					item.createTime = new Date(item.createTime).toLocaleString().replaceAll('/', '-')
				});
				this.tableData = data;
				this.total = res.message.total;
			});
		},
		handleAdd() {
			this.rowData = {};
			this.addVisible = true;
		},
		handleEdit(row) {
			this.rowData = row;
			this.addVisible = true;
		},
		handleDel(row) {
			this.$confirm("确认删除?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				deletes(`${cim_main}monitor/deleteMonitorAlarmRule`, {
					id: `${row.id}`,
				}).then((res) => {
					this.$message.success("删除成功");
					this.getTableList();
				});
			});
		},
		handleClickButton(val, obj) {
			if (this.addVisible) {
				this.addVisible = false;
				if (val) {
					this.getTableList();
				}
			}
		},
	},
};
</script>
<style lang="scss" scoped>
.d_container {
	padding-left: 24px;
	padding-right: 24px;
	box-shadow: 0px 6px 12px 0px rgba(0, 0, 0, 0.08);
	border-radius: 8px;
	height: 100%;
}

.head-slot {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 10px 0;
}

.foot-slot {
	margin: 10px 0;
}


::v-deep .page-table {
	.el-table {
		height: calc(100% - 110px);
	}
}
</style>