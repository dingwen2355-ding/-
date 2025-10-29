<template>
  <a-card :bordered="false">
    <!-- 抽屉 -->
    <a-drawer
      title="社保信息列表"
      :width="screenWidth"
      @close="onClose"
      :visible="visible"
    >
      <!-- 抽屉内容的border -->
      <div
        :style="{
          padding:'10px',
          border: '1px solid #e9e9e9',
          background: '#fff',
        }">

        <div class="table-page-search-wrapper">
          <a-form layout="inline" :form="form" @keyup.enter.native="searchQuery">
<!--            <a-row :gutter="10">-->
<!--              <a-col :md="9" :sm="12">-->
<!--                <a-form-item label="股东名称">-->
<!--                  <a-input style="width: 120px;" placeholder="请输入股东名称" v-model="queryParam.gdmc"></a-input>-->
<!--                </a-form-item>-->
<!--              </a-col>-->

<!--              <a-col :md="7" :sm="24">-->
<!--              <span style="float: left;" class="table-page-search-submitButtons">-->
<!--                <a-button type="primary" @click="searchQuery">搜索</a-button>-->
<!--                <a-button type="primary" @click="searchReset" style="margin-left: 8px">重置</a-button>-->
<!--              </span>-->
<!--              </a-col>-->
<!--            </a-row>-->
            <a-row>
              <a-col :md="2" :sm="24">
                <a-button style="margin-bottom: 10px" type="primary" @click="handleAdd">新增</a-button>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <div>
          <a-table
            ref="table"
            rowKey="id"
            size="middle"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            @change="handleTableChange"
            :scroll="{ x: 800, y: 300 }"
          >

          <span slot="action" slot-scope="text, record">
            <a @click="handleEdit(record)">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
              <a>删除</a>
            </a-popconfirm>
          </span>

          </a-table>
        </div>
      </div>
    </a-drawer>
    <social-insurance-modal ref="modalForm" @ok="modalFormOk"></social-insurance-modal> <!-- 字典数据 -->
  </a-card>
</template>

<script>
  import pick from 'lodash.pick'
  import {filterObj} from '@/utils/util';
  import SocialInsuranceModal from './modules/SocialInsuranceModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "SocialInsuranceList",
    mixins: [JeecgListMixin],
    components: {SocialInsuranceModal},
    data() {
      return {
        columns: [
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'},
            width:100,
            fix:'left'
          },
          {
            title: '缴存年月',
            align: "center",
            width:100,
            dataIndex: 'jfny',

          },
          {
            title: '应缴金额',
            align: "center",
            dataIndex: 'yjje',
          },
          {
            title: '实缴金额',
            align: "center",
            dataIndex: 'sjje',
          },
          {
            title: '缴纳日期',
            align: "center",
            dataIndex: 'jnrq',
          }

        ],
        queryParam: {
          qyid: "",
          dictName: "",
          itemText: "",
          delFlag: "1",
          status: [],
        },
        title: "操作",
        visible: false,
        screenWidth: 800,
        model: {},
        qyid: "",
        status: 1,
        labelCol: {
          xs: {span: 5},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 12},
          sm: {span: 12},
        },
        type:"",
        form: this.$form.createForm(this),
        validatorRules: {
          itemText: {rules: [{required: true, message: '请输入名称!'}]},
          itemValue: {rules: [{required: true, message: '请输入数据值!'}]},
        },
        url: {
          list: "/company/soc/getlist",
          delete: "/company/soc/delete"
        },
      }
    },
    created() {
      // 当页面初始化时,根据屏幕大小来给抽屉设置宽度
      this.resetScreenSize();
    },
    methods: {
      add(qyid) {
        this.qyid = qyid;
        this.edit({});
      },
      edit(record) {
        if (record.id) {
          this.qyid = record.id;
        }
        this.queryParam = {}
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.qyid = this.qyid;
        this.model.status = this.status;
        this.visible = true;
        this.type = record.type;
        // this.$nextTick(() => {
        //   this.form.setFieldsValue(pick(this.model, 'itemText', 'itemValue'))
        // });
        // 当其它模块调用该模块时,调用此方法加载字典数据
        this.loadData();
      },

      getQueryParams() {
        var param = Object.assign({}, this.queryParam);
        param.qyid = this.qyid;
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        param.type = this.type;
        if (this.superQueryParams) {
          param['superQueryParams'] = encodeURI(this.superQueryParams)
          param['superQueryMatchType'] = this.superQueryMatchType
        }
        return filterObj(param);
      },

      // 添加字典数据
      handleAdd() {
        this.$refs.modalForm.add(this.qyid,this.type);
        this.$refs.modalForm.title = "新增";
      },
      showDrawer() {
        this.visible = true
      },
      onClose() {
        this.visible = false
        this.form.resetFields();
        this.dataSource = [];
      },
      // 抽屉的宽度随着屏幕大小来改变
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 600) {
          this.screenWidth = screenWidth;
        } else {
          this.screenWidth = 600;
        }
      },
    }
  }
</script>
<style scoped>
</style>