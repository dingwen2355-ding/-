<template>
    <!-- 新版的: 按照附件格式来的: 上传的时候按照附件对象,获取详情的时候,附件也是要按照对象获取的 -->
    <!-- 附件对象格式 -->
    <!-- {
        id: 1, // 这个原上传字段为media_id, 做过处理
        originalName: 'SSSS.jpg',
        contentType: 'image/jpeg'
    } -->
    <div class="base-up-file-wapper" :class="{'no-border': noBorder}">
        <baseFileItem
            v-for="(img, index) in srclist"
            :key="index"
            :useToken="useToken"
            :src="img"
            :onlyUrl="onlyUrl"
            :showDel="showDel"
            @del="delItem"
        />
        <div v-if="showAdd && real_limit > 0" class="add-attachment">
            <el-upload
                ref="upload"
                v-loading="vLoading"
                :data="extraData"
                drag
                :action="baseUpUrl"
                :headers="headers"
                multiple
                :limit="has_limit"
                :on-exceed="upExceed"
                :on-success="upSuccess"
                :on-progress="onProgress"
                :on-error="upErr"
                :before-upload="beforeUpLoad"
                :accept="accept"
            >
                <div class="el-upload__text">
                    <slot name="icon-slot">
                        <i class="el-icon-plus" />
                    </slot>
                </div>
            </el-upload>
        </div>
    </div>
</template>
<script>
import baseFileItem from './baseFileItem.vue'
import { api } from '@/config'
import Cookies from 'js-cookie'
export default {
    components: { baseFileItem },
    props: {
        // 下载时是否使用token:系统提供有对象和无对象的,默认现在用到这个组件的是有对象的,并区分下载时是否需要token
        // 是否需要token时上传和下载用的是两套接口
        useToken: {
            type: Boolean,
            default: true
        },
        limit: {
            type: [Number, String],
            default: 5
        },
        // 附件列表
        srclist: {
            type: Array,
            default: () => {
                return []
            }
        },
        // 是否能显示删除
        showDel: {
            type: Boolean,
            default: true
        },
        // 是否显示上传
        showAdd: {
            type: Boolean,
            default: false
        },
        // 只针对图片,只返回url,没有id,直接显示,不需要去取
        onlyUrl: {
            type: Boolean,
            default: false
        },
        onlyImg: {
            // 如果为true, 则只能传图片
            type: Boolean,
            default: false
        },
        onlyVideo: {
            // 如果为true, 则只能传视频
            type: Boolean,
            default: false
        },
        fileOverSize: {
            // 每个文件的限制大小:默认5M
            type: [Number, String],
            default: 10
        },
        tableName: {
            type: String,
            default: 'notable'
        },
        fieldName: {
            type: String,
            default: 'fileList'
        },
        noBorder: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            vLoading: false,
            nowFileListNum: 0,
            // baseUpUrl: api + '/file/uploadFile',
            headers: { 'Authorization': Cookies.get('token') },
            imageTypeList: [
                '.jpg', '.JPG', '.jpeg', '.JPEG',
                '.tif', '.TIF',
                '.gif', '.GIF',
                '.png', '.PNG'
            ],
            videoArr: [
                '.avi', '.wmv', '.mpeg', '.mp4', '.m4v', '.mov', '.asf', '.flv', '.f4v', '.rmvb', '.rm', '.3gp', '.vob',
                '.AVI', '.WMV', '.MPEG', '.MP4', '.M4V', '.MOV', '.ASF', '.FLV', '.F4V', '.RMVB', '.RM', '.3GP', '.VOB'
            ]
        }
    },
    computed: {
        baseUpUrl() {
            if (this.onlyUrl) {
                // 没有附件ID,只有URL
                return api + '/file/upload'
            }
            if (this.useToken) {
                // 下载时需要token
                return api + '/file/uploadFile'
            } else {
                // 下载时不需要token
                return api + '/file/api/uploadFile'
            }
        },
        accept() {
            if (this.onlyImg) return this.imageTypeList.join(',')
            if (this.onlyVideo) return this.videoArr.join(',')
            return '*'
        },
        extraData() {
            return {
                businessModule: this.tableName,
                fieldName: this.fieldName
            }
        },
        real_limit() {
            let real_num = 0
            if (this.srclist.length >= this.limit) {
                real_num = 0
            } else {
                real_num = this.limit - this.srclist.length
            }
            return real_num
        },
        has_limit() {
            // 这里研究了一下源码,是这样的,limit为0时认为无数量限制,所以这里要想生效,limit至少为1
            // 所以这里当为0时,通过外部隐藏按钮实现
            // 这是源码的条件:
            // if (this.limit && this.fileList.length + files.length > this.limit) {
            //     this.onExceed && this.onExceed(files, this.fileList);
            //     return;
            // }
            // 解决方案:反向计算,先算真实limit,然后反向计算需要的limit
            let real_num = 0
            if (this.srclist.length >= this.limit) {
                real_num = 0
            } else {
                real_num = this.limit - this.srclist.length
            }
            let numFileList = this.nowFileListNum
            let need_num = numFileList + real_num
            return need_num
        }
    },
    methods: {
        onProgress(event, file, fileList) {
            this.vLoading = true
        },
        upSuccess(response, file, fileList) {
            this.vLoading = false
            try {
                if (response.code != '200') {
                    this.$errorMessage(response.message || '上传失败')
                    return
                }
                if (this.onlyUrl) {
                    // 没有附件ID,只有URL
                    this.srclist.push(response.data.url)
                } else {
                    this.srclist.push({
                        id: response.data.id.toString() || response.data.id.toString() || null,
                        originalName: response.data.originalName.toString(),
                        contentType: response.data.contentType,
                        fileSize: response.data.fileSize,
                        url: response.data.url // 相对路径
                    })
                }
                this.nowFileListNum = fileList.length
            } catch (error) {
                console.log(error)
                this.$errorMessage('上传异常,请稍后重试')
            }
        },
        upErr(e, file, fileList) {
            this.$errorMessage('上传异常,请稍后重试')
            console.log(e)
            this.vLoading = false
        },
        upExceed(files, fileList) {
            // 文件超出个数限制时的钩子
            this.$message.warning(`当前限制选择 ${this.limit} 个文件`);
        },
        delItem(src) {
            if (src) {
                let selectIndex = -1
                this.srclist.forEach((i, index) => {
                    if (i.id == src.id) selectIndex = index
                })
                if (selectIndex > -1) {
                    this.srclist.splice(selectIndex, 1)
                }
            }
        },
        beforeUpLoad(file) {
            // 上传前的验证
            let imgArr = this.imageTypeList
            let back = ''
            // 如果设置了只能上传图片,则针对文件名做过滤
            if (this.onlyImg) {
                let typePass = false
                imgArr.forEach(a => {
                    // 这里因为是单个文件的调用, 所以是反过来验证的
                    if (file.name.indexOf(a) > -1) {
                        typePass = true
                    }
                })
                if (!typePass) back = '只能上传图片'
            }
            let fileSize = file.size / 1024 / 1024
            if (fileSize > this.fileOverSize) {
                back && (back += ', 且')
                back += `文件大小请控制在${this.fileOverSize}M以内`
            }
            if (back) {
                this.$message.warning(back)
                return false
            } else {
                return true
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.base-up-file-wapper{
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    &.no-border{
        ::v-deep .el-upload-dragger{
            border: 0px solid #ffffff !important;
            &:hover{
                border: 0px solid #ffffff !important;
            }
        }
    }
    .add-attachment{
        width: 70px;
        height: 70px;
        margin-right: 10px;
        margin-bottom: 10px;
        ::v-deep .el-upload__text{
            width: 70px !important;
            height: 70px !important;
            user-select: none;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 35px;
            color: #c0c4cc;
            &:hover{
                color: var(--td-brand-color);
            }
        }
        ::v-deep .el-upload-dragger{
            width: 100%;
            height: 100%;
            &:hover{
                border-color: var(--td-brand-color);
            }
        }
        ::v-deep .el-upload-list{
            display: none;
        }
    }
}
</style>
