// pages/user/user.js
var app = getApp()
Page({
	data: {
		list: [
      {
        icon: 'icon-user-bangding.png',
        text: '完善资料',
        url: 'bangding/bangding'
      },{
				icon: 'wdsc.png',
				text: '我的收藏',
				url: 'collection/collection'
			},
			{
				icon: 'zj.png',
				text: '历史记录',
				url: 'footprint/footprint'
			},
			{
				icon: 'dz.png',
				text: '地址管理',
				url: 'address/index'
			},
			{
				icon: 'sz.png',
				text: '设置',
				url: 'set/set'
			}
		],
		cont: 1,
		remind: '加载中',
    tjr:false,
	},
	//下拉刷新
	onPullDownRefresh: function() {
		wx.showNavigationBarLoading() //在标题栏中显示加载
		setTimeout(function() {
			wx.hideNavigationBarLoading() //完成停止加载
			wx.stopPullDownRefresh() //停止下拉刷新
		}, 1500);
		this.requestMyData();
	},
  copyText: function (t) { 
    var a = t.currentTarget.dataset.text; 
    wx.setClipboardData({ 
      data: a, 
      success: function () { 
          wx.showToast({ 
            title: "已复制" 
            })
          }
        }) 
    },
	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
		wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数7a896c app.d.bgcolor
			animation: {
				duration: 400,
				timingFunc: 'easeIn'
			}
		});
		var plug_ins = app.globalData.userInfo.plug_ins; // 插件
		this.setData({
      bgcolor: app.d.bgcolor, // 背景颜色
			plug_ins: plug_ins, // 插件
		});
		this.requestMyData();
	},
	//页面加载完成函数
	onReady: function() {
		var that = this;
		setTimeout(function() {
			that.setData({
				remind: ''
			});
		}, 1000);
	},
	onShow: function() {
		var cont = this.data.cont;
		var that = this;
		if(cont > 1) {
			that.requestMyData();
		} else {
			that.setData({
				cont: cont + 1
			})
		}
	},

	requestMyData: function() {
		var that = this;
		wx.request({
			url: app.d.ceshiUrl + '&action=user&m=index',
			method: 'post',
			data: {
				openid: app.globalData.userInfo.openid
			},
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: function(res) {
				var status = res.data.status;
				if(status == 1) {
					var user = res.data.user;
					if(user.wx_name == 'undefined') {
						that.setData({
							userlogin: true
						});
						that.login();
					} else {
						that.setData({
							user: user,
							article: res.data.article,
							logo: res.data.logo,
							company: res.data.company,
							th: res.data.th,
							dfh_num: res.data.dfh_num,
							dfk_num: res.data.dfk_num,
							dpj_num: res.data.dpj_num,
							dsh_num: res.data.dsh_num,
							plug_ins: res.data.plug_ins,
              tjr: res.data.tjr,
              support: res.data.support
						});
					}
				} else {
					wx.showToast({
						title: '非法操作！',
						duration: 2000
					});
				}
        app.userlogin(1);
			},
			error: function(e) {
				wx.showToast({
					title: '网络异常！',
					duration: 2000
				});
			}
		});
	},
  go: function (e) {
    console.log(e)
    var url = e.currentTarget.dataset.url
    wx.navigateTo({
      url: url
    })
  },
})