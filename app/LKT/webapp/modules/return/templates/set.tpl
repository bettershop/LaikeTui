<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
{literal}
<style>
    .button-conter {
        display: flex;
        justify-content: center;
    }
    
    #btn1 {
        margin-right: 5px;
    }
</style>
{/literal}
<title>系统参数</title>

</head>

<body>
<nav class="breadcrumb">
    配置管理 <span class="c-gray en">&gt;</span> 
    售后地址设置
</nav>


<div class="page-container">
    <form name="form1" action="index.php?module=return&action=set" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>联系人：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input style="width:200px" type="text" name="name" autocomplete="off" value="{$list->name}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>联系电话：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input style="width:200px" type="text" name="tel" autocomplete="off" value="{$list->tel}" class="input-text">
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>详细地址：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="address" autocomplete="off" value="{$list->address}" class="input-text">
                </div>
            </div>

        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"></label>
            <div class="formControls col-xs-8 col-sm-6">
                <button id="btn1" class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <!-- <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;清空&nbsp;&nbsp;</button> -->
            </div>
        </div>
    </form>
</div>
</div>
</body>
</html>