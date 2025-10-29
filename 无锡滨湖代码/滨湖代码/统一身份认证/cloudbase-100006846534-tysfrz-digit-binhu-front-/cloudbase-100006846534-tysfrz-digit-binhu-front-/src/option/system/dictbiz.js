export const optionParent = {
  height: 'auto',
  calcHeight: 30,
  tip: false,
  searchShow: true,
  searchMenuSpan: 10,
  searchLabelWidth: 72,
  border: true,
  index: true,
  selection: true,
  editBtn:false,
  delBtn:false,
  viewBtn: true,
  dialogWidth: 880,
  dialogClickModal: false,
  columnBtn: false,
  align: "center",
  menuWidth: 300,
  stripe: true,
  column: [
    {
      label: "字典编号",
      prop: "code",
      search: true,
      slot: true,
      span: 24,
      rules: [
        {
          required: true,
          message: "请输入字典编号",
          trigger: "blur"
        }
      ]
    },
    {
      label: "字典名称",
      prop: "dictValue",
      search: true,
      align: "center",
      rules: [
        {
          required: true,
          message: "请输入字典名称",
          trigger: "blur"
        }
      ]
    },
    {
      label: "字典排序",
      prop: "sort",
      type: "number",
      align: "right",
      hide: true,
      rules: [
        {
          required: true,
          message: "请输入字典排序",
          trigger: "blur"
        }
      ]
    },
    {
      label: "封存",
      prop: "isSealed",
      type: "switch",
      align: "center",
      dicData: [
        {
          label: "否",
          value: 0
        },
        {
          label: "是",
          value: 1
        }
      ],
      value: 0,
      slot: true,
      rules: [
        {
          required: true,
          message: "请选择封存",
          trigger: "blur"
        }
      ]
    },
    {
      label: "字典备注",
      prop: "remark",
      hide: true
    }
  ]
};

export const optionChild = {
  height: 'auto',
  calcHeight: 95,
  tip: false,
  searchShow: true,
  searchMenuSpan: 10,
  tree: true,
  border: true,
  index: true,
  selection: true,
  viewBtn: true,
  menuWidth: 300,
  dialogWidth: 880,
  dialogClickModal: false,
  editBtn: false,
  delBtn: false,
  columnBtn: false,
  align: "center",
  stripe: true,
  column: [
    {
      label: "字典编号",
      prop: "code",
      addDisabled: true,
      editDisabled: true,
      search: true,
      span: 24,
      rules: [
        {
          required: true,
          message: "请输入字典编号",
          trigger: "blur"
        }
      ]
    },
    {
      label: "字典名称",
      prop: "dictValue",
      search: true,
      align: "center",
      rules: [
        {
          required: true,
          message: "请输入字典名称",
          trigger: "blur"
        }
      ]
    },
    {
      label: "上级字典",
      prop: "parentId",
      type: "tree",
      dicData: [],
      hide: true,
      props: {
        label: "title"
      },
      addDisabled: true,
      editDisabled: true,
      rules: [
        {
          required: false,
          message: "请选择上级字典",
          trigger: "click"
        }
      ]
    },
    {
      label: "字典键值",
      prop: "dictKey",
      rules: [
        {
          required: true,
          message: "请输入字典键值",
          trigger: "blur"
        }
      ]
    },
    {
      label: "字典排序",
      prop: "sort",
      type: "number",
      align: "right",
      hide: true,
      rules: [
        {
          required: true,
          message: "请输入字典排序",
          trigger: "blur"
        }
      ]
    },
    {
      label: "封存",
      prop: "isSealed",
      type: "switch",
      align: "center",
      dicData: [
        {
          label: "否",
          value: 0
        },
        {
          label: "是",
          value: 1
        }
      ],
      value: 0,
      slot: true,
      rules: [
        {
          required: true,
          message: "请选择封存",
          trigger: "blur"
        }
      ]
    },
    {
      label: "字典备注",
      prop: "remark",
      hide: true
    }
  ]
};
