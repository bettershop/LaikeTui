<?php /* Smarty version 2.6.26, created on 2021-01-28 15:54:49
         compiled from index.tpl */ ?>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

	<?php echo '
	<style type="text/css">

		.tab_content {
		    background-color: white;
		    text-align: center;
		}

		#delorderdiv {
			margin-left: 20px;
			display: inline;
			color: red;
		}

		.order-item {
			border: 1px solid transparent;
			margin-bottom: 1rem;
		}

		.order-item table {
			margin: 0;
		}

		.order-item:hover {
			border: 1px solid #3c8ee5;
		}

		.goods-item {
			margin-bottom: .75rem;
		}

		.goods-item:last-child {
			margin-bottom: 0;
		}

		.btn-success:focus,
		.btn-success:active,
		.btn-success.active {
			background-color: #2890FF !important;
		}

		.btn-success:hover,
		.btn-success:focus,
		.btn-success:active,
		.btn-success.active {
			border: 0 !important;
		}

		.goods-name {
			white-space: nowrap;
			text-overflow: ellipsis;
			overflow: hidden;
		}

		.status-item.active {
			color: inherit;
		}

		.badge {
			display: inline-block;
			padding: .25em .4em;
			font-size: 75%;
			font-weight: 700;
			line-height: 1;
			color: #fff;
			text-align: center;
			white-space: nowrap;
			vertical-align: baseline;
			border-radius: .25rem
		}

		.badge:empty {
			display: none
		}

		.btn .badge {
			position: relative;
			top: -1px
		}

		a.badge:focus,
		a.badge:hover {
			color: #fff;
			text-decoration: none;
			cursor: pointer
		}

		.badge-pill {
			padding-right: .6em;
			padding-left: .6em;
			border-radius: 10rem
		}

		.badge-default {
			background-color: #636c72
		}

		.badge-default[href]:focus,
		.badge-default[href]:hover {
			background-color: #4b5257
		}

		.badge-primary {
			background-color: #0275d8
		}

		.badge-primary[href]:focus,
		.badge-primary[href]:hover {
			background-color: #025aa5
		}

		.badge-success {
			background-color: #5cb85c
		}

		.badge-success[href]:focus,
		.badge-success[href]:hover {
			background-color: #449d44
		}

		.badge-info {
			background-color: #5bc0de
		}

		.badge-info[href]:focus,
		.badge-info[href]:hover {
			background-color: #31b0d5
		}

		.badge-warning {
			background-color: #f0ad4e
		}

		.badge-warning[href]:focus,
		.badge-warning[href]:hover {
			background-color: #ec971f
		}

		.badge-danger {
			background-color: #d9534f
		}

		.badge-danger[href]:focus,
		.badge-danger[href]:hover {
			background-color: #c9302c
		}

		.mask {
			position: absolute;
			top: 0;
			bottom: 0;
			left: 0;
			right: 0;

		}

		a:hover {
			color: red;
			text-decoration: none;
		}

		.table-bordered th,
		.table-bordered td {
			border: none;
			text-align: center;
			vertical-align: middle;
		}

		.txc th {
			text-align: center;
		}

		.imgTd img {
			width: 50px;
			height: 50px;
			margin-right: 10px;
		}

		.maskLeft {
			width: 30%;
			float: left;
			text-align: right;
			padding-right: 20px;
			height: 36px;
			line-height: 36px;
		}

		.maskRight {
			width: 70%;
			float: left;
		}

		.ipt1 {
			padding-left: 10px;
			width: 300px;
			height: 36px;
			border: 1px solid #eee;
		}

		.wl_maskNew {
			position: fixed;
			z-index: 9999999;
			top: 0;
			bottom: 0;
			left: 0;
			right: 0;
			background: rgba(0, 0, 0, 0.6);
			display: block;
		}

		.wl_maskNewContent {
			width: 920px;
			height: 600px;
			background: rgba(255, 255, 255, 1);
			border-radius: 4px;
			margin: 0 auto;
			position: relative;
			top: 10%;
		}

		.dc {
			position: fixed;
			z-index: 999;
			top: 0;
			bottom: 0;
			left: 0;
			right: 0;
			background: rgba(0, 0, 0, 0.6);
			display: block;
		}

		.table-bordered td {
			border-bottom: 1px solid #eee !important;
		}

		.table {
			border-collapse: collapse;
		}

		form[name=form1] {
			/*background: #f7f7f7;*/
			padding: 10px;
			text-align: left;
			height: 36 !important;
		}

		input::-webkit-input-placeholder {
			/* placeholder颜色  */
			color: #cccccc;
			/* placeholder字体大小  */
			font-size: 14px;
			/* placeholder位置  */
			text-align: left;
		}

		option {
			/* placeholder颜色  */
			color: #cccccc;
			/* placeholder字体大小  */
			font-size: 14px;
			/* placeholder位置  */
			text-align: center;
		}

		.seach_test {
			font-family: \'MicrosoftYaHei\', \'Microsoft YaHei\';
			font-weight: 400;
			font-style: normal;
			font-size: 12px;
			color: #666666;
			text-align: right;
			line-height: 18px;
		}

		.butten_o {
			width: 69px;
			height: 28px;
			background: inherit;
			background-color: rgba(153, 153, 153, 1);
			border: none;
			border-radius: 4px;
			-moz-box-shadow: none;
			-webkit-box-shadow: none;
			box-shadow: none;
			font-family: \'MicrosoftYaHei\', \'Microsoft YaHei\';
			font-weight: 400;
			font-style: normal;
			font-size: 14px;
			color: #FFFFFF;
			text-align: center;
			line-height: 21px;
		}

		.seach_span {
			position: relative;
		}

		#u2386_img {
			position: absolute;
			right: 10px;
			top: 5px;
			width: 14px;
			height: 14px;
		}

		.txc {
			background: #fff;
		}

		.custom-control {
			position: relative;
			display: -webkit-inline-box;
			display: -webkit-inline-flex;
			display: -ms-inline-flexbox;
			display: inline-flex;
			min-height: 1.5rem;
			padding-left: 1.5rem;
			margin-right: 1rem;
			cursor: pointer;
			float: left;
		}

		.goods-item {
			padding: 10px;
			margin-top: -1px;
		}

		.goods-item:last-child {
			margin-bottom: 0;
		}

		.goods-pic {
			width: 5.5rem;
			height: 5.5rem;
			display: inline-block;
			background-color: #ddd;
			background-size: cover;
			background-position: center;
			margin-right: 1rem;
		}

		.ml-ttt {
			margin-left: 20px;
		}

		.goods-info {
			text-align: left;
		}

		.fs-0 {
			margin-left: 35px;
		}

		.ttt_tr {
			margin-top: 10px;
		}

		span {
			color: #818181;
		}

		th {
			color: #818181;
		}

		.u2523 {
			font-family: \'PingFangSC-Semibold\', \'PingFang SC Semibold\', \'PingFang SC\';
			font-weight: 650;
			font-style: normal;
			font-size: 14px;
			color: #414658;
		}

		#u2554 {
			font-size: 13px;
			color: #818181;
		}

		.ax_label {
			font-size: 14px;
			text-align: left;
		}

		.ax_default {
			font-family: \'ArialMT\', \'Arial\';
			font-weight: 400;
			font-style: normal;
			font-size: 13px;
			color: #333333;
			text-align: left;
			line-height: normal;
		}

		.caozuo_b:hover {
			color: #38b4ed;
		}

		label {
			display: inline-block;
			margin-bottom: 0;
		}

		.tml-5 {
			margin: 5px 0;
		}

		p {
			margin: 0;
		}

		.wlmk_div {
			display: flex;
			justify-content: flex-start;
		}

		.wlmk_box {
			text-align: right;
			visibility: visible;
			color: #818181;
		}

		.wlmk_box_2 {
			text-align: left;
		}

		.btn-success:hover {
			background: #2481e5;
		}

		.fs-0 {
			display: flex;
			align-items: center;
		}

		form .select {
			height: 100%;
		}

		form[name=form1] input {
			height: 100%;
		}

		.f9e {
			color: #888F9E;
		}

		#btn8:hover {
			border: 1px solid #2890FF !important;
			color: #2890FF !important;
		}

		.nmor {
			border: 0;
			border-radius: 4px;
			background-color: #2890FF !important;
			color: white !important;
		}

		.nmor:hover {
			background-color: #2481e5 !important;
		}

		.tab_dat a {
			width: 88px !important;
			color: #888f9e !important;
			cursor: pointer;
		}

		.tab_dat a img {
			margin-top: -3px;
		}

		.float_right {
			float: right;
		}

		.unable {
			color: #d6dce9 !important;
			border-color: #E9ECEF !important;

		}

		.stopCss:hover {
			cursor: not-allowed;
		}

		.stopCss {
			width: 88px;
			height: 20px;
			border: 1px solid #e9ecef;
			color: #d8dbe8 !important;
			font-size: 12px;
			border-radius: 2px;
			line-height: 20px;
			display: inline-block;
			margin-left: -2%;
			margin-bottom: 8px;
		}

		.textColor {
			color: #414658;
		}

		.tab_dat .hover_a:hover {
			color: rgb(40, 144, 255) !important;
		}

		.tab_news label {
			margin-left: 10px !important;
		}

		.tab_tb_news label {
			padding-left: 10px !important;
		}

		.custom-control {
			padding-left: 0px !important;
		}

		.custom-control-indicator {
			margin-top: 0px !important;
		}
	</style>

	'; ?>

	<title>订单列表</title>
</head>

<body>


<nav class="breadcrumb">
    订单管理 <span class="c-gray en">&gt;</span> 
    订单列表 
</nav>

	<div class="pd-20">
		<div class="text-c">
			<form name="form1" id="form1" action="index.php" method="get">

				<input type="hidden" name="module" value="orderslist" />
				<input type="hidden" name="having" value="123" />
				<input type="hidden" name="ordtype" value="<?php echo $this->_tpl_vars['otype']; ?>
" />
				<input type="hidden" name="gcode" value="<?php echo $this->_tpl_vars['status']; ?>
" />
				<input type="hidden" name="ocode" value="<?php echo $this->_tpl_vars['ostatus']; ?>
" />
				<div>
					<input type="text" name="sNo" id="sNo_" size='8' value="<?php echo $this->_tpl_vars['sNo']; ?>
" placeholder="请输入订单编号/姓名/电话/会员ID"
						style="width:230px;height: 31px;" class="input-text seach_bottom">

					<select name="otype" class="select seach_bottom" id="otype_"
						style="width: 130px;font-size: 14px;height: 31px;color: #cccccc;vertical-align: middle;">
						<option value="">请选择订单类型</option>

						<?php $_from = $this->_tpl_vars['ordtype']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['key'] => $this->_tpl_vars['item']):
?>
						<option value="<?php echo $this->_tpl_vars['key']; ?>
" <?php if ($this->_tpl_vars['otype'] == $this->_tpl_vars['key']): ?>selected<?php endif; ?>><?php echo $this->_tpl_vars['item']; ?>
 </option> <?php endforeach; endif; unset($_from); ?> </select> <select
							name="status" class="select seach_bottom" id="status_"
							style="width: 130px;font-size: 14px;height: 31px;color: #cccccc;vertical-align: middle;">
						<option value="">请选择订单状态</option>
						<?php echo $this->_tpl_vars['class']; ?>

					</select>

					<input type="text" class="input-text seach_bottom" value="<?php echo $this->_tpl_vars['startdate']; ?>
" placeholder="请输入开始时间" id="startdate"
						name="startdate" style="width:150px;">
					<span style='display: inline-block;height: 36px;'>
						<span style='display: flex;align-items:center;'>
							至
						</span>
					</span>
					<input type="text" class="input-text seach_bottom" value="<?php echo $this->_tpl_vars['enddate']; ?>
" placeholder="请输入结束时间" id="enddate"
						name="enddate" style="width:150px;margin-left:5px;">

					<input class="btn btn-success" id="btn1" type="submit" value="查询">
					<input type="button" value="重置" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076; height: 31px;"
						class="reset" onclick="empty()" />

					<input type="button" value="导出" id="btn2" style="margin-right: 0px;float: right;" class="btn btn-success"
						onclick="excel(location.href)">
					<input type="button" value="批量删除" id="btn2" style="margin-right: 8px;float: right;width: 80px!important;"
						class="btn btn-success" onclick="del_orders()">
				</div>
			</form>



		</div>
		<div class="mt-20 table-scroll" style="overflow: scroll; width: 101%; height: 495px;">
			<table class="table table-bordered tab_content">
				<thead>
					<tr class="txc tab_tr">

						<th class="tab_news">
							<label class="custom-control custom-checkbox">
								<input name="orders_all" value="all" type="checkbox" class="custom-control-input orders_all">
								<span class="custom-control-indicator"></span>
							</label>
							订单信息
						</th>

						<th >订单总计</th>
						<th >数量</th>
						<th s>订单状态</th>
						<?php if ($this->_tpl_vars['otype'] == 't2'): ?>
						<th >拼团状态</th>
						<?php endif; ?>
						<th >订单类型</th>

						<th >买家信息</th>
						<th >支付方式</th>
						<th >物流信息</th>
						<th class="tab_dat">操作</th>
					</tr>

				</thead>
				<tbody>
					<?php $_from = $this->_tpl_vars['order']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f1'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f1']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['item']):
        $this->_foreach['f1']['iteration']++;
?>

					<tr class="tab_head">
						<td colspan="<?php if ($this->_tpl_vars['otype'] == 't2'): ?>10<?php else: ?>9<?php endif; ?>" class="tab_tb_news">
							<label class="custom-control custom-checkbox">

								<input name="orders[]" value="<?php echo $this->_tpl_vars['item']->id; ?>
" type="checkbox" class="custom-control-input orders_select"
									title="<?php echo $this->_tpl_vars['item']->status; ?>
">
								<span class="custom-control-indicator"></span>

								<span class="custom-control-description">
									<span class="ml-ttt f9e">订单编号：<?php echo $this->_tpl_vars['item']->sNo; ?>
</span>
									<span class="ml-ttt f9e">创单时间：<?php echo $this->_tpl_vars['item']->add_time; ?>
</span>
								</span>

							</label>
						</td>
					</tr>

					<tr class="tab_td">
						<!-- 订单信息 -->
						<td class="tab_news">

							<?php $_from = $this->_tpl_vars['item']->products; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f2'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f2']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['key2'] => $this->_tpl_vars['item2']):
        $this->_foreach['f2']['iteration']++;
?>
							<!-- 只显示一个 -->
							<?php if ($this->_tpl_vars['key2'] < 1): ?> <div class="goods-item" flex="dir:left box:first">

								<div class="fs-0 f9e" style="float:left">
									<div class="goods-pic" style="background-image: url('<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['item2']->imgurl; ?>
')"></div>
								</div>

								<div class="goods-info">
									<div class="goods-name u2523">
										<?php echo $this->_tpl_vars['item2']->product_title; ?>

									</div>
									<div class="mt-1">
										<span class="fs-sm f9e">
											规格：
											<span class="text-danger">
												<span class="mr-3 c658 textColor">
													<?php echo $this->_tpl_vars['item2']->size; ?>

												</span>
											</span>
										</span>
									</div>

									<div class="mt-1">
										<span class="fs-sm f9e">
											数量：
											<span class="text-danger  textColor">
												<?php echo $this->_tpl_vars['item2']->num; ?>
件
											</span>
										</span>
									</div>

									<div>
										<span class="fs-sm f9e">
											小计：
											<span class="text-danger mr-4  textColor">
												<?php echo $this->_tpl_vars['item2']->num*$this->_tpl_vars['item2']->p_price; ?>
元
											</span>
										</span>
									</div>

								</div>
		</div>
		<?php endif; ?>
		<?php endforeach; endif; unset($_from); ?>
		</td>

		<td>
			<div>&yen;<?php echo $this->_tpl_vars['item']->z_price; ?>
</div>
		</td>

		<td>
			<div><?php echo $this->_tpl_vars['item']->num; ?>
</div>
		</td>

		<!-- 订单状态 -->
		<td>
			<div class="text" style="visibility: visible;">
				<p>
					<span style="font-size: 18px;font-family:'Helvetica';color:<?php echo $this->_tpl_vars['item']->bgcolor; ?>
">●</span><span
						style="font-family:'MicrosoftYaHei', 'Microsoft YaHei';color:#414658;"> </span><span
						style="font-family:'MicrosoftYaHei', 'Microsoft YaHei';color:#888f9e;"><?php echo $this->_tpl_vars['item']->status; ?>
</span>
				</p>
			</div>
		</td>

		<?php if ($this->_tpl_vars['otype'] == 't2'): ?>
		<td>
			<div>
				<span class='f9e'><?php echo $this->_tpl_vars['item']->pt_status; ?>
</span>
			</div>
		</td>
		<?php endif; ?>

		<!-- 订单类型 -->
		<td>
			<div>
				<span>
					<?php if ($this->_tpl_vars['item']->otype == 'pt'): ?>
					拼团订单
					<?php else: ?>
					普通订单
					<?php endif; ?>
				</span>
			</div>
		</td>

		<!-- 买家信息 -->
		<td>
			<div id="u2554" class="ax_default ax_label">
				<div id="u2554_div" class=""></div>
				<div id="u2555" class="text" style="visibility: visible;">

					<p>
						<span class='f9e' style="display: inline-block;width: 60px;">会员ID：</span>
						<span class="textColor"><?php if ($this->_tpl_vars['item']->user_id): ?><?php echo $this->_tpl_vars['item']->user_id; ?>
<?php else: ?>暂无<?php endif; ?></span>
					</p>

					<p>
						<span class='f9e' style="display: inline-block;width: 60px;">联系人：</span>
						<span class="textColor"><?php if ($this->_tpl_vars['item']->name): ?><?php echo $this->_tpl_vars['item']->name; ?>
<?php else: ?>暂无<?php endif; ?></span>
					</p>

					<p class="tab_nowrap ">
						<span class="f9e" style="display: inline-block;width: 60px;">电 &nbsp;&nbsp;&nbsp;话：</span>
						<span class="textColor"><?php if ($this->_tpl_vars['item']->mobile): ?><?php echo $this->_tpl_vars['item']->mobile; ?>
<?php else: ?>暂无<?php endif; ?></span>
					</p>

					<p>
						<span class="f9e" style="display: inline-block;width: 60px;">地 &nbsp;&nbsp;&nbsp;址：</span>
						<span class="textColor"><?php if ($this->_tpl_vars['item']->address): ?><?php echo $this->_tpl_vars['item']->address; ?>
<?php else: ?>暂无<?php endif; ?></span>
					</p>

				</div>
			</div>
		</td>

		<!-- 支付方式 -->
		<td>
			<div>
				<span>
					<?php if ($this->_tpl_vars['item']->pay == 'wxPay'): ?>
					微信支付
					<?php elseif ($this->_tpl_vars['item']->pay == 'wallet_Pay'): ?>
					余额支付
					<?php elseif ($this->_tpl_vars['item']->pay == 'consumer_pay'): ?>
					消费金支付
					<?php else: ?>组合支付<?php endif; ?>
				</span>
			</div>
		</td>

		<!-- 物流信息 -->
		<td>
			<div class="wlmk_div">

				<div class="wlmk_box" style="width: 100px;">

					<?php if (! empty ( $this->_tpl_vars['item']->courier_num )): ?>
					<?php $_from = $this->_tpl_vars['item']->courier_num; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f3'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f3']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['key3'] => $this->_tpl_vars['item3']):
        $this->_foreach['f3']['iteration']++;
?>
					<?php if (! empty ( $this->_tpl_vars['item3']['courier_num'] )): ?>
					<div id="wl" class="f9e">物流单号 <?php echo $this->_tpl_vars['key3']+1; ?>
: </div>
					<?php endif; ?>
					<?php endforeach; endif; unset($_from); ?>
					<?php else: ?>
					<div class="f9e">物流单号：</div>
					<?php endif; ?>

					<div class="f9e">运费：</div>
				</div>

				<div class="wlmk_box_2">
					<?php if (! empty ( $this->_tpl_vars['item']->courier_num )): ?>
					<?php $_from = $this->_tpl_vars['item']->courier_num; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f3'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f3']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['key3'] => $this->_tpl_vars['item3']):
        $this->_foreach['f3']['iteration']++;
?>
					<?php if (! empty ( $this->_tpl_vars['item3']['courier_num'] )): ?>

					<div class="goods-name doods-span" style="width:200px;">
						<span><?php echo $this->_tpl_vars['item3']['courier_num']; ?>
(<?php echo $this->_tpl_vars['item3']['kuaidi_name']; ?>
)</span>
						<span class="vieworder" onclick="send_btn1(this,'<?php echo $this->_tpl_vars['item']->sNo; ?>
','<?php echo $this->_tpl_vars['item3']['courier_num']; ?>
',true)"
							style="display: none">查看物流</span>
					</div>

					<?php endif; ?>
					<?php endforeach; endif; unset($_from); ?>
					<?php else: ?>
					<div>暂无</div>
					<?php endif; ?>

					<div><?php if ($this->_tpl_vars['item']->freight): ?>￥<?php echo $this->_tpl_vars['item']->freight; ?>
<?php else: ?>免邮<?php endif; ?></div>
				</div>
			</div>
		</td>

		<!-- 操作 -->
		<td class="tab_dat">

			<a class="hover_a" onclick="navto('index.php?module=orderslist&action=Detail&id=<?php echo $this->_tpl_vars['item']->id; ?>
')" title="订单详情">
				<img src="images/icon1/ck.png" />&nbsp;订单详情
			</a>


			<?php if ($this->_tpl_vars['item']->statu <= 3): ?> <a class="hover_a"
				onclick="navto('index.php?module=orderslist&action=Modify&id=<?php echo $this->_tpl_vars['item']->id; ?>
&type=updata')" title="编辑订单">
				<img src="images/icon1/xg.png" />&nbsp;编辑订单
				</a>
				<?php else: ?>
				<div class="stopCss">
					<img style="margin-top: -3px;" src="images/icon1/xg.png" />&nbsp;编辑订单
				</div>
				<?php endif; ?>

				<?php if ($this->_tpl_vars['item']->statu == 1): ?>
				<a class="hover_a" onclick=send_btn(this,'<?php echo $this->_tpl_vars['item']->otype; ?>
','<?php echo $this->_tpl_vars['item']->sNo; ?>
',<?php echo $this->_tpl_vars['item']->statu; ?>
)>
					<img src="images/icon1/ck.png" />&nbsp;发货
				</a>
				<?php elseif (! empty ( $this->_tpl_vars['item']->courier_num[0]['courier_num'] )): ?>
				<a class="hover_a" onclick="send_btn1(this,'<?php echo $this->_tpl_vars['item']->sNo; ?>
','<?php echo $this->_tpl_vars['item']->products[0]->courier_num; ?>
')">
					<img src="images/iIcon/wul.png" />&nbsp;查看物流
				</a>
				<?php endif; ?>

				<?php if ($this->_tpl_vars['item']->statu == 0): ?>
				<a class="hover_a" onclick="colse('<?php echo $this->_tpl_vars['item']->sNo; ?>
')">
					<img src="images/iIcon/chaaG.png" />&nbsp;关闭订单
				</a>
				<?php endif; ?>

		</td>
		</tr>
		<tr class="page_h16">
			<td colspan="<?php if ($this->_tpl_vars['otype'] == 't2'): ?>10<?php else: ?>9<?php endif; ?>"></td>
		</tr>
		<?php endforeach; endif; unset($_from); ?>
		</tbody>
		</table>
	</div>
	<div style="text-align: center;display: flex;justify-content: center;"><?php echo $this->_tpl_vars['pages_show']; ?>
</div>

	</div>

	<div class="dc" style="display:none;">
		<div class="maskNewContent">
			<div class="maskTitle ">
				添加快递信息
			</div>
			<a href="javascript:void(0);" class="closeA qx" style="display: block;"><img src="images/icon1/gb.png" /></a>
			<form action="" method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
				<div id="tab-category" class="HuiTab">
					<div class="" style="margin-top: 35px;">
						<div class="">
							<input type="hidden" name="sNo" value="" class="order_id">
							<input type="hidden" name="otype" value="<?php echo $this->_tpl_vars['otype']; ?>
" class="otype">
							<input type="hidden" name="trade" value="3">
							<label class="maskLeft" style="">快递公司：</label>
							<div class="formControls maskRight" style="width: 60%;float: left;">
								<form name="hh" action="" method="post">
									<span class="search">
										<input class="ww ipt1" id="makeInput" autocomplete="off" onkeyup="setContent(this,event);"
											onfocus="setDemo(this,event)" type="text" placeholder="请选择或输入快递名称">
										<select name="kuaidi" class="selectName" id="hh"
											style="display: none; position: absolute;z-index:99;width: 153px;margin-top: 1px;margin-left: 0px;"
											onkeyup="getfocus(this,event)" onclick="choose(this)" size="10" id="num1">
											<?php $_from = $this->_tpl_vars['express']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f1'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f1']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['item']):
        $this->_foreach['f1']['iteration']++;
?>
											<option value="<?php echo $this->_tpl_vars['item']->id; ?>
"><?php echo $this->_tpl_vars['item']->kuaidi_name; ?>
</option>
											<?php endforeach; endif; unset($_from); ?>
										</select>
									</span>
								</form>
							</div>
							<div class="clearfix"></div>
							<div class="col-3">
							</div>
						</div>
						<div class="">
							<label class="maskLeft" style="">快递单号：</label>
							<div class="maskRight" style="">
								<input type="text" class="ipt1" value="" name="danhao" placeholder="请输入正确的快递单号" />
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<div class="row cl">
					<div class="col-9 " style="margin-left:40%">
						<input class="qd closeMask" type="submit" value="提交">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="maskNew dd" style="display: none;">
		<div class="maskNewContent">
			<div class="maskTitle ">
				查看物流信息
			</div>
			<a href="javascript:void(0);" class="closeA qx"><img src="images/icon1/gb.png" /></a>
			<div class="page-container" style="margin: 0 auto;">
				<?php if ($this->_tpl_vars['res'] == 1): ?>
				<p style=" text-align: center;height: 100%;">未查询到物流信息</p>
				<?php else: ?>
				<?php $_from = $this->_tpl_vars['res']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }if (count($_from)):
    foreach ($_from as $this->_tpl_vars['key'] => $this->_tpl_vars['item']):
?>
				<div class='time' style="    margin-left: 30px;"><?php echo $this->_tpl_vars['item']->time; ?>
</div>
				<div class='step' style="font-size: 0.5rem; padding: 5px 20px;    margin-left: 30px;"><?php echo $this->_tpl_vars['item']->context; ?>
</div>
				<?php endforeach; endif; unset($_from); ?>
				<?php endif; ?>

			</div>
			<div style="text-align:center;margin-top:30px">
				<button class="closeMask" onclick=hm()>确认</button>
			</div>
		</div>
	</div>

	<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>

	<script language="javascript" src="style/js/ssd1.js"> </script>


	<?php echo '
	<script type="text/javascript">

		$(".orders_all").bind("click",
            function () {
                $(".orders_select").prop("checked", $(this).prop("checked"));
            });
        $(".orders_select").bind("click",
            function () {
                var $sel = $(".orders_select");
                var b = true;
                for (var i = 0; i < $sel.length; i++) {
                    if ($sel[i].checked == false) {
                        b = false;
                        break;
                    }
                }
                $(".orders_all").prop("checked", b);
        })
        


		// 删除订单
		var deIsOpn = 0
		var conStr = \'\'
		function del_orders() {

			var $sel = $(".orders_select");
			var b = true;
			var con_str = \'\'
			for (var i = 0; i < $sel.length; i++) {
				if ($sel[i].checked == true) {
					if ($sel[i].title === \'拼团中\') {
						parent.appendMask2(\'存在拼团中的订单不能删除\')
						deIsOpn = 0
						return
					}
					con_str += $sel[i].value + \',\';
				}
			}

			if (con_str.length) {
				deIsOpn = 1
				conStr = con_str
				parent.appendMask2(\'确认删除此所选订单？此操作不可恢复！\')

			} else {
				parent.appendMask2(\'请选择需要删除的订单\')
			}
		}


		function deletes(){
            deleteSS();
        }

        function deleteSS() {
            if (deIsOpn === 1) {
                $.ajax({
                    url: \'index.php?module=orderslist&action=delorder&ids=\' + conStr,
                    type: "post",
                    data: {},
                    success: function (res) {
                        location.replace(location.href);
                    }
                })
            } else if (deIsOpn === 2) {
                openS()
            }
        }

		function excel(url) {
			export_popup(url);
		}

		function export_popup(url) {
			var res = `<div class="pup_div" id="pup_div">
                        <div class="pup_flex">
                            <div class="pup_auto">
                                <div class="pup_head"><span>导出数据</span>
                                    <img src="images/icon/cha.png" onclick="export_close(\'${url}\',\'\')">
                                </div>
                                
                                <div class="pup_dc">
                                    <div class="pup_dcv" onclick="export_close(\'${url}\',\'This_page\')">
                                        <div>
                                            <img src="images/iIcon/scby.png" />
                                            <p>导出本页</p>
                                        </div>
                                    </div>
                                    <div class="pup_dcv" onclick="export_close(\'${url}\',\'whole\')">
                                        <div>
                                            <img src="images/iIcon/dcqb.png" />
                                            <p>导出全部</p>
                                        </div>
                                    </div>
                                    <div class="pup_dcv" onclick="export_close(\'${url}\',\'inquiry\')"> 
                                        <div>
                                            <img src="images/iIcon/dcss.png" />
                                            <p>导出查询</p>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>`;
			$("body").append(res);
		}

		function export_close(url, type) {
			$("#pup_div").remove();
			location.href = location.href + \'&pageto=\' + type;
		}


		//实现全选与反选
		var ids = [];
		$("#allAndNotAll").click(function () {
			if (this.checked) {
				$("input[name=checkid]").each(function (index) {
					$(this).prop("checked", true);
					var val = $(this).val();
					arrModify(ids, val, 1);
				});
			} else {
				$("input[name=checkid]").each(function (index) {
					$(this).prop("checked", false);
					var val = $(this).val();
					arrModify(ids, val, 2);
				});
			}

		});


		// 关闭订单
		function openS() {
			$.ajax({
				url: \'index.php?module=orderslist&action=addsign&m=close&sNo=\' + colsEsno,
				type: "post",
				data: {},
				success: function (res) {
					location.replace(location.href);
				}
			})
		}

		function closeMask1() {
			$(".maskNew").remove();
			if (deIsOpn === 1) {
                deleteSS()
			} else if (deIsOpn === 2) {
				openS()
			}
		}



		function closeMask4() {
			$(".maskNew1").remove();
			location.replace(location.href);
		}
		function confirm(content, id) {
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

        function navto(URI) {
            var parma = $(\'#form1\').serializeArray()
            var urllist = []
            parma.forEach(function (item) {
                if (item.name === \'sNo\') {
                    item.name = item.name + 1
                }
                urllist.push(item.name + \'=\' + item.value)
            })
            parma = "&" + urllist.join(\'&\') + \'&page=\' + $(\'.active\').children(\'a\').text()
            location.href = URI + parma
        }


        function arrModify(arr, val, type) { //１为增加元素  2为删除元素
            if (type == 1) {
                var index = $.inArray(val, arr);
                if (index === -1) arr.push(val);
            } else if (type == 2) {
                arr.remove(val);
            }
            return arr;
        }


        function send_btn(obj, otype, id, status, sNo) {
            var dingdan = id;
            var stu = status;
            $(\'.order_id\').val(id);
            $(\'.otype\').val(otype ? otype : \'yb\');

            if (stu == 6) {
                appenMask1(\'订单已关闭，不能发货!\', "ts");
            }
            if (stu >= 2 && stu != 6) {
                appenMask1(\'订单已发货，请勿重复操作!\', "ts");
            }

            if (stu == 0) {
                appenMask1(\'订单还没付款!\', "ts");
            }

            if (stu == 1) {
                location.href = \'index.php?module=orderslist&action=addsign&sNo=\' + dingdan
            }

        };

        function send_btn1(obj, id, courier_num, is = false) {

            var r_sNo = id;

            $.ajax({
                url: \'index.php?module=orderslist&action=kuaidishow&r_sNo=\' + r_sNo + \'&courier_num=\' + courier_num,
                type: "post",
                success: function (res) {
                    var data = JSON.parse(res);

                    if (!data[0].data.length) {
                        appendMask(\'暂无物流信息！\', "ts");
                        return
                    }

                    if (is) {
                        d = []
                        for (var item of data) {
                            if (item.courier_num === courier_num) {
                                d.push(item)
                            }
                        }
                        data = d
                    }

                    if (data.length) {
                        closeMask1();
                        var str = \'\';
                        var title = \'\'

                        function getnr(data) {
                            for (var aaa of data) {

                                str += `


									<li>
										<i style="color:rgba(151,160,180,1);font-size:14px;font-style: initial;">${aaa.time}</i>
										<i style="color:rgba(65,70,88,1);font-size:14px;font-style: initial;">${aaa.context}</i>
									</li>


								`


                            }

                            return str
                        }

                        for (var item of data) {
                            title = `

						<div class="row">

							<div class="col-2" style="text-align: end;color: rgba(65,70,88,1);font-size: 14px;">物流公司：</div>
							<div class="col-9" style="color:rgba(65,70,88,1);font-size: 14px;">${ item.kuaidi_name}</div>
						</div>

						<div class="row">
							<div class="col-2" style="text-align: end;color: rgba(65,70,88,1);font-size: 14px;">运单号码：</div>
							<div class="col-9" style="color:rgba(65,70,88,1);font-size: 14px;">${ item.courier_num}</div>
						</div>

						<div class="row">
							<div class="col-2" style="text-align: end;color: rgba(65,70,88,1);font-size: 14px;">物流跟踪：</div>
							<div class="col-9">
									<ul>
							${

                                getnr(item.data)

                                }
							</ul>
							</div>
						</div>
						`
                        }

                        wl_appendMask(title, "cg");
                    } else {
                        appendMask(\'暂无物流信息！\', "ts");
                    }
                },
            });
        };



        function appendMask(content, src) {
            $("body").append(`
				<div class="maskNew ">
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


        function wl_appendMask(content, src) {
            $("body").append(`
				<div class="wl_maskNew">

					<div class="wl_maskNewContent">
						<a href="javascript:void(0);" class="closeA" onclick=close_wl_Mask1() ><img src="images/icon1/gb.png"/></a>
						<div class="maskTitle" style="display: block;font-size:16px;font-weight:bold;">物流信息</div>
						<div style="height: 470px;position: relative;top:20px;font-size: 22px;overflow: scroll;">
							${content}
						</div>
						<div style="text-align:center;margin-top:30px">
					<button class="closeMask" onclick=close_wl_Mask1() >确认</button>
				</div>
					</div>
				</div>


			`)
        }

        function close_wl_Mask1() {
            $(".wl_maskNew").remove();
        }




	</script>
	'; ?>

</body>

</html>