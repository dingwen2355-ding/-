<template>
  <div class="map-range" v-show="rangeShow">
    <div class="p-container">
      <div>10m</div>
      <div>
        <div style="margin-left: 30rem" id="curTitle">3000m</div>
        <div class="bg" id="bg">
          <div id="cur" class="cur"></div>
          <div id="circle" class="circle" style="cursor: pointer"></div>
        </div>
      </div>
      <div>20000m</div>
    </div>
  </div>
</template>

<script>
import ZnvMap from '@/components/map/znvMap'
export default {
  data () {
    return {
      center: null,
      rangeShow: false,
      list: []
    }
  },
  watch: {
    '$store.getters.currentCircle': {
      handler(V) {
        this.center = V.center
      }
    }
  },
  mounted () {
    this.$root.Bus.$on('addCircle', data => {
      this.center = data
      this.addCircle()
      setTimeout(() => {
        ZnvMap.showCircleContainPoints('alert')
      }, 100)
      this.rangeShow = true
    })
    this.$root.Bus.$on('setRangeShow', data => {
      this.rangeShow = data
    })
    this.$root.Bus.$on('changeMarkerType', data => {
      this.list = data
    })

    let self = this
    let Scale = function (btn, bar, title) {
      this.btn = document.getElementById(btn)
      this.bar = document.getElementById(bar)
      this.title = document.getElementById(title)
      this.step = this.bar.getElementsByTagName('DIV')[0]
      this.init()
    }
    Scale.prototype = {
      init: function () {
        let f = this
        let g = document
        let b = window
        let m = Math
        f.btn.onmousedown = function (e) {
          var x = (e || b.event).clientX
          var l = this.offsetLeft
          var max = f.bar.offsetWidth - this.offsetWidth
          g.onmousemove = function (e) {
            var thisX = (e || b.event).clientX
            var to = m.min(max, m.max(-2, l + (thisX - x)))
            f.btn.style.left = to + 'rem'
            f.ondrag(m.round(m.max(0, to / max) * 100), to)
            b.getSelection ? b.getSelection().removeAllRanges() : g.selection.empty()
          }
          // eslint-disable-next-line no-new-func
          g.onmouseup = function (e) {
            ZnvMap.clearDraw()
            setTimeout(() => {
              ZnvMap.showCircleContainPoints('alert')
            }, 100)
            this.onmousemove = null
            this.onmouseup = null
            console.log(self.curM, self.list, '2222222222222222222222')
            // setTimeout(() => {
            //   ZnvMap.showCircleContainPointByKey([
            //     {
            //       key: 'type',
            //       value: self.list
            //     }
            //   ])
            // }, 1000)
            self.$root.Bus.$emit('distance', self.curM)

            // if (self.curM <= 2000) {
            //   ZnvMap.gotoMapPoint({
            //     lng: self.center.gpsX,
            //     lat: self.center.gpsY,
            //     zoom: 15
            //   })
            // } else if (self.curM > 2000 && self.curM <= 4000) {
            //   ZnvMap.gotoMapPoint({
            //     lng: self.center.gpsX,
            //     lat: self.center.gpsY,
            //     zoom: 14
            //   })
            // } else if (self.curM > 4000 && self.curM <= 6500) {
            //   ZnvMap.gotoMapPoint({
            //     lng: self.center.gpsX,
            //     lat: self.center.gpsY,
            //     zoom: 13
            //   })
            // } else if (self.curM > 6500 && self.curM <= 13000) {
            //   ZnvMap.gotoMapPoint({
            //     lng: self.center.gpsX,
            //     lat: self.center.gpsY,
            //     zoom: 12
            //   })
            // } else if (self.curM > 13000 && self.curM <= 20000) {
            //   ZnvMap.gotoMapPoint({
            //     lng: self.center.gpsX,
            //     lat: self.center.gpsY,
            //     zoom: 11
            //   })
            // }
            // ZnvMap.showCircleContainPointByKey([
            //   {
            //     key: 'type',
            //     value: this.list
            //   }
            // ])
          }
        }
      },
      ondrag: function (pos, x) {
        this.step.style.width = Math.max(0, x) + 'rem'
        // this.title.innerHTML = pos + '%'
        this.title.innerHTML = Math.round((pos / 100) * (20000 - 10)) + 10 + 'm'
        self.curM = Math.round((pos / 100) * (20000 - 10)) + 10
        self.changeAlertRange(self.curM)
        // console.log(self.curM)
      }
    }
    // eslint-disable-next-line no-new
    new Scale('circle', 'bg', 'curTitle')
  },
  methods: {
    addCircle (radius = 3000) {
      ZnvMap.addCircle('alert', {
        center: { lng: this.center.gpsX, lat: this.center.gpsY },
        radius: radius, // 单位米
        strokeColor: '#FF7C2F',
        fillColor: '#FF7C2F',
        strokeOpacity: 1,
        fillOpacity: 0.2
      })
    },
    changeAlertRange (radius) {
      if (!this.center.gpsX) {
        return
      }
      ZnvMap.removeAllCircle()
      this.addCircle(radius)
    }
  },
  beforeDestroy () {
    this.$root.Bus.$off('addCircle')
  }
}
</script>

<style lang="scss">
.map-range {
  position: absolute;
  left: 25%;
  top: 170rem;
  // color: #173672;
  color: #fff;
  z-index: 4;
  .p-container {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    .bg {
      width: 322rem;
      height: 20rem;
      border: 1rem solid #0071ff;
      border-radius: 10rem;
      padding: 6rem;
      position: relative;
    }
    .cur {
      width: 61rem;
      height: 6rem;
      background: linear-gradient(90deg, #0071ff 0%, #00b2ff 100%);
      border-radius: 3rem;
    }
    .circle {
      width: 10rem;
      height: 10rem;
      background: linear-gradient(90deg, #0071ff 0%, #00b2ff 100%);
      border-radius: 50%;
      position: absolute;
      top: 4rem;
      left: 60rem;
    }
  }
}
</style>
