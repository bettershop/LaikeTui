define(function(){
	/**
	
	参数:
		parentid: 父分类表单 id
		childid: 子分类表单 id
	
	示例:
		<select name="industry_1" id="industry_1" class="form-control" value="{$item['industry1']}"></select>
		<select name="industry_2" id="industry_2" class="form-control" value="{$item['industry2']}"></select>
		<script type="text/javascript">
			require(['industry'], function(industry){
				industry.init('industry_1','industry_2');
			});
		</script>
	*/
	var init = function(parentid, childid){
		var CFD="不限";var CSD="不限";var ShowT=1;var CFData=[];var CSData=[];var CAData='';var CLIST='美食-1$本帮江浙菜-11,川菜-12,粤菜-13,湘菜-14,贵州菜-15,东北菜-16,台湾菜-17,新疆/清真菜-18,西北菜-19,素菜-20,火锅-21,自助餐-22,小吃快餐-23,日本-24,韩国料理-25,东南亚菜-26,西餐-27,面包甜点-28,其他-29#休闲娱乐-2$密室-30,咖啡厅-31,酒吧-32,茶馆-33,KTV-34,电影院-35,文化艺术-36,景点/郊游-37,公园-38,足疗按摩-39,洗浴-40,游乐游艺-41,桌球-42,桌面游戏-43,DIY手工坊-44,其他-45#购物-3$综合商场-46,食品茶酒-47,服饰鞋包-48,珠宝饰品-49,化妆品-50,运动户外-51,亲子购物-52,品牌折扣店-53,数码家电-54,家居建材-55,特色集市-56,书店-57,花店-58,眼镜店-59,超市/便利店-60,药店-61,其他-62#丽人-4$美发-63,美容/SPA-64,化妆品-65,瘦身纤体-66,美甲-67,瑜伽-68,舞蹈-69,个性写真-70,整形-71,齿科-72,其他-73#结婚-5$婚纱摄影-74,婚宴-75,婚戒首饰-76,婚纱礼服-77,婚庆公司-78,彩妆造型-79,司仪主持-80,婚礼跟拍-81,婚车租赁-82,婚礼小商品-83,婚房装修-84,其他-85#亲子-6$幼儿教育-86,亲子摄影-87,亲子游乐-88,亲子购物-89,孕产护理-90,其他-91#运动健身-7$游泳馆-92,羽毛球馆-93,健身中心-94,瑜伽-95,舞蹈-96,篮球场-97,网球场-98,足球场-99,高尔夫场-100,保龄球馆-101,桌球馆-102,乒乓球馆-103,武术场馆-104,体育场馆-105,其他-106#酒店-8$五星级酒店-107,四星级酒店-108,三星级酒店-109,经济型酒店-110,公寓式酒店-111,精品酒店-112,青年旅舍-113,度假村-114,其他-115#爱车-9$4S店/汽车销售-116,汽车保险-117,维修保养-118,配件/车饰-119,驾校-120,汽车租赁-121,停车场-122,加油站-123,其他-124#生活服务-10$旅行社-125,培训-126,室内装潢-127,宠物-128,齿科-129,快照/冲印-130,家政-131,银行-132,学校-133,团购网站-134,其他-135#其他-11$其他-136';function CS(){if(ShowT)CLIST=CFD+"$"+CSD+"#"+CLIST;CAData=CLIST.split("#");for(var i=0;i<CAData.length;i++){parts=CAData[i].split("$");CFData[i]=parts[0];CSData[i]=parts[1].split(",")}var self=this;this.SelF=document.getElementById(parentid);this.SelS=document.getElementById(childid);this.DefF=this.SelF.getAttribute('value');this.DefS=this.SelS.getAttribute('value');this.SelF.CS=this;this.SelS.CS=this;this.SelF.onchange=function(){CS.SetS(self)};CS.SetF(this)};CS.SetF=function(self){for(var i=0;i<CFData.length;i++){var title,value;title=CFData[i].split("-")[0];value=CFData[i].split("-")[0];if(title==CFD){value=""}self.SelF.options.add(new Option(title,value));if(self.DefF==value){self.SelF[i].selected=true}}CS.SetS(self)};CS.SetS=function(self){var fi=self.SelF.selectedIndex;var slist=CSData[fi];self.SelS.length=0;if(self.SelF.value!=""&&ShowT){self.SelS.options.add(new Option(CSD,""))}for(var i=0;i<slist.length;i++){var title,value;title=slist[i].split("-")[0];value=slist[i].split("-")[0];if(title==CSD){value=""}self.SelS.options.add(new Option(title,value));if(self.DefS==value){self.SelS[self.SelF.value!=""?i+1:i].selected=true}}};
		CS();
	};
	return {init: init};
});