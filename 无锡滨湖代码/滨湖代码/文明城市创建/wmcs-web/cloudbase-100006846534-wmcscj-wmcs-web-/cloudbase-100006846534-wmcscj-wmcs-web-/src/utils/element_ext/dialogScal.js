import Vue from 'vue'

// v-dialogScal: 弹窗缩放属性
Vue.directive('dialogScal', {
    bind(el, binding, vnode, oldVnode) {
        // 整个弹窗
        const dragDom = el.querySelector('.el-dialog')
        // 由于已经写了内部自定义css, 目前的模式是, 宽度由dragDom定, 高度其实是由in_dia定的
        // 不加一层了, 只加右下角
        let r_b = document.createElement('i')
        dragDom.appendChild(r_b)
        // 在弹窗右下角加上一个10-10px的控制块
        r_b.style.background = '#e2e2e2'
        r_b.style.borderRight = '1px dashed black'
        r_b.style.borderBottom = '1px dashed black'
        r_b.style.cursor = 'se-resize'
        r_b.style.position = 'absolute'
        r_b.style.height = '10px'
        r_b.style.width = '10px'
        r_b.style.right = '0px'
        r_b.style.bottom = '0px'
        r_b.style.zIndex = '99'
        r_b.onmousedown = (e) => {
            // 初始化
            let clientX = e.clientX
            let clientY = e.clientY
            const in_dia = dragDom.querySelector('.dia-bd-hei')
            let dia_width = dragDom.getBoundingClientRect().width
            let in_height = in_dia.getBoundingClientRect().height
            in_dia.style.maxHeight = 'unset'
            in_dia.style.height = in_height + 'px'
            document.onmousemove = (e) => {
                e.preventDefault()
                // 宽: 由于布局的问题,鼠标移动x,真实看宽度要增加2x
                let move_x = 2 * (e.clientX - clientX)
                let now_width = dia_width + move_x
                now_width = now_width > 400 ? now_width : 400
                dragDom.style.width = now_width + 'px'
                // 高
                let move_y = e.clientY - clientY
                let now_height = in_height + move_y
                now_height = now_height > 100 ? now_height : 100
                in_dia.style.height = now_height + 'px'
            }
            document.onmouseup = (e) => {
                // 松开则解除绑定
                document.onmousemove = null
                document.onmouseup = null
            }
        }
    }
})
