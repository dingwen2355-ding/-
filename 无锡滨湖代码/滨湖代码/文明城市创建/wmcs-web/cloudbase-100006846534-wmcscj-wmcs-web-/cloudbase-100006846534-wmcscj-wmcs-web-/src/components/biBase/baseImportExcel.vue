<template>
    <div>
        <t-button class="margin-right-10" theme="warning" @click="openImport()">导入</t-button>
        <el-dialog
            v-if="dia_show_window"
            title="导入"
            :visible.sync="dia_show_window"
            append-to-body
            width="700px"
            :before-close="cancel"
        >
            <div v-loading="v_loading" class="dia-bd-hei">
                <div style="margin-bottom:20px;text-align: center;">
                    <!-- <span style="margin-right:20px;">上传Excel</span> -->
                    <span style="color: #409EFF;cursor: pointer;" @click="downMuBan()">下载Excel模板</span>
                </div>
                <div class="show-flex-r j-c">
                    <el-upload
                        drag
                        multiple
                        :auto-upload="false"
                        :file-list="filelist"
                        :on-change="addfile"
                        :on-remove="delfile"
                        :accept="accept"
                        action="/noaction"
                    >
                        <div style="height:100%;" class="show-flex-c d-c">
                            <img src="~@/assets/images/file_icon/excel.png" style="width: 70px;height: 70px;" />
                            <div style="margin-top:20px;">点击或将文件拖拽到这里上传</div>
                            <div>支持扩展名：.xls、.xlsx</div>
                        </div>
                    </el-upload>
                </div>
                <div style="max-height: 100px;overflow: auto;margin-top:10px;">
                    <div v-for="(errmessage, index) in errorList" :key="index" class="zr-link-d">{{ errmessage }}</div>
                </div>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button size="mini" type="warning" @click="cancel()">取 消</el-button>
                <el-button size="mini" type="primary" @click="importData()">导入数据</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
const XLSX = require('xlsx')
const FileSaver = require('file-saver')
export default {
    props: {
        outImport: {
            type: Function,
            default: () => {
                return new Promise((res, rej) => {
                    res()
                })
            }
        },
        getKeyArray: {
            type: Function,
            default: () => {
                return []
            }
        },
        mubanTitle: {
            type: String,
            default: '模板'
        }
    },
    data() {
        return {
            dia_show_window: false,
            errorList: [],
            v_loading: false,
            filelist: [],
            accept: '.xls,.xlsx'
        }
    },
    methods: {
        openImport() {
            console.log('888888')
            this.dia_show_window = true
        },
        cancel() {
            this.dia_show_window = false
            this.filelist = []
            this.v_loading = false
            this.errorList = []
        },
        addfile(file, b) {
            const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1)
            const whiteList = ['xls', 'xlsx']
            if (whiteList.indexOf(fileSuffix) === -1) {
                this.$errorMessage('上传文件只能是 xls,xlsx格式')
                b.splice(b.indexOf(file), 1)
            }
            this.filelist = b
        },
        delfile(a, b) {
            this.filelist = b
        },
        downMuBan() {
            // 下载模板
            let keyArr = this.getKeyArray()
            let obj = {}
            keyArr.forEach(k => {
                obj[k.name] = ''
            })
            let workBook = {
                SheetNames: ['Sheet1'], Sheets: {}, Props: {}
            }
            workBook.Sheets['Sheet1'] = XLSX.utils.json_to_sheet([obj])
            workBook.Sheets['Sheet1']['!cols'] = keyArr.map(i => {
                return { wch: 35 }
            })
            var wbout = XLSX.write(workBook, {
                bookType: 'xlsx',
                bookSST: true,
                type: 'array'
            })
            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), this.mubanTitle + '.xlsx')
        },
        importData() {
            let _this = this
            this.errorList = []
            console.log('开始上传...')
            if (this.v_loading) return
            if (this.filelist.length == 0) return this.$alert('请先上传文件!')
            this.v_loading = true
            Promise.all(this.filelist.map(i => { return this.read_excel(i.raw) }))
            .then(fileList => {
                let arr = []
                fileList.forEach(fi => {
                    fi.forEach(item => {
                        arr.push(item)
                    })
                })
                return _this.outImport(arr)
            })
            .then(data => {
                _this.$successMessage('导入成功')
                _this.cancel()
                _this.$emit('success')
                return
            })
            .catch(e => {
                if (e instanceof Array) {
                    console.log(e)
                    _this.errorList = e
                    _this.$errorMessage('上传失败')
                }
            })
            .finally(e => {
                _this.v_loading = false
            })
        },
        read_excel(file) {
            let _this = this
            return new Promise((res, rej) => {
                // 开始转
                let reader = new FileReader()
                reader.readAsArrayBuffer(file)
                reader.onload = function() {
                    try {
                        const buffer = reader.result
                        const bytes = new Uint8Array(buffer)
                        const length = bytes.byteLength
                        let binary = ''
                        for (let i = 0; i < length; i++) {
                            binary += String.fromCharCode(bytes[i])
                        }
                        const wb = XLSX.read(binary, {
                            type: 'binary'
                        })
                        const outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])
                        res(outdata)
                    } catch (e) {
                        console.log(e)
                        // rej('文件验证失败')
                        _this.$alert('导入失败,请检查文件格式是否和模板一致')
                        throw new Error('失败')
                    }
                }
            })
        }
    }
}
</script>
