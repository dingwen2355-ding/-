<template>
  <div class="left">
    <div class="deviceInfo">
      <img
        src="@/assets/businessDistrictComponents/icon_cover2_com.png"
        alt=""
      />
      <div>
        <span>设备名称</span>
        <div>
          {{ coverData.device_code || "智能井盖" }}
          <span class="offline" v-if="coverData.status === '离线'">离线中</span>
          <span class="online" v-else>在线</span>
        </div>
      </div>
    </div>

    <div class="c-title">井盖倾覆监测</div>

    <div class="c-infos">
      <div class="deviceInfo">
        <img
          src="@/assets/businessDistrictComponents/icon_jiaodu1.png"
          alt=""
        />
        <div>
          <span>倾斜角度</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img src="@/assets/businessDistrictComponents/icon_lock2.png" alt="" />
        <div>
          <span>是否上锁</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img
          src="@/assets/businessDistrictComponents/icon_battry2.png"
          alt=""
        />
        <div>
          <span>电池电量</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img
          src="@/assets/businessDistrictComponents/icon_battry2.png"
          alt=""
        />
        <div>
          <span>电池电压</span>
          <div>-</div>
        </div>
      </div>
      <div class="deviceInfo">
        <img
          src="@/assets/businessDistrictComponents/icon_xinhao2.png"
          alt=""
        />
        <div>
          <span>信号质量</span>
          <div>-</div>
        </div>
      </div>
    </div>

    <div class="c-title">环境监测</div>

    <div class="monitot">
      <div>硫化氢</div>
      <div>---</div>
      <!-- <div>0.08ppm(&lt;100ppm)</div> -->
      <div>NH3</div>
      <!-- <div>0.08ppm(&lt;100ppm)</div> -->
      <div>---</div>
    </div>
  </div>
</template>
<script>
export default {
  name: "cover-info", // 智能井盖
  props: ["activeSix", "coverData"],
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
  grid-template-rows: 60px 60px 60px;
  grid-template-columns: auto 147px;
  .status {
    width: 44px;
    height: 19px;
    border-radius: 2px;
    font-size: 12px;
    border: 1px solid #1bc078;
    background: #1bc07899;
    color: #fff;
    padding: 0 4px;
    margin-top: 2px;
    text-align: center;
  }
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
      .online {
        border: 1px solid #c6e4ff;
        background: #00d5ff;
      }
    }
  }
}

.monitot {
  display: grid;
  grid-template-rows: 36px 36px;
  grid-template-columns: 128px auto;
  grid-gap: 2px;
  margin-top: 10px;
  div:nth-child(odd) {
    background: rgba(18, 161, 235, 0.25);
    font-size: 15px;
    font-family: "PingFang SC";
    text-align: right;
    line-height: 36px;
    padding-right: 24px;
  }
  div:nth-child(even) {
    background: rgba(31, 168, 239, 0.1);
    font-family: "PingFang SC";
    font-size: 18px;
    line-height: 36px;
    padding-left: 24px;
    color: #00d5ff;
  }
}
</style>
