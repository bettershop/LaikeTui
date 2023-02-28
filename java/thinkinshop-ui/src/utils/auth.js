import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token'
let num = new Date().getTime() //失效时间
let time= new Date(num + 60 * 60 * 1000 * 2);

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token, {
    expires: time
  })
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
