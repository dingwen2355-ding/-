function pxResizw(res) {
  const clientWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
  if (!clientWidth) return
  let fontSize = clientWidth / 375
  return res * fontSize
}
export default pxResizw
