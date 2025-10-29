import apiRequest from "../../request"
import treeApiRequest from "../../requestTree"
function cleanArray(actual) {
    const newArray = [];
    for (let i = 0; i < actual.length; i++) {
        if (actual[i]) {
            newArray.push(actual[i]);
        }
    }
    return newArray;
}

function toQueryString(obj) {
    if (!obj) return "";
    return cleanArray(
        Object.keys(obj).map(key => {
            if (obj[key] === undefined) return "";
            return encode(key) + "=" + encode(obj[key]);
        })
    ).join("&");
}

//云渲染资源
export const getpreview = params => apiRequest.post('service/media/getconfig' , params);

// 图层树
export const gettree = params => treeApiRequest.post('service/media/tree' , params);
//更新图层树中所选的3dt
export const updateTree = params => treeApiRequest.put('service/scene/update' , params);
// 获取图层树中所有选中的3dt
export const getSelectIDInTree = params => treeApiRequest.get(`service/scene/getbyid?token=${params.token}&id=${params.id}` , params);
// 控制树
export const getControlTree = params => treeApiRequest.get(`service/basedata/tree?token=${params.token}&ids=${params.ids}` , params);

//渲染组装详情
export const getone = params => apiRequest.post('service/media/one' , params);

//渲染组装更新
export const getupdate = params => apiRequest.post('service/media/update', params);

//楼层数据
export const getFloorData = params => apiRequest.post('service/coordinate/list', params);

//新增导览
export const postNavigition = params => treeApiRequest.post('service/scene/navigition', params);
//获取导览
export const getNavigition = params => treeApiRequest.get('service/scene/navigition', params);


// 根据范围获取tilelayer中的objectid
export const getObjectIdsInBoundary = params => treeApiRequest.get(`service/object/info/byscope?token=${params.token}&scope=${params.scope}&tilelayerids=${params.tilelayerids}&flag=${params.flag}` , params);