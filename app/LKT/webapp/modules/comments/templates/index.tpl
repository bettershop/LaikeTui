
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

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


<nav class="breadcrumb">
    系统管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=comments">评价列表</a>
</nav>



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

            <input type="text" name="sNo" size='8' value="{$sNo}" id="" placeholder=" 订单编号" autocomplete="off" style="width:200px" class="input-text">
            <input placeholder="请输入开始时间" id="startdate" name="startdate" value="{$startdate}" size="8" readonly class="scinput_s" style="width: 140px; height:26px;font-size: 14px;vertical-align: middle;" />
            至
            <input placeholder="请输入结束时间" id="enddate" name="enddate" value="{$enddate}" size="8" readonly  class="scinput_s" style="width: 140px; height:26px;font-size: 14px;vertical-align: middle;"/>
            <input class="btn btn-success" id="btn1" type="submit" value="查询">

        </form>
    </div>
    
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>ID</th>
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
                    <td style="width: 60px">{$item->uid}</td>
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

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


{literal}
<script type="text/javascript">

laydate.render({
          elem: '#startdate', //指定元素
           trigger: 'click',
          type: 'date',

        });
       
        laydate.render({
          elem: '#enddate',
          trigger: 'click',
          type: 'date'
        });


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


</body>
</html>