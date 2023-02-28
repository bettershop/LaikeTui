
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>插件管理</title>
{literal}
<style>
   	td a{
        width: 44%;
        margin: 2%!important;
        float: left;
    }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=plug_ins">插件列表</a>
</nav>

<div class="pd-20">
    <div style="clear:both;">
        <button class="btn newBtn radius" value="添加插件" onclick="location.href='index.php?module=plug_ins&action=add';">
        	<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
                <img src="images/icon1/add.png"/>&nbsp;添加插件
           	</div>
        </button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>图片</th>
                    <th>插件名称</th>
                    <th>类型</th>
                    <th>软件名</th>
                    <th>发布时间</th>
                    <th>状态</th>
                    <th style="width: 170px;">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    {if $item->image == ''}
                    <td><image class='pimg' src="{$uploadImg}nopic.jpg" style="width: 48px;height:48px;"/></td>
                    {else}
                    <td><image class='pimg' src="{$uploadImg}{$item->image}" style="width: 48px;height:48px;"/></td>
                    {/if}
                    <td>{$item->name}</td>
                    <td>{if $item->type == 0}<span>小程序</span>{else}<span>app</span>{/if}</td>
                    <td>{$item->software_name}</td>
                    <td>{$item->add_time}</td>
                    <td>{if $item->status == 0}<span style="color: #fff;background: #EE2C2C;width:20px;border-radius: 10px;padding: 0 10px;">未启用</span>{elseif $item->status == 1}<span style="color: #fff;width:20px;background:#3CB371;border-radius: 10px;padding: 0 10px;">已启用</span>{/if}</td>
                    <td style="width: 170px;">
                        {if $item->status == 0}
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" title="启用" onclick="confirm1('确定要启用该插件?',{$item->id},'启用')">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/qy.png"/>&nbsp;启用
                            	</div>
                    		</div>
                        </a>
                        {elseif $item->status ==1}
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" title="禁用" onclick="confirm1('确定要禁用该插件?',{$item->id},'禁用')">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/jy.png"/>&nbsp;禁用
                            	</div>
                    		</div>
                        </a>
                        {/if}
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=plug_ins&action=modify&id={$item->id}&uploadImg={$uploadImg}&http={$item->http}" title="修改" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
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


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}
<script type="text/javascript" src="style/js/bootstrap.min.js"></script>
{literal}
<script type="text/javascript">


/*系统-栏目-添加*/
function system_category_add(title,url,w,h){
  layer_show(title,url,w,h);
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
									<button class="closeMask" onclick=closeMask1()>取消</button>
								</div>
							</div>
						</div>	
					`)
			}
			function closeMask(id){
				$(".maskNew").remove();
			    $.ajax({
			    	type:"post",
			    	url:"index.php?module=plug_ins&action=del&id="+id,
			    	async:true,
			    	success:function(res){
			    		console.log(res);
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

			function appendMask(content,src){
				$("body").append(`
						<div class="maskNew">
							<div class="maskNewContent" style="height:300px">
								<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
								<div class="maskTitle">提示</div>	
								<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
								<div style="height: 100px;position: relative;top:20px;font-size: 22px;text-align: center;">
									${content}
								</div>
								<div style="text-align:center;">
									<button class="closeMask" onclick=closeMask1() >确认</button>
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
			    	url:"index.php?module=plug_ins&action=whether&id="+id,
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
</script>
{/literal}
</body>
</html>