<template>
	<cloud-tmpl title='高程' :scrollHeight='10' @close='handleClose'>
		<template v-slot:content>
			<ul class="content-warp">
				<li>
					<span>高度</span>
					<span>{{ elevation }}m</span>
				</li>
			</ul>
		</template>
	</cloud-tmpl>
</template>

<script>
export default {
	name: "Elevation",
	data() {
		return {
			elevation: 0,
			markerIds: [],
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.MouseClickPoint) {
					this.onEventMeasurement({
						Type: "Coordinate",
						Coordinate: val.MouseClickPoint,
					});
				}
			}
		},
	},
	methods: {
		onEventMeasurement(data) {
			if (data.Type === "Coordinate") {
				this.Coordinate = data.Coordinate;
				this.elevation = Math.round(this.Coordinate[2] * 100) / 100;
				let imgUrl = `${window.location.origin}${window.location.pathname}static/tagImage/circle.png`
				let markerId = "mm_" + new Date().getTime() + this.elevation;
				let o = {
					id: markerId,
					coordinate: data.Coordinate, //坐标位置
					coordinateType: 0, //默认0是投影坐标系，也可以设置为经纬度空间坐标系值为1
					imagePath: imgUrl, //显示图片路径
					imageSize: [20, 20], //图片宽高[width,height]
					anchors: [-16, 32], //锚点
					range: [10, 10000], //可视范围
					fixedSize: true, //图片固定尺寸，取值范围：false 自适应，近大远小，true 固定尺寸，默认值：false
					text: "Height: " + this.elevation,
					//   textSize: 20, // 文字大小
					useTextAnimation: false, //打开文字展开动画效果
					textRange: [1, 6000], //文本可视范围[近裁距离, 远裁距离]
					textOffset: [0, 0], // 文本偏移
					textBackgroundColor: [0, 0, 0, 0],
					fontSize: 12, //字体大小
					fontOutlineSize: 1, //字体轮廓线大小
					fontColor: Color.Yellow, //字体颜色
					fontOutlineColor: "#000", //字体轮廓线颜色

					showLine: false, //标注点下方是否显示垂直牵引线
					lineSize: [10, 1], //垂直牵引线宽度和高度[width, height]
					lineColor: [0, 0, 0, 0.8], //垂直牵引线颜色
					lineOffset: [20, 20], //垂直牵引线偏移

					autoHidePopupWindow: true, //失去焦点后是否自动关闭弹出窗口
					autoHeight: false, // 自动判断下方是否有物体
					displayMode: 2, //显示模式
					priority: 0, //避让优先级
					occlusionCull: false, //是否参与遮挡剔除
				};
				window.origAPI.marker.add(o).then(() => {
					this.markerIds.push(markerId)
				});
			}
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		window.origAPI.marker.delete(this.markerIds);
		this.handleClose();
	},
};
</script>

<style lang="scss" scoped>
.content-warp {
	font-size: 16px;
	color: #ffffff;

	li {
		padding: 0 10px;
		line-height: 56px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
}
</style>