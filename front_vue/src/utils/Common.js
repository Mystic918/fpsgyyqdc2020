var isMobile = function(str) {
  str = str || ''
  var reg = /^1[3456789]\d{9}$/
  return reg.test(str)
}

var isComplexPassword = function(str) {
  str = str || ''
  var reg = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,16}$/
  return reg.test(str)
}

var formatNumber = function(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

var formatTime = function(number, format) {
  var formateArr = ['Y', 'M', 'D', 'h', 'm', 's']
  var returnArr = []

  var date = new Date(number * 1000)
  returnArr.push(date.getFullYear())
  returnArr.push(formatNumber(date.getMonth() + 1))
  returnArr.push(formatNumber(date.getDate()))

  returnArr.push(formatNumber(date.getHours()))
  returnArr.push(formatNumber(date.getMinutes()))
  returnArr.push(formatNumber(date.getSeconds()))

  for (var i in returnArr) {
    format = format.replace(formateArr[i], returnArr[i])
  }
  return format
}

var arrIndex = function(arr, val) {
  var result = -1
  for (let i = 0; i < arr.length; i++) {
    if (arr[i] == val) {
      result = i
    }
  }
  return result
}

var arrRemove = function(arr, val) {
  var newArr = []
  arr.map(item => {
    if (item != val) {
      newArr.push(item)
    }
  })
  return newArr
}

var arrRange = function(start, end) {
  var arr = []
  for (let i = start; i < end; i++) {
    arr.push(i)
  }
  return arr
}

module.exports = {
  isMobile,
  isComplexPassword,
  formatTime,
  arrIndex,
  arrRemove,
  arrRange
}
