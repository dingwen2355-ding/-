<template>
	<cloud-tmpl :title='title' :top='top' :right='60' :width='300' :scrollHeight='scrollHeight' :loading='isLoading'
		@close='handleClose'>
		<!-- 头部插槽 -->
		<!-- <template v-slot:head>
			<el-button >请求</el-button>
		</template> -->
		<!-- 正文插槽-滚动体 -->
		<template v-slot:content>
			<div class='content-box'>
				<p class="table-aliasname" v-if="rowData.tableAliasname">{{ rowData.tableAliasname }}</p>
				<div class="attr-item" v-if="type == '点'">
					<p>显示标题字段</p>
					<el-select v-model="configInfo.titleField">
						<el-option v-for="it in fields" :key="it.name" :value="it.name" :label="it.name"></el-option>
					</el-select>
				</div>

				<div class="attr-item" v-if="type == '点'">
					<p>点坐标高度字段</p>
					<el-select v-model="configInfo.heightField">
						<el-option v-for="it in fields" :key="it.name" :value="it.name" :label="it.name"></el-option>
					</el-select>
				</div>

				<el-collapse v-model="activeCollapse" @change="handleChange">
					<el-collapse-item v-if="type == '点'" :title="`符号样式 (${markerIcons[configInfo.imageIndex].name})`"
						name="icon">
						<div class="attr-item">
							<div class="icon-box">
								<el-scrollbar style="height: 100%">
									<ul>
										<li v-for="(it, i) in markerIcons" :key="i"
											:class="{ iconActive: configInfo.imageIndex == i }" @click="iconChose(i)">
											<img :src="it.url" alt="icon" />
										</li>
									</ul>
								</el-scrollbar>
							</div>
						</div>
					</el-collapse-item>
				</el-collapse>

				<div class="attr-item item-oneline" v-if="type == '点'">
					<p>字体大小</p>
					<el-input type="number" v-model="configInfo.fontSize" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '点'">
					<p>字体颜色</p>
					<el-color-picker size="mini" v-model="configInfo.fontColor"></el-color-picker>
					<el-input readonly v-model="configInfo.fontColor" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '点'">
					<p>字体背景颜色</p>
					<el-color-picker size="mini" v-model="configInfo.textBackgroundColor"></el-color-picker>
					<el-input readonly v-model="configInfo.textBackgroundColor" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '点'">
					<p>字体轮廓颜色</p>
					<el-color-picker size="mini" v-model="configInfo.fontOutlineColor"></el-color-picker>
					<el-input readonly v-model="configInfo.fontOutlineColor" />
				</div>



				<div class="attr-item item-oneline" v-if="type == '线'">
					<p>线宽</p>
					<el-input type="number" v-model="configInfo.thickness" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '线'">
					<p>默认颜色</p>
					<el-color-picker size="mini" v-model="configInfo.polyLineColor"></el-color-picker>
					<el-input readonly v-model="configInfo.polyLineColor" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '线'">
					<p>样式</p>
					<el-radio-group v-model="configInfo.polyLineShape">
						<el-radio :label="0">直线</el-radio>
						<el-radio :label="1">曲线</el-radio>
					</el-radio-group>
				</div>

				<div class="attr-item item-oneline" v-if="type == '线'">
					<p>深度检查</p>
					<el-radio-group v-model="configInfo.depthTest">
						<el-radio :label="true">是</el-radio>
						<el-radio :label="false">否</el-radio>
					</el-radio-group>
				</div>

				<div class="attr-item item-oneline" v-if="type == '面'">
					<p>启用高度字段</p>
					<el-radio-group v-model="configInfo.enableHeightField">
						<el-radio :label="false">否</el-radio>
						<el-radio :label="true">是</el-radio>
					</el-radio-group>
				</div>

				<div class="attr-item item-oneline" v-if="type == '面' && !configInfo.enableHeightField">
					<p>多边形高度</p>
					<el-input type="number" v-model="configInfo.polygoHeight" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '面' && configInfo.enableHeightField">
					<p>高度字段</p>
					<el-select v-model="configInfo.heightField">
						<el-option v-for="it in fields" :key="it.name" :value="it.name" :label="it.name"></el-option>
					</el-select>
				</div>

				<div class="attr-item item-oneline" v-if="type == '面'">
					<p>默认颜色</p>
					<el-color-picker size="mini" v-model="configInfo.polygonColor"></el-color-picker>
					<el-input readonly v-model="configInfo.polygonColor" />
				</div>

				<div class="attr-item item-oneline" v-if="type == '面'">
					<p>多边形样式</p>
					<el-select v-model="configInfo.polygonStyle">
						<el-option v-for="it in polygonStyles" :key="it.name" :value="it.value"
							:label="it.name"></el-option>
					</el-select>
				</div>

				<el-collapse v-model="activeCollapse" @change="handleChange">
					<el-collapse-item v-if="type == '点'" title="牵引线设置" name="draftLineSet">
						<div class="attr-item item-oneline">
							<p>是否显示牵引线</p>
							<el-radio-group v-model="configInfo.showLine">
								<el-radio :label="false">否</el-radio>
								<el-radio :label="true">是</el-radio>
							</el-radio-group>
						</div>

						<div class="attr-item item-oneline" v-if="type == '点'">
							<p>牵引线颜色</p>
							<el-color-picker size="mini" v-model="configInfo.lineColor"></el-color-picker>
							<el-input readonly v-model="configInfo.lineColor" />
						</div>

						<div class="attr-item">
							<p>牵引线尺寸</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.lineSize[0]" />
									<span>宽度</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.lineSize[1]" />
									<span>长度</span>
								</div>
							</div>
						</div>

						<div class="attr-item">
							<p>牵引线偏移</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.lineOffset[0]" />
									<span>宽度</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.lineOffset[1]" />
									<span>长度</span>
								</div>
							</div>
						</div>


					</el-collapse-item>

					<el-collapse-item v-if="type == '点'" title="高级设置" name="1">
						<div class="attr-item">
							<p>可视范围</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.markerRange[0]" />
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.markerRange[1]" />
								</div>
							</div>
						</div>

						<div class="attr-item">
							<p>文本可视范围</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.textRange[0]" />
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.textRange[1]" />
								</div>
							</div>
						</div>

						<div class="attr-item item-oneline">
							<p>显示模式</p>
							<el-select v-model="configInfo.displayMode">
								<el-option v-for="it in displayModes" :key="it.value" :value="it.value"
									:label="it.name"></el-option>
							</el-select>
						</div>

						<div class="attr-item item-oneline">
							<p>参与遮挡</p>
							<el-radio-group v-model="configInfo.occlusionCull">
								<el-radio :label="false">否</el-radio>
								<el-radio :label="true">是</el-radio>
							</el-radio-group>
						</div>
					</el-collapse-item>

					<el-collapse-item v-if="type != '点' && false" title="Transform" name="2">
						<div class="attr-item">
							<p>位置</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>X</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>Y</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>Z</span>
								</div>
							</div>
						</div>

						<div class="attr-item">
							<p>旋转</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>X</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>Y</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>Z</span>
								</div>
							</div>
						</div>

						<div class="attr-item">
							<p>缩放</p>
							<div class="number-list">
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>X</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>Y</span>
								</div>
								<div>
									<el-input-number controls-position="right" v-model="configInfo.num" />
									<span>Z</span>
								</div>
							</div>
						</div>
					</el-collapse-item>
				</el-collapse>
			</div>
		</template>
		<!-- 底部插槽 -->
		<template v-slot:foot>
			<div class='content-foot'>
				<el-button size="small" type="primary" plain @click="handleClose">取消</el-button>
				<el-button size="small" type="primary" @click="handleSubmit">确定</el-button>
			</div>
		</template>
	</cloud-tmpl>
</template>

<script>
import SpatialDataModule from './SpatialData.module.js' // 公共业务
import { get } from '@/utils/fetch';

export default {
	name: 'SpatialDataCcnfigTmpl',
	mixins: [SpatialDataModule],
	props: ["rowData"],
	data() {
		return {
			title: '显示配置',
			activeCollapse: [],
			isLoading: false,
			scrollHeight: 60,
			top: 120,
			// ---------------
			type: "",
			configInfo: {},
			fields: [
				{ name: "feature_name" },
				{ name: "model_x" },
				{ name: "model_y" },
				{ name: "model_z" },
			],
			displayModes: [
				{ name: "避让聚合(不显示)", value: 0 },
				{ name: "避让聚合(显示)", value: 1 },
				{ name: "非避让聚合(显示)", value: 2 },
				{ name: "非避让聚合(不显示)", value: 3 },
				{ name: "智能模式", value: 4 },
			],
			polylineStyles: [
				{ name: "箭头样式1", value: 0 },
				{ name: "箭头样式2", value: 1 },
				{ name: "流动线", value: 2 },
				{ name: "光流", value: 3 },
				{ name: "固定线条", value: 4 },
				{ name: "贴地模式", value: 5 },
			],
			polygonStyles: [
				{ name: "单色", value: 0 },
				{ name: "圆点", value: 1 },
				{ name: "体积", value: 2 },
				{ name: "渐变", value: 3 },
				{ name: "动态渐变", value: 4 },
				{ name: "波纹", value: 5 },
				{ name: "宽波纹", value: 6 },
				{ name: "旋转箭头", value: 7 },
				{ name: "旋转线", value: 8 },
				{ name: "旋转渐变", value: 9 },
				{ name: "贴地模式", value: 10 },
			],
			dataList: [],
			marker: {},
			polyLine: {},
			polygon: {},
		};
	},
	watch: {
		configInfo: {
			immediate: false,
			deep: true,
			handler(val) {
				if (!val) return;
				switch (this.type) {
					case "点":
						this.markerHelper();
						break;
					case "线":
						this.polyLineHelper();
						break;
					case "面":
						this.polygonHelper();
						break;
				}
			}
		}
	},
	created() {
		this.configInfo = { ...this.defaultDataConfig }

		if (this.rowData) {
			// this.title = this.title + " (" + this.rowData.featureType + ")"
			this.type = this.getFeatType(this.rowData.featureType)
			if (this.fusionMethodJudge(this.rowData.fusionMethod)) this.getDetailsAll(this.rowData);
			else this.getDetails2(this.rowData)
		}

		if (this.type == "点") {
			this.top = 120
			this.scrollHeight = 60
		} else {
			this.top = 160
			this.scrollHeight = 30
		}
	},
	beforeDestroy() {
		window.origAPI.marker.clear();
		window.origAPI.polyline.clear();
		window.origAPI.polygon.clear();
	},
	methods: {
		handleChange(val) {
			// console.log(val);
		},
		iconChose(index) {
			this.$set(this.configInfo, 'imageIndex', index)
		},
		handleSubmit() {
			let config = this.configInfo // TODO 根据类型 细化参数
			this.$emit('closeEvent', config)
		},
		handleClose() {
			this.$emit('closeEvent')
		},
		async getDetails2(row) {
			let configRes = await this.getDataConfig(row);
			if (configRes && configRes.layerStyle) {
				let dataConfig = JSON.parse(configRes.layerStyle);
				this.configInfo = {
					...this.configInfo,
					...dataConfig,
				}
			}

			let params = {
				sourceId: row.sourceId,
				tableName: row.tableName,
			}
			get(`${cim_main}fileFusion/getFusionFile`, params).then(res => {
				this.dataList = res.message || [];
				this.$nextTick(() => {
					if (this.dataList.length > 0) {
						switch (this.type) {
							case "点":
								this.markerHelper();
								break;
							case "线":
								this.polyLineHelper();
								break;
							case "面":
								this.polygonHelper();
								break;
						}
					} else this.$message.warning("未查询到数据")
				})
			})
		},
		async getDetailsAll(row) {
			let configRes = await this.getDataConfig(row);
			if (configRes && configRes.layerStyle) {
				let dataConfig = JSON.parse(configRes.layerStyle);
				this.configInfo = {
					...this.configInfo,
					...dataConfig,
				}
			}

			let params = {
				tableName: row.tableName,
				start: 1,
				limit: 999999999,
			}
			get(`${cim_main}fusion/getExcelDataList`, params).then(res => {
				let { data, field } = res.message;
				this.fields = field.map(it => {
					return { name: it.fieldName }
				})
				this.dataList = data.list;
				this.$nextTick(() => {
					if (this.dataList.length > 0) {
						switch (this.type) {
							case "点":
								this.markerHelper();
								break;
							case "线":
								this.polyLineHelper();
								break;
							case "面":
								this.polygonHelper();
								break;
						}
					} else this.$message.warning("未查询到数据")
				})
			})
		},
		markerHelper() {
			this.markerHelperOne(this.dataList[0], this.configInfo)
		},
		polyLineHelper() {
			this.polyLineHelperOne(this.dataList[0], this.configInfo)
		},
		polygonHelper() {
			this.polygonHelperOne(this.dataList[0], this.configInfo)
		},
	},
};
</script>
<style lang='scss' scoped>
::v-deep .content-box {
	// 	outline: solid 1px pink;
	// 	height: 500px;
	.table-aliasname {
		padding-bottom: 10px;
	}
}

.attr-item {
	margin-bottom: 16px;

	p {
		color: #999;
		margin-bottom: 8px;
	}

	.el-select {
		width: 100%;
	}



	::v-deep.number-list {
		display: flex;
		align-items: center;
		justify-content: space-between;

		&>div {
			text-align: center;
		}

		.el-input-number {
			width: 100%;

			.el-input__inner {
				// width: 86px;
				border-radius: 4px;
				outline: none;
				height: 32px;
				border: 1px solid #646b6f;
				background: #494d52;
				padding-left: 7px;
				padding-right: 22px;
				color: rgba(255, 255, 255, 0.8);
			}

			.el-input__inner:focus {
				outline: none;
				border-radius: 4px;
				border: 1px solid;
				border-color: #02b2ff;
			}
		}

		.el-input-number__decrease,
		.el-input-number__increase {
			width: 16px !important;
			background: rgba(46, 52, 65, 0.8);
			padding-right: 10px;
			box-sizing: border-box;
			border: none;

			i {
				font-size: 14px;
			}
		}
	}
}

.icon-box {
	padding: 10px;
	border-bottom: solid 1px #999;
	height: 120px;

	ul {
		display: flex;
		flex-wrap: wrap;

		li {
			// outline: solid 1px #ccc;
			width: 22%;
			height: 50px;
			text-align: center;
			line-height: 50px;
			font-size: 30px;
			margin-bottom: 5px;
			padding: 2px;
			border-radius: 5px;
		}

		img {
			width: 60%;
			max-height: 46px;
			box-sizing: border-box;
			cursor: pointer;
		}

		.iconActive {
			border: solid 1px #02b2ff;
		}
	}
}

::v-deep .item-oneline {
	display: flex;
	align-items: center;

	p {
		margin: 0;
		margin-right: auto;
	}

	.el-input {
		width: 90px;
		margin-left: 10px;

		input {
			color: #999;
		}
	}

	.el-select {
		width: 60%;

		.el-input {
			width: 93%;
		}
	}
}

::v-deep .el-collapse {
	border: 0;

	.el-collapse-item__header {
		background-color: transparent;
		color: #999;
		border: 0;
		font-size: 14px;
	}

	.el-collapse-item__wrap,
	.el-collapse-item__content {
		border: 0;
		background-color: transparent;
		color: #999;
		font-size: 14px;
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