/**
 * rtsp无法获取链接转去播放hls
 */
import { getRtspUrl } from '../api/info'
export function RTSP2HLS(cameraInfoList) {
  let cameraIds = cameraInfoList.map(e => {
    return e.cameraId
  })
  let videoInfoList = []
  return getRtspUrl(cameraIds.join(',')).then(res => {
    let cameraRtspInfoList = cameraInfoList.map(e => {
      e.rtspUrl = ''
      res.data.data.some(rtspItem => {
        if (e.cameraId.includes(rtspItem.cameraId)) {
          e.rtspUrl = rtspItem.rtspUrl
          return true
        }
      })
      return e
    })
    cameraRtspInfoList.forEach((e, index) => {
      // 存在rtsp链接
      if (e.rtspUrl) {
        videoInfoList.push({
          url: e.rtspUrl,
          type: 'rtsp',
          cameraId: e.cameraId
        })
      } else { // 不存在rtsp链接
        let transfromSrc = e.cameraLink
        let isHas = precinctConfig.regionUsers.some(e => {
          return e === sessionStorage.userName
        })
        if (isHas) {
          transfromSrc = e.cameraLink.replace(precinctConfig.videoRoad.roadStreet, precinctConfig.videoRoad.roadRegion)
          transfromSrc = transfromSrc.replace('networkType=0', 'networkType=1')
        }
        videoInfoList.push({
          url: transfromSrc,
          type: 'hls',
          cameraId: e.cameraId
        })
      }
    })
    return videoInfoList
  })
}

/**
 * hls无法获取链接转去播放rtsp
 */
export async function HLS2RTSP(cameraInfoList) {
  let videoInfoList = []
  for (let i = 0; i < cameraInfoList.length; i++) {
    let e = {
      cameraLink: cameraInfoList[i].cameraLink,
      cameraId: cameraInfoList[i].cameraId
    }
    // 存在hls链接
    if (e.cameraLink) {
      let transfromSrc = e.cameraLink
      let isHas = precinctConfig.regionUsers.some(e => {
        return e === sessionStorage.userName
      })
      if (isHas) {
        transfromSrc = e.cameraLink.replace(precinctConfig.videoRoad.roadStreet, precinctConfig.videoRoad.roadRegion)
        transfromSrc = transfromSrc.replace('networkType=0', 'networkType=1')
      }
      videoInfoList.push({
        url: transfromSrc,
        type: 'hls',
        cameraId: e.cameraId
      })
    } else { // 不存在hls链接
      let rtspInfo = await getRtspUrl(e.cameraId)
      videoInfoList.push({
        url: rtspInfo.data.data[0].rtspUrl,
        type: 'rtsp',
        cameraId: e.cameraId
      })
    }
  }
  return videoInfoList
}
