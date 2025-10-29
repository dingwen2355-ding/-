<template>
	<view class="sally-checkbox" :class="[`sally-checkbox--${checkboxPlacement}`,{'is--disabled': disabled}]"
		@click="handleCheckChange">
		<view class="sally-checkbox--icon" :class="'sally-'+type+'-checked'" v-if="checkedStatus === 2">
		</view>
		<view class="sally-checkbox--icon" :class="'sally-'+type+'-indeterminate'" v-else-if="checkedStatus === 1">
		</view>
		<view class="sally-checkbox--icon" :class="'sally-'+type+'-outline'" v-else>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'sally-checkbox',
		props: {

			type: {
				type: String,
				default: 'checkbox', //radio,checkbox
			},
			/**
			 * 选择框的位置，可选 left/right
			 */
			checkboxPlacement: {
				type: String,
				default: 'left',
			},
			/**
			 */
			disabled: {
				type: Boolean,
				default: false,
			},
			/**
			 */
			checkedStatus: {
				type: Number,
				default: 1,
			},
		},
		methods: {

			handleCheckChange() {

				this.$emit('change')
			}
		}
	}
</script>

<style lang="less" scoped>
	// 灰色加载
	.sally-loading {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTE0NC4yMDUgMjAyLjQ5NmExMzYuNjc4IDEzNi42NzggMCAxMDI3My4zNTcgMCAxMzYuNjc4IDEzNi42NzggMCAxMC0yNzMuMzU3IDB6TTQxLjcyOCA0OTIuOTAyYTExOS41NzggMTE5LjU3OCAwIDEwMjM5LjE1NSAwIDExOS41NzggMTE5LjU3OCAwIDEwLTIzOS4xNTUgMHpNMTQ0LjIzIDc0OS4xNThhMTAyLjUwMiAxMDIuNTAyIDAgMTAyMDUuMDA1IDAgMTAyLjUwMiAxMDIuNTAyIDAgMTAtMjA1LjAwNSAwek00MzUuMiA4NjEuOTI2YTg5LjYgODkuNiAwIDEwMTc5LjIgMCA4OS42IDg5LjYgMCAxMC0xNzkuMiAwem0yODkuODQzLTk1LjY2NmE4NS40MjcgODUuNDI3IDAgMTAxNzAuODU1IDAgODUuNDI3IDg1LjQyNyAwIDEwLTE3MC44NTUgMHptMTM2LjcwNC0yOTAuNDMzYTY4LjMyNiA2OC4zMjYgMCAxMDEzNi42NTMgMCA2OC4zMjYgNjguMzI2IDAgMTAtMTM2LjY1MyAwek03NTkuMjIgMjE5LjU3MWE1MS4yNTEgNTEuMjUxIDAgMTAxMDIuNTAyIDAgNTEuMjUxIDUxLjI1MSAwIDEwLTEwMi41MDMgMHpNNTEyIDg1LjM3NmEzNC4xNzYgMzQuMTc2IDAgMTA2OC4zNTIgMCAzNC4xNzYgMzQuMTc2IDAgMTAtNjguMzUyIDB6Ii8+PC9zdmc+');
		opacity: 0.7;
	}

	// 灰色三角
	.sally-arrow-down {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDE3MDcgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjEzLjM3NSIgaGVpZ2h0PSIxMjgiPjxwYXRoIGQ9Ik0xMjIuODcgMi4yMjRMODUxLjk4NyAwbDczMi45MzMgNi40NzJjNDQuNTUzIDAgODMuNzE4IDIuNDExIDk2LjczMSAxMi4zNzUgMzEuNDIgMjQuMDU0IDI3Ljk4NiA3My4xODQgMCAxMDEuMDQ2TDkwNS44NzQgOTY5LjM1N2MtMTQuNzg3IDE0Ljg1LTM0LjUyNCAyMS4yLTUzLjk0NiAyMC4yNDYtMTkuNDI2Ljk1Ni0zOS4xMDEtNS4zOTUtNTMuOTUyLTIwLjI0NkwyMi4yIDExOS44OTVjLTI3LjkyMi0yNy44NjItMzAuOTctNzYuNTQzIDAtMTAwLjk4QzM2LjYwOCA3LjU1MiA2MS42ODQgMi4yMjQgMTIyLjg3IDIuMjI0eiIgZmlsbD0iIzhhOGE4YSIvPjwvc3ZnPg==');
	}

	// 多选-灰色选择边框
	.sally-checkbox-outline {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTgwOS45ODQgMTI4cTM0LjAwNSAwIDU5Ljk5IDI1Ljk4NHQyNS45ODMgNTkuOTl2NTk2LjAxcTAgMzQuMDA1LTI1Ljk4NCA1OS45OXQtNTkuOTg5IDI1Ljk4M2gtNTk2LjAxcS0zNC4wMDYgMC01OS45OS0yNS45ODRUMTI4IDgwOS45ODR2LTU5Ni4wMXEwLTM0LjAwNiAyNS45ODQtNTkuOTlUMjEzLjk3NCAxMjhoNTk2LjAxem0wIDg2LjAxNmgtNTk2LjAxdjU5Ni4wMWg1OTYuMDF2LTU5Ni4wMXoiIGZpbGw9IiM4YThhOGEiLz48L3N2Zz4=');

		// 颜色太深了，减淡一点
		opacity: 0.4;
	}

	// 多选-蓝色选中
	.sally-checkbox-checked {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTQyNS45ODQgNzI2LjAxNmwzODQtMzg0LTU5Ljk5LTYxLjk5NS0zMjQuMDEgMzI0LjAxMS0xNTIuMDIxLTE1Mi4wMjFMMjEzLjk3MyA1MTJ6bTM4NC01OTguMDE2cTM2LjAxIDAgNjEuMDEzIDI1Ljk4NFQ4OTYgMjEzLjk3NHY1OTYuMDFxMCAzNC4wMDUtMjUuMDAzIDU5Ljk5dC02MS4wMTMgMjUuOTgzaC01OTYuMDFxLTM2LjAxMSAwLTYxLjAxNC0yNS45ODR0LTI1LjAwMy01OS45ODl2LTU5Ni4wMXEwLTM0LjAwNiAyNS4wMDMtNTkuOTlUMjEzLjk3MyAxMjhoNTk2LjAxMXoiIGZpbGw9IiMwMDdhZmYiLz48L3N2Zz4=');
	}

	// 多选-蓝色半选中
	.sally-checkbox-indeterminate {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTcyNi4wMTYgNTUzLjk4NHYtODQuMDFoLTQyNy45OXY4NC4wMWg0MjcuOTl6TTgwOS45ODQgMTI4cTM0LjAwNSAwIDU5Ljk5IDI1Ljk4NHQyNS45ODMgNTkuOTl2NTk2LjAxcTAgMzQuMDA1LTI1Ljk4NCA1OS45OXQtNTkuOTg5IDI1Ljk4M2gtNTk2LjAxcS0zNC4wMDYgMC01OS45OS0yNS45ODRUMTI4IDgwOS45ODR2LTU5Ni4wMXEwLTM0LjAwNiAyNS45ODQtNTkuOTlUMjEzLjk3NCAxMjhoNTk2LjAxeiIgZmlsbD0iIzAwN2FmZiIvPjwvc3ZnPg==');
	}

	// 单选-灰色选择边框
	.sally-radio-outline {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTUxMiA4NTQuMDE2cTEzOS45OSAwIDI0MC45ODEtMTAwLjk5MnQxMDAuOTkyLTI0MC45ODFUNzUyLjk4MSAyNzEuMDYgNTEyIDE3MC4wNyAyNzEuMDE5IDI3MS4wNjEgMTcwLjAyNyA1MTIuMDQzdDEwMC45OTIgMjQwLjk4MVQ1MTIgODU0LjAxNnptMC03NjhxMTc2IDAgMzAxLjAxMyAxMjUuMDEzdDEyNS4wMTQgMzAxLjAxNC0xMjUuMDE0IDMwMS4wMTNUNTEyIDkzOC4wNjkgMjEwLjk4NyA4MTMuMDU2IDg1Ljk3MyA1MTIuMDQzdDEyNS4wMTQtMzAxLjAxNFQ1MTIgODYuMDE2eiIgZmlsbD0iIzhhOGE4YSIvPjwvc3ZnPg==');

		// 颜色太深了，减淡一点
		opacity: 0.4;
	}

	// 单选-蓝色选中
	.sally-radio-checked {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTUxMiA4NTQuMDE2cTEzOS45OSAwIDI0MC45ODEtMTAwLjk5MnQxMDAuOTkyLTI0MC45ODFUNzUyLjk4MSAyNzEuMDYgNTEyIDE3MC4wNyAyNzEuMDE5IDI3MS4wNjEgMTcwLjAyNyA1MTIuMDQzdDEwMC45OTIgMjQwLjk4MVQ1MTIgODU0LjAxNnptMC03NjhxMTc2IDAgMzAxLjAxMyAxMjUuMDEzdDEyNS4wMTQgMzAxLjAxNC0xMjUuMDE0IDMwMS4wMTNUNTEyIDkzOC4wNjkgMjEwLjk4NyA4MTMuMDU2IDg1Ljk3MyA1MTIuMDQzdDEyNS4wMTQtMzAxLjAxNFQ1MTIgODYuMDE2em0wIDIxMS45NjhxODguMDIxIDAgMTUwLjk5NyA2My4wMTlUNzI2LjAxNiA1MTJ0LTYzLjAxOSAxNTAuOTk3VDUxMiA3MjYuMDE2dC0xNTAuOTk3LTYzLjAxOVQyOTcuOTg0IDUxMnQ2My4wMTktMTUwLjk5N1Q1MTIgMjk3Ljk4NHoiIGZpbGw9IiMwMDdhZmYiLz48L3N2Zz4=');
	}

	// 单选-蓝色半选中
	.sally-radio-indeterminate {
		background-image: url('data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTI4IiBoZWlnaHQ9IjEyOCI+PHBhdGggZD0iTTY0MCA1MTJxMCA1Mi4wMS0zOC4wMTYgODkuOTg0VDUxMiA2NDB0LTg5Ljk4NC0zOC4wMTZUMzg0IDUxMnQzOC4wMTYtODkuOTg0VDUxMiAzODR0ODkuOTg0IDM4LjAxNlQ2NDAgNTEyek01MTIgODU0LjAxNnExMzkuOTkgMCAyNDAuOTgxLTEwMC45OTJ0MTAwLjk5Mi0yNDAuOTgxVDc1Mi45ODEgMjcxLjA2IDUxMiAxNzAuMDcgMjcxLjAxOSAyNzEuMDYxIDE3MC4wMjcgNTEyLjA0M3QxMDAuOTkyIDI0MC45ODFUNTEyIDg1NC4wMTZ6bTAtNzY4cTE3NiAwIDMwMS4wMTMgMTI1LjAxM3QxMjUuMDE0IDMwMS4wMTQtMTI1LjAxNCAzMDEuMDEzVDUxMiA5MzguMDY5IDIxMC45ODcgODEzLjA1NiA4NS45NzMgNTEyLjA0M3QxMjUuMDE0LTMwMS4wMTRUNTEyIDg2LjAxNnoiIGZpbGw9IiMwMDdhZmYiLz48L3N2Zz4=');
	}

	.sally-checkbox--icon {
		width: 1.25rem;
		height: 1.25rem;
		background-repeat: no-repeat;
		background-position: center center;
		background-size: 100%;
	}
</style>