/*
 * @Descripttion: 混入
 * @version:
 * @Author: GM
 * @Date: 2021-03-10 16:31:34
 * @LastEditors: GM
 * @LastEditTime: 2021-10-29 14:24:47
 */

export default {
  data() {
    return {
      rules: {
        legalPhone: [{ validator: this.mobilephoneValidate }],
        legalIdCard: [{ validator: this.checkRegNo }],
        card: [
          {
            required: true,
            message: "请输入公民身份号码",
            trigger: "blur"
          },
          { validator: this.checkRegNo }
        ],

        tel: [
          {
            required: true,
            message: "请输入联系方式",
            trigger: "blur"
          },
          { validator: this.mobilephoneValidate }
        ],
        ownerPhone: [{ validator: this.ownerTelValidate }],
        ownerCard: [{ validator: this.ownerCardValidate }],
        phone: [
          {
            required: true,
            message: "请输入联系方式",
            trigger: "blur"
          },
          { validator: this.mobilephoneValidate }
        ],
        principalContact: [
          {
            trigger: ["blur", "change"]
          },
          { validator: this.principalContactValidate }
        ],
        principalIdNumber: [{ validator: this.checkRegNo }],
        representativeContactWay: [
          {
            required: true,
            message: "请输入负责人联系方式",
            trigger: "blur"
          },
          { validator: this.mobilephoneValidate }
        ],
        logEnterpriseLeaderTel: [
          {
            required: true,
            message: "请输入企业负责人联系方式",
            trigger: "blur"
          },
          { validator: this.mobilephoneValidate }
        ],
        logEnterpriseTel: [
          {
            required: true,
            message: "请输入企业联系方式",
            trigger: "blur"
          },
          { validator: this.mobilephoneValidate }
        ],
        logEnterpriseLeaderIdNumber: [{ validator: this.checkRegNo }]
      },
      businessRules: {
        cardNum: [{ validator: this.checkRegNo }],
        accountIdNumber: [{ validator: this.accountIdNumberCheck }],
        accountTel: [{ validator: this.accountTelValidate }],
        petitionReceiveTel: [
          {
            required: true,
            message: "请输入联系方式",
            trigger: "blur"
          },
          { validator: this.petitionReceiveTelValidate }
        ],
        guardianCard: [{ validator: this.guardianCardValidate }],
        familyTel: [{ validator: this.familyTelValidate }],
        guardianTel: [{ validator: this.guardianTellValidate }],
        helperTel: [
          {
            required: true,
            message: "请输入帮扶人联系方式",
            trigger: "blur"
          },
          { validator: this.helperTelValidate }
        ],
        controlTel: [{ validator: this.controlTelValidate }]
      },
      houseRules: {
        accountIdNumber: [{ validator: this.accountIdNumberCheck }],
        accountTel: [{ validator: this.accountTelValidate }]
      },
      flowRules: {
        cardNum: [{ validator: this.checkRegNo }]
      }
    };
  },
  created() {},
  methods: {
    accountTelValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback, "户主");
    },
    ownerTelValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback, "房主");
    },
    ownerCardValidate(rule, value, callback) {
      this.checkRegNo(rule, value, callback, "房主");
    },
    guardianCardValidate(rule, value, callback) {
      this.checkRegNo(rule, value, callback, "监护人");
    },
    guardianTellValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback, "监护人");
    },
    familyTelValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback, "家庭主要成员");
    },
    helperTelValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback, "帮扶人");
    },
    controlTelValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback, "管控人");
    },
    petitionReceiveTelValidate(rule, value, callback) {
      this.mobilephoneValidate(rule, value, callback);
    },
    principalContactValidate(rule, value, callback) {
      this.mobilephoneValidate(
        rule,
        value,
        callback,
        "整治牵头单位负责人联系方式"
      );
    },
    // 电话验证
    mobilephoneValidate(rule, value, callback, errorMsg) {
      // 主要就是添加一个对undefined和空串的判断
      if (typeof value === "undefined" || value === "") {
        callback();
      } else {
        let error = "";
        if (typeof errorMsg == "string") {
          error = errorMsg;
        }
        let regPone = null;
        let mobile = /^1(3|4|5|6|7|8|9)\d{9}$/; //最新16手机正则
        let tel = /^(0[0-9]{2,3}\-)([2-9][0-9]{4,7})+(\-[0-9]{1,4})?$/; //座机
        if (value.charAt(0) == 0) {
          // charAt查找第一个字符方法，用来判断输入的是座机还是手机号
          regPone = tel;
          if (!regPone.test(value)) {
            callback(
              new Error(
                "请输入正确" + error + "电话格式(座机格式'区号-座机号码')"
              )
            );
          }
        } else {
          regPone = mobile;
          if (!regPone.test(value)) {
            callback(new Error("请输入正确" + error + "联系方式"));
          }
        }
        callback();
      }
    },
    accountIdNumberCheck(rule, idcode, callback) {
      this.checkRegNo(rule, idcode, callback, "户主");
    },

    // 身份证号码校验
    checkRegNo(rule, idcode, callback, errorMsg) {
      if (typeof idcode === "undefined" || idcode === "") {
        callback();
      } else {
        // 加权因子
        var weight_factor = [
          7,
          9,
          10,
          5,
          8,
          4,
          2,
          1,
          6,
          3,
          7,
          9,
          10,
          5,
          8,
          4,
          2
        ];
        // 校验码
        var check_code = [
          "1",
          "0",
          "X",
          "9",
          "8",
          "7",
          "6",
          "5",
          "4",
          "3",
          "2"
        ];

        var code = idcode + "";
        var last = idcode[17]; //最后一位

        var seventeen = code.substring(0, 17);

        // ISO 7064:1983.MOD 11-2
        // 判断最后一位校验码是否正确
        var arr = seventeen.split("");
        var len = arr.length;
        var num = 0;
        for (var i = 0; i < len; i++) {
          num = num + arr[i] * weight_factor[i];
        }

        // 获取余数
        var resisue = num % 11;
        var last_no = check_code[resisue];

        // 格式的正则
        // 正则思路
        /*
        第一位不可能是0
        第二位到第六位可以是0-9
        第七位到第十位是年份，所以七八位为19或者20
        十一位和十二位是月份，这两位是01-12之间的数值
        十三位和十四位是日期，是从01-31之间的数值
        十五，十六，十七都是数字0-9
        十八位可能是数字0-9，也可能是X
        */
        var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/;

        // 判断格式是否正确
        var format = idcard_patter.test(idcode);
        let error = "";
        if (typeof errorMsg == "string") {
          error = errorMsg;
        }
        // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
        return last === last_no && format
          ? callback()
          : callback(new Error("请输入正确" + error + "公民身份号码"));
      }
    },
    // 数字大小验证
    checkNumber(rule, value, callback) {
      if (value == undefined || value == "" || value == null) {
        callback();
      } else {
        // 将输入的值转为数字
        var val = Number(value);
        if (isNaN(val)) {
          callback(new Error("请输入数字"));
          return;
        }
        // 正则表达式校验输入的数字是否在0-1000之内并且属于整数
        if (val > 1000 || val <= 0) {
          callback(new Error("请输入1-1000内的数值"));
          return;
        }
        callback();
      }
    },
    // 数字大小验证。必须为整数
    checkNumberNotFloat(rule, value, callback) {
      if (value == undefined || value == "" || value == null) {
        callback();
      } else {
        // 将输入的值转为数字
        var val = Number(value);
        // 正则表达式校验输入的数字是否在0-1000之内并且属于整数
        if (typeof value == "string" && value.indexOf(".") != -1) {
          callback(new Error("请输入整数"));
          return;
        }
        if (isNaN(val)) {
          callback(new Error("请输入数字"));
          return;
        }
        if (val > 1000 || val <= 0) {
          callback(new Error("请输入1-1000内的整数"));
          return;
        }
        callback();
      }
    },
    // 再次验证
    againRules(a, b = "form") {
      this.$refs[b].validateField(a);
    }
  }
};
