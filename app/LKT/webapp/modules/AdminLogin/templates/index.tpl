<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<LINK rel="Bookmark" href="/favicon.ico" >
	<LINK rel="Shortcut Icon" href="/favicon.ico" />
	
	<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
	<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
	<link href="style/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
	<link href="style/css/style.css" rel="stylesheet" type="text/css" />
	<script src="style/js/message.js"></script>
	<link rel="stylesheet" type="text/css" href="style/css/font-awesome.min.css">
	<link rel="stylesheet" href="style/css/message.css">
	<link rel="stylesheet" href="style/css/changeIndex.css" />

	<title>LaiKeTui商城系统 · 专注用户体验</title>
	{literal}
		<style type="text/css">
			.bk_2{
				/*overflow: scroll;*/
			}
			.bk_2::-webkit-scrollbar{
				display: none;
			}
			.Hui-article{
				top: 65px;
			}
			#Hui-tabNav{
				height: 65px;
				background: none;
			}
			.topTitle{
				display: inline-block;
				height: 65px;
				line-height: 65px;
				font-size: 18px;
				padding-left: 30px;
				color: #414658;
				position: relative;
				top: -35px;
			}
			.Hui-userbar li a img{
				width: 18px;
				height: 18px;
			}

			.userIdImg{
				width: 35px!important;
				height: 35px!important;
				border-radius: 50%;
			}
			.dropDown-menu li{
				height: 40px;
				line-height: 40px;
			}
			.dropDown-menu li a{
				height: 40px;
				line-height: 40px;
			}
			.dropDown-menu li a:hover{
				background: #f6f7f8;
			}
			.dropDown_A:hover{
				background: none!important;
			}
			.Hui-userbar > li > a:hover, .Hui-userbar > li.current > a{
				background-color: none!important;
			}
			.sp5 dt{
				border-left: 3px solid #008DEF;
				box-sizing: border-box;
			}
			.mask1{
				position: absolute;
				z-index: 9999999;
				top: 0;
				bottom: 0;
				left: 0;
				right: 0;
				background: rgba(0,0,0,0.6);
				display: none;
			}
			.mask1Content{
				width: 500px;
				height: 300px;
				margin: 0 auto;
				position: relative;
				top: 200px;
				background: #fff;
				border-radius: 10px;
			}
			#jbxx,#changePassword{
				position: absolute;
				z-index: 9999999;
				top: 0;
				bottom: 0;
				left: 0;
				right: 0;
				background: rgba(102,102,102,.1);
				display: none;
			}
			
			#chang_div{
				position: absolute;
				top:60px;
				bottom: 0;
				left: 0;
				right: 0;
				width: 100%;
				display: flex;
			}
			
			.maskContent1{
				width: 500px;
				margin: auto;
				position: relative;
				background: #fff;
				border-radius: 10px;
				padding: 10px 0px;
			}
			.maskTitle{
				height: 50px;
				padding-left: 30px;
				line-height: 50px;
				text-align: left;
				color: #414658;
				font-size: 16px;
				border-bottom: 2px solid #e9ecef;
			}
			.iptDiv{
				height: 40px;
			}
			.iptLeft{
				width: 25%;
				float: left;
				text-align: right;
				padding-right: 5px;
				box-sizing: border-box;
				line-height: 35px;
				height: 35px;
			}
			.iptRight{
				width: 75%;
				float: left;
				color: #414658;
			}
			.iptRight input{
				border: 1px solid #d5dbe8;
				width: 80%;
				height: 35px;
				line-height: 35px;
				border-radius: 5px;
				padding-left: 20px;
			}
			::-webkit-input-placeholder { /* WebKit, Blink, Edge */
			    color:    #97a0b4;
			}
			:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
			   color:    #97a0b4;
			}
			::-moz-placeholder { /* Mozilla Firefox 19+ */
			   color:    #97a0b4;
			}
			:-ms-input-placeholder { /* Internet Explorer 10-11 */
			   color:    #97a0b4;
			}
			.maskContent1 input[type=submit]{
				width: 100px;
				height: 40px;
				border: 1px solid #eee;
				border-radius: 5px;
				background: #008DEF;
				color: #fff;
				font-size: 16px;
				line-height: 40px;
				display: inline-block;
				margin-right: 30px;
				
			}
			.closeMaskBtn{
				width: 100px;
				height: 40px;
				border-radius: 5px;
				background: #fff;
				color: #008DEF;
				border: 1px solid #008DEF;
				font-size: 16px;
				line-height: 40px;
				display: inline-block;
				text-align: center;
				box-sizing: border-box;
				cursor: pointer;
			}
			.closeA{
				position: absolute;
				right:10px;
				top: 15px;
				width: 30px;
				height: 30px;
				color: #eee;
			}
			
			/*个人资料*/
			.mezl_img{
				padding-top: 35px;
				text-align: center;
			}
			.mezl_img img{
				width: 88px;
				height: 88px;
				display: block;
				margin: 0 auto;
				border-radius: 50%;
				background-size: 100% 100%;
			}
			.mezl_name{
				font-size: 16px;
				color: #414658;
				padding: 15px 10px 0px;
			}
			.mezl_zhz{
				font-size: 14px;
				color: #97a0b4;
				line-height: 28px;
			}
			.mezl_radio span{
				padding: 0 10px;
			}
			.mezl_radio input{
				margin-top: 0px;
				width:15px;
			}
			.mezl_radio label{
				margin-right: 20px;
			}
			
			.mezl_sie{
				width: 88.5%;
			}
			
			.mezl_sie div{
				width:30%;
				margin-right: 2%;
				float: left;
				position: relative;
			}
			
			.jbxx_div input{
				padding-left: 10px;
			}
			
			.mezl_si select{
				width: 100%;
				border: 1px solid #d5dbe8;
			    height: 35px;
			    line-height: 35px;
			    border-radius: 5px;
			    outline: none;
			    appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                padding-left: 10px;
                	
			}
			.mezl_sie div:after{
                content: "";
                width: 10px;
                height: 5px;
                background-image: url(images/xia.png);
                background-size: 100% 100%;
                position: absolute;
                right: 8px;
                top:15px;
                pointer-events: none;
            }
            
            
            .jbxx_div .submit{
            	padding: 0;
            }
            .jbxx_div input::-webkit-outer-spin-button,
		    .jbxx_div input::-webkit-inner-spin-button {
		        -webkit-appearance: none;
		    }
            
            input[type="number"]{
		        -moz-appearance: textfield;
		    }
		    
		    .magic-radio {  
			  position: absolute;  
			  display: none; 
			}  
			  
			.magic-radio + label {  
			  position: relative;  
			  padding-left: 30px;  
			  cursor: pointer;  
			  vertical-align: middle;
			}  
			.magic-radio + label:hover:before {  
			    animation-duration: 0.4s;  
			    animation-fill-mode: both;  
			    animation-name: hover-color; 
			}
			
			.magic-radio + label.radio1:before,.magic-radio + label.radio2:before {  
			    position: absolute;  
			    top: 0;  
			    left: 0;  
			    display: inline-block;  
			    width: 15px;  
			    height: 15px;  
			    content: '';  
			    border: 1px solid #B4BED2
			}  
			
			.magic-radio + label:after {  
			    position: absolute;  
			    display: none;  
			    content: ''
			}
			.mezl_radio{padding-top: 6px;}
			.magic-radio[disabled] + label {  
			  cursor: not-allowed;  
			  color: #e4e4e4; }  
			 .magic-radio:checked + label:before {  
			  animation-name: none; }  
			  
			.magic-radio:checked + label:after {  
			  display: block; }  
			  
			.magic-radio + label:before {  
			  border-radius: 50%; }  
			.magic-radio + label.radio1:after,.magic-radio + label.radio2:after {  
			  top: 0px;  
			  left: 1px;  
			  width: 11px;  
			  height: 11px;
			  margin: 2.5px;
			  border-radius: 50%;  
			  background:#2890FF;
			} 
			.message-bell-btn{
				margin: 0px;
			}
			.icon-xiaoxi{
				display: none;
			}
			.icon-tixing{
				display: flex;
				align-items: center;
				height: 18px;
				width: 18px;
			}
			.icon-tixing:before{
				display: inline-block;
				content:"";
				width: 18px;
				height: 18px;
				background: url(images/iIcon/xiaoxi.png) no-repeat;
				background-size: 18px 18px;
				padding:0px 10px;
				position: relative;
    			top: 6px;
			}
			.message-bell{
				height: 60px;
				line-height: 60px;
			}
			#changePsw{
				cursor: pointer;
			}
			#changeInf{
				cursor: pointer;
			}
		  
			.footers {
				height: 40px;
				width: 98%;
				background-color: #222;
				color: #ddd;
				z-index: 5000;
				position: fixed;
				bottom: 0;
				left: 0;
				padding: 0px 20px;
				display: flex;
				justify-content:space-between;
				align-items: center;
			}
			
			.ba {
				color: #ddd;
			}

			.ba:hover {
				color: #06c;
			}
		</style>
	{/literal}
</head>
<body>
<header class="Hui-header cl" style="margin:0;">
	<a class="Hui-logo l" title="H-ui.admin v2.3" href="index.php?module=AdminLogin">
		<img style="width: 100px;" src="images/admin_logo.png">
	</a>
	<a class="Hui-logo-m l" href="index.php?module=AdminLogin" title="H-ui.admin"></a>
	<span class="Hui-subtitle l"></span>
	<a class="changeAside" href="javascript:void(0)">
		<img style="width: 20px;" src="images/iIcon/qh.png" alt="" />
	</a>
	<ul class="bigUl">
		<li {if $type == 0}class="active"{/if}><a href="index.php?module=AdminLogin">平台</a></li>
		<li {if $type == 1}class="active"{/if}><a href="index.php?module=AdminLogin&type=1">小程序</a></li>
		<li><a class="none" href="#javascript" title="正在开发中，暂未发布">APP</a></li>
		<li><a class="none" href="#javascript" title="正在开发中，暂未发布">数据报表</a></li>
	</ul>

	<ul class="Hui-userbar">
		<li class="dropDown dropDown_hover headerLi">
			<a href="#" class="dropDown_A">
				<img class="userIdImg" src="images/iIcon/tx.png" alt="" />
				{$nickname}
				<i class="Hui-iconfont">&#xe6d5;</i>
			</a>
			<ul class=" sp1 dropDown-menu radius box-shadow sysBtn">
				<li>
					<a  href="javascript:void(0)" title="修改密码">
						<i><img src="images/iIcon/xg.png"/>
							<img src="images/iIcon/xgmm_h.png" style="display: none;"/>
						</i>
						&nbsp;修改密码
					</a>
				</li>

				<li>
					<a title="基本信息">
						<i><img src="images/iIcon/xinxi.png"/>
							<img src="images/iIcon/jbxx_h.png" style="display: none;"/>
						</i>
						&nbsp;基本信息
					</a>
				</li>
			</ul>
		</li>
		<li class="headerLi" style="height: 60px;">
			<div id="message">

			</div>
			

		</li>
		<li id="Hui-skin" class="headerLi dropDown right dropDown_hover">
			<a href="javascript:;" title="换肤">
				<i class="" style="font-size:18px"> <img src="images/iIcon/hf_1.png"/></i>
			</a>
			<ul class="dropDown-menu radius box-shadow changeColor" style="    position: absolute;left: -35px!important;width: 120px;">
				<li class="color1" ><a href="javascript:;" data-val="default" data-color="#000" style="background-color: #fff; color: #000;" title="默认（黑色）">默认（黑色）</a></li>

				<li class="color2"><a href="javascript:;" data-val="blue" data-color="#2d6dcc" style="background-color: #fff;color: #000;" title="蓝色">蓝色</a></li>

				<li class="color3"><a href="javascript:;" data-val="green" data-color="#19a97b" style="background-color: #fff;color: #000;" title="绿色">绿色</a></li>

				<li class="color4"><a href="javascript:;" data-val="red" data-color="#c81623" style="background-color: #fff;color: #000;" title="红色">红色</a></li>

				<li class="color5"><a href="javascript:;" data-val="yellow" data-color="#ffd200" style="background-color: #fff;color: #000;" title="黄色">黄色</a></li>

				<li class="color6"><a href="javascript:;" data-val="orange" data-color="#ff4a00" style="background-color: #fff;color: #000;" title="橙色">橙色</a></li>
			</ul>
		</li>
		<li>
			<a href="index.php?module=Login&action=logout" title="退出系统">
				<img src="images/iIcon/tc.png"/>
			</a>
		</li>


	</ul>
	<a href="javascript:;" class="Hui-nav-toggle Hui-iconfont" aria-hidden="false">&#xe667;</a>
</header>
<aside class="Hui-aside" style="background: #fff;">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		{if $type == 0}
		<dl id="menu-article"  class="active sp2 sp5">
			<dt >
				<span class="asideImgWrap">
					<img class="asideImg" src="images/iIcon/sy_1.png" />
					<img class="asideImg" src="images/iIcon/sy.png" />
				</span>
				<a class="index1" href="index.php?module=AdminLogin" data-title="系统首页">
					<span class="t2" style="color: #0080ff;">系统首页</span>
				</a>
			</dt>
		</dl>
		{/if}
		{foreach from=$list item=item name=f1}
			<dl class="menu-system">
				<dt {if $smarty.foreach.f1.index==0 && $type !=0 }class="selected"{/if} >
					<span class="asideImgWrap">
						<img class="asideImg" src="{$item->image}" style="width: 19px;height: 18px;"/>
						<img class="asideImg" src="{$item->image1}" style="width: 19px;height: 18px;"/>
					</span>
					<span class="t2">{$item->title}</span>
					<span class="asideImgWrapRight">
						<img class="asideImgRight" src="images/iIcon/sq.png"/>
						<img class="asideImgRight" src="images/iIcon/zk.png"/>
					</span>
				</dt>
				<dd {if $smarty.foreach.f1.index==0 && $type !=0}style="display: block;"{/if} >
					<ul>
						{foreach from=$item->res item=item1 name=f2}
							<li class="textApi"><a _href="{$item1->url}" data-title="{$item1->title}" href="javascript:void(0)">{$item1->title}</a></li>
						{/foreach}
					</ul>
				</dd>
			</dl>
		{/foreach}

	</div>
</aside>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp" style="opacity: 0;visibility: hidden;">
			<ul id="min_title_list" class="acrossTab cl" style="margin:0;">
				<li class="active"><span>系统首页</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group">
			<a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
		</div>
		<a class="topTitle" style="" title="系统首页" data-href="index.php?module=index">系统首页</a>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div  class="loading"></div>
			{if $type == 0}
			<iframe id="childframe" scrolling="yes" frameborder="0" src="index.php?module=index"></iframe>
			{else}
			<iframe id="childframe" scrolling="yes" frameborder="0" src="index.php?module=system"></iframe>
			{/if}
		</div>
	</div>
	
</section>
<div class="mask1">
	<div class="mask1Content">
		<div style="height: 100px;position: relative;top: 20%;font-size: 22px;text-align: center;">
			更多功能查看商业V3版<br/>
			【微信+支付宝+百度+抖音】小程序+Saas+APP+会员制+H5+多店铺<br/>完美的二开神器+打通微信支付宝APP支付<br/>
			<a target="_blank" style="color:red" href="http://www.laiketui.com/action/">【点击查看】</a>
		</div>
		<button class="closeMask" style="bottom: 5px;position: absolute;left: 38%;">确认</button>
	</div>
</div>
<div id="changePassword">
	<div id="chang_div">
		<div class="maskContent1" style="height: 320px;">
			<a href="javascript:void(0);" class="closeA clsCPW"><img src="images/icon1/gb.png"/></a>
			<div class="maskTitle">修改密码</div>
			<form   action="javascript:void(0);" method="post">
				
				<div class="iptDiv">
					<div class="iptLeft">
						原密码：
					</div>
					<div class="iptRight">
						<input type="password" placeholder="请输入原密码" name="oldPW"  value=""/>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="iptDiv">
					<div class="iptLeft">
						新密码：
					</div>
					<div class="iptRight">
						<input type="password" placeholder="请输入新密码" name="newPW" value="" />
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="iptDiv">
					<div class="iptLeft">
						确认密码：
					</div>
					<div class="iptRight">
						<input type="password" placeholder="请再次输入新密码" name="curNewPW"  value=""/>
					</div>
					<div class="clearfix"></div>
				</div>
				<div style="height: 80px;position:relative;top:15px;display: flex;align-items: center;">
					<div style="margin:0 auto;display: flex;align-items: center;">
						<input type="submit" id="changePsw" value="确认" />
						<buttom class="closeMaskBtn clsCPW">取消</buttom>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<div id="jbxx" class="jbxx_div">
	<div id="chang_div">
		<div class="maskContent1" style="height: 560px;">
			<a class="closeA clsCPW chang_click"><img src="images/icon1/gb.png"/></a>
			<div class="maskTitle">个人信息</div>
			<form  action="javascript:void(0);" method="post">
				<input type="hidden" name="module" value="AdminLogin" />
				<input type="hidden" name="action" value="maskContent" /> 
				<div class="mezl_div">
					<div class="mezl_img">
						<img style="border-radius: 50%;" src="images/iIcon/tx.png" alt="" />
						<div class="mezl_name">{$r[0]->name}</div>
						<div class="mezl_zhz">{$r[0]->role1}</div>
					</div>
				</div>
				
				<!--input框-->
				<div class="iptDiv">
					<div class="iptLeft">
						昵称：
					</div>
					<div class="iptRight">
						<input type="text" placeholder="请填写您的昵称" name="name" id="nameId" value="{$r[0]->nickname}" />
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="iptDiv">
					<div class="iptLeft">
						生日：
					</div>
					<div class="iptRight mezl_si">
						<div class="mezl_sie">
							<div><select onchange="setDays()" id="select1" name="year"></select></div>
							<div><select onchange="setDays()" id="select2" name="mouth"></select></div>
							<div><select id="select3" name="day"></select></div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="iptDiv">
					<div class="iptLeft">
						性别：
					</div>
					<div class="iptRight mezl_radio ">
						{if $r[0]->sex == 1}
						<input class="magic-radio" type="radio" name="sex" id="r1" value="1" checked>  
                		<label for="r1" class="radio1">男</label> 
                		 <input class="magic-radio" type="radio" name="sex" id="r2" value="2" >  
		                <label for="r2" class="radio2">女</label>  
                		{else}
                		<input class="magic-radio" type="radio" name="sex" id="r1" value="1" >  
                		<label for="r1" class="radio1">男</label> 
                		 <input class="magic-radio" type="radio" name="sex" id="r2" value="2" checked>  
		                <label for="r2" class="radio2">女</label>  
                		{/if}
						 
		               
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="iptDiv">
					<div class="iptLeft">
						手机号码：
					</div>
					<div class="iptRight">
						<input type="number" placeholder="请填写您的手机号" name="tel" id="telId" value="{$r[0]->tel}" />
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div style="height: 80px;position:relative;top:5px;display: flex;align-items: center;">
					<div style="margin:0 auto;display: flex;align-items: center;">
						<input type="submit" value="确认" id="changeInf" class="submit"/>
						<buttom class="closeMaskBtn clsCPW coljks">取消</buttom>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="footers">
	<div>
			<span>联系地址：长沙市岳麓区绿地中央广场5栋3408</span>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span><a style="color: red" href="http://www.laiketui.com/question/" target="_blank">BUG提交</a></span>
	</div>

	<div>
			<span>
				<a class="ba" href="http://www.laiketui.com">Copyright&nbsp;©&nbsp;2020&nbsp;壹拾捌号网络版权所有[官方网站]&nbsp;&nbsp;</a>
			</span>
	</div>
</div>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/js/H-ui.js"></script>
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>
{literal}
	<script type="text/javascript">
		window.onload=function(){
			var selects = document.getElementsByTagName("select");//通过标签名获取select对象
			var date = new Date();
			var nowYear = date.getFullYear();//获取当前的年
			for(var i=nowYear-100;i<=nowYear;i++){
				var optionYear = document.createElement("option");
				optionYear.innerHTML=i;
				optionYear.value=i;
				selects[0].appendChild(optionYear);
			}
			for(var i=1;i<=12;i++){
				var optionMonth = document.createElement("option");
				optionMonth.innerHTML=i;
				optionMonth.value=i;
				selects[1].appendChild(optionMonth);
			}
			getDays(selects[1].value,selects[0].value,selects);
		}
		var data1;
		$.ajax({
			type:"post",
			url:"index.php?module=AdminLogin&action=maskContent",
			async:true,
			dataType:"json",
			success:function(data){
				data1=data.re[0];
				console.log(data1)
				
			}
		});
		$("#changePsw").click(function(){
			var oldPW=$("[name=oldPW]").val();
			var newPW=$("[name=newPW]").val();
			var curPW=$("[name=curNewPW]").val();
			console.log(newPW.length,curPW);
			if(newPW==curPW && newPW.length>5){
				$.ajax({
					type:"post",
					url:"index.php?module=AdminLogin&action=changePassword",
					async:true,
					dataType:"json",
					data:{
						oldPW:oldPW,
						newPW:newPW,
						curPW:curPW,
					},
					success:function(res){
						console.log(res)
						if(res.status==3){
							appendMask(res.info,"cg");
							$("#changePassword").hide();
							location.replace(location.href);
						}
						else {
							appendMask(res.info,"ts");
						}
					}
				});
			}else if(oldPW&&newPW&&curPW&&newPW!=curPW){
				appendMask("密码输入不一致！","ts");
			}else{
				appendMask("请输入完整信息！","ts");
			}	
		})
		$("#changeInf").click(function(){
			var sex=$("[name=sex]:checked").val();
			var birthday=$("#select1").val()+"-"+$("#select2").val()+"-"+$("#select3").val()
			var nickname=$("[name=name]").val();
			var tel=$("[name=tel]").val();
			if(sex.length>0 && birthday.length>0 && tel.length>0 && nickname.length>0){
				$.ajax({
					type:"post",
					url:"index.php?module=AdminLogin&action=maskContent",
					async:true,
					dataType:"json",
					data:{
						nickname:nickname,
						tel:tel,
						birthday:birthday,
						sex:sex,
					},
					success:function(res){
						if(res.status==3){
							appendMask(res.info,"cg");
							$("#jbxx").hide();
							location.replace(location.href);
						}
						else{
							appendMask(res.info,"ts");
						}
					}
				});
			}
			else{
				appendMask("请输入完整信息！","ts");
			}
			
		})
		// 获取某年某月存在多少天
		function getDaysInMonth(month,year){
			var days;
			if (month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) {
				days=31;
			}else if (month==4 || month==6 || month==9 || month==11){
				days=30;
			}else{
				if ((year%4 == 0 && year%100 != 0) || (year%400 == 0)) {     // 判断是否为润二月
					days=29; 
				}else { 
					days=28; 
				}
			}
			return days;
		}
		function setDays(){
			var selects = document.getElementsByTagName("select");
			var year = selects[0].options[selects[0].selectedIndex].value;
			var month = selects[1].options[selects[1].selectedIndex].value;
			getDays(month,year,selects);
		}
		function getDays(month,year,selects){
			var days = getDaysInMonth(month,year);
			selects[2].options.length = 0;
			for(var i=1;i<=days;i++){
				var optionDay = document.createElement("option");
				optionDay.innerHTML=i;
				optionDay.value=i;
				selects[2].appendChild(optionDay);
			}
		}
		
        /*菜单导航*/
        $(".sp1 li").each(function(){
            $(this).mouseover(function(){
                $(".dropDown_A").css("background","none")
            })
        })
        $(".none").each(function(){
            $(this).click(function(){
                $(".mask1").show();
            })
        })
        $(".closeMask").click(function(){
            $(".mask1").hide();
        })
        
        $(".clsCPW").on("click",function(){
            $("#changePassword").hide();
            
        })
        $(".sp2").click(function(){
            $(this).siblings().find(".t2").css("color","#333");
            $(this).find(".t2").css("color","#148cf1");
            $(this).find(".asideImgWrap img").eq(0).hide();
            $(this).find(".asideImgWrap img").eq(1).show();
            $(".bk_2 div").each(function(){
                $(this).removeClass("sp5");
                $(".sp2").addClass("sp5");
            })

            $(this).siblings().find(".asideImgWrap img").eq(1).hide();
            $(this).siblings().find(".asideImgWrap img").eq(0).show();
            $(this).siblings().find(".asideImgWrapRight img:odd").hide();
            $(this).siblings().find(".asideImgWrapRight img:even").show();
        })
        $(".textApi a").each(function(){
            $(this).click(function(){
                $(this).parent().parent().parent().parent().siblings().removeClass("sp5");
                $(this).parent().parent().parent().parent().addClass("sp5");
//		$(this).parent().parent().siblings().find(".asideImgWrap");
                $(this).parent().parent().parent().parent().siblings().each(function(){
                    $(this).find(".t2").css("color","#333");
                    $(this).find(".asideImgWrap img").eq(1).hide();
                    $(this).find(".asideImgWrap img").eq(0).show();
                });
                $(this).parent().parent().parent().siblings().find(".asideImgWrap img").eq(0).hide();
                $(this).parent().parent().parent().siblings().find(".t2").css("color","#0080ff");
                $(this).parent().parent().parent().siblings().find(".asideImgWrap img").eq(1).show();
                $(".topTitle").text($(this).text())
                $(".sp2").find(".asideImg").eq(1).hide();
                $(".sp2").find(".asideImg").eq(0).show();
                $(".sp2").find(".t2").css("color","#333");

            })
        });

        function tab_titleList(obj){
            var bStop = false,
                bStopIndex = 0,
                href = $(obj).attr('data-href'),
                title = $(obj).attr("data-title"),
                topWindow = $(window.parent.document),
                show_navLi = topWindow.find("#min_title_list li"),
                iframe_box = topWindow.find("#iframe_box");
            if(!href||href==""){
                alert("data-href不存在，v2.5版本之前用_href属性，升级后请改为data-href属性");
                return false;
            }if(!title){
                alert("v2.5版本之后使用data-title属性");
                return false;
            }
            if(title==""){
                alert("data-title属性不能为空");
                return false;
            }
            show_navLi.each(function() {
                if($(this).find('span').attr("data-href")==href){
                    bStop=true;
                    bStopIndex=show_navLi.index($(this));
                    return false;
                }
            });
            if(!bStop){
                creatIframe(href,title);
                min_titleList();
            }else{
                show_navLi.removeClass("active").eq(bStopIndex).addClass("active");
                iframe_box.find(".show_iframe").hide().eq(bStopIndex).show().find("iframe").attr("src",href);
            }
        }

        $(function(){
            MessagePlugin.init({
                elem: "#message",
                msgData: [
                    {text: "暂无信息", id: 1, readStatus: 1},
                ],
                msgUnReadData: 0,
                noticeUnReadData: 0,
                msgClick: function(obj) {
                    alert($(obj).text());
                },
                noticeClick: function(obj) {
                    alert("提醒点击事件");
                },
                allRead: function(obj) {
                    alert("全部已读");
                },
                getNodeHtml: function(obj, node) {
                    if (obj.readStatus == 1) {
                        node.isRead = true;
                    } else {
                        node.isRead = false;
                    }
                    var html = "<p>"+ obj.text +"</p>";
                    node.html = html;
                    return node;
                }
            });
            
            $(".menu-system").each(function(){
                $(this).mouseover(function(){
                    $(this).removeClass("active");

                })
                $(this).mouseover(function(){
                	if($(".changeAside").hasClass("changed")){
                        $(".menu-system dt").addClass("selected");
                        $(this).css({
                            paddingRight:"190px",
                            background:"#f6f7f8",
                        })
                        $(this).find("dd").css({
                            position:"absolute",
                            display:"block",
                            left:"50px",
                            width:"140px",
                            background:"#f6f7f8",
                        })
                        $(this).find("dt").css({
                            background:"#f6f7f8",
                        })
                        $(this).find(".t2").css({
                            position:"absolute",
                            display:"block",
                            width: "100px",
                            left: "70px",
                            background:"#f6f7f8",
                        })
                        $(".Hui-aside .menu_dropdown dd li a").css({
                            paddingLeft:"25px",
                        })
                        $(".Hui-aside .menu_dropdown dd ul").css("padding",0)
                    }

                })
                $(this).mouseout(function(){
                    if($(".changeAside").hasClass("changed")){
                        $(".menu-system dt").removeClass("selected");


                        $(this).find("dd").css({
                            position:"static",
                            display:"none",
                            left:"50px",
                            width:"200px",
                            background:"#fff",
                        })
                        $(this).css({
                            paddingRight:"0px",
                            background:"#fff",
                        })
                        $(this).find("dt").css({
                            background:"#fff",
                        })
                        $(this).find(".t2").css({
                            position:"static",
                            display:"none",
                            width: "130px",
                            left: "40px",
                            background:"#fff",
                        })
                        $(".Hui-aside .menu_dropdown dd li a").css({
                            paddingLeft:"52px",
                        })
                        $(".Hui-aside .menu_dropdown dd ul").css("padding","3px,8px")
                    }

                })
            })
            $(".sp2 .asideImgWrap img").eq(1).show();
            $(".sp2 .asideImgWrap img").eq(0).hide();
            $(".changeColor li a").each(function(){
                $(this).mouseover(function(){
                    $(this).css("backgroundColor",$(this).attr("data-color"));
                    $(this).css("color","#fff");
                })
                $(this).mouseout(function(){
                    $(this).css("backgroundColor","#fff");
                    $(this).css("color","#000");
                })
            })
            $(".sysBtn li a").each(function(){
                $(this).mouseover(function(){
                    $(this).find("img").eq(0).hide();
                    $(this).find("img").eq(1).show();
                })
                $(this).mouseout(function(){
                    $(this).find("img").eq(1).hide();
                    $(this).find("img").eq(0).show();
                })
            })
            $(".sysBtn li a").eq(0).click(function(){
                $("#changePassword").show();
                $("[name=oldPW]").val("");
				$("[name=newPW]").val("");
				$("[name=curNewPW]").val("");
            })
            $(".chang_click").click(function(){
                $(".jbxx_div").hide()
            })
            
            $(".coljks").click(function(){
                $(".jbxx_div").hide()
            })
            
            $(".sysBtn li a").eq(1).click(function(){
                $("#jbxx").show();
                if(data1){
                	if($("[name=name]").val().length=!0){
						$("[name=name]").val(data1.nickname)
					}
					if($("[name=tel]").val().length=!0){
						$("[name=tel]").val(data1.tel)
					}
					$("[name=sex]").eq(data1.sex).attr("selected");
					var birthday1=data1.birthday.split("-");
					console.log(birthday1)
                }
				$.ajax({
					type:"get",
					url:"index.php?module=AdminLogin&action=maskContent",
					dataType:"json",
					success:function(res){
						var _data = res.re[0];
						if(res&&res.re&&res.re[0]){
							console.log(_data.nickname)
							$('#nameId').attr('value', _data.nickname)
							$('#telId').attr('value', _data.tel)
							$('#r'+_data.sex).attr('checked', 'checked')
							var _date = _data.birthday.split("-")
							console.log(_date);
							$("#select1").val(_date[0]);
							$("#select2").val(_date[1]);
							$("#select3").val(_date[2]);
						}
					}
				});
            })
            $(".closeA").on("mouseover",function(){
            	$(this).find("img").attr("src","images/icon1/gb_h.png");
            })
            $(".closeA").on("mouseout",function(){
            	$(this).find("img").attr("src","images/icon1/gb.png");
            })
        });
        function appendMask(content,src){
						$("body").append(`
								<div class="maskNew">
									<div class="maskNewContent">
										<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
										<div class="maskTitle">基本信息</div>	
										<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
										<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
											${content}
										</div>
										<div style="text-align:center;margin-top:30px">
											<button class="closeMask" onclick=closeMask1() >确认</button>
										</div>
										
									</div>
								</div>	
							`)
					}
        function closeMask1(){
					$(".maskNew").remove();
				}

				function appendMask2(content){

					$("body").append(`
						<div class="maskNew">
							<div class="maskNewContent">
								<a href="javascript:void(0);" class="closeA" onclick=closeMask12() ><img src="images/icon1/gb.png"/></a>
								<div class="maskTitle">提示</div>	
								<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
								<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
									${content}
								</div>
								<div style="text-align:center;margin-top:30px">
									<button class="closeMask" onclick=closeMask12() >确认</button>
									<button class="closeMask" onclick=closeMask10() >取消</button>
								</div>
								
							</div>
						</div>	
					`)
				}
				
				function closeMask12(){
					$(".maskNew").remove();
					$('iframe')[1].contentWindow.deletes()
				}
				
				function closeMask10() {
					$(".maskNew").remove();
				}


	</script>
{/literal}
</body>
</html>