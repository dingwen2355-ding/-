import tagClass from "./manager/tag.class";
// import store from '@/services/airCity.store';
import { get } from "@/utils/fetch";

let mapIconMap = {
  定位: require("@/assets/mapIcon/定位.png"),
  固定视频: require("@/assets/mapIcon/固定视频_blue_hover.png"),
  公司企业: require("@/assets/mapIcon/point_公司企业@2x.png"),
  交通设施: require("@/assets/mapIcon/point_交通设施@2x.png"),
  旅游景点: require("@/assets/mapIcon/point_旅游景点@2x.png"),
  生活服务: require("@/assets/mapIcon/point_生活服务@2x.png"),
  休闲娱乐: require("@/assets/mapIcon/point_休闲娱乐@2x.png"),
  医疗保健: require("@/assets/mapIcon/point_医疗保健@2x.png"),
  单兵: require("@/assets/mapIcon/单兵_blue_hover.png"),
  救援队伍: require("@/assets/mapIcon/救援队伍_blue_hover.png"),
  避难场所: require("@/assets/mapIcon/避难场所_blue_hover.png"),
  应急物资: require("@/assets/mapIcon/应急物资_blue_hover.png"),
  专家: require("@/assets/mapIcon/专家_blue_hover.png"),
};

const tag = new tagClass();
export function initPoint() {
  tag.clear().then(() => {
    loadMapPoints();
  });
}

export let markerWatch = {
  "$store.getters.eventSealAPI": {
    deep: true,
    handler(v) {
      if (v.eventtype === "LeftMouseButtonClick" && v.Type === "tag") {
        window.top.postMessage(v.Id, "*");
      }
    },
  },
};

function loadPointLayer(pointList, opts) {
  let points = pointList.map((e) => {
    return {
      id: e.id,
      coordinate: [
        parseFloat(e.position.lng),
        parseFloat(e.position.lat),
        e.position.height,
      ],
      imagePath: location.origin + location.pathname + e.icon,
      imageSize: [e.size.width, e.size.height],
      text: e.title,
      showLine: true,
      coordinateType: 1,
      range: [1, 8000.0],
      textRange: 2000,
      textColor: Color.Black,
      textBackgroundColor: Color.White,
    };
  });
  tag.add(points);
}

function loadMapPoints() {
  // 事件点位
  queryEventPoint();
  // 应急救援队伍
  queryRescuePoint();
}

// 事件点位
function queryEventPoint(opts) {
  let eventId = getQueryByName(location.href, "eventId");
  get("/apis/eventDeal/eventInfo?eventId=" + eventId).then((res) => {
    let pointList = queryEventPointProcess(res.data);
    loadPointLayer(pointList, opts);
  });
}
function queryEventPointProcess(data) {
  return data.map((e, index) => {
    let temp = {
      id: "event-marker",
      type: "event",
      icon: mapIconMap["定位"],
      title: e.eventTitle,
      position: { lng: e.gpsx, lat: e.gpsy, height: 10 },
      size: { width: 42, height: 48 },
      offset: { x: -21, y: -48 },
      extData: {},
    };
    return temp;
  });
}

// 应急救援队伍
function queryRescuePoint(opts) {
  get("/community/teamInfo/queryRescueTeamInfo?isAll=1").then((res) => {
    let pointList = queryRescuePointProcess(res.data.teamList);
    loadPointLayer(pointList, opts);
  });
}
function queryRescuePointProcess(data) {
  return data.map((e, index) => {
    e.type = "firestation";
    e.clickType = "应急救援队伍";
    let temp = {
      id: "应急救援队伍_" + e.ID,
      icon: mapIconMap["救援队伍"],
      title: e.TEAM_NAME,
      position: { lng: e.LONGITUDE, lat: e.LATITUDE, height: 10 },
      size: { width: 30, height: 34 },
      offset: { x: -15, y: -34 },
      extData: e,
    };
    return temp;
  });
}

// 获取url参数
function getQueryByName(url, name) {
  var reg = new RegExp("[?&]" + name + "=([^&#]+)");
  var query = url.match(reg);
  return query ? query[1] : null;
}
