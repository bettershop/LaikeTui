const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  name: state => state.user.name,
  merchants_Logo: state => state.user.merchants_Logo,
  head_img: state => state.user.head_img,
  authorizationList: state => state.user.authorizationList,
  permission_routes:state => state.permission.routes,
  language: state => state.lang.language,
  orderListNum: state => state.orderNum.orderListNum,
  physicalOrderNum: state => state.orderNum.physicalOrderNum,
  activityOrderNum: state => state.orderNum.activityOrderNum,
  refundListNum: state => state.orderNum.refundListNum,
  secOrderNum: state => state.orderNum.secOrderNum,
  inOrderNum: state => state.orderNum.inOrderNum

}
export default getters
