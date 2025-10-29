import Vue from 'vue';

/*Vue.directive('drag', {
  bind(el, binding, vnode, oldVnode) {
    const dialogHeaderEl = el.querySelector('.el-dialog__header')
    const dragDom = el.querySelector('.el-dialog')
    dialogHeaderEl.style.cursor = 'move'

    // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
    const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null)

    dialogHeaderEl.onmousedown = (e) => {
      // 鼠标按下，获得鼠标在盒子内的坐标（鼠标在页面的坐标 减去 对话框的坐标），计算当前元素距离可视区的距离
      const disX = e.clientX - dialogHeaderEl.offsetLeft
      const disY = e.clientY - dialogHeaderEl.offsetTop

      // 获取到的值带px 正则匹配替换
      let styL, styT

      // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
      if (sty.left.includes('%')) {
        styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100)
        styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100)
      } else {
        styL = +sty.left.replace(/\px/g, '')
        styT = +sty.top.replace(/\px/g, '')
      }

      document.onmousemove = function(e) {
        // 鼠标移动，用鼠标在页面的坐标 减去 鼠标在盒子里的坐标，获得模态框的left和top值
        // 通过事件委托，计算移动的距离
        const l = e.clientX - disX
        const t = e.clientY - disY

        // 移动当前元素
        dragDom.style.left = `${l + styL}px`
        dragDom.style.top = `${t + styT}px`

        // 将此时的位置传出去
        // binding.value({x:e.pageX,y:e.pageY})
      }

      document.onmouseup = function(e) {
        //  鼠标弹起，移除鼠标移动事件
        document.onmousemove = null
        document.onmouseup = null
      }
    }
  }
})*/

// v-dialogDrag: 弹窗拖拽
Vue.directive('drag', {
  // 1.指令绑定到元素上回立刻执行bind函数，只执行一次
  // 2.每个函数中第一个参数永远是el，表示绑定指令的元素，el参数是原生js对象
  // 3.通过el.focus()是无法获取焦点的，因为只有插入DOM后才生效bind: function (el) { },
  // inserted表示一个元素，插入到DOM中会执行inserted函数，只触发一次
  inserted: function (el) {
    el.onmousedown = function (e) {
      var disx = e.pageX - el.offsetLeft
      var disy = e.pageY - el.offsetTop
      document.onmousemove = function (e) {
        el.style.left = e.pageX - disx + 'px'
        el.style.top = e.pageY - disy + 'px'
        console.log(el.style.left)
        console.log(el.style.top)
      }
      document.onmouseup = function () {
        document.onmousemove = document.onmouseup = null
      }
    }
  }// 当VNode更新的时候会执行updated，可以触发多次updated: function (el) { }
})
