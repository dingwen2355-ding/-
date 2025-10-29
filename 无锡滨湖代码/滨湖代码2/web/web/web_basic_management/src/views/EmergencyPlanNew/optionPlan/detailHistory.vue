<template>
  <div v-if="dialogVisible">
    <el-dialog
      title="历史预案详情"
      :visible.sync="dialogVisible"
      width="800px"
      :modal="false"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item title="● 基本信息" name="1">
          <div class="info_body" v-loading="loading">
            <el-form
              ref="formLabel"
              :rules="rules"
              :model="form"
              style="margin-right: 35px"
              label-width="110px"
              :disabled="isCode === 1"
            >
              <el-form-item label="名称" prop="name">
                <el-input clearable v-model="form.name" placeholder="请输入预案名称" disabled></el-input>
              </el-form-item>
              <el-form-item label="级别" prop="levelId" v-if="addShow">
                <el-select
                  filterable
                  clearable
                  disabled
                  v-model="form.levelId"
                  placeholder="请选择预案级别"
                  style="width: 100%"
                  @change="changeLevel"
                >
                  <el-option
                    v-for="item in manageLevelList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                label="所属辖区"
                prop="regionId"
                v-show="addShow"
                v-if="isShowRegion && form.levelId==='988'"
              >
                <el-select
                  v-model="form.regionId"
                  placeholder="所在辖区"
                  disabled
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
              <el-form-item
                label="所属辖区"
                prop="regionId"
                v-show="addShow"
                v-if="isShowRegion&& form.levelId==='989'|| form.levelId==='990'"
              >
                <el-cascader
                  style="width: 100%"
                  filterable
                  disabled
                  v-model="form.regionId"
                  :options="districtList"
                  :props="districtProps"
                  placeholder="请选择所属辖区"
                  clearable
                  collapse-tags
                  @change="change"
                  :show-all-levels="false"
                ></el-cascader>
              </el-form-item>
              <el-form-item label="发布单位分类" prop="unitTypeId" v-if="addShow">
                <el-select
                  filterable
                  clearable
                  disabled
                  v-model="form.unitTypeId"
                  placeholder="请选择发布单位分类"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in unitTypeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
              <div
                v-if="addShow && form.levelId==987 || form.levelId== 988"
                style="display: flex; align-items: center;"
              >
                <el-form-item label="发布单位名称" prop="unitId" style="width: 100%">
                  <el-select
                    filterable
                    clearable
                    disabled
                    v-model="form.unitId"
                    placeholder="请选择发布单位分类"
                    style="width: 100%"
                  >
                    <el-option
                      v-for="item in unitNameList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <!--                <el-button disabled type="primary" disabled size="medium" @click="showTag" icon="el-icon-edit"-->
                <!--                           style="margin: 0 0 25px 15px;"></el-button>-->
              </div>
              <el-form-item label="发布单位名称" prop="unitName" v-else>
                <el-input clearable v-model="form.unitName" disabled placeholder="请输入发布单位名称"></el-input>
              </el-form-item>
              <el-form-item label="应对灾种" prop="emergencyTypeId" v-if="addShow">
                <el-cascader
                  style="width: 100%"
                  filterable
                  disabled
                  v-model="form.emergencyTypeId"
                  :options="options"
                  :props="props"
                  placeholder="请选择应对灾种"
                  clearable
                  collapse-tags
                  @change="change"
                  :show-all-levels="false"
                ></el-cascader>
              </el-form-item>
              <el-form-item label="预案分类" prop="eventTypeId" v-if="addShow">
                <el-select
                  filterable
                  clearable
                  v-model="form.eventTypeId"
                  placeholder="请选择预案分类"
                  disabled
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in eventTypeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
              <!-- <el-form-item label="发布时间">
                <el-date-picker
                  v-model="form.time"
                  type="date"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>-->
              <el-form-item label="上传文档" prop="fileUrl">
                <el-upload
                  style="width: 100%"
                  ref="upload"
                  action="/"
                  :multiple="false"
                  :file-list="fileList"
                  :show-file-list="true"
                  :on-change="handleChange"
                  :on-remove="handleRemove"
                  :on-preview="handlePreview"
                  :before-remove="beforeRemove"
                  :auto-upload="false"
                  accept=".docx, .pdf, .PDF"
                  disabled
                >
                  <el-button slot="trigger" size="small" type="primary" disabled>上传文档</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传docx、pdf文件，且不超过50MB</div>
                </el-upload>
              </el-form-item>
            </el-form>
            <!--            <div slot="footer">-->
            <!--              <el-button type="primary" @click="handleOk" style="margin-left: 680px">保存</el-button>-->
            <!--            </div>-->
          </div>
        </el-collapse-item>
        <el-collapse-item title="● 预案结构化" name="2">
          <div class="right">
            <!-- <emergency-plan-tags showName="true" :planName="planName" :showRightContent="showRightContent"> -->
            <!--            <div class="right_title">{{ planName }}</div>-->
            <div class="tab">
              <div class="item" v-for="item in tabList" :key="item.id">
                <div class="line" :class="{ isActived: item.id === isActived }"></div>
                <div
                  class="top"
                  :class="{ isActived: item.id === isActived }"
                  @click="handleDetail(item)"
                >{{ item.id + 1 }}</div>
                <div class="name">{{ item.name }}</div>
              </div>
            </div>
            <div slot="prepare" style="height: 100%" v-if="isActived === 0">
              <!--              <div style="text-align: right; height: 47px; width: 100%">-->
              <!--                <el-button type="success" @click="saveGroups()" style="margin-right: 10px">保存</el-button>-->
              <!--                <el-button type="primary" icon="el-icon-plus" @click="addGroupItem()" style="margin-bottom: 10px">-->
              <!--                  添加分组-->
              <!--                </el-button>-->
              <!--              </div>-->
              <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
                <div
                  style="width: 100%; margin-top: 10px; margin-left: 10px"
                  v-for="item in groupData"
                  :key="item.id"
                >
                  <el-form :inline="true" :model="item" ref="form">
                    <el-row class="header">
                      <el-row>
                        <el-col>
                          <el-form-item label="分组" placeholder="请输入分组">
                            <el-input
                              v-model="item.groupName"
                              :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                            ></el-input>
                            <i
                              class="el-icon-delete"
                              style="color: red; font-size: 20px"
                              @click="delGroup(item.id, item)"
                            ></i>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col>
                          <el-form-item label="描述" placeholder="请输入描述信息">
                            <!--<el-input
                              type="textarea"
                              :rows="6"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                              v-model="item.remark"
                            ></el-input>-->
                            <auto-editor
                              v-model="item.remark"
                              :disabled="false"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                            ></auto-editor>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <!-- <el-form-item style="float: right; margin-right: 20px">
                        <el-button type="danger" plain @click="delGroup(item.id, item)">删除</el-button>
                      </el-form-item>-->
                    </el-row>
                  </el-form>
                </div>
              </div>
            </div>
            <div slot="monitoringWarning" style="height: 100%" v-if="isActived === 1">
              <!--              <div style="text-align: right; height: 47px; width: 100%">-->
              <!--                <el-button type="success" @click="saveWarningGroups()" style="margin-right: 10px">保存</el-button>-->
              <!--                <el-button type="primary" icon="el-icon-plus" @click="addWarningGroupItem()"-->
              <!--                           style="margin-bottom: 10px">添加分组-->
              <!--                </el-button>-->
              <!--              </div>-->
              <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
                <div
                  style="width: 100%; margin-top: 10px; margin-left: 10px"
                  v-for="item in groupWarningData"
                  :key="item.id"
                >
                  <el-form :inline="true" :model="item" ref="form">
                    <el-row class="header">
                      <el-row>
                        <el-col>
                          <el-form-item label="分组" placeholder="请输入分组">
                            <el-input
                              v-model="item.groupName"
                              :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                            ></el-input>
                            <i
                              class="el-icon-delete"
                              style="color: red; font-size: 20px"
                              @click="delWarningGroup(item.id, item)"
                            ></i>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col>
                          <el-form-item label="描述" placeholder="请输入描述信息">
                            <!--<el-input
                              type="textarea"
                              :rows="6"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                              v-model="item.remark"
                            ></el-input>-->
                            <auto-editor
                              :disabled="false"
                              v-model="item.remark"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                            ></auto-editor>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <!-- <el-form-item style="float: right; margin-right: 20px">
                        <el-button type="danger" plain @click="delWarningGroup(item.id, item)">删除</el-button>
                      </el-form-item>-->
                    </el-row>
                  </el-form>
                </div>
              </div>
            </div>
            <div slot="emergencyResponse" style="height: 100%" v-if="isActived === 2">
              <!--              <div style="text-align: right; height: 47px; width: 100%">-->
              <!--                <el-button type="success" @click="saveResponseGroups()" style="margin-right: 10px">保存</el-button>-->
              <!--                <el-button type="primary" icon="el-icon-plus" @click="addResponseGroupItem()"-->
              <!--                           style="margin-bottom: 10px">添加分组-->
              <!--                </el-button>-->
              <!--              </div>-->
              <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
                <div
                  style="width: 100%; margin-top: 10px; margin-left: 10px"
                  v-for="item in groupResponseData"
                  :key="item.id"
                >
                  <el-form :inline="true" :model="item" ref="form">
                    <el-row class="header">
                      <el-row>
                        <el-col>
                          <el-form-item label="分组" placeholder="请输入分组">
                            <el-input
                              v-model="item.groupName"
                              :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                            ></el-input>
                            <i
                              class="el-icon-delete"
                              style="color: red; font-size: 20px"
                              @click="delResponseGroup(item.id, item)"
                            ></i>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col>
                          <el-form-item label="描述" placeholder="请输入描述信息">
                            <!--<el-input
                              type="textarea"
                              :rows="6"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                              v-model="item.remark"
                            ></el-input>-->
                            <auto-editor
                              :disabled="false"
                              v-model="item.remark"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                            ></auto-editor>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <!-- <el-form-item style="float: right; margin-right: 20px">
                        <el-button type="danger" plain @click="delResponseGroup(item.id, item)">删除</el-button>
                      </el-form-item>-->
                    </el-row>
                  </el-form>
                </div>
              </div>
            </div>
            <div slot="emergencySafe" style="height: 100%" v-if="isActived === 3">
              <!--              <div style="text-align: right; height: 47px; width: 100%">-->
              <!--                <el-button type="success" @click="saveSafeGroups()" style="margin-right: 10px">保存</el-button>-->
              <!--                <el-button type="primary" icon="el-icon-plus" @click="addSafeGroupItem()" style="margin-bottom: 10px">-->
              <!--                  添加分组-->
              <!--                </el-button>-->
              <!--              </div>-->
              <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
                <div
                  style="width: 100%; margin-top: 10px; margin-left: 10px"
                  v-for="item in groupSafeData"
                  :key="item.id"
                >
                  <el-form :inline="true" :model="item" ref="form">
                    <el-row class="header">
                      <el-row>
                        <el-col>
                          <el-form-item label="分组" placeholder="请输入分组">
                            <el-input
                              v-model="item.groupName"
                              :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                            ></el-input>
                            <i
                              class="el-icon-delete"
                              style="color: red; font-size: 20px"
                              @click="delSafeGroup(item.id, item)"
                            ></i>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col>
                          <el-form-item label="描述" placeholder="请输入描述信息">
                            <!--<el-input
                              type="textarea"
                              :rows="6"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                              v-model="item.remark"
                            ></el-input>-->
                            <auto-editor
                              v-model="item.remark"
                              :disabled="false"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                            ></auto-editor>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <!-- <el-form-item style="float: right; margin-right: 20px">
                        <el-button type="danger" plain @click="delSafeGroup(item.id, item)">删除</el-button>
                      </el-form-item>-->
                    </el-row>
                  </el-form>
                </div>
              </div>
            </div>
            <div slot="stopEmergencyResponse" style="height: 100%" v-if="isActived === 4">
              <!--              <div style="text-align: right; height: 47px; width: 100%">-->
              <!--                <el-button type="success" @click="saveResponseStopGroups()" style="margin-right: 10px">保存</el-button>-->
              <!--                <el-button-->
              <!--                  type="primary"-->
              <!--                  icon="el-icon-plus"-->
              <!--                  @click="addResponseStopGroupItem()"-->
              <!--                  style="margin-bottom: 10px"-->
              <!--                >添加分组-->
              <!--                </el-button>-->
              <!--              </div>-->
              <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
                <div
                  style="width: 100%; margin-top: 10px; margin-left: 10px"
                  v-for="item in groupResponseStopData"
                  :key="item.id"
                >
                  <el-form :inline="true" :model="item" ref="form">
                    <el-row class="header">
                      <el-row>
                        <el-col>
                          <el-form-item label="分组" placeholder="请输入分组">
                            <el-input
                              v-model="item.groupName"
                              :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                            ></el-input>
                            <i
                              class="el-icon-delete"
                              style="color: red; font-size: 20px"
                              @click="delResponseStopGroup(item.id, item)"
                            ></i>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col>
                          <el-form-item label="描述" placeholder="请输入描述信息">
                            <!--<el-input
                              type="textarea"
                              :rows="6"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                              v-model="item.remark"
                            ></el-input>-->
                            <AutoEditor
                              v-model="item.remark"
                              :disabled="false"
                              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                            ></AutoEditor>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <!-- <el-form-item style="float: right; margin-right: 20px">
                        <el-button type="danger" plain @click="delResponseStopGroup(item.id, item)">删除</el-button>
                      </el-form-item>-->
                    </el-row>
                  </el-form>
                </div>
              </div>
            </div>
            <div slot="rewardsDuty" style="height: 70%" v-if="isActived === 5">
              <!--              <div style="text-align: right; height: 47px; width: 100%">-->
              <!--                <el-button type="success" @click="saveRewardsDuty()">保存</el-button>-->
              <!--              </div>-->
              <div class="info_body" style="margin-top: 20px; height: 100%; width: 100%">
                <el-form :model="planRewardsDutyForm" ref="form" :disabled="planId == ''">
                  <el-form-item label="描述">
                    <auto-editor
                      :disabled="false"
                      v-model="planRewardsDutyForm.rewardsDuty"
                      :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                    ></auto-editor>
                  </el-form-item>
                </el-form>
              </div>
            </div>
            <div slot="about" v-if="isActived === 6">
              <div style="text-align: right; height: 47px; width: 100%">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  @click="addAboutDoc()"
                  style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
                >新增</el-button>

                <div style="height: 565px; overflow: scroll">
                  <el-table
                    v-loading="aboutLoading"
                    class="tableInfo"
                    border
                    :data="aboutDocData"
                    style="width: 100%"
                  >
                    <el-table-column prop="fileName" label="名称"></el-table-column>
                    <el-table-column prop="remark" label="备注"></el-table-column>
                    <el-table-column prop="createTime" label="上传时间"></el-table-column>
                    <el-table-column fixed="right" label="操作" width="180">
                      <template slot-scope="scope">
                        <el-button @click="aboutDocView(scope.row)" type="text" size="medium">详情</el-button>
                        <el-button @click="deleteAboutDoc(scope.row)" type="text" size="medium">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
            </div>
            <!--           </emergency-plan-tags> -->
          </div>
        </el-collapse-item>
      </el-collapse>
      <!--      <div slot="footer" class="dialog-footer">-->
      <!--        <el-button @click="handleClose()">取 消</el-button>-->
      <!--        <el-button type="primary" @click="handleConfirm" v-if="isCode !== 1">确 定</el-button>-->
      <!--      </div>-->
    </el-dialog>
    <el-dialog
      :title="  '单位管理'"
      :visible.sync="tagDialogVisibles"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose1"
    >
      <el-tag
        :key="tag.id"
        v-for="tag in dynamicTags"
        closable
        :disable-transitions="false"
        @close="handleCloses(tag)"
      >{{ tag.name }}</el-tag>
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="$event.target.blur"
        @blur="handleInputConfirm"
      ></el-input>
      <el-button v-else class="button-new-tag" size="small" @click="showInput">点击新增</el-button>
    </el-dialog>
    <plan-view-dialog
      :dialogVisible.sync="showView"
      :isCode="isCode"
      :planId="planId"
      @cancel="afterCancel"
    ></plan-view-dialog>
  </div>
</template>
<script>
import Axios from '@/utils/request'
import znvTime from '../../../utils/znvTime'
import AutoEditor from '../../../components/AutoEditor/AutoEditor.vue'
import planViewDialog from '../../EmergencyPlan/emergencyPlanOption/planViewDialog.vue'

export default {
  name: 'detailHistory',
  components: { planViewDialog, AutoEditor },
  props: ['dialogVisible', 'formInfo', 'isCode', 'addShow', 'rowId'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.fileList = []
      let obj = {}
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      // console.log('this.formInfo', this.formInfo)
      // console.log('this.form', this.form)
      // if (this.formInfo.emergencyTypeId) {
      //   this.form.emergencyTypeId = this.form.emergencyTypeId.split(',')
      // }
      if (this.form.levelId === '988') {
        this.form.regionId = Number(this.form.regionId)
      }
      if (this.form.unitId) {
        this.form.unitId = Number(this.form.unitId)
      }
      // console.log(this.formInfo)
      // this.form.emergencyTypeId = ['19', '18', '20', '25', '902']
      if (this.formInfo.levelId === 988 || this.formInfo.levelId === 989 || this.formInfo.levelId === 990) {
        // console.log("需要所属区县的：", val)
        this.isShowRegion = true
      } else {
        // console.log("选中的级别：", val)
        this.isShowRegion = false
      }
      if (this.formInfo.fileData) {
        // console.log('fileData', this.formInfo.fileData)
        for (let item of this.formInfo.fileData) {
          // console.log('文档 item: ', item)
          obj = {
            url: item.fileUrl,
            raw: new File([item], item.fileName),
            name: item.fileName
          }
          this.fileList.push(obj)
        }
      }
      // if (this.formInfo.fileUrls) {
      //   console.log('flieUrl', this.formInfo.fileUrls)
      //   for (let item of this.formInfo.fileUrls) {
      //     console.log('文档路径item: ', item)
      //     flieUrl = item.split('/')[item.split('/').length - 1].split('-')[1]
      //     console.log('flieUrl', flieUrl)
      //     obj = {
      //       url: item,
      //       raw: new File([item], flieUrl),
      //       name: flieUrl
      //     }
      //     this.fileList.push(obj)
      //   }
      // }
      if (v) {
        if (this.isCode === 0) {
          this.planId = this.rowId
          // this.queryTableList(this.rowId)
          // this.queryPlanGroupList(this.rowId)
          this.handleClick(this.formInfo)
        }
        if (this.isCode === 10) {
          this.planId = this.rowId
          // this.queryTableList(this.rowId)
          // this.queryPlanGroupList(this.rowId)
          this.handleClick(this.formInfo)
        }
      }
    },
    watch: {
      '$store.getters.isCollapse': {
        handler(v) {
          this.isCollapse = v
        },
        immediate: true
      }
    }
  },
  data() {
    return {
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        levelId: [{ required: true, message: '请选择级别', trigger: 'blur' }],
        eventTypeId: [{ required: true, message: '请选择类型', trigger: 'blur' }],
        regionId: [{ required: true, message: '请选择所属辖区', trigger: 'blur' }],
        emergencyTypeId: [{ required: true, message: '请选择分类', trigger: 'blur' }],
        unitTypeId: [{ required: true, message: '请选择发布单位分类', trigger: 'blur' }],
        unitName: [{ required: true, message: '请输入发布单位名称', trigger: 'blur' }],
        unitId: [{ required: true, message: '请选择发布单位名称', trigger: 'blur' }]
      },
      planId: '',
      showView: false,
      loading: false,
      loadingHistory: false,
      tableData: [],
      activeName: ['1'],
      planName: '',
      planRewardsDutyForm: { rewardsDuty: '' },
      searchform: {
        planName: null
      },
      tabList: [
        {
          id: 0,
          name: '前期准备'
        },
        {
          id: 1,
          name: '监测预警'
        },
        {
          id: 2,
          name: '应急响应'
        },
        {
          id: 3,
          name: '应急保障'
        },
        {
          id: 4,
          name: '应急响应终止'
        },
        {
          id: 5,
          name: '奖励与责任追究'
        }
        // {
        //   id: 6,
        //   name: '相关资料'
        // }
      ],
      isActived: 0,
      aboutLoading: false,
      aboutDocData: [],
      groupData: [],
      isCollapse: false,
      groupSafeData: [],
      groupResponseStopData: [],
      groupWarningData: [],
      manageLevelList: [], // 管理级别
      eventTypeList: [],
      areaList: [],
      groupResponseData: [],
      options: [],
      props: {
        multiple: true,
        emitPath: false
      },
      id: 0,
      name: '',
      form: {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        regionId: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'), // 上传时间
        fileUrls: [], // 上传文档
        unitTypeId: '',
        unitId: '',
        unitName: ''
      },
      unitTypeList: [], // 发布单位分类
      newFormD: {},
      fileList: [],
      isShowRegion: false,
      districtList: [],
      districtProps: { emitPath: false },
      tagDialogVisibles: false,
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      dialogVisibles: false
    }
  },
  mounted() {
    this.getManageLevel() // 管理级别
    this.queryPlanType()
    this.queryEventType()
    this.queryUnitType()
    this.queryDistrictList()
    this.getAreaList()
    this.initTag()
  },
  methods: {
    afterCancel() {
      this.page = 1
      // this.queryTableList()
    },
    // 详情
    handleView(row) {
      // console.log('详情row: ', row)
      this.showView = true
      this.planId = row.id

      // if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
      //   row.emergencyTypeId = row.emergencyTypeId.split(',')
      // }
      // if (row.levelId) {
      //   row.levelId = Number(row.levelId)
      // }
      // this.formInfo = row
    },
    // queryTableList(id) {
    //   this.loadingHistory = true
    //   let url = `/binhuapis/get-history?id=${id}`
    //   Axios.get(url).then((res) => {
    //     this.tableData = res.data.data
    //     this.loadingHistory = false
    //   })
    // },
    // 新增相关资料
    addAboutDoc() {
      if (this.planId) {
        this.isPlanAboutCode = 2
        this.resetAboutDoc()
        this.showPlanAboutAdd = true
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 查看资料详情
    aboutDocView(row) {
      // console.log('查看资料详情: ', row)
      this.isPlanAboutCode = 1
      this.formPlanAboutInfo = row
      this.showPlanAboutAdd = true
    },
    // 删除资料详情
    deleteAboutDoc(row) {
      // console.log('删除相关资料: ', row)
      let params = { delIds: [] }
      params.delIds.push(row.id)
      // console.log('删除分组参数:', params)
      let url = '/binhuapis/save-plan-data'
      Axios.post(url, params)
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.queryMyPlanList()
            this.queryPlanGroupList(this.planId)
            // this.planId = row.id
          } else {
            this.$message.error('删除失败')
          }
        })
        .catch(() => {
          this.$message.error('删除失败')
        })
    },
    // 保存奖励与责任追究
    saveRewardsDuty() {
      if (this.planId) {
        let params = {
          id: this.planId,
          rewardDuty: this.planRewardsDutyForm.rewardsDuty
        }
        let url = '/binhuapis/update-plan-reward-duty'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    delResponseStopGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupResponseStopData.pop()
          // console.log('this.groupResponseStopData', this.groupResponseStopData)
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    delResponseGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupResponseData.pop()
          // console.log('this.groupResponseData', this.groupResponseData)
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    addResponseGroupItem() {
      if (this.planId) {
        this.groupResponseData.push({
          groupName: '',
          remark: '',
          groupType: '3'
        })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    addResponseStopGroupItem() {
      if (this.planId) {
        this.groupResponseStopData.push({
          groupName: '',
          remark: '',
          groupType: '5'
        })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 应急响应终止
    saveResponseStopGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupResponseStopData) {
          if (item.id === undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/binhuapis/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 应急响应
    saveResponseGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupResponseData) {
          if (item.id === undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/binhuapis/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    delWarningGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupWarningData.pop()
          // console.log('this.groupWarningData', this.groupWarningData)
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 获取预案list
    queryMyPlanList() {
      this.loading = true
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        userId: localStorage.getItem('userId'),
        status: ['0', '3']
      }
      if (this.searchform.planName) {
        params.name = this.searchform.planName
      }
      // console.log('params', params)
      Axios.post(url, params).then((res) => {
        this.planTableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    // 点击表格名称后事件
    handleClick(row) {
      // console.log('5')
      // console.log('点击表格名称后事件', row)
      this.showRightContent = true
      this.planId = row.id
      this.planName = row.name
      // console.log('查看rows2', row)
      this.queryPlanGroupList(row.id)
    },
    handleDetail(val) {
      this.isActived = val.id
    },
    // 前期准备
    // 保存分组
    saveGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupData) {
          if (item.id === undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/binhuapis/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 添加分组
    addGroupItem() {
      if (this.planId) {
        this.groupData.push({
          groupName: '',
          remark: '',
          groupType: '1'
        })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    resetList() {
      this.showRightContent = false
      this.groupData = []
      this.aboutDocData = []
      this.groupWarningData = []
      this.groupResponseData = []
      this.groupSafeData = []
      this.groupResponseStopData = []
    },
    initTag() {
      let url = '/binhuapis/getUnit'
      Axios.get(url).then(res => {
        if (res.data.code === 200) {
          let tData = res.data.data
          // console.log(tData)
          // let tDataShuZu = []
          // for (let i = 0; i < tData.length; i++) {
          //   tDataShuZu.push(tData[i].name)
          //   console.log(tData[i].name)
          // }
          this.dynamicTags = tData
          // this.unitNameList = tDataShuZu
          this.unitNameList = tData
        } else {
          this.$message.error('职务数据查询失败！' + res.data.message)
        }
      })
    },
    // 获取预案分组以及相关资料list
    queryPlanGroupList(id) {
      let url
      if (this.isCode === 0) {
        url = '/binhuapis/query-group-data?id=' + id + '&isHistory=true'
      } else {
        url = '/binhuapis/query-group-data?id=' + id + '&isHistory=false'
      }
      Axios.get(url).then((res) => {
        if (res.data.data !== null) {
          // 前期准备
          this.groupData = res.data.data.planGroupBeans
          // 监测预警
          this.groupWarningData = res.data.data.planMonitorGroups
          // 应急响应
          this.groupResponseData = res.data.data.planResponseGroups
          // 应急保障
          this.groupSafeData = res.data.data.planSupportGroups
          // 应急响应终止
          this.groupResponseStopData = res.data.data.planStopGroups
          // 奖励与责任追究
          this.planRewardsDutyForm.rewardsDuty = res.data.data.rewardDuty
          // 相关资料
          this.aboutDocData = res.data.data.planDataBeans
        } else {
          // 前期准备
          this.groupData = []
          // 监测预警
          this.groupWarningData = []
          // 应急响应
          this.groupResponseData = []
          // 应急保障
          this.groupSafeData = []
          // 应急响应终止
          this.groupResponseStopData = []
          // 奖励与责任追究
          this.planRewardsDutyForm.rewardsDuty = []
          // 相关资料
          this.aboutDocData = []
        }
      })
    },
    // 删除分组
    delGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupData.pop()
          // console.log('this.groupData', this.groupData)
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    addSafeGroupItem() {
      if (this.planId) {
        this.groupSafeData.push({
          groupName: '',
          remark: '',
          groupType: '4'
        })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 应急保障
    saveSafeGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupSafeData) {
          if (item.id === undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/binhuapis/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 监测预警
    saveWarningGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupWarningData) {
          if (item.id === undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/binhuapis/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    addWarningGroupItem() {
      if (this.planId) {
        this.groupWarningData.push({
          groupName: '',
          remark: '',
          groupType: '2'
        })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    showInput() {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    delSafeGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupSafeData.pop()
          // console.log('this.groupSafeData', this.groupSafeData)
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    handleInputConfirm() {
      let inputValue = this.inputValue
      // if (inputValue) {
      //   this.dynamicTags.push(inputValue);
      // }
      let url = '/binhuapis/addUnit?name=' + inputValue
      Axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.$message.success('新增成功！')
          this.initTag()
        } else {
          this.$message.error(res.data.message)
        }
      })
      this.inputVisible = false
      this.inputValue = ''
    },
    handleCloses(tag) {
      this.$confirm('此操作将删除该单位信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        let url = '/binhuapis/delUnit?id=' + tag.id
        Axios.get(url).then(res => {
          if (res.data.code === 200) {
            this.$message.success('删除成功！')
            this.initTag()
          } else {
            this.$message.error(res.data.message)
          }
        })
      })
    },
    showTag() {
      this.tagDialogVisibles = true
    },
    handleClose1() {
      this.tagDialogVisibles = false
    },
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children)
        }
      }
      return data
    },
    // 获取区级数据
    queryDistrictList() {
      let url = '/binhuapis/getRegion'
      Axios.get(url).then((r) => {
        // console.log('分类获取', r.data.data)
        this.districtList = this.getTreeData(r.data.data)
      })
    },
    // 发布单位分类获取
    queryUnitType() {
      let url = '/binhuapis/getUnitType'
      Axios.get(url).then((r) => {
        // console.log('分类获取', r.data.data)
        this.unitTypeList = r.data.data
      })
    },
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/getLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data
      })
    },
    getAreaList() {
      // ?userLevel=${localStorage.level}&userId=${localStorage.userId}
      let url = '/binhuapis/companyDict/queryRegion'
      Axios.get(url).then(res => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    changeLevel(val) {
      this.form.regionId = ''
      if (val === 988 || val === 989 || val === 990) {
        // console.log("需要所属区县的：", val)
        this.isShowRegion = true
      } else {
        // console.log("选中的级别：", val)
        this.isShowRegion = false
      }
    },
    change(value) {
      // console.log(value)
    },
    // 类型获取
    queryPlanType() {
      let url = '/binhuapis/get-type-tree'
      Axios.get(url).then((r) => {
        // console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // 分类获取
    queryEventType() {
      let url = '/binhuapis/get-event-type'
      Axios.get(url).then((r) => {
        // console.log('分类获取', r.data.data)
        this.eventTypeList = r.data.data
      })
    },
    // 保存上传文件
    saveUploadFile() {
      let url = '/binhuapis/save-plan-data'
      let params = {
        addPlanDataBeans: [],
        updatePlanDataBeans: [],
        delIds: []
      }
      let addFiles = {}
      for (let item of this.fileList) {
        addFiles = {
          planId: this.form.id,
          fileUrl: item.url,
          fileName: item.name.substring(0, item.name.lastIndexOf('.')),
          createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
        }
        params.addPlanDataBeans.push(addFiles)
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message({
            type: 'success',
            message: '保存成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: '保存失败'
          })
        }
      })
    },
    reset() {
      this.isShowRegion = false
      this.loading = false
      this.form = {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        regionId: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'), // 上传时间
        fileUrls: [], // 上传文档
        unitTypeId: '',
        unitId: '',
        unitName: ''
      }
      this.activeName = ['1']
      // this.$refs.upload.clearFiles()
    },
    // 取 消
    handleClose() {
      this.reset()
      this.$emit('update:dialogVisible', false)
      // if (this.isCode != 1) {
      //   this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
      //     distinguishCancelAndClose: true,
      //     confirmButtonText: '提交保存',
      //     cancelButtonText: '关闭窗口',
      //     type: 'warning'
      //   })
      //     .then(() => {
      //       this.handleOk()
      //     })
      //     .catch((e) => {
      //       if (e === 'cancel') {
      //         this.reset()
      //         this.$emit('update:dialogVisible', false)
      //       }
      //     })
      // } else {
      //   this.$emit('update:dialogVisible', false)
      // }
    },
    // 确 定
    handleOk() {
      // console.log("form", this.form);
      this.$refs['formLabel'].validate((valid) => {
        if (valid) {
          // console.log('1')
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    handleConfirm() {
      this.loading = true
      // this.$emit('success')
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    submitAll() {
      // this.loading = true
      this.newFormD = JSON.parse(JSON.stringify(this.form))
      // console.log('查看newFormD', this.newFormD)
      // console.log('查看form', this.newFormD)
      // console.log('this.newFormD.emergencyTypeId', this.form.emergencyTypeId)
      if (Array.isArray(this.newFormD.emergencyTypeId)) {
        this.newFormD.emergencyTypeId = this.newFormD.emergencyTypeId.join(',')
      }
      this.newFormD.planDataBeans = []
      let aboutData = {}
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/update-plan'
        for (let item of this.fileList) {
          // console.log('item', item)
          aboutData = {
            planId: this.form.id,
            fileName: item.name.substring(0, item.name.lastIndexOf('.')),
            fileUrl: item.url,
            createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
          }
          this.newFormD.planDataBeans.push(aboutData)
        }
      } else {
        url = '/binhuapis/add-plan'
        for (let item of this.fileList) {
          // console.log('item', item)
          aboutData = {
            fileUrl: item.url,
            fileName: item.name.substring(0, item.name.lastIndexOf('.')),
            createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
          }
          this.newFormD.planDataBeans.push(aboutData)
        }
      }
      if (this.form.levelId === 987 || this.form.levelId === 988) {
        this.newFormD.unitName = this.unitNameList.filter((v) => v.id === this.form.unitId)[0].name
      }
      this.newFormD.precinctId = localStorage.getItem('newDepartmentId')
      // console.log('this.newFormD', this.newFormD)
      Axios.post(url, this.newFormD).then((res) => {
        this.loading = false
        if (res.data.code === 200) {
          if (this.formInfo && this.formInfo.id) {
            this.$message.success('修改基本信息成功！')
          } else {
            this.$message.success('新增基本信息成功！')
          }
          // console.log('2')
          // this.$emit('success')
          // this.reset()
          // this.$emit('update:dialogVisible', false)
          // this.queryId()
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 查询新增得预案得id值
    queryId() {
      // console.log('3')
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: 1,
        pageSize: 10,
        userId: localStorage.userId,
        status: ['0', '3'],
        name: this.form.name
      }
      Axios.post(url, params).then(res => {
        if (res.data.code === 200) {
          // console.log('4')
          this.id = res.data.data.list[0].id
          this.name = res.data.data.list[0].name
          let rows = {}
          rows.id = this.id
          rows.name = this.name
          this.handleClick(rows)
          // console.log('查看id', this.id)
          // console.log('查看rows1', rows)
        }
      })
    },
    // 文件上传相关操作
    handleChange(file, fileList) {
      const types = file.name.split('.')[file.name.split('.').length - 1]
      const fileType = ['docx', 'pdf', 'PDF'].some((item) => item === types)
      if (!fileType) {
        this.$message('格式错误！请重新选择')
      }
      let existFile = fileList.slice(0, fileList.length - 1).find((f) => f.name === file.name)
      if (existFile) {
        this.$message.error('当前文件已经存在!')
        fileList.pop()
      }
      // console.log('fileList', fileList)
      if (file.size / 1024 / 1024 > 50) {
        this.$message({
          message: '上传文件大小不能超过 50MB!',
          type: 'error'
        })
        fileList.pop()
      } else {
        this.loading = true
        for (let item of fileList) {
          if (item.url === undefined) {
            // console.log('fileList-item', item)
            let formData = new FormData()
            formData.append('uploadFile', item.raw)
            // console.log('formData', formData)
            let url = '/binhuapis/resourceAgreement/uploadFile'
            Axios.post(url, formData).then((res) => {
              this.loading = false
              if (res.data.code === 200) {
                this.fileList.push({
                  url: res.data.data,
                  name: file.name
                })
                // console.log('this.fileList', this.fileList)
                this.$message.success('上传成功！')
              } else {
                this.fileList.pop()
                // console.log('this.fileList', this.fileList)
                this.$message.error('上传失败！')
              }
            })
          }
        }
      }
    },
    beforeRemove(file, fileList) {
      // 已上传文件删除前提醒确认
      if (this.isCode !== 1) {
        return this.$confirm('此操作将删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
      } else {
        this.$message.warning('查看时无法删除')
        return false
      }
    },
    handleRemove(file, fileList) {
      // console.log('file', file)
      if (file && file.status === 'success') {
        // console.log('this.fileList', this.fileList)
        let fileUrl = ''
        if (this.fileList && this.fileList.length > 0) {
          this.fileList.forEach((item) => {
            if (item.name === file.name) {
              fileUrl = item.url
            }
          })
          this.loading = true
          let url = '/binhuapis/resourceAgreement/deleteFile?fileUrl=' + fileUrl
          Axios.post(url).then((res) => {
            this.loading = false
            if (res.data.code === 200) {
              this.$message.success('删除成功！')
              this.fileList = fileList
            } else {
              this.$message.error('删除失败！')
              this.fileList = fileList
            }
          })
        }
        // console.log('this.fileList', this.fileList)
      }
    },
    // 点击文件下载
    handlePreview(file) {
      // console.log('file', file)
      let url = file.url
      window.open(url, '_bank')
    }
  }
}
</script>
<style lang="scss" scoped>
.el-upload__tip {
  font-size: 14px;
  color: #5daf34;
  margin-top: 0px;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
  margin-bottom: 20px;
}

.right {
  // width: 70%; // height: 870px; // margin-right: 10px; // margin-top: 10px; // margin-left: 10px; width: 92%; height: 600px; background: #ffffff; padding: 15px; margin-left: 20px;

  .right_title {
    font-size: 18px;
    font-family: Microsoft YaHei, serif;
    font-weight: bold;
    color: #4e5a6e;
    line-height: 80px;
  }

  .tab {
    display: flex;

    .item {
      width: 133px;
      text-align: center;
      position: relative;
      padding-top: 40px;
      margin-bottom: 30px;

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
        margin-bottom: 12px;
        z-index: 9;
        position: absolute;
        top: 0;
        left: calc(50% - 17px);
        cursor: pointer;
      }

      .name {
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #414c5c;
      }

      .line {
        width: 133px;
        height: 2px;
        background: #eff2f5;
        position: absolute;
        top: 16px;
        left: 0;
      }

      .isActived {
        background: #0093ff;
      }
    }
  }
}
</style>
