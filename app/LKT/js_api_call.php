<?php
/**
 * JS_API支付demo
 * ====================================================
 * 在微信浏览器里面打开H5网页中执行JS调起支付。接口输入输出数据格式为JSON。
 * 成功调起支付需要三个步骤：
 * 步骤1：网页授权获取用户openid
 * 步骤2：使用统一支付接口，获取prepay_id
 * 步骤3：使用jsapi调起支付
*/
session_name("money_mojavi");
session_start();
date_default_timezone_set('Asia/Chongqing');
set_time_limit(0);
require_once(dirname(__FILE__)."/webapp/config.php");
require_once(MO_APP_DIR."/mojavi.php");
require_once(dirname(__FILE__).'/webapp/config/db_config.php');
require_once(dirname(__FILE__).'/webapp/lib/DBAction.class.php');
require_once(dirname(__FILE__).'/webapp/lib/WxPayPubHelper/WxPayPubHelper.php');

	
	//使用jsapi接口
	$jsApi = new JsApi_pub();

	//=========步骤1：网页授权获取用户openid============
	//通过code获得openid
	if (!isset($_GET['code']))
	{
		$_SESSION['dingdan'] =  $_GET['dingdan'];
		$_SESSION['cmoney'] =  $_GET['cmoney'];
		//触发微信返回code码
		$url = $jsApi->createOauthUrlForCode(WxPayConf_pub::JS_API_CALL_URL);
		Header("Location: $url"); 
	}else
	{
		//获取code码，以获取openid
		$code = $_GET['code'];
		$jsApi->setCode($code);
		$openid = $jsApi->getOpenId();
	}
	
	//=========步骤2：使用统一支付接口，获取prepay_id============
	//使用统一支付接口
	$unifiedOrder = new UnifiedOrder_pub();
	
	//设置统一支付接口参数
	//设置必填参数
	//appid已填,商户无需重复填写
	//mch_id已填,商户无需重复填写
	//noncestr已填,商户无需重复填写
	//spbill_create_ip已填,商户无需重复填写
	//sign已填,商户无需重复填写
	$unifiedOrder->setParameter("openid","$openid");
	$unifiedOrder->setParameter("body","微信支付");//商品描述
	
	//自定义订单号，此处仅作举例
	$out_trade_no = $_SESSION['dingdan'];
	$unifiedOrder->setParameter("out_trade_no",$out_trade_no);//商户订单号 
	$cmoney = $_SESSION['cmoney'];
	$unifiedOrder->setParameter("total_fee",$cmoney*100);//总金额
	$unifiedOrder->setParameter("notify_url",WxPayConf_pub::NOTIFY_URL);//通知地址 
	$unifiedOrder->setParameter("trade_type","JSAPI");//交易类型
	
	//非必填参数，商户可根据实际情况选填
	//$unifiedOrder->setParameter("sub_mch_id","XXXX");//子商户号  
	//$unifiedOrder->setParameter("device_info","XXXX");//设备号 
	//$unifiedOrder->setParameter("attach","XXXX");//附加数据 
	//$unifiedOrder->setParameter("time_start","XXXX");//交易起始时间
	//$unifiedOrder->setParameter("time_expire","XXXX");//交易结束时间 
	//$unifiedOrder->setParameter("goods_tag","XXXX");//商品标记 
	//$unifiedOrder->setParameter("openid","XXXX");//用户标识
	//$unifiedOrder->setParameter("product_id","XXXX");//商品ID

	$prepay_id = $unifiedOrder->getPrepayId();
	//=========步骤3：使用jsapi调起支付============
	$jsApi->setPrepayId($prepay_id);

	$jsApiParameters = $jsApi->getParameters();

	
?>

<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <title>微信安全支付</title>
	<link rel="stylesheet" href="style/css/frm.css" >
	<script type="text/javascript" src="style/js/jquery.min.js"></script>
	<script type="text/javascript">

		//调用微信JS api 支付
		function jsApiCall()
		{
			WeixinJSBridge.invoke(
				'getBrandWCPayRequest',
				<?php echo $jsApiParameters; ?>,
				function(res){
					WeixinJSBridge.log(res.err_msg);
					if(res.err_msg == "get_brand_wcpay_request:ok" ) {
						alert('支付完成!');location.href='index.php?module=api&action=cz&o=<?php echo $out_trade_no; ?>&wxpay=1';
						//$("#qxzsx").html("支付完成!");
						//$("#wxpay").val(1);
						//$("#buy_lay_frm2").show();
					} 

					if(res.err_msg == "get_brand_wcpay_request:cancel" ) {
						alert('支付取消!');location.href='index.php?module=api&action=cz&o=<?php echo $out_trade_no; ?>&wxpay=2';
						//$("#qxzsx").html("支付取消!");
						//$("#wxpay").val(2);
						//$("#buy_lay_frm2").show();
					}

					if(res.err_msg == "get_brand_wcpay_request:fail" ) {
						alert('支付失败!');location.href='index.php?module=api&action=cz&o=<?php echo $out_trade_no; ?>&wxpay=3';
						//$("#qxzsx").html("支付失败!");
						//$("#wxpay").val(3);
						//$("#buy_lay_frm2").show();
					}
				}
			);
		}

		function callpay()
		{
			if (typeof WeixinJSBridge == "undefined"){
			    if( document.addEventListener ){
			        document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
			    }else if (document.attachEvent){
			        document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
			        document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
			    }
			}else{
			    jsApiCall();
			}
		}

		callpay();
		
	</script>
</head>

<script type="text/javascript">
/*遮罩层*/
	$(function(){	
		$("#ysc").click(function(){
			//location.href='index.php?module=HandleOrder&action=view&sNo=<?php echo $out_trade_no; ?>';
			wxpay = $("#wxpay").val();
			location.href='index.php?module=api&action=cz&o=<?php echo $out_trade_no; ?>&wxpay='+wxpay;
		});
	})
</script>
<body>

<div id="buy_lay_frm2">
	<div class="frm" style="width:750px;height:300px;">
		<div class="tips" id="qxzsx" style="height:220px;font-size:54px;line-height:220px;font-weight:bold;">支付失败！</div>
		<div class="btns" style="height:80px;">
			<input type="hidden" id="wxpay" value="" />
			<input id="ysc" style="width:100%;padding-top: 0px;font-size:34px;color:#fff;line-height:80px;height:80px;" class="btn" type="button" value=" 确定 " />
		</div>
	</div>
</div>	
	
</body>
</html>