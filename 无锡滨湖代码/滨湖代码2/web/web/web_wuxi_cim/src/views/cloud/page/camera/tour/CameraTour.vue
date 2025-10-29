<template>
	<div>
		<div class="cloud-func" v-if="!showEditPanle">
			<div class="func-title">
				导览
				<span @click="handleClose">
					<i class="el-icon-close"></i>
				</span>
			</div>
			<div class="func-warp">
				<el-scrollbar class="warp-list">
					<div class="list" v-for="(item, index) in oaList" :key="index" :class="{ active: index == activeIndex }"
						@click="activeIndex = index">
						<ul class="warp-list-top">
							<li>
								<span :title="item.name">{{ item.name }}</span>
							</li>
							<img :src="item.keyFrames[0].image" alt="" />
						</ul>
						<el-dropdown placement="left" :disabled="disabled" @command="handleCommand">
							<span class="el-dropdown-link">
								<svg-icon icon-class="cloud-more" class-name="icon">
								</svg-icon>
							</span>
							<el-dropdown-menu class="cloud-dropdown" slot="dropdown">
								<el-dropdown-item :command="{ type: '编辑', item, index }">编辑</el-dropdown-item>
								<!-- <el-dropdown-item :command="{ type: '下载', item: item, i:index }"
										                  >下载</el-dropdown-item
										                > -->
								<el-dropdown-item v-if="playIndex != index"
									:command="{ type: '播放', item, index }">播放</el-dropdown-item>
								<el-dropdown-item v-else :command="{ type: '停止', item, index }">停止</el-dropdown-item>
								<el-dropdown-item :command="{ type: '删除', item, index }">删除</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</div>
				</el-scrollbar>
				<div class="btn-wrap">
					<div @click="handleLabelAdd()">
						<i class="el-icon-plus"></i>
						<span>新增导览</span>
					</div>
				</div>
			</div>
		</div>

		<TourDetailsTmpl v-else :editTarget="editTarget" @cancelEdit="cancelEdit" />
	</div>
</template>

<script>
import TourDetailsTmpl from "./common/TourDetails.tmpl";
import { get, deletes } from "@/utils/fetch";
export default {
	components: { TourDetailsTmpl },
	name: "CameraTour",
	data() {
		return {
			oaList: [],
			activeIndex: -1,
			playIndex: -1,
			currPlayId: "",
			playtimer: null,
			editTarget: null,
			showEditPanle: false,
			disabled: false,
			index: null
		};
	},
	created() {
		this.initData()
	},
	methods: {
		async initData() {
			let res = await get(`${cim_main}service/scene/props`, {
				token: this.$route.query.token,
				sceneid: this.$route.query.mediaid,
				type: "guide",
			});
			this.oaList = (res.message || []).map(item => { return { ...JSON.parse(item.description), id: item.id } });
		},
		async handleLabelAdd() {
			await this.stop();
			this.editTarget = null;
			this.showEditPanle = true;
		},
		async cancelEdit(val) {
			if (val) {
				await this.initData();
			}
			this.showEditPanle = false;
		},
		async handleCommand(command) {
			let node = this.oaList[command.index];
			await this.stop();
			switch (command.type) {
				case "编辑":
					this.editTarget = command;
					this.index = command.index;
					this.showEditPanle = true;
					break;
				case "播放":
					this.play(command);
					break;
				case "删除":
					this.oaList.splice(command.index, 1);
					this.deleteGuide(node);
					break;
			}
		},
		// 删除数据库导览
		deleteGuide(data) {
			this.disabled = true;
			deletes(`${cim_main}service/scene/props`, {
				token: this.$route.query.token,
				id: data.id
			}).then(res => {
				this.disabled = false;
			});
		},
		async play(command) {
			let layerTreeCMPT = window.Player.$refs.tree;
			let checkIds = layerTreeCMPT.getSelectedTreeIds();
			let ids = command.item.checkedIds || [];
			layerTreeCMPT.filterText = "";
			layerTreeCMPT.$refs.modeltree.setCheckedKeys(ids); // 通过 keys 设置目前勾选的节点

			let showIds = ids.map(i => { if (!checkIds.includes(i)) return i; }).filter(i => i); // 待显示 3dt id
			if (showIds.length > 0) window.sealAPI._tileLayer.show_biz(showIds);
			let hideIds = checkIds.map(i => { if (!ids.includes(i)) return i; }).filter(i => i); // 待隐藏 3dt id
			if (hideIds.length > 0) window.sealAPI._tileLayer.hide_biz(hideIds);

			this.$nextTick(async () => {
				this.currPlayId = command.item.id
				this.playIndex = command.index;

				let frames = [];
				command.item.keyFrames.forEach((it) => {
					frames.push(
						new CameraTourKeyFrame(it.index, it.time, it.location, it.rotation)
					);
				});

				let cameraTour = new CameraTourData(
					command.item.id,
					command.item.name,
					frames
				);

				await window.sealAPI._cameraTour.add(cameraTour);
				await window.sealAPI._cameraTour.play(command.item.id);

				this.playtimer = setTimeout(() => {
					this.playtimer = null;
					this.playIndex = -1;
					this.currPlayId = "";
				}, command.item.time * 1000);
			});
		},
		async stop() {
			return new Promise(async (resolve, reject) => {
				if (this.playtimer) {
					clearTimeout(this.playtimer);
					this.playtimer = null;

					if (this.currPlayId) {
						// await window.sealAPI._cameraTour.stop_biz();
						await window.origAPI.cameraTour.stop()
						await window.sealAPI._cameraTour.delete(this.currPlayId);
					}
					this.playIndex = -1;
					this.currPlayId = "";
				}
				resolve()
			})
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		if (this.playtimer || this.currPlayId) this.stop();
		this.handleClose();
	},
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.warp-list .list {
	align-items: start !important;

	.warp-list-top {
		width: calc(100% - 30px);

		img {
			margin-top: 6px;
			width: calc(100% + 30px);
		}
	}
}
</style>
