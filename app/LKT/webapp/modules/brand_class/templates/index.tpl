
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}


<title>商品品牌</title>
{literal}
    <style type="text/css">
        td a{
            width: 29%;
            float: left;
            margin: 2%!important;
        }
    </style>
{/literal}

</head>
<body>

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    品牌管理 
</nav>


<div class="pd-20">
    <div style="clear:both;">
        <button  class="btn newBtn radius" onclick="location.href='index.php?module=brand_class&action=add';">
        	<img src="images/icon1/add.png" alt="" />新增品牌
        </button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>ID</th>
                    <th>品牌图片</th>
                    <th>品牌名称</th>
                    <th>添加时间</th>
                    <th style="width: 180px;">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$item->brand_id}</td>
                    <td>{if $item->brand_pic != ''}<image class='pimg' src="{$uploadImg}{$item->brand_pic}" style="width: 50px;height:50px;"/>{else}<span>暂无图片</span>{/if}</td>
                    <td>{$item->brand_name}</td>
                    <td>{$item->brand_time}</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=brand_class&action=modify&cid={$item->brand_id}" title="修改" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                            </div>
                        </a>

                        {if $item->tistrue=='1'}
                            <font class="ml-5" style="color:red;" title="已删除"><i class="Hui-iconfont">&#xe6e2;</i></font>
                        {else}
                            <a style="text-decoration:none" class="ml-5" href="javascript:void(0)" onclick="confirm('确定要删除此商品品牌吗?','{$item->brand_id}')">
                            	<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
	                                <img src="images/icon1/del.png"/>&nbsp;删除
	                            	</div>
	                            </div>
                            </a>
                        {/if}
                    </td>
                </tr>
            </form>
            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


{literal}
<script type="text/javascript">


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
}
function closeMask(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=brand_class&action=del&cid="+id,
    	async:true,
    	success:function(res){
    		if(res==1){
    			appendMask("删除成功","cg");
            }else if(res==2){
    			appendMask("该品牌正在使用，不允删除","ts");
            }else{
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
}
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
        url:"index.php?module=brand_class&action=status&id="+id,
        async:true,
        success:function(res){
            if(content=="启用"){
                if(res==1){
                    appendMask("启用成功","cg");
                }else{
                    appendMask("启用失败","ts");
                }
            }else{
                if(res==1){
                    appendMask("禁用成功","cg");
                }else if(res==2){
                    appendMask("该品牌正在使用，不允禁用","ts");
                }else{
                    appendMask("禁用失败","ts");
                }
            }
        }
    });
}
</script>
{/literal}
</body>
</html>