<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>系统管理</title>
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
    系统管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=keyword">热门关键词</a>
</nav>

<div class="pd-20">
    <div style="clear:both;">
        <a href="index.php?module=keyword&action=Add"><input type="button" class="btn btn-primary radius" value="添加关键词" /></a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>关键词</th>
                    <th style="width:140px">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$res item=res}
                <tr class="text-c">
                    <td>{$res.id}</td>
                    <td>{$res.keyword}</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=keyword&action=Modify&id={$res.id}" title="修改" >
                       		<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
            				</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=keyword&action=Del&id={$res.id}" onclick="return confirm('确定删除吗？')">
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


</body>
</html>

