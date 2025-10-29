<template>
    <!-- `参考文章: https://blog.csdn.net/liuqiao0327/article/details/107126784/`, -->
    <!-- https://blog.csdn.net/L_eiDigaga/article/details/121533398 -->
    <div style="width:100%;height:100%">
        <quill-editor
            v-show="!disable"
            ref="zrQuill"
            v-model="editContent"
            class="zr-base-quill ql-editor"
            :options="editOptions"
            style="padding:0;margin-top:-10px;"
            @change="change($event)"
        />
        <!-- 图片上传 -->
        <div style="display: none;">
            <form :id="formId" action method="post" enctype="multipart/form-data">
                <!-- multiple -->
                <!-- 限制只能选一张 -->
                <input
                    :id="imgId"
                    style="display: none"
                    type="file"
                    name="file"
                    accept="image/jpg, image/jpeg, image/png, image/gif"
                    @change="uploadImg('uploadFormMulti')"
                />
            </form>
        </div>
        <div v-if="disable" class="ql-editor" style="width:100%;min-height:100px;max-height:400px;overflow:auto;border:1px solid #ebeef5;" v-html="value" />
    </div>
    <!-- @blur @focus @ready -->
</template>
<script>
import { upLoadFileByFile, file2Base64 } from '@/api/basic'
import { apiFileUrl } from '@/config/index'
import Quill from 'quill'
let fontSizeStyle = Quill.import('attributors/style/size')
fontSizeStyle.whitelist = [
    '12px',
    '14px',
    '16px',
    '18px',
    '20px',
    '22px',
    '24px',
    '26px'
]
Quill.register(fontSizeStyle, true)
export default {
    props: {
        value: {
            type: String,
            default: ''
        },
        disable: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            formId: 'id-' + Math.random(),
            imgId: 'id-' + Math.random(),
            editContent: this.value,
            editOptions: {
                placeholder: '请输入内容...',
                modules: {
                    toolbar: [
                        ['bold', 'italic', 'underline', 'strike'], // 加粗, 斜体, 下划线, 删除线
                        // ['blockquote', 'code-block'], // 引用, 代码块
                        [{ header: 1 }, { header: 2 }], // 1,2级标题
                        [{ color: [] }],
                        [{ background: [] }],
                        [{ list: 'ordered' }, { list: 'bullet' }], // 有序, 无序列表
                        // [{ script: 'sub' }, { script: 'super' } ], // 上标, 下标
                        // [{ indent: '-1' }, { indent: '+1' }], // 缩进
                        // [{ direction: 'rtl' }], // 文本方向
                        // [{ size: [] }], // 字体大小
                        [{
                            size: [
                                '12px',
                                '14px',
                                '16px',
                                '18px',
                                '20px',
                                '22px',
                                '24px',
                                '26px'
                            ]
                        }],
                        // [{ header: [1, 2, 3, 4, 5, 6, false] }],
                        // [{ font: [] }],
                        [{ align: [] }],
                        ['image']
                        // ['link', 'image']
                    ]
                }
            },
            imageTypeList: [
                '.jpg', '.JPG', '.jpeg', '.JPEG',
                '.tif', '.TIF',
                '.gif', '.GIF',
                '.png', '.PNG'
            ],
            fileOverSize: {
                // 每个文件的限制大小:默认5M
                type: [Number, String],
                default: 5
            }
        }
    },
    watch: {
        value(v) {
            this.editContent = v
        }
    },
    mounted() {
        let _this = this
        let imgHandler = function(imgclick) {
            // 替换图片上传 点击事件
            if (imgclick) {
                var fileInput = document.getElementById(_this.imgId) // 隐藏的file文本ID
                fileInput.click() // 加一个触发事件
            }
        }
        // 替换点击事件
        this.$refs.zrQuill.quill.getModule('toolbar').addHandler('image', imgHandler)
        // 替换复制图片事件
        this.replacePasteImg()
        // 禁止拖拽事件: 目前没有实现
        // this.unableDragImg()
    },
    methods: {
        change(e) {
            this.$emit('input', e.html)
        },
        uploadImg() {
            // https://blog.csdn.net/weixin_42080056/article/details/98774354 :这里实现了吗?忘了,测一下
            // 替换后的图片上传方法
            let _this = this
            let imgVals = document.getElementById(_this.imgId).files[0]
            let imgArr = this.imageTypeList
            try {
                console.log(imgVals,imgArr)
                file2Base64(imgVals)
                .then(d => {
                    let url = d
                    // let typePass = false
                    // imgArr.forEach(a => {
                    //     // 这里因为是单个文件的调用, 所以是反过来验证的
                    //     if (url.indexOf(a) > -1) {
                    //         typePass = true
                    //     }
                    // })
                    // if (!typePass) return _this.$message.warning('只能上传图片格式')
                    let fileSize = imgVals.size / 1024 / 1024
                    if (fileSize > 1) {
                    // if (fileSize > this.fileOverSize) {
                        return _this.$message.warning('文件过大, 当前最大上传1M，无法上传')
                    }
                    let Range = _this.$refs.zrQuill.quill.getSelection()
                    _this.$refs.zrQuill.quill.insertEmbed(
                        Range != null ? Range.index : 0,
                        'image',
                        url
                    )
                })
                // upLoadFileByFile(imgVals)
                // .then(data => {
                //     let baseUrl = apiFileUrl
                //     let url = baseUrl + data.data.data.url
                //     let typePass = false
                //     imgArr.forEach(a => {
                //         // 这里因为是单个文件的调用, 所以是反过来验证的
                //         if (url.indexOf(a) > -1) {
                //             typePass = true
                //         }
                //     })
                //     let fileSize = imgVals.size / 1024 / 1024
                //     if (fileSize > this.fileOverSize) {
                //         return _this.$message.warning('文件过大，无法上传')
                //     }
                //     if (!typePass) return _this.$message.warning('只能上传图片格式')
                    
                //     let Range = _this.$refs.zrQuill.quill.getSelection()
                //     _this.$refs.zrQuill.quill.insertEmbed(
                //         Range != null ? Range.index : 0,
                //         'image',
                //         url
                //     )
                // })
            } catch (e) {
                document.getElementById(_this.imgId).value = ''
                _this.$message.warning('上传失败')
            }
        },
        replacePasteImg() {
            let _this = this
            let quill = this.$refs.zrQuill.quill
            quill.root.addEventListener('paste', evt => {
                if (evt.clipboardData && evt.clipboardData.files && evt.clipboardData.files.length) {
                    evt.preventDefault();
                    [].forEach.call(evt.clipboardData.files, file => {
                        if (!file.type.match(/^image\/(gif|jpe?g|a?png|bmp)/i)) {
                            return
                        }
                        file2Base64(file)
                        .then(d => {
                            let url = d
                            let Range = _this.$refs.zrQuill.quill.getSelection()
                            _this.$refs.zrQuill.quill.insertEmbed(
                                Range != null ? Range.index : 0,
                                'image',
                                url
                            )
                            quill.setSelection(Range.index + 1)
                        })
                        // upLoadFileByFile(file)
                        // .then(data => {
                        //     let baseUrl = apiFileUrl
                        //     let url = baseUrl + data.data.data.url
                        //     let Range = _this.$refs.zrQuill.quill.getSelection()
                        //     _this.$refs.zrQuill.quill.insertEmbed(
                        //         Range != null ? Range.index : 0,
                        //         'image',
                        //         url
                        //     )
                        //     quill.setSelection(Range.index + 1)
                        // })
                    })
                }
            }, false)
        },
        unableDragImg() {
            // let _this = this
            // let quill = this.$refs.zrQuill.quill
            // console.log(quill.root);
            // quill.root.draggable = false
        }
    }
}
</script>
<style scoped lang="scss">
.zr-base-quill{
    width: 100%;
    height: 100%;
    ::v-deep.ql-container{
        height: 260px;
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='12px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='12px']::before {
        content: '12px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='14px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='14px']::before {
        content: '14px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='16px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='16px']::before {
        content: '16px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='18px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='18px']::before {
        content: '18px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='20px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='20px']::before {
        content: '20px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='22px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='22px']::before {
        content: '22px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='24px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='24px']::before {
        content: '24px';
    }
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-label[data-value='26px']::before,
    ::v-deep .ql-snow .ql-picker.ql-size .ql-picker-item[data-value='26px']::before {
        content: '26px';
    }
}
</style>
