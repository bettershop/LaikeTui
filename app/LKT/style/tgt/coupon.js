define(['jquery', 'underscore', 'bootstrap'], function($, _){
	var coupon = {
		'defaultoptions' : {
			callback : null,
			type : 'all',
			multiple : false,
			ignore : {
				'local' : false,
				'wechat' : true
			}
		},
		'init' : function(callback, options) {
			var $this = this;
			$this.options = $.extend({}, $this.defaultoptions, options);
			$this.options.callback = callback;

			$('#counpon-Modal').remove();
			$(document.body).append($this.buildHtml().mainDialog);

			$this.modalobj = $('#counpon-Modal');
			$this.modalobj.find('.modal-header .nav li a').click(function(){
				var type = $(this).data('type');
				$this.localPage(type, 1);
				$(this).tab('show')
				return false;
			});
			if (!$(this).data('init')) {
				if($this.options.type && $this.options.type != 'all') {
					$this.modalobj.find('.modal-header .nav li.' + $this.options.type + ' a').trigger('click');
				} else {
					$this.modalobj.find('.modal-header .nav li.show:first a').trigger('click');
				}
			}
			$this.modalobj.modal('show');
			return $this.modalobj;
		},

		'localPage' : function(type, page) {
			var $this = this;
			var $history = $this.modalobj.find('.coupon-content #' + type);
			$history.html('<div class="info"><i class="fa fa-spinner fa-pulse fa-lg"></i> 数据加载中</div>');
			$.getJSON('./index.php?c=utility&a=coupon&do=' + type, {'page': page}, function(data){
				data = data.message;
				$this.modalobj.find('#coupon-list-pager').html('');
				if(!_.isEmpty(data.items)) {
					$this.modalobj.find('#btn-select').show();
					$history.data('attachment', data.items);
					$history.empty();
					var Dialog = type + 'Dialog';
					$history.html(_.template($this.buildHtml()[Dialog])(data));
					$this.modalobj.find('#coupon-list-pager').html(data.page);
					$this.modalobj.find('.pagination a').click(function(){
						$this.localPage(type, $(this).attr('page'));
					});
					$history.find('.conpon-list').click(function(){
						$this.selectCoupon(this);
					});
				} else {
					$history.html('<div class="info"><i class="fa fa-info-circle fa-lg"></i> 暂无数据</div>');
				}
			});

			$this.modalobj.find('.modal-footer .btn-primary').unbind('click').click(function(){
				var attachment = [];
				$history.find('.btn-primary').each(function(){
					attachment.push($history.data('attachment')[$(this).data('attachid')]);
					$(this).removeClass('btn-primary');
				});
				$this.finish(attachment);
			});
			return false;
		},

		'selectCoupon' : function(obj) {
			var $this = this;
			$(obj).toggleClass('btn-primary');
			if (!$this.options.multiple) {
				$this.modalobj.find('.modal-footer .btn-primary').trigger('click');
			}
		},

		'finish' : function(attachment) {
			var $this = this;
			if($.isFunction($this.options.callback)) {
				if ($this.options.multiple == false) {
					$this.options.callback(attachment[0]);
				} else {
					$this.options.callback(attachment);
				}
				$this.modalobj.modal('hide');
			}
		},

		'buildHtml' : function() {
			var dialog = {};
			dialog['mainDialog'] = '<div id="counpon-Modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">\n' +
				'	<div class="modal-dialog">\n' +
				'		<div class="modal-content modal-lg">\n' +
				'			<div class="modal-header">\n' +
				'				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\n' +
				'				<h3>'+
				'					<ul role="tablist" class="nav nav-pills" style="font-size:14px; margin-top:-20px;">'+
				'						<li role="presentation" class="local ' + (this.options.ignore.local ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="local" role="tab" aria-controls="local" href="#local">系统优惠券</a>'+
				'						</li>'+
				'						<li role="presentation" class="wechat ' + (this.options.ignore.wechat ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="wechat" role="tab" aria-controls="wechat" href="#wechat">微信卡券</a>'+
				'						</li>'+
				'					</ul>'+
				'				</h3>'+
				'			</div>\n' +
				'			<div class="modal-body coupon-content">\n' +
				'				<div class="tab-content">'+
				'					<div id="local" class="tab-pane" class="active" role="tabpanel"></div>'+
				'					<div id="wechat" class="tab-pane" role="tabpanel"></div>'+
				'				</div>' +
				'			</div>\n' +
				'			<div class="modal-footer">\n' +
				'				<div style="float: left;">\n' +
				'					<nav id="coupon-list-pager">\n' +
				'						<ul class="pager" style="margin: 0;"></ul>\n' +
				'					</nav>\n' +
				'				</div>\n' +
				'				<div id="btn-select" style="float: right; display: none">\n' +
				'						<button '+(this.options.multiple ? '' : 'style="display:none;"')+' type="button" class="btn btn-primary">确认</button>\n' +
										(this.options.multiple ? '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>\n' : '') +
				'				</div>\n' +
				'			</div>\n'+
				'		</div>\n' +
				'	</div>\n' +
				'</div>';

			dialog['localDialog'] ='<table class="table table-hover table-bordered">\n'+
				'				<thead>\n'+
				'					<tr>\n'+
				'						<th width="130" class="text-center">标题</th>\n'+
				'						<th class="text-center">类型</th>\n'+
				'						<th width="100" class="text-center">使用条件</th>\n'+
				'						<th class="text-center">折扣/减免</th>\n'+
				'						<th class="text-center">发行/已领</th>\n'+
				'						<th width="185" class="text-center">使用期限</th>\n'+
				'						<th class="text-center">操作</th>\n'+
				'					</tr>'+
				'				</thead>'+
				'				<tbody>'+
				'					<%var items = _.sortBy(items, function(item) {return -item.couponid;});%>' +
				'					<%_.each(items, function(item) {%> \n' +
				'					<tr title="<%=item.title%>">' +
				'						<td><%=item.title%></td>' +
				'						<td><%if(item.type == 1) {%><span class="label label-success">折扣券</span><%} else {%><span class="label label-danger">代金券</span><%}%></td>' +
				'						<td>满<%=item.condition%>元</td>' +
				'						<td><%if(item.type == 1) {%>打<%=item.discount*10%>折<%} else {%>减<%=item.discount%>元<%}%></td>' +
				'						<td><%=item.amount%>/<strong class="text-danger"><%=item.dosage%></strong></td>' +
				'						<td><%=item.starttime_cn%> ~ <%=item.endtime_cn%></td>' +
				'						<td><a href="javascript:;" class="btn btn-default conpon-list" data-title="<%=item.title%>" data-attachid="<%=item.couponid%>">选取</a></td>' +
				'					</tr>' +
				'					<%});%>' +
				'				</tbody>'+
				'   		</table>';

			dialog['wechatDialog'] ='<table class="table table-hover table-bordered">\n'+
				'				<thead>\n'+
				'					<tr>\n'+
				'						<th width="130" class="text-center">标题</th>\n'+
				'						<th class="text-center">类型</th>\n'+
				'						<th width="250" class="text-center">卡券有效期</th>\n'+
				'						<th class="text-center">库存/每人限领</th>\n'+
				'						<th class="text-center">操作</th>\n'+
				'					</tr>'+
				'				</thead>'+
				'				<tbody>'+
				'					<%var items = _.sortBy(items, function(item) {return -item.couponid;});%>' +
				'					<%_.each(items, function(item) {%> \n' +
				'					<tr title="<%=item.title%>">' +
				'						<td><%=item.title%></td>' +
				'						<td><%if(item.ctype == "discount") {%><span class="label label-success">折扣券</span><%} else if(item.ctype == "cash") {%><span class="label label-danger">代金券</span><%} else if(item.ctype == "gift") {%><span class="label label-danger">礼品券</span><%} else if(item.ctype == "groupon") {%><span class="label label-danger">团购券</span><%} else if(item.ctype == "general_coupon") {%><span class="label label-danger">优惠券</span><%}%></td>' +
				'						<td><%if(item.date_info.time_type == 1) {%><%=item.date_info.time_limit_start%> ~ <%=item.date_info.time_limit_end%><%} else {%>领取后<%=item.date_info.date_info%>天后生效,<%=item.date_info.limit%>天有效期<%}%></td>' +
				'						<td><%=item.quantity%>/<strong class="text-danger"><%=item.get_limit%></strong></td>' +
				'						<td><a href="javascript:;" class="btn btn-default conpon-list" data-title="<%=item.title%>" data-attachid="<%=item.id%>">选取</a></td>' +
				'					</tr>' +
				'					<%});%>' +
				'				</tbody>'+
				'   		</table>';
			return dialog;
		}
	};
	return coupon;
});