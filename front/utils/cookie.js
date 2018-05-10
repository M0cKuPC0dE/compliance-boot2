export default (vc) => {
  var result = {}
  var cookie = vc.isServer ? vc.req.headers.cookie : document.cookie
  cookie.split('; ').forEach(function (sp) {
    var arr = sp.split('=')
    arr[1] && (result[arr[0]] = arr[1])
  })
  return result
}
