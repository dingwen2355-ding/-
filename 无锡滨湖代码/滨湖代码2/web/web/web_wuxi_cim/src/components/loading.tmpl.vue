<template>
  <div id="loading">
    <div>
      <img src="../assets/images/bg/load_2.png" />
      <div :class="['svg', loadStatus]">
        <div v-if="loadStatus === 'load'">
          <svg version="1.1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="38" height="38"
            viewBox="0 0 38 38" preserveAspectRatio="xMinYMin meet">
            <text x="14" y="21" font-family="Monaco" font-size="2px" style="letter-spacing:0.6" fill="grey">LOADING
              <animate attributeName="opacity" values="0;1;0" dur="1.8s" repeatCount="indefinite" />
            </text>
            <path fill="#373a42" d="M20,35c-8.271,0-15-6.729-15-15S11.729,5,20,5s15,6.729,15,15S28.271,35,20,35z M20,5.203
      C11.841,5.203,5.203,11.841,5.203,20c0,8.159,6.638,14.797,14.797,14.797S34.797,28.159,34.797,20
      C34.797,11.841,28.159,5.203,20,5.203z">
            </path>
            <path fill="#373a42" d="M20,33.125c-7.237,0-13.125-5.888-13.125-13.125S12.763,6.875,20,6.875S33.125,12.763,33.125,20
      S27.237,33.125,20,33.125z M20,7.078C12.875,7.078,7.078,12.875,7.078,20c0,7.125,5.797,12.922,12.922,12.922
      S32.922,27.125,32.922,20C32.922,12.875,27.125,7.078,20,7.078z">
            </path>
            <path fill="#3A86FF" stroke="#3A86FF" stroke-width="1" stroke-miterlimit="10"
              d="M5.203,20
        c0-8.159,6.638-14.797,14.797-14.797V5C11.729,5,5,11.729,5,20s6.729,15,15,15v-0.203C11.841,34.797,5.203,28.159,5.203,20z">
              <animateTransform attributeName="transform" type="rotate" from="0 20 20" to="360 20 20"
                calcMode="spline" keySplines="0.4, 0, 0.2, 1" keyTimes="0;1" dur="2s" repeatCount="indefinite" />
            </path>
            <path fill="#CFE1FF" stroke="#CFE1FF" stroke-width="0.4" stroke-miterlimit="10" d="M7.078,20
    c0-7.125,5.797-12.922,12.922-12.922V6.875C12.763,6.875,6.875,12.763,6.875,20S12.763,33.125,20,33.125v-0.203
    C12.875,32.922,7.078,27.125,7.078,20z">
              <animateTransform attributeName="transform" type="rotate" from="0 20 20" to="360 20 20" dur="1.8s"
                repeatCount="indefinite" />
            </path>
          </svg>
        </div>
        <img v-else-if="loadStatus === 'failed'" src="../assets/images/bg/load-failed.png" />
        <div id="l_text"></div>
        <!-- <div v-if="loadStatus === 'failed'" class="button">
          <el-button @click="closePage">关闭页面</el-button>
          <el-button @click="reloadPage">重新加载</el-button>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loadStatus: 'load',
      _timer: null,
      first_timer : null,
    }
  },
  mounted () {
    this.initLoading()
  },
  methods: {
    // loading text
    
    initLoading() {
      let self = this;
      let arrText = [
        "加载模型文件",
        "加载场景",
        "渲染页面",
      ];
      let elem = document.getElementById("l_text");
      function _fade() {
        let oldIndex = Number(elem.getAttribute("index") || -1);
        let index = oldIndex < arrText.length - 1 ? oldIndex + 1 : oldIndex;
        if(index<3){
          setTimeout(() => {
          elem.setAttribute("class", "fadeOut");
          elem.setAttribute("index", index);
          elem.innerText = arrText[index];
          elem.setAttribute("class", "fadeIn");
          }, 300);
        }
      }
      self.first_timer = setTimeout(()=>{
        _fade();
        self._timer = setInterval(_fade,4000);
      },300);
      
    
    },
    loadSuccess() {
      clearInterval(this._timer);
      this.loadStatus = "load";
    },
    loadFailed(err = "未申请实例，请到个人中心申请！") {
      clearTimeout(this.first_timer);
      clearInterval(this._timer);
      this.loadStatus = "failed";
      let elem = document.getElementById("l_text");
      elem.innerText = err;
    },
    closePage() {
      window.close();
    },
    reloadPage() {
      window.location.reload();
    },
  }
}
</script>

<style lang="scss" scoped>
/* loading begin */
#loading {
  // position: fixed;
  z-index: 101;
  // width: 100vw;
  // height: 100vh;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url("~@/assets/images/bg/load_1.png") center center no-repeat;
  background-size: cover;
  & > div {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .svg {
    position: absolute;
    width: 26%;
    transform: translateY(-2.5rem);
    &.failed {
      transform: translateY(-25%);
    }
    div {
      &:first-child {
        position: relative;
        width: 100%;
        padding-bottom: 100%;
        -webkit-perspective: 600;
        perspective: 600;
      }
      &.button {
        display: flex;
        position: absolute;
        transform: translateX(-50%);
        left: 50%;
        bottom: -62%;
        .el-button {
          background-color: transparent;
          color: #fff;
          font-size: 0.875rem;
          border: 1px solid #fff;
          border-radius: 4px;
          opacity: 0.6;
          padding: 10px 22px;
          &:last-child {
            margin-left: 24px;
          }
        }
      }
    }
    svg {
      position: absolute;
      width: 100%;
      height: 100%;
    }
    img {
      position: relative;
      width: 79.375%;
      left: 50%;
      transform: translateX(-50%);
    }
    #l_text {
      position: absolute;
      width: 300px;
      left: 50%;
      transform: translateY(10px);
      transform: translateX(-50%);
      text-align: center;
      color: #eeeeee;
      opacity: 0.6;
      font-family: "Microsoft YaHei";
      font-size: 1rem;
    }
    .fadeIn {
      animation: fadeIn 0.3s;
      -webkit-animation: fadeIn 0.3s;
    }
    .fadeOut {
      animation: fadeOut 0.3s;
      -webkit-animation: fadeOut 0.3s;
    }
  }
}
@keyframes fadeIn
{
  from {opacity: 0;}
  to {opacity: 0.6;}
}
@-webkit-keyframes fadeIn
{
  from {opacity: 0;}
  to {opacity: 0.6;}
}
@keyframes fadeOut
{
  from {opacity: 0.6;}
  to {opacity: 0;}
}
@-webkit-keyframes fadeOut
{
  from {opacity: 0.6;}
  to {opacity: 0;}
}
/* loading end */
</style>
