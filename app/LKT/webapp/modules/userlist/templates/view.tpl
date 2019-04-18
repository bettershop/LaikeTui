
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
<link href="style/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<link href="style/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
{literal}
<script type="text/javascript">
function check(f){
    if(Trim(f.news_title.value) == "" ){
        alert('文章标题不能为空！');
        return false;
    }
}
</script>
{/literal}
<title>用户信息详情</title>
{literal}
<style type="text/css">
	table th{
		border: none;
		font-weight: normal!important;
		color: #888f9e;
		font-size: 14px;
	}
	.table th{
		padding: 15px 20px;
	}
	table{
		background-color: #fff;
		border-bottom-left-radius: 10px;
    	border-bottom-right-radius: 10px;
	}
	.ulTitle {
    height: 50px;
    line-height: 50px;
    text-align: left;
    padding-left: 20px;
    font-size: 16px;
    color: #414658;
    font-weight: 600;
    font-family: "微软雅黑";
    margin-bottom: 0px;
    border-bottom: 2px solid #eee;
    background: #fff;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}
.table td{
	border: none;
}
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb" style="margin-top: 20px;"><i class="Hui-iconfont">&#xe705;</i> 用户管理 
	<span class="c-gray en">&gt;</span> 用户列表管理 <span class="c-gray en">&gt;</span> 用户信息详情 
</nav>
<div class="pd-20"style="padding-top: 0px;">
    <div class="Huiform">
    	<div class="ulTitle">
    		用户信息详情
    	</div>
        <table class="table table-bg" border="1">
            <tbody>
                <tr>
                    <th class="text-r">头像：</th>
                    <td>
                    	<img src="{$user[0]->headimgurl}" style="width: 60px;height:60px;border-radius: 30px;border: 1px solid darkgray;"/>
                    </td>
                </tr>
                <tr>
                    <th width="100" class="text-r"> 用户名：</th>
                    <td>
                    	<span>{$user[0]->user_name}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 性别：</th>
                    <td>
                    	{if $user[0]->sex == '1'}
                    	<span>男</span>
                    	{elseif $user[0]->sex == '2'}
                    	<span>女</span>
                    	{else}
                    	<span>未知</span>
                    	{/if}
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 手机：</th>
                    <td>
                    	<span>{$user[0]->mobile}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">邮箱：</th>
                    <td>
                    	<span>{$user[0]->e_mail}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">地址：</th>
                    <td>
                    	<span>{$user[0]->detailed_address}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">账号：</th>
                    <td>
                    	<span>{$user[0]->Account}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">分享次数：</th>
                    <td>
                    	<span>{$user[0]->share_num}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">余额：</th>
                    <td>
                    	<span>{$user[0]->money}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">注册时间：</th>
                    <td>
                    	<span>{$user[0]->Register_data}</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="modpub/js/check.js" > </script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="style/lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="style/lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script>
<script type="text/javascript" src="style/js/H-ui.admin.js"></script> 

</body>
</html>