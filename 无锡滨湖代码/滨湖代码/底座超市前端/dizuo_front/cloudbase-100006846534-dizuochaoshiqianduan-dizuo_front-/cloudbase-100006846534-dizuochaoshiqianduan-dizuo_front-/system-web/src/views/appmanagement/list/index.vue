<template>
  <!-- 应用列表 -->
  <div class="appwrap">
    <!-- 头部筛选条件 -->

    <el-row>
      <el-col :span="5">
        应用名称:
        <el-input
          v-model="sAppName"
          placeholder="请输入应用名称/APP ID"
          style="width: 70%"
        ></el-input>
      </el-col>
      <el-col :span="5">
        应用模块:
        <el-select
          v-model="sAppModule"
          placeholder="请选择应用模块"
          @change="checkAppModule"
        >
          <el-option
            v-for="item in dict.type.app_module"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="5">
        场景分类:
        <el-select
          v-model="sAppSort"
          placeholder="请选择场景分类"
          @change="fChangeAppSort"
        >
          <el-option
            v-for="item in dict.type.app_type"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="5">
        是否上架:
        <el-select
          v-model="sIsSj"
          placeholder="请选择是否上架"
          @change="fChangeIsSj"
        >
          <el-option
            v-for="item in aIsSj"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="5">
        是否推荐:
        <el-select
          v-model="sIsTj"
          placeholder="请选择是否推荐"
          @change="fChangeIsTj"
        >
          <el-option
            v-for="item in aIsTj"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="fSearch()">查询</el-button>
        <el-button @click="fClearQuery()">重置</el-button>
      </el-col>
    </el-row>
    <br />
    <!--  -->
    <el-row>
      <el-button type="primary" @click="fOpenLayer()">新增</el-button>
      <el-button type="danger" @click="fDeleteDataAll()">删除</el-button>
    </el-row>
    <br />
    <el-table
      ref="multipleTable"
      :data="aTableData.slice((nCurrent - 1) * 10, nCurrent * 10)"
      stripe
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="fSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center">
      </el-table-column>
      <!-- <el-table-column align="center" prop="appSerialNo" label="应用编号"></el-table-column> -->
      <el-table-column
        align="center"
        prop="appName"
        label="应用名称"
      ></el-table-column>
      <el-table-column align="center" prop="appModule" label="所属模块">
        <template slot-scope="scope"
          >{{
            fSet(
              dict.type.app_module.filter(
                (item) => item.value === scope.row.appModule
              )
            )
          }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="source" label="应用来源">
        <template slot-scope="scope"
          >{{
            fSet(
              dict.type.app_source.filter(
                (item) => item.value === scope.row.source
              )
            )
          }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="appType" label="场景分类">
        <template slot-scope="scope">{{ getFun(scope.row.appType) }} </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="是否上架">
        <!-- <template slot-scope="scope">{{ fSetSattus(aIsSj, scope.row.status) }}</template> -->
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status == 0"
            size="mini"
            type="warning"
            plain
            @click="fChangeFlag(scope.row, 3, '下架', 'status')"
            >下架
          </el-button>
          <el-button
            size="mini"
            type="success"
            v-if="scope.row.status == 3"
            plain
            @click="fChangeFlag(scope.row, 0, '上架', 'status')"
            >上架
          </el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="recommendFlag" label="是否推荐">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.recommendFlag == 0"
            size="mini"
            type="warning"
            plain
            @click="fChangeFlag(scope.row, 1, '推荐', 'recommendFlag')"
            >不推荐
          </el-button>
          <el-button
            size="mini"
            type="success"
            v-if="scope.row.recommendFlag == 1"
            plain
            @click="fChangeFlag(scope.row, 0, '不推荐', 'recommendFlag')"
            >推荐
          </el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="zhiding" label="是否置顶">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.zhiding == 1"
            size="mini"
            type="warning"
            plain
            @click="fChangeFlag(scope.row, 2, '置顶', 'zhiding')"
            >置顶
          </el-button>
          <el-button
            size="mini"
            type="success"
            v-if="scope.row.zhiding == 2"
            plain
            @click="fChangeFlag(scope.row, 1, '取消置顶', 'zhiding')"
            >取消置顶
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="show" label="数字底座展示">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.show == 1"
            size="mini"
            type="success"
            plain
            @click="fChangeFlag(scope.row, 2, '展示', 'show')"
            >不展示
          </el-button>
          <el-button
            size="mini"
            type="warning"
            v-if="scope.row.show == 2"
            plain
            @click="fChangeFlag(scope.row, 1, '不展示', 'show')"
            >展示
          </el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="300px">
        <div class="wrap" slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            plain
            @click="fGetDetails(scope.row.id, true)"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="primary"
            plain
            @click="fGetDetails(scope.row.id, false)"
            v-if="scope.row.status == 3"
            >编辑</el-button
          >
          <!-- <el-button size="mini" type="primary" plain @click="fGetDetails(scope.row.id, false)">编辑</el-button> -->
          <!-- <el-button size="mini" type="success" plain
              @click="fChangeStatus(scope.row, 0, '启用')">启用</el-button> -->
          <!-- <el-button v-if="scope.row.status == 0" size="mini" type="warning" plain
            @click="fChangeStatus(scope.row, 3, '下架')">下架
          </el-button>
          <el-button size="mini" type="success" v-if="scope.row.status == 3" plain
            @click="fChangeStatus(scope.row, 0, '上架')">上架
          </el-button> -->
          <el-button
            size="mini"
            v-if="scope.row.casAppId == null || scope.row.casAppId.length <= 0"
            type="success"
            plain
            @click="fSetAppType(scope.row.id)"
            >认证</el-button
          >
          <el-button
            size="mini"
            type="danger"
            v-if="scope.row.status == 3"
            plain
            @click="fDeleteData(scope.row.id)"
            >删除
          </el-button>
        </div>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @current-change="fCurrentChange"
      :current-page="nCurrent"
      :page-size="10"
      layout="total, prev, pager, next, jumper"
      :total="nTotal"
    >
    </el-pagination>

    <!-- 新增弹框 -->
    <el-dialog
      ref="adddialog"

      :title="sLayerTitle"
      :visible.sync="bDialogVisible"
      width="70%"


      destroy-on-close
    >
      <el-form
        ref="oAddFormRules"
        :rules="oAddFormRules"
        :model="oAddForm"
        label-width="100px"
        :disabled="disabled"


      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用编号">
              <el-input
                v-model="oAddForm.appSerialNo"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sortBy">
              <el-input type="number" v-model="oAddForm.sortBy"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用名称" prop="appName">
              <el-input
                v-model="oAddForm.appName"
                show-word-limit
                maxlength="50"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否入驻" prop="status">
              <el-radio-group v-model="oAddForm.status">
                <el-radio
                  v-for="item in aIsSj"
                  :key="item.value"
                  :label="item.value"
                  >{{ item.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用分类" prop="appModule">
              <!-- <el-input v-model="oAddForm.appModule"></el-input> -->
              <el-select
                v-model="oAddForm.appModule"
                placeholder="请选择应用分类"
                width="100%"
                @change="checkAppModule"
              >
                <el-option
                  v-for="item in dict.type.app_module"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否置顶" prop="zhiding">
              <el-radio-group v-model="oAddForm.zhiding">
                <!-- <el-radio v-for="item in aIsrz" :key="item.value" :label="item.value">{{ item.label }}</el-radio> -->
                <el-radio label="2">是</el-radio>
                <el-radio label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="应用来源" prop="source">
              <el-select v-model="oAddForm.source" placeholder="请选择应用来源">
                <el-option
                  v-for="item in dict.type.app_source"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数字底座展示" prop="show">
              <el-radio-group v-model="oAddForm.show">
                <!-- <el-radio v-for="item in aIsrz" :key="item.value" :label="item.value">{{ item.label }}</el-radio> -->
                <el-radio label="2">是</el-radio>
                <el-radio label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="场景分类" prop="appType">
              <!-- <el-input v-model="oAddForm.appType"></el-input> -->
              <el-select
                v-model="oAddForm.appType"
                multiple
                placeholder="请选择场景分类"
              >
                <el-option
                  v-for="item in dict.type.app_type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否推荐" prop="recommendFlag">
              <!-- <el-select v-model="oAddForm.recommendFlag" placeholder="请选择是否推荐" @change="fChangeIsTj">
                <el-option v-for="item in aIsTj" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select> -->
              <el-radio-group v-model="oAddForm.recommendFlag">
                <el-radio
                  v-for="item in aIsTj"
                  :key="item.value"
                  :label="item.value"
                  >{{ item.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="principalName">
              <el-input v-model="oAddForm.principalName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人电话" prop="principalPhone">
              <el-input v-model="oAddForm.principalPhone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属部门" prop="dept">
              <el-cascader
                v-model="oAddForm.dept"
                clearable
                filterable
                :options="aSsbm"
                @change="fSsbmSelectChange"
                :props="{ value: 'id', label: 'title' }"
                placeholder="请选择所属部门"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用链接" prop="appLink">
              <el-input
                v-model="oAddForm.appLink"

                placeholder="请输入应用链接"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用介绍" prop="remark">
              <el-input
                type="textarea"
                :rows="3"
                placeholder="请输入内容"
                show-word-limit
                maxlength="500"
                v-model="oAddForm.remark"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="特色亮点" prop="liangdian">
              <el-input
                type="textarea"
                :rows="3"
                placeholder="请输入内容(请用分号(';')分割)"
                v-model="oAddForm.liangdian"
                show-word-limit
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用图标" prop="appPic">
              <el-image
                v-if="oAddForm.appPic"
                style="width: 100px; height: 100px"
                :src="oAddForm.appPic"
                :preview-src-list="[oAddForm.appPic]"
              >
              </el-image>
              <el-upload
                action="#"
                :http-request="requestUpload"
                :show-file-list="false"
                :before-upload="beforeUpload"
              >
                <el-button size="small">
                  选择
                  <i class="el-icon-upload el-icon--right"></i>
                  <div slot="tip" class="el-upload__tip">
                    支持png/jpg格式、尺寸(宽*高):200x200、不超过2M;
                  </div>
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用成果" prop="appScreenshot">
              <el-upload
                action="#"
                :http-request="requestUpload"
                :file-list="aPicList"
                list-type="picture-card"
                :on-change="beforeUploadV1"
                :on-remove="fRemoveLogo"
                :on-preview="fPictureCardPreview"
              >
                <i slot="default" class="el-icon-plus"></i>
                <div slot="tip" class="el-upload__tip">
                  支持png/jpg格式、尺寸(宽*高):200x200、不超过2M;
                </div>
              </el-upload>
              <!-- 预览 -->
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="sDialogImageUrl" alt="" />
              </el-dialog>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="亮点截图">
              <el-upload
                action="#"
                :http-request="requestUpload"
                :file-list="aPicListV2"
                list-type="picture-card"
                :on-change="beforeUploadV2"
                :on-remove="fRemoveLogoV2"
                :on-preview="fPictureCardPreview"
              >
                <i slot="default" class="el-icon-plus"></i>
                <div slot="tip" class="el-upload__tip">
                  支持png/jpg格式、不超过2M;
                </div>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="sDialogImageUrl" alt="" />
              </el-dialog>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请表上传">
              <el-upload
                class="upload-demo"
                :action="sUploadUrl"
                :headers="{ Authorization: 'Bearer ' + token }"
                :on-preview="handlePreview"
                :on-success="fUploadFileV2"
                :on-remove="handleRemoveV2"
                :multiple="false"
                :file-list="fileListV2"
                :before-upload="beforeUploadFile"
              >
                <el-button size="small" type="primary">点击上传</el-button>
                <el-button
                  size="small"
                  type="info"
                  icon="el-icon-download"
                  @click="fClickExcel"
                  >下载模板</el-button
                >
              </el-upload>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="其他材料">
              <el-upload
                class="upload-demo"
                :action="sUploadUrl"
                :headers="{ Authorization: 'Bearer ' + token }"
                :on-preview="handlePreview"
                :on-success="fUploadFile"
                :on-remove="handleRemove"
                :multiple="false"
                :file-list="fileList"
                :before-upload="handleBeforeUpload"
              >
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">
                  文件大小不能超过100M
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="fOnSubmit">确 认</el-button>
          <el-button @click="bDialogVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 详情弹框 -->
    <el-dialog title="应用详情" :visible.sync="bDialogDetails" width="50%"    destroy-on-close>
      <el-form
        ref="oAddFormRulesdetail"

        :model="oAddForm"
        label-width="100px"


      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用编号">
              {{ oAddForm.appSerialNo || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序">
              {{ oAddForm.sortBy || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用名称">
              {{ oAddForm.appName || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否上架">
              {{ fSetLabel(aIsSj, oAddForm.status, "value", "label", 0) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用分类">
              {{
                fSetLabel(
                  dict.type.app_module,
                  oAddForm.appModule,
                  "value",
                  "label",
                  0
                )
              }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否置顶">
              {{ oAddForm.zhiding == 2 ? "是" : "否" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用来源">
              {{
                fSetLabel(
                  dict.type.app_source,
                  oAddForm.source,
                  "value",
                  "label",
                  0
                )
              }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数字底座展示">
              {{ oAddForm.show == 2 ? "是" : "否" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="场景分类">
              {{ oAddForm.appType || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否推荐">
              {{
                fSetLabel(aIsTj, oAddForm.recommendFlag, "value", "label", 0)
              }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人">
              {{ oAddForm.principalName || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人电话">
              {{ oAddForm.principalPhone || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属部门">
              {{ fSetSsbm(oAddForm.dept) }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用链接">
              {{ oAddForm.appLink || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用介绍">
              {{ oAddForm.remark || "--" }}
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="特色亮点">
              <template
                v-if="fSetLiangdian(oAddForm.liangdian).indexOf(';') >= 0"
              >
                <div
                  class=""
                  v-for="(text, idx) in fSetLiangdian(oAddForm.liangdian).split(
                    ';'
                  )"
                >
                  {{ idx + 1 }}:{{ text }}
                </div>
              </template>
              <template v-else>
                {{ oAddForm.liangdian || "--" }}
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图标">
              <el-image
                v-if="oAddForm.appPic"
                style="width: 100px; height: 100px"
                :src="oAddForm.appPic"
                :preview-src-list="[oAddForm.appPic]"
              >
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应用成果">
              <el-image
                v-for="(url, idx) in oAddForm.appScreenshot"
                :key="idx"
                style="width: 100px; height: 100px"
                :src="url"
                :preview-src-list="oAddForm.appScreenshot"
              >
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="亮点截图">
              <el-image
                v-for="(url, idx) in oAddForm.liangdianpic"
                :key="idx"
                style="width: 100px; height: 100px"
                :src="url"
                :preview-src-list="oAddForm.liangdianpic"
              >
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="附件信息">
              <el-table border stripe :data="fSetTable(null, oAddForm.upload)">
                <el-table-column
                  align="center"
                  type="index"
                  width="70px"
                  label="编号"
                ></el-table-column>
                <el-table-column
                  align="center"
                  prop="originalFilename"
                  label="文件名"
                >
                  <template slot-scope="{ row }">
                    {{ row.originalFilename.split(".")[0] }}
                  </template>
                </el-table-column>
                <el-table-column
                  align="center"
                  prop="originalFilename"
                  label="文件类型"
                >
                  <template slot-scope="{ row }">
                    {{ row.originalFilename.split(".")[1] }}
                  </template>
                </el-table-column>
                <el-table-column align="center" width="100px" label="操作">
                  <template slot-scope="scope">
                    <el-button type="primary" @click="fDownloadFile(scope.row)"
                      >下载</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="bDialogDetails = false">关闭</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="bImageUrlVisible">
      <img width="100%" :src="sDialogImageUrl" alt="" />
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
    <div class="tip">
      <el-dialog title="提示" :visible.sync="bAppType" width="25%" :center="true" top="30%">
        <div style="text-align: center;"> <span>您确定要认证吗？</span></div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="bAppType = false">取 消</el-button>
        <el-button type="primary" @click="bAppType = false">认 证</el-button>
      </span>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import {
  cmAppList, //查询应用列表
  cmAppDetail, //应用列表详情
  cmAppSave, //新增应用列表
  cmAppDetele, //删除应用列表
  cmAppTypeList, // 应用类别
  cmAppUpdateStatus, // 应用列表下架
  cmAppCertification, // 认证
  downloadResource,
  cmMobanGet, // 申请表获取
} from "@/api/management/management";
import { uploadAvatar } from "@/api/system/user";

import userAvatar from "../../system/user/profile/userAvatar.vue";

import { getToken } from "@/utils/auth";
import axios from "axios";
import dict from "@/utils/dict";
import { addApplicationDept } from "@/api/cas/cas";
// import moment from 'moment'

export default {
  name: "",
  dicts: ["app_module", "sys_user_sex", "app_source", "app_type"],
  components: {
    userAvatar,
  },
  props: [""],
  data() {
    return {
      bDialogDetails: false,
      token: getToken(),
      sUploadUrl: "https://2.21.138.89:11511/system_server/common/upload",
      // sUploadUrl: 'http://10.10.29.141:8080/common/upload',
      sPreviewUrl: "",
      sId: "", // 应用id
      sIconIamgeUrl: "",
      sAppName: "", // 应用名称
      // 应用类别
      sAppSort: "",
      sAppModule: "",
      aAppSort: [],
      fAppSort: [
        { value: "1", label: "1" },
        { value: "2", label: "2" },
      ],
      // 是否上架
      sIsSj: "",
      aIsSj: [
        { value: 0, label: "上架" },
        // { value: 1, label: '待审核' },
        // { value: 2, label: '驳回' },
        { value: 3, label: "下架" },
      ],
      aIsrz: [
        { value: 0, label: "否" },
        { value: 1, label: "是" },
      ],
      // 是否推荐
      sIsTj: "",
      aIsTj: [
        { value: 0, label: "是" },
        { value: 1, label: "否" },
      ],
      // 表格数据
      aTableData: [],
      aMultipleSelection: [],
      nCurrent: 1, // 分页页码
      nTotal: 0, // 总条数
      bDialogVisible: false,
      aSsbm: [],
      fileList: [],
      fileListV2: [],
      oAddForm: {
        appSerialNo: "", // 编号
        principalName: "", // 联系人
        principalPhone: "", // 联系人电话号码
        appPic: "", // 应用图标
        appName: "", // 应用名称
        appModule: "", // 所属模块
        sortBy: "", // 显示顺序
        status: 0, // 是否上架
        recommendFlag: null, // 是否推荐
        appType: "", // 应用类别
        dept: "", // 所属部门
        appLink: "", // 应用链接
        remark: "", // 应用介绍
        liangdian: "", // 特色亮点
        appScreenshot: [],
        upload: [],
        liangdianpic: [],
        moban: [],
        source: "",
        zhiding: "",
        show: "",
      },
      oAddFormRules: {
        principalName: [
          { required: true, message: "请输入联系人", trigger: "blur" },
        ],
        moban: [{ required: true, message: "请上传申请表", trigger: "change" }],
        principalPhone: [
          { required: true, message: "请输入联系人电话号码", trigger: "blur" },
          {
            required: true,
            pattern:
              /^[1][3,5,8]\d{9}$|^\d{7,8}$|^(\d{4}|\d{3})-(\d{7,8}$)|^\d{3,5}$|^(\d{4}|\d{3})-(\d{7,8})-(\d{4})$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        source: [
          { required: true, message: "请选择应用来源", trigger: "change" },
        ],
        appPic: [
          { required: true, message: "请上传应用图标", trigger: "blur,change" },
        ],
        remark: [
          { required: true, message: "请输入应用介绍", trigger: "blur" },
        ],
        liangdian: [
          { required: true, message: "请输入特色亮点", trigger: "blur" },
        ],
        appName: [
          { required: true, message: "请输入应用名称", trigger: "blur" },
        ],
        appModule: [
          { required: true, message: "请选择所属模块", trigger: "change" },
        ],
        sortBy: [{ required: true, message: "请输入排序", trigger: "blur" }],
        status: [
          { required: true, message: "请选择是否上架", trigger: "change" },
        ],
        recommendFlag: [
          { required: true, message: "请选择是否推荐", trigger: "change" },
        ],
        appType: [
          { required: true, message: "请选择应用类别", trigger: "change,blur" },
        ],
        dept: [
          { required: true, message: "请选择所属部门", trigger: "change" },
        ],
        // appLink: [
        //   { required: true, message: '请输入应用链接', trigger: 'blur' },
        // ],
        appScreenshot: [
          { required: true, message: "请上传应用成果", trigger: "blur,change" },
        ],
      },
      sDialogImageUrl: "",
      bImageUrlVisible: false,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      sLayerTitle: "新增应用列表",
      aPicList: [],
      aPicListV2: [],
      aYylb: [], // 应用分类
      bAppType: false, // 认真弹框
      bPreview: false,
      aAllDept: [],
      aAppSource: [],
      apptypeList: undefined,
    };
  },
  computed: {},
  watch: {},
  created() {
    //console.log('dddddddddddd',dict.type)
  },
  mounted() {

    this.oAddForm.upload = [];
    this.getAppType();
    this.getList();
    this.fRequestApplicationDept(); // 请求蓝创的所属部门
  },
  beforeDestroy() {},
  methods: {
    getFun(val) {
      let aa = undefined;
      let cc = "";
      // console.log(val)
      // fSet(dict.type.app_type.filter(item => item.value === scope.row.appType))
      if (val != "") {
        let arr = val.split(",");
        arr.map((item) => {
          this.dict.type.app_type.map((key) => {
            if (key.raw.dictValue == item) {
              aa = key.raw.dictLabel;
              // console.log(aa, "aa");
              cc += aa + " ";
            }
          });
        });
        return cc;
      }
    },
    detailFun() {},
    handleBeforeUpload(file) {
      // 校检文件大小
      const isLt = file.size / 1024 / 1024 < 100;
      if (!isLt) {
        this.$message.error(`上传文件大小不能超过 100 MB!`);
        return false;
      }
      return true;
    },
    fSetLiangdian(str) {
      if (str) {
        return str.replace(/[；]/g, ";");
      } else {
        return "--";
      }
    },
    fSetTable(arr1, arr2) {
      if (arr1 && (arr1.length > 0) & arr2 && arr2.length > 0) {
        return [...arr1, ...arr2];
      } else if (arr1 && arr1.length > 0) {
        return arr1;
      } else if (arr2 && arr2.length > 0) {
        return arr2;
      }
    },
    fSetLabel(data, value, key, label, flag) {
      //  console.log("15678987654356789")
      //   console.log(data, "data");
    //     console.log(value, "value");

      if (flag == 0) {
        return data.filter((item) => item[key] == value).length > 0
          ? data.filter((item) => item[key] == value)[0][label]
          : "--" || "--";
      } else {
        if (value.length > 0) {
          // value = value.split(",");
          // let aa = "";
          // let cc = "";
          // value.map((item) => {
          //   data.map((key) => {
          //     if (key.raw.dictValue == item) {
          //       aa = key.raw.dictLabel;
          //       // console.log(aa, "aa");
          //       cc += aa + " ";
          //     }
          //   });
          // });
          // return cc;
        }
      }
    },
    findNode(arr) {
      arr.forEach((item) => {
        this.aAllDept.push(item);
        item.children && item.children.length > 0
          ? this.findNode(item.children)
          : "";
        item.children = "";
      });
    },
    fSetSsbm(id, arr) {
      let aData = this.aAllDept.filter((item) => item.id == id);
      return aData.length > 0 ? aData[0].title : "--";
    },
    fUploadFileV2(res, file, fileList) {

      this.oAddForm.moban = [];
      fileList.forEach((item) => {
        this.oAddForm.moban.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.originalFilename,
          url: item.response.url,
          originalFilename: item.response.originalFilename,
        });
      });
    }, // 文件上传成功

    fUploadFile(res, file, fileList) {
      // console.log(res,file,fileList)
      this.oAddForm.upload = [];
      fileList.forEach((item) => {
        this.oAddForm.upload.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.originalFilename,
          url: item.response.url,
          originalFilename: item.response.originalFilename,
        });
      });
    }, // 文件上传成功
    downloadFile(blob, fileName) {
      let blobUrl = window.URL.createObjectURL(
        new Blob([blob], { type: "application/force-download" })
      );
      let a = document.createElement("a");
      a.style.display = "none";
      a.setAttribute("href", blobUrl);
      a.setAttribute("download", `${fileName}`);
      document.body.appendChild(a);
      a.click(); //执行下载
      window.URL.revokeObjectURL(blobUrl);
      document.body.removeChild(a);
    },
    // 附件下载
    fDownloadFile(row) {
      downloadResource({ resource: row.url }).then((res) => {
        this.downloadFile(res, row.newFileName);
      });
    },
    // 文件预览
    handlePreview(data) {
      downloadResource({ resource: data.response.url }).then((res) => {
        this.downloadFile(res, data.response.newFileName);
        return;
      });
    },

    fClickExcel() {
      cmMobanGet({ type: 0 }).then((res) => {
        downloadResource({ resource: res.data[0].upload[0].url }).then(
          (resource) => {
            this.downloadFile(resource, res.data[0].upload[0].originalFilename);
            return;
          }
        );
      });
    },

    // 附件信息删除
    handleRemoveV2(file, fileList) {
      console.log(fileList, "333");
      console.log(this.oAddForm.moban, "1111");
      // this.oAddForm.moban = [];
      fileList.forEach((item) => {
        console.log(item);
        this.oAddForm.moban.push({
          path: item.response.path,
          newFileName: item.response.newFileName,
          name: item.response.name,
          url: item.response.url,
          originalFilename: item.response.originalFilename,
        });
      });
      if (fileList.length == 0) {
        this.oAddForm.moban = [];
      }
      console.log(this.oAddForm.moban, "2222");
    },

    // 附件信息删除
    handleRemove(file, fileList) {
      // console.log(file,fileList);
      fileList.forEach((item) => {
        this.oAddForm.upload.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.originalFilename,
          url: item.response.url,
          originalFilename: item.response.originalFilename,
        });
      });
    },
    fSet(arr) {
      // console.log(arr)
      if (arr && arr.length > 0) {
        return arr[0].label;
      } else {
        return "--";
      }
    },
    // dict,
    // 认证按钮
    ensureConfirm(id){
      this.bAppType=false
          cmAppCertification({
            id: id,
          }).then((res) => {
            this.$message({
              type: "success",
              message: `认证成功`,
            });
            if (res.data===true){
              that.getList();
            }
          });
    },
    fSetAppType(id) {
      let that=this
    //  this.bAppType=true
       this.$alert("您确定要认证吗？", "认证", {
        confirmButtonText: "确定",
        callback: (action) => {
          cmAppCertification({
            id: id,
          }).then((res) => {
            this.$message({
              type: "success",
              message: `认证成功`,
            });
            if (res.data===true){
              that.getList();
            }
          });
        },
      });
    },
    // 请求蓝创的所属部门
    fRequestApplicationDept() {
      addApplicationDept().then((res) => {
        this.aSsbm = res.data;
        // console.log('====>>>>', this.aSsbm)
        this.aAllDept = [];
        this.findNode(JSON.parse(JSON.stringify(res.data)));
      });
    },
    // 搜索按钮
    fSearch() {
      this.nCurrent = 1;
      this.getList();
    },
    // 清除列表搜索条件
    fClearQuery() {
      this.sAppName = "";
      this.sAppSort = "";
      this.sAppModule = "";
      this.sIsSj = "";
      this.sIsTj = "";
      this.fSearch();
    },
    // 打开新增弹框
    fOpenLayer() {


      this.sLayerTitle = "新增应用列表";
      this.resetform()
      if(this.$refs['oAddFormRules']){
        this.$refs.oAddFormRules.resetFields()
      }

      this.bDialogVisible = true;
      this.disabled = false;
    },
    resetform(){

      this.fAppSort = [];

      this.oAddForm = {
        appSerialNo: "", // 编号
        appPic: "", // 应用图标
        appName: "", // 应用名称
        appModule: "", // 所属模块
        sortBy: "", // 显示顺序
        status: '', // 是否上架
        recommendFlag: '', // 是否推荐
        appType: "", // 应用类别
        // dept: '',// 应用单位
        remark: "", // 应用介绍
        appLink: "", // 应用链接
        appScreenshot: [],
        liangdianpic: [],
        upload: [],
        moban: [],
      };
      this.aPicList = [];
      this.aPicListV2=[]
      this.fileListV2=[]
      this.fileList=[]
    },


    checkAppModule(e) {
      console.info("checkAppModule:");
      console.info(e);
      // this.getAppType(e)
      this.oAddForm.appType = null;
      this.fAppSort = this.aAppSort.filter((item) => {
        return item.appModule === e;
      });
    },
    /** 请求应用类别 */
    getAppType(appModule) {
      if (!appModule) return;
      this.loading = true;
      cmAppTypeList({
        typeName: "", // 类型名称
        appModule: appModule || "", // 所属模块
        status: 0, // 状态
        pageNum: 1, // 页码
        pageSize: 1000, // 每页的数量
      }).then((res) => {
        // console.log(res.data.records)
        let records = res.data.records;
        this.aAppSort = records;
      });
    },
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      cmAppList({
        appName: this.sAppName, // 应用名称
        appType: this.sAppSort, // 应用类别
        appModule: this.sAppModule, // 应用类别
        status: this.sIsSj, // 是否上架
        recommendFlag: this.sIsTj, // 是否推荐
        pageNum: this.nCurrent, // 页码
        pageSize: 10, // 每页的数量
      }).then((res) => {
        // console.log(res)
        // debugger
        // this.aTableData = res.data.records;
        // this.nTotal = res.data.total;
        this.aTableData = res.data;
        this.nTotal = res.data.length;
        let arr = [];
        console.log(this.dict.type.app_type);
      });
    },
    handleClose(done) {
      this.oAddForm.remark = "";
      this.oAddForm.upload = [];
      this.oAddForm.moban = [];
      this.fileList = [];
      this.fileListV2 = [];
      this.closefield()
      this.bDialogVisible = false;

    },
    // 获取详情
    fGetDetails(id, status) {
      this.fAppSort = [];
      cmAppDetail({ id }).then((res) => {
        // console.log(res);
        res.data.liangdianpic
          ? (res.data.liangdianpic = res.data.liangdianpic)
          : (res.data.liangdianpic = []);
        res.data.appScreenshot
          ? (res.data.appScreenshot = res.data.appScreenshot)
          : (res.data.appScreenshot = []);
        for (const key in this.oAddForm) {
          this.oAddForm[key] = res.data[key];
        }
        if (status == false) {
          this.oAddForm.appType = res.data.appType.split(",");
        } else {
          if (res.data.appType.length > 0) {
            res.data.appType = res.data.appType.split(",");
            let aa = "";
            let cc = "";
            res.data.appType.map((item) => {
              this.dict.type.app_type.map((key) => {
                if (key.raw.dictValue == item) {
                  aa = key.raw.dictLabel;
                  // console.log(aa, "aa");
                  cc += aa + " ";
                }
              });
            });
            this.oAddForm.appType = cc;
          }
        }
        console.log(this.oAddForm.appType);
        this.aPicList = [];
        this.aPicListV2 = [];
        this.getAppType(this.oAddForm.appModule);
        // console.log(this.oAddForm.appScreenshot)
        if (res.data.appScreenshot) {
          JSON.parse(res.data.appScreenshot).forEach((item) => {
            this.aPicList.push({
              name: item,
              url: item,
            });
          });
          this.aPicList = this.oAddForm.appScreenshot = JSON.parse(
            this.oAddForm.appScreenshot
          );
          this.aPicList = this.fSetPicArr(this.oAddForm.appScreenshot);
        }

        if (res.data.liangdianpic && res.data.liangdianpic.length > 0) {
          JSON.parse(res.data.liangdianpic).forEach((item) => {
            this.aPicListV2.push({
              name: item,
              url: item,
            });
          });
          this.aPicListV2 = this.oAddForm.liangdianpic = JSON.parse(
            this.oAddForm.liangdianpic
          );
          this.aPicListV2 = this.fSetPicArr(this.oAddForm.liangdianpic);
        }
        // console.log(this.aPicListV2, res.data.liangdianpic, this.oAddForm.liangdianpic)

        this.oAddForm.upload = this.oAddForm?.upload || [];
        this.oAddForm.moban = this.oAddForm?.moban || [];
        // console.log(this.oAddForm?.upload)
        this.fileList = this.oAddForm?.upload || [];
        // console.log(this.oAddForm?.upload)
        this.fileListV2 = this.oAddForm?.moban || [];
        this.fileListV2.map((item) => {
          item.name = item.originalFilename;
        });
        this.fileList.map((item) => {
          item.name = item.originalFilename;
        });
        // console.log(this.fileListV2)
        this.disabled = status;
        if (this.disabled) {
          this.sLayerTitle = "应用列表详情";
          this.bDialogDetails = true;
          // console.log(this.aPicList)
          return;
        } else {
          this.sLayerTitle = "修改应用列表";
          this.sId = id;
        }
        this.bDialogVisible = true;
      });
    },
    // 批量删除
    fDeleteDataAll() {
      // console.log(this.aMultipleSelection)
      if (this.aMultipleSelection.length > 0) {
        //
        this.fDeleteData(this.aMultipleSelection.join(","));
      } else {
        this.$message({
          type: "warning",
          message: "请选择需要删除的数据!",
        });
      }
    },
    // 删除
    fDeleteData(id) {
      this.$confirm("您确定要删除吗, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          cmAppDetele({ id }).then((res) => {
            this.nCurrent = 1;
            this.getList();
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 下架按钮
    fChangeStatus(row, status, text) {
      cmAppUpdateStatus({
        id: row.id, // 类型名称
        status: status, // 状态
      }).then((res) => {
        // console.log(res)
        this.nCurrent = 1;
        this.getList();
        this.$message({
          message: text + "成功",
          type: "success",
        });
      });
    },
    fChangeFlag(row, status, text, key) {
      this.$confirm(
        "你已经选择了" + row.appName + ",是否" + text + "该应用?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          row[key] = status;
          cmAppSave({ ...row }).then((res) => {
            // console.log(res)
            this.getList();
            this.$message({
              message: "修改成功",
              type: "success",
            });
          });
        })
        .catch(() => {});
    },
    // 设置文字
    fSetSattus(data, text) {
      let stext = "";
      if (data.filter((item) => item.value == text)[0]) {
        stext = data.filter((item) => item.value == text)[0].label;
      } else {
        stext = "--";
      }
      return stext;
    },
    // 设置图片回显
    fSetPicArr(data) {
      let array = [];
      data.forEach((item, idx) => {
        array.push({
          name: idx,
          url: item,
        });
      });
      return array;
    },
    // 应用类别切换
    fChangeAppSort(val) {
      console.log(val);
    },
    // 是否上架切换
    fChangeIsSj(val) {
      console.log(val);
    },
    // 是否推荐切换
    fChangeIsTj(val) {
      console.log(val);
    },
    // 所属部门下拉框切换
    fSsbmSelectChange(item) {
      if (this.oAddForm.dept) {
        this.oAddForm.dept = this.oAddForm.dept[this.oAddForm.dept.length - 1];
      }
    },
    // 表格多选框
    fSelectionChange(data) {
      this.aMultipleSelection = [];
      data.forEach((item) => {
        this.aMultipleSelection.push(item.id);
      });
      // this.aMultipleSelection = val;
    },
    // 分页切换
    fCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.nCurrent = val;
      // this.getList();
    },
    // 删除图片
    fRemoveImg(file, fileList) {
      console.log(file, fileList);
    },
    // 预览图片
    fPictureCardPreview(file) {
      this.sDialogImageUrl = file.url;
      this.bImageUrlVisible = true;
    },
    // 新增弹框确定按钮
    fOnSubmit() {
      this.$nextTick(() => {
        this.$refs["oAddFormRules"].validate((valid) => {
          if (valid) {
            if (this.oAddForm.moban && this.oAddForm.moban.length > 0) {
            } else {
              this.$message.error("请上传申请表!");
              return;
            }
            this.oAddForm.appType = JSON.parse(
              JSON.stringify(this.oAddForm.appType)
            ).join(",");
            let oData = {
              ...this.oAddForm,
              appScreenshot: JSON.stringify(this.oAddForm.appScreenshot),
              liangdianpic: JSON.stringify(this.oAddForm.liangdianpic),
              // upload: JSON.stringify(this.oAddForm.upload)
            };
            if (this.sId) {
              oData.id = this.sId;
            }

            // this.oAddForm.appType =  this.oAddForm.appTyp//e.join(",");

            cmAppSave({ ...oData }).then((res) => {
              // console.log(res)
              this.nCurrent = 1;
              this.getList();
              this.$message({
                message: this.sId ? "修改成功" : "新增成功",
                type: "success",
              });
              this.oAddForm.upload = [];
              this.oAddForm.moban = [];
              this.fileList = [];
              this.fileListV2 = [];
              this.bDialogVisible = false;
              this.sId = "";
            });
          } else {
            console.log("error submit!!");
            // this.bDialogVisible = false;
            return false;
          }
        });
      });
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    beforeUploadFile(a) {
      console.log(a);
      console.log(a.size);
    },
    //
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError(
          "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。"
        );
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.oAddForm.appPic = reader.result;
        };
        let formData = new FormData();
        formData.append("avatarfile", file);
        uploadAvatar(formData).then((response) => {
          this.oAddForm.appPic = process.env.VUE_APP_BASE_API + response.imgUrl;
          this.$modal.msgSuccess("上传成功");
        });
      }
    },
    beforeUploadV1(file, fileList) {
      // this.oAddForm.appScreenshot = [];
      console.log(file, fileList);

      if (file.raw.type.indexOf("image/") == -1) {
        this.$modal.msgError(
          "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。"
        );
      } else {
        // const reader = new FileReader();
        // reader.readAsDataURL(file.raw);
        // reader.onload = () => {
        //     // this.oAddForm.appScreenshot.push(reader.result);
        // };
        let formData = new FormData();
        formData.append("avatarfile", file.raw);
        uploadAvatar(formData).then((response) => {
          console.log(response, 111);
          this.oAddForm.appScreenshot.push(
            process.env.VUE_APP_BASE_API + response.imgUrl
          );
          // this.oAddForm.appScreenshot = process.env.VUE_APP_BASE_API + response.imgUrl;
          // this.$modal.msgSuccess("上传成功");
        });
        console.log(this.oAddForm.appScreenshot);
      }
    },
    beforeUploadV2(file, fileList) {
      if (file.raw.type.indexOf("image/") == -1) {
        this.$modal.msgError(
          "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。"
        );
      } else {
        let formData = new FormData();
        formData.append("avatarfile", file.raw);
        uploadAvatar(formData).then((response) => {
          console.log(response, this.oAddForm.liangdianpic);
          this.oAddForm.liangdianpic.push(
            process.env.VUE_APP_BASE_API + response.imgUrl
          );
          console.log(this.oAddForm.liangdianpic);
        });
      }
    },
    fRemoveLogo(file, fileList) {
      this.oAddForm.appScreenshot = [];
      // return
      let index;
      fileList.forEach((data, idx) => {
        this.oAddForm.appScreenshot.push(data.url);
        // if (data.uid === file.uid || data.url === file.url) {
        //     index = idx
        // }
      });
      // if (index) {
      //     this.oAddForm.appScreenshot.splice(index, 1)
      // }
    },
    fRemoveLogoV2(file, fileList) {
      this.oAddForm.liangdianpic = [];
      let index;
      fileList.forEach((data, idx) => {
        this.oAddForm.liangdianpic.push(data.url);
      });
    },

    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // fBeforeUpload(file) {
    //     console.log(file)
    //     let formData = new FormData();
    //     formData.append("avatarfile", file);
    //     uploadAvatar(formData).then(response => {
    //         this.open = false;
    //         this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
    //         store.commit('SET_AVATAR', this.options.img);
    //         this.$modal.msgSuccess("修改成功");
    //         this.visible = false;
    //     });
    // },
  },
};
</script>
<style lang="scss" scoped>
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

::v-deep .el-col {
  margin-bottom: 12px;
}


  ::v-deep .tip .el-dialog:not(.is-fullscreen) {
	margin-top: 30vh !important;
}


</style>
