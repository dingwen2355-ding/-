<template>
  <div class="left">
    <div class="deviceInfo">
      <img src="@/assets/businessDistrictComponents/icon_cover2.png" alt="" />
      <div>
        <span>设备名称</span>
        <div>
          智能垃圾桶
          <span>在线中</span>
          <!-- <span class="offline">离线</span> -->
        </div>
      </div>
    </div>

    <div class="c-title">设备状态</div>

    <div class="c-infos">
      <div class="deviceInfo">
        <img
          src="@/assets/businessDistrictComponents/icon_dianya1.png"
          alt=""
        />
        <div>
          <span>电压</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img src="@/assets/businessDistrictComponents/icon_wendu2.png" alt="" />
        <div>
          <span>温度</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img src="@/assets/businessDistrictComponents/icon_gaodu2.png" alt="" />
        <div>
          <span>垃圾桶溢满剩余高度</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img src="@/assets/businessDistrictComponents/icon_yiman2.png" alt="" />
        <div>
          <span>满溢报警</span>
          <div>-</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "dustbin-info", // 智能垃圾桶
};
</script>
<style lang="scss" scoped>
.c-title {
  width: 364px;
  height: 28px;
  font-size: 16px;
  color: #00d5ff;
  line-height: 28px;
  padding-left: 10px;
  background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 100%);
}
.c-infos {
  margin-top: 15px;
  display: grid;
  grid-template-rows: 60px 60px;
  grid-template-columns: auto 147px;
}
.deviceInfo {
  display: flex;
  align-items: center;
  margin-bottom: 15px;

  img {
    width: 42px;
    height: 42px;
  }

  & > div {
    margin-left: 15px;

    & > span {
      font-size: 15px;
      color: #bcd1e9;
      font-family: "Inter";
    }

    & > div {
      color: #00d5ff;
      font-size: 20px;
      font-family: "DIN";

      span {
        width: 44px;
        height: 19px;
        border-radius: 2px;
        font-size: 12px;
        border: 1px solid #1bc078;
        background: #1bc07899;
        color: #fff;
        padding: 0 4px;
        margin-left: 8px;
      }
      .offline {
        border: 1px solid #c6e4ff;
        background: #c6e4ff99;
      }
    }
  }
}
</style>
