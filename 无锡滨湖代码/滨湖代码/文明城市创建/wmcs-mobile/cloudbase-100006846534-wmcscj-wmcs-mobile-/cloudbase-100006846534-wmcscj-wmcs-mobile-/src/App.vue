<script>
export default {
  onLaunch: function () {
    console.log("App Launch");
  },
  onShow: function () {
    console.log("App Show");
    
  },
  onHide: function () {
    console.log("App Hide");
  }
};
</script>

<style>
.view {
  background-color: #F4F5FD;
}

@font-face {
  font-family: alibaba55;
  src: url("static/font/AlibabaPuHuiTi-2-55-Regular.ttf");
}

#app {
  font-family: alibaba55;
}

.cycle {
  position: absolute;
  top: 30upx;
  right: 60upx;
  color: red;
  font-size: 12px;
}
.tbg {
    width: 100%;
    height: 105upx;
    background: url("static/images/tbg.png") no-repeat;
    background-size: 640upx 25upx;
    background-position: 55upx 45upx;
    text-align: center;
    line-height: 105upx;
    font-size: 42upx;
    color: #4770bf;
    font-weight: 700;
}
</style>
