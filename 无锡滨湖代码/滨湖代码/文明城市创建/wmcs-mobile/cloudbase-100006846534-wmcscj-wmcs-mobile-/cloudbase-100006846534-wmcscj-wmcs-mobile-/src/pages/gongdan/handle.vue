<template>
    <view class="login-page">
        <UniSegmentedControl class="swicth" :current="current" :values="items" @clickItem="onClickItem" styleType="button"
            activeColor="#4770bf"></UniSegmentedControl>
        <template v-if="current == 1">
            <div class="field">
                <div class="label">考核周期：</div>
                <div class="value">{{ row.cycle }}</div>
            </div>
            <div class="field">
                <div class="label">点位类型：</div>
                <div class="value">{{ row.checkPointsType }}</div>
            </div>
            <div class="field">
                <div class="label">事发点位：</div>
                <div class="value">{{ row.checkPoints }}</div>
            </div>
            <div class="field">
                <div class="label">检查项目：</div>
                <div class="value">{{ row.checkItem }}</div>
            </div>
            <div class="field">
                <div class="label">责任单位：</div>
                <div class="value">{{ row.deptNames }}</div>
            </div>
            <div class="field">
                <div class="label">扣分：</div>
                <div class="value">{{ row.score }}</div>
            </div>
            <div class="field">
                <div class="label">事件类型：</div>
                <div class="value">{{ row.eventTypeOneName }}~{{ row.eventTypeTwoName }}~{{ row.eventTypeName }}</div>
            </div>
            <div class="field">
                <div class="label">地址描述：</div>
                <div class="value">{{ row.address }}</div>
            </div>
            <div class="field">
                <div class="label">问题描述：</div>
                <div class="value">{{ row.content }}</div>
            </div>
            <div class="field">
                <div class="label">附件：</div>
                <div class="value">
                    <div class="file">
                        <image v-for="i, index in files" :key="index" :src="i" @click="preview(index)"></image>
                    </div>
                </div>
            </div>

        </template>
        <template v-if="current == 0">
            <uni-section title="事件流程" type="line"></uni-section>
            <uni-steps :active="(row.status || 10) / 10 - 1" :options="stepsData" active-color="#4770bf" direction="column">
            </uni-steps>
            <template v-if="title == '受理'">
                <uni-section title="事件处置方式" type="line"></uni-section>
                <uni-data-checkbox style="padding-left:30px;" v-model="submitType"
                    :localdata="submitTypes"></uni-data-checkbox>
            </template>
            <uni-section title="事件处置" type="line"></uni-section>
            <template v-if="submitType == 1">
                <uni-forms ref="form" :modelValue="formData" label-position="top" :rules="rules">
                    <uni-forms-item required label="节点" name="nodeid">
                        <uni-data-picker v-model="formData.nodeid" placeholder="请选择节点" popup-title="请选择节点"
                            :localdata="nodeList" @change="nodeChange">
                        </uni-data-picker>
                        <!-- <uni-data-select v-model="formData.nodeid" :localdata="nodeList"
                            @change="nodeChange()"></uni-data-select> -->
                    </uni-forms-item>
                    <uni-forms-item label="处理人" name="handlerid">
                        <uni-data-checkbox :multiple="nodeHandleType == 2" v-model="formData.handleDeptid"
                            :localdata="handlerids"></uni-data-checkbox>
                    </uni-forms-item>
                    <uni-forms-item required label="处理意见" name="handleopinion">
                        <uni-easyinput type="textarea" v-model="formData.handleopinion" placeholder="请输入处理意见" />
                    </uni-forms-item>
                    <uni-forms-item label="附件" name="attchpath">
                        <uni-file-picker ref="upload" v-model="fileList" :limit="5" file-mediatype="image" mode="grid"
                            file-extname="png,jpg" @delete="delIMG" @progress="progress" @success="success" @fail="fail"
                            @select="select" />
                    </uni-forms-item>
                </uni-forms>
                <button style="background-color: #4770bf;color: #fff;" @click="submit()">{{ title }}</button>
            </template>
            <template v-if="submitType == 2">
                <uni-forms ref="form2" :modelValue="formData2" label-position="top" :rules="rules2">
                    <uni-forms-item required label="时间" name="extensionDate">
                        <picker mode="date" :value="formData2.extensionDate" @change="bindDateChange">
                            <view class="uni-input">{{ formData2.extensionDate }}</view>
                        </picker>
                    </uni-forms-item>
                    <uni-forms-item required label="原因" name="reason">
                        <uni-easyinput type="textarea" v-model="formData2.reason" placeholder="请输入原因" />
                    </uni-forms-item>
                    <uni-forms-item label="附件" name="attchpath">
                        <uni-file-picker ref="upload" v-model="fileList" :limit="5" file-mediatype="image" mode="grid"
                            file-extname="png,jpg" @delete="delIMG" @progress="progress" @success="success" @fail="fail"
                            @select="select" />
                    </uni-forms-item>
                </uni-forms>
                <button style="background-color: #4770bf;color: #fff;" @click="extensionSave()">延期</button>
            </template>
            <template v-if="submitType == 3">
                <uni-forms ref="form3" :modelValue="formData3" label-position="top" :rules="rules3">
                    <uni-forms-item required label="原因" name="reason">
                        <uni-easyinput type="textarea" v-model="formData2.reason" placeholder="请输入原因" />
                    </uni-forms-item>
                </uni-forms>
                <button style="background-color: #4770bf;color: #fff;" @click="appealSave()">申诉</button>
            </template>
        </template>

    </view>
</template>

<script>
import uniEasyinput from "@/plugins/uni-easyinput/uni-easyinput.vue"
import uniForms from "@/plugins/uni-forms/uni-forms.vue"
import uniFormsItem from "@/plugins/uni-forms-item/uni-forms-item.vue"
import uniDataSelect from "@/plugins/uni-data-select/uni-data-select.vue"
import uniDataPicker from "@/plugins/uni-data-picker/uni-data-picker.vue"
import uniDrawer from "@/plugins/uni-drawer/uni-drawer.vue"
import customTreeSelect from "@/plugins/custom-tree-select/custom-tree-select.vue"
import UniDataCheckbox from "../../plugins/uni-data-checkbox/uni-data-checkbox.vue"
import uniSteps from "@/plugins/uni-steps/uni-steps.vue"
import uniSection from "@/plugins/uni-section/uni-section.vue"
import UniSegmentedControl from '../../plugins/uni-segmented-control/uni-segmented-control.vue'
import uniFilePicker from "@/plugins/uni-file-picker/uni-file-picker.vue"

export default {
    components: { uniFilePicker, UniSegmentedControl, customTreeSelect, uniEasyinput, uniForms, uniFormsItem, uniDataSelect, uniDataPicker, uniDrawer, UniDataCheckbox, uniSteps, uniSection },
    data() {
        return {
            submitType: 1,
            submitTypes: [
                {
                    text: "受理", value: 1
                },
                {
                    text: "延期", value: 2
                },
                {
                    text: "申诉", value: 3
                }
            ],
            files: [],
            fileList: [],
            selectedFileList: [],
            info: {},
            stepsData: [],
            title: "受理",
            items: ["事件处置", "事件详情"],
            current: 0,
            nodeList: [],
            handlerids: [],
            nodeHandleType: 1,
            formData: {
                flowinstanceid: "",
                nodeid: "",
                handleopinion: "",
                handleDeptid: "",
            },
            formData2: {
                taskId: "",
                extensionDate: "",
                reason: ""
            },
            formData3: {
                applyReason: ""
            },
            rules: {
                nodeid: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择节点',
                        }, {
                            validateFunction: function (rule, value, data, callback) {
                                if (!value) {
                                    callback('请选择节点')
                                }
                                return true
                            }
                        }
                    ]
                },
                // handlerid: {
                //     rules: [
                //         {
                //             required: true,
                //             errorMessage: '请选择事处理人',
                //         },
                //     ]
                // },
                handleopinion: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请输入处理意见',
                        },
                    ]
                },
            },
            rules2: {
                nodeid: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择节点',
                        },
                    ]
                },
                extensionDate: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择时间',
                        },
                    ]
                },
                reason: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请输入原因',
                        },
                    ]
                },
            },
            rules3: {
                applyReason: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请输入原因',
                        },
                    ]
                },
            },
            uploadImages: []
        }
    },
    onLoad(query) {
        this.row = JSON.parse(decodeURIComponent(query.row))
        this.files = [];
        let files = this.row.attchpath.split(",");
        files.map(re => {
            this.files.push(this.$resource(re))
        })
        this.title = query.title;
        this.formData.flowinstanceid = this.row.flowinstanceid;
        this.formData.optTag = this.row.optTag;
        uni.setNavigationBarTitle({
            title: this.title
        })
        this.$api.myReportDetail(this.row.id, this.row.flowinstanceid, this.row.scope, this.row.taskid).then(res => {
            this.info = res.data.eventReport;
            this.formData = Object.assign(this.formData, this.info)
            if (this.info.handlerDeptType == null || this.info.handlerDeptType == 2) {
                this.stepsData = [
                    { title: "事件提交" }, { title: "区分派" }, { title: "单位受理" }, { title: "社区整改" }, { title: "区核查" }, { title: "已办结" },
                ]
            } else {
                this.stepsData = [
                    { title: "事件提交" }, { title: "区分派" }, { title: "单位受理" }, { title: "区核查" }, { title: "已办结" },
                ]
            }
        })

    },
    mounted() {
        this.getNodeList()
    },
    methods: {
        bindDateChange: function (e) {
            this.formData2.extensionDate = e.detail.value
        },
        submit() {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            this.$refs.form.validate().then(res => {
                this.submitData()
            }).catch(err => {
                uni.hideLoading()
                console.log('表单错误信息：', err);
            })
        },
        async submitData() {
            let requertData = JSON.parse(JSON.stringify(this.formData));
            requertData.attchpath = this.uploadImages.map((r) => { return r.url }).join(",")
            if (this.nodeHandleType == 2 && requertData.handlerid) {
                requertData.handlerid = requertData.handlerid.join(",");
            }
            this.$api.submitEvent(requertData).then(res => {
                if (res.code == "200") {
                    uni.navigateBack()
                }
                uni.hideLoading()
            })
        },
        extensionSave() {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            let requertData = JSON.parse(JSON.stringify(this.formData2));
            requertData.taskId = this.row.taskid;
            requertData.extensionDate = requertData.extensionDate + " 00:00:00"
            this.$refs.form2.validate().then(res => {
                this.$api.extensionSave(requertData).then(res => {
                    if (res.code == "200") {
                        uni.navigateBack()
                    }
                    uni.hideLoading()
                })
            }).catch(err => {
                uni.hideLoading()
                console.log('表单错误信息：', err);
            })

        },
        appealSave() {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            let requertData = JSON.parse(JSON.stringify(this.formData2));
            requertData.taskId = this.row.taskid;
            this.$refs.form3.validate().then(res => {
                this.$api.appealsave(requertData).then(res => {
                    if (res.code == "200") {
                        uni.navigateBack()
                    }
                    uni.hideLoading()
                })
            }).catch(err => {
                uni.hideLoading()
                console.log('表单错误信息：', err);
            })

        },
        getNodeList() {
            this.$api.editEvent({
                taskId: this.row.taskid,
                flowinstanceid: this.row.flowinstanceid
            }).then(r => {
                this.nodeList = r.data.routeList.map(item => {
                    return { text: item.endnodename, value: item.endnodeid };
                });
                if (this.nodeList.length == 1) {
                    this.formData.nodeid = this.nodeList[0].value
                    this.nodeChange()
                }
            });
        },
        gethandlerids() {
            this.$api.getDeptsByNodeId({
                flowid: "1234",
                nodeid: this.formData.nodeid,
                flowInstanceId: this.row.flowinstanceid
            }).then(r => {
                this.nodeHandleType = r.data.nodeHandleType
                if (r.data.value) {
                    this.handlerids = r.data.value.map(item => {
                        return { text: item.name, value: item.id };
                    });
                    if (this.handlerids.length == 1) {
                        this.formData.handlerid = this.handlerids[0].value
                    }
                }

            });
        },
        nodeChange() {
            this.formData.handlerid = ""
            this.handlerids = [];
            this.gethandlerids()
        },
        onClickItem(e) {
            if (this.current != e.currentIndex) {
                this.current = e.currentIndex;
            }
        },
        preview(index) {
            uni.previewImage({
                current: index,//当前所点击预览的图片地址
                urls: this.files,//这就是当前行图片数据，注意一定要是数组格式
                indicator: 'number',
                loop: true
            });
        },
        upload(pathList) {
            var _this = this;
            var returnList = []
            return new Promise((resolve, reject) => {
                // 循环数组上传若干个图片地址
                for (let i = 0; i < pathList.length; i++) {
                    uni.uploadFile({
                        url: upload_base_url, // 仅为示例，非真实的接口地址
                        filePath: pathList[i],
                        name: 'file',
                        formData: {
                        },
                        header: {
                            "Authorization": uni.getStorageSync("access_token")
                        },
                        success: (res) => {
                            returnList.push(JSON.parse(res.data).data)
                            resolve(returnList)
                        }
                    });
                }
            })
        },
        delIMG(e) {
            const num = this.selectedFileList.findIndex(v => v.url === e.tempFilePath);
            const num2 = this.uploadImages.findIndex(v => v.tempFilePath === e.tempFilePath);
            this.selectedFileList.splice(num, 1);
            this.uploadImages.splice(num2, 1);
        },
        // 获取上传状态
        select(e) {
            this.selectedFileList.push(e.tempFilePaths[0])
            uni.uploadFile({
                url: upload_base_url, // 仅为示例，非真实的接口地址
                filePath: e.tempFilePaths[0],
                name: 'file',
                formData: {
                },
                header: {
                    "Authorization": uni.getStorageSync("access_token")
                },
                success: (res) => {
                    this.uploadImages.push({
                        url: JSON.parse(res.data).data.url,
                        tempFilePath: e.tempFilePaths[0]
                    })
                }
            });
        },
        // 获取上传进度
        progress(e) {
            console.log('上传进度：', e)
        },

        // 上传成功
        success(e) {
            console.log('上传成功')
        },

        // 上传失败
        fail(e) {
            console.log('上传失败：', e)
        }
    }
}
</script>
  
<style scoped lang="scss">
page {
    background: #fff;
}

.login-page {
    height: 100vh;
    padding: 30upx 30upx;
}

.uni-input {
    border: 1px solid #dcdfe6;
    height: 36px;
    border-radius: 4px;
    line-height: 36px;
    padding-left: 10px;
}

.field {
    width: 100%;
    margin: 25rpx;
    display: flex;
    flex-direction: row;
    justify-content: space-around;

    .label {
        width: 25%;
    }

    .value {
        width: 75%;
        color: #4770bf;

        .file {
            width: 100%;

            image {
                width: 30%;
                height: 180upx;
                margin-right: 2%;
                margin-bottom: 20upx;
            }
        }
    }
}
</style>