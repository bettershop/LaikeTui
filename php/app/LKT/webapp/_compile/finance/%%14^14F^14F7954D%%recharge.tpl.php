<?php /* Smarty version 2.6.26, created on 2021-02-15 13:08:28
         compiled from recharge.tpl */ ?>

<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />

<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

	<title>财务管理</title>
</head>
<body>

<nav class="breadcrumb">
    财务管理 <span class="c-gray en">&gt;</span> 
    充值列表 
</nav>


<div class="pd-20">
	<div class="text-c">
		<form name="form1" action="index.php?module=finance&action=recharge" method="get">
			<input type="hidden" name="module" value="finance" />
			<input type="hidden" name="action" value="recharge" />
			<input type="hidden" name="pagesize" value="<?php echo $this->_tpl_vars['pagesize']; ?>
" id="pagesize" />
			<input type="text" class="input-text" style="width:250px" placeholder="用户ID" autocomplete="off" name="zhanghao" value="<?php echo $this->_tpl_vars['zhanghao']; ?>
">
			<input type="text" class="input-text" style="width:250px" placeholder="手机号" autocomplete="off" name="mobile" value="<?php echo $this->_tpl_vars['mobile']; ?>
">
			<input type="text" class="input-text" style="width:250px" placeholder="昵称" autocomplete="off" name="user_name" value="<?php echo $this->_tpl_vars['user_name']; ?>
">
			<input type="submit" class="btn btn-success" value="查 询">
			<input type="button" value="导出" class="btn btn-success" onclick="excel('all')">
		</form>
	</div>

	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover">
			<thead>
			<tr class="text-c">
				<th width="150">用户ID</th>
				<th width="150">用户昵称</th>
				<th width="130">操作金额</th>
				<th width="50">来源</th>
				<th width="150">添加时间</th>
				<th width="100">类型</th>
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
					<td><?php echo $this->_tpl_vars['item']->money; ?>
</td>
					<td><?php if ($this->_tpl_vars['item']->source == 1): ?>小程序<?php elseif ($this->_tpl_vars['item']->source == 2): ?>app<?php endif; ?></td>
					<td><?php echo $this->_tpl_vars['item']->add_date; ?>
</td>
					<td>
						<?php if ($this->_tpl_vars['item']->type == 1): ?>充值<?php endif; ?>
						<?php if ($this->_tpl_vars['item']->type == 14): ?>系统充值<?php endif; ?>
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

