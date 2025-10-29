<template>
  <div class="planViewDialog" v-if="dialogVisible">
    <el-dialog
      title="预案拆解信息"
      :visible.sync="dialogVisible"
      width="1055px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
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
        <!--        <div slot="prepare" style="height: 100%" v-if="isActived === 0">-->
        <!--          <div style="text-align: right; height: 47px; width: 100%">-->
        <!--            <el-button type="success" @click="saveGroups()" style="margin-right: 10px">保存</el-button>-->
        <!--            <el-button type="primary" icon="el-icon-plus" @click="addGroupItem()" style="margin-bottom: 10px">-->
        <!--              添加分组-->
        <!--            </el-button>-->
        <!--          </div>-->
        <!--          <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
        <!--            <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupData" :key="item.id">-->
        <!--              <el-form :inline="true" :model="item" ref="form">-->
        <!--                <el-row class="header">-->
        <!--                  <el-row>-->
        <!--                    <el-col>-->
        <!--                      <el-form-item label="分组" placeholder="请输入分组">-->
        <!--                        <el-input v-model="item.groupName"-->
        <!--                                  :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
        <!--                        <i class="el-icon-delete" style="color: red; font-size: 20px"-->
        <!--                           @click="delGroup(item.id, item)"></i>-->
        <!--                      </el-form-item>-->
        <!--                    </el-col>-->
        <!--                  </el-row>-->
        <!--                  <el-row>-->
        <!--                    <el-col>-->
        <!--                      <el-form-item label="描述" placeholder="请输入描述信息">-->
        <!--                        &lt;!&ndash;<el-input-->
        <!--                          type="textarea"-->
        <!--                          :rows="6"-->
        <!--                          :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
        <!--                          v-model="item.remark"-->
        <!--                        ></el-input>&ndash;&gt;-->
        <!--                        <auto-editor-->
        <!--                          v-model="item.remark"-->
        <!--                          :disabled="false"-->
        <!--                          :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
        <!--                        ></auto-editor>-->
        <!--                      </el-form-item>-->
        <!--                    </el-col>-->
        <!--                  </el-row>-->
        <!--                  &lt;!&ndash; <el-form-item style="float: right; margin-right: 20px">-->
        <!--                    <el-button type="danger" plain @click="delGroup(item.id, item)">删除</el-button>-->
        <!--                  </el-form-item>&ndash;&gt;-->
        <!--                </el-row>-->
        <!--              </el-form>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--        </div>-->
        <!--        <div slot="monitoringWarning" style="height: 100%" v-if="isActived === 1">-->
        <!--          <div style="text-align: right; height: 47px; width: 100%">-->
        <!--            <el-button type="success" @click="saveWarningGroups()" style="margin-right: 10px">保存</el-button>-->
        <!--            <el-button type="primary" icon="el-icon-plus" @click="addWarningGroupItem()"-->
        <!--                       style="margin-bottom: 10px">添加分组-->
        <!--            </el-button>-->
        <!--          </div>-->
        <!--          <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
        <!--            <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupWarningData"-->
        <!--                 :key="item.id">-->
        <!--              <el-form :inline="true" :model="item" ref="form">-->
        <!--                <el-row class="header">-->
        <!--                  <el-row>-->
        <!--                    <el-col>-->
        <!--                      <el-form-item label="分组" placeholder="请输入分组">-->
        <!--                        <el-input v-model="item.groupName"-->
        <!--                                  :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
        <!--                        <i-->
        <!--                          class="el-icon-delete"-->
        <!--                          style="color: red; font-size: 20px"-->
        <!--                          @click="delWarningGroup(item.id, item)"-->
        <!--                        ></i>-->
        <!--                      </el-form-item>-->
        <!--                    </el-col>-->
        <!--                  </el-row>-->
        <!--                  <el-row>-->
        <!--                    <el-col>-->
        <!--                      <el-form-item label="描述" placeholder="请输入描述信息">-->
        <!--                        &lt;!&ndash;<el-input-->
        <!--                          type="textarea"-->
        <!--                          :rows="6"-->
        <!--                          :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
        <!--                          v-model="item.remark"-->
        <!--                        ></el-input>&ndash;&gt;-->
        <!--                        <auto-editor-->
        <!--                          :disabled="false"-->
        <!--                          v-model="item.remark"-->
        <!--                          :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
        <!--                        ></auto-editor>-->
        <!--                      </el-form-item>-->
        <!--                    </el-col>-->
        <!--                  </el-row>-->
        <!--                  &lt;!&ndash; <el-form-item style="float: right; margin-right: 20px">-->
        <!--                    <el-button type="danger" plain @click="delWarningGroup(item.id, item)">删除</el-button>-->
        <!--                  </el-form-item>&ndash;&gt;-->
        <!--                </el-row>-->
        <!--              </el-form>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--        </div>-->
        <div slot="emergencyResponse" style="height: 100%" v-if="isActived === 0">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button type="success" @click="saveResponseGroups()" style="margin-right: 10px">保存</el-button>
            <el-button
              type="primary"
              icon="el-icon-plus"
              @click="addResponseGroupItem()"
              style="margin-bottom: 10px"
            >添加分组</el-button>
          </div>
          <div style="height: 650px; width: 100%; overflow-x: hidden">
            <div
              style="width: 100%; margin-top: 10px; margin-left: 10px"
              v-for="item in groupResponseData"
              :key="item.id"
            >
              <el-form :inline="true" :model="item" ref="form">
                <el-row class="header">
                  <el-row>
                    <el-col>
                      <el-form-item label="级别:" placeholder="请选择级别">
                        <el-select
                          v-model="item.levelId"
                          style="width: 220px"
                          placeholder="请选择响应级别"
                          clearable
                        >
                          <el-option
                            v-for="(item,key) in levelTypes"
                            :value="item.value"
                            :label="item.label"
                            :key="key"
                          ></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col>
                      <el-form-item label="启动条件:" placeholder="请输入启动条件">
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
                      <el-form-item label="响应条件:" placeholder="请输入响应条件">
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
        <div slot="emergencySafe" style="height: 100%" v-if="isActived === 1">
          <!--          <el-tabs tab-position="top" style="position: relative">-->
          <!--            <el-tab-pane label="应急专家">应急专家</el-tab-pane>-->
          <!--            <el-tab-pane label="救援队伍">救援队伍</el-tab-pane>-->
          <!--            <el-tab-pane label="物资装备">物资装备</el-tab-pane>-->
          <!--            <el-tab-pane label="避难场所">避难场所</el-tab-pane>-->
          <!--          </el-tabs>-->
          <!--          <div>-->
          <el-button type="success" @click="savePlanSupportGroup()" style="margin-left: 910px">保存</el-button>
          <emergency-support
            :isCode="2"
            ref="emergencySupport"
            :planId="planId"
            :suport-position="position"
            :viewData="viewData"
          />
          <!--          </div>-->
          <!--          <div style="text-align: right; height: 47px; width: 100%">-->
          <!--            <el-button type="success" @click="saveSafeGroups()" style="margin-right: 10px">保存</el-button>-->
          <!--            <el-button type="primary" icon="el-icon-plus" @click="addSafeGroupItem()" style="margin-bottom: 10px">-->
          <!--              添加分组-->
          <!--            </el-button>-->
          <!--          </div>-->
          <!--          <div style="height: 650px; width: 100%; overflow-x: hidden">-->
          <!--            <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupSafeData"-->
          <!--                 :key="item.id">-->
          <!--              <el-form :inline="true" :model="item" ref="form">-->
          <!--                <el-row class="header">-->
          <!--                  <el-row>-->
          <!--                    <el-col>-->
          <!--                      <el-form-item label="分组" placeholder="请输入分组">-->
          <!--                        <el-input v-model="item.groupName"-->
          <!--                                  :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
          <!--                        <i-->
          <!--                          class="el-icon-delete"-->
          <!--                          style="color: red; font-size: 20px"-->
          <!--                          @click="delSafeGroup(item.id, item)"-->
          <!--                        ></i>-->
          <!--                      </el-form-item>-->
          <!--                    </el-col>-->
          <!--                  </el-row>-->
          <!--                  <el-row>-->
          <!--                    <el-col>-->
          <!--                      <el-form-item label="描述" placeholder="请输入描述信息">-->
          <!--                        &lt;!&ndash;<el-input-->
          <!--                          type="textarea"-->
          <!--                          :rows="6"-->
          <!--                          :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
          <!--                          v-model="item.remark"-->
          <!--                        ></el-input>&ndash;&gt;-->
          <!--                        <auto-editor v-model="item.remark" :disabled="false"-->
          <!--                                     :style="isCollapse ? 'width: 1085px' : 'width: 940px'"></auto-editor>-->
          <!--                      </el-form-item>-->
          <!--                    </el-col>-->
          <!--                  </el-row>-->
          <!--                  &lt;!&ndash; <el-form-item style="float: right; margin-right: 20px">-->
          <!--                    <el-button type="danger" plain @click="delSafeGroup(item.id, item)">删除</el-button>-->
          <!--                  </el-form-item>&ndash;&gt;-->
          <!--                </el-row>-->
          <!--              </el-form>-->
          <!--            </div>-->
          <!--          </div>-->
        </div>
        <div slot="stopEmergencyResponse" style="height: 100%" v-if="isActived === 2">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button
              type="success"
              @click="saveResponseStopGroups()"
              style="margin-right: 10px"
            >保存</el-button>
            <el-button
              type="primary"
              icon="el-icon-plus"
              @click="addResponseStopGroupItem()"
              style="margin-bottom: 10px"
            >添加分组</el-button>
          </div>
          <div style="height: 650px; width: 100%; overflow-x: hidden">
            <div
              style="width: 100%; margin-top: 10px; margin-left: 10px"
              v-for="item in groupResponseStopData"
              :key="item.id"
            >
              <el-form :inline="true" :model="item" ref="form">
                <el-row class="header">
                  <el-row>
                    <el-col>
                      <el-form-item label="终止条件:" placeholder="请输入终止条件">
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
                      <el-form-item label="终止程序:" placeholder="请输入终止程序">
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
        <!--        <div slot="rewardsDuty" style="height: 70%" v-if="isActived === 3">-->
        <!--          <div style="text-align: right; height: 47px; width: 100%">-->
        <!--            <el-button type="success" @click="saveRewardsDuty()">保存</el-button>-->
        <!--          </div>-->
        <!--          <div class="info_body" style="margin-top: 20px; height: 100%; width: 100%">-->
        <!--            <el-form :model="planRewardsDutyForm" ref="form" :disabled="planId === ''">-->
        <!--              <el-form-item label="描述">-->
        <!--                <auto-editor-->
        <!--                  :disabled="false"-->
        <!--                  v-model="planRewardsDutyForm.rewardsDuty"-->
        <!--                  :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
        <!--                ></auto-editor>-->
        <!--              </el-form-item>-->
        <!--            </el-form>-->
        <!--          </div>-->
        <!--        </div>-->
        <!--        <div slot="about" v-if="isActived === 6">-->
        <!--          <div style="text-align: right; height: 47px; width: 100%">-->
        <!--            <el-button-->
        <!--              type="primary"-->
        <!--              icon="el-icon-plus"-->
        <!--              @click="addAboutDoc()"-->
        <!--              style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"-->
        <!--            >新增-->
        <!--            </el-button>-->

        <!--            <div style="height: 565px; overflow: scroll">-->
        <!--              <el-table v-loading="aboutLoading" class="tableInfo" border :data="aboutDocData" style="width: 100%">-->
        <!--                <el-table-column prop="fileName" label="名称"></el-table-column>-->
        <!--                <el-table-column prop="remark" label="备注"></el-table-column>-->
        <!--                <el-table-column prop="createTime" label="上传时间"></el-table-column>-->
        <!--                <el-table-column fixed="right" label="操作" width="180">-->
        <!--                  <template slot-scope="scope">-->
        <!--                    <el-button @click="aboutDocView(scope.row)" type="text" size="medium">详情</el-button>-->
        <!--                    <el-button @click="deleteAboutDoc(scope.row)" type="text" size="medium">删除</el-button>-->
        <!--                  </template>-->
        <!--                </el-table-column>-->
        <!--              </el-table>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--        </div>-->
        <!--           </emergency-plan-tags> -->
      </div>
      <!--      <div class="tab">-->
      <!--        <div class="item" v-for="item in tabList" :key="item.id">-->
      <!--          <div class="line" :class="{ isActived: item.id === isActived }"></div>-->
      <!--          <div class="top" :class="{ isActived: item.id === isActived }" @click="handleDetail(item)">{{ item.id + 1 }}</div>-->
      <!--          <div class="name">{{ item.name }}</div>-->
      <!--        </div>-->
      <!--      </div>-->
      <!--      <div style="height: 100%" v-if="isActived === 0">-->
      <!--        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
      <!--          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupData" :key="item.id">-->
      <!--            <el-form :inline="true" :model="item" ref="form">-->
      <!--              <el-row class="header">-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="分组" placeholder="请输入分组">-->
      <!--                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="描述" placeholder="请输入描述信息">-->
      <!--                      <auto-editor-->
      <!--                        v-model="item.remark"-->
      <!--                        :disabled="true"-->
      <!--                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
      <!--                      ></auto-editor>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--              </el-row>-->
      <!--            </el-form>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </div>-->
      <!--      <div style="height: 100%" v-if="isActived === 1">-->
      <!--        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
      <!--          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupWarningData" :key="item.id">-->
      <!--            <el-form :inline="true" :model="item" ref="form">-->
      <!--              <el-row class="header">-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="分组" placeholder="请输入分组">-->
      <!--                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="描述" placeholder="请输入描述信息">-->
      <!--                      <auto-editor-->
      <!--                        :disabled="true"-->
      <!--                        v-model="item.remark"-->
      <!--                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
      <!--                      ></auto-editor>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--              </el-row>-->
      <!--            </el-form>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </div>-->
      <!--      <div style="height: 100%" v-if="isActived === 2">-->
      <!--        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
      <!--          <div-->
      <!--            style="width: 100%; margin-top: 10px; margin-left: 10px"-->
      <!--            v-for="item in groupResponseData"-->
      <!--            :key="item.id"-->
      <!--          >-->
      <!--            <el-form :inline="true" :model="item" ref="form">-->
      <!--              <el-row class="header">-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="分组" placeholder="请输入分组">-->
      <!--                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="描述" placeholder="请输入描述信息">-->
      <!--                      <auto-editor-->
      <!--                        :disabled="true"-->
      <!--                        v-model="item.remark"-->
      <!--                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
      <!--                      ></auto-editor>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--              </el-row>-->
      <!--            </el-form>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </div>-->
      <!--      <div style="height: 100%" v-if="isActived === 3">-->
      <!--        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
      <!--          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupSafeData" :key="item.id">-->
      <!--            <el-form :inline="true" :model="item" ref="form">-->
      <!--              <el-row class="header">-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="分组" placeholder="请输入分组">-->
      <!--                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="描述" placeholder="请输入描述信息">-->
      <!--                      <auto-editor-->
      <!--                        :disabled="true"-->
      <!--                        v-model="item.remark"-->
      <!--                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
      <!--                      ></auto-editor>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--              </el-row>-->
      <!--            </el-form>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </div>-->
      <!--      <div style="height: 100%" v-if="isActived === 4">-->
      <!--        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">-->
      <!--          <div-->
      <!--            style="width: 100%; margin-top: 10px; margin-left: 10px"-->
      <!--            v-for="item in groupResponseStopData"-->
      <!--            :key="item.id"-->
      <!--          >-->
      <!--            <el-form :inline="true" :model="item" ref="form">-->
      <!--              <el-row class="header">-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="分组" placeholder="请输入分组">-->
      <!--                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--                <el-row>-->
      <!--                  <el-col>-->
      <!--                    <el-form-item label="描述" placeholder="请输入描述信息">-->
      <!--                      <auto-editor-->
      <!--                        :disabled="true"-->
      <!--                        v-model="item.remark"-->
      <!--                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
      <!--                      ></auto-editor>-->
      <!--                    </el-form-item>-->
      <!--                  </el-col>-->
      <!--                </el-row>-->
      <!--              </el-row>-->
      <!--            </el-form>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </div>-->
      <!--      <div style="height: 70%" v-if="isActived === 5">-->
      <!--        <div class="info_body" style="margin-top: 20px; height: 100%; width: 100%">-->
      <!--          <el-form :model="planRewardsDutyForm" ref="form" :disabled="planId === ''">-->
      <!--            <el-form-item label="描述">-->
      <!--              <auto-editor-->
      <!--                :disabled="true"-->
      <!--                v-model="planRewardsDutyForm.rewardsDuty"-->
      <!--                :style="isCollapse ? 'width: 1085px' : 'width: 940px'"-->
      <!--              ></auto-editor>-->
      <!--            </el-form-item>-->
      <!--          </el-form>-->
      <!--        </div>-->
      <!--      </div>-->
      <div v-if="isActived === 3">
        <div style="text-align: right; height: 47px; width: 100%">
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
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button @click="handleClose()" type="primary">确 定</el-button>
      </div>
      <add-about-dialog
        :dialogVisible.sync="showPlanAboutAdd"
        :formInfo="formPlanAboutInfo"
        :is-code="isPlanAboutCode"
        :plan-id="planAuditId"
      ></add-about-dialog>
    </el-dialog>
  </div>
</template>
<script>
import Axios from '@/utils/request'
import addAboutDialog from '../../myPlan/components/addAboutDialog.vue'
import AutoEditor from '../../../components/AutoEditor/AutoEditor'
import EmergencySupport from '@/views/PlanResponse/option/module/emergencySupport.vue'
import request from '@/utils/request'
export default {
  name: 'planViewDialog',
  props: ['dialogVisible', 'planId'],
  components: { EmergencySupport, addAboutDialog, AutoEditor },
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.planAuditId = this.planId
      this.queryPlanGroupList(this.planId)
    }
  },
  data() {
    return {
      planAuditId: '',
      showPlanAboutAdd: false,
      formPlanAboutInfo: {},
      isPlanAboutCode: 1, // 1 查看
      tabList: [
        // {
        //   id: 0,
        //   name: '前期准备'
        // },
        // {
        //   id: 1,
        //   name: '监测预警'
        // },
        {
          id: 0,
          name: '应急响应'
        },
        {
          id: 1,
          name: '应急保障'
        },
        {
          id: 2,
          name: '终止条件'
        },
        // {
        //   id: ,
        //   name: '奖励与责任追究'
        // }
        {
          id: 3,
          name: '相关资料'
        }
      ],
      isActived: 0,
      isCollapse: false,
      groupData: [],
      levelId: '',
      groupWarningData: [],
      groupResponseData: [],
      levelTypes: [],
      // groupSafeData: [],
      groupResponseStopData: [],
      planRewardsDutyForm: { rewardsDuty: '' },
      aboutLoading: false,
      aboutDocData: [],
      position: 'top',
      viewData: {
        planId: null,
        planResponseGroups: { // 应急响应
          levelId: '',
          remark: '',
          extendRemark: ''
        },
        planSupportGroups: { // 应急支持
          experts: [],
          shelters: [],
          stuffs: [],
          teams: []
        },
        planStopGroups: { // 终止条件
          remark: '',
          extendRemark: ''
        },
        planDataBeans: [] // 预案资料
      }
    }
  },
  mounted() {
    this.getLevelType()
  },
  methods: {
    handleDetail(val) {
      // console.log('handleDetail', val)
      this.isActived = val.id
    },
    // 前期准备
    // 保存分组
    // saveGroups() {
    //   if (this.planId) {
    //     let params = { planDataBeans: [], planStopGroups: [], planSupportGroups: {}, planResponseGroups: [], planId: this.planId }
    //     for (let item of this.groupData) {
    //       if (item.id === undefined) {
    //         // console.log('保存分组:', item)
    //         item.planId = this.planId
    //         params.addPlanGroupBeans.push(item)
    //       } else {
    //         // console.log('保存分组:', item)
    //         item.planId = this.planId
    //         params.updatePlanGroupBeans.push(item)
    //       }
    //     }
    //     // console.log('保存分组参数:', params)
    //     let url = '/binhuapis/update-plan-group'
    //     Axios.post(url, params)
    //       .then((res) => {
    //         if (res.data.code === 200) {
    //           this.$message.success('保存成功')
    //           this.queryPlanGroupList(this.planId)
    //         } else {
    //           this.$message.error('保存失败')
    //         }
    //       })
    //       .catch(() => {
    //         this.$message.error('保存失败')
    //       })
    //   } else {
    //     this.$message.warning('请先保存基本信息后再进行操作')
    //   }
    // },
    // 添加分组
    addGroupItem() {
      if (this.planId) {
        console.log('sdsad', this.planId)
        console.log('r32r', typeof this.planId)
        this.groupData.push({
          groupName: '',
          remark: '',
          groupType: '1'
        })
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    // 删除分组
    delGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = []
          params.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis//del-plan'
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
    // 监测预警
    // saveWarningGroups() {
    //   if (this.planId) {
    //     let params = { planDataBeans: [], planStopGroups: [], planSupportGroups: {}, planResponseGroups: [], planId: this.planId }
    //     for (let item of this.groupWarningData) {
    //       if (item.id === undefined) {
    //         // console.log('保存分组:', item)
    //         item.planId = this.planId
    //         params.addPlanGroupBeans.push(item)
    //       } else {
    //         // console.log('保存分组:', item)
    //         item.planId = this.planId
    //         params.updatePlanGroupBeans.push(item)
    //       }
    //     }
    //     // console.log('保存分组参数:', params)
    //     let url = '/binhuapis/update-plan-group'
    //     Axios.post(url, params)
    //       .then((res) => {
    //         if (res.data.code === 200) {
    //           this.$message.success('保存成功')
    //           this.queryPlanGroupList(this.planId)
    //         } else {
    //           this.$message.error('保存失败')
    //         }
    //       })
    //       .catch(() => {
    //         this.$message.error('保存失败')
    //       })
    //   } else {
    //     this.$message.warning('请先保存基本信息后再进行操作')
    //   }
    // },
    delWarningGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = []
          params.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/binhuapis//del-plan'
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
    // 查询级别
    getLevelType() {
      let url = '/binhuapis/get-level-type'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.levelTypes = res.data.data
        }
      })
    },
    // 保存应急保障
    savePlanSupportGroup() {
      if (this.planId) {
        let params = {
          planDataBeans: this.aboutDocData,
          planStopGroups: this.groupResponseStopData,
          planSupportGroups: this.viewData.planSupportGroups,
          planResponseGroups: this.groupResponseData,
          planId: this.planId
        }
        // let responsData = []
        // this.groupResponseData.forEach(item => {
        //   item.planId = this.planId
        //   responsData.push(item)
        // })
        // params.planResponseGroups = responsData
        let url = '/binhuapis/update-plan-group'
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
        let params = {
          planDataBeans: this.aboutDocData,
          planStopGroups: this.groupResponseStopData,
          planSupportGroups: this.viewData.planSupportGroups,
          planResponseGroups: [],
          planId: this.planId
        }
        let responsData = []
        this.groupResponseData.forEach(item => {
          item.planId = this.planId
          responsData.push(item)
        })
        params.planResponseGroups = responsData
        let url = '/binhuapis/update-plan-group'
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
    addResponseGroupItem() {
      if (this.planId) {
        if (this.groupResponseData.length === 1) {
          this.$message.warning('不允许新增多条数据！')
        } else {
          this.groupResponseData.push({
            groupName: '',
            remark: '',
            groupType: '3'
          })
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    delResponseGroup(index, item) {
      if (this.planId) {
        if (item.id !== undefined) {
          let params = {
            delIds: [],
            addPlanGroupBeans: [],
            updatePlanGroupBeans: []
          }
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
    addWarningGroupItem() {
      console.log('this.planId的值', this.planId)
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
    // 应急保障
    // saveSafeGroups() {
    //   if (this.planId) {
    //     let params = {
    //       planDataBeans: this.aboutDocData,
    //       planStopGroups: this.groupResponseStopData,
    //       planSupportGroups: [],
    //       planResponseGroups: this.groupResponseData,
    //       planId: this.planId
    //     }
    //     let responsData = []
    //     this.groupSafeData.forEach(item => {
    //       item.planId = this.planId
    //       responsData.push(item)
    //     })
    //     params.planSupportGroups = responsData
    //     let url = '/binhuapis/update-plan-group'
    //     Axios.post(url, params)
    //       .then((res) => {
    //         if (res.data.code === 200) {
    //           this.$message.success('保存成功')
    //           this.queryPlanGroupList(this.planId)
    //         } else {
    //           this.$message.error('保存失败')
    //         }
    //       })
    //       .catch(() => {
    //         this.$message.error('保存失败')
    //       })
    //   } else {
    //     this.$message.warning('请先保存基本信息后再进行操作')
    //   }
    // },
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
    delSafeGroup(index, item) {
      if (this.planId) {
        if (item.id !== undefined) {
          let params = []
          params.push(index.toString())
          let url = '/binhuapis//del-plan'
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
    // 应急响应终止
    saveResponseStopGroups() {
      if (this.planId) {
        let params = {
          planDataBeans: this.aboutDocData,
          planStopGroups: [],
          planSupportGroups: this.viewData.planSupportGroups,
          planResponseGroups: this.groupResponseData,
          planId: this.planId
        }
        let responsData = []
        this.groupResponseStopData.forEach(item => {
          item.planId = this.planId
          responsData.push(item)
        })
        params.planStopGroups = responsData
        let url = '/binhuapis/update-plan-group'
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
    addResponseStopGroupItem() {
      if (this.planId) {
        if (this.groupResponseStopData.length === 1) {
          this.$message.warning('不允许新增多条数据！')
        } else {
          this.groupResponseStopData.push({
            groupName: '',
            remark: '',
            groupType: '5'
          })
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
    },
    delResponseStopGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id !== undefined) {
          let params = {
            delIds: [],
            addPlanGroupBeans: [],
            updatePlanGroupBeans: []
          }
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
        }
      } else {
        this.$message.warning('请先保存基本信息后再进行操作')
      }
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
    // 删除资料详情
    deleteAboutDoc(row) {
      // console.log('删除相关资料: ', row)
      let params = { delIds: [] }
      params.delIds.push(row.id)
      // console.log('删除分组参数:', params)
      // let url = '/binhuapis/update-plan-data'
      let url = '/binhuapis/save-plan-group'
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
    handleClose() {
      this.$emit('update:dialogVisible', false)
      this.isActived = 0
      this.$emit('cancel')
    },
    // 获取预案分组以及相关资料list
    queryPlanGroupList(id) {
      let url = '/binhuapis/query-group-data?id=' + id + '&isHistory=false'
      Axios.get(url).then((res) => {
        if (res.data.data !== null) {
          // 前期准备
          // this.groupData = res.data.data.planGroupBeans
          // 监测预警
          // this.groupWarningData = res.data.data.planMonitorGroups
          // 应急响应
          this.groupResponseData = res.data.data.planResponseGroups
          // 应急保障
          // this.groupSafeData = res.data.data.planSupportGroups
          this.viewData.planSupportGroups = res.data.data.planSupportGroups
          // 应急响应终止
          this.groupResponseStopData = res.data.data.planStopGroups
          // 奖励与责任追究
          this.planRewardsDutyForm.rewardsDuty = res.data.data.rewardDuty
          // 相关资料
          this.aboutDocData = res.data.data.planDataBeans
        } else {
          // 前期准备
          // this.groupData = []
          // 监测预警
          // this.groupWarningData = []
          // 应急响应
          this.groupResponseData = []
          // 应急保障
          this.viewData.planSupportGroups = {
            experts: [],
            shelters: [],
            stuffs: [],
            teams: []
          }
          // 应急响应终止
          this.groupResponseStopData = []
          // 奖励与责任追究
          this.planRewardsDutyForm.rewardsDuty = []
          // 相关资料
          this.aboutDocData = []
        }
      })
    },
    // 查看资料详情
    aboutDocView(row) {
      // console.log('查看资料详情: ', row)
      this.isPlanAboutCode = 1
      this.formPlanAboutInfo = row
      this.showPlanAboutAdd = true
    }
  }
}
</script>
<style lang="scss">
.planViewDialog {
  .el-dialog__body {
    height: 800px;
  }
  .tab {
    display: flex;
    //justify-content: space-between;
    .item {
      width: 210px;
      margin-left: 35px;
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
        width: 210px;
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
