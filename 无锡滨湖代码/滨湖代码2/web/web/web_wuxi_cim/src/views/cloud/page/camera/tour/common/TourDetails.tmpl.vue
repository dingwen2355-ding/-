<template>
	<div class="cloud-camera-tour-detail">
		<div class="panel-title">
			<div class="top-left">
				<span v-if="!editname">{{
					option.name ? option.name : "导览名称可以编辑"
				}}</span>
				<el-input :autofocus="true" v-if="editname" v-model="option.name" />
				<i v-if="!editname" class="el-icon-edit" @click.stop="editname = true"></i>
				<i v-if="editname" class="el-icon-check" @click.stop="editname = false"></i>
			</div>

			<div class="top-right">
				<span>{{ duration }} 秒 </span>
				<span @click.stop="timeStep(-1)">
					<i class="el-icon-arrow-left"></i>
				</span>
				<el-tooltip class="item" effect="dark" :content="'上一帧播放到当前帧时间'" placement="top-start">
					<span> {{ interval }} 秒 </span>
				</el-tooltip>
				<span @click.stop="timeStep(1)">
					<i class="el-icon-arrow-right"></i>
				</span>
				<span @click.stop="playSwitch" style="margin: 0 10px">
					<el-tooltip class="item" effect="dark" :content="playCameraTour ? '停止导览' : '播放导览'"
						placement="top-start">
						<i :class="playCameraTour ? 'el-icon-video-pause' : 'el-icon-caret-right'
							"></i>
					</el-tooltip>
				</span>
				<!-- <span @click.stop="handleDownload" style="margin: 0 10px">
					          <i class="el-icon-download"></i>
					        </span> -->
				<span @click.stop="openKey()" :class="{ settingOn: keyOn }">
					<el-tooltip class="item" effect="dark" :content="keyOn
							? '关闭键盘控制'
							: '开启键盘控制（W、A、S、D、Q、E、↑、↓、←、→）'
						" placement="top-start">
						<i class="el-icon-setting"></i>
					</el-tooltip>
				</span>
			</div>
		</div>
		<div class="panel-warp">
			<div class="warp-list">
				<span @click.stop="arrow(1)">
					<i class="el-icon-arrow-left"></i>
				</span>
				<div class="wrap-center">
					<div class="list" v-for="(item, i) in option.keyFrames" :key="i"
						:class="{ list_check: checkCur.index === i }"
						:style="`margin-left:${i === 0 ? marginLeftList + 10 : 10}px`" @click="handleFrameClick(item, i)"
						@mouseenter="hoverIndex = i" @mouseleave="hoverIndex = -1">
						<span class="i-number">
							{{ i + 1 }}
						</span>
						<span class="i-reset" v-if="checkCur.index === i" @click.stop="resetKey(i)">
							<i class="el-icon-refresh"></i>
						</span>
						<span class="i-inset" v-if="checkCur.index === i" @click.stop="insetKey(i)">
							<i class="el-icon-bottom"></i>
						</span>
						<span class="i-delete" v-if="hoverIndex === i" @click.stop="deleteKey(i)">
							<i class="el-icon-delete"></i>
						</span>
						<img class="camera-img" :src="item.image" />
					</div>
					<div class="list" @click="addkeyFrame">
						<svg-icon icon-class="cloud-add2" class-name="icon"> </svg-icon>
					</div>
				</div>
				<span @click.stop="arrow(-1)">
					<i class="el-icon-arrow-right"></i>
				</span>
			</div>

			<div class="warp-footer">
				<div @click="cancle">
					<span>取消</span>
				</div>
				<div @click="sure">
					<span>确定</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { post, put, deletes } from "@/utils/fetch";
const defaultOption = {
	id: "",
	name: "",
	keyFrames: [], // [{ index: 0, location: [], rotation: [], image: "" }],
	time: 0,
	checkedIds: [],
};
export default {
	props: ["editTarget"],
	data() {
		return {
			option: null,
			editIndex: -1,
			editname: false, // 导览名称编辑开关
			interval: 0, // 视角间隔时间
			duration: 0, // 导览总时间
			playCameraTour: false,
			playtourtimer: null, // 导览播放定时器
			checkCur: {}, // 当前点击的 frame
			marginLeftList: 0, // 导览 frames 横向列表的左测移动距离
			hoverIndex: -1, // 鼠标当前 hover 的 frame 索引
			keyOn: false, // 是否开启键盘交互
		};
	},

	watch: {
		option: {
			immediate: false,
			deep: true,
			handler(val) {
				if (val) this.$forceUpdate();
			},
		},
	},
	created() {
		if (this.editTarget) {
			this.option = JSON.parse(JSON.stringify(this.editTarget.item));
			this.editIndex = this.editTarget.index;
			if (this.editTarget.item.keyFrames.length > 0) {
				this.interval = 3;
				this.duration = this.editTarget.item.keyFrames[this.editTarget.item.keyFrames.length - 1].time
			}
		} else this.option = JSON.parse(JSON.stringify(defaultOption));

		if (!this.option.id) this.option.id = "cameraTour" + Date.now();
	},
	beforeDestroy() {
		this.stop();
	},
	methods: {
		// 键盘控制 开关
		openKey() {
			this.keyOn = !this.keyOn;
			window.sealAPI._camera.addKeyEvent(
				this.keyOn
			);
		},

		// 导览播放 开关
		playSwitch() {
			if (this.playCameraTour) {
				this.stop();
				return;
			}
			this.checkCur = {};
			if (this.option.keyFrames.length < 2) return;

			this.$nextTick(async () => {
				let frames = [];
				this.option.keyFrames.forEach((it) => {
					frames.push(
						new CameraTourKeyFrame(it.index, it.time, it.location, it.rotation)
					);
				});

				let cameraTour = new CameraTourData(
					this.option.id,
					this.option.name ? this.option.name : "cameraTemp",
					frames
				);
				await window.sealAPI._cameraTour.add(cameraTour);
				await window.sealAPI._cameraTour.play(this.option.id);
				this.playCameraTour = true;
				this.playtourtimer = setTimeout(() => {
					this.playCameraTour = false;
					this.playtourtimer = null;
				}, this.option.time * 1000);
			});
		},
		// 停止播放
		async stop() {
			this.checkCur = {};

			if (this.playCameraTour) {
				this.playCameraTour = false;
				if (this.playtourtimer) clearTimeout(this.playtourtimer);
				this.playtourtimer = null;

				await window.sealAPI._cameraTour.stop_biz();
				await window.sealAPI._cameraTour.delete(this.option.id);
			}
		},

		// 调整画面时间间隔
		timeStep(step) {
			if (this.interval <= 0 && step < 0) return;
			this.interval += step;
		},
		// 新增frame
		addkeyFrame() {
			if (this.interval === 0 && this.option.keyFrames.length > 0)
				return this.$message("相邻两帧时间不能为0秒");
			this.setKeyFrames(1);
		},
		// 删除frame
		deleteKey(i) {
			this.option.keyFrames.splice(i, 1);
			this.$nextTick(() => {
				this.keyFramesReset();
			});
		},
		// 重置frame
		resetKey(i) {
			this.setKeyFrames(2, i);
		},
		// 插入frame
		insetKey(i) {
			this.setKeyFrames(3, i);
		},

		/** frame 构造与操作
		 * @param {*} type 1 新增 2 更新 3 插入
		 * @param {*} i 非新增索引
		 * @param {*} newTime 新的时间间隔
		 */
		async setKeyFrames(type, i) {
			this.checkCur = {};

			let res = await window.sealAPI._camera.get();
			let location = res.camera.slice(0, 3);
			let rotation = res.camera.slice(3, 6);
			let image = this.getCurCameraImage();

			if (this.interval <= 0) this.interval = 3;

			if (!this.option.keyFrames.length) this.duration = 0;
			else this.duration += this.interval;

			let frame = {
				index: this.option.keyFrames.length,
				time: this.duration,
				location,
				rotation,
				image,
				interval: !this.option.keyFrames.length ? 0 : this.interval,
			};

			if (type === 1) {
				this.option.keyFrames.push(frame);
				this.option.time = this.duration;
			} else if (type === 2) {
				this.option.keyFrames.splice(i, 1, { ...frame });
			} else if (type === 3) {
				this.option.keyFrames.splice(i + 1, 0, { ...frame });
			}

			if (type !== 1)
				this.$nextTick(() => {
					this.keyFramesReset();
				});
		},
		// 获取当前画面
		getCurCameraImage() {
			let video = document
				.getElementById(window.RTCPlayer.divId)
				.querySelector("video");
			let canvas = document.createElement("canvas");
			let scale = video.videoWidth / video.videoHeight;
			canvas.width = 300;
			canvas.height = parseInt(300 / scale);
			canvas
				.getContext("2d")
				.drawImage(video, 0, 0, canvas.width, canvas.height);
			let base64 = canvas.toDataURL("image/png", 0.7);
			return base64;
		},
		// keyFrames 重新计算
		keyFramesReset() {
			this.stop();

			let frameList = [...this.option.keyFrames];
			frameList.forEach((item, index) => {
				let time;
				if (index == 0) {
					time = 0;
					item.interval = 0;
				} else time = item.interval + this.option.keyFrames[index - 1].time;
				this.option.keyFrames.splice(index, 1, { ...item, index, time });
			});

			this.duration =
				this.option.keyFrames[this.option.keyFrames.length - 1].time;
			this.option.time = this.duration;
		},

		// 导览 keyFrames 列表横向滚动
		arrow(step) {
			let bool1 = this.marginLeftList >= 0 && step > 0;
			let bool2 =
				this.marginLeftList <= -this.option.keyFrames.length * 243 && step < 0;

			if (bool1 || bool2) return;

			this.checkCur = {};
			this.marginLeftList += step * 243;
		},

		// 画面单机
		async handleFrameClick(item) {
			await this.stop();
			if (this.checkCur.index === item.index) return;
			this.interval = item.interval;
			this.checkCur = item;
			window.sealAPI._camera.set(...item.location, item.rotation[0], item.rotation[1], 1);
		},
		async sure() {
			await this.stop();
			let checkedIds = window.Player.$refs.tree.getSelectedTreeIds();
			let opCheckedIds = this.option.checkedIds || []
			if (this.editTarget) {
				if (checkedIds.length != opCheckedIds.length) {
					this.option.checkedIds = checkedIds;
				} else {
					let target = false
					checkedIds.forEach(id => {
						target = !opCheckedIds.includes(id)
					})
					if (target) this.option.checkedIds = checkedIds;
				}
				this.editGuide(this.option, this.$parent.index);
			} else {
				if (!this.option.name) this.option.name = "导览" + Date.now();
				this.option.checkedIds = checkedIds;
				this.saveGuide(this.option);
			}
		},
		// 编辑导览到后端数据库
		async editGuide(item, index) {
			let res = await put(`${cim_main}service/scene/props?token=${this.$route.query.token}`, {
				token: this.$route.query.token,
				sceneid: this.$route.query.mediaid,
				type: "guide",
				description: JSON.stringify(item),
				id: this.editTarget.item.id
			});
			if (res.message === "1") this.$emit("cancelEdit", true);
			else this.$message.error("保存失败！");
		},
		// 保存导览到后端数据库
		async saveGuide(obj) {
			let res = await post(`${cim_main}service/scene/props?token=${this.$route.query.token}`, {
				token: this.$route.query.token,
				sceneid: this.$route.query.mediaid,
				type: "guide",
				description: JSON.stringify(obj)
			});
			if (res.message === "1") this.$emit("cancelEdit", true);
			else this.$message.error("保存失败！");
		},
		async cancle() {
			await this.stop();
			this.$emit("cancelEdit", false);
		},
	},
};
</script>
<style lang="scss" scoped>
.cloud-camera-tour-detail {
	position: fixed;
	bottom: -110px;
	left: 104px;
	transform: translateY(-50%);
	z-index: 1000;
	width: 1784px;
	height: 251px;
	background: rgba(46, 52, 65, 0.8);
	box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
		0px 0px 0px 0px rgba(0, 0, 0, 0.5), 0px 2px 5px 0px rgba(38, 38, 44, 0.5);
	border-radius: 15px;

	::v-deep .panel-title {
		height: 56px;
		line-height: 56px;
		font-size: 22px;
		color: #ffffff;
		display: flex;
		align-items: center;
		padding: 0 25px;
		box-sizing: border-box;
		background: rgba(46, 52, 65, 0.8);
		border-top-left-radius: 15px;
		border-top-right-radius: 15px;

		::v-deep.top-left {
			// float: left;
			.el-icon-check {
				position: absolute;
				margin-top: 17px;
				margin-left: 10px;
			}

			input::-webkit-input-placeholder {
				color: #9b9ea3;
			}

			.el-input__inner {
				background: transparent;
				height: 32px;
				background: #494d52;
				border-radius: 4px;
				border: 1px solid #646b6f;
				font-size: 14px;
				color: #ffffff;
			}
		}

		.top-right {
			position: absolute;
			right: 20px;

			>span i:hover {
				cursor: pointer;
				color: #02b2ff;
			}
		}

		>span {
			cursor: pointer;
			display: inline-block;
			width: 22px;
			height: 22px;
			background: rgba(0, 0, 0, 0.5);
			box-shadow: 0px 0px 5px 0px rgba(0, 0, 0, 0.5);
			border: 1px solid #ffffff;
			border-radius: 50%;
			display: flex;
			justify-content: center;
			align-items: center;

			i {
				font-size: 8px;
			}
		}
	}

	::v-deep .panel-warp {
		// padding: 10px 25px;
		// padding-bottom: 20px;
		box-sizing: border-box;

		.warp-list {
			margin-left: 20px;
			width: 1600px;
			display: inline-flex;
			color: #fff;

			.wrap-center {
				margin-bottom: 0 !important;
				overflow: hidden;
				width: 1480px;
				height: 150px;
				display: -webkit-inline-box;
				padding-top: 40px;

				.list {
					width: 233px;
					height: 131px;
					float: left;
					box-sizing: border-box;
					border: 2px solid #ddd;
					margin-left: 10px;
					cursor: pointer;

					.camera-img {
						width: 100%;
						height: 100%;
					}

					.el-dropdown {
						float: right;
					}

					.i-reset {
						position: relative;
						margin-top: -30px;
						margin-right: 100px;
						display: inline-block;
						font-size: 1.5rem;
						float: right;
					}

					.i-inset {
						position: relative;
						margin-top: -30px;
						margin-right: -18px;
						display: inline-block;
						font-size: 1.5rem;
						float: right;
					}

					.i-delete {
						position: absolute;
						margin-top: 5px;
						margin-left: 210px;
						display: inline-block;
						float: right;
					}

					.i-delete:hover {
						color: red;
					}

					.i-number {
						position: absolute;
						margin-left: 3px;
						margin-top: 3px;
					}
				}

				.list_check {
					border: 2px solid #239afe;
				}

				.list:last-of-type {
					background-color: #d8d8d8;

					svg {
						width: 48px;
						height: 48px;
						margin: 41.5px 92.5px 41.5px 92.5px;
						border: 2px dashed #999;
					}
				}

				.list:hover {
					border: 2px solid #ff0000;
				}
			}

			>span {
				cursor: pointer;
				margin-top: 80px;

				i {
					font-size: 2rem;
				}

				i:hover {
					color: #239afe;
				}
			}

			.el-progress {
				width: 100%;
				margin-top: 80px;

				.el-progress-bar {
					padding-right: 0;
				}
			}
		}

		.warp-footer {
			// display: flex;
			justify-content: space-between;
			font-size: 16px;
			margin-top: 60px;
			float: right;
			width: 150px;

			div {
				margin: 2.5%;
				float: left;
				cursor: pointer;
				width: 64%;
				height: 40px;
				line-height: 40px;
				color: white;
				text-align: center;
				border-radius: 5px;
				border: 1px solid #029eff;
			}

			div:last-of-type {
				background-color: #029eff;
			}
		}

		.val-sty {
			.warp-border {
				border-bottom: 1px solid rgba(255, 255, 255, 0.1);
				padding-bottom: 10px;
				padding-top: 10px;
			}

			>div {

				// margin-bottom: 20px;
				p {
					width: 100px;
					font-size: 16px;
					color: #ffffff;
					margin-bottom: 8px;
				}

				input::-webkit-input-placeholder {
					color: #9b9ea3;
				}

				.el-input__inner {
					background: transparent;
					height: 32px;
					background: #494d52;
					border-radius: 4px;
					border: 1px solid #646b6f;
					font-size: 14px;
					color: #ffffff;
				}

				.el-select {
					width: 100%;
				}

				.el-select .el-input.is-focus .el-input__inner {
					border-color: #646b6f;
				}

				.el-input__icon {
					line-height: 32px;
				}

				display: flex;
				justify-content: space-between;
				align-items: center;
				padding-left: 12px;

				.el-slider {
					width: 100%;
				}

				.el-input-number {
					width: 136%;
					margin-left: 20px;

					.el-input__inner {
						border-radius: 4px;
						outline: none;
						height: 32px;
						color: #ffffff;
						border: 1px solid #646b6f;
						background: #494d52;
						padding-left: 7px;
						padding-right: 22px;
					}

					.el-input__inner:focus {
						outline: none;
						border-radius: 4px;
						border: 1px solid;
						border-color: #02b2ff;
					}
				}

				.el-input-number.is-controls-right .el-input-number__decrease {
					right: 1px;
					bottom: 15px;
					top: auto;
					left: auto;
				}

				.el-input-number__decrease,
				.el-input-number__increase {
					width: 22px;
					height: 10px;
					top: 3px;
					padding-right: 10px;
					box-sizing: border-box;
					background: transparent;
					border: none;

					i {
						font-size: 20px;
					}
				}

				.el-icon-arrow-down:before {
					content: "\e790";
				}

				.el-icon-arrow-up:before {
					content: "\e78f";
				}
			}
		}
	}

	.settingOn {
		color: #239afe;
	}
}
</style>