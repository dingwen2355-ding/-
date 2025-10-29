<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="eventDetailsDialog"
      :title="title"
      :visible.sync="dialogVisible"
      width="1780px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-card class="leftCard">
          <div class="tab">
            <div class="item" v-for="(item,index) in ProcessInformationList" :key="index" @click="handleDetail(index)">
              <div class="line" v-if="index!==ProcessInformationList.length-1"></div>
              <div class="top" :class="{ isActived: index === stepsActive }">{{ index + 1 }}</div>
              <div class="name" :class="{ itemActive: index === stepsActive }">{{ item.dealNodeName }}</div>
              <div class="time">{{ item.processTime }}</div>
            </div>
          </div>
        </el-card>
        <el-card class="rightCard">
          <el-collapse v-model="active">
            <el-collapse-item title="● 事件基础信息" name="1">
              <el-form
                :inline="true"
                ref="formData1"
                :rules="rules1"
                class="storage-form"
                :model="formData1"
                label-width="110px"
                style="margin-top:10px"
                :disabled="title === '详情'"
              >
                <el-form-item label="事件编号" prop="taskId">
                  <el-input clearable v-model="formData1.taskId" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="案卷编号" prop="recordId">
                  <el-input clearable v-model="formData1.recordId" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件名称" prop="eventName">
                  <el-input clearable v-model="formData1.eventName" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件状态" prop="eventState">
                  <span class="stateBorder">
                    <span :style="cerrorColor(formData1.eventStateId)">{{formData1.eventState}}</span>
                    <!-- <span
                      :class="[formData1.overdueState==='已超期'?'overdueStatRed':'',
                formData1.overdueState==='即将超期'?'overdueStatyellow':'']"
                      v-if="formData1.overdueState!=='正常'&&formData1.overdueState!==null"
                    >({{formData1.overdueState}})</span>-->
                    <span
                      :class="[formData1.overdueStateId==='30'?'overdueStatRed':'',
                formData1.overdueStateId==='20'?'overdueStatyellow':'']"
                      v-if="formData1.overdueStateId!=='10'&&formData1.overdueStateId!==null &&formData1.overdueStateId!==''"
                    >({{formData1.overdueState}})</span>
                  </span>
                  <!-- <el-input
                    clearable
                    v-model="formData1.eventState"
                    :class="[formData2.eventState==='已超期'&&formData2.eventState==='即将超期'?'overdueStatRed':'']"
                  ></el-input>-->
                </el-form-item>
                <!-- <el-form-item label="超期状态" prop="overdueState">
                  <el-input clearable v-model="formData1.overdueState"></el-input>
                </el-form-item>-->
                <el-form-item label="事件位置描述" prop="address">
                  <el-input type="textarea" :rows="2" v-model="formData1.address" style="width:220px" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="经度坐标" prop="gpsx">
                  <el-input clearable v-model="formData1.gpsx" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="纬度坐标" prop="gpsy">
                  <el-input clearable v-model="formData1.gpsy" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="所属社区" prop="gridName">
                  <el-input clearable v-model="formData1.gridName" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件详细描述" prop="desc">
                  <el-input type="textarea" :rows="2" v-model="formData1.desc" style="width:1230px" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件标签" prop="eventTag">
                  <!-- <span
                    class="stateBorder"
                    v-if="!eventTagChangeShow"
                    clearable
                    :readonly="true"
                    style="color:#606266"
                  >{{formData1.eventTag }}</span>-->
                  <el-cascader
                    :options="eventTagList"
                    :props="optionProps"
                    v-model="formData1.eventTag"
                    clearable
                    :filterable="true"
                    :disabled="!eventTagChangeShow"
                    placeholder="请选择事件标签"
                  ></el-cascader>
                  <span v-if="roleId==='1' || roleId==='10'">
                    <el-button
                      type="primary"
                      size="mini"
                      @click="eventTagChangeShow=!eventTagChangeShow"
                      style="margin-left:5px"
                      v-if="!eventTagChangeShow"
                    >修改</el-button>
                    <el-button type="primary" @click="eventTagChange" size="mini" style="margin-left:5px" v-else>确定</el-button>
                  </span>
                </el-form-item>
                <el-form-item label="主题名称" prop="topicName">
                  <el-input clearable v-model="formData1.topicName" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件类型" prop="eventType" style="margin-right:200px">
                  <el-input clearable v-model="formData1.eventType" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件级别" prop="level">
                  <el-input clearable v-model="formData1.level" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件破坏程度" prop="damageDegree">
                  <el-input clearable v-model="formData1.damageDegree" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件上报渠道" prop="channel">
                  <el-input clearable v-model="formData1.channel" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="痛点难点" prop="isPainSpot">
                  <span class="stateBorder">{{ formData1.isPainSpot==='1'?'是':'否' }}</span>
                </el-form-item>
                <el-form-item label="上报人姓名" prop="reportPerson">
                  <el-input clearable v-model="formData1.reportPerson" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="上报人电话" prop="reportPhone">
                  <el-input clearable v-model="formData1.reportPhone" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件上报来源" prop="reportSource">
                  <el-input clearable v-model="formData1.reportSource" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="信息来源" prop="infoSource">
                  <el-input clearable v-model="formData1.infoSource" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="事件发生时间" prop="happenTime">
                  <el-date-picker
                    v-model="formData1.happenTime"
                    type="datetime"
                    format="yyyy-MM-dd HH:mm:ss"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :readonly="true"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item label="事件上报时间" prop="syncTime">
                  <el-date-picker
                    v-model="formData1.syncTime"
                    type="datetime"
                    format="yyyy-MM-dd HH:mm:ss"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :readonly="true"
                  ></el-date-picker>
                </el-form-item>
                <el-form-item label="处理截止时间" prop="deadlineTimeCopy">
                  <el-date-picker
                    v-model="deadlineTimeCopy"
                    type="datetime"
                    format="yyyy-MM-dd HH:mm:ss"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :readonly="!deadlineTimeShow"
                  ></el-date-picker>
                  <span v-if=" formData1.eventStateId==='30' &&   roleId==='1' || roleId==='10'">
                    <el-button
                      type="primary"
                      size="mini"
                      @click="deadlineTimeShow=!deadlineTimeShow"
                      style="margin-left:5px"
                      v-if="!deadlineTimeShow"
                    >修改</el-button>
                    <el-button type="primary" @click="deadlineTimeChange" size="mini" style="margin-left:5px" v-else>确定</el-button>
                  </span>
                </el-form-item>
                <el-form-item label="事件结案时间" prop="endTime" v-if="title==='事件详情'">
                  <el-date-picker
                    v-model="formData1.endTime"
                    type="datetime"
                    format="yyyy-MM-dd HH:mm:ss"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :readonly="true"
                  ></el-date-picker>
                </el-form-item>
              </el-form>
              <!-- 附件 -->
              <div class="files">
                <span v-if="formData1.files !== '' && formData1.files !== null && formData1.files !== undefined">事件附件</span>
                <AttachmentUpload :imgUrl="formData1.files" :title="title" />
              </div>
            </el-collapse-item>
            <el-collapse-item title="● 事件处置过程信息" name="2" v-if="dealNodeName!=='数据接入'">
              <el-form
                :inline="true"
                ref="formData2"
                :rules="rules2"
                class="storage-form"
                :model="formData2"
                label-width="110px"
                style="margin-top:10px"
                :disabled="title === '详情'"
              >
                <!-- 提交区级 -->
                <div v-if="formData2.dealNodeId==='9'">
                  <el-form-item label="反馈人" prop="distributePersonName">
                    <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="反馈时间" prop="distributeTime">
                    <el-date-picker
                      v-model="formData2.distributeTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="备注" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:s"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 已派遣 -->
                <div v-else-if="formData2.dealNodeId==='20'">
                  <el-form-item label="下派姓名" prop="distributePersonName">
                    <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派时间" prop="distributeTime">
                    <el-date-picker
                      v-model="formData2.distributeTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>

                  <el-form-item label="下派处置意见" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:300px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="处置部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="处置人电话" prop="dealPersonPhone" style="margin-right:200px">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置开始时间" prop="dealStartTime">
                    <el-date-picker
                      v-model="formData2.dealStartTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置截止时间" prop="dealEndTime">
                    <el-date-picker
                      v-model="formData2.dealEndTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                </div>
                <!-- 非我所属 -->
                <div v-else-if="formData2.dealNodeId==='10' || formData2.dealNodeId==='45'">
                  <el-form-item label="下派姓名" prop="distributePersonName">
                    <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派时间" prop="distributeTime">
                    <el-date-picker
                      v-model="formData2.distributeTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="下派处置意见" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:300px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="处置部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="处置人电话" prop="dealPersonPhone" style="margin-right:200px">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置开始时间" prop="dealStartTime">
                    <el-date-picker
                      v-model="formData2.dealStartTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置截止时间" prop="dealEndTime">
                    <el-date-picker
                      v-model="formData2.dealEndTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="备注" prop="dealInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.dealInfo"
                      style="width:1240px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 接收 -->
                <div v-else-if="formData2.dealNodeId==='50'">
                  <el-form-item label="下派姓名" prop="distributePersonName">
                    <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派时间" prop="distributeTime">
                    <el-date-picker
                      v-model="formData2.distributeTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="下派处置意见" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:300px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="处置部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="处置人电话" prop="dealPersonPhone" style="margin-right:200px">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置开始时间" prop="dealStartTime">
                    <el-date-picker
                      v-model="formData2.dealStartTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置截止时间" prop="dealEndTime">
                    <el-date-picker
                      v-model="formData2.dealEndTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="备注" prop="dealInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.dealInfo"
                      style="width:1240px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 已处置 -->
                <div v-else-if="formData2.dealNodeId==='60'">
                  <el-form-item label="下派姓名" prop="distributePersonName">
                    <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派时间" prop="distributeTime">
                    <el-date-picker
                      v-model="formData2.distributeTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="下派处置意见" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:300px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="处置部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="处置人电话" prop="dealPersonPhone" style="margin-right:200px">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置开始时间" prop="dealStartTime">
                    <el-date-picker
                      v-model="formData2.dealStartTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置截止时间" prop="dealEndTime">
                    <el-date-picker
                      v-model="formData2.dealEndTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置反馈" prop="dealInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.dealInfo"
                      style="width:1240px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 抢单 -->
                <div v-else-if="formData2.dealNodeId==='30'">
                  <el-form-item label="处置部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="处置人电话" prop="dealPersonPhone" style="margin-right:200px">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置开始时间" prop="dealStartTime">
                    <el-date-picker
                      v-model="formData2.dealStartTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置截止时间" prop="dealEndTime">
                    <el-date-picker
                      v-model="formData2.dealEndTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="抢单备注" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:1230px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 追回 -->
                <div v-else-if="formData2.dealNodeId==='40'">
                  <el-form-item label="追回部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="追回人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="追回人电话" prop="dealPersonPhone">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="追回时间" prop="processTime">
                    <el-date-picker
                      v-model="formData2.processTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="追回备注" prop="dealInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.dealInfo"
                      style="width:1230px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 报废 -->
                <div v-else-if="formData2.dealNodeId==='70'">
                  <el-form-item label="报废人姓名" prop="distributePersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.distributePersonName" placement="top">
                      <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="报废人电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="报废事由" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:1230px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
                <!-- 其他 -->
                <div v-else>
                  <el-form-item label="下派姓名" prop="distributePersonName">
                    <el-input clearable v-model="formData2.distributePersonName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派电话" prop="distributePhone">
                    <el-input clearable v-model="formData2.distributePhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="下派时间" prop="distributeTime">
                    <el-date-picker
                      v-model="formData2.distributeTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="下派处置意见" prop="distributeInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.distributeInfo"
                      style="width:300px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="处置部门名称" prop="dealDeptName">
                    <el-input clearable v-model="formData2.dealDeptName" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置人姓名" prop="dealPersonName">
                    <el-tooltip class="item" effect="dark" :content="formData2.dealPersonName" placement="top">
                      <el-input clearable v-model="formData2.dealPersonName" :readonly="true"></el-input>
                    </el-tooltip>
                  </el-form-item>
                  <el-form-item label="处置人电话" prop="dealPersonPhone" style="margin-right:200px">
                    <el-input clearable v-model="formData2.dealPersonPhone" :readonly="true"></el-input>
                  </el-form-item>
                  <el-form-item label="处置开始时间" prop="dealStartTime">
                    <el-date-picker
                      v-model="formData2.dealStartTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="处置截止时间" prop="dealEndTime">
                    <el-date-picker
                      v-model="formData2.dealEndTime"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      :readonly="true"
                    ></el-date-picker>
                  </el-form-item>
                  <el-form-item label="备注" prop="dealInfo">
                    <el-input
                      type="textarea"
                      :rows="2"
                      v-model="formData2.dealInfo"
                      style="width:1240px"
                      :readonly="true"
                    ></el-input>
                  </el-form-item>
                </div>
              </el-form>
              <!-- 附件 -->
              <div class="files">
                <span v-if="formData2.files !== '' && formData2.files !== null && formData2.files !== undefined">处置附件</span>
                <AttachmentUpload :imgUrl="formData2.files" :title="title" />
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </div>
      <!-- <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>-->
    </el-dialog>
  </div>
</template>
<script>
import AttachmentUpload from '../../components/attachmentUpload.vue'
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'eventDetails',
  props: ['detailsDialog', 'detailsDialogData'],
  components: { AttachmentUpload },
  data() {
    return {
      readonly: true,
      dialogVisible: false,
      tableData: [],
      title: '事件详情',
      active: ['1', '2'],
      formData1: {
        taskId: '', // 事件编号
        recordId: '', // 案卷编号
        eventName: '', // 事件名称
        address: '', // 事件位置描述
        happenTime: '', // 事件发生时间
        damageDegree: '', // 事件破坏程度
        topicName: '', // 主题名称
        desc: '', // 事件详细描述
        eventState: '', // 事件状态
        overdueState: '', // 超期状态
        eventTag: '', // 事件标签
        eventType: '', // 事件类型
        level: '', // 事件级别
        reportSource: '', // 事件上报来源
        channel: '', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: '', // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: ''
      },
      rules1: {},
      formData2: {
        distributePersonName: '', // 下派姓名
        distributePhone: '', // 下派电话
        distributeTime: '', // 下派时间
        distributeInfo: '', // 下派处置意见
        dealStartTime: '', // 处置开始时间
        dealEndTime: '', // 处置截止时间
        dealDeptName: '', // 处置部门名称
        dealPersonName: '', // 处置人姓名
        dealPersonPhone: '', // 处置人电话
        dealInfo: '', // 处置反馈
        files: ''
      },
      rules2: {},
      ProcessInformationList: [],
      stepsActive: 1,
      ProcessInformationListLength: 0,
      dealNodeName: '',
      deadlineTimeShow: false, // 处置截止时间修改
      roleId: sessionStorage.getItem('roleId'),
      deadlineTimeCopy: '',
      eventTagChangeShow: false, // 事件标签的修改
      eventTagList: [], // 事件标签列表
      optionProps: {
        value: 'nodeId',
        label: 'nodeName',
        children: 'childrenList',
        expandTrigger: 'hover',
        checkStrictly: true
      },
      isChange: false
    }
  },

  computed: {
    // 处置框
    dealInfoChange() {
      return (v) => {
        let data = '22'
        switch (v.dealNodeName) {
          case '已处置':
            data = '处置反馈'
            break
          case '已派遣':
            data = '下派意见'
            break
          case '非我所属':
            data = '备注'
            break
          case '处置中':
            data = '备注'
            break
          case '提交区级':
            data = '备注'
            break
          default:
            data = '备注'
            break
        }
        return data
      }
    },
    cerrorColor() {
      return (v) => {
        let color = ''
        switch (v) {
          case '10': // 报废
            color = { color: '#B0B0B0' }
            break
          case '20': //  已反馈
            color = { color: '#231FB3' }
            break
          case '30': // 待派遣
            color = { color: '#0FB4F0' }
            break
          case '40': // 待接收
            color = { color: '#0005BD' }
            break
          case '50': // 处置中
            color = { color: '#7030A0' }
            break
          case '60': // 已处置
            color = { color: '#1FA159' }
            break
          default:
            color = { color: '#000000' }
            break
        }
        return color
      }
    }
  },
  watch: {
    detailsDialog(v) {
      this.dialogVisible = JSON.parse(JSON.stringify(v))
      if (v) {
        // this.formData1 = JSON.parse(JSON.stringify(this.detailsDialogData))
        // this.getProcessInformation()
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
      }
    },
    detailsDialogData: {
      handler(v) {
        this.formData1 = JSON.parse(JSON.stringify(v))
        if (this.formData1.deadlineTime) {
          this.deadlineTimeCopy = JSON.parse(JSON.stringify(this.formData1.deadlineTime))
        }
        if (this.formData1.eventId) {
          this.getProcessInformation()
        }
        this.formData1.eventTag = this.formData1.eventTagId === null || this.formData1.eventTagId === undefined ? '' : JSON.parse(JSON.stringify(this.formData1.eventTagId))
      }
    }
  },
  mounted() {
    this.getEventTagList()
  },
  methods: {
    // 事件标签的修改
    eventTagChange() {
      let eventTag = ''
      if (this.formData1.eventTag) {
        if (this.formData1.eventTag.length === 1) {
          eventTag = this.formData1.eventTag[0]
        } else if (this.formData1.eventTag.length === 2) {
          eventTag = this.formData1.eventTag[1]
        } else {
          eventTag = this.formData1.eventTag[2]
        }
      }
      let param = {
        eventTagId: eventTag,
        eventId: this.formData1.eventId
      }
      axios.post('/binhuapis/event/updateEventTagId', qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          this.eventTagChangeShow = false
          this.$notify({
            title: '成功',
            message: '修改事件标签成功',
            type: 'success',
            duration: 1000
          })
          this.isChange = true
        } else {
          this.$notify({
            title: '警告',
            message: '获取事件标签失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 获取事件标签
    getEventTagList() {
      axios.get('/binhuapis/event/getEventTagTree').then((res) => {
        if (res.data.code === 200) {
          this.eventTagList = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取事件标签失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 修改处置截止时间
    deadlineTimeChange() {
      let param = {
        deadlineTime: this.deadlineTimeCopy,
        eventId: this.formData1.eventId
      }
      axios.post('/binhuapis/event/updateDeadlineTime', qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          this.deadlineTimeShow = false
          this.formData1.deadlineTime = this.deadlineTimeCopy
          this.$notify({
            title: '成功',
            message: '修改处置截止时间成功',
            type: 'success',
            duration: 1000
          })
          this.isChange = true
        } else {
          this.$notify({
            title: '警告',
            message: '获取截止时间失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    handleDetail(val) {
      this.dealNodeName = this.ProcessInformationList[val].dealNodeName
      this.stepsActive = val
      this.formData2 = this.ProcessInformationList[this.stepsActive]
    },
    // stepCilck(index) {
    //   this.stepsActive = index
    //   this.formData2 = this.ProcessInformationList[this.stepsActive]
    // },
    getProcessInformation() {
      axios.get('/binhuapis/event/queryEventFlowByEventId?eventId=' + this.formData1.eventId).then((res) => {
        if (res.data.code === 200) {
          if (res.data.data !== []) {
            this.ProcessInformationList = res.data.data
            if (this.ProcessInformationList.length !== 0) {
              this.ProcessInformationListLength = this.ProcessInformationList.length
              this.formData2 = JSON.parse(JSON.stringify(this.ProcessInformationList[this.ProcessInformationList.length - 1]))
              this.stepsActive = this.ProcessInformationList.length - 1
              this.dealNodeName = this.ProcessInformationList[this.ProcessInformationList.length - 1].dealNodeName
            }
          }
        } else {
          this.$notify({
            title: '警告',
            message: '查询失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 确定按钮
    handleOk() { },
    handleClose() {
      this.formData1 = {
        taskId: '', // 事件编号
        recordId: '', // 案卷编号
        eventName: '', // 事件名称
        address: '', // 事件位置描述
        happenTime: '', // 事件发生时间
        damageDegree: '', // 事件破坏程度
        topicName: '', // 主题名称
        desc: '', // 事件详细描述
        eventState: '', // 事件状态
        overdueState: '', // 超期状态
        eventTag: '', // 事件标签
        eventType: '', // 事件类型
        level: '', // 事件级别
        reportSource: '', // 事件上报来源
        channel: '', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: '', // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '' // 上报人电话
      }
      this.formData2 = {
        distributePersonName: '', // 下派姓名
        distributePhone: '', // 下派电话
        distributeTime: '', // 下派时间
        distributeInfo: '', // 下派处置意见
        dealStartTime: '', // 处置开始时间
        dealEndTime: '', // 处置截止时间
        dealDeptName: '', // 处置部门名称
        dealPersonName: '', // 处置人姓名
        dealPersonPhone: '', // 处置人电话
        dealInfo: '' // 处置反馈
      }
      // 刷新
      if (this.isChange) {
        this.$emit('refresh')
      }
      this.isChange = false
      this.$emit('close')
      this.$emit('update:detailsDialogData', {})
      this.$emit('update:detailsDialog', false)
    }
  }
}
</script>
<style lang="scss" scoped>
.eventDetailsDialog {
  .files {
    margin-left: 40px;
  }
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 700px;
    display: flex;

    .leftCard {
      width: 14%;
      height: 100%;
      overflow: auto;
      overflow-x: hidden;
      .el-card__body {
        padding: 0;
      }
      // .steps {
      //   height: 700px;
      // }
      .tab {
        // height: 620px;
        display: flex;
        flex-direction: column;

        // position: relative;
        // top: -50px;
        .item {
          cursor: pointer;
          width: 100%;
          // flex-grow: 1;
          text-align: center;
          position: relative;
          height: 100px;
          // &:nth-of-type(1) {
          //   flex-grow: 0;
          // }
          &:last-child {
            flex-grow: 0;
          }
          .top {
            width: 34px;
            height: 34px;
            background: #839bb2;
            border-radius: 50%;
            margin: auto;
            font-size: 18px;
            font-family: Microsoft YaHei;
            font-weight: bold;
            color: #ffffff;
            text-align: center;
            line-height: 34px;
            // margin-bottom: 12px;
            z-index: 9;
            position: absolute;
            top: 0;
            left: calc(20% - 17px);
            cursor: pointer;
          }
          .name {
            padding: 0 10px;
            border-radius: 25px;
            margin-left: 5px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #414c5c;
            position: absolute;
            // top: 35%;
            top: 0;
            line-height: 34px;
            left: calc(50% - 20px);
          }
          .time {
            position: absolute;
            left: calc(50% - 40px);
            top: 35px;
            font-size: 1px;
          }
          .line {
            width: 2px;
            height: 100%;
            background: #0093ff;
            position: absolute;
            top: 0px;
            left: calc(20%);
            z-index: 2;
          }
          .isActived {
            background: #0093ff;
            // color: #0093ff;
          }
          .itemActive {
            background: #8ebcde;
          }
        }
        // .item &:nth-of-type(1) {
        //   .line {
        //     width: 2px;
        //     height: 100%;
        //     background: #ffffff;
        //     position: absolute;
        //     top: 0px;
        //     left: calc(20%);
        //     z-index: 2;
        //   }
        // }
      }
    }
    .rightCard {
      overflow: auto;
      overflow-x: hidden;
      width: 96%;
      height: 100%;
      margin-left: 10px;
      .stateBorder {
        display: inline-block;
        width: 220px;
        height: 40px;
        border-radius: 5px;
        border: 1px solid #dcdfe6;
        padding-left: 10px;
      }
    }
  }
}
</style>
