
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="style/css/style.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
{literal}
<style type="text/css">
i{
    cursor: pointer;
}

.textIpt{
	border: 1px solid #eee;
	padding-left:20px;
	height: 30px;
	line-height: 30px;
}
</style>
<script type="text/javascript">
    setSize();
    addEventListener('resize',setSize);
    function setSize() {
        document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
    }   
    /*alert弹出层*/
    function jqalert(param) {
        var title = param.title,
            content = param.content,
            yestext = param.yestext,
            notext = param.notext,
            yesfn = param.yesfn,
            nofn = param.nofn,
            id = param.id,
            url = param.url,
            nolink = param.nolink,
            yeslink = param.yeslink,
            prompt = param.prompt,
            click_bg = param.click_bg,
            obj = param.obj,
            type = param.type,
            price = param.price,
            str = `<a style="text-decoration:none" class="ml-5" href="index.php?module=return&action=view&id=${id}" title="查看">
            		<div style="align-items: center;font-size: 12px;display: flex;">
                    	<div style="margin: 0 auto;display: flex;align-items: center;">
                        <img src="images/icon1/ck.png"/>&nbsp;查看
                        </div>
                    </div>
            		</a>`,
            
            td = $(obj).parent("td");
            
        if (click_bg === undefined){
            click_bg = true;
        }
        if (yestext === undefined){
            yestext = '确认';
        }
        if (!nolink){
            nolink = 'javascript:void(0);';
        }
        if (!yeslink){
            yeslink = 'javascript:void(0);';
        }

         var htm = '';
                htm +=`<div class="maskNew" id="jq-alert" style="text-align:center;"><div class="alert maskNewContent">`
                if(title) htm+=`<div class="MaskTitle">${title}</div>`;
                if (prompt){
                    htm += `<div class="content" style="text-align:center;margin-bottom:30px;"><div class="prompt">
                    		<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
		                     <p class="prompt-content" style="text-align:center;font-size:22px;margin:30px auto;">${prompt}</p>
		                    <input type="text" class="prompt-text textIpt"></div>
		                    </div>`
                }else {
                    if(type == 1 || type == 6){
                        htm+=`<div class="content" style="text-align:center;margin:30px auto;font-size:22px;">${content}</div>`
                    }else{
                        htm +=`<div class="content">
                        	<div class="prompt">
                       	 		<p class="prompt-content" style="text-align:center;font-size:22px;margin:30px auto;">${content}</p>
                       	 		<div style="text-align:center;margin-bottom:20px;">
                       	 			<span class="pd20" >应退：${price} <input type="hidden" value="${price}" class="ytje"> &nbsp; 实退:</span>
                        			<input type="text" style="width:100px" value="${price}" class="prompt-text inp_maie textIpt" readonly="readonly">
                        		</div>
                       	 	</div>
                        	
                        </div>`;
                    }
                }
                if (!notext){
                    htm+=`<div class="fd-btn">
                   		 <a href="${yeslink}" class="confirm closeMask" style="display:inline-block;font-size:14px" id="yes_btn">${yestext}</a>
                    	</div>
                   	</div>`
                }else {
                    htm+=`<div class="fd-btn">
                    	<a href="${yeslink}" class="confirm closeMask"   style="display:inline-block;font-size:14px;margin-right:30px;"  id="yes_btn">${yestext}</a>
                       	<a href="${nolink}"  data-role="cancel" class="cancel closeMask" style="display:inline-block;font-size:14px;background-color:#fff;color:#666;">${notext}</a>
                         </div>
                        </div>`
                }

        $('body').append(htm);
        var al = $('#jq-alert');
        al.on('click','[data-role="cancel"]',function () {
            al.remove();
           
            if (nofn){
                param.nofn();
                nofn = '';
            }
            param = {};
        });
        $(document).delegate('.alert','click',function (event) {
            event.stopPropagation();
        });
        $("#yes_btn").click( function () {
            if(type == 2 || type == 5 || type == 8){
                    var text = $(".prompt-text").val();
                    if(text.length >1){
                          $.ajax({
                           type: "POST",
                           url: url,
                           data: "id="+id+'&text='+text+'&m='+type,
                           success: function(res){
                           	console.log(res);
                            if(res){
                                td.html(str);
                                td.prev().html('<span style="color:#ff2a1f;">已拒绝</span>');
                                jqtoast('提交成功');
                                setTimeout(function () {
                                    al.remove();
                                },300);     
                            }else{
                                jqtoast('操作失败!');
                            }
                           }
                        });

                    }else{
                        jqtoast('原由不能为空!');
                    }
            }else{

                    var text = $(".prompt-text").val();
                    if(type == 1 || type == 6){

                        $.ajax({
                           type: "POST",
                           url: url,
                           data: "id="+id+'&m='+type,
                           success: function(res){
                           	console.log(res);
                            if(res == 1){
                                td.html(str);
                                if(type == '4' || type == '9'){
                                   var status = '<span style="color:#8FBC8F;">已退款</span>';
                                }else{
                                   var status = '<span style="color:#A4D3EE;">待买家发货</span>';
                                }
                                td.prev().html('<span style="color:#30c02d;">'+status+'</span>');
                                jqtoast('提交成功');
                                setTimeout(function () {
                                    al.remove();
                                },300);     
                            }else{
                                jqtoast('操作失败!');
                            }
                           }
                        });
                    }
                    else{
                    	console.log(url)
                         if(Number(text) > 0 && Number(text) <= Number(price)){
                            $.ajax({
                               type: "POST",
                               url: url,
                               data: "id="+id+'&m='+type+'&price='+Number(text),
                               success: function(res){
                               	 console.log(res);
                                if(res == 1){
                                    td.html(str);
                                    if(type == '4' || type == '9'){
                                       var status = '<span style="color:#8FBC8F;">已退款</span>';
                                    }else{
                                       var status = '<span style="color:#A4D3EE;">待买家发货</span>';
                                    }
                                    td.prev().html('<span style="color:#30c02d;">'+status+'</span>');
                                    jqtoast('退款成功'+text);
                                    setTimeout(function () {
                                        al.remove();
                                    },300);     
                                }else{
                                    jqtoast('操作失败!');
                                }
                               }
                            });
                        }else{
                            jqtoast('输入金额有误,请重新输入!');
                        }
                                              
                    }
            }

        });

        if(click_bg === true){
            $(document).delegate('#jq-alert','click',function () {
                setTimeout(function () {
                    al.remove();
                },300);
                yesfn ='';
                nofn = '';
                param = {};
            });
        }

    }
    /*toast 弹出提示*/
    function jqtoast(text,sec) {
        var _this = text;
        var this_sec = sec;
        var htm = '';
        htm += '<div class="jq-toast" style="display: none;">';
        if (_this){
            htm +='<div class="toast">'+_this+'</div></div>';
            $('body').append(htm);
            $('.jq-toast').fadeIn();

        }else {
            jqalert({
                title:'提示',
                content:'提示文字不能为空',
                yestext:'确定'
            })
        }
        if (!sec){
            this_sec = 2000;
        }
        setTimeout(function () {
            $('.jq-toast').fadeOut(function () {
                $(this).remove();
            });
            _this = '';
        },this_sec);
    }
</script>

<style>
        .show-list{
            width:80%;
            margin: 0 auto;
        }
        .show-list li{
            height: 10px;
            font-size: 18px;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-bottom: 1px solid #dcdcdc;
        }
            *{
                margin: 0;
                padding:0;
                list-style: none;
            }
            a{
                text-decoration: none;
            }

            /*jq-alert弹出层封装样式*/
            .jq-alert{
                position: fixed;
                top:0;
                left:0;
                width:100%;
                height:100%;
                display: -webkit-box;
                display: -webkit-flex;
                display: -ms-flexbox;
                display: flex;
                -webkit-flex-direction: row;
                flex-direction: row;
                -webkit-justify-content: center;
                -webkit-align-items: center;
                justify-content: center;
                align-items: center;
                background-color: rgba(0,0,0,.3);
                z-index: 99;
            }
            .jq-alert .alert{
                background-color: #FFF;
                width:440px;
                height:250px;
                border-radius: 4px;
                overflow: hidden;
            }
            .jq-alert .alert .title{
                position: relative;
                margin: 0;
                font-size: 18px;
                text-align: center;
                font-weight: normal;
                color: rgba(0,0,0,.8);
            }
            .jq-alert .alert .content{
                padding:0 18px;
                font-size: 18px;
                color: rgba(0,0,0,.6);
                height: 56%;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }
            .jq-alert .alert .content .prompt{
                width:100%;
            }
            .jq-alert .alert .content .prompt .prompt-content{
                font-size: 18px;
                color: rgba(0,0,0,.54);
                margin: 0;
                margin-bottom: 20px;
                /*text-align: center;*/
            }
            .jq-alert .alert .content .prompt .prompt-text{
                height: 73px;
                background:none;
                border:none;
                outline: none;
                width: 100%;
                box-sizing: border-box;
                margin-top: 20px;
                background-color: #FFF;
                border:1px solid #dcdcdc;
                text-indent:5px;
            }
            .jq-alert .alert .content .prompt .prompt-text:focus{
                border: 1px solid #2196F3;
                background-color: rgba(33,150,243,.08);
            }
            .jq-alert .alert .fd-btn{
                height: 50px;
                position: relative;
                display: -webkit-box;
                display: -webkit-flex;
                display: -ms-flexbox;
                display: flex;
                -webkit-flex-direction: row;
                flex-direction: row;
                -webkit-justify-content: center;
                -webkit-align-items: center;
                justify-content: center;
                align-items: center;
            }
            .jq-alert .alert .fd-btn:after{
                position: absolute;
                content: "";
                top:0;
                left:0;
                width:100%;
                height: 1px;
                background-color: #F3F3F3;
            }
            .jq-alert .alert .fd-btn a{
                width:100%;
                font-size: 18px;
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: center;
                color: rgba(0,0,0,.8);
            }
            .jq-alert .alert .fd-btn a.cancel{
                position: relative;
                color: rgba(0,0,0,.5);
                line-height: 50px;
            }
            .jq-alert .alert .fd-btn a.cancel:after{
                content: "";
                position: absolute;
                top:.1rem;
                right:0;
                width: 1px;
                height: .6rem;
                background-color: #F3F3F3;
            }
            .jq-alert .alert .fd-btn a.confirm{
                color: #2196F3;
            }
            .jq-alert .alert .fd-btn a.confirm:active{
                background-color: #2196F3;
                color: #FFF;
            }

            /*toast弹出层*/
            .jq-toast{
                z-index: 999;
                position:fixed;
                top:0;
                left:0;
                width:100%;
                height: 100%;
                display: -webkit-box;
                display: -webkit-flex;
                display: -ms-flexbox;
                display: flex;
                flex-direction: row;
                -webkit-flex-direction: row;
                -ms-flex-direction: row;
                justify-content: center;
                -webkit-justify-content: center;
                align-items: center;
                -webkit-align-items: center;
            }
            .jq-toast .toast{
                max-width: 80%;
                padding: 10px 20px;
                background-color: rgba(0,0,0,.48);
                color: #FFF;
                border-radius: 4px;
                font-size: 18px;
            }
            .confirm .cancel{
                text-decoration: none !important;
            }
            .inp_maie{
                height: 32px !important;
                width: 20% !important;
                margin-top: 0 !important;
            }

           	td a{
                width: 28%!important;
                margin: 2%!important;
                float: left;
            }
</style>
{/literal}
<title>退货列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 退货列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
<input type="hidden" class="price" value="">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="return" />
            <input type="text" name="p_name" size='8' value="{$p_name}" id="" placeholder="订单号" style="width:200px" class="input-text">
            <select name="r_type" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
                <option value="">订单状态</option>
<option {if $r_type == 1}selected="selected"{/if} value="1">审核中</option>
<option {if $r_type == 2}selected="selected"{/if} value="2">待买家发货</option>
<option {if $r_type == 3}selected="selected"{/if} value="3">已拒绝</option>
<option {if $r_type == 4}selected="selected"{/if} value="4">待商家收货</option>
<option {if $r_type == 5}selected="selected"{/if} value="5">已退款</option>
<option {if $r_type == 6}selected="selected"{/if} value="6" >拒绝并退回商品</option>
            </select>
          	<div style="position: relative;display: inline-block;">
				<input name="startdate" value="{$startdate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 25px;top: 7px;" onclick="new Calendar().show(document.form1.startdate);" />~
				</div>
				<div style="position: relative;display: inline-block;">
				<input  name="enddate" value="{$enddate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 10px;top: 7px;" onclick="new Calendar().show(document.form1.enddate);" />
				</div>
			<input name="" id="btn1" class="btn btn-success" type="submit" value="查询">
            <input type="button" id="btn2"  value="导出" class="btn btn-success" onclick="excel('all')">
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序号</th>
                    <th width="150" aria-valuetext="user_id">用户id</th>
                    <th width="130" aria-valuetext="p_name">产品名称</th>
                    <th width="150" aria-valuetext="p_price">产品价格</th>
                    <th width="150" aria-valuetext="num">数量</th>
                    <th width="150" aria-valuetext="r_sNo">订单号</th>
                    <th width="150" aria-valuetext="add_time">发布时间</th>
                    <th width="150" aria-valuetext="re_type">类型</th>
                    <th width="100" aria-valuetext="status">状态</th>
                    <th style="width:250px;" width="250px">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">   
                    <td>{$item->id}</td>
                    <td>{$item->user_id}</td>
                    <td style="text-align:left!important;width: 400px;">{$item->p_name}</td>
                    <td>{$item->p_price}</td>
                    <td>{$item->num}</td>
                    <td>{$item->r_sNo}</td>
                    <td>{$item->add_time}</td>
                    <td style="width: 120px;">
                        {if $item->re_type == 2}
                            <span >仅退款</span>
                        {elseif $item->re_type == 1}
                            <span >退货退款</span>
                        {else}
                            <span >换货</span>
                        {/if}
                    </td>
                    <td style="width: 230px;">
                        {if $item->r_type == 0}<span>审核中</span>
                        {elseif $item->r_type == 1 || $item->r_type == 6}<span >待买家发货</span>
                        {elseif $item->r_type == 2 || $item->r_type == 8}<span >已拒绝</span>
                        {elseif $item->r_type == 3 }<span>待商家收货</span>
                        {elseif $item->r_type == 4 || $item->r_type == 9}<span >已退款</span>
                        {else}<span style="color: #ff2a1f;">拒绝并退回商品</span>{/if}
                    </td>

                    <td width="250px"> 
                        <a style="text-decoration:none;" class="ml-5" href="index.php?module=return&action=view&id={$item->id}" title="查看">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin: 0 auto;display: flex;align-items: center;">
                                <img src="images/icon1/ck.png"/>&nbsp;查看
                                </div>
                            </div>
                        </a>
                        {if $item->r_type == 0}
                            {if $item->re_type == 1}
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="审核通过" onclick="is_ok(this,{$item->id},1,'确定要通过该用户的申请,并让用户寄回?')">
                                    <div style="align-items: center;font-size: 12px;display: flex;">
	                                    <div style="margin: 0 auto;display: flex;align-items: center;">
	                                    <img src="images/icon1/qy.png"/>&nbsp;通过
	                                    </div>
                                    </div>
                                </a>
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="审核拒绝" onclick="refuse(this,{$item->id},2)">
                                    <div style="align-items: center;font-size: 12px;display: flex;">
	                                    <div style="margin: 0 auto;display: flex;align-items: center;">
	                                    <img src="images/icon1/jy.png"/>&nbsp;拒绝
	                                    </div>
                                    </div>
                                </a>
                            {elseif $item->re_type == 2}
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="审核通过" onclick="is_ok(this,{$item->id},9,'确定要通过该用户的申请,并将钱款原路返还?')">
                                    <div style="align-items: center;font-size: 12px;display: flex;">
	                                    <div style="margin: 0 auto;display: flex;align-items: center;">
	                                    <img src="images/icon1/qy.png"/>&nbsp;通过
	                                    </div>
                                    </div>
                                </a>
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="审核拒绝" onclick="refuse(this,{$item->id},8)">
                                    <div style="align-items: center;font-size: 12px;display: flex;">
	                                    <div style="margin: 0 auto;display: flex;align-items: center;">
	                                    <img src="images/icon1/jy.png"/>&nbsp;拒绝
	                                    </div>
                                    </div>
                                </a>
                            {else}
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="审核通过" onclick="is_ok(this,{$item->id},6,'确定要通过该用户的申请,并让用户寄回?')"><div style="align-items: center;font-size: 12px;display: flex;">
                                    <div style="margin: 0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/qy.png"/>&nbsp;通过
                                    </div>
                                    </div>
                                </a>
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="审核拒绝" onclick="refuse(this,{$item->id},2)"><div style="align-items: center;font-size: 12px;display: flex;">
                                    <div style="margin: 0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/jy.png"/>&nbsp;拒绝
                                    </div>
                                    </div>
                                </a>
                            {/if}
                        {elseif $item->r_type == 3}

                            {if $item->re_type < 3}
                             <a style="text-decoration:none" class="ml-5" href="javascript:;" title="同意并退款" onclick="is_ok(this,{$item->id},4,'确定已到货并退款到用户?')">
                             	<div style="align-items: center;font-size: 12px;display: flex;">
                                    <div style="margin: 0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/qy.png"/>&nbsp;通过
                                    </div>
                                    </div>
                                </a>
                            {else}
                                 <a style="text-decoration:none" class="ml-5" href="javascript:;" title="已收到货并回寄" onclick="zxfahuo('{$item->r_sNo}','{$item->id}')">
                                 	<div style="align-items: center;font-size: 12px;display: flex;">
                                    <div style="margin: 0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/sx.png"/>&nbsp;收到货并回寄
                                    </div>
                                    </div>
                                </a>
                            {/if}
                           
                            <a style="text-decoration:none" class="ml-5" href="javascript:;" title="拒绝并退回商品" onclick="refuse(this,{$item->id},5)">
                            	<div style="align-items: center;font-size: 12px;display: flex;">
                                    <div style="margin: 0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/jy.png"/>&nbsp;拒绝
                                    </div>
                                    </div>
                                </a>

                        {/if}


                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>
<script type='text/javascript' src='modpub/js/calendar.js'> </script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

{literal}
<script type="text/javascript">



function excel(pageto) {
    var pagesize = $("[name='DataTables_Table_0_length'] option:selected").val();
    var page = $(".current").text();
    var type_asc = $(".sorting_asc").attr('aria-valuetext');
    var type_desc = $(".sorting_desc").attr('aria-valuetext');
    if(type_asc && !type_desc){
        var sort = 'asc';
        var sort_name = type_asc;
    }else{
        var sort = 'desc';
        var sort_name = type_desc;
    }
    location.href=location.href+'&pageto='+pageto+'&pagesize='+pagesize+'&page='+page+'&sort='+sort+'&sort_name='+sort_name;
}

function refuse(obj,id,type) {
    jqalert({
        title:'提示',
        prompt:'请填写拒绝理由?',
        yestext:'提交',
        notext:'取消',
        id:id,
        url:"index.php?module=return&action=examine",
        obj:obj,
        type:type,
    })
};

function zxfahuo(sNo,id) {
     layer_show('添加快递信息','index.php?module=return&action=addsign&id='+id+'&sNo='+sNo,600,400);
}

function is_ok(obj,id,type,content) {
    console.log(type == 4 || type == 9);
    if(type == 4 || type == 9){
        $.ajax({
            type: "GET",
            url: "index.php?module=return&action=examine",
            data: "id="+id+'&f=check'+'&m='+type,
            success: function(res){
                if(res){
                    $(".price").val(res);
                    jqalert({
                        title:'提示',
                        content:content,
                        yestext:'是的',
                        url:"index.php?module=return&action=examine",
                        id:id,
                        notext:'取消',
                        obj:obj,
                        type:type,
                        price:res
                    })
                }else{
                    jqtoast('操作失败!');
                }
            }
        });
    }else if(type == 7){
        send_btn(id);
    }else{
        jqalert({
            title:'提示',
            content:content,
            yestext:'是的',
            url:"index.php?module=return&action=examine",
            id:id,
            notext:'取消',
            obj:obj,
            type:type,
        })
    }

};
        function appendMask(content,src){
			$("body").append(`
					<div class="maskNew">
						<div class="maskNewContent">
							<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
							<div class="maskTitle">删除订单</div>	
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
		function closeMask(id){
			 $.get("index.php?module=return&action=del",{'id':id},function(res){
                    if(res.status=="1"){
                       appendMask("删除成功","cg");
                    }else{
                        appendMask("删除失败","ts");
                    }
                },"json");
		    $(".maskNew").remove();
		}
		function closeMask2(strId){
			console.log(strId);
//			let strId1=strId.split(",");
//			console.log(strId);
             $.get("index.php?module=product&action=del",{'id':strId},function(res){
                if(res.status=="1"){
                    appendMask("删除成功","cg")
                }else{
                    appendMask("删除失败","ts")
                }
            },"json");
		    $(".maskNew").remove();
		}
		function closeMask1(){
			$(".maskNew").remove();
			location.replace(location.href);
		}
		function confirm (obj,id,type,content){
			console.log(obj)
			if(type == 1 || type == 6){
				$("body").append(`
					<div class="maskNew">
						<div class="maskNewContent">
							<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
							<div class="maskTitle">提示</div>	
							<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
							<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
								${content}
							</div>
							<div><span class="pd20">应退：'+price+' <input type="hidden" value="'+price+'" class="ytje">   &nbsp; 实退:</span>
							<input type="text" value="'+price+'" class="prompt-text inp_maie"></div>
							<div style="text-align:center;margin-top:30px">
								<button class="closeMask" style="margin-right:20px" onclick=closeMask("${id}"}) >确认</button>
								<button class="closeMask" onclick=closeMask1() >取消</button>
							</div>
						</div>
					</div>	
				`)
			}
			
		}
		function confirm1 (content,strId,type){
			$("body").append(`
					<div class="maskNew">
						<div class="maskNewContent">
							<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
							<div class="maskTitle">删除订单</div>	
							<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
							<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
								${content}
							</div>
							<div style="text-align:center;margin-top:30px">
								<button class="closeMask" style="margin-right:20px" onclick=closeMask2("${strId}") >确认</button>
								<button class="closeMask" onclick=closeMask1() >取消</button>
							</div>
						</div>
					</div>	
				`)
			
		}

</script>

{/literal}
</body>
</html>