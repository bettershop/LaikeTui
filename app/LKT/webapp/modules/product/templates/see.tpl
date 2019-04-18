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

<title>产品分类管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 产品列表管理 <span class="c-gray en">&gt;</span> 产品属性 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="index.php?module=product&action={$url}" title="关闭"><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <h1 style="text-align: center;">{$product_title}</h1>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    {foreach from=$attribute_key item=item name=f1}
                        {if $smarty.foreach.f1.first}
                            <input type="hidden" >
                        {else}
                            <th style='text-align: center;'>{$item}</th>
                        {/if}
                    {/foreach}
                </tr>
            </thead>
            <tbody>
            {foreach from=$attribute_value item=item1 key=k name=f2}
                <tr class='num' id='tr_{$k+1}'>
                    {foreach from=$item1 item=item2 name=f3 key=k3}
                        {if $smarty.foreach.f3.last}
                            <td style="text-align: center;">
                                <image src='{$uploadImg}{$item2}' style='height:50px;width:50px;' />
                            </td>
                        {else}
                            {if $smarty.foreach.f3.first}
                                <input type="hidden" name="rid" id="rid_{$k+1}" value="{$item2}">
                            {else}
                                <td style="text-align: center;">{$item2}</td>
                            {/if}
                        {/if}
                    {/foreach}
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
</div>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div> 
<script type="text/javascript" src="style/js/jquery.js"></script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

</body>
</html>