<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="预约日期">
              <a-range-picker
              :value="[queryParam.orderTime_begin || null, queryParam.orderTime_end||null]"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              @change="
                (e) => {
                  $set(queryParam, 'orderTime_begin',e[0]?( e[0] + ' 00:00:00'):null)
                  $set(queryParam, 'orderTime_end', e[1]?(e[1] + ' 23:59:59'):null)
                }
              "
            />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="reserveUser">
              <a-input placeholder="请输入reserveUser" v-model="queryParam.reserveUser"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="审核状态">
              <!-- <a-input placeholder="请输入预约场地" v-model="queryParam.reservePlace"></a-input> -->
              <j-dict-select-tag  v-model="queryParam.reservePlace" dictCode="reserve_audit_state"></j-dict-select-tag>
              <!-- <a-select v-model="queryParam.state">
                
                <a-select-option key="0" value="0">待审核</a-select-option>
                <a-select-option key="1" value="1">通过</a-select-option>
                <a-select-option key="2" value="2">不通过</a-select-option>
              </a-select> -->
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
             
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div  class="table-operator">
     <!--  <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
     <!--  <a-button type="primary" icon="download" @click="handleExportXls('预约记录')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
    </div>

    <!-- table区域-begin -->
    <div>
      

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
         

          <a-popconfirm title="确定取消吗?" @confirm="() => handleCancelMyReserve(record.id)">
                <a :disabled="record.auditState=='99'">取消申请</a>
              </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <reserveVerify-modal ref="modalForm" @ok="modalFormOk"></reserveVerify-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import ReserveVerifyModal from './modules/ReserveVerifyModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { postAction } from '../../api/manage'

  export default {
    name: "MyReserveVerifyList",
    mixins:[JeecgListMixin],
    components: {
      ReserveVerifyModal
    },
    props:{
      type:{default:'audit'}
    },
    data () {
      return {
        description: '预约记录管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
           {
            title: '预约日期',
            align:"center",
            dataIndex: 'reserveDate'
           },
           {
            title: '预约时段',
            align:"center",
            customRender:function (t,r,index) {
              return <div domPropsInnerHTML={r.periodList.reduce((prev,cur,index,arr)=>{
                  return prev+cur.beginTime+' - '+cur.endTime +'<br/>'
              },'')}></div> 
          }
            
           },
           {
            title: '预约时长/分',
            align:"center",
            dataIndex: 'reserveTime'
           },
           {
            title: '单价(元/小时)/总价(元)',
            align:"center",
            customRender:function (t,r,index) {
              return (r.billing*60).toFixed(2)+' / '+(r.billing*r.reserveTime).toFixed(2)
          }
           
           },
           
           {
            title: '场地名称',
            align:"center",
            dataIndex: 'placeName'
           },
           {
            title: '场地类型',
            align:"center",
            dataIndex: 'placeType_dictText'
           },
           {
            title: '楼层',
            align:"center",
            dataIndex: 'floor'
           },
           {
            title: '申请人',
            align:"center",
            dataIndex: 'reserveUser_dictText'
           },
           {
            title: '状态',
            align:"center",
            dataIndex: 'auditState_dictText'
           },
           {
            title: '审核人',
            align:"center",
            dataIndex: 'auditUser_dictText'
           },
         
           {
            title: '审核有效期',
            align:"center",
            dataIndex: 'expirationDate'
           },
           {
            title: '审核时间',
            align:"center",
            dataIndex: 'auditTime'
           },
           {
            title: '审核意见',
            align:"center",
            dataIndex: 'auditMsg'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/reserve/reserveRecord/myReserveRecord",    
          cancelMyReserve:'reserve/reserveRecord/cancelMyReserve'

       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      handleAudit(record){
         
    this.$refs.modalForm.edit(record);
    this.$refs.modalForm.title = "审核";
    this.$refs.modalForm.disableSubmit = false;
      },
      handleCancelMyReserve(id){
        postAction(this.url.cancelMyReserve,{},{params:{recordId:id}}).then(res=>{
          if(res.success){
            this.$message.success('取消成功')
            this.loadData()
          }else{
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>