<template>
    <a-card class="reserveList" :bordered="false">
  
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            
            <!-- <a-col :span="6">
                <div :style="{ width: '240px', border: '1px solid #d9d9d9', borderRadius: '4px' }">
                    <a-calendar :fullscreen="false" :header-render="headerRender" :disabledDate="disabledDate" @panelChange="onPanelChange" />
                </div>
            </a-col> -->
                   
            <a-col :span="6">
                <a-form-item label="日期"> 
                <a-date-picker v-model="queryParam.reserveDate" valueFormat="YYYY-MM-DD" format="YYYY-MM-DD"  :disabledDate="disabledDate" ></a-date-picker>
                </a-form-item>
            </a-col>
           
           
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="场所类型">            
              <j-dict-select-tag placeholder="请选择类型" v-model="queryParam.placeType" dictCode="PLACE_TYPE"/>     
            </a-form-item>    
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">  
                <a-form-item label="时段">             
                <a-select mode="multiple">
                  <a-select-option v-for="item in dict.ReservePeriodList" :key="item.id" :value="item.id">
                    {{item.beginTime+' - '+item.endTime}}
                </a-select-option>
                </a-select>
              <!-- <j-dict-select-tag placeholder="请选择时段" v-model="queryParam.type" dictCode="message_type"/>  -->
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
      <div class="table-operator">
      
       
      </div>
  
      <!-- table区域-begin -->
      <a-card  :loading="loading" :bordered="false">
        <a-row >
          <a-col v-for="item in dataSource" :key="item.id" :offset="1" :span="4">
            <!-- <a-card @click="PeriodList(item)" hoverable style="width: 240px"> -->
              <a-card @click="handleShowModal(item)" hoverable style="width: 240px">
            <div  slot="cover">
                <img style="    width: 100%;height: 180px;"   
            alt="example"
            :src="imgList[item.placeType-1]"
            />
    <div v-if="item.available =='-1'" style="position: absolute;top: 0;    width: 100%;background: #ffffffcf;height: 180px;    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;">
        <a-icon style="    font-size: 70px;" type="stop" />
        <div style="font-size: 35px;">已满</div>
    </div>
    <a-card :loading="item.listLoading" v-if="item.PeriodListVisible" style="position: absolute;top: 0;    width: 100%;
    background: #ffffffcf;height: 180px;" @click="e=>e.stopPropagation()">
        <!-- <a-spin :spinning="item.listLoading" />        -->
        <div style="margin: 3px 0"  v-for="period in item.periodList" :key="period.periodId">
           <a-tag v-if="!period.available" color="#b9b9b9" style="font-size: 20px;">{{period.beginTime+' - '+period.endTime}}</a-tag>
                <a-checkable-tag v-else style="font-size: 20px;" :checked="item.checkedList&&item.checkedList[period.periodId]!=null" @change="handleChangePeriod(item,period,$event)">{{period.beginTime+' - '+period.endTime}}</a-checkable-tag> 
          
         
        </div>  
        <a-button :disabled="!item.checkedList||Object.keys(item.checkedList).length==0" style="width:100%" size="small" @click="reserve(item)" type="">申请</a-button>
    </a-card>
    </div>
    
   
   
    <a-card-meta :title="item.placeName">
      <template slot="description">
        <div>{{(item.billing*60).toFixed(2)}}(元)/小时</div>
        <a-tag color="green">{{item.placeType_dictText}}</a-tag>
        <div style="height: 20px;margin-top:10px; white-space: nowrap;overflow: hidden;">{{item.description}}</div>
      </template>
    </a-card-meta>
  </a-card>

          </a-col>
        </a-row>
        <a-pagination
      style="float: right;margin-top: 10px;"
      v-model="ipagination.current"
      :total="ipagination.total"
      :pageSizeOptions="ipagination.pageSizeOptions"
      size="small"
      :showTotal="ipagination.showTotal"
      showSizeChanger
      show-less-items
    />
       <!--  <a-table
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
            <a @click="handleEdit(record)">编辑</a>
  
            <a-divider type="vertical" />   
              <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                <a>删除</a>
              </a-popconfirm>  
          </span>
  
        </a-table> -->
      </a-card>
      <!-- table区域-end -->
  
      <!-- 表单区域 -->
      <modal-form ref="modalForm" @ok="modalFormOk"></modal-form>
    </a-card>
  </template>
  
  <script>
    import '@/assets/less/TableExpand.less'
    import modalForm from './modules/ReserveModal'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import {getReservePeriodList} from '@/api/api'
    import {getAction,postAction} from '@/api/manage'
    import moment from 'moment'
    export default {
      name: "ReserveList",
      mixins:[JeecgListMixin],
      components: {
        modalForm
            
      },
      data () {
        let imgList=[
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fstaticqn.qizuang.com%2Fmeitu%2F20201109%2F5fa8bb04a860f-s5.jpg&refer=http%3A%2F%2Fstaticqn.qizuang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666163531&t=746093a4a149a9448d9b293ef55e349d',
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fmingguandg.com%2Fpic%2F201804171615464859.jpg&refer=http%3A%2F%2Fmingguandg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666164497&t=21f9af3df934aedec6c8363e20cb220b',]
        return {
          description: '场地预约页面',
          disableMixinCreated:true,
          imgList,
          dict:{},
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
              title: '名称',
              align:"center",
              dataIndex: 'placeName'
             },
            /*  {
              title: '正文',
              align:"center",
              dataIndex: 'content'
             }, */
             {
              title: '类型',
              align:"center",
              dataIndex: 'placeType_dictText'
             },
          /*    {
              title: '创建时间',
              align:"center",
              dataIndex: 'createTime'
             },
             {
              title: '创建人',
              align:"center",
              dataIndex: 'createBy_dictText'
             }, */
            {
              title: '操作',
              dataIndex: 'action',
              align:"center",
              scopedSlots: { customRender: 'action' },
            }
          ],
          url: {
            list: "/reserve/reservePlace/availablePlace",
            placeReservedPeriod:"/reserve/recordPeriod/placeReservedPeriod",
            reserve:"reserve/reserveRecord/reserve",
            delete: "/message/message/delete",
            deleteBatch: "/message/message/deleteBatch",
            exportXlsUrl: "message/message/exportXls",
            importExcelUrl: "message/message/importExcel",
         },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    created(){
      this.$set(this.queryParam,'reserveDate', moment().add(1, 'days').format('YYYY-MM-DD')) 

      this.loadData();
    this.initDictConfig();
    },
      methods: {
        onPanelChange(value, mode) {
      console.log(value, mode);
    },
    reserve(record){
        this.$set(record,'listLoading',true)
        let periodList=Object.values(record.checkedList)
        postAction(this.url.reserve,{reservePlace:record.id,reserveDate:this.queryParam.reserveDate,periodList}).then(res=>{
            if(res.success){
                this.$message.success(res.message)
                this.loadData()
            }else{
                this.$message.error(res.message)
            }
        }).finally(()=>{
            this.$set(record,'listLoading',false)
        })
    },
    handleChangePeriod(record,period,e){
        console.log(record,period,e);
        if(!record.checkedList) 
        this.$set(record,'checkedList',{})
        if(e)
        this.$set(record.checkedList,period.periodId,period)
        
        else{
            let list =JSON.parse(JSON.stringify(record.checkedList))
            delete list[period.periodId]
            this.$set(record,'checkedList',list)
          // delete record.checkedList[period.periodId]
        // record.checkedList[period.periodId]=null
        }
    },

    PeriodList(record){
        if(record.available =='-1'){ //约满
            return false 
        }
        if(record.PeriodListVisible){
            this.$set(record,'PeriodListVisible',false)
            this.$set(record,'checkedList',{}) //重置已选择的数据
            return false
        }
        this.$set(record,'listLoading',true)
        getAction(this.url.placeReservedPeriod,{placeId:record.id,reserveDate:this.queryParam.reserveDate}).then(res=>{
            if(res.success){
                this.$set(record,'periodList',res.result)
            }else{
                this.$message.error(res.message)
            }
        }).finally(()=>{
            this.$set(record,'listLoading',false)
        })
    
        this.$set(record,'PeriodListVisible',true)
    },
    handleShowModal(record){
      this.$refs.modalForm.show(record,this.queryParam.reserveDate)
    },
    loadData(arg) {
      if(!this.url.list){
        this.$message.error("请设置url.list属性!")
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1;
      }
      var params = this.getQueryParams();//查询条件
      this.loading = true;
      postAction(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records;
          this.ipagination.total = res.result.total;
        }
        if(res.code===510){
          this.$message.warning(res.message)
        }
        this.loading = false;
      })
    },
    disabledDate(e){
        return e<moment()
      //  return e.format('YYYY-MM-DD')<moment().format('YYYY-MM-DD')
    },
    initDictConfig(){
        getReservePeriodList().then(res=>{
            if(res.success){
                this.$set(this.dict,'ReservePeriodList',res.result)
            }
        })
    }
   /*    headerRender({ value, type, onChange, onTypeChange }) {
       // console.log('tttttt',value);
      const start = 0;
      const end = 12;
      const monthOptions = [];

      const current = value.clone();
      const localeData = value.localeData();
      const months = [];
      for (let i = 0; i < 12; i++) {
        current.month(i);
        months.push(localeData.monthsShort(current));
      }

      for (let index = start; index < end; index++) {
        monthOptions.push(
          <a-select-option class="month-item" key={`${index}`}>
            {months[index]}
          </a-select-option>,
        );
      }
      const month = value.month();

      const year = value.year();
      const options = [];
      for (let i = year - 10; i < year + 10; i += 1) {
        options.push(
          <a-select-option key={i} value={i} class="year-item">
            {i}
          </a-select-option>,
        );
      }
      return (
        <div style={{ padding: '10px' }}>
          <a-row type="flex" justify="end">
            
            <a-col>
              <a-select
                size="small"
                dropdownMatchSelectWidth={false}
                class="my-year-select"
                onChange={newYear => {
                  const now = value.clone().year(newYear);
                  onChange(now);
                }}
                value={String(year)}
              >
                {options}
              </a-select>
            </a-col>
            <a-col>
              <a-select
                size="small"
                dropdownMatchSelectWidth={false}
                value={String(month)}
                onChange={selectedMonth => {
                  const newValue = value.clone();
                  newValue.month(parseInt(selectedMonth, 10));
                  onChange(newValue);
                }}
              >
                {monthOptions}
              </a-select>
            </a-col>
          </a-row>
        </div>
      );
    }, */
},
    }
  </script>
  <style scoped>
    @import '~@assets/less/common.less';
.example {
  text-align: center;
  background: rgba(0, 0, 0, 0.05);
  border-radius: 4px;
  margin-bottom: 20px;
  padding: 30px 50px;
  margin: 20px 0;
}

  </style>

  <style lang="less">
    .reserveList{
        .ant-fullcalendar table {
    height: 200px;
        }
    }

    .fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
  </style>