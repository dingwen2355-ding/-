<template>
  <a-modal
    :title="title"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 85%;overflow-y: hidden">
    <div>
      <a-tabs default-active-key="1">
        <a-tab-pane force-render key="1" force tab="基本信息">
          <a-form :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" :form="form">
            <a-row :gutter="8">
              <a-col :span="6">
                <a-form-item label="人员姓名">
                  <a-input placeholder="请输入姓名" v-decorator.trim="['name',validatorRules.name]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="人员性别">
                  <a-radio-group v-decorator.trim="['sex',validatorRules.sex]">
                    <a-radio value="男">
                      男
                    </a-radio>
                    <a-radio value="女">
                      女
                    </a-radio>
                  </a-radio-group>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="宗教信仰">
                  <j-dict-select-tag dict-code="faith" placeholder="请选择"
                                     v-decorator="['faith', { rules: validatorRules.faith }]"
                                     @input="handInputFaith"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="兵役状况">
                  <j-dict-select-tag dict-code="active_status" placeholder="请选择"
                                     v-decorator="['activeStatus', { rules: validatorRules.activeStatus }]"
                                     @input="handInputActive"></j-dict-select-tag>
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="6">
                <a-form-item label="证件号码">
                  <a-input placeholder="请输入证件号码" v-decorator.trim="['idCard',validatorRules.idCard]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="民族">
                  <j-dict-select-tag dict-code="nation" placeholder="请选择"
                                     v-decorator="['nation', { rules: validatorRules.nation }]"
                                     @input="handInputNation"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="户籍区划">
                  <a-cascader :options="options" placeholder="请选择"
                              v-decorator.trim="['registerPlace',validatorRules.registerPlace]"
                              :getPopupContainer="triggerNode => triggerNode.parentNode" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="户籍详细地址">
                  <a-input v-model="form.country" placeholder="请输入户籍详细地址"
                           v-decorator.trim="['domicileAddress',validatorRules.domicileAddress]" />
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="6">
                <a-form-item label="户籍责任区">
                  <a-cascader :options="options" placeholder="请选择"
                              v-decorator.trim="['domicilePrefecture',validatorRules.domicilePrefecture]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="联系电话">
                  <a-input placeholder="请输入联系电话" v-decorator.trim="['phone',validatorRules.phone]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="住房性质">
                  <j-dict-select-tag dict-code="nature_type" placeholder="请选择"
                                     v-decorator="['natureType', { rules: validatorRules.natureType }]"
                                     @input="handInputNatureType"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="人员类别">
                  <a-select placeholder="请选择" v-decorator.trim="['peopleStatus',validatorRules.peopleStatus]"
                            :getPopupContainer="triggerNode => triggerNode.parentNode">
                    <a-select-option value="">请选择</a-select-option>
                    <a-select-option value="户籍人口">户籍人口</a-select-option>
                    <a-select-option value="流动人口">流动人口</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="6">
                <a-form-item label="文化程度">
                  <j-dict-select-tag dict-code="education" placeholder="请选择"
                                     v-decorator="['education', { rules: validatorRules.education }]"
                                     @input="handInputEducation"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="政治面貌">
                  <j-dict-select-tag dict-code="polical" placeholder="请选择"
                                     v-decorator="['politicsStatus', { rules: validatorRules.politicsStatus }]"
                                     @input="handInputPoliticsStatus"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="婚姻状况">
                  <j-dict-select-tag dict-code="marital_status" placeholder="请选择"
                                     v-decorator="['maritalStatus', { rules: validatorRules.maritalStatus }]"
                                     @input="handInputMaritalStatus"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="人员标签">
                  <j-multi-select-tag dict-code="PEOPLE_TAG" placeholder="请选择"
                                      v-decorator="['peopleTag', { rules: validatorRules.peopleTag }]"
                                      @input="handInput"></j-multi-select-tag>
                </a-form-item>
              </a-col>

            </a-row>
          </a-form>
        </a-tab-pane>
        <a-tab-pane force-render key="2" tab="房屋地址">
          <a-form :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" :form="form">
            <a-row :gutter="8">
              <a-col :span="12">
                <a-form-item label="选择社区">
                  <a-cascader :options="communityOptions" placeholder="请选择"
                              v-decorator.trim="['communityCascader',validatorRules.communityCascader]"
                              :getPopupContainer="triggerNode => triggerNode.parentNode"
                              @change="handChangeCommunityId"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="选择房屋">
                  <a-cascader :options="houseOptions" placeholder="请选择"
                              v-decorator.trim="['houseCascader',validatorRules.houseCascader]"
                              :getPopupContainer="triggerNode => triggerNode.parentNode"
                              @change="handChangeHouseId" />
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="12">
                <a-form-item label="户籍状态">
                  <j-dict-select-tag dict-code="register_status" placeholder="请选择"
                                     v-decorator="['registerStatus', { rules: validatorRules.registerStatus }]"
                                     @input="handInputRegisterStatus"></j-dict-select-tag>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="与户主关系">
                  <j-dict-select-tag dict-code="owner_relationship" placeholder="请选择"
                                     v-decorator.trim="['ownerRelationship',validatorRules.ownerRelationship]"
                                     @input="handOwnerRelationship" />
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="12">
                <a-form-item label="是否常住">
                  <a-radio-group placeholder="请选择"
                                 v-decorator.trim="['residentPopulation',validatorRules.residentPopulation]">
                    <a-radio value="1">是</a-radio>
                    <a-radio value="0">否</a-radio>
                  </a-radio-group>
                </a-form-item>
              </a-col>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['communityCode',validatorRules.communityCode]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['communityId',validatorRules.communityId]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['communityName',validatorRules.communityName]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['streetCode',validatorRules.streetCode]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['streetName',validatorRules.streetName]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['livePlaceAddress',validatorRules.livePlaceAddress]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['houseId',validatorRules.houseId]" />
              </a-form-item>
              <a-form-item style="display: none">
                <a-input placeholder="请选择" v-decorator.trim="['roomNumber',validatorRules.roomNumber]" />
              </a-form-item>

            </a-row>
          </a-form>
        </a-tab-pane>
        <a-tab-pane force-render key="3" tab="学历与职业">
          <a-form :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" :form="form">
            <a-row :gutter="8">
              <a-col :span="12">
                <a-form-item label="单位/学校">
                  <a-input placeholder="请输入" v-decorator.trim="['organization',validatorRules.organization]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="单位或学校地址">
                  <a-input placeholder="请选择"
                           v-decorator.trim="['organizationAddress',validatorRules.organizationAddress]" />
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="12">
                <a-form-item label="单位或学校电话">
                  <a-input placeholder="请选择"
                           v-decorator.trim="['organizationPhone',validatorRules.organizationPhone]" />
                </a-form-item>
              </a-col>
              <!--              <a-col :span="12">-->
              <!--                <a-form-item label="产业类型">-->
              <!--                  <j-dict-select-tag dict-code="owner_relationship" placeholder="请选择"-->
              <!--                                     v-decorator.trim="['industryType',validatorRules.industryType]"-->
              <!--                                     @input="handOwnerRelationship" />-->
              <!--                </a-form-item>-->
              <!--              </a-col>-->
              <a-col :span="12">
                <a-form-item label="进校/进单位时间">
                  <a-date-picker v-decorator.trim="['inOrganizationTime',validatorRules.inOrganizationTime]" />
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="12">
                <a-form-item label="是否办理保险">
                  <a-radio-group placeholder="请选择" v-decorator.trim="['insuranceFlag',validatorRules.insuranceFlag]">
                    <a-radio value="是">是</a-radio>
                    <a-radio value="否">否</a-radio>
                  </a-radio-group>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="是否签订劳动合同">
                  <a-radio-group placeholder="请选择" v-decorator.trim="['signFlag',validatorRules.signFlag]">
                    <a-radio value="是">是</a-radio>
                    <a-radio value="否">否</a-radio>
                  </a-radio-group>
                </a-form-item>
              </a-col>

            </a-row>
          </a-form>
        </a-tab-pane>
        <a-tab-pane force-render key="4" tab="其他信息">
          <a-form :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" :form="form">
            <a-row :gutter="8">
              <a-col :span="6">
                <a-form-item label="交通工具">
                  <j-dict-select-tag dict-code="vehicle_type" placeholder="请选择"
                                     v-decorator.trim="['vehicleType',validatorRules.vehicleType]"
                                     @input="handInputVehicleType" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="车牌号">
                  <a-input placeholder="请输入" v-decorator.trim="['vehicleNumber',validatorRules.vehicleNumber]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="身高">
                  <a-input type="number" v-decorator.trim="['height',validatorRules.height]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="网络联系方式">
                  <a-input v-decorator.trim="['netContactWay',validatorRules.netContactWay]" />
                </a-form-item>
              </a-col>

            </a-row>
            <a-row :gutter="8">
              <a-col :span="6">
                <a-form-item label="来本地日期">
                  <a-date-picker placeholder="请选择" v-decorator.trim="['arrivalTime',validatorRules.arrivalTime]" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="登记日期">
                  <a-date-picker placeholder="请选择" v-decorator.trim="['recordTime',validatorRules.recordTime]" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-modal>
</template>

<script>

import JMultiSelectTag from '@comp/dict/JMultiSelectTag.vue'
import { getAction, postAction, putAction } from '@api/manage'
import pick from 'lodash.pick'
import moment from 'moment'
import 'moment/locale/zh-cn'

moment.locale('zh-cn')
export default {
  name: 'PeopleModal',
  components: { JMultiSelectTag },
  data() {
    return {
      options: [],
      communityOptions: [],
      houseOptions: [],
      title: '操作',
      modalWidth: '80vw',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      record: {},
      isIdCardVisible: false,
      isPhoneVisible: false,
      validatorRules: {
        name: {
          rules: [
            {
              required: true,
              message: '请输入姓名!'
            }
          ]
        },
        country: {
          rules: [
            {
              required: true,
              message: '请输入国家!'
            }
          ]
        },
        idCard: {
          rules: [
            {
              required: true,
              message: '请输入身份证号!'
            },
            {
              pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
              message: '请输入正确的身份证号!'
            }
          ]
        },
        phone: {
          rules: [
            {
              pattern: /^1[3456789]\d{9}$/,
              message: '手机号码格式不正确!'
            }
          ]
        },
        peopleStatus: {
          rules: [
            {
              required: true,
              message: '请选择!'
            }
          ]
        },
        communityCascader: {
          rules: [
            {
              required: true,
              message: '请选择!'
            }
          ]
        },
        residentPopulation: {
          rules: [
            {
              required: true,
              message: '请选择!'
            }
          ]
        },
        ownerRelationship: {
          rules: [
            {
              required: true,
              message: '请选择!'
            }
          ]
        },
        height: {
          rules: [
            {
              pattern: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
              message: '请输入正确的身高!'
            }
          ]
        }
      },
      url: {
        area: '/person/getArea',
        community: '/person/getCommunity',
        house: '/person/getHouse'
      }
    }
  },
  created() {
  },
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.record = record
      this.model = Object.assign({}, record)
      // 点击编辑时, 根据社区代码获取房屋的下拉选项
      if (record.communityName) {
        getAction(this.url.house, { sqdm: record.communityName }).then((res) => {
          if (res.success) {
            this.houseOptions = res.result.options
          }
        })
      }
      console.log(record.roomNumber)
      console.log(this.houseOptions)
      // 回显社区和房屋
      this.model.communityCascader = this.findPath(record.communityCode, this.communityOptions)
      this.model.houseCascader = this.findPath(record.roomNumber,this.houseOptions)

      // 将model中的registerPlace和domicilePrefecture转换成数组
      if (this.model.registerPlace) {
        this.model.registerPlace = this.model.registerPlace.split('/')
      }
      if (this.model.domicilePrefecture) {
        this.model.domicilePrefecture = this.model.domicilePrefecture.split('/')
      }
      this.visible = true
      /**
       * 将model中的数据赋值给表单
       */
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'name', 'sex', 'peopleTag', 'education', 'politicsStatus', 'maritalStatus',
          'faith', 'phone', 'idCard', 'birth', 'country', 'registerPlace', 'domicileAddress', 'domicilePrefecture', 'homePhone', 'peopleStatus', 'activeStatus',
          'nation', 'organization', 'organizationAddress', 'organizationPhone', 'industryType', 'insuranceFlag', 'signFlag', 'inOrganizationTime',
          'livePlaceAddress', 'residentPopulation', 'ownerRelationship', 'registerStatus', 'natureType', 'vehicleType', 'vehicleNumber', 'netContactWay',
          'arrivalTime', 'recordTime', 'height', 'communityId', 'houseId', 'roomNumber', 'communityName', 'streetCode', 'streetName', 'communityCode', 'communityCascader', 'houseCascader'))
      })

    },
    /**
     * 关闭弹窗
     */
    close() {
      this.$emit('close')
      this.visible = false
    },
    /**
     * 确认提交
     */
    handleOk() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          let formData = Object.assign(this.model, values)
          that.confirmLoading = true
          // 处理数据 如果是数组则转换成字符串,如果为null或者undefined则转换成空字符串
          if (formData.registerPlace === null || formData.registerPlace === undefined) {
            formData.registerPlace = ''
          } else {
            formData.registerPlace = formData.registerPlace.join('/')
          }
          if (formData.domicilePrefecture === null || formData.domicilePrefecture === undefined) {
            formData.domicilePrefecture = ''
          } else {
            formData.domicilePrefecture = formData.domicilePrefecture.join('/')
          }

          // 时间格式转换
          if (formData.arrivalTime !== undefined) {
            formData.arrivalTime = moment(formData.arrivalTime).format('YYYY-MM-DD HH:mm:ss')
          }
          if (formData.recordTime !== undefined) {
            formData.recordTime = moment(formData.recordTime).format('YYYY-MM-DD HH:mm:ss')
          }
          if (formData.inOrganizationTime !== undefined) {
            formData.inOrganizationTime = moment(formData.inOrganizationTime).format('YYYY-MM-DD HH:mm:ss')
          }

          let obj
          console.log(formData)
          if (this.title === '新增') {
            obj = postAction('/person/add', formData)
          } else if (this.title === '编辑') {
            obj = putAction('/person/edit', formData)
          }
          // 提交数据
          obj.then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              that.$emit('ok')
            } else {
              that.$message.warning(res.message)
            }
          }).finally(() => {
            that.confirmLoading = false
            that.close()
          })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    /**
     * 切换是否显示完整的身份证号
     */
    handelOpenIdCard() {
      this.isIdCardVisible = !this.isIdCardVisible // 切换是否显示完整的身份证号
    },
    /**
     * 选择社区,变化时触发
     * @param value
     */
    handChangeCommunityId(value, selectedOptions) {
      this.form.setFieldsValue({ houseId: '' })
      // 如果value为空,则直接返回
      if (value.length === 0) {
        return
      }
      console.log(value)
      console.log(selectedOptions)
      this.form.setFieldsValue({
        communityCode: selectedOptions[1].code,
        communityId: selectedOptions[1].code,
        streetCode: selectedOptions[0].code,
        streetName: value[value.length - 2],
        communityName: value[value.length - 1]
      })
      this.$nextTick(() => {
        console.log(this.form.getFieldsValue())
      })
      // 先清空houseOptions
      this.houseOptions = []
      const sqdm = value[value.length - 1]
      getAction(this.url.house, { sqdm: sqdm }).then((res) => {
        if (res.success) {
          this.houseOptions = res.result.options
        }
      })
    },
    handChangeHouseId(value, selectedOptions) {
      if (value.length === 0) {
        return
      }
      console.log(value)
      console.log(selectedOptions)
      let livePlaceAddress = ''
      for (let i = 0; i < selectedOptions.length; i++) {
        livePlaceAddress = livePlaceAddress + selectedOptions[i].label
      }
      this.form.setFieldsValue({
        roomNumber: selectedOptions[selectedOptions.length - 1].code,
        livePlaceAddress: livePlaceAddress
      })

      console.log(this.form.getFieldsValue())

    },
    /**
     * 获取区域数据
     * @param value 传入的区域编码
     * @param list 传入的区域列表
     * @param path 传入的区域路径
     * @returns {*|null|*[]} 返回区域路径
     */
    findPath(value, list, path = []) {
      for (const item of list) {
        if (item.code === value) {
          return [...path, item.value]
        }
        if (item.children) {
          const foundPath = this.findPath(value, item.children, [...path, item.value])
          if (foundPath) {
            return foundPath
          }
        }
      }
      return null
    },
    /**
     * 切换是否显示完整的手机号
     */
    handelOpenPhone() {
      this.isPhoneVisible = !this.isPhoneVisible // 切换是否显示完整的身份证号
    },
    /**
     * 选择人员标签,变化时触发
     * @param value
     */
    handInput(value) {
      this.form.setFieldsValue({ peopleTag: value })
      this.form.validateFields(['peopleTag'])
    },
    /**
     * 选择兵役状况,变化时触发
     * @param value
     */
    handInputActive(value) {
      this.form.setFieldsValue({ activeStatus: value })
      this.form.validateFields(['activeStatus'])
    },
    /**
     * 选择宗教信仰,变化时触发
     * @param value
     */
    handInputFaith(value) {
      this.form.setFieldsValue({ faith: value })
      this.form.validateFields(['faith'])
    },
    /**
     * 选择文化程度,变化时触发
     * @param value
     */
    handInputEducation(value) {
      this.form.setFieldsValue({ education: value })
      this.form.validateFields(['education'])
    },
    /**
     * 选择婚姻状况,变化时触发
     * @param value
     */
    handInputMaritalStatus(value) {
      this.form.setFieldsValue({ maritalStatus: value })
      this.form.validateFields(['maritalStatus'])
    },
    /**
     * 选择政治面貌,变化时触发
     * @param value
     */
    handInputPoliticsStatus(value) {
      this.form.setFieldsValue({ politicsStatus: value })
      this.form.validateFields(['politicsStatus'])
    },
    /**
     * 选择民族,变化时触发
     * @param value
     */
    handInputNation(value) {
      this.form.setFieldsValue({ nation: value })
      this.form.validateFields(['nation'])
    },
    /**
     * 选择与户主关系,变化时触发
     * @param value
     */
    handOwnerRelationship(value) {
      this.form.setFieldsValue({ ownerRelationship: value })
      this.form.validateFields(['ownerRelationship'])
    },
    /**
     * 选择户籍状态,变化时触发
     * @param value
     */
    handInputRegisterStatus(value) {
      this.form.setFieldsValue({ registerStatus: value })
      this.form.validateFields(['registerStatus'])
    },
    /**
     * 选择住房性质,变化时触发
     * @param value
     */
    handInputNatureType(value) {
      this.form.setFieldsValue({ natureType: value })
      this.form.validateFields(['natureType'])
    },
    /**
     * 选择交通工具,变化时触发
     * @param value
     */
    handInputVehicleType(value) {
      this.form.setFieldsValue({ vehicleType: value })
      this.form.validateFields(['vehicleType'])
    },
    /**
     * 选择社区,变化时触发
     * @param value
     */
    handInputCommunityId(value) {
      this.form.setFieldsValue({ communityId: value })
      this.form.validateFields(['communityId'])
    },
    /**
     * 选择房屋,变化时触发
     * @param value
     */
    handInputHouseId(value) {
      this.form.setFieldsValue({ houseId: value })
      this.form.validateFields(['houseId'])
    }
  },
  computed: {
    computeYear() {
      const idCard = this.record.idCard
      // 从身份证中提取出生年份
      return idCard.substring(6, 10)
    },
    computeMonth() {
      const idCard = this.record.idCard
      // 从身份证中提取出生年份
      return idCard.substring(10, 12)
    },
    computeIdCard: {
      get() {
        const idCard = this.record.idCard
        if (this.isIdCardVisible) {
          return idCard // 直接返回完整的身份证号
        } else {
          // 从身份证中提取出生年份
          return idCard.substring(0, 3) + '****' + idCard.substring(idCard.length - 4)
        }
      },
      set() {

      }
    },
    computePhone: {
      get() {
        const phone = this.record.phone
        if (this.isPhoneVisible) {
          return phone // 直接返回完整的身份证号
        } else if (phone !== undefined && phone !== null && phone !== '') {
          // 从身份证中提取出生年份
          return phone.substring(0, 3) + '****' + phone.substring(phone.length - 4)
        } else {
          return ''
        }
      },
      set() {

      }
    }
  },
  beforeMount() {
    /**
     * 获取区域
     */
    getAction(this.url.area, '').then((res) => {
      if (res.success) {
        this.options = res.result.options
      }
    })

    /**
     * 获取社区
     */
    getAction(this.url.community, '').then((res) => {
      if (res.success) {
        this.communityOptions = res.result.options
      }
    })
  }
}
</script>

<style scoped>

</style>