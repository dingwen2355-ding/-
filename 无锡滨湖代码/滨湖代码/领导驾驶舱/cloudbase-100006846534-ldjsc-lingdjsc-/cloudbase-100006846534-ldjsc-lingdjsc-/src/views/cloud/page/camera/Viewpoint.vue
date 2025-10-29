<template>
	<div class="cloud-func">
		<div class="func-title">
			视点
			<span @click.stop="handleClose">
				<i class="el-icon-close"></i>
			</span>
		</div>

		<div class="func-warp" v-show="!isShowAddPanel">
			<el-scrollbar class="warp-list">
				<div class="list" v-for="(item, index) in cameraViews" :key="index">
					<div class="list-left">
						<i class="el-icon-camera-solid icon" @click="handleClickCamera(item)"></i>
						<span>{{ item.name }}</span>
					</div>
					<el-dropdown placement="left" @command="handleCommand">
						<span class="el-dropdown-link">
							<svg-icon icon-class="cloud-more" class-name="icon"> </svg-icon>
						</span>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item :command="{ type: '删除', item: item }">删除</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</div>
			</el-scrollbar>
			<div class="btn-wrap">
				<div @click="drawParam()">
					<i class="el-icon-plus"></i>
					<span>新增视点</span>
				</div>
			</div>
		</div>

		<el-form v-show="isShowAddPanel" size="mini" :model="fModel" class="func-warp">
			<el-form-item label="视点名称" prop="name">
				<el-input v-model="fModel.name" placeholder="最多10个字" maxlength="10"></el-input>
			</el-form-item>
			<el-form-item class="btn">
				<el-button @click="cancle">取消</el-button>
				<el-button type="primary" @click="sure">确定</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
import { get, post, put, deletes } from "@/utils/fetch";
export default {
	name: "Viewpoint",
	data() {
		return {
			cameraViews: [],
			fModel: {
				name: "",
			},
			isShowAddPanel: false,
		};
	},
	created() {
		this.initData()
	},
	methods: {
		// 获取数据库视点
		async getViewPoint() {
			let res = await get(`${cim_main}service/scene/props`, {
				sceneid: this.$route.query.mediaid,
				type: "viewPoint",
			});
			return (res.message || []).map(item => { return { ...JSON.parse(item.description), id: item.id } });
		},
		// 删除数据库视点
		async deleteViewPoint(data) {
			let res = await deletes(`${cim_main}service/scene/props`, { id: data.id });
			if (res.message === "1") this.initData();
			else this.$message.error("删除失败！");
		},
		// 保存视点到后端数据库
		async saveViewPoint(obj) {
			let res = await post(`${cim_main}service/scene/props?token=${this.$store.getters.token}`, {
				sceneid: this.$route.query.mediaid,
				type: "viewPoint",
				description: JSON.stringify(obj)
			});
			if (res.message === "1") {
				this.initData();
				this.isShowAddPanel = false;
			} else this.$message.error("保存失败！");
		},
		// // 编辑视点到后端数据库
		// async editViewPoint(obj) {
		// 	let res = await put(`${cim_main}service/scene/props?token=${this.$store.getters.token}`, {
		// 		sceneid: this.$route.query.mediaid,
		// 		type: "viewPoint",
		// 		description: JSON.stringify(obj),
		// 		id: obj.id
		// 	})
		// 	if (res.message === "1") {
		// 		this.initData();
		// 		this.isShowAddPanel = false;
		// 	}
		// 	else this.$message.error("保存失败！");
		// },
		async initData() {
			this.cameraViews = await this.getViewPoint();
		},
		handleClickCamera(item) {
			window.sealAPI._camera.set_biz(item.visualAngle);

			// 【控制树】模型数据 选中的数据id
			let layerTreeCMPT = window.Player.$refs.tree;
			let checkIds = layerTreeCMPT.getSelectedTreeIds();
			let ids = item.checkedIds;
			layerTreeCMPT.filterText = "";
			layerTreeCMPT.$refs.modeltree.setCheckedKeys(ids); // 通过 keys 设置目前勾选的节点

			let showIds = ids.map(i => { if (!checkIds.includes(i)) return i; }).filter(i => i); // 待显示 3dt id
			if (showIds.length > 0) window.sealAPI._tileLayer.show_biz(showIds);
			let hideIds = checkIds.map(i => { if (!ids.includes(i)) return i; }).filter(i => i); // 待隐藏 3dt id
			if (hideIds.length > 0) window.sealAPI._tileLayer.hide_biz(hideIds);
		},
		async handleCommand(command) {
			let { type, item } = command
			if (type === "删除") {
				this.deleteViewPoint(item);
			}
		},
		drawParam() {
			this.fModel.name = "";
			this.isShowAddPanel = true
		},
		async sure() {
			if (!this.fModel.name) return this.$message.warning("名称不能为空");

			let params = {
				name: this.fModel.name
			};
			// 保存当前视角
			let resV = await window.sealAPI._camera.get();
			resV = [resV].map(({ x, y, z, pitch, yaw }) => ({ x, y, z, pitch, yaw }));
			params.visualAngle = resV[0];
			// 【控制树】保存选中的模型id
			params.checkedIds = window.Player.$refs.tree.getSelectedTreeIds();

			this.saveViewPoint(params);
		},
		cancle() {
			this.isShowAddPanel = false;
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		this.handleClose();
	}
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.warp-list {
	i.icon {
		cursor: pointer;
	}
}
</style>
