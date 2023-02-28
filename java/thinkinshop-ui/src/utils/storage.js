// 储存用户信息
const userInfo = 'laike_admin_uaerInfo'
// 储存来源
const source = 'laike_source'

export function setStorage(name, value) {
    localStorage.setItem(name,JSON.stringify(value));
}

export function getStorage(name){
    return JSON.parse(localStorage.getItem(name));
}

export function removeStorage(name){
    localStorage.removeItem(name);
}