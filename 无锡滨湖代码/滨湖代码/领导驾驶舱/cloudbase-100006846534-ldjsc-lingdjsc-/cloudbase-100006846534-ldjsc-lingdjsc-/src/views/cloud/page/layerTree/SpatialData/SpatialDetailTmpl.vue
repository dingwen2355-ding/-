<template>
	<div>
		<template v-if="fusionMethodJudge(rowData.fusionMethod)">
			<div class="tmpl-container" v-show="tableUnfold">
				<div class="top-btns">
					<el-button type="text" icon="el-icon-d-arrow-right" @click="tableUnfold = false"></el-button>
					<span class="table-aliasname">{{ rowData.tableAliasname }}</span>
					<el-button type="text" icon="el-icon-close" @click="handleClickClose"></el-button>
				</div>
				<el-table ref="multipleTable" empty-text="没有可以列出的数据 :-(" :data="tableData" :height="320"
					style="width: 100%; color: #fff" v-loading="loading">
					<el-table-column header-align="center" align="center" label="序号" type="index" width="80" fixed="left">
						<template slot-scope="{ $index, row }">
							<template v-if="play3DStatus(row)">
								<i class="el-icon-video-play" @click="play3DEvent(row, true)"></i>
							</template>
							<template v-else>
								<span>{{ (currentPage - 1) * pageSize + $index + 1 }}</span>
							</template>
						</template>
					</el-table-column>
					<el-table-column v-for="(item, index) in fields" :key="index" :prop="item.fieldName"
						:label="item.fieldAliasName">
					</el-table-column>
					<el-table-column label="操作" width="100">
						<template slot-scope="{row}">
							<el-dropdown trigger="click">
								<i class="el-icon-more"></i>
								<el-dropdown-menu slot="dropdown" class="tableDrop">
									<el-dropdown-item @click.native="positionEvent(row)">定位</el-dropdown-item>
									<template v-if="row.fusion_obj">
										<template v-if="videoPlayRow && videoPlayRow.grid_id == row.grid_id">
											<el-dropdown-item @click.native="playEvent(row, true)">
												<span>二维暂停</span>
											</el-dropdown-item>
										</template>
										<template v-else>
											<el-dropdown-item @click.native="playEvent(row)">
												<span>二维播放</span>
											</el-dropdown-item>
										</template>
										<template v-if="play3DStatus(row)">
											<el-dropdown-item v-if="row.fusion_obj" @click.native="play3DEvent(row, true)">
												<span>三维暂停</span>
											</el-dropdown-item>
										</template>
										<template v-else>
											<el-dropdown-item v-if="row.fusion_obj" @click.native="play3DEvent(row)">
												<span>三维播放</span>
											</el-dropdown-item>
										</template>
									</template>
								</el-dropdown-menu>
							</el-dropdown>
						</template>
					</el-table-column>
				</el-table>

				<div class="page-wrap">
					<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
						:current-page="currentPage" :page-sizes="[10, 20, 50, 100, 200]" :page-size="pageSize"
						layout="total, prev, pager, next, jumper" :total="total">
					</el-pagination>
					<i class="el-icon-refresh" @click="refreshTableData"></i>
				</div>
			</div>
			<div class="tmpl-container table-fold" v-show="!tableUnfold">
				<div class="top-btns">
					<el-button type="text" icon="el-icon-d-arrow-left" @click="tableUnfold = true"></el-button>
					<el-button type="text" icon="el-icon-close" @click="handleClickClose"></el-button>
				</div>
			</div>
		</template>

		<!-- <template v-else-if="fusionMethod == 'fileUpdate'"> -->
		<template v-else>
			<cloud-tmpl title='详情' :top='120' :right='60' :width='300' :scrollHeight='55' :loading='loading'
				@close='handleClickClose'>
				<!-- 正文插槽-滚动体 -->
				<template v-slot:content>
					<div class='content-box'>
						<div class="file-name">
							<img src="@/assets/images/cloud/page/file2.png" alt="logo" />
							<div>
								<p>{{ detailsInfo.fileName || "文件名缺失" }}</p>
								<span>{{ detailsInfo.fileSuffix || "未知" }} | {{ fileSizsTransform(detailsInfo.fileSize)
								}}</span>
							</div>
						</div>

						<div class="attr-item">
							<span>绑定:layer_Id</span>
							<p>{{ detailsInfo.layerId }}</p>
						</div>
						<div class="attr-item">
							<span>绑定:object_Id</span>
							<p>{{ detailsInfo.objectId }}</p>
						</div>
						<div class="attr-item">
							<span>X</span>
							<p>{{ detailsInfo.modelX }}</p>
						</div>
						<div class="attr-item">
							<span>Y</span>
							<p>{{ detailsInfo.modelY }}</p>
						</div>
						<div class="attr-item">
							<span>Z</span>
							<p>{{ detailsInfo.modelZ }}</p>
						</div>
					</div>
				</template>
				<!-- 底部插槽 -->
				<template v-slot:foot>
					<div class='content-foot'>
						<el-button size="small" type="primary" plain @click="downloadData">下载</el-button>
						<el-button size="small" type="primary" @click="positionEvent2">定位</el-button>
					</div>
				</template>
			</cloud-tmpl>
		</template>

		<VideoDataPlayTmpl v-if="videoPlayRow" :row="videoPlayRow" :gridBindFieldInfo="gridBindFieldInfo"
			@positionEvent="positionEvent" @closeEvent="closeVideoDataPlay" />
	</div>
</template>

<script>
import SpatialDataModule from './SpatialData.module.js' // 公共业务
import VideoDataPlayTmpl from './videoDataPlay.tmpl.vue'
import { get } from "@/utils/fetch";
import { fileSizsTransform } from '@/utils/funs';
import { downloadFile, isNumeric } from "@/utils/util"
export default {
	props: ["rowData"],
	components: { VideoDataPlayTmpl },
	mixins: [SpatialDataModule],
	data() {
		return {
			title: '',
			// ----------------------
			loading: false,
			tableData: [],
			fields: [],
			detailsInfo: {},
			// 分页相关
			currentPage: 1, //当前页数
			pageSize: 10, //每页显示个数
			total: 0, //总条目数
			fusionMethod: "", // batchUpdate(指定字段),singleUpdate（赋坐标）,featureUpdate（要素绑定）,fileUpdate(图文关联), null 
			currList: [],
			videoPlayRow: null,// 二维播放
			gridBindFieldInfo: null,
			videoFusionPlay: null,
			multipleSelection: [],
			videoFusionPlayItems: [],
			tableUnfold: true,
		};
	},
	computed: {
		play3DStatus() {
			return (row) => {
				let target = this.videoFusionPlayItems.find(item => item.grid_id == row.grid_id)
				return !!target
			}
		}
	},
	watch: {
		multipleSelection: {
			deep: true,
			handler(val) {
				let list = [...val]
				if (list.length > 2) list = [val[0], val[1]]
				this.videoFusionPlayItems = list
			}
		},
		videoFusionPlayItems: {
			deep: true,
			handler(val) {
				if (val.length <= 2) {
					sessionStorage.setItem("videoFusionPlayInfo", JSON.stringify(val))
					return
				};
				setTimeout(() => {
					let list = [...val];
					let outRow = list.shift();
					this.videoFusionPlayItems = list;
					sessionStorage.setItem("videoFusionPlayInfo", JSON.stringify(list))
					window.origAPI.videoProjection.delete(outRow.fusionObj.id);
				}, 500)
			}
		}
	},
	created() {
		window.isNumeric = isNumeric;

		let videoFusionPlayInfo = sessionStorage.getItem("videoFusionPlayInfo")
		if (videoFusionPlayInfo) this.videoFusionPlayItems = JSON.parse(videoFusionPlayInfo);
		else window.origAPI.videoProjection.clear();

		let fusionMethod = this.rowData.fusionMethod
		this.fusionMethod = this.rowData.fusionMethod

		switch (fusionMethod) {
			case "fileUpdate":
				this.getDetails2();
				break;
			case "batchUpdate":
			case "singleUpdate":
			default:
				this.handleCurrentChange(1)
		}
	},
	mounted() {
		this.getGridBindField(this.rowData.tableName);
	},
	beforeDestroy() {
		window.origAPI.tileLayer.stopHighlightAllActors()

		let { layerId, objectId } = this.detailsInfo
		if (layerId && objectId) window.sealAPI._tileLayer.stopHighlightActor(layerId, objectId);

		// if (this.videoFusionPlay) window.origAPI.videoProjection.delete(this.videoFusionPlay.id);

		if (this.currList.length <= 0) return;
		let ids = [];
		switch (this.featureType) {
			case "点":
				ids = this.currList.map(item => `mk-${item.id || item.grid_id}`);
				window.origAPI.marker.delete(ids);
				break;
			case "线":
				ids = this.currList.map(item => `pline-${item.id || item.grid_id}`);
				window.origAPI.polyline.delete(ids);
				break;
			case "面":
				ids = this.currList.map(item => `plgon-${item.id || item.grid_id}`);
				window.origAPI.polygon.delete(ids);
				break;
		}
		this.currItem = {
			type: "",
			row: null,
		}
	},
	methods: {
		fileSizsTransform,
		// play3DStatus(row) {
		// 	let target = this.videoFusionPlayItems.find(item => item.id == row.id || item.grid_id == row.grid_id)
		// 	return !!target
		// },
		// -----------------------------
		getDetails2() {
			// if (this.loading) return;
			this.loading = true;

			let params = {
				sourceId: this.rowData.sourceId,
				tableName: this.rowData.tableName,
			}
			get(`${cim_main}fileFusion/getFusionFile`, params).then(res => {
				this.detailsInfo = res.message[0] || {}
				this.loading = false;
			})

		},
		// 获取绑定字段信息
		getGridBindField(tableName) {
			get(`${cim_main}fusion/getGridBindField`, { tableName }).then(res => {
				let info = res.message;
				if (info) this.gridBindFieldInfo = info;
			})
		},
		/* 操作-二维播放 */
		playEvent(row, isPause = false) {
			this.videoPlayRow = null;
			if (isPause) return;

			setTimeout(() => {
				this.videoPlayRow = row;
			}, 300)
		},
		/* 操作-三维播放 */
		play3DEvent(row, isPause = false) {
			let { fusion_obj } = row;
			if (!fusion_obj) return;
			let fusionObj = JSON.parse(fusion_obj);

			if (isPause) {
				window.origAPI.videoProjection.delete(fusionObj.id);
				let targetIndex = this.videoFusionPlayItems.findIndex(item => item.id == row.id || item.grid_id == row.grid_id)
				if (targetIndex > -1) this.videoFusionPlayItems.splice(targetIndex, 1)
				return
			}

			this.videoPlayRow = null;

			let videoFusionPlayItem = {
				id: row.id,
				grid_id: row.grid_id,
				fusionObj: {
					id: fusionObj.id
				}
			}
			this.videoFusionPlayItems.push(videoFusionPlayItem)
			setTimeout(() => {
				window.origAPI.videoProjection.delete(fusionObj.id, () => {
					window.origAPI.videoProjection.add(fusionObj, () => {
						window.origAPI.videoProjection.focus(fusionObj.id, 30, 2);
					});
				});
			}, 500);
		},
		/* 融合播放关闭 */
		closeVideoDataPlay() {
			this.videoPlayRow = null;
		},
		/* 操作-定位 */
		locationEvent(row) {
			if (!isNumeric(row.model_x)) {
				this.$message.warning("model_x 字段值不是数字，请检查数据！！！");
				return;
			}
			if (!isNumeric(row.model_y)) {
				this.$message.warning("model_y 字段值不是数字，请检查数据！！！");
				return;
			}
			if (!isNumeric(row.model_z)) {
				this.$message.warning("model_z 字段值不是数字，请检查数据！！！");
				return;
			}
			window.sealAPI._camera.lookAt(row.model_x, row.model_y, row.model_z, 200, -45);
			return true;
		},
		positionEvent2() {
			let { layerId, objectId } = this.detailsInfo
			if (layerId && objectId) {
				window.sealAPI._tileLayer.focusActor(layerId, objectId)
				window.sealAPI._tileLayer.highlightActor(layerId, objectId)
			} else this.$message.error('无法定位')
		},
		downloadData() {
			let row = this.rowData
			if (!row) return this.$message.error("无法下载");
			let url = `${cim_main}fusion/downloadExcel?tableName=${row.tableName}&token=${this.$store.state.login.account.token}`
			// downloadFile(url, row.tableAliasname, 'xls');
			window.open(url, "_blank")
		},
		getDetails() {
			if (this.loading) return;
			this.loading = true;
			this.tableData = [];

			let params = {
				tableName: this.rowData.tableName,
				start: this.currentPage,
				limit: this.pageSize,
			}
			get(`${cim_main}fusion/getExcelDataList`, params).then(res => {
				let { data, field } = res.message;
				this.fields = field;
				this.tableData = data.list;
				this.total = data.total;
				this.loading = false;
			})
		},
		async positionEvent(item) {
			let configRes = await this.getDataConfig(this.rowData)
			let dataConfig;
			if (configRes && configRes.layerStyle) dataConfig = JSON.parse(configRes.layerStyle);
			else dataConfig = this.defaultDataConfig;

			let featureType = this.getFeatType(this.rowData.featureType)
			this.featureType = featureType;
			let target = this.currList.find(it => it.id == item.id || it.grid_id == item.grid_id);
			if (!target) this.currList.push(item);
			switch (featureType) {
				case "点":
					if (!item.model_x || !item.model_y) {
						// this.$message.warning("数据坐标无效！");
						if (item.layer_id && item.object_id) {
							window.sealAPI._tileLayer.focusActor(item.layer_id, item.object_id)
							window.sealAPI._tileLayer.highlightActor(item.layer_id, item.object_id)
						} else this.$message.warning("无有效绑定信息，无法定位")
						return;
					}
					this.markerHelperOne(item, dataConfig, true)
					break;
				case "线":
					this.polyLineHelperOne(item, dataConfig)
					break;
				case "面":
					this.polygonHelperOne(item, dataConfig)
					break;
			}
		},
		// 刷新表格数据
		refreshTableData() {
			this.handleCurrentChange(1);
		},
		handleSelectionChange(val) { },
		toggleSelection(rows) {
			if (rows) {
				rows.forEach(row => {
					this.$refs.multipleTable.toggleRowSelection(row);
				});
			} else {
				this.$refs.multipleTable.clearSelection();
			}
		},
		// 选择每页多少条
		handleSizeChange(val) {
			this.pageSize = val;
			this.getDetails()
		},
		// 切换页码
		handleCurrentChange(val) {
			this.currentPage = val;
			this.getDetails()
		},
		handleClickClose() {
			this.$emit('closeEvent')
		},
	},
};
</script>

<style scoped lang="scss">
.tmpl-container {
	position: fixed;
	right: 30px;
	bottom: 20px;
	background: rgba(46, 52, 65, 0.8);
	border-radius: 10px;
	padding: 0 20px;
	width: calc(100vw - 460px);

	.top-btns {
		display: flex;
		align-items: center;
		justify-content: space-between;

		.el-button {
			font-size: 20px;
		}

		.el-icon-d-arrow-right,
		.table-aliasname {
			color: #ccc !important;
		}
	}
}

.table-fold {
	width: 100px;
}

/deep/ .el-table {
	width: 100%;

	.el-loading-mask {
		background-color: transparent;
	}

	.el-checkbox__input {
		.el-checkbox__inner {
			background-color: transparent;
		}
	}

	.el-icon-video-play {
		cursor: pointer;
		color: #409EFF;
	}
}

// 滚动条的宽度
/deep/ .el-table__body-wrapper::-webkit-scrollbar {
	width: 90%; // 横向滚动条
	height: 10px; // 纵向滚动条 必写
}

// 滚动条的滑块
/deep/ .el-table__body-wrapper::-webkit-scrollbar-thumb {
	background-color: rgba(144, 147, 153, 0.5);
	border-radius: 3px;
}

/*最外层透明*/
/deep/ .el-table,
/deep/ .el-table__expanded-cell {
	background-color: transparent !important;
}

/* 表格内背景颜色 */
/deep/ .el-table th,
/deep/ .el-table tr,
/deep/ .el-table td {
	background-color: transparent !important;
	border: 0; //去除表格
}

/*去除底边框*/
/deep/.el-table td.el-table__cell {
	border: 0;
}

/deep/.el-table th.el-table__cell.is-leaf {
	border: 0;
}

/deep/ .el-table__fixed::before {
	background-color: transparent !important;
}

/deep/ .el-table::before {
	background-color: transparent !important;
}

/deep/ .el-table {
	.el-button {
		.el-icon-location {
			font-size: 16px;
		}
	}
}

/deep/.el-pagination {
	.el-pager {
		.number {
			background-color: transparent;
			color: #999;
		}

		.active {
			color: #eee;
		}

		.btn-quicknext {
			background: transparent;
			color: #999;
		}
	}

	button {
		background: transparent;
		border: 0;
		color: #999;
	}


	input.el-input__inner {
		background-color: transparent;
		border-color: #999;
		color: #eee;
	}
}

.content-box {

	.file-name {
		border: solid 1px #666;
		border-radius: 10px;
		padding: 12px;
		display: flex;
		align-items: flex-start;

		img {
			margin-right: 12px;
			width: 44px;
			height: 44px;
		}

		p {
			font-size: 18px;
			width: 100%;
			word-wrap: break-word;
		}

		span {
			color: #999;
			font-size: 14px;
		}

	}

	.attr-item {
		padding: 16px 0;

		span {
			color: #999;
		}

		p {
			margin-top: 5px;
			width: 100%;
			word-wrap: break-word;
		}
	}

}

.content-foot {
	display: flex;
	align-items: center;
	justify-content: space-evenly;

	.el-button {
		width: 40%;
	}

	.el-button:nth-child(1) {
		background-color: transparent;
		border-color: #409EFF;
	}

}
</style>