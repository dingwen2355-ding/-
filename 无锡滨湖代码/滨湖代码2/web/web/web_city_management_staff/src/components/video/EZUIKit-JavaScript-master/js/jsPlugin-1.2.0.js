!(function (t, e) {
  if (typeof exports === 'object' && typeof module === 'object') { module.exports = e() } else if (typeof define === 'function' && define.amd) define([], e)
  else {
    var n = e()
    for (var i in n) (typeof exports === 'object' ? exports : t)[i] = n[i]
  }
})(this, function () {
  return (function (t) {
    function e (i) {
      if (n[i]) return n[i].exports
      var r = (n[i] = { exports: {}, id: i, loaded: !1 })
      return t[i].call(r.exports, r, r.exports, e), (r.loaded = !0), r.exports
    }
    var n = {}
    return (e.m = t), (e.c = n), (e.p = ''), e(0)
  })([
    function (t, e, n) {
      'use strict'
      function i (t) {
        return t && t.__esModule ? t : { default: t }
      }
      function r (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 }),
      (e.JSPlugin = void 0)
      var o = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var a = n(5)
      var s = n(15)
      var u = n(16)
      var l = n(10)
      var f = n(9)
      var c = n(1)
      var h = i(c)
      var d = (function () {
        function t () {
          document.addEventListener(
            'visibilitychange',
            function () {
              if (document.hidden) {
                for (var t = 0; t < 16; t++) {
                  p[I][t] &&
                      p[I][t].bLoad &&
                      p[I][t].oPlayCtrl.PlayM4_IsVisible(!1)
                }
              } else {
                for (var e = 0; e < 16; e++) {
                  p[I][e] &&
                      p[I][e].bLoad &&
                      p[I][e].oPlayCtrl.PlayM4_IsVisible(!0)
                }
              }
            },
            !1
          )
        }
        function e (t, e) {
          t && e && ((p[M].iWidth = t), (p[M].iHeight = e))
          var n = p[M].iWidth % p[M].iCurrentSplit
          var i = p[M].iHeight % p[M].iCurrentSplit
          var r =
                (p[M].iWidth - n - 2 * p[M].iCurrentSplit) / p[M].iCurrentSplit
          var o =
                (p[M].iHeight - i - 2 * p[M].iCurrentSplit) /
                p[M].iCurrentSplit
          var a = (p[M].iWidth - n) / p[M].iCurrentSplit
          var s = (p[M].iHeight - i) / p[M].iCurrentSplit
          var u = p[M].iCurrentSplit
          D = (0, h.default)('#' + p[M].szId)
          for (
            var l =
                  '<div class="parent-wnd" style="overflow:hidden;width:100%; height:100%; position: relative;">',
              f = 0;
            f < p[z];
            f++
          ) {
            (t = r + (f % u === u - 1 ? n : 0)),
            (e = o + (f + u >= Math.pow(u, 2) ? i : 0))
            var c = a + (f % u === u - 1 ? n : 0)
            var d = s + (f + u >= Math.pow(u, 2) ? i : 0)
            l +=
                '<div style="float:left; background-color: ' +
                p[M].oStyle.background +
                '; position: relative; width: ' +
                c +
                'px; height: ' +
                d +
                'px;"><canvas id="canvas' +
                f +
                '" class="play-window" style="border:1px solid ' +
                p[M].oStyle.border +
                ';" wid="' +
                f +
                '" width="' +
                t +
                '" height="' +
                e +
                '"></canvas><canvas id="canvas_draw' +
                f +
                '"  class="draw-window" style="position:absolute; top:0; left:0;" wid="' +
                f +
                '" width=' +
                c +
                ' height=' +
                d +
                '></canvas></div>'
          }
          (l += '</div>'),
          D.html(l),
          D.find('.parent-wnd')
            .eq(0)
            .children()
            .eq(0)
            .find('.play-window')
            .eq(0)
            .css('border', '1px solid ' + p[M].oStyle.borderSelect)
        }
        function n () {
          p.EventCallback = (function () {
            return {
              loadEventHandler: function () {
                window.loadEventHandler && window.loadEventHandler()
              },
              zoomEventResponse: function () {},
              windowEventSelect: function (t) {
                p[H] !== t &&
                    ((p[H] = t),
                    (p[I][t].bEZoom || p[I][t].b3DZoom) &&
                      ((0, h.default)('.draw-window').unbind(),
                      p[A].setDrawStatus(!1),
                      (p[A] = null),
                      (p[A] = new f.ESCanvas('canvas_draw' + t)),
                      p[A].setShapeType('Rect'),
                      p[A].setDrawStyle('#ff0000', '', 0),
                      p[I][t].bEZoom
                        ? p[A].setDrawStatus(!0, function (e) {
                          e.startPos &&
                              e.endPos &&
                              (e.startPos[0] > e.endPos[0]
                                ? p[I][t].oPlayCtrl.PlayM4_SetDisplayRegion(
                                  null,
                                  !1
                                )
                                : p[I][t].oPlayCtrl.PlayM4_SetDisplayRegion(
                                  {
                                    left: e.startPos[0],
                                    top: e.startPos[1],
                                    right: e.endPos[0],
                                    bottom: e.endPos[1]
                                  },
                                  !0
                                ))
                        })
                        : p[I][t].b3DZoom &&
                          p[A].setDrawStatus(!0, function (t) {
                            p[j](t)
                          })),
                    window.GetSelectWndInfo && window.GetSelectWndInfo(t))
              },
              pluginErrorHandler: function (t, e, n) {
                window.PluginEventHandler &&
                    window.PluginEventHandler(t, e, n)
              },
              windowEventOver: function (t) {
                window.windowEventOver && window.windowEventOver(t)
              },
              windowEventOut: function (t) {
                window.windowEventOut && window.windowEventOut(t)
              },
              windowEventUp: function (t) {
                window.windowEventUp && window.windowEventUp(t)
              },
              windowFullCcreenChange: function (t) {
                window.windowFullCcreenChange &&
                    window.windowFullCcreenChange(t)
              },
              firstFrameDisplay: function (t) {
                window.firstFrameDisplay && window.firstFrameDisplay(t)
              },
              performanceLack: function () {
                window.performanceLack && window.performanceLack()
              }
            }
          })()
        }
        function i () {
          n(),
          D.find('.parent-wnd')
            .eq(0)
            .children()
            .each(function (t) {
              var e = this;
              (0, h.default)(e)
                .unbind()
                .bind('mousedown', function () {
                  D.find('.parent-wnd')
                    .eq(0)
                    .find('.play-window')
                    .css('border', '1px solid ' + p[M].oStyle.border),
                  D.find('.parent-wnd')
                    .eq(0)
                    .children()
                    .eq(t)
                    .find('.play-window')
                    .eq(0)
                    .css(
                      'border',
                      '1px solid ' + p[M].oStyle.borderSelect
                    ),
                  p.EventCallback.windowEventSelect(
                    parseInt(
                      D.find('.parent-wnd')
                        .eq(0)
                        .children()
                        .eq(t)
                        .find('.play-window')
                        .eq(0)
                        .attr('wid'),
                      10
                    )
                  )
                }),
              (0, h.default)(e).bind('mouseover', function (e) {
                p.EventCallback.windowEventOver(t), e.stopPropagation()
              }),
              (0, h.default)(e).bind('mouseout', function (e) {
                p.EventCallback.windowEventOut(t), e.stopPropagation()
              }),
              (0, h.default)(e).bind('mouseup', function () {
                p.EventCallback.windowEventUp(t)
              }),
              (0, h.default)(e).bind('dblclick', function (t) {
                if (p[I][p[H]].bPlay) {
                  var n =
                            document.fullscreen ||
                            document.webkitIsFullScreen ||
                            document.mozFullScreen ||
                            !1
                  var i = (0, h.default)(e).get(0)
                  if (n) {
                    if (
                      D.find('.parent-wnd')
                        .eq(0)
                        .width() === (0, h.default)(window).width()
                    ) { return }
                    document.exitFullscreen
                      ? document.exitFullscreen()
                      : document.webkitCancelFullScreen
                        ? document.webkitCancelFullScreen()
                        : document.mozCancelFullScreen &&
                                document.mozCancelFullScreen()
                  } else {
                    i.requestFullScreen
                      ? i.requestFullScreen()
                      : i.webkitRequestFullScreen
                        ? i.webkitRequestFullScreen()
                        : i.mozRequestFullScreen &&
                                i.mozRequestFullScreen(),
                    (p[B] = (0, h.default)(e))
                  }
                  t.stopPropagation()
                }
              })
            }),
          typeof document.fullScreen !== 'undefined'
            ? document.addEventListener('fullscreenchange', function () {
              var t = document.fullscreen || !1
              p.EventCallback.windowFullCcreenChange(t)
            })
            : typeof document.webkitIsFullScreen !== 'undefined'
              ? document.addEventListener(
                'webkitfullscreenchange',
                function () {
                  var t = document.webkitIsFullScreen || !1
                  p.EventCallback.windowFullCcreenChange(t)
                }
              )
              : typeof document.mozFullScreen !== 'undefined' &&
                    document.addEventListener(
                      'mozfullscreenchange',
                      function () {
                        var t = document.mozFullScreen || !1
                        p.EventCallback.windowFullCcreenChange(t)
                      }
                    )
        }
        function c () {
          for (
            var t = D.find('.parent-wnd')
                .eq(0)
                .children().length,
              e = p[M].iWidth % p[M].iCurrentSplit,
              n = p[M].iHeight % p[M].iCurrentSplit,
              i =
                  (p[M].iWidth - e - 2 * p[M].iCurrentSplit) /
                  p[M].iCurrentSplit,
              r =
                  (p[M].iHeight - n - 2 * p[M].iCurrentSplit) /
                  p[M].iCurrentSplit,
              o = (p[M].iWidth - e) / p[M].iCurrentSplit,
              a = (p[M].iHeight - n) / p[M].iCurrentSplit,
              s = p[M].iCurrentSplit,
              u = 0;
            u < t;
            u++
          ) {
            var l = i + (u % s === s - 1 ? e : 0)
            var f = r + (u + s >= Math.pow(s, 2) ? n : 0)
            var c = o + (u % s === s - 1 ? e : 0)
            var h = a + (u + s >= Math.pow(s, 2) ? n : 0)
            D.find('.parent-wnd')
              .eq(0)
              .children()
              .eq(u)
              .width(c),
            D.find('.parent-wnd')
              .eq(0)
              .children()
              .eq(u)
              .height(h),
            D.find('.parent-wnd')
              .eq(0)
              .children()
              .eq(u)
              .find('.draw-window')
              .attr('width', c),
            D.find('.parent-wnd')
              .eq(0)
              .children()
              .eq(u)
              .find('.draw-window')
              .attr('height', h),
            D.find('.parent-wnd')
              .eq(0)
              .children()
              .eq(u)
              .find('.play-window')
              .attr('width', l),
            D.find('.parent-wnd')
              .eq(0)
              .children()
              .eq(u)
              .find('.play-window')
              .attr('height', f)
          }
          D.find('.parent-wnd')
            .eq(p[H])
            .find('.play-window')
            .css('border', '1px solid ' + p[M].oStyle.border),
          D.find('.parent-wnd')
            .eq(p[H])
            .children()
            .eq(0)
            .find('.play-window')
            .eq(0)
            .css('border', '1px solid ' + p[M].oStyle.borderSelect)
        }
        function d (t, e, n, i, r, o, a) {
          if ((0, h.default)('#' + p[I][n].windowID).length) {
            var s = !1
            i && r && (s = !0),
            (p[I][n].bLoad = !0),
            T.openStream(
              t,
              e,
              function (t) {
                if (t.bHead && !p[I][n].bPlay) {
                  (p[I][n].bPlay = !0),
                  (p[I][n].aHead = new Uint8Array(t.buf)),
                  p[I][n].oPlayCtrl.PlayM4_OpenStream(t.buf, S, 2097152),
                  p[I][n].szSecretKey !== '' &&
                          (p[I][n].oPlayCtrl.PlayM4_SetSecretKey(
                            1,
                            p[I][n].szSecretKey,
                            128
                          ),
                          (p[I][n].szSecretKey = '')),
                  p[I][n].aHead[8] === 4
                    ? p[I][n].oPlayCtrl.PlayM4_SetStreamOpenMode(0)
                    : p[I][n].oPlayCtrl.PlayM4_SetStreamOpenMode(1),
                  p[I][n].oPlayCtrl.PlayM4_SetInputBufSize(b),
                  p[I][n].oPlayCtrl.PlayM4_Play(p[I][n].windowID)
                } else {
                  var e = new Uint8Array(t.buf)
                  var i = p[I][n].oPlayCtrl.PlayM4_GetInputBufSize()
                  var r = p[I][n].oPlayCtrl.PlayM4_GetYUVBufSize()
                  r !== 2 ||
                        p[I][n].bFirstFrame ||
                        ((p[I][n].bFirstFrame = !0),
                        p.EventCallback.firstFrameDisplay(n))
                  var o = p[I][n].oPlayCtrl.PlayM4_GetDecodeFrameType()
                  i > 0.5 * b && i < 0.8 * b && p[I][n].iRate === 1
                    ? o !== g &&
                          (p[I][n].oPlayCtrl.PlayM4_SetDecodeFrameType(g),
                          p.EventCallback.performanceLack())
                    : i >= 0.8 * b && (e = new Uint8Array([1, 2, 3, 4])),
                  r > 10 && r < 15
                    ? o !== g &&
                            (p[I][n].oPlayCtrl.PlayM4_SetDecodeFrameType(g),
                            p.EventCallback.performanceLack())
                    : r > 15 && (e = new Uint8Array([1, 2, 3, 4])),
                  r < 10 &&
                          i < 0.5 * b &&
                          o !== v &&
                          p[I][n].oPlayCtrl.PlayM4_SetDecodeFrameType(v),
                  t.statusString
                    ? p.EventCallback.pluginErrorHandler(n, w, t)
                    : t.type && t.type === 'exception'
                      ? p.EventCallback.pluginErrorHandler(n, _, t)
                      : p[I][n].oPlayCtrl.PlayM4_InputData(e, e.length),
                  (e = null)
                }
                p[I][n].szStorageUUID &&
                      k.inputData(p[I][n].szStorageUUID, t.buf),
                (t = null)
              },
              function () {
                p[I][n].bPlay &&
                      (p.EventCallback.pluginErrorHandler(n, P),
                      (p[I][n].bPlay = !1),
                      (p[I][n].bFrameForward = !1),
                      (p[I][n].iRate = 1),
                      p[I][n].oPlayCtrl &&
                        (p[I][n].oPlayCtrl.PlayM4_Stop(),
                        p[I][n].oPlayCtrl.PlayM4_CloseStream()))
              }
            ).then(
              function (t) {
                (p[I][n].szStreamUUID = t),
                T.startPlay(t, i, r).then(
                  function () {
                    s
                      ? ((p[I][n].szPlayType = 'playback'),
                      (p[I][n].iRate = 1),
                      p[I][n].oPlayCtrl.PlayM4_PlayRate(p[I][n].iRate))
                      : (p[I][n].szPlayType = 'realplay'),
                    o()
                  },
                  function (t) {
                    a(t)
                  }
                )
              },
              function (t) {
                a(t)
              }
            )
          }
        }
        if (typeof Symbol !== 'undefined') {
          var p = null
          var y = -1
          var m = 0
          var v = 0
          var g = 1
          var S = 40
          var b = 4194304
          var w = 1001
          var _ = 1002
          var P = 1003
          var C = 2001
          var x = 2002
          var T = new s.StreamClient()
          var k = null
          var D = null
          var M = Symbol('OPTIONS')
          var R = Symbol('CURRENTPLAYRATE')
          var E = Symbol('CURRENTSOUNDWND')
          var z = Symbol('MAXWNDNUM')
          var I = Symbol('MAXWNDNUM')
          var A = Symbol('DRAWCANVAS')
          var F = Symbol('SHAPEID')
          var O = Symbol('WINDOWFULL')
          var B = Symbol('SINGLEWINDOW')
          var L = Symbol('FILETMP')
          var N = Symbol('STATUSTMP')
          var q = Symbol('UPGRADESTATUSURL')
          var H = Symbol('CURWNDINDEX')
          var j = Symbol('CALLBACKFUNCTION')
          var W = (function () {
            function n (o) {
              r(this, n), (p = this)
              var a = {
                szId: 'playWnd',
                iType: 1,
                iWidth: 400,
                iHeight: 300,
                iMaxSplit: 4,
                iCurrentSplit: 2,
                szBasePath: './'
              }
              var wsParams = { wsParams: '' }
              this[M] = Object.assign(a, wsParams, o)
              var s = {
                border: '#343434',
                borderSelect: '#FFCC00',
                background: '#4C4B4B'
              };
              (s = Object.assign(s, o.oStyle)),
              (this[M].oStyle = s),
              this[M].iCurrentSplit > this[M].iMaxSplit &&
                      (this[M].iCurrentSplit = this[M].iMaxSplit),
              (this[R] = 1),
              (this[E] = -1),
              (this[z] = this[M].iMaxSplit * this[M].iMaxSplit),
              (this[F] = ''),
              (this[O] = !1),
              (this[B] = null),
              (this[L] = null),
              (this[N] = ''),
              (this[q] = ''),
              (this[H] = -1),
              (this[j] = null),
              (k = new l.StorageManager(
                this[M].szBasePath + '/transform'
              )),
              (D = (0, h.default)('#' + p[M].szId)),
              (this[I] = [])
              for (var u = 0; u < this[z]; u++) {
                (this[I][u] = {}),
                (this[I][u].bSelect = !1),
                (this[I][u].bPlay = !1),
                (this[I][u].bRecord = !1),
                (this[I][u].oPlayCtrl = null),
                (this[I][u].szPlayType = ''),
                (this[I][u].szStorageUUID = ''),
                (this[I][u].szStreamUUID = ''),
                (this[I][u].aHead = []),
                (this[I][u].bLoad = !1),
                (this[I][u].windowID = 'canvas' + u),
                (this[I][u].drawID = 'canvas_draw' + u),
                (this[I][u].iRate = 1),
                (this[I][u].bEZoom = !1),
                (this[I][u].b3DZoom = !1),
                (this[I][u].szSecretKey = ''),
                (this[I][u].bFrameForward = !1),
                (this[I][u].iDecodeType = v),
                (this[I][u].bFirstFrame = !1)
              }
              t(),
              e(),
              (this[A] = new f.ESCanvas('canvas_draw0')),
              this[M].iType === 0 &&
                      (0, h.default)('#' + p[M].szId).hide(),
              i(),
              p.EventCallback.windowEventSelect(0)
            }
            return (
              o(n, [
                {
                  key: 'JS_ArrangeWindow',
                  value: function (t) {
                    if (
                      (t < p[M].iMaxSplit
                        ? (p[M].iCurrentSplit = t)
                        : (p[M].iCurrentSplit = p[M].iMaxSplit),
                      a.oTool.isFirefox())
                    ) {
                      for (
                        var e = 0;
                        e < p[M].iMaxSplit * p[M].iMaxSplit;
                        e++
                      ) {
                        p[I][e].oPlayCtrl &&
                              p[I][e].oPlayCtrl.PlayM4_ClearCanvas()
                      }
                    }
                    c(), p.EventCallback.windowEventSelect(0)
                  }
                },
                {
                  key: 'JS_SetSecretKey',
                  value: function (t, e) {
                    return t < 0
                      ? -1
                      : e === '' || typeof e === 'undefined'
                        ? -1
                        : ((this[I][t].szSecretKey = e), 0)
                  }
                },
                {
                  key: 'JS_Play',
                  value: function (t, e, n, i, r) {
                    var o = new Promise(function (o, a) {
                      return n < 0 || n > p[z] - 1
                        ? void a()
                        : p[I][n].bFrameForward
                          ? void a()
                          : (p[I][n].bPlay && p.JS_Stop(n),
                          (p[I][n].bFirstFrame = !1),
                          (p[I][n].iDecodeType = v),
                          void (p[I][n].oPlayCtrl
                            ? d(t, e, n, i, r, o, a)
                            : (p[I][n].oPlayCtrl = new u.JSPlayCtrl(
                              p[M].szBasePath + '/playctrl/',
                              function (s) {
                                s.cmd !== 'loaded' || p[I][n].bLoad
                                  ? s.cmd === 'OnebyOne'
                                    ? s.status
                                      ? T.resume(p[I][n].szStreamUUID)
                                      : T.pause(p[I][n].szStreamUUID)
                                    : s.cmd === 'GetFrameData' &&
                                              p.EventCallback.pluginErrorHandler(
                                                n,
                                                C
                                              )
                                  : d(t, e, n, i, r, o, a)
                              },
                              n
                            ))))
                    })
                    return o
                  }
                },
                {
                  key: 'JS_Seek',
                  value: function (t, e, n) {
                    var i = new Promise(function (i, r) {
                      return t < 0 || t > p[z] - 1
                        ? void r()
                        : p[I][t].bPlay
                          ? void T.seek(p[I][t].szStreamUUID, e, n).then(
                            function () {
                              i()
                            },
                            function (t) {
                              r(t)
                            }
                          )
                          : void r()
                    })
                    return i
                  }
                },
                {
                  key: 'JS_GetSdkVersion',
                  value: function () {
                    return p[I][0].oPlayCtrl.PlayM4_GetSdkVersion()
                  }
                },
                {
                  key: 'JS_DestroyWorker',
                  value: function () {
                    p[I].forEach(function (t) {
                      t.bPlay && t.oPlayCtrl.PlayM4_CloseStream(),
                      t.oPlayCtrl &&
                              (t.oPlayCtrl.PlayM4_Destroy(),
                              (t.oPlayCtrl = null),
                              (t.bLoad = !1))
                    })
                  }
                },
                {
                  key: 'JS_Stop',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return t < 0 || t > p[z] - 1
                        ? void n()
                        : (p[I][t].szStorageUUID && p.JS_StopSave(t),
                        p[I][t].bEZoom && p.JS_DisableZoom(t),
                        p[E] === t && (p[E] = -1),
                        void T.stop(p[I][t].szStreamUUID).then(
                          function () {
                            (p[I][t].bPlay = !1),
                            (p[I][t].bFrameForward = !1),
                            (p[I][t].iRate = 1),
                            p[I][t].oPlayCtrl &&
                                      (p[I][t].oPlayCtrl.PlayM4_Stop(),
                                      p[I][t].oPlayCtrl.PlayM4_CloseStream()),
                            setTimeout(function () {
                              e()
                            }, 500)
                          },
                          function () {
                            setTimeout(function () {
                              n()
                            }, 500)
                          }
                        ))
                    })
                    return e
                  }
                },
                {
                  key: 'JS_Pause',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return t < 0 || t > p[z] - 1
                        ? void n()
                        : p[I][t].bPlay
                          ? p[I][t].bFrameForward
                            ? void n()
                            : void T.pause(p[I][t].szStreamUUID).then(
                              function () {
                                p[I][t].oPlayCtrl.PlayM4_Pause(!0), e()
                              },
                              function (t) {
                                n(t)
                              }
                            )
                          : void n()
                    })
                    return e
                  }
                },
                {
                  key: 'JS_Resume',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return t < 0 || t > p[z] - 1
                        ? void n()
                        : p[I][t].bPlay
                          ? void T.resume(p[I][t].szStreamUUID).then(
                            function () {
                              p[R] !== 1 &&
                                      ((p[I][t].iRate = p[R]),
                                      T.setPlayRate(
                                        p[I][t].szStreamUUID,
                                        p[I][t].iRate
                                      ),
                                      p[I][t].oPlayCtrl.PlayM4_PlayRate(
                                        p[I][t].iRate
                                      ),
                                      p[R] > 1
                                        ? p[I][
                                          t
                                        ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                          g
                                        )
                                        : p[I][
                                          t
                                        ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                          v
                                        )),
                              p[I][t].bFrameForward
                                ? (p[I][t].oPlayCtrl.PlayM4_Play(
                                  p[I][t].windowID
                                ),
                                (p[I][t].bFrameForward = !1))
                                : p[I][t].oPlayCtrl.PlayM4_Pause(!1),
                              e()
                            },
                            function (t) {
                              n(t)
                            }
                          )
                          : void n()
                    })
                    return e
                  }
                },
                {
                  key: 'JS_Slow',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return t < 0 || t > p[z] - 1
                        ? void n()
                        : p[I][t].bPlay
                          ? p[I][t].szPlayType !== 'playback'
                            ? void n()
                            : p[I][t].iRate === -8
                              ? void n()
                              : p[I][t].bFrameForward
                                ? void n()
                                : (p[I][t].iRate < 0 &&
                                        p[I][t].iRate > -8 &&
                                        (p[I][t].iRate *= 2),
                                p[I][t].iRate === 1 &&
                                        (p[I][t].iRate *= -2),
                                p[I][t].iRate > 1 && (p[I][t].iRate /= 2),
                                void T.setPlayRate(
                                  p[I][t].szStreamUUID,
                                  p[I][t].iRate
                                ).then(
                                  function () {
                                    p[I][t].iRate < 2
                                      ? p[I][
                                        t
                                      ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                        v
                                      )
                                      : (p[I][
                                        t
                                      ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                        g
                                      ),
                                      p[I][
                                        t
                                      ].oPlayCtrl.PlayM4_SetIFrameDecInterval(
                                        0
                                      )),
                                    p[I][t].oPlayCtrl.PlayM4_PlayRate(
                                      p[I][t].iRate
                                    ),
                                    e()
                                  },
                                  function (t) {
                                    n(t)
                                  }
                                ))
                          : void n()
                    })
                    return e
                  }
                },
                {
                  key: 'JS_Fast',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return t < 0 || t > p[z] - 1
                        ? void n()
                        : p[I][t].bPlay
                          ? p[I][t].szPlayType !== 'playback'
                            ? void n()
                            : p[I][t].bFrameForward
                              ? void n()
                              : p[I][t].iRate === 8
                                ? void n()
                                : (p[I][t].iRate === -2
                                  ? (p[I][t].iRate = 1)
                                  : p[I][t].iRate < -2
                                    ? (p[I][t].iRate /= 2)
                                    : p[I][t].iRate > 0 &&
                                            p[I][t].iRate < 8 &&
                                            (p[I][t].iRate *= 2),
                                void T.setPlayRate(
                                  p[I][t].szStreamUUID,
                                  p[I][t].iRate
                                ).then(
                                  function () {
                                    p[I][t].iRate < 2
                                      ? p[I][
                                        t
                                      ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                        v
                                      )
                                      : (p[I][
                                        t
                                      ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                        g
                                      ),
                                      p[I][t].iRate === 8
                                        ? p[I][
                                          t
                                        ].oPlayCtrl.PlayM4_SetIFrameDecInterval(
                                          2
                                        )
                                        : p[I][
                                          t
                                        ].oPlayCtrl.PlayM4_SetIFrameDecInterval(
                                          0
                                        )),
                                    p[I][t].oPlayCtrl.PlayM4_PlayRate(
                                      p[I][t].iRate
                                    ),
                                    e()
                                  },
                                  function (t) {
                                    n(t)
                                  }
                                ))
                          : void n()
                    })
                    return e
                  }
                },
                {
                  key: 'JS_Transmission',
                  value: function (t, e) {
                    var n = new Promise(function (n, i) {
                      return t < 0 || t > p[z] - 1
                        ? void i()
                        : p[I][t].szStreamUUID
                          ? void T.transmission(
                            p[I][t].szStreamUUID,
                            e
                          ).then(
                            function (t) {
                              n(t)
                            },
                            function (t) {
                              i(t)
                            }
                          )
                          : void i()
                    })
                    return n
                  }
                },
                {
                  key: 'JS_FrameForward',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return t < 0 || t > p[z] - 1
                        ? void n()
                        : p[I][t].bPlay
                          ? (p[I][t].iRate !== 1
                            ? ((p[I][t].iRate = 1),
                            (p[R] = p[I][t].iRate),
                            T.setPlayRate(
                              p[I][t].szStreamUUID,
                              p[I][t].iRate
                            ).then(
                              function () {
                                p[I][t].oPlayCtrl.PlayM4_PlayRate(
                                  p[I][t].iRate
                                ),
                                p[I][
                                  t
                                ].oPlayCtrl.PlayM4_SetDecodeFrameType(
                                  v
                                ),
                                p[I][t].oPlayCtrl.PlayM4_OneByOne(),
                                (p[I][t].bFrameForward = !0)
                              },
                              function (t) {
                                n(t)
                              }
                            ))
                            : (p[I][t].oPlayCtrl.PlayM4_PlayRate(
                              p[I][t].iRate
                            ),
                            p[I][t].oPlayCtrl.PlayM4_SetDecodeFrameType(
                              v
                            ),
                            p[I][t].oPlayCtrl.PlayM4_OneByOne(),
                            (p[I][t].bFrameForward = !0)),
                          void e())
                          : void n()
                    })
                    return e
                  }
                },
                {
                  key: 'JS_GetOSDTime',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      if (t < 0 || t > p[z] - 1) return void n(y)
                      if (!p[I][t].bPlay) return void n(y)
                      var i = p[I][t].oPlayCtrl.PlayM4_GetOSDTime(function (
                        t
                      ) {
                        var n = Date.parse(t.replace(/-/g, ' ')) / 1e3
                        e(n)
                      })
                      return i !== 0 ? void n(y) : void 0
                    })
                    return e
                  }
                },
                {
                  key: 'JS_OpenSound',
                  value: function (t) {
                    return t < 0 || t > p[z] - 1
                      ? y
                      : p[I][t].bPlay
                        ? p[E] === t
                          ? y
                          : (p[E] !== -1 &&
                                  p[I][p[E]].oPlayCtrl.PlayM4_StopSound(),
                          p[I][t].oPlayCtrl.PlayM4_PlaySound(t) !== 0
                            ? y
                            : ((p[E] = t), m))
                        : y
                  }
                },
                {
                  key: 'JS_GetVolume',
                  value: function (t, e) {
                    p[I][t].oPlayCtrl.PlayM4_GetVolume(function (t) {
                      e(t)
                    })
                  }
                },
                {
                  key: 'JS_SetVolume',
                  value: function (t, e) {
                    return p[I][t].oPlayCtrl.PlayM4_SetVolume(e) !== 0
                      ? y
                      : m
                  }
                },
                {
                  key: 'JS_CloseSound',
                  value: function () {
                    var t = p[E]
                    return t < 0 || t > p[z] - 1
                      ? y
                      : p[I][t].bPlay
                        ? p[I][t].oPlayCtrl.PlayM4_StopSound() !== 0
                          ? y
                          : ((p[E] = -1), m)
                        : y
                  }
                },
                {
                  key: 'JS_EnableZoom',
                  value: function (t) {
                    return t < 0 || t > p[z] - 1
                      ? y
                      : p[I][t].bPlay
                        ? ((0, h.default)('.draw-window').unbind(),
                        (this[A] = new f.ESCanvas('canvas_draw' + t)),
                        this[A].setShapeType('Rect'),
                        this[A].setDrawStyle('#ff0000', '', 0),
                        this[A].setDrawStatus(!0, function (e) {
                          e.startPos &&
                                  e.endPos &&
                                  (e.startPos[0] > e.endPos[0]
                                    ? p[I][t].oPlayCtrl.PlayM4_SetDisplayRegion(
                                      null,
                                      !1
                                    )
                                    : p[I][t].oPlayCtrl.PlayM4_SetDisplayRegion(
                                      {
                                        left: e.startPos[0],
                                        top: e.startPos[1],
                                        right: e.endPos[0],
                                        bottom: e.endPos[1]
                                      },
                                      !0
                                    ))
                        }),
                        (p[I][t].bEZoom = !0),
                        m)
                        : y
                  }
                },
                {
                  key: 'JS_DisableZoom',
                  value: function (t) {
                    return t < 0 || t > p[z] - 1
                      ? y
                      : p[I][t].bPlay
                        ? (this[A].setDrawStatus(!1),
                        this[I][t].oPlayCtrl.PlayM4_SetDisplayRegion(
                          null,
                          !1
                        ) !==
                              0
                          ? y
                          : ((this[I][t].bEZoom = !1), m))
                        : y
                  }
                },
                {
                  key: 'JS_Enable3DZoom',
                  value: function (t, e) {
                    return t < 0 || t > p[z] - 1
                      ? y
                      : p[I][t].bPlay
                        ? ((0, h.default)('.draw-window').unbind(),
                        (this[j] = e),
                        (this[A] = new f.ESCanvas('canvas_draw' + t)),
                        this[A].setShapeType('Rect'),
                        this[A].setDrawStyle('#ff0000', '', 0),
                        this[A].setDrawStatus(!0, function (t) {
                          e(t)
                        }),
                        (p[I][t].b3DZoom = !0),
                        m)
                        : y
                  }
                },
                {
                  key: 'JS_Disable3DZoom',
                  value: function (t) {
                    return t < 0 || t > p[z] - 1
                      ? y
                      : p[I][t].bPlay
                        ? (this[A].setDrawStatus(!1),
                        (this[I][t].b3DZoom = !1),
                        m)
                        : y
                  }
                },
                {
                  key: 'JS_CapturePicture',
                  value: function (t, e, n) {
                    var i = new Promise(function (i, r) {
                      return t < 0 || t > p[z] - 1
                        ? void r()
                        : p[I][t].bPlay
                          ? (n || (n = 'JPEG'),
                          void (n === 'BMP'
                            ? p[I][t].oPlayCtrl.PlayM4_GetBMP(function (
                              t
                            ) {
                              t === 6
                                ? r(x)
                                : (a.oTool.downloadFile(t, e + '.BMP'),
                                i())
                            })
                            : n === 'JPEG' &&
                                    p[I][t].oPlayCtrl.PlayM4_GetJPEG(function (
                                      t
                                    ) {
                                      t === 6
                                        ? r(x)
                                        : (a.oTool.downloadFile(t, e + '.jpeg'),
                                        i())
                                    })))
                          : void r()
                    })
                    return i
                  }
                },
                {
                  key: 'JS_StopRealPlayAll',
                  value: function () {
                    T.stopAll(),
                    p[I].forEach(function (t, e) {
                      t.bPlay &&
                              (t.szStorageUUID && p.JS_StopSave(e),
                              t.bEZoom && p.JS_DisableZoom(e),
                              t.oPlayCtrl.PlayM4_Stop(),
                              t.oPlayCtrl.PlayM4_CloseStream()),
                      (t.bPlay = !1)
                    }),
                    (p[E] = -1)
                  }
                },
                {
                  key: 'JS_StartSave',
                  value: function (t, e) {
                    var n = new Promise(function (n, i) {
                      if (t < 0 || t > p[z] - 1) return void i()
                      if (!p[I][t].bPlay) return void i()
                      e.indexOf('.mp4') < 0 && (e += '.mp4')
                      var r = p[I][t].aHead
                      var o = 0
                      p[I][t].szPlayType === 'playback' && (o = 1),
                      k
                        .startRecord(e, r, 2, o, {
                          cbEventHandler: function (e) {
                            p.EventCallback.pluginErrorHandler(t, e)
                          }
                        })
                        .then(
                          function (e) {
                            (p[I][t].szStorageUUID = e), n()
                          },
                          function () {
                            i()
                          }
                        )
                    })
                    return n
                  }
                },
                {
                  key: 'JS_StopSave',
                  value: function (t) {
                    var e = new Promise(function (e, n) {
                      return p[I][t].szStorageUUID
                        ? void k.stopRecord(p[I][t].szStorageUUID).then(
                          function () {
                            (p[I][t].szStorageUUID = ''), e()
                          },
                          function (t) {
                            n(t)
                          }
                        )
                        : void n()
                    })
                    return e
                  }
                },
                {
                  key: 'JS_GetLocalConfig',
                  value: function () {
                    return ''
                  }
                },
                {
                  key: 'JS_SetLocalConfig',
                  value: function () {
                    return !0
                  }
                },
                {
                  key: 'JS_SetGridInfo',
                  value: function (t) {
                    if (t === null || typeof t === 'undefined') return -1
                    var e = '#ff0000'
                    return (
                      t.style &&
                            t.style.drawColor &&
                            (e = t.style.drawColor),
                      this[A].setDrawStyle(e),
                      this[A].setShapesInfoByType('Grid', [
                        {
                          szGridMap: t.gridMap,
                          iGridColNum: t.gridColNum,
                          iGridRowNum: t.gridRowNum
                        }
                      ]),
                      0
                    )
                  }
                },
                {
                  key: 'JS_GetGridInfo',
                  value: function () {
                    if (!this[A]) return {}
                    var t = this[A].getShapesInfoByType('Grid')[0]
                    return t
                      ? {
                        gridColNum: t.iGridColNum,
                        gridRowNum: t.iGridRowNum,
                        gridMap: t.szGridMap
                      }
                      : { iGridRowNum: 18, iGridColNum: 22, szGridMap: '' }
                  }
                },
                {
                  key: 'JS_SetDrawShapeInfo',
                  value: function (t, e) {
                    return typeof t === 'undefined' || t === ''
                      ? -1
                      : (this[A].setShapeType(t),
                      e.style &&
                              this[A].setDrawStyle(
                                e.style.szDrawColor || '',
                                e.style.szFillColor || '',
                                e.style.iTranslucent || 0
                              ),
                      e.iMaxShapeSupport &&
                              e.iMaxShapeSupport > 0 &&
                              this[A].setMaxShapeSupport(e.iMaxShapeSupport),
                      void (
                        e.iMaxPointSupport &&
                              e.iMaxPointSupport > 0 &&
                              this[A].setCurrentShapeInfo({
                                szId: '',
                                szTips: '',
                                iMinClosed: 3,
                                iMaxPointNum: e.iMaxPointSupport,
                                iPolygonType: 1,
                                szDrawColor: e.style.szDrawColor || '',
                                szFillColor: e.style.szFillColor || '',
                                iTranslucent: e.style.iTranslucent || 0
                              })
                      ))
                  }
                },
                {
                  key: 'JS_SetPolygonInfo',
                  value: function (t) {
                    if (typeof t === 'undefined' || !t.length) return -1
                    var e = []
                    if (t.length > 0) {
                      for (var n = 0, i = t.length; n < i; n++) {
                        var r = t[n].aPoint
                        r.length > 0 && e.push(t[n])
                      }
                    }
                    return e.length > 0
                      ? (this[A].setShapesInfoByType('Polygon', e), 0)
                      : -1
                  }
                },
                {
                  key: 'JS_GetPolygonInfo',
                  value: function () {
                    for (
                      var t = [],
                        e = this[A].getShapesInfoByType('Polygon'),
                        n = 0,
                        i = e.length;
                      n < i;
                      n++
                    ) {
                      var r = e[n]
                      var o = {
                        aPoint: r.aPoint,
                        bClosed: r.bClosed,
                        szTips: r.szTips
                      }
                      t.push(o)
                    }
                    return t
                  }
                },
                {
                  key: 'JS_SetLineInfo',
                  value: function (t) {
                    if (typeof t === 'undefined' || !t.length) return -1
                    var e = []
                    if (t.length > 0) {
                      for (var n = 0, i = t.length; n < i; n++) {
                        var r = t[n].aPoint
                        r.length > 0 && e.push(t[n])
                      }
                    }
                    return e.length > 0
                      ? (this[A].setShapesInfoByType('Line', e), 0)
                      : -1
                  }
                },
                {
                  key: 'JS_GetLineInfo',
                  value: function () {
                    for (
                      var t = [],
                        e = this[A].getShapesInfoByType('Line'),
                        n = 0,
                        i = e.length;
                      n < i;
                      n++
                    ) {
                      var r = e[n]
                      var o = {
                        iLineType: r.iLineType,
                        aPoint: r.aPoint,
                        szTips: r.szTips
                      }
                      t.push(o)
                    }
                    return t
                  }
                },
                {
                  key: 'JS_SetRectInfo',
                  value: function (t) {
                    if (typeof t === 'undefined' || !t.length) return -1
                    var e = []
                    if (t.length > 0) {
                      for (var n = 0, i = t.length; n < i; n++) {
                        var r = t[n].aPoint
                        r.length > 0 && e.push(t[n])
                      }
                    }
                    return e.length > 0
                      ? (this[A].setShapesInfoByType('Rect', e), 0)
                      : -1
                  }
                },
                {
                  key: 'JS_GetRectInfo',
                  value: function () {
                    for (
                      var t = [],
                        e = this[A].getShapesInfoByType('Rect'),
                        n = 0,
                        i = e.length;
                      n < i;
                      n++
                    ) {
                      var r = e[n]
                      var o = { aPoint: r.aPoint, szTips: r.szTips }
                      t.push(o)
                    }
                    return t
                  }
                },
                {
                  key: 'JS_SetRegionInfo',
                  value: function (t) {
                    var e = this
                    this[A].clearAllShape()
                    var n = a.oTool.parseXmlFromStr(t)
                    if (
                      (this[A].setDrawStyle('#ff0000', '#343434', 0.3),
                      (0, h.default)(n).find('DetectionRegionInfo').length >
                            0)
                    ) {
                      !(function () {
                        e[A].setShapeType('Rect')
                        var t = parseInt(
                          (0, h.default)(n)
                            .find('MaxRegionNum')
                            .eq(0)
                            .text(),
                          10
                        )
                        e[A].setMaxShapeSupport(t),
                        (e[A].m_szDisplayMode = (0, h.default)(n)
                          .find('DisplayMode')
                          .eq(0)
                          .text()),
                        (e[A].m_szVideoFormat = (0, h.default)(n)
                          .find('videoFormat')
                          .eq(0)
                          .text()),
                        (e[A].m_iHorizontalResolution = parseInt(
                          (0, h.default)(n)
                            .find('HorizontalResolution')
                            .eq(0)
                            .text(),
                          10
                        )),
                        (e[A].m_iVerticalResolution = parseInt(
                          (0, h.default)(n)
                            .find('VerticalResolution')
                            .eq(0)
                            .text(),
                          10
                        ))
                        var i = [];
                        (0, h.default)(n)
                          .find('DetectionRegion')
                          .each(function () {
                            for (
                              var t = [],
                                e = 0,
                                n = (0, h.default)(this).find('positionX')
                                  .length;
                              e < n;
                              e++
                            ) {
                              var r =
                                      Math.round(
                                        (0, h.default)(this)
                                          .find('positionX')
                                          .eq(e)
                                          .text()
                                      ) *
                                      p[A].m_iCanvasWidth /
                                      p[A].m_iHorizontalResolution
                              var o =
                                      (p[A].m_iVerticalResolution -
                                        Math.round(
                                          (0, h.default)(this)
                                            .find('positionY')
                                            .eq(e)
                                            .text()
                                        )) *
                                      p[A].m_iCanvasHeight /
                                      p[A].m_iVerticalResolution
                              t.push([r, o])
                            }
                            t.length > 0 &&
                                  (t[0][0] !== 0 ||
                                    t[1][0] !== 0 ||
                                    t[2][0] !== 0 ||
                                    t[3][0] !== 0) &&
                                  i.push({
                                    aPoint: t,
                                    iEditType:
                                      p[A].m_szDisplayMode === 'transparent'
                                        ? 1
                                        : 0
                                  })
                          }),
                        e[A].setShapesInfoByType('Rect', i)
                      })()
                    } else if (
                      (0, h.default)(n).find('MoveDetection').length > 0
                    ) {
                      this[A].setShapeType('Grid')
                      var i = parseInt(
                        (0, h.default)(n)
                          .find('columnGranularity')
                          .eq(0)
                          .text(),
                        10
                      )
                      var r = parseInt(
                        (0, h.default)(n)
                          .find('rowGranularity')
                          .eq(0)
                          .text(),
                        10
                      )
                      var o = (0, h.default)(n)
                        .find('gridMap')
                        .eq(0)
                        .text()
                      this[A].setShapesInfoByType('Grid', [
                        { szGridMap: o, iGridColNum: i, iGridRowNum: r }
                      ])
                    }
                    return 0
                  }
                },
                {
                  key: 'JS_GetRegionInfo',
                  value: function () {
                    if (!this[A]) return ''
                    var t = this[A].getShapeType()
                    var e = '<?xml version="1.0" encoding="utf-8"?>'
                    if (t === 'Rect') {
                      (e += '<DetectionRegionInfo>'),
                      (e +=
                              '<videoFormat>' +
                              this[A].m_szVideoFormat +
                              '</videoFormat><RegionType>roi</RegionType>'),
                      (e +=
                              '<ROI><HorizontalResolution>' +
                              this[A].m_iHorizontalResolution +
                              '</HorizontalResolution><VerticalResolution>' +
                              this[A].m_iVerticalResolution +
                              '</VerticalResolution></ROI>'),
                      (e +=
                              '<DisplayMode>' +
                              this[A].m_szDisplayMode +
                              '</DisplayMode><MaxRegionNum>' +
                              this[A].getMaxShapeSupport() +
                              '</MaxRegionNum>'),
                      (e += '<DetectionRegionList>')
                      for (
                        var n = this[A].getShapesInfoByType('Rect'),
                          i = 0,
                          r = n.length;
                        i < r;
                        i++
                      ) {
                        var o = n[i].aPoint;
                        (e += '<DetectionRegion><RegionCoordinatesList>'),
                        (e +=
                                '<RegionCoordinates><positionX>' +
                                Math.round(
                                  o[3][0] *
                                    this[A].m_iHorizontalResolution /
                                    this[A].m_iCanvasWidth
                                ) +
                                '</positionX><positionY>' +
                                (this[A].m_iVerticalResolution -
                                  Math.round(
                                    o[3][1] *
                                      this[A].m_iVerticalResolution /
                                      this[A].m_iCanvasHeight
                                  )) +
                                '</positionY></RegionCoordinates>'),
                        (e +=
                                '<RegionCoordinates><positionX>' +
                                Math.round(
                                  o[2][0] *
                                    this[A].m_iHorizontalResolution /
                                    this[A].m_iCanvasWidth
                                ) +
                                '</positionX><positionY>' +
                                (this[A].m_iVerticalResolution -
                                  Math.round(
                                    o[2][1] *
                                      this[A].m_iVerticalResolution /
                                      this[A].m_iCanvasHeight
                                  )) +
                                '</positionY></RegionCoordinates>'),
                        (e +=
                                '<RegionCoordinates><positionX>' +
                                Math.round(
                                  o[1][0] *
                                    this[A].m_iHorizontalResolution /
                                    this[A].m_iCanvasWidth
                                ) +
                                '</positionX><positionY>' +
                                (this[A].m_iVerticalResolution -
                                  Math.round(
                                    o[1][1] *
                                      this[A].m_iVerticalResolution /
                                      this[A].m_iCanvasHeight
                                  )) +
                                '</positionY></RegionCoordinates>'),
                        (e +=
                                '<RegionCoordinates><positionX>' +
                                Math.round(
                                  o[0][0] *
                                    this[A].m_iHorizontalResolution /
                                    this[A].m_iCanvasWidth
                                ) +
                                '</positionX><positionY>' +
                                (this[A].m_iVerticalResolution -
                                  Math.round(
                                    o[0][1] *
                                      this[A].m_iVerticalResolution /
                                      this[A].m_iCanvasHeight
                                  )) +
                                '</positionY></RegionCoordinates>'),
                        (e +=
                                '</RegionCoordinatesList></DetectionRegion>')
                      }
                      (e += '</DetectionRegionList>'),
                      (e += '</DetectionRegionInfo>')
                    } else if (t === 'Grid') {
                      var a = this[A].getShapesInfoByType('Grid')[0]
                      a ||
                            (a = {
                              iGridRowNum: 18,
                              iGridColNum: 22,
                              szGridMap: ''
                            }),
                      (e +=
                              '<MoveDetection><videoFormat>PAL</videoFormat><RegionType>grid</RegionType>'),
                      (e +=
                              '<Grid><rowGranularity>' +
                              a.iGridRowNum +
                              '</rowGranularity><columnGranularity>' +
                              a.iGridColNum +
                              '</columnGranularity></Grid>'),
                      (e += '<DisplayMode>transparent</DisplayMode>'),
                      (e +=
                              '<gridMap>' +
                              a.szGridMap +
                              '</gridMap></MoveDetection>')
                    }
                    return e
                  }
                },
                {
                  key: 'JS_SetDrawStatus',
                  value: function (t) {
                    return this[A] ? (this[A].setDrawStatus(t), 0) : -1
                  }
                },
                {
                  key: 'JS_ClearRegion',
                  value: function () {
                    return this[A] ? (this[A].clearAllShape(), 0) : -1
                  }
                },
                {
                  key: 'JS_GetTextOverlay',
                  value: function () {
                    if (!this[A]) return ''
                    var t = '<?xml version="1.0" encoding="utf-8"?>';
                    (t += '<OSD>'),
                    (t +=
                            '<videoResolutionWidth>' +
                            this[A].m_iHorizontalResolution +
                            '</videoResolutionWidth>'),
                    (t +=
                            '<videoResolutionHeight>' +
                            this[A].m_iVerticalResolution +
                            '</videoResolutionHeight>')
                    for (
                      var e = '',
                        n = '',
                        i = '',
                        r = this[A].getShapesInfoByType('RectOSD'),
                        o = 0,
                        a = r.length;
                      o < a;
                      o++
                    ) {
                      var s = r[o]
                      var u = Math.round(
                        s.iPositionX *
                                this[A].m_iHorizontalResolution /
                                this[A].m_iCanvasWidth
                      )
                      var l = Math.round(
                        s.iPositionY *
                                this[A].m_iVerticalResolution /
                                this[A].m_iCanvasHeight
                      )
                      s.szOSDType === 'overlay-date'
                        ? ((e +=
                                '<DateTimeOverlay><Type>' +
                                s.szDateStyle +
                                '</Type>'),
                        (e +=
                                '<clockType>' + s.szClockType + '</clockType>'),
                        (e +=
                                '<displayWeek>' +
                                s.szDisplayWeek +
                                '</displayWeek>'),
                        (e += '<enabled>' + s.szEnabled + '</enabled>'),
                        (e +=
                                '<positionX>' +
                                u +
                                '</positionX><positionY>' +
                                l +
                                '</positionY></DateTimeOverlay>'))
                        : s.szOSDType === 'overlay-ch'
                          ? ((n +=
                                  '<channelNameOverlay><enabled>' +
                                  s.szEnabled +
                                  '</enabled>'),
                          (n +=
                                  '<ChannelName>' +
                                  s.szText +
                                  '</ChannelName>'),
                          (n +=
                                  '<positionX>' +
                                  u +
                                  '</positionX><positionY>' +
                                  l +
                                  '</positionY></channelNameOverlay>'))
                          : s.szOSDType === 'overlay-text' &&
                                ((i +=
                                  '<TextOverlay><id>' +
                                  s.szId +
                                  '</id><enabled>' +
                                  s.szEnabled +
                                  '</enabled>'),
                                (i +=
                                  '<displayText>' +
                                  s.szText +
                                  '</displayText>'),
                                (i +=
                                  '<positionX>' +
                                  u +
                                  '</positionX><positionY>' +
                                  l +
                                  '</positionY></TextOverlay>'))
                    }
                    return (
                      (t += e),
                      (t += n),
                      (t += '<TextOverlayList>'),
                      (t += i),
                      (t += '</TextOverlayList>'),
                      (t += '</OSD>')
                    )
                  }
                },
                {
                  key: 'JS_SetTextOverlay',
                  value: function (t) {
                    this[A].setMaxShapeSupport(20)
                    var e = a.oTool.parseXmlFromStr(t)
                    if (
                      (this[A].clearShapeByType('RectOSD'),
                      (0, h.default)(e).find('OSD').length > 0)
                    ) {
                      if (
                        (this[A].setDrawStyle('#ff0000', '#343434', 0.7),
                        (this[A].m_iHorizontalResolution = parseInt(
                          (0, h.default)(e)
                            .find('videoResolutionWidth')
                            .eq(0)
                            .text(),
                          10
                        )),
                        (this[A].m_iVerticalResolution = parseInt(
                          (0, h.default)(e)
                            .find('videoResolutionHeight')
                            .eq(0)
                            .text(),
                          10
                        )),
                        (0, h.default)(e).find('channelNameOverlay')
                          .length > 0)
                      ) {
                        var n = (0, h.default)(e)
                          .find('channelNameOverlay')
                          .eq(0)
                        var i = (0, h.default)(n)
                          .find('ChannelName')
                          .eq(0)
                          .text()
                        var r = (0, h.default)(n)
                          .find('enabled')
                          .eq(0)
                          .text()
                        var o =
                                Math.round(
                                  (0, h.default)(n)
                                    .find('positionX')
                                    .eq(0)
                                    .text()
                                ) *
                                this[A].m_iCanvasWidth /
                                this[A].m_iHorizontalResolution
                        var s =
                                Math.round(
                                  (0, h.default)(n)
                                    .find('positionY')
                                    .eq(0)
                                    .text()
                                ) *
                                this[A].m_iCanvasHeight /
                                this[A].m_iVerticalResolution
                        this[A].addOSDShape(i, r, o, s, {
                          szOSDType: 'overlay-ch'
                        })
                      }
                      if (
                        (0, h.default)(e).find('DateTimeOverlay').length > 0
                      ) {
                        var u = (0, h.default)(e)
                          .find('DateTimeOverlay')
                          .eq(0)
                        var l = (0, h.default)(u)
                          .find('enabled')
                          .eq(0)
                          .text()
                        var f =
                                (0, h.default)(u)
                                  .find('Type')
                                  .eq(0)
                                  .text() ||
                                (0, h.default)(u)
                                  .find('type')
                                  .eq(0)
                                  .text()
                        var c = (0, h.default)(u)
                          .find('displayWeek')
                          .eq(0)
                          .text()
                        var d = (0, h.default)(u)
                          .find('clockType')
                          .eq(0)
                          .text()
                        var y = ''
                        var m = ''
                        var v = [
                          'Sunday',
                          'Monday',
                          'Tuesday',
                          'Wednesday',
                          'Thursday',
                          'Friday',
                          'Saturday'
                        ]
                        var g = new Date()
                        switch (
                          (c === 'true' && (m = v[g.getDay()]),
                          (d = d === '24hour' ? '' : 'AM/PM'),
                          f)
                        ) {
                          case '0':
                            y = 'YYYY-MM-DD ' + m + ' hh:mm:ss ' + d
                            break
                          case '1':
                            y = 'MM-DD-YYYY ' + m + ' hh:mm:ss ' + d
                            break
                          case '2':
                            y = 'CHR-YYYY-MM-DD ' + m + ' hh:mm:ss ' + d
                            break
                          case '3':
                            y = 'CHR-MM-DD-YYYY ' + m + ' hh:mm:ss ' + d
                            break
                          case '4':
                            y = 'DD-MM-YYYY ' + m + ' hh:mm:ss ' + d
                            break
                          case '5':
                            y = 'CHR-DD-MM-YYYY ' + m + ' hh:mm:ss ' + d
                        }
                        var S =
                                Math.round(
                                  (0, h.default)(u)
                                    .find('positionX')
                                    .eq(0)
                                    .text()
                                ) *
                                this[A].m_iCanvasWidth /
                                this[A].m_iHorizontalResolution
                        var b =
                                Math.round(
                                  (0, h.default)(u)
                                    .find('positionY')
                                    .eq(0)
                                    .text()
                                ) *
                                this[A].m_iCanvasHeight /
                                this[A].m_iVerticalResolution
                        this[A].addOSDShape(y, l, S, b, {
                          szOSDType: 'overlay-date',
                          szDateStyle: f,
                          szDisplayWeek: c,
                          szClockType: d
                        })
                      }
                      (0, h.default)(e).find('TextOverlayList').length >
                            0 &&
                            (0, h.default)(e)
                              .find('TextOverlayList')
                              .eq(0)
                              .find('TextOverlay')
                              .each(function () {
                                var t = (0, h.default)(this)
                                  .find('displayText')
                                  .eq(0)
                                  .text()
                                var e = (0, h.default)(this)
                                  .find('enabled')
                                  .eq(0)
                                  .text()
                                var n = (0, h.default)(this)
                                  .find('id')
                                  .eq(0)
                                  .text()
                                var i =
                                    Math.round(
                                      (0, h.default)(this)
                                        .find('positionX')
                                        .eq(0)
                                        .text()
                                    ) *
                                    p[A].m_iCanvasWidth /
                                    p[A].m_iHorizontalResolution
                                var r =
                                    Math.round(
                                      (0, h.default)(this)
                                        .find('positionY')
                                        .eq(0)
                                        .text()
                                    ) *
                                    p[A].m_iCanvasHeight /
                                    p[A].m_iVerticalResolution
                                p[A].addOSDShape(t, e, i, r, {
                                  szOSDType: 'overlay-text',
                                  szId: n
                                })
                              })
                    }
                    return 0
                  }
                },
                {
                  key: 'JS_ClearSnapInfo',
                  value: function (t) {
                    return this[A]
                      ? (t === 0
                        ? this[A].clearShapeByType('Rect')
                        : t === 1
                          ? this[A].clearShapeByType('Polygon')
                          : t === 2
                            ? this[A].clearShapeByType('Line')
                            : t === 3
                              ? (this[A].clearShapeByType('Rect'),
                              this[A].clearShapeByType('Polygon'))
                              : this[A].clearAllShape(),
                      0)
                      : -1
                  }
                },
                {
                  key: 'JS_ClearTargetPolygon',
                  value: function (t) {
                    var e = a.oTool.parseXmlFromStr(t)
                    var n = this[A].getAllShapesInfo()
                    var i = n.length
                    if (i > 0) {
                      for (var r = 0; r < i; r++) {
                        var o = (0, h.default)(e)
                          .find('id')
                          .eq(0)
                          .text()
                        if (n[r].szType === 'Polygon' && n[r].szId === o) {
                          this[A].deleteShape(r)
                          break
                        }
                      }
                    }
                  }
                },
                {
                  key: 'JS_SetSnapPolygonInfo',
                  value: function (t) {
                    this[A].setShapeType('Polygon'),
                    this[A].setMaxShapeSupport(20),
                    this[A].setDrawStyle('#FFFF00', '#FFFF00', 0.1)
                    var e = a.oTool.parseXmlFromStr(t)
                    var n = this[A].getAllShapesInfo()
                    var i = n.length
                    if (i > 0) {
                      for (var r = 0; r < i; r++) {
                        var o = (0, h.default)(e)
                          .find('id')
                          .eq(0)
                          .text()
                        if (n[r].szType === 'Polygon' && n[r].szId === o) {
                          this[A].deleteShape(r)
                          break
                        }
                      }
                    }
                    var s = []
                    return (
                      (0, h.default)(e).find('SnapPolygonList').length >
                            0 &&
                            (0, h.default)(e)
                              .find('SnapPolygonList')
                              .eq(0)
                              .find('SnapPolygon')
                              .each(function () {
                                var t = (0, h.default)(e)
                                  .find('id')
                                  .eq(0)
                                  .text()
                                var n = parseInt(
                                  (0, h.default)(e)
                                    .find('polygonType')
                                    .eq(0)
                                    .text() || '1',
                                  10
                                )
                                var i =
                                    (0, h.default)(e)
                                      .find('Tips')
                                      .eq(0)
                                      .text() ||
                                    (0, h.default)(e)
                                      .find('tips')
                                      .eq(0)
                                      .text()
                                var r = parseInt(
                                  (0, h.default)(e)
                                    .find('MinClosed')
                                    .eq(0)
                                    .text(),
                                  10
                                )
                                var o = parseInt(
                                  (0, h.default)(e)
                                    .find('PointNumMax')
                                    .eq(0)
                                    .text(),
                                  10
                                )
                                var a =
                                    parseInt(
                                      (0, h.default)(e)
                                        .find('EditType')
                                        .eq(0)
                                        .text(),
                                      10
                                    ) || 0
                                var u =
                                    (0, h.default)(e)
                                      .find('isClosed')
                                      .eq(0)
                                      .text() ===
                                    'true'
                                var l =
                                    'rgb(' +
                                    (0, h.default)(e)
                                      .find('r')
                                      .eq(0)
                                      .text() +
                                    ', ' +
                                    (0, h.default)(e)
                                      .find('g')
                                      .eq(0)
                                      .text() +
                                    ', ' +
                                    (0, h.default)(e)
                                      .find('b')
                                      .eq(0)
                                      .text() +
                                    ')'
                                var f = l
                                var c = 0.1
                                var d = [];
                                (0, h.default)(e)
                                  .find('pointList')
                                  .eq(0)
                                  .find('point')
                                  .each(function (t) {
                                    (d[t] = []),
                                    (d[t][0] = Math.round(
                                      (0, h.default)(this)
                                        .find('x')
                                        .eq(0)
                                        .text() * p[A].m_iCanvasWidth
                                    )),
                                    (d[t][1] = Math.round(
                                      (0, h.default)(this)
                                        .find('y')
                                        .eq(0)
                                        .text() * p[A].m_iCanvasHeight
                                    ))
                                  }),
                                d.length > 0
                                  ? (s.push({
                                    szId: t,
                                    iPolygonType: n,
                                    iMinClosed: r,
                                    iMaxPointNum: o,
                                    iEditType: a,
                                    aPoint: d,
                                    bClosed: u,
                                    szTips: i,
                                    szDrawColor: l,
                                    szFillColor: f,
                                    iTranslucent: c
                                  }),
                                  p[A].setDrawStatus(!1))
                                  : (p[A].setCurrentShapeInfo({
                                    szId: t,
                                    szTips: i,
                                    iMinClosed: r,
                                    iMaxPointNum: o,
                                    iPolygonType: n,
                                    szDrawColor: l,
                                    szFillColor: f,
                                    iTranslucent: c
                                  }),
                                  p[A].setDrawStatus(!0))
                              }),
                      s.length > 0 &&
                            this[A].setShapesInfoByType('Polygon', s),
                      0
                    )
                  }
                },
                {
                  key: 'JS_GetSnapPolygonInfo',
                  value: function () {
                    for (
                      var t =
                              "<?xml version='1.0' encoding='utf-8'?><SnapPolygonList>",
                        e = this[A].getShapesInfoByType('Polygon'),
                        n = 0,
                        i = e.length;
                      n < i;
                      n++
                    ) {
                      var r = e[n];
                      (t += '<SnapPolygon>'),
                      (t += '<id>' + r.szId + '</id>'),
                      (t +=
                              '<polygonType>' +
                              r.iPolygonType +
                              '</polygonType>'),
                      (t += '<color>')
                      var o = r.szDrawColor
                        .substring(4, r.szDrawColor.length - 1)
                        .split(',');
                      (t += '<r>' + o[0] + '</r>'),
                      (t += '<g>' + o[1] + '</g>'),
                      (t += '<b>' + o[2] + '</b>'),
                      (t += '</color>'),
                      (t += '<tips>' + r.szTips + '</tips>'),
                      (t += '<isClosed>' + r.bClosed + '</isClosed>')
                      var a = r.aPoint
                      t += '<pointList>'
                      for (var s = 0, u = a.length; s < u; s++) {
                        t +=
                              '<point><x>' +
                              (a[s][0] / this[A].m_iCanvasWidth).toFixed(6) +
                              '</x><y>' +
                              (a[s][1] / this[A].m_iCanvasHeight).toFixed(6) +
                              '</y></point>'
                      }
                      (t += '</pointList>'), (t += '</SnapPolygon>')
                    }
                    return (t += '</SnapPolygonList>')
                  }
                },
                {
                  key: 'JS_SetSnapDrawMode',
                  value: function () {
                    return this[A]
                      ? (this[A].setDrawMutiShapeOneTime(!1), 0)
                      : -1
                  }
                },
                {
                  key: 'JS_SetSnapLineInfo',
                  value: function (t) {
                    this[A].setShapeType('Line'),
                    this[A].setMaxShapeSupport(20),
                    this[A].setDrawStyle('#FFFF00', '#FFFF00', 0.1)
                    var e = a.oTool.parseXmlFromStr(t)
                    var n = this[A].getAllShapesInfo()
                    var i = n.length
                    if (i > 0) {
                      for (var r = 0; r < i; r++) {
                        var o = (0, h.default)(e)
                          .find('id')
                          .eq(0)
                          .text()
                        if (n[r].szType === 'Line' && n[r].szId === o) {
                          this[A].deleteShape(r)
                          break
                        }
                      }
                    }
                    var s = []
                    return (
                      (0, h.default)(e).find('SnapLineList').length > 0 &&
                            (0, h.default)(e)
                              .find('SnapLineList')
                              .eq(0)
                              .find('SnapLine')
                              .each(function () {
                                var t = (0, h.default)(e)
                                  .find('id')
                                  .eq(0)
                                  .text()
                                var n = parseInt(
                                  (0, h.default)(e)
                                    .find('LineTypeEx')
                                    .eq(0)
                                    .text(),
                                  10
                                )
                                var i =
                                    parseInt(
                                      (0, h.default)(e)
                                        .find('CustomType')
                                        .text(),
                                      10
                                    ) ||
                                    parseInt(
                                      (0, h.default)(e)
                                        .find('LineType')
                                        .text(),
                                      10
                                    )
                                var r =
                                    parseInt(
                                      (0, h.default)(e)
                                        .find('ArrowType')
                                        .text(),
                                      10
                                    ) || 0
                                var o =
                                    (0, h.default)(e)
                                      .find('Tips')
                                      .eq(0)
                                      .text() ||
                                    (0, h.default)(e)
                                      .find('tips')
                                      .eq(0)
                                      .text()
                                var a =
                                    'rgb(' +
                                    (0, h.default)(e)
                                      .find('r')
                                      .eq(0)
                                      .text() +
                                    ', ' +
                                    (0, h.default)(e)
                                      .find('g')
                                      .eq(0)
                                      .text() +
                                    ', ' +
                                    (0, h.default)(e)
                                      .find('b')
                                      .eq(0)
                                      .text() +
                                    ')'
                                var u = [];
                                (u[0] = []),
                                (u[1] = []),
                                (u[0][0] = Math.round(
                                  (0, h.default)(e)
                                    .find('StartPos')
                                    .eq(0)
                                    .find('x')
                                    .eq(0)
                                    .text() * p[A].m_iCanvasWidth
                                )),
                                (u[0][1] = Math.round(
                                  (0, h.default)(e)
                                    .find('StartPos')
                                    .eq(0)
                                    .find('y')
                                    .eq(0)
                                    .text() * p[A].m_iCanvasHeight
                                )),
                                (u[1][0] = Math.round(
                                  (0, h.default)(e)
                                    .find('EndPos')
                                    .eq(0)
                                    .find('x')
                                    .eq(0)
                                    .text() * p[A].m_iCanvasWidth
                                )),
                                (u[1][1] = Math.round(
                                  (0, h.default)(e)
                                    .find('EndPos')
                                    .eq(0)
                                    .find('y')
                                    .eq(0)
                                    .text() * p[A].m_iCanvasHeight
                                )),
                                u.length > 0 &&
                                    (s.push({
                                      szId: t,
                                      iLineType: n,
                                      aPoint: u,
                                      szTips: o,
                                      iDirection: i,
                                      iArrowType: r,
                                      szDrawColor: a
                                    }),
                                    p[A].setDrawStatus(!1))
                              }),
                      s.length > 0 &&
                            this[A].setShapesInfoByType('Line', s),
                      0
                    )
                  }
                },
                {
                  key: 'JS_GetSnapLineInfo',
                  value: function () {
                    for (
                      var t =
                              "<?xml version='1.0' encoding='utf-8'?><SnapLineList>",
                        e = this[A].getShapesInfoByType('Line'),
                        n = 0,
                        i = e.length;
                      n < i;
                      n++
                    ) {
                      (t += '<SnapLine>'),
                      (t += '<id>' + e[n].szId + '</id>'),
                      (t +=
                              '<LineTypeEx>' +
                              e[n].iLineType +
                              '</LineTypeEx>'),
                      (t +=
                              '<CustomType>0</CustomType><MoveChange>0</MoveChange><ArrowType>' +
                              e[n].iArrowType +
                              '</ArrowType>'),
                      (t += '<tips>' + e[n].szTips + '</tips>')
                      var r = e[n].aPoint;
                      (t +=
                            '<StartPos><x>' +
                            (r[0][0] / p[A].m_iCanvasWidth).toFixed(6) +
                            '</x><y>' +
                            (r[0][1] / p[A].m_iCanvasHeight).toFixed(6) +
                            '</y></StartPos>'),
                      (t +=
                              '<EndPos><x>' +
                              (r[1][0] / p[A].m_iCanvasWidth).toFixed(6) +
                              '</x><y>' +
                              (r[1][1] / p[A].m_iCanvasHeight).toFixed(6) +
                              '</y></EndPos>'),
                      (t += '<LineSelected>false</LineSelected>'),
                      e[n].aCrossArrowPoint.length > 0 &&
                              ((t +=
                                '<PDCArrow><Sp_x>' +
                                (
                                  e[n].aCrossArrowPoint[0][0] /
                                  p[A].m_iCanvasWidth
                                ).toFixed(6) +
                                '</Sp_x>'),
                              (t +=
                                '<Sp_y>' +
                                (
                                  e[n].aCrossArrowPoint[0][1] /
                                  p[A].m_iCanvasWidth
                                ).toFixed(6) +
                                '</Sp_y>'),
                              (t +=
                                '<Ep_x>' +
                                (
                                  e[n].aCrossArrowPoint[1][0] /
                                  p[A].m_iCanvasWidth
                                ).toFixed(6) +
                                '</Ep_x>'),
                              (t +=
                                '<Ep_y>' +
                                (
                                  e[n].aCrossArrowPoint[1][1] /
                                  p[A].m_iCanvasWidth
                                ).toFixed(6) +
                                '</Ep_y></PDCArrow>')),
                      (t += '<PDCShowMark>false</PDCShowMark>')
                      var o = e[n].szDrawColor.split(',')[0].split('(')[1]
                      var a = e[n].szDrawColor.split(',')[1]
                      var s = e[n].szDrawColor.split(',')[2].split(')')[0];
                      (t +=
                            '<color><r>' +
                            (o || '255') +
                            '</r><g>' +
                            (a || '255') +
                            '</g><b>' +
                            (s || '0') +
                            '</b></color>'),
                      (t += '</SnapLine>')
                    }
                    return (t += '</SnapLineList>')
                  }
                },
                {
                  key: 'JS_FullScreenDisplay',
                  value: function (t) {
                    if (t) {
                      this[O] = t
                      var e = (0, h.default)('#' + p[M].szId).get(0)
                      e.requestFullScreen
                        ? e.requestFullScreen()
                        : e.webkitRequestFullScreen
                          ? e.webkitRequestFullScreen()
                          : e.mozRequestFullScreen &&
                                e.mozRequestFullScreen()
                    }
                  }
                },
                {
                  key: 'JS_FullScreenSingle',
                  value: function (t) {
                    if (p[I][t].bPlay) {
                      var e =
                              document.fullscreen ||
                              document.webkitIsFullScreen ||
                              document.mozFullScreen ||
                              !1
                      var n = D.find('.parent-wnd')
                        .eq(0)
                        .children()
                        .eq(t)
                        .get(0)
                      if (e) {
                        if (
                          D.find('.parent-wnd')
                            .eq(0)
                            .width() === (0, h.default)(window).width()
                        ) { return }
                        document.exitFullscreen
                          ? document.exitFullscreen()
                          : document.webkitCancelFullScreen
                            ? document.webkitCancelFullScreen()
                            : document.mozCancelFullScreen &&
                                  document.mozCancelFullScreen()
                      } else {
                        n.requestFullScreen
                          ? n.requestFullScreen()
                          : n.webkitRequestFullScreen
                            ? n.webkitRequestFullScreen()
                            : n.mozRequestFullScreen &&
                                  n.mozRequestFullScreen(),
                        (p[B] = D.find('.parent-wnd')
                          .eq(0)
                          .children()
                          .eq(t))
                      }
                    }
                  }
                },
                {
                  key: 'JS_StartDownload',
                  value: function (t, e, n, i) {
                    var r = (0, h.default)(a.oTool.parseXmlFromStr(i))
                      .find('playbackURI')
                      .eq(0)
                      .text()
                    var o = t + '?playbackURI=' + r
                    var s = '.mp4'
                    t.indexOf('picture/Streaming/tracks') > 0 &&
                          ((o = t), (s = '.jpg'))
                    var u = o.indexOf('&name=') + 6
                    var l = o.indexOf('&size=')
                    return (
                      (n = o.substring(u, l)),
                      (0, h.default)('body').append(
                        '<a id="jsplugin_download_a" href="' +
                              o +
                              '" download=' +
                              n +
                              s +
                              '><li id="jsplugin_download_li"></li></a>'
                      ),
                      (0, h.default)('#jsplugin_download_li').trigger(
                        'click'
                      ),
                      (0, h.default)('#jsplugin_download_a').remove(),
                      0
                    )
                  }
                },
                {
                  key: 'JS_Resize',
                  value: function (t, e) {
                    if (
                      (this[O]
                        ? ((t = (0, h.default)(window).width()),
                        (e = (0, h.default)(window).height()),
                        (0, h.default)('#' + this[M].szId).css({
                          width: t,
                          height: e
                        }),
                        (this[O] = !1))
                        : (0, h.default)('#' + this[M].szId).css({
                          width: t,
                          height: e
                        }),
                      (this[M].iWidth = t),
                      (this[M].iHeight = e),
                      a.oTool.isFirefox())
                    ) {
                      for (
                        var n = 0;
                        n < p[M].iMaxSplit * p[M].iMaxSplit;
                        n++
                      ) {
                        p[I][n].oPlayCtrl &&
                              p[I][n].oPlayCtrl.PlayM4_ClearCanvas()
                      }
                    }
                    c(),
                    p[B] &&
                            ((t = (0, h.default)(window).width()),
                            (e = (0, h.default)(window).height()),
                            p[B].css({ width: t, height: e }),
                            p[B].find('canvas').attr('width', t - 2),
                            p[B].find('canvas').attr('height', e - 2),
                            (p[B] = null)),
                    this[A].resizeCanvas(),
                    this[A].canvasRedraw()
                  }
                },
                {
                  key: 'JS_WndCreate',
                  value: function (t, n, i) {
                    e(n, i),
                    this[A].updateCanvas('canvas_draw0'),
                    this[A].clearAllShape(),
                    t === 0
                      ? (0, h.default)('#' + this[M].szId).hide()
                      : (0, h.default)('#' + this[M].szId).show(),
                    p.EventCallback.windowEventSelect(0)
                  }
                },
                {
                  key: 'JS_ExportDeviceConfig',
                  value: function (t) {
                    return (
                      (0, h.default)('body').append(
                        '<a id="jsplugin_download_a" href="' +
                              t +
                              '"><li id="jsplugin_download_li"></li></a>'
                      ),
                      (0, h.default)('#jsplugin_download_li').trigger(
                        'click'
                      ),
                      (0, h.default)('#jsplugin_download_a').remove(),
                      0
                    )
                  }
                },
                {
                  key: 'JS_OpenFileBrowser',
                  value: function (t, e, n, i, r) {
                    p[L] = null
                    var o = window.document.createElement('input');
                    (o.type = 'file'),
                    e.toLowerCase() === 'bmp' && (o.accept = 'image/bmp'),
                    t === 0 && o.setAttribute('webkitdirectory', ''),
                    o.addEventListener('change', function () {
                      t === 1
                        ? ((p[L] = o.files[0]),
                        (n[i] = o.files[0].name),
                        r &&
                                  r.$digest(function () {
                                    n[i] = o.files[0].name
                                  }))
                        : t === 0 && (p[L] = o.files)
                    })
                    var a = document.createEvent('MouseEvents')
                    a.initEvent('click', !0, !0), o.dispatchEvent(a)
                  }
                },
                {
                  key: 'JS_UploadFile',
                  value: function (t, e, n, i) {
                    var r = 0
                    var o = new XMLHttpRequest()
                    return (
                      (o.onreadystatechange = function () {
                        o.readyState === 4 && o.status !== 200 && (r = -1)
                      }),
                      o.open('put', t, !1),
                      o.setRequestHeader('Content-Type', i),
                      o.send(p[L]),
                      r
                    )
                  }
                },
                {
                  key: 'JS_StartAsynUpload',
                  value: function (t) {
                    var e = new XMLHttpRequest()
                    return (
                      (e.onreadystatechange = function () {
                        e.readyState === 4 && (p[N] = e.responseText)
                      }),
                      e.open('put', t, !0),
                      e.send(p[L]),
                      0
                    )
                  }
                },
                {
                  key: 'JS_StopAsynUpload',
                  value: function () {
                    (p[L] = null), (p[N] = '')
                  }
                },
                {
                  key: 'JS_GetUploadErrorInfo',
                  value: function () {
                    return typeof p[N] === 'string' && p[N].length > 0
                      ? p[N]
                      : ''
                  }
                },
                {
                  key: 'JS_StartUpgradeEx',
                  value: function (t, e) {
                    var n = new Promise(function (n, i) {
                      if (!t) return i(), y
                      if (!e) return i(), y
                      p[N] = 0
                      var r = new XMLHttpRequest();
                      (r.onreadystatechange = function () {
                        if (r.readyState === 4) {
                          if (r.status === 200) (p[N] = 100), n()
                          else {
                            p[N] = 1
                            var t = a.oTool.parseXmlFromStr(r.responseText)
                            (0, h.default)(t)
                              .find('subStatusCode')
                              .text() ===
                                'lowPrivilege'
                              ? i(403)
                              : i()
                          }
                        }
                      }),
                      r.open('put', t, !0),
                      r.send(p[L]),
                      (p[q] = e),
                      setTimeout(function () {
                        n()
                      }, 3e3)
                    })
                    return n
                  }
                },
                {
                  key: 'JS_UpgradeStatus',
                  value: function () {
                    return p[N] === 100 ? 0 : p[N]
                  }
                },
                {
                  key: 'JS_UpgradeProgress',
                  value: function () {
                    var t = 0
                    var e = new XMLHttpRequest()
                    return (
                      (e.onreadystatechange = function () {
                        e.readyState === 4 &&
                              e.status === 200 &&
                              (t = parseInt(
                                (0, h.default)(
                                  a.oTool.parseXmlFromStr(e.responseText)
                                )
                                  .find('percent')
                                  .text(),
                                10
                              ))
                      }),
                      e.open('get', p[q], !1),
                      e.send(null),
                      p[N] === 100 ? 100 : t
                    )
                  }
                },
                {
                  key: 'JS_StopUpgrade',
                  value: function () {
                    return (p[L] = null), 0
                  }
                },
                {
                  key: 'JS_ExportDeviceLog',
                  value: function (t, e) {
                    function n (t) {
                      a.push(t)
                      var e = t.slice('')
                      if (/^[\u4e00-\u9fa5]/.test(t)) {
                        for (var n = 0; n < 30 - 2 * e.length; n++) { a.push(' ') }
                      } else { for (var i = 0; i < 30 - e.length; i++) a.push(' ') }
                    }
                    e = 'Log.txt'
                    var i = []
                    var r = []
                    i = i.concat(
                      (0, h.default)(t)
                        .find('searchMatchItem')
                        .toArray()
                    )
                    for (var o = 0; o < i.length; o++) {
                      (r[o] = []),
                      (r[o][0] = (0, h.default)(i[o])
                        .find('logtime')
                        .text()
                        .replace('T', ' ')
                        .replace('Z', '')),
                      (r[o][1] = (0, h.default)(i[o])
                        .find('majortype')
                        .text()),
                      (r[o][2] = (0, h.default)(i[o])
                        .find('minortype')
                        .text()),
                      (r[o][3] = (0, h.default)(i[o])
                        .find('channelid')
                        .text()),
                      (r[o][4] = (0, h.default)(i[o])
                        .find('userName')
                        .text()),
                      (r[o][5] = (0, h.default)(i[o])
                        .find('remoteaddress')
                        .text())
                    }
                    var a = []
                    n(' '),
                    n(
                      (0, h.default)(t)
                        .find('laLogTime')
                        .text()
                    ),
                    n(
                      (0, h.default)(t)
                        .find('laLogMajorType')
                        .text()
                    ),
                    n(
                      (0, h.default)(t)
                        .find('laLogMinorType')
                        .text()
                    ),
                    n(
                      (0, h.default)(t)
                        .find('laLogChannel')
                        .text()
                    ),
                    n(
                      (0, h.default)(t)
                        .find('laLogRemoteUser')
                        .text()
                    ),
                    n(
                      (0, h.default)(t)
                        .find('laLogRemoteIP')
                        .text()
                    ),
                    a.push('\r\n')
                    for (var s = 0; s < r.length; s++) {
                      var u = (s + 1).toString()
                      n(u)
                      for (var l = 0; l < 6; l++) n(r[s][l])
                      a.push('\r\n')
                    }
                    a = a.join('')
                    var f = new Blob([a], { type: 'text/plain' })
                    var c = (window.URL || window.webkitURL).createObjectURL(
                      f
                    )
                    var d = window.document.createElement('a');
                    (d.href = c), (d.download = e)
                    var p = document.createEvent('MouseEvents')
                    p.initEvent('click', !0, !0), d.dispatchEvent(p)
                  }
                },
                {
                  key: 'JS_GetWndContainer',
                  value: function (t) {
                    return t < 0 || typeof t === 'undefined' || t === null
                      ? -1
                      : D.find('.parent-wnd')
                        .eq(0)
                        .children()
                        .eq(t)[0]
                  }
                },
                {
                  key: 'JS_GetWndStatus',
                  value: function (t) {
                    if (t < 0 || typeof t === 'undefined' || t === null) { return -1 }
                    var e = {
                      bPlay: this[I][t].bPlay,
                      bSound: this[E] === t,
                      bSelect: this[I][t].bSelect,
                      iRate: this[I][t].iRate
                    }
                    return e
                  }
                },
                {
                  key: 'JS_SelectWnd',
                  value: function (t) {
                    D.find('.parent-wnd')
                      .eq(0)
                      .children()
                      .eq(t)
                      .mousedown()
                  }
                }
              ]),
              n
            )
          })()
          return W
        }
      })()
      e.JSPlugin = d
    },
    function (t, e, n) {
      var i, r;
      (function (t) {
        'use strict'
        var n =
          typeof Symbol === 'function' && typeof Symbol.iterator === 'symbol'
            ? function (t) {
              return typeof t
            }
            : function (t) {
              return t &&
                  typeof Symbol === 'function' &&
                  t.constructor === Symbol &&
                  t !== Symbol.prototype
                ? 'symbol'
                : typeof t
            } /*!
	 * jQuery JavaScript Library v3.2.1
	 * https://jquery.com/
	 *
	 * Includes Sizzle.js
	 * https://sizzlejs.com/
	 *
	 * Copyright JS Foundation and other contributors
	 * Released under the MIT license
	 * https://jquery.org/license
	 *
	 * Date: 2017-03-20T18:59Z
	 */
        !(function (e, i) {
          n(t) === 'object' && n(t.exports) === 'object'
            ? (t.exports = e.document
              ? i(e, !0)
              : function (t) {
                if (!t.document) {
                  throw new Error(
                    'jQuery requires a window with a document'
                  )
                }
                return i(t)
              })
            : i(e)
        })(typeof window !== 'undefined' ? window : void 0, function (o, a) {
          function s (t, e) {
            e = e || st
            var n = e.createElement('script');
            (n.text = t), e.head.appendChild(n).parentNode.removeChild(n)
          }
          function u (t) {
            var e = !!t && 'length' in t && t.length
            var n = bt.type(t)
            return (
              n !== 'function' &&
              !bt.isWindow(t) &&
              (n === 'array' ||
                e === 0 ||
                (typeof e === 'number' && e > 0 && e - 1 in t))
            )
          }
          function l (t, e) {
            return t.nodeName && t.nodeName.toLowerCase() === e.toLowerCase()
          }
          function f (t, e, n) {
            return bt.isFunction(e)
              ? bt.grep(t, function (t, i) {
                return !!e.call(t, i, t) !== n
              })
              : e.nodeType
                ? bt.grep(t, function (t) {
                  return (t === e) !== n
                })
                : typeof e !== 'string'
                  ? bt.grep(t, function (t) {
                    return ht.call(e, t) > -1 !== n
                  })
                  : Rt.test(e)
                    ? bt.filter(e, t, n)
                    : ((e = bt.filter(e, t)),
                    bt.grep(t, function (t) {
                      return ht.call(e, t) > -1 !== n && t.nodeType === 1
                    }))
          }
          function c (t, e) {
            for (; (t = t[e]) && t.nodeType !== 1;);
            return t
          }
          function h (t) {
            var e = {}
            return (
              bt.each(t.match(Ot) || [], function (t, n) {
                e[n] = !0
              }),
              e
            )
          }
          function d (t) {
            return t
          }
          function p (t) {
            throw t
          }
          function y (t, e, n, i) {
            var r
            try {
              t && bt.isFunction((r = t.promise))
                ? r
                  .call(t)
                  .done(e)
                  .fail(n)
                : t && bt.isFunction((r = t.then))
                  ? r.call(t, e, n)
                  : e.apply(void 0, [t].slice(i))
            } catch (t) {
              n.apply(void 0, [t])
            }
          }
          function m () {
            st.removeEventListener('DOMContentLoaded', m),
            o.removeEventListener('load', m),
            bt.ready()
          }
          function v () {
            this.expando = bt.expando + v.uid++
          }
          function g (t) {
            return (
              t === 'true' ||
              (t !== 'false' &&
                (t === 'null'
                  ? null
                  : t === +t + ''
                    ? +t
                    : Wt.test(t)
                      ? JSON.parse(t)
                      : t))
            )
          }
          function S (t, e, n) {
            var i
            if (void 0 === n && t.nodeType === 1) {
              if (
                ((i = 'data-' + e.replace(Ut, '-$&').toLowerCase()),
                (n = t.getAttribute(i)),
                typeof n === 'string')
              ) {
                try {
                  n = g(n)
                } catch (t) {}
                jt.set(t, e, n)
              } else n = void 0
            }
            return n
          }
          function b (t, e, n, i) {
            var r
            var o = 1
            var a = 20
            var s = i
              ? function () {
                return i.cur()
              }
              : function () {
                return bt.css(t, e, '')
              }
            var u = s()
            var l = (n && n[3]) || (bt.cssNumber[e] ? '' : 'px')
            var f =
                (bt.cssNumber[e] || (l !== 'px' && +u)) &&
                Vt.exec(bt.css(t, e))
            if (f && f[3] !== l) {
              (l = l || f[3]), (n = n || []), (f = +u || 1)
              do (o = o || '.5'), (f /= o), bt.style(t, e, f + l)
              while (o !== (o = s() / u) && o !== 1 && --a)
            }
            return (
              n &&
                ((f = +f || +u || 0),
                (r = n[1] ? f + (n[1] + 1) * n[2] : +n[2]),
                i && ((i.unit = l), (i.start = f), (i.end = r))),
              r
            )
          }
          function w (t) {
            var e
            var n = t.ownerDocument
            var i = t.nodeName
            var r = $t[i]
            return r || ((e = n.body.appendChild(n.createElement(i))),
            (r = bt.css(e, 'display')),
            e.parentNode.removeChild(e),
            r === 'none' && (r = 'block'),
            ($t[i] = r),
            r)
          }
          function _ (t, e) {
            for (var n, i, r = [], o = 0, a = t.length; o < a; o++) {
              (i = t[o]),
              i.style &&
                  ((n = i.style.display),
                  e
                    ? (n === 'none' &&
                        ((r[o] = Ht.get(i, 'display') || null),
                        r[o] || (i.style.display = '')),
                    i.style.display === '' && Xt(i) && (r[o] = w(i)))
                    : n !== 'none' &&
                      ((r[o] = 'none'), Ht.set(i, 'display', n)))
            }
            for (o = 0; o < a; o++) r[o] != null && (t[o].style.display = r[o])
            return t
          }
          function P (t, e) {
            var n
            return (
              (n =
                typeof t.getElementsByTagName !== 'undefined'
                  ? t.getElementsByTagName(e || '*')
                  : typeof t.querySelectorAll !== 'undefined'
                    ? t.querySelectorAll(e || '*')
                    : []),
              void 0 === e || (e && l(t, e)) ? bt.merge([t], n) : n
            )
          }
          function C (t, e) {
            for (var n = 0, i = t.length; n < i; n++) { Ht.set(t[n], 'globalEval', !e || Ht.get(e[n], 'globalEval')) }
          }
          function x (t, e, n, i, r) {
            for (
              var o,
                a,
                s,
                u,
                l,
                f,
                c = e.createDocumentFragment(),
                h = [],
                d = 0,
                p = t.length;
              d < p;
              d++
            ) {
              if (((o = t[d]), o || o === 0)) {
                if (bt.type(o) === 'object') bt.merge(h, o.nodeType ? [o] : o)
                else if (ee.test(o)) {
                  for (
                    a = a || c.appendChild(e.createElement('div')),
                    s = (Zt.exec(o) || ['', ''])[1].toLowerCase(),
                    u = te[s] || te._default,
                    a.innerHTML = u[1] + bt.htmlPrefilter(o) + u[2],
                    f = u[0];
                    f--;

                  ) { a = a.lastChild }
                  bt.merge(h, a.childNodes),
                  (a = c.firstChild),
                  (a.textContent = '')
                } else h.push(e.createTextNode(o))
              }
            }
            for (c.textContent = '', d = 0; (o = h[d++]);) {
              if (i && bt.inArray(o, i) > -1) r && r.push(o)
              else if (
                ((l = bt.contains(o.ownerDocument, o)),
                (a = P(c.appendChild(o), 'script')),
                l && C(a),
                n)
              ) { for (f = 0; (o = a[f++]);) Qt.test(o.type || '') && n.push(o) }
            }
            return c
          }
          function T () {
            return !0
          }
          function k () {
            return !1
          }
          function D () {
            try {
              return st.activeElement
            } catch (t) {}
          }
          function M (t, e, i, r, o, a) {
            var s, u
            if ((typeof e === 'undefined' ? 'undefined' : n(e)) === 'object') {
              typeof i !== 'string' && ((r = r || i), (i = void 0))
              for (u in e) M(t, u, i, r, e[u], a)
              return t
            }
            if (
              (r == null && o == null
                ? ((o = i), (r = i = void 0))
                : o == null &&
                  (typeof i === 'string'
                    ? ((o = r), (r = void 0))
                    : ((o = r), (r = i), (i = void 0))),
              o === !1)
            ) { o = k } else if (!o) return t
            return (
              a === 1 &&
                ((s = o),
                (o = function (t) {
                  return bt().off(t), s.apply(this, arguments)
                }),
                (o.guid = s.guid || (s.guid = bt.guid++))),
              t.each(function () {
                bt.event.add(this, e, o, r, i)
              })
            )
          }
          function R (t, e) {
            return l(t, 'table') &&
              l(e.nodeType !== 11 ? e : e.firstChild, 'tr')
              ? bt('>tbody', t)[0] || t
              : t
          }
          function E (t) {
            return (
              (t.type = (t.getAttribute('type') !== null) + '/' + t.type), t
            )
          }
          function z (t) {
            var e = le.exec(t.type)
            return e ? (t.type = e[1]) : t.removeAttribute('type'), t
          }
          function I (t, e) {
            var n, i, r, o, a, s, u, l
            if (e.nodeType === 1) {
              if (
                Ht.hasData(t) &&
                ((o = Ht.access(t)), (a = Ht.set(e, o)), (l = o.events))
              ) {
                delete a.handle, (a.events = {})
                for (r in l) {
                  for (n = 0, i = l[r].length; n < i; n++) { bt.event.add(e, r, l[r][n]) }
                }
              }
              jt.hasData(t) &&
                ((s = jt.access(t)), (u = bt.extend({}, s)), jt.set(e, u))
            }
          }
          function A (t, e) {
            var n = e.nodeName.toLowerCase()
            n === 'input' && Kt.test(t.type)
              ? (e.checked = t.checked)
              : (n !== 'input' && n !== 'textarea') ||
                (e.defaultValue = t.defaultValue)
          }
          function F (t, e, n, i) {
            e = ft.apply([], e)
            var r
            var o
            var a
            var u
            var l
            var f
            var c = 0
            var h = t.length
            var d = h - 1
            var p = e[0]
            var y = bt.isFunction(p)
            if (
              y ||
              (h > 1 && typeof p === 'string' && !gt.checkClone && ue.test(p))
            ) {
              return t.each(function (r) {
                var o = t.eq(r)
                y && (e[0] = p.call(this, r, o.html())), F(o, e, n, i)
              })
            }
            if (
              h &&
              ((r = x(e, t[0].ownerDocument, !1, t, i)),
              (o = r.firstChild),
              r.childNodes.length === 1 && (r = o),
              o || i)
            ) {
              for (a = bt.map(P(r, 'script'), E), u = a.length; c < h; c++) {
                (l = r),
                c !== d &&
                    ((l = bt.clone(l, !0, !0)),
                    u && bt.merge(a, P(l, 'script'))),
                n.call(t[c], l, c)
              }
              if (u) {
                for (
                  f = a[a.length - 1].ownerDocument, bt.map(a, z), c = 0;
                  c < u;
                  c++
                ) {
                  (l = a[c]),
                  Qt.test(l.type || '') &&
                      !Ht.access(l, 'globalEval') &&
                      bt.contains(f, l) &&
                      (l.src
                        ? bt._evalUrl && bt._evalUrl(l.src)
                        : s(l.textContent.replace(fe, ''), f))
                }
              }
            }
            return t
          }
          function O (t, e, n) {
            for (
              var i, r = e ? bt.filter(e, t) : t, o = 0;
              (i = r[o]) != null;
              o++
            ) {
              n || i.nodeType !== 1 || bt.cleanData(P(i)),
              i.parentNode &&
                  (n && bt.contains(i.ownerDocument, i) && C(P(i, 'script')),
                  i.parentNode.removeChild(i))
            }
            return t
          }
          function B (t, e, n) {
            var i
            var r
            var o
            var a
            var s = t.style
            return (
              (n = n || de(t)),
              n &&
                ((a = n.getPropertyValue(e) || n[e]),
                a !== '' ||
                  bt.contains(t.ownerDocument, t) ||
                  (a = bt.style(t, e)),
                !gt.pixelMarginRight() &&
                  he.test(a) &&
                  ce.test(e) &&
                  ((i = s.width),
                  (r = s.minWidth),
                  (o = s.maxWidth),
                  (s.minWidth = s.maxWidth = s.width = a),
                  (a = n.width),
                  (s.width = i),
                  (s.minWidth = r),
                  (s.maxWidth = o))),
              void 0 !== a ? a + '' : a
            )
          }
          function L (t, e) {
            return {
              get: function () {
                return t()
                  ? void delete this.get
                  : (this.get = e).apply(this, arguments)
              }
            }
          }
          function N (t) {
            if (t in Se) return t
            for (var e = t[0].toUpperCase() + t.slice(1), n = ge.length; n--;) { if (((t = ge[n] + e), t in Se)) return t }
          }
          function q (t) {
            var e = bt.cssProps[t]
            return e || (e = bt.cssProps[t] = N(t) || t), e
          }
          function H (t, e, n) {
            var i = Vt.exec(e)
            return i ? Math.max(0, i[2] - (n || 0)) + (i[3] || 'px') : e
          }
          function j (t, e, n, i, r) {
            var o
            var a = 0
            for (
              o = n === (i ? 'border' : 'content') ? 4 : e === 'width' ? 1 : 0;
              o < 4;
              o += 2
            ) {
              n === 'margin' && (a += bt.css(t, n + Yt[o], !0, r)),
              i
                ? (n === 'content' &&
                      (a -= bt.css(t, 'padding' + Yt[o], !0, r)),
                n !== 'margin' &&
                      (a -= bt.css(t, 'border' + Yt[o] + 'Width', !0, r)))
                : ((a += bt.css(t, 'padding' + Yt[o], !0, r)),
                n !== 'padding' &&
                      (a += bt.css(t, 'border' + Yt[o] + 'Width', !0, r)))
            }
            return a
          }
          function W (t, e, n) {
            var i
            var r = de(t)
            var o = B(t, e, r)
            var a = bt.css(t, 'boxSizing', !1, r) === 'border-box'
            return he.test(o)
              ? o
              : ((i = a && (gt.boxSizingReliable() || o === t.style[e])),
              o === 'auto' &&
                  (o = t['offset' + e[0].toUpperCase() + e.slice(1)]),
              (o = parseFloat(o) || 0),
              o + j(t, e, n || (a ? 'border' : 'content'), i, r) + 'px')
          }
          function U (t, e, n, i, r) {
            return new U.prototype.init(t, e, n, i, r)
          }
          function G () {
            we &&
              (st.hidden === !1 && o.requestAnimationFrame
                ? o.requestAnimationFrame(G)
                : o.setTimeout(G, bt.fx.interval),
              bt.fx.tick())
          }
          function V () {
            return (
              o.setTimeout(function () {
                be = void 0
              }),
              (be = bt.now())
            )
          }
          function Y (t, e) {
            var n
            var i = 0
            var r = { height: t }
            for (e = e ? 1 : 0; i < 4; i += 2 - e) { (n = Yt[i]), (r['margin' + n] = r['padding' + n] = t) }
            return e && (r.opacity = r.width = t), r
          }
          function X (t, e, n) {
            for (
              var i,
                r = (K.tweeners[e] || []).concat(K.tweeners['*']),
                o = 0,
                a = r.length;
              o < a;
              o++
            ) { if ((i = r[o].call(n, e, t))) return i }
          }
          function J (t, e, n) {
            var i
            var r
            var o
            var a
            var s
            var u
            var l
            var f
            var c = 'width' in e || 'height' in e
            var h = this
            var d = {}
            var p = t.style
            var y = t.nodeType && Xt(t)
            var m = Ht.get(t, 'fxshow')
            n.queue ||
              ((a = bt._queueHooks(t, 'fx')),
              a.unqueued == null &&
                ((a.unqueued = 0),
                (s = a.empty.fire),
                (a.empty.fire = function () {
                  a.unqueued || s()
                })),
              a.unqueued++,
              h.always(function () {
                h.always(function () {
                  a.unqueued--, bt.queue(t, 'fx').length || a.empty.fire()
                })
              }))
            for (i in e) {
              if (((r = e[i]), _e.test(r))) {
                if (
                  (delete e[i],
                  (o = o || r === 'toggle'),
                  r === (y ? 'hide' : 'show'))
                ) {
                  if (r !== 'show' || !m || void 0 === m[i]) continue
                  y = !0
                }
                d[i] = (m && m[i]) || bt.style(t, i)
              }
            }
            if (((u = !bt.isEmptyObject(e)), u || !bt.isEmptyObject(d))) {
              c &&
                t.nodeType === 1 &&
                ((n.overflow = [p.overflow, p.overflowX, p.overflowY]),
                (l = m && m.display),
                l == null && (l = Ht.get(t, 'display')),
                (f = bt.css(t, 'display')),
                f === 'none' &&
                  (l
                    ? (f = l)
                    : (_([t], !0),
                    (l = t.style.display || l),
                    (f = bt.css(t, 'display')),
                    _([t]))),
                (f === 'inline' || (f === 'inline-block' && l != null)) &&
                  bt.css(t, 'float') === 'none' &&
                  (u ||
                    (h.done(function () {
                      p.display = l
                    }),
                    l == null &&
                      ((f = p.display), (l = f === 'none' ? '' : f))),
                  (p.display = 'inline-block'))),
              n.overflow &&
                  ((p.overflow = 'hidden'),
                  h.always(function () {
                    (p.overflow = n.overflow[0]),
                    (p.overflowX = n.overflow[1]),
                    (p.overflowY = n.overflow[2])
                  })),
              (u = !1)
              for (i in d) {
                u ||
                  (m
                    ? 'hidden' in m && (y = m.hidden)
                    : (m = Ht.access(t, 'fxshow', { display: l })),
                  o && (m.hidden = !y),
                  y && _([t], !0),
                  h.done(function () {
                    y || _([t]), Ht.remove(t, 'fxshow')
                    for (i in d) bt.style(t, i, d[i])
                  })),
                (u = X(y ? m[i] : 0, i, h)),
                i in m ||
                    ((m[i] = u.start), y && ((u.end = u.start), (u.start = 0)))
              }
            }
          }
          function $ (t, e) {
            var n, i, r, o, a
            for (n in t) {
              if (
                ((i = bt.camelCase(n)),
                (r = e[i]),
                (o = t[n]),
                Array.isArray(o) && ((r = o[1]), (o = t[n] = o[0])),
                n !== i && ((t[i] = o), delete t[n]),
                (a = bt.cssHooks[i]),
                a && 'expand' in a)
              ) {
                (o = a.expand(o)), delete t[i]
                for (n in o) n in t || ((t[n] = o[n]), (e[n] = r))
              } else e[i] = r
            }
          }
          function K (t, e, n) {
            var i
            var r
            var o = 0
            var a = K.prefilters.length
            var s = bt.Deferred().always(function () {
              delete u.elem
            })
            var u = function () {
              if (r) return !1
              for (
                var e = be || V(),
                  n = Math.max(0, l.startTime + l.duration - e),
                  i = n / l.duration || 0,
                  o = 1 - i,
                  a = 0,
                  u = l.tweens.length;
                a < u;
                a++
              ) { l.tweens[a].run(o) }
              return (
                s.notifyWith(t, [l, o, n]),
                o < 1 && u
                  ? n
                  : (u || s.notifyWith(t, [l, 1, 0]),
                  s.resolveWith(t, [l]),
                  !1)
              )
            }
            var l = s.promise({
              elem: t,
              props: bt.extend({}, e),
              opts: bt.extend(
                !0,
                { specialEasing: {}, easing: bt.easing._default },
                n
              ),
              originalProperties: e,
              originalOptions: n,
              startTime: be || V(),
              duration: n.duration,
              tweens: [],
              createTween: function (e, n) {
                var i = bt.Tween(
                  t,
                  l.opts,
                  e,
                  n,
                  l.opts.specialEasing[e] || l.opts.easing
                )
                return l.tweens.push(i), i
              },
              stop: function (e) {
                var n = 0
                var i = e ? l.tweens.length : 0
                if (r) return this
                for (r = !0; n < i; n++) l.tweens[n].run(1)
                return (
                  e
                    ? (s.notifyWith(t, [l, 1, 0]), s.resolveWith(t, [l, e]))
                    : s.rejectWith(t, [l, e]),
                  this
                )
              }
            })
            var f = l.props
            for ($(f, l.opts.specialEasing); o < a; o++) {
              if ((i = K.prefilters[o].call(l, t, f, l.opts))) {
                return (
                  bt.isFunction(i.stop) &&
                    (bt._queueHooks(l.elem, l.opts.queue).stop = bt.proxy(
                      i.stop,
                      i
                    )),
                  i
                )
              }
            }
            return (
              bt.map(f, X, l),
              bt.isFunction(l.opts.start) && l.opts.start.call(t, l),
              l
                .progress(l.opts.progress)
                .done(l.opts.done, l.opts.complete)
                .fail(l.opts.fail)
                .always(l.opts.always),
              bt.fx.timer(
                bt.extend(u, { elem: t, anim: l, queue: l.opts.queue })
              ),
              l
            )
          }
          function Z (t) {
            var e = t.match(Ot) || []
            return e.join(' ')
          }
          function Q (t) {
            return (t.getAttribute && t.getAttribute('class')) || ''
          }
          function tt (t, e, i, r) {
            var o
            if (Array.isArray(e)) {
              bt.each(e, function (e, o) {
                i || Ie.test(t)
                  ? r(t, o)
                  : tt(
                    t +
                        '[' +
                        ((typeof o === 'undefined' ? 'undefined' : n(o)) ===
                          'object' &&
                        o != null
                          ? e
                          : '') +
                        ']',
                    o,
                    i,
                    r
                  )
              })
            } else if (i || bt.type(e) !== 'object') r(t, e)
            else for (o in e) tt(t + '[' + o + ']', e[o], i, r)
          }
          function et (t) {
            return function (e, n) {
              typeof e !== 'string' && ((n = e), (e = '*'))
              var i
              var r = 0
              var o = e.toLowerCase().match(Ot) || []
              if (bt.isFunction(n)) {
                for (; (i = o[r++]);) {
                  i[0] === '+'
                    ? ((i = i.slice(1) || '*'), (t[i] = t[i] || []).unshift(n))
                    : (t[i] = t[i] || []).push(n)
                }
              }
            }
          }
          function nt (t, e, n, i) {
            function r (s) {
              var u
              return (
                (o[s] = !0),
                bt.each(t[s] || [], function (t, s) {
                  var l = s(e, n, i)
                  return typeof l !== 'string' || a || o[l]
                    ? a
                      ? !(u = l)
                      : void 0
                    : (e.dataTypes.unshift(l), r(l), !1)
                }),
                u
              )
            }
            var o = {}
            var a = t === Ge
            return r(e.dataTypes[0]) || (!o['*'] && r('*'))
          }
          function it (t, e) {
            var n
            var i
            var r = bt.ajaxSettings.flatOptions || {}
            for (n in e) { void 0 !== e[n] && ((r[n] ? t : i || (i = {}))[n] = e[n]) }
            return i && bt.extend(!0, t, i), t
          }
          function rt (t, e, n) {
            for (
              var i, r, o, a, s = t.contents, u = t.dataTypes;
              u[0] === '*';

            ) {
              u.shift(),
              void 0 === i &&
                  (i = t.mimeType || e.getResponseHeader('Content-Type'))
            }
            if (i) {
              for (r in s) {
                if (s[r] && s[r].test(i)) {
                  u.unshift(r)
                  break
                }
              }
            }
            if (u[0] in n) o = u[0]
            else {
              for (r in n) {
                if (!u[0] || t.converters[r + ' ' + u[0]]) {
                  o = r
                  break
                }
                a || (a = r)
              }
              o = o || a
            }
            if (o) return o !== u[0] && u.unshift(o), n[o]
          }
          function ot (t, e, n, i) {
            var r
            var o
            var a
            var s
            var u
            var l = {}
            var f = t.dataTypes.slice()
            if (f[1]) { for (a in t.converters) l[a.toLowerCase()] = t.converters[a] }
            for (o = f.shift(); o;) {
              if (
                (t.responseFields[o] && (n[t.responseFields[o]] = e),
                !u && i && t.dataFilter && (e = t.dataFilter(e, t.dataType)),
                (u = o),
                (o = f.shift()))
              ) {
                if (o === '*') o = u
                else if (u !== '*' && u !== o) {
                  if (((a = l[u + ' ' + o] || l['* ' + o]), !a)) {
                    for (r in l) {
                      if (
                        ((s = r.split(' ')),
                        s[1] === o && (a = l[u + ' ' + s[0]] || l['* ' + s[0]]))
                      ) {
                        a === !0
                          ? (a = l[r])
                          : l[r] !== !0 && ((o = s[0]), f.unshift(s[1]))
                        break
                      }
                    }
                  }
                  if (a !== !0) {
                    if (a && t.throws) e = a(e)
                    else {
                      try {
                        e = a(e)
                      } catch (t) {
                        return {
                          state: 'parsererror',
                          error: a ? t : 'No conversion from ' + u + ' to ' + o
                        }
                      }
                    }
                  }
                }
              }
            }
            return { state: 'success', data: e }
          }
          var at = []
          var st = o.document
          var ut = Object.getPrototypeOf
          var lt = at.slice
          var ft = at.concat
          var ct = at.push
          var ht = at.indexOf
          var dt = {}
          var pt = dt.toString
          var yt = dt.hasOwnProperty
          var mt = yt.toString
          var vt = mt.call(Object)
          var gt = {}
          var St = '3.2.1'
          var bt = function t (e, n) {
            return new t.fn.init(e, n)
          }
          var wt = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g
          var _t = /^-ms-/
          var Pt = /-([a-z])/g
          var Ct = function (t, e) {
            return e.toUpperCase()
          };
          (bt.fn = bt.prototype = {
            jquery: St,
            constructor: bt,
            length: 0,
            toArray: function () {
              return lt.call(this)
            },
            get: function (t) {
              return t == null
                ? lt.call(this)
                : t < 0
                  ? this[t + this.length]
                  : this[t]
            },
            pushStack: function (t) {
              var e = bt.merge(this.constructor(), t)
              return (e.prevObject = this), e
            },
            each: function (t) {
              return bt.each(this, t)
            },
            map: function (t) {
              return this.pushStack(
                bt.map(this, function (e, n) {
                  return t.call(e, n, e)
                })
              )
            },
            slice: function () {
              return this.pushStack(lt.apply(this, arguments))
            },
            first: function () {
              return this.eq(0)
            },
            last: function () {
              return this.eq(-1)
            },
            eq: function (t) {
              var e = this.length
              var n = +t + (t < 0 ? e : 0)
              return this.pushStack(n >= 0 && n < e ? [this[n]] : [])
            },
            end: function () {
              return this.prevObject || this.constructor()
            },
            push: ct,
            sort: at.sort,
            splice: at.splice
          }),
          (bt.extend = bt.fn.extend = function () {
            var t
            var e
            var i
            var r
            var o
            var a
            var s = arguments[0] || {}
            var u = 1
            var l = arguments.length
            var f = !1
            for (
              typeof s === 'boolean' &&
                  ((f = s), (s = arguments[u] || {}), u++),
              (typeof s === 'undefined' ? 'undefined' : n(s)) === 'object' ||
                    bt.isFunction(s) ||
                    (s = {}),
              u === l && ((s = this), u--);
              u < l;
              u++
            ) {
              if ((t = arguments[u]) != null) {
                for (e in t) {
                  (i = s[e]),
                  (r = t[e]),
                  s !== r &&
                        (f &&
                        r &&
                        (bt.isPlainObject(r) || (o = Array.isArray(r)))
                          ? (o
                            ? ((o = !1), (a = i && Array.isArray(i) ? i : []))
                            : (a = i && bt.isPlainObject(i) ? i : {}),
                          (s[e] = bt.extend(f, a, r)))
                          : void 0 !== r && (s[e] = r))
                }
              }
            }
            return s
          }),
          bt.extend({
            expando: 'jQuery' + (St + Math.random()).replace(/\D/g, ''),
            isReady: !0,
            error: function (t) {
              throw new Error(t)
            },
            noop: function () {},
            isFunction: function (t) {
              return bt.type(t) === 'function'
            },
            isWindow: function (t) {
              return t != null && t === t.window
            },
            isNumeric: function (t) {
              var e = bt.type(t)
              return (
                (e === 'number' || e === 'string') &&
                  !isNaN(t - parseFloat(t))
              )
            },
            isPlainObject: function (t) {
              var e, n
              return (
                !(!t || pt.call(t) !== '[object Object]') &&
                  (!(e = ut(t)) ||
                    ((n = yt.call(e, 'constructor') && e.constructor),
                    typeof n === 'function' && mt.call(n) === vt))
              )
            },
            isEmptyObject: function (t) {
              var e
              for (e in t) return !1
              return !0
            },
            type: function (t) {
              return t == null
                ? t + ''
                : (typeof t === 'undefined' ? 'undefined' : n(t)) ===
                      'object' ||
                    typeof t === 'function'
                  ? dt[pt.call(t)] || 'object'
                  : typeof t === 'undefined'
                    ? 'undefined'
                    : n(t)
            },
            globalEval: function (t) {
              s(t)
            },
            camelCase: function (t) {
              return t.replace(_t, 'ms-').replace(Pt, Ct)
            },
            each: function (t, e) {
              var n
              var i = 0
              if (u(t)) {
                for (
                  n = t.length;
                  i < n && e.call(t[i], i, t[i]) !== !1;
                  i++
                );
              } else for (i in t) if (e.call(t[i], i, t[i]) === !1) break
              return t
            },
            trim: function (t) {
              return t == null ? '' : (t + '').replace(wt, '')
            },
            makeArray: function (t, e) {
              var n = e || []
              return (
                t != null &&
                    (u(Object(t))
                      ? bt.merge(n, typeof t === 'string' ? [t] : t)
                      : ct.call(n, t)),
                n
              )
            },
            inArray: function (t, e, n) {
              return e == null ? -1 : ht.call(e, t, n)
            },
            merge: function (t, e) {
              for (var n = +e.length, i = 0, r = t.length; i < n; i++) { t[r++] = e[i] }
              return (t.length = r), t
            },
            grep: function (t, e, n) {
              for (var i, r = [], o = 0, a = t.length, s = !n; o < a; o++) { (i = !e(t[o], o)), i !== s && r.push(t[o]) }
              return r
            },
            map: function (t, e, n) {
              var i
              var r
              var o = 0
              var a = []
              if (u(t)) {
                for (i = t.length; o < i; o++) { (r = e(t[o], o, n)), r != null && a.push(r) }
              } else for (o in t) (r = e(t[o], o, n)), r != null && a.push(r)
              return ft.apply([], a)
            },
            guid: 1,
            proxy: function t (e, n) {
              var i, r, t
              if (
                (typeof n === 'string' && ((i = e[n]), (n = e), (e = i)),
                bt.isFunction(e))
              ) {
                return (
                  (r = lt.call(arguments, 2)),
                  (t = function () {
                    return e.apply(n || this, r.concat(lt.call(arguments)))
                  }),
                  (t.guid = e.guid = e.guid || bt.guid++),
                  t
                )
              }
            },
            now: Date.now,
            support: gt
          }),
          typeof Symbol === 'function' &&
              (bt.fn[Symbol.iterator] = at[Symbol.iterator]),
          bt.each(
            'Boolean Number String Function Array Date RegExp Object Error Symbol'.split(
              ' '
            ),
            function (t, e) {
              dt['[object ' + e + ']'] = e.toLowerCase()
            }
          )
          var xt = /*!
	  * Sizzle CSS Selector Engine v2.3.3
	  * https://sizzlejs.com/
	  *
	  * Copyright jQuery Foundation and other contributors
	  * Released under the MIT license
	  * http://jquery.org/license
	  *
	  * Date: 2016-08-08
	  */ (function (
              t
            ) {
              function e (t, e, n, i) {
                var r
                var o
                var a
                var s
                var u
                var l
                var f
                var h = e && e.ownerDocument
                var p = e ? e.nodeType : 9
                if (
                  ((n = n || []),
                  typeof t !== 'string' || !t || (p !== 1 && p !== 9 && p !== 11))
                ) { return n }
                if (
                  !i &&
                ((e ? e.ownerDocument || e : H) !== I && z(e), (e = e || I), F)
                ) {
                  if (p !== 11 && (u = vt.exec(t))) {
                    if ((r = u[1])) {
                      if (p === 9) {
                        if (!(a = e.getElementById(r))) return n
                        if (a.id === r) return n.push(a), n
                      } else if (
                        h &&
                      (a = h.getElementById(r)) &&
                      N(e, a) &&
                      a.id === r
                      ) { return n.push(a), n }
                    } else {
                      if (u[2]) return Z.apply(n, e.getElementsByTagName(t)), n
                      if (
                        (r = u[3]) &&
                      _.getElementsByClassName &&
                      e.getElementsByClassName
                      ) { return Z.apply(n, e.getElementsByClassName(r)), n }
                    }
                  }
                  if (_.qsa && !V[t + ' '] && (!O || !O.test(t))) {
                    if (p !== 1) (h = e), (f = t)
                    else if (e.nodeName.toLowerCase() !== 'object') {
                      for (
                        (s = e.getAttribute('id'))
                          ? (s = s.replace(wt, _t))
                          : e.setAttribute('id', (s = q)),
                        l = T(t),
                        o = l.length;
                        o--;

                      ) { l[o] = '#' + s + ' ' + d(l[o]) }
                      (f = l.join(',')),
                      (h = (gt.test(t) && c(e.parentNode)) || e)
                    }
                    if (f) {
                      try {
                        return Z.apply(n, h.querySelectorAll(f)), n
                      } catch (t) {
                      } finally {
                        s === q && e.removeAttribute('id')
                      }
                    }
                  }
                }
                return D(t.replace(st, '$1'), e, n, i)
              }
              function n () {
                function t (n, i) {
                  return (
                    e.push(n + ' ') > P.cacheLength && delete t[e.shift()],
                    (t[n + ' '] = i)
                  )
                }
                var e = []
                return t
              }
              function i (t) {
                return (t[q] = !0), t
              }
              function r (t) {
                var e = I.createElement('fieldset')
                try {
                  return !!t(e)
                } catch (t) {
                  return !1
                } finally {
                  e.parentNode && e.parentNode.removeChild(e), (e = null)
                }
              }
              function o (t, e) {
                for (var n = t.split('|'), i = n.length; i--;) { P.attrHandle[n[i]] = e }
              }
              function a (t, e) {
                var n = e && t
                var i =
                  n &&
                  t.nodeType === 1 &&
                  e.nodeType === 1 &&
                  t.sourceIndex - e.sourceIndex
                if (i) return i
                if (n) for (; (n = n.nextSibling);) if (n === e) return -1
                return t ? 1 : -1
              }
              function s (t) {
                return function (e) {
                  var n = e.nodeName.toLowerCase()
                  return n === 'input' && e.type === t
                }
              }
              function u (t) {
                return function (e) {
                  var n = e.nodeName.toLowerCase()
                  return (n === 'input' || n === 'button') && e.type === t
                }
              }
              function l (t) {
                return function (e) {
                  return 'form' in e
                    ? e.parentNode && e.disabled === !1
                      ? 'label' in e
                        ? 'label' in e.parentNode
                          ? e.parentNode.disabled === t
                          : e.disabled === t
                        : e.isDisabled === t ||
                        (e.isDisabled !== !t && Ct(e) === t)
                      : e.disabled === t
                    : 'label' in e && e.disabled === t
                }
              }
              function f (t) {
                return i(function (e) {
                  return (
                    (e = +e),
                    i(function (n, i) {
                      for (var r, o = t([], n.length, e), a = o.length; a--;) { n[(r = o[a])] && (n[r] = !(i[r] = n[r])) }
                    })
                  )
                })
              }
              function c (t) {
                return t && typeof t.getElementsByTagName !== 'undefined' && t
              }
              function h () {}
              function d (t) {
                for (var e = 0, n = t.length, i = ''; e < n; e++) i += t[e].value
                return i
              }
              function p (t, e, n) {
                var i = e.dir
                var r = e.next
                var o = r || i
                var a = n && o === 'parentNode'
                var s = W++
                return e.first
                  ? function (e, n, r) {
                    for (; (e = e[i]);) { if (e.nodeType === 1 || a) return t(e, n, r) }
                    return !1
                  }
                  : function (e, n, u) {
                    var l
                    var f
                    var c
                    var h = [j, s]
                    if (u) {
                      for (; (e = e[i]);) { if ((e.nodeType === 1 || a) && t(e, n, u)) return !0 }
                    } else {
                      for (; (e = e[i]);) {
                        if (e.nodeType === 1 || a) {
                          if (
                            ((c = e[q] || (e[q] = {})),
                            (f = c[e.uniqueID] || (c[e.uniqueID] = {})),
                            r && r === e.nodeName.toLowerCase())
                          ) { e = e[i] || e } else {
                            if ((l = f[o]) && l[0] === j && l[1] === s) { return (h[2] = l[2]) }
                            if (((f[o] = h), (h[2] = t(e, n, u)))) return !0
                          }
                        }
                      }
                    }
                    return !1
                  }
              }
              function y (t) {
                return t.length > 1
                  ? function (e, n, i) {
                    for (var r = t.length; r--;) if (!t[r](e, n, i)) return !1
                    return !0
                  }
                  : t[0]
              }
              function m (t, n, i) {
                for (var r = 0, o = n.length; r < o; r++) e(t, n[r], i)
                return i
              }
              function v (t, e, n, i, r) {
                for (
                  var o, a = [], s = 0, u = t.length, l = e != null;
                  s < u;
                  s++
                ) {
                  (o = t[s]) &&
                  ((n && !n(o, i, r)) || (a.push(o), l && e.push(s)))
                }
                return a
              }
              function g (t, e, n, r, o, a) {
                return (
                  r && !r[q] && (r = g(r)),
                  o && !o[q] && (o = g(o, a)),
                  i(function (i, a, s, u) {
                    var l
                    var f
                    var c
                    var h = []
                    var d = []
                    var p = a.length
                    var y = i || m(e || '*', s.nodeType ? [s] : s, [])
                    var g = !t || (!i && e) ? y : v(y, h, t, s, u)
                    var S = n ? (o || (i ? t : p || r) ? [] : a) : g
                    if ((n && n(g, S, s, u), r)) {
                      for (l = v(S, d), r(l, [], s, u), f = l.length; f--;) { (c = l[f]) && (S[d[f]] = !(g[d[f]] = c)) }
                    }
                    if (i) {
                      if (o || t) {
                        if (o) {
                          for (l = [], f = S.length; f--;) { (c = S[f]) && l.push((g[f] = c)) }
                          o(null, (S = []), l, u)
                        }
                        for (f = S.length; f--;) {
                          (c = S[f]) &&
                          (l = o ? tt(i, c) : h[f]) > -1 &&
                          (i[l] = !(a[l] = c))
                        }
                      }
                    } else (S = v(S === a ? S.splice(p, S.length) : S)), o ? o(null, a, S, u) : Z.apply(a, S)
                  })
                )
              }
              function S (t) {
                for (
                  var e,
                    n,
                    i,
                    r = t.length,
                    o = P.relative[t[0].type],
                    a = o || P.relative[' '],
                    s = o ? 1 : 0,
                    u = p(
                      function (t) {
                        return t === e
                      },
                      a,
                      !0
                    ),
                    l = p(
                      function (t) {
                        return tt(e, t) > -1
                      },
                      a,
                      !0
                    ),
                    f = [
                      function (t, n, i) {
                        var r =
                        (!o && (i || n !== M)) ||
                        ((e = n).nodeType ? u(t, n, i) : l(t, n, i))
                        return (e = null), r
                      }
                    ];
                  s < r;
                  s++
                ) {
                  if ((n = P.relative[t[s].type])) f = [p(y(f), n)]
                  else {
                    if (
                      ((n = P.filter[t[s].type].apply(null, t[s].matches)), n[q])
                    ) {
                      for (i = ++s; i < r && !P.relative[t[i].type]; i++);
                      return g(
                        s > 1 && y(f),
                        s > 1 &&
                        d(
                          t
                            .slice(0, s - 1)
                            .concat({ value: t[s - 2].type === ' ' ? '*' : '' })
                        ).replace(st, '$1'),
                        n,
                        s < i && S(t.slice(s, i)),
                        i < r && S((t = t.slice(i))),
                        i < r && d(t)
                      )
                    }
                    f.push(n)
                  }
                }
                return y(f)
              }
              function b (t, n) {
                var r = n.length > 0
                var o = t.length > 0
                var a = function (i, a, s, u, l) {
                  var f
                  var c
                  var h
                  var d = 0
                  var p = '0'
                  var y = i && []
                  var m = []
                  var g = M
                  var S = i || (o && P.find.TAG('*', l))
                  var b = (j += g == null ? 1 : Math.random() || 0.1)
                  var w = S.length
                  for (
                    l && (M = a === I || a || l);
                    p !== w && (f = S[p]) != null;
                    p++
                  ) {
                    if (o && f) {
                      for (
                        c = 0, a || f.ownerDocument === I || (z(f), (s = !F));
                        (h = t[c++]);

                      ) {
                        if (h(f, a || I, s)) {
                          u.push(f)
                          break
                        }
                      }
                      l && (j = b)
                    }
                    r && ((f = !h && f) && d--, i && y.push(f))
                  }
                  if (((d += p), r && p !== d)) {
                    for (c = 0; (h = n[c++]);) h(y, m, a, s)
                    if (i) {
                      if (d > 0) { for (; p--;) y[p] || m[p] || (m[p] = $.call(u)) }
                      m = v(m)
                    }
                    Z.apply(u, m),
                    l &&
                        !i &&
                        m.length > 0 &&
                        d + n.length > 1 &&
                        e.uniqueSort(u)
                  }
                  return l && ((j = b), (M = g)), y
                }
                return r ? i(a) : a
              }
              var w
              var _
              var P
              var C
              var x
              var T
              var k
              var D
              var M
              var R
              var E
              var z
              var I
              var A
              var F
              var O
              var B
              var L
              var N
              var q = 'sizzle' + 1 * new Date()
              var H = t.document
              var j = 0
              var W = 0
              var U = n()
              var G = n()
              var V = n()
              var Y = function (t, e) {
                return t === e && (E = !0), 0
              }
              var X = {}.hasOwnProperty
              var J = []
              var $ = J.pop
              var K = J.push
              var Z = J.push
              var Q = J.slice
              var tt = function (t, e) {
                for (var n = 0, i = t.length; n < i; n++) { if (t[n] === e) return n }
                return -1
              }
              var et =
                'checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped'
              var nt = '[\\x20\\t\\r\\n\\f]'
              var it = '(?:\\\\.|[\\w-]|[^\0-\\xa0])+'
              var rt =
                '\\[' +
                nt +
                '*(' +
                it +
                ')(?:' +
                nt +
                '*([*^$|!~]?=)' +
                nt +
                '*(?:\'((?:\\\\.|[^\\\\\'])*)\'|"((?:\\\\.|[^\\\\"])*)"|(' +
                it +
                '))|)' +
                nt +
                '*\\]'
              var ot =
                ':(' +
                it +
                ')(?:\\(((\'((?:\\\\.|[^\\\\\'])*)\'|"((?:\\\\.|[^\\\\"])*)")|((?:\\\\.|[^\\\\()[\\]]|' +
                rt +
                ')*)|.*)\\)|)'
              var at = new RegExp(nt + '+', 'g')
              var st = new RegExp(
                '^' + nt + '+|((?:^|[^\\\\])(?:\\\\.)*)' + nt + '+$',
                'g'
              )
              var ut = new RegExp('^' + nt + '*,' + nt + '*')
              var lt = new RegExp('^' + nt + '*([>+~]|' + nt + ')' + nt + '*')
              var ft = new RegExp('=' + nt + '*([^\\]\'"]*?)' + nt + '*\\]', 'g')
              var ct = new RegExp(ot)
              var ht = new RegExp('^' + it + '$')
              var dt = {
                ID: new RegExp('^#(' + it + ')'),
                CLASS: new RegExp('^\\.(' + it + ')'),
                TAG: new RegExp('^(' + it + '|[*])'),
                ATTR: new RegExp('^' + rt),
                PSEUDO: new RegExp('^' + ot),
                CHILD: new RegExp(
                  '^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(' +
                    nt +
                    '*(even|odd|(([+-]|)(\\d*)n|)' +
                    nt +
                    '*(?:([+-]|)' +
                    nt +
                    '*(\\d+)|))' +
                    nt +
                    '*\\)|)',
                  'i'
                ),
                bool: new RegExp('^(?:' + et + ')$', 'i'),
                needsContext: new RegExp(
                  '^' +
                    nt +
                    '*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(' +
                    nt +
                    '*((?:-\\d)?\\d*)' +
                    nt +
                    '*\\)|)(?=[^-]|$)',
                  'i'
                )
              }
              var pt = /^(?:input|select|textarea|button)$/i
              var yt = /^h\d$/i
              var mt = /^[^{]+\{\s*\[native \w/
              var vt = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/
              var gt = /[+~]/
              var St = new RegExp(
                '\\\\([\\da-f]{1,6}' + nt + '?|(' + nt + ')|.)',
                'ig'
              )
              var bt = function (t, e, n) {
                var i = '0x' + e - 65536
                return i !== i || n
                  ? e
                  : i < 0
                    ? String.fromCharCode(i + 65536)
                    : String.fromCharCode(
                      (i >> 10) | 55296,
                      (1023 & i) | 56320
                    )
              }
              var wt = /([\0-\x1f\x7f]|^-?\d)|^-$|[^\0-\x1f\x7f-\uFFFF\w-]/g
              var _t = function (t, e) {
                return e
                  ? t === '\0'
                    ? '�'
                    : t.slice(0, -1) +
                      '\\' +
                      t.charCodeAt(t.length - 1).toString(16) +
                      ' '
                  : '\\' + t
              }
              var Pt = function () {
                z()
              }
              var Ct = p(
                function (t) {
                  return t.disabled === !0 && ('form' in t || 'label' in t)
                },
                { dir: 'parentNode', next: 'legend' }
              )
              try {
                Z.apply((J = Q.call(H.childNodes)), H.childNodes),
                J[H.childNodes.length].nodeType
              } catch (t) {
                Z = {
                  apply: J.length
                    ? function (t, e) {
                      K.apply(t, Q.call(e))
                    }
                    : function (t, e) {
                      for (var n = t.length, i = 0; (t[n++] = e[i++]););
                      t.length = n - 1
                    }
                }
              }
              (_ = e.support = {}),
              (x = e.isXML = function (t) {
                var e = t && (t.ownerDocument || t).documentElement
                return !!e && e.nodeName !== 'HTML'
              }),
              (z = e.setDocument = function (t) {
                var e
                var n
                var i = t ? t.ownerDocument || t : H
                return i !== I && i.nodeType === 9 && i.documentElement
                  ? ((I = i),
                  (A = I.documentElement),
                  (F = !x(I)),
                  H !== I &&
                      (n = I.defaultView) &&
                      n.top !== n &&
                      (n.addEventListener
                        ? n.addEventListener('unload', Pt, !1)
                        : n.attachEvent && n.attachEvent('onunload', Pt)),
                  (_.attributes = r(function (t) {
                    return (t.className = 'i'), !t.getAttribute('className')
                  })),
                  (_.getElementsByTagName = r(function (t) {
                    return (
                      t.appendChild(I.createComment('')),
                      !t.getElementsByTagName('*').length
                    )
                  })),
                  (_.getElementsByClassName = mt.test(
                    I.getElementsByClassName
                  )),
                  (_.getById = r(function (t) {
                    return (
                      (A.appendChild(t).id = q),
                      !I.getElementsByName || !I.getElementsByName(q).length
                    )
                  })),
                  _.getById
                    ? ((P.filter.ID = function (t) {
                      var e = t.replace(St, bt)
                      return function (t) {
                        return t.getAttribute('id') === e
                      }
                    }),
                    (P.find.ID = function (t, e) {
                      if (typeof e.getElementById !== 'undefined' && F) {
                        var n = e.getElementById(t)
                        return n ? [n] : []
                      }
                    }))
                    : ((P.filter.ID = function (t) {
                      var e = t.replace(St, bt)
                      return function (t) {
                        var n =
                              typeof t.getAttributeNode !== 'undefined' &&
                              t.getAttributeNode('id')
                        return n && n.value === e
                      }
                    }),
                    (P.find.ID = function (t, e) {
                      if (typeof e.getElementById !== 'undefined' && F) {
                        var n
                        var i
                        var r
                        var o = e.getElementById(t)
                        if (o) {
                          if (
                            ((n = o.getAttributeNode('id')),
                            n && n.value === t)
                          ) { return [o] }
                          for (
                            r = e.getElementsByName(t), i = 0;
                            (o = r[i++]);

                          ) {
                            if (
                              ((n = o.getAttributeNode('id')),
                              n && n.value === t)
                            ) { return [o] }
                          }
                        }
                        return []
                      }
                    })),
                  (P.find.TAG = _.getElementsByTagName
                    ? function (t, e) {
                      return typeof e.getElementsByTagName !== 'undefined'
                        ? e.getElementsByTagName(t)
                        : _.qsa
                          ? e.querySelectorAll(t)
                          : void 0
                    }
                    : function (t, e) {
                      var n
                      var i = []
                      var r = 0
                      var o = e.getElementsByTagName(t)
                      if (t === '*') {
                        for (; (n = o[r++]);) { n.nodeType === 1 && i.push(n) }
                        return i
                      }
                      return o
                    }),
                  (P.find.CLASS =
                      _.getElementsByClassName &&
                      function (t, e) {
                        if (typeof e.getElementsByClassName !== 'undefined' && F) { return e.getElementsByClassName(t) }
                      }),
                  (B = []),
                  (O = []),
                  (_.qsa = mt.test(I.querySelectorAll)) &&
                      (r(function (t) {
                        (A.appendChild(t).innerHTML =
                          "<a id='" +
                          q +
                          "'></a><select id='" +
                          q +
                          "-\r\\' msallowcapture=''><option selected=''></option></select>"),
                        t.querySelectorAll("[msallowcapture^='']").length &&
                            O.push('[*^$]=' + nt + '*(?:\'\'|"")'),
                        t.querySelectorAll('[selected]').length ||
                            O.push('\\[' + nt + '*(?:value|' + et + ')'),
                        t.querySelectorAll('[id~=' + q + '-]').length ||
                            O.push('~='),
                        t.querySelectorAll(':checked').length ||
                            O.push(':checked'),
                        t.querySelectorAll('a#' + q + '+*').length ||
                            O.push('.#.+[+~]')
                      }),
                      r(function (t) {
                        t.innerHTML =
                          "<a href='' disabled='disabled'></a><select disabled='disabled'><option/></select>"
                        var e = I.createElement('input')
                        e.setAttribute('type', 'hidden'),
                        t.appendChild(e).setAttribute('name', 'D'),
                        t.querySelectorAll('[name=d]').length &&
                            O.push('name' + nt + '*[*^$|!~]?='),
                        t.querySelectorAll(':enabled').length !== 2 &&
                            O.push(':enabled', ':disabled'),
                        (A.appendChild(t).disabled = !0),
                        t.querySelectorAll(':disabled').length !== 2 &&
                            O.push(':enabled', ':disabled'),
                        t.querySelectorAll('*,:x'),
                        O.push(',.*:')
                      })),
                  (_.matchesSelector = mt.test(
                    (L =
                        A.matches ||
                        A.webkitMatchesSelector ||
                        A.mozMatchesSelector ||
                        A.oMatchesSelector ||
                        A.msMatchesSelector)
                  )) &&
                      r(function (t) {
                        (_.disconnectedMatch = L.call(t, '*')),
                        L.call(t, "[s!='']:x"),
                        B.push('!=', ot)
                      }),
                  (O = O.length && new RegExp(O.join('|'))),
                  (B = B.length && new RegExp(B.join('|'))),
                  (e = mt.test(A.compareDocumentPosition)),
                  (N =
                      e || mt.test(A.contains)
                        ? function (t, e) {
                          var n = t.nodeType === 9 ? t.documentElement : t
                          var i = e && e.parentNode
                          return (
                            t === i ||
                              !(
                                !i ||
                                i.nodeType !== 1 ||
                                !(n.contains
                                  ? n.contains(i)
                                  : t.compareDocumentPosition &&
                                    16 & t.compareDocumentPosition(i))
                              )
                          )
                        }
                        : function (t, e) {
                          if (e) {
                            for (; (e = e.parentNode);) { if (e === t) return !0 }
                          }
                          return !1
                        }),
                  (Y = e
                    ? function (t, e) {
                      if (t === e) return (E = !0), 0
                      var n =
                            !t.compareDocumentPosition -
                            !e.compareDocumentPosition
                      return n || ((n =
                                (t.ownerDocument || t) ===
                                (e.ownerDocument || e)
                                  ? t.compareDocumentPosition(e)
                                  : 1),
                      1 & n ||
                              (!_.sortDetached &&
                                e.compareDocumentPosition(t) === n)
                        ? t === I || (t.ownerDocument === H && N(H, t))
                          ? -1
                          : e === I ||
                                    (e.ownerDocument === H && N(H, e))
                            ? 1
                            : R
                              ? tt(R, t) - tt(R, e)
                              : 0
                        : 4 & n
                          ? -1
                          : 1)
                    }
                    : function (t, e) {
                      if (t === e) return (E = !0), 0
                      var n
                      var i = 0
                      var r = t.parentNode
                      var o = e.parentNode
                      var s = [t]
                      var u = [e]
                      if (!r || !o) {
                        return t === I
                          ? -1
                          : e === I
                            ? 1
                            : r
                              ? -1
                              : o
                                ? 1
                                : R
                                  ? tt(R, t) - tt(R, e)
                                  : 0
                      }
                      if (r === o) return a(t, e)
                      for (n = t; (n = n.parentNode);) s.unshift(n)
                      for (n = e; (n = n.parentNode);) u.unshift(n)
                      for (; s[i] === u[i];) i++
                      return i
                        ? a(s[i], u[i])
                        : s[i] === H
                          ? -1
                          : u[i] === H
                            ? 1
                            : 0
                    }),
                  I)
                  : I
              }),
              (e.matches = function (t, n) {
                return e(t, null, null, n)
              }),
              (e.matchesSelector = function (t, n) {
                if (
                  ((t.ownerDocument || t) !== I && z(t),
                  (n = n.replace(ft, "='$1']")),
                  _.matchesSelector &&
                    F &&
                    !V[n + ' '] &&
                    (!B || !B.test(n)) &&
                    (!O || !O.test(n)))
                ) {
                  try {
                    var i = L.call(t, n)
                    if (
                      i ||
                      _.disconnectedMatch ||
                      (t.document && t.document.nodeType !== 11)
                    ) { return i }
                  } catch (t) {}
                }
                return e(n, I, null, [t]).length > 0
              }),
              (e.contains = function (t, e) {
                return (t.ownerDocument || t) !== I && z(t), N(t, e)
              }),
              (e.attr = function (t, e) {
                (t.ownerDocument || t) !== I && z(t)
                var n = P.attrHandle[e.toLowerCase()]
                var i =
                    n && X.call(P.attrHandle, e.toLowerCase())
                      ? n(t, e, !F)
                      : void 0
                return void 0 !== i
                  ? i
                  : _.attributes || !F
                    ? t.getAttribute(e)
                    : (i = t.getAttributeNode(e)) && i.specified
                      ? i.value
                      : null
              }),
              (e.escape = function (t) {
                return (t + '').replace(wt, _t)
              }),
              (e.error = function (t) {
                throw new Error('Syntax error, unrecognized expression: ' + t)
              }),
              (e.uniqueSort = function (t) {
                var e
                var n = []
                var i = 0
                var r = 0
                if (
                  ((E = !_.detectDuplicates),
                  (R = !_.sortStable && t.slice(0)),
                  t.sort(Y),
                  E)
                ) {
                  for (; (e = t[r++]);) e === t[r] && (i = n.push(r))
                  for (; i--;) t.splice(n[i], 1)
                }
                return (R = null), t
              }),
              (C = e.getText = function (t) {
                var e
                var n = ''
                var i = 0
                var r = t.nodeType
                if (r) {
                  if (r === 1 || r === 9 || r === 11) {
                    if (typeof t.textContent === 'string') return t.textContent
                    for (t = t.firstChild; t; t = t.nextSibling) n += C(t)
                  } else if (r === 3 || r === 4) return t.nodeValue
                } else for (; (e = t[i++]);) n += C(e)
                return n
              }),
              (P = e.selectors = {
                cacheLength: 50,
                createPseudo: i,
                match: dt,
                attrHandle: {},
                find: {},
                relative: {
                  '>': { dir: 'parentNode', first: !0 },
                  ' ': { dir: 'parentNode' },
                  '+': { dir: 'previousSibling', first: !0 },
                  '~': { dir: 'previousSibling' }
                },
                preFilter: {
                  ATTR: function (t) {
                    return (
                      (t[1] = t[1].replace(St, bt)),
                      (t[3] = (t[3] || t[4] || t[5] || '').replace(St, bt)),
                      t[2] === '~=' && (t[3] = ' ' + t[3] + ' '),
                      t.slice(0, 4)
                    )
                  },
                  CHILD: function (t) {
                    return (
                      (t[1] = t[1].toLowerCase()),
                      t[1].slice(0, 3) === 'nth'
                        ? (t[3] || e.error(t[0]),
                        (t[4] = +(t[4]
                          ? t[5] + (t[6] || 1)
                          : 2 * (t[3] === 'even' || t[3] === 'odd'))),
                        (t[5] = +(t[7] + t[8] || t[3] === 'odd')))
                        : t[3] && e.error(t[0]),
                      t
                    )
                  },
                  PSEUDO: function (t) {
                    var e
                    var n = !t[6] && t[2]
                    return dt.CHILD.test(t[0])
                      ? null
                      : (t[3]
                        ? (t[2] = t[4] || t[5] || '')
                        : n &&
                            ct.test(n) &&
                            (e = T(n, !0)) &&
                            (e = n.indexOf(')', n.length - e) - n.length) &&
                            ((t[0] = t[0].slice(0, e)), (t[2] = n.slice(0, e))),
                      t.slice(0, 3))
                  }
                },
                filter: {
                  TAG: function (t) {
                    var e = t.replace(St, bt).toLowerCase()
                    return t === '*'
                      ? function () {
                        return !0
                      }
                      : function (t) {
                        return t.nodeName && t.nodeName.toLowerCase() === e
                      }
                  },
                  CLASS: function (t) {
                    var e = U[t + ' ']
                    return (
                      e ||
                      ((e = new RegExp(
                        '(^|' + nt + ')' + t + '(' + nt + '|$)'
                      )) &&
                        U(t, function (t) {
                          return e.test(
                            (typeof t.className === 'string' && t.className) ||
                              (typeof t.getAttribute !== 'undefined' &&
                                t.getAttribute('class')) ||
                              ''
                          )
                        }))
                    )
                  },
                  ATTR: function (t, n, i) {
                    return function (r) {
                      var o = e.attr(r, t)
                      return o == null
                        ? n === '!='
                        : !n ||
                            ((o += ''),
                            n === '='
                              ? o === i
                              : n === '!='
                                ? o !== i
                                : n === '^='
                                  ? i && o.indexOf(i) === 0
                                  : n === '*='
                                    ? i && o.indexOf(i) > -1
                                    : n === '$='
                                      ? i && o.slice(-i.length) === i
                                      : n === '~='
                                        ? (
                                          ' ' +
                                            o.replace(at, ' ') +
                                            ' '
                                        ).indexOf(i) > -1
                                        : n === '|=' &&
                                          (o === i ||
                                            o.slice(0, i.length + 1) ===
                                              i + '-'))
                    }
                  },
                  CHILD: function (t, e, n, i, r) {
                    var o = t.slice(0, 3) !== 'nth'
                    var a = t.slice(-4) !== 'last'
                    var s = e === 'of-type'
                    return i === 1 && r === 0
                      ? function (t) {
                        return !!t.parentNode
                      }
                      : function (e, n, u) {
                        var l
                        var f
                        var c
                        var h
                        var d
                        var p
                        var y = o !== a ? 'nextSibling' : 'previousSibling'
                        var m = e.parentNode
                        var v = s && e.nodeName.toLowerCase()
                        var g = !u && !s
                        var S = !1
                        if (m) {
                          if (o) {
                            for (; y;) {
                              for (h = e; (h = h[y]);) {
                                if (
                                  s
                                    ? h.nodeName.toLowerCase() === v
                                    : h.nodeType === 1
                                ) { return !1 }
                              }
                              p = y = t === 'only' && !p && 'nextSibling'
                            }
                            return !0
                          }
                          if (
                            ((p = [a ? m.firstChild : m.lastChild]), a && g)
                          ) {
                            for (
                              h = m,
                              c = h[q] || (h[q] = {}),
                              f = c[h.uniqueID] || (c[h.uniqueID] = {}),
                              l = f[t] || [],
                              d = l[0] === j && l[1],
                              S = d && l[2],
                              h = d && m.childNodes[d];
                              (h =
                                  (++d && h && h[y]) || (S = d = 0) || p.pop());

                            ) {
                              if (h.nodeType === 1 && ++S && h === e) {
                                f[t] = [j, d, S]
                                break
                              }
                            }
                          } else if (
                            (g &&
                                ((h = e),
                                (c = h[q] || (h[q] = {})),
                                (f = c[h.uniqueID] || (c[h.uniqueID] = {})),
                                (l = f[t] || []),
                                (d = l[0] === j && l[1]),
                                (S = d)),
                            S === !1)
                          ) {
                            for (
                              ;
                              (h =
                                  (++d && h && h[y]) ||
                                  (S = d = 0) ||
                                  p.pop()) &&
                                ((s
                                  ? h.nodeName.toLowerCase() !== v
                                  : h.nodeType !== 1) ||
                                  !++S ||
                                  (g &&
                                    ((c = h[q] || (h[q] = {})),
                                    (f = c[h.uniqueID] || (c[h.uniqueID] = {})),
                                    (f[t] = [j, S])),
                                  h !== e));

                            );
                          }
                          return (
                            (S -= r), S === i || (S % i === 0 && S / i >= 0)
                          )
                        }
                      }
                  },
                  PSEUDO: function (t, n) {
                    var r
                    var o =
                        P.pseudos[t] ||
                        P.setFilters[t.toLowerCase()] ||
                        e.error('unsupported pseudo: ' + t)
                    return o[q]
                      ? o(n)
                      : o.length > 1
                        ? ((r = [t, t, '', n]),
                        P.setFilters.hasOwnProperty(t.toLowerCase())
                          ? i(function (t, e) {
                            for (var i, r = o(t, n), a = r.length; a--;) { (i = tt(t, r[a])), (t[i] = !(e[i] = r[a])) }
                          })
                          : function (t) {
                            return o(t, 0, r)
                          })
                        : o
                  }
                },
                pseudos: {
                  not: i(function (t) {
                    var e = []
                    var n = []
                    var r = k(t.replace(st, '$1'))
                    return r[q]
                      ? i(function (t, e, n, i) {
                        for (
                          var o, a = r(t, null, i, []), s = t.length;
                          s--;

                        ) { (o = a[s]) && (t[s] = !(e[s] = o)) }
                      })
                      : function (t, i, o) {
                        return (
                          (e[0] = t),
                          r(e, null, o, n),
                          (e[0] = null),
                          !n.pop()
                        )
                      }
                  }),
                  has: i(function (t) {
                    return function (n) {
                      return e(t, n).length > 0
                    }
                  }),
                  contains: i(function (t) {
                    return (
                      (t = t.replace(St, bt)),
                      function (e) {
                        return (
                          (e.textContent || e.innerText || C(e)).indexOf(t) > -1
                        )
                      }
                    )
                  }),
                  lang: i(function (t) {
                    return (
                      ht.test(t || '') || e.error('unsupported lang: ' + t),
                      (t = t.replace(St, bt).toLowerCase()),
                      function (e) {
                        var n
                        do {
                          if (
                            (n = F
                              ? e.lang
                              : e.getAttribute('xml:lang') ||
                                e.getAttribute('lang'))
                          ) {
                            return (
                              (n = n.toLowerCase()),
                              n === t || n.indexOf(t + '-') === 0
                            )
                          }
                        }
                        while ((e = e.parentNode) && e.nodeType === 1)
                        return !1
                      }
                    )
                  }),
                  target: function (e) {
                    var n = t.location && t.location.hash
                    return n && n.slice(1) === e.id
                  },
                  root: function (t) {
                    return t === A
                  },
                  focus: function (t) {
                    return (
                      t === I.activeElement &&
                      (!I.hasFocus || I.hasFocus()) &&
                      !!(t.type || t.href || ~t.tabIndex)
                    )
                  },
                  enabled: l(!1),
                  disabled: l(!0),
                  checked: function (t) {
                    var e = t.nodeName.toLowerCase()
                    return (
                      (e === 'input' && !!t.checked) ||
                      (e === 'option' && !!t.selected)
                    )
                  },
                  selected: function (t) {
                    return (
                      t.parentNode && t.parentNode.selectedIndex,
                      t.selected === !0
                    )
                  },
                  empty: function (t) {
                    for (t = t.firstChild; t; t = t.nextSibling) { if (t.nodeType < 6) return !1 }
                    return !0
                  },
                  parent: function (t) {
                    return !P.pseudos.empty(t)
                  },
                  header: function (t) {
                    return yt.test(t.nodeName)
                  },
                  input: function (t) {
                    return pt.test(t.nodeName)
                  },
                  button: function (t) {
                    var e = t.nodeName.toLowerCase()
                    return (
                      (e === 'input' && t.type === 'button') || e === 'button'
                    )
                  },
                  text: function (t) {
                    var e
                    return (
                      t.nodeName.toLowerCase() === 'input' &&
                      t.type === 'text' &&
                      ((e = t.getAttribute('type')) == null ||
                        e.toLowerCase() === 'text')
                    )
                  },
                  first: f(function () {
                    return [0]
                  }),
                  last: f(function (t, e) {
                    return [e - 1]
                  }),
                  eq: f(function (t, e, n) {
                    return [n < 0 ? n + e : n]
                  }),
                  even: f(function (t, e) {
                    for (var n = 0; n < e; n += 2) t.push(n)
                    return t
                  }),
                  odd: f(function (t, e) {
                    for (var n = 1; n < e; n += 2) t.push(n)
                    return t
                  }),
                  lt: f(function (t, e, n) {
                    for (var i = n < 0 ? n + e : n; --i >= 0;) t.push(i)
                    return t
                  }),
                  gt: f(function (t, e, n) {
                    for (var i = n < 0 ? n + e : n; ++i < e;) t.push(i)
                    return t
                  })
                }
              }),
              (P.pseudos.nth = P.pseudos.eq)
              for (w in {
                radio: !0,
                checkbox: !0,
                file: !0,
                password: !0,
                image: !0
              }) { P.pseudos[w] = s(w) }
              for (w in { submit: !0, reset: !0 }) P.pseudos[w] = u(w)
              return (
                (h.prototype = P.filters = P.pseudos),
                (P.setFilters = new h()),
                (T = e.tokenize = function (t, n) {
                  var i
                  var r
                  var o
                  var a
                  var s
                  var u
                  var l
                  var f = G[t + ' ']
                  if (f) return n ? 0 : f.slice(0)
                  for (s = t, u = [], l = P.preFilter; s;) {
                    (i && !(r = ut.exec(s))) ||
                    (r && (s = s.slice(r[0].length) || s), u.push((o = []))),
                    (i = !1),
                    (r = lt.exec(s)) &&
                      ((i = r.shift()),
                      o.push({ value: i, type: r[0].replace(st, ' ') }),
                      (s = s.slice(i.length)))
                    for (a in P.filter) {
                      !(r = dt[a].exec(s)) ||
                      (l[a] && !(r = l[a](r))) ||
                      ((i = r.shift()),
                      o.push({ value: i, type: a, matches: r }),
                      (s = s.slice(i.length)))
                    }
                    if (!i) break
                  }
                  return n ? s.length : s ? e.error(t) : G(t, u).slice(0)
                }),
                (k = e.compile = function (t, e) {
                  var n
                  var i = []
                  var r = []
                  var o = V[t + ' ']
                  if (!o) {
                    for (e || (e = T(t)), n = e.length; n--;) { (o = S(e[n])), o[q] ? i.push(o) : r.push(o) }
                    (o = V(t, b(r, i))), (o.selector = t)
                  }
                  return o
                }),
                (D = e.select = function (t, e, n, i) {
                  var r
                  var o
                  var a
                  var s
                  var u
                  var l = typeof t === 'function' && t
                  var f = !i && T((t = l.selector || t))
                  if (((n = n || []), f.length === 1)) {
                    if (
                      ((o = f[0] = f[0].slice(0)),
                      o.length > 2 &&
                      (a = o[0]).type === 'ID' &&
                      e.nodeType === 9 &&
                      F &&
                      P.relative[o[1].type])
                    ) {
                      if (
                        ((e = (P.find.ID(a.matches[0].replace(St, bt), e) ||
                        [])[0]),
                        !e)
                      ) { return n }
                      l && (e = e.parentNode),
                      (t = t.slice(o.shift().value.length))
                    }
                    for (
                      r = dt.needsContext.test(t) ? 0 : o.length;
                      r-- && ((a = o[r]), !P.relative[(s = a.type)]);

                    ) {
                      if (
                        (u = P.find[s]) &&
                      (i = u(
                        a.matches[0].replace(St, bt),
                        (gt.test(o[0].type) && c(e.parentNode)) || e
                      ))
                      ) {
                        if ((o.splice(r, 1), (t = i.length && d(o)), !t)) { return Z.apply(n, i), n }
                        break
                      }
                    }
                  }
                  return (
                    (l || k(t, f))(
                      i,
                      e,
                      !F,
                      n,
                      !e || (gt.test(t) && c(e.parentNode)) || e
                    ),
                    n
                  )
                }),
                (_.sortStable =
                q
                  .split('')
                  .sort(Y)
                  .join('') === q),
                (_.detectDuplicates = !!E),
                z(),
                (_.sortDetached = r(function (t) {
                  return (
                    1 & t.compareDocumentPosition(I.createElement('fieldset'))
                  )
                })),
                r(function (t) {
                  return (
                    (t.innerHTML = "<a href='#'></a>"),
                    t.firstChild.getAttribute('href') === '#'
                  )
                }) ||
                o('type|href|height|width', function (t, e, n) {
                  if (!n) {
                    return t.getAttribute(
                      e,
                      e.toLowerCase() === 'type' ? 1 : 2
                    )
                  }
                }),
                (_.attributes &&
                r(function (t) {
                  return (
                    (t.innerHTML = '<input/>'),
                    t.firstChild.setAttribute('value', ''),
                    t.firstChild.getAttribute('value') === ''
                  )
                })) ||
                o('value', function (t, e, n) {
                  if (!n && t.nodeName.toLowerCase() === 'input') { return t.defaultValue }
                }),
                r(function (t) {
                  return t.getAttribute('disabled') == null
                }) ||
                o(et, function (t, e, n) {
                  var i
                  if (!n) {
                    return t[e] === !0
                      ? e.toLowerCase()
                      : (i = t.getAttributeNode(e)) && i.specified
                        ? i.value
                        : null
                  }
                }),
                e
              )
            })(o);
          (bt.find = xt),
          (bt.expr = xt.selectors),
          (bt.expr[':'] = bt.expr.pseudos),
          (bt.uniqueSort = bt.unique = xt.uniqueSort),
          (bt.text = xt.getText),
          (bt.isXMLDoc = xt.isXML),
          (bt.contains = xt.contains),
          (bt.escapeSelector = xt.escape)
          var Tt = function (t, e, n) {
            for (
              var i = [], r = void 0 !== n;
              (t = t[e]) && t.nodeType !== 9;

            ) {
              if (t.nodeType === 1) {
                if (r && bt(t).is(n)) break
                i.push(t)
              }
            }
            return i
          }
          var kt = function (t, e) {
            for (var n = []; t; t = t.nextSibling) { t.nodeType === 1 && t !== e && n.push(t) }
            return n
          }
          var Dt = bt.expr.match.needsContext
          var Mt = /^<([a-z][^\/\0>:\x20\t\r\n\f]*)[\x20\t\r\n\f]*\/?>(?:<\/\1>|)$/i
          var Rt = /^.[^:#\[\.,]*$/;
          (bt.filter = function (t, e, n) {
            var i = e[0]
            return (
              n && (t = ':not(' + t + ')'),
              e.length === 1 && i.nodeType === 1
                ? bt.find.matchesSelector(i, t)
                  ? [i]
                  : []
                : bt.find.matches(
                  t,
                  bt.grep(e, function (t) {
                    return t.nodeType === 1
                  })
                )
            )
          }),
          bt.fn.extend({
            find: function (t) {
              var e
              var n
              var i = this.length
              var r = this
              if (typeof t !== 'string') {
                return this.pushStack(
                  bt(t).filter(function () {
                    for (e = 0; e < i; e++) { if (bt.contains(r[e], this)) return !0 }
                  })
                )
              }
              for (n = this.pushStack([]), e = 0; e < i; e++) { bt.find(t, r[e], n) }
              return i > 1 ? bt.uniqueSort(n) : n
            },
            filter: function (t) {
              return this.pushStack(f(this, t || [], !1))
            },
            not: function (t) {
              return this.pushStack(f(this, t || [], !0))
            },
            is: function (t) {
              return !!f(
                this,
                typeof t === 'string' && Dt.test(t) ? bt(t) : t || [],
                !1
              ).length
            }
          })
          var Et
          var zt = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/
          var It = (bt.fn.init = function (t, e, n) {
            var i, r
            if (!t) return this
            if (((n = n || Et), typeof t === 'string')) {
              if (
                ((i =
                    t[0] === '<' && t[t.length - 1] === '>' && t.length >= 3
                      ? [null, t, null]
                      : zt.exec(t)),
                !i || (!i[1] && e))
              ) {
                return !e || e.jquery
                  ? (e || n).find(t)
                  : this.constructor(e).find(t)
              }
              if (i[1]) {
                if (
                  ((e = e instanceof bt ? e[0] : e),
                  bt.merge(
                    this,
                    bt.parseHTML(
                      i[1],
                      e && e.nodeType ? e.ownerDocument || e : st,
                      !0
                    )
                  ),
                  Mt.test(i[1]) && bt.isPlainObject(e))
                ) {
                  for (i in e) {
                    bt.isFunction(this[i])
                      ? this[i](e[i])
                      : this.attr(i, e[i])
                  }
                }
                return this
              }
              return (
                (r = st.getElementById(i[2])),
                r && ((this[0] = r), (this.length = 1)),
                this
              )
            }
            return t.nodeType
              ? ((this[0] = t), (this.length = 1), this)
              : bt.isFunction(t)
                ? void 0 !== n.ready
                  ? n.ready(t)
                  : t(bt)
                : bt.makeArray(t, this)
          });
          (It.prototype = bt.fn), (Et = bt(st))
          var At = /^(?:parents|prev(?:Until|All))/
          var Ft = { children: !0, contents: !0, next: !0, prev: !0 }
          bt.fn.extend({
            has: function (t) {
              var e = bt(t, this)
              var n = e.length
              return this.filter(function () {
                for (var t = 0; t < n; t++) { if (bt.contains(this, e[t])) return !0 }
              })
            },
            closest: function (t, e) {
              var n
              var i = 0
              var r = this.length
              var o = []
              var a = typeof t !== 'string' && bt(t)
              if (!Dt.test(t)) {
                for (; i < r; i++) {
                  for (n = this[i]; n && n !== e; n = n.parentNode) {
                    if (
                      n.nodeType < 11 &&
                      (a
                        ? a.index(n) > -1
                        : n.nodeType === 1 && bt.find.matchesSelector(n, t))
                    ) {
                      o.push(n)
                      break
                    }
                  }
                }
              }
              return this.pushStack(o.length > 1 ? bt.uniqueSort(o) : o)
            },
            index: function (t) {
              return t
                ? typeof t === 'string'
                  ? ht.call(bt(t), this[0])
                  : ht.call(this, t.jquery ? t[0] : t)
                : this[0] && this[0].parentNode
                  ? this.first().prevAll().length
                  : -1
            },
            add: function (t, e) {
              return this.pushStack(
                bt.uniqueSort(bt.merge(this.get(), bt(t, e)))
              )
            },
            addBack: function (t) {
              return this.add(
                t == null ? this.prevObject : this.prevObject.filter(t)
              )
            }
          }),
          bt.each(
            {
              parent: function t (e) {
                var t = e.parentNode
                return t && t.nodeType !== 11 ? t : null
              },
              parents: function (t) {
                return Tt(t, 'parentNode')
              },
              parentsUntil: function (t, e, n) {
                return Tt(t, 'parentNode', n)
              },
              next: function (t) {
                return c(t, 'nextSibling')
              },
              prev: function (t) {
                return c(t, 'previousSibling')
              },
              nextAll: function (t) {
                return Tt(t, 'nextSibling')
              },
              prevAll: function (t) {
                return Tt(t, 'previousSibling')
              },
              nextUntil: function (t, e, n) {
                return Tt(t, 'nextSibling', n)
              },
              prevUntil: function (t, e, n) {
                return Tt(t, 'previousSibling', n)
              },
              siblings: function (t) {
                return kt((t.parentNode || {}).firstChild, t)
              },
              children: function (t) {
                return kt(t.firstChild)
              },
              contents: function (t) {
                return l(t, 'iframe')
                  ? t.contentDocument
                  : (l(t, 'template') && (t = t.content || t),
                  bt.merge([], t.childNodes))
              }
            },
            function (t, e) {
              bt.fn[t] = function (n, i) {
                var r = bt.map(this, e, n)
                return (
                  t.slice(-5) !== 'Until' && (i = n),
                  i && typeof i === 'string' && (r = bt.filter(i, r)),
                  this.length > 1 &&
                      (Ft[t] || bt.uniqueSort(r), At.test(t) && r.reverse()),
                  this.pushStack(r)
                )
              }
            }
          )
          var Ot = /[^\x20\t\r\n\f]+/g;
          (bt.Callbacks = function (t) {
            t = typeof t === 'string' ? h(t) : bt.extend({}, t)
            var e
            var n
            var i
            var r
            var o = []
            var a = []
            var s = -1
            var u = function () {
              for (r = r || t.once, i = e = !0; a.length; s = -1) {
                for (n = a.shift(); ++s < o.length;) {
                  o[s].apply(n[0], n[1]) === !1 &&
                      t.stopOnFalse &&
                      ((s = o.length), (n = !1))
                }
              }
              t.memory || (n = !1), (e = !1), r && (o = n ? [] : '')
            }
            var l = {
              add: function () {
                return (
                  o &&
                      (n && !e && ((s = o.length - 1), a.push(n)),
                      (function e (n) {
                        bt.each(n, function (n, i) {
                          bt.isFunction(i)
                            ? (t.unique && l.has(i)) || o.push(i)
                            : i && i.length && bt.type(i) !== 'string' && e(i)
                        })
                      })(arguments),
                      n && !e && u()),
                  this
                )
              },
              remove: function () {
                return (
                  bt.each(arguments, function (t, e) {
                    for (var n; (n = bt.inArray(e, o, n)) > -1;) { o.splice(n, 1), n <= s && s-- }
                  }),
                  this
                )
              },
              has: function (t) {
                return t ? bt.inArray(t, o) > -1 : o.length > 0
              },
              empty: function () {
                return o && (o = []), this
              },
              disable: function () {
                return (r = a = []), (o = n = ''), this
              },
              disabled: function () {
                return !o
              },
              lock: function () {
                return (r = a = []), n || e || (o = n = ''), this
              },
              locked: function () {
                return !!r
              },
              fireWith: function (t, n) {
                return (
                  r ||
                      ((n = n || []),
                      (n = [t, n.slice ? n.slice() : n]),
                      a.push(n),
                      e || u()),
                  this
                )
              },
              fire: function () {
                return l.fireWith(this, arguments), this
              },
              fired: function () {
                return !!i
              }
            }
            return l
          }),
          bt.extend({
            Deferred: function (t) {
              var e = [
                [
                  'notify',
                  'progress',
                  bt.Callbacks('memory'),
                  bt.Callbacks('memory'),
                  2
                ],
                [
                  'resolve',
                  'done',
                  bt.Callbacks('once memory'),
                  bt.Callbacks('once memory'),
                  0,
                  'resolved'
                ],
                [
                  'reject',
                  'fail',
                  bt.Callbacks('once memory'),
                  bt.Callbacks('once memory'),
                  1,
                  'rejected'
                ]
              ]
              var i = 'pending'
              var r = {
                state: function () {
                  return i
                },
                always: function () {
                  return a.done(arguments).fail(arguments), this
                },
                catch: function (t) {
                  return r.then(null, t)
                },
                pipe: function () {
                  var t = arguments
                  return bt
                    .Deferred(function (n) {
                      bt.each(e, function (e, i) {
                        var r = bt.isFunction(t[i[4]]) && t[i[4]]
                        a[i[1]](function () {
                          var t = r && r.apply(this, arguments)
                          t && bt.isFunction(t.promise)
                            ? t
                              .promise()
                              .progress(n.notify)
                              .done(n.resolve)
                              .fail(n.reject)
                            : n[i[0] + 'With'](this, r ? [t] : arguments)
                        })
                      }),
                      (t = null)
                    })
                    .promise()
                },
                then: function (t, i, r) {
                  function a (t, e, i, r) {
                    return function () {
                      var u = this
                      var l = arguments
                      var f = function () {
                        var o, f
                        if (!(t < s)) {
                          if (((o = i.apply(u, l)), o === e.promise())) {
                            throw new TypeError(
                              'Thenable self-resolution'
                            )
                          }
                          (f =
                                  o &&
                                  ((typeof o === 'undefined'
                                    ? 'undefined'
                                    : n(o)) ===
                                    'object' ||
                                    typeof o === 'function') &&
                                  o.then),
                          bt.isFunction(f)
                            ? r
                              ? f.call(o, a(s, e, d, r), a(s, e, p, r))
                              : (s++,
                              f.call(
                                o,
                                a(s, e, d, r),
                                a(s, e, p, r),
                                a(s, e, d, e.notifyWith)
                              ))
                            : (i !== d && ((u = void 0), (l = [o])),
                            (r || e.resolveWith)(u, l))
                        }
                      }
                      var c = r
                        ? f
                        : function () {
                          try {
                            f()
                          } catch (n) {
                            bt.Deferred.exceptionHook &&
                                      bt.Deferred.exceptionHook(
                                        n,
                                        c.stackTrace
                                      ),
                            t + 1 >= s &&
                                        (i !== p && ((u = void 0), (l = [n])),
                                        e.rejectWith(u, l))
                          }
                        }
                      t
                        ? c()
                        : (bt.Deferred.getStackHook &&
                                (c.stackTrace = bt.Deferred.getStackHook()),
                        o.setTimeout(c))
                    }
                  }
                  var s = 0
                  return bt
                    .Deferred(function (n) {
                      e[0][3].add(
                        a(0, n, bt.isFunction(r) ? r : d, n.notifyWith)
                      ),
                      e[1][3].add(a(0, n, bt.isFunction(t) ? t : d)),
                      e[2][3].add(a(0, n, bt.isFunction(i) ? i : p))
                    })
                    .promise()
                },
                promise: function (t) {
                  return t != null ? bt.extend(t, r) : r
                }
              }
              var a = {}
              return (
                bt.each(e, function (t, n) {
                  var o = n[2]
                  var s = n[5];
                  (r[n[1]] = o.add),
                  s &&
                        o.add(
                          function () {
                            i = s
                          },
                          e[3 - t][2].disable,
                          e[0][2].lock
                        ),
                  o.add(n[3].fire),
                  (a[n[0]] = function () {
                    return (
                      a[n[0] + 'With'](
                        this === a ? void 0 : this,
                        arguments
                      ),
                      this
                    )
                  }),
                  (a[n[0] + 'With'] = o.fireWith)
                }),
                r.promise(a),
                t && t.call(a, a),
                a
              )
            },
            when: function (t) {
              var e = arguments.length
              var n = e
              var i = Array(n)
              var r = lt.call(arguments)
              var o = bt.Deferred()
              var a = function (t) {
                return function (n) {
                  (i[t] = this),
                  (r[t] = arguments.length > 1 ? lt.call(arguments) : n),
                  --e || o.resolveWith(i, r)
                }
              }
              if (
                e <= 1 &&
                  (y(t, o.done(a(n)).resolve, o.reject, !e),
                  o.state() === 'pending' || bt.isFunction(r[n] && r[n].then))
              ) { return o.then() }
              for (; n--;) y(r[n], a(n), o.reject)
              return o.promise()
            }
          })
          var Bt = /^(Eval|Internal|Range|Reference|Syntax|Type|URI)Error$/;
          (bt.Deferred.exceptionHook = function (t, e) {
            o.console &&
              o.console.warn &&
              t &&
              Bt.test(t.name) &&
              o.console.warn(
                'jQuery.Deferred exception: ' + t.message,
                t.stack,
                e
              )
          }),
          (bt.readyException = function (t) {
            o.setTimeout(function () {
              throw t
            })
          })
          var Lt = bt.Deferred();
          (bt.fn.ready = function (t) {
            return (
              Lt.then(t).catch(function (t) {
                bt.readyException(t)
              }),
              this
            )
          }),
          bt.extend({
            isReady: !1,
            readyWait: 1,
            ready: function (t) {
              (t === !0 ? --bt.readyWait : bt.isReady) ||
                  ((bt.isReady = !0),
                  (t !== !0 && --bt.readyWait > 0) || Lt.resolveWith(st, [bt]))
            }
          }),
          (bt.ready.then = Lt.then),
          st.readyState === 'complete' ||
            (st.readyState !== 'loading' && !st.documentElement.doScroll)
            ? o.setTimeout(bt.ready)
            : (st.addEventListener('DOMContentLoaded', m),
            o.addEventListener('load', m))
          var Nt = function t (e, n, i, r, o, a, s) {
            var u = 0
            var l = e.length
            var f = i == null
            if (bt.type(i) === 'object') {
              o = !0
              for (u in i) t(e, n, u, i[u], !0, a, s)
            } else if (
              void 0 !== r &&
                ((o = !0),
                bt.isFunction(r) || (s = !0),
                f &&
                  (s
                    ? (n.call(e, r), (n = null))
                    : ((f = n),
                    (n = function (t, e, n) {
                      return f.call(bt(t), n)
                    }))),
                n)
            ) {
              for (; u < l; u++) { n(e[u], i, s ? r : r.call(e[u], u, n(e[u], i))) }
            }
            return o ? e : f ? n.call(e) : l ? n(e[0], i) : a
          }
          var qt = function (t) {
            return t.nodeType === 1 || t.nodeType === 9 || !+t.nodeType
          };
          (v.uid = 1),
          (v.prototype = {
            cache: function (t) {
              var e = t[this.expando]
              return (
                e ||
                    ((e = {}),
                    qt(t) &&
                      (t.nodeType
                        ? (t[this.expando] = e)
                        : Object.defineProperty(t, this.expando, {
                          value: e,
                          configurable: !0
                        }))),
                e
              )
            },
            set: function (t, e, n) {
              var i
              var r = this.cache(t)
              if (typeof e === 'string') r[bt.camelCase(e)] = n
              else for (i in e) r[bt.camelCase(i)] = e[i]
              return r
            },
            get: function (t, e) {
              return void 0 === e
                ? this.cache(t)
                : t[this.expando] && t[this.expando][bt.camelCase(e)]
            },
            access: function (t, e, n) {
              return void 0 === e ||
                  (e && typeof e === 'string' && void 0 === n)
                ? this.get(t, e)
                : (this.set(t, e, n), void 0 !== n ? n : e)
            },
            remove: function (t, e) {
              var n
              var i = t[this.expando]
              if (void 0 !== i) {
                if (void 0 !== e) {
                  Array.isArray(e)
                    ? (e = e.map(bt.camelCase))
                    : ((e = bt.camelCase(e)),
                    (e = e in i ? [e] : e.match(Ot) || [])),
                  (n = e.length)
                  for (; n--;) delete i[e[n]]
                }
                (void 0 === e || bt.isEmptyObject(i)) &&
                    (t.nodeType
                      ? (t[this.expando] = void 0)
                      : delete t[this.expando])
              }
            },
            hasData: function (t) {
              var e = t[this.expando]
              return void 0 !== e && !bt.isEmptyObject(e)
            }
          })
          var Ht = new v()
          var jt = new v()
          var Wt = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/
          var Ut = /[A-Z]/g
          bt.extend({
            hasData: function (t) {
              return jt.hasData(t) || Ht.hasData(t)
            },
            data: function (t, e, n) {
              return jt.access(t, e, n)
            },
            removeData: function (t, e) {
              jt.remove(t, e)
            },
            _data: function (t, e, n) {
              return Ht.access(t, e, n)
            },
            _removeData: function (t, e) {
              Ht.remove(t, e)
            }
          }),
          bt.fn.extend({
            data: function t (e, i) {
              var r
              var o
              var t
              var a = this[0]
              var s = a && a.attributes
              if (void 0 === e) {
                if (
                  this.length &&
                    ((t = jt.get(a)),
                    a.nodeType === 1 && !Ht.get(a, 'hasDataAttrs'))
                ) {
                  for (r = s.length; r--;) {
                    s[r] &&
                        ((o = s[r].name),
                        o.indexOf('data-') === 0 &&
                          ((o = bt.camelCase(o.slice(5))), S(a, o, t[o])))
                  }
                  Ht.set(a, 'hasDataAttrs', !0)
                }
                return t
              }
              return (typeof e === 'undefined' ? 'undefined' : n(e)) ===
                  'object'
                ? this.each(function () {
                  jt.set(this, e)
                })
                : Nt(
                  this,
                  function (t) {
                    var n
                    if (a && void 0 === t) {
                      if (((n = jt.get(a, e)), void 0 !== n)) return n
                      if (((n = S(a, e)), void 0 !== n)) return n
                    } else {
                      this.each(function () {
                        jt.set(this, e, t)
                      })
                    }
                  },
                  null,
                  i,
                  arguments.length > 1,
                  null,
                  !0
                )
            },
            removeData: function (t) {
              return this.each(function () {
                jt.remove(this, t)
              })
            }
          }),
          bt.extend({
            queue: function t (e, n, i) {
              var t
              if (e) {
                return (
                  (n = (n || 'fx') + 'queue'),
                  (t = Ht.get(e, n)),
                  i &&
                      (!t || Array.isArray(i)
                        ? (t = Ht.access(e, n, bt.makeArray(i)))
                        : t.push(i)),
                  t || []
                )
              }
            },
            dequeue: function (t, e) {
              e = e || 'fx'
              var n = bt.queue(t, e)
              var i = n.length
              var r = n.shift()
              var o = bt._queueHooks(t, e)
              var a = function () {
                bt.dequeue(t, e)
              }
              r === 'inprogress' && ((r = n.shift()), i--),
              r &&
                    (e === 'fx' && n.unshift('inprogress'),
                    delete o.stop,
                    r.call(t, a, o)),
              !i && o && o.empty.fire()
            },
            _queueHooks: function (t, e) {
              var n = e + 'queueHooks'
              return (
                Ht.get(t, n) ||
                  Ht.access(t, n, {
                    empty: bt.Callbacks('once memory').add(function () {
                      Ht.remove(t, [e + 'queue', n])
                    })
                  })
              )
            }
          }),
          bt.fn.extend({
            queue: function (t, e) {
              var n = 2
              return (
                typeof t !== 'string' && ((e = t), (t = 'fx'), n--),
                arguments.length < n
                  ? bt.queue(this[0], t)
                  : void 0 === e
                    ? this
                    : this.each(function () {
                      var n = bt.queue(this, t, e)
                      bt._queueHooks(this, t),
                      t === 'fx' &&
                              n[0] !== 'inprogress' &&
                              bt.dequeue(this, t)
                    })
              )
            },
            dequeue: function (t) {
              return this.each(function () {
                bt.dequeue(this, t)
              })
            },
            clearQueue: function (t) {
              return this.queue(t || 'fx', [])
            },
            promise: function (t, e) {
              var n
              var i = 1
              var r = bt.Deferred()
              var o = this
              var a = this.length
              var s = function () {
                --i || r.resolveWith(o, [o])
              }
              for (
                typeof t !== 'string' && ((e = t), (t = void 0)),
                t = t || 'fx';
                a--;

              ) {
                (n = Ht.get(o[a], t + 'queueHooks')),
                n && n.empty && (i++, n.empty.add(s))
              }
              return s(), r.promise(e)
            }
          })
          var Gt = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source
          var Vt = new RegExp('^(?:([+-])=|)(' + Gt + ')([a-z%]*)$', 'i')
          var Yt = ['Top', 'Right', 'Bottom', 'Left']
          var Xt = function (t, e) {
            return (
              (t = e || t),
              t.style.display === 'none' ||
                  (t.style.display === '' &&
                    bt.contains(t.ownerDocument, t) &&
                    bt.css(t, 'display') === 'none')
            )
          }
          var Jt = function (t, e, n, i) {
            var r
            var o
            var a = {}
            for (o in e) (a[o] = t.style[o]), (t.style[o] = e[o])
            r = n.apply(t, i || [])
            for (o in e) t.style[o] = a[o]
            return r
          }
          var $t = {}
          bt.fn.extend({
            show: function () {
              return _(this, !0)
            },
            hide: function () {
              return _(this)
            },
            toggle: function (t) {
              return typeof t === 'boolean'
                ? t
                  ? this.show()
                  : this.hide()
                : this.each(function () {
                  Xt(this) ? bt(this).show() : bt(this).hide()
                })
            }
          })
          var Kt = /^(?:checkbox|radio)$/i
          var Zt = /<([a-z][^\/\0>\x20\t\r\n\f]+)/i
          var Qt = /^$|\/(?:java|ecma)script/i
          var te = {
            option: [1, "<select multiple='multiple'>", '</select>'],
            thead: [1, '<table>', '</table>'],
            col: [2, '<table><colgroup>', '</colgroup></table>'],
            tr: [2, '<table><tbody>', '</tbody></table>'],
            td: [3, '<table><tbody><tr>', '</tr></tbody></table>'],
            _default: [0, '', '']
          };
          (te.optgroup = te.option),
          (te.tbody = te.tfoot = te.colgroup = te.caption = te.thead),
          (te.th = te.td)
          var ee = /<|&#?\w+;/
          !(function () {
            var t = st.createDocumentFragment()
            var e = t.appendChild(st.createElement('div'))
            var n = st.createElement('input')
            n.setAttribute('type', 'radio'),
            n.setAttribute('checked', 'checked'),
            n.setAttribute('name', 't'),
            e.appendChild(n),
            (gt.checkClone = e.cloneNode(!0).cloneNode(!0).lastChild.checked),
            (e.innerHTML = '<textarea>x</textarea>'),
            (gt.noCloneChecked = !!e.cloneNode(!0).lastChild.defaultValue)
          })()
          var ne = st.documentElement
          var ie = /^key/
          var re = /^(?:mouse|pointer|contextmenu|drag|drop)|click/
          var oe = /^([^.]*)(?:\.(.+)|)/;
          (bt.event = {
            global: {},
            add: function (t, e, n, i, r) {
              var o
              var a
              var s
              var u
              var l
              var f
              var c
              var h
              var d
              var p
              var y
              var m = Ht.get(t)
              if (m) {
                for (
                  n.handler && ((o = n), (n = o.handler), (r = o.selector)),
                  r && bt.find.matchesSelector(ne, r),
                  n.guid || (n.guid = bt.guid++),
                  (u = m.events) || (u = m.events = {}),
                  (a = m.handle) ||
                      (a = m.handle = function (e) {
                        return typeof bt !== 'undefined' &&
                          bt.event.triggered !== e.type
                          ? bt.event.dispatch.apply(t, arguments)
                          : void 0
                      }),
                  e = (e || '').match(Ot) || [''],
                  l = e.length;
                  l--;

                ) {
                  (s = oe.exec(e[l]) || []),
                  (d = y = s[1]),
                  (p = (s[2] || '').split('.').sort()),
                  d &&
                      ((c = bt.event.special[d] || {}),
                      (d = (r ? c.delegateType : c.bindType) || d),
                      (c = bt.event.special[d] || {}),
                      (f = bt.extend(
                        {
                          type: d,
                          origType: y,
                          data: i,
                          handler: n,
                          guid: n.guid,
                          selector: r,
                          needsContext: r && bt.expr.match.needsContext.test(r),
                          namespace: p.join('.')
                        },
                        o
                      )),
                      (h = u[d]) ||
                        ((h = u[d] = []),
                        (h.delegateCount = 0),
                        (c.setup && c.setup.call(t, i, p, a) !== !1) ||
                          (t.addEventListener && t.addEventListener(d, a))),
                      c.add &&
                        (c.add.call(t, f),
                        f.handler.guid || (f.handler.guid = n.guid)),
                      r ? h.splice(h.delegateCount++, 0, f) : h.push(f),
                      (bt.event.global[d] = !0))
                }
              }
            },
            remove: function (t, e, n, i, r) {
              var o
              var a
              var s
              var u
              var l
              var f
              var c
              var h
              var d
              var p
              var y
              var m = Ht.hasData(t) && Ht.get(t)
              if (m && (u = m.events)) {
                for (e = (e || '').match(Ot) || [''], l = e.length; l--;) {
                  if (
                    ((s = oe.exec(e[l]) || []),
                    (d = y = s[1]),
                    (p = (s[2] || '').split('.').sort()),
                    d)
                  ) {
                    for (
                      c = bt.event.special[d] || {},
                      d = (i ? c.delegateType : c.bindType) || d,
                      h = u[d] || [],
                      s =
                          s[2] &&
                          new RegExp(
                            '(^|\\.)' + p.join('\\.(?:.*\\.|)') + '(\\.|$)'
                          ),
                      a = o = h.length;
                      o--;

                    ) {
                      (f = h[o]),
                      (!r && y !== f.origType) ||
                          (n && n.guid !== f.guid) ||
                          (s && !s.test(f.namespace)) ||
                          (i &&
                            i !== f.selector &&
                            (i !== '**' || !f.selector)) ||
                          (h.splice(o, 1),
                          f.selector && h.delegateCount--,
                          c.remove && c.remove.call(t, f))
                    }
                    a &&
                      !h.length &&
                      ((c.teardown && c.teardown.call(t, p, m.handle) !== !1) ||
                        bt.removeEvent(t, d, m.handle),
                      delete u[d])
                  } else for (d in u) bt.event.remove(t, d + e[l], n, i, !0)
                }
                bt.isEmptyObject(u) && Ht.remove(t, 'handle events')
              }
            },
            dispatch: function (t) {
              var e
              var n
              var i
              var r
              var o
              var a
              var s = bt.event.fix(t)
              var u = new Array(arguments.length)
              var l = (Ht.get(this, 'events') || {})[s.type] || []
              var f = bt.event.special[s.type] || {}
              for (u[0] = s, e = 1; e < arguments.length; e++) { u[e] = arguments[e] }
              if (
                ((s.delegateTarget = this),
                !f.preDispatch || f.preDispatch.call(this, s) !== !1)
              ) {
                for (
                  a = bt.event.handlers.call(this, s, l), e = 0;
                  (r = a[e++]) && !s.isPropagationStopped();

                ) {
                  for (
                    s.currentTarget = r.elem, n = 0;
                    (o = r.handlers[n++]) && !s.isImmediatePropagationStopped();

                  ) {
                    (s.rnamespace && !s.rnamespace.test(o.namespace)) ||
                      ((s.handleObj = o),
                      (s.data = o.data),
                      (i = (
                        (bt.event.special[o.origType] || {}).handle || o.handler
                      ).apply(r.elem, u)),
                      void 0 !== i &&
                        (s.result = i) === !1 &&
                        (s.preventDefault(), s.stopPropagation()))
                  }
                }
                return f.postDispatch && f.postDispatch.call(this, s), s.result
              }
            },
            handlers: function (t, e) {
              var n
              var i
              var r
              var o
              var a
              var s = []
              var u = e.delegateCount
              var l = t.target
              if (u && l.nodeType && !(t.type === 'click' && t.button >= 1)) {
                for (; l !== this; l = l.parentNode || this) {
                  if (
                    l.nodeType === 1 &&
                    (t.type !== 'click' || l.disabled !== !0)
                  ) {
                    for (o = [], a = {}, n = 0; n < u; n++) {
                      (i = e[n]),
                      (r = i.selector + ' '),
                      void 0 === a[r] &&
                          (a[r] = i.needsContext
                            ? bt(r, this).index(l) > -1
                            : bt.find(r, this, null, [l]).length),
                      a[r] && o.push(i)
                    }
                    o.length && s.push({ elem: l, handlers: o })
                  }
                }
              }
              return (
                (l = this),
                u < e.length && s.push({ elem: l, handlers: e.slice(u) }),
                s
              )
            },
            addProp: function (t, e) {
              Object.defineProperty(bt.Event.prototype, t, {
                enumerable: !0,
                configurable: !0,
                get: bt.isFunction(e)
                  ? function () {
                    if (this.originalEvent) return e(this.originalEvent)
                  }
                  : function () {
                    if (this.originalEvent) return this.originalEvent[t]
                  },
                set: function (e) {
                  Object.defineProperty(this, t, {
                    enumerable: !0,
                    configurable: !0,
                    writable: !0,
                    value: e
                  })
                }
              })
            },
            fix: function (t) {
              return t[bt.expando] ? t : new bt.Event(t)
            },
            special: {
              load: { noBubble: !0 },
              focus: {
                trigger: function () {
                  if (this !== D() && this.focus) return this.focus(), !1
                },
                delegateType: 'focusin'
              },
              blur: {
                trigger: function () {
                  if (this === D() && this.blur) return this.blur(), !1
                },
                delegateType: 'focusout'
              },
              click: {
                trigger: function () {
                  if (
                    this.type === 'checkbox' &&
                    this.click &&
                    l(this, 'input')
                  ) { return this.click(), !1 }
                },
                _default: function (t) {
                  return l(t.target, 'a')
                }
              },
              beforeunload: {
                postDispatch: function (t) {
                  void 0 !== t.result &&
                    t.originalEvent &&
                    (t.originalEvent.returnValue = t.result)
                }
              }
            }
          }),
          (bt.removeEvent = function (t, e, n) {
            t.removeEventListener && t.removeEventListener(e, n)
          }),
          (bt.Event = function (t, e) {
            return this instanceof bt.Event
              ? (t && t.type
                ? ((this.originalEvent = t),
                (this.type = t.type),
                (this.isDefaultPrevented =
                        t.defaultPrevented ||
                        (void 0 === t.defaultPrevented && t.returnValue === !1)
                          ? T
                          : k),
                (this.target =
                        t.target && t.target.nodeType === 3
                          ? t.target.parentNode
                          : t.target),
                (this.currentTarget = t.currentTarget),
                (this.relatedTarget = t.relatedTarget))
                : (this.type = t),
              e && bt.extend(this, e),
              (this.timeStamp = (t && t.timeStamp) || bt.now()),
              void (this[bt.expando] = !0))
              : new bt.Event(t, e)
          }),
          (bt.Event.prototype = {
            constructor: bt.Event,
            isDefaultPrevented: k,
            isPropagationStopped: k,
            isImmediatePropagationStopped: k,
            isSimulated: !1,
            preventDefault: function () {
              var t = this.originalEvent;
              (this.isDefaultPrevented = T),
              t && !this.isSimulated && t.preventDefault()
            },
            stopPropagation: function () {
              var t = this.originalEvent;
              (this.isPropagationStopped = T),
              t && !this.isSimulated && t.stopPropagation()
            },
            stopImmediatePropagation: function () {
              var t = this.originalEvent;
              (this.isImmediatePropagationStopped = T),
              t && !this.isSimulated && t.stopImmediatePropagation(),
              this.stopPropagation()
            }
          }),
          bt.each(
            {
              altKey: !0,
              bubbles: !0,
              cancelable: !0,
              changedTouches: !0,
              ctrlKey: !0,
              detail: !0,
              eventPhase: !0,
              metaKey: !0,
              pageX: !0,
              pageY: !0,
              shiftKey: !0,
              view: !0,
              char: !0,
              charCode: !0,
              key: !0,
              keyCode: !0,
              button: !0,
              buttons: !0,
              clientX: !0,
              clientY: !0,
              offsetX: !0,
              offsetY: !0,
              pointerId: !0,
              pointerType: !0,
              screenX: !0,
              screenY: !0,
              targetTouches: !0,
              toElement: !0,
              touches: !0,
              which: function (t) {
                var e = t.button
                return t.which == null && ie.test(t.type)
                  ? t.charCode != null
                    ? t.charCode
                    : t.keyCode
                  : !t.which && void 0 !== e && re.test(t.type)
                    ? 1 & e
                      ? 1
                      : 2 & e
                        ? 3
                        : 4 & e
                          ? 2
                          : 0
                    : t.which
              }
            },
            bt.event.addProp
          ),
          bt.each(
            {
              mouseenter: 'mouseover',
              mouseleave: 'mouseout',
              pointerenter: 'pointerover',
              pointerleave: 'pointerout'
            },
            function (t, e) {
              bt.event.special[t] = {
                delegateType: e,
                bindType: e,
                handle: function (t) {
                  var n
                  var i = this
                  var r = t.relatedTarget
                  var o = t.handleObj
                  return (
                    (r && (r === i || bt.contains(i, r))) ||
                        ((t.type = o.origType),
                        (n = o.handler.apply(this, arguments)),
                        (t.type = e)),
                    n
                  )
                }
              }
            }
          ),
          bt.fn.extend({
            on: function (t, e, n, i) {
              return M(this, t, e, n, i)
            },
            one: function (t, e, n, i) {
              return M(this, t, e, n, i, 1)
            },
            off: function (t, e, i) {
              var r, o
              if (t && t.preventDefault && t.handleObj) {
                return (
                  (r = t.handleObj),
                  bt(t.delegateTarget).off(
                    r.namespace ? r.origType + '.' + r.namespace : r.origType,
                    r.selector,
                    r.handler
                  ),
                  this
                )
              }
              if (
                (typeof t === 'undefined' ? 'undefined' : n(t)) === 'object'
              ) {
                for (o in t) this.off(o, e, t[o])
                return this
              }
              return (
                (e !== !1 && typeof e !== 'function') ||
                    ((i = e), (e = void 0)),
                i === !1 && (i = k),
                this.each(function () {
                  bt.event.remove(this, t, i, e)
                })
              )
            }
          })
          var ae = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([a-z][^\/\0>\x20\t\r\n\f]*)[^>]*)\/>/gi
          var se = /<script|<style|<link/i
          var ue = /checked\s*(?:[^=]|=\s*.checked.)/i
          var le = /^true\/(.*)/
          var fe = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g
          bt.extend({
            htmlPrefilter: function (t) {
              return t.replace(ae, '<$1></$2>')
            },
            clone: function t (e, n, i) {
              var r
              var o
              var a
              var s
              var t = e.cloneNode(!0)
              var u = bt.contains(e.ownerDocument, e)
              if (
                !(
                  gt.noCloneChecked ||
                  (e.nodeType !== 1 && e.nodeType !== 11) ||
                  bt.isXMLDoc(e)
                )
              ) {
                for (s = P(t), a = P(e), r = 0, o = a.length; r < o; r++) { A(a[r], s[r]) }
              }
              if (n) {
                if (i) {
                  for (
                    a = a || P(e), s = s || P(t), r = 0, o = a.length;
                    r < o;
                    r++
                  ) { I(a[r], s[r]) }
                } else I(e, t)
              }
              return (
                (s = P(t, 'script')),
                s.length > 0 && C(s, !u && P(e, 'script')),
                t
              )
            },
            cleanData: function (t) {
              for (
                var e, n, i, r = bt.event.special, o = 0;
                void 0 !== (n = t[o]);
                o++
              ) {
                if (qt(n)) {
                  if ((e = n[Ht.expando])) {
                    if (e.events) {
                      for (i in e.events) {
                        r[i]
                          ? bt.event.remove(n, i)
                          : bt.removeEvent(n, i, e.handle)
                      }
                    }
                    n[Ht.expando] = void 0
                  }
                  n[jt.expando] && (n[jt.expando] = void 0)
                }
              }
            }
          }),
          bt.fn.extend({
            detach: function (t) {
              return O(this, t, !0)
            },
            remove: function (t) {
              return O(this, t)
            },
            text: function (t) {
              return Nt(
                this,
                function (t) {
                  return void 0 === t
                    ? bt.text(this)
                    : this.empty().each(function () {
                      (this.nodeType !== 1 &&
                            this.nodeType !== 11 &&
                            this.nodeType !== 9) ||
                            (this.textContent = t)
                    })
                },
                null,
                t,
                arguments.length
              )
            },
            append: function () {
              return F(this, arguments, function (t) {
                if (
                  this.nodeType === 1 ||
                    this.nodeType === 11 ||
                    this.nodeType === 9
                ) {
                  var e = R(this, t)
                  e.appendChild(t)
                }
              })
            },
            prepend: function () {
              return F(this, arguments, function (t) {
                if (
                  this.nodeType === 1 ||
                    this.nodeType === 11 ||
                    this.nodeType === 9
                ) {
                  var e = R(this, t)
                  e.insertBefore(t, e.firstChild)
                }
              })
            },
            before: function () {
              return F(this, arguments, function (t) {
                this.parentNode && this.parentNode.insertBefore(t, this)
              })
            },
            after: function () {
              return F(this, arguments, function (t) {
                this.parentNode &&
                    this.parentNode.insertBefore(t, this.nextSibling)
              })
            },
            empty: function () {
              for (var t, e = 0; (t = this[e]) != null; e++) {
                t.nodeType === 1 &&
                    (bt.cleanData(P(t, !1)), (t.textContent = ''))
              }
              return this
            },
            clone: function (t, e) {
              return (
                (t = t != null && t),
                (e = e == null ? t : e),
                this.map(function () {
                  return bt.clone(this, t, e)
                })
              )
            },
            html: function (t) {
              return Nt(
                this,
                function (t) {
                  var e = this[0] || {}
                  var n = 0
                  var i = this.length
                  if (void 0 === t && e.nodeType === 1) return e.innerHTML
                  if (
                    typeof t === 'string' &&
                      !se.test(t) &&
                      !te[(Zt.exec(t) || ['', ''])[1].toLowerCase()]
                  ) {
                    t = bt.htmlPrefilter(t)
                    try {
                      for (; n < i; n++) {
                        (e = this[n] || {}),
                        e.nodeType === 1 &&
                              (bt.cleanData(P(e, !1)), (e.innerHTML = t))
                      }
                      e = 0
                    } catch (t) {}
                  }
                  e && this.empty().append(t)
                },
                null,
                t,
                arguments.length
              )
            },
            replaceWith: function () {
              var t = []
              return F(
                this,
                arguments,
                function (e) {
                  var n = this.parentNode
                  bt.inArray(this, t) < 0 &&
                      (bt.cleanData(P(this)), n && n.replaceChild(e, this))
                },
                t
              )
            }
          }),
          bt.each(
            {
              appendTo: 'append',
              prependTo: 'prepend',
              insertBefore: 'before',
              insertAfter: 'after',
              replaceAll: 'replaceWith'
            },
            function (t, e) {
              bt.fn[t] = function (t) {
                for (
                  var n, i = [], r = bt(t), o = r.length - 1, a = 0;
                  a <= o;
                  a++
                ) {
                  (n = a === o ? this : this.clone(!0)),
                  bt(r[a])[e](n),
                  ct.apply(i, n.get())
                }
                return this.pushStack(i)
              }
            }
          )
          var ce = /^margin/
          var he = new RegExp('^(' + Gt + ')(?!px)[a-z%]+$', 'i')
          var de = function (t) {
            var e = t.ownerDocument.defaultView
            return (e && e.opener) || (e = o), e.getComputedStyle(t)
          }
          !(function () {
            function t () {
              if (s) {
                (s.style.cssText =
                  'box-sizing:border-box;position:relative;display:block;margin:auto;border:1px;padding:1px;top:1%;width:50%'),
                (s.innerHTML = ''),
                ne.appendChild(a)
                var t = o.getComputedStyle(s);
                (e = t.top !== '1%'),
                (r = t.marginLeft === '2px'),
                (n = t.width === '4px'),
                (s.style.marginRight = '50%'),
                (i = t.marginRight === '4px'),
                ne.removeChild(a),
                (s = null)
              }
            }
            var e
            var n
            var i
            var r
            var a = st.createElement('div')
            var s = st.createElement('div')
            s.style &&
              ((s.style.backgroundClip = 'content-box'),
              (s.cloneNode(!0).style.backgroundClip = ''),
              (gt.clearCloneStyle = s.style.backgroundClip === 'content-box'),
              (a.style.cssText =
                'border:0;width:8px;height:0;top:0;left:-9999px;padding:0;margin-top:1px;position:absolute'),
              a.appendChild(s),
              bt.extend(gt, {
                pixelPosition: function () {
                  return t(), e
                },
                boxSizingReliable: function () {
                  return t(), n
                },
                pixelMarginRight: function () {
                  return t(), i
                },
                reliableMarginLeft: function () {
                  return t(), r
                }
              }))
          })()
          var pe = /^(none|table(?!-c[ea]).+)/
          var ye = /^--/
          var me = {
            position: 'absolute',
            visibility: 'hidden',
            display: 'block'
          }
          var ve = { letterSpacing: '0', fontWeight: '400' }
          var ge = ['Webkit', 'Moz', 'ms']
          var Se = st.createElement('div').style
          bt.extend({
            cssHooks: {
              opacity: {
                get: function (t, e) {
                  if (e) {
                    var n = B(t, 'opacity')
                    return n === '' ? '1' : n
                  }
                }
              }
            },
            cssNumber: {
              animationIterationCount: !0,
              columnCount: !0,
              fillOpacity: !0,
              flexGrow: !0,
              flexShrink: !0,
              fontWeight: !0,
              lineHeight: !0,
              opacity: !0,
              order: !0,
              orphans: !0,
              widows: !0,
              zIndex: !0,
              zoom: !0
            },
            cssProps: { float: 'cssFloat' },
            style: function t (e, i, r, o) {
              if (e && e.nodeType !== 3 && e.nodeType !== 8 && e.style) {
                var a
                var s
                var u
                var l = bt.camelCase(i)
                var f = ye.test(i)
                var t = e.style
                return (
                  f || (i = q(l)),
                  (u = bt.cssHooks[i] || bt.cssHooks[l]),
                  void 0 === r
                    ? u && 'get' in u && void 0 !== (a = u.get(e, !1, o))
                      ? a
                      : t[i]
                    : ((s = typeof r === 'undefined' ? 'undefined' : n(r)),
                    s === 'string' &&
                        (a = Vt.exec(r)) &&
                        a[1] &&
                        ((r = b(e, i, a)), (s = 'number')),
                    r != null &&
                        r === r &&
                        (s === 'number' &&
                          (r += (a && a[3]) || (bt.cssNumber[l] ? '' : 'px')),
                        gt.clearCloneStyle ||
                          r !== '' ||
                          i.indexOf('background') !== 0 ||
                          (t[i] = 'inherit'),
                        (u && 'set' in u && void 0 === (r = u.set(e, r, o))) ||
                          (f ? t.setProperty(i, r) : (t[i] = r))),
                    void 0)
                )
              }
            },
            css: function (t, e, n, i) {
              var r
              var o
              var a
              var s = bt.camelCase(e)
              var u = ye.test(e)
              return (
                u || (e = q(s)),
                (a = bt.cssHooks[e] || bt.cssHooks[s]),
                a && 'get' in a && (r = a.get(t, !0, n)),
                void 0 === r && (r = B(t, e, i)),
                r === 'normal' && e in ve && (r = ve[e]),
                n === '' || n
                  ? ((o = parseFloat(r)), n === !0 || isFinite(o) ? o || 0 : r)
                  : r
              )
            }
          }),
          bt.each(['height', 'width'], function (t, e) {
            bt.cssHooks[e] = {
              get: function (t, n, i) {
                if (n) {
                  return !pe.test(bt.css(t, 'display')) ||
                      (t.getClientRects().length &&
                        t.getBoundingClientRect().width)
                    ? W(t, e, i)
                    : Jt(t, me, function () {
                      return W(t, e, i)
                    })
                }
              },
              set: function (t, n, i) {
                var r
                var o = i && de(t)
                var a =
                      i &&
                      j(
                        t,
                        e,
                        i,
                        bt.css(t, 'boxSizing', !1, o) === 'border-box',
                        o
                      )
                return (
                  a &&
                      (r = Vt.exec(n)) &&
                      (r[3] || 'px') !== 'px' &&
                      ((t.style[e] = n), (n = bt.css(t, e))),
                  H(t, n, a)
                )
              }
            }
          }),
          (bt.cssHooks.marginLeft = L(gt.reliableMarginLeft, function (t, e) {
            if (e) {
              return (
                (parseFloat(B(t, 'marginLeft')) ||
                    t.getBoundingClientRect().left -
                      Jt(t, { marginLeft: 0 }, function () {
                        return t.getBoundingClientRect().left
                      })) + 'px'
              )
            }
          })),
          bt.each({ margin: '', padding: '', border: 'Width' }, function (
            t,
            e
          ) {
            (bt.cssHooks[t + e] = {
              expand: function (n) {
                for (
                  var i = 0,
                    r = {},
                    o = typeof n === 'string' ? n.split(' ') : [n];
                  i < 4;
                  i++
                ) { r[t + Yt[i] + e] = o[i] || o[i - 2] || o[0] }
                return r
              }
            }),
            ce.test(t) || (bt.cssHooks[t + e].set = H)
          }),
          bt.fn.extend({
            css: function (t, e) {
              return Nt(
                this,
                function (t, e, n) {
                  var i
                  var r
                  var o = {}
                  var a = 0
                  if (Array.isArray(e)) {
                    for (i = de(t), r = e.length; a < r; a++) { o[e[a]] = bt.css(t, e[a], !1, i) }
                    return o
                  }
                  return void 0 !== n ? bt.style(t, e, n) : bt.css(t, e)
                },
                t,
                e,
                arguments.length > 1
              )
            }
          }),
          (bt.Tween = U),
          (U.prototype = {
            constructor: U,
            init: function (t, e, n, i, r, o) {
              (this.elem = t),
              (this.prop = n),
              (this.easing = r || bt.easing._default),
              (this.options = e),
              (this.start = this.now = this.cur()),
              (this.end = i),
              (this.unit = o || (bt.cssNumber[n] ? '' : 'px'))
            },
            cur: function () {
              var t = U.propHooks[this.prop]
              return t && t.get
                ? t.get(this)
                : U.propHooks._default.get(this)
            },
            run: function (t) {
              var e
              var n = U.propHooks[this.prop]
              return (
                this.options.duration
                  ? (this.pos = e = bt.easing[this.easing](
                    t,
                    this.options.duration * t,
                    0,
                    1,
                    this.options.duration
                  ))
                  : (this.pos = e = t),
                (this.now = (this.end - this.start) * e + this.start),
                this.options.step &&
                    this.options.step.call(this.elem, this.now, this),
                n && n.set ? n.set(this) : U.propHooks._default.set(this),
                this
              )
            }
          }),
          (U.prototype.init.prototype = U.prototype),
          (U.propHooks = {
            _default: {
              get: function (t) {
                var e
                return t.elem.nodeType !== 1 ||
                    (t.elem[t.prop] != null && t.elem.style[t.prop] == null)
                  ? t.elem[t.prop]
                  : ((e = bt.css(t.elem, t.prop, '')),
                  e && e !== 'auto' ? e : 0)
              },
              set: function (t) {
                bt.fx.step[t.prop]
                  ? bt.fx.step[t.prop](t)
                  : t.elem.nodeType !== 1 ||
                      (t.elem.style[bt.cssProps[t.prop]] == null &&
                        !bt.cssHooks[t.prop])
                    ? (t.elem[t.prop] = t.now)
                    : bt.style(t.elem, t.prop, t.now + t.unit)
              }
            }
          }),
          (U.propHooks.scrollTop = U.propHooks.scrollLeft = {
            set: function (t) {
              t.elem.nodeType &&
                  t.elem.parentNode &&
                  (t.elem[t.prop] = t.now)
            }
          }),
          (bt.easing = {
            linear: function (t) {
              return t
            },
            swing: function (t) {
              return 0.5 - Math.cos(t * Math.PI) / 2
            },
            _default: 'swing'
          }),
          (bt.fx = U.prototype.init),
          (bt.fx.step = {})
          var be
          var we
          var _e = /^(?:toggle|show|hide)$/
          var Pe = /queueHooks$/;
          (bt.Animation = bt.extend(K, {
            tweeners: {
              '*': [
                function (t, e) {
                  var n = this.createTween(t, e)
                  return b(n.elem, t, Vt.exec(e), n), n
                }
              ]
            },
            tweener: function (t, e) {
              bt.isFunction(t) ? ((e = t), (t = ['*'])) : (t = t.match(Ot))
              for (var n, i = 0, r = t.length; i < r; i++) {
                (n = t[i]),
                (K.tweeners[n] = K.tweeners[n] || []),
                K.tweeners[n].unshift(e)
              }
            },
            prefilters: [J],
            prefilter: function (t, e) {
              e ? K.prefilters.unshift(t) : K.prefilters.push(t)
            }
          })),
          (bt.speed = function (t, e, i) {
            var r =
                t && (typeof t === 'undefined' ? 'undefined' : n(t)) === 'object'
                  ? bt.extend({}, t)
                  : {
                    complete: i || (!i && e) || (bt.isFunction(t) && t),
                    duration: t,
                    easing: (i && e) || (e && !bt.isFunction(e) && e)
                  }
            return (
              bt.fx.off
                ? (r.duration = 0)
                : typeof r.duration !== 'number' &&
                    (r.duration in bt.fx.speeds
                      ? (r.duration = bt.fx.speeds[r.duration])
                      : (r.duration = bt.fx.speeds._default)),
              (r.queue != null && r.queue !== !0) || (r.queue = 'fx'),
              (r.old = r.complete),
              (r.complete = function () {
                bt.isFunction(r.old) && r.old.call(this),
                r.queue && bt.dequeue(this, r.queue)
              }),
              r
            )
          }),
          bt.fn.extend({
            fadeTo: function (t, e, n, i) {
              return this.filter(Xt)
                .css('opacity', 0)
                .show()
                .end()
                .animate({ opacity: e }, t, n, i)
            },
            animate: function (t, e, n, i) {
              var r = bt.isEmptyObject(t)
              var o = bt.speed(e, n, i)
              var a = function () {
                var e = K(this, bt.extend({}, t), o);
                (r || Ht.get(this, 'finish')) && e.stop(!0)
              }
              return (
                (a.finish = a),
                r || o.queue === !1 ? this.each(a) : this.queue(o.queue, a)
              )
            },
            stop: function (t, e, n) {
              var i = function (t) {
                var e = t.stop
                delete t.stop, e(n)
              }
              return (
                typeof t !== 'string' && ((n = e), (e = t), (t = void 0)),
                e && t !== !1 && this.queue(t || 'fx', []),
                this.each(function () {
                  var e = !0
                  var r = t != null && t + 'queueHooks'
                  var o = bt.timers
                  var a = Ht.get(this)
                  if (r) a[r] && a[r].stop && i(a[r])
                  else { for (r in a) a[r] && a[r].stop && Pe.test(r) && i(a[r]) }
                  for (r = o.length; r--;) {
                    o[r].elem !== this ||
                        (t != null && o[r].queue !== t) ||
                        (o[r].anim.stop(n), (e = !1), o.splice(r, 1))
                  }
                  (!e && n) || bt.dequeue(this, t)
                })
              )
            },
            finish: function (t) {
              return (
                t !== !1 && (t = t || 'fx'),
                this.each(function () {
                  var e
                  var n = Ht.get(this)
                  var i = n[t + 'queue']
                  var r = n[t + 'queueHooks']
                  var o = bt.timers
                  var a = i ? i.length : 0
                  for (
                    n.finish = !0,
                    bt.queue(this, t, []),
                    r && r.stop && r.stop.call(this, !0),
                    e = o.length;
                    e--;

                  ) {
                    o[e].elem === this &&
                        o[e].queue === t &&
                        (o[e].anim.stop(!0), o.splice(e, 1))
                  }
                  for (e = 0; e < a; e++) { i[e] && i[e].finish && i[e].finish.call(this) }
                  delete n.finish
                })
              )
            }
          }),
          bt.each(['toggle', 'show', 'hide'], function (t, e) {
            var n = bt.fn[e]
            bt.fn[e] = function (t, i, r) {
              return t == null || typeof t === 'boolean'
                ? n.apply(this, arguments)
                : this.animate(Y(e, !0), t, i, r)
            }
          }),
          bt.each(
            {
              slideDown: Y('show'),
              slideUp: Y('hide'),
              slideToggle: Y('toggle'),
              fadeIn: { opacity: 'show' },
              fadeOut: { opacity: 'hide' },
              fadeToggle: { opacity: 'toggle' }
            },
            function (t, e) {
              bt.fn[t] = function (t, n, i) {
                return this.animate(e, t, n, i)
              }
            }
          ),
          (bt.timers = []),
          (bt.fx.tick = function () {
            var t
            var e = 0
            var n = bt.timers
            for (be = bt.now(); e < n.length; e++) { (t = n[e]), t() || n[e] !== t || n.splice(e--, 1) }
            n.length || bt.fx.stop(), (be = void 0)
          }),
          (bt.fx.timer = function (t) {
            bt.timers.push(t), bt.fx.start()
          }),
          (bt.fx.interval = 13),
          (bt.fx.start = function () {
            we || ((we = !0), G())
          }),
          (bt.fx.stop = function () {
            we = null
          }),
          (bt.fx.speeds = { slow: 600, fast: 200, _default: 400 }),
          (bt.fn.delay = function (t, e) {
            return (
              (t = bt.fx ? bt.fx.speeds[t] || t : t),
              (e = e || 'fx'),
              this.queue(e, function (e, n) {
                var i = o.setTimeout(e, t)
                n.stop = function () {
                  o.clearTimeout(i)
                }
              })
            )
          }),
          (function () {
            var t = st.createElement('input')
            var e = st.createElement('select')
            var n = e.appendChild(st.createElement('option'));
            (t.type = 'checkbox'),
            (gt.checkOn = t.value !== ''),
            (gt.optSelected = n.selected),
            (t = st.createElement('input')),
            (t.value = 't'),
            (t.type = 'radio'),
            (gt.radioValue = t.value === 't')
          })()
          var Ce
          var xe = bt.expr.attrHandle
          bt.fn.extend({
            attr: function (t, e) {
              return Nt(this, bt.attr, t, e, arguments.length > 1)
            },
            removeAttr: function (t) {
              return this.each(function () {
                bt.removeAttr(this, t)
              })
            }
          }),
          bt.extend({
            attr: function (t, e, n) {
              var i
              var r
              var o = t.nodeType
              if (o !== 3 && o !== 8 && o !== 2) {
                return typeof t.getAttribute === 'undefined'
                  ? bt.prop(t, e, n)
                  : ((o === 1 && bt.isXMLDoc(t)) ||
                        (r =
                          bt.attrHooks[e.toLowerCase()] ||
                          (bt.expr.match.bool.test(e) ? Ce : void 0)),
                  void 0 !== n
                    ? n === null
                      ? void bt.removeAttr(t, e)
                      : r && 'set' in r && void 0 !== (i = r.set(t, n, e))
                        ? i
                        : (t.setAttribute(e, n + ''), n)
                    : r && 'get' in r && (i = r.get(t, e)) !== null
                      ? i
                      : ((i = bt.find.attr(t, e)), i == null ? void 0 : i))
              }
            },
            attrHooks: {
              type: {
                set: function (t, e) {
                  if (!gt.radioValue && e === 'radio' && l(t, 'input')) {
                    var n = t.value
                    return t.setAttribute('type', e), n && (t.value = n), e
                  }
                }
              }
            },
            removeAttr: function (t, e) {
              var n
              var i = 0
              var r = e && e.match(Ot)
              if (r && t.nodeType === 1) { for (; (n = r[i++]);) t.removeAttribute(n) }
            }
          }),
          (Ce = {
            set: function (t, e, n) {
              return e === !1 ? bt.removeAttr(t, n) : t.setAttribute(n, n), n
            }
          }),
          bt.each(bt.expr.match.bool.source.match(/\w+/g), function (t, e) {
            var n = xe[e] || bt.find.attr
            xe[e] = function (t, e, i) {
              var r
              var o
              var a = e.toLowerCase()
              return (
                i ||
                    ((o = xe[a]),
                    (xe[a] = r),
                    (r = n(t, e, i) != null ? a : null),
                    (xe[a] = o)),
                r
              )
            }
          })
          var Te = /^(?:input|select|textarea|button)$/i
          var ke = /^(?:a|area)$/i
          bt.fn.extend({
            prop: function (t, e) {
              return Nt(this, bt.prop, t, e, arguments.length > 1)
            },
            removeProp: function (t) {
              return this.each(function () {
                delete this[bt.propFix[t] || t]
              })
            }
          }),
          bt.extend({
            prop: function (t, e, n) {
              var i
              var r
              var o = t.nodeType
              if (o !== 3 && o !== 8 && o !== 2) {
                return (
                  (o === 1 && bt.isXMLDoc(t)) ||
                      ((e = bt.propFix[e] || e), (r = bt.propHooks[e])),
                  void 0 !== n
                    ? r && 'set' in r && void 0 !== (i = r.set(t, n, e))
                      ? i
                      : (t[e] = n)
                    : r && 'get' in r && (i = r.get(t, e)) !== null
                      ? i
                      : t[e]
                )
              }
            },
            propHooks: {
              tabIndex: {
                get: function (t) {
                  var e = bt.find.attr(t, 'tabindex')
                  return e
                    ? parseInt(e, 10)
                    : Te.test(t.nodeName) || (ke.test(t.nodeName) && t.href)
                      ? 0
                      : -1
                }
              }
            },
            propFix: { for: 'htmlFor', class: 'className' }
          }),
          gt.optSelected ||
              (bt.propHooks.selected = {
                get: function (t) {
                  var e = t.parentNode
                  return e && e.parentNode && e.parentNode.selectedIndex, null
                },
                set: function (t) {
                  var e = t.parentNode
                  e &&
                    (e.selectedIndex,
                    e.parentNode && e.parentNode.selectedIndex)
                }
              }),
          bt.each(
            [
              'tabIndex',
              'readOnly',
              'maxLength',
              'cellSpacing',
              'cellPadding',
              'rowSpan',
              'colSpan',
              'useMap',
              'frameBorder',
              'contentEditable'
            ],
            function () {
              bt.propFix[this.toLowerCase()] = this
            }
          ),
          bt.fn.extend({
            addClass: function (t) {
              var e
              var n
              var i
              var r
              var o
              var a
              var s
              var u = 0
              if (bt.isFunction(t)) {
                return this.each(function (e) {
                  bt(this).addClass(t.call(this, e, Q(this)))
                })
              }
              if (typeof t === 'string' && t) {
                for (e = t.match(Ot) || []; (n = this[u++]);) {
                  if (
                    ((r = Q(n)), (i = n.nodeType === 1 && ' ' + Z(r) + ' '))
                  ) {
                    for (a = 0; (o = e[a++]);) { i.indexOf(' ' + o + ' ') < 0 && (i += o + ' ') }
                    (s = Z(i)), r !== s && n.setAttribute('class', s)
                  }
                }
              }
              return this
            },
            removeClass: function (t) {
              var e
              var n
              var i
              var r
              var o
              var a
              var s
              var u = 0
              if (bt.isFunction(t)) {
                return this.each(function (e) {
                  bt(this).removeClass(t.call(this, e, Q(this)))
                })
              }
              if (!arguments.length) return this.attr('class', '')
              if (typeof t === 'string' && t) {
                for (e = t.match(Ot) || []; (n = this[u++]);) {
                  if (
                    ((r = Q(n)), (i = n.nodeType === 1 && ' ' + Z(r) + ' '))
                  ) {
                    for (a = 0; (o = e[a++]);) {
                      for (; i.indexOf(' ' + o + ' ') > -1;) { i = i.replace(' ' + o + ' ', ' ') }
                    }
                    (s = Z(i)), r !== s && n.setAttribute('class', s)
                  }
                }
              }
              return this
            },
            toggleClass: function (t, e) {
              var i = typeof t === 'undefined' ? 'undefined' : n(t)
              return typeof e === 'boolean' && i === 'string'
                ? e
                  ? this.addClass(t)
                  : this.removeClass(t)
                : bt.isFunction(t)
                  ? this.each(function (n) {
                    bt(this).toggleClass(t.call(this, n, Q(this), e), e)
                  })
                  : this.each(function () {
                    var e, n, r, o
                    if (i === 'string') {
                      for (
                        n = 0, r = bt(this), o = t.match(Ot) || [];
                        (e = o[n++]);

                      ) { r.hasClass(e) ? r.removeClass(e) : r.addClass(e) }
                    } else {
                      (void 0 !== t && i !== 'boolean') ||
                            ((e = Q(this)),
                            e && Ht.set(this, '__className__', e),
                            this.setAttribute &&
                              this.setAttribute(
                                'class',
                                e || t === !1
                                  ? ''
                                  : Ht.get(this, '__className__') || ''
                              ))
                    }
                  })
            },
            hasClass: function (t) {
              var e
              var n
              var i = 0
              for (e = ' ' + t + ' '; (n = this[i++]);) {
                if (n.nodeType === 1 && (' ' + Z(Q(n)) + ' ').indexOf(e) > -1) { return !0 }
              }
              return !1
            }
          })
          var De = /\r/g
          bt.fn.extend({
            val: function (t) {
              var e
              var n
              var i
              var r = this[0]
              {
                if (arguments.length) {
                  return (
                    (i = bt.isFunction(t)),
                    this.each(function (n) {
                      var r
                      this.nodeType === 1 &&
                        ((r = i ? t.call(this, n, bt(this).val()) : t),
                        r == null
                          ? (r = '')
                          : typeof r === 'number'
                            ? (r += '')
                            : Array.isArray(r) &&
                              (r = bt.map(r, function (t) {
                                return t == null ? '' : t + ''
                              })),
                        (e =
                          bt.valHooks[this.type] ||
                          bt.valHooks[this.nodeName.toLowerCase()]),
                        (e &&
                          'set' in e &&
                          void 0 !== e.set(this, r, 'value')) ||
                          (this.value = r))
                    })
                  )
                }
                if (r) {
                  return (
                    (e =
                      bt.valHooks[r.type] ||
                      bt.valHooks[r.nodeName.toLowerCase()]),
                    e && 'get' in e && void 0 !== (n = e.get(r, 'value'))
                      ? n
                      : ((n = r.value),
                      typeof n === 'string'
                        ? n.replace(De, '')
                        : n == null
                          ? ''
                          : n)
                  )
                }
              }
            }
          }),
          bt.extend({
            valHooks: {
              option: {
                get: function (t) {
                  var e = bt.find.attr(t, 'value')
                  return e != null ? e : Z(bt.text(t))
                }
              },
              select: {
                get: function (t) {
                  var e
                  var n
                  var i
                  var r = t.options
                  var o = t.selectedIndex
                  var a = t.type === 'select-one'
                  var s = a ? null : []
                  var u = a ? o + 1 : r.length
                  for (i = o < 0 ? u : a ? o : 0; i < u; i++) {
                    if (
                      ((n = r[i]),
                      (n.selected || i === o) &&
                          !n.disabled &&
                          (!n.parentNode.disabled ||
                            !l(n.parentNode, 'optgroup')))
                    ) {
                      if (((e = bt(n).val()), a)) return e
                      s.push(e)
                    }
                  }
                  return s
                },
                set: function (t, e) {
                  for (
                    var n,
                      i,
                      r = t.options,
                      o = bt.makeArray(e),
                      a = r.length;
                    a--;

                  ) {
                    (i = r[a]),
                    (i.selected =
                          bt.inArray(bt.valHooks.option.get(i), o) > -1) &&
                          (n = !0)
                  }
                  return n || (t.selectedIndex = -1), o
                }
              }
            }
          }),
          bt.each(['radio', 'checkbox'], function () {
            (bt.valHooks[this] = {
              set: function (t, e) {
                if (Array.isArray(e)) { return (t.checked = bt.inArray(bt(t).val(), e) > -1) }
              }
            }),
            gt.checkOn ||
                  (bt.valHooks[this].get = function (t) {
                    return t.getAttribute('value') === null ? 'on' : t.value
                  })
          })
          var Me = /^(?:focusinfocus|focusoutblur)$/
          bt.extend(bt.event, {
            trigger: function (t, e, i, r) {
              var a
              var s
              var u
              var l
              var f
              var c
              var h
              var d = [i || st]
              var p = yt.call(t, 'type') ? t.type : t
              var y = yt.call(t, 'namespace') ? t.namespace.split('.') : []
              if (
                ((s = u = i = i || st),
                i.nodeType !== 3 &&
                  i.nodeType !== 8 &&
                  !Me.test(p + bt.event.triggered) &&
                  (p.indexOf('.') > -1 &&
                    ((y = p.split('.')), (p = y.shift()), y.sort()),
                  (f = p.indexOf(':') < 0 && 'on' + p),
                  (t = t[bt.expando]
                    ? t
                    : new bt.Event(
                      p,
                      (typeof t === 'undefined' ? 'undefined' : n(t)) ===
                          'object' && t
                    )),
                  (t.isTrigger = r ? 2 : 3),
                  (t.namespace = y.join('.')),
                  (t.rnamespace = t.namespace
                    ? new RegExp(
                      '(^|\\.)' + y.join('\\.(?:.*\\.|)') + '(\\.|$)'
                    )
                    : null),
                  (t.result = void 0),
                  t.target || (t.target = i),
                  (e = e == null ? [t] : bt.makeArray(e, [t])),
                  (h = bt.event.special[p] || {}),
                  r || !h.trigger || h.trigger.apply(i, e) !== !1))
              ) {
                if (!r && !h.noBubble && !bt.isWindow(i)) {
                  for (
                    l = h.delegateType || p,
                    Me.test(l + p) || (s = s.parentNode);
                    s;
                    s = s.parentNode
                  ) { d.push(s), (u = s) }
                  u === (i.ownerDocument || st) &&
                    d.push(u.defaultView || u.parentWindow || o)
                }
                for (a = 0; (s = d[a++]) && !t.isPropagationStopped();) {
                  (t.type = a > 1 ? l : h.bindType || p),
                  (c =
                      (Ht.get(s, 'events') || {})[t.type] &&
                      Ht.get(s, 'handle')),
                  c && c.apply(s, e),
                  (c = f && s[f]),
                  c &&
                      c.apply &&
                      qt(s) &&
                      ((t.result = c.apply(s, e)),
                      t.result === !1 && t.preventDefault())
                }
                return (
                  (t.type = p),
                  r ||
                    t.isDefaultPrevented() ||
                    (h._default && h._default.apply(d.pop(), e) !== !1) ||
                    !qt(i) ||
                    (f &&
                      bt.isFunction(i[p]) &&
                      !bt.isWindow(i) &&
                      ((u = i[f]),
                      u && (i[f] = null),
                      (bt.event.triggered = p),
                      i[p](),
                      (bt.event.triggered = void 0),
                      u && (i[f] = u))),
                  t.result
                )
              }
            },
            simulate: function (t, e, n) {
              var i = bt.extend(new bt.Event(), n, {
                type: t,
                isSimulated: !0
              })
              bt.event.trigger(i, null, e)
            }
          }),
          bt.fn.extend({
            trigger: function (t, e) {
              return this.each(function () {
                bt.event.trigger(t, e, this)
              })
            },
            triggerHandler: function (t, e) {
              var n = this[0]
              if (n) return bt.event.trigger(t, e, n, !0)
            }
          }),
          bt.each(
            'blur focus focusin focusout resize scroll click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup contextmenu'.split(
              ' '
            ),
            function (t, e) {
              bt.fn[e] = function (t, n) {
                return arguments.length > 0
                  ? this.on(e, null, t, n)
                  : this.trigger(e)
              }
            }
          ),
          bt.fn.extend({
            hover: function (t, e) {
              return this.mouseenter(t).mouseleave(e || t)
            }
          }),
          (gt.focusin = 'onfocusin' in o),
          gt.focusin ||
              bt.each({ focus: 'focusin', blur: 'focusout' }, function (t, e) {
                var n = function (t) {
                  bt.event.simulate(e, t.target, bt.event.fix(t))
                }
                bt.event.special[e] = {
                  setup: function () {
                    var i = this.ownerDocument || this
                    var r = Ht.access(i, e)
                    r || i.addEventListener(t, n, !0),
                    Ht.access(i, e, (r || 0) + 1)
                  },
                  teardown: function () {
                    var i = this.ownerDocument || this
                    var r = Ht.access(i, e) - 1
                    r
                      ? Ht.access(i, e, r)
                      : (i.removeEventListener(t, n, !0), Ht.remove(i, e))
                  }
                }
              })
          var Re = o.location
          var Ee = bt.now()
          var ze = /\?/
          bt.parseXML = function (t) {
            var e
            if (!t || typeof t !== 'string') return null
            try {
              e = new o.DOMParser().parseFromString(t, 'text/xml')
            } catch (t) {
              e = void 0
            }
            return (
              (e && !e.getElementsByTagName('parsererror').length) ||
                bt.error('Invalid XML: ' + t),
              e
            )
          }
          var Ie = /\[\]$/
          var Ae = /\r?\n/g
          var Fe = /^(?:submit|button|image|reset|file)$/i
          var Oe = /^(?:input|select|textarea|keygen)/i;
          (bt.param = function (t, e) {
            var n
            var i = []
            var r = function (t, e) {
              var n = bt.isFunction(e) ? e() : e
              i[i.length] =
                  encodeURIComponent(t) +
                  '=' +
                  encodeURIComponent(n == null ? '' : n)
            }
            if (Array.isArray(t) || (t.jquery && !bt.isPlainObject(t))) {
              bt.each(t, function () {
                r(this.name, this.value)
              })
            } else for (n in t) tt(n, t[n], e, r)
            return i.join('&')
          }),
          bt.fn.extend({
            serialize: function () {
              return bt.param(this.serializeArray())
            },
            serializeArray: function () {
              return this.map(function () {
                var t = bt.prop(this, 'elements')
                return t ? bt.makeArray(t) : this
              })
                .filter(function () {
                  var t = this.type
                  return (
                    this.name &&
                      !bt(this).is(':disabled') &&
                      Oe.test(this.nodeName) &&
                      !Fe.test(t) &&
                      (this.checked || !Kt.test(t))
                  )
                })
                .map(function (t, e) {
                  var n = bt(this).val()
                  return n == null
                    ? null
                    : Array.isArray(n)
                      ? bt.map(n, function (t) {
                        return {
                          name: e.name,
                          value: t.replace(Ae, '\r\n')
                        }
                      })
                      : { name: e.name, value: n.replace(Ae, '\r\n') }
                })
                .get()
            }
          })
          var Be = /%20/g
          var Le = /#.*$/
          var Ne = /([?&])_=[^&]*/
          var qe = /^(.*?):[ \t]*([^\r\n]*)$/gm
          var He = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/
          var je = /^(?:GET|HEAD)$/
          var We = /^\/\//
          var Ue = {}
          var Ge = {}
          var Ve = '*/'.concat('*')
          var Ye = st.createElement('a');
          (Ye.href = Re.href),
          bt.extend({
            active: 0,
            lastModified: {},
            etag: {},
            ajaxSettings: {
              url: Re.href,
              type: 'GET',
              isLocal: He.test(Re.protocol),
              global: !0,
              processData: !0,
              async: !0,
              contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
              accepts: {
                '*': Ve,
                text: 'text/plain',
                html: 'text/html',
                xml: 'application/xml, text/xml',
                json: 'application/json, text/javascript'
              },
              contents: { xml: /\bxml\b/, html: /\bhtml/, json: /\bjson\b/ },
              responseFields: {
                xml: 'responseXML',
                text: 'responseText',
                json: 'responseJSON'
              },
              converters: {
                '* text': String,
                'text html': !0,
                'text json': JSON.parse,
                'text xml': bt.parseXML
              },
              flatOptions: { url: !0, context: !0 }
            },
            ajaxSetup: function (t, e) {
              return e
                ? it(it(t, bt.ajaxSettings), e)
                : it(bt.ajaxSettings, t)
            },
            ajaxPrefilter: et(Ue),
            ajaxTransport: et(Ge),
            ajax: function (t, e) {
              function i (t, e, n, i) {
                var u
                var f
                var d
                var p
                var w
                var _ = e
                c ||
                    ((c = !0),
                    l && o.clearTimeout(l),
                    (r = void 0),
                    (s = i || ''),
                    (C.readyState = t > 0 ? 4 : 0),
                    (u = (t >= 200 && t < 300) || t === 304),
                    n && (p = rt(y, C, n)),
                    (p = ot(y, p, C, u)),
                    u
                      ? (y.ifModified &&
                          ((w = C.getResponseHeader('Last-Modified')),
                          w && (bt.lastModified[a] = w),
                          (w = C.getResponseHeader('etag')),
                          w && (bt.etag[a] = w)),
                      t === 204 || y.type === 'HEAD'
                        ? (_ = 'nocontent')
                        : t === 304
                          ? (_ = 'notmodified')
                          : ((_ = p.state),
                          (f = p.data),
                          (d = p.error),
                          (u = !d)))
                      : ((d = _),
                      (!t && _) || ((_ = 'error'), t < 0 && (t = 0))),
                    (C.status = t),
                    (C.statusText = (e || _) + ''),
                    u
                      ? g.resolveWith(m, [f, _, C])
                      : g.rejectWith(m, [C, _, d]),
                    C.statusCode(b),
                    (b = void 0),
                    h &&
                      v.trigger(u ? 'ajaxSuccess' : 'ajaxError', [
                        C,
                        y,
                        u ? f : d
                      ]),
                    S.fireWith(m, [C, _]),
                    h &&
                      (v.trigger('ajaxComplete', [C, y]),
                      --bt.active || bt.event.trigger('ajaxStop')))
              }
              (typeof t === 'undefined' ? 'undefined' : n(t)) === 'object' &&
                  ((e = t), (t = void 0)),
              (e = e || {})
              var r
              var a
              var s
              var u
              var l
              var f
              var c
              var h
              var d
              var p
              var y = bt.ajaxSetup({}, e)
              var m = y.context || y
              var v = y.context && (m.nodeType || m.jquery) ? bt(m) : bt.event
              var g = bt.Deferred()
              var S = bt.Callbacks('once memory')
              var b = y.statusCode || {}
              var w = {}
              var _ = {}
              var P = 'canceled'
              var C = {
                readyState: 0,
                getResponseHeader: function (t) {
                  var e
                  if (c) {
                    if (!u) {
                      for (u = {}; (e = qe.exec(s));) { u[e[1].toLowerCase()] = e[2] }
                    }
                    e = u[t.toLowerCase()]
                  }
                  return e == null ? null : e
                },
                getAllResponseHeaders: function () {
                  return c ? s : null
                },
                setRequestHeader: function (t, e) {
                  return (
                    c == null &&
                          ((t = _[t.toLowerCase()] = _[t.toLowerCase()] || t),
                          (w[t] = e)),
                    this
                  )
                },
                overrideMimeType: function (t) {
                  return c == null && (y.mimeType = t), this
                },
                statusCode: function (t) {
                  var e
                  if (t) {
                    if (c) C.always(t[C.status])
                    else for (e in t) b[e] = [b[e], t[e]]
                  }
                  return this
                },
                abort: function (t) {
                  var e = t || P
                  return r && r.abort(e), i(0, e), this
                }
              }
              if (
                (g.promise(C),
                (y.url = ((t || y.url || Re.href) + '').replace(
                  We,
                  Re.protocol + '//'
                )),
                (y.type = e.method || e.type || y.method || y.type),
                (y.dataTypes = (y.dataType || '*')
                  .toLowerCase()
                  .match(Ot) || ['']),
                y.crossDomain == null)
              ) {
                f = st.createElement('a')
                try {
                  console.log('YE', Ye, Ye.host)
                  (f.href = y.url),
                  (f.href = f.href),
                  (y.crossDomain =
                        Ye.protocol + '//' + Ye.host !=
                        f.protocol + '//' + f.host)
                } catch (t) {
                  y.crossDomain = !0
                }
              }
              if (
                (y.data &&
                    y.processData &&
                    typeof y.data !== 'string' &&
                    (y.data = bt.param(y.data, y.traditional)),
                nt(Ue, y, e, C),
                c)
              ) { return C }
              (h = bt.event && y.global),
              h && bt.active++ === 0 && bt.event.trigger('ajaxStart'),
              (y.type = y.type.toUpperCase()),
              (y.hasContent = !je.test(y.type)),
              (a = y.url.replace(Le, '')),
              y.hasContent
                ? y.data &&
                      y.processData &&
                      (y.contentType || '').indexOf(
                        'application/x-www-form-urlencoded'
                      ) ===
                        0 &&
                      (y.data = y.data.replace(Be, '+'))
                : ((p = y.url.slice(a.length)),
                y.data &&
                        ((a += (ze.test(a) ? '&' : '?') + y.data),
                        delete y.data),
                y.cache === !1 &&
                        ((a = a.replace(Ne, '$1')),
                        (p = (ze.test(a) ? '&' : '?') + '_=' + Ee++ + p)),
                (y.url = a + p)),
              y.ifModified &&
                    (bt.lastModified[a] &&
                      C.setRequestHeader(
                        'If-Modified-Since',
                        bt.lastModified[a]
                      ),
                    bt.etag[a] &&
                      C.setRequestHeader('If-None-Match', bt.etag[a])),
              ((y.data && y.hasContent && y.contentType !== !1) ||
                    e.contentType) &&
                    C.setRequestHeader('Content-Type', y.contentType),
              C.setRequestHeader(
                'Accept',
                y.dataTypes[0] && y.accepts[y.dataTypes[0]]
                  ? y.accepts[y.dataTypes[0]] +
                        (y.dataTypes[0] !== '*' ? ', ' + Ve + '; q=0.01' : '')
                  : y.accepts['*']
              )
              for (d in y.headers) C.setRequestHeader(d, y.headers[d])
              if (y.beforeSend && (y.beforeSend.call(m, C, y) === !1 || c)) { return C.abort() }
              if (
                ((P = 'abort'),
                S.add(y.complete),
                C.done(y.success),
                C.fail(y.error),
                (r = nt(Ge, y, e, C)))
              ) {
                if (
                  ((C.readyState = 1), h && v.trigger('ajaxSend', [C, y]), c)
                ) { return C }
                y.async &&
                    y.timeout > 0 &&
                    (l = o.setTimeout(function () {
                      C.abort('timeout')
                    }, y.timeout))
                try {
                  (c = !1), r.send(w, i)
                } catch (t) {
                  if (c) throw t
                  i(-1, t)
                }
              } else i(-1, 'No Transport')
              return C
            },
            getJSON: function (t, e, n) {
              return bt.get(t, e, n, 'json')
            },
            getScript: function (t, e) {
              return bt.get(t, void 0, e, 'script')
            }
          }),
          bt.each(['get', 'post'], function (t, e) {
            bt[e] = function (t, n, i, r) {
              return (
                bt.isFunction(n) && ((r = r || i), (i = n), (n = void 0)),
                bt.ajax(
                  bt.extend(
                    { url: t, type: e, dataType: r, data: n, success: i },
                    bt.isPlainObject(t) && t
                  )
                )
              )
            }
          }),
          (bt._evalUrl = function (t) {
            return bt.ajax({
              url: t,
              type: 'GET',
              dataType: 'script',
              cache: !0,
              async: !1,
              global: !1,
              throws: !0
            })
          }),
          bt.fn.extend({
            wrapAll: function (t) {
              var e
              return (
                this[0] &&
                    (bt.isFunction(t) && (t = t.call(this[0])),
                    (e = bt(t, this[0].ownerDocument)
                      .eq(0)
                      .clone(!0)),
                    this[0].parentNode && e.insertBefore(this[0]),
                    e
                      .map(function () {
                        for (var t = this; t.firstElementChild;) { t = t.firstElementChild }
                        return t
                      })
                      .append(this)),
                this
              )
            },
            wrapInner: function (t) {
              return bt.isFunction(t)
                ? this.each(function (e) {
                  bt(this).wrapInner(t.call(this, e))
                })
                : this.each(function () {
                  var e = bt(this)
                  var n = e.contents()
                  n.length ? n.wrapAll(t) : e.append(t)
                })
            },
            wrap: function (t) {
              var e = bt.isFunction(t)
              return this.each(function (n) {
                bt(this).wrapAll(e ? t.call(this, n) : t)
              })
            },
            unwrap: function (t) {
              return (
                this.parent(t)
                  .not('body')
                  .each(function () {
                    bt(this).replaceWith(this.childNodes)
                  }),
                this
              )
            }
          }),
          (bt.expr.pseudos.hidden = function (t) {
            return !bt.expr.pseudos.visible(t)
          }),
          (bt.expr.pseudos.visible = function (t) {
            return !!(
              t.offsetWidth ||
                t.offsetHeight ||
                t.getClientRects().length
            )
          }),
          (bt.ajaxSettings.xhr = function () {
            try {
              return new o.XMLHttpRequest()
            } catch (t) {}
          })
          var Xe = { 0: 200, 1223: 204 }
          var Je = bt.ajaxSettings.xhr();
          (gt.cors = !!Je && 'withCredentials' in Je),
          (gt.ajax = Je = !!Je),
          bt.ajaxTransport(function (t) {
            var e, n
            if (gt.cors || (Je && !t.crossDomain)) {
              return {
                send: function (i, r) {
                  var a
                  var s = t.xhr()
                  if (
                    (s.open(t.type, t.url, t.async, t.username, t.password),
                    t.xhrFields)
                  ) { for (a in t.xhrFields) s[a] = t.xhrFields[a] }
                  t.mimeType &&
                      s.overrideMimeType &&
                      s.overrideMimeType(t.mimeType),
                  t.crossDomain ||
                        i['X-Requested-With'] ||
                        (i['X-Requested-With'] = 'XMLHttpRequest')
                  for (a in i) s.setRequestHeader(a, i[a]);
                  (e = function (t) {
                    return function () {
                      e &&
                          ((e = n = s.onload = s.onerror = s.onabort = s.onreadystatechange = null),
                          t === 'abort'
                            ? s.abort()
                            : t === 'error'
                              ? typeof s.status !== 'number'
                                ? r(0, 'error')
                                : r(s.status, s.statusText)
                              : r(
                                Xe[s.status] || s.status,
                                s.statusText,
                                (s.responseType || 'text') !== 'text' ||
                                  typeof s.responseText !== 'string'
                                  ? { binary: s.response }
                                  : { text: s.responseText },
                                s.getAllResponseHeaders()
                              ))
                    }
                  }),
                  (s.onload = e()),
                  (n = s.onerror = e('error')),
                  void 0 !== s.onabort
                    ? (s.onabort = n)
                    : (s.onreadystatechange = function () {
                      s.readyState === 4 &&
                              o.setTimeout(function () {
                                e && n()
                              })
                    }),
                  (e = e('abort'))
                  try {
                    s.send((t.hasContent && t.data) || null)
                  } catch (t) {
                    if (e) throw t
                  }
                },
                abort: function () {
                  e && e()
                }
              }
            }
          }),
          bt.ajaxPrefilter(function (t) {
            t.crossDomain && (t.contents.script = !1)
          }),
          bt.ajaxSetup({
            accepts: {
              script:
                  'text/javascript, application/javascript, application/ecmascript, application/x-ecmascript'
            },
            contents: { script: /\b(?:java|ecma)script\b/ },
            converters: {
              'text script': function (t) {
                return bt.globalEval(t), t
              }
            }
          }),
          bt.ajaxPrefilter('script', function (t) {
            void 0 === t.cache && (t.cache = !1),
            t.crossDomain && (t.type = 'GET')
          }),
          bt.ajaxTransport('script', function (t) {
            if (t.crossDomain) {
              var e, n
              return {
                send: function (i, r) {
                  (e = bt('<script>')
                    .prop({ charset: t.scriptCharset, src: t.url })
                    .on(
                      'load error',
                      (n = function (t) {
                        e.remove(),
                        (n = null),
                        t && r(t.type === 'error' ? 404 : 200, t.type)
                      })
                    )),
                  st.head.appendChild(e[0])
                },
                abort: function () {
                  n && n()
                }
              }
            }
          })
          var $e = []
          var Ke = /(=)\?(?=&|$)|\?\?/
          bt.ajaxSetup({
            jsonp: 'callback',
            jsonpCallback: function () {
              var t = $e.pop() || bt.expando + '_' + Ee++
              return (this[t] = !0), t
            }
          }),
          bt.ajaxPrefilter('json jsonp', function (t, e, n) {
            var i
            var r
            var a
            var s =
                  t.jsonp !== !1 &&
                  (Ke.test(t.url)
                    ? 'url'
                    : typeof t.data === 'string' &&
                      (t.contentType || '').indexOf(
                        'application/x-www-form-urlencoded'
                      ) ===
                        0 &&
                      Ke.test(t.data) &&
                      'data')
            if (s || t.dataTypes[0] === 'jsonp') {
              return (
                (i = t.jsonpCallback = bt.isFunction(t.jsonpCallback)
                  ? t.jsonpCallback()
                  : t.jsonpCallback),
                s
                  ? (t[s] = t[s].replace(Ke, '$1' + i))
                  : t.jsonp !== !1 &&
                      (t.url +=
                        (ze.test(t.url) ? '&' : '?') + t.jsonp + '=' + i),
                (t.converters['script json'] = function () {
                  return a || bt.error(i + ' was not called'), a[0]
                }),
                (t.dataTypes[0] = 'json'),
                (r = o[i]),
                (o[i] = function () {
                  a = arguments
                }),
                n.always(function () {
                  void 0 === r ? bt(o).removeProp(i) : (o[i] = r),
                  t[i] && ((t.jsonpCallback = e.jsonpCallback), $e.push(i)),
                  a && bt.isFunction(r) && r(a[0]),
                  (a = r = void 0)
                }),
                'script'
              )
            }
          }),
          (gt.createHTMLDocument = (function () {
            var t = st.implementation.createHTMLDocument('').body
            return (
              (t.innerHTML = '<form></form><form></form>'),
              t.childNodes.length === 2
            )
          })()),
          (bt.parseHTML = function (t, e, n) {
            if (typeof t !== 'string') return []
            typeof e === 'boolean' && ((n = e), (e = !1))
            var i, r, o
            return (
              e ||
                  (gt.createHTMLDocument
                    ? ((e = st.implementation.createHTMLDocument('')),
                    (i = e.createElement('base')),
                    (i.href = st.location.href),
                    e.head.appendChild(i))
                    : (e = st)),
              (r = Mt.exec(t)),
              (o = !n && []),
              r
                ? [e.createElement(r[1])]
                : ((r = x([t], e, o)),
                o && o.length && bt(o).remove(),
                bt.merge([], r.childNodes))
            )
          }),
          (bt.fn.load = function (t, e, i) {
            var r
            var o
            var a
            var s = this
            var u = t.indexOf(' ')
            return (
              u > -1 && ((r = Z(t.slice(u))), (t = t.slice(0, u))),
              bt.isFunction(e)
                ? ((i = e), (e = void 0))
                : e &&
                    (typeof e === 'undefined' ? 'undefined' : n(e)) ===
                      'object' &&
                    (o = 'POST'),
              s.length > 0 &&
                  bt
                    .ajax({
                      url: t,
                      type: o || 'GET',
                      dataType: 'html',
                      data: e
                    })
                    .done(function (t) {
                      (a = arguments),
                      s.html(
                        r
                          ? bt('<div>')
                            .append(bt.parseHTML(t))
                            .find(r)
                          : t
                      )
                    })
                    .always(
                      i &&
                        function (t, e) {
                          s.each(function () {
                            i.apply(this, a || [t.responseText, e, t])
                          })
                        }
                    ),
              this
            )
          }),
          bt.each(
            [
              'ajaxStart',
              'ajaxStop',
              'ajaxComplete',
              'ajaxError',
              'ajaxSuccess',
              'ajaxSend'
            ],
            function (t, e) {
              bt.fn[e] = function (t) {
                return this.on(e, t)
              }
            }
          ),
          (bt.expr.pseudos.animated = function (t) {
            return bt.grep(bt.timers, function (e) {
              return t === e.elem
            }).length
          }),
          (bt.offset = {
            setOffset: function (t, e, n) {
              var i
              var r
              var o
              var a
              var s
              var u
              var l
              var f = bt.css(t, 'position')
              var c = bt(t)
              var h = {}
              f === 'static' && (t.style.position = 'relative'),
              (s = c.offset()),
              (o = bt.css(t, 'top')),
              (u = bt.css(t, 'left')),
              (l =
                    (f === 'absolute' || f === 'fixed') &&
                    (o + u).indexOf('auto') > -1),
              l
                ? ((i = c.position()), (a = i.top), (r = i.left))
                : ((a = parseFloat(o) || 0), (r = parseFloat(u) || 0)),
              bt.isFunction(e) && (e = e.call(t, n, bt.extend({}, s))),
              e.top != null && (h.top = e.top - s.top + a),
              e.left != null && (h.left = e.left - s.left + r),
              'using' in e ? e.using.call(t, h) : c.css(h)
            }
          }),
          bt.fn.extend({
            offset: function (t) {
              if (arguments.length) {
                return void 0 === t
                  ? this
                  : this.each(function (e) {
                    bt.offset.setOffset(this, t, e)
                  })
              }
              var e
              var n
              var i
              var r
              var o = this[0]
              if (o) {
                return o.getClientRects().length
                  ? ((i = o.getBoundingClientRect()),
                  (e = o.ownerDocument),
                  (n = e.documentElement),
                  (r = e.defaultView),
                  {
                    top: i.top + r.pageYOffset - n.clientTop,
                    left: i.left + r.pageXOffset - n.clientLeft
                  })
                  : { top: 0, left: 0 }
              }
            },
            position: function () {
              if (this[0]) {
                var t
                var e
                var n = this[0]
                var i = { top: 0, left: 0 }
                return (
                  bt.css(n, 'position') === 'fixed'
                    ? (e = n.getBoundingClientRect())
                    : ((t = this.offsetParent()),
                    (e = this.offset()),
                    l(t[0], 'html') || (i = t.offset()),
                    (i = {
                      top: i.top + bt.css(t[0], 'borderTopWidth', !0),
                      left: i.left + bt.css(t[0], 'borderLeftWidth', !0)
                    })),
                  {
                    top: e.top - i.top - bt.css(n, 'marginTop', !0),
                    left: e.left - i.left - bt.css(n, 'marginLeft', !0)
                  }
                )
              }
            },
            offsetParent: function () {
              return this.map(function () {
                for (
                  var t = this.offsetParent;
                  t && bt.css(t, 'position') === 'static';

                ) { t = t.offsetParent }
                return t || ne
              })
            }
          }),
          bt.each(
            { scrollLeft: 'pageXOffset', scrollTop: 'pageYOffset' },
            function (t, e) {
              var n = e === 'pageYOffset'
              bt.fn[t] = function (i) {
                return Nt(
                  this,
                  function (t, i, r) {
                    var o
                    return (
                      bt.isWindow(t)
                        ? (o = t)
                        : t.nodeType === 9 && (o = t.defaultView),
                      void 0 === r
                        ? o
                          ? o[e]
                          : t[i]
                        : void (o
                          ? o.scrollTo(
                            n ? o.pageXOffset : r,
                            n ? r : o.pageYOffset
                          )
                          : (t[i] = r))
                    )
                  },
                  t,
                  i,
                  arguments.length
                )
              }
            }
          ),
          bt.each(['top', 'left'], function (t, e) {
            bt.cssHooks[e] = L(gt.pixelPosition, function (t, n) {
              if (n) {
                return (
                  (n = B(t, e)), he.test(n) ? bt(t).position()[e] + 'px' : n
                )
              }
            })
          }),
          bt.each({ Height: 'height', Width: 'width' }, function (t, e) {
            bt.each(
              { padding: 'inner' + t, content: e, '': 'outer' + t },
              function (n, i) {
                bt.fn[i] = function (r, o) {
                  var a = arguments.length && (n || typeof r !== 'boolean')
                  var s = n || (r === !0 || o === !0 ? 'margin' : 'border')
                  return Nt(
                    this,
                    function (e, n, r) {
                      var o
                      return bt.isWindow(e)
                        ? i.indexOf('outer') === 0
                          ? e['inner' + t]
                          : e.document.documentElement['client' + t]
                        : e.nodeType === 9
                          ? ((o = e.documentElement),
                          Math.max(
                            e.body['scroll' + t],
                            o['scroll' + t],
                            e.body['offset' + t],
                            o['offset' + t],
                            o['client' + t]
                          ))
                          : void 0 === r
                            ? bt.css(e, n, s)
                            : bt.style(e, n, r, s)
                    },
                    e,
                    a ? r : void 0,
                    a
                  )
                }
              }
            )
          }),
          bt.fn.extend({
            bind: function (t, e, n) {
              return this.on(t, null, e, n)
            },
            unbind: function (t, e) {
              return this.off(t, null, e)
            },
            delegate: function (t, e, n, i) {
              return this.on(e, t, n, i)
            },
            undelegate: function (t, e, n) {
              return arguments.length === 1
                ? this.off(t, '**')
                : this.off(e, t || '**', n)
            }
          }),
          (bt.holdReady = function (t) {
            t ? bt.readyWait++ : bt.ready(!0)
          }),
          (bt.isArray = Array.isArray),
          (bt.parseJSON = JSON.parse),
          (bt.nodeName = l),
          (i = []),
          (r = (function () {
            return bt
          }.apply(e, i))),
          !(void 0 !== r && (t.exports = r))
          var Ze = o.jQuery
          var Qe = o.$
          return (
            (bt.noConflict = function (t) {
              return (
                o.$ === bt && (o.$ = Qe),
                t && o.jQuery === bt && (o.jQuery = Ze),
                bt
              )
            }),
            a || (o.jQuery = o.$ = bt),
            bt
          )
        })
      }.call(e, n(8)(t)))
    },
    function (t, e, n) {
      'use strict'
      var i = n(6)
      var r = n(7)
      var o = r;
      (o.v1 = i), (o.v4 = r), (t.exports = o)
    },
    function (t, e) {
      'use strict'
      function n (t, e) {
        var n = e || 0
        var r = i
        return (
          r[t[n++]] +
          r[t[n++]] +
          r[t[n++]] +
          r[t[n++]] +
          '-' +
          r[t[n++]] +
          r[t[n++]] +
          '-' +
          r[t[n++]] +
          r[t[n++]] +
          '-' +
          r[t[n++]] +
          r[t[n++]] +
          '-' +
          r[t[n++]] +
          r[t[n++]] +
          r[t[n++]] +
          r[t[n++]] +
          r[t[n++]] +
          r[t[n++]]
        )
      }
      for (var i = [], r = 0; r < 256; ++r) { i[r] = (r + 256).toString(16).substr(1) }
      t.exports = n
    },
    function (t, e) {
      (function (e) {
        'use strict'
        var n
        var i = e.crypto || e.msCrypto
        if (i && i.getRandomValues) {
          var r = new Uint8Array(16)
          n = function () {
            return i.getRandomValues(r), r
          }
        }
        if (!n) {
          var o = new Array(16)
          n = function () {
            for (var t, e = 0; e < 16; e++) {
              (3 & e) === 0 && (t = 4294967296 * Math.random()),
              (o[e] = (t >>> ((3 & e) << 3)) & 255)
            }
            return o
          }
        }
        t.exports = n
      }.call(
        e,
        (function () {
          return this
        })()
      ))
    },
    function (t, e) {
      'use strict'
      function n (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 })
      var i = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var r = (function () {
        function t () {
          n(this, t),
          (this._keyStr =
                'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=')
        }
        return (
          i(t, [
            {
              key: '$',
              value: function (t) {
                var e = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/
                var n = /^(?:\s*(<[\w\W]+>)[^>]*|.([\w-]*))$/
                if (e.test(t)) {
                  var i = e.exec(t)
                  return document.getElementById(i[2])
                }
                if (n.test(t)) {
                  for (
                    var r = n.exec(t),
                      o = document.getElementsByTagName('*'),
                      a = [],
                      s = 0,
                      u = o.length;
                    s < u;
                    s++
                  ) {
                    o[s].className.match(
                      new RegExp('(\\s|^)' + r[2] + '(\\s|$)')
                    ) && a.push(o[s])
                  }
                  return a
                }
              }
            },
            {
              key: 'dateFormat',
              value: function (t, e) {
                var n = {
                  'M+': t.getMonth() + 1,
                  'd+': t.getDate(),
                  'h+': t.getHours(),
                  'm+': t.getMinutes(),
                  's+': t.getSeconds(),
                  'q+': Math.floor((t.getMonth() + 3) / 3),
                  S: t.getMilliseconds()
                };
                /(y+)/.test(e) &&
                    (e = e.replace(
                      RegExp.$1,
                      (t.getFullYear() + '').substr(4 - RegExp.$1.length)
                    ))
                for (var i in n) {
                  new RegExp('(' + i + ')').test(e) &&
                      (e = e.replace(
                        RegExp.$1,
                        RegExp.$1.length === 1
                          ? n[i]
                          : ('00' + n[i]).substr(('' + n[i]).length)
                      ))
                }
                return e
              }
            },
            {
              key: 'downloadFile',
              value: function (t, e) {
                var n = t
                t instanceof Blob || t instanceof File || (n = new Blob([t]))
                var i = window.URL.createObjectURL(n)
                var r = window.document.createElement('a');
                (r.href = i), (r.download = e)
                var o = document.createEvent('MouseEvents')
                o.initEvent('click', !0, !0), r.dispatchEvent(o)
              }
            },
            {
              key: 'createxmlDoc',
              value: function () {
                for (
                  var t,
                    e = [
                      'MSXML2.DOMDocument',
                      'MSXML2.DOMDocument.5.0',
                      'MSXML2.DOMDocument.4.0',
                      'MSXML2.DOMDocument.3.0',
                      'Microsoft.XmlDom'
                    ],
                    n = 0,
                    i = e.length;
                  n < i;
                  n++
                ) {
                  try {
                    t = new ActiveXObject(e[n])
                    break
                  } catch (e) {
                    t = document.implementation.createDocument('', '', null)
                    break
                  }
                }
                return (t.async = 'false'), t
              }
            },
            {
              key: 'parseXmlFromStr',
              value: function (t) {
                if (t === null || t === '') return null
                var e = this.createxmlDoc()
                if (
                  navigator.appName === 'Netscape' ||
                    navigator.appName === 'Opera'
                ) {
                  var n = new DOMParser()
                  e = n.parseFromString(t, 'text/xml')
                } else e.loadXML(t)
                return e
              }
            },
            {
              key: 'encode',
              value: function (t) {
                var e
                var n
                var i
                var r
                var o
                var a
                var s
                var u = ''
                var l = 0
                for (t = this._utf8_encode(t); l < t.length;) {
                  (e = t.charCodeAt(l++)),
                  (n = t.charCodeAt(l++)),
                  (i = t.charCodeAt(l++)),
                  (r = e >> 2),
                  (o = ((3 & e) << 4) | (n >> 4)),
                  (a = ((15 & n) << 2) | (i >> 6)),
                  (s = 63 & i),
                  isNaN(n) ? (a = s = 64) : isNaN(i) && (s = 64),
                  (u =
                        u +
                        this._keyStr.charAt(r) +
                        this._keyStr.charAt(o) +
                        this._keyStr.charAt(a) +
                        this._keyStr.charAt(s))
                }
                return u
              }
            },
            {
              key: 'decode',
              value: function (t) {
                var e
                var n
                var i
                var r
                var o
                var a
                var s
                var u = ''
                var l = 0
                for (t = t.replace(/[^A-Za-z0-9+\/=]/g, ''); l < t.length;) {
                  (r = this._keyStr.indexOf(t.charAt(l++))),
                  (o = this._keyStr.indexOf(t.charAt(l++))),
                  (a = this._keyStr.indexOf(t.charAt(l++))),
                  (s = this._keyStr.indexOf(t.charAt(l++))),
                  (e = (r << 2) | (o >> 4)),
                  (n = ((15 & o) << 4) | (a >> 2)),
                  (i = ((3 & a) << 6) | s),
                  (u += String.fromCharCode(e)),
                  a !== 64 && (u += String.fromCharCode(n)),
                  s !== 64 && (u += String.fromCharCode(i))
                }
                return (u = this._utf8_decode(u))
              }
            },
            {
              key: '_utf8_encode',
              value: function (t) {
                t = t.replace(/\r\n/g, '\n')
                for (var e = '', n = 0; n < t.length; n++) {
                  var i = t.charCodeAt(n)
                  i < 128
                    ? (e += String.fromCharCode(i))
                    : i > 127 && i < 2048
                      ? ((e += String.fromCharCode((i >> 6) | 192)),
                      (e += String.fromCharCode((63 & i) | 128)))
                      : ((e += String.fromCharCode((i >> 12) | 224)),
                      (e += String.fromCharCode(((i >> 6) & 63) | 128)),
                      (e += String.fromCharCode((63 & i) | 128)))
                }
                return e
              }
            },
            {
              key: '_utf8_decode',
              value: function (t) {
                for (var e = '', n = 0, i = 0, r = 0; n < t.length;) {
                  if (((i = t.charCodeAt(n)), i < 128)) { (e += String.fromCharCode(i)), n++ } else if (i > 191 && i < 224) {
                    (r = t.charCodeAt(n + 1)),
                    (e += String.fromCharCode(((31 & i) << 6) | (63 & r))),
                    (n += 2)
                  } else {
                    r = t.charCodeAt(n + 1)
                    var o = t.charCodeAt(n + 2);
                    (e += String.fromCharCode(
                      ((15 & i) << 12) | ((63 & r) << 6) | (63 & o)
                    )),
                    (n += 3)
                  }
                }
                return e
              }
            },
            {
              key: 'isFirefox',
              value: function () {
                var t = !1
                var e = navigator.userAgent.toLowerCase()
                var n = ''
                var i = -1
                return (
                  e.match(/firefox\/([\d.]+)/) &&
                      ((n = e.match(/firefox\/([\d.]+)/)[1]),
                      (i = parseInt(n.split('.')[0], 10)),
                      i > -1 && (t = !0)),
                  t
                )
              }
            }
          ]),
          t
        )
      })()
      e.oTool = new r()
    },
    function (t, e, n) {
      'use strict'
      function i (t, e, n) {
        var i = (e && n) || 0
        var r = e || []
        t = t || {}
        var a = void 0 !== t.clockseq ? t.clockseq : u
        var c = void 0 !== t.msecs ? t.msecs : new Date().getTime()
        var h = void 0 !== t.nsecs ? t.nsecs : f + 1
        var d = c - l + (h - f) / 1e4
        if (
          (d < 0 && void 0 === t.clockseq && (a = (a + 1) & 16383),
          (d < 0 || c > l) && void 0 === t.nsecs && (h = 0),
          h >= 1e4)
        ) { throw new Error("uuid.v1(): Can't create more than 10M uuids/sec") }
        (l = c), (f = h), (u = a), (c += 122192928e5)
        var p = (1e4 * (268435455 & c) + h) % 4294967296;
        (r[i++] = (p >>> 24) & 255),
        (r[i++] = (p >>> 16) & 255),
        (r[i++] = (p >>> 8) & 255),
        (r[i++] = 255 & p)
        var y = (c / 4294967296 * 1e4) & 268435455;
        (r[i++] = (y >>> 8) & 255),
        (r[i++] = 255 & y),
        (r[i++] = ((y >>> 24) & 15) | 16),
        (r[i++] = (y >>> 16) & 255),
        (r[i++] = (a >>> 8) | 128),
        (r[i++] = 255 & a)
        for (var m = t.node || s, v = 0; v < 6; ++v) r[i + v] = m[v]
        return e || o(r)
      }
      var r = n(4)
      var o = n(3)
      var a = r()
      var s = [1 | a[0], a[1], a[2], a[3], a[4], a[5]]
      var u = 16383 & ((a[6] << 8) | a[7])
      var l = 0
      var f = 0
      t.exports = i
    },
    function (t, e, n) {
      'use strict'
      function i (t, e, n) {
        var i = (e && n) || 0
        typeof t === 'string' &&
          ((e = t == 'binary' ? new Array(16) : null), (t = null)),
        (t = t || {})
        var a = t.random || (t.rng || r)()
        if (((a[6] = (15 & a[6]) | 64), (a[8] = (63 & a[8]) | 128), e)) { for (var s = 0; s < 16; ++s) e[i + s] = a[s] }
        return e || o(a)
      }
      var r = n(4)
      var o = n(3)
      t.exports = i
    },
    function (t, e) {
      'use strict'
      t.exports = function (t) {
        return (
          t.webpackPolyfill ||
            ((t.deprecate = function () {}),
            (t.paths = []),
            (t.children = []),
            (t.webpackPolyfill = 1)),
          t
        )
      }
    },
    function (t, e, n) {
      'use strict'
      function i (t) {
        return t && t.__esModule ? t : { default: t }
      }
      function r (t, e) {
        if (!t) {
          throw new ReferenceError(
            "this hasn't been initialised - super() hasn't been called"
          )
        }
        return !e || (typeof e !== 'object' && typeof e !== 'function') ? t : e
      }
      function o (t, e) {
        if (typeof e !== 'function' && e !== null) {
          throw new TypeError(
            'Super expression must either be null or a function, not ' +
              typeof e
          )
        }
        (t.prototype = Object.create(e && e.prototype, {
          constructor: {
            value: t,
            enumerable: !1,
            writable: !0,
            configurable: !0
          }
        })),
        e &&
            (Object.setPrototypeOf
              ? Object.setPrototypeOf(t, e)
              : (t.__proto__ = e))
      }
      function a (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 }),
      (e.ESCanvas = void 0)
      var s = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var u = n(1)
      var l = i(u)
      var f = (function () {
        function t () {
          i[f].clearRect(0, 0, i.m_iCanvasWidth, i.m_iCanvasHeight)
          for (var t = 0, e = i[c].length; t < e; t++) i[c][t].draw()
        }
        function e (t) {
          var e = i[c].length
          e < i[p] && i[c].push(t)
        }
        function n () {
          function n () {
            for (var t = -1, e = 0, n = i[c].length; e < n; e++) {
              if (i[c][e].m_bChoosed) {
                t = e
                break
              }
            }
            return t
          }
          var r = !1
          var o = 0
          var a = 0
          var s = 'draw'
          var l = null;
          (i[u][0].oncontextmenu = function () {
            return !1
          }),
          (i[u][0].onselectstart = function () {
            return !1
          }),
          i[u].unbind(),
          i[u].bind('mousedown', function (f) {
            if (f.button === 2) {
              i[m] &&
                    l &&
                    l.m_aPoint.length >= l.m_iMinClosed - 1 &&
                    ((l.m_bClosed = !0),
                    (i[m] = !1),
                    l.setPointInfo(l.m_aPoint),
                    e(l),
                    t(),
                    (r = !1),
                    i[g] || (i[h] = !1))
            } else if (f.button === 0) {
              if (((o = f.offsetX), (a = f.offsetY), (s = 'draw'), !i[m])) {
                var y = n()
                if (
                  (y !== -1 &&
                        i[c][y].inArc(f.offsetX, f.offsetY, 5) &&
                        (s = 'stretch'),
                  s !== 'stretch')
                ) {
                  for (var S = 0, b = i[c].length; S < b; S++) {
                    i[c][S].inShape(f.offsetX, f.offsetY)
                      ? ((i[c][S].m_bChoosed = !0),
                      i[c][S].getMouseDownPoints(f.offsetX, f.offsetY),
                      (s = 'drag'))
                      : (i[c][S].m_bChoosed = !1)
                  }
                }
                s === 'drag'
                  ? (i[u][0].style.cursor = 'move')
                  : (i[u][0].style.cursor = 'default')
              }
              if (s === 'draw' && i[h]) {
                if (i[p] <= i[c].length && i[d] !== 'Grid') return
                i[d] === 'Rect'
                  ? (l = new w())
                  : i[d] === 'Grid'
                    ? i[c].length === 0 && ((l = new P()), e(l))
                    : i[d] === 'Polygon' &&
                          (i[m] ||
                            ((i[m] = !0),
                            (l = new x()),
                            (l.m_szId = i[v].szId || ''),
                            (l.m_szTips = i[v].szTips || ''),
                            (l.m_iMinClosed = i[v].iMinClosed || 3),
                            (l.m_iMaxPointNum = i[v].iMaxPointNum || 11),
                            (l.m_iPolygonType = i[v].iPolygonType),
                            (l.m_szDrawColor = i[v].szDrawColor),
                            (l.m_szFillColor = i[v].szFillColor),
                            (l.m_iTranslucent = i[v].iTranslucent)),
                          l.m_iPolygonType === 1 &&
                            (l.addPoint(o, a),
                            l.m_aPoint.length === l.m_iMaxPointNum &&
                              ((l.m_bClosed = !0),
                              (i[m] = !1),
                              e(l),
                              t(),
                              (r = !1),
                              i[g] || (i[h] = !1))))
              }
              r = !0
            }
          }),
          i[u].bind('mousemove', function (e) {
            if (i[m]) {
              i[h] &&
                    r &&
                    (i[d] === 'Polygon' &&
                      l.m_iPolygonType === 0 &&
                      (l.m_bClosed = !0),
                    t(),
                    l.move(e.offsetX, e.offsetY, o, a))
            } else {
              var u = n()
              u > -1
                ? r &&
                      (s === 'drag'
                        ? i[c][u].drag(e.offsetX, e.offsetY)
                        : s === 'stretch' &&
                          i[c][u].stretch(e.offsetX, e.offsetY))
                : i[h] &&
                      r &&
                      (i[d] === 'Rect'
                        ? l.move([[o, a], [e.offsetX, e.offsetY]])
                        : i[d] === 'Grid' &&
                          i[c][0].move(o, a, e.offsetX, e.offsetY))
            }
          }),
          i[u].bind('mouseup', function (n) {
            if (
              ((i[u][0].style.cursor = 'default'),
              l !== null && typeof l !== 'undefined' && s === 'draw')
            ) {
              if (i[d] === 'Rect') {
                if (
                  (Math.abs(n.offsetX - o) > 2 &&
                        Math.abs(n.offsetY - a) > 2 &&
                        (e(l), i[g] || (i[h] = !1)),
                  i[S])
                ) {
                  var f = { startPos: [], endPos: [] }
                  n.offsetX > o && n.offsetY > a
                    ? ((f.startPos = l.m_aPoint[0] || [
                      n.offsetX,
                      n.offsetY
                    ]),
                    (f.endPos = l.m_aPoint[2] || [n.offsetX, n.offsetY]))
                    : ((f.startPos = l.m_aPoint[2] || [
                      n.offsetX,
                      n.offsetY
                    ]),
                    (f.endPos = l.m_aPoint[0] || [n.offsetX, n.offsetY])),
                  i[S] && i[S](f),
                  (l = null),
                  i.clearAllShape()
                }
              } else {
                i[d] === 'Polygon' &&
                      l.m_iPolygonType === 0 &&
                      i[m] &&
                      Math.abs(n.offsetX - o) > 2 &&
                      Math.abs(n.offsetY - a) > 2 &&
                      (e(l), (i[m] = !1), i[g] || (i[h] = !1))
              }
            }
            (r = !!i[m]), t()
          }),
          i[u].bind('dblclick', function () {
            i[h] &&
                  i[d] === 'Grid' &&
                  ((i[c][0].m_szGridMap =
                    'fffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffcfffffc'),
                  t())
          }),
          i[u].bind('mouseout', function () {
            (i[u][0].style.cursor = 'default'), (r = !!i[m])
          })
        }
        if (typeof Symbol !== 'undefined') {
          var i = null
          var u = Symbol('CANVAS')
          var f = Symbol('CONTEXT')
          var c = Symbol('SHAPES')
          var h = Symbol('DRAWSTATUS')
          var d = Symbol('SHAPETYPE')
          var p = Symbol('MAXSHAPENUMSUPPORT')
          var y = Symbol('SHAPESTYLE')
          var m = Symbol('POLYGONDRAWING')
          var v = Symbol('CURRENTSHAPEINFO')
          var g = Symbol('DRAWSHAPEMULTIONETIME')
          var S = Symbol('EVENTCALLBACK')
          var b = (function () {
            function e () {
              a(this, e),
              (this.m_szId = ''),
              (this.m_aPoint = []),
              (this.m_bChoosed = !1),
              (this.m_szDrawColor = i[y].szDrawColor),
              (this.m_szFillColor = i[y].szFillColor),
              (this.m_iTranslucent = i[y].iTranslucent),
              (this.m_iIndexChoosePoint = -1),
              (this.m_iDriftStartX = 0),
              (this.m_iDriftStartY = 0),
              (this.m_oEdgePoints = {
                top: { x: 0, y: 0 },
                left: { x: 0, y: 0 },
                right: { x: 0, y: 0 },
                bottom: { x: 0, y: 0 }
              }),
              (this.m_szTips = ''),
              (this.m_iEditType = 0),
              (this.m_iMinClosed = 3),
              (this.m_iMaxPointNum = 11),
              (this.m_bClosed = !1)
            }
            return (
              s(e, [
                { key: 'draw', value: function () {} },
                {
                  key: 'drag',
                  value: function (e, n) {
                    var r = this.m_aPoint.length
                    var o = 0
                    for (o = 0; o < r; o++) {
                      if (
                        this.m_aPoint[o][0] + e - this.m_iDriftStartX >
                              i.m_iCanvasWidth ||
                            this.m_aPoint[o][1] + n - this.m_iDriftStartY >
                              i.m_iCanvasHeight ||
                            this.m_aPoint[o][0] + e - this.m_iDriftStartX < 0 ||
                            this.m_aPoint[o][1] + n - this.m_iDriftStartY < 0
                      ) {
                        return (
                          (this.m_iDriftStartX = e),
                          void (this.m_iDriftStartY = n)
                        )
                      }
                    }
                    for (o = 0; o < r; o++) {
                      (this.m_aPoint[o][0] =
                            this.m_aPoint[o][0] + e - this.m_iDriftStartX),
                      (this.m_aPoint[o][1] =
                              this.m_aPoint[o][1] + n - this.m_iDriftStartY)
                    }
                    (this.m_iDriftStartX = e),
                    (this.m_iDriftStartY = n),
                    this.setPointInfo(this.m_aPoint),
                    t()
                  }
                },
                {
                  key: 'stretch',
                  value: function (e, n) {
                    this.m_iEditType === 0 &&
                          (this.m_iIndexChoosePoint !== -1 &&
                            ((this.m_aPoint[this.m_iIndexChoosePoint][0] = e),
                            (this.m_aPoint[this.m_iIndexChoosePoint][1] = n)),
                          this.setPointInfo(this.m_aPoint),
                          t())
                  }
                },
                {
                  key: 'inShape',
                  value: function (t, e) {
                    for (
                      var n = !1,
                        i = this.m_aPoint.length,
                        r = 0,
                        o = i - 1;
                      r < i;
                      o = r++
                    ) {
                      this.m_aPoint[r][1] > e != this.m_aPoint[o][1] > e &&
                            t <
                              (this.m_aPoint[o][0] - this.m_aPoint[r][0]) *
                                (e - this.m_aPoint[r][1]) /
                                (this.m_aPoint[o][1] - this.m_aPoint[r][1]) +
                                this.m_aPoint[r][0] &&
                            (n = !n)
                    }
                    return n
                  }
                },
                {
                  key: 'inArc',
                  value: function (t, e, n) {
                    for (
                      var i = !1, r = 0, o = this.m_aPoint.length;
                      r < o;
                      r++
                    ) {
                      var a = Math.sqrt(
                        (t - this.m_aPoint[r][0]) *
                              (t - this.m_aPoint[r][0]) +
                              (e - this.m_aPoint[r][1]) *
                                (e - this.m_aPoint[r][1])
                      )
                      if (a < n) {
                        (i = !0), (this.m_iIndexChoosePoint = r)
                        break
                      }
                    }
                    return i
                  }
                },
                {
                  key: 'getMouseDownPoints',
                  value: function (t, e) {
                    (this.m_iDriftStartX = t), (this.m_iDriftStartY = e)
                  }
                },
                {
                  key: 'getPointInfo',
                  value: function () {
                    return this.m_aPoint
                  }
                },
                {
                  key: 'setPointInfo',
                  value: function (t) {
                    t !== null &&
                          typeof t !== 'undefined' &&
                          t.length > 0 &&
                          ((this.m_aPoint = t), this.setEdgePoints(t))
                  }
                },
                {
                  key: 'addPoint',
                  value: function (t, e) {
                    this.m_aPoint.length < this.m_iMaxPointNum &&
                          this.m_aPoint.push([t, e]),
                    this.m_aPoint.length === this.m_iMaxPointNum &&
                            this.setPointInfo(this.m_aPoint)
                  }
                },
                {
                  key: 'setEdgePoints',
                  value: function (t) {
                    for (var e = 0, n = t.length; e < n; e++) {
                      e === 0
                        ? ((this.m_oEdgePoints.top.x = t[e][0]),
                        (this.m_oEdgePoints.top.y = t[e][1]),
                        (this.m_oEdgePoints.left.x = t[e][0]),
                        (this.m_oEdgePoints.left.y = t[e][1]),
                        (this.m_oEdgePoints.right.x = t[e][0]),
                        (this.m_oEdgePoints.right.y = t[e][1]),
                        (this.m_oEdgePoints.bottom.x = t[e][0]),
                        (this.m_oEdgePoints.bottom.y = t[e][1]))
                        : (t[e][1] < this.m_oEdgePoints.top.y &&
                                ((this.m_oEdgePoints.top.x = t[e][0]),
                                (this.m_oEdgePoints.top.y = t[e][1])),
                        t[e][0] > this.m_oEdgePoints.right.x &&
                                ((this.m_oEdgePoints.right.x = t[e][0]),
                                (this.m_oEdgePoints.right.y = t[e][1])),
                        t[e][1] > this.m_oEdgePoints.bottom.y &&
                                ((this.m_oEdgePoints.bottom.x = t[e][0]),
                                (this.m_oEdgePoints.bottom.y = t[e][1])),
                        t[e][0] < this.m_oEdgePoints.left.x &&
                                ((this.m_oEdgePoints.left.x = t[e][0]),
                                (this.m_oEdgePoints.left.y = t[e][1])))
                    }
                  }
                }
              ]),
              e
            )
          })()
          var w = (function (e) {
            function n () {
              a(this, n)
              var t = r(
                this,
                (n.__proto__ || Object.getPrototypeOf(n)).call(this)
              )
              return (t.m_szType = 'Rect'), t
            }
            return (
              o(n, e),
              s(n, [
                {
                  key: 'setPointInfo',
                  value: function (t) {
                    if (t !== null && typeof t !== 'undefined') {
                      for (
                        var e = t[0][0],
                          n = t[0][1],
                          i = t[0][0],
                          r = t[0][1],
                          o = 0,
                          a = t.length;
                        o < a;
                        o++
                      ) {
                        e > t[o][0] && (e = t[o][0]),
                        n > t[o][1] && (n = t[o][1]),
                        i < t[o][0] && (i = t[o][0]),
                        r < t[o][1] && (r = t[o][1])
                      }
                      this.m_aPoint = [[e, n], [i, n], [i, r], [e, r]]
                    }
                  }
                },
                {
                  key: 'draw',
                  value: function () {
                    (i[f].fillStyle = this.m_szFillColor),
                    (i[f].strokeStyle = this.m_szDrawColor)
                    var t = this.m_aPoint[0][0]
                    var e = this.m_aPoint[0][1]
                    var n = this.m_aPoint[2][0] - t
                    var r = this.m_aPoint[2][1] - e
                    if (
                      ((i[f].globalAlpha = this.m_iTranslucent),
                      i[f].fillRect(t, e, n, r),
                      (i[f].globalAlpha = 1),
                      i[f].fillText(
                        this.m_szTips,
                        (t + this.m_aPoint[2][0]) / 2,
                        (e + this.m_aPoint[2][1]) / 2
                      ),
                      this.m_bChoosed)
                    ) {
                      var o = Math.round(n / 2)
                      var a = Math.round(r / 2)
                      if (this.m_iEditType === 0) {
                        for (
                          var s = [
                              t,
                              t + o,
                              t + n,
                              t,
                              t + n,
                              t,
                              t + o,
                              t + n
                            ],
                            u = [
                              e,
                              e,
                              e,
                              e + a,
                              e + a,
                              e + r,
                              e + r,
                              e + r
                            ],
                            l = 0;
                          l < 8;
                          l++
                        ) {
                          i[f].beginPath(),
                          i[f].arc(s[l], u[l], 3, 0, 360, !1),
                          (i[f].fillStyle = this.m_szDrawColor),
                          i[f].closePath(),
                          i[f].fill()
                        }
                      }
                    }
                    i[f].strokeRect(t, e, n, r)
                  }
                },
                {
                  key: 'stretch',
                  value: function (e, n) {
                    this.m_iEditType === 0 &&
                          (this.m_iIndexChoosePoint === 0
                            ? e < this.m_aPoint[2][0] &&
                              n < this.m_aPoint[2][1] &&
                              ((this.m_aPoint[0][0] = e),
                              (this.m_aPoint[0][1] = n),
                              (this.m_aPoint[3][0] = e),
                              (this.m_aPoint[1][1] = n))
                            : this.m_iIndexChoosePoint === 1
                              ? n < this.m_aPoint[2][1] &&
                                ((this.m_aPoint[0][1] = n),
                                (this.m_aPoint[1][1] = n))
                              : this.m_iIndexChoosePoint === 2
                                ? e > this.m_aPoint[3][0] &&
                                  n < this.m_aPoint[3][1] &&
                                  ((this.m_aPoint[1][0] = e),
                                  (this.m_aPoint[1][1] = n),
                                  (this.m_aPoint[2][0] = e),
                                  (this.m_aPoint[0][1] = n))
                                : this.m_iIndexChoosePoint === 3
                                  ? e < this.m_aPoint[2][0] &&
                                    ((this.m_aPoint[0][0] = e),
                                    (this.m_aPoint[3][0] = e))
                                  : this.m_iIndexChoosePoint === 4
                                    ? e > this.m_aPoint[0][0] &&
                                      ((this.m_aPoint[1][0] = e),
                                      (this.m_aPoint[2][0] = e))
                                    : this.m_iIndexChoosePoint === 5
                                      ? e < this.m_aPoint[1][0] &&
                                        n > this.m_aPoint[1][1] &&
                                        ((this.m_aPoint[3][0] = e),
                                        (this.m_aPoint[3][1] = n),
                                        (this.m_aPoint[0][0] = e),
                                        (this.m_aPoint[2][1] = n))
                                      : this.m_iIndexChoosePoint === 6
                                        ? n > this.m_aPoint[1][1] &&
                                          ((this.m_aPoint[2][1] = n),
                                          (this.m_aPoint[3][1] = n))
                                        : this.m_iIndexChoosePoint === 7 &&
                                          e > this.m_aPoint[0][0] &&
                                          n > this.m_aPoint[0][1] &&
                                          ((this.m_aPoint[2][0] = e),
                                          (this.m_aPoint[2][1] = n),
                                          (this.m_aPoint[1][0] = e),
                                          (this.m_aPoint[3][1] = n)),
                          t())
                  }
                },
                {
                  key: 'move',
                  value: function (e) {
                    t(), (this.m_bChoosed = !0)
                    var n = e[0][0]
                    var i = e[0][1]
                    var r = e[1][0]
                    var o = e[1][1]
                    this.setPointInfo([[n, i], [r, i], [r, o], [n, o]]),
                    this.draw()
                  }
                },
                {
                  key: 'inArc',
                  value: function (t, e, n) {
                    for (
                      var i = this.m_aPoint[0][0],
                        r = this.m_aPoint[0][1],
                        o = this.m_aPoint[2][0] - i,
                        a = this.m_aPoint[2][1] - r,
                        s = Math.round(o / 2),
                        u = Math.round(a / 2),
                        l = [i, i + s, i + o, i, i + o, i, i + s, i + o],
                        f = [r, r, r, r + u, r + u, r + a, r + a, r + a],
                        c = 0;
                      c < 8;
                      c++
                    ) {
                      var h = Math.sqrt(
                        (t - l[c]) * (t - l[c]) + (e - f[c]) * (e - f[c])
                      )
                      if (h < n) return (this.m_iIndexChoosePoint = c), !0
                    }
                    return !1
                  }
                }
              ]),
              n
            )
          })(b)
          var _ = (function (e) {
            function n (t, e) {
              a(this, n)
              var i = r(
                this,
                (n.__proto__ || Object.getPrototypeOf(n)).call(this)
              )
              return (
                (i.m_szType = 'RectOSD'),
                (i.m_szOSDType = 'overlay-date'),
                (i.m_szText = t || ''),
                (i.m_szEnabled = e || ''),
                (i.m_szDateStyle = ''),
                (i.m_szClockType = ''),
                (i.m_szDisplayWeek = ''),
                (i.m_szId = ''),
                i
              )
            }
            return (
              o(n, e),
              s(n, [
                {
                  key: 'draw',
                  value: function () {
                    if (this.m_szEnabled === 'true') {
                      var t = this.m_aPoint[0][0]
                      var e = this.m_aPoint[0][1]
                      var n = this.m_aPoint[2][0] - t
                      var r = this.m_aPoint[2][1] - e
                      i[f].beginPath(),
                      (i[f].strokeStyle = this.m_szDrawColor),
                      (i[f].globalAlpha = 1),
                      i[f].rect(t, e, n, r),
                      (i[f].font = '15px serif'),
                      i[f].strokeText(this.m_szText, t, e + 15),
                      i[f].stroke()
                    }
                  }
                },
                {
                  key: 'drag',
                  value: function (e, n) {
                    var r = this.m_aPoint.length
                    var o = 0
                    for (o = 0; o < r; o++) {
                      if (
                        this.m_aPoint[o][1] + n - this.m_iDriftStartY >
                              i.m_iCanvasHeight ||
                            this.m_aPoint[o][0] + e - this.m_iDriftStartX < 0 ||
                            this.m_aPoint[o][1] + n - this.m_iDriftStartY < 0
                      ) {
                        return (
                          (this.m_iDriftStartX = e),
                          void (this.m_iDriftStartY = n)
                        )
                      }
                    }
                    for (o = 0; o < r; o++) {
                      (this.m_aPoint[o][0] =
                            this.m_aPoint[o][0] + e - this.m_iDriftStartX),
                      (this.m_aPoint[o][1] =
                              this.m_aPoint[o][1] + n - this.m_iDriftStartY)
                    }
                    (this.m_iDriftStartX = e),
                    (this.m_iDriftStartY = n),
                    this.setEdgePoints(this.m_aPoint),
                    t()
                  }
                },
                { key: 'stretch', value: function () {} }
              ]),
              n
            )
          })(b)
          var P = (function (t) {
            function e () {
              a(this, e)
              var t = r(
                this,
                (e.__proto__ || Object.getPrototypeOf(e)).call(this)
              )
              return (
                (t.m_szType = 'Grid'),
                (t.m_iGridColNum = 22),
                (t.m_iGridRowNum = 18),
                (t.m_szGridMap = ''),
                (t.m_aAddGridMap = []),
                t
              )
            }
            return (
              o(e, t),
              s(e, [
                {
                  key: 'draw',
                  value: function () {
                    for (
                      var t = i.m_iCanvasWidth / this.m_iGridColNum,
                        e = i.m_iCanvasHeight / this.m_iGridRowNum,
                        n = '',
                        r = 0;
                      r < this.m_iGridRowNum;
                      r++
                    ) {
                      for (
                        var o = this.m_szGridMap.substring(
                            6 * r,
                            6 * r + 6
                          ),
                          a = parseInt('f' + o, 16)
                            .toString(2)
                            .split('')
                            .slice(4),
                          s = '',
                          u = 0;
                        u < this.m_iGridColNum;
                        u++
                      ) {
                        var l = ''
                        a[u] === '1'
                          ? ((i[f].strokeStyle = this.m_szDrawColor),
                          (i[f].globalAlpha = 1),
                          i[f].strokeRect(t * u, e * r, t, e),
                          (l = '1'))
                          : (l = '0'),
                        this.m_aAddGridMap.length &&
                                this.m_aAddGridMap[r][u] === 1 &&
                                ((i[f].strokeStyle = this.m_szDrawColor),
                                i[f].strokeRect(t * u, e * r, t, e),
                                (l = '1')),
                        (s += l)
                      }
                      n += parseInt('1111' + s + '00', 2)
                        .toString(16)
                        .substring(1)
                    }
                    this.m_szGridMap = n
                  }
                },
                {
                  key: 'move',
                  value: function (t, e, n, r) {
                    var o = i.m_iCanvasWidth / this.m_iGridColNum
                    var a = i.m_iCanvasHeight / this.m_iGridRowNum
                    var s = Math.floor(t / o)
                    var u = Math.floor(e / a)
                    var l = Math.floor(Math.abs(n - t) / o)
                    var f = Math.floor(Math.abs(r - e) / a)
                    var c = 1
                    var h = 1;
                    (c = n - t > 0 ? 1 : -1), (h = r - e > 0 ? 1 : -1)
                    for (var d = [], p = 0; p < this.m_iGridRowNum; p++) {
                      d[p] = []
                      for (var y = 0; y < this.m_iGridColNum; y++) {
                        c === 1
                          ? h === 1
                            ? p >= u && p <= u + f && y >= s && y <= s + l
                              ? (d[p][y] = 1)
                              : (d[p][y] = 0)
                            : p <= u && p >= u - f && y >= s && y <= s + l
                              ? (d[p][y] = 1)
                              : (d[p][y] = 0)
                          : h === 1
                            ? p >= u && p <= u + f && y <= s && y >= s - l
                              ? (d[p][y] = 1)
                              : (d[p][y] = 0)
                            : p <= u && p >= u - f && y <= s && y >= s - l
                              ? (d[p][y] = 1)
                              : (d[p][y] = 0)
                      }
                    }
                    (this.m_aAddGridMap = d), this.draw()
                  }
                }
              ]),
              e
            )
          })(b)
          var C = (function (t) {
            function e () {
              a(this, e)
              var t = r(
                this,
                (e.__proto__ || Object.getPrototypeOf(e)).call(this)
              )
              return (
                (t.m_szType = 'Line'),
                (t.m_iLineType = 0),
                (t.m_iDirection = 0),
                (t.m_iArrowType = 0),
                (t.m_aCrossArrowPoint = []),
                t
              )
            }
            return (
              o(e, t),
              s(e, [
                {
                  key: 'draw',
                  value: function () {
                    this.m_iLineType === 0
                      ? this.drawNormalLine()
                      : this.m_iLineType === 1
                        ? this.drawArrowLine()
                        : this.m_iLineType === 3
                          ? this.drawCrossLine()
                          : this.m_iLineType === 4 && this.drawLineCount()
                  }
                },
                {
                  key: 'drawNormalLine',
                  value: function () {
                    if (
                      ((i[f].globalAlpha = 1), this.m_aPoint.length > 0)
                    ) {
                      i[f].beginPath(),
                      (i[f].strokeStyle = this.m_szDrawColor),
                      (i[f].lineWidth = 2),
                      i[f].moveTo(
                        this.m_aPoint[0][0],
                        this.m_aPoint[0][1]
                      )
                      for (var t = 1, e = this.m_aPoint.length; t < e; t++) {
                        i[f].lineTo(
                          this.m_aPoint[t][0],
                          this.m_aPoint[t][1]
                        )
                      }
                      if ((i[f].stroke(), this.m_bChoosed)) {
                        for (
                          var n = 0, r = this.m_aPoint.length;
                          n < r;
                          n++
                        ) {
                          i[f].beginPath(),
                          (i[f].fillStyle = this.m_szDrawColor),
                          i[f].arc(
                            this.m_aPoint[n][0],
                            this.m_aPoint[n][1],
                            3,
                            0,
                            2 * Math.PI,
                            !0
                          ),
                          i[f].closePath(),
                          i[f].fill()
                        }
                      }
                      this.m_szTips !== '' &&
                            ((i[f].strokeStyle = this.m_szDrawColor),
                            i[f].fillText(
                              this.m_szTips,
                              this.m_aPoint[0][0] + 10,
                              this.m_aPoint[0][1] + 4
                            ))
                    }
                  }
                },
                {
                  key: 'drawArrowLine',
                  value: function (t, e, n, r, o, a, s, u) {
                    (a = typeof a !== 'undefined' ? a : 30),
                    (s = typeof s !== 'undefined' ? s : 10),
                    (u = typeof u !== 'undefined' ? u : 1)
                    var l = 180 * Math.atan2(n - o, e - r) / Math.PI
                    var c = (l + a) * Math.PI / 180
                    var h = (l - a) * Math.PI / 180
                    var d = s * Math.cos(c)
                    var p = s * Math.sin(c)
                    var y = s * Math.cos(h)
                    var m = s * Math.sin(h)
                    i[f].save(), i[f].beginPath()
                    var v = e - d
                    var g = n - p
                    i[f].moveTo(v, g),
                    i[f].lineTo(e, n),
                    (v = e - y),
                    (g = n - m),
                    i[f].lineTo(v, g),
                    i[f].moveTo(e, n),
                    i[f].lineTo(r, o),
                    t === 1 &&
                            ((v = r + d),
                            (g = o + p),
                            i[f].moveTo(v, g),
                            i[f].lineTo(r, o),
                            (v = r + y),
                            (g = o + m),
                            i[f].lineTo(v, g)),
                    (i[f].strokeStyle = this.m_szDrawColor),
                    (i[f].lineWidth = u),
                    i[f].stroke(),
                    i[f].restore()
                  }
                },
                {
                  key: 'drawCrossLine',
                  value: function () {
                    this.drawNormalLine()
                    var t = (this.m_aPoint[0][0] + this.m_aPoint[1][0]) / 2
                    var e = (this.m_aPoint[0][1] + this.m_aPoint[1][1]) / 2
                    var n =
                            180 *
                            Math.atan2(
                              e - this.m_aPoint[0][1],
                              t - this.m_aPoint[0][0]
                            ) /
                            Math.PI
                    var r = (n + 90) * Math.PI / 180
                    var o = (n - 90) * Math.PI / 180
                    var a = 25 * Math.cos(r)
                    var s = 25 * Math.sin(r)
                    var u = 25 * Math.cos(o)
                    var l = 25 * Math.sin(o)
                    var c = 0
                    var h = 0;
                    (c = t - a), (h = e - s)
                    var d = 0
                    var p = 0
                    this.m_iDirection === 0
                      ? ((d = -10), (p = -15))
                      : this.m_iDirection === 1
                        ? ((d = 10), (p = 10))
                        : ((d = 10), (p = -15)),
                    this.m_iDirection !== 0 &&
                            this.drawArrowLine(0, c, h, t, e),
                    (i[f].strokeStyle = this.m_szDrawColor),
                    (i[f].font = '8px'),
                    i[f].strokeText('A', c + d, h + 4),
                    (c = t - u),
                    (h = e - l),
                    this.m_iDirection !== 1 &&
                            this.drawArrowLine(0, c, h, t, e),
                    (i[f].strokeStyle = this.m_szDrawColor),
                    (i[f].font = '8px'),
                    i[f].strokeText('B', c + p, h + 4)
                  }
                },
                {
                  key: 'drawLineCount',
                  value: function () {
                    this.drawNormalLine()
                    var t = (this.m_aPoint[0][0] + this.m_aPoint[1][0]) / 2
                    var e = (this.m_aPoint[0][1] + this.m_aPoint[1][1]) / 2
                    var n =
                            180 *
                            Math.atan2(
                              e - this.m_aPoint[0][1],
                              t - this.m_aPoint[0][0]
                            ) /
                            Math.PI
                    var i = (n + 90) * Math.PI / 180
                    var r = (n - 90) * Math.PI / 180
                    var o = 25 * Math.cos(i)
                    var a = 25 * Math.sin(i)
                    var s = 25 * Math.cos(r)
                    var u = 25 * Math.sin(r)
                    var l = 0
                    var f = 0;
                    (l = t - o),
                    (f = e - a),
                    this.m_iArrowType === 1
                      ? ((l = t - s),
                      (f = e - u),
                      this.drawArrowLine(0, l, f, t, e))
                      : this.m_iArrowType === 0 &&
                              this.drawArrowLine(0, l, f, t, e),
                    (this.m_aCrossArrowPoint = [[t, e], [l, f]])
                  }
                },
                {
                  key: 'inShape',
                  value: function (t, e) {
                    for (
                      var n = !1, i = 0, r = this.m_aPoint.length - 1;
                      i < r;
                      i++
                    ) {
                      var o = Math.sqrt(
                        (this.m_aPoint[i + 1][0] - this.m_aPoint[i][0]) *
                                (this.m_aPoint[i + 1][0] -
                                  this.m_aPoint[i][0]) +
                                (this.m_aPoint[i + 1][1] -
                                  this.m_aPoint[i][1]) *
                                  (this.m_aPoint[i + 1][1] -
                                    this.m_aPoint[i][1])
                      )
                      var a = Math.sqrt(
                        (t - this.m_aPoint[i][0]) *
                                (t - this.m_aPoint[i][0]) +
                                (e - this.m_aPoint[i][1]) *
                                  (e - this.m_aPoint[i][1])
                      )
                      var s = Math.sqrt(
                        (t - this.m_aPoint[i + 1][0]) *
                                (t - this.m_aPoint[i + 1][0]) +
                                (e - this.m_aPoint[i + 1][1]) *
                                  (e - this.m_aPoint[i + 1][1])
                      )
                      a + s - o < 1 && (n = !0)
                    }
                    return n
                  }
                }
              ]),
              e
            )
          })(b)
          var x = (function (e) {
            function n () {
              a(this, n)
              var t = r(
                this,
                (n.__proto__ || Object.getPrototypeOf(n)).call(this)
              )
              return (t.m_szType = 'Polygon'), (t.m_iPolygonType = 1), t
            }
            return (
              o(n, e),
              s(n, [
                {
                  key: 'setPointInfo',
                  value: function (t) {
                    if (t !== null && typeof t !== 'undefined') {
                      if (this.m_iPolygonType === 0) {
                        for (
                          var e = t[0][0],
                            n = t[0][1],
                            i = t[0][0],
                            r = t[0][1],
                            o = 0,
                            a = t.length;
                          o < a;
                          o++
                        ) {
                          e > t[o][0] && (e = t[o][0]),
                          n > t[o][1] && (n = t[o][1]),
                          i < t[o][0] && (i = t[o][0]),
                          r < t[o][1] && (r = t[o][1])
                        }
                        this.m_aPoint = [[e, n], [i, n], [i, r], [e, r]]
                      } else {
                        this.m_iPolygonType === 1
                          ? (this.m_aPoint = t)
                          : (this.m_aPoint = t)
                      }
                      this.setEdgePoints(t)
                    }
                  }
                },
                {
                  key: 'draw',
                  value: function () {
                    if (this.m_aPoint.length > 0) {
                      (i[f].fillStyle = this.m_szFillColor),
                      (i[f].strokeStyle = this.m_szDrawColor),
                      (i[f].globalAlpha = 1)
                      var t = 0
                      var e = 0
                      if (this.m_bChoosed) {
                        for (t = 0, e = this.m_aPoint.length; t < e; t++) {
                          i[f].beginPath(),
                          i[f].arc(
                            this.m_aPoint[t][0],
                            this.m_aPoint[t][1],
                            3,
                            0,
                            360,
                            !1
                          ),
                          (i[f].fillStyle = this.m_szDrawColor),
                          i[f].closePath(),
                          i[f].fill()
                        }
                      }
                      for (
                        i[f].beginPath(),
                        i[f].moveTo(
                          this.m_aPoint[0][0],
                          this.m_aPoint[0][1]
                        ),
                        t = 0,
                        e = this.m_aPoint.length;
                        t < e;
                        t++
                      ) {
                        t !== 0 &&
                              i[f].lineTo(
                                this.m_aPoint[t][0],
                                this.m_aPoint[t][1]
                              )
                      }
                      i[f].stroke(),
                      this.m_bClosed &&
                              (i[f].fillText(
                                this.m_szTips,
                                (this.m_oEdgePoints.left.x +
                                  this.m_oEdgePoints.right.x) /
                                  2,
                                (this.m_oEdgePoints.top.y +
                                  this.m_oEdgePoints.bottom.y) /
                                  2
                              ),
                              i[f].closePath(),
                              i[f].stroke(),
                              (i[f].globalAlpha = this.m_iTranslucent),
                              i[f].fill())
                    }
                  }
                },
                {
                  key: 'move',
                  value: function (t, e, n, r) {
                    if (this.m_iPolygonType === 1) {
                      if (
                        this.m_aPoint.length < this.m_iMaxPointNum &&
                            this.m_aPoint.length > 0
                      ) {
                        (i[f].fillStyle = this.m_szFillColor),
                        (i[f].strokeStyle = this.m_szDrawColor),
                        (i[f].globalAlpha = 1)
                        var o = 0
                        var a = 0
                        for (o = 0, a = this.m_aPoint.length; o < a; o++) {
                          i[f].beginPath(),
                          i[f].arc(
                            this.m_aPoint[o][0],
                            this.m_aPoint[o][1],
                            3,
                            0,
                            360,
                            !1
                          ),
                          (i[f].fillStyle = this.m_szDrawColor),
                          i[f].closePath(),
                          i[f].fill()
                        }
                        for (
                          i[f].beginPath(),
                          i[f].moveTo(
                            this.m_aPoint[0][0],
                            this.m_aPoint[0][1]
                          ),
                          o = 0,
                          a = this.m_aPoint.length;
                          o < a;
                          o++
                        ) {
                          o !== 0 &&
                                i[f].lineTo(
                                  this.m_aPoint[o][0],
                                  this.m_aPoint[o][1]
                                )
                        }
                        i[f].lineTo(t, e), i[f].closePath(), i[f].stroke()
                      }
                    } else if (this.m_iPolygonType === 0) {
                      this.m_bChoosed = !0
                      var s = n
                      var u = r
                      var l = t
                      var c = e
                      this.setPointInfo([[s, u], [l, u], [l, c], [s, c]]),
                      this.draw()
                    }
                  }
                },
                {
                  key: 'stretch',
                  value: function (e, n) {
                    this.m_iEditType === 0 &&
                          (this.m_iPolygonType === 1
                            ? this.m_iIndexChoosePoint !== -1 &&
                              ((this.m_aPoint[this.m_iIndexChoosePoint][0] = e),
                              (this.m_aPoint[this.m_iIndexChoosePoint][1] = n))
                            : this.m_iIndexChoosePoint === 0
                              ? e < this.m_aPoint[2][0] &&
                                n < this.m_aPoint[2][1] &&
                                ((this.m_aPoint[0][0] = e),
                                (this.m_aPoint[0][1] = n),
                                (this.m_aPoint[3][0] = e),
                                (this.m_aPoint[1][1] = n))
                              : this.m_iIndexChoosePoint === 1
                                ? e > this.m_aPoint[3][0] &&
                                  n < this.m_aPoint[3][1] &&
                                  ((this.m_aPoint[1][0] = e),
                                  (this.m_aPoint[1][1] = n),
                                  (this.m_aPoint[2][0] = e),
                                  (this.m_aPoint[0][1] = n))
                                : this.m_iIndexChoosePoint === 2
                                  ? e > this.m_aPoint[0][0] &&
                                    n > this.m_aPoint[0][1] &&
                                    ((this.m_aPoint[2][0] = e),
                                    (this.m_aPoint[2][1] = n),
                                    (this.m_aPoint[1][0] = e),
                                    (this.m_aPoint[3][1] = n))
                                  : this.m_iIndexChoosePoint === 3 &&
                                    e < this.m_aPoint[1][0] &&
                                    n > this.m_aPoint[1][1] &&
                                    ((this.m_aPoint[3][0] = e),
                                    (this.m_aPoint[3][1] = n),
                                    (this.m_aPoint[0][0] = e),
                                    (this.m_aPoint[2][1] = n)),
                          this.setPointInfo(this.m_aPoint),
                          t())
                  }
                }
              ]),
              n
            )
          })(b)
          var T = (function () {
            function r (t) {
              a(this, r),
              (i = this),
              (this[u] = (0, l.default)('#' + t)),
              (this[f] = this[u][0].getContext('2d')),
              (this[c] = []),
              (this[h] = !1),
              (this[d] = 'Rect'),
              (this[p] = 10),
              (this[g] = !0),
              (this[v] = {}),
              (this[S] = null),
              (this[y] = {
                szDrawColor: '#ff0000',
                szFillColor: '#343434',
                iTranslucent: 0.7
              }),
              (this[m] = !1),
              (this.m_iCanvasWidth = this[u].width()),
              (this.m_iCanvasHeight = this[u].height()),
              (this.m_iHorizontalResolution = 0),
              (this.m_iVerticalResolution = 0),
              (this.m_szDisplayMode = ''),
              (this.m_szVideoFormat = ''),
              n(),
              (this[c].length = 0)
            }
            return (
              s(r, [
                {
                  key: 'setDrawMutiShapeOneTime',
                  value: function (t) {
                    this[g] = t
                  }
                },
                {
                  key: 'setMaxShapeSupport',
                  value: function (t) {
                    this[p] = t
                  }
                },
                {
                  key: 'getMaxShapeSupport',
                  value: function () {
                    return this[p]
                  }
                },
                {
                  key: 'setDrawStatus',
                  value: function (t, e) {
                    (this[h] = t),
                    e && t && (this[S] = e),
                    t || (this[S] = null)
                  }
                },
                {
                  key: 'setShapeType',
                  value: function (e) {
                    (this[d] = e), t()
                  }
                },
                {
                  key: 'setCurrentShapeInfo',
                  value: function (t) {
                    this[v] = t || {
                      szId: '',
                      szTips: '',
                      iMinClosed: 3,
                      iMaxPointNum: 11,
                      iPolygonType: 1
                    }
                  }
                },
                {
                  key: 'getShapeType',
                  value: function () {
                    return this[d]
                  }
                },
                {
                  key: 'getAllShapesInfo',
                  value: function () {
                    for (var t = [], e = 0, n = this[c].length; e < n; e++) {
                      this[c][e].m_szType === 'Grid'
                        ? t.push({
                          szType: this[c][e].m_szType,
                          szGridMap: this[c][e].m_szGridMap,
                          iGridColNum: this[c][e].m_iGridColNum,
                          iGridRowNum: this[c][e].m_iGridRowNum
                        })
                        : this[c][e].m_szType === 'RectOSD'
                          ? t.push({
                            szType: this[c][e].m_szType,
                            szText: this[c][e].m_szText,
                            szEnabled: this[c][e].m_szEnabled,
                            szOSDType: this[c][e].m_szOSDType,
                            iPositionX: this[c][e].m_aPoint[0][0],
                            iPositionY: this[c][e].m_aPoint[0][1],
                            szDateStyle: this[c][e].m_szDateStyle,
                            szClockType: this[c][e].m_szClockType,
                            szDisplayWeek: this[c][e].m_szDisplayWeek,
                            szId: this[c][e].m_szId
                          })
                          : t.push({
                            szType: this[c][e].m_szType,
                            aPoint: this[c][e].m_aPoint,
                            szId: this[c][e].m_szId
                          })
                    }
                    return t
                  }
                },
                {
                  key: 'getShapesInfoByType',
                  value: function (t) {
                    for (var e = [], n = 0, i = this[c].length; n < i; n++) {
                      this[c][n].m_szType === t &&
                            (this[c][n].m_szType === 'Grid'
                              ? e.push({
                                szType: this[c][n].m_szType,
                                szGridMap: this[c][n].m_szGridMap,
                                iGridColNum: this[c][n].m_iGridColNum,
                                iGridRowNum: this[c][n].m_iGridRowNum
                              })
                              : this[c][n].m_szType === 'RectOSD'
                                ? e.push({
                                  szType: this[c][n].m_szType,
                                  szText: this[c][n].m_szText,
                                  szEnabled: this[c][n].m_szEnabled,
                                  szOSDType: this[c][n].m_szOSDType,
                                  iPositionX: this[c][n].m_aPoint[0][0],
                                  iPositionY: this[c][n].m_aPoint[0][1],
                                  szDateStyle: this[c][n].m_szDateStyle,
                                  szClockType: this[c][n].m_szClockType,
                                  szDisplayWeek: this[c][n].m_szDisplayWeek,
                                  szId: this[c][n].m_szId
                                })
                                : t === 'Polygon'
                                  ? e.push({
                                    szType: this[c][n].m_szType,
                                    szId: this[c][n].m_szId,
                                    iPolygonType: this[c][n].m_iPolygonType,
                                    iMinClosed: this[c][n].m_iMinClosed,
                                    iMaxPointNum: this[c][n].m_iMaxPointNum,
                                    iEditType: this[c][n].m_iEditType,
                                    aPoint: this[c][n].m_aPoint,
                                    bClosed: this[c][n].m_bClosed,
                                    szTips: this[c][n].m_szTips,
                                    szDrawColor: this[c][n].m_szDrawColor,
                                    szFillColor: this[c][n].m_szFillColor,
                                    iTranslucent: this[c][n].m_iTranslucent
                                  })
                                  : t === 'Line'
                                    ? e.push({
                                      szType: this[c][n].m_szType,
                                      szId: this[c][n].m_szId,
                                      aPoint: this[c][n].m_aPoint,
                                      szTips: this[c][n].m_szTips,
                                      iLineType: this[c][n].m_iLineType,
                                      iDirection: this[c][n].m_iDirection,
                                      iArrowType: this[c][n].m_iArrowType,
                                      szDrawColor: this[c][n].m_szDrawColor,
                                      aCrossArrowPoint: this[c][n]
                                        .m_aCrossArrowPoint
                                    })
                                    : t === 'Rect'
                                      ? e.push({
                                        szType: this[c][n].m_szType,
                                        iEditType: this[c][n].m_iEditType,
                                        aPoint: this[c][n].m_aPoint,
                                        szTips: this[c][n].m_szTips,
                                        szDrawColor: this[c][n].m_szDrawColor,
                                        szFillColor: this[c][n].m_szFillColor,
                                        iTranslucent: this[c][n]
                                          .m_iTranslucent
                                      })
                                      : e.push({
                                        szType: this[c][n].m_szType,
                                        aPoint: this[c][n].m_aPoint
                                      }))
                    }
                    return e
                  }
                },
                {
                  key: 'setShapesInfoByType',
                  value: function (n, i) {
                    i || (i = [])
                    var r = null
                    if (n === 'Rect' || n === 'Polygon' || n === 'Line') {
                      for (var o = 0, a = i.length; o < a; o++) {
                        n === 'Rect'
                          ? ((r = new w()),
                          (r.m_iEditType = i[o].iEditType),
                          (r.m_szTips = i[o].szTips),
                          i[o].style &&
                                  ((r.m_szDrawColor = i[o].style.szDrawColor),
                                  (r.m_szFillColor = i[o].style.szFillColor),
                                  (r.m_iTranslucent = i[o].style.iTranslucent)))
                          : n === 'Polygon'
                            ? ((r = new x()),
                            i[o].iPolygonType === 0
                              ? (r.m_bClosed = !0)
                              : (r.m_bClosed = i[o].bClosed),
                            (r.m_szTips = i[o].szTips),
                            (r.m_szId = i[o].szId || ''),
                            (r.m_iPolygonType = i[o].iPolygonType),
                            (r.m_iMinClosed = i[o].iMinClosed || 3),
                            (r.m_iMaxPointNum = i[o].iMaxPointNum || 11),
                            (r.m_iEditType = i[o].iEditType),
                            i[o].style &&
                                    ((r.m_szDrawColor = i[o].style.szDrawColor),
                                    (r.m_szFillColor = i[o].style.szFillColor),
                                    (r.m_iTranslucent =
                                      i[o].style.iTranslucent)))
                            : n === 'Line' &&
                                  ((r = new C()),
                                  (r.m_iLineType = i[o].iLineType),
                                  (r.m_szTips = i[o].szTips),
                                  (r.m_szId = i[o].szId),
                                  (r.m_iDirection = i[o].iDirection),
                                  (r.m_iArrowType = i[o].iArrowType),
                                  i[o].style &&
                                    (r.m_szDrawColor = i[o].style.szDrawColor),
                                  r.setPointInfo(i[o].aPoint)),
                        r.setPointInfo(i[o].aPoint),
                        o === 0 && (r.m_bChoosed = !0),
                        e(r)
                      }
                    } else {
                      n === 'Grid' &&
                            ((r = new P()),
                            (r.m_szGridMap = i[0].szGridMap || ''),
                            (r.m_iGridColNum = i[0].iGridColNum || 22),
                            (r.m_iGridRowNum = i[0].iGridRowNum || 18),
                            e(r))
                    }
                    t()
                  }
                },
                {
                  key: 'addOSDShape',
                  value: function (n, i, r, o, a) {
                    r || o || ((r = 0), (o = 0)), a || (a = {})
                    var s = new _(n, i)
                    var u = 10 * n.length;
                    (s.m_aPoint = [
                      [r, o],
                      [u + r, o],
                      [u + r, o + 20],
                      [r, o + 20]
                    ]),
                    (s.m_szOSDType = a.szOSDType || ''),
                    (s.m_szDateStyle = a.szDateStyle || ''),
                    (s.m_szClockType = a.szClockType || ''),
                    (s.m_szDisplayWeek = a.szDisplayWeek || ''),
                    (s.m_szId = a.szId || ''),
                    e(s),
                    t()
                  }
                },
                {
                  key: 'setCanvasSize',
                  value: function (e, n) {
                    e > 0 &&
                          n > 0 &&
                          ((this.m_iCanvasWidth = e),
                          (this.m_iCanvasHeight = n),
                          t())
                  }
                },
                {
                  key: 'setDrawStyle',
                  value: function (t, e, n) {
                    this[y] = {
                      szDrawColor: t,
                      szFillColor: e,
                      iTranslucent: n
                    }
                  }
                },
                {
                  key: 'clearAllShape',
                  value: function () {
                    (this[c].length = 0), t()
                  }
                },
                {
                  key: 'clearShapeByType',
                  value: function (e) {
                    for (var n = this[c].length, i = n; i > 0; i--) {
                      this[c][i - 1].m_szType === e &&
                            (e === 'Grid'
                              ? ((this[c][i - 1].m_szGridMap = ''),
                              (this[c][i - 1].m_aAddGridMap = []))
                              : this[c].splice(i - 1, 1))
                    }
                    t()
                  }
                },
                {
                  key: 'deleteShape',
                  value: function (e) {
                    this[c].length > e && this[c].splice(e, 1), t()
                  }
                },
                {
                  key: 'updateCanvas',
                  value: function (t) {
                    (this[u] = (0, l.default)('#' + t)),
                    (this[f] = this[u][0].getContext('2d')),
                    (this.m_iCanvasWidth = this[u].width()),
                    (this.m_iCanvasHeight = this[u].height()),
                    n()
                  }
                },
                {
                  key: 'resizeCanvas',
                  value: function () {
                    (this.m_iCanvasWidth = this[u].width()),
                    (this.m_iCanvasHeight = this[u].height())
                  }
                },
                {
                  key: 'canvasRedraw',
                  value: function () {
                    t()
                  }
                }
              ]),
              r
            )
          })()
          return T
        }
      })()
      e.ESCanvas = f
    },
    function (t, e, n) {
      'use strict'
      function i (t) {
        return t && t.__esModule ? t : { default: t }
      }
      function r (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 }),
      (e.StorageManager = void 0)
      var o = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var a = n(2)
      var s = i(a)
      var u = n(5)
      var l = 'Web/RecordFiles/'
      var f = 'Web/PlaybackFiles/'
      var c = 1e3
      var h = 1
      var d = 3001;
      (window.requestFileSystem =
        window.requestFileSystem || window.webkitRequestFileSystem),
      (window.URL = window.URL || window.webkitURL)
      var p = (function () {
        function t (e, n, i, o) {
          r(this, t),
          (this.szUUID = e),
          (this.szFileName = n),
          (this.iStreamType = i),
          (this.szPath = ''),
          (this.bStart = !1),
          (this.aStreamList = []),
          (this.options = o)
        }
        return (
          o(t, [
            {
              key: 'init',
              value: function () {
                var t = this
                this.iStreamType === 0
                  ? (this.szPath = l)
                  : this.iStreamType === 1 && (this.szPath = f),
                (this.szPath += this.getDateDir())
                var e = t.szPath.split('/')
                var n = new Promise(function (n) {
                  window.requestFileSystem(
                    window.TEMPORARY,
                    t.options.iFileSize,
                    function (i) {
                      t.createDir(i.root, e, function () {
                        n()
                      })
                    },
                    t.errorHandler
                  )
                })
                return n
              }
            },
            {
              key: 'getDateDir',
              value: function () {
                return u.oTool.dateFormat(new Date(), 'yyyy-MM-dd')
              }
            },
            {
              key: 'createDir',
              value: function (t, e, n) {
                var i = this
                e.length
                  ? t.getDirectory(
                    e[0],
                    { create: !0 },
                    function (t) {
                      i.createDir(t, e.slice(1), n)
                    },
                    i.errorHandler
                  )
                  : n()
              }
            },
            { key: 'errorHandler', value: function () {} },
            {
              key: 'writeFileHeader',
              value: function (t) {
                var e = this
                window.requestFileSystem(
                  window.TEMPORARY,
                  e.options.iFileSize,
                  function (n) {
                    n.root.getFile(
                      e.szPath + '/' + e.szFileName,
                      { create: !0 },
                      function (n) {
                        n.createWriter(function (n) {
                          (n.onwriteend = function () {
                            (e.bStart = !0), e.writeFile(n)
                          }),
                          (n.onerror = function () {}),
                          n.seek(n.length)
                          var i = new Blob([t])
                          n.write(i)
                        }, e.errorHandler)
                      },
                      e.errorHandler
                    )
                  },
                  e.errorHandler
                )
              }
            },
            {
              key: 'writeFileContent',
              value: function (t) {
                this.aStreamList.push(t)
              }
            },
            {
              key: 'writeFile',
              value: function (t) {
                var e = this
                if (this.bStart) {
                  if (this.aStreamList.length > 0) {
                    var n = this.aStreamList.shift()
                    if (
                      (t.seek(t.length), t.length >= this.options.iFileSize)
                    ) {
                      return void (
                        this.options.cbEventHandler &&
                          this.options.cbEventHandler(d, this.szUUID)
                      )
                    }
                    var i = new Blob([n])
                    t.write(i)
                  } else {
                    setTimeout(function () {
                      e.writeFile(t)
                    }, c)
                  }
                }
              }
            },
            {
              key: 'stopWriteFile',
              value: function () {
                var t = this;
                (this.bStart = !1), (this.aStreamList.length = 0)
                var e = new Promise(function (e) {
                  window.requestFileSystem(
                    window.TEMPORARY,
                    t.options.iFileSize,
                    function (n) {
                      n.root.getFile(
                        t.szPath + '/' + t.szFileName,
                        { create: !1 },
                        function (t) {
                          t.file(function (t) {
                            e(), u.oTool.downloadFile(t, t.name)
                          })
                        },
                        t.errorHandler
                      )
                    },
                    t.errorHandler
                  )
                })
                return e
              }
            }
          ]),
          t
        )
      })()
      var y = (function () {
        function t (e, n, i, o, a, s, u) {
          r(this, t),
          (this.szBasePath = e),
          (this.szUUID = n),
          (this.szFileName = i),
          (this.aHeadBuf = new Uint8Array(o)),
          (this.iPackType = a),
          (this.iStreamType = s),
          (this.oWorker = null),
          (this.oFileSystem = null),
          (this.options = u)
        }
        return (
          o(t, [
            {
              key: 'init',
              value: function () {
                var t = this
                var e = new Promise(function (e, n) {
                  t.initFileSystem().then(
                    function () {
                      t.initWorker().then(
                        function () {
                          e(t.szUUID)
                        },
                        function (t) {
                          n(t)
                        }
                      )
                    },
                    function (t) {
                      n(t)
                    }
                  )
                })
                return e
              }
            },
            {
              key: 'initFileSystem',
              value: function () {
                var t = this
                this.oFileSystem = new p(
                  this.szUUID,
                  this.szFileName,
                  this.iStreamType,
                  this.options
                )
                var e = new Promise(function (e, n) {
                  t.oFileSystem.init().then(
                    function () {
                      e()
                    },
                    function (t) {
                      n(t)
                    }
                  )
                })
                return e
              }
            },
            {
              key: 'initWorker',
              value: function () {
                var t = this
                var e = new Promise(function (e) {
                  (t.oWorker = new Worker(
                    t.szBasePath + '/systemTransform-worker.min.js'
                  )),
                  (t.oWorker.onmessage = function (n) {
                    var i = n.data
                    if (i.type === 'loaded') {
                      t.oWorker.postMessage(
                        {
                          type: 'create',
                          buf: t.aHeadBuf.buffer,
                          len: 40,
                          packType: t.iPackType
                        },
                        [t.aHeadBuf.buffer]
                      )
                    } else if (i.type === 'created') e()
                    else if (i.type === 'outputData') {
                      var r = new Uint8Array(i.buf)
                      h === i.dType
                        ? t.oFileSystem.writeFileHeader(r)
                        : t.oFileSystem.writeFileContent(r)
                    }
                  })
                })
                return e
              }
            },
            {
              key: 'inputData',
              value: function (t) {
                if (this.oWorker) {
                  var e = new Uint8Array(t)
                  this.oWorker.postMessage(
                    { type: 'inputData', buf: e.buffer, len: e.length },
                    [e.buffer]
                  )
                }
              }
            },
            {
              key: 'stopRecord',
              value: function () {
                var t = this
                var e = new Promise(function (e, n) {
                  t.oWorker
                    ? t.oWorker.postMessage({ type: 'release' })
                    : n(),
                  t.oFileSystem
                    ? t.oFileSystem.stopWriteFile().then(
                      function () {
                        e()
                      },
                      function () {
                        n()
                      }
                    )
                    : n()
                })
                return e
              }
            }
          ]),
          t
        )
      })()
      var m = (function () {
        if (typeof Symbol !== 'undefined') {
          var t = Symbol('STORAGELIST')
          var e = (function () {
            function e (n, i) {
              r(this, e),
              (this.szBasePath = n),
              (this[t] = {}),
              (this.options = { iFileSize: 1073741824 }),
              Object.assign(this.options, i)
            }
            return (
              o(e, [
                {
                  key: 'startRecord',
                  value: function (e, n, i, r, o) {
                    var a = this
                    var u = s.default.v4()
                    var l = Object.assign({}, this.options, o)
                    var f = new y(this.szBasePath, u, e, n, i, r, l)
                    var c = new Promise(function (e, n) {
                      f.init().then(
                        function (n) {
                          (a[t][n] = f), e(n)
                        },
                        function (t) {
                          n(t)
                        }
                      )
                    })
                    return c
                  }
                },
                {
                  key: 'inputData',
                  value: function (e, n) {
                    var i = this[t][e]
                    i && i.inputData(n)
                  }
                },
                {
                  key: 'stopRecord',
                  value: function (e) {
                    var n = this
                    var i = new Promise(function (i, r) {
                      var o = n[t][e]
                      o
                        ? o.stopRecord().then(
                          function () {
                            delete n[t][e], i()
                          },
                          function () {
                            r()
                          }
                        )
                        : r()
                    })
                    return i
                  }
                }
              ]),
              e
            )
          })()
          return e
        }
      })()
      e.StorageManager = m
    },
    function (t, e) {
      'use strict'
      function n (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 })
      var i = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var r = 3001
      var o = 3002
      var a = 3003
      var s = (function () {
        if (typeof Symbol !== 'undefined') {
          var t = (function () {
            function t () {
              n(this, t)
            }
            return (
              i(t, [
                {
                  key: 'createClientObject',
                  value: function (t, e, n) {
                    return {
                      socket: t,
                      id: e,
                      playURL: n,
                      resolve: null,
                      reject: null
                    }
                  }
                },
                {
                  key: 'getMediaFromSdp',
                  value: function (t) {
                    for (
                      var e = t.indexOf('MEDIAINFO=') + 10,
                        n = t.slice(e, e + 80),
                        i = [],
                        r = 0,
                        o = n.length / 2;
                      r < o;
                      r++
                    ) { i[r] = parseInt(n.slice(2 * r, 2 * r + 2), 16) }
                    return new Uint8Array(i)
                  }
                },
                {
                  key: 'playCmd',
                  value: function (t) {
                    var e = { sequence: 0, cmd: 'realplay', url: t }
                    return JSON.stringify(e)
                  }
                },
                {
                  key: 'playbackCmd',
                  value: function (t, e, n) {
                    var i = {
                      sequence: 0,
                      cmd: 'playback',
                      url: n,
                      startTime: t,
                      endTime: e
                    }
                    return JSON.stringify(i)
                  }
                },
                {
                  key: 'playRateCmd',
                  value: function (t) {
                    var e = { sequence: 0, cmd: 'speed', rate: t }
                    return JSON.stringify(e)
                  }
                },
                {
                  key: 'pauseCmd',
                  value: function () {
                    var t = { sequence: 0, cmd: 'pause' }
                    return JSON.stringify(t)
                  }
                },
                {
                  key: 'resumeCmd',
                  value: function () {
                    var t = { sequence: 0, cmd: 'resume' }
                    return JSON.stringify(t)
                  }
                },
                {
                  key: 'getError',
                  value: function (t) {
                    var e = r
                    return (
                      t &&
                          (parseInt(t.statusCode, 10) === 6 &&
                          t.subStatusCode === 'streamLimit'
                            ? (e = o)
                            : parseInt(t.statusCode, 10) === 4 &&
                              t.subStatusCode === 'badAuthorization' &&
                              (e = a)),
                      { iErrorNum: e, oError: t }
                    )
                  }
                }
              ]),
              t
            )
          })()
          return t
        }
      })()
      e.DirectDevice = s
    },
    function (t, e) {
      'use strict'
      function n (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 })
      var i = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var r = (function () {
        if (typeof Symbol !== 'undefined') {
          var t = (function () {
            function t () {
              n(this, t)
            }
            return (
              i(t, [
                {
                  key: 'createClientObject',
                  value: function (t, e, n, i) {
                    return {
                      socket: t,
                      id: e,
                      iCurChannel: n,
                      iCurStream: i,
                      resolve: null,
                      reject: null
                    }
                  }
                },
                {
                  key: 'zeroPlayCmd',
                  value: function (t, e) {
                    var n = [
                      0,
                      0,
                      0,
                      44,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      19,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      t + 1,
                      0,
                      0,
                      0,
                      e,
                      0,
                      0,
                      4,
                      0
                    ]
                    return new Uint8Array(n)
                  }
                },
                {
                  key: 'playCmd',
                  value: function (t, e) {
                    var n = [
                      0,
                      0,
                      0,
                      44,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      3,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      t,
                      0,
                      0,
                      0,
                      e,
                      0,
                      0,
                      4,
                      0
                    ]
                    return new Uint8Array(n)
                  }
                },
                {
                  key: 'playbackCmd',
                  value: function (t, e, n, i) {
                    var r = t.split('T')[0]
                    var o = t.split('T')[1]
                    var a = '0' + parseInt(r.substring(0, 4), 10).toString(16)
                    var s = parseInt(r.substring(4, 6), 10)
                    var u = parseInt(r.substring(6), 10)
                    var l = parseInt(o.substring(0, 2), 10)
                    var f = parseInt(o.substring(2, 4), 10)
                    var c = parseInt(o.substring(4, 6), 10)
                    var h = e.split('T')[0]
                    var d = e.split('T')[1]
                    var p = '0' + parseInt(h.substring(0, 4), 10).toString(16)
                    var y = parseInt(h.substring(4, 6), 10)
                    var m = parseInt(d.substring(0, 2), 10)
                    var v = parseInt(d.substring(2, 4), 10)
                    var g = parseInt(d.substring(4, 6), 10)
                    var S = [
                      0,
                      0,
                      0,
                      96,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      3,
                      1,
                      2,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      n,
                      0,
                      0,
                      parseInt(a.substring(0, 2), 16),
                      parseInt(a.substring(2, 4), 16),
                      0,
                      0,
                      0,
                      s,
                      0,
                      0,
                      0,
                      u,
                      0,
                      0,
                      0,
                      l,
                      0,
                      0,
                      0,
                      f,
                      0,
                      0,
                      0,
                      c,
                      0,
                      0,
                      parseInt(p.substring(0, 2), 16),
                      parseInt(p.substring(2, 4), 16),
                      0,
                      0,
                      0,
                      y,
                      0,
                      0,
                      0,
                      u,
                      0,
                      0,
                      0,
                      m,
                      0,
                      0,
                      0,
                      v,
                      0,
                      0,
                      0,
                      g,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      i,
                      0,
                      0,
                      0
                    ]
                    return new Uint8Array(S)
                  }
                },
                {
                  key: 'playRateCmd',
                  value: function (t) {
                    for (
                      var e = (parseInt(t, 10) >>> 0)
                          .toString(16)
                          .toLocaleUpperCase()
                          .toString(16),
                        n = e.length;
                      n < 8;
                      n++
                    ) { e = '0' + e }
                    for (
                      var i = [0, 0, 0, 0], r = 0, o = e.length;
                      r < o;
                      r += 2
                    ) {
                      i[Math.floor(r / 2)] = parseInt(
                        e.substring(r, r + 2),
                        16
                      )
                    }
                    var a = [
                      0,
                      0,
                      0,
                      36,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      3,
                      1,
                      47,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      i[0],
                      i[1],
                      i[2],
                      i[3]
                    ]
                    return new Uint8Array(a)
                  }
                },
                {
                  key: 'pauseCmd',
                  value: function () {
                    var t = [
                      0,
                      0,
                      0,
                      32,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      3,
                      1,
                      7,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0
                    ]
                    return new Uint8Array(t)
                  }
                },
                {
                  key: 'resumeCmd',
                  value: function () {
                    var t = [
                      0,
                      0,
                      0,
                      32,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      3,
                      1,
                      8,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0,
                      0
                    ]
                    return new Uint8Array(t)
                  }
                }
              ]),
              t
            )
          })()
          return t
        }
      })()
      e.DirectDeviceCustom = r
    },
    function (t, e, n) {
      'use strict'
      function i (t) {
        return t && t.__esModule ? t : { default: t }
      }
      function r (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 }),
      (e.LiveMedia = void 0)
      var o = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var a = n(18)
      var s = i(a)
      var u = n(17)
      var l = i(u)
      var f = (function () {
        if (typeof Symbol !== 'undefined') {
          var t = (function () {
            function t () {
              r(this, t)
            }
            return (
              o(t, [
                {
                  key: 'createClientObject',
                  value: function (t, e, n, i, r) {
                    var o = l.default.AES.encrypt(
                      new Date().getTime().toString(),
                      l.default.enc.Hex.parse(
                        '1234567891234567123456789123456712345678912345671234567891234567'
                      ),
                      {
                        mode: l.default.mode.CBC,
                        iv: l.default.enc.Hex.parse(
                          '12345678912345671234567891234567'
                        ),
                        padding: l.default.pad.Pkcs7
                      }
                    ).ciphertext.toString()
                    o.length < 64 && (o += o)
                    var a = l.default.AES.encrypt(
                      new Date().getTime().toString(),
                      l.default.enc.Hex.parse(
                        '12345678912345671234567891234567'
                      ),
                      {
                        mode: l.default.mode.CBC,
                        iv: l.default.enc.Hex.parse(
                          '12345678912345671234567891234567'
                        ),
                        padding: l.default.pad.Pkcs7
                      }
                    ).ciphertext.toString()
                    return {
                      socket: t,
                      id: e,
                      PKD: n,
                      rand: i,
                      playURL: r.playURL || '',
                      auth: r.auth || '',
                      token: r.token || '',
                      key: o,
                      iv: a,
                      resolve: null,
                      reject: null
                    }
                  }
                },
                {
                  key: 'playCmd',
                  value: function (t) {
                    var e = {
                      sequence: 0,
                      cmd: 'realplay',
                      url: t.playURL,
                      key: s.default
                        .encrypt(t.iv + ':' + t.key, t.PKD)
                        .cipher.split('?')[0],
                      authorization: l.default.AES.encrypt(
                        t.rand + ':' + t.auth,
                        l.default.enc.Hex.parse(t.key),
                        {
                          mode: l.default.mode.CBC,
                          iv: l.default.enc.Hex.parse(t.iv),
                          padding: l.default.pad.Pkcs7
                        }
                      ).ciphertext.toString(),
                      token: l.default.AES.encrypt(
                        t.token,
                        l.default.enc.Hex.parse(t.key),
                        {
                          mode: l.default.mode.CBC,
                          iv: l.default.enc.Hex.parse(t.iv),
                          padding: l.default.pad.Pkcs7
                        }
                      ).ciphertext.toString()
                    }
                    return JSON.stringify(e)
                  }
                },
                {
                  key: 'playbackCmd',
                  value: function (t, e, n) {
                    var i = {
                      sequence: 0,
                      cmd: 'playback',
                      url: t.playURL,
                      key: s.default
                        .encrypt(t.iv + ':' + t.key, t.PKD)
                        .cipher.split('?')[0],
                      authorization: l.default.AES.encrypt(
                        t.rand + ':' + t.auth,
                        l.default.enc.Hex.parse(t.key),
                        {
                          mode: l.default.mode.CBC,
                          iv: l.default.enc.Hex.parse(t.iv),
                          padding: l.default.pad.Pkcs7
                        }
                      ).ciphertext.toString(),
                      token: l.default.AES.encrypt(
                        t.token,
                        l.default.enc.Hex.parse(t.key),
                        {
                          mode: l.default.mode.CBC,
                          iv: l.default.enc.Hex.parse(t.iv),
                          padding: l.default.pad.Pkcs7
                        }
                      ).ciphertext.toString(),
                      startTime: e,
                      endTime: n
                    }
                    return JSON.stringify(i)
                  }
                },
                {
                  key: 'seekCmd',
                  value: function (t, e) {
                    var n = {
                      sequence: 0,
                      cmd: 'seek',
                      startTime: t,
                      endTime: e
                    }
                    return JSON.stringify(n)
                  }
                }
              ]),
              t
            )
          })()
          return t
        }
      })()
      e.LiveMedia = f
    },
    function (t, e) {
      'use strict'
      function n (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 })
      var i = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var r = (function () {
        if (typeof Symbol !== 'undefined') {
          var t = (function () {
            function t () {
              n(this, t)
            }
            return (
              i(t, [
                {
                  key: 'createClientObject',
                  value: function (t, e, n, i) {
                    return {
                      socket: t,
                      id: e,
                      playURL: n,
                      deviceSerial: i.deviceSerial || '',
                      verificationCode: i.verificationCode || '',
                      resolve: null,
                      reject: null
                    }
                  }
                },
                {
                  key: 'playCmd',
                  value: function (t) {
                    var e = {
                      sequence: 0,
                      cmd: 'realplay',
                      deviceSerial: t.deviceSerial,
                      verificationCode: t.verificationCode,
                      url: t.playURL
                    }
                    return JSON.stringify(e)
                  }
                },
                {
                  key: 'playbackCmd',
                  value: function (t, e, n) {
                    var i = {
                      sequence: 0,
                      cmd: 'playback',
                      deviceSerial: t.deviceSerial,
                      verificationCode: t.verificationCode,
                      url: t.playURL,
                      startTime: e,
                      endTime: n
                    }
                    return JSON.stringify(i)
                  }
                }
              ]),
              t
            )
          })()
          return t
        }
      })()
      e.LocalService = r
    },
    function (t, e, n) {
      'use strict'
      function i (t) {
        return t && t.__esModule ? t : { default: t }
      }
      function r (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 }),
      (e.StreamClient = void 0)
      var o = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var a = n(2)
      var s = i(a)
      var u = n(12)
      var l = n(11)
      var f = n(13)
      var c = n(14)
      var h = (function () {
        if (typeof Symbol !== 'undefined') {
          var t = Symbol('WEBSOCKET')
          var e = Symbol('GETINDEX')
          var n = Symbol('PROTOCOLVERSION')
          var i = Symbol('CIPHERSUITES')
          var a = new u.DirectDeviceCustom()
          var h = new l.DirectDevice()
          var d = new f.LiveMedia()
          var p = new c.LocalService()
          var y = (function () {
            function u () {
              r(this, u),
              (this[n] = '0.1'),
              (this[i] = 0),
              (this[t] = []),
              (this.ERRORS = {}),
              (this[e] = function (e) {
                for (var n = -1, i = 0, r = this[t].length; i < r; i++) {
                  if (this[t][i].id === e) {
                    n = i
                    break
                  }
                }
                return n
              })
            }
            return (
              o(u, [
                {
                  key: 'openStream',
                  value: function (r, o, u, l) {
                    console.log('r', r)
                    var f = this
                    var c = r.split(':')
                    var y = c[0]
                    var m = c[1].split('//')[1]
                    var v = Math.floor(c[2].split('/')[0])
                    o = o || {}
                    console.log('oooooo', o)
                    console.log('this[M]-99-p', p)
                    console.log('y,m,v', y, m, v)
                    var g = o.sessionID || o.session || ''
                    var S = new window.WebSocket(r)
                    S.binaryType = 'arraybuffer'
                    var b = s.default.v4()
                    var w = Math.floor(c[2].split('/')[1] / 100)
                    var _ = Math.floor(c[2].split('/')[1] % 100) - 1
                    w === 0 && (_ = 0)
                    var P = new Promise(function (r, s) {
                      (S.onopen = function () {
                        o.playURL ||
                              o.sessionID ||
                              o.deviceSerial ||
                              (f[t].push(a.createClientObject(S, b, w, _)),
                              r(b))
                      }),
                      (S.onmessage = function (a) {
                        if (typeof a.data === 'string') {
                          var l = JSON.parse(a.data)
                          var c = f[e](b)
                          if (l && l.version && l.cipherSuite) {
                            if (
                              ((f[n] = l.version),
                              (f[i] = parseInt(l.cipherSuite, 10)),
                              l && l.PKD && l.rand)
                            ) {
                              f[t].push(
                                d.createClientObject(
                                  S,
                                  b,
                                  l.PKD,
                                  l.rand,
                                  o
                                )
                              )
                            } else {
                              var y =
                                      'live://' +
                                      m +
                                      ':' +
                                      v +
                                      '/' +
                                      w +
                                      '/' +
                                      _
                              f[i] === -1
                                ? f[t].push(
                                  p.createClientObject(S, b, y, o)
                                )
                                : f[t].push(
                                  h.createClientObject(S, b, y)
                                )
                            }
                            return void r(b)
                          }
                          if (l && l.sdp) {
                            var g = h.getMediaFromSdp(l.sdp)
                            u({ bHead: !0, buf: g })
                          }
                          if (
                            (l &&
                                    l.cmd &&
                                    l.cmd === 'end' &&
                                    u({ type: 'exception', cmd: l.cmd }),
                            l &&
                                    l.statusString &&
                                    (l.statusString.toLowerCase() === 'ok' &&
                                      f[t][c].resolve &&
                                      f[t][c].resolve(l),
                                    l.statusString.toLowerCase() !== 'ok'))
                          ) {
                            var P = h.getError(l)
                            c > -1
                              ? f[t][c].reject && f[t][c].reject(P)
                              : s(P)
                          }
                        } else {
                          var C = {}
                          var x = new Uint8Array(a.data)
                          if (
                            x.byteLength === 64 ||
                                  x.byteLength === 40
                          ) {
                            for (
                              var T = -1, k = x.byteLength, D = 0;
                              D < k;
                              D++
                            ) {
                              if (
                                x[D] === 73 &&
                                      x[D + 1] === 77 &&
                                      x[D + 2] === 75 &&
                                      x[D + 3] === 72
                              ) {
                                T = D
                                break
                              }
                            }
                            if (T !== -1) {
                              var M = x.slice(T, T + 40)
                              C = { bHead: !0, buf: M }
                            } else C = { bHead: !1, buf: x }
                          } else C = { bHead: !1, buf: x }
                          u(C), (x = null), (C = null), (a = null)
                        }
                      }),
                      (S.onclose = function () {
                        for (var e = 0, n = f[t].length; e < n; e++) {
                          if (f[t][e].id === b) {
                            f[t][e].resolve(),
                            f[t].splice(e, 1),
                            setTimeout(function () {
                              l()
                            }, 200)
                            break
                          }
                        }
                        s()
                      })
                    })
                    return P
                  }
                },
                {
                  key: 'startPlay',
                  value: function (r, o, s) {
                    var u = this
                    var l = this[e](r)
                    o &&
                          s &&
                          u[n] === '0.1' &&
                          ((o = o.replace(/-/g, '').replace(/:/g, '')),
                          (s = s.replace(/-/g, '').replace(/:/g, '')))
                    var f = new Promise(function (e, r) {
                      if (l > -1) {
                        (u[t][l].resolve = e), (u[t][l].reject = r)
                        var f = null
                        o && s
                          ? u[n] !== '0.1'
                            ? u[i] === 0
                              ? (f = d.playbackCmd(u[t][l], o, s))
                              : u[i] === 1
                                ? (f = h.playbackCmd(o, s, u[t][l].playURL))
                                : u[i] === -1 &&
                                      (f = p.playbackCmd(u[t][l], o, s))
                            : (f = a.playbackCmd(
                              o,
                              s,
                              u[t][l].iCurChannel,
                              u[t][l].iCurStream
                            ))
                          : u[t][l].iCurChannel === 0 && u[n] === '0.1'
                            ? (f = a.zeroPlayCmd(
                              u[t][l].iCurChannel,
                              u[t][l].iCurStream
                            ))
                            : u[n] !== '0.1'
                              ? u[i] === 0
                                ? (f = d.playCmd(u[t][l]))
                                : u[i] === 1
                                  ? (f = h.playCmd(u[t][l].playURL))
                                  : u[i] === -1 && (f = p.playCmd(u[t][l]))
                              : (f = a.playCmd(
                                u[t][l].iCurChannel,
                                u[t][l].iCurStream
                              )),
                        u[t][l].socket.send(f),
                        u[n] === '0.1' && e()
                      } else u[n] === '0.1' && r()
                    })
                    return f
                  }
                },
                { key: 'singleFrame', value: function () {} },
                {
                  key: 'setPlayRate',
                  value: function (e, i) {
                    var r = this
                    var o = new Promise(function (o, s) {
                      for (var u = 0, l = r[t].length; u < l; u++) {
                        if (r[t][u].id === e) {
                          if (r[n] === '0.1') {
                            var f = a.playRateCmd(i)
                            r[t][u].socket.send(f), o()
                            break
                          }
                          (r[t][u].resolve = o), (r[t][u].reject = s)
                          var c = h.playRateCmd(i)
                          r[t][u].socket.send(c)
                        }
                      }
                    })
                    return o
                  }
                },
                {
                  key: 'seek',
                  value: function (e, n, i) {
                    var r = this
                    var o = new Promise(function (o, a) {
                      for (var s = 0, u = r[t].length; s < u; s++) {
                        if (r[t][s].id === e) {
                          (r[t][s].resolve = o), (r[t][s].reject = a)
                          var l = d.seekCmd(n, i)
                          r[t][s].socket.send(l)
                        }
                      }
                    })
                    return o
                  }
                },
                {
                  key: 'pause',
                  value: function (e) {
                    var i = this
                    var r = new Promise(function (r, o) {
                      for (var s = 0, u = i[t].length; s < u; s++) {
                        if (i[t][s].id === e) {
                          if (i[n] === '0.1') {
                            var l = a.pauseCmd()
                            i[t][s].socket.send(l), r()
                            break
                          }
                          (i[t][s].resolve = r), (i[t][s].reject = o)
                          var f = h.pauseCmd()
                          i[t][s].socket.send(f)
                        }
                      }
                    })
                    return r
                  }
                },
                {
                  key: 'transmission',
                  value: function (e, n) {
                    var i = this
                    var r = new Promise(function (r, o) {
                      for (var a = 0, s = i[t].length; a < s; a++) {
                        i[t][a].id === e &&
                                ((i[t][a].resolve = r),
                                (i[t][a].reject = o),
                                i[t][a].socket.send(n))
                      }
                    })
                    return r
                  }
                },
                {
                  key: 'resume',
                  value: function (e) {
                    var i = this
                    var r = new Promise(function (r, o) {
                      for (var s = 0, u = i[t].length; s < u; s++) {
                        if (i[t][s].id === e) {
                          if (i[n] === '0.1') {
                            var l = a.resumeCmd()
                            i[t][s].socket.send(l), r()
                            break
                          }
                          (i[t][s].resolve = r), (i[t][s].reject = o)
                          var f = h.resumeCmd()
                          i[t][s].socket.send(f)
                        }
                      }
                    })
                    return r
                  }
                },
                {
                  key: 'stop',
                  value: function (e) {
                    var n = this
                    var i = new Promise(function (i, r) {
                      if (e) {
                        for (
                          var o = -1, a = 0, s = n[t].length;
                          a < s;
                          a++
                        ) {
                          if (n[t][a].id === e) {
                            (o = a),
                            (n[t][a].resolve = i),
                            n[t][a].socket.close(1e3, 'CLOSE')
                            break
                          }
                        }
                        o === -1 && r()
                      } else r()
                    })
                    return i
                  }
                },
                {
                  key: 'stopAll',
                  value: function () {
                    for (var e = this, n = 0, i = e[t].length; n < i; n++) { e[t][n].socket.close(1e3, 'CLOSE') }
                  }
                }
              ]),
              u
            )
          })()
          return y
        }
      })()
      e.StreamClient = h
    },
    function (t, e) {
      'use strict'
      function n (t, e) {
        if (!(t instanceof e)) { throw new TypeError('Cannot call a class as a function') }
      }
      Object.defineProperty(e, '__esModule', { value: !0 })
      var i = (function () {
        function t (t, e) {
          for (var n = 0; n < e.length; n++) {
            var i = e[n];
            (i.enumerable = i.enumerable || !1),
            (i.configurable = !0),
            'value' in i && (i.writable = !0),
            Object.defineProperty(t, i.key, i)
          }
        }
        return function (e, n, i) {
          return n && t(e.prototype, n), i && t(e, i), e
        }
      })()
      var r = 0
      var o = 1
      var a = 2
      var s = 11
      var u = 16
      var l = 31
      var f = 60
      var c = 61
      var h = 63
      var d = 0
      var p = 1
      var y = 0
      var m = 1
      var v = 0
      var g = 1
      var S = 15
      var b = 8
      var w = 1
      var _ = 25
      var P = 20
      var C = 5
      var x = 5242880
      var T = 5e3
      var k = !1
      var D = !1
      var M = { id: null, cmd: null, data: null, errorCode: 0, status: null }
      e.JSPlayCtrl = (function () {
        function t (e, i, c) {
          if ((n(this, t), e == null || void 0 === e || typeof e !== 'string')) { return o }
          if (((this.szBasePath = e), !i || typeof i !== 'function')) return o;
          (this.fnCallBack = i),
          (this.decodeWorker = null),
          (this.streamOpenMode = null),
          (this.bOpenStream = !1),
          (this.audioRenderer = null),
          (this.aAudioBuffer = []),
          (this.iAudioBufferSize = 0),
          (this.oSuperRender = null),
          (this.aVideoFrameBuffer = []),
          (this.YUVBufferSize = w),
          (this.szOSDTime = null),
          (this.bPlaySound = !1),
          (this.bPlay = !1),
          (this.bPause = !1),
          (this.bOnebyOne = !1),
          (this.bPlayRateChange = !1),
          (this.dataCallBackFun = null),
          (this.nWidth = 0),
          (this.nHeight = 0),
          (this.sCanvasId = null),
          (this.aDisplayBuf = null),
          (this.bVisibility = !0),
          (this.nDecFrameType = v),
          (this.iCanvasWidth = 0),
          (this.iCanvasHeight = 0),
          (this.iZoomNum = 0),
          (this.iRatio_x = 1),
          (this.iRatio_y = 1),
          (this.stDisplayRect = { top: 0, left: 0, right: 0, bottom: 0 }),
          (this.stYUVRect = { top: 0, left: 0, right: 0, bottom: 0 }),
          (this.aInputDataLens = []),
          (this.aInputDataBuffer = []),
          (this.bIsGetYUV = !1),
          (this.bIsFirstFrame = !0),
          (this.iInputMaxBufSize = x),
          (this.bIsInput = !1),
          (this.bIsInputBufOver = !1),
          (this.iInputDataLen = T)
          var h = this
          if (
            ((this.setCallBack = function (t, e, n, i, r) {
              var o = M;
              (o.id = c),
              (o.cmd = e),
              (o.data = n),
              (o.errorCode = i),
              (o.status = r),
              t.fnCallBack(o)
            }),
            !k)
          ) {
            k = !0
            var d = document.createElement('script');
            (d.type = 'text/javascript'),
            (d.src = h.szBasePath + 'AudioRenderer.js')
            var p = document.getElementsByTagName('head')[0]
            p.appendChild(d),
            (d.onload = d.onreadystatechange = function () {
              !this.readyState ||
                  this.readyState === 'loaded' ||
                  this.readyState === 'complete'
            })
          }
          if (!D) {
            D = !0
            var y = document.createElement('script');
            (y.type = 'text/javascript'),
            (y.src = h.szBasePath + 'SuperRender_10.js')
            var m = document.getElementsByTagName('head')[0]
            m.appendChild(y),
            (y.onload = y.onreadystatechange = function () {
              !this.readyState ||
                  this.readyState === 'loaded' ||
                  this.readyState === 'complete'
            })
            var g = document.createElement('script');
            (g.type = 'text/javascript'),
            (g.src = h.szBasePath + 'SuperRender_20.js')
            var R = document.getElementsByTagName('head')[0]
            R.appendChild(g),
            (g.onload = g.onreadystatechange = function () {
              !this.readyState ||
                  this.readyState === 'loaded' ||
                  this.readyState === 'complete'
            })
          }
          (this.convertErrorCode = function (t) {
            switch (t) {
              case 1:
                return r
              case 98:
                return o
              default:
                return t
            }
          }),
          (this.arrayBufferCopy = function (t) {
            var e = t.byteLength
            var n = new Uint8Array(e)
            var i = new Uint8Array(t)
            var r = 0
            for (r = 0; r < e; r++) n[r] = i[r]
            return n
          }),
          (this.inputDataFun = function () {
            var t
            var e = 0
            if (((h.bIsGetYUV = !1), h.bIsInputBufOver)) { t = new Uint8Array(1) } else {
              for (
                ;
                h.aInputDataLens.length > 0 &&
                  ((e += h.aInputDataLens.shift()), !(e > h.iInputDataLen));

              );
              t = h.aInputDataBuffer.splice(0, e)
            }
            var n = new Uint8Array(t)
            var i = { command: 'InputData', data: n.buffer, dataSize: e }
            h.bPlay &&
                (h.bPause
                  ? h.bOnebyOne && h.decodeWorker.postMessage(i, [i.data])
                  : h.decodeWorker.postMessage(i, [i.data])),
            (t = null),
            (n = null)
          }),
          (this.getPic = function (t, e) {
            if (this.decodeWorker == null || this.oSuperRender == null) { return a }
            if (!this.bPlay) return a
            if (!t || typeof t !== 'function') return o
            if (((this.dataCallBackFun = t), this.iZoomNum === 0)) {
              (this.stYUVRect.left = 0),
              (this.stYUVRect.top = 0),
              (this.stYUVRect.right = 0),
              (this.stYUVRect.bottom = 0)
            } else {
              if (this.iCanvasWidth === 0 || this.iCanvasHeight === 0) {
                (this.stYUVRect.left = 0),
                (this.stYUVRect.top = 0),
                (this.stYUVRect.right = 0),
                (this.stYUVRect.bottom = 0)
              } else {
                var n = this.nWidth / this.iCanvasWidth
                var i = this.nHeight / this.iCanvasHeight;
                (this.stYUVRect.left = Math.round(
                  this.stDisplayRect.left * n
                )),
                (this.stYUVRect.top = Math.round(
                  this.stDisplayRect.top * i
                )),
                (this.stYUVRect.right = Math.round(
                  this.stDisplayRect.right * n
                )),
                (this.stYUVRect.bottom = Math.round(
                  this.stDisplayRect.bottom * i
                ))
              }
              if (
                this.stYUVRect.right - this.stYUVRect.left < 32 ||
                  this.stYUVRect.bottom - this.stYUVRect.top < 32
              ) { return o }
            }
            if (this.aDisplayBuf == null) return a
            var s = this.arrayBufferCopy(this.aDisplayBuf)
            var u = {
              command: e,
              data: s.buffer,
              width: this.nWidth,
              height: this.nHeight,
              rect: this.stYUVRect
            }
            return this.decodeWorker.postMessage(u, [u.data]), r
          }),
          (this.createWorker = function (t) {
            if (window.Worker) {
              if (
                this.decodeWorker == null &&
                  ((this.decodeWorker = new Worker(
                    h.szBasePath + 'DecodeWorker.js'
                  )),
                  this.decodeWorker == null)
              ) { return f }
              this.decodeWorker.onmessage = function (e) {
                var n = null
                var i = e.data
                switch (i.function) {
                  case 'loaded':
                    (n = 'loaded'), t.setCallBack(t, 'loaded', 0, 0, !0)
                    break
                  case 'SetStreamOpenMode':
                    n = 'SetStreamOpenMode'
                    break
                  case 'OpenStream':
                    n = 'OpenStream'
                    break
                  case 'InputData':
                    (n = 'InputData'),
                    i.errorCode === s &&
                          ((h.bIsInputBufOver = !0), h.inputDataFun()),
                    i.errorCode === l && (h.bIsInputBufOver = !1)
                    break
                  case 'GetFrameData':
                    if (
                      ((n = 'GetFrameData'),
                      !h.bIsFirstFrame && i.errorCode === l)
                    ) {
                      (h.bIsInputBufOver = !1),
                      setTimeout(h.inputDataFun(), 5)
                      break
                    }
                    if (
                      (h.bIsInputBufOver
                        ? h.inputDataFun()
                        : i.type === 'videoType' &&
                            (h.aInputDataLens.length > 0 && h.bIsInput
                              ? (h.inputDataFun(), (h.bIsInput = !1))
                              : (h.bIsGetYUV = !0),
                            (h.bIsFirstFrame = !1)),
                      h.bVisibility)
                    ) {
                      switch (i.type) {
                        case 'videoType':
                          if (i.data == null || i.frameInfo == null) return o;
                          (h.bIsFirstFrame = !1),
                          (t.nWidth = i.frameInfo.width),
                          (t.nHeight = i.frameInfo.height)
                          var r = new Object();
                          (r.data = i.data),
                          (r.osdTime = i.osd),
                          t.aVideoFrameBuffer.push(r),
                          (r = null)
                          var a = t.aVideoFrameBuffer.length
                          if (
                            (a > P &&
                                (t.bOnebyOne ||
                                  t.aVideoFrameBuffer.splice(0, C)),
                            t.bOnebyOne && t.aVideoFrameBuffer.length >= S)
                          ) {
                            t.setCallBack(t, 'OnebyOne', 0, 0, !1),
                            (t.bIsFirstFrame = !0)
                            break
                          }
                          break
                        case 'audioType':
                          if (t.bPlaySound && !t.bPlayRateChange) {
                            for (
                              var f = new Uint8Array(i.data),
                                c = t.aAudioBuffer.length,
                                d = 0,
                                p = f.length;
                              d < p;
                              d++
                            ) { t.aAudioBuffer[c + d] = f[d] }
                            t.iAudioBufferSize++,
                            (f = null),
                            t.iAudioBufferSize >= _ &&
                                  (t.audioRenderer.Play(
                                    t.aAudioBuffer,
                                    t.aAudioBuffer.length,
                                    i.frameInfo
                                  ),
                                  t.aAudioBuffer.splice(
                                    0,
                                    t.aAudioBuffer.length
                                  ),
                                  (t.aAudioBuffer.length = 0),
                                  (t.iAudioBufferSize = 0))
                          }
                          break
                        case 'privateType':
                      }
                    }
                    break
                  case 'PlaySound':
                    n = 'PlaySound'
                    break
                  case 'GetJPEG':
                    n = 'GetJPEG'
                    var y = i.data
                    t.dataCallBackFun(y)
                    break
                  case 'GetBMP':
                    n = 'GetBMP'
                    var m = i.data
                    t.dataCallBackFun(m)
                }
                n !== 'GetFrameData'
                  ? t.setCallBack(
                    t,
                    n,
                    0,
                    t.convertErrorCode(i.errorCode),
                    !0
                  )
                  : u === i.errorCode &&
                      t.setCallBack(
                        t,
                        n,
                        0,
                        t.convertErrorCode(i.errorCode),
                        !0
                      )
              }
            }
          }),
          this.createWorker(h),
          (this.draw = function () {
            if (h.bPlay) {
              h.bPause || requestAnimationFrame(h.draw)
              var t = h.aVideoFrameBuffer.length
              if (
                (h.bOnebyOne &&
                    t <= b &&
                    h.setCallBack(h, 'OnebyOne', 0, l, !0),
                t > h.YUVBufferSize)
              ) {
                var e = h.aVideoFrameBuffer.shift()
                h.aDisplayBuf = e.data
                var n = new Uint8Array(h.aDisplayBuf)
                h.oSuperRender.SR_DisplayFrameData(h.nWidth, h.nHeight, n),
                (n = null),
                (h.szOSDTime = e.osdTime),
                (e = null)
              }
            } else {
              h.bPlay ||
                  (h.aVideoFrameBuffer.splice(0, h.aVideoFrameBuffer.length),
                  h.aAudioBuffer.splice(0, h.aAudioBuffer.length))
            }
          })
        }
        return (
          i(t, [
            {
              key: 'PlayM4_SetStreamOpenMode',
              value: function (t) {
                return t == null || void 0 === t
                  ? o
                  : t !== y && t !== m
                    ? o
                    : ((this.streamOpenMode = t), r)
              }
            },
            {
              key: 'PlayM4_OpenStream',
              value: function (t, e, n) {
                return this.decodeWorker == null
                  ? a
                  : t == null || e <= 0 || n <= 0
                    ? o
                    : ((this.bPlay = !1),
                    (this.bPause = !1),
                    (this.bOnebyOne = !1),
                    (this.bIsFirstFrame = !0),
                    (this.bIsGetYUV = !1),
                    (this.bIsInput = !1),
                    this.decodeWorker.postMessage({
                      command: 'SetStreamOpenMode',
                      data: this.streamOpenMode
                    }),
                    this.decodeWorker.postMessage({
                      command: 'OpenStream',
                      data: t,
                      dataSize: e,
                      bufPoolSize: n
                    }),
                    (this.bOpenStream = !0),
                    r)
              }
            },
            {
              key: 'PlayM4_CloseStream',
              value: function () {
                return this.decodeWorker === null || this.bOpenStream === !1
                  ? a
                  : (this.PlayM4_Stop(),
                  this.decodeWorker.postMessage({ command: 'CloseStream' }),
                  this.oSuperRender !== null &&
                      (this.oSuperRender.SR_Destroy(),
                      (this.oSuperRender = null)),
                  this.audioRenderer !== null &&
                      (this.audioRenderer.Stop(), (this.audioRenderer = null)),
                  this.aAudioBuffer.splice(0, this.aAudioBuffer.length),
                  this.aVideoFrameBuffer.splice(
                    0,
                    this.aVideoFrameBuffer.length
                  ),
                  this.aInputDataBuffer.splice(
                    0,
                    this.aInputDataBuffer.length
                  ),
                  this.aInputDataLens.splice(0, this.aInputDataLens.length),
                  (this.bOpenStream = !1),
                  (this.iAudioBufferSize = 0),
                  r)
              }
            },
            {
              key: 'PlayM4_Destroy',
              value: function () {
                return this.decodeWorker === null
                  ? r
                  : (this.PlayM4_CloseStream(),
                  this.decodeWorker.terminate(),
                  (this.decodeWorker = null),
                  r)
              }
            },
            {
              key: 'PlayM4_InputData',
              value: function (t, e) {
                if (this.decodeWorker === null || this.bOpenStream === !1) { return a }
                var n = this.aInputDataBuffer.length
                if (e === 4) {
                  var i = new Uint8Array(t.buffer)
                  if (i[0] === 1 && i[1] === 2 && i[2] === 3 && i[3] === 4) {
                    return (
                      this.bIsFirstFrame
                        ? this.inputDataFun()
                        : this.bIsGetYUV
                          ? this.inputDataFun()
                          : (this.bIsInput = !0),
                      (i = null),
                      r
                    )
                  }
                }
                if (n > this.iInputMaxBufSize) return s
                var o = null
                var l = e
                switch (this.streamOpenMode) {
                  case m:
                    (o = new Uint8Array(t.buffer)), this.aInputDataLens.push(e)
                    break
                  case y:
                    l = e + 4
                    var f = new Uint32Array([e])
                    var c = new Uint8Array(f.buffer);
                    (o = new Uint8Array(l)),
                    o.set(c, 0),
                    o.set(t, 4),
                    (f = null),
                    (c = null),
                    this.aInputDataLens.push(e + 4)
                    break
                  default:
                    return u
                }
                for (var h = 0; h < l; h++) this.aInputDataBuffer[n + h] = o[h]
                return (
                  (o = null),
                  this.bIsFirstFrame
                    ? this.inputDataFun()
                    : this.bIsGetYUV
                      ? this.inputDataFun()
                      : (this.bIsInput = !0),
                  r
                )
              }
            },
            {
              key: 'PlayM4_Play',
              value: function (t) {
                if (this.decodeWorker === null || this.bOpenStream === !1) { return a }
                if (t !== null && typeof t !== 'string') return o
                if (
                  (this.bOnebyOne &&
                    ((this.bPlayRateChange = !1),
                    (this.bOnebyOne = !1),
                    (this.bPause = !1),
                    this.draw()),
                  this.bPlay)
                ) { return r }
                if (this.oSuperRender == null) {
                  var e = document.getElementById(t)
                  var n = e.getContext('webgl2')
                  if (
                    (n
                      ? (this.oSuperRender = new SuperRender2(
                        t,
                        this.szBasePath
                      ))
                      : (this.oSuperRender = new SuperRender(
                        t,
                        this.szBasePath
                      )),
                    this.oSuperRender == null)
                  ) { return c }
                }
                return this.audioRenderer == null &&
                  ((this.audioRenderer = new AudioRenderer()),
                  this.audioRenderer == null)
                  ? c
                  : ((this.sCanvasId = t),
                  (this.bPlay = !0),
                  (this.bPause = !1),
                  (this.bOnebyOne = !1),
                  (this.bPlaySound = !1),
                  (this.bPlayRateChange = !1),
                  this.draw(),
                  r)
              }
            },
            {
              key: 'PlayM4_Stop',
              value: function () {
                return this.decodeWorker == null || this.oSuperRender == null
                  ? a
                  : this.bPlay
                    ? (this.bPlaySound &&
                        (this.PlayM4_StopSound(), (this.bPlaySound = !0)),
                    (this.bPlay = !1),
                    (this.bOnebyOne = !1),
                    (this.bPause = !1),
                    this.oSuperRender.SR_SetDisplayRect(null),
                    (this.iZoomNum = 0),
                    this.oSuperRender.SR_DisplayFrameData(
                      this.nWidth,
                      this.nHeight,
                      null
                    ),
                    r)
                    : a
              }
            },
            {
              key: 'PlayM4_PlayRate',
              value: function (t) {
                return this.decodeWorker == null
                  ? a
                  : (t === 1
                    ? (this.bPlayRateChange = !1)
                    : (this.bPlayRateChange = !0),
                  t < 1 && (t = 1),
                  (this.iInputDataLen = t * T),
                  r)
              }
            },
            {
              key: 'PlayM4_Pause',
              value: function (t) {
                return this.decodeWorker == null || this.oSuperRender == null
                  ? a
                  : this.bPlay
                    ? this.bOnebyOne
                      ? a
                      : typeof t !== 'boolean'
                        ? o
                        : ((this.bPause = t),
                        (this.bIsFirstFrame = !0),
                        t
                          ? this.bPlaySound &&
                              (this.PlayM4_StopSound(), (this.bPlaySound = !0))
                          : (this.bPlaySound && this.PlayM4_PlaySound(),
                          this.draw()),
                        r)
                    : a
              }
            },
            {
              key: 'PlayM4_OneByOne',
              value: function () {
                return this.decodeWorker == null || this.oSuperRender == null
                  ? a
                  : this.bPlay
                    ? ((this.iInputDataLen = T),
                    (this.bPause = !0),
                    (this.bOnebyOne = !0),
                    (this.bPlayRateChange = !0),
                    this.draw(),
                    r)
                    : a
              }
            },
            {
              key: 'PlayM4_PlaySound',
              value: function (t) {
                return this.decodeWorker === null || this.bOpenStream === !1
                  ? a
                  : t < 0 || t > 16
                    ? o
                    : this.audioRenderer == null &&
                      ((this.audioRenderer = new AudioRenderer()),
                      this.audioRenderer == null)
                      ? c
                      : (this.audioRenderer.SetWndNum(t),
                      (this.bPlaySound = !0),
                      r)
              }
            },
            {
              key: 'PlayM4_StopSound',
              value: function () {
                return this.decodeWorker == null || this.audioRenderer == null
                  ? a
                  : this.bPlaySound
                    ? ((this.bPlaySound = !1), r)
                    : a
              }
            },
            {
              key: 'PlayM4_SetDisplayBuf',
              value: function (t) {
                return this.decodeWorker == null
                  ? a
                  : t <= 0
                    ? o
                    : ((this.YUVBufferSize = t), r)
              }
            },
            {
              key: 'PlayM4_SetSecretKey',
              value: function (t, e, n) {
                if (this.decodeWorker == null || this.bOpenStream === !1) { return a }
                if (e == null) return o
                if (p === t) {
                  if (n !== 128) return o
                  if (e == null || void 0 === e) return o
                } else if (d !== t) return o
                return (
                  this.decodeWorker.postMessage({
                    command: 'SetSecretKey',
                    data: e,
                    nKeyType: t,
                    nKeyLen: n
                  }),
                  r
                )
              }
            },
            {
              key: 'PlayM4_SetDecodeFrameType',
              value: function (t) {
                return this.decodeWorker == null || this.oSuperRender == null
                  ? a
                  : t !== v && t !== g
                    ? o
                    : ((this.nDecFrameType = t),
                    this.decodeWorker.postMessage({
                      command: 'SetDecodeFrameType',
                      data: t
                    }),
                    r)
              }
            },
            {
              key: 'PlayM4_SetIFrameDecInterval',
              value: function (t) {
                return this.nDecFrameType !== g
                  ? a
                  : t < 0
                    ? o
                    : (this.decodeWorker.postMessage({
                      command: 'SetIFrameDecInterval',
                      data: t
                    }),
                    r)
              }
            },
            {
              key: 'PlayM4_SetDisplayRegion',
              value: function (t, e) {
                if (
                  this.decodeWorker === null ||
                  this.bPlay === !1 ||
                  this.oSuperRender === null
                ) { return a }
                if (this.canvasId === null) return a
                if (e === !0) {
                  if (t === null || void 0 === t) return o
                  if (
                    typeof t.left !== 'number' ||
                    typeof t.top !== 'number' ||
                    typeof t.right !== 'number' ||
                    typeof t.bottom !== 'number'
                  ) { return o }
                  if (t.right < 0 || t.left < 0 || t.top < 0 || t.bottom < 0) { return o }
                  var n = t.left
                  var i = t.right
                  var s = t.top
                  var u = t.bottom
                  if (
                    i - n < 16 ||
                    u - s < 16 ||
                    i - n > this.nWidth ||
                    u - s > this.nHeight
                  ) { return o }
                  var l = document
                    .getElementById(this.sCanvasId)
                    .getBoundingClientRect();
                  (this.iCanvasWidth = l.width),
                  (this.iCanvasHeight = l.height),
                  this.iZoomNum !== 0 &&
                      ((n =
                        Math.round(n / this.iRatio_x) +
                        this.stDisplayRect.left),
                      (s =
                        Math.round(s / this.iRatio_y) + this.stDisplayRect.top),
                      (i =
                        Math.round(i / this.iRatio_x) +
                        this.stDisplayRect.left),
                      (u =
                        Math.round(u / this.iRatio_y) +
                        this.stDisplayRect.top)),
                  (this.stDisplayRect = {
                    top: s,
                    left: n,
                    right: i,
                    bottom: u
                  }),
                  this.oSuperRender.SR_SetDisplayRect(this.stDisplayRect)
                  var f = i - n
                  var c = u - s;
                  (this.iRatio_x = this.iCanvasWidth / f),
                  (this.iRatio_y = this.iCanvasHeight / c),
                  this.iZoomNum++
                } else {
                  this.oSuperRender.SR_SetDisplayRect(null),
                  (this.iZoomNum = 0)
                }
                return (
                  (this.bPause || this.bOnebyOne || this.bPlayRateChange) &&
                    this.oSuperRender.SR_DisplayFrameData(
                      this.nWidth,
                      this.nHeight,
                      new Uint8Array(this.aDisplayBuf)
                    ),
                  r
                )
              }
            },
            {
              key: 'PlayM4_GetBMP',
              value: function (t) {
                return this.getPic(t, 'GetBMP')
              }
            },
            {
              key: 'PlayM4_GetJPEG',
              value: function (t) {
                return this.getPic(t, 'GetJPEG')
              }
            },
            {
              key: 'PlayM4_SetVolume',
              value: function (t) {
                return this.decodeWorker == null
                  ? a
                  : this.audioRenderer == null
                    ? a
                    : t < 0 || t > 100
                      ? o
                      : (this.audioRenderer.SetVolume(t / 100), r)
              }
            },
            {
              key: 'PlayM4_GetVolume',
              value: function (t) {
                if (this.decodeWorker == null) return a
                if (this.audioRenderer == null) return a
                if (t && typeof t === 'function') {
                  var e = this.audioRenderer.GetVolume()
                  return e === null ? h : (t(10 * Math.round(10 * e)), r)
                }
                return o
              }
            },
            {
              key: 'PlayM4_GetOSDTime',
              value: function (t) {
                return this.decodeWorker == null
                  ? a
                  : this.bPlay
                    ? t && typeof t === 'function'
                      ? (t(this.szOSDTime), r)
                      : o
                    : a
              }
            },
            {
              key: 'PlayM4_IsVisible',
              value: function (t) {
                return (this.bVisibility = t), r
              }
            },
            {
              key: 'PlayM4_GetSdkVersion',
              value: function () {
                return '07020114'
              }
            },
            {
              key: 'PlayM4_GetInputBufSize',
              value: function () {
                return this.aInputDataBuffer.length
              }
            },
            {
              key: 'PlayM4_SetInputBufSize',
              value: function (t) {
                return t > 0 ? ((this.iInputMaxBufSize = t), r) : o
              }
            },
            {
              key: 'PlayM4_GetYUVBufSize',
              value: function () {
                return this.aVideoFrameBuffer.length
              }
            },
            {
              key: 'PlayM4_ClearCanvas',
              value: function () {
                return this.oSuperRender == null
                  ? a
                  : (this.oSuperRender.SR_DisplayFrameData(
                    this.nWidth,
                    this.nHeight,
                    null
                  ),
                  r)
              }
            },
            {
              key: 'PlayM4_ReleaseInputBuffer',
              value: function () {
                return this.aInputDataBuffer === null
                  ? a
                  : (this.aInputDataBuffer.splice(
                    0,
                    this.aInputDataBuffer.length
                  ),
                  this.aInputDataLens.splice(0, this.aInputDataLens.length),
                  r)
              }
            },
            {
              key: 'PlayM4_GetDecodeFrameType',
              value: function () {
                return this.nDecFrameType
              }
            }
          ]),
          t
        )
      })()
    },
    function (t, e) {
      'use strict'
      Object.defineProperty(e, '__esModule', { value: !0 })
      var n =
        n ||
        (function (t, e) {
          var n = {}
          var i = (n.lib = {})
          var r = function () {}
          var o = (i.Base = {
            extend: function (t) {
              r.prototype = this
              var e = new r()
              return (
                t && e.mixIn(t),
                e.hasOwnProperty('init') ||
                    (e.init = function () {
                      e.$super.init.apply(this, arguments)
                    }),
                (e.init.prototype = e),
                (e.$super = this),
                e
              )
            },
            create: function () {
              var t = this.extend()
              return t.init.apply(t, arguments), t
            },
            init: function () {},
            mixIn: function (t) {
              for (var e in t) t.hasOwnProperty(e) && (this[e] = t[e])
              t.hasOwnProperty('toString') && (this.toString = t.toString)
            },
            clone: function () {
              return this.init.prototype.extend(this)
            }
          })
          var a = (i.WordArray = o.extend({
            init: function (t, n) {
              (t = this.words = t || []),
              (this.sigBytes = n != e ? n : 4 * t.length)
            },
            toString: function (t) {
              return (t || u).stringify(this)
            },
            concat: function (t) {
              var e = this.words
              var n = t.words
              var i = this.sigBytes
              if (((t = t.sigBytes), this.clamp(), i % 4)) {
                for (var r = 0; r < t; r++) {
                  e[(i + r) >>> 2] |=
                      ((n[r >>> 2] >>> (24 - 8 * (r % 4))) & 255) <<
                      (24 - 8 * ((i + r) % 4))
                }
              } else if (n.length > 65535) { for (r = 0; r < t; r += 4) e[(i + r) >>> 2] = n[r >>> 2] } else e.push.apply(e, n)
              return (this.sigBytes += t), this
            },
            clamp: function () {
              var e = this.words
              var n = this.sigBytes;
              (e[n >>> 2] &= 4294967295 << (32 - 8 * (n % 4))),
              (e.length = t.ceil(n / 4))
            },
            clone: function () {
              var t = o.clone.call(this)
              return (t.words = this.words.slice(0)), t
            },
            random: function (e) {
              for (var n = [], i = 0; i < e; i += 4) { n.push((4294967296 * t.random()) | 0) }
              return new a.init(n, e)
            }
          }))
          var s = (n.enc = {})
          var u = (s.Hex = {
            stringify: function (t) {
              var e = t.words
              t = t.sigBytes
              for (var n = [], i = 0; i < t; i++) {
                var r = (e[i >>> 2] >>> (24 - 8 * (i % 4))) & 255
                n.push((r >>> 4).toString(16)), n.push((15 & r).toString(16))
              }
              return n.join('')
            },
            parse: function (t) {
              for (var e = t.length, n = [], i = 0; i < e; i += 2) {
                n[i >>> 3] |=
                    parseInt(t.substr(i, 2), 16) << (24 - 4 * (i % 8))
              }
              return new a.init(n, e / 2)
            }
          })
          var l = (s.Latin1 = {
            stringify: function (t) {
              var e = t.words
              t = t.sigBytes
              for (var n = [], i = 0; i < t; i++) {
                n.push(
                  String.fromCharCode(
                    (e[i >>> 2] >>> (24 - 8 * (i % 4))) & 255
                  )
                )
              }
              return n.join('')
            },
            parse: function (t) {
              for (var e = t.length, n = [], i = 0; i < e; i++) { n[i >>> 2] |= (255 & t.charCodeAt(i)) << (24 - 8 * (i % 4)) }
              return new a.init(n, e)
            }
          })
          var f = (s.Utf8 = {
            stringify: function (t) {
              try {
                return decodeURIComponent(escape(l.stringify(t)))
              } catch (t) {
                throw Error('Malformed UTF-8 data')
              }
            },
            parse: function (t) {
              return l.parse(unescape(encodeURIComponent(t)))
            }
          })
          var c = (i.BufferedBlockAlgorithm = o.extend({
            reset: function () {
              (this._data = new a.init()), (this._nDataBytes = 0)
            },
            _append: function (t) {
              typeof t === 'string' && (t = f.parse(t)),
              this._data.concat(t),
              (this._nDataBytes += t.sigBytes)
            },
            _process: function (e) {
              var n = this._data
              var i = n.words
              var r = n.sigBytes
              var o = this.blockSize
              var s = r / (4 * o)
              var s = e ? t.ceil(s) : t.max((0 | s) - this._minBufferSize, 0)
              if (((e = s * o), (r = t.min(4 * e, r)), e)) {
                for (var u = 0; u < e; u += o) this._doProcessBlock(i, u);
                (u = i.splice(0, e)), (n.sigBytes -= r)
              }
              return new a.init(u, r)
            },
            clone: function () {
              var t = o.clone.call(this)
              return (t._data = this._data.clone()), t
            },
            _minBufferSize: 0
          }))
          i.Hasher = c.extend({
            cfg: o.extend(),
            init: function (t) {
              (this.cfg = this.cfg.extend(t)), this.reset()
            },
            reset: function () {
              c.reset.call(this), this._doReset()
            },
            update: function (t) {
              return this._append(t), this._process(), this
            },
            finalize: function (t) {
              return t && this._append(t), this._doFinalize()
            },
            blockSize: 16,
            _createHelper: function (t) {
              return function (e, n) {
                return new t.init(n).finalize(e)
              }
            },
            _createHmacHelper: function (t) {
              return function (e, n) {
                return new h.HMAC.init(t, n).finalize(e)
              }
            }
          })
          var h = (n.algo = {})
          return n
        })(Math)
      !(function () {
        var t = n
        var e = t.lib.WordArray
        t.enc.Base64 = {
          stringify: function (t) {
            var e = t.words
            var n = t.sigBytes
            var i = this._map
            t.clamp(), (t = [])
            for (var r = 0; r < n; r += 3) {
              for (
                var o =
                    (((e[r >>> 2] >>> (24 - 8 * (r % 4))) & 255) << 16) |
                    (((e[(r + 1) >>> 2] >>> (24 - 8 * ((r + 1) % 4))) & 255) <<
                      8) |
                    ((e[(r + 2) >>> 2] >>> (24 - 8 * ((r + 2) % 4))) & 255),
                  a = 0;
                a < 4 && r + 0.75 * a < n;
                a++
              ) { t.push(i.charAt((o >>> (6 * (3 - a))) & 63)) }
            }
            if ((e = i.charAt(64))) for (; t.length % 4;) t.push(e)
            return t.join('')
          },
          parse: function (t) {
            var n = t.length
            var i = this._map
            var r = i.charAt(64)
            r && ((r = t.indexOf(r)), r != -1 && (n = r))
            for (var r = [], o = 0, a = 0; a < n; a++) {
              if (a % 4) {
                var s = i.indexOf(t.charAt(a - 1)) << (2 * (a % 4))
                var u = i.indexOf(t.charAt(a)) >>> (6 - 2 * (a % 4));
                (r[o >>> 2] |= (s | u) << (24 - 8 * (o % 4))), o++
              }
            }
            return e.create(r, o)
          },
          _map:
            'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/='
        }
      })(),
      (function (t) {
        function e (t, e, n, i, r, o, a) {
          return (
            (t = t + ((e & n) | (~e & i)) + r + a),
            ((t << o) | (t >>> (32 - o))) + e
          )
        }
        function i (t, e, n, i, r, o, a) {
          return (
            (t = t + ((e & i) | (n & ~i)) + r + a),
            ((t << o) | (t >>> (32 - o))) + e
          )
        }
        function r (t, e, n, i, r, o, a) {
          return (
            (t = t + (e ^ n ^ i) + r + a), ((t << o) | (t >>> (32 - o))) + e
          )
        }
        function o (t, e, n, i, r, o, a) {
          return (
            (t = t + (n ^ (e | ~i)) + r + a),
            ((t << o) | (t >>> (32 - o))) + e
          )
        }
        for (
          var a = n,
            s = a.lib,
            u = s.WordArray,
            l = s.Hasher,
            s = a.algo,
            f = [],
            c = 0;
          c < 64;
          c++
        ) { f[c] = (4294967296 * t.abs(t.sin(c + 1))) | 0 }
        (s = s.MD5 = l.extend({
          _doReset: function () {
            this._hash = new u.init([
              1732584193,
              4023233417,
              2562383102,
              271733878
            ])
          },
          _doProcessBlock: function (t, n) {
            for (var a = 0; a < 16; a++) {
              var s = n + a
              var u = t[s]
              t[s] =
                  (16711935 & ((u << 8) | (u >>> 24))) |
                  (4278255360 & ((u << 24) | (u >>> 8)))
            }
            var a = this._hash.words
            var s = t[n + 0]
            var u = t[n + 1]
            var l = t[n + 2]
            var c = t[n + 3]
            var h = t[n + 4]
            var d = t[n + 5]
            var p = t[n + 6]
            var y = t[n + 7]
            var m = t[n + 8]
            var v = t[n + 9]
            var g = t[n + 10]
            var S = t[n + 11]
            var b = t[n + 12]
            var w = t[n + 13]
            var _ = t[n + 14]
            var P = t[n + 15]
            var C = a[0]
            var x = a[1]
            var T = a[2]
            var k = a[3]
            var C = e(C, x, T, k, s, 7, f[0])
            var k = e(k, C, x, T, u, 12, f[1])
            var T = e(T, k, C, x, l, 17, f[2])
            var x = e(x, T, k, C, c, 22, f[3])
            var C = e(C, x, T, k, h, 7, f[4])
            var k = e(k, C, x, T, d, 12, f[5])
            var T = e(T, k, C, x, p, 17, f[6])
            var x = e(x, T, k, C, y, 22, f[7])
            var C = e(C, x, T, k, m, 7, f[8])
            var k = e(k, C, x, T, v, 12, f[9])
            var T = e(T, k, C, x, g, 17, f[10])
            var x = e(x, T, k, C, S, 22, f[11])
            var C = e(C, x, T, k, b, 7, f[12])
            var k = e(k, C, x, T, w, 12, f[13])
            var T = e(T, k, C, x, _, 17, f[14])
            var x = e(x, T, k, C, P, 22, f[15])
            var C = i(C, x, T, k, u, 5, f[16])
            var k = i(k, C, x, T, p, 9, f[17])
            var T = i(T, k, C, x, S, 14, f[18])
            var x = i(x, T, k, C, s, 20, f[19])
            var C = i(C, x, T, k, d, 5, f[20])
            var k = i(k, C, x, T, g, 9, f[21])
            var T = i(T, k, C, x, P, 14, f[22])
            var x = i(x, T, k, C, h, 20, f[23])
            var C = i(C, x, T, k, v, 5, f[24])
            var k = i(k, C, x, T, _, 9, f[25])
            var T = i(T, k, C, x, c, 14, f[26])
            var x = i(x, T, k, C, m, 20, f[27])
            var C = i(C, x, T, k, w, 5, f[28])
            var k = i(k, C, x, T, l, 9, f[29])
            var T = i(T, k, C, x, y, 14, f[30])
            var x = i(x, T, k, C, b, 20, f[31])
            var C = r(C, x, T, k, d, 4, f[32])
            var k = r(k, C, x, T, m, 11, f[33])
            var T = r(T, k, C, x, S, 16, f[34])
            var x = r(x, T, k, C, _, 23, f[35])
            var C = r(C, x, T, k, u, 4, f[36])
            var k = r(k, C, x, T, h, 11, f[37])
            var T = r(T, k, C, x, y, 16, f[38])
            var x = r(x, T, k, C, g, 23, f[39])
            var C = r(C, x, T, k, w, 4, f[40])
            var k = r(k, C, x, T, s, 11, f[41])
            var T = r(T, k, C, x, c, 16, f[42])
            var x = r(x, T, k, C, p, 23, f[43])
            var C = r(C, x, T, k, v, 4, f[44])
            var k = r(k, C, x, T, b, 11, f[45])
            var T = r(T, k, C, x, P, 16, f[46])
            var x = r(x, T, k, C, l, 23, f[47])
            var C = o(C, x, T, k, s, 6, f[48])
            var k = o(k, C, x, T, y, 10, f[49])
            var T = o(T, k, C, x, _, 15, f[50])
            var x = o(x, T, k, C, d, 21, f[51])
            var C = o(C, x, T, k, b, 6, f[52])
            var k = o(k, C, x, T, c, 10, f[53])
            var T = o(T, k, C, x, g, 15, f[54])
            var x = o(x, T, k, C, u, 21, f[55])
            var C = o(C, x, T, k, m, 6, f[56])
            var k = o(k, C, x, T, P, 10, f[57])
            var T = o(T, k, C, x, p, 15, f[58])
            var x = o(x, T, k, C, w, 21, f[59])
            var C = o(C, x, T, k, h, 6, f[60])
            var k = o(k, C, x, T, S, 10, f[61])
            var T = o(T, k, C, x, l, 15, f[62])
            var x = o(x, T, k, C, v, 21, f[63]);
            (a[0] = (a[0] + C) | 0),
            (a[1] = (a[1] + x) | 0),
            (a[2] = (a[2] + T) | 0),
            (a[3] = (a[3] + k) | 0)
          },
          _doFinalize: function () {
            var e = this._data
            var n = e.words
            var i = 8 * this._nDataBytes
            var r = 8 * e.sigBytes
            n[r >>> 5] |= 128 << (24 - r % 32)
            var o = t.floor(i / 4294967296)
            for (
              n[(((r + 64) >>> 9) << 4) + 15] =
                  (16711935 & ((o << 8) | (o >>> 24))) |
                  (4278255360 & ((o << 24) | (o >>> 8))),
              n[(((r + 64) >>> 9) << 4) + 14] =
                    (16711935 & ((i << 8) | (i >>> 24))) |
                    (4278255360 & ((i << 24) | (i >>> 8))),
              e.sigBytes = 4 * (n.length + 1),
              this._process(),
              e = this._hash,
              n = e.words,
              i = 0;
              i < 4;
              i++
            ) {
              (r = n[i]),
              (n[i] =
                    (16711935 & ((r << 8) | (r >>> 24))) |
                    (4278255360 & ((r << 24) | (r >>> 8))))
            }
            return e
          },
          clone: function () {
            var t = l.clone.call(this)
            return (t._hash = this._hash.clone()), t
          }
        })),
        (a.MD5 = l._createHelper(s)),
        (a.HmacMD5 = l._createHmacHelper(s))
      })(Math),
      (function () {
        var t = n
        var e = t.lib
        var i = e.Base
        var r = e.WordArray
        var e = t.algo
        var o = (e.EvpKDF = i.extend({
          cfg: i.extend({ keySize: 4, hasher: e.MD5, iterations: 1 }),
          init: function (t) {
            this.cfg = this.cfg.extend(t)
          },
          compute: function (t, e) {
            for (
              var n = this.cfg,
                i = n.hasher.create(),
                o = r.create(),
                a = o.words,
                s = n.keySize,
                n = n.iterations;
              a.length < s;

            ) {
              u && i.update(u)
              var u = i.update(t).finalize(e)
              i.reset()
              for (var l = 1; l < n; l++) (u = i.finalize(u)), i.reset()
              o.concat(u)
            }
            return (o.sigBytes = 4 * s), o
          }
        }))
        t.EvpKDF = function (t, e, n) {
          return o.create(n).compute(t, e)
        }
      })(),
      n.lib.Cipher ||
          (function (t) {
            var e = n
            var i = e.lib
            var r = i.Base
            var o = i.WordArray
            var a = i.BufferedBlockAlgorithm
            var s = e.enc.Base64
            var u = e.algo.EvpKDF
            var l = (i.Cipher = a.extend({
              cfg: r.extend(),
              createEncryptor: function (t, e) {
                return this.create(this._ENC_XFORM_MODE, t, e)
              },
              createDecryptor: function (t, e) {
                return this.create(this._DEC_XFORM_MODE, t, e)
              },
              init: function (t, e, n) {
                (this.cfg = this.cfg.extend(n)),
                (this._xformMode = t),
                (this._key = e),
                this.reset()
              },
              reset: function () {
                a.reset.call(this), this._doReset()
              },
              process: function (t) {
                return this._append(t), this._process()
              },
              finalize: function (t) {
                return t && this._append(t), this._doFinalize()
              },
              keySize: 4,
              ivSize: 4,
              _ENC_XFORM_MODE: 1,
              _DEC_XFORM_MODE: 2,
              _createHelper: function (t) {
                return {
                  encrypt: function (e, n, i) {
                    return (typeof n === 'string' ? y : p).encrypt(t, e, n, i)
                  },
                  decrypt: function (e, n, i) {
                    return (typeof n === 'string' ? y : p).decrypt(t, e, n, i)
                  }
                }
              }
            }))
            i.StreamCipher = l.extend({
              _doFinalize: function () {
                return this._process(!0)
              },
              blockSize: 1
            })
            var f = (e.mode = {})
            var c = function (e, n, i) {
              var r = this._iv
              r ? (this._iv = t) : (r = this._prevBlock)
              for (var o = 0; o < i; o++) e[n + o] ^= r[o]
            }
            var h = (i.BlockCipherMode = r.extend({
              createEncryptor: function (t, e) {
                return this.Encryptor.create(t, e)
              },
              createDecryptor: function (t, e) {
                return this.Decryptor.create(t, e)
              },
              init: function (t, e) {
                (this._cipher = t), (this._iv = e)
              }
            })).extend();
            (h.Encryptor = h.extend({
              processBlock: function (t, e) {
                var n = this._cipher
                var i = n.blockSize
                c.call(this, t, e, i),
                n.encryptBlock(t, e),
                (this._prevBlock = t.slice(e, e + i))
              }
            })),
            (h.Decryptor = h.extend({
              processBlock: function (t, e) {
                var n = this._cipher
                var i = n.blockSize
                var r = t.slice(e, e + i)
                n.decryptBlock(t, e),
                c.call(this, t, e, i),
                (this._prevBlock = r)
              }
            })),
            (f = f.CBC = h),
            (h = (e.pad = {}).Pkcs7 = {
              pad: function (t, e) {
                for (
                  var n = 4 * e,
                    n = n - t.sigBytes % n,
                    i = (n << 24) | (n << 16) | (n << 8) | n,
                    r = [],
                    a = 0;
                  a < n;
                  a += 4
                ) { r.push(i) }
                (n = o.create(r, n)), t.concat(n)
              },
              unpad: function (t) {
                t.sigBytes -= 255 & t.words[(t.sigBytes - 1) >>> 2]
              }
            }),
            (i.BlockCipher = l.extend({
              cfg: l.cfg.extend({ mode: f, padding: h }),
              reset: function () {
                l.reset.call(this)
                var t = this.cfg
                var e = t.iv
                var t = t.mode
                if (this._xformMode == this._ENC_XFORM_MODE) { var n = t.createEncryptor } else (n = t.createDecryptor), (this._minBufferSize = 1)
                this._mode = n.call(t, this, e && e.words)
              },
              _doProcessBlock: function (t, e) {
                this._mode.processBlock(t, e)
              },
              _doFinalize: function () {
                var t = this.cfg.padding
                if (this._xformMode == this._ENC_XFORM_MODE) {
                  t.pad(this._data, this.blockSize)
                  var e = this._process(!0)
                } else (e = this._process(!0)), t.unpad(e)
                return e
              },
              blockSize: 4
            }))
            var d = (i.CipherParams = r.extend({
              init: function (t) {
                this.mixIn(t)
              },
              toString: function (t) {
                return (t || this.formatter).stringify(this)
              }
            }))
            var f = ((e.format = {}).OpenSSL = {
              stringify: function (t) {
                var e = t.ciphertext
                return (
                  (t = t.salt),
                  (t
                    ? o
                      .create([1398893684, 1701076831])
                      .concat(t)
                      .concat(e)
                    : e
                  ).toString(s)
                )
              },
              parse: function (t) {
                t = s.parse(t)
                var e = t.words
                if (e[0] == 1398893684 && e[1] == 1701076831) {
                  var n = o.create(e.slice(2, 4))
                  e.splice(0, 4), (t.sigBytes -= 16)
                }
                return d.create({ ciphertext: t, salt: n })
              }
            })
            var p = (i.SerializableCipher = r.extend({
              cfg: r.extend({ format: f }),
              encrypt: function (t, e, n, i) {
                i = this.cfg.extend(i)
                var r = t.createEncryptor(n, i)
                return (
                  (e = r.finalize(e)),
                  (r = r.cfg),
                  d.create({
                    ciphertext: e,
                    key: n,
                    iv: r.iv,
                    algorithm: t,
                    mode: r.mode,
                    padding: r.padding,
                    blockSize: t.blockSize,
                    formatter: i.format
                  })
                )
              },
              decrypt: function (t, e, n, i) {
                return (
                  (i = this.cfg.extend(i)),
                  (e = this._parse(e, i.format)),
                  t.createDecryptor(n, i).finalize(e.ciphertext)
                )
              },
              _parse: function (t, e) {
                return typeof t === 'string' ? e.parse(t, this) : t
              }
            }))
            var e = ((e.kdf = {}).OpenSSL = {
              execute: function (t, e, n, i) {
                return (
                  i || (i = o.random(8)),
                  (t = u.create({ keySize: e + n }).compute(t, i)),
                  (n = o.create(t.words.slice(e), 4 * n)),
                  (t.sigBytes = 4 * e),
                  d.create({ key: t, iv: n, salt: i })
                )
              }
            })
            var y = (i.PasswordBasedCipher = p.extend({
              cfg: p.cfg.extend({ kdf: e }),
              encrypt: function (t, e, n, i) {
                return (
                  (i = this.cfg.extend(i)),
                  (n = i.kdf.execute(n, t.keySize, t.ivSize)),
                  (i.iv = n.iv),
                  (t = p.encrypt.call(this, t, e, n.key, i)),
                  t.mixIn(n),
                  t
                )
              },
              decrypt: function (t, e, n, i) {
                return (
                  (i = this.cfg.extend(i)),
                  (e = this._parse(e, i.format)),
                  (n = i.kdf.execute(n, t.keySize, t.ivSize, e.salt)),
                  (i.iv = n.iv),
                  p.decrypt.call(this, t, e, n.key, i)
                )
              }
            }))
          })(),
      (function () {
        for (
          var t = n,
            e = t.lib.BlockCipher,
            i = t.algo,
            r = [],
            o = [],
            a = [],
            s = [],
            u = [],
            l = [],
            f = [],
            c = [],
            h = [],
            d = [],
            p = [],
            y = 0;
          y < 256;
          y++
        ) { p[y] = y < 128 ? y << 1 : (y << 1) ^ 283 }
        for (var m = 0, v = 0, y = 0; y < 256; y++) {
          var g = v ^ (v << 1) ^ (v << 2) ^ (v << 3) ^ (v << 4)
          var g = (g >>> 8) ^ (255 & g) ^ 99;
          (r[m] = g), (o[g] = m)
          var S = p[m]
          var b = p[S]
          var w = p[b]
          var _ = (257 * p[g]) ^ (16843008 * g);
          (a[m] = (_ << 24) | (_ >>> 8)),
          (s[m] = (_ << 16) | (_ >>> 16)),
          (u[m] = (_ << 8) | (_ >>> 24)),
          (l[m] = _),
          (_ = (16843009 * w) ^ (65537 * b) ^ (257 * S) ^ (16843008 * m)),
          (f[g] = (_ << 24) | (_ >>> 8)),
          (c[g] = (_ << 16) | (_ >>> 16)),
          (h[g] = (_ << 8) | (_ >>> 24)),
          (d[g] = _),
          m ? ((m = S ^ p[p[p[w ^ S]]]), (v ^= p[p[v]])) : (m = v = 1)
        }
        var P = [0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54]
        var i = (i.AES = e.extend({
          _doReset: function () {
            for (
              var t = this._key,
                e = t.words,
                n = t.sigBytes / 4,
                t = 4 * ((this._nRounds = n + 6) + 1),
                i = (this._keySchedule = []),
                o = 0;
              o < t;
              o++
            ) {
              if (o < n) i[o] = e[o]
              else {
                var a = i[o - 1]
                o % n
                  ? n > 6 &&
                        o % n == 4 &&
                        (a =
                          (r[a >>> 24] << 24) |
                          (r[(a >>> 16) & 255] << 16) |
                          (r[(a >>> 8) & 255] << 8) |
                          r[255 & a])
                  : ((a = (a << 8) | (a >>> 24)),
                  (a =
                          (r[a >>> 24] << 24) |
                          (r[(a >>> 16) & 255] << 16) |
                          (r[(a >>> 8) & 255] << 8) |
                          r[255 & a]),
                  (a ^= P[(o / n) | 0] << 24)),
                (i[o] = i[o - n] ^ a)
              }
            }
            for (e = this._invKeySchedule = [], n = 0; n < t; n++) {
              (o = t - n),
              (a = n % 4 ? i[o] : i[o - 4]),
              (e[n] =
                      n < 4 || o <= 4
                        ? a
                        : f[r[a >>> 24]] ^
                          c[r[(a >>> 16) & 255]] ^
                          h[r[(a >>> 8) & 255]] ^
                          d[r[255 & a]])
            }
          },
          encryptBlock: function (t, e) {
            this._doCryptBlock(t, e, this._keySchedule, a, s, u, l, r)
          },
          decryptBlock: function (t, e) {
            var n = t[e + 1];
            (t[e + 1] = t[e + 3]),
            (t[e + 3] = n),
            this._doCryptBlock(t, e, this._invKeySchedule, f, c, h, d, o),
            (n = t[e + 1]),
            (t[e + 1] = t[e + 3]),
            (t[e + 3] = n)
          },
          _doCryptBlock: function (t, e, n, i, r, o, a, s) {
            for (
              var u = this._nRounds,
                l = t[e] ^ n[0],
                f = t[e + 1] ^ n[1],
                c = t[e + 2] ^ n[2],
                h = t[e + 3] ^ n[3],
                d = 4,
                p = 1;
              p < u;
              p++
            ) {
              var y =
                      i[l >>> 24] ^
                      r[(f >>> 16) & 255] ^
                      o[(c >>> 8) & 255] ^
                      a[255 & h] ^
                      n[d++]
            }
            var m =
                      i[f >>> 24] ^
                      r[(c >>> 16) & 255] ^
                      o[(h >>> 8) & 255] ^
                      a[255 & l] ^
                      n[d++]
            var v =
                      i[c >>> 24] ^
                      r[(h >>> 16) & 255] ^
                      o[(l >>> 8) & 255] ^
                      a[255 & f] ^
                      n[d++]
            var h =
                      i[h >>> 24] ^
                      r[(l >>> 16) & 255] ^
                      o[(f >>> 8) & 255] ^
                      a[255 & c] ^
                      n[d++]
            var l = y
            var f = m
            var c = v;
            (y =
                  ((s[l >>> 24] << 24) |
                    (s[(f >>> 16) & 255] << 16) |
                    (s[(c >>> 8) & 255] << 8) |
                    s[255 & h]) ^
                  n[d++]),
            (m =
                    ((s[f >>> 24] << 24) |
                      (s[(c >>> 16) & 255] << 16) |
                      (s[(h >>> 8) & 255] << 8) |
                      s[255 & l]) ^
                    n[d++]),
            (v =
                    ((s[c >>> 24] << 24) |
                      (s[(h >>> 16) & 255] << 16) |
                      (s[(l >>> 8) & 255] << 8) |
                      s[255 & f]) ^
                    n[d++]),
            (h =
                    ((s[h >>> 24] << 24) |
                      (s[(l >>> 16) & 255] << 16) |
                      (s[(f >>> 8) & 255] << 8) |
                      s[255 & c]) ^
                    n[d++]),
            (t[e] = y),
            (t[e + 1] = m),
            (t[e + 2] = v),
            (t[e + 3] = h)
          },
          keySize: 8
        }))
        t.AES = e._createHelper(i)
      })(),
      (e.default = n)
    },
    function (t, e) {
      'use strict'
      function n (t, e, n) {
        t != null &&
          (typeof t === 'number'
            ? this.fromNumber(t, e, n)
            : e == null && typeof t !== 'string'
              ? this.fromString(t, 256)
              : this.fromString(t, e))
      }
      function r () {
        return new n(null)
      }
      function o (t, e, n, i, r, o) {
        for (; --o >= 0;) {
          var a = e * this[t++] + n[i] + r
          var r = Math.floor(a / 67108864)
          n[i++] = 67108863 & a
        }
        return r
      }
      function a (t, e, n, i, r, o) {
        var a = 32767 & e
        for (e >>= 15; --o >= 0;) {
          var s = 32767 & this[t]
          var u = this[t++] >> 15
          var l = e * s + u * a
          var s = a * s + ((32767 & l) << 15) + n[i] + (1073741823 & r)
          var r = (s >>> 30) + (l >>> 15) + e * u + (r >>> 30)
          n[i++] = 1073741823 & s
        }
        return r
      }
      function s (t, e, n, i, r, o) {
        var a = 16383 & e
        for (e >>= 14; --o >= 0;) {
          var s = 16383 & this[t]
          var u = this[t++] >> 14
          var l = e * s + u * a
          var s = a * s + ((16383 & l) << 14) + n[i] + r
          var r = (s >> 28) + (l >> 14) + e * u
          n[i++] = 268435455 & s
        }
        return r
      }
      function u (t) {
        return Ge.charAt(t)
      }
      function l (t, e) {
        var n = Ve[t.charCodeAt(e)]
        return n == null ? -1 : n
      }
      function f (t) {
        for (var e = this.t - 1; e >= 0; --e) t[e] = this[e];
        (t.t = this.t), (t.s = this.s)
      }
      function c (t) {
        (this.t = 1),
        (this.s = t < 0 ? -1 : 0),
        t > 0 ? (this[0] = t) : t < -1 ? (this[0] = t + DV) : (this.t = 0)
      }
      function h (t) {
        var e = r()
        return e.fromInt(t), e
      }
      function d (t, e) {
        var i
        if (e == 16) i = 4
        else if (e == 8) i = 3
        else if (e == 256) i = 8
        else if (e == 2) i = 1
        else if (e == 32) i = 5
        else {
          if (e != 4) return void this.fromRadix(t, e)
          i = 2
        }
        this.s = this.t = 0
        for (var r = t.length, o = !1, a = 0; --r >= 0;) {
          var s = i == 8 ? 255 & t[r] : l(t, r)
          s < 0
            ? t.charAt(r) == '-' && (o = !0)
            : ((o = !1),
            a == 0
              ? (this[this.t++] = s)
              : a + i > this.DB
                ? ((this[this.t - 1] |=
                      (s & ((1 << (this.DB - a)) - 1)) << a),
                (this[this.t++] = s >> (this.DB - a)))
                : (this[this.t - 1] |= s << a),
            (a += i),
            a >= this.DB && (a -= this.DB))
        }
        i == 8 &&
          (128 & t[0]) != 0 &&
          ((this.s = -1),
          a > 0 && (this[this.t - 1] |= ((1 << (this.DB - a)) - 1) << a)),
        this.clamp(),
        o && n.ZERO.subTo(this, this)
      }
      function p () {
        for (var t = this.s & this.DM; this.t > 0 && this[this.t - 1] == t;) { --this.t }
      }
      function y (t) {
        if (this.s < 0) return '-' + this.negate().toString(t)
        if (t == 16) t = 4
        else if (t == 8) t = 3
        else if (t == 2) t = 1
        else if (t == 32) t = 5
        else if (t == 64) t = 6
        else {
          if (t != 4) return this.toRadix(t)
          t = 2
        }
        var e
        var n = (1 << t) - 1
        var i = !1
        var r = ''
        var o = this.t
        var a = this.DB - (o * this.DB) % t
        if (o-- > 0) {
          for (
            a < this.DB && (e = this[o] >> a) > 0 && ((i = !0), (r = u(e)));
            o >= 0;

          ) {
            a < t
              ? ((e = (this[o] & ((1 << a) - 1)) << (t - a)),
              (e |= this[--o] >> (a += this.DB - t)))
              : ((e = (this[o] >> (a -= t)) & n),
              a <= 0 && ((a += this.DB), --o)),
            e > 0 && (i = !0),
            i && (r += u(e))
          }
        }
        return i ? r : '0'
      }
      function m () {
        var t = r()
        return n.ZERO.subTo(this, t), t
      }
      function g () {
        return this.s < 0 ? this.negate() : this
      }
      function S (t) {
        var e = this.s - t.s
        if (e != 0) return e
        var n = this.t
        var e = n - t.t
        if (e != 0) return e
        for (; --n >= 0;) if ((e = this[n] - t[n]) != 0) return e
        return 0
      }
      function b (t) {
        var e
        var n = 1
        return (
          (e = t >>> 16) != 0 && ((t = e), (n += 16)),
          (e = t >> 8) != 0 && ((t = e), (n += 8)),
          (e = t >> 4) != 0 && ((t = e), (n += 4)),
          (e = t >> 2) != 0 && ((t = e), (n += 2)),
          t >> 1 != 0 && (n += 1),
          n
        )
      }
      function w () {
        return this.t <= 0
          ? 0
          : this.DB * (this.t - 1) + b(this[this.t - 1] ^ (this.s & this.DM))
      }
      function _ (t, e) {
        var n
        for (n = this.t - 1; n >= 0; --n) e[n + t] = this[n]
        for (n = t - 1; n >= 0; --n) e[n] = 0;
        (e.t = this.t + t), (e.s = this.s)
      }
      function P (t, e) {
        for (var n = t; n < this.t; ++n) e[n - t] = this[n];
        (e.t = Math.max(this.t - t, 0)), (e.s = this.s)
      }
      function C (t, e) {
        var n
        var i = t % this.DB
        var r = this.DB - i
        var o = (1 << r) - 1
        var a = Math.floor(t / this.DB)
        var s = (this.s << i) & this.DM
        for (n = this.t - 1; n >= 0; --n) { (e[n + a + 1] = (this[n] >> r) | s), (s = (this[n] & o) << i) }
        for (n = a - 1; n >= 0; --n) e[n] = 0;
        (e[a] = s), (e.t = this.t + a + 1), (e.s = this.s), e.clamp()
      }
      function x (t, e) {
        e.s = this.s
        var n = Math.floor(t / this.DB)
        if (n >= this.t) e.t = 0
        else {
          var i = t % this.DB
          var r = this.DB - i
          var o = (1 << i) - 1
          e[0] = this[n] >> i
          for (var a = n + 1; a < this.t; ++a) { (e[a - n - 1] |= (this[a] & o) << r), (e[a - n] = this[a] >> i) }
          i > 0 && (e[this.t - n - 1] |= (this.s & o) << r),
          (e.t = this.t - n),
          e.clamp()
        }
      }
      function T (t, e) {
        for (var n = 0, i = 0, r = Math.min(t.t, this.t); n < r;) { (i += this[n] - t[n]), (e[n++] = i & this.DM), (i >>= this.DB) }
        if (t.t < this.t) {
          for (i -= t.s; n < this.t;) { (i += this[n]), (e[n++] = i & this.DM), (i >>= this.DB) }
          i += this.s
        } else {
          for (i += this.s; n < t.t;) { (i -= t[n]), (e[n++] = i & this.DM), (i >>= this.DB) }
          i -= t.s
        }
        (e.s = i < 0 ? -1 : 0),
        i < -1 ? (e[n++] = this.DV + i) : i > 0 && (e[n++] = i),
        (e.t = n),
        e.clamp()
      }
      function k (t, e) {
        var i = this.abs()
        var r = t.abs()
        var o = i.t
        for (e.t = o + r.t; --o >= 0;) e[o] = 0
        for (o = 0; o < r.t; ++o) e[o + i.t] = i.am(0, r[o], e, o, 0, i.t);
        (e.s = 0), e.clamp(), this.s != t.s && n.ZERO.subTo(e, e)
      }
      function D (t) {
        for (var e = this.abs(), n = (t.t = 2 * e.t); --n >= 0;) t[n] = 0
        for (n = 0; n < e.t - 1; ++n) {
          var i = e.am(n, e[n], t, 2 * n, 0, 1);
          (t[n + e.t] += e.am(n + 1, 2 * e[n], t, 2 * n + 1, i, e.t - n - 1)) >=
            e.DV && ((t[n + e.t] -= e.DV), (t[n + e.t + 1] = 1))
        }
        t.t > 0 && (t[t.t - 1] += e.am(n, e[n], t, 2 * n, 0, 1)),
        (t.s = 0),
        t.clamp()
      }
      function M (t, e, i) {
        var o = t.abs()
        if (!(o.t <= 0)) {
          var a = this.abs()
          if (a.t < o.t) e != null && e.fromInt(0), i != null && this.copyTo(i)
          else {
            i == null && (i = r())
            var s = r()
            var u = this.s
            var t = t.s
            var l = this.DB - b(o[o.t - 1])
            if (
              (l > 0
                ? (o.lShiftTo(l, s), a.lShiftTo(l, i))
                : (o.copyTo(s), a.copyTo(i)),
              (o = s.t),
              (a = s[o - 1]),
              a != 0)
            ) {
              var f = a * (1 << this.F1) + (o > 1 ? s[o - 2] >> this.F2 : 0)
              var c = this.FV / f
              var f = (1 << this.F1) / f
              var h = 1 << this.F2
              var d = i.t
              var p = d - o
              var y = e == null ? r() : e
              for (
                s.dlShiftTo(p, y),
                i.compareTo(y) >= 0 && ((i[i.t++] = 1), i.subTo(y, i)),
                n.ONE.dlShiftTo(o, y),
                y.subTo(s, s);
                s.t < o;

              ) { s[s.t++] = 0 }
              for (; --p >= 0;) {
                var m =
                  i[--d] == a
                    ? this.DM
                    : Math.floor(i[d] * c + (i[d - 1] + h) * f)
                if ((i[d] += s.am(0, m, i, p, 0, o)) < m) {
                  for (s.dlShiftTo(p, y), i.subTo(y, i); i[d] < --m;) { i.subTo(y, i) }
                }
              }
              e != null && (i.drShiftTo(o, e), u != t && n.ZERO.subTo(e, e)),
              (i.t = o),
              i.clamp(),
              l > 0 && i.rShiftTo(l, i),
              u < 0 && n.ZERO.subTo(i, i)
            }
          }
        }
      }
      function R (t) {
        var e = r()
        return (
          this.abs().divRemTo(t, null, e),
          this.s < 0 && e.compareTo(n.ZERO) > 0 && t.subTo(e, e),
          e
        )
      }
      function E (t) {
        this.m = t
      }
      function z (t) {
        return t.s < 0 || t.compareTo(this.m) >= 0 ? t.mod(this.m) : t
      }
      function I (t) {
        return t
      }
      function A (t) {
        t.divRemTo(this.m, null, t)
      }
      function F (t, e, n) {
        t.multiplyTo(e, n), this.reduce(n)
      }
      function O (t, e) {
        t.squareTo(e), this.reduce(e)
      }
      function B () {
        if (this.t < 1) return 0
        var t = this[0]
        if ((1 & t) == 0) return 0
        var e = 3 & t
        var e = (e * (2 - (15 & t) * e)) & 15
        var e = (e * (2 - (255 & t) * e)) & 255
        var e = (e * (2 - (((65535 & t) * e) & 65535))) & 65535
        var e = (e * (2 - (t * e) % this.DV)) % this.DV
        return e > 0 ? this.DV - e : -e
      }
      function L (t) {
        (this.m = t),
        (this.mp = t.invDigit()),
        (this.mpl = 32767 & this.mp),
        (this.mph = this.mp >> 15),
        (this.um = (1 << (t.DB - 15)) - 1),
        (this.mt2 = 2 * t.t)
      }
      function N (t) {
        var e = r()
        return (
          t.abs().dlShiftTo(this.m.t, e),
          e.divRemTo(this.m, null, e),
          t.s < 0 && e.compareTo(n.ZERO) > 0 && this.m.subTo(e, e),
          e
        )
      }
      function q (t) {
        var e = r()
        return t.copyTo(e), this.reduce(e), e
      }
      function H (t) {
        for (; t.t <= this.mt2;) t[t.t++] = 0
        for (var e = 0; e < this.m.t; ++e) {
          var n = 32767 & t[e]
          var i =
              (n * this.mpl +
                (((n * this.mph + (t[e] >> 15) * this.mpl) & this.um) << 15)) &
              t.DM
          var n = e + this.m.t
          for (t[n] += this.m.am(0, i, t, e, 0, this.m.t); t[n] >= t.DV;) { (t[n] -= t.DV), t[++n]++ }
        }
        t.clamp(),
        t.drShiftTo(this.m.t, t),
        t.compareTo(this.m) >= 0 && t.subTo(this.m, t)
      }
      function j (t, e) {
        t.squareTo(e), this.reduce(e)
      }
      function W (t, e, n) {
        t.multiplyTo(e, n), this.reduce(n)
      }
      function U () {
        return (this.t > 0 ? 1 & this[0] : this.s) == 0
      }
      function G (t, e) {
        if (t > 4294967295 || t < 1) return n.ONE
        var i = r()
        var o = r()
        var a = e.convert(this)
        var s = b(t) - 1
        for (a.copyTo(i); --s >= 0;) {
          if ((e.sqrTo(i, o), (t & (1 << s)) > 0)) e.mulTo(o, a, i)
          else { var u = i }
          var i = o
          var o = u
        }
        return e.revert(i)
      }
      function V (t, e) {
        var n
        return (
          (n = t < 256 || e.isEven() ? new E(e) : new L(e)), this.exp(t, n)
        )
      }
      function Y () {
        var t = r()
        return this.copyTo(t), t
      }
      function X () {
        if (this.s < 0) {
          if (this.t == 1) return this[0] - this.DV
          if (this.t == 0) return -1
        } else {
          if (this.t == 1) return this[0]
          if (this.t == 0) return 0
        }
        return ((this[1] & ((1 << (32 - this.DB)) - 1)) << this.DB) | this[0]
      }
      function J () {
        return this.t == 0 ? this.s : (this[0] << 24) >> 24
      }
      function $ () {
        return this.t == 0 ? this.s : (this[0] << 16) >> 16
      }
      function K (t) {
        return Math.floor(Math.LN2 * this.DB / Math.log(t))
      }
      function Z () {
        return this.s < 0
          ? -1
          : this.t <= 0 || (this.t == 1 && this[0] <= 0)
            ? 0
            : 1
      }
      function Q (t) {
        if ((t == null && (t = 10), this.signum() == 0 || t < 2 || t > 36)) { return '0' }
        var e = this.chunkSize(t)
        var e = Math.pow(t, e)
        var n = h(e)
        var i = r()
        var o = r()
        var a = ''
        for (this.divRemTo(n, i, o); i.signum() > 0;) {
          (a = (e + o.intValue()).toString(t).substr(1) + a),
          i.divRemTo(n, i, o)
        }
        return o.intValue().toString(t) + a
      }
      function tt (t, e) {
        this.fromInt(0), e == null && (e = 10)
        for (
          var i = this.chunkSize(e),
            r = Math.pow(e, i),
            o = !1,
            a = 0,
            s = 0,
            u = 0;
          u < t.length;
          ++u
        ) {
          var f = l(t, u)
          f < 0
            ? t.charAt(u) == '-' && this.signum() == 0 && (o = !0)
            : ((s = e * s + f),
            ++a >= i &&
                (this.dMultiply(r), this.dAddOffset(s, 0), (s = a = 0)))
        }
        a > 0 && (this.dMultiply(Math.pow(e, a)), this.dAddOffset(s, 0)),
        o && n.ZERO.subTo(this, this)
      }
      function et (t, e, i) {
        if (typeof e === 'number') {
          if (t < 2) this.fromInt(1)
          else {
            for (
              this.fromNumber(t, i),
              this.testBit(t - 1) ||
                  this.bitwiseTo(n.ONE.shiftLeft(t - 1), lt, this),
              this.isEven() && this.dAddOffset(1, 0);
              !this.isProbablePrime(e);

            ) {
              this.dAddOffset(2, 0),
              this.bitLength() > t &&
                  this.subTo(n.ONE.shiftLeft(t - 1), this)
            }
          }
        } else {
          var i = []
          var r = 7 & t;
          (i.length = (t >> 3) + 1),
          e.nextBytes(i),
          r > 0 ? (i[0] &= (1 << r) - 1) : (i[0] = 0),
          this.fromString(i, 256)
        }
      }
      function nt () {
        var t = this.t
        var e = []
        e[0] = this.s
        var n
        var i = this.DB - (t * this.DB) % 8
        var r = 0
        if (t-- > 0) {
          for (
            i < this.DB &&
            (n = this[t] >> i) != (this.s & this.DM) >> i &&
            (e[r++] = n | (this.s << (this.DB - i)));
            t >= 0;

          ) {
            i < 8
              ? ((n = (this[t] & ((1 << i) - 1)) << (8 - i)),
              (n |= this[--t] >> (i += this.DB - 8)))
              : ((n = (this[t] >> (i -= 8)) & 255),
              i <= 0 && ((i += this.DB), --t)),
            (128 & n) != 0 && (n |= -256),
            r == 0 && (128 & this.s) != (128 & n) && ++r,
            (r > 0 || n != this.s) && (e[r++] = n)
          }
        }
        return e
      }
      function it (t) {
        return this.compareTo(t) == 0
      }
      function rt (t) {
        return this.compareTo(t) < 0 ? this : t
      }
      function ot (t) {
        return this.compareTo(t) > 0 ? this : t
      }
      function at (t, e, n) {
        var i
        var r
        var o = Math.min(t.t, this.t)
        for (i = 0; i < o; ++i) n[i] = e(this[i], t[i])
        if (t.t < this.t) {
          for (r = t.s & this.DM, i = o; i < this.t; ++i) n[i] = e(this[i], r)
          n.t = this.t
        } else {
          for (r = this.s & this.DM, i = o; i < t.t; ++i) n[i] = e(r, t[i])
          n.t = t.t
        }
        (n.s = e(this.s, t.s)), n.clamp()
      }
      function st (t, e) {
        return t & e
      }
      function ut (t) {
        var e = r()
        return this.bitwiseTo(t, st, e), e
      }
      function lt (t, e) {
        return t | e
      }
      function ft (t) {
        var e = r()
        return this.bitwiseTo(t, lt, e), e
      }
      function ct (t, e) {
        return t ^ e
      }
      function ht (t) {
        var e = r()
        return this.bitwiseTo(t, ct, e), e
      }
      function dt (t, e) {
        return t & ~e
      }
      function pt (t) {
        var e = r()
        return this.bitwiseTo(t, dt, e), e
      }
      function yt () {
        for (var t = r(), e = 0; e < this.t; ++e) t[e] = this.DM & ~this[e]
        return (t.t = this.t), (t.s = ~this.s), t
      }
      function mt (t) {
        var e = r()
        return t < 0 ? this.rShiftTo(-t, e) : this.lShiftTo(t, e), e
      }
      function vt (t) {
        var e = r()
        return t < 0 ? this.lShiftTo(-t, e) : this.rShiftTo(t, e), e
      }
      function gt (t) {
        if (t == 0) return -1
        var e = 0
        return (
          (65535 & t) == 0 && ((t >>= 16), (e += 16)),
          (255 & t) == 0 && ((t >>= 8), (e += 8)),
          (15 & t) == 0 && ((t >>= 4), (e += 4)),
          (3 & t) == 0 && ((t >>= 2), (e += 2)),
          (1 & t) == 0 && ++e,
          e
        )
      }
      function St () {
        for (var t = 0; t < this.t; ++t) { if (this[t] != 0) return t * this.DB + gt(this[t]) }
        return this.s < 0 ? this.t * this.DB : -1
      }
      function bt (t) {
        for (var e = 0; t != 0;) (t &= t - 1), ++e
        return e
      }
      function wt () {
        for (var t = 0, e = this.s & this.DM, n = 0; n < this.t; ++n) { t += bt(this[n] ^ e) }
        return t
      }
      function _t (t) {
        var e = Math.floor(t / this.DB)
        return e >= this.t
          ? this.s != 0
          : (this[e] & (1 << (t % this.DB))) != 0
      }
      function Pt (t, e) {
        var i = n.ONE.shiftLeft(t)
        return this.bitwiseTo(i, e, i), i
      }
      function Ct (t) {
        return this.changeBit(t, lt)
      }
      function xt (t) {
        return this.changeBit(t, dt)
      }
      function Tt (t) {
        return this.changeBit(t, ct)
      }
      function kt (t, e) {
        for (var n = 0, i = 0, r = Math.min(t.t, this.t); n < r;) { (i += this[n] + t[n]), (e[n++] = i & this.DM), (i >>= this.DB) }
        if (t.t < this.t) {
          for (i += t.s; n < this.t;) { (i += this[n]), (e[n++] = i & this.DM), (i >>= this.DB) }
          i += this.s
        } else {
          for (i += this.s; n < t.t;) { (i += t[n]), (e[n++] = i & this.DM), (i >>= this.DB) }
          i += t.s
        }
        (e.s = i < 0 ? -1 : 0),
        i > 0 ? (e[n++] = i) : i < -1 && (e[n++] = this.DV + i),
        (e.t = n),
        e.clamp()
      }
      function Dt (t) {
        var e = r()
        return this.addTo(t, e), e
      }
      function Mt (t) {
        var e = r()
        return this.subTo(t, e), e
      }
      function Rt (t) {
        var e = r()
        return this.multiplyTo(t, e), e
      }
      function Et () {
        var t = r()
        return this.squareTo(t), t
      }
      function zt (t) {
        var e = r()
        return this.divRemTo(t, e, null), e
      }
      function It (t) {
        var e = r()
        return this.divRemTo(t, null, e), e
      }
      function At (t) {
        var e = r()
        var n = r()
        return this.divRemTo(t, e, n), [e, n]
      }
      function Ft (t) {
        (this[this.t] = this.am(0, t - 1, this, 0, 0, this.t)),
        ++this.t,
        this.clamp()
      }
      function Ot (t, e) {
        if (t != 0) {
          for (; this.t <= e;) this[this.t++] = 0
          for (this[e] += t; this[e] >= this.DV;) {
            (this[e] -= this.DV),
            ++e >= this.t && (this[this.t++] = 0),
            ++this[e]
          }
        }
      }
      function Bt () {}
      function Lt (t) {
        return t
      }
      function Nt (t, e, n) {
        t.multiplyTo(e, n)
      }
      function qt (t, e) {
        t.squareTo(e)
      }
      function Ht (t) {
        return this.exp(t, new Bt())
      }
      function jt (t, e, n) {
        var i = Math.min(this.t + t.t, e)
        for (n.s = 0, n.t = i; i > 0;) n[--i] = 0
        var r
        for (r = n.t - this.t; i < r; ++i) { n[i + this.t] = this.am(0, t[i], n, i, 0, this.t) }
        for (r = Math.min(t.t, e); i < r; ++i) this.am(0, t[i], n, i, 0, e - i)
        n.clamp()
      }
      function Wt (t, e, n) {
        --e
        var i = (n.t = this.t + t.t - e)
        for (n.s = 0; --i >= 0;) n[i] = 0
        for (i = Math.max(e - this.t, 0); i < t.t; ++i) { n[this.t + i - e] = this.am(e - i, t[i], n, 0, 0, this.t + i - e) }
        n.clamp(), n.drShiftTo(1, n)
      }
      function Ut (t) {
        (this.r2 = r()),
        (this.q3 = r()),
        n.ONE.dlShiftTo(2 * t.t, this.r2),
        (this.mu = this.r2.divide(t)),
        (this.m = t)
      }
      function Gt (t) {
        if (t.s < 0 || t.t > 2 * this.m.t) return t.mod(this.m)
        if (t.compareTo(this.m) < 0) return t
        var e = r()
        return t.copyTo(e), this.reduce(e), e
      }
      function Vt (t) {
        return t
      }
      function Yt (t) {
        for (
          t.drShiftTo(this.m.t - 1, this.r2),
          t.t > this.m.t + 1 && ((t.t = this.m.t + 1), t.clamp()),
          this.mu.multiplyUpperTo(this.r2, this.m.t + 1, this.q3),
          this.m.multiplyLowerTo(this.q3, this.m.t + 1, this.r2);
          t.compareTo(this.r2) < 0;

        ) { t.dAddOffset(1, this.m.t + 1) }
        for (t.subTo(this.r2, t); t.compareTo(this.m) >= 0;) { t.subTo(this.m, t) }
      }
      function Xt (t, e) {
        t.squareTo(e), this.reduce(e)
      }
      function Jt (t, e, n) {
        t.multiplyTo(e, n), this.reduce(n)
      }
      function $t (t, e) {
        var n
        var i
        var o = t.bitLength()
        var a = h(1)
        if (o <= 0) return a;
        (n = o < 18 ? 1 : o < 48 ? 3 : o < 144 ? 4 : o < 768 ? 5 : 6),
        (i = o < 8 ? new E(e) : e.isEven() ? new Ut(e) : new L(e))
        var s = []
        var u = 3
        var l = n - 1
        var f = (1 << n) - 1
        if (((s[1] = i.convert(this)), n > 1)) {
          for (o = r(), i.sqrTo(s[1], o); u <= f;) { (s[u] = r()), i.mulTo(o, s[u - 2], s[u]), (u += 2) }
        }
        for (var c, d = t.t - 1, p = !0, y = r(), o = b(t[d]) - 1; d >= 0;) {
          for (
            o >= l
              ? (c = (t[d] >> (o - l)) & f)
              : ((c = (t[d] & ((1 << (o + 1)) - 1)) << (l - o)),
              d > 0 && (c |= t[d - 1] >> (this.DB + o - l))),
            u = n;
            (1 & c) == 0;

          ) { (c >>= 1), --u }
          if (((o -= u) < 0 && ((o += this.DB), --d), p)) { s[c].copyTo(a), (p = !1) } else {
            for (; u > 1;) i.sqrTo(a, y), i.sqrTo(y, a), (u -= 2)
            u > 0 ? i.sqrTo(a, y) : ((u = a), (a = y), (y = u)),
            i.mulTo(y, s[c], a)
          }
          for (; d >= 0 && (t[d] & (1 << o)) == 0;) {
            i.sqrTo(a, y),
            (u = a),
            (a = y),
            (y = u),
            --o < 0 && ((o = this.DB - 1), --d)
          }
        }
        return i.revert(a)
      }
      function Kt (t) {
        var e = this.s < 0 ? this.negate() : this.clone()
        var t = t.s < 0 ? t.negate() : t.clone()
        if (e.compareTo(t) < 0) {
          var n = e
          var e = t
          var t = n
        }
        var n = e.getLowestSetBit()
        var i = t.getLowestSetBit()
        if (i < 0) return e
        for (
          n < i && (i = n), i > 0 && (e.rShiftTo(i, e), t.rShiftTo(i, t));
          e.signum() > 0;

        ) {
          (n = e.getLowestSetBit()) > 0 && e.rShiftTo(n, e),
          (n = t.getLowestSetBit()) > 0 && t.rShiftTo(n, t),
          e.compareTo(t) >= 0
            ? (e.subTo(t, e), e.rShiftTo(1, e))
            : (t.subTo(e, t), t.rShiftTo(1, t))
        }
        return i > 0 && t.lShiftTo(i, t), t
      }
      function Zt (t) {
        if (t <= 0) return 0
        var e = this.DV % t
        var n = this.s < 0 ? t - 1 : 0
        if (this.t > 0) {
          if (e == 0) n = this[0] % t
          else for (var i = this.t - 1; i >= 0; --i) n = (e * n + this[i]) % t
        }
        return n
      }
      function Qt (t) {
        var e = t.isEven()
        if ((this.isEven() && e) || t.signum() == 0) return n.ZERO
        for (
          var i = t.clone(),
            r = this.clone(),
            o = h(1),
            a = h(0),
            s = h(0),
            u = h(1);
          i.signum() != 0;

        ) {
          for (; i.isEven();) {
            i.rShiftTo(1, i),
            e
              ? ((o.isEven() && a.isEven()) ||
                    (o.addTo(this, o), a.subTo(t, a)),
              o.rShiftTo(1, o))
              : a.isEven() || a.subTo(t, a),
            a.rShiftTo(1, a)
          }
          for (; r.isEven();) {
            r.rShiftTo(1, r),
            e
              ? ((s.isEven() && u.isEven()) ||
                    (s.addTo(this, s), u.subTo(t, u)),
              s.rShiftTo(1, s))
              : u.isEven() || u.subTo(t, u),
            u.rShiftTo(1, u)
          }
          i.compareTo(r) >= 0
            ? (i.subTo(r, i), e && o.subTo(s, o), a.subTo(u, a))
            : (r.subTo(i, r), e && s.subTo(o, s), u.subTo(a, u))
        }
        return r.compareTo(n.ONE) != 0
          ? n.ZERO
          : u.compareTo(t) >= 0
            ? u.subtract(t)
            : u.signum() < 0
              ? (u.addTo(t, u), u.signum() < 0 ? u.add(t) : u)
              : u
      }
      function te (t) {
        var e
        var n = this.abs()
        if (n.t == 1 && n[0] <= Ye[Ye.length - 1]) {
          for (e = 0; e < Ye.length; ++e) if (n[0] == Ye[e]) return !0
          return !1
        }
        if (n.isEven()) return !1
        for (e = 1; e < Ye.length;) {
          for (var i = Ye[e], r = e + 1; r < Ye.length && i < Xe;) { i *= Ye[r++] }
          for (i = n.modInt(i); e < r;) if (i % Ye[e++] == 0) return !1
        }
        return n.millerRabin(t)
      }
      function ee (t) {
        var e = this.subtract(n.ONE)
        var i = e.getLowestSetBit()
        if (i <= 0) return !1
        var o = e.shiftRight(i)
        var t = (t + 1) >> 1
        t > Ye.length && (t = Ye.length)
        for (var a = r(), s = 0; s < t; ++s) {
          a.fromInt(Ye[Math.floor(Math.random() * Ye.length)])
          var u = a.modPow(o, this)
          if (u.compareTo(n.ONE) != 0 && u.compareTo(e) != 0) {
            for (var l = 1; l++ < i && u.compareTo(e) != 0;) {
              if (((u = u.modPowInt(2, this)), u.compareTo(n.ONE) == 0)) { return !1 }
            }
            if (u.compareTo(e) != 0) return !1
          }
        }
        return !0
      }
      function ne () {}
      function ie (t) {
        var e
        for (e = 0; e < t.length; e++) t[e] = Math.floor(256 * Math.random())
      }
      function re () {
        (this.j = this.i = 0), (this.S = [])
      }
      function oe (t) {
        var e, n, i
        for (e = 0; e < 256; ++e) this.S[e] = e
        for (e = n = 0; e < 256; ++e) {
          (n = (n + this.S[e] + t[e % t.length]) & 255),
          (i = this.S[e]),
          (this.S[e] = this.S[n]),
          (this.S[n] = i)
        }
        this.j = this.i = 0
      }
      function ae () {
        var t
        return (
          (this.i = (this.i + 1) & 255),
          (this.j = (this.j + this.S[this.i]) & 255),
          (t = this.S[this.i]),
          (this.S[this.i] = this.S[this.j]),
          (this.S[this.j] = t),
          this.S[(t + this.S[this.i]) & 255]
        )
      }
      function se () {
        return new re()
      }
      function ue (t) {
        ($e[Ke++] ^= 255 & t),
        ($e[Ke++] ^= (t >> 8) & 255),
        ($e[Ke++] ^= (t >> 16) & 255),
        ($e[Ke++] ^= (t >> 24) & 255),
        Ke >= Ze && (Ke -= Ze)
      }
      function le () {
        ue(new Date().getTime())
      }
      function fe () {
        if (Je == null) {
          for (le(), Je = se(), Je.init($e), Ke = 0; Ke < $e.length; ++Ke) { $e[Ke] = 0 }
          Ke = 0
        }
        return Je.next()
      }
      function ce (t) {
        var e
        for (e = 0; e < t.length; ++e) t[e] = fe()
      }
      function he () {}
      function de (t) {
        function e (t, e) {
          var n = (65535 & t) + (65535 & e)
          return (((t >> 16) + (e >> 16) + (n >> 16)) << 16) | (65535 & n)
        }
        function n (t, e) {
          return (t >>> e) | (t << (32 - e))
        }
        return (
          (t = (function (t) {
            for (
              var t = t.replace(/\r\n/g, '\n'), e = '', n = 0;
              n < t.length;
              n++
            ) {
              var i = t.charCodeAt(n)
              i < 128
                ? (e += String.fromCharCode(i))
                : (i > 127 && i < 2048
                  ? (e += String.fromCharCode((i >> 6) | 192))
                  : ((e += String.fromCharCode((i >> 12) | 224)),
                  (e += String.fromCharCode(((i >> 6) & 63) | 128))),
                (e += String.fromCharCode((63 & i) | 128)))
            }
            return e
          })(t)),
          (function (t) {
            for (var e = '', n = 0; n < 4 * t.length; n++) {
              e +=
                '0123456789abcdef'.charAt(
                  (t[n >> 2] >> (8 * (3 - n % 4) + 4)) & 15
                ) +
                '0123456789abcdef'.charAt(
                  (t[n >> 2] >> (8 * (3 - n % 4))) & 15
                )
            }
            return e
          })(
            (function (t, i) {
              var r
              var o
              var a
              var s
              var u
              var l
              var f
              var c
              var h
              var d
              var p
              var y
              var m = [
                1116352408,
                1899447441,
                3049323471,
                3921009573,
                961987163,
                1508970993,
                2453635748,
                2870763221,
                3624381080,
                310598401,
                607225278,
                1426881987,
                1925078388,
                2162078206,
                2614888103,
                3248222580,
                3835390401,
                4022224774,
                264347078,
                604807628,
                770255983,
                1249150122,
                1555081692,
                1996064986,
                2554220882,
                2821834349,
                2952996808,
                3210313671,
                3336571891,
                3584528711,
                113926993,
                338241895,
                666307205,
                773529912,
                1294757372,
                1396182291,
                1695183700,
                1986661051,
                2177026350,
                2456956037,
                2730485921,
                2820302411,
                3259730800,
                3345764771,
                3516065817,
                3600352804,
                4094571909,
                275423344,
                430227734,
                506948616,
                659060556,
                883997877,
                958139571,
                1322822218,
                1537002063,
                1747873779,
                1955562222,
                2024104815,
                2227730452,
                2361852424,
                2428436474,
                2756734187,
                3204031479,
                3329325298
              ]
              var v = [
                1779033703,
                3144134277,
                1013904242,
                2773480762,
                1359893119,
                2600822924,
                528734635,
                1541459225
              ]
              var g = Array(64)
              for (
                t[i >> 5] |= 128 << (24 - i % 32),
                t[(((i + 64) >> 9) << 4) + 15] = i,
                h = 0;
                h < t.length;
                h += 16
              ) {
                for (
                  r = v[0],
                  o = v[1],
                  a = v[2],
                  s = v[3],
                  u = v[4],
                  l = v[5],
                  f = v[6],
                  c = v[7],
                  d = 0;
                  d < 64;
                  d++
                ) {
                  (g[d] =
                    d < 16
                      ? t[d + h]
                      : e(
                        e(
                          e(
                            n(g[d - 2], 17) ^
                                n(g[d - 2], 19) ^
                                (g[d - 2] >>> 10),
                            g[d - 7]
                          ),
                          n(g[d - 15], 7) ^
                              n(g[d - 15], 18) ^
                              (g[d - 15] >>> 3)
                        ),
                        g[d - 16]
                      )),
                  (p = e(
                    e(
                      e(
                        e(c, n(u, 6) ^ n(u, 11) ^ n(u, 25)),
                        (u & l) ^ (~u & f)
                      ),
                      m[d]
                    ),
                    g[d]
                  )),
                  (y = e(
                    n(r, 2) ^ n(r, 13) ^ n(r, 22),
                    (r & o) ^ (r & a) ^ (o & a)
                  )),
                  (c = f),
                  (f = l),
                  (l = u),
                  (u = e(s, p)),
                  (s = a),
                  (a = o),
                  (o = r),
                  (r = e(p, y))
                }
                (v[0] = e(r, v[0])),
                (v[1] = e(o, v[1])),
                (v[2] = e(a, v[2])),
                (v[3] = e(s, v[3])),
                (v[4] = e(u, v[4])),
                (v[5] = e(l, v[5])),
                (v[6] = e(f, v[6])),
                (v[7] = e(c, v[7]))
              }
              return v
            })(
              (function (t) {
                for (var e = [], n = 0; n < 8 * t.length; n += 8) { e[n >> 5] |= (255 & t.charCodeAt(n / 8)) << (24 - n % 32) }
                return e
              })(t),
              8 * t.length
            )
          )
        )
      }
      function pe (t) {
        function e (t, e) {
          return (t << e) | (t >>> (32 - e))
        }
        function n (t) {
          var e
          var n
          var i = ''
          for (e = 7; e >= 0; e--) { (n = (t >>> (4 * e)) & 15), (i += n.toString(16)) }
          return i
        }
        var i
        var r
        var o
        var a
        var s
        var u
        var l
        var f = Array(80)
        var c = 1732584193
        var h = 4023233417
        var d = 2562383102
        var p = 271733878
        var y = 3285377520
        var t = (function (t) {
          for (
            var t = t.replace(/\r\n/g, '\n'), e = '', n = 0;
            n < t.length;
            n++
          ) {
            var i = t.charCodeAt(n)
            i < 128
              ? (e += String.fromCharCode(i))
              : (i > 127 && i < 2048
                ? (e += String.fromCharCode((i >> 6) | 192))
                : ((e += String.fromCharCode((i >> 12) | 224)),
                (e += String.fromCharCode(((i >> 6) & 63) | 128))),
              (e += String.fromCharCode((63 & i) | 128)))
          }
          return e
        })(t)
        o = t.length
        var m = []
        for (i = 0; i < o - 3; i += 4) {
          (r =
            (t.charCodeAt(i) << 24) |
            (t.charCodeAt(i + 1) << 16) |
            (t.charCodeAt(i + 2) << 8) |
            t.charCodeAt(i + 3)),
          m.push(r)
        }
        switch (o % 4) {
          case 0:
            i = 2147483648
            break
          case 1:
            i = (t.charCodeAt(o - 1) << 24) | 8388608
            break
          case 2:
            i =
              (t.charCodeAt(o - 2) << 24) | (t.charCodeAt(o - 1) << 16) | 32768
            break
          case 3:
            i =
              (t.charCodeAt(o - 3) << 24) |
              (t.charCodeAt(o - 2) << 16) |
              (t.charCodeAt(o - 1) << 8) |
              128
        }
        for (m.push(i); m.length % 16 != 14;) m.push(0)
        for (
          m.push(o >>> 29), m.push((o << 3) & 4294967295), t = 0;
          t < m.length;
          t += 16
        ) {
          for (i = 0; i < 16; i++) f[i] = m[t + i]
          for (i = 16; i <= 79; i++) { f[i] = e(f[i - 3] ^ f[i - 8] ^ f[i - 14] ^ f[i - 16], 1) }
          for (r = c, o = h, a = d, s = p, u = y, i = 0; i <= 19; i++) {
            (l =
              (e(r, 5) + ((o & a) | (~o & s)) + u + f[i] + 1518500249) &
              4294967295),
            (u = s),
            (s = a),
            (a = e(o, 30)),
            (o = r),
            (r = l)
          }
          for (i = 20; i <= 39; i++) {
            (l = (e(r, 5) + (o ^ a ^ s) + u + f[i] + 1859775393) & 4294967295),
            (u = s),
            (s = a),
            (a = e(o, 30)),
            (o = r),
            (r = l)
          }
          for (i = 40; i <= 59; i++) {
            (l =
              (e(r, 5) +
                ((o & a) | (o & s) | (a & s)) +
                u +
                f[i] +
                2400959708) &
              4294967295),
            (u = s),
            (s = a),
            (a = e(o, 30)),
            (o = r),
            (r = l)
          }
          for (i = 60; i <= 79; i++) {
            (l = (e(r, 5) + (o ^ a ^ s) + u + f[i] + 3395469782) & 4294967295),
            (u = s),
            (s = a),
            (a = e(o, 30)),
            (o = r),
            (r = l)
          }
          (c = (c + r) & 4294967295),
          (h = (h + o) & 4294967295),
          (d = (d + a) & 4294967295),
          (p = (p + s) & 4294967295),
          (y = (y + u) & 4294967295)
        }
        return (l = n(c) + n(h) + n(d) + n(p) + n(y)), l.toLowerCase()
      }
      function ye (t, e) {
        return new n(t, e)
      }
      function me (t, e) {
        if (e < t.length + 11) { throw 'Message too long for RSA (n=' + e + ', l=' + t.length + ')' }
        for (var i = [], r = t.length - 1; r >= 0 && e > 0;) {
          var o = t.charCodeAt(r--)
          o < 128
            ? (i[--e] = o)
            : o > 127 && o < 2048
              ? ((i[--e] = (63 & o) | 128), (i[--e] = (o >> 6) | 192))
              : ((i[--e] = (63 & o) | 128),
              (i[--e] = ((o >> 6) & 63) | 128),
              (i[--e] = (o >> 12) | 224))
        }
        for (i[--e] = 0, r = new he(), o = []; e > 2;) {
          for (o[0] = 0; o[0] == 0;) r.nextBytes(o)
          i[--e] = o[0]
        }
        return (i[--e] = 2), (i[--e] = 0), new n(i)
      }
      function ve () {
        (this.n = null),
        (this.e = 0),
        (this.coeff = this.dmq1 = this.dmp1 = this.q = this.p = this.d = null)
      }
      function ge (t, e) {
        t != null && e != null && t.length > 0 && e.length > 0
          ? ((this.n = ye(t, 16)), (this.e = parseInt(e, 16)))
          : alert('Invalid RSA global key')
      }
      function Se (t) {
        return t.modPowInt(this.e, this.n)
      }
      function be (t) {
        return (
          (t = me(t, (this.n.bitLength() + 7) >> 3)),
          t == null
            ? null
            : ((t = this.doPublic(t)),
            t == null
              ? null
              : ((t = t.toString(16)), (1 & t.length) == 0 ? t : '0' + t))
        )
      }
      function we (t, e) {
        for (var n = t.toByteArray(), i = 0; i < n.length && n[i] == 0;) ++i
        if (n.length - i != e - 1 || n[i] != 2) return null
        for (++i; n[i] != 0;) if (++i >= n.length) return null
        for (var r = ''; ++i < n.length;) {
          var o = 255 & n[i]
          o < 128
            ? (r += String.fromCharCode(o))
            : o > 191 && o < 224
              ? ((r += String.fromCharCode(((31 & o) << 6) | (63 & n[i + 1]))),
              ++i)
              : ((r += String.fromCharCode(
                ((15 & o) << 12) | ((63 & n[i + 1]) << 6) | (63 & n[i + 2])
              )),
              (i += 2))
        }
        return r
      }
      function _e (t, e, n) {
        t != null && e != null && t.length > 0 && e.length > 0
          ? ((this.n = ye(t, 16)),
          (this.e = parseInt(e, 16)),
          (this.d = ye(n, 16)))
          : alert('Invalid RSA private key')
      }
      function Pe (t, e, n, i, r, o, a, s) {
        t != null && e != null && t.length > 0 && e.length > 0
          ? ((this.n = ye(t, 16)),
          (this.e = parseInt(e, 16)),
          (this.d = ye(n, 16)),
          (this.p = ye(i, 16)),
          (this.q = ye(r, 16)),
          (this.dmp1 = ye(o, 16)),
          (this.dmq1 = ye(a, 16)),
          (this.coeff = ye(s, 16)))
          : alert('Invalid RSA private key')
      }
      function Ce (t, e) {
        var i = new ne()
        var r = t >> 1
        this.e = parseInt(e, 16)
        for (var o = new n(e, 16); ;) {
          for (
            ;
            (this.p = new n(t - r, 1, i)),
            this.p
              .subtract(n.ONE)
              .gcd(o)
              .compareTo(n.ONE) !=
                0 || !this.p.isProbablePrime(10);

          );
          for (
            ;
            (this.q = new n(r, 1, i)),
            this.q
              .subtract(n.ONE)
              .gcd(o)
              .compareTo(n.ONE) !=
                0 || !this.q.isProbablePrime(10);

          );
          if (this.p.compareTo(this.q) <= 0) {
            var a = this.p;
            (this.p = this.q), (this.q = a)
          }
          var a = this.p.subtract(n.ONE)
          var s = this.q.subtract(n.ONE)
          var u = a.multiply(s)
          if (u.gcd(o).compareTo(n.ONE) == 0) {
            (this.n = this.p.multiply(this.q)),
            (this.d = o.modInverse(u)),
            (this.dmp1 = this.d.mod(a)),
            (this.dmq1 = this.d.mod(s)),
            (this.coeff = this.q.modInverse(this.p))
            break
          }
        }
      }
      function xe (t) {
        if (this.p == null || this.q == null) return t.modPow(this.d, this.n)
        for (
          var e = t.mod(this.p).modPow(this.dmp1, this.p),
            t = t.mod(this.q).modPow(this.dmq1, this.q);
          e.compareTo(t) < 0;

        ) { e = e.add(this.p) }
        return e
          .subtract(t)
          .multiply(this.coeff)
          .mod(this.p)
          .multiply(this.q)
          .add(t)
      }
      function Te (t) {
        return (
          (t = this.doPrivate(ye(t, 16))),
          t == null ? null : we(t, (this.n.bitLength() + 7) >> 3)
        )
      }
      function ke (t, e, n) {
        e /= 4
        for (
          var t = (0, an[n])(t),
            n = '00' + on[n] + t,
            t = '',
            e = e - 4 - n.length,
            i = 0;
          i < e;
          i += 2
        ) { t += 'ff' }
        return (sPaddedMessageHex = '0001' + t + n)
      }
      function De (t, e) {
        var n = ke(t, this.n.bitLength(), e)
        return this.doPrivate(ye(n, 16)).toString(16)
      }
      function Me (t) {
        return (
          (t = ke(t, this.n.bitLength(), 'sha1')),
          this.doPrivate(ye(t, 16)).toString(16)
        )
      }
      function Re (t) {
        return (
          (t = ke(t, this.n.bitLength(), 'sha256')),
          this.doPrivate(ye(t, 16)).toString(16)
        )
      }
      function Ee (t, e, n) {
        var i = new ve()
        return i.setPublic(e, n), i.doPublic(t)
      }
      function ze (t, e, n) {
        return Ee(t, e, n)
          .toString(16)
          .replace(/^1f+00/, '')
      }
      function Ie (t) {
        for (var e in on) {
          var n = on[e]
          var i = n.length
          if (t.substring(0, i) == n) return [e, t.substring(i)]
        }
        return []
      }
      function Ae (t, e, n, i) {
        return (
          (e = ze(e, n, i)),
          (n = Ie(e)),
          n.length != 0 && ((e = n[1]), (t = (0, an[n[0]])(t)), e == t)
        )
      }
      function Fe (t, e) {
        var n = ye(t, 16)
        return Ae(e, n, this.n.toString(16), this.e.toString(16))
      }
      function Oe (t, e) {
        var e = e.replace(/[ \n]+/g, '')
        var n = this.doPublic(ye(e, 16))
          .toString(16)
          .replace(/^1f+00/, '')
        var i = Ie(n)
        return i.length != 0 && ((n = i[1]), (i = (0, an[i[0]])(t)), n == i)
      }
      var Be
      var Le =
          typeof Symbol === 'function' && typeof Symbol.iterator === 'symbol'
            ? function (t) {
              return typeof t
            }
            : function (t) {
              return t &&
                  typeof Symbol === 'function' &&
                  t.constructor === Symbol &&
                  t !== Symbol.prototype
                ? 'symbol'
                : typeof t
            }
      var Ne = { appName: 'Netscape', appVersion: 40 }
      var qe = 0xdeadbeefcafe
      var He = (16777215 & qe) == 15715070
      He && Ne.appName == 'Microsoft Internet Explorer'
        ? ((n.prototype.am = a), (Be = 30))
        : He && Ne.appName != 'Netscape'
          ? ((n.prototype.am = o), (Be = 26))
          : ((n.prototype.am = s), (Be = 28)),
      (n.prototype.DB = Be),
      (n.prototype.DM = (1 << Be) - 1),
      (n.prototype.DV = 1 << Be)
      var je = 52;
      (n.prototype.FV = Math.pow(2, je)),
      (n.prototype.F1 = je - Be),
      (n.prototype.F2 = 2 * Be - je)
      var We
      var Ue
      var Ge = '0123456789abcdefghijklmnopqrstuvwxyz'
      var Ve = []
      for (We = '0'.charCodeAt(0), Ue = 0; Ue <= 9; ++Ue) Ve[We++] = Ue
      for (We = 'a'.charCodeAt(0), Ue = 10; Ue < 36; ++Ue) Ve[We++] = Ue
      for (We = 'A'.charCodeAt(0), Ue = 10; Ue < 36; ++Ue) Ve[We++] = Ue;
      (E.prototype.convert = z),
      (E.prototype.revert = I),
      (E.prototype.reduce = A),
      (E.prototype.mulTo = F),
      (E.prototype.sqrTo = O),
      (L.prototype.convert = N),
      (L.prototype.revert = q),
      (L.prototype.reduce = H),
      (L.prototype.mulTo = W),
      (L.prototype.sqrTo = j),
      (n.prototype.copyTo = f),
      (n.prototype.fromInt = c),
      (n.prototype.fromString = d),
      (n.prototype.clamp = p),
      (n.prototype.dlShiftTo = _),
      (n.prototype.drShiftTo = P),
      (n.prototype.lShiftTo = C),
      (n.prototype.rShiftTo = x),
      (n.prototype.subTo = T),
      (n.prototype.multiplyTo = k),
      (n.prototype.squareTo = D),
      (n.prototype.divRemTo = M),
      (n.prototype.invDigit = B),
      (n.prototype.isEven = U),
      (n.prototype.exp = G),
      (n.prototype.toString = y),
      (n.prototype.negate = m),
      (n.prototype.abs = g),
      (n.prototype.compareTo = S),
      (n.prototype.bitLength = w),
      (n.prototype.mod = R),
      (n.prototype.modPowInt = V),
      (n.ZERO = h(0)),
      (n.ONE = h(1)),
      (Bt.prototype.convert = Lt),
      (Bt.prototype.revert = Lt),
      (Bt.prototype.mulTo = Nt),
      (Bt.prototype.sqrTo = qt),
      (Ut.prototype.convert = Gt),
      (Ut.prototype.revert = Vt),
      (Ut.prototype.reduce = Yt),
      (Ut.prototype.mulTo = Jt),
      (Ut.prototype.sqrTo = Xt)
      var Ye = [
        2,
        3,
        5,
        7,
        11,
        13,
        17,
        19,
        23,
        29,
        31,
        37,
        41,
        43,
        47,
        53,
        59,
        61,
        67,
        71,
        73,
        79,
        83,
        89,
        97,
        101,
        103,
        107,
        109,
        113,
        127,
        131,
        137,
        139,
        149,
        151,
        157,
        163,
        167,
        173,
        179,
        181,
        191,
        193,
        197,
        199,
        211,
        223,
        227,
        229,
        233,
        239,
        241,
        251,
        257,
        263,
        269,
        271,
        277,
        281,
        283,
        293,
        307,
        311,
        313,
        317,
        331,
        337,
        347,
        349,
        353,
        359,
        367,
        373,
        379,
        383,
        389,
        397,
        401,
        409,
        419,
        421,
        431,
        433,
        439,
        443,
        449,
        457,
        461,
        463,
        467,
        479,
        487,
        491,
        499,
        503,
        509,
        521,
        523,
        541,
        547,
        557,
        563,
        569,
        571,
        577,
        587,
        593,
        599,
        601,
        607,
        613,
        617,
        619,
        631,
        641,
        643,
        647,
        653,
        659,
        661,
        673,
        677,
        683,
        691,
        701,
        709,
        719,
        727,
        733,
        739,
        743,
        751,
        757,
        761,
        769,
        773,
        787,
        797,
        809,
        811,
        821,
        823,
        827,
        829,
        839,
        853,
        857,
        859,
        863,
        877,
        881,
        883,
        887,
        907,
        911,
        919,
        929,
        937,
        941,
        947,
        953,
        967,
        971,
        977,
        983,
        991,
        997
      ]
      var Xe = 67108864 / Ye[Ye.length - 1];
      (n.prototype.chunkSize = K),
      (n.prototype.toRadix = Q),
      (n.prototype.fromRadix = tt),
      (n.prototype.fromNumber = et),
      (n.prototype.bitwiseTo = at),
      (n.prototype.changeBit = Pt),
      (n.prototype.addTo = kt),
      (n.prototype.dMultiply = Ft),
      (n.prototype.dAddOffset = Ot),
      (n.prototype.multiplyLowerTo = jt),
      (n.prototype.multiplyUpperTo = Wt),
      (n.prototype.modInt = Zt),
      (n.prototype.millerRabin = ee),
      (n.prototype.clone = Y),
      (n.prototype.intValue = X),
      (n.prototype.byteValue = J),
      (n.prototype.shortValue = $),
      (n.prototype.signum = Z),
      (n.prototype.toByteArray = nt),
      (n.prototype.equals = it),
      (n.prototype.min = rt),
      (n.prototype.max = ot),
      (n.prototype.and = ut),
      (n.prototype.or = ft),
      (n.prototype.xor = ht),
      (n.prototype.andNot = pt),
      (n.prototype.not = yt),
      (n.prototype.shiftLeft = mt),
      (n.prototype.shiftRight = vt),
      (n.prototype.getLowestSetBit = St),
      (n.prototype.bitCount = wt),
      (n.prototype.testBit = _t),
      (n.prototype.setBit = Ct),
      (n.prototype.clearBit = xt),
      (n.prototype.flipBit = Tt),
      (n.prototype.add = Dt),
      (n.prototype.subtract = Mt),
      (n.prototype.multiply = Rt),
      (n.prototype.divide = zt),
      (n.prototype.remainder = It),
      (n.prototype.divideAndRemainder = At),
      (n.prototype.modPow = $t),
      (n.prototype.modInverse = Qt),
      (n.prototype.pow = Ht),
      (n.prototype.gcd = Kt),
      (n.prototype.isProbablePrime = te),
      (n.prototype.square = Et),
      (function (t, e, n, i, r, o, a) {
        function s (t) {
          var e
          var i
          var r = this
          var o = t.length
          var a = 0
          var s = (r.i = r.j = r.m = 0)
          for (r.S = [], r.c = [], o || (t = [o++]); a < n;) r.S[a] = a++
          for (a = 0; a < n; a++) {
            (e = r.S[a]),
            (s = (s + e + t[a % o]) & (n - 1)),
            (i = r.S[s]),
            (r.S[a] = i),
            (r.S[s] = e)
          }
          (r.g = function (t) {
            var e = r.S
            var i = (r.i + 1) & (n - 1)
            var o = e[i]
            var a = (r.j + o) & (n - 1)
            var s = e[a];
            (e[i] = s), (e[a] = o)
            for (var u = e[(o + s) & (n - 1)]; --t;) {
              (i = (i + 1) & (n - 1)),
              (o = e[i]),
              (a = (a + o) & (n - 1)),
              (s = e[a]),
              (e[i] = s),
              (e[a] = o),
              (u = u * n + e[(o + s) & (n - 1)])
            }
            return (r.i = i), (r.j = a), u
          }),
          r.g(n)
        }
        function u (t, e, n, i, r) {
          if (
            ((n = []),
            (r = typeof t === 'undefined' ? 'undefined' : Le(t)),
            e && r == 'object')
          ) {
            for (i in t) {
              if (i.indexOf('S') < 5) {
                try {
                  n.push(u(t[i], e - 1))
                } catch (t) {}
              }
            }
          }
          return n.length ? n : t + (r != 'string' ? '\0' : '')
        }
        function l (t, e, i, r) {
          for (t += '', r = i = 0; r < t.length; r++) {
            var o = e
            var a = r & (n - 1)
            var s = (i ^= 19 * e[r & (n - 1)]) + t.charCodeAt(r)
            o[a] = s & (n - 1)
          }
          t = ''
          for (r in e) t += String.fromCharCode(e[r])
          return t
        }
        (e.seedrandom = function (f, c) {
          var h
          var d = []
          var f = l(
            u(
              c
                ? [f, t]
                : arguments.length
                  ? f
                  : [new Date().getTime(), t, window],
              3
            ),
            d
          )
          return (
            (h = new s(d)),
            l(h.S, t),
            (e.random = function () {
              for (var t = h.g(i), e = a, s = 0; t < r;) { (t = (t + s) * n), (e *= n), (s = h.g(1)) }
              for (; t >= o;) (t /= 2), (e /= 2), (s >>>= 1)
              return (t + s) / e
            }),
            f
          )
        }),
        (a = e.pow(n, i)),
        (r = e.pow(2, r)),
        (o = 2 * r),
        l(e.random(), t)
      })([], Math, 256, 6, 52),
      (ne.prototype.nextBytes = ie),
      (re.prototype.init = oe),
      (re.prototype.next = ae)
      var Je
      var $e
      var Ke
      var Ze = 256
      if ($e == null) {
        ($e = []), (Ke = 0)
        var Qe
        if (Ne.appName == 'Netscape' && Ne.appVersion < '5' && window.crypto) {
          var tn = window.crypto.random(32)
          for (Qe = 0; Qe < tn.length; ++Qe) $e[Ke++] = 255 & tn.charCodeAt(Qe)
        }
        for (; Ke < Ze;) {
          (Qe = Math.floor(65536 * Math.random())),
          ($e[Ke++] = Qe >>> 8),
          ($e[Ke++] = 255 & Qe)
        }
        (Ke = 0), le()
      }
      he.prototype.nextBytes = ce
      var en = {
        hex: function (t) {
          return de(t)
        }
      }
      var nn = {
        hex: function (t) {
          return pe(t)
        }
      }
      var rn = function (t) {
        function e (t, e) {
          var n, i, r, o, a
          return (
            (r = 2147483648 & t),
            (o = 2147483648 & e),
            (n = 1073741824 & t),
            (i = 1073741824 & e),
            (a = (1073741823 & t) + (1073741823 & e)),
            n & i
              ? 2147483648 ^ a ^ r ^ o
              : n | i
                ? 1073741824 & a
                  ? 3221225472 ^ a ^ r ^ o
                  : 1073741824 ^ a ^ r ^ o
                : a ^ r ^ o
          )
        }
        function n (t, n, i, r, o, a, s) {
          return (
            (t = e(t, e(e((n & i) | (~n & r), o), s))),
            e((t << a) | (t >>> (32 - a)), n)
          )
        }
        function i (t, n, i, r, o, a, s) {
          return (
            (t = e(t, e(e((n & r) | (i & ~r), o), s))),
            e((t << a) | (t >>> (32 - a)), n)
          )
        }
        function r (t, n, i, r, o, a, s) {
          return (
            (t = e(t, e(e(n ^ i ^ r, o), s))),
            e((t << a) | (t >>> (32 - a)), n)
          )
        }
        function o (t, n, i, r, o, a, s) {
          return (
            (t = e(t, e(e(i ^ (n | ~r), o), s))),
            e((t << a) | (t >>> (32 - a)), n)
          )
        }
        function a (t) {
          var e
          var n = ''
          var i = ''
          for (e = 0; e <= 3; e++) {
            (i = (t >>> (8 * e)) & 255),
            (i = '0' + i.toString(16)),
            (n += i.substr(i.length - 2, 2))
          }
          return n
        }
        var s
        var u
        var l
        var f
        var c
        var h
        var d
        var p
        var y = []
        var t = (function (t) {
          for (
            var t = t.replace(/\r\n/g, '\n'), e = '', n = 0;
            n < t.length;
            n++
          ) {
            var i = t.charCodeAt(n)
            i < 128
              ? (e += String.fromCharCode(i))
              : (i > 127 && i < 2048
                ? (e += String.fromCharCode((i >> 6) | 192))
                : ((e += String.fromCharCode((i >> 12) | 224)),
                (e += String.fromCharCode(((i >> 6) & 63) | 128))),
              (e += String.fromCharCode((63 & i) | 128)))
          }
          return e
        })(t)
        var y = (function (t) {
          var e
          var n = t.length
          e = n + 8
          for (
            var i = 16 * ((e - e % 64) / 64 + 1),
              r = Array(i - 1),
              o = 0,
              a = 0;
            a < n;

          ) {
            (e = (a - a % 4) / 4),
            (o = (a % 4) * 8),
            (r[e] |= t.charCodeAt(a) << o),
            a++
          }
          return (
            (r[(a - a % 4) / 4] |= 128 << ((a % 4) * 8)),
            (r[i - 2] = n << 3),
            (r[i - 1] = n >>> 29),
            r
          )
        })(t)
        for (
          c = 1732584193,
          h = 4023233417,
          d = 2562383102,
          p = 271733878,
          t = 0;
          t < y.length;
          t += 16
        ) {
          (s = c),
          (u = h),
          (l = d),
          (f = p),
          (c = n(c, h, d, p, y[t + 0], 7, 3614090360)),
          (p = n(p, c, h, d, y[t + 1], 12, 3905402710)),
          (d = n(d, p, c, h, y[t + 2], 17, 606105819)),
          (h = n(h, d, p, c, y[t + 3], 22, 3250441966)),
          (c = n(c, h, d, p, y[t + 4], 7, 4118548399)),
          (p = n(p, c, h, d, y[t + 5], 12, 1200080426)),
          (d = n(d, p, c, h, y[t + 6], 17, 2821735955)),
          (h = n(h, d, p, c, y[t + 7], 22, 4249261313)),
          (c = n(c, h, d, p, y[t + 8], 7, 1770035416)),
          (p = n(p, c, h, d, y[t + 9], 12, 2336552879)),
          (d = n(d, p, c, h, y[t + 10], 17, 4294925233)),
          (h = n(h, d, p, c, y[t + 11], 22, 2304563134)),
          (c = n(c, h, d, p, y[t + 12], 7, 1804603682)),
          (p = n(p, c, h, d, y[t + 13], 12, 4254626195)),
          (d = n(d, p, c, h, y[t + 14], 17, 2792965006)),
          (h = n(h, d, p, c, y[t + 15], 22, 1236535329)),
          (c = i(c, h, d, p, y[t + 1], 5, 4129170786)),
          (p = i(p, c, h, d, y[t + 6], 9, 3225465664)),
          (d = i(d, p, c, h, y[t + 11], 14, 643717713)),
          (h = i(h, d, p, c, y[t + 0], 20, 3921069994)),
          (c = i(c, h, d, p, y[t + 5], 5, 3593408605)),
          (p = i(p, c, h, d, y[t + 10], 9, 38016083)),
          (d = i(d, p, c, h, y[t + 15], 14, 3634488961)),
          (h = i(h, d, p, c, y[t + 4], 20, 3889429448)),
          (c = i(c, h, d, p, y[t + 9], 5, 568446438)),
          (p = i(p, c, h, d, y[t + 14], 9, 3275163606)),
          (d = i(d, p, c, h, y[t + 3], 14, 4107603335)),
          (h = i(h, d, p, c, y[t + 8], 20, 1163531501)),
          (c = i(c, h, d, p, y[t + 13], 5, 2850285829)),
          (p = i(p, c, h, d, y[t + 2], 9, 4243563512)),
          (d = i(d, p, c, h, y[t + 7], 14, 1735328473)),
          (h = i(h, d, p, c, y[t + 12], 20, 2368359562)),
          (c = r(c, h, d, p, y[t + 5], 4, 4294588738)),
          (p = r(p, c, h, d, y[t + 8], 11, 2272392833)),
          (d = r(d, p, c, h, y[t + 11], 16, 1839030562)),
          (h = r(h, d, p, c, y[t + 14], 23, 4259657740)),
          (c = r(c, h, d, p, y[t + 1], 4, 2763975236)),
          (p = r(p, c, h, d, y[t + 4], 11, 1272893353)),
          (d = r(d, p, c, h, y[t + 7], 16, 4139469664)),
          (h = r(h, d, p, c, y[t + 10], 23, 3200236656)),
          (c = r(c, h, d, p, y[t + 13], 4, 681279174)),
          (p = r(p, c, h, d, y[t + 0], 11, 3936430074)),
          (d = r(d, p, c, h, y[t + 3], 16, 3572445317)),
          (h = r(h, d, p, c, y[t + 6], 23, 76029189)),
          (c = r(c, h, d, p, y[t + 9], 4, 3654602809)),
          (p = r(p, c, h, d, y[t + 12], 11, 3873151461)),
          (d = r(d, p, c, h, y[t + 15], 16, 530742520)),
          (h = r(h, d, p, c, y[t + 2], 23, 3299628645)),
          (c = o(c, h, d, p, y[t + 0], 6, 4096336452)),
          (p = o(p, c, h, d, y[t + 7], 10, 1126891415)),
          (d = o(d, p, c, h, y[t + 14], 15, 2878612391)),
          (h = o(h, d, p, c, y[t + 5], 21, 4237533241)),
          (c = o(c, h, d, p, y[t + 12], 6, 1700485571)),
          (p = o(p, c, h, d, y[t + 3], 10, 2399980690)),
          (d = o(d, p, c, h, y[t + 10], 15, 4293915773)),
          (h = o(h, d, p, c, y[t + 1], 21, 2240044497)),
          (c = o(c, h, d, p, y[t + 8], 6, 1873313359)),
          (p = o(p, c, h, d, y[t + 15], 10, 4264355552)),
          (d = o(d, p, c, h, y[t + 6], 15, 2734768916)),
          (h = o(h, d, p, c, y[t + 13], 21, 1309151649)),
          (c = o(c, h, d, p, y[t + 4], 6, 4149444226)),
          (p = o(p, c, h, d, y[t + 11], 10, 3174756917)),
          (d = o(d, p, c, h, y[t + 2], 15, 718787259)),
          (h = o(h, d, p, c, y[t + 9], 21, 3951481745)),
          (c = e(c, s)),
          (h = e(h, u)),
          (d = e(d, l)),
          (p = e(p, f))
        }
        return (a(c) + a(h) + a(d) + a(p)).toLowerCase()
      };
      (ve.prototype.doPublic = Se),
      (ve.prototype.setPublic = ge),
      (ve.prototype.encrypt = be),
      (ve.prototype.doPrivate = xe),
      (ve.prototype.setPrivate = _e),
      (ve.prototype.setPrivateEx = Pe),
      (ve.prototype.generate = Ce),
      (ve.prototype.decrypt = Te)
      var on = [];
      (on.sha1 = '3021300906052b0e03021a05000414'),
      (on.sha256 = '3031300d060960864801650304020105000420')
      var an = [];
      (an.sha1 = nn.hex),
      (an.sha256 = en.hex),
      (ve.prototype.signString = De),
      (ve.prototype.signStringWithSHA1 = Me),
      (ve.prototype.signStringWithSHA256 = Re),
      (ve.prototype.verifyString = Oe),
      (ve.prototype.verifyHexSignatureForMessage = Fe)
      var sn = (function () {
        var t = {
          Sbox: [
            99,
            124,
            119,
            123,
            242,
            107,
            111,
            197,
            48,
            1,
            103,
            43,
            254,
            215,
            171,
            118,
            202,
            130,
            201,
            125,
            250,
            89,
            71,
            240,
            173,
            212,
            162,
            175,
            156,
            164,
            114,
            192,
            183,
            253,
            147,
            38,
            54,
            63,
            247,
            204,
            52,
            165,
            229,
            241,
            113,
            216,
            49,
            21,
            4,
            199,
            35,
            195,
            24,
            150,
            5,
            154,
            7,
            18,
            128,
            226,
            235,
            39,
            178,
            117,
            9,
            131,
            44,
            26,
            27,
            110,
            90,
            160,
            82,
            59,
            214,
            179,
            41,
            227,
            47,
            132,
            83,
            209,
            0,
            237,
            32,
            252,
            177,
            91,
            106,
            203,
            190,
            57,
            74,
            76,
            88,
            207,
            208,
            239,
            170,
            251,
            67,
            77,
            51,
            133,
            69,
            249,
            2,
            127,
            80,
            60,
            159,
            168,
            81,
            163,
            64,
            143,
            146,
            157,
            56,
            245,
            188,
            182,
            218,
            33,
            16,
            255,
            243,
            210,
            205,
            12,
            19,
            236,
            95,
            151,
            68,
            23,
            196,
            167,
            126,
            61,
            100,
            93,
            25,
            115,
            96,
            129,
            79,
            220,
            34,
            42,
            144,
            136,
            70,
            238,
            184,
            20,
            222,
            94,
            11,
            219,
            224,
            50,
            58,
            10,
            73,
            6,
            36,
            92,
            194,
            211,
            172,
            98,
            145,
            149,
            228,
            121,
            231,
            200,
            55,
            109,
            141,
            213,
            78,
            169,
            108,
            86,
            244,
            234,
            101,
            122,
            174,
            8,
            186,
            120,
            37,
            46,
            28,
            166,
            180,
            198,
            232,
            221,
            116,
            31,
            75,
            189,
            139,
            138,
            112,
            62,
            181,
            102,
            72,
            3,
            246,
            14,
            97,
            53,
            87,
            185,
            134,
            193,
            29,
            158,
            225,
            248,
            152,
            17,
            105,
            217,
            142,
            148,
            155,
            30,
            135,
            233,
            206,
            85,
            40,
            223,
            140,
            161,
            137,
            13,
            191,
            230,
            66,
            104,
            65,
            153,
            45,
            15,
            176,
            84,
            187,
            22
          ],
          ShiftRowTab: [0, 5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 7, 12, 1, 6, 11]
        }
        return (
          (t.Init = function () {
            t.Sbox_Inv = Array(256)
            for (var e = 0; e < 256; e++) t.Sbox_Inv[t.Sbox[e]] = e
            for (t.ShiftRowTab_Inv = Array(16), e = 0; e < 16; e++) { t.ShiftRowTab_Inv[t.ShiftRowTab[e]] = e }
            for (t.xtime = Array(256), e = 0; e < 128; e++) { (t.xtime[e] = e << 1), (t.xtime[128 + e] = (e << 1) ^ 27) }
          }),
          (t.Done = function () {
            delete t.Sbox_Inv, delete t.ShiftRowTab_Inv, delete t.xtime
          }),
          (t.ExpandKey = function (e) {
            var n
            var i = e.length
            var r = 1
            switch (i) {
              case 16:
                n = 176
                break
              case 24:
                n = 208
                break
              case 32:
                n = 240
                break
              default:
                alert(
                  'my.ExpandKey: Only key lengths of 16, 24 or 32 bytes allowed!'
                )
            }
            for (var o = i; o < n; o += 4) {
              var a = e.slice(o - 4, o)
              o % i == 0
                ? ((a = [
                  t.Sbox[a[1]] ^ r,
                  t.Sbox[a[2]],
                  t.Sbox[a[3]],
                  t.Sbox[a[0]]
                ]),
                (r <<= 1) >= 256 && (r ^= 283))
                : i > 24 &&
                    o % i == 16 &&
                    (a = [
                      t.Sbox[a[0]],
                      t.Sbox[a[1]],
                      t.Sbox[a[2]],
                      t.Sbox[a[3]]
                    ])
              for (var s = 0; s < 4; s++) e[o + s] = e[o + s - i] ^ a[s]
            }
          }),
          (t.Encrypt = function (e, n) {
            var i = n.length
            t.AddRoundKey(e, n.slice(0, 16))
            for (var r = 16; r < i - 16; r += 16) {
              t.SubBytes(e, t.Sbox),
              t.ShiftRows(e, t.ShiftRowTab),
              t.MixColumns(e),
              t.AddRoundKey(e, n.slice(r, r + 16))
            }
            t.SubBytes(e, t.Sbox),
            t.ShiftRows(e, t.ShiftRowTab),
            t.AddRoundKey(e, n.slice(r, i))
          }),
          (t.Decrypt = function (e, n) {
            var i = n.length
            for (
              t.AddRoundKey(e, n.slice(i - 16, i)),
              t.ShiftRows(e, t.ShiftRowTab_Inv),
              t.SubBytes(e, t.Sbox_Inv),
              i -= 32;
              i >= 16;
              i -= 16
            ) {
              t.AddRoundKey(e, n.slice(i, i + 16)),
              t.MixColumns_Inv(e),
              t.ShiftRows(e, t.ShiftRowTab_Inv),
              t.SubBytes(e, t.Sbox_Inv)
            }
            t.AddRoundKey(e, n.slice(0, 16))
          }),
          (t.SubBytes = function (t, e) {
            for (var n = 0; n < 16; n++) t[n] = e[t[n]]
          }),
          (t.AddRoundKey = function (t, e) {
            for (var n = 0; n < 16; n++) t[n] ^= e[n]
          }),
          (t.ShiftRows = function (t, e) {
            for (var n = [].concat(t), i = 0; i < 16; i++) t[i] = n[e[i]]
          }),
          (t.MixColumns = function (e) {
            for (var n = 0; n < 16; n += 4) {
              var i = e[n + 0]
              var r = e[n + 1]
              var o = e[n + 2]
              var a = e[n + 3]
              var s = i ^ r ^ o ^ a;
              (e[n + 0] ^= s ^ t.xtime[i ^ r]),
              (e[n + 1] ^= s ^ t.xtime[r ^ o]),
              (e[n + 2] ^= s ^ t.xtime[o ^ a]),
              (e[n + 3] ^= s ^ t.xtime[a ^ i])
            }
          }),
          (t.MixColumns_Inv = function (e) {
            for (var n = 0; n < 16; n += 4) {
              var i = e[n + 0]
              var r = e[n + 1]
              var o = e[n + 2]
              var a = e[n + 3]
              var s = i ^ r ^ o ^ a
              var u = t.xtime[s]
              var l = t.xtime[t.xtime[u ^ i ^ o]] ^ s;
              (s ^= t.xtime[t.xtime[u ^ r ^ a]]),
              (e[n + 0] ^= l ^ t.xtime[i ^ r]),
              (e[n + 1] ^= s ^ t.xtime[r ^ o]),
              (e[n + 2] ^= l ^ t.xtime[o ^ a]),
              (e[n + 3] ^= s ^ t.xtime[a ^ i])
            }
          }),
          t
        )
      })()
      var un = (function () {
        var t = {}
        return (
          sn.Init(),
          (t.b256to64 = function (t) {
            var e
            var n
            var i
            var r = ''
            var o = 0
            var a = 0
            var s = t.length
            for (i = 0; i < s; i++) {
              (n = t.charCodeAt(i)),
              a == 0
                ? ((r += 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                  (n >> 2) & 63
                )),
                (e = (3 & n) << 4))
                : a == 1
                  ? ((r += 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                    e | ((n >> 4) & 15)
                  )),
                  (e = (15 & n) << 2))
                  : a == 2 &&
                        ((r += 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                          e | ((n >> 6) & 3)
                        )),
                        (o += 1),
                        (r += 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                          63 & n
                        ))),
              (o += 1),
              (a += 1),
              a == 3 && (a = 0)
            }
            return (
              a > 0 &&
                  ((r += 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                    e
                  )),
                  (r += '=')),
              a == 1 && (r += '='),
              r
            )
          }),
          (t.b64to256 = function (t) {
            var e
            var n
            var i = ''
            var r = 0
            var o = 0
            var a = t.length
            for (n = 0; n < a; n++) {
              (e = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.indexOf(
                t.charAt(n)
              )),
              e >= 0 &&
                    (r &&
                      (i += String.fromCharCode(o | ((e >> (6 - r)) & 255))),
                    (r = (r + 2) & 7),
                    (o = (e << r) & 255))
            }
            return i
          }),
          (t.b16to64 = function (t) {
            var e
            var n
            var i = ''
            for (
              t.length % 2 == 1 && (t = '0' + t), e = 0;
              e + 3 <= t.length;
              e += 3
            ) {
              (n = parseInt(t.substring(e, e + 3), 16)),
              (i +=
                    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                      n >> 6
                    ) +
                    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                      63 & n
                    ))
            }
            for (
              e + 1 == t.length
                ? ((n = parseInt(t.substring(e, e + 1), 16)),
                (i += 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                  n << 2
                )))
                : e + 2 == t.length &&
                    ((n = parseInt(t.substring(e, e + 2), 16)),
                    (i +=
                      'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                        n >> 2
                      ) +
                      'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                        (3 & n) << 4
                      )));
              (3 & i.length) > 0;

            ) { i += '=' }
            return i
          }),
          (t.b64to16 = function (t) {
            var e
            var n
            var i = ''
            var r = 0
            for (e = 0; e < t.length && t.charAt(e) != '='; ++e) {
              (v = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.indexOf(
                t.charAt(e)
              )),
              v < 0 ||
                    (r == 0
                      ? ((i += u(v >> 2)), (n = 3 & v), (r = 1))
                      : r == 1
                        ? ((i += u((n << 2) | (v >> 4))), (n = 15 & v), (r = 2))
                        : r == 2
                          ? ((i += u(n)),
                          (i += u(v >> 2)),
                          (n = 3 & v),
                          (r = 3))
                          : ((i += u((n << 2) | (v >> 4))),
                          (i += u(15 & v)),
                          (r = 0)))
            }
            return r == 1 && (i += u(n << 2)), i
          }),
          (t.string2bytes = function (t) {
            for (var e = [], n = 0; n < t.length; n++) { e.push(t.charCodeAt(n)) }
            return e
          }),
          (t.bytes2string = function (t) {
            for (var e = '', n = 0; n < t.length; n++) { e += String.fromCharCode(t[n]) }
            return e
          }),
          (t.blockXOR = function (t, e) {
            for (var n = Array(16), i = 0; i < 16; i++) n[i] = t[i] ^ e[i]
            return n
          }),
          (t.blockIV = function () {
            var t = new he()
            var e = Array(16)
            return t.nextBytes(e), e
          }),
          (t.pad16 = function (t) {
            var e = t.slice(0)
            var n = (16 - t.length % 16) % 16
            for (i = t.length; i < t.length + n; i++) e.push(0)
            return e
          }),
          (t.depad = function (t) {
            for (t = t.slice(0); t[t.length - 1] == 0;) { t = t.slice(0, t.length - 1) }
            return t
          }),
          (t.encryptAESCBC = function (e, n) {
            var i = n.slice(0)
            sn.ExpandKey(i)
            for (
              var r = t.string2bytes(e),
                r = t.pad16(r),
                o = t.blockIV(),
                a = 0;
              a < r.length / 16;
              a++
            ) {
              var s = r.slice(16 * a, 16 * a + 16)
              var u = o.slice(16 * a, 16 * a + 16)
              var s = t.blockXOR(u, s)
              sn.Encrypt(s, i), (o = o.concat(s))
            }
            return (i = t.bytes2string(o)), t.b256to64(i)
          }),
          (t.decryptAESCBC = function (e, n) {
            var i = n.slice(0)
            sn.ExpandKey(i)
            for (
              var e = t.b64to256(e), r = t.string2bytes(e), o = [], a = 1;
              a < r.length / 16;
              a++
            ) {
              var s = r.slice(16 * a, 16 * a + 16)
              var u = r.slice(16 * (a - 1), 16 * (a - 1) + 16)
              sn.Decrypt(s, i), (s = t.blockXOR(u, s)), (o = o.concat(s))
            }
            return (o = t.depad(o)), t.bytes2string(o)
          }),
          (t.wrap60 = function (t) {
            for (var e = '', n = 0; n < t.length; n++) { n % 60 == 0 && n != 0 && (e += '\n'), (e += t[n]) }
            return e
          }),
          (t.generateAESKey = function () {
            var t = Array(16)
            return new he().nextBytes(t), t
          }),
          (t.generateRSAKey = function (t, e) {
            Math.seedrandom(en.hex(t))
            var n = new ve()
            return n.generate(e, '10001'), n
          }),
          (t.publicKeyString = function (t) {
            return (pubkey = t.n.toString(16))
          }),
          (t.publicKeyID = function (t) {
            return rn(t)
          }),
          (t.publicKeyFromString = function (t) {
            var t = t.split('|')[0]
            var e = new ve()
            return e.setPublic(t, '10001'), e
          }),
          (t.encrypt = function (e, n, i) {
            var r = ''
            try {
              var o = t.publicKeyFromString(n)
              r += o.encrypt(e) + '?'
            } catch (t) {
              return { status: 'Invalid global key' }
            }
            return { status: 'success', cipher: r }
          }),
          (t.decrypt = function (t, e) {
            var n = t.split('?')
            var i = e.decrypt(n[0])
            return { status: 'success', plaintext: i, signature: 'unsigned' }
          }),
          t
        )
      })()
      t.exports = un
    }
  ])
})
