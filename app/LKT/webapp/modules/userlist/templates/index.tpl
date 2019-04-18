
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
#delorderdiv{
    margin-left: 20px;
    display: inline;
    color:red;
}
td a{
    width: 90%;
    margin: 2%;
    float: left;
}
.textIpt{
    border: 1px solid #eee;
    padding-left:20px;
    height: 30px;
    line-height: 30px;
}
#allAndNotAll{
    position: absolute;
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
        str = '<a style="text-decoration:none" class="ml-5" href="index.php?module=return&action=view&id='+param.id+'" title="查看"><i class="Hui-iconfont">&#xe63e;</i></a>',
        td = $(obj).parent("td");
        if(type=="score"){
            title="积分充值"
        }
        else if(type=="money"){
            title="金额充值"
        }
    console.log(id);
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
    htm +='<div class="maskNew" id="jq-alert"><div class="maskNewContent alert" style="height:250px">';
    if(title) htm+='<div class="maskTitle" style="font-weight:600">'+title+'</div>';
    if (prompt){
        if(type=="score"){
            prompt="积分金额"
        }
        else if(type=="money"){
            prompt="余额金额"
        }
        htm += '<div class="content"  style="text-align:center;font-size:22px;margin:0 auto;"><div class="prompt">';
        htm += `<span class="prompt-content"style=" display:inline-block;text-align:center;font-size:16px;margin-top:30px;padding-right:10px">${prompt}</span>
                    <input type="number" style="width:200px;padding-left:20px" placeholder="请输入充值的金额"  maxlength="11" oninput="if(value.length>10)value=value.slice(0,10)"
                    min="0" max="1000000" class="prompt-text textIpt">
                    <div style="color:#ff453d;font-size:12px;margin-top:10px;"><i><img style="margin-right:5px;width:12px;height:12px;" src="images/icon1/ts1.png"/></i>扣除请添加负号</div>

                </div>`
        htm +='</div>';
    }else {
        if(type == 1){
            htm+='<div class="content"  style="text-align:center;font-size:22px;margin:30px auto;">'+content+'</div>';
        }else{
            htm += '<div class="content"><div class="prompt"  style="text-align:center;font-size:22px;margin-bottom:30px;">';
            htm += '<div class="prompt-content">'+content+'</div>';
            htm += '<span class="pd20">应退：'+price+' <input type="hidden" value="'+price+'" class="ytje">   &nbsp; 实退:</span><input type="text" value="'+price+'" class="prompt-text inp_maie"></div>';
            htm +='</div>';
        }

    }
    if (!notext){
        htm+='<div class="fd-btn"><a href="'+yeslink+'" class="confirm closeMask" id="yes_btn" style="display:inline-block;">'+yestext+'</a></div>'
        htm+='</div>';
    }else {
        htm+='<div class="fd-btn" style="text-align:center;font-size:22px;margin:30px auto;">'+

            '<a href="'+yeslink+'" class="confirm closeMask" style="display:inline-block;margin-right:30px;height:35px;line-height:35px;"  id="yes_btn">'+yestext+'</a>'+
            '<a href="'+nolink+'"  data-role="cancel" class="cancel closeMask" style="display:inline-block;background:#fff;color:#007aff;height:35px;line-height:35px">'+notext+'</a>'+

            '</div>';
        htm+='</div>';
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
        var price = Number($(".prompt-text").val());
        $.ajax({
            type: "GET",
            url: url,
            data:{
                user_id:id,
                m:type,
                price:price
            },
            success: function(res){
                console.log(res)
                if(res == 1){
                    jqtoast('提交成功');
                    setTimeout(function () {
                        al.remove();
                        location.replace(location.href);
                    },300);
                }else{
                    jqtoast('操作失败!');
                }
            }
        });
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
    font-size: 16px;
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
    height: 50px;
    background:none;
    border:none;
    outline: none;
    width: 100%;
    box-sizing: border-box;
    margin-top: 20px;
    background-color: #FFF;
    border:1px solid #dcdcdc;
    text-indent:5px;
    text-align: center;
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
.dj{
    color: #fff;
    font-size: 12px;
    border-radius: 2px;
    width: 40%;
    display: block;
    margin: 5px;
    float: left;
}
#DataTables_Table_0_length{
    position: absolute;
    bottom: 3px;
    padding-bottom: 10px;
    left: 20px;
}
#DataTables_Table_0_info{
    margin-left: 20px;
}
#btn1:hover{
    background-color: #2481e5!important;
}
#btn1{
    height: 36px;
    line-height: 36px;
}

</style>
{/literal}
<title>订单列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe705;</i> 用户管理 <span class="c-gray en">&gt;</span> 用户列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c">
        <form method="get" action="index.php" name="form1" style="    padding-bottom: 15px">
            <div style="height:30px; border-left:solid 1px #fff;">
                <input type="hidden" name="module" value="userlist"  />
                <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />
                <input type="text" class="input-text" style="width:200px" placeholder="用户名" name="name" value="{$name}">
                <input type="text" class="input-text" style="width:200px" placeholder="手机号码" name="tel" value="{$tel}">
                <select name="source" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
                    <option value="0" selected>用户来源</option>
                    <option value="1" {if $source == 1} selected {/if}>小程序</option>
                    <option value="2" {if $source == 2} selected {/if}>手机app</option>
                </select>
                <div style="position: relative;display: inline-block;">
					<input name="startdate" value="{$startdate}" size="8" readonly class="scinput_s iptRl" style="" />
					<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 10px;top: 7px;" onclick="new Calendar().show(document.form1.startdate);" />
				</div>至
				<div style="position: relative;display: inline-block;margin-left: 5px;">
					<input  name="enddate" value="{$enddate}" size="8" readonly class="scinput_s iptRl" style="" />
					<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 10px;top: 7px;" onclick="new Calendar().show(document.form1.enddate);" />
				</div>
                <input name="" id="btn1" class="btn btn-success" type="submit" value="查询">
                <!--<input type="button" id="btn2" value="导出本页" class="btn btn-success" onclick="excel('ne')">-->
                <input type="button" id="btn2" value="导出" class="btn btn-success" onclick="excel('all')">
            </div>

        </form>
    </div>

    <div class="mt-20">
        <div style="width:80%;margin:20px 0 20px 0px;height: 41px;line-height: 41px;display: flex; align-items: center;">
        	<input type="checkbox" id="allAndNotAll"/>
        	<label for="allAndNotAll" id="qxIpt" style="height: 29px;line-height: 29px;">全选</label>
           	<a class="btn radius"  style="background-color:#38b4ed;color: #fff;margin-left: 20px;" id="getAllSelectedId" href="javascript:void(0);" title="发私信"  style="margin-left:20px;">
           		<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
                    <img src="images/icon1/fsx.png"/>&nbsp;发私信
           	 	</div>
           	</a>
        </div>
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th width="40">选择</th>
                    <th width="40">ID</th>
                    <th width="100">头像</th>
                    <th width="150">用户昵称</th>
                    <th width="100">余额</th>
                    <th width="50">积分</th>
                    <th width="180">注册时间</th>
                    <th width="150">手机号码</th>
                    <th width="150">来源</th>
                    <th width="100">订单总额</th>
                    <th width="100">订单总数</th>
                    <th width="100">分享次数</th>
                    <th style="width: 200px;">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>
                    	<div style="display: flex;align-items: center;height: 60px;">
                            <input type="checkbox" name="checkid" id="checkid{$item->id}" class="inputC " value="{$item->id}" onchange="selectId({$item->id})">
                            <label for="checkid{$item->id}"></label>
                        </div>
                    	
                    </td>
                    <td>{$item->id}</td>
                    <td><image class='pimg' src="{$item->headimgurl}" style="width: 60px;height:60px;border-radius: 30px;border: 1px solid darkgray;"/></td>
                    <td>{$item->user_name}</td>
                    <td>￥{$item->money}</td>
                    <td>{$item->score}</td>
                    <td>{$item->Register_data}</td>
                    <td>{$item->mobile}</td>
                    <td>{if $item->source == 1}小程序{elseif $item->source == 2}手机app{/if}</td>
                    <td>{$item->z_price}</td>
                    <td style="width: 70px;">{$item->z_num}</td>
                    <td style="width: 70px;">{$item->share_num}</td>
                    <td style="width: 200px;" >
                        <a style="text-decoration:none" class="ml-5" href="javascript:;" title="充值积分" onclick="refuse(this,'{$item->user_id}','score','请输入充值的积分金额，扣除添加负号')">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/jfcz.png"/>&nbsp;充值积分
                            	</div>
                    		</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="javascript:;" title="充值余额" onclick="refuse(this,'{$item->user_id}','money','请输入充值的余额，扣除添加负号')">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;display: flex;align-items: center;"> 
                                <img src="images/icon1/yecz.png"/>&nbsp;充值余额
                            	</div>
                    		</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=userlist&action=view&id={$item->id}" title="查看" >
                        	<div style="font-size: 12px;display: flex;align-items: center;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/ck.png"/>&nbsp;查看
                            	</div>
                    		</div>
                        </a>
                        <a  style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm('确定要删除此用户吗?',{$item->id})">
                        	<div style="font-size: 12px;display: flex;align-items: center;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/del.png"/>&nbsp;删除
                            	</div>
                    		</div>
                        </a>
                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>

<script type='text/javascript' src='modpub/js/calendar.js'> </script>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/FineMessBox/js/common.js"></script>
<script type="text/javascript" src="style/FineMessBox/js/subModal.js"></script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/js/H-ui.js"></script>
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

{literal}
<script type="text/javascript">
function excel(pageto) {
    var pagesize = $("#pagesize").val();
    location.href=location.href+'&pageto='+pageto+'&pagesize='+pagesize;
}
function refuse(obj,id,type,text) {
    jqalert({
        title:'充值',
        prompt:text,
        yestext:'提交',
        notext:'取消',
        id:id,
        url:"index.php?module=userlist&action=recharge",
        obj:obj,
        type:type,
    })
};

var ids= [];
$("#allAndNotAll").click(function() {
    if (this.checked){
        $("input[name=checkid]").each(function(index){
            $(this).prop("checked", true);
            var val = $(this).val();
            arrModify(ids,val,1);
        });
    } else {
        $("input[name=checkid]").each(function(index) {
            $(this).prop("checked", false);
            var val = $(this).val();
            arrModify(ids,val,2);
        });
    }

});
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
}
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
}

function arrModify(arr,val,type) {   //１为增加元素  2为删除元素
    if(type == 1){
        var index = $.inArray(val,arr);
        if(index === -1) arr.push(val);
    }else if(type == 2){
        arr.remove(val);
    }
    return arr;
}
function selectId(i){
    i = i.toString();
    var index = $.inArray(i,ids);  //判断数组中是否存在该值,如存在返回下标值,如不存在，返回-1
    if($('#checkid'+i).prop('checked') == true){
        if(index === -1) ids.push(i);
    }else{
        ids.remove(i);
    }
}
Array.prototype.distinct = function(){   //数组去重
    var arr = this,
        result = [],
        i,
        j,
        len = arr.length;
    for(i = 0; i < len; i++){
        for(j = i + 1; j < len; j++){
            if(arr[i] === arr[j]){
                j = ++i;
            }
        }
        result.push(arr[i]);
    }
    return result;
}


$('#getAllSelectedId').click(function(){
    if(ids.length === 0){
        layer.alert('没有选择用户！',{
            icon: 5,
            title: "提示"
        });
    }else{
        ids = ids.distinct();
        var delIds=ids.join(",");
        location.href="index.php?module=userlist&action=Seng&id="+delIds
    }
});

function appendMask(content,src){
	$("body").append(`
		<div class="maskNew">
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
};
function closeMask(id){
	$.ajax({
    	type:"get",
    	url:"index.php?module=userlist&action=del&id="+id,
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
};
    </script>
{/literal}
</body>
</html>