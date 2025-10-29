<template>
  <div class="s-canvas" >
    <canvas id="s-canvas" :width="contentWidth" :height="contentHeight"></canvas>
  </div>
</template>

<script>
export default {
  name: 'Identify',
  props: {
    identifyCode: {
      default: function () { return '1234' }
    },
    fontSizeMin: {
      default: function () { return 30 }
    },
    fontSizeMax: {
      default: function () { return 50 }
    },
    backgroundColorMin: {
      default: function () { return 180 }
    },
    backgroundColorMax: {
      default: function () { return 240 }
    },
    colorMin: {
      default: function () { return 50 }
    },
    colorMax: {
      default: function () { return 160 }
    },
    lineColorMin: {
      default: function () { return 40 }
    },
    lineColorMax: {
      default: function () { return 180 }
    },
    dotColorMin: {
      default: function () { return 0 }
    },
    dotColorMax: {
      default: function () { return 255 }
    },
    contentWidth: {
      default: function () { return 220 }
    },
    contentHeight: {
      default: function () { return 54 }
    }
  },
  methods: {
    // 生成一个随机数
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
    // 生成一个随机的颜色
    randomColor (min, max) {
      var r = this.randomNum(min, max)
      var g = this.randomNum(min, max)
      var b = this.randomNum(min, max)
      return 'rgb(' + r + ',' + g + ',' + b + ')'
    },
    drawPic () {
      var canvas = document.querySelector('#s-canvas')
      var ctx = canvas.getContext('2d')
      ctx.textBaseline = 'bottom'
      // 绘制背景
      ctx.fillStyle = this.randomColor(this.backgroundColorMin, this.backgroundColorMax)
      ctx.fillRect(0, 0, this.contentWidth, this.contentHeight)
      // 绘制文字
      for (var i = 0; i < this.identifyCode.length; i++) {
        this.drawText(ctx, this.identifyCode[i], i)
      }
      // this.drawLine(ctx)
      // this.drawDot(ctx)
    },
    drawText (ctx, txt, i) {
      ctx.fillStyle = this.randomColor(this.colorMin, this.colorMax)
      ctx.font = this.randomNum(this.fontSizeMin, this.fontSizeMax) + 'px SimHei'
      var x = (i + 1) * (this.contentWidth / (this.identifyCode.length + 1))
      var y = this.randomNum(this.fontSizeMax, this.contentHeight - 5)
      var deg = this.randomNum(-45, 45)
      // 修改坐标原点和旋转角度
      ctx.translate(x, y)
      ctx.rotate(deg * Math.PI / 180)
      ctx.fillText(txt, 0, 0)
      // 恢复坐标原点和旋转角度
      ctx.rotate(-deg * Math.PI / 180)
      ctx.translate(-x, -y)
    },
    drawLine (ctx) {
      // 绘制干扰线
      for (var i = 0; i < 8; i++) {
        ctx.strokeStyle = this.randomColor(this.lineColorMin, this.lineColorMax)
        ctx.beginPath()
        ctx.moveTo(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight))
        ctx.lineTo(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight))
        ctx.stroke()
      }
    },
    drawDot (ctx) {
      // 绘制干扰点
      for (var i = 0; i < 100; i++) {
        ctx.fillStyle = this.randomColor(0, 255)
        ctx.beginPath()
        ctx.arc(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight), 1, 0, 2 * Math.PI)
        ctx.fill()
      }
    }
  },
  watch: {
    identifyCode () {
      this.drawPic()
    }
  },
  mounted () {
    this.drawPic()
  }
}
</script>

<style scoped>

</style>
