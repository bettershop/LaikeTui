<?php /* Smarty version 2.6.26, created on 2021-04-04 16:28:44
         compiled from view.tpl */ ?>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

<?php echo '

</script>
'; ?>

<title>用户信息详情</title>
<?php echo '
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
'; ?>

</head>
<body>


<nav class="breadcrumb">
    用户管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=userlist">用户列表</a> <span class="c-gray en">&gt;</span>  
    用户信息详情 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
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
                    	<img src="<?php echo $this->_tpl_vars['user'][0]->headimgurl; ?>
" style="width: 60px;height:60px;border-radius: 30px;border: 1px solid darkgray;"/>
                    </td>
                </tr>
                <tr>
                    <th width="100" class="text-r"> 用户名：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->user_name; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 性别：</th>
                    <td>
                    	<?php if ($this->_tpl_vars['user'][0]->sex == '1'): ?>
                    	<span>男</span>
                    	<?php elseif ($this->_tpl_vars['user'][0]->sex == '2'): ?>
                    	<span>女</span>
                    	<?php else: ?>
                    	<span>未知</span>
                    	<?php endif; ?>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 手机：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->mobile; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">邮箱：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->e_mail; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">地址：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->detailed_address; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">账号：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->Account; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">分享次数：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->share_num; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">余额：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->money; ?>
</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">注册时间：</th>
                    <td>
                    	<span><?php echo $this->_tpl_vars['user'][0]->Register_data; ?>
</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>


</body>
</html>