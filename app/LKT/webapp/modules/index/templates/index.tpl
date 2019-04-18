<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link href="style/css/dashboard.css" rel="stylesheet" />
    <link rel="stylesheet" href="style/assets/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="style/assets/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="style/assets/vendor/linearicons/style.css">
    <link rel="stylesheet" href="style/assets/vendor/chartist/css/chartist-custom.css">
    <link rel="stylesheet" href="style/assets/css/main.css">
    <link rel="stylesheet" href="style/assets/css/demo.css">
    <link href="style/css/style.css" rel="stylesheet" />
    <link href="style/css/font-awesome.css" rel="stylesheet" />
    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
    <link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png">
    <script src="style/assets/vendor/jquery/jquery.min.js"></script>
    <script src="style/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="style/assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="style/assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
    <script src="style/assets/vendor/chartist/js/chartist.min.js"></script>
    <script src="style/assets/scripts/klorofil-common.js"></script>
    <script src="style/js/echarts.js" type="text/javascript" charset="utf-8"></script>
    <title>我的桌面</title>
    {literal}
        <style type="text/css">
            .leftIcon{
                float: left;
                width: 35%;
                height: 100%;
                text-align: center;
                line-height: 100px;
                border-bottom-left-radius: 5px;
                border-top-left-radius: 5px;
                -moz-border-radius-topleft: 5px;
                -moz-border-radius-bottomleft: 5px;
            }
            .row_a .col-md-2{
                padding: 0 5px;
            }
            .rightNum{
                float: left;
                width: 65%;
                height: 100%;
                display: flex;
                align-items: center;
                background: #fff;
                border-bottom-right-radius: 5px;
                border-top-right-radius: 5px;
                -moz-border-radius-topright: 5px;
                -moz-border-radius-bottomright: 5px;
            }
            .rightNum div{
                width: 100%;
            }
            .rightNum div span {
                display: block;
                width: 100%;
                text-align: center;
            }
            .metric{
                height: 100px;
                padding: 0px;
                border: none;
                margin-bottom: 10px;
            }
            .number,.title{
            	font-weight: 600!important;
            }
            .percentage{
            	display: none;
            }
            .main-content{
                padding: 0 5px;
            }
            .mainLeft{
                height: 100%;
                float: left;
                width: 40%;
                padding: 0 5px;
            }
            .mainRight{
                height: 100%;
                float: left;
                width: 60%;
                padding: 0 5px;
            }
            .realDiv{
                width: 100%;
                background: #fff;
                border-radius: 5px;
                height: 240px;
                margin-bottom: 10px;
            }
            .tjtDiv{
                width: 100%;
                background: #fff;
                border-radius: 5px;
                margin-bottom: 10px;
            }
            .panel-body{
                padding-left: 0px!important;
                padding-right: 0px!important;
            }
            .realDivTitle{
                padding: 0px 20px;
                font-size: 16px;
                font-weight: bold;
                font-family: "微软雅黑";
                color: #414658;
                height: 60px;
                line-height: 60px;
            }
            .realDivMainNum{
                height: 100px;
                padding: 30px;
                text-align: center;
                vertical-align: bottom;
                border-bottom: 1px solid #eee;
            }
            .realDiv3Num{
                height: 60px;
                line-height: 60px;
                margin: 10px 0px;
            }
            .realNum3{
                height: 100%;
                width: 33.33%;
                box-sizing: border-box;
                border-right: 1px solid #eee;
                float: left;
                text-align: center;
                display: flex;
                align-items: center;
            }
            .realNum3 p{
                height: 20px;
                line-height: 20px;
                margin-bottom: 5px;
            }
            .number{
                font-size: 32px;
                color: #0880ff;
            }
            .upText{
                color: #23bf08;
            }
            .downText{
                color: #dc3545;
            }
            .num{
                font-size: 22px;
                color: #414658;
            }
            .numText{
                color: #888f9e;
                font-size: 14px;
            }
            .member{
                width: 67%;
                height: 100%;
                border-radius: 5px;
                float: left;
                margin-right: 0.5%;
                background: #fff;
            }
            .news{
                width: 32%;
                height: 49%;
                border-radius: 5px;
                float: left;
                margin-left: 0.5%;
                background: #fff;
            }
            .title1{
                position: relative;
                top: -30px;
            }
            .title1 a{
                color: #6a7076;
                font-size: 14px;
                font-family: "微软雅黑";
                display: inline-block;
                margin-right: 10px;
            }
            .activeTJTBtn{
                color: #0880ff!important;
            }
            .tjtDivIn{
                padding: 10px;
            }
            .news ul {
                list-style: none;
                padding: 10px;
            }
            .news ul li{
                padding: 10px;
                border-bottom: 1px solid #eee;
                height: 70px;
                position: relative;
            }
            .news ul li a{
                display: inline-block;
                height: 100%;
                width: 100%;
                position: relative;
            }
            .news ul li span{
                color: #414658;
                font-size: 14px;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical
            }
            .newsDate{
                position: absolute;
                right: 0px;
                bottom:-5px;
                font-size: 12px;
                color: #888f9e;
            }
            .realNum3 div{
            	width: 100%;
            }
            .numText{
            	margin-bottom: 0px!important;
            }
        </style>
    {/literal}
</head>
<body>
<!-- 显示更新 -->
<div style="position: fixed; left: 35%; top: 222px; width: 400px; z-index: 309; display: none;" class="dialog-simple check-version-dialog artDialog aui-state-focus">
    <div class="aui-outer animated dialogShow">
        <div class="aui-mask"></div>
        <table class="aui-border">
            <tbody>
            <tr>
                <td class="aui-nw"></td>
                <td class="aui-n"></td>
                <td class="aui-ne"></td>
            </tr>
            <tr>
                <td class="aui-w"></td>
                <td class="aui-c">
                    <div class="aui-inner">
                        <table class="aui-dialog">
                            <tbody>
                            <tr>
                                <td colspan="2" class="aui-header">
                                    <div class="aui-title-bar dialog-menu" id="check-version-dialog">
                                        <div class="aui-title" style="cursor: move;"><img src="./static/images/file_icon/icon_others/info.png" draggable="false" ondragstart="return false;">更新提示</div>
                                        <a class="aui-min"></a>
                                        <a class="aui-max"></a>
                                        <a class="aui-close"></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="aui-icon" style="display: none;">
                                    <div class="aui-icon-bg" style="background: none;"></div>
                                </td>
                                <td class="aui-main" style="padding: 0px; width: 330px; height: auto;">
                                    <div class="aui-content">
                                        <div class="update-box">
                                            <div class="title">
                                                <div class="button-radius">
                                                    <div class="progress hidden"> <span class="total-size"></span> <span class="download-speed"></span>
                                                        <div class="progress-bar"></div>
                                                    </div>
                                                    <a href="javascript:;" class="update-start"><span>立即更新</span><i class="icon-arrow-right"></i></a>
                                                </div>
                                                <a href="javascript:;" class="ver_tips update-self-dialog" target="_blank"><span>自动更新</span></a>
                                                <a href="javascript:;"  class="ver_tips ignore">暂时忽略</a>
                                                <div class="version">当前版本：<span class="lao_v"></span> | 最新版本：<span class="new_v"></span> <span class="badge" style="background:#f60;">new</span></div>
                                                <div style="clear:both"></div>
                                            </div>
                                            <div class="version-info"> <i>ver <span class="new_v"></span> 更新说明：</i>
                                                <div class="version-info-content">
                                                    <p class="content_version">

                                                    </p>
                                                    <a class="more" href="index.php" target="_blank" style="position: relative;">查看更多</a>
                                                    <div style="clear:both"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="aui-footer">
                                    <div class="aui-buttons" style="display: none;"></div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
                <td class="aui-e"></td>
            </tr>
            <tr>
                <td class="aui-sw"></td>
                <td class="aui-s"></td>
                <td class="aui-se"></td>
            </tr>
            </tbody>
        </table>
        <div class="resize-handle resize-top" resize="top"></div>
        <div class="resize-handle resize-right" resize="right"></div>
        <div class="resize-handle resize-bottom" resize="bottom"></div>
        <div class="resize-handle resize-left" resize="left"></div>
        <div class="resize-handle resize-top-right" resize="top-right"></div>
        <div class="resize-handle resize-bottom-right" resize="bottom-right"></div>
        <div class="resize-handle resize-bottom-left" resize="bottom-left"></div>
        <div class="resize-handle resize-top-left" resize="top-left"></div>
    </div>
</div>
<input type="hidden" name="version" id="version" value="{$version}">
<!-- 显示更新 -->
<div class="main" style="background: #edf1f5;">
    <!-- MAIN CONTENT -->
    <div class="main-content">
        <div class="container-fluid">
            <!-- OVERVIEW -->
            <div class="panel panel-headline"  style="background-color: transparent;box-shadow: none;margin-bottom: 0px;">
                <div class="panel-body">
                    <input type="hidden" name="day1" value="{$today}">
                    <input type="hidden" name="day2" value="{$yesterday}">
                    <input type="hidden" name="day3" value="{$qiantian}">
                    <input type="hidden" name="day4" value="{$sitian}">
                    <input type="hidden" name="day5" value="{$wutian}">
                    <input type="hidden" name="day6" value="{$liutian}">
                    <input type="hidden" name="day7" value="{$qitian}">

                    <input type="hidden" name="couhuiyuan01" value="{$couhuiyuan01}">
                    <input type="hidden" name="couhuiyuan02" value="{$couhuiyuan02}">
                    <input type="hidden" name="couhuiyuan03" value="{$couhuiyuan03}">
                    <input type="hidden" name="couhuiyuan04" value="{$couhuiyuan04}">
                    <input type="hidden" name="couhuiyuan05" value="{$couhuiyuan05}">
                    <input type="hidden" name="couhuiyuan06" value="{$couhuiyuan06}">
                    <input type="hidden" name="couhuiyuan07" value="{$couhuiyuan07}">
                    <input type="hidden" name="couhuiyuan" value="{$couhuiyuan}">
                    <div class="row">
                        <a class="row_a" href="index.php?module=orderslist&status=0&sNo=">
                            <div class="col-md-2" >
                                <div class="metric">

                                    <div class="leftIcon" style="background: #68c8c7;"><img src="../LKT/images/fukuan.png"/></div>
                                    <div class="rightNum">
                                        <div>
                                            <span class="number" style="color:#68c8c7 ;">{$dfk}</span>
                                            <span class="title">待付款</span>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </a>
                        <a class="row_a" href="index.php?module=orderslist&status=1&sNo=">
                            <div class="col-md-2">
                                <div class="metric">
                                    <div class="leftIcon" style="background: #ff6c60;"><img src="../LKT/images/fahuo.png"/></div>
                                    <div class="rightNum">
                                        <div>
                                            <span class="number" style="color:#ff6c60 ;">{$dp}</span>
                                            <span class="title">待发货</span>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </a>
                        <a class="row_a" href="index.php?module=orderslist&status=2&sNo=">
                            <div class="col-md-2">
                                <div class="metric">
                                    <div class="leftIcon" style="background: #feb04c;"><img src="../LKT/images/shouhuo.png"/></div>
                                    <div class="rightNum">
                                        <div>
                                            <span class="number" style="color:#feb04c ;">{$yth}</span>
                                            <span class="title">待收货</span>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </a>
                        <a class="row_a" href="index.php?module=orderslist&status=3&sNo=">
                            <div class="col-md-2">
                                <div class="metric">
                                    <div class="leftIcon" style="background: #a6d867;"><img src="../LKT/images/pingjia.png"/></div>
                                    <div class="rightNum">
                                        <div>
                                            <span class="number" style="color:#a6d867 ;">{$pj}</span>
                                            <span class="title">待评价</span>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </a>
                        <a class="row_a" href="index.php?module=orderslist&status=4&sNo=">
                            <div class="col-md-2">
                                <div class="metric">
                                    <div class="leftIcon" style="background: #57c8f2;"><img src="../LKT/images/tuihuo.png"/></div>
                                    <div class="rightNum">
                                        <div>
                                            <span class="number" style="color:#57c8f2 ;">{$th}</span>
                                            <span class="title">退货</span>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="row">
                        <div class="mainLeft">
                            <div class="realDiv">
                                <div class="realDivTitle">实时营业额</div>
                                <div class="realDivMainNum">
                                    <span class="number">{$day_yy} </span>
                                    <span class="percentage">
		                                {if $yingye_day >0}
                                            <i class="fa fa-caret-up text-success"></i>
		                               	{else}
		                                <i class="fa fa-caret-down text-danger"></i>
                                        {/if}
                                        <span class="{if $yingye_day >0}upText{else}downText{/if}">
		                                	{$yingye_day}
		                                </span>
		                            </span>
                                </div>
                                <div class="realDiv3Num">
                                    <div class="realNum3">
                                    	<div>
                                    		<p class="num">{$yes_yy}</p>
                                        	<p class="numText">昨日营业额</p>
                                    	</div>
                                        
                                    </div>
                                    <div class="realNum3">
                                    	<div>
                                        	<p class="num">{$tm01}</p>
                                        	<p class="numText">本月营业额</p>
                                        </div>
                                    </div>
                                    <div class="realNum3" style="border:none">
                                    	<div>
                                        	<p class="num">{$tm02}</p>
                                        	<p class="numText">累计营业额</p>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="realDiv">
                                <div class="realDivTitle">实时订单数</div>
                                <div class="realDivMainNum">
                                    <span class="number">{$daydd}</span>
                                    <span class="percentage">
		                                {if $dingdan_day >0}
                                            <i class="fa fa-caret-up text-success"></i>
		                               	{else}
		                                <i class="fa fa-caret-down text-danger"></i>
                                        {/if}
                                        <span class="{if $dingdan_day >0}upText{else}downText{/if}">
		                                	{$dingdan_day}
		                                </span>
		                            </span>
                                </div>
                                <div class="realDiv3Num">
                                    <div class="realNum3">
                                    	<div>
                                        	<p class="num">{$yesdd}</p>
                                        	<p class="numText">昨日订单</p>
                                        </div>
                                    </div>
                                    <div class="realNum3">
                                    	<div>
                                        	<p class="num">{$tm}</p>
                                        	<p class="numText">本月订单</p>
                                        </div>
                                    </div>
                                    <div class="realNum3" style="border:none">
                                    	<div>
                                        	<p class="num">{$leiji_dd}</p>
                                        	<p class="numText">累计订单</p>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="realDiv">
                                <div class="realDivTitle">实时访客数</div>
                                <div class="realDivMainNum">
                                    <span class="number">{$fangke01}</span>
                                    <span class="percentage">
		                                {if $fangkebizhi >0}
                                            <i class="fa fa-caret-up text-success"></i>
		                               	{else}
		                                <i class="fa fa-caret-down text-danger"></i>
                                        {/if}
                                        <span class="{if $fangkebizhi >0}upText{else}downText{/if}">
		                                	{$fangkebizhi}
		                                </span>
		                            </span>
                                </div>
                                <div class="realDiv3Num">
                                    <div class="realNum3">
                                    	<div>
                                        <p class="num">{$fangke02}</p>
                                        <p class="numText">昨日访客</p>
                                        </div>
                                    </div>
                                    <div class="realNum3">
                                    	<div>
                                        	<p class="num">{$fangke03}</p>
                                        	<p class="numText">本月访客</p>
                                        </div>
                                    </div>
                                    <div class="realNum3" style="border:none">
                                    	<div>
                                        	<p class="num">{$fangke}</p>
                                        	<p class="numText">累计访客</p>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mainRight" >
                            <div class="tjtDiv" style="height: 315px;background: transparent;">
                                <div class="member">
                                    <div class="realDivTitle">会员统计</div>
                                    <div class="title1" style="float: right; margin-right: 20px" >累计会员: {$couhuiyuan}  人</div>
                                    <div class="tjtDivIn" id="memberTJT" style="height:240px;margin: 0 auto;">

                                    </div>
                                </div>
                                <div class="news" style="margin-bottom: 6px">
                                    <div class="realDivTitle" style="border-bottom: 2px solid #eee;">公告</div>
                                    <ul style="height: 120px;overflow: auto;">
                                        {foreach from=$res_notice item = item1 name=f1}
                                            <li>
                                                <a href="index.php?module=notice&action=article&id={$item1->id}">
                                                    <span>{$item1->name}</span>
                                                    <div class="newsDate">{$item1->time}</div>
                                                </a>
                                            </li>
                                        {/foreach}
                                    </ul>
                                </div>
                                <div class="news">
                                    <div class="realDivTitle" style="border-bottom: 2px solid #eee;">授权信息</div>
                                    <ul style="height: 120px;overflow: auto;">
                                        {$authorization}
                                    </ul>
                                </div>

                                <div class="tjtDivIn" class="clearfix"></div>
                            </div>
                            <div class="tjtDiv" style="height: 415px;">
                                <div class="realDivTitle">订单统计</div>
                                <div class="title1" style="float: right; margin-right: 20px" >
                                    <a href="javacript:void(0)" class="activeTJTBtn">本周</a>
                                    <a href="javacript:void(0)">本月</a>
                                    <a href="javacript:void(0)">本年</a>
                                </div>
                                <div class="tjtDivIn" id="ddTJT" style="height:345px;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
{literal}
    <script type="text/javascript">
        $(function() {
            var data, options;

            var day1 = $('input[name=day1]').val(); // 今天
            var day2 = $('input[name=day2]').val(); // 昨天
            var day3 = $('input[name=day3]').val(); // 前天
            var day4 = $('input[name=day4]').val(); // 4天
            var day5 = $('input[name=day5]').val(); // 5天
            var day6 = $('input[name=day6]').val(); // 6天
            var day7 = $('input[name=day7]').val(); // 7天

            var couhuiyuan01 = $('input[name=couhuiyuan01]').val(); // 今天注册人数
            var couhuiyuan02 = $('input[name=couhuiyuan02]').val(); // 昨天注册人数
            var couhuiyuan03 = $('input[name=couhuiyuan03]').val(); // 前天注册人数
            var couhuiyuan04 = $('input[name=couhuiyuan04]').val(); // 4天前注册人数
            var couhuiyuan05 = $('input[name=couhuiyuan05]').val(); // 5天前注册人数
            var couhuiyuan06 = $('input[name=couhuiyuan06]').val(); // 6天前注册人数
            var couhuiyuan07 = $('input[name=couhuiyuan07]').val(); // 7天前注册人数
            var couhuiyuan = $('input[name=couhuiyuan]').val(); // 会员总数

            // visits trend charts(访问趋势图)
//          data = {
//              labels: [day7, day6, day5, day4, day3, day2, day1],
//              series: [{
//                  name: 'series-real',
//                  data: [couhuiyuan07, couhuiyuan06, couhuiyuan05, couhuiyuan04, couhuiyuan03, couhuiyuan02, couhuiyuan01,/*couhuiyuan*/],
//              }]
//          };
//
//          options = {
//              showGridBackground: true,
//              fullWidth: true,
//              lineSmooth: false,
//              height: "220px",
//              width:"100%",
//              low: 0,
//              high: 'auto',
//              series: {
//                  'series-projection': {
//                      showArea: true,
//                      showPoint: false,
//                      showLine: false
//                  },
//              },
//              axisX: {
//                  showGrid: true,
//
//              },
//              axisY: {
//                  showGrid: true,
//                  onlyInteger: true,
//                  offset: 0,
//              },
//              chartPadding: {
//                  left: 20,
//                  right: 40
//              }
//
//          };
//			
			var myChart = echarts.init(document.getElementById('memberTJT'),null,{renderer: 'svg'});
			myChart.setOption({
				xAxis:{data:[day7, day6, day5, day4, day3, day2, day1]},
				yAxis:{
					splitLine: {show:false},
//				 	data:[couhuiyuan07, couhuiyuan06, couhuiyuan05, couhuiyuan04, couhuiyuan03, couhuiyuan02, couhuiyuan01,/*couhuiyuan*/]
						min:0,
				 },
				grid: {
				    top: 10,
				    bottom: 20,
				    left:40,
				    right:20,
				},
				tooltip: {
				    formatter: " {a} <br/> {c} "
				},
				series:{
					name:"会员人数",
				 	data:[
					 		[day7,couhuiyuan07],
					 		[day6,couhuiyuan06],
					 		[day5,couhuiyuan05],
					 		[day4,couhuiyuan04],
					 		[day3,couhuiyuan03],
					 		[day2,couhuiyuan02],
					 		[day1,couhuiyuan01],
					 	],
				 	type:"bar",
				 	
				},
				color:"#0880ff",
				
			});
//          new Chartist.Line('#memberTJT', data, options);
			var myChart1 = echarts.init(document.getElementById('ddTJT'),null,{renderer: 'svg'});
			myChart1.setOption({
				xAxis:{data:[day7, day6, day5, day4, day3, day2, day1]},
				yAxis:{
					splitLine: {show:false},
//				 	data:[couhuiyuan07, couhuiyuan06, couhuiyuan05, couhuiyuan04, couhuiyuan03, couhuiyuan02, couhuiyuan01,/*couhuiyuan*/]
						min:0,
				 },
				grid: {
				    top: 10,
				    bottom: 20,
				    left:40,
				    right:20,
				},
				tooltip: {
				    formatter: " {a} <br/> {c} "
				},
				color:"#0880ff",
				series:{
					name:"会员人数",
				 	data:[
					 		[day7,couhuiyuan07],
					 		[day6,couhuiyuan06],
					 		[day5,couhuiyuan05],
					 		[day4,couhuiyuan04],
					 		[day3,couhuiyuan03],
					 		[day2,couhuiyuan02],
					 		[day1,couhuiyuan01],
					 	],
				 	type:"line",
				 }
			});
//          new Chartist.Bar('#ddTJT', data,{
//              axisX: {
//                  showGrid: false,
//
//              },
//              axisY: {
//                  showGrid: false,
//                  onlyInteger: true,
//                  offset: 0,
//              },
//              chartPadding: {
//                  left: 20,
//                  right: 20
//              },
//          });
//          chack_update();
        });

        //关闭更新
        $(".aui-close").click( function () {
            $(".dialog-simple").hide();
        });
        $(".ignore").click( function () {
            $(".dialog-simple").hide();
        });

        //立即更新
        $(".update-start").click( function () {
            var that = $(this);
            that.find("span").text('下载中,请稍后..');
            $.ajax({
                url: "index.php?module=index&upgrade=1&download=true",
                cache: false,
                success: function(res){
                    if(res){
                        var lkt_web_version = $(".lkt_web_version").val();
                        var r=confirm("下载成功,是否立即更新?");
                        if (r==true){
                            parent.location.href='../index.php?updata='+lkt_web_version;
                        }
                        else{
                            $(".dialog-simple").hide();
                        }

                    }else{
                        alert('下载失败！');
                    }

                }
            });
        });

        function chack_update() {
            var version = $("#version").val();
            $.ajax({
                url: "index.php?module=index&upgrade="+version,
                cache: false,
                success: function(res){
                    obj = JSON.parse(res);
                    var status = obj.status;
                    if(status == 1){
                        var new_v = obj.lkt_web_version;
                        var v_content = obj.detail;
                        $(".lao_v").text(version);
                        $(".new_v").text(new_v);
                        $(".content_version").append(v_content);

                        $(".dialog-simple").show();
                    }else{
                        $(".dialog-simple").hide();
                    }

                }
            });
        }
        console.log(document.getElementById("skin"));
    </script>
{/literal}
</body>
</html>