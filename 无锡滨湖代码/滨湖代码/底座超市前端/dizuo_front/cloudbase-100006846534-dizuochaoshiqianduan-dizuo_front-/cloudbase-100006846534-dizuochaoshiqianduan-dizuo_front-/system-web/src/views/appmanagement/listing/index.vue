<template>
  <!-- 申请上架 -->
  <div class="appwrap">
    <!-- 头部筛选条件 -->
    <el-row>
      <el-col :span="6">
        应用名称:
        <el-input class="input" v-model="sAppSort" placeholder="请输入应用名称" clearable></el-input>
      </el-col>
      <el-col :span="6">
        审核状态:
        <el-select v-model="sShStutas" placeholder="请选择是否通过" @change="fChangeIsSj">
          <el-option v-for="item in aShStatus" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="8">
        申请时间内:
        <el-date-picker value-format="yyyy-MM-dd" style="width: 70%;" v-model="sSqTime" type="daterange"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="fSearch()">查询</el-button>
        <el-button @click="fChongzhi()">重置</el-button>
      </el-col>
    </el-row>
    <br />
    <br />
    <el-table ref="multipleTable" :data="aTableData" stripe tooltip-effect="dark" style="width: 100%"
      @selection-change="fSelectionChange">
      <!-- <el-table-column type="selection" width="55">
      </el-table-column> -->
      <el-table-column prop="appName" label="应用名称"></el-table-column>
      <el-table-column prop="appLink" label="应用地址" show-overflow-tooltip></el-table-column>
      <el-table-column prop="applicationReason" label="申请理由" show-overflow-tooltip></el-table-column>
      <el-table-column prop="params.createByUname" label="申请人"></el-table-column>
      <el-table-column prop="createTime" label="申请时间" show-overflow-tooltip></el-table-column>
      <el-table-column prop="params.examine.remark" label="审核意见" show-overflow-tooltip></el-table-column>
      <!-- <el-table-column prop="status" label="审核状态"></el-table-column> -->
      <el-table-column align="center" prop="status" label="审核状态">
        <template slot-scope="{row}">
          <span :class="['status-item', '_bg' + row.status]"> {{ fSetSattus(aShStatus, row.status) }}</span>
        </template>
        <!-- <el-button size="mini" :type="fSetType(aShStatus, scope.row.status)" plain>
            {{ fSetSattus(aShStatus, scope.row.status) }}
          </el-button> -->
      </el-table-column>
      <el-table-column prop="updateTime" label="处理时间" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="操作" width="300px">
        <div class="wrap" slot-scope="scope">
          <el-button size="mini" type="primary" plain @click="fGetDetails(scope.row, true)">详情</el-button>
          <el-button size="mini" v-if="scope.row.status == 1" type="primary" plain
            @click="fGetDetails(scope.row, false)">操作
          </el-button>
          <!-- <el-button v-if="scope.row.status == 1" size="mini" type="warning" plain
              @click="fChangeStatus(scope.row, 2, '驳回')">驳回</el-button>
          <el-button v-if="scope.row.status == 1" size="mini" type="success" plain
              @click="fChangeStatus(scope.row, 0, '上架')">上架</el-button> -->
        </div>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination @current-change="fCurrentChange" :current-page="nCurrent" :page-size="15"
      layout="total, prev, pager, next, jumper" :total="nTotal">
    </el-pagination>

    <!-- 新增弹框 -->
    <el-dialog title="申请上架审核" :visible.sync="bDialogVisible" width="70%">
      <el-form ref="form" :model="oAddForm" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用编号">
              {{ oAddForm.appSerialNo || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序">
              {{ oAddForm.sortBy || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用名称">
              {{ oAddForm.appName || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否入驻">
              {{ fSetLabel(aIsSj, oAddForm.status, 'value', 'label',1) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用分类">
              {{ fSetLabel(dict.type.app_module, oAddForm.appModule, 'value', 'label') }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否置顶">
              {{ oAddForm.zhiding == 2 ? '是' : '否' }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用来源">
              {{ fSetLabel(dict.type.app_source, oAddForm.source, 'value', 'label') }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数字底座展示">
              {{ oAddForm.show == 2 ? '是' : '否' }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="场景分类">
              {{ fSetLabel(dict.type.app_type, oAddForm.appType, 'value', 'label') }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否推荐">
              {{ fSetLabel(aIsTj, oAddForm.recommendFlag, 'value', 'label') }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人">
              {{ oAddForm.principalName || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人电话">
              {{ oAddForm.principalPhone || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属部门">
              {{ fSetSsbm(oAddForm.dept) }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用链接">
              {{ oAddForm.appLink || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用介绍">
              {{ oAddForm.remark || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="特色亮点">
              {{ oAddForm.liangdian || '--' }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图标">
              <el-image v-if="oAddForm.appPic" style="width: 100px; height: 100px" :src="oAddForm.appPic"
                :preview-src-list="[oAddForm.appPic]">
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用成果">
              <el-image v-for="(url, idx) in oAddForm.appScreenshot" :key="idx" style="width: 100px; height: 100px"
                :src="url" :preview-src-list="oAddForm.appScreenshot">
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="亮点截图">
              <el-image v-for="(url, idx) in oAddForm.liangdianpic" :key="idx" style="width: 100px; height: 100px"
                :src="url" :preview-src-list="oAddForm.liangdianpic">
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="附件信息">
              <el-table border stripe :data="fSetTable(null, oAddForm.upload)">
                <el-table-column align="center" type="index" width="70px" label="编号"></el-table-column>
                <el-table-column align="center" prop="originalFilename" label="文件名">
                  <template slot-scope="{row}">
                    {{ row.originalFilename.split('.')[0] }}
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="originalFilename" label="文件类型">
                  <template slot-scope="{row}">
                    {{ row.originalFilename.split('.')[1] }}
                  </template>
                </el-table-column>
                <el-table-column align="center" width="100px" label="操作">
                  <template slot-scope="scope">
                    <el-button type="primary" @click="fDownloadFile(scope.row)">下载</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="审核状态">
              <el-radio v-model="oAddForm.status" :disabled="disabled" :label="0">通过</el-radio>
              <el-radio v-model="oAddForm.status" :disabled="disabled" :label="2">驳回</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="审核意见">
              <el-input type="textarea" :rows="3" :disabled="disabled" v-model="remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="fOnSubmit" :disabled="disabled">操作</el-button>
          <el-button @click="bDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 详情弹框 -->
    <!-- <el-dialog title="新增应用类别" :visible.sync="bDialogVisible" width="50%">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="bDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="bDialogVisible = false">确 定</el-button>
        </span>
    </el-dialog> -->
    <el-dialog :visible.sync="bImageUrlVisible">
      <img width="100%" :src="sDialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>

import {
  cmAppTypeList,// 请求应用类别
  cmAppReplayList,//查询申请上架列表
  cmAppUpdateStatus,// 状态更新
  cmAppDetail,
} from "@/api/management/management";
import { getToken } from '@/utils/auth'

import { addApplicationDept } from "@/api/cas/cas";

export default {
  name: '',
  dicts: ['app_module', 'sys_user_sex', 'app_source', 'app_type'],
  components: {},
  props: [''],
  data() {
    return {
      token: getToken(),
      sUploadUrl: 'https://2.21.138.89:11511/system_server/common/upload',
      sId: '',
      sAppName: '',// 应用名称
      // 应用类型
      sAppSort: '',
      aAppSort: [],
      // 审核状态
      sShStutas: '',
      aShStatus: [
        { value: 0, label: '通过', type: 'success', },
        { value: 1, label: '待审核', type: 'primary', },
        { value: 2, label: '驳回', type: 'warning', },
        { value: 3, label: '下架', type: 'danger', },
        { value: 4, label: '撤回', type: 'warning', },
      ],
      aSsbm: [],
      // 是否上架
      sIsSj: '',
      aIsSj: [
        { value: 0, label: '通过' },
        { value: 1, label: '待审核' },
        { value: 2, label: '驳回' },
        { value: 3, label: '下架' },
        { value: 4, label: '撤回' },
      ],
      aIsrz: [
        { value: 0, label: '否' },
        { value: 1, label: '是' },
      ],
      // 是否推荐
      sIsTj: '',
      aIsTj: [
        { value: 0, label: '是' },
        { value: 1, label: '否' },
      ],
      // 申请时间
      sSqTime: '',
      // 表格数据
      aTableData: [],
      aMultipleSelection: [],
      nCurrent: 1, // 分页页码
      nTotal: 0, // 总条数
      bDialogVisible: false,
      disabled: false,
      oAddForm: {
        appSerialNo: '',// 编号
        principalName: '',// 联系人
        principalPhone: '',// 联系人电话号码
        appPic: '',// 应用图标
        appName: '',// 应用名称
        appModule: '',// 所属模块
        sortBy: '', // 显示顺序
        status: null,// 是否上架
        recommendFlag: null,// 是否推荐
        appType: '',// 应用类别
        dept: '',// 所属部门
        appLink: '',// 应用链接
        remark: "",// 应用介绍
        liangdian: '',// 特色亮点
        appScreenshot: [],
        upload: [],
        source: '',
        zhiding: '',
        show: '',
        liangdianpic:[],
      },
      remark: "",
      sDialogImageUrl: '',
      bImageUrlVisible: false,
      startDate: '',//
      endDate: '',//
      aAllDept: [],
      aPicList: [],
      aPicListV2: [],


    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {
    this.getList();
    this.fGetDeptName();
    // console.log(this.dict.type.app_module)

  },
  beforeDestroy() {

  },
  methods: {
    fSetTable(arr1, arr2) {
      if (arr1 && arr1.length > 0 & arr2 && arr2.length > 0) {
        return [...arr1, ...arr2]
      } else if (arr1 && arr1.length > 0) {
        return arr1
      } else if (arr2 && arr2.length > 0) {
        return arr2
      }
    },
    fSetLabel(data, value, key, label,flag) {
      // console.log(data,"1")
      if(flag == 1){
        if(value){
          return data.filter(item => item[key] == value)[0][label] || '--'
        }else{
          value = 0;
         return data.filter(item => item[key] == value)[0][label] || '--'
        }
      }else{
        if (value) {
        // console.log(value)
        // console.log( this.oAddForm.status,"222")
          return data.filter(item => item[key] == value)[0][label] || '--'
        } else {
          return '--'
        }
      }
    },
    fUploadFile(res, file, fileList) {
      // console.log(res,file,fileList)
      this.oAddForm.upload = []
      fileList.forEach(item => {
        this.oAddForm.upload.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.newFileName,
          url: item.response.url,
          originalFilename: item.response.originalFilename,
        })
      })
    },
    checkAppModule(e) {
      // this.getAppType(e)
      this.oAddForm.appType = null
      this.fAppSort = this.aAppSort.filter(item => {
        return item.appModule === e;
      })
    },
    downloadFile(blob, fileName) {
      let blobUrl = window.URL.createObjectURL(new Blob([blob], { type: 'application/force-download' }))
      let a = document.createElement('a')
      a.style.display = 'none'
      a.setAttribute("href", blobUrl)
      a.setAttribute('download', `${fileName}`);
      document.body.appendChild(a)
      a.click() //执行下载
      window.URL.revokeObjectURL(blobUrl)
      document.body.removeChild(a)
    },
    // 附件下载
    fDownloadFile(row) {
      downloadResource({ resource: row.url }).then(res => {
        this.downloadFile(res, row.newFileName)
      })
    },
    // 文件预览
    handlePreview(data) {
      downloadResource({ resource: data.response.url }).then(res => {
        this.downloadFile(res, data.response.newFileName)
        return
      })
    },
    fGetDeptName() {
      addApplicationDept().then(res => {
        this.aSsbm = res.data;
        this.aAllDept = []
        this.findNode(res.data)
      })
    },
    findNode(arr) {
      arr.forEach(item => {
        this.aAllDept.push(item)
        item.children && item.children.length > 0 ? this.findNode(item.children) : ""
        item.children = ''
      })
    },
    fSetSsbm(id, arr) {
      let aData = this.aAllDept.filter(item => item.id == id)
      // console.log(aData)
      return aData.length > 0 ? aData[0].title : '--'
    },
    // 搜索按钮
    fSearch() {
      this.nCurrent = 1;
      // console.log(this.sSqTime)
      this.getList();
    },
    // 重置按钮
    fChongzhi() {
      this.sSqTime = ['', '',]
      this.sAppSort = ''
      this.sShStutas = ''
      this.nCurrent = 1;
      this.getList();
    },
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      cmAppReplayList({
        startDate: this.sSqTime[0] || '',// 开始时间
        endDate: this.sSqTime[1] || '',// 开始时间
        // appType: this.sAppSort,// 应用类别
        appName: this.sAppSort,// 应用名称
        status: this.sShStutas,// 是否上架
      }, {
        pageNum: this.nCurrent,// 页码
        pageSize: 13,// 每页的数量
      }).then(res => {
        // console.log(res)


        this.aTableData = res.data.records;
        this.nTotal = res.data.total;
      }
      );
    },
    /** 请求应用类别 */
    getAppType(appModule) {
      this.loading = true;
      cmAppTypeList({
        typeName: '',// 类型名称
        appModule: appModule,// 所属模块
        status: 0,// 状态
        pageNum: 1,// 页码
        pageSize: 1000,// 每页的数量
      }).then(res => {
        // console.log(res)
        this.aAppSort = res.data.records;
      }
      );
    },
    // 获取详情
    fGetDetails(row, status) {
      // this.oAddForm = { ...row };
      // this.bDialogVisible = true

      this.disabled = status;
      // console.log(this.disabled)
      this.sId = row.id;
      // this.getAppType(this.oAddForm.appModule);
      cmAppDetail({ id: row.id }).then(res => {

        this.fileList = []
        this.fileListV2 = []
        for (const key in this.oAddForm) {
          this.oAddForm[key] = res.data[key]
        }
        console.log(this.oAddForm.status,"09kmjucdi")

        this.aPicList = []
        this.getAppType(this.oAddForm.appModule)
        // console.log(this.oAddForm.appScreenshot)
        if (res.data.appScreenshot) {
          JSON.parse(res.data.appScreenshot).forEach(item => {
            this.aPicList.push({
              name: item,
              url: item,
            })
          })
          this.aPicList =
            this.oAddForm.appScreenshot = JSON.parse(this.oAddForm.appScreenshot)
          this.aPicList = this.fSetPicArr(this.oAddForm.appScreenshot)

        }

        if (res.data.liangdianpic) {
          JSON.parse(res.data.liangdianpic).forEach(item => {
            this.aPicListV2.push({
              name: item,
              url: item,
            })
          })
          this.aPicListV2 =
            this.oAddForm.liangdianpic = JSON.parse(this.oAddForm.liangdianpic)
          this.aPicListV2 = this.fSetPicArr(this.oAddForm.liangdianpic)
        }
        this.oAddForm.upload = this.oAddForm?.upload
        this.oAddForm.moban = this.oAddForm?.moban
        this.fileList = this.oAddForm?.upload || []
        this.fileListV2 = this.oAddForm?.moban || []

        this.bDialogVisible = true
      })
    },
    // 设置图片回显
    fSetPicArr(data) {
      let array = []
      data.forEach((item, idx) => {
        array.push({
          name: idx,
          url: item
        })
      })
      return array;
    },
    // 下架按钮
    fChangeStatus(row, status, text) {
      this.oAddForm = { ...row };
      this.bDialogVisible = true; // 打开弹框
      this.disabled = false; //  是否禁用
      this.sId = row.id;
      this.getAppType(this.oAddForm.appModule);

      // cmAppUpdateStatus({
      //     id: row.id,// 类型名称
      //     status: status,// 状态
      // }).then(res => {
      //     // console.log(res)
      //     this.nCurrent = 1;
      //     this.getList();
      //     this.$message({
      //         message: text + '成功',
      //         type: 'success'
      //     });
      // })
    },
    // 设置文字
    fSetSattus(data, text) {
      let stext = ''
      if (data.filter(item => item.value == text)[0]) {
        stext = data.filter(item => item.value == text)[0].label;
      } else {
        stext = '--';
      }
      return stext;
    },
    fSetType(data, text) {
      let stext = 'primary'
      if (data.filter(item => item.value == text)[0]) {
        stext = data.filter(item => item.value == text)[0].type;
      } else {
        stext = 'primary';
      }
      return stext;
    },
    // 应用类别切换
    fChangeAppSort(val) {
      // console.log(val)
    },
    // 是否上架切换
    fChangeIsSj(val) {
      // console.log(val)
    },
    // 是否推荐切换
    fChangeIsTj(val) {
      // console.log(val)
    },
    // 表格多选框
    fSelectionChange(val) {
      this.aMultipleSelection = val;
    },
    // 分页切换
    fCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.nCurrent = val
      this.getList();
    },
    // 删除图片
    fRemoveImg(file, fileList) {
      // console.log(file, fileList);
    },
    // 预览图片
    fPictureCardPreview(file) {
      this.sDialogImageUrl = file.url;
      this.bImageUrlVisible = true;
    },
    // 弹框确定按钮
    fOnSubmit() {
      if ((this.oAddForm.status === 0 || this.oAddForm.status === 2) && this.remark) {
        cmAppUpdateStatus({
          id: this.sId,// 类型名称
          status: this.oAddForm.status,// 状态
          remark: this.remark,// 审核理由
        }).then(res => {
          // console.log(res)
          this.nCurrent = 1;
          this.bDialogVisible = false;
          this.getList();
          this.$message({
            message: '操作成功',
            type: 'success'
          });
        })
      } else {
        this.$message({
          message: '请完成表单内容',
          type: 'warning'
        });
      }
    }
  }
}
</script>
<style lang='scss' scoped>
.appwrap {
  height: 100%;
  width: 100%;
  padding: 30px;
}

// ::v-deep .el-col {
//     display: flex;
//     align-items: center;
//     justify-content: center;
// }

::v-deep .el-select {
  width: 70%;
}

::v-deep .el-pagination {
  display: flex;
  align-items: center;
  justify-content: end;
  margin-top: 20px;
}

::v-deep .el-input {
  width: 200px;
}

.item-status {
  display: inline-block;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 15px;
  font-weight: bold;
  color: #fff;

}

// ._bg0 {
//   background: #3bd11d;
// }

// ._bg1 {
//   background: #0d8ade;
// }

// ._bg2 {
//   background: #dea026;
// }

// ._bg3 {
//   background: #de4545;
// }

.status-item {
  display: inline-block;
  position: relative;
  font-size: 15px;
  color: #000;


}

.status-item::before {
  position: absolute;
  left: -15px;
  top: 6px;
  content: '';
  width: 10px;
  height: 10px;
}

._bg0::before {
  background: #3bd11d;
}

._bg1::before {
  background: #0d8ade;
}

._bg2::before {
  background: #dea026;
}

._bg3::before {
  background: #de4545;
}

._bg4::before {
  background: #848484;
}
</style>
