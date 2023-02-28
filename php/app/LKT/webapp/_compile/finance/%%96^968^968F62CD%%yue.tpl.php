<?php /* Smarty version 2.6.26, created on 2021-02-15 13:08:30
         compiled from yue.tpl */ ?>
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
    余额列表 
</nav>


<div class="pd-20">
	<div class="text-c">
		<form name="form1" action="index.php?module=finance&action=yue" method="get">
			<input type="hidden" name="module" value="finance" />
			<input type="hidden" name="action" value="yue" />
			<input type="hidden" name="pagesize" value="<?php echo $this->_tpl_vars['pagesize']; ?>
" id="pagesize" />
			<div>
				<select name="otype" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
					<option value="all" <?php if ($this->_tpl_vars['type'] == 'all'): ?> selected<?php endif; ?>>全部</option>
					<option value="1" <?php if ($this->_tpl_vars['type'] == '1'): ?> selected<?php endif; ?>>用户充值</option>
					<option value="2" <?php if ($this->_tpl_vars['type'] == '2'): ?>selected <?php endif; ?>>申请提现</option>
					<option value="4" <?php if ($this->_tpl_vars['type'] == '4'): ?> selected<?php endif; ?>>余额消费</option>
					<option value="5" <?php if ($this->_tpl_vars['type'] == '5'): ?> selected<?php endif; ?>>退款</option>
					<option value="7" <?php if ($this->_tpl_vars['type'] == '7'): ?> selected<?php endif; ?>>佣金</option>
					<option value="11" <?php if ($this->_tpl_vars['type'] == '11'): ?> selected<?php endif; ?>>系统扣款</option>
					<option value="12" <?php if ($this->_tpl_vars['type'] == '12'): ?> selected<?php endif; ?>>给好友转余额</option>
					<option value="13" <?php if ($this->_tpl_vars['type'] == '13'): ?> selected<?php endif; ?>>转入余额</option>
					<option value="14" <?php if ($this->_tpl_vars['type'] == '14'): ?> selected<?php endif; ?>>系统充值</option>
					<option value="19" <?php if ($this->_tpl_vars['type'] == '19'): ?> selected<?php endif; ?>>消费金解封</option>
					<option value="20" <?php if ($this->_tpl_vars['type'] == '20'): ?> selected<?php endif; ?>>抽奖中奖</option>
					<option value="21" <?php if ($this->_tpl_vars['type'] == '21'): ?> selected<?php endif; ?>>提现成功</option>
					<option value="22" <?php if ($this->_tpl_vars['type'] == '22'): ?> selected<?php endif; ?>>提现失败</option>
					<option value="23" <?php if ($this->_tpl_vars['type'] == '23'): ?> selected<?php endif; ?>>取消订单</option>
				</select>
				<input type="text" class="input-text" style="width:150px" placeholder="用户ID" autocomplete="off" name="name" value="<?php echo $this->_tpl_vars['name']; ?>
">

				<div style="position: relative;display: inline-block;">
				<input name="startdate" id="startdate" placeholder="请输入开始时间" value="<?php echo $this->_tpl_vars['startdate']; ?>
" size="8" readonly class="scinput_s iptRl" style="" />
				</div>
				至
				<div style="position: relative;display: inline-block;">
				<input  name="enddate" id="enddate" value="<?php echo $this->_tpl_vars['enddate']; ?>
" placeholder="请输入结束时间" size="8" readonly class="scinput_s iptRl" style="" />
				
				</div>
				<input type="submit" class="btn btn-success" id="btn1" value="查 询">
				&nbsp;
				<input type="button" value="导出" id="btn2" class="btn btn-success" onclick="excel('all')">
			</div>
		</form>
    </div>

	<div class="mt-20" style="background: #fff;">
		<table class="table table-border table-bordered table-bg table-hover">
			<thead>
				<tr class="text-c">
					<th width="150" aria-valuetext="user_id">用户ID</th>
					<th width="130" aria-valuetext="user_name">用户名</th>
					<th width="150" aria-valuetext="money">金额</th>
					<th width="130" aria-valuetext="source">来源</th>
					<th width="150" aria-valuetext="add_date">时间</th>
					<th width="150" aria-valuetext="type">类型</th>
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
							<?php if ($this->_tpl_vars['item']->type == 1 || $this->_tpl_vars['item']->type == 5 || $this->_tpl_vars['item']->type == 13 || $this->_tpl_vars['item']->type == 14 || $this->_tpl_vars['item']->type == 19 || $this->_tpl_vars['item']->type == 20 || $this->_tpl_vars['item']->type == 22 || $this->_tpl_vars['item']->type == 23): ?>+<?php echo $this->_tpl_vars['item']->money; ?>
<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 2 || $this->_tpl_vars['item']->type == 4 || $this->_tpl_vars['item']->type == 11 || $this->_tpl_vars['item']->type == 12 || $this->_tpl_vars['item']->type == 21): ?>-<?php echo $this->_tpl_vars['item']->money; ?>
<?php endif; ?>
						</td>
						<td><?php if ($this->_tpl_vars['item']->source == 1): ?>小程序<?php elseif ($this->_tpl_vars['item']->source == 2): ?>app<?php endif; ?></td>
						<td><?php echo $this->_tpl_vars['item']->add_date; ?>
</td>
						<td>
							<?php if ($this->_tpl_vars['item']->type == 1): ?>用户充值<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 2): ?>申请提现<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 4): ?>余额消费<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 5): ?>退款<?php endif; ?>
														<?php if ($this->_tpl_vars['item']->type == 11): ?>系统扣款<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 12): ?>给好友转余额<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 13): ?>转入余额<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 14): ?>系统充值<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 19): ?>消费金解封<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 20): ?>抽奖中奖<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 21): ?>提现成功<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 22): ?>提现失败<?php endif; ?>
							<?php if ($this->_tpl_vars['item']->type == 23): ?>取消订单<?php endif; ?>
						</td>
	                    <td style="min-width:120px;">
							<a style="text-decoration:none" class="ml-5" href="index.php?module=finance&action=yue_see&user_id=<?php echo $this->_tpl_vars['item']->user_id; ?>
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

<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

<?php echo '
<script type="text/javascript">
function excel(pageto) {
	var pagesize = $("#pagesize").val();
	location.href=location.href+\'&pageto=\'+pageto+\'&pagesize=\'+pagesize;
}

laydate.render({
          elem: \'#startdate\', //指定元素
           trigger: \'click\',
          type: \'date\',

        });
       
        laydate.render({
          elem: \'#enddate\',
          trigger: \'click\',
          type: \'date\'
        });

</script>
'; ?>

</body>
</html>

