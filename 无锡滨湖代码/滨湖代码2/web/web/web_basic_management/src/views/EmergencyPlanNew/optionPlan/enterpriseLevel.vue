<template>
  <section class="enterpriseLevel">
    <el-row class="header">
      <el-col :span="44">
        <el-form :inline="true" :model="searchForm" ref="form">
          <el-form-item label="所在辖区">
            <el-select
              v-model="searchForm.regionId"
              placeholder="请选择所在辖区"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in areaList"
                :value="item.regionId"
                :label="item.regionName"
                :key="item.regionId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="企业名称">
            <el-input v-model="searchForm.companyName" placeholder="请输入企业名称"></el-input>
          </el-form-item>
          <el-form-item label="企业类型">
            <el-cascader
              :options="enterpriseTypeList"
              :props="optionProps"
              v-model="searchForm.companyType"
              clearable
              placeholder="企业类型"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="预案名称">
            <el-input v-model="searchForm.planName" placeholder="请输入预案名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <div style="height: 655px; overflow: scroll">
      <el-table
        v-loading="loading"
        class="tableInfo"
        border
        :data="tableData"
        style="width: 100%"
        @expand-change="expandChange"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-table
              border
              v-loading="loadingInner"
              :data="props.row.childList"
              style="width: 100%"
            >
              <el-table-column prop="planType" label="预案类型" width="200"></el-table-column>
              <el-table-column prop="date" label="处置卡信息" width="1100">
                <template slot-scope="scope">
                  <!-- <el-table-column type="index" label="序号" width="50"></el-table-column> -->
                  <el-tag
                    :key="tag.planId"
                    v-for="(tag, t_index) in scope.row.dealData"
                    style="cursor: pointer; margin-right: 10px"
                    :disable-transitions="false"
                    @click="openTag(tag, scope.$index, t_index)"
                  >{{ tag.name }}</el-tag>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="regionName" label="所在辖区"></el-table-column>
        <el-table-column prop="companyName" label="企业名称"></el-table-column>
        <el-table-column prop="companyTypeName" label="企业类型"></el-table-column>
        <el-table-column prop="allCount" label="综合预案"></el-table-column>
        <el-table-column prop="specialCount" label="专项预案"></el-table-column>
        <el-table-column prop="handleCount" label="现场处置预案"></el-table-column>
      </el-table>
    </div>
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-size="pageSize"
      :page-sizes="[50, 100, 500, 1000]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
    <!-- 综合预案弹窗 -->
    <div class="dialogContent" v-if="addDialogVisible">
      <el-dialog
        :visible.sync="addDialogVisible"
        :append-to-body="false"
        :modal-append-to-body="false"
        title="综合处置卡"
        class="addContent"
        top="10vh"
        :before-close="closeDialog1"
      >
        <el-form ref="form1" :rules="rules1" :model="formData1" label-width="120px">
          <el-row :gutter="12">
            <el-col :span="12">
              <el-form-item label="综合处置卡标题" prop="title">
                <el-input v-model="formData1.title"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人/电话" prop="mainManager">
                <el-input v-model="formData1.mainManager"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="紧急联系人/电话">
            <el-input v-model="formData1.emergencyContact"></el-input>
          </el-form-item>

          <el-form-item label="处理原则">
            <el-input
              type="textarea"
              :rows="6"
              placeholder="请输入处理原则"
              v-model="formData1.disposalPrinciple"
            ></el-input>
          </el-form-item>

          <el-form-item label="风险分析">
            <el-input
              type="textarea"
              :rows="6"
              placeholder="请输入风险分析"
              v-model="formData1.riskAnalysis"
            ></el-input>
          </el-form-item>

          <el-form-item label="注意事项">
            <el-input
              type="textarea"
              :rows="6"
              placeholder="请输入注意事项"
              v-model="formData1.cautionDesc"
            ></el-input>
          </el-form-item>
          <el-form-item label="附件">
            <el-upload
              style="width: 100%"
              ref="upload"
              action="/"
              :multiple="false"
              :file-list="filePlanList"
              :show-file-list="true"
              :on-preview="handlePreview"
              :auto-upload="false"
              accept=".docx, .pdf, .PDF, .xlsx, .jpg, .PNG"
            >
              <!-- <el-button slot="trigger" size="small" type="primary">上传文档</el-button> -->
              <!-- <div slot="tip" class="el-upload__tip">只能上传docx/pdf/xlsx/jpg/PNG文件，且不超过50MB</div> -->
            </el-upload>
          </el-form-item>
        </el-form>

        <el-divider content-position="left">危险单元</el-divider>

        <div class="bottomContent" style="margin-bottom: 90px">
          <div
            class="bottomItem"
            style="margin-top: 10px; margin-left: 120px"
            v-for="(item, index) in bottomData"
            :key="index"
          >
            <el-row :gutter="20">
              <el-col :span="6">
                <el-input type="textarea" :rows="4" placeholder="名称" v-model="item.name"></el-input>
              </el-col>

              <el-col :span="6">
                <el-input type="textarea" :rows="4" placeholder="涉及物料及储量" v-model="item.stuff"></el-input>
              </el-col>

              <el-col :span="6">
                <el-input
                  type="textarea"
                  :rows="4"
                  placeholder="危险特性"
                  v-model="item.characteristic"
                ></el-input>
              </el-col>

              <el-col :span="4">
                <el-button @click="delItem(0, index)" v-if="isCode !== 1">删除</el-button>
              </el-col>
            </el-row>
          </div>
          <el-button
            type="success"
            round
            plain
            icon="el-icon-circle-plus"
            @click="addBottomItem(0)"
            v-if="isCode !== 1"
            style="margin-top: 10px; margin-bottom: 40px; margin-left: 120px"
          >添加危险单元</el-button>
        </div>
        <div style="position: absolute; right: 18px; bottom: 15px">
          <el-button icon="el-icon-circle-close" @click="closeDialog1(1)">取消</el-button>
          <el-button
            type="primary"
            icon="el-icon-s-promotion"
            @click="submitDialog(0)"
            v-if="isCode !== 1"
          >提交</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 专项预案弹窗 -->
    <div class="dialogContent" v-if="addDialogVisible2">
      <el-dialog
        :visible.sync="addDialogVisible2"
        :append-to-body="false"
        :modal-append-to-body="false"
        title="专项处置卡"
        class="addContent"
        top="2vh"
        :before-close="closeDialog2"
      >
        <el-form ref="form2" :rules="rules2" :model="formData2" label-width="120px">
          <el-divider content-position="left">事故风险分析</el-divider>

          <el-row :gutter="0">
            <el-col :span="11">
              <el-form-item label="标题" prop="title">
                <el-input placeholder="请输入标题" v-model="formData2.title"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="事故类型" prop="imgType">
                <el-select v-model="formData2.accidentType" placeholder="请选择事故类型" clearable>
                  <el-option
                    v-for="(item, index) in typeList"
                    :key="index"
                    :label="item.accidentTypeName"
                    :value="item.accidentType"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="危险程度分析">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入危险程度分析"
                  v-model="formData2.riskLevel"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="发生区域">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入发生区域"
                  v-model="formData2.occurrenceArea"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="影响范围">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入影响范围"
                  v-model="formData2.scopeInfluence"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="事故征兆">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入事故征兆"
                  v-model="formData2.accidentSign"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="次生衍生事故">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入次生衍生事故"
                  v-model="formData2.secondaryDerivativeAccident"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="发生季节">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入发生季节"
                  v-model="formData2.occurrenceSeason"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-divider content-position="left">应急小组的职责</el-divider>

          <el-form-item label="应急小组名称">
            <el-input placeholder="请输入应急小组名称" v-model="formData2.emergencyTeam"></el-input>
          </el-form-item>

          <el-form-item label="应急小组职责">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入应急小组职责"
              v-model="formData2.emergencyTeamDuty"
            ></el-input>
          </el-form-item>

          <el-form-item label="注意事项">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入注意事项"
              v-model="formData2.cautionDesc"
            ></el-input>
          </el-form-item>
        </el-form>

        <el-divider content-position="left">处置措施</el-divider>

        <div class="bottomContent" style="margin-bottom: 90px">
          <div
            class="bottomItem"
            style="margin-top: 10px; margin-left: 120px"
            v-for="(item, index) in bottomData2"
            :key="index"
          >
            <el-row :gutter="20">
              <el-col :span="6">
                <el-input type="textarea" :rows="3" placeholder="步骤" v-model="item.step"></el-input>
              </el-col>

              <el-col :span="6">
                <el-input type="textarea" :rows="3" placeholder="处置措施" v-model="item.measures"></el-input>
              </el-col>

              <el-col :span="6">
                <el-input type="textarea" :rows="3" placeholder="负责人" v-model="item.head"></el-input>
              </el-col>

              <el-col :span="4">
                <el-button @click="delItem(1, index)" v-if="isCode !== 1">删除</el-button>
              </el-col>
            </el-row>
          </div>
          <el-button
            type="success"
            round
            plain
            icon="el-icon-circle-plus"
            @click="addBottomItem(1)"
            v-if="isCode !== 1"
            style="margin-top: 10px; margin-bottom: 40px; margin-left: 120px"
          >处置措施</el-button>
        </div>

        <div style="position: absolute; right: 18px; bottom: 15px">
          <el-button icon="el-icon-circle-close" @click="closeDialog2(1)">取消</el-button>
          <el-button
            type="primary"
            icon="el-icon-s-promotion"
            @click="submitDialog(1)"
            v-if="isCode !== 1"
          >提交</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 现场处置预案弹窗 -->
    <div class="dialogContent" v-if="addDialogVisible3">
      <el-dialog
        :visible.sync="addDialogVisible3"
        :append-to-body="false"
        :modal-append-to-body="false"
        title="现场处置卡"
        class="addContent"
        top="2vh"
        :before-close="closeDialog3"
      >
        <el-form ref="form3" :rules="rules3" :model="formData3" label-width="120px">
          <el-divider content-position="left">事故风险分析</el-divider>

          <el-row :gutter="0">
            <el-col :span="11">
              <el-form-item label="标题" prop="title">
                <el-input placeholder="请输入标题" v-model="formData3.title"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="事故类型" prop="imgType">
                <el-select v-model="formData3.accidentType" placeholder="请选择事故类型" clearable>
                  <el-option
                    v-for="(item, index) in typeList"
                    :key="index"
                    :label="item.accidentTypeName"
                    :value="item.accidentType"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="危险程度分析">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入危险程度分析"
                  v-model="formData3.riskLevel"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="发生区域">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入发生区域"
                  v-model="formData3.occurrenceArea"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="影响范围">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入影响范围"
                  v-model="formData3.scopeInfluence"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="事故征兆">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入事故征兆"
                  v-model="formData3.accidentSign"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="11">
              <el-form-item label="次生衍生事故">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入次生衍生事故"
                  v-model="formData3.secondaryDerivativeAccident"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="2">
              <el-form-item label="发生季节">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入发生季节"
                  v-model="formData3.occurrenceSeason"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-divider content-position="left">应急小组的职责</el-divider>

          <el-form-item label="应急小组名称">
            <el-input placeholder="请输入应急小组名称" v-model="formData3.emergencyTeam"></el-input>
          </el-form-item>

          <el-form-item label="应急小组职责">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入应急小组职责"
              v-model="formData3.emergencyTeamDuty"
            ></el-input>
          </el-form-item>

          <el-form-item label="注意事项">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入注意事项"
              v-model="formData3.cautionDesc"
            ></el-input>
          </el-form-item>
        </el-form>

        <el-divider content-position="left">处置措施</el-divider>

        <div class="bottomContent" style="margin-bottom: 90px">
          <div
            class="bottomItem"
            style="margin-top: 10px; margin-left: 120px"
            v-for="(item, index) in bottomData3"
            :key="index"
          >
            <el-row :gutter="20">
              <el-col :span="6">
                <el-input type="textarea" :rows="3" placeholder="步骤" v-model="item.step"></el-input>
              </el-col>

              <el-col :span="6">
                <el-input type="textarea" :rows="3" placeholder="处置措施" v-model="item.measures"></el-input>
              </el-col>

              <el-col :span="6">
                <el-input type="textarea" :rows="3" placeholder="负责人" v-model="item.head"></el-input>
              </el-col>

              <el-col :span="4">
                <el-button @click="delItem(2, index)" v-if="isCode !== 1">删除</el-button>
              </el-col>
            </el-row>
          </div>
          <el-button
            type="success"
            round
            plain
            icon="el-icon-circle-plus"
            @click="addBottomItem(2)"
            v-if="isCode !== 1"
            style="margin-top: 10px; margin-bottom: 40px; margin-left: 120px"
          >处置措施</el-button>
        </div>
        <div style="position: absolute; right: 18px; bottom: 15px">
          <el-button icon="el-icon-circle-close" @click="closeDialog3(1)">取消</el-button>
          <el-button
            type="primary"
            icon="el-icon-s-promotion"
            @click="submitDialog(2)"
            v-if="isCode !== 1"
          >提交</el-button>
        </div>
      </el-dialog>
    </div>
  </section>
</template>
<script>
import Axios from '@/utils/request'
// import znvTime from '../../../utils/znvTime'
export default {
  components: {},
  name: 'enterpriseLevel',
  data() {
    return {
      loading: false,
      loadingInner: false,
      tableData: [],
      page: 1,
      pageSize: 10,
      total: 0,
      searchForm: {},
      areaList: [],
      enterpriseTypeList: [],
      optionProps: {
        value: 'companyType',
        label: 'companyTypeName',
        children: 'children',
        checkStrictly: true
      },
      isCode: 1,
      typeList: [],
      bottomData: [],
      bottomData2: [],
      bottomData3: [],
      addDialogVisible: false,
      addDialogVisible2: false,
      addDialogVisible3: false,
      fileList: [
        [
          {
            name: '123.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          },
          {
            name: '234.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          }
        ]
      ],
      rules1: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        mainManager: [{ required: true, message: '请输入负责人/电话', trigger: 'blur' }]
      },
      rules2: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
      },
      rules3: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
      },
      formData1: {
        belongCompanyId: '', // 企业id
        title: '', // 标题
        mainManager: '', // 主要负责人/电话
        emergencyContact: '', // 企业紧急联系人/电话
        disposalPrinciple: '', // 处置原则
        riskAnalysis: '', // 风险分析
        cautionDesc: '', // 注意事项
        hazardousUnit: '', // 危险单元
        createId: '',
        createName: '',
        dataFile: '' // 预案文件
      },
      formData2: {
        belongCompanyId: '', // 企业id
        title: '', // 标题
        accidentType: '', // 事故类型
        riskLevel: '', // 危险程度
        occurrenceArea: '', // 发生区域
        scopeInfluence: '', // 影响范围
        accidentSign: '', // 事故征兆
        secondaryDerivativeAccident: '', // 次生衍生事故
        occurrenceSeason: '', // 发生季节
        emergencyTeam: '', // 应急小组
        emergencyTeamDuty: '', // 应急小组职责
        cautionDesc: '', // 注意事项
        disposalMeasures: '' // 处置措施
      },
      formData3: {
        belongCompanyId: '', // 企业id
        title: '', // 标题
        accidentType: '', // 事故类型
        riskLevel: '', // 危险程度
        occurrenceArea: '', // 发生区域
        scopeInfluence: '', // 影响范围
        accidentSign: '', // 事故征兆
        secondaryDerivativeAccident: '', // 次生衍生事故
        occurrenceSeason: '', // 发生季节
        emergencyTeam: '', // 应急小组
        emergencyTeamDuty: '', // 应急小组职责
        cautionDesc: '', // 注意事项
        disposalMeasures: '' // 处置措施
      },
      filePlanList: []
    }
  },
  mounted() {
    this.getTypeList()
    this.getAreaList()
    this.getEnterpriseType()
  },
  methods: {
    // 获取企业类型
    getEnterpriseType() {
      let url = '/binhuapis/companyDict/queryCompanyType'
      Axios.get(url).then((r) => {
        if (r.data.code === 200) {
          this.enterpriseTypeList = r.data.data
        }
      })
    },
    // 获取所在辖区
    getAreaList() {
      //?userLevel=${localStorage.level}&userId=${localStorage.userId}
      let url = `/binhuapis/companyDict/queryRegion`
      Axios.get(url).then(res => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    // 点击文件下载
    handlePreview(file) {
      // console.log('file', file)
      let url = file.url
      window.open(url, '_bank')
    },
    // 获取事故类型
    getTypeList() {
      let url = '/binhuapis/superviseCompany/queryAccidentDick'
      Axios.get(url).then((r) => {
        this.typeList = r.data.data.splice(1).map((item) => {
          item.accidentType = String(item.accidentType)
          return item
        })
      })
    },
    closeDialog1(v) {
      this.addDialogVisible = false
    },

    closeDialog2(v) {
      this.addDialogVisible2 = false
    },

    closeDialog3(v) {
      this.addDialogVisible3 = false
    },
    openTag(tag, index, tindex) {
      console.log(tag)
      this.editing = tindex
      let url = ''
      if (index === 0) {
        url = '/binhuapis/getCompanyPlanInfoNew?id=' + tag.planId + '&type=' + tag.type
        Axios.get(url).then((res) => {
          this.filePlanList = []
          this.formData1 = JSON.parse(JSON.stringify(res.data.data))
          if (this.formData1.hazardousUnit) {
            this.bottomData = JSON.parse(this.formData1.hazardousUnit)
          }
          if (this.formData1.dataFile) {
            let obj1 = JSON.parse(JSON.stringify(this.formData1.dataFile))
            if (obj1.indexOf('[') !== -1) {
              let arr = JSON.parse(obj1)
              this.filePlanList = arr.map((v) => {
                return {
                  url: v.fileUrl,
                  name: v.fileName
                }
              })
            }
          }
          this.addDialogVisible = true
        })
      } else if (index === 1) {
        url = '/binhuapis/getCompanyPlanInfoNew?id=' + tag.planId + '&type=' + tag.type
        Axios.get(url).then((res) => {
          this.formData2 = JSON.parse(JSON.stringify(res.data.data))
          if (this.formData2.disposalMeasures) {
            this.bottomData2 = JSON.parse(this.formData2.disposalMeasures)
          }
          this.addDialogVisible2 = true
        })
      } else if (index === 2) {
        url = '/binhuapis/getCompanyPlanInfoNew?id=' + tag.planId + '&type=' + tag.type
        Axios.get(url).then((res) => {
          this.formData3 = JSON.parse(JSON.stringify(res.data.data))
          if (this.formData3.disposalMeasures) {
            this.bottomData3 = JSON.parse(this.formData3.disposalMeasures)
          }
          this.addDialogVisible3 = true
        })
      }
    },
    expandChange(row, expandRows) {
      console.log(row, expandRows)
      let dataList = [
        {
          planType: '综合预案',
          dealData: []
        },
        {
          planType: '专项预案',
          dealData: []
        },
        {
          planType: '现场处置预案',
          dealData: []
        }
      ]
      this.loadingInner = true
      let url = '/binhuapis/getCompanyPlan?companyId=' + row.companyId

      Axios.get(url).then((res) => {
        res.data.data.forEach(e => {
          dataList.forEach(item => {
            if (item.planType === e.type) {
              item.dealData.push(e)
            }
          })
        })
        console.log(dataList);
        row.childList = dataList
        this.loadingInner = false
      })
    },
    queryTableList() {
      this.loading = true
      let url = '/binhuapis/queryCompanyPlanNew'
      let params = {
        status: ['2'],
        pageNum: this.page,
        pageSize: this.pageSize
        // userId: localStorage.getItem('userId')
      }
      if (this.searchForm.regionId) {
        params.regionId = this.searchForm.regionId
      }
      if (this.searchForm.companyName) {
        params.companyName = this.searchForm.companyName
      }
      if (this.searchForm.companyType) {
        params.companyType = this.searchForm.companyType.join(',')
      }
      if (this.searchForm.planName) {
        params.planName = this.searchForm.planName
      }
      Axios.post(url, params).then((res) => {
        this.tableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    // 查询
    onSubmit() {
      // console.log("类型--searchForm", this.searchForm);
      this.page = 1
      this.queryTableList()
    },
    // 分页管理
    handleCurrentChange(val) {
      // console.log('当前页', val)
      this.page = val
      this.queryTableList()
    },
    handleSizeChange(val) {
      // console.log('一页展示', val, '条数据')
      this.pageSize = val
      this.queryTableList()
    },
    // 详情
    handleView(row) {
      // console.log('row: ', row)
      this.showView = true
      this.planId = row.id
    },
    // 反馈
    handleFeedback(row) {
      // console.log('row: ', row)
      this.id = row.id
      this.showRemark = true
    }
  }
}
</script>
<style scoped>
</style>
