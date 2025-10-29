<template>
  <div id="app">
    <!-- <my-process-palette /> -->
    <my-process-designer :key="`designer-${reloadIndex}`" :options="{
      taskResizingEnabled: true,
      eventResizingEnabled: true
    }" v-model="xmlString" v-bind="controlForm" keyboard ref="processDesigner" @element-click="elementClick"
      @init-finished="initModeler" @save-process="handleSaveProcess" />
    <my-properties-panel :key="`penal-${reloadIndex}`" :bpmn-modeler="modeler" :prefix="controlForm.prefix"
      class="process-panel panelOwn" :formObj="formObj" />

    <!-- demo config -->
    <div class="demo-control-bar">
      <div class="open-control-dialog" @click="controlDrawerVisible = true"><i class="el-icon-setting"></i></div>
    </div>
    <el-drawer :visible.sync="controlDrawerVisible" size="400px" title="偏好设置" append-to-body destroy-on-close>
      <el-form :model="controlForm" size="small" label-width="100px" class="control-form" @submit.native.prevent>
        <el-form-item label="流程ID">
          <el-input v-model="controlForm.processId" @change="reloadProcessDesigner" />
        </el-form-item>
        <el-form-item label="流程名称">
          <el-input v-model="controlForm.processName" @change="reloadProcessDesigner" />
        </el-form-item>
        <el-form-item label="流转模拟">
          <el-switch v-model="controlForm.simulation" inactive-text="停用" active-text="启用"
            @change="reloadProcessDesigner" />
        </el-form-item>
        <el-form-item label="禁用双击">
          <el-switch v-model="controlForm.labelEditing" inactive-text="停用" active-text="启用"
            @change="changeLabelEditingStatus" />
        </el-form-item>
        <el-form-item label="隐藏label">
          <el-switch v-model="controlForm.labelVisible" inactive-text="停用" active-text="启用"
            @change="changeLabelVisibleStatus" />
        </el-form-item>
        <el-form-item label="流程引擎">
          <el-radio-group v-model="controlForm.prefix" @change="reloadProcessDesigner(true)">
            <el-radio label="camunda">camunda</el-radio>
            <el-radio label="flowable">flowable</el-radio>
            <el-radio label="activiti">activiti</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工具栏">
          <el-radio-group v-model="controlForm.headerButtonSize">
            <el-radio label="mini">mini</el-radio>
            <el-radio label="small">small</el-radio>
            <el-radio label="medium">medium</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-drawer>

    <!--    <div class="info-tip">-->
    <!--      <p><strong>该项目仅作为Bpmn.js的简单演示项目，不涉及过多的自定义Render内容。</strong></p>-->
    <!--      <p>注：activiti 好像不支持表单配置，控制台可能会报错</p>-->
    <!--      <p>-->
    <!--        <span>更多配置请查看源码：</span>-->
    <!--        <a href="https://github.com/miyuesc/bpmn-process-designer">MiyueSC/bpmn-process-designer</a>-->
    <!--      </p>-->
    <!--      <p>-->
    <!--        <span>疑问请在此留言：</span>-->
    <!--        <a href="https://github.com/miyuesc/bpmn-process-designer/issues/16">MiyueSC/bpmn-process-designer/issues</a>-->
    <!--      </p>-->
    <!--    </div>-->
  </div>
</template>

<script>
// 自定义的属性解析文件
import translations from "../../package/translations";
// 自定义渲染（隐藏了 label 标签）
import CustomRenderer from "./modules/custom-renderer";
// 自定义元素选中时的弹出菜单（修改 默认任务 为 用户任务）
import CustomContentPadProvider from "../../package/designer/plugins/content-pad";
// 自定义左侧菜单（修改 默认任务 为 用户任务）
import CustomPaletteProvider from "../../package/designer/plugins/palette";
import Log from "../../package/Log";
// 任务resize
import resizeTask from "bpmn-js-task-resize/lib";
import axios from "axios";
import { getAction, postAction } from '@/api/manage'
export default {
  name: "App",
  props: {
    initXmlStr: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      formObj: [],
      url: "/v1/ActivitiDesignController/getDesignList",
      xmlString: "",
      modeler: null,
      reloadIndex: 0,
      controlDrawerVisible: false,
      translationsSelf: translations,
      controlForm: {
        processId: "",
        processName: "",
        simulation: true,
        labelEditing: false,
        labelVisible: false,
        prefix: "activiti",
        headerButtonSize: "mini",
        // additionalModel: []
        additionalModel: [CustomContentPadProvider, CustomPaletteProvider, resizeTask]
      },
      addis: {
        CustomContentPadProvider,
        CustomPaletteProvider
      }
    };
  },
  created() { },
  mounted() {
    // this.initFormList();
  },
  methods: {
    //加载表单列表
    initFormList() {
      console.log("initFormList");
      // let token = this.getQueryString("token");
      // if (token === null) return;
      // console.log(token);
      let url = this.requestUrl + this.url;
      getAction(url).then(res => {
        console.log(res.result);
        this.formObj = res.result;
        this.$store.state.bpmn.formObj = JSON.parse(JSON.stringify(this.formObj))
        // this.$bus.$emit("getFormArr",)
      });
    },
    initModeler(modeler) {
      setTimeout(() => {
        //把bpmnMolder放进vuex里面去
        this.$store.state.bpmn.modeler = modeler
        this.modeler = modeler;
        const canvas = modeler.get("canvas");
        const rootElement = canvas.getRootElement();
        Log.prettyPrimary("Process Id:", rootElement.id);
        Log.prettyPrimary("Process Name:", rootElement.businessObject.name);
      }, 10);
    },
    reloadProcessDesigner(deep) {
      this.controlForm.additionalModel = [];
      for (let key in this.addis) {
        if (this.addis[key]) {
          this.controlForm.additionalModel.push(this.addis[key]);
        }
      }
      deep && (this.xmlString = undefined);
      this.reloadIndex += 1;
      this.modeler = null; // 避免 panel 异常
    },
    handleSaveProcess() {
      this.$emit('saveProcess', this.xmlString)
    },
    changeLabelEditingStatus(status) {
      this.addis.labelEditing = status ? { labelEditingProvider: ["value", ""] } : false;
      this.reloadProcessDesigner();
    },
    changeLabelVisibleStatus(status) {
      this.addis.customRenderer = status ? CustomRenderer : false;
      this.reloadProcessDesigner();
    },
    elementClick(element) {
      this.element = element;
    }
  },
  watch: {
    initXmlStr: {
      handler(val) {
        if (val) {
          this.xmlString = val
        }
      },
      immediate: true,
    },
  },

};
</script>

<style >
.panelOwn {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
}

.my-process-designer {
  width: 1900px;
}

body {
  overflow: hidden;
  margin: 0;
  box-sizing: border-box;
}

#app {
  position: relative;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  display: flex;
  grid-template-columns: 100px auto max-content;
}

.demo-control-bar {
  position: fixed;
  right: 8px;
  bottom: 8px;
  z-index: 1;

}

.open-control-dialog {
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  font-size: 32px;
  background: rgba(64, 158, 255, 1);
  color: #ffffff;
  cursor: pointer;
}

.info-tip {
  position: fixed;
  top: 40px;
  right: 500px;
  z-index: 10;
  color: #999999;
}

.control-form {}

.control-form .el-radio {
  width: 100%;
  line-height: 32px;
}

.element-overlays {
  box-sizing: border-box;
  padding: 8px;
  background: rgba(0, 0, 0, 0.6);
  border-radius: 4px;
  color: #fafafa;
}

body,
body {
  /* 滚动条 */

}
</style>
