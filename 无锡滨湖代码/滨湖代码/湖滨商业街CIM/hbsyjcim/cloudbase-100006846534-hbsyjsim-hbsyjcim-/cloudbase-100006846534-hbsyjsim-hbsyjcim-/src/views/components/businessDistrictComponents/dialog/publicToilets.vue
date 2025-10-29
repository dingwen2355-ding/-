<template>
  <div class="left">
    <div class="deviceInfo">
      <img
        src="@/assets/businessDistrictComponents/icon_cover2_com.png"
        alt=""
      />
      <div>
        <span>设备名称</span>
        <div>厕所_公厕[{{ activeSix == 1 ? "男" : "女" }}]</div>
      </div>
    </div>

    <div class="occupancy">
      <span class="oc-title">厕所占用监测</span>
      <div class="oc-type">
        {{ activeSix == 1 ? "男" : "女" }}厕 <span class="oc-number">0/15</span>
      </div>
    </div>

    <div class="c-title">空气质量监测</div>

    <div class="c-infos">
      <div class="deviceInfo">
        <img
          src="@/assets/businessDistrictComponents/icon_tongfeng.png"
          alt=""
        />
        <div>
          <span>通风、除臭设备</span>
          <div class="status">在线中</div>
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
        <img src="@/assets/businessDistrictComponents/icon_shidu.png" alt="" />
        <div>
          <span>湿度</span>
          <div>-</div>
        </div>
      </div>
    </div>

    <div class="c-title">环境监测</div>

    <div class="monitot">
      <div>H2S</div>
      <div>---</div>
      <!-- <div>0.036ppm</div> -->
      <div>NH3</div>
      <div>---</div>
      <!-- <div>0.082ppm</div> -->
      <div>TVOC</div>
      <div>---</div>
      <!-- <div>0.5ppm</div> -->
    </div>
  </div>
</template>
<script>
export default {
  name: "dustbin-info", // 智能公厕
  props: ["activeSix"],
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
    }
  }
}
.occupancy {
  width: 328px;
  height: 100px;
  background-image: url("@/assets/businessDistrictComponents/img_tolit_usrbox_bg.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
  .oc-title {
    font-size: 16px;
    margin-top: 18px;
    margin-left: 32px;
  }
  .oc-type {
    margin-top: 14px;
    margin-left: 32px;
    font-size: 15px;
    color: #a0bde0;
    display: flex;
    align-items: center;
    span {
      font-size: 24px;
      margin-left: 15px;
      color: #00d5ff;
      font-family: "DIN";
    }
  }
}
.monitot {
  display: grid;
  grid-template-rows: 36px 36px 36px;
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
