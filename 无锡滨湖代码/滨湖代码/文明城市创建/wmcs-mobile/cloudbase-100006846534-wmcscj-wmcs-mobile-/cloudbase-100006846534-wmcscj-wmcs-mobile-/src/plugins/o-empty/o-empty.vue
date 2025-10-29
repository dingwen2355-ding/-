<template>
	<view :class="['o-empty']" :style="Bg+Height">
		<image :style="ImgSize" :src="Img" mode="aspectFit"></image>
		<view class="text">{{text}}</view>
		<view class="slot" v-if="$slots.default"> <slot></slot> </view>
	</view>
</template>

<script>
	export default {
		name:'oEmpty',
		// #ifdef MP-WEIXIN
		options:{ virtualHost:true },
		// #endif
		props:{
			
			// img：缺省图片,可选值 error network search，支持传入图片URL，图片仅支持jpg/png/svg三种格式
			// imgSize：缺省图尺寸,
			// text：描述文字,
			// bg：背景色
            // height：组件占位最小高度，实际高度看是否被内容撑开
			
			img:{
				type:String,
				default:'e'
			},
			imgSize:{
				type:[String,Number],
				default:'320'
			},
			text:{
				type:String,
				default:'暂无数据'
			},
			bg:{
				type:String,
				default:'#fff'
			},
            height:{
                type:String,
                default:'480rpx'
            }
		},
		data() {
			return {
				
			}
		},
		methods: {
			
		},
		computed: {
			Bg(){ return `backgroundColor:${this.bg};`},
            Height(){
				if(['rpx','px','rem','em','vw','vh','%'].includes(this.height)) return `minHeight:${this.height}`
				else return `minHeight:${this.height}rpx`
			},
			Img(){
				if(['error','network','search'].includes(this.img)) return require(`./${this.img}.svg`);
				else if (this.img.endsWith('.jpg') || this.img.endsWith('.png') || this.img.endsWith('.svg')) return this.img;
				else return require('./empty.svg')
			},
			ImgSize(){
				if(['rpx','px','rem','em','vw','%'].includes(this.imgSize)) return `width:${this.imgSize};height:${this.imgSize};`
				else return `width:${this.imgSize}rpx;height:${this.imgSize}rpx;`
			}
		},
	}
</script>

<style lang="scss">
	.o-empty{
		// min-height:350rpx;
		display: flex;
		flex-direction: column;
		padding: 64rpx 0!important;
		align-items: center;
		justify-content: center;
		box-sizing: border-box;
	}
	.center{text-align: center;}
	.text{
		color:#969798;
		padding: 20rpx 0;
	}
	.slot{
		padding-top: 20rpx;
	}
</style>
