export function drawCurvePath(ctx, start, end, curveness, percent) {
  var cp = [
    (start[0] + end[0]) / 2 - (start[1] - end[1]) * curveness,
    (start[1] + end[1]) / 2 - (end[0] - start[0]) * curveness
  ]

  var t = percent / 100

  var p0 = start
  var p1 = cp
  var p2 = end

  var v01 = [p1[0] - p0[0], p1[1] - p0[1]] // 向量<p0, p1>
  var v12 = [p2[0] - p1[0], p2[1] - p1[1]] // 向量<p1, p2>

  var q0 = [p0[0] + v01[0] * t, p0[1] + v01[1] * t]
  var q1 = [p1[0] + v12[0] * t, p1[1] + v12[1] * t]

  var v = [q1[0] - q0[0], q1[1] - q0[1]] // 向量<q0, q1>

  var b = [q0[0] + v[0] * t, q0[1] + v[1] * t]

  ctx.moveTo(p0[0], p0[1])

  ctx.quadraticCurveTo(q0[0], q0[1], b[0], b[1])
}
export function drawTriangle(ctx, x1, y1, x2, y2, x3, y3, color, type) {
  ctx.beginPath()
  ctx.moveTo(x1, y1)
  ctx.lineTo(x2, y2)
  ctx.lineTo(x3, y3)
  ctx[type + 'Style'] = color
  ctx.closePath()
  ctx[type]()
}
