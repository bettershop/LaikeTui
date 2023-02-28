<?php /* Smarty version 2.6.26, created on 2021-02-15 13:08:31
         compiled from jifen.tpl */ ?>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

<title>积分列表</title>
<?php echo '
<style>
td a{
	width: 44%;
	margin: 0 auto!important;
}
.dataTables_wrapper .dataTables_length{
	bottom: 13px;
}
#btn1:hover{
	background-color: #2481e5!important;
}
#btn1{
	height: 36px;
	line-height: 36px;
}
#btn2:hover{
	background-color: #0fa675!important;
}
#btn2{
	background-color: #17ba8e!important;
	height: 36px;
	line-height: 36px;
}
</style>
'; ?>

</head>

<body>

<nav class="breadcrumb">
    财务管理 <span class="c-gray en">&gt;</span> 
    积分列表 
</nav>


<div class="pd-20">
	<div class="text-c">
		<form name="form1" action="index.php?module=finance&action=jifen" method="get">
			<input type="hidden" name="module" value="finance" />
			<input type="hidden" name="action" value="jifen" />
			<input type="hidden" name="pagesize" value="<?php echo $this->_tpl_vars['pagesize']; ?>
" id="pagesize" />
			<div>
				<select name="otype" class="select" style="width: 200px;height: 31px;vertical-align: middle;">
					<option value="all" <?php if ($this->_tpl_vars['type'] == 'all'): ?> selected<?php endif; ?>>请选择要查询的类型</option>
					<option value="0" <?php if ($this->_tpl_vars['type'] == '0'): ?> selected<?php endif; ?>>签到</option>
					<option value="1" <?php if ($this->_tpl_vars['type'] == '1'): ?> selected<?php endif; ?>>消费</option>
					<option value="2" <?php if ($this->_tpl_vars['type'] == '2'): ?> selected<?php endif; ?>>关注</option>
					<option value="3" <?php if ($this->_tpl_vars['type'] == '3'): ?> selected<?php endif; ?>>转积分给好友</option>
					<option value="4" <?php if ($this->_tpl_vars['type'] == '4'): ?> selected<?php endif; ?>>好友转积分</option>
					<option value="5" <?php if ($this->_tpl_vars['type'] == '5'): ?> selected<?php endif; ?>>系统扣除</option>
					<option value="6" <?php if ($this->_tpl_vars['type'] == '6'): ?> selected<?php endif; ?>>系统充值</option>
					<option value="7" <?php if ($this->_tpl_vars['type'] == '7'): ?> selected<?php endif; ?>>抽奖</option>
				</select>
				<input type="text" class="input-text" style="width:250px" placeholder="用户名/用户ID" autocomplete="off" name="user_name" value="<?php echo $this->_tpl_vars['user_name']; ?>
">
				<input type="text" class="input-text" style="width:250px" placeholder="手机号" autocomplete="off" name="mobile" value="<?php echo $this->_tpl_vars['mobile']; ?>
">

																																				<input type="submit" class="btn btn-success" id="btn1" value="查 询">
				<input type="button" value="导出" class="btn btn-success" id="btn2" onclick="excel('all')">
			</div>
		</form>
	</div>

	<div class="mt-20" style="background: #fff;">
		<table class="table table-border table-bordered table-bg table-hover">
			<thead>
				<tr class="text-c">
					<th width="150" aria-valuetext="user_id">用户ID</th>
					<th width="130" aria-valuetext="user_name">用户名</th>
					<th width="150" aria-valuetext="sign_score">充值积分</th>
					<th width="130" aria-valuetext="source">来源</th>
					<th width="150" aria-valuetext="sign_time">时间</th>
					<th width="150" aria-valuetext="type">充值方式</th>
					<th width="100">操作</th>
				</tr>
			</thead>
            <tbody>
	            <?php $_from = $this->_tpl_vars['list']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f1'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f1']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['item']):
        $this->_foreach['f1']['iteration']++;
?>
	                <tr class="text-c">
	                    <td><?php echo $this->_tpl_vars['item']->user_id; ?>
</td>
	                    <td><?php echo $this->_tpl_vars['item']->user_name; ?>
</td>
	         			
	                    <td>
							<?php if ($this->_tpl_vars['item']->type == 0 || $this->_tpl_vars['item']->type == 2 || $this->_tpl_vars['item']->type == 4 || $this->_tpl_vars['item']->type == 6 || $this->_tpl_vars['item']->type == 7): ?>+<?php echo $this->_tpl_vars['item']->sign_score; ?>
<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 1 || $this->_tpl_vars['item']->type == 3 || $this->_tpl_vars['item']->type == 5): ?>-<?php echo $this->_tpl_vars['item']->sign_score; ?>
<?php endif; ?>
	         			</td>
						<td><?php if ($this->_tpl_vars['item']->source == 1): ?>小程序<?php elseif ($this->_tpl_vars['item']->source == 2): ?>APP<?php endif; ?></td>
						<td><?php echo $this->_tpl_vars['item']->sign_time; ?>
</td>
	                    <td>
	                    	<?php if ($this->_tpl_vars['item']->type == 0): ?>签到领积分<?php endif; ?>
	                    	<?php if ($this->_tpl_vars['item']->type == 1): ?>消费积分<?php endif; ?>
	                    	<?php if ($this->_tpl_vars['item']->type == 2): ?>首次关注得积分<?php endif; ?>
	                    	<?php if ($this->_tpl_vars['item']->type == 3): ?>转积分给好友<?php endif; ?>
	                    	<?php if ($this->_tpl_vars['item']->type == 4): ?>好友转积分<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 5): ?>系统扣除<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 6): ?>系统充值<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 7): ?>抽奖<?php endif; ?>
	                    </td>
	                    <td style="min-width:120px;">
							<a style="text-decoration:none" class="ml-5" href="index.php?module=finance&action=jifen_see&user_id=<?php echo $this->_tpl_vars['item']->user_id; ?>
" title="查看">
								<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;">
	                                <img src="images/icon1/ck.png"/>&nbsp;查看
	                            	</div>
                        		</div>
							</a>
	                    </td>
	                </tr>
	            <?php endforeach; endif; unset($_from); ?>
            </tbody>
        </table>
    </div>
	<div style="text-align: center;display: flex;justify-content: center;"><?php echo $this->_tpl_vars['pages_show']; ?>
</div>
</div>

<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>


<?php echo '
<script type="text/javascript">
function excel(pageto) {
	var pagesize = $("#pagesize").val();
	location.href=location.href+\'&pageto=\'+pageto+\'&pagesize=\'+pagesize;
}
</script>
'; ?>

</body>

</html>