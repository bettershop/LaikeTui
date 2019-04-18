<!DOCTYPE HTML>
<html>

<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link href="//at.alicdn.com/t/font_353057_iozwthlolt.css" rel="stylesheet">
	<link href="style/statics/mch/css/bootstrap.min.css" rel="stylesheet">
	<link href="style/statics/mch/css/jquery.datetimepicker.min.css" rel="stylesheet">
	<link href="style/statics/css/flex.css?version=2.5.8.0" rel="stylesheet">
	<link href="style/statics/css/common.css?version=2.5.8.0" rel="stylesheet">
	<link href="style/statics/mch/css/common.v2.css?version=2.5.8.0" rel="stylesheet">

	<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
	<link href="style/css/style.css" rel="stylesheet" type="text/css" />
	<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
	{literal}
		<style type="text/css">
			#delorderdiv {
				margin-left: 20px;
				display: inline;
				color: red;
			}
			#btn1:hover{
				background-color: #2481e5!important;
			}
			#btn1{
				height: 36px;
				line-height: 36px;
			}
			#btn2:hover{
				background-color: #2481e5!important;
			}
			#btn2{
				height: 36px;
				line-height: 36px;
			}
		</style>
		<style>
			.order-item {
				border: 1px solid transparent;
				margin-bottom: 1rem;
			}

			.order-item table {
				margin: 0;
			}

			.order-item:hover {
				border: 1px solid #3c8ee5;
			}

			.goods-item {
				margin-bottom: .75rem;
			}

			.goods-item:last-child {
				margin-bottom: 0;
			}



			.goods-name {
				white-space: nowrap;
				text-overflow: ellipsis;
				overflow: hidden;
			}

			.status-item.active {
				color: inherit;
			}

			.badge {
				display: inline-block;
				padding: .25em .4em;
				font-size: 75%;
				font-weight: 700;
				line-height: 1;
				color: #fff;
				text-align: center;
				white-space: nowrap;
				vertical-align: baseline;
				border-radius: .25rem
			}

			.badge:empty {
				display: none
			}

			.btn .badge {
				position: relative;
				top: -1px
			}

			a.badge:focus,
			a.badge:hover {
				color: #fff;
				text-decoration: none;
				cursor: pointer
			}

			.badge-pill {
				padding-right: .6em;
				padding-left: .6em;
				border-radius: 10rem
			}

			.badge-default {
				background-color: #636c72
			}

			.badge-default[href]:focus,
			.badge-default[href]:hover {
				background-color: #4b5257
			}

			.badge-primary {
				background-color: #0275d8
			}

			.badge-primary[href]:focus,
			.badge-primary[href]:hover {
				background-color: #025aa5
			}

			.badge-success {
				background-color: #5cb85c
			}

			.badge-success[href]:focus,
			.badge-success[href]:hover {
				background-color: #449d44
			}

			.badge-info {
				background-color: #5bc0de
			}

			.badge-info[href]:focus,
			.badge-info[href]:hover {
				background-color: #31b0d5
			}

			.badge-warning {
				background-color: #f0ad4e
			}

			.badge-warning[href]:focus,
			.badge-warning[href]:hover {
				background-color: #ec971f
			}

			.badge-danger {
				background-color: #d9534f
			}

			.badge-danger[href]:focus,
			.badge-danger[href]:hover {
				background-color: #c9302c
			}
			.mask{
				position: absolute;
				top: 0;
				bottom: 0;
				left: 0;
				right: 0;

			}
			a:hover {color: red; text-decoration:none;}
			.table-bordered th, .table-bordered td{
				border: none;
				text-align: center;
				vertical-align: middle;
			}
			.txc th{
				text-align: center;
			}
			.imgTd img{
				width: 50px;
				height: 50px;
				margin-right: 10px;
			}
			table {
				vertical-align: middle;

			}
			td a{
				float: left;
				width: 100%!important;

			}
			.maskLeft{
				width: 30%;
				float: left;
				text-align: right;
				padding-right: 20px;
				height:36px;
				line-height: 36px;
			}
			.maskRight{
				width: 70%;
				float: left;
			}
			.ipt1{
				padding-left: 10px;
				width:300px;
				height: 36px;
				border: 1px solid #eee;
			}
			.wl_maskNew {
			    position: fixed;
			    z-index: 9999999;
			    top: 0;
			    bottom: 0;
			    left: 0;
			    right: 0;
			    background: rgba(0,0,0,0.6);
			    display: block;
			}
			.wl_maskNewContent {
			    width: 500px;
			    height: 519px;
			    margin: 0 auto;
			    position: relative;
			    top: 200px;
			    background: #fff;
			    border-radius: 10px;
			}
			.dc {
			    position: fixed;
			    z-index: 999;
			    top: 0;
			    bottom: 0;
			    left: 0;
			    right: 0;
			    background: rgba(0,0,0,0.6);
			    display: block;
			}
		</style>

	{/literal}
	<title>订单列表</title>
</head>

<body>
<nav class="breadcrumb" style="height: 50px;"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 订单列表<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-c">
		<form name="form1" action="index.php" method="get">
			<input type="hidden" name="module" value="orderslist" />
			<input type="hidden" name="having" value="123" />
			<input type="hidden" name="ordtype" value="{$otype}" />
			<input type="hidden" name="gcode" value="{$status}" />
			<input type="hidden" name="ocode" value="{$ostatus}" />
			<select name="otype" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
				{foreach from=$ordtype item="item" key="key"}
					<option value="{$key}" {if $otype==$key}selected{/if}>{$item}</option>
				{/foreach}
			</select>

			<select name="status" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
				<option value="">订单状态</option>
				{$class}
			</select>

			<select name="source" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
				<option value="">平台类型</option>
				{$source}
			</select>

			<select name="brand" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
				<option value="">请选择品牌</option>
				{$brand_str}
			</select>

			<input type="text" name="sNo" size='8' value="{$sNo}" id="" placeholder=" 订单编号或收件人姓名/电话..." style="width:200px" class="input-text">
			<div style="position: relative;display: inline-block;">
				<input name="startdate" value="{$startdate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 25px;top: 7px;" onclick="new Calendar().show(document.form1.startdate);" />
			</div>至
			<div style="position: relative;display: inline-block;margin-left: 5px;">
				<input  name="enddate" value="{$enddate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 10px;top: 7px;" onclick="new Calendar().show(document.form1.enddate);" />
			</div>
			<input class="btn btn-success" id="btn1" type="submit" value="查询">
			<input type="button" value="导出" id="btn2" style="margin-right: 0px;" class="btn btn-success" onclick="excel('all')">

		</form>
	</div>
	<div class="mt-20">
		<table class="table table-bordered bg-white">
			<thead>
			<tr class="txc">

				<th style="width: 170px;">订单号
					<!--<span style="font-size: 12px;">订单数: {$data1.num} 订单金额: {$data1.numprice}元 </span>-->
				</th>
				<th style="width: 70px;">商品图片</th>
				<th style="width:250px!important;" width="250px">商品信息</th>
				<th style="width: 75px;">实际付款</th>
				<th style="width: 75px;">订单状态</th>
				<th style="width: 75px;">支付方式</th>
				<th style="width: 75px;">发货方式</th>
				<th style="width: 75px;">订单类型</th>
				<th style="width: 75px;">用户</th>
				<th>平台</th>
				<th>下单时间</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			{foreach from=$order item=item name=f1}
				<tr>

					<td>
						<span>{$item->sNo}</span>
					</td>
					<td class="imgTd">
						{foreach from=$item->products item=item2 name=f2}
							<div >
								<img class="goods-pic" src="{$uploadImg}{$item2->imgurl}"/>
							</div>
						{/foreach}
					</td>
					<td class="tbText" style="width:250px!important;text-align: left;" width="250px">
						{foreach from=$item->products item=item2 name=f2}
							<span style="text-align: left;">{$item2->product_title}</span>
						{/foreach}
					</td>
					<td>
						<div>&yen;{$item->z_price}</div>
					</td>
					<td>

						<div>
							<span style="">{$item->status}</span>
						</div>
					</td>
					<td>
						<div>
								<span>
									{if $item->pay == 'wxPay'}微信支付
									{elseif $item->pay == 'wallet_Pay'}余额支付
									{elseif $item->pay == 'consumer_pay'}消费金支付
									{else}组合支付{/if}
								</span>
						</div>
					</td>
					<td>
						<div>
							<span >快递发送</span>
						</div>
					</td>
					<td>
						<div>
							<span >{if $item->otype == 'pt'}拼团订单{else}{if $item->drawid>0}抽奖订单{else}普通订单{/if}{/if}</span>
						</div>
					</td>
					<td>
						<div  class="goods-name">
							<span >{$item->user_name}</span>
						</div>
					</td>
					<td>
						<div style="text-align: center;">
							<span >{if $item->source == 1}小程序{else}APP{/if}</span>
						</div>
					</td>
					<td>
						<div style="text-align: center;">
							<span >{$item->add_time}</span>
						</div>
					</td>
					<td  style="text-align: center;width: 80px;">
						{if $item->statu == 1}
							<a class="btn send-btn" href="javascript:void(0);" onclick=send_btn(this,'{$item->otype}','{$item->sNo}',{$item->statu},{$item->drawid})> 发货</a>
						{/if}
						{if $item->statu >= 2}
							<a class="btn send-btn1" href="javascript:void(0);" onclick="send_btn1(this,'{$item->sNo}','{$item->products[0]->courier_num}')" >查看物流</a>
						{/if}
						<a class="btn" style="margin-top: 5px;" href="index.php?module=orderslist&action=Detail&id={$item->id}">查看详情</a>
						<br>
					</td>
				</tr>
			{/foreach}
			</tbody>
		</table>
	</div>
	<div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>

</div>
<div class="dc" style="display:none;">
	<div class="maskNewContent">
		<div class="maskTitle ">
			添加快递信息
		</div>
		<a href="javascript:void(0);" class="closeA qx"><img src="images/icon1/gb.png"/></a>
		<form action="" method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
			<div id="tab-category" class="HuiTab">
				<div class="" style="margin-top: 35px;">
					<div class="" >
						<input type="hidden" name="sNo" value="" class="order_id">
						<input type="hidden" name="otype" value="{$otype}" class="otype">
						<input type="hidden" name="trade" value="3">
						<label class="maskLeft" style="" >快递公司：</label>
						<div class="formControls maskRight" style="width: 60%;float: left;">
							<form name="hh" action="" method="post" >
						    <span class="search">
							    <input class="ww ipt1" id="makeInput" autocomplete="off" onkeyup="setContent(this,event);" onfocus="setDemo(this,event)" type="text" placeholder="请选择或输入快递名称">
							    <select name="kuaidi" class="selectName" id="hh" style="display: none; position: absolute;z-index:99;width: 153px;margin-top: 1px;margin-left: 0px;" onkeyup="getfocus(this,event)" onclick="choose(this)" size="10" id ="num1">
									{foreach from=$express item = item name=f1}
										<option value="{$item->id}" >{$item->kuaidi_name}</option>
									{/foreach}
							    </select>
							</span>
							</form>
						</div>
						<div class="clearfix"></div>
						<div class="col-3">
						</div>
					</div>
					<div class="" >
						<label class="maskLeft" style="">快递单号：</label>
						<div class="maskRight" style="">
							<input type="text" class="ipt1" value="" name="danhao" placeholder="请输入正确的快递单号"/>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<div class="row cl">
				<div class="col-9 " style="margin-left:40%">
					<input class="qd closeMask" type="submit" value="提交">
				</div>
			</div>
		</form>
	</div>
</div>
<div class="maskNew dd" style="display: none;">
	<div class="maskNewContent">
		<div class="maskTitle ">
			查看物流信息
		</div>
		<a href="javascript:void(0);" class="closeA qx"><img src="images/icon1/gb.png"/></a>
		<div class="page-container" style="margin: 0 auto;">
			{if $res ==1 }
				<p style=" text-align: center;height: 100%;">未查询到物流信息</p>
			{else}
				{foreach from=$res item="item" key="key"}
					<div class='time' style="    margin-left: 30px;">{$item->time}</div>
					<div class='step' style="font-size: 0.5rem; padding: 5px 20px;    margin-left: 30px;">{$item->context}</div>
				{/foreach}
			{/if}

		</div>
		<div style="text-align:center;margin-top:30px">
			<button class="closeMask"  onclick=hm()>确认</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type='text/javascript' src='modpub/js/calendar.js'></script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/js/H-ui.js"></script>
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>
<!--_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script language="javascript"  src="style/ssd1.js"> </script>
{literal}
<script type="text/javascript">

    function check(f) {
        if (Trim(f.product_title.value) == "") {
            alert("产品名称不能为空！");
            f.product_title.value = '';
            return false;
        }
        if (Trim(f.keyword.value) == "") {
            alert("关键词不能为空！");
            f.keyword.value = '';
            return false;
        }
        if (Trim(f.sort.value) == "") {
            alert("排序不能为空！");
            f.sort.value = '';
            return false;
        }
        f.sort.value = Trim(f.sort.value);
        if (!/^(([1-9][0-9]*)|0)(\.[0-9]{1,2})?$/.test(f.sort.value)) {
            alert("排序号必须为数字，且格式为 ####.## ！");
            f.sort.value = '';
            return false;
        }
        console.log(1);
        return true;
    }

    $(".qd").click(function(){
        var id = $('.order_id').val(); // 订单号
        var express = $('select[name=kuaidi]').val(); // 快递公司id
        var express_name = $('select[name=kuaidi]').find("option:selected").text(); // 快递公司名称
        var courier_num = $('input[name=danhao]').val(); // 快递单号
        // var freight = $('input[name=yunfei]').val();
        var otype = $(".otype").val(); // 类型
        console.log(courier_num,express_name,express,"as4d65");
        console.log(express_name.length,express.length)
        if(express_name.length < 1){
			appendMask_tj('请选择快递公司',"ts");
        }else if(courier_num.length < 10){
        	appendMask_tj('请填写正确快递单号',"ts");
        }else{
        $.ajax({
            url:"index.php?module=orderslist&action=addsign",
            type:"post",
            data:{sNo:id,trade:3,express:express,courier_num:courier_num,otype:otype,express_name:express_name},
            dataType:"json",
            success:function (data) {
                console.log(data);
                $(".dc").hide();
                if(data == 1){
                    console.log(1)
                    appendMask1('发货成功',"cg");
                }else{
                    appendMask('快递单号已存在，请勿重复',"ts");
                }
            },
        });
        }

    });

    function system_category_add(title,url,w,h){
        appendMask(title,url,w,h);
    }

    $(".qx").click(function(){
        $(".dc").hide();
    })
    function send_btn(obj,otype, id, status,drawid) {
    	console.log(otype, id, status,drawid);
        var dingdan = id;
        var stu = status;
        $('.order_id').val(id);
        $('.otype').val(otype ? otype:'yb');

        if(stu == 6) {
            appenMask1('订单已关闭，不能发货!',"ts");
        }
        if(stu >= 2 && stu != 6) {
            appenMask1('订单已发货，请勿重复操作!',"ts");
        }

        if(stu == 0) {
            appenMask1('订单还没付款!',"ts");
        }

        if(stu == 1) {
            if(drawid == 0){//普通订单
                $(".dc").show();
            }else{
                appenMask1('抽奖订单请进入订单详情后发货！',"ts");
            }
        }

    };

    function send_btn1(obj, id,courier_num) {
        var r_sNo = id;
        $.ajax({
            url:'index.php?module=orderslist&action=kuaidishow&r_sNo='+r_sNo+'&courier_num='+courier_num,
            type:"post",
            data:{},
            success:function (res) {
            	var data = JSON.parse(res);
                console.log(data.code);
                if(data.code == 1){
                	closeMask1();
                    console.log(1);
                    var str = '';
                    for (var i = 0; i < data.data.length; i++) {
                    	str += '<div class="time" style="margin-left: 30px;">'+data.data[i].time+'</div><div class="step" style="font-size: 0.5rem; padding: 5px 20px;    margin-left: 30px;">'+data.data[i].context+'</div>';
                    }
                    wl_appendMask(str,"cg");
                }else{
                    appendMask('暂无物流信息！',"ts");
                }
            },
        });
    };





    function excel(pageto) {
        var pagesize = $("[name='DataTables_Table_0_length'] option:selected").val();
        location.href = location.href + '&pageto=' + pageto + '&pagesize=' + 10;
    }

    var i = 0;
    $('select[name=otype]').change(function() {
        let ss = $(this).children('option:selected').val();
        if(ss == 't2') {
            $('select[name=status]').empty();
            $('select[name=status]').append("<option value=''>拼团状态</option><option value='g0'>未付款</option><option value='g1'>拼团中</option><option value='g2'>拼团成功</option><option value='g3'>拼团失败</option>");
        } else {
            $('select[name=status]').empty();
            $('select[name=status]').append("<option value=''>订单状态</option><option value='0'>未付款</option><option value='1'>未发货</option><option value='2'>已发货</option><option value='3'>待评论</option><option value='4'>退货</option><option value='5'>已签收</option>");
            $('select[name=ostatus]').remove();
        }
    })

    $('select[name=status]').change(function() {
        let ss = $('select[name=otype]').children('option:selected').val();
        let gg = $(this).children('option:selected').val();
        if(gg == 'g2') {
            $('#fail').remove();
            $('select[name=status]').after("<select name='ostatus' class='select' id='success' style='width: 80px;height: 31px;vertical-align: middle;margin-left: 5px;'><option value='g1' selected>未发货</option><option value='g2'>已发货</option><option value='g3'>已签收</option></select>");
        } else if(gg == 'g3') {
            $('#success').remove();
            $('select[name=status]').after("<select name='ostatus' class='select' id='fail' style='width: 80px;height: 31px;vertical-align: middle;margin-left: 5px;'><option value='g10' selected>未退款</option><option value='g11'>已退款</option></select>");
        } else {
            $('select[name=ostatus]').remove();
        }
    })

    var having = $('input[name=having]').val();
    var otype = $('input[name=ordtype]').val();
    var gstatus = $('input[name=gcode]').val();
    var ostatus = $('input[name=ocode]').val();
    if(having == '123') {
        var tv = $('select[name=otype]').children('option:selected').val();
        if(tv == 't2') {
            $('select[name=status]').empty();
            $('select[name=status]').append("<option value=''>拼团状态</option>");
            var options = {
                g0: '未付款',
                g1: '拼团中',
                g2: '拼团成功',
                g3: '拼团失败'
            };
            var str = '';

            $.each(options, function(index, element) {
                str = '<option value="' + index + '"';
                if(gstatus == index) {
                    str += ' selected';
                }
                str += '>' + element + '</option>';
                $('select[name=status]').append(str);
            })


            var gv = $('select[name=status]').children('option:selected').val();
            if(gstatus == 'g2') {
                $('#fail').remove();
                $('select[name=status]').after("<select name='ostatus' class='select' id='success' style='width: 80px;height: 31px;vertical-align: middle;margin-left: 5px;'></select>");
                var options = {
                    g1: '未发货',
                    g2: '已发货',
                    g3: '已签收'
                };
                var str = '';
                $.each(options, function(index, element) {
                    str = '<option value="' + index + '"';
                    if(ostatus == index) {
                        str += ' selected';
                    }
                    str += '>' + element + '</option>';
                    $('select[name=ostatus]').append(str);
                })
            } else if(gstatus == 'g3') {
                $('#success').remove();
                $('select[name=status]').after("<select name='ostatus' class='select' id='fail' style='width: 80px;height: 31px;vertical-align: middle;margin-left: 5px;'><select>");
                var options = {
                    g10: '未退款',
                    g11: '已退款'
                };
                var str = '';
                $.each(options, function(index, element) {
                    str = '<option value="' + index + '"';
                    if(ostatus == index) {
                        str += ' selected';
                    }
                    str += '>' + element + '</option>';
                    $('select[name=ostatus]').append(str);
                })
            } else {
                $('select[name=ostatus]').remove();
            }
        }

    }

    //实现全选与反选
    var ids = [];
    $("#allAndNotAll").click(function() {
        if(this.checked) {
            $("input[name=checkid]").each(function(index) {
                $(this).prop("checked", true);
                var val = $(this).val();
                arrModify(ids, val, 1);
            });
        } else {
            $("input[name=checkid]").each(function(index) {
                $(this).prop("checked", false);
                var val = $(this).val();
                arrModify(ids, val, 2);
            });
        }

    });

    Array.prototype.indexOf = function(val) {
        for(var i = 0; i < this.length; i++) {
            if(this[i] == val) return i;
        }
        return -1;
    }
    Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if(index > -1) {
            this.splice(index, 1);
        }
    }

    function arrModify(arr, val, type) { //１为增加元素  2为删除元素
        if(type == 1) {
            var index = $.inArray(val, arr);
            if(index === -1) arr.push(val);
        } else if(type == 2) {
            arr.remove(val);
        }
        return arr;
    }

    function selectId(i) {
        i = i.toString();
        var index = $.inArray(i, ids); //判断数组中是否存在该值,如存在返回下标值,如不存在，返回-1
        if($('#checkid' + i).prop('checked') == true) {
            if(index === -1) ids.push(i);
        } else {
            ids.remove(i);
        }
    }
    Array.prototype.distinct = function() { //数组去重
        var arr = this,
            result = [],
            i,
            j,
            len = arr.length;
        for(i = 0; i < len; i++) {
            for(j = i + 1; j < len; j++) {
                if(arr[i] === arr[j]) {
                    j = ++i;
                }
            }
            result.push(arr[i]);
        }
        return result;
    }

    $('#getAllSelectedId').click(function() {

        if(ids.length === 0) {
            layer.alert('没有选择订单！', {
                icon: 5,
                title: "提示"
            });
        } else {
            layer.confirm('确定要删除吗？', function(index) {
                ids = ids.distinct();
                var delIds = ids.join(",");
                //console.log(ids);
                $.ajax({
                    url: "index.php?module=orderslist&action=delorder",
                    type: "post",
                    data: {
                        ids: delIds
                    },
                    dataType: "json",
                    success: function(data) {
                        if(data.code == 1) {
                            layer.alert(data.msg, {
                                skin: 'layui-layer-lan',
                                closeBtn: 0,
                                anim: 4 //动画类型
                            }, function() {
                                location.reload();
                            });

                        }
                    },
                });
            });
        }
    });
    function confirm1 (content,id,content1){
        $("body").append(`
				<div class="maskNew">
					<div class="maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle">提示</div>
						<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
						<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
							${content}
						</div>
						<div style="text-align:center;margin-top:30px">
							<button class="closeMask" style="margin-right:20px" onclick=closeMask2("${id}","${content1}") >确认</button>
							<button class="closeMask" onclick=closeMask1() >取消</button>
						</div>
					</div>
				</div>
			`)
    }
    function closeMask2(id,content){
        $(".maskNew").remove();
        $.ajax({
            type:"post",
            url:"index.php?module=coupon&action=whether&id="+id,
            async:true,
            success:function(res){
                console.log(res);
                if(content=="启用"){
                    if(res==1){
                        appendMask("启用成功","cg");
                    }
                    else{
                        appendMask("启用失败","ts");
                    }
                }
                else{
                    if(res==1){
                        appendMask("禁用成功","cg");
                    }
                    else{
                        appendMask("禁用失败","ts");
                    }
                }
            }
        });
    }
    function wl_appendMask(content,src){
        $("body").append(`
				<div class="wl_maskNew">
					<div class="wl_maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=close_wl_Mask1() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle">物流信息</div>
						<div style="height: 370px;position: relative;top:20px;font-size: 22px;text-align: center;overflow: scroll;">
							${content}
						</div>
						<div style="text-align:center;margin-top:30px">
							<button class="closeMask" onclick=close_wl_Mask1() >确认</button>
						</div>
					</div>
				</div>
			`)
    }

    function close_wl_Mask1() {
    	$(".wl_maskNew").remove();
    }

    function appendMask(content,src){
        $("body").append(`
				<div class="maskNew ">
					<div class="maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle">提示</div>
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

    function appendMask_tj(content,src){
    	$(".dc").hide();
        $("body").append(`
				<div class="maskNew ">
					<div class="maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle">提示</div>
						<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
						<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
							${content}
						</div>
						<div style="text-align:center;margin-top:30px">
							<button class="closeMask" onclick=closeMask_tj() >确认</button>
						</div>
					</div>
				</div>
			`)
    }

    function closeMask_tj() {
    	 $(".maskNew").remove();
    	 $(".dc").show();
    }

    function appendMask1(content,src){
        $("body").append(`
				<div class="maskNew maskNew1">
					<div class="maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=closeMask4() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle">提示</div>
						<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
						<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
							${content}
						</div>
						<div style="text-align:center;margin-top:30px">
							<button class="closeMask" onclick=closeMask4() >确认</button>
						</div>

					</div>
				</div>
			`)
    }
    function closeMask(id){
        $(".maskNew").remove();
        $.ajax({
            type:"post",
            url:"index.php?module=coupon&action=del&id="+id,
            async:true,
            success:function(res){
                console.log(res)
                if(res==1){
                    appendMask("删除成功","cg");
                }
                else{
                    appendMask("删除失败","ts");
                }
            }
        });
    }
    function closeMask1(){
        $(".maskNew").remove();
    }
    function closeMask4(){
        $(".maskNew1").remove();
        location.replace(location.href);
    }
    function confirm (content,id){
        $("body").append(`
				<div class="maskNew">
					<div class="maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle">提示</div>
						<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
						<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
							${content}
						</div>
						<div style="text-align:center;margin-top:30px">
							<button class="closeMask" style="margin-right:20px" onclick=closeMask("${id}") >确认</button>
							<button class="closeMask" onclick=closeMask1() >取消</button>
						</div>
					</div>
				</div>
			`)
    }
    function hm(){
        $(".dd").hide();
    }
</script>
<script type="text/javascript">
	window.onload=function(){
		$(".imgTd").each(function(){
			$(this).find("div").each(function(){
				$(this).hide();
			})
			$(this).find("div").eq(0).show()
		})
		$(".tbText").each(function(){
			$(this).find("span").each(function(){
				$(this).hide();
			})
			$(this).find("span").eq(0).show()
		})
	}
</script>
{/literal}
</body>

</html>