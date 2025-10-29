<template>
    <div class="base-file-item" :class="{'only-icon': onlyIcon}">
        <!-- 纯ICON缩略图展示 -->
        <div v-if="att_type=='img' && onlyIcon" :style="{width: diyWidth, height: diyHeight}">
            <el-image
                class="link-img"
                :src="real_src"
                :style="{width: diyWidth, height: diyHeight}"
                :preview-src-list="srcarr"
                fit="contain"
                @click.stop="closeItem"
            >
                <div slot="error" style="width:70px;height:70px;" class="show-flex d-c">
                    <i class="el-icon-picture-outline" />
                </div>
            </el-image>
        </div>
        <!-- style="width: 70px; height: 70px;margin-right:10px;" -->
        <el-image
            v-if="att_type=='img' && !onlyIcon"
            style="width: 70px; height: 70px;margin-right:10px;"
            class="link-img"
            :src="real_src"
            :preview-src-list="srcarr"
            fit="contain"
            @click.stop="closeItem"
        >
            <div slot="error" style="width:70px;height:70px;" class="show-flex d-c">
                <i class="el-icon-picture-outline" />
            </div>
        </el-image>
        <div v-if="showDel" class="base-att-del" @click="delItem">
            <i class="el-icon-close" />
        </div>
        <a v-if="att_type=='excel'" class="link" target="_blank" @click="downFile()">
            <img class="img" src="~@/assets/images/file_icon/excel.png" alt="" />
            <span class="down_link">{{ src.originalName || '下载' }}</span>
        </a>
        <!-- :href="src" 后期支持播放 :target="_blank" 视频直接会在本页面播放导致页面回不去了,要新页面打开:后期做 -->
        <a v-if="att_type=='video'" class="link" target="_blank" @click="downFile()">
            <img class="img" src="~@/assets/images/file_icon/unknow.png" alt="" />
            <span class="down_link">{{ src.originalName || '下载' }}</span>
        </a>
        <a v-if="att_type=='word'" class="link" target="_blank" @click="downFile()">
            <img class="img" src="~@/assets/images/file_icon/word.png" alt="" />
            <span class="down_link">{{ src.originalName || '下载' }}</span>
        </a>
        <a v-if="att_type=='pdf'" class="link" target="_blank" @click="downFile()">
            <img class="img" src="~@/assets/images/file_icon/pdf.png" alt="" />
            <span class="down_link">{{ src.originalName || '下载' }}</span>
        </a>
        <a v-if="att_type=='txt'" class="link" target="_blank" @click="downFile()">
            <img class="img" src="~@/assets/images/file_icon/txt.png" alt="" />
            <span class="down_link">{{ src.originalName || '下载' }}</span>
        </a>
        <a v-if="att_type=='unknow'" class="link" target="_blank" @click="downFile()">
            <img class="img" src="~@/assets/images/file_icon/unknow.png" alt="" />
            <span class="down_link">{{ src.originalName || '下载' }}</span>
        </a>
    </div>
</template>
<script>
import { downLoadFile, downLoadFileByUrl } from '@/api/basic'
export default {
    props: {
        // 下载时是否使用token:系统提供有对象和无对象的,默认现在用到这个组件的是有对象的,并区分下载时是否需要token
        // 是否需要token时上传和下载用的是两套接口
        useToken: {
            type: Boolean,
            default: true
        },
        src: {
            type: [Object, String],
            default: () => { return {} }
        },
        showDel: {
            type: Boolean,
            default: true
        },
        // 只针对图片,只返回url,没有id,直接显示,不需要去取
        onlyUrl: {
            type: Boolean,
            default: false
        },
        onlyIcon: {
            // 纯ICON图片展示
            type: Boolean,
            default: false
        },
        diyWidth: {
            // 纯ICON展示时自定义宽度
            type: String,
            default: '70px'
        },
        diyHeight: {
            // 纯ICON展示时自定义高度
            type: String,
            default: '70px'
        },
        noOpenScal: {
            // 点击不显示缩略图
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            id: null, // 请求之前
            // 实际重新请求的文件流
            real_src: ''
        }
    },
    computed: {
        srcarr() {
            if (this.noOpenScal) return []
            return [this.real_src]
        },
        att_type() {
            if (this.onlyUrl) {
                return 'img'
            }
            let type_arr = [
                {
                    name: 'img',
                    arr: [
                        '.jpg', '.JPG', '.jpeg', '.JPEG',
                        '.tif', '.TIF',
                        '.gif', '.GIF',
                        '.png', '.PNG'
                    ]
                },
                {
                    name: 'video',
                    arr: [
                        '.avi', '.wmv', '.mpeg', '.mp4', '.m4v', '.mov', '.asf', '.flv', '.f4v', '.rmvb', '.rm', '.3gp', '.vob',
                        '.AVI', '.WMV', '.MPEG', '.MP4', '.M4V', '.MOV', '.ASF', '.FLV', '.F4V', '.RMVB', '.RM', '.3GP', '.VOB'
                    ]
                },
                {
                    name: 'excel',
                    arr: [
                        '.xls', '.XLS',
                        '.xlsx', '.XLSX'
                    ]
                },
                {
                    name: 'word',
                    arr: [
                        '.doc', '.DOC',
                        '.docx', '.DOCX'
                    ]
                },
                {
                    name: 'pdf',
                    arr: [
                        '.pdf', '.PDF'
                    ]
                },
                {
                    name: 'txt',
                    arr: [
                        '.txt', '.TXT'
                    ]
                }
            ]
            let type_name = 'unknow'
            if (this.src.originalName.indexOf('.') > -1) {
                // 外链图片的地址是 XXXX.jpg&Token=XXXX:所以先按照&Token先分离一下
                let t_1 = this.src.originalName.split('&Token')
                let t_namearr = t_1[0].split('.')
                let t_name = '.' + t_namearr[t_namearr.length - 1].trim()
                type_arr.forEach(t => {
                    t.arr.forEach(i => {
                        if (t_name == i) {
                            type_name = t.name
                        }
                    })
                })
            }
            return type_name
        }
    },
    mounted() {
        if (this.onlyUrl) {
            this.getRealSrcByUrl()
            return
        }
        // 只有图片才需要立马加载流去预览
        if (this.id) return
        this.id = this.src.id
        if (this.src.id && this.src.contentType.indexOf('image') > -1) {
            this.getRealSrc()
        }
    },
    methods: {
        getRealSrcByUrl() {
            let _this = this
            // 获取实际路径
            downLoadFileByUrl(this.src)
            .then(data => {
                let urlCreator = window.URL || window.webkitURL;
                let imageUrl = urlCreator.createObjectURL(data.data)
                _this.real_src = imageUrl
            })
        },
        getRealSrc(down) {
            let _this = this
            // 获取实际路径
            downLoadFile(this.src.id, this.useToken)
            .then(data => {
                let urlCreator = window.URL || window.webkitURL;
                let imageUrl = urlCreator.createObjectURL(data.data)
                _this.real_src = imageUrl
                // 获取后是否需要直接下载
                if (down) { _this.createFile() }
            })
        },
        closeItem() {
            this.$nextTick(() => {
                // 获取遮罩层dom
                let domImageMask = document.querySelector('.el-image-viewer__mask')
                if (!domImageMask) return
                domImageMask.addEventListener('click', () => {
                    // 点击遮罩层时调用关闭按钮的 click 事件
                    document.querySelector('.el-image-viewer__close').click()
                })
            })
        },
        delItem() {
            this.$emit('del', this.src)
        },
        downFile() {
            let _this = this
            console.log('下载文件')
            if (this.real_src) {
                // 已经下载过了, 直接下载
                this.createFile()
            } else {
                // 未下载过, 请求下载
                _this.getRealSrc(true)
            }
        },
        createFile() {
            let downloadElement = document.createElement('a')
            // 创建下载的链接
            let href = this.real_src
            downloadElement.href = href
            // 下载后文件名
            downloadElement.download = this.src.originalName
            document.body.appendChild(downloadElement)
            // 点击下载
            downloadElement.click()
            // 下载完成移除元素
            document.body.removeChild(downloadElement)
        }
    }
}
</script>
<style lang='scss' scoped>
.base-file-item{
    // flex: 1;
    position: relative;
    display: flex;
    height: 90px;
    width: 70px;
    padding-bottom: 20px;
    display: inline-block;
    margin-right: 10px;
    margin-bottom: 10px;
    &.only-icon{
        // 缩略图展示
        margin-right: 0px;
        margin-bottom: 0px;
        padding-bottom: 0px;
        height: unset;
        width: unset;
        .link-img{
            margin-right:0;
            border: 1px solid #EBEEF5;
        }
    }
    .base-att-del{
        cursor: pointer;
        position: absolute;
        right: -5px;
        top: -5px;
        width: 15px;
        height: 15px;
        background: #F56C6C;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #fff;
        font-size: 12px;
        &:hover{
            transform: scale(1.1);
        }
    }
    .link-img{
        border: 1px solid #EBEEF5;
        width: 70px; height: 70px;margin-right:10px;
    }
    .link{
        display: block;
        width: 100%;
        height: 100%;
        .img{
            width: 100%;
            height: 100%;
            border: 1px solid #EBEEF5;
        }
        .down_link{
            cursor: pointer;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            font-size: 12px;
            line-height: 15px;
            position: absolute;
            bottom: -10px;
            left: 0;
            width: 100%;
            height: 30px;
            text-align: center;
            // letter-spacing: 2px;
            color: #66b1ff;
            overflow: hidden;
            // white-space: nowrap;
            text-overflow: ellipsis;
        }
    }
}
</style>
