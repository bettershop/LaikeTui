define(['bootstrap'], function($){
	var trade = {};
	var reg_credit = /^[+-]?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/i;
	var reg_int = /^[0-9]\d*$/i;
	trade.init = function() {
		$('.modal-trade-credit1, .modal-trade-credit2, .modal-trade-consume, .modal-trade-card, .modal-trade-cardsn').on('click', function(){
			$('#consume-Modal, #credit-Modal, #card-Modal, #card-edit-Modal, #group-Modal').remove();
			var type = $(this).data('type');
			var uid = parseInt($(this).data('uid'));
			if(type == 'consume') {
				trade.consume(uid);
			} else if(type == 'credit1' || type == 'credit2') {
				trade.credit(type, uid);
			} else if(type == 'card') {
				trade.card(uid);
			} else if(type == 'cardsn') {
				trade.card_edit(uid);
			}
		});
	};

	trade.consume = function(uid) {
		var html = '<div class="modal fade" id="consume-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
			'	<div class="modal-dialog modal-lg" role="document">'+
			'		<div class="modal-content">'+
			'			<form class="table-responsive form-inline" method="post" action="" id="form-consume">'+
			'				<div class="modal-header">'+
			'					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
			'					<h4 class="modal-title" id="myModalLabel">会员消费</h4>'+
			'				</div>'+
			'				<div class="modal-body">'+
			'					<table class="table table-hover table-bordered">'+
			'						<tr>'+
			'							<th width="150">'+
			'								<select name="type" id="type" class="form-control">'+
			'									<option value="mobile">手机号</option>'+
			'									<option value="uid">会员UID</option>'+
			'								</select>'+
			'							</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<input type="text" value="" name="username" id="username" class="form-control"/>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr id="user" style="display: none">'+
			'							<th>会员信息</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<strong class="form-control-static"></strong>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>消费总计</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<input type="text" value="" name="total" id="total" class="form-control"/>'+
			'										<span class="input-group-addon">元</span>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>实收金额</th>'+
			'							<td>'+
			'								<div class="input-group">'+
			'									<input type="text" value="" name="money" id="money" class="form-control" readonly/>'+
			'									<span class="input-group-addon">元</span>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th rowspan="3">支付方式</th>'+
			'							<td>'+
			'								<label class="checkbox-inline">'+
			'									<input type="checkbox" name="is_credit2_pay" id="is_credit2_pay" value="1" /> 余额支付'+
			'								</label>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<input type="text" value="0" name="credit2" id="credit2" disabled class="form-control"/>'+
			'										<span class="input-group-addon">元</span>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<td>'+
			'								<label class="checkbox-inline">'+
			'									<input type="checkbox" name="is_credit1_pay" id="is_credit1_pay" value="1" /> 积分抵现'+
			'								</label>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<input type="text" value="0" name="credit1" id="credit1" disabled class="form-control"/>'+
			'										<span class="input-group-addon">积分　抵消</span>'+
			'										<input type="text" value="0" name="offset_money" id="offset_money" disabled class="form-control"/>'+
			'										<span class="input-group-addon">元</span>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<td>'+
			'								<label class="checkbox-inline">'+
			'									<input type="checkbox" value="1" name="is_cash_pay" id="is_cash_pay" /> 现金支付'+
			'								</label>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<input type="text" value="0" name="cash" id="cash" disabled class="form-control"/>'+
			'										<span class="input-group-addon">元　　找零</span>'+
			'										<input type="text" value="0" name="return_cash" id="return_cash" disabled class="form-control"/>'+
			'										<span class="input-group-addon">元</span>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>备注</th>'+
			'							<td>'+
			'								<textarea name="remark" class="form-control" cols="81"></textarea>'+
			'							</td>'+
			'						</tr>'+
			'					</table>'+
			'				</div>'+
			'				<div class="modal-footer">'+
			'					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
			'					<input type="button" class="btn btn-primary" id="submit-form" value="提交">'+
			'				</div>'+
			'			</form>'+
			'		</div>'+
			'	</div>'+
			'</div>';
		require(['validator'], function($){
			$('#consume-Modal').remove();
			$(document.body).append(html);
			var dialog = $('#consume-Modal');
			dialog.modal('show');

			$('#username').focus();
			$('#form-consume').bootstrapValidator({
				fields: {
					username: {
						validators: {
							notEmpty: {
								message: '请填写用户手机或UID'
							}
						}
					},
					total: {
						validators: {
							notEmpty: {
								message: '请填写消费总计'
							},
							regexp: {
								regexp: reg_credit,
								message: '最多只保留小数点后2位的正数'
							}
						}
					},
					credit2: {
						validators: {
							notEmpty: {
								message: '请填写余额支付金额'
							},
							regexp: {
								regexp: reg_credit,
								message: '最多只保留小数点后2位的正数'
							},
							between: {
								min: 0,
								max: 100000000,
								money: 0,
								message : '超出可用余额'
							}
						}
					},
					credit1: {
						validators: {
							notEmpty: {
								message: '请填写积分数量'
							},
							regexp: {
								regexp: reg_int,
								message: '积分数量只能是正整数'
							},
							between: {
								min: 0,
								max: 1000000000,
								message : '超出会员所拥有的积分或超出最多可抵消金额'
							}
						}
					},
					cash: {
						validators: {
							notEmpty: {
								message: '请填写现金支付金额'
							},
							regexp: {
								regexp: reg_credit,
								message: '最多只保留小数点后2位的正数'
							}
						}
					}
				}
			});

			var Validator = $('#form-consume').data('bootstrapValidator');
			var consume = {
				'user': {},
				'card': {},
				'total': 0,
				'money': 0,
				'last_money': 0,
				'return_cash':0,
				'is_credit1_pay': false,
				'is_credit2_pay': false,
				'is_cash_pay': false,
				'credit1': 0,
				'credit2': 0,
				'cash': 0,
				'offset_money': 0,
				'password': '',

				'init': function(uid) {
					Validator.enableFieldValidators('credit2');
					Validator.enableFieldValidators('credit1');
					Validator.enableFieldValidators('cash');
					this.getuser();
					if(uid > 0) {
						$('#type').val('uid');
						$('#username').val(uid);
						$('#username').trigger('blur');
						$('#username, #type').attr('disabled', true);
					}
					this.initmoney();
					this.initconsume();
					this.submit();
				},

				'checkuser': function() {
					if(this.user.uid == undefined || !this.user.uid) {
						this.user = {};
						this.card = {};
						$('#username').focus();
						Validator.updateMessage('username', 'notEmpty', '未找到对应会员');
						Validator.updateStatus('username', 'INVALID', 'notEmpty');
						//util.message('未找到对应会员');
						return false;
					}
					return true;
				},

				'getuser': function(){
					var _this = this;
					$('#username').blur(function(){
						Validator.validateField('username');
						var username = $.trim($('#username').val());
						var type = $('#type').val();
						if(username) {
							$.post('./index.php?c=mc&a=trade&do=user&', {'type':type, 'username':username}, function(data){
								var data = $.parseJSON(data);
								if(data.error != 'none') {
									_this.user = {};
									_this.card = {};
									$('#user').hide();
									Validator.updateMessage('username', 'notEmpty', data.message);
									Validator.updateStatus('username', 'INVALID', 'notEmpty');
									return false;
								} else {
									_this.user = data.user;
									_this.card = data.card;
									$('#user strong').html(data.html);
									$('#user').show();
								}
							});
						}
					});
				},

				'initmoney': function() {
					var _this = this;
					$('#total').blur(function(){
						if(!_this.checkuser()) {
							return false;
						}

						var total = parseFloat($(this).val());
						_this.total = total;
						money = total;
						if(_this.user.discount && total >= _this.user.discount.condition) {
							if(_this.card.discount_type == 1) {
								money = total - _this.user.discount.discount;
							} else {
								if(!_this.user.discount.discount) {
									_this.user.discount.discount = 1;
								}
								money = total * _this.user.discount.discount;
							}
							if(money < 0) {
								money = 0;
							}
						}
						$('#money').val(money);
						_this.money = money;
						_this.last_money = money;
					});
				},

				'initcredit1': function() {
					var _this = this;
					$('#credit1').keyup(function(){
						if(!_this.checkuser()) {
							return false;
						}
						_this.credit1 = parseInt($(this).val());

						if(isNaN(_this.credit1)) {
							_this.credit1 = 0;
						}
						if(_this.credit1 >= 0 && _this.card.offset_rate > 0 && _this.card.offset_max > 0) {
							$('#offset_money').val(_this.credit1 / _this.card.offset_rate);
							_this.offset_money = _this.credit1/_this.card.offset_rate;
						}
						var credit1 = parseInt($(this).val());
						if(_this.user.credit1 < credit1) {
							Validator.updateOption('credit1', 'between', 'max', _this.user.credit1);
							Validator.updateMessage('credit1', 'between', '超出会员账户可用积分');
							Validator.updateStatus('credit1', 'INVALID', 'between');
						} else if(_this.card && (_this.card.offset_rate > 0) && (_this.card.offset_max > 0) && (credit1 > (_this.card.offset_rate * _this.card.offset_max))) {
							Validator.updateOption('credit1', 'between', 'max', _this.card.offset_rate * _this.card.offset_max);
							Validator.updateMessage('credit1', 'between', '积分最多可抵消'+_this.card.offset_max);
							Validator.updateStatus('credit1', 'INVALID', 'between');
						}
						_this.updatereturn();
					});
				},

				'initcredit2': function() {
					var _this = this;
					$('#credit2').keyup(function(){
						if(!_this.checkuser()) {
							return false;
						}

						var money = $('#money').val();
						if(money > 0) {
							var credit2 = parseFloat($('#credit2').val());
							if(_this.user.credit2 < credit2) {
								Validator.updateOption('credit2', 'between', 'max', _this.user.credit2);
								Validator.updateMessage('credit2', 'between', '超出会员账户可用余额');
								Validator.updateStatus('credit2', 'INVALID', 'between');
							} else if(credit2 > money) {
								Validator.updateOption('credit2', 'between', 'max', money);
								Validator.updateMessage('credit2', 'between', '使用余额不能大于应付金额');
								Validator.updateStatus('credit2', 'INVALID', 'between');
							}
							_this.credit2 = credit2;
							_this.updatereturn();
						}
					});
				},

				'initcash': function() {
					var _this = this;
					$('#cash').keyup(function(){
						if(!_this.checkuser()) {
							return false;
						}

						var money = $('#money').val();
						if(money > 0) {
							var cash = parseFloat($('#cash').val());
							_this.cash = cash;
							_this.updatereturn();
						}
					});
				},

				'updatereturn': function() {
					var _this = this;
					_this.return_cash = _this.money - _this.credit2 - _this.offset_money - _this.cash;
					if(_this.return_cash > 0) {
						_this.last_money = _this.return_cash;
						$('#return_cash').val(0);
					} else {
						_this.last_money = 0;
						$('#return_cash').val(Math.abs(_this.return_cash));
					}
				},

				'updatecredit1': function(){
					var _this = this;
					if(_this.card.offset_rate > 0 && _this.card.offset_max > 0 && _this.last_money > 0) {
						var min = Math.min.apply(null, [_this.user.credit1, _this.card.offset_rate * _this.card.offset_max, _this.card.offset_rate * _this.last_money]);
						$('#credit1').val(min).focus().select();
						$('#offset_money').val(min/_this.card.offset_rate);
						_this.credit1 = min;
						_this.offset_money = min/_this.card.offset_rate;
					}
				},

				'updatecredit2': function(){
					var _this = this;
					var min = Math.min(_this.user.credit2, _this.last_money);
					$('#credit2').val(min);
					_this.credit2 = min;
				},

				'updatecash': function(){
					var _this = this;
					$('#cash').val(_this.last_money);
					_this.cash = _this.last_money;
				},

				'initconsume': function() {
					var _this = this;
					_this.is_credit1_pay = $('#is_credit1_pay').prop('checked');
					_this.is_credit2_pay = $('#is_credit2_pay').prop('checked');
					_this.is_cash_pay = $('#is_cash_pay').prop('checked');
					_this.credit1 = parseInt($('#credit1').val());
					_this.credit2 = parseFloat($('#credit2').val());
					_this.cash = parseFloat($('#cash').val());

					_this.initcredit1();
					_this.initcredit2();
					_this.initcash();

					$('#is_credit1_pay').click(function(){
						if(!_this.checkuser()) {
							return false;
						}
						if(!_this.money) {
							Validator.updateStatus('total', 'INVALID', 'regexp');
							$('#total').focus().select();
							return false;
						}
						if(!$(this).prop('checked')) {
							//如果是非选中状态
							Validator.updateStatus('credit1', 'VALID');
							$('#credit1').attr('disabled', true);
							Validator.enableFieldValidators('credit1', false);
							$('#credit1').val(0);
							$('#offset_money').val(0);
							$(this).prop('checked', false);
							_this.is_credit1_pay = false;
							_this.credit1 = 0;
							_this.offset_money = 0;
						} else if(_this.last_money >= 0) {
							if(_this.last_money == 0) {
								return false;
							}
							_this.is_credit1_pay = true;
							$('#credit1').removeAttr('disabled');
							Validator.enableFieldValidators('credit1', true);
							_this.updatecredit1();
						}
						_this.updatereturn();
					});

					$('#is_credit2_pay').click(function(){
						if(!_this.checkuser()) {
							return false;
						}
						if(!_this.money) {
							Validator.updateStatus('total', 'INVALID', 'regexp');
							$('#total').focus().select();
							return false;
						}

						if(!$(this).prop('checked')) {
							//如果是非选中状态
							Validator.updateStatus('credit2', 'VALID');
							$('#credit2').attr('disabled', true);
							Validator.enableFieldValidators('credit2', false);
							$('#credit2').val(0);
							$(this).prop('checked', false);
							_this.is_credit2_pay = false;
							_this.credit2 = 0;
						} else if(_this.last_money >= 0) {
							if(_this.last_money == 0) {
								return false;
							}

							$('#credit2').removeAttr('disabled');
							Validator.enableFieldValidators('credit2', true);
							_this.is_credit2_pay = true;
							_this.updatecredit2();
						}
						_this.updatereturn();
					});

					$('#is_cash_pay').click(function(){
						if(!_this.checkuser()) {
							return false;
						}
						if(!_this.money) {
							Validator.updateStatus('total', 'INVALID', 'regexp');
							$('#total').focus().select();
							return false;
						}

						if(!$(this).prop('checked')) {
							//如果是非选中状态
							Validator.updateStatus('cash', 'VALID');
							$('#cash').attr('disabled', true);
							Validator.enableFieldValidators('cash', false);
							$('#cash').val(0);
							$(this).prop('checked', false);
							_this.is_cash_pay = false;
							_this.cash = 0;
						} else if(_this.last_money >= 0) {
							if(_this.last_money == 0) {
								return false;
							}

							$('#cash').removeAttr('disabled');
							Validator.enableFieldValidators('cash', true);
							_this.is_cash_pay = true;
							_this.updatecash();
						}
						_this.updatereturn();
					});
				},

				'submit': function() {
					var _this = this;
					_this.updatereturn();

					$('#form-consume #submit-form').click(function(){
						Validator.validate();
						if(Validator.isValid()) {
							if(_this.last_money > 0) {
								util.message('支付金额小于实收金额，请检查表单');
								return false;
							}
							_this.password = $.trim($(':input[name="password"]').val());
							_this.remark = $.trim($('textarea[name="remark"]').val());
							var param = {
								'uid': _this.user.uid,
								'total': _this.total,
								'money': _this.money,
								'credit2': _this.credit2,
								'credit1': _this.credit1,
								'cash': _this.cash,
								'offset_money': _this.offset_money,
								'return_cash': _this.return_cash,
								'password': _this.password,
								'remark': _this.remark
							}
							$.post('./index.php?c=mc&a=trade&do=consume', param, function(data){
								if(data != 'success') {
									util.message(data, '', 'error');
									return false;
								} else {
									dialog.modal('hide');
									util.message('交易成功', 'refresh', 'success');
									return false;
								}
							});
						}
					});
				}
			};
			consume.init(uid);
		});
	};

	trade.credit = function(type, uid) {
		var types = {'credit1': '积分', 'credit2': '余额'};
		var html = '<div class="modal fade" id="credit-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
			'	<div class="modal-dialog modal-lg" role="document">'+
			'		<div class="modal-content">'+
			'			<form class="table-responsive form-inline" method="post" action="" id="form-credit">'+
			'				<div class="modal-header">'+
			'					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
			'					<h4 class="modal-title" id="myModalLabel">会员'+types[type]+'操作</h4>'+
			'				</div>'+
			'				<div class="modal-body">'+
			'					<table class="table table-hover table-bordered">'+
			'						<tr>'+
			'							<th width="150">'+
			'								<select name="type" id="type" class="form-control">'+
			'									<option value="mobile">手机号</option>'+
			'									<option value="uid">会员UID</option>'+
			'								</select>'+
			'							</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<input type="text" value="" name="username" id="username" class="form-control"/>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr id="user" style="display: none">'+
			'							<th>会员信息</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<strong class="form-control-static"></strong>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>修改'+types[type]+'(增减)</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<input type="text" value="" name="num" id="total" class="form-control"/>'+
			'									<div class="help-block">输入500,则标识增加500;输入-500则表示减少500</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>备注</th>'+
			'							<td>'+
			'								<textarea name="remark" class="form-control" cols="81"></textarea>'+
			'							</td>'+
			'						</tr>'+
			'					</table>'+
			'				</div>'+
			'				<div class="modal-footer">'+
			'					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
			'					<input type="submit" class="btn btn-primary" id="submit" name="提交"value="提交">'+
			'				</div>'+
			'			</form>'+
			'		</div>'+
			'	</div>'+
			'</div>';
		require(['validator'], function($){
			$('#credit-Modal').remove();
			$(document.body).append(html);
			var dialog = $('#credit-Modal');
			dialog.modal('show');

			$('#form-credit').bootstrapValidator({
				fields: {
					username: {
						validators: {
							notEmpty: {
								message: '请填写用户手机或UID'
							}
						}
					},
					num: {
						validators: {
							notEmpty: {
								message: '请填写数量'
							},
							regexp: {
								regexp: /^[+-]?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/i,
								message: '最多只保留小数点后2位的正数'
							}
						}
					}
				}
			});
			var Validator = $('#form-credit').data('bootstrapValidator');
			var credit = {
				'user': {},
				'card': {},
				'init': function(type, uid) {
					this.type = type,
					this.getuser();
					if(uid > 0) {
						$('#type').val('uid');
						$('#username').val(uid);
						$('#username').trigger('blur');
						$('#username, #type').attr('disabled', true);
					}
					this.submit();
				},

				getuser: function() {
					var _this = this;
					$('#username').blur(function(){
						Validator.validateField('username');
						var username = $.trim($('#username').val());
						var type = $('#type').val();
						if(username) {
							$.post('./index.php?c=mc&a=trade&do=user&', {'type':type, 'username':username}, function(data){
								var data = $.parseJSON(data);
								if(data.error != 'none') {
									_this.user = {};
									_this.card = {};
									$('#user').hide();
									Validator.updateMessage('username', 'notEmpty', data.message);
									Validator.updateStatus('username', 'INVALID', 'notEmpty');
									return false;
								} else {
									_this.user = data.user;
									_this.card = data.card;
									$('#user strong').html(data.html);
									$('#user').show();
								}
							});
						}
					});
				},

				'checkuser': function() {
					if(this.user.uid == undefined || !this.user.uid) {
						this.user = {};
						this.card = {};
						$('#username').focus();
						Validator.updateMessage('username', 'notEmpty', '未找到对应会员');
						Validator.updateStatus('username', 'INVALID', 'notEmpty');
						//util.message('未找到对应会员');
						return false;
					}
					return true;
				},

				'submit': function() {
					var _this = this;

					$('#form-credit .btn-primary').click(function(){
						_this.checkuser();
						Validator.validate();
						if(Validator.isValid()) {
							_this.password = $.trim($(':input[name="password"]').val());
							_this.num = $.trim($(':text[name="num"]').val());
							_this.remark = $.trim($('textarea[name="remark"]').val());
							var param = {
								'uid': _this.user.uid,
								'num': _this.num,
								'password': _this.password,
								'remark': _this.remark,
								'type': _this.type
							}
							$.post('./index.php?c=mc&a=trade&do=credit', param, function(data){
								if(data != 'success') {
									util.message(data, '', 'error');
									return false;
								} else {
									dialog.modal('hide');
									util.message('操作成功', 'refresh', 'success');
									return false;
								}
							});
						}
					});
				}
			};
			credit.init(type, uid);
		});
	};

	trade.card = function(uid) {
		var html = '<div class="modal fade" id="card-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
			'	<div class="modal-dialog modal-lg" role="document">'+
			'		<div class="modal-content">'+
			'			<form class="table-responsive form-inline" method="post" action="" id="form-card">'+
			'				<div class="modal-header">'+
			'					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
			'					<h4 class="modal-title" id="myModalLabel">发放会员卡</h4>'+
			'				</div>'+
			'				<div class="modal-body">'+
			'					<table class="table table-hover table-bordered">'+
			'						<tr>'+
			'							<th width="150">'+
			'								<select name="type" id="type" class="form-control">'+
			'									<option value="mobile">手机号</option>'+
			'									<option value="uid">会员UID</option>'+
			'								</select>'+
			'							</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<input type="text" value="" name="username" id="username" class="form-control"/>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr id="user" style="display: none">'+
			'							<th>会员信息</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<strong class="form-control-static"></strong>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'					</table>'+
			'				</div>'+
			'				<div class="modal-footer">'+
			'					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
			'					<input type="submit" class="btn btn-primary" id="submit" name="提交"value="提交">'+
			'				</div>'+
			'			</form>'+
			'		</div>'+
			'	</div>'+
			'</div>';
		require(['validator'], function($){
			$('#card-Modal').remove();
			$(document.body).append(html);
			var dialog = $('#card-Modal');
			dialog.modal('show');

			$('#form-card').bootstrapValidator({
				fields: {
					username: {
						validators: {
							notEmpty: {
								message: '请填写用户手机或UID'
							}
						}
					}
				}
			});
			var Validator = $('#form-card').data('bootstrapValidator');
			var card = {
				'user': {},
				'card': {},
				'init': function(uid) {
					this.getuser();
					if(uid > 0) {
						$('#type').val('uid');
						$('#username').val(uid);
						$('#username').trigger('blur');
						if(this.user.uid > 0) {
							$('#username,  #type').attr('disabled', true);
						}
					}
					this.submit();
				},

				getuser: function() {
					var _this = this;
					$('#username').blur(function(){
						Validator.validateField('username');
						var username = $.trim($('#username').val());
						var type = $('#type').val();
						if(username) {
							$.post('./index.php?c=mc&a=trade&do=user&', {'type':type, 'username':username}, function(data){
								var data = $.parseJSON(data);
								if(data.error != 'none') {
									_this.user = {};
									_this.card = {};
									$('#user').hide();
									Validator.updateMessage('username', 'notEmpty', data.message);
									Validator.updateStatus('username', 'INVALID', 'notEmpty');
									return false;
								} else {
									_this.user = data.user;
									_this.card = data.card;
									$('#user strong').html(data.html);
									$('#user').show();
								}
							});
						}
					});
				},

				'checkuser': function() {
					if(this.user.uid == undefined || !this.user.uid) {
						this.user = {};
						this.card = {};
						$('#username').focus();
						Validator.updateMessage('username', 'notEmpty', '未找到对应会员');
						Validator.updateStatus('username', 'INVALID', 'notEmpty');
						return false;
					}

					if(this.card.id > 0) {
						util.message('该会员已领取会员卡');
						return false;
					}
					return true;
				},

				'submit': function() {
					var _this = this;
					$('#form-card .btn-primary').click(function(){
						_this.checkuser();
						Validator.validate();
						if(Validator.isValid()) {
							_this.password = $.trim($(':input[name="password"]').val());
							var param = {
								'uid': _this.user.uid,
								'password': _this.password,
							}
							$.post('./index.php?c=mc&a=trade&do=card', param, function(data){
								if(data != 'success') {
									util.message(data, '', 'error');
									return false;
								} else {
									dialog.modal('hide');
									util.message('发放会员卡成功', 'refresh', 'success');
									return false;
								}
							});
						}
					});
				}
			};
			card.init(uid);
		});
	}

	trade.card_edit = function(uid) {
		var html = '<div class="modal fade" id="card-edit-Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
			'	<div class="modal-dialog modal-lg" role="document">'+
			'		<div class="modal-content">'+
			'			<form class="table-responsive form-inline" method="post" action="" id="form-card">'+
			'				<div class="modal-header">'+
			'					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
			'					<h4 class="modal-title" id="myModalLabel">更改会员卡号</h4>'+
			'				</div>'+
			'				<div class="modal-body">'+
			'					<table class="table table-hover table-bordered">'+
			'						<tr>'+
			'							<th width="150">'+
			'								<select name="type" id="type" class="form-control">'+
			'									<option value="mobile">手机号</option>'+
			'									<option value="uid">会员UID</option>'+
			'								</select>'+
			'							</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<input type="text" value="" name="username" id="username" class="form-control"/>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr id="user" style="display: none">'+
			'							<th>会员信息</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<div class="input-group">'+
			'										<strong class="form-control-static"></strong>'+
			'									</div>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>原卡号</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<p class="label label-danger" id="old_cardsn"></p>'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'						<tr>'+
			'							<th>设置新卡号</th>'+
			'							<td>'+
			'								<div class="form-group">'+
			'									<input type="text" name="cardsn" id="cardsn" value="" class="form-control">'+
			'								</div>'+
			'							</td>'+
			'						</tr>'+
			'					</table>'+
			'				</div>'+
			'				<div class="modal-footer">'+
			'					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
			'					<input type="submit" class="btn btn-primary" id="submit" name="提交"value="提交">'+
			'				</div>'+
			'			</form>'+
			'		</div>'+
			'	</div>'+
			'</div>';
		require(['validator'], function($){
			$('#card-edit-Modal').remove();
			$(document.body).append(html);
			var dialog = $('#card-edit-Modal');
			dialog.modal('show');

			$('#form-card').bootstrapValidator({
				fields: {
					username: {
						validators: {
							notEmpty: {
								message: '请填写用户手机或UID'
							}
						}
					},
					cardsn: {
						validators: {
							notEmpty: {
								message: '新卡号不能为空'
							},
							remote: {
								url: "./index.php?c=mc&a=trade&do=cardsn&type=check",
								data: function(validator) {
									return {
										data: validator.getFieldElements('cardsn').val()
									};
								},
								message: '卡号已经被其他用户使用'
							}
						}
					}
				}
			});
			var Validator = $('#form-card').data('bootstrapValidator');
			var card_edit = {
				'user': {},
				'card': {},
				'init': function(uid) {
					this.getuser();
					if(uid > 0) {
						$('#type').val('uid');
						$('#username').val(uid);
						$('#username').trigger('blur');
						if(this.user.uid > 0) {
							$('#username, #type').attr('disabled', true);
						}
					}
					this.submit();
				},

				getuser: function() {
					var _this = this;
					$('#username').blur(function(){
						Validator.validateField('username');
						var username = $.trim($('#username').val());
						var type = $('#type').val();
						if(username) {
							$.post('./index.php?c=mc&a=trade&do=user&', {'type':type, 'username':username}, function(data){
								var data = $.parseJSON(data);
								if(data.error != 'none') {
									_this.user = {};
									_this.card = {};
									$('#user').hide();
									Validator.updateMessage('username', 'notEmpty', data.message);
									Validator.updateStatus('username', 'INVALID', 'notEmpty');
									return false;
								} else {
									_this.user = data.user;
									_this.card = data.card;
									$('#user strong').html(data.html);
									$('#old_cardsn').html(data.user.cardsn);
									$('#user').show();
								}
							});
						}
					});
				},

				'checkuser': function() {
					if(this.user.uid == undefined || !this.user.uid) {
						this.user = {};
						this.card = {};
						$('#username').focus();
						Validator.updateMessage('username', 'notEmpty', '未找到对应会员');
						Validator.updateStatus('username', 'INVALID', 'notEmpty');
						return false;
					}
					return true;
				},

				'submit': function() {
					var _this = this;
					$('#form-card .btn-primary').click(function(){
						_this.checkuser();
						Validator.validate();
						if(Validator.isValid()) {
							var param = {
								'uid': _this.user.uid,
								'cardsn': $.trim($('#cardsn').val()),
								'password': $.trim($(':input[name="password"]').val())
							}
							$.post('./index.php?c=mc&a=trade&do=cardsn', param, function(data){
								if(data != 'success') {
									util.message(data, '', 'error');
									return false;
								} else {
									dialog.modal('hide');
									util.message('更改卡号成功', 'refresh', 'success');
									return false;
								}
							});
						}
					});
				}
			};
			card_edit.init(uid);
		});
	}

	return trade;
});
