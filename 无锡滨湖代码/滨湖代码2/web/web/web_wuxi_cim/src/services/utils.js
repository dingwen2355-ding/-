'use strict'

/* eslint-disable */
String.prototype.isEmpty = function () {
  if (this === null || this === undefined || this === '' || this.toString().trim() === '') {
    return true
  } else {
    return false
  }
}
Number.prototype.isEmpty = function () {
  if (this === null || this === undefined || this === '' || this.toString().trim() === '') {
    return true
  } else {
    return false
  }
}
Array.prototype.isEmpty = function () {
  if (this === null || this === undefined || this === [] || this.length === 0) {
    return true
  } else {
    return false
  }
}