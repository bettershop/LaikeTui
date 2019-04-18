define(['jquery', 'underscore', 'bootstrap', 'jquery.wookmark', 'jquery.jplayer'], function($, _){
	var material = {
		'defaultoptions' : {
			callback : null,
			type : 'all',
			multiple : false,
			ignore : {
				'wxcard' : true,
				'image' : false,
				'news' : false,
				'video' : false,
				'voice' : false
			}
		},
		'init' : function(callback, options) {
			var $this = this;
			$this.options = $.extend({}, $this.defaultoptions, options);
			$this.options.callback = callback;
			$('#material-Modal').remove();
			$(document.body).append($this.buildHtml().mainDialog);

			$this.modalobj = $('#material-Modal');
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
			var page = page || 1;
			$('.checkMedia').removeClass('checkedMedia');
			var $content = $this.modalobj.find('.material-content #' + type);
			$content.html('<div class="info text-center"><i class="fa fa-spinner fa-pulse fa-lg"></i> 数据加载中</div>');
			var url = './index.php?c=utility&a=material&do=list&type=' + type;
			if(type == 'wxcard') {
				url = './index.php?c=utility&a=coupon&do=wechat';
			}
			$.getJSON(url, {'page': page}, function(data){
				data = data.message;
				$this.modalobj.find('#material-list-pager').html('');
				if(!_.isEmpty(data.items)) {
					$this.modalobj.find('#btn-select').show();
					$content.data('attachment', data.items);
					$content.empty();
					var Dialog = type + 'Dialog';
					$content.html(_.template($this.buildHtml()[Dialog])(data));
					if(type == 'news') {
						setTimeout(function(){
							$('.water').wookmark({
								align: 'center',
								autoResize: false,
								container: $('#news'),
								autoResize :true
							});
						}, 100);
					}
					$this.selectMedia();
					$this.playaudio();
					$this.modalobj.find('#material-list-pager').html(data.pager);
					$this.modalobj.find('#material-list-pager .pagination a').click(function(){
						$this.localPage(type, $(this).attr('page'));
					});
				} else {
					$content.html('<div class="info text-center"><i class="fa fa-info-circle fa-lg"></i> 暂无数据</div>');
				}
			});
			$this.modalobj.find('.modal-footer .btn-primary').unbind('click').click(function(){
				var attachment = [];
				$content.find('.checkedMedia').each(function(){
					attachment.push($content.data('attachment')[$(this).data('attachid')]);
				});
				$this.finish(attachment);
			});
			return false;
		},

		'selectMedia' : function(){
			var $this = this;
			$this.modalobj.on('click', '.checkMedia', function(){
				if(!$this.options.multiple) {
					$('.checkMedia').removeClass('checkedMedia');
				}
				$(this).addClass('checkedMedia');
				var type = $(this).data('type');
				if(type == 'news') {
					if(!$this.options.multiple) {
						$('#news .panel-group').removeClass('selected');
					}
					$(this).addClass('selected');
				} else if(type == 'image') {
					if(!$this.options.multiple) {
						$('#image div').removeClass('img-item-selected');
					}
					$(this).addClass('img-item-selected');
				} else {
					if(!$this.options.multiple) {
						$('.checkMedia').removeClass('btn-primary');
					}
					$(this).addClass('btn-primary');
				}
				if(!$this.options.multiple) {
					$this.modalobj.find('.modal-footer .btn-primary').trigger('click');
				}
			});
		},

		'playaudio' : function(){
			$("#voice, .panel").on('click', '.audio-player-play', function(){
				var src = $(this).data("attach");
				if(!src) {
					return;
				}
				if ($("#player")[0]) {
					var player = $("#player");
				} else {
					var player = $('<div id="player"></div>');
					$(document.body).append(player);
				}
				player.data('control', $(this));
				player.jPlayer({
					playing: function() {
						$(this).data('control').find("i").removeClass("fa-play").addClass("fa-stop");
					},
					pause: function (event) {
						$(this).data('control').find("i").removeClass("fa-stop").addClass("fa-play");
					},
					swfPath: "resource/components/jplayer",
					supplied: "mp3,wma,wav,amr",
					solution: "html, flash"
				});
				player.jPlayer("setMedia", {mp3: $(this).data("attach")}).jPlayer("play");
				if($(this).find("i").hasClass("fa-stop")) {
					player.jPlayer("stop");
				} else {
					$('.audio-msg').find('.fa-stop').removeClass("fa-stop").addClass("fa-play");
					player.jPlayer("setMedia", {mp3: $(this).data("attach")}).jPlayer("play");
				}
			});
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
			dialog['mainDialog'] = '<div id="material-Modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">\n' +
				'	<div class="modal-dialog">\n' +
				'		<div class="modal-content modal-lg">\n' +
				'			<div class="modal-header">\n' +
				'				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\n' +
				'				<h3>'+
				'					<ul role="tablist" class="nav nav-pills" style="font-size:14px; margin-top:-20px;">'+
				'						<li role="presentation" class="news ' + (this.options.ignore.news ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="news" role="tab" aria-controls="news" href="#news">图文</a>'+
				'						</li>'+
				'						<li role="presentation" class="image ' + (this.options.ignore.image ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="image" role="tab" aria-controls="image" href="#image">图片</a>'+
				'						</li>'+
				'						<li role="presentation" class="voice ' + (this.options.ignore.voice ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="voice" role="tab" aria-controls="voice" href="#voice">语音</a>'+
				'						</li>'+
				'						<li role="presentation" class="video ' + (this.options.ignore.video ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="video" role="tab" aria-controls="video" href="#video">视频</a>'+
				'						</li>'+
				'						<li role="presentation" class="wxcard ' + (this.options.ignore.wxcard ? 'hide' : 'show') + '">'+
				'							<a data-toggle="tab" data-type="wxcard" role="tab" aria-controls="wxcard" href="#wxcard">微信卡券</a>'+
				'						</li>'+
				'					</ul>'+
				'				</h3>'+
				'			</div>\n' +
				'			<div class="modal-body material-content">\n' +
				'				<div class="tab-content">'+
				'					<div id="news" class="tab-pane material clearfix" class="active" role="tabpanel" style="position:relative"></div>'+
				'					<div id="image" class="tab-pane history" role="tabpanel"></div>'+
				'					<div id="voice" class="tab-pane" role="tabpanel"></div>'+
				'					<div id="video" class="tab-pane" role="tabpanel"></div>'+
				'					<div id="wxcard" class="tab-pane" role="tabpanel"></div>'+
				'				</div>' +
				'			</div>\n' +
				'			<div class="modal-footer">\n' +
				'				<div style="float: left;">\n' +
				'					<nav id="material-list-pager">\n' +
				'					</nav>\n' +
				'				</div>\n' +
				'				<div id="btn-select" style="float: right; display: none">\n' +
				'					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>\n' +
				'					<button type="button" class="btn btn-primary">确认</button>\n' +
				'				</div>\n' +
				'			</div>\n'+
				'		</div>\n' +
				'	</div>\n' +
				'</div>';

			dialog['imageDialog'] = '<ul class="img-list clearfix">\n' +
				'<%var items = _.sortBy(items, function(item) {return -item.id;});%>' +
				'<%_.each(items, function(item) {%> \n' +
				'<div class="checkMedia" data-media="<%=item.media_id%>" data-type="image" data-attachid="<%=item.id%>">' +
				'	<li class="img-item" style="padding:5px">\n' +
				'		<div class="img-container" style="background-image: url(\'<%=item.attach%>\');">\n' +
				'			<div class="select-status"><span></span></div>\n' +
				'		</div>\n' +
				'	</li>\n' +
				'</div>\n' +
				'<%});%>\n' +
				'</ul>';

			dialog['voiceDialog'] ='<table class="table table-hover table-bordered" style="margin-bottom:0">'+
				'						<thead class="navbar-inner">'+
				'							<tr>'+
				'								<th>标题</th>'+
				'								<th style="width:20%;text-align:right">创建时间</th>'+
				'								<th style="width:20%;text-align:right"></th>'+
				'							</tr>'+
				'							</thead>'+
				'							<tbody class="history-content">'+
				'							<%var items = _.sortBy(items, function(item) {return -item.createtime;});%>' +
				'							<%_.each(items, function(item) {%> \n' +
				'							<tr>'+
				'								<td><%=item.filename%></td>'+
				'								<td align="right"><%=item.createtime_cn%></td>'+
				'								<td align="right">'+
				'									<div class="btn-group">'+
				'										<a href="javascript:;" class="btn btn-default btn-sm audio-player-play audio-msg" data-attach="<%=item.attach%>"><i class="fa fa-play"></i></a>'+
				'										<a href="javascript:;" class="btn btn-default btn-sm checkMedia" data-media="<%=item.media_id%>" data-type="voice" data-attachid="<%=item.id%>">选取</a>'+
				'									</div>'+
				'								</td>'+
				'							</tr>'+
				'							<%});%>' +
				'						</tbody>'+
				'					</table>';

			dialog['videoDialog'] ='<table class="table table-hover table-bordered" style="margin-bottom:0">'+
				'						<thead class="navbar-inner">'+
				'							<tr>'+
				'								<th>标题</th>'+
				'								<th style="width:20%;text-align:right">创建时间</th>'+
				'								<th style="width:20%;text-align:right"></th>'+
				'							</tr>'+
				'							</thead>'+
				'							<tbody class="history-content">'+
				'							<%var items = _.sortBy(items, function(item) {return -item.createtime;});%>' +
				'							<%_.each(items, function(item) {%> \n' +
				'							<tr>'+
				'								<%if(item.tag.title) {var title = item.tag.title} else {var title =item.filename}%>'+
				'								<td><%=title%></td>'+
				'								<td align="right"><%=item.createtime_cn%></td>'+
				'								<td align="right">'+
				'									<div class="btn-group">'+
				'										<a href="javascript:;" class="btn btn-default btn-sm checkMedia" data-media="<%=item.media_id%>" data-type="video" data-attachid="<%=item.id%>">选取</a>'+
				'									</div>'+
				'								</td>'+
				'							</tr>'+
				'							<%});%>' +
				'						</tbody>'+
				'					</table>';

			dialog['wxcardDialog'] ='<table class="table table-hover table-bordered">\n'+
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
				'						<td><a href="javascript:;" class="btn btn-default btn-sm checkMedia" data-title="<%=item.title%>" data-type="wxcard" data-media="<%=item.card_id%>" data-attachid="<%=item.id%>">选取</a></td>' +
				'					</tr>' +
				'					<%});%>' +
				'				</tbody>'+
				'   		</table>';

			dialog['newsDialog'] = '<%var items = _.sortBy(items, function(item) {return -item.createtime;});%>' +
				'					<%_.each(items, function(item) {%> \n' +
				'					<div class="col-md-5 col-md-5 col-md-5 water" style="display:none">'+
				'						<div class="panel-group checkMedia" data-media="<%=item.media_id%>" data-type="news" data-attachid="<%=item.id%>">'+
				'							<%var index = 0;%>\n' +
				'							<%_.each(item.items, function(data) {%>\n' +
				'								<%index++;%>\n' +
				'								<div class="panel panel-default">'+
				'									<%if(index == 1) {%>\n' +
				'									<div class="panel-body">'+
				'										<div class="img">'+
				'											<i class="default">封面图片</i>'+
				'											<img src="<%=data.thumb_url%>">'+
				'											<span class="text-left"><%=data.title%></span>'+
				'										</div>'+
				'									</div>'+
				'									<%} else {%>\n' +
				'									<div class="panel-body">'+
				'										<div class="text">'+
				'											<h4><%=data.title%></h4>'+
				'										</div>'+
				'										<div class="img">'+
				'											<img src="<%=data.thumb_url%>">'+
				'											<i class="default">缩略图</i>'+
				'										</div>'+
				'									</div>'+
				'									<%}%>\n' +
				'								</div>'+
				'							<%});%>'+
				'							<div class="mask"></div>'+
				'							<i class="fa fa-check"></i>'+
				'						</div>'+
				'					</div>'+
				'					<%});%>';

			return dialog;
		}
	};
	return material;
});