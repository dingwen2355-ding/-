// 0：默认；1：X光；2：纯色；3：水晶体；
// 4：暗黑；5：科幻；6：扩散；
var showStyleList = [
  {
    id: 1,
    type: "默认",
    label: "theDefault"
  },
  {
    id: 2,
    type: "科幻",
    label: "science",
    children: [
      {
        id: 2001,
        typeName: "地形模型",
        cimtypes: [8,9, 14, 20, 28,],
        style: 5,
        rgba: [0.142, 0.416, 0.444, 1]
      },
      {
        id: 2002,
        typeName: "水系模型",
        cimtypes: [12, 18, 23, 31,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 2003,
        typeName: "行政区模型",
        cimtypes: [10, 15,],
        style: 3,
        rgba: [0.722, 0.910, 0.769, 1]
      },
      {
        id: 2004,
        typeName: "建筑模型",
        cimtypes: [16, 21, 29, 39, 44,],
        style: 5,
        rgba: [0.146, 0.514, 0.561, 1]
      },
      {
        id: 2006,
        typeName: "交通模型",
        cimtypes: [13, 17, 22, 30, 40, 45,],
        style: 3,
        rgba: [0, 0.808, 0.961, 1]
      },
      {
        id: 2007,
        typeName: "场地模型",
        cimtypes: [26, 34, 41, 46,],
        style: 5,
        rgba: [0.142, 0.416, 0.444, 1]
      },
      {
        id: 2008,
        typeName: "地下空间模型",
        cimtypes: [36, 43, 48,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 2009,
        typeName: "地质模型",
        cimtypes: [37,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 2010,
        typeName: "植被模型",
        cimtypes: [19, 24, 32,],
        style: 5,
        rgba: [0.142, 0.416, 0.444, 1]
      },
    ]
  },
  {
    id: 3,
    type: "扩散",
    label: "spreadOf",
    children: [
      {
        id: 3001,
        typeName: "地形模型",
         cimtypes: [8,9, 14, 20, 28,],
        style: 6,
        rgba: [0, 0.459, 1, 1]
      },
      {
        id: 3002,

        typeName: "水系模型",
        cimtypes: [12, 18, 23, 31,],
        style: 3,
        rgba: [0.314, 0.526, 0.361, 1]
      },
      {
        id: 3003,

        typeName: "行政区模型",
        cimtypes: [10, 15,],
        style: 3,
        rgba: [0.338, 0.616, 0.910, 1]
      },
      {
        id: 3004,
        typeName: "建筑模型",
        cimtypes: [16, 21, 29, 39, 44,],
        style: 6,
        rgba: [0.163, 0.769, 0.989, 1]
      },
      // {
      //   id:3005,
      //   typeName:"建筑内部模型",
      //   style:,
      //   rgba:[]
      // },
      {
        id: 3006,
        typeName: "交通模型",
        cimtypes: [13, 17, 22, 30, 40, 45,],
        style: 3,
        rgba: [0.091, 0.514, 0.714, 1]
      },
      {
        id: 3007,
        typeName: "场地模型",
        cimtypes: [26, 34, 41, 46,],
        style: 6,
        rgba: [0, 0.459, 1, 1]
      },
      {
        id: 3008,
        typeName: "地下空间模型",
        cimtypes: [36, 43, 48,],
        style: 6,
        rgba: [0, 1, 0.981, 1]
      },
      {
        id: 3009,
        typeName: "地质模型",
        cimtypes: [37,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 3010,
        typeName: "植被模型",
        cimtypes: [19, 24, 32,],
        style: 6,
        rgba: [0, 0.459, 1, 1]
      },
    ]
  },
  {
    id: 4,
    type: "暗黑",
    label: "dark",
    children: [
      {
        id: 4001,
        typeName: "地形模型",
         cimtypes: [8,9, 14, 20, 28,],
        style: 6,
        rgba: [0.420, 0.585, 1, 1]
      },
      {
        id: 4002,

        typeName: "水系模型",
        cimtypes: [12, 18, 23, 31,],
        style: 3,
        rgba: [0.314, 0.526, 0.361, 1]
      },
      {
        id: 4003,

        typeName: "行政区模型",
        cimtypes: [10, 15,],
        style: 3,
        rgba: [0.350, 0.608, 0.910, 1]
      },
      {
        id: 4004,
        typeName: "建筑模型",
        cimtypes: [16, 21, 29, 39, 44,],
        style: 4,
        rgba: [0.373, 0.593, 0.961, 1]
      },
      // {
      //   id:4005,
      //   typeName:"建筑内部模型",
      //   style:,
      //   rgba:[]
      // },
      {
        id: 4006,
        typeName: "交通模型",
        cimtypes: [13, 17, 22, 30, 40, 45,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 4007,
        typeName: "场地模型",
        cimtypes: [26, 34, 41, 46,],
        style: 6,
        rgba: [0.420, 0.585, 1, 1]
      },
      {
        id: 4008,
        typeName: "地下空间模型",
        cimtypes: [36, 43, 48,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 4009,
        typeName: "地质模型",
        cimtypes: [37,],
        style: 3,
        rgba: [0.961, 0.734, 0.550, 1]
      },
      {
        id: 4010,
        typeName: "植被模型",
        cimtypes: [19, 24, 32,],
        style: 6,
        rgba: [0.420, 0.585, 1, 1]
      },
    ]
  },
  {
    id: 5,
    type: "水晶",
    label: "crystal",
    children: [
      {
        id: 5001,
        typeName: "地形模型",
         cimtypes: [8,9, 14, 20, 28,],
        style: 6,
        rgba: [0.428, 0.193, 1, 1]
      },
      {
        id: 5002,

        typeName: "水系模型",
        cimtypes: [12, 18, 23, 31,],
        style: 3,
        rgba: [0, 0.687, 0.961, 1]
      },
      {
        id: 5003,
        typeName: "行政区模型",
        cimtypes: [10, 15,],
        style: 3,
        rgba: [0, 0.844, 0.910, 1]
      },
      {
        id: 5004,
        typeName: "建筑模型",
        cimtypes: [16, 21, 29, 39, 44,],
        style: 3,
        rgba: [0.102, 0.561, 0.910, 1]
      },
      // {
      //   id:5005,
      //   typeName:"建筑内部模型",
      //   style:,
      //   rgba:[]
      // },
      {
        id: 5006,
        typeName: "交通模型",
        cimtypes: [13, 17, 22, 30, 40, 45,],
        style: 3,
        rgba: [0.538, 0.377, 0.302, 1]
      },
      {
        id: 5007,
        typeName: "场地模型",
        cimtypes: [26, 34, 41, 46,],
        style: 6,
        rgba: [0.428, 0.193, 1, 1]
      },
      {
        id: 5008,
        typeName: "地下空间模型",
        cimtypes: [36, 43, 48,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 5009,
        typeName: "地质模型",
        cimtypes: [37,],
        style: 3,
        rgba: [0.961, 0.734, 0.550, 1]
      },
      {
        id: 5010,
        typeName: "植被模型",
        cimtypes: [19, 24, 32,],
        style: 6,
        rgba: [0.428, 0.193, 1, 1]
      },
    ]
  },
  {
    id: 6,
    type: "X光",
    label: "xRay",
    children: [
      {
        id: 6001,
        typeName: "地形模型",
         cimtypes: [8,9, 14, 20, 28,],
        style: 5,
        rgba: [0.161, 0.208, 0.138, 1]
      },
      {
        id: 6002,
        typeName: "水系模型",
        cimtypes: [12, 18, 23, 31,],
        style: 3,
        rgba: [0.314, 0.526, 0.361, 1]
      },
      {
        id: 6003,
        typeName: "行政区模型",
        cimtypes: [10, 15,],
        style: 3,
        rgba: [0.330, 0.208, 0.526, 1]
      },
      {
        id: 6004,
        typeName: "建筑模型",
        cimtypes: [16, 21, 29, 39, 44,],
        style: 1,
        rgba: [0.514, 0.710, 0.836, 0.035]
      },
      // {
      //   id:6005,
      //   typeName:"建筑内部模型",
      //   style:,
      //   rgba:[]
      // },
      {
        id: 6006,
        typeName: "交通模型",
        cimtypes: [13, 17, 22, 30, 40, 45,],
        style: 3,
        rgba: [0.714, 0.687, 0.491, 1]
      },
      {
        id: 6007,
        typeName: "场地模型",
        cimtypes: [26, 34, 41, 46,],
        style: 5,
        rgba: [0.161, 0.208, 0.138, 1]
      },
      {
        id: 6008,
        typeName: "地下空间模型",
        cimtypes: [36, 43, 48,],
        style: 1,
        rgba: [0.400, 0.750, 0.640, 0.06]
      },
      {
        id: 6009,
        typeName: "地质模型",
        cimtypes: [37,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 6001,
        typeName: "植被模型",
        cimtypes: [19, 24, 32,],
        style: 5,
        rgba: [0.161, 0.208, 0.138, 1]
      },
    ]
  },
  {
    id: 7,
    type: "纯色",
    label: "pureColor",
    children: [
      {
        id: 7001,
        typeName: "地形模型",
         cimtypes: [8,9, 14, 20, 28,],
        style: 5,
        rgba: [0.318, 0.318, 0.110, 1]
      },
      {
        id: 7002,
        typeName: "水系模型",
        cimtypes: [12, 18, 23, 31,],
        style: 3,
        rgba: [0.314, 0.526, 0.361, 1]
      },
      {
        id: 7003,
        typeName: "行政区模型",
        cimtypes: [10, 15,],
        style: 3,
        rgba: [0.350, 0.608, 0.910, 1]
      },
      {
        id: 7004,
        typeName: "建筑模型",
        cimtypes: [16, 21, 29, 39, 44,],
        style: 2,
        rgba: [1, 0.985, 0.914, 1]
      },
      // {
      //   id:7005,
      //   typeName:"建筑内部模型",
      //   style:,
      //   rgba:[]
      // },
      {
        id: 7006,
        typeName: "交通模型",
        cimtypes: [13, 17, 22, 30, 40, 45,],
        style: 3,
        rgba: [0.714, 0.455, 0.322, 1]
      },
      {
        id: 7007,
        typeName: "场地模型",
        cimtypes: [26, 34, 41, 46,],
        style: 5,
        rgba: [0.318, 0.318, 0.110, 1]
      },
      {
        id: 7008,
        typeName: "地下空间模型",
        cimtypes: [36, 43, 48,],
        style: 3,
        rgba: [0.350, 0.769, 0.961, 1]
      },
      {
        id: 7009,
        typeName: "地质模型",
        cimtypes: [37,],
        style: 3,
        rgba: [0.961, 0.734, 0.550, 1]
      },
      {
        id: 7010,
        typeName: "植被模型",
        cimtypes: [19, 24, 32,],
        style: 5,
        rgba: [0.318, 0.318, 0.110, 1]
      },
    ]
  },
];

export default {
  showStyleList
}