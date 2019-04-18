
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
<title>评价管理</title>
{literal}
<style type="text/css">
	  #btn1:hover{
            	background-color: #2481e5!important;
            }
             #btn1{
            	height: 36px;
            	line-height: 36px;
            }
@font-face {font-family: "iconfont";
  src: url('iconfont.eot?t=1529402722179'); /* IE9*/
  src: url('iconfont.eot?t=1529402722179#iefix') format('embedded-opentype'), /* IE6-IE8 */
  url('data:application/x-font-woff;charset=utf-8;base64,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') format('woff'),
  url('iconfont.ttf?t=1529402722179') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
  url('iconfont.svg?t=1529402722179#iconfont') format('svg'); /* iOS 4.1- */
}
 table a{
 	width: 27%;
 	margin: 2%!important;
 }
.iconfont {
  font-family:"iconfont" !important;
  font-size:16px;
  font-style:normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.icon-zhongping:before { content: "\e504"; }

.icon-bottom-comment:before { content: "\e508"; }

.icon-chaping:before { content: "\e634"; }

.icon-haoping:before { content: "\e608"; }

.icon-frown:before { content: "\e77e"; }

.icon-meh:before { content: "\e780"; }

.icon-smile:before { content: "\e783"; }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 评价列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="comments" />
            <input type="hidden" name="ordtype" value="{$otype}" />
            <input type="hidden" name="gcode" value="{$status}" />
            <input type="hidden" name="ocode" value="{$ostatus}" />
            <select name="otype" class="select" style="width: 80px;height: 31px;vertical-align: middle;">
                {foreach from=$ordtype item="item" key="key"}
                   <option value="{$key}" {if $otype==$key}selected{/if}>{$item}</option>
                {/foreach} 
            </select>

            <input type="text" name="sNo" size='8' value="{$sNo}" id="" placeholder=" 订单编号" style="width:200px" class="input-text">
            <input name="startdate" value="{$startdate}" size="8" readonly class="scinput_s" style="width: 100px; height:26px;font-size: 14px;vertical-align: middle;" />
            <img src="modpub/images/datetime.gif" style="cursor:pointer;" onclick="new Calendar().show(document.form1.startdate);" />
            至
            <input name="enddate" value="{$enddate}" size="8" readonly  class="scinput_s" style="width: 100px; height:26px;font-size: 14px;vertical-align: middle;"/>
            <img src="modpub/images/datetime.gif" style="cursor:pointer;" onclick="new Calendar().show(document.form1.enddate);" />
            <input class="btn btn-success" id="btn1" type="submit" value="查询">

        </form>
    </div>
    
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>id</th>
                    <th>评价人</th>
                    <th>订单编号</th>
                    <th>商品名称</th>
                    <th>评价类型</th>
                    <th>评价内容</th>
                    <th>添加时间</th>
                    <th>订单金额</th>
                    <th>订单类型</th>
                    <th style="width: 180px">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$order item=item name=f1}
                <tr class="text-c">
                    <td>{$item->id}</td>
                    <td style="width: 60px">{$item->anonymous}</td>
                    <td>{$item->r_sNo}</td>
                    <td style="text-align: left;">{$item->p_name}</td>
                    <td style="width: 70px">
        {if $item->CommentType1 == 'GOOD'}<span class="icon iconfont icon-haoping" style="color: #43CD80;"></span>{elseif $item->CommentType1 == 'NOTBAD'}<span style="color: #EEAD0E;" class="icon iconfont icon-zhongping"></span>{else}<span style="color: #EE4000;" class="icon iconfont icon-frown"></span>{/if}
                    </td>
                    <td style="width: 70px">{$item->content}</td>
                    <td>{$item->add_time}</td>
                    <td style="width: 70px">{$item->p_price}</td> 
                    <td style="width: 70px">{if $item->otype == 'pt'}<span>拼团订单</span>{else}{if $item->drawid>0}<span>抽奖订单</span>{else}<span>普通订单</span>{/if}{/if}</td>
                    <td style="width: 180px">
                      <a style="text-decoration:none" class="ml-5" href="index.php?module=comments&action=reply&id={$item->id}" title="回复评论" >
                      	<div style="align-items: center;font-size: 12px;display: flex;">
                        	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                            <img src="images/icon1/hf.png"/>&nbsp;回复
                        	</div>
                    		</div>
                      </a>

                        <a style="text-decoration:none" class="ml-5" href="index.php?module=comments&action=modify&id={$item->id}" title="修改评价" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                        	</div>
                        </a>
                         <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="del_coms(this,{$item->id})" title="删除评价" >
                         	<div style="align-items: center;font-size: 12px;display: flex;">
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
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>
<script type='text/javascript' src='modpub/js/calendar.js'> </script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
{literal}
<script type="text/javascript">
    function del_coms(obj,id) {
    var r=confirm("确定删除本条评论吗？",id)
    if (r==true){
        $.ajax({
           type: "POST",
           url: "index.php?module=comments&action=del&id="+id,
           data: "",
           success: function(res){
                if(res == 1){
                  alert('删除成功!');
                  $(obj).parents(".text-c").remove();
                }else{
                  alert('删除失败!');
                }
           }
        });
    }else{

    }

    }
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
			$(".maskNew").remove();
		    $.ajax({
		    	type:"post",
		    	url:"index.php?module=comments&action=del&cid="+id,
		    	async:true,
		    	success:function(res){
		    		if(res==1){
		    			appendMask("删除成功","cg");
		    			$(obj).parents(".text-c").remove();
		    			location.href="index.php?module=product";
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
		function confirm (content,id){
			console.log("index.php?module=product&action=del&cid=",id);
			$("body").append(`
					<div class="maskNew">
						<div class="maskNewContent">
							<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
							<div class="maskTitle">删除评论</div>	
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
</script> 
{/literal}
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>
</body>
</html>