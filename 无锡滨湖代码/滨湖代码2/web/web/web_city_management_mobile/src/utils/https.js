export function httpToHppts(v) {
  if (location.protocol === 'https:') {
    return v.replace('http://39.75.167.141:8090', 'https://39.75.167.141:8092')
  } else {
    return v
  }
}
