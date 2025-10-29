'use strict'
import "./utils";
import moment from 'moment'

export function parseTime(time, format = "YYYY-MM-DD HH:mm:ss") {
  if (!time) {
    return ''
  } else {
    return moment(time).format(format)
  }
}
export function filterSize(bytes) {
  if (bytes === 0) return "0 B";
  var k = 1024,
    sizes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"],
    i = Math.floor(Math.log(bytes) / Math.log(k));
  return (bytes / Math.pow(k, i)).toPrecision(3) + sizes[i];
}