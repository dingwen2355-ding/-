import coordtransform from './coordtransform'
import { Toast } from 'vant'

let apiCloudByZnv = {}

apiCloudByZnv.sms = function (numbers, text, successCallBack, errerCallBack) {
  window.api &&
    window.api.sms(
      {
        numbers: numbers,
        text: text
      },
      function (ret, err) {
        if (ret && ret.status) {
          // 已发送
          successCallBack && successCallBack()
        } else {
          // 发送失败
          errerCallBack && errerCallBack()
        }
      }
    )
}

apiCloudByZnv.call = function (number) {
  window.api &&
    window.api.call({
      type: 'tel_prompt',
      number: number
    })
}

apiCloudByZnv.sendEvent = function (eventName, data) {
  window.api &&
    window.api.sendEvent({
      name: eventName,
      extra: data
    })
}

var aMapLBS = null
apiCloudByZnv.getGps = function (successCallBack, errerCallBack) {
  // app端
  if (window.api) {
    if (!aMapLBS) {
      aMapLBS = window.api.require('aMapLBS')
      aMapLBS.updateLocationPrivacy({
        privacyAgree: 'didAgree',
        privacyShow: 'didShow',
        containStatus: 'didContain'
      })
      aMapLBS.configManager(
        {
          accuracy: 'hundredMeters',
          filter: 1
        },
        function (ret, err) {
          console.log(ret.status)
          if (ret.status) {
            aMapLBS.singleLocation(
              {
                timeout: 10
              },
              function (ret, err) {
                if (ret.status) {
                  successCallBack && successCallBack(coordtransform.gcj02towgs84(ret.lon, ret.lat))
                } else {
                  logShow('自动地理定位失败,请填写。')
                  errerCallBack && errerCallBack(JSON.stringify(ret))
                }
              }
            )
          }
        }
      )
    } else {
      aMapLBS.singleLocation(
        {
          timeout: 10
        },
        function (ret, err) {
          if (ret.status) {
            successCallBack && successCallBack(coordtransform.gcj02towgs84(ret.lon, ret.lat))
          } else {
            logShow('自动地理定位失败,请填写。')
            errerCallBack && errerCallBack(JSON.stringify(ret))
          }
        }
      )
    }
  } else {
    // H5端
    logShow('浏览器端不支持自动地理定位,请填写或转用app端。')
    errerCallBack && errerCallBack()
  }
}

// 根据用户的数据类型打开相应的Activity
apiCloudByZnv.openFileByView = function (file) {
  window.api &&
    window.api.openApp(
      {
        androidPkg: 'android.intent.action.VIEW',
        uri: file
      },
      function (ret, err) {
        console.log(JSON.stringify(ret))
      }
    )
}

// superFile打开文件
var docInteraction = null
apiCloudByZnv.openFile = function (file) {
  // app端
  if (window.api) {
    if (!docInteraction) {
      docInteraction = window.api.require('docInteraction')
      docInteraction.open(
        {
          path: file
        },
        function (ret, err) {
          if (err) {
            if (err.code === 1) {
              logShow('没有能打开此文档的应用程序')
            } else if (err.code === 2) {
              logShow('文件不存在')
            }
          } else {
            logShow('打开文件' + JSON.stringify(ret))
          }
        }
      )
    } else {
      docInteraction.open(
        {
          path: file
        },
        function (ret, err) {
          if (err) {
            if (err.code === 1) {
              logShow('没有能打开此文档的应用程序')
            } else if (err.code === 2) {
              logShow('文件不存在')
            }
          } else {
            logShow('打开文件' + JSON.stringify(ret))
          }
        }
      )
    }
  } else {
    // H5端
    logShow('浏览器端不支持预览文件')
  }
}

// 文件下载
apiCloudByZnv.downloadFile = function (fileUrl, fileName, callBack) {
  window.api &&
    window.api.download(
      {
        url: fileUrl,
        savePath: 'fs://download/' + fileName,
        report: true,
        cache: true,
        allowResume: true
      },
      function (ret, err) {
        console.log(JSON.stringify(ret))
        callBack(ret, err)
      }
    )
}

// 读取路径下的文件
var fs = null
apiCloudByZnv.readDir = function (path, successCallBack, errerCallBack) {
  // app端
  if (window.api) {
    if (!fs) {
      fs = window.api.require('fs')
      fs.readDir(
        {
          path: path
        },
        function (ret, err) {
          if (ret.status) {
            successCallBack(ret)
          } else {
            errerCallBack(err)
          }
        }
      )
    } else {
      fs.readDir(
        {
          path: path
        },
        function (ret, err) {
          if (ret.status) {
            successCallBack(ret)
          } else {
            errerCallBack(err)
          }
        }
      )
    }
  } else {
    // H5端
    logShow('浏览器端不支持读取路径下的文件')
    errerCallBack()
  }
}

function logShow(log) {
  Toast({
    message: log,
    position: 'bottom'
  })
}

// 用法
export default apiCloudByZnv
