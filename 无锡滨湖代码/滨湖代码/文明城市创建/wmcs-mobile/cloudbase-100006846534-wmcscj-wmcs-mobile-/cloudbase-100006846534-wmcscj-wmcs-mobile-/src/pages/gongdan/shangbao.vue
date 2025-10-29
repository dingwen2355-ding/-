<template>
    <view class="login-page">
        <!-- {{ weixindata }} -->
        <!--{{ weixindata2 }} -->
        <uni-forms ref="form" :modelValue="formData" label-position="top" :rules="rules" label-width="200">
            <uni-forms-item required label="考核周期" name="cycleId">
                <uni-data-select v-model="formData.cycleId" :localdata="cycles" @change="cycleChange()"></uni-data-select>
            </uni-forms-item>
            <uni-forms-item required label="街道/社区" name="regionCode">
                <custom-tree-select :showChildren="false" :mutiple="false" :listData="regionTree"
                    v-model="formData.regionCode" @change="changeRegionCode()" />
            </uni-forms-item>
            <uni-forms-item required label="事发点位" name="checkPointsId">
                <uni-data-select @change="checkPointsIdChange()" v-model="formData.checkPointsId"
                    :localdata="points"></uni-data-select>
            </uni-forms-item>
            <uni-forms-item required label="点位类型" name="checkPointsTypeId">
                <uni-data-select :disabled="true" v-model="formData.checkPointsTypeId" :localdata="pointTypes"
                    @change="pointsTypeChange()"></uni-data-select>
            </uni-forms-item>
            <uni-forms-item required label="检查项目" name="checkItemId">
                <uni-data-select v-model="formData.checkItemId" :localdata="checkItems"
                    @change="changeCheckItems()"></uni-data-select>
            </uni-forms-item>
            <uni-forms-item required label="事件类型" name="eventType">
                <uni-data-picker v-model="formData.eventType" placeholder="请选择事件类型" popup-title="请选择事件类型"
                    :localdata="eventType" @change="eventTypechange" @nodeclick="eventTypeonnodeclick"
                    :map="{ text: 'name', value: 'id' }">
                </uni-data-picker>
            </uni-forms-item>
            <uni-forms-item required label="责任单位" name="deptIds">
                <custom-tree-select :mutiple="true" :listData="depts" v-model="formData.deptIds" />
            </uni-forms-item>
            <uni-forms-item required label="扣分" name="score">
                <uni-easyinput type="number" v-model="formData.score" placeholder="请输入扣分" />
            </uni-forms-item>
            <uni-forms-item required label="问题描述" name="content">
                <uni-easyinput type="textarea" v-model="formData.content" placeholder="请输入问题描述" />
            </uni-forms-item>
            <!-- <uni-forms-item label="地址描述" name="address">
                <uni-easyinput type="textarea" v-model="formData.address" placeholder="请输入地址描述" />
            </uni-forms-item> -->
            <uni-forms-item label="附件" name="attchpath">
                <uni-file-picker ref="upload" v-model="fileList" :limit="5" file-mediatype="image" mode="grid"
                    file-extname="png,jpg" @progress="progress" @success="success" @fail="fail" @delete="delIMG"
                    @select="select" />
            </uni-forms-item>
        </uni-forms>

        <button style="background-color: #4770bf;color: #fff;" @click="submit()">上报</button>
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
import uniFilePicker from "@/plugins/uni-file-picker/uni-file-picker.vue"
export default {
    components: { customTreeSelect, uniEasyinput, uniForms, uniFormsItem, uniDataSelect, uniDataPicker, uniDrawer, uniFilePicker },
    data() {
        return {
            weixindata: "",
            weixindata2: "",
            cycles: [],
            pointTypes: [],
            points: [],
            checkItems: [],
            oldcheckItems: [],
            oldpoints: [],
            eventType: [],
            depts: [],
            fileList: [],
            selectedFileList: [],
            oldDeptId: "",
            regionTree: [],
            formData: {
                checkPointsTypeId: "",
                checkPointsId: "",
                checkItemId: "",
                deptIds: [],
                eventType: "",
                eventTypeOne: "",
                eventTypeTwo: "",
                score: 0,
                eventTitle: "",
                content: "",
                address: "",
                flowid: "1234",
                routeid: "1",
                startnodeid: "Start",
                endnodeid: "Node1",
                attchpath: [],
                cycleId: "",
                cycle: "",
                longitude: "",
                latitude: "",
                regionCode: ""
            },
            uploadImages: [],
            rules: {
                checkPointsTypeId: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择点位类型',
                        },
                    ]
                },
                checkPointsId: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择事发点位',
                        },
                    ]
                },
                checkItemId: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择检查项目',
                        },
                    ]
                },
                eventType: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择事件类型',
                        },
                    ]
                },
                deptIds: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择责任单位',
                        },
                    ]
                },
                score: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请输入扣分',
                        },
                    ]
                },
                cycleId: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请选择考核周期',
                        },
                    ]
                },
                content: {
                    rules: [
                        {
                            required: true,
                            errorMessage: '请输入问题描述',
                        },
                    ]
                },
            }
        }
    },
    mounted() {
        this.getPointTypes()
        this.getEventType()
        this.getDept()
        this.getCycles()
        this.getRegionTree()
        // this.$api.jianquan().then(res => {
        //     let newConfig = {
        //         beta: true,
        //         debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        //         appId: 'ww29a8dfb1be6173c1', // 必填，政务微信的cropID
        //         timestamp: res.data.timestamp, // 必填，生成签名的时间戳
        //         nonceStr: res.data.noncestr, // 必填，生成签名的随机串
        //         signature: res.data.signature,// 必填，签名，见附录1
        //         jsApiList: ["getLocation"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        //     }
        //     this.weixindata = newConfig
        //     jWeixin.config(newConfig);

        //     jWeixin.error(function (res2) {
        //         this.weixindata2 = res2
        //     });
        //     jWeixin.ready(function (res2) {
        //         this.weixindata2 = res2
        //     });
        // })
        // uni.getLocation({
        //     type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        //     success: (res) => {
        //         this.weixindata = res
        //         this.formData.latitude = res.latitude
        //         this.formData.longitude = res.longitude
        //         var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        //         var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
        //         var speed = res.speed; // 速度，以米/每秒计
        //         var accuracy = res.accuracy; // 位置精度
        //         var gps_status = res.gps_status; //gps状态，-1：应用未获取GPS权限；
        //         console.log(res);
        //         // 0：已获取GPS权限，GPS信号异常；
        //         // 1：已获取GPS权限，GPS信号正常，AGPS信号异常；
        //         // 2：已获取GPS权限，GPS信号异常，AGPS信号正常；
        //         // 3：已获取GPS权限，GPS/AGPS信号正常
        //     },
        //     fail: (err) => {
        //         this.weixindata = err
        //         console.log(err);
        //     }
        // });
    },
    methods: {
        getRegionTree() {
            this.$api.RegionTree({}).then(res => {
                this.regionTree = res.data.records
            })
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
        submitData() {
            let requertData = this.filterResponseData(JSON.parse(JSON.stringify(this.formData)));
            requertData.attchpath = this.uploadImages.map((r) => { return r.url }).join(",")
            this.$api.eventReport(requertData).then(res => {
                if (res.code == "200") {
                    uni.navigateBack()
                }
                uni.hideLoading()
            })
        },
        filterResponseData(requertData) {
            requertData.deptIds = requertData.deptIds.join(",")
            this.eventType.forEach(e => {
                e.children.forEach(e1 => {
                    e1.children.forEach(e2 => {
                        if (e2.id == requertData.eventType) {
                            requertData.eventTypeOne = e.id
                            requertData.eventTypeTwo = e1.id
                        }
                    })
                })
            })
            return requertData;
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
        getCycles() {
            this.$api.getAssessCycleList().then(res => {
                this.cycles = [];
                res.data.records.forEach(element => {
                    this.cycles.push({ value: element.id, text: element.name })
                });
            })
        },
        getPointTypes() {
            this.$api.ListcheckPointsType().then(res => {
                this.pointTypes = [];
                res.data.records.forEach(element => {
                    this.pointTypes.push({ value: element.id, text: element.name })
                });
            })
        },
        getPoints() {
            this.$api.ListcheckPoints({ regionCode: this.formData.regionCode }).then(res => {
                this.points = [];
                this.oldpoints = res.data;
                res.data.forEach(element => {
                    this.points.push({ value: element.id, text: element.name })
                });
            })
        },
        getCheckItems() {
            this.$api.ListcheckItem(this.formData.checkPointsTypeId).then(res => {
                this.checkItems = [];
                this.oldcheckItems = res.data.records;
                res.data.records.forEach(element => {
                    this.checkItems.push({ value: element.id, text: element.name })
                });
            })
        },
        changeRegionCode() {
            console.log(this.formData.regionCode);
            this.getPoints()
        },
        changeCheckItems() {
            let node = this.oldcheckItems.filter(f => f.id = this.formData.checkItemId)[0]
            this.formData.score = node.score
            this.formData.eventTitle = node.name
            if (node.deptIds) {
                let nodedeptids = node.deptIds.split(",");
                this.formData.deptIds = [this.oldDeptId, ...nodedeptids]
            }
            this.formData.eventType = node.eventTypeId
        },
        checkPointsIdChange() {
            if (this.formData.checkPointsId) {
                if (this.oldDeptId) {
                    let index = this.formData.deptIds.indexOf(this.oldDeptId);
                    this.formData.deptIds.splice(index, 1)
                }
                let node = this.oldpoints.filter(f => f.id == this.formData.checkPointsId)[0]
                this.formData.checkPointsTypeId = node.typeId
                let depts = this.depts[0].children;
                depts.forEach(d => {
                    if (d.name == node.regionName) {
                        this.oldDeptId = d.id;
                        this.formData.deptIds.push(d.id);
                        this.formData.regionCode = node.regionCode;
                    }
                })
                this.pointsTypeChange()
            } else {
                let index = this.formData.deptIds.indexOf(this.oldDeptId);
                this.formData.deptIds.splice(index, 1)
            }
        },
        getEventType() {
            this.$api.ListeventType().then(res => {
                this.eventType = res.data.records
            })
        },
        pointsTypeChange() {
            this.formData.checkItemId = ""
            this.checkItems = [];
            this.getCheckItems()
        },
        cycleChange() {
            this.formData.cycle = this.cycles.filter(e => e.value == this.formData.cycleId)[0].text
        },
        eventTypechange() {
            // console.log(this.formData.eventType);
        },
        eventTypeonnodeclick(node) {
            // console.log(node);
        },
        getDept() {
            this.$api.ListDept().then(res => {
                this.depts = res.data.records
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
    padding: 20upx 30upx;


}

.drawer {
    padding: 30upx 20upx;
}
</style>