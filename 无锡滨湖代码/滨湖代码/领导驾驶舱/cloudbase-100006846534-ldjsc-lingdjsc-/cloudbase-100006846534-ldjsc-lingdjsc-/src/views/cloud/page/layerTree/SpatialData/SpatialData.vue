<template>
	<div class="application-model">
		<div class="tree-top tree-warp">
			<!--搜索-->
			<div class="tree-search">
				<img :src="require('@/assets/images/cloud/page/tree-1.png')" />
				<el-input type="text" placeholder="搜索数据" v-model="filterText"></el-input>
			</div>
			<el-scrollbar style="height: calc(100% - 70px)" :class="{ switch: $store.getters.sceneInfoData.needSW }">
				<el-tree ref="treeEle" :data="treeDataFilter" :props="defaultTreeProps" :node-key="treeNodeKey"
					default-expand-all :expand-on-click-node="false" :filter-node-method="treeFilterNode" show-checkbox
					@check-change="handleTreeCheckChange">
					<div class="custom-tree-node" slot-scope="{ node, data }">
						<template v-if="data.tableAliasname && data.tableName">
							<div class="treenode">
								<div class="treenode-left">
									<p class="label" :title="data.tableAliasname">
										{{ data.tableAliasname }}
									</p>
								</div>
								<el-dropdown trigger="click">
									<i class="el-icon-more"></i>
									<el-dropdown-menu slot="dropdown" class="tableDrop">
										<el-dropdown-item @click.native="showConfigEvent(data)">显示配置</el-dropdown-item>
										<el-dropdown-item @click.native="detailsEvent(data)">详情</el-dropdown-item>
										<!-- <el-dropdown-item @click.native="analysisEvent(data)">分析</el-dropdown-item> -->
										<!-- TODO: 可以通过 data.dataLevel 和 data.dataType 来判断数据大类和数据类型，决定不同的操作菜单 -->
									</el-dropdown-menu>
								</el-dropdown>
							</div>
						</template>

						<template v-else>
							<p class="label" @click="threeItemClick(data)">{{ node.label }}</p>
						</template>
					</div>
				</el-tree>
			</el-scrollbar>
		</div>

		<SpatialDataConfigTmpl v-if="configVisible" :rowData="configRow" @closeEvent="closeEvent" />
		<SpatialDetailTmpl v-if="detailsVisible" :rowData="detailsRow" @closeEvent="closeEvent" />
	</div>
</template>

<script>
import SpatialDataConfigTmpl from "./SpatialDataConfig.tmpl.vue" // 显示配置-弹窗
import SpatialDetailTmpl from "./SpatialDetailTmpl.vue"; // 详情-弹窗
import SpatialDataModule from './SpatialData.module.js' // 公共业务

import { get, post } from "@/utils/fetch";
import { debounce } from "@/utils/funs.js";

// const dataTreeBuild = (list, pid = "") => {
// 	function getChildren(data, result, pid) {
// 		for (const item of data) {
// 			if (item.pid === pid) {
// 				const newItem = { childrens: [], ...item }
// 				let target = result.find(it => it.id == newItem.id)
// 				if (!target) result.push(newItem)
// 				getChildren(data, newItem.childrens, item.id)
// 			}
// 		}
// 	}

// 	let result = []
// 	getChildren(list, result, pid)
// 	return result
// }

export default {
	name: "SpatialData",
	components: {
		SpatialDataConfigTmpl,
		SpatialDetailTmpl,
	},
	props: ['baseData'],
	// props: {
	// 	baseList: {
	// 		type: Array,
	// 		default: () => []
	// 	},
	// },
	mixins: [SpatialDataModule],
	data() {
		return {
			// baseList: [],
			filterText: "",
			treeDataFilter: [],
			treeNodeKey: "id",
			defaultTreeProps: { children: "childrens", label: "name" },
			// 详情表格
			detailsVisible: false,
			detailsRow: null,
			detailsTable: [],
			// 显示配置
			configVisible: false,
			configRow: null,
			configType: "", // (row.featureType) PointZM、LineString、LinearRing、Polygon、MultiPoint、MultiPolygon、Point
			treeCheckedList: [],
			assembleInfo: {
				// tableName: {
				// 	type: "",
				// 	ids: [],
				// },
			},
			delList: {

			}
		}
	},
	watch: {
		filterText(val) {
			this.$refs.treeEle.filter(val);
		},
	},
	created() {
		this.treeDataFilter = this.baseData || []
		// if (this.baseData) {
		// 	let { baseFolderList, baseDataList } = this.baseData
		// 	let list = this.baseDataDirTreeBuild(baseFolderList, baseDataList)
		// 	this.treeDataFilter = dataTreeBuild(list, '1')
		// }
	},
	mounted() {
		// $Bus.$off("spatial-data-stop");
		// $Bus.$on("spatial-data-stop", () => {
		// 	this.handledRopdown(null, "stop");
		// });
	},
	methods: {
		baseDataDirTreeBuild(baseFolderList, baseDataList) {
			function dataToDir(dirDataTree, dataList) {
				dataList.forEach(row => {
					dirDataTree.forEach((dir, dindex) => {
						if (dir.id == row.catalogueId && dirDataTree[dindex].childrens) {
							let ex = dirDataTree[dindex].childrens.find(it => it.id == row.id)
							if (!ex) dirDataTree[dindex].childrens.push(row)
						} else if (dirDataTree[dindex].childrens) {
							dirDataTree[dindex].childrens = dataToDir(dirDataTree[dindex].childrens, [row])
						}
					})
				})
				console.log('tiejinning_log dirDataTree', dirDataTree);
				return dirDataTree
			}

			if (!baseFolderList || baseFolderList.length == 0) return [...baseDataList]

			let dirDataTree = [...baseFolderList]
			let dataList = [...baseDataList]

			let dirDataTreeFix = dataToDir(dirDataTree, dataList)
			return dirDataTreeFix
		},
		/* Tree Filter */
		treeFilterNode(value, data) {
			if (!value) return true;
			return (data.name || data.tableAliasname).indexOf(value) !== -1;
			// return (data.name).indexOf(value) !== -1;
		},
		/* Tree Check */
		handleTreeCheckChange: debounce(function (e) {
			let nodes = this.$refs.treeEle.getCheckedNodes();
			let checkedList = nodes.filter(o => !o.children);
			let delList = [];
			[...this.treeCheckedList].forEach(item => {
				let delTarget = checkedList.find(it => it.tableName == item.tableName)
				if (!delTarget) delList.push(item);
			})
			this.delList = delList;
			this.treeCheckedList = checkedList;

			this.$nextTick(() => {
				this.clearDelList()
				// TODO 根据数据 进行场景的叠加
				if (this.treeCheckedList.length > 0) {
					this.treeCheckedList.forEach(row => {
						if (this.fusionMethodJudge(row.fusionMethod)) this.getDetailsAll(row)
						else this.getDetails2(row)
					})
				} else {
					this.assembleInfo = {};
					window.origAPI.marker.clear();
					window.origAPI.polyline.clear();
					window.origAPI.polygon.clear();
				}
			})
		}),
		/* 显示配置 */
		showConfigEvent(row) {
			this.closeEvent()
			window.origAPI.marker.clear();
			window.origAPI.polyline.clear();
			window.origAPI.polygon.clear();

			this.$refs.treeEle.setCheckedKeys([])
			setTimeout(() => {
				this.configRow = row
				this.configVisible = true
			}, 1000)
			// this.$nextTick(() => {
			// 	this.configRow = row
			// 	this.configVisible = true
			// })
		},
		/* 详情 */
		detailsEvent(row) {
			this.closeEvent()
			this.$nextTick(() => {
				this.detailsRow = row;
				this.detailsVisible = true;
			})
		},
		/* 分析 TODO */
		analysisEvent(row) {

		},
		/* 关闭弹窗 */
		closeEvent(data) {
			if (this.configVisible) {
				this.configVisible = false
				data && this.saveDataConfig(this.configRow, data);
			}

			if (this.detailsVisible) {
				this.detailsVisible = false
			}
		},
		saveDataConfig(row, config) {
			let formData = new FormData();
			formData.append("tableName", row.tableName);
			formData.append("layerStyle", JSON.stringify(config));
			post(`${cim_main}fusion/updateLayerStyle`, formData).then(res => {
				if (res.message == '1') {
					this.$message.success("保存成功")
					this.$refs.treeEle.setCheckedKeys([row.id]);
				}
				else this.$message.error("保存失败")
			})
		},
		async getDetails2(row) {
			let target = this.assembleInfo[row.tableName]
			if (target) return;
			let configRes = await this.getDataConfig(row)

			let dataConfig;
			if (configRes && configRes.layerStyle) dataConfig = JSON.parse(configRes.layerStyle);
			else dataConfig = this.defaultDataConfig;

			let params = {
				sourceId: row.sourceId,
				tableName: row.tableName,
			}
			get(`${cim_main}fileFusion/getFusionFile`, params).then(res => {
				let list = res.message
				this.markerHelper(list, dataConfig, row.tableName);
			})
		},
		async getDetailsAll(row) {
			let target = this.assembleInfo[row.tableName]
			if (target) return;

			let configRes = await this.getDataConfig(row)
			let dataConfig;
			if (configRes && configRes.layerStyle) dataConfig = JSON.parse(configRes.layerStyle);
			else dataConfig = this.defaultDataConfig;
			let params = {
				tableName: row.tableName,
				start: 1,
				limit: 999999999,
			}
			get(`${cim_main}fusion/getExcelDataList`, params).then(res => {
				let { data, field } = res.message;
				if (data.list.length > 0) {
					let featureType = this.getFeatType(row.featureType)
					switch (featureType) {
						case "点":
							this.markerHelper(data.list, dataConfig, row.tableName);
							break;
						case "线":
							this.polyLineHelper(data.list, dataConfig, row.tableName);
							break;
						case "面":
							this.polygonHelper(data.list, dataConfig, row.tableName);
							break;
					}
				} else this.$message.warning("未查询代数据信息")
			})
		},
		clearDelList() {
			this.delList.forEach(row => {
				let target = this.assembleInfo[row.tableName]
				if (target) {
					switch (target.type) {
						case "点":
							// window.origAPI.marker.clear();
							window.origAPI.marker.delete(target.ids)
							break;
						case "线":
							// window.origAPI.polyline.clear();
							window.origAPI.polyline.delete(target.ids)
							break;
						case "面":
							// window.origAPI.polygon.clear();
							window.origAPI.polygon.delete(target.ids)
							break;
					}
					this.$set(this.assembleInfo, row.tableName, null)
				}
			})
			this.delList = [];
		},
		markerHelper(list, dataConfig, tableName) {
			let markerOptions = list.map(item => {
				// let coordinate = item.th_geom.split("(")[1].split(")")[0].split(" ");
				// coordinate.length = 3;
				// coordinate = coordinate.map(o => Number(o));
				let coordinate =
					[item.model_x, item.model_y, 5]
				if (item.model_z) coordinate[2] = item.model_z

				if (!item.model_x) {
					coordinate = [item.modelX, item.modelY, item.modelZ,]
				}

				if (!coordinate[0] || !coordinate[1]) return null;

				let option = {
					...this.defalutOption_Point,
					id: `mk-${item.id || item.grid_id}`,
					text: item.feature_name || "标题缺失",
					coordinate,
				}

				if (dataConfig) {
					let model_z = item[dataConfig.heightField]
					if (isNaN(model_z)) {
						model_z = 5
					}
					coordinate[2] = Number(model_z);
					option = {
						...option,
						coordinate,
						text: item[dataConfig.titleField] || item.name || "标题缺失",
						fontSize: dataConfig.fontSize,
						fontColor: dataConfig.fontColor,
						fontOutlineColor: dataConfig.fontOutlineColor,
						textBackgroundColor: dataConfig.textBackgroundColor,
						textRange: dataConfig.textRange,
						range: dataConfig.markerRange,
						imagePath: this.markerIcons[dataConfig.imageIndex].url,
						showLine: dataConfig.showLine,
						lineColor: dataConfig.lineColor,
						lineSize: dataConfig.lineSize,
						lineOffset: dataConfig.lineOffset,
						occlusionCull: dataConfig.occlusionCull,
					}
					// 添加视频融合信息
					if (item.fusion_obj) option['userData'] = JSON.stringify({ fusionObj: item.fusion_obj })
				}
				return option;
			})

			let markerOptionsFix = markerOptions.filter(ii => !!ii);
			if (markerOptionsFix.length > 0) {
				this.$nextTick(() => {
					let ids = markerOptionsFix.map(it => it.id);
					this.$set(this.assembleInfo, tableName, { type: "点", ids })
					window.origAPI.marker.delete(ids, () => {
						window.origAPI.marker.add(markerOptionsFix, () => {
							window.origAPI.marker.focusAll()
						})
					})
				})
			}
		},
		polyLineHelper(list, dataConfig, tableName) {
			let polyLineOptions = list.map(item => {
				let coordArr = item.th_geom.split("((")[1].split("))")[0].split(",")

				let coordinates = coordArr.map(str => {
					let arr = str.split(" ");
					return arr.map(it => Number(it));
				})

				let option = {
					...this.defalutOption_Line,
					id: `pline-${item.id || item.grid_id}`,
					coordinates,
					thickness: dataConfig.thickness,
					color: dataConfig.polyLineColor,
					shap: dataConfig.polyLineShape,
					depthTest: dataConfig.depthTest,
				}
				return option
			})

			this.$nextTick(() => {
				let ids = polyLineOptions.map(it => it.id);
				this.$set(this.assembleInfo, tableName, { type: "线", ids })
				window.origAPI.polyline.delete(ids, () => {
					window.origAPI.polyline.add(polyLineOptions, () => {
						// window.origAPI.polyline.focus(ids)
					})
				})
			})

		},
		polygonHelper(list, dataConfig, tableName) {
			let polygonOptions = list.map(item => {
				let coordArr = item.th_geom.split("(((")[1].split(")))")[0].split(",")

				let coordinates = coordArr.map(str => {
					let arr = str.split(" ");
					return arr.map(it => Number(it));
				})

				let option = {
					...this.defalutOption_Plane,
					id: `plgon-${item.id || item.grid_id}`,
					coordinates,
					color: dataConfig.polygonColor,
					depthTest: dataConfig.depthTest,
					style: dataConfig.polygonStyle,
				}

				return option
			})

			this.$nextTick(() => {
				let ids = polygonOptions.map(it => it.id);
				this.$set(this.assembleInfo, tableName, { type: "面", ids })
				window.origAPI.polygon.delete(ids, () => {
					window.origAPI.polygon.add(polygonOptions, () => {
						// window.origAPI.polygon.focus(ids, 1000)
					})
				})
			})
		},
	}
}

</script>

<style lang="scss">
@import "../css/upload-3dt.scss";
</style>

<style lang="scss" scoped>
.locateActive {
	color: #029eff !important;
}

/deep/ .el-tree {
	.treenode {
		width: 90% !important;
	}
}

.application-model {
	height: 100%;

	.el_dropdown {
		background: rgba(46, 52, 65, 0.9);
		box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.4),
			0px 0px 0px 0px rgba(0, 0, 0, 0.5), 0px 2px 5px 0px rgba(38, 38, 44, 0.5);
		border: 0;
		border-radius: 2px;

		.el-dropdown-menu__item {
			font-size: 16px;
			font-weight: 400;
			color: #ffffff;
			//   &:hover {
			//     background: #aed6ff33;
			//     color: #ffffff;
			//   }
		}

		.popper__arrow {
			border-bottom-color: transparent !important;

			&::after {
				border-bottom-color: rgba(46, 52, 65, 0.9) !important;
			}
		}
	}

	.tree-warp {
		flex: 1;
		padding: 16px;
		box-sizing: border-box;
	}

	.tree-top {
		height: 100%;
		overflow: hidden;
		border-bottom: 1px solid rgba($color: #ffffff, $alpha: 0.2);

		/* search */
		.tree-search {
			display: flex;
			align-items: center;
			margin-bottom: 5px;
			padding-bottom: 5px;
			border-bottom: 1px solid rgba($color: #ffffff, $alpha: 0.2);

			img {
				width: 24px;
				height: 24px;
			}

			/deep/ .el-input {
				.el-input__inner {
					width: 100%;
					border: none;
					outline: none;
					font-size: 16px;
					color: #9ea1a7;
					padding-left: 5px;
					background: transparent;
				}

				.el-input__inner::-webkit-input-placeholder {
					font-size: 16px;
					color: #9ea1a7;
				}
			}
		}

		/* 添加3dt数据 */
		.add3dt {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			white-space: nowrap;
			color: #d8d8d8;
			text-align: center;
			cursor: pointer;

			&>div:first-child {
				margin: 0px auto;
				display: flex;
				align-items: center;
				justify-content: center;
				width: 146px;
				height: 91px;
				border-radius: 7px;
				border: 1px solid #979797;
				font-size: 28px;
			}

			p {
				margin: 20px 0 0 0;
				font-size: 16px;
				font-weight: 400;
				color: #9ea1a7;
				line-height: 25px;
			}
		}

		/* 添加3dt */
		/deep/.list_3dt {
			font-size: 16px;
			font-weight: 400;
			color: #ffffff;
			height: 22px;
			line-height: 22px;

			.add {
				color: #9ea1a7;
				margin: 20px 0;
				cursor: pointer;
			}

			.item_3dt {
				&:not(:first-child) {
					margin-top: 8px;
				}

				&.checked {
					background: #aed6ff33;
				}

				background: #aed6ff33;
				border-radius: 8px;

				&>.el-row {
					line-height: 50px;
					padding: 0 8px 0 16px;
				}

				.el-dropdown {
					cursor: pointer;
					padding: 0 4px;
					font-size: 16px;
					color: #b5b4bf;
					transform: rotate(90deg);
					-ms-transform: rotate(90deg);
					-moz-transform: rotate(90deg);
					-webkit-transform: rotate(90deg);
					-o-transform: rotate(90deg);
				}

				/* 进度条
        .schedule {
          text-align: center;
          padding-bottom: 10px;
          .el-progress {
            .el-progress-bar__outer {
              background-color: #979797;
            }
            .el-progress__text {
              display: none;
            }
          }
          & > span {
            font-size: 14px;
            font-weight: 400;
            color: #9ea1a7;
          }
        } */
				.upload {
					float: right;
					font-size: 12px;
					opacity: 0.6;
				}
			}
		}

		/* 应用场景 */
		/deep/.application_scenario {
			.el-color-picker {
				width: 100%;
			}

			.el-color-picker__trigger {
				width: 100% !important;
			}
		}

		/* scrollbar */
		/deep/.el-scrollbar {
			box-sizing: border-box;

			.el-scrollbar__wrap {
				overflow-x: hidden;

				/* 设置滚动条的样式 */
				&::-webkit-scrollbar {
					background-color: rgba(46, 52, 65, 0.2);
				}

				/* 滚动条滑块 */
				&::-webkit-scrollbar-thumb {
					background-color: rgba(46, 52, 65, 0.4);
				}

				.el-scrollbar__view {
					.el-tree {
						background: transparent;
						color: #eeeeee;

						// &>.el-tree-node>.el-tree-node__children>.el-tree-node>.el-tree-node__children>.el-tree-node {
						//   &>.el-tree-node__content {
						//     padding-left: 72px !important;

						//     .el-tree-node__expand-icon {
						//       display: none;
						//     }
						//   }

						//   &>.el-tree-node__children {
						//     display: none;
						//   }
						// }
					}

					.el-tree-node:focus>.el-tree-node__content {
						background-color: rgba(0, 0, 0, 0.3);
					}

					.el-tree-node.is-current>.el-tree-node__content {
						background-color: rgba(0, 0, 0, 0.3);
					}

					/*.el-tree-node.is-current > .el-tree-node__content > .el-tree-node__label{
            color: #02b2ff;
          }*/
					.el-tree-node__content:hover {
						background: rgba(0, 0, 0, 0.2) !important;
					}

					.el-tree-node__expand-icon {
						color: #d8d8d8;
					}

					.el-tree-node__expand-icon.is-leaf {
						color: transparent;
					}

					.el-checkbox__inner:hover {
						border-color: transparent;
					}

					.el-checkbox__input.is-checked .el-checkbox__inner {
						/* 边框选中 */
						border-color: transparent;
						background: url("~@/assets/images/cloud/page/tree-2.png") no-repeat;
						border: none;
						background-size: 100% 100%;
					}

					.el-checkbox__input.is-indeterminate .el-checkbox__inner {
						/* 非全选半选中 */
						border-color: transparent;
						background: url("~@/assets/images/cloud/page/tree-4.png") no-repeat;
						border: none;
						background-size: 100% 100%;
					}

					.el-checkbox__inner {
						width: 24px;
						height: 24px;
						background: url("~@/assets/images/cloud/page/tree-3.png") no-repeat;
						border: none;
						background-size: 100% 100%;
					}

					.el-checkbox__inner::after {
						border-color: transparent;
					}
				}
			}
		}

		/deep/ .switch {
			.el-scrollbar__wrap {
				margin-right: 0 !important;
				z-index: 999;
			}
		}
	}
}

/deep/ .el-tree-node__content {
	height: 40px;
	padding-left: 18px;
	overflow: hidden;

	.custom-tree-node {
		width: 100%;
		padding-right: 10px;

		.label {
			color: #fff;
			text-overflow: ellipsis;
			width: 150px;
			overflow: hidden;
		}

		.treenode {
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.treenode-left {
				width: calc(100% - 30px);
				display: flex;

				.box {
					display: inline-flex;
					align-items: center;
					font-size: 12px;
					color: #999;
					margin-top: 4px;
				}
			}
		}
	}
}

/deep/ .el-tag--info {
	background: transparent;
}

/deep/ .el-dropdown-selfdefine {
	color: #ccc;
}

.back-box {
	color: #fff;
	font-size: 18px;
	display: flex;
	align-items: center;
	border-bottom: solid 2px #999;
	padding: 5px 0;

	.el-button {
		color: #fff;
		font-size: 18px;
		margin-right: 10px;
	}
}

ul.detail-container {
	padding: 10px 0;
	color: #999;

	li {
		border-radius: 2px;
		padding: 10px;
		display: flex;
		align-items: flex-start;
		justify-content: space-between;

		.treenode-left {
			width: calc(100% - 30px);

			.box {
				display: flex;
				align-items: center;

				.name {
					color: #fff;
					margin-right: 6px;
					max-width: 150px;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}

			.content {
				font-size: 12px;
				padding-top: 2px;
			}
		}
	}

	li.active {
		background: rgba(0, 0, 0, 0.3);
	}
}

/deep/ .el-pagination {
	display: flex;
	margin: 16px auto;
	margin-top: 20px;
	text-align: center;
	position: relative;
	justify-content: center;
	padding: 0;

	.el-pagination__sizes {
		margin-right: 0;
		position: absolute;
		top: 30px;
		margin: 0 auto;
	}
}
</style>