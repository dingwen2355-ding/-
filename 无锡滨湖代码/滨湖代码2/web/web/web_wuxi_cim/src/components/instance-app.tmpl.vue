<template>
	<div>
		<!-- 实例申请 -->
		<el-dialog title="实例预约使用时间" :visible.sync="dialogVisible" width="40%" custom-class="d_form"
			:before-close="backConfirm">
			<div class="d_opr">
				<el-date-picker v-model="ruledate" type="date" placeholder="选择日期" @change="handleChangeParm"
					:picker-options="pickerOptions" :clearable="false" :editable="false">
				</el-date-picker>
				<el-select v-model="instanceid" placeholder="请选择" @change="handleChangeParm">
					<el-option v-for="(item, index) in instanceLise" :key="index" :label="(item.name || '').replace('\n', '')"
						:value="item.id" :name="item.name">
					</el-option>
				</el-select>
				<el-button @click="handleClear" size="small">清除</el-button>
			</div>
			<div class="hours">
				<div v-for="(item, index) in timeList" :key="index" :ck="
					formatTime(item.value) >= ruletime[0] &&
					formatTime(item.value) <= ruletime[1]
				" :yd="item.checked" @click="handleClickHour(item)">
					<el-popover placement="top-start" width="200" trigger="hover">
						<div>
							{{ tipTitle(item) }}
						</div>
						<div v-if="item.type === 3">
							预约人 : {{ item.name }}<br />
							电话 : {{ item.phone }}
						</div>
						<span style="padding: 15px" slot="reference">{{ item.value }}</span>
					</el-popover>
				</div>
			</div>
			<span slot="footer">
				<el-button @click="backConfirm" size="small">取 消</el-button>
				<el-button size="small" type="primary" @click="handleSubmit()" :disabled="reserveFlag">确定预约</el-button>
			</span>
		</el-dialog>
		<!-- 实例预约成功 -->
		<instance-reserve-success-tmpl v-if="reserveSuccessVisible" :reserveInfo="reserveInfo"
			@handleClickButton="handleClickButton"></instance-reserve-success-tmpl>
	</div>
</template>
<script>
import { get, generalPostEvent } from "@/utils/fetch";
import moment from "moment";
import InstanceReserveSuccessTmpl from "./instance-reserve-success.tmpl.vue";
export default {
	props: ["moduleName"],
	data() {
		return {
			dialogVisible: false,
			pickerOptions: {
				disabledDate(time) {
					return time.getTime() < Date.now() - 8.64e7;
				},
			},
			instanceLise: [],
			instanceid: "",
			ruledate: "",
			ruletime: [],
			timeList: [], // 小时
			phone: "",
			reserveInfo: null,
			reserveSuccessVisible: false,
			reserveFlag: true, //没有选择时间，按钮禁止
		};
	},
	components: {
		InstanceReserveSuccessTmpl,
	},
	created() {
		// 默认选择当天日期
		this.ruledate = new Date();
		this.getApplyCase();
		this.getTimeList();
	},
	methods: {
		// --- 存储预约信息 - 基础平台 - moduleName = "base" ---
		saveReserveInfo(reserveInfo, moduleName = "base") {
			let userId = this.$store.getters.userInfo.id;
			if (!!userId && this.moduleName === moduleName) {
				let { ruledate, ruletime } = reserveInfo;
				ruletime =
					Object.prototype.toString.call(ruletime) == "[object Array]"
						? ruletime
						: ruletime.split("~");
				let CIM_account_appoint = {
					id: userId,
					during: {
						ruledate,
						ruletime,
					},
				};
				localStorage.setItem(
					"CIM_account_appoint",
					JSON.stringify(CIM_account_appoint)
				);
			}
		},
		// 列表->实例
		async getApplyCase() {
			if (this.moduleName === "demo") {
				this.instanceLise = [
					{
						id: "07b7878d-568a-4123-ae94-8fae1e851ac0",
						name: "CIM演示平台\n",
					},
				];
				this.defaultInstance();
				return;
			}
			await get(`${cim_main}service/instance/list`).then((res) => {
				this.instanceLise = res.message;
				this.defaultInstance();
			});
		},
		defaultInstance() {
			// 查询是否有预约申请
			this.checkLoginUser();
			// 默认选中第一个
			if (this.instanceLise.length > 0 && !this.instanceid) {
				this.instanceid = this.instanceLise[0].id;
				this.handleChangeParm();
			}
		},
		// 判断时间是否预约
		tipTitle(item) {
			if (item.type === 1) {
				return "该时间已过去";
			} else if (item.type === 3) {
				return "该时间已被预约";
			} else if (item.type === 2) {
				return "该时间可预约";
			}
		},
		// 确定预约
		handleSubmit() {
			let ruletime = this.ruletime;
			ruletime[0] = moment().set("hours", Number(ruletime[0])).format("HH:00");
			ruletime[1] = moment().set("hours", Number(ruletime[1])).format("HH:59");
			generalPostEvent(`${cim_loginServer}freedo/user/setuserrule`, {
				instanceid: this.instanceid,
				ruledate: this.formatDate(this.ruledate),
				ruletime: ruletime.join("~"),
			})
				.then((res) => {
					this.saveReserveInfo(
						{
							ruledate: this.formatDate(),
							ruletime,
						},
						"base"
					);

					this.reserveInfo = {
						instanceid: this.instanceid,
						ruletime: ruletime.join("~"),
						ruledate: this.ruledate,
					};
					this.dialogVisible = false;
					this.reserveSuccessVisible = true;
				})
				.catch((err) => {
					localStorage.removeItem("CIM_account_appoint");
					this.$message.error(err.data.body.message);
				});
		},
		// 选择日期、实例
		handleChangeParm() {
			let hour = new Date().getHours();
			this.getTimeList();
			this.timeList.filter((val) => {
				if (val.checked && val.value > hour) {
					val.checked = false;
				}
			});
			if (!this.ruledate || !this.instanceid) {
				return false;
			}
			get(`${cim_main}mediaServer/user/getruleinfo`, {
				instanceId: this.instanceid,
				ruledate: this.formatDate(this.ruledate),
				phone: this.phone,
				type: 1,
			}).then((res) => {
				let list = res.message;
				if (list.length === 0) {
					return;
				}
				list.forEach((item) => {
					let tempTimeInfo = (item.ruletime || "").split("~").map((val) => {
						return parseInt(val);
					});
					for (let i = tempTimeInfo[0]; i <= tempTimeInfo[1]; i++) {
						this.timeList.forEach((val) => {
							if (val.value === i) {
								val.checked = true;
								val.type = 3;
								val.name = item.name;
								val.phone = item.phone;
							}
						});
					}
				});
				this.$forceUpdate();
			});
		},
		// 登录用户是否预约(未过期) ? 提示预约成功 : 实例申请
		async checkLoginUser() {
			let res = await get(`${cim_main}mediaServer/user/getruleinfo`, {
				type: 2,
			});
			try {
				let ids = this.instanceLise.map((item) => item.id);
				res.message.forEach((item) => {
					if (ids.indexOf(item.instanceid) !== -1 && !!item.ruletime) {
						let str = item.ruledate + " " + item.ruletime.split("~")[1];
						if (moment() < moment(str)) {
							let reserveInfo = {
								instanceid: item.instanceid,
								iid: item.iid,
								mappingproxyurl: item.mappingproxyurl,
								proxyinurl: item.proxyinurl,
								ruletime: item.ruletime,
								ruledate: item.ruledate,
							};
							this.reserveInfo = reserveInfo;
							this.saveReserveInfo(reserveInfo, "base");

							// this.dialogVisible = false;
							this.reserveSuccessVisible = true;
							throw new Error();
						}
					}
				});
				// this.reserveSuccessVisible = false;
				this.dialogVisible = true;
			} catch (e) { }
		},
		// 改变时间格式
		formatTime(val) {
			return val < 10 ? `0${val}` : `${val}`;
		},
		// 选择时间段
		handleClickHour(item) {
			if (item.type !== 2) {
				return false;
			}
			let self = this;
			// 所选时间段是否可预约
			self.verType = (start, end) => {
				let _RS = true;
				for (let i = Number(start); i <= Number(end); i++) {
					if (self.timeList[i].type !== 2) {
						_RS = false;
						break;
					}
				}
				return _RS;
			};
			// 限制4个小时
			self.limit4H = (num1, num2, opr) => {
				let lim = 4; // 限时
				if (opr === "+") {
					if (Number(num2) > Number(num1) + (lim - 1)) {
						this.$message.warning(`限时${lim}小时!`);
						return Number(num1) + (lim - 1);
					} else {
						return Number(num2);
					}
				} else if (opr === "-") {
					if (Number(num1) < Number(num2) - (lim - 1)) {
						this.$message.warning(`限时${lim}小时!`);
						return Number(num2) - (lim - 1);
					} else {
						return Number(num1);
					}
				}
			};
			let str = self.formatTime(item.value);
			if (!self.ruletime[0] || self.ruletime[0] !== self.ruletime[1]) {
				self.ruletime[0] = str;
				self.ruletime[1] = str;
			} else {
				if (str < self.ruletime[0]) {
					if (self.verType(str, self.ruletime[1])) {
						self.ruletime[0] = self.limit4H(str, self.ruletime[1], "-");
					}
				} else if (str > self.ruletime[1]) {
					if (self.verType(self.ruletime[0], str)) {
						self.ruletime[1] = self.limit4H(self.ruletime[0], str, "+");
					}
				}
			}
			self.$forceUpdate();
			this.reserveFlag = false;
		},
		// 清除选中时间段
		handleClear() {
			this.ruletime = [];
			this.reserveFlag = true;
		},
		// 改变时间格式
		formatDate(date) {
			return moment(date).format("YYYY-MM-DD");
		},
		// 取消预约
		handleCancelReserve() {
			generalPostEvent(`${cim_loginServer}freedo/user/setuserrule`, {
				instanceid: this.instanceid,
				ruledate: this.formatDate(this.ruledate),
			}).then((res) => {
				this.$message.success("已取消");
				this.backConfirm();
			});
		},
		getTimeList() {
			let nowDate = new Date(); // 当前时间
			let nowHour = nowDate.getHours();
			const tempArr = [];
			for (let i = 0; i <= 23; i++) {
				tempArr.push({
					value: i,
					checked: i < nowHour && this.ruledate <= nowDate ? true : false,
					type: i < nowHour && this.ruledate <= nowDate ? 1 : 2,
					name: "",
					phone: "",
				});
			}
			this.timeList = tempArr;
		},
		handleClickButton(val, obj) {
			if (this.reserveSuccessVisible) {
				this.reserveSuccessVisible = false;
				if (val) {
					this.dialogVisible = true;
					this.handleChangeParm();
				} else {
					this.backConfirm();
				}
			}
		},
		backConfirm() {
			this.$emit("handleClickButton");
		},
	},
};
</script>

<style lang="scss" scoped>
@import "~@/assets/css/reset-dialog.scss";

.d_opr {
	display: flex;
	align-items: center;
	justify-content: left;

	.el-select,
	.el-date-editor {
		width: 160px !important;
	}

	&>*:not(:first-child) {
		margin-left: 30px;
	}
}

.hours {
	border-radius: 8px;
	border: 1px solid #c4c4c6;
	margin-top: 16px;
	padding: 6px 24px 24px 6px;

	div {
		box-sizing: border-box;
		display: inline-block;
		width: 90px;
		height: 90px;
		border-radius: 8px;
		border: 1px solid #979797;
		font-size: 24px;
		font-weight: 500;
		color: #000000;
		line-height: 90px;
		text-align: center;
		margin: 18px 0 0 18px;

		&[ck] {
			background-color: #e2ecfb;
		}

		&[yd] {
			background-color: #d8d8d8;
		}

		&:not([yd]) {
			cursor: pointer;
		}

		&[xz] {
			background-color: rgb(167, 167, 167);
		}
	}

	div:nth-child(7) span:nth-child(8) {
		width: 198px;
		height: 90px;
		background: #e2ecfb;
		border-radius: 8px;
	}
}
</style>
