<template>
  <!-- 个人中心 -->
  <div class="">
    <!-- 顶部大图 -->
    <!-- <div class="home-banner">
        <div class="search-wrap">
            <div class="search-title">个人中心</div>
        </div>
    </div> -->


    <div class="box">
      <div class="box-block _bg">
        <div class="box-block-tit">个人中心</div>
      </div>
    </div>
    <div class="box _flex" style="padding-top: 0px;">
      <div class="box-block _border">
        <img :src="userInfo.avatar" alt="" class="user-image">
        <div class="user-box">
          <div class="user-box-item">欢迎您，{{ oUserInfoV1.userName }}!</div>
          <div class="user-box-time">上次登录时间:{{ oUserInfoV1.lastLoginTime }}</div>
        </div>
      </div>
    </div>

    <!-- 基本信息 -->
    <div class="flex-row box">
      <div class=" _flex ">
        <com-block-b title="基本信息" class="box-block ">
          <div class="userinfo-wrap">
            <div class="userinfo-wrap-item"><span style="color: #000;">用户名：</span>{{ oUserInfoV1.userName }}
            </div>
            <div class="userinfo-wrap-item"><span style="color: #000;">手机号码：</span>{{ oUserInfo.phone || '--' }}
            </div>
            <div class="userinfo-wrap-item"><span style="color: #000;">单位：</span>{{ oUserInfoV1.deptName }}
            </div>
            <div class="userinfo-wrap-item"><span style="color: #000;">职位：</span>{{ oUserInfoV1.position }}
            </div>
            <div class="userinfo-wrap-item"><span style="color: #000;">性别：</span>{{ fSetSex(oUserInfo.sex) }}
            </div>
          </div>
        </com-block-b>
      </div>
      <div class=" _flex ">
        <com-block-b title="通知公告" :num="nMessageTotal" class="box-block ">
          <div class="userinfo-wrap _over">
            <div class="message-item" v-for="(item, idx) in aMessageData" :key="'aMessageData' + idx"
                 @click="fOpenMessageLayer(item)">
              <div class="message-item-top " :class="[item.read == '1' ? '_read' : '']">{{ item.nickName }} {{
                  item.noticeTitle
                }}
              </div>
              <div class="message-item-text">
                <span>{{ item.noticeContent }}</span>
                <span>{{ item.updateTime }}</span>
              </div>

            </div>
          </div>
        </com-block-b>
      </div>
    </div>


    <!-- 常用应用  -  通知公告 -->
    <div class="box _flex " v-if="false">
      <div class="box-block _flex">
        <com-block-b title="常用应用" bgtype="bg1" class="box-block-item ">
          <div class="block-item">
            <template v-if="aUserAppData.length > 0">
              <div class="item-box-app " v-for="(item, idx) in aUserAppData.slice(0, 4)"
                   :key="'aUserAppData' + idx" @click="fClickGoToDetails(item)">
                <img v-if="item.icon" :src="item.icon" alt="" class="box-app-image"/>
                <div v-else class="box-app-image _bg"></div>
                <div class="text-ellipsis-1" :title="item.name">{{ item.name }}</div>
              </div>
            </template>
            <template v-else>
              <el-empty description="暂无数据"></el-empty>
            </template>
          </div>

        </com-block-b>
        <com-block-b title="信息通知" bgtype="bg1" class="box-block-item ">
          <div class="block-item">
            <vue-seamless-scroll :data="aMsgData" class="box-scroll-wrap " :class-option="defaultOption"
                                 v-if="aMsgData.length >= 1">
              <div class="box-scroll-item  " v-for="(item, idx) in aMsgData" :key="'aMsgData' + idx">
                <div class="scroll-item-label text-ellipsis-1" :title="item.applicationName || item.name">
                  <!-- 时间 -->
                  {{ item.approveTime || item.applicationTime || item.updateTime }}&nbsp;
                  <!-- 类型 -->
                  {{ item.type || fSetShzt(item.reviewStatus) }}
                  <span v-if="item.type !== '待审核'">了</span>
                  <!-- 名字 -->
                  {{ item.applicationName || item.name || item.appName }}
                  <span v-if="item.type !== '上架' || item.type !== '驳回'">请耐心等待审核结果</span>
                </div>
              </div>
            </vue-seamless-scroll>
            <template v-else>
              <el-empty description="暂无数据"></el-empty>
            </template>
          </div>
        </com-block-b>
      </div>
    </div>


    <!-- 我的应用 -->
    <div class="box _flex ">
      <com-block-b title="我的应用" class="box-block" type="input">
        <div class="search-input-wrap" slot="input">
          <el-input placeholder="请输入应用名称" v-model="sMyAppKeyWord" class="search-input">
            <el-button type="primary" slot="append" icon="el-icon-search" @click="fClickSearch()">搜索</el-button>
          </el-input>
        </div>
        <div class="myapp-block" v-if="aMyAppData.length > 0">
          <div class="myapp-block-item"
               v-for="(item, idx) in aMyAppData.slice((nCcurrent - 1) * 9, (nCcurrent) * 9)"
               :key="'aMyAppData' + idx" @click="fClickGoToDetails(item)">
            <img v-if="item.icon" :src="item.icon" alt="" class="block-item-image"/>
            <div v-else class="block-item-image _bg"></div>
            <div class="block-item-box">
              <div class="item-box-title text-ellipsis-1" :title="item.name">{{ item.name }}</div>
              <!-- <div class="item-box-detail text-ellipsis-2" :title="item.description">{{ item.description }}
              </div>
              <div class="item-box">
                  <div class="item-box-unit">所属部门：{{ item.manageDeptName }}</div>
                  <div class="usercenter-more" @click.stop="fClickGoToDetails(item)">查看详情 ></div>
              </div> -->
            </div>
          </div>
        </div>
        <div class="myapp-block" v-else>
          <el-empty description="暂无数据"></el-empty>
        </div>
        <div class="myapp-block _flexe">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                         :current-page="nCcurrent" :page-size="nNowPage" layout=" prev, pager, next, jumper"
                         :total="aTotal">
          </el-pagination>
        </div>

      </com-block-b>
    </div>

    <!-- 我的申请 -->
    <div class="box _flex ">
      <com-block-b title="我的申请" class="box-block _bgcolor">
        <div class="box-block-tab">
          <div class="block-tab" :class="sTabType === 'qxsq' ? '_act' : ''" @click="fChangeAppTab('qxsq')">申请试用
          </div>
          <div class="block-tab" :class="sTabType === 'yysq' ? '_act' : ''" @click="fChangeAppTab('yysq')">入驻申请
          </div>
          <div class="block-tab" :class="sTabType === 'nlsq' ? '_act' : ''" @click="fChangeAppTab('nlsq')">能力申请
          </div>
        </div>
        <div class="block-apply">
          <el-table :data="aSqData" stripe style="width: 100%" v-if="sTabType === 'yysq'">
            <el-table-column align="center" prop="appSerialNo" width="70px" label="编号"></el-table-column>
            <el-table-column align="center" prop="appName" width="200px" label="名称"></el-table-column>
            <!-- <el-table-column align="center" label="提供方" width="70px">都市圈</el-table-column> -->
            <el-table-column align="center" prop="createTime" label="创建时间"></el-table-column>
            <el-table-column align="center" prop="updateTime" label="处理时间"></el-table-column>
            <el-table-column align="center" prop="applicationReason" label="申请理由"></el-table-column>
            <el-table-column align="center" prop="status" width="150px" label="审核状态">
              <template slot-scope="scope">
                                <span :class="['item-status', '_bg' + scope.row.status]">
                                    {{ fSetShztSq(scope.row.status) }}
                                </span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="params.examine.remark" label="审核意见"></el-table-column>
            <el-table-column align="center" width="100px" label="操作">
              <template slot-scope="scope">
                <!--                                <el-button @click="fOpenDetailsLayer(scope.row)">详情</el-button>-->
                <el-button size="mini" @click="fChangeShangJiaStatus(scope.row)"
                           v-if="scope.row.status == 1">撤回
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-table :data="aSqData" stripe style="width: 100%" v-if="sTabType === 'qxsq'">
            <el-table-column align="center" type="index" width="70px" label="编号"></el-table-column>
            <el-table-column align="center" prop="applicationName" width="200px" label="名称"></el-table-column>
            <!-- <el-table-column align="center" label="提供方" width="70px">都市圈</el-table-column> -->
            <el-table-column align="center" prop="applicationTime" label="最近申请时间"></el-table-column>
            <el-table-column align="center" prop="approveTime" label="处理时间"></el-table-column>
            <el-table-column align="center" prop="reason" label="申请理由"></el-table-column>
            <el-table-column align="center" prop="reviewStatus" width="150px" label="审核状态">
              <template slot-scope="scope">
                                <span :class="['item-status', '_bg' + scope.row.reviewStatus]">{{
                                    fSetShzt(scope.row.reviewStatus)
                                  }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="reviewOpinion" label="审核意见"></el-table-column>
            <el-table-column align="center" width="100px" label="操作">
              <template slot-scope="scope">
                <el-button size="mini" plain @click="withdrawAuthorityApplyFor(scope.row)"
                           v-if="scope.row.reviewStatus === 0">撤回
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-table :data="aSqData" stripe style="width: 100%" v-if="sTabType === 'nlsq'">
            <el-table-column align="center" type="index" width="70px" label="编号"></el-table-column>
            <el-table-column align="center" prop="principalName" width="200px" label="联系人"></el-table-column>
            <el-table-column align="center" prop="createTime" label="创建时间"></el-table-column>
            <!-- <el-table-column align="center" label="提供方" width="70px">都市圈</el-table-column> -->
            <el-table-column align="center" prop="principalPhone" label="号码"></el-table-column>
            <el-table-column align="center" prop="danwei" label="单位类型">
              <template slot-scope="scope">
                <span> {{ fSetSsbm(scope.row.danwei) }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="danweiType" label="申请单位">
              <template slot-scope="scope">
                <span> {{ fSetShztNengli(scope.row.danweiType) }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="params.examine.createTime" label="审核时间"></el-table-column>
            <el-table-column align="center" prop="applicationReason" label="申请理由"></el-table-column>
            <el-table-column align="center" prop="status" width="160px" label="审核状态">
              <template slot-scope="scope">
                                <span :class="['item-status', '_bg' + scope.row.status]">
                                    {{ fSetZhuangtaiNengli(scope.row.status) }}
                                </span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="params.examine.reason" label="审核意见"></el-table-column>
            <el-table-column align="center" width="100px" label="操作">
              <template slot-scope="scope">
                <el-button size="mini" @click="fChangeStatus(scope.row)"
                           v-if="scope.row.status == 1">撤回
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="myapp-block _flexe">
          <el-pagination @size-change="handleSizeChange" @current-change="fSqhandleCurrentChange"
                         :current-page="nSqCcurrent" :page-size="nSqNowPage" layout=" prev, pager, next, jumper"
                         :total="aSqTotal">
          </el-pagination>
        </div>
      </com-block-b>
    </div>

    <el-dialog title="申请详情" :visible.sync="bDetailLayer" width="70%">
      <el-form ref="appApplyForm" :model="oNowDetails" label-width="100px" disabled label-position="left">
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用名称" prop="appName">
              <el-input v-model="oNowDetails.appName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sortBy">
              <el-input type="number" v-model="oNowDetails.sortBy"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="应用类别" prop="appType">
              <!-- <el-input v-model="oNowDetails.appType"></el-input> -->
              <el-select v-model="oNowDetails.appType" placeholder="请选择应用类别">
                <el-option v-for="item in aAppSort" :key="item.id" :label="item.typeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属模块" prop="appModule">
              <!-- <el-input v-model="oNowDetails.appModule"></el-input> -->
              <el-select v-model="oNowDetails.appModule" placeholder="请选择应用模块" width="100%">
                <el-option v-for="item in app_module" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictValue">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否推荐" prop="recommendFlag">
              <el-select v-model="oNowDetails.recommendFlag" placeholder="请选择是否推荐">
                <el-option v-for="item in aIsTj" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
              <!-- <el-radio v-model="oNowDetails.recommendFlag" label="1">是</el-radio>
              <el-radio v-model="oNowDetails.recommendFlag" label="2">否</el-radio> -->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="应用链接" prop="appLink">
              <el-input v-model="oNowDetails.appLink"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="应用介绍" prop="remark">
              <el-input type="textarea" :rows="3" placeholder="请输入内容" v-model="oNowDetails.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="申请理由" prop="applicationReason">
              <el-input type="textarea" :rows="3" placeholder="请输入内容"
                        v-model="oNowDetails.applicationReason"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="应用图标:" v-if="oNowDetails.appPic">
          <el-image style="width: 100px; height: 100px" :src="oNowDetails.appPic"
                    :preview-src-list="[oNowDetails.appPic]">
          </el-image>
        </el-form-item>

        <el-form-item label="应用截图:" v-if="oNowDetails.appScreenshot">
          <el-image v-for="(img, idx) in JSON.parse(oNowDetails.appScreenshot)"
                    style="width: 100px; height: 100px" :src="img"
                    :preview-src-list="JSON.parse(oNowDetails.appScreenshot)">
          </el-image>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="bDetailLayer = false">关 闭</el-button>
            </span>
    </el-dialog>

    <el-dialog title="公告详情" :visible.sync="bMessageLayer" width="40%" :before-close="handleDialogClose">
      <div class="message-item">
        <div class="message-item-top " :class="[oMessageDate.read == '1' ? '_read' : '']">{{
            oMessageDate.nickName
          }} {{
            oMessageDate.noticeTitle
          }}
        </div>
        <div class="message-item-text">
          <span>{{ oMessageDate.noticeContent }}</span>
          <span>{{ oMessageDate.updateTime }}</span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
                <!-- <el-button plain @click="bMessageLayer = false">取 消</el-button> -->
                <el-button type="primary" @click="handleDialogClose">确 定</el-button>
            </span>
    </el-dialog>


  </div>
</template>

<script>

import comBlockB from '@/components/page/comBlockB.vue'; // 块容器-边框
import comBlockT from '@/components/page/comBlockT.vue'; // 块容器-标题
import vueSeamlessScroll from 'vue-seamless-scroll'
import {URLS_USERINFO, URLS_APPAPPLY} from '@/api/url.js'
import fGetAppListAsync from '@/api/setAppList.js'


export default {
  name: '',
  components: {
    vueSeamlessScroll,// 滚动列表
    comBlockB,// 块容器-边框
    comBlockT,// 块容器-标题
  },
  props: [''],
  data() {
    return {
      sMyAppKeyWord: '',// 关键字
      oUserInfoV1: {},// 个人中心
      aUserAppData: [],// 常用应用
      aUserAppDataCopy: [],
      aMyAppData: [],
      nCcurrent: 1,// 当前页
      nNowPage: 9,// 每页数量
      aTotal: 0,
      aMsgData: [],// 短信通知
      aUseApp: [],
      sTabType: 'qxsq',
      tableData: [],
      aSqData: [],// 申请的显示数据
      nSqCcurrent: 1,// 当前页
      nSqNowPage: 10,// 每页数量
      aSqTotal: 0,
      bDetailLayer: false,
      oNowDetails: {},
      dialogVisible: false,
      aYylb: [], // 应用类别
      aSsbm: [], // 所属部门
      app_module: [],
      aAppSort: [],
      // 是否推荐
      sIsTj: '',
      aIsTj: [
        {value: 0, label: '是'},
        {value: 1, label: '否'},
      ],
      aAllData: [],
      userInfo: {},
      aUnitType: [],
      aAllDept: [],
      aMessageData: [],
      oMessageDate: {},
      nMessageTotal: "",
      bMessageLayer: false,
    }
  },
  computed: {
    oUserInfo() {
      if (this.$store.state.oUserInfo.account) {
        return this.$store.state.oUserInfo;
      } else {
        return JSON.parse(localStorage.getItem('userInfo'));
      }
    },
    defaultOption() {
      return {
        step: 0.3, // 数值越大速度滚动越快
        limitMoveNum: this.aMsgData.length, // 开始无缝滚动的数据量 this.dataList.length
        autoPlay: this.aMsgData.length > 4 ? true : false,
        hoverStop: true, // 是否开启鼠标悬停stop
        direction: 1, // 0向下 1向上 2向左 3向右
        openWatch: true, // 开启数据实时监控刷新dom
        singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
        singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
        waitTime: 2000 // 单步运动停止的时间(默认值1000ms)
      }
    },
    oObject() {
      if (window.localStorage.getItem('obj')) {
        return JSON.parse(window.localStorage.getItem('obj'))
      } else {
        return {}
      }

    },
  },
  watch: {
    oObject: {
      handler(newVal) {
        // console.log(newVal)
        if (newVal.name) {
          this.tableData = [newVal]
        }
      },
      deep: true,
      immediate: true,
    }
  },
  created() {

  },
  mounted() {
    // console.log(this.oUserInfo)
    this.$on('fSetSqjlList', (res) => {
      // console.log(res, '111')
    })
    this.fRequestAllpp();
    this.fRequestMyApp();// 请求用户信息
    // this.fRequestSmsNotice();// 获取短信通知记录
    // this.fGetSqsjList();// 请求应用申请记录
    this.fRequestZd();// 请求字典
    // this.fRequestApplicationApplyForList(); // 根据申请人查询应用申请
    this.fRequestApplicationClassification();// 请求应用类别
    this.fRequestApplicationDept();// 请求所属部门
    this.fRequestAuthorityApplyForList(); // 根据申请人查询应用申请
    // aMsgData
    // this.fRequestMsgData(9999); // 请求信息通知
    this.fGetUserInfo();// 获取用户信息
    this.fGetUnitType();// 请求字典 单位类型
    this.fGetNoticeList();// 请求字典 单位类型
  },
  beforeDestroy() {

  },
  methods: {
    handleDialogClose() {
      if (this.oMessageDate.read == 0) {
        this.fGetNoticeList();
      }
      this.bMessageLayer = false;

    },
    fOpenMessageLayer(item) {
      this.oMessageDate = item;
      this.bMessageLayer = true;
      this.fReadMeaage()
    },
    fReadMeaage() {
      // http://127.0.0.1:11512/system/notice/12
      this.$fRequest({ // 已读
        // method: 'post',
        url: this.$ip.server_ocn + 'system/notice/' + this.oMessageDate.noticeId,
      }).then(res => {

      })
    },
    fGetNoticeList() {
      this.aMessageData = [];
      let request1 = this.$fRequest({ // 已读
        method: 'post',
        url: this.$ip.server_ocn + 'system/notice/list?pageNum=1&pageSize=999999',
        data: {
          read: '0'
        },
      })
      let request2 = this.$fRequest({ // 未读
        method: 'post',
        url: this.$ip.server_ocn + 'system/notice/list?pageNum=1&pageSize=999999',
        data: {
          read: '1'
        },
      })
      Promise.all([request1, request2]).then(([res1, res2]) => {
        this.nMessageTotal = res1.total > 0 ? res1.total : "";
        this.$EventBus.$emit('setTotal', res1.total || 0)
        localStorage.setItem('messageTotal', this.nMessageTotal)
        this.aMessageData = [...res1.rows, ...res2.rows]
      })
    },

    fChangeStatusV1(row) {
      this.$confirm('您确定要撤回该条能力申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        $.ajax({
          url: 'https://2.21.138.89:11511/cmApp/save',
          method: 'post',
          headers: {
            'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            // "Content-Type": "application/json"
          },
          dataType: 'json',
          contentType: 'application/json;charset=utf-8',
          data: JSON.stringify({
            ...row,
            status: '4',
          })
        }).then(res => {
          // console.log(res)
          this.nCurrent = 1;
          if (res.code == '200') {
            // this.$emit('fSetSqjlList', this.oAppApplyForm)
            window.localStorage.setItem('obj', JSON.stringify(this.oAppApplyForm))
            this.$message({
              message: '恭喜你，撤回成功',
              type: 'success'
            });
            this.nSqCcurrent = 1;
            this.fGetSqsjList(); // 根据申请人查询应用申请
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤回'
        });
      });
    },
    fChangeStatus(row) {
      this.$confirm('您确定要撤回该条能力申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        $.ajax({
          url: 'https://2.21.138.89:11511/cmAbilitymoban/save',
          method: 'post',
          headers: {
            'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            // "Content-Type": "application/json"
          },
          dataType: 'json',
          contentType: 'application/json;charset=utf-8',
          data: JSON.stringify({
            ...row,
            status: '4',
          })
        }).then(res => {
          // console.log(res)
          this.nCurrent = 1;
          if (res.code == '200') {
            // this.$emit('fSetSqjlList', this.oAppApplyForm)
            window.localStorage.setItem('nengliobj', JSON.stringify(this.oAppApplyForm))
            this.$message({
              message: '恭喜你，撤回成功',
              type: 'success'
            });
            this.nSqCcurrent = 1;
            this.fGetNengliList(); // 根据申请人查询应用申请
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤回'
        });
      });

    },
    withdrawAuthorityApplyFor(row) {
      this.$confirm('您确定要撤回该权限申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        $.ajax({
          url: 'https://2.21.138.89:11511/api/withdrawAuthorityApplyFor',
          method: 'post',
          headers: {
            'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            // "Content-Type": "application/json"
          },
          dataType: 'json',
          contentType: 'application/json;charset=utf-8',
          data: JSON.stringify({
            id: row.id,
            appName: row.applicationName,
          })
        }).then(res => {
          // console.log(res)
          this.nCurrent = 1;
          if (res.code == '200') {

            this.$message({
              message: '恭喜你，撤回成功',
              type: 'success'
            });
            this.nSqCcurrent = 1;
            this.fRequestAuthorityApplyForList(); // 根据申请人查询应用申请
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤回'
        });
      });
    },
    fChangeShangJiaStatus(row) {
      this.$confirm('您确定要撤回该条上架申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        $.ajax({
          url: 'https://2.21.138.89:11511/cmApp/updateStatus',
          method: 'post',
          headers: {
            'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            // "Content-Type": "application/json"
          },
          dataType: 'json',
          contentType: 'application/json;charset=utf-8',
          data: JSON.stringify({
            id: row.id,
            status: 4,
          })
        }).then(res => {
          // console.log(res)
          this.nCurrent = 1;
          if (res.code == '200') {
            // this.$emit('fSetSqjlList', this.oAppApplyForm)
            window.localStorage.setItem('nengliobj', JSON.stringify(this.oAppApplyForm))
            this.$message({
              message: '恭喜你，撤回成功',
              type: 'success'
            });
            this.nSqCcurrent = 1;
            this.fGetSqsjList(); // 根据申请人查询应用申请
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤回'
        });
      });

    },
    // 请求字典 单位类型
    fGetUnitType() {
      this.$fRequest({
        url: this.$ip.server_ocn + 'system/dict/data/type/unit_type'
      }).then(res => {
        this.aUnitType = res.data || []
      })
    },
    // 获取用户信息
    fGetUserInfo() {
      this.$fRequest({
        url: URLS_USERINFO.u_getbUserInfo,
        data: {
          account: this.oUserInfo.account,
        }
      }).then(res => {
        console.log('获取用户信息=====>>>>>', res)
        this.userInfo = res.data;
      })
    },
    async fRequestAllpp() {
      const res = await fGetAppListAsync();
      this.aAllData = res
    },
    // 根据id找文字
    fSetTiele(data, id, options) {
      if (data?.filter(item => item[options.id] == id).length > 0) {
        return data?.filter(item => item[options.id] == id)[0][options.name]
      } else {
        data.forEach(item => {
          if (item.children && item.children.length > 0) {
            this.fSetTiele(item.children, id, options)
          }
        });
      }
    },
    // 应用类别下拉框切换
    fYylxSelectChange(item) {
      // if (this.oNowDetails.categoryId) {
      //     this.oNowDetails.categoryId = this.oNowDetails.categoryId[this.oNowDetails.categoryId.length - 1]
      // }
    },
    // 请求应用类别
    fRequestApplicationClassification() {
      this.$fRequest({
        url: URLS_APPAPPLY.u_addApplicationClassification
      }).then(res => {
        // console.log(res);
        this.aYylb = res.data;
      })
    },
    // 所属部门下拉框切换
    fSsbmSelectChange(item) {
      // if (this.oNowDetails.deptId) {
      //     this.oNowDetails.deptId = this.oNowDetails.deptId[this.oNowDetails.deptId.length - 1]
      // }

    },
    // 请求所属部门
    fRequestApplicationDept() {
      this.$fRequest({
        url: URLS_APPAPPLY.u_addApplicationDept
      }).then(res => {
        console.log(res, '请求所属部门');
        this.aSsbm = res.data;
        this.findNode(JSON.parse(JSON.stringify(res.data)))
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
      return aData.length > 0 ? aData[0].title : '--'
    },
    // 打开详情弹框
    fOpenDetailsLayer(data) {
      // console.log(data, data.logo.indexOf(','), data.logo.split(','));
      console.log(data)
      this.oNowDetails = data;
      this.oNowDetails.deptName = this.fSetTiele(this.aSsbm, data.deptId, {id: 'id', name: 'title'})
      this.oNowDetails.categoryName = this.fSetTiele(this.aYylb, data.categoryId, {id: 'id', name: 'title'})
      this.bDetailLayer = true;
      // console.log(this.oNowDetails)
    },
    fSetShztNengli(type) {
      return type ? this.aUnitType.filter(item => item.dictValue == type)[0]?.dictLabel || '--' : '--'
    },
    fSetZhuangtaiNengli(type) {
      if (type == 0) {
        return '上架';
      } else if (type == 1) {
        return '待审核';
      } else if (type == 2) {
        return '驳回';
      } else if (type == 3) {
        return '下架';
      } else if (type == 4) {
        return '撤销';
      } else {
        return '--';
      }
    },
    // 设置审核状态
    fSetShzt(type) {
      // console.log(type);
      if (type == 0) {
        return '待审核';
      } else if (type == 1) {
        return '通过';
      } else if (type == 2) {
        return '驳回';
      }
    },
    fSetShztSq(type) {
      if (type == 0) {
        return '上架';
      } else if (type == 1) {
        return '待审核';
      } else if (type == 2) {
        return '驳回';
      } else if (type == 3) {
        return '下架';
      } else if (type == 4) {
        return '撤销';
      } else {
        return '--';
      }
    },
    // 通过
    fClickOk(data) {
      console.log(data, 'ok')
    },
    // 拒绝
    fClickNo(data) {
      console.log(data, 'No')

    },
    // 设置行为
    fSetSex(type) {
      if (type == 0) {
        return '男';
      } else if (type == 1) {
        return '女';
      } else {
        return '未知';
      }
    },

    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.nCcurrent = val;
      // this.fRequestMyApp();// 请求我的应用
    },
    // 权限申请分页切换
    fSqhandleCurrentChange(val) {
      this.nSqCcurrent = val;
      // this.sTabType = type;
      switch (this.sTabType) {
        case 'yysq':
          this.fGetSqsjList(); // 根据申请人查询应用申请
          break;
        case 'qxsq':
          this.fRequestAuthorityApplyForList(); // 根据申请人查询应用申请
          break;
        case 'nlsq':
          this.fGetNengliList(); // 根据申请人查询应用申请
          break;
      }

    },
    fClickSearch() {
      this.aMyAppData = this.aUserAppDataCopy.filter(item => item.name.indexOf(this.sMyAppKeyWord) >= 0)
      this.aTotal = this.aMyAppData.length
      // this.fRequestMyApp();// 请求我的应用
    },
    // 去详情页面
    fClickGoToDetails(item) {
      // console.log(item)
      window.open(item.url)
      return
      let oObj = this.$fSetData(item, ['appSecret'])
      oObj = this.aAllData.filter(data => data.casAppId === item.id)[0] || null
      if (!oObj) return
      localStorage.setItem('appDeatils', JSON.stringify(oObj))

      this.$router.push({
        path: "/appMarket/appDetails",
        // query: { data: JSON.stringify(oObj) }
      })
      // this.$router.push({
      //     path: "/usercenter/appDetails",
      //     query: { ...item }
      // })
    },
    // 点击我的应用
    fClickMyApp(item) {
      return
      window.open(item.url)
    },

    // 请求信息通知
    async fRequestMsgData(len) {
      this.aMsgData = []
      let api1 = this.$fRequest({
        url: URLS_USERINFO.u_applicationApplyForList,
        data: {
          applicant: this.oUserInfo.account,
          // keyword: this.sMyAppKeyWord,
          current: 1,// 当前页
          size: len,// 每页数量
        }
      })
      let api2 = this.$fRequest({
        url: URLS_USERINFO.u_authorityApplyForList,
        data: {
          applicant: this.oUserInfo.account,
          // keyword: this.sMyAppKeyWord,
          current: 1,// 当前页
          size: len,// 每页数量
        }
      })
      let api3 = $.ajax({
        url: `https://2.21.138.89:11511/cmApp/replayList?pageNum=${1}&pageSize=${9999}`,
        method: 'post',
        headers: {
          'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          // "Content-Type": "application/json"
        },
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({params: {'own': 1}})
      })
      await Promise.all([api1, api2, api3]).then(res => {
        console.log(res)
        res[0].data.records.forEach(item => {
          this.aMsgData.push({
            ...item,
            type: '上架'
          })
        })
        res[1].data.records.forEach(item => {
          this.aMsgData.push({
            ...item,
            type: item.reviewOpinion
          })
        })

        res[2].data.records.forEach(item => {
          this.aMsgData.push({
            ...item,
            type: this.fSetShztSq(item.status)
          })
        })
      })
      // console.log(this.aMsgData.length, this.aMsgData)
      return
    },
    // 根据申请人查询应用申请
    fRequestApplicationApplyForList() {
      this.$fRequest({
        url: URLS_USERINFO.u_applicationApplyForList,
        data: {
          applicant: this.oUserInfo.account,
          // keyword: this.sMyAppKeyWord,
          current: this.nSqCcurrent,// 当前页
          size: this.nSqNowPage,// 每页数量
        }
      }).then(res => {
        // console.log(res, '根据申请人查询应用申请')
        this.aSqData = res.data.records;
        this.aSqTotal = res.data.total;

      })
    },
    // 根据申请人查询权限申请
    fRequestAuthorityApplyForList() {
      this.aSqData = []
      this.aSqTotal = 0
      this.$fRequest({
        url: URLS_USERINFO.u_authorityApplyForList,
        data: {
          applicant: this.oUserInfo.account,
          // keyword: this.sMyAppKeyWord,
          current: this.nSqCcurrent,// 当前页
          size: this.nSqNowPage,// 每页数量
        }
      }).then(res => {
        console.log(res, '根据申请人查询权限申请')
        if (res.code == 200) {
          this.aSqData = res.data.records;
          this.aSqTotal = res.data.total;
        }
      }).catch(err => {
        this.aSqData = []
        this.aSqTotal = 0
      })
    },
    // 请求当前登录用户的应用
    fRequestMyApp() {
      this.$fRequest({
        url: URLS_USERINFO.u_userApplication,
        data: {
          account: this.oUserInfo.account,
          keyword: this.sMyAppKeyWord,
          current: this.nCcurrent,// 当前页
          size: this.nNowPage,// 每页数量
        }
      }).then(res => {
        let aData = []
        res.data.forEach(item => {
          aData.push(...item.appList.records)
        })
        var obj = {}
        var newArr = aData.reduce((pre, cur) => {
          if (!obj[cur.id]) { // obj没有cur的id则放入数组
            obj[cur.id] = true
            return [...pre, cur]
          } else {
            return pre
          }
        }, [])
        this.aUserAppData = newArr.sort(this.fSetDataSort('loginNumber', false))
        this.aUserAppDataCopy = newArr.sort(this.fSetDataSort('loginNumber', false))
        this.oUserInfoV1 = res.data[0];
        // console.log(this.oUserInfoV1, '个人中心')
        this.aMyAppData = newArr;
        // this.aTotal = 19;
        this.aTotal = newArr.length;
        // console.log( this.aTotal)
        if (this.aMyAppData.length >= 5) {
          this.aUseApp = this.aMyAppData.slice(0, 4)
        } else {
          this.aUseApp = this.aMyAppData
        }
      })
    },

    // 获取短信通知记录
    fRequestSmsNotice() {
      this.$fRequest({
        url: URLS_USERINFO.u_smsNotice,
        data: {
          account: this.oUserInfo.account,
        }
      }).then(res => {
        if (res.data) {
          this.aMsgData = res.data;

        } else {
          this.aMsgData = [];
        }
      })
    },
    // 排序
    fSetDataSort(attr, rev) {
      //第二个参数没有传递 默认升序排列
      if (rev == undefined) {
        rev = 1;
      } else {
        rev = (rev) ? 1 : -1;
      }
      return function (a, b) {
        a = a[attr];
        b = b[attr];
        if (a < b) {
          return rev * -1;
        }
        if (a > b) {
          return rev * 1;
        }
        return 0;
      }
    },
    // 请求字典
    fRequestZd() {
      $.ajax({
        url: 'https://2.21.138.89:11511/system/dict/data/type/app_module',
        method: 'get',
        headers: {
          'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          // "Content-Type": "application/json"
        },
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
      }).then(res => {
        // console.log(res)
        this.app_module = res.data
      })
    },
    fGetNengliList() {
      this.aSqData = [];
      this.aSqTotal = 0;
      if (this.sTabType != 'nlsq') return
      this.$fRequest({
        method: 'post',
        url: this.$ip.server_ocn + 'cmAbility/centerAbility',
        data: {
          size: 10,
          current: this.nSqCcurrent,
        }
      }).then(res => {
        // console.log(res)
        // this.aUnitType = res.data || []
        this.aSqData = res.data.records;
        this.aSqTotal = res.data.total;
      })
    },
    // 申请上架的列表
    fGetSqsjList() {
      $.ajax({
        url: `https://2.21.138.89:11511/cmApp/replayList?pageNum=${this.nSqCcurrent}&pageSize=${this.nSqNowPage}`,
        method: 'post',
        headers: {
          'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          // "Content-Type": "application/json"
        },
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({params: {'own': 1}})
      }).then(res => {
        // console.log(res)
        this.aSqData = res.data.records;
        this.aSqTotal = res.data.total;
      })
    },
    // 申请的切换
    fChangeAppTab(type) {
      this.sTabType = type;
      this.nSqCcurrent = 1;
      this.aSqData = []
      console.log(this.sTabType)
      switch (this.sTabType) {
        case 'yysq':
          // 请求上架申请的列表
          this.fGetSqsjList(); // 根据申请人查询应用申请
          break;
        case 'qxsq':
          console.log('权限申请')
          this.fRequestAuthorityApplyForList(); // 根据申请人查询应用申请
        case 'nlsq':
          this.fGetNengliList(); // 根据申请人查询应用申请
          break;
      }
    },
    handlePictureCardPreview(file) {
      this.oNowDetails.logo = file.url;
      // console.log(file)
      this.dialogVisible = true;
    }
  }
}
</script>
<style lang='less' scoped>
.block {
  margin: 0;
}

.flex-row {
  display: flex;
  align-items: center;
  justify-content: space-between;

  ._flex {
    width: 48%;
    // padding: 0%;
  }
}

.usercenter-banner {
  width: 100%;
  height: 617px;
  padding: 109px 0 0 0;
  margin-bottom: 50px;
  // margin-top: -65px;
  background: url('../../assets/image/home/grzxbg.png') no-repeat center / 100% 617px;
}

.home-banner {
  position: relative;
  width: 100%;
  height: 617px;
  margin-bottom: 100px;
  background: url('../../assets/image/home/grzxbg.png') no-repeat center / 100% 617px;
}

.search-wrap {
  display: flex;
  align-items: center;
  // justify-content: center;
  flex-direction: column;
  padding-top: 102px;
  position: absolute;
  top: 0%;
  left: 0%;
  height: 100%;
  width: 100%;
  z-index: 999;
  font-weight: bold;
}

.search-title {
  color: #FFFFFF;
  font-size: 40px;
}

.usercenter-banner-title {
  text-align: center;
  margin-bottom: 28px;
  color: #fff;
  font-weight: 500;
  font-size: 60px;
}

.usercenter-banner-detail {
  text-align: center;
  width: 50%;
  margin: 0 auto;
  font-size: 18px;
  text-indent: 2rem;
  color: #fff;
}

.usercenter-banner-value {
  width: 620px;
}

.box-block {
  width: 100%;
  // margin-bottom: 50px;

  &._bgcolor {
    background-color: #FAFAFA;
    margin-bottom: 30px;
  }

  &._bg {
    // margin-top: -25px;
    margin-bottom: 29px;
    text-align: center;
    font-size: 36px;
    color: #1A1B1F;
    background: url('../../assets/image/comments/tit_bg2.png') no-repeat center center;
  }

  &._border {
    display: flex;
    align-items: center;
    height: 148px;
    margin: 0 20px;
    background: url('../../assets/image/userCenter/bg.png') no-repeat center / 100%;
  }

  &._flex {
    display: flex;
    justify-content: space-between;
  }

}

.user-image {
  width: 60px;
  height: 60px;
  margin: 0 50px;
  border-radius: 50%;
  overflow: hidden;
}

.user-box-item {
  margin-bottom: 20px;
  font-size: 20px;
  color: #000;
}

.user-box-time {
  color: #999;
  font-size: 16px;
}

.box-block-item {
  width: 47%;
  height: 400px;
  overflow: hidden;

}

.block-item {
  display: flex;
  align-items: center;
  justify-content: space-around;
  overflow: hidden;
  padding: 0 20px;
}

.userinfo-wrap {
  // display: flex;
  // align-items: center;
  // justify-content: space-between;
  // flex-wrap: wrap;
  width: 100%;
  padding: 30px;
  height: 400px;

  &._over {
    overflow-y: auto;

  }
}

.message-item {
  height: 80px;
  padding-left: 30px;
  cursor: pointer;
}

.message-item-top {
  position: relative;

  &::after {
    content: '';
    position: absolute;
    left: -25px;
    top: 6px;
    width: 15px;
    height: 15px;
    background-color: #5C5E66;
  }

  &._read {
    &::after {
      content: '';
      background-color: #155DFF;
    }
  }
}

.message-item-text {
  display: flex;
  // align-items: center;
  justify-content: space-between;
  flex-shrink: 1;

  span {
    display: flex;
    flex-shrink: 1;
  }

  span:nth-of-type(1) {
    width: 75%;
  }
}

.userinfo-wrap-item {
  width: 100%;
  height: 50px;
  margin-bottom: 15px;
  font-weight: bold;
  font-size: 22px;
  color: #5C5E66;
}


.item-box-app {
  display: flex;
  align-items: center;
  flex-direction: column;
  font-size: 20px;
  width: 180px;
  // margin-right: 10px;
  margin-top: 50px;
  color: rgba(0, 0, 0, 0.9);
  cursor: pointer;
  user-select: none;
}

.box-app-image {
  width: 114px;
  height: 114px;
  margin-bottom: 22px;

  &._bg {
    background: url('../../assets/image/comments/list_icon.png') no-repeat center center / 100%;
  }
}


.box-scroll-wrap {
  width: 100%;
  // margin-top: 200px !important;
  overflow: hidden;
}

.box-scroll-item {
  display: flex;
  width: 800px;
  // height: 110px !important;
  margin-bottom: 40px;
  font-size: 28px;
  white-space: nowrap !important;
  overflow: hidden !important;
  text-overflow: ellipsis !important;
}

.scroll-item-label {
  white-space: nowrap !important;
  overflow: hidden !important;
  text-overflow: ellipsis !important;
  // flex-shrink: 0;
  width: 800px;
  // width: 130px;
}


.myapp-block {
  display: flex;
  align-items: center;
  // justify-content: space-around;
  flex-wrap: wrap;
  width: 100%;
  // min-height: 400px;
  padding: 20px 0 30px;

  &._flexe {
    justify-content: end;
    min-height: 10;
    padding-right: 20px;

    /deep/ .el-input__inner {
      height: auto;
      font-size: 12px;
      background: rgba(255, 255, 255, 0.39);
      box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
    }
  }
}

.myapp-block-item {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: column;
  width: 150px;
  height: 150px;
  // margin-bottom: 20px;

  margin: 30px 1% 30px;
  // border: 2px solid rgba(0, 0, 0, 0.1);
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
  cursor: pointer;
}

.block-item-image {
  width: 90px;
  height: 90px;

  &._bg {
    background: url('../../assets/image/comments/list_icon.png') no-repeat center center / 100%;

  }
}

.block-item-box {
  width: 100%;
}

.item-box-title {
  text-align: center;
  padding: 0 10px;
  box-sizing: border-box;
  font-size: 20px;
  font-weight: 500;
  color: #000000;
}

.item-box-detail {
  height: 60px;
  margin: 15px 0;
  font-size: 20px;
  font-weight: 300;
  color: #000000;
}

.item-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
}

.item-box-unit {
  color: #000000;
}

.usercenter-more {
  font-size: 14px;
  color: #266FE8;
  cursor: pointer;
  user-select: none;

  &:hover {
    opacity: .75;
  }

  &._cen {
    // margin-bottom: 50px;
    text-align: center;
  }
}

/deep/ .el-empty {
  margin: 0 auto
}

/deep/ .el-button {
  background-color: #26acf7 !important;
  color: #FFFFFF !important;
}

.box-block-tab {
  display: flex;
  width: 270px;
  height: 30px;
  margin: 21px 0 25px 24px;
  overflow: hidden;
  background-color: #fafafa;
  color: #000629;
  font-size: 16px;
  text-align: center;
  // line-height: 60px;
  font-weight: bold;
}

.block-tab {
  width: 50%;
  cursor: pointer;
  user-select: none;

  &:hover {
    opacity: .75;
  }

  &._act {
    color: #155DFF;
    background: url('../../assets/image/comments/tab_bg.png') no-repeat center center / 100%;
  }
}

.block-apply {
  width: 100%;
  padding: 0 30px 20px;
}

/deep/ .el-table th.el-table__cell {
  background: #F3F3F3;
}

/deep/ .el-table thead {
  color: #1A1B1F;
}

/deep/ .el-table__row--striped {
  background: #d3e2f4;
}


/deep/ .el-image {
  // background: none !important;
  margin-right: 15px;
}

/deep/ .el-step__icon-inner {
  font-size: 50px;
}

/deep/ .el-step__icon {
  height: 50px;
  width: 50px;
  margin-top: -12px;
  color: #fff;
  background: linear-gradient(152deg, #5CA4FF 0%, #2F6BFF 100%);
}

/deep/ .el-step__title {
  font-size: 25px;
}

/deep/ .el-image-viewer__mask {
  z-index: 999999999999999 !important;
}

.item-status {
  position: relative;
  display: inline-block;
  width: 120px;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 15px;
  color: #000;

  &._bg0 {
    // background: #3bd11d;

    &::before {
      position: absolute;
      left: 20px;
      top: 12px;
      content: '';
      width: 10px;
      height: 10px;
      background: #3bd11d;
    }
  }

  &._bg1 {

    // background: #0d8ade;
    &::before {
      position: absolute;
      left: 20px;
      top: 12px;
      content: '';
      width: 10px;
      height: 10px;
      background: #0d8ade;
    }
  }

  &._bg2 {

    // background: #dea026;
    &::before {
      position: absolute;
      left: 20px;
      top: 12px;
      content: '';
      width: 10px;
      height: 10px;
      background: #dea026;
    }
  }

  &._bg3 {

    // background: #de4545;
    &::before {
      position: absolute;
      left: 20px;
      top: 12px;
      content: '';
      width: 10px;
      height: 10px;
      background: #de4545;
    }
  }

  &._bg4 {
    &::before {
      position: absolute;
      left: 20px;
      top: 12px;
      content: '';
      width: 10px;
      height: 10px;
      background: #848484;
    }
  }
}
</style>