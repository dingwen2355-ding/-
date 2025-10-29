<template>
  <div class="Left2To1 module">
    <SmallTitle :titleList="['指挥体系']" />
    <img
      class="button1"
      src="../../../../assets/global/设置.png"
      style="cursor: pointer"
      alt
      @click="showVisible = true"
    />
    <div class="content">
      <!-- <div class="card">
        <div class="frontIcon">
          <img src="@/assets/left/指挥长@2x.png" style="width: 132rem; height: 132rem" alt />
          <img
            src="@/assets/left/向下@2x.png"
            style="margin-top: 20rem; width: 40rem; height: 216rem"
            alt
          />
        </div>
        <div class="cardBackground">
          <div class="dutyHead">指挥长</div>
          <div class="leaderBox" v-for="(item, index) in list" :key="index">
            <div
              style="padding: 0 72rem; font-size: 60rem; font-family: Microsoft YaHei; font-weight: 400"
            >区指挥长</div>
            <div class="right-part" style="width: 65%">
              <span
                v-show="item.unit"
                style="display: inline-block; color: #fff; margin-right: 44rem"
                class="line-one"
                :title="item.unit"
              >{{ item.unit }}/{{ item.duty }}</span>
              <span
                v-show="item.duty != null"
                class="line-one"
                style="float: right; text-align: right"
                :title="item.duty"
              >{{ item.leader }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="frontIcon">
          <img src="@/assets/left/指挥长@2x.png" style="width: 132rem; height: 132rem" alt />
          <img
            src="@/assets/left/向下@2x.png"
            style="margin-top: 20rem; width: 40rem; height: 216rem"
            alt
          />
        </div>
        <div class="cardBackground">
          <div class="dutyHead">区级指挥</div>
          <div>
            <div v-for="(item, index) in relatedAreaList" :key="item.dutyLevel + index">
              <div
                style="padding: 56rem 0; margin: 35rem 0"
                class="right-part"
                v-for="leader in item.data.slice(0, 1)"
                :key="leader.personId"
              >
                <span
                  v-show="leader.unit"
                  style="display: inline-block; color: #fff; margin-right: 44rem; width: 70%; margin-left: 72rem"
                  class="line-one"
                  :title="leader.unit"
                >{{ leader.unit }}</span>
                <span
                  style="text-align: right; padding-right: 48rem"
                  v-show="leader.duty != null"
                  class="line-one"
                  :title="leader.duty"
                >{{ leader.leader }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="frontIcon">
          <img src="@/assets/left/指挥长@2x.png" style="width: 132rem; height: 132rem" alt />
          <img
            src="@/assets/left/向下@2x.png"
            style="margin-top: 20rem; width: 40rem; height: 216rem"
            alt
          />
        </div>
        <div class="cardBackground">
          <div class="dutyHead">街镇指挥</div>
          <div style="display: flex; flex-wrap: wrap; justify-content: space-between">
            <div v-for="(item, index) in relatedLocalList" :key="item.dutyLevel + index">
              <div
                style="padding: 56rem 0; margin: 35rem 0"
                class="right-part"
                v-for="leader in item.data.slice(0, 1)"
                :key="leader.personId"
              >
                <span
                  v-show="leader.unit"
                  style="display: inline-block; color: #fff; margin-right: 44rem; width: 70%; margin-left: 72rem"
                  class="line-one"
                  :title="leader.unit"
                >{{ leader.unit }}</span>
                <span
                  style="text-align: right; padding-right: 48rem"
                  v-show="leader.duty != null"
                  class="line-one"
                  :title="leader.duty"
                >{{ leader.leader }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="frontIcon">
          <img src="@/assets/left/指挥长@2x.png" style="width: 132rem; height: 132rem" alt />
          <img
            src="@/assets/left/向下@2x.png"
            style="opacity: 0; margin-top: 20rem; width: 40rem; height: 216rem"
            alt
          />
        </div>
        <div class="cardBackground">
          <div class="dutyHead">社会指挥</div>
          <div style="display: flex; flex-wrap: wrap; justify-content: space-between">
            <div v-for="(item, index) in relatedSocialList" :key="item.dutyLevel + index">
              <div
                style="padding: 56rem 0; margin: 35rem 0"
                class="right-part"
                v-for="leader in item.data.slice(0, 1)"
                :key="leader.personId"
              >
                <span
                  v-show="leader.unit"
                  style="display: inline-block; color: #fff; margin-right: 44rem; width: 70%; margin-left: 72rem"
                  class="line-one"
                  :title="leader.unit"
                >{{ leader.unit }}</span>
                <span
                  style="text-align: right; padding-right: 48rem"
                  v-show="leader.duty != null"
                  class="line-one"
                  :title="leader.duty"
                >{{ leader.leader }}</span>
              </div>
           
            </div>
          </div>
        </div>
      </div>-->
      <div class="top">
        <div class="list-item" v-for="item in listData" :key="item.id">
          <div class="bhDeptName">{{item.bhDeptName}}分中心</div>
          <div style="display: flex;align-items: center;">
            <div class="position">负责人：</div>
            <div class="name">{{ item.responsiblePersonName }}</div>
            <div class="majorMobile">{{ item.responsiblePersonMajorMobile }}</div>
            <i class="phoneIcon el-icon-phone" @click="openPhoneDiolag"></i>
          </div>
          <div style="display: flex;align-items: center;">
            <div class="position">联系人：</div>
            <div class="name">{{ item.name }}</div>
            <div class="majorMobile">{{ item.majorMobile }}</div>
            <i class="phoneIcon el-icon-phone" @click="openPhoneDiolag"></i>
          </div>
        </div>
      </div>
      <!-- <div class="bottom"></div> -->
    </div>
    <!-- <el-dialog
      :visible.sync="showVisibleSSXGDWXQ"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showVisibleSSXGDWXQ=false"
        style="width: 4060rem;height: 3326rem;"
        src="../../../../assets/global/事件相关区属单位详情@2x.png"
        alt
      />
    </el-dialog>-->
    <!-- <el-dialog
      :visible.sync="showVisible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img @click="showVisible=false"
           style="width: 4060rem;height: 3326rem;"
           src="../../../../assets/global/指挥组织体系编辑@2x.png"
           alt=""
      />

      <span style="position:absolute;bottom:600rem;left:200rem;display: inline-block;width: 1200rem;height: 200rem;background-color: transparent;" @click.prevent="showChild1Visible=true"></span>
      <span style="position:absolute;bottom:600rem;left:1450rem;display: inline-block;width: 1200rem;height: 200rem;background-color: transparent;" @click.prevent="showChild2Visible=true"></span>
      <span style="position:absolute;bottom:600rem;right:200rem;display: inline-block;width: 1200rem;height: 200rem;background-color: transparent;" @click.prevent="showChild3Visible=true"></span>
    </el-dialog>-->
    <personnalDialog
      :dialogVisibleFlag.sync="showVisible"
      :currentEvent="currentEvent"
      :eventType="eventType"
      :unitOptions="unitOptions"
    />
    <el-dialog
      :visible.sync="showChild1Visible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showChild1Visible = false"
        style="width: 5590rem; height: 3218rem"
        src="../../../../assets/global/指挥长设置@2x.png"
        alt
      />
    </el-dialog>
    <el-dialog
      :visible.sync="showChild2Visible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showChild2Visible = false"
        style="width: 5590rem; height: 3218rem"
        src="../../../../assets/global/事件相关属地单位@2x.png"
        alt
      />
    </el-dialog>
    <el-dialog
      :visible.sync="showChild3Visible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showChild3Visible = false"
        style="width: 5590rem; height: 3218rem"
        src="../../../../assets/global/事件相关属地单位@2x.png"
        alt
      />
    </el-dialog>
    <DialogAddressBook
      :dialogAddressBookVisible.sync="dialogAddressBookVisible"
      @openDialog="childOpenDialog"
    />
    <leaderDialog :dialogVisibleFlag.sync="showLeaderVisible" :showLeader="showLeader" />
    <DialogPhone :dialogPhoneVisible.sync="dialogPhoneVisible" :infomation="dialogPhoneData" />
    <DialogMessage :dialogMessageVisible.sync="messageVisible" :infomation="dialogMessageData" />
    <PhoneDiolag :dialogVisibleFlag.sync="openPhoneDiolagVisible" />
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import Axios from '@/utils/request'
import DialogAddressBook from '../dialog/DialogAddressBook.vue'
import leaderDialog from '../dialog/leaderDialog.vue'
import DialogPhone from '../dialog/DialogPhone.vue'
import DialogMessage from '../dialog/DialogMessage.vue'
import personnalDialog from '../dialog/personnalDialog.vue'
import PhoneDiolag from '@/components/PhoneDiolag.vue'
import moment from 'moment'
export default {
  name: 'Left2To1',
  components: {
    SmallTitle,
    leaderDialog,
    DialogAddressBook,
    DialogPhone,
    DialogMessage,
    personnalDialog,
    PhoneDiolag
  },
  data() {
    return {
      showVisibleSSXGDWXQ: false,
      showChild1Visible: false,
      showChild2Visible: false,
      showChild3Visible: false,
      showVisible: false,
      importVisible: false,
      list: [],
      listArr: [],
      reData: {},
      personnalDialogVisible: false,
      currentEvent: '',
      eventType: '',
      unitOptions: [],
      showLeaderVisible: false,
      showLeader: [],
      showLeaderList: [],
      showLeaderList1: [],
      dialogAddressBookVisible: false,
      dialogPhoneVisible: false,
      precinctId: this.$route.query.precinctId,
      messageVisible: false,
      dialogPhoneData: {},
      dialogMessageData: {},
      timer: null,
      dutyList: [],
      total: 0,
      currentPage: 1,
      pageSize: 2,
      currentPageData: [],
      activeIndex: 0,
      planId: 0,
      openPhoneDiolagVisible: false,
      // listData: [{
      //   "id": "1659107355322765317",
      //   "name": "尹雍",
      //   "account": "yinyong001",
      //   "password": "41d377df7104742f51f5b5bcd522774f60317cd1",
      //   "majorDept": 10020,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "工作人员",
      //   "majorMobile": "15052255225",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10004",
      //   "isLeaderInOtherDept": "0",
      //   "otherPosition": "工作人员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "20480,16384",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "马山街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 20480,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959620",
      //   "name": "吴珉",
      //   "account": "wumin001",
      //   "password": "30cc18e55814bfe9781fdb2bc9739e9c478ee11e",
      //   "majorDept": 10022,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "部门主任",
      //   "majorMobile": "13961832322",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10006,10694",
      //   "isLeaderInOtherDept": "0,0",
      //   "otherPosition": "部门主任,组员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "20480,12288,0",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "蠡园街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 20480,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959617",
      //   "name": "何炜锋",
      //   "account": "heweifeng001",
      //   "password": "21d3cfe43619e18e9bd5586d1ecbaca94f948884",
      //   "majorDept": 10020,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "工作人员",
      //   "majorMobile": "18360578692",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10004",
      //   "isLeaderInOtherDept": "0",
      //   "otherPosition": "工作人员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "16384,12288",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "马山街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 16384,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959621",
      //   "name": "周煜杰",
      //   "account": "zhouyujie001",
      //   "password": "61a6c69d017683b7214226a027e23421de319b53",
      //   "majorDept": 10023,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "镇政法委员",
      //   "majorMobile": "18552429333",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10007,10708",
      //   "isLeaderInOtherDept": "0,0",
      //   "otherPosition": "镇政法委员,组长",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "16384,12288,0",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "胡埭镇",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 16384,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959624",
      //   "name": "沈静",
      //   "account": "shenjing001",
      //   "password": "73c4356b8b074aa9288250874eca35bc05550152",
      //   "majorDept": 10025,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "综合行政执法局副局长",
      //   "majorMobile": "18961873612",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10009,10710,10711",
      //   "isLeaderInOtherDept": "0,0,0",
      //   "otherPosition": "综合行政执法局副局长,组员,组长",
      //   "gender": 2,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "16384,16384,0,0",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "荣巷街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 16384,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355331153922",
      //   "name": "江梽锋",
      //   "account": "jiangzhifeng001",
      //   "password": "c44002c54aa9484d274e5083da7e371d13c02666",
      //   "majorDept": 10026,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "中心主任",
      //   "majorMobile": "13912356582",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10010",
      //   "isLeaderInOtherDept": "0",
      //   "otherPosition": "工作人员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "16384,16384",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "蠡湖街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 16384,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959618",
      //   "name": "嬴龙",
      //   "account": "yinglong001",
      //   "password": "1551dff936db5fcee44e13d0fb7de9460893129d",
      //   "majorDept": 10021,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "信息督查科副科长",
      //   "majorMobile": "18921159696",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10005",
      //   "isLeaderInOtherDept": "0",
      //   "otherPosition": "信息督查科副科长",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "12288,8192",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "雪浪街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 12288,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355331153921",
      //   "name": "冯烨明",
      //   "account": "fengyeming001",
      //   "password": "16306ff3e2ce6782d709f22a7bb4f893240a4aee",
      //   "majorDept": 10025,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "中心负责人",
      //   "majorMobile": "13665141632",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10009",
      //   "isLeaderInOtherDept": "0",
      //   "otherPosition": "中心负责人",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "12288,12288",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "荣巷街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 12288,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355331153923",
      //   "name": "徐扣祥",
      //   "account": "xukouxiang",
      //   "password": "0933c1cd54fb2347f4da80285612b1f3e2ae68d5",
      //   "majorDept": 10026,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "工作人员",
      //   "majorMobile": "13921196045",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10010",
      //   "isLeaderInOtherDept": "0",
      //   "otherPosition": "工作人员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "12288,12288",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "蠡湖街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 12288,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107351635972097",
      //   "name": "倪锡坤",
      //   "account": "nixikun@bhqhljd",
      //   "password": "e918cc28976edd22df2414b02701b0fbb27e6f23",
      //   "majorDept": 4788,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "主任",
      //   "majorMobile": "13400029525",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10024,10008,10656",
      //   "isLeaderInOtherDept": "0,0,0",
      //   "otherPosition": "中心主任,中心主任,组员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "0510-85110728",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "12288,12288,12288,0",
      //   "isUpdated": 1,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": "1658281570898817026",
      //   "updateTime": "2023-09-01 18:58:02",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "河埒街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 0,
      //   "deptPositions": [],
      //   "depts": ""
      // }, {
      //   "id": "1659107354643288072",
      //   "name": "陈建忠",
      //   "account": "chenjianzhong@bhqxljd",
      //   "password": "73941a57df27aeb567cd5268392a349e2ad10b06",
      //   "majorDept": 7030,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "副组长",
      //   "majorMobile": "13771448899",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "4785,10021,10005,10573",
      //   "isLeaderInOtherDept": "0,0,0,0",
      //   "otherPosition": "综合行政执法局副局长,综合行政执法局副局长,综合行政执法局副局长,副组长",
      //   "gender": 1,
      //   "avatar": "http://wwlocal.qq.com/cgi-bin/downloadfile?type=download&fileid=WI5712-vyocZbqvpSimIUorO6dlUbb&uin=1688849887291882&filename=icon.png/0",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "16384,436796600,16384,12288,0",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "综合行政执法局",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 0,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959619",
      //   "name": "张军",
      //   "account": "zhangjun@bhqlhkfq",
      //   "password": "3159900137ba06b1f36c92f331206616f16202cc",
      //   "majorDept": 5574,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "组长",
      //   "majorMobile": "13063632285",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "5877,10006,10022",
      //   "isLeaderInOtherDept": "0,0,0",
      //   "otherPosition": "蠡园开发区党政办主任、综合管理服务指挥中心主任,蠡园开发区党政办主任、综合管理服务指挥中心主任,蠡园开发区党政办主任、综合管理服务指挥中心主任",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "0510-85110688",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "500303896,20480,16384,24576",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "派驻第五纪检监察组",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 0,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959622",
      //   "name": "包凯圆",
      //   "account": "baokaiyuan001",
      //   "password": "6ba12f9f5bd93673ce43480d9724b39c952b45c3",
      //   "majorDept": 12781,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "副站长",
      //   "majorMobile": "13771105688",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10023,10007,10709,10650",
      //   "isLeaderInOtherDept": "0,0,0,0",
      //   "otherPosition": "指挥中心副站长,指挥中心副站长,组员,组员",
      //   "gender": 1,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "0,12288,8192,0,0",
      //   "isUpdated": 0,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": -1,
      //   "updateTime": "2024-01-26 15:00:00",
      //   "isDeleted": 0,
      //   "lastLoginTime": "2023-10-13 16:15:03",
      //   "isAuthorized": 0,
      //   "isOnline": 0,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "综合指挥中心",
      //   "isEnableName": "启用",
      //   "isOnlineName": "离线",
      //   "isAuthorizedName": "未授权",
      //   "order": 0,
      //   "deptPositions": [],
      //   "depts": ""
      // },
      // {
      //   "id": "1659107355326959623",
      //   "name": "滕燕",
      //   "account": "tengyan001",
      //   "password": "856621556114910c286056f07bf08505b3d054c1",
      //   "majorDept": 4788,
      //   "isLeaderInDept": 0,
      //   "majorPosition": "副主任",
      //   "majorMobile": "15961717224",
      //   "isSendSms": 1,
      //   "isEnable": 1,
      //   "enable": 1,
      //   "otherDept": "10024,10008",
      //   "isLeaderInOtherDept": "0,0",
      //   "otherPosition": "工作人员,工作人员",
      //   "gender": 2,
      //   "avatar": "",
      //   "englishName": "",
      //   "email": "",
      //   "telephone": "0510-85801936",
      //   "externalCorpName": "无锡市人民政府",
      //   "sort": "8192,8192,8192",
      //   "isUpdated": 1,
      //   "dataSource": 1,
      //   "createUser": "1658281570898817026",
      //   "createTime": "2023-05-18 16:03:11",
      //   "updateUser": "1658281570898817026",
      //   "updateTime": "2023-09-01 18:58:30",
      //   "isDeleted": 0,
      //   "lastLoginTime": "2023-11-23 11:05:21",
      //   "isAuthorized": 0,
      //   "isOnline": 1,
      //   "initPassword": "",
      //   "isPasswordUpdated": 0,
      //   "isSendSmsInitPassword": 0,
      //   "majorBhDeptName": "",
      //   "otherBhDeptName": "",
      //   "bhDeptName": "河埒街道",
      //   "isEnableName": "启用",
      //   "isOnlineName": "在线",
      //   "isAuthorizedName": "未授权",
      //   "order": 0,
      //   "deptPositions": [],
      //   "depts": ""
      // }
      // ]
      listData: [
        {
          bhDeptName: "马山",
          responsiblePersonName: "周志珺",
          responsiblePersonMajorMobile: "13812070345",
          name: "杨强",
          majorMobile: "15052255225"
        },
        {
          bhDeptName: "雪浪",
          responsiblePersonName: "陈建忠",
          responsiblePersonMajorMobile: "13771448899",
          name: "赢龙",
          majorMobile: "18921159696"
        },
        {
          bhDeptName: "蠡园",
          responsiblePersonName: "杨忠伟",
          responsiblePersonMajorMobile: "13771020970",
          name: "周泓彬",
          majorMobile: "18851560632"
        },
        {
          bhDeptName: "胡埭",
          responsiblePersonName: "周煜杰",
          responsiblePersonMajorMobile: "18552429333",
          name: "包凯圆",
          majorMobile: "13771105688"
        },
        {
          bhDeptName: "河埒",
          responsiblePersonName: "倪锡坤",
          responsiblePersonMajorMobile: "13400029525",
          name: "滕燕",
          majorMobile: "15961717224"
        },
        {
          bhDeptName: "荣巷",
          responsiblePersonName: "许弘",
          responsiblePersonMajorMobile: "13861716915",
          name: "薛东利",
          majorMobile: "18915231662"
        },
        {
          bhDeptName: "蠡湖",
          responsiblePersonName: "虞琪",
          responsiblePersonMajorMobile: "13912356582",
          name: "徐扣祥",
          majorMobile: "13921196045"
        }
      ]
    }
  },
  watch: {
    '$store.getters.eventPlanId'(v) {
      console.log('planId:', v)
      this.planId = v
      this.communicationOrderByDepartment()
    }
  },
  mounted() {
    this.$root.Bus.$on('refreshIframe', () => {
      this.communicationOrderByDepartment()
    })
  },
  computed: {
    relatedAreaList() {
      if (!this.reData['2']) {
        return []
      }
      return this.reData['2'][0] || []
    },
    relatedLocalList() {
      if (!this.reData['3']) {
        return []
      }
      return this.reData['3'][0] || []
    },
    relatedSocialList() {
      if (!this.reData['4']) {
        return []
      }
      return this.reData['4'][0] || []
    }
  },
  methods: {
    openPhoneDiolag() {
      this.openPhoneDiolagVisible = true
    },
    handleClose() { },
    doShowChild(type) {
      this.showChildVisible = true
      this.showChildType = type
    },
    getCurrentPageData(v) {
      this.activeIndex = v - 1
      let begin = (v - 1) * this.pageSize
      let end = v * this.pageSize
      this.currentPageData = this.dutyList.slice(begin, end)
    },
    importCity() {
      this.timer && clearInterval(this.timer)
      let url = '/apis/warningInfo/getDutyInfo'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let list = res.data.data
          for (let i = 0; i < list.length; i++) {
            list[i].leader =
              list[i].name.replace(' ', '').length === 2
                ? list[i].name.slice(0, 1) + '\xa0\xa0\xa0' + list[i].name.slice(1)
                : list[i].name
            if (list[i].duty === '带班市领导') {
              list[i].sort = 1
              // list[i].url = 'http://10.1.50.137:7777/boss/myResource/c0ZTYzVCZnVhYU09/' + list[i].name + '.jpg'
              list[i].url = list[i].imgurl || ''
            }
            if (list[i].duty === '指挥长') {
              list[i].sort = 2
            }
            if (list[i].duty === '专职值班长') {
              list[i].sort = 3
            }
            if (list[i].duty === '值班员') {
              list[i].sort = 4
            }
          }
          // 按顺序展示
          list.sort(function (a, b) {
            return a.sort >= b.sort ? 1 : -1
          })
          // 按相同职务组成新数组
          let tempArr = []
          let Data = []
          for (let i = 0; i < list.length; i++) {
            if (tempArr.indexOf(list[i].duty) === -1) {
              Data.push({
                duty: list[i].duty,
                dataInfo: [list[i]]
              })
              tempArr.push(list[i].duty)
            } else {
              for (let j = 0; j < Data.length; j++) {
                if (Data[j].duty === list[i].duty) {
                  Data[j].dataInfo.push(list[i])
                  break
                }
              }
            }
          }
          this.listArr = Data
        }
        this.intervalFixedPointTaskFn(
          {
            timeFixedArr: ['08:55:00'],
            delayTime: 1000
          },
          this.importCity
        )
      })
    },
    intervalFixedPointTaskFn({ timeFixedArr = [], delayTime = 1000 }, tickerFn) {
      let executed = true // true代表需要定时刷新的方法是否执行了,后面纵使在设定时间范围内也不执行自动刷新
      this.timer && clearInterval(this.timer)
      this.timer = setInterval(() => {
        console.log('timer...')
        let currentTime = new Date().getTime() // 当前时间
        let currentYMD = moment(currentTime).format('YYYY-MM-DD') // 当前年月日
        let refresh = false // 判断是否需要刷新
        // 判断是否在设定的时间范围内
        let count = 0 // 记录不在设定范围内的数量
        for (let i = 0; i < timeFixedArr.length; i++) {
          let item = timeFixedArr[i] // 固定的时分秒
          let setTime = new Date(`${currentYMD} ${item}`).getTime() // 完整时间
          if (currentTime >= setTime && currentTime <= setTime + delayTime) {
            // 没有刷新过且在设定时间范围内 - 刷新
            if (!executed) {
              // 只执行一次
              refresh = true
            }
            break
          } else {
            count++
          }
        }

        if (count === timeFixedArr.length) {
          executed = false
        }

        if (refresh) {
          executed = true
          console.log(`${moment(currentTime).format('YYYY-MM-DD HH:mm:ss')} 执行定时任务....`)
          tickerFn && tickerFn()
        }
      }, 1000)
    },
    childOpenDialog(data) {
      this.openDialog(data.type, data.data)
    },
    openDialog(type, data) {
      switch (type) {
        case 'video':
          this.dialogVideoVisible = true
          this.dialogVideoData = data
          break
        case 'law-record':
          this.dialogLawRecordVisible = true
          this.dialogLawRecordData = data
          break
        case 'addressbook':
          this.dialogAddressBookVisible = true
          break
        case 'phone':
          this.dialogPhoneVisible = true
          this.dialogPhoneData = data
          break
        case 'message':
          this.messageVisible = true
          this.dialogMessageData = data
          break
        default:
          break
      }
    },
    talk(val) {
      val.unit = val.post
      this.dialogPhoneVisible = true
      this.dialogPhoneData = val
    },
    checking() {
      this.importVisible = true
    },
    leader(v, i) {
      // return false
      if (v && v.length > 0) {
        console.log('leader', v)
        this.showLeader = v
      } else {
        this.showLeader = []
      }
      this.showLeaderVisible = true
    },
    communicationOrderByDepartment() {
      let url = '/apis/Command/communicationOrderByDepartmentByPrecinctId?linkType=2&isMax=isMax&planId=' + this.planId
      let _this = this
      Axios.get(url).then((res) => {
        let data = res.data.data
        _this.reData = data
        _this.showLeaderList = []
        if (data['2']) {
          let leader = data['2'][0]
          for (let key in leader) {
            let obj = {
              index: 0,
              name: leader[key].name,
              data: leader[key].data
            }
            _this.showLeaderList.push(obj)
          }
        } else {
        }
        if (data['3']) {
          let leader = data['3'][0]
          for (let key in leader) {
            let obj = {
              index: 1,
              name: leader[key].name,
              data: leader[key].data
            }
            _this.showLeaderList1.push(obj)
          }
        } else {
          // _this.showLeaderList1.push({ index: 1 })
        }
        _this.list = data['1'] ? data['1'][0]['999998'].data : []
      })
    },
    openQueryUserDialog() {
      this.personnalDialogVisible = true
    },
    getEventLists() {
      let url = '/apis/eventDeal/eventInfo'
      if (this.$route.query.precinctId) {
        url += '?precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          if (res.data.data.length > 0) {
            this.currentEvent = res.data.data[0].eventType
            let event = res.data.data[0].eventType
            this.queryUnit(event)
          } else {
            this.queryUnit()
          }
        }
      })
    },
    queryUnit(e) {
      const url = '/apis/manage/queryEventTypeList'
      Axios.get(url).then((r) => {
        this.unitOptions = r.data.data
        this.eventType = ''
        this.unitOptions.forEach((item) => {
          if (e && e === item.typeName) {
            this.eventType = item.id
          }
        })
      })
    }
  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
  }
}
</script>
<style lang="scss" scoped>
.videoDialogZHTXBJ {
  z-index: 10000 !important;
  ::v-deep .el-dialog {
    position: relative;
    .el-dialog__header {
      display: none;
    }
  }
}
</style>
<style lang="scss" scoped>
.Left2To1 {
  width: 1672rem;
  position: absolute;
  top: 440rem;
  left: 3048rem;
  .button1 {
    width: 140rem;
    height: 140rem;
    position: absolute;
    right: 0rem;
    top: 60rem;
  }
  .content {
    height: 3064rem;
    // margin: 0rem 0 30rem;
    // padding-top: 54rem;
    position: relative;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;
    overflow: auto;
    .top {
      height: 3064rem;
      display: flex;
      flex-direction: column;
      align-items: center;
      overflow: hidden;
      overflow-y: auto;

      .list-item {
        margin: 50rem 0;
        .bhDeptName {
          font-size: 80rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ffffff;
          margin-bottom: 20rem;
        }
        .phoneIcon {
          width: 150rem;
          height: 150rem;
          background: #238bbe;
          border-radius: 50%;
          color: #e1e0dc;
          line-height: 150rem;
          font-size: 100rem;
          text-align: center;
          border: 10rem solid #238bbe;
          cursor: pointer;
        }
        .position {
          font-size: 70rem;
          width: 366rem;
          height: 164rem;
          line-height: 164rem;
          // background-image: url('../../../../assets/left/指挥中心@2x.png');
          background-repeat: no-repeat;
          background-size: 100% 100%;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #68c6f4;
        }
        .name {
          font-size: 70rem;
          width: 336rem;
          height: 164rem;
          line-height: 164rem;
          // background-image: url('../../../../assets/left/指挥中心@2x.png');
          background-repeat: no-repeat;
          background-size: 100% 100%;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #68c6f4;
        }
        .majorMobile {
          font-size: 70rem;
          width: 506rem;
          height: 164rem;
          line-height: 164rem;
          // background-image: url('../../../../assets/left/指挥中心@2x.png');
          background-repeat: no-repeat;
          background-size: 100% 100%;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #68c6f4;
        }
      }
    }
    .bottom {
      width: 1672rem;
      height: 586rem;
      background-image: url('../../../../assets/left/建筑@2x.png');
      background-repeat: no-repeat;
      background-size: 100% 100%;
    }
    .card {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      flex-wrap: wrap;
      width: 100%;
      background-image: url('../../../../assets/left/bg.png');
      background: rgba(3, 16, 50, 0.5);
      .frontIcon {
        margin-top: 40rem;
        align-self: flex-start;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-left: 48rem;
      }
      .cardBackground {
        width: 1352rem;
        margin: 35rem;
        border: 8rem solid #002e7b;
        border-radius: 8rem;
        min-height: 308rem;
        .dutyHead {
          font-size: 64rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          width: 100%;
          text-indent: 24rem;
          height: 124rem;
          line-height: 124rem;
          background: linear-gradient(90deg, #1c4eb4 0%, rgba(28, 78, 180, 0) 100%);
        }
        .leaderBox {
          display: flex;
          justify-content: space-between;
          padding: 56rem 0;
        }
      }
      img {
        width: 180rem;
        height: 114rem;
        // margin-top: 70rem;
      }
      .left-part {
        width: 33%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;
        background-image: url('../../../../assets/left/值班体系@2x.png');
        background-repeat: no-repeat;
        background-position: 20rem 35rem;
        .line-one {
          font-size: 20rem;
          font-family: PingFang SC;
          font-weight: 600;
          color: #ffffff;
          white-space: nowrap;
        }
        .line-two {
          font-size: 16rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #9eacc1;
          white-space: nowrap;
        }
      }
      .center-part {
        width: 33%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;

        font-size: 14rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #5a697d;
      }
      .right-part {
        // width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 44rem;
        font-family: PingFang SC;
        font-weight: 400;
        color: #ffffff;
        margin-left: 28rem;
        padding-right: 48rem;
        .line-one {
          color: #68c6f4;
          font-size: 60rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          &:nth-child(2) {
            flex-grow: 1;
            flex-shrink: 0;
            align-self: center;
          }
        }
        .more {
          width: 160rem;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
    }
    .cardSJ {
      margin-top: 10rem;
      // margin-top: 40rem;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      width: 100%;
      height: 100rem;

      .cardDiv:first-of-type .leaderOne {
        width: 98%;
        background-image: url('../../../../assets/left/带班领导.png');
        background-repeat: no-repeat;
        margin-left: 0;
      }
      .cardDiv:last-of-type .leaderOne .right-part .line-one {
        margin: 6rem 0;
      }
      .leaderOne {
        background-image: url('../../../../assets/left/其他三个.png');
        background-repeat: no-repeat;
        width: 94%;
        background-size: 100% 100%;
        padding: 0rem 5rem 0 10rem;
        margin-left: 7rem;
        .showFlex {
          display: flex;
          align-items: center;
          height: 25rem;
        }
        .dutyUl {
          display: flex;
          position: absolute;
          left: 40%;
          bottom: 0%;
          // justify-content: center;
          li {
            width: 10rem;
            height: 10rem;
            cursor: pointer;
            background: url('../../../../assets/left/圆形.png');
            background-repeat: no-repeat;
          }
        }
        .active {
          width: 20rem !important;
          background: url('../../../../assets/left/矩形.png') !important;
          background-repeat: no-repeat !important;
        }
        .line-two {
          color: #a3c9fc;
          font-size: 12rem;
        }
        .right-part {
          img {
            height: 56rem;
            margin-top: 5rem;
            margin-right: 10rem;
          }
          .line-one:first-of-type {
            font-size: 14rem;
            color: #fff;
            position: relative;
            // margin: 0 0 8rem 0;
            img {
              width: 15rem;
              height: 15rem;
              // margin-top: 0;
              // margin-right: 0;
              margin-left: 36rem;
              // position: absolute;
              // right: 24rem;
              cursor: pointer;
            }
          }
          .line-one:last-of-type {
            color: #a3c9fc;
            font-size: 10rem;
            flex: 1;
            justify-content: center;
            flex-direction: column;
            img {
              width: 18rem;
              height: 18rem;
              margin-top: 0;
              margin-right: 0;
              margin-left: 20rem;
              cursor: pointer;
            }
          }
        }
      }
      .leaderother {
        background-image: url('../../../../assets/left/其他三个.png');
        background-repeat: no-repeat;
        width: 95%;
        background-size: 100% 100%;
      }
    }
    .row-panel {
      display: flex;
      flex-wrap: nowrap;
      justify-content: space-between;
      width: 100%;
      .info-p {
        width: 1388rem;
        height: 125rem;
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        flex-grow: 1;
        // margin-top: 30rem;

        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        justify-content: space-between;
        align-items: center;
        cursor: pointer;
        div {
          display: flex;
          align-items: center;
          margin-left: 40rem;
          .icon-a {
            width: 70rem;
            height: 60rem;
            display: inline-block;
            background-image: url('../../../../assets/left/机关单位@2x.png');
            background-repeat: no-repeat;
            background-size: contain;
          }
          .icon-b {
            width: 70rem;
            height: 60rem;
            display: inline-block;
            background-image: url('../../../../assets/left/号码归属地@2x.png');
            background-repeat: no-repeat;
            background-size: contain;
          }
          .icon-c {
            width: 22rem;
            height: 22rem;
            display: inline-block;
            background-image: url('../../../../assets/left/单位@2x.png');
            background-repeat: no-repeat;
          }
          .icon-d {
            width: 22rem;
            height: 22rem;
            display: inline-block;
            background-image: url('../../../../assets/left/救援力量@2x.png');
            background-repeat: no-repeat;
          }
          .info {
            font-size: 48rem;
            font-family: SourceHanSansCN;
            font-weight: 400;
            color: #ffffff;
            line-height: 24rem;
            margin-left: 10rem;
          }
        }
        .num {
          font-size: 56rem;
          font-family: PingFang SC;
          font-weight: 400;
          color: #ffffff;
          line-height: 24rem;
          margin-right: 20rem;
        }
      }
    }
    .onlyLine {
      position: absolute;
      top: -2rem;
      right: 55rem;
      width: 100rem;
      height: 26rem;
      font-size: 15rem;
      font-family: PingFang SC;
      text-align: center;
      line-height: 26rem;
      font-weight: 600;
      color: #ffffff;
      background: linear-gradient(0deg, RGBA(49, 173, 243, 1) 30%, #ffffff 70.042969%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      border: 1rem solid RGBA(21, 97, 155, 1);
      cursor: pointer;
    }
    .menu-1 {
      position: absolute;
      top: -3rem;
      right: 20rem;
      width: 26rem;
      height: 26rem;
      border: 1rem solid rgba(17, 155, 248, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      i {
        font-size: 18rem;
        color: rgba(17, 155, 248, 1);
      }
    }
  }
}
</style>
