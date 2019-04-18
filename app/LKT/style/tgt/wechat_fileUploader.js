define(['bootstrap', 'webuploader', 'util', 'underscore', 'filestyle'], function($, WebUploader, util, _){
	fileUploader = {
		'supports' : ['upload', 'browser', 'video'],
		'defaultoptions' : {
			global : false,
			acid : 0,
			mode : '',
			callback : null, // 回调方法
			type : 'image', // 上传组件类型
			direct : false, // 效果, 是否选择即返回, 单图可用.
			multi : false, // 返回结果是 object 还是 Array
			dest_dir : '', // 自定义上传目录
			tabs : { // 选项卡, remote
				'upload': '',
				'browser' : '',
				'video' : ''
	 		}
		},

		'options' : {}, // 当前配置项
		'flag' : 0,
		'mode' : '',

		'show' : function(callback, options){
			if(options.error) {
				util.message('公众号没有权限');
				return false;
			}
			this.mode = '';
			this.type = options.type;
			if(options.mode) {
				this.mode = options.mode;
			}
			if(!options.acid) {
				this.select_acid(callback, options);
			} else {
				this.acid = options.acid;
				this.init(callback, options);
			}
		},

		'reset' : function(){
			if(this.modalobj != null){
				this.images = [];
				this.flag = 0;
				for(i in this.options.tabs){
					eval("$this.reset_"+i+"();");
				}
			}
		},

		'hide' : function(){
			if(this.modalobj != null){
				this.reset();
				this.modalobj.modal('hide');
			}
		},

		'uploader' : {},
		'modalobj' : null,
		'images' : [],
		/*上次上次控件的状态,tabname,active*/
		'historyOptions' : '',

		//选择子公众号
		'select_acid' : function(callback, options) {
			var $this = this;
			if ($('#modal-acid').length == 0) {
				$(document.body).append('<div id="modal-acid" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true"></div>');
				this.acidobj = $('#modal-acid');
				this.acidobj.append(
					'<div class="modal-dialog" style="width: 710px;">\n'+
						'	<div class="modal-content">\n'+
						'		<div class="modal-header">'+
						'			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
						'			 <h4 class="modal-title">选择子公众号</h4>' +
						'		</div>'+
						'		<div class="modal-body tab-content" id="acid"></div>' +
						'		<div class="modal-footer">' +
						'			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>' +
						'			<button type="button" class="btn btn-primary" id="acid-checked">确定</button>' +
						'		</div>' +
						'	</div>' +
						'</div>'
				);
				$.getJSON('./index.php?c=utility&a=wechat_file&do=select_acid', function(data) {
					if(data == 'error') {
						util.message('没有可用的公众号', '', 'error');
						return false;
					} else {
						if(data.total > 1) {
							var html = '';
							$.each(data.item, function(k, v){
								html += '<div class="radio"><label><input type="radio" name="acid" value="'+ v.acid+'"/>'+ v.name +'</label></div>';
							});
							$('#modal-acid').find('#acid').html(html);
						}
					}
				});
			}
			this.acidobj.modal('show');
			this.acidobj.find('#acid-checked').unbind('click').click(function(){
				var acid = $('#modal-acid').find(':radio:checked').val();
				if(!acid) {
					util.message('请选择公众号', '', 'error');
					return false;
				}
				$this.acid = acid;
				$this.acidobj.modal('hide');
				$this.init(callback, options);
			});
		},

		'init' : function(callback, options) {
			$this = this;
			this.options = $.extend({}, this.defaultoptions, options);
			this.options.callback = callback;
			if(options.tabs){
				this.options.tabs = {};
				for(i in options.tabs){
					if($.inArray(i, $this.supports) > -1){
						$this.options.tabs[i] = options.tabs[i];
					}
				}
			}

			if ($('#modal-wechat-fileUploader').length == 0) {
				$(document.body).append('<div id="modal-wechat-fileUploader" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true"></div>');
				this.modalobj = $('#modal-wechat-fileUploader');
				this.modalobj.append(
					'<div class="modal-dialog" style="width: 710px;">\n'+
						'	<div class="modal-content">\n'+
						'		<div class="modal-header" style="padding: 5px;">'+
						'			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
						'			<ul class="nav nav-pills" role="tablist">'+
						'			</ul>'+
						'		</div>'+
						'		<div class="modal-body tab-content" style="padding-top:5px;"></div>\n' +
						'	</div>\n' +
						'</div>\n'
				);
			} else {
				this.modalobj = $('#modal-wechat-fileUploader');
			}

			var currentOptions = '';

			for(i in this.options.tabs){
				eval("this.init_"+i+"();");
				currentOptions += i + $this.options.tabs[i];
			}

			if(!this.historyOptions || this.historyOptions != currentOptions){
				$this.modalobj.find('.nav-pills').find('li').removeClass('active').hide();
				$this.modalobj.find('.tab-pane').removeClass('active');
				for(i in $this.options.tabs){
					$this.modalobj.find('.nav-pills').find('a[aria-controls="'+i+'"]').parent().show();
					if($this.options.tabs[i]){
						$this.modalobj.find('.nav-pills').find('a[aria-controls="'+i+'"]:first').parent().addClass('active');
						$this.modalobj.find('#'+i).addClass('active');
					}
				}
			}


			$($this.modalobj.find('.nav-pills').find('li.active').find('a').attr('href')).addClass('active');
			this.reset();
			this.historyOptions = currentOptions;
			this.modalobj.modal({'keyboard': false});
			this.modalobj.modal('show');
		},

		'init_video' : function() {
			$this = this;
			if(this.modalobj.find('#video').length == 0){
				this.modalobj.find('.nav-pills').append('<li role="presentation"><a aria-controls="video" role="tab" data-toggle="tab" href="#video">上传文件</a></li>');
				this.modalobj.find('.modal-body').append(this.template().video);
				this.modalobj.find('#video').find(':file[name="file"]').filestyle({buttonText: '选择文件'});
			}
			this.modalobj.find('#video').find('button.btn-primary').off('click');
			this.modalobj.find('#video').find('button.btn-primary').on('click', function(){
				var url = "./index.php?c=utility&a=wechat_file&do=upload&types=video";
				var acid = $this.acid;
				var mode = $('#fileUploader_video_form').find(':input[name="mode"]:checked').val();
				$('#fileUploader_video_form').attr('action', url + '&acid='+acid+'&mode='+mode);

				if(!$('#fileUploader_video_form :text[name="title"]').val()) {
					util.message('视频标题不能为空');
					return false;
				}
				if(!$('#fileUploader_video_form textarea[name="introduction"]').val()) {
					util.message('视频描述不能为空');
					return false;
				}
				$('#fileUploader_video_form').submit();
				util.loading();
				var interval = setInterval(function(){
					var $target = $('#fileUploader_video_target').get(0).contentWindow.document.body.innerText;
					if($target != ''){
						clearInterval(interval);
						var result = $.parseJSON($target);
						if(result.message){
							alert(result.message);
							util.loaded();
						} else if(result.media_id) {
							if ($.isFunction($this.options.callback)){
								if($this.options.multi){
									$this.options.callback([result]);
								} else {
									$this.options.callback(result);
								}
								util.loaded();
								$this.hide();
							}
						}
					}
				}, 500);
			});
		},

		'reset_video' : function(){},

		'init_browser' : function() {
			$this = this;
			if(this.modalobj.find('#wechat_browser').length == 0){
				this.modalobj.find('.nav-pills').append('<li role="presentation"><a aria-controls="browser" role="tab" data-toggle="tab" href="#wechat_browser">浏览附件</a></li>');
				this.modalobj.find('.modal-body').append(this.template().browser);
			}
			this.localPage(1);
			this.modalobj.find('#wechat_browser').find('button.btn-primary').off('click');
			this.modalobj.find('#wechat_browser').find('button.btn-primary').on('click', function(){
				if ($this.images.length > 0){
					if($.isFunction($this.options.callback)){
						if($this.options.multi){
							$this.options.callback($this.images);
						} else {
							$this.options.callback($this.images[0]);
						}
						$this.hide();
					}
				} else {
					alert('未选择任何文件.');
				}
			});
		},

		'localPage' : function(page) {
			var $this = this;
			var $browser = $this.modalobj.find('#wechat_browser');
			var acid = $this.acid;
			var type = $this.type;
			var mode = $this.mode;
			$.getJSON('./index.php?c=utility&a=wechat_file&do=browser', {'page': page, 'acid' : acid, 'type' : type, 'mode' : mode}, function(data){
				data = data.message;
				$browser.find('.browser-content').html('<i class="fa fa-spinner"></i>');
				if(!_.isEmpty(data.items)) {
					$browser.data('attachment', data.items);
					$browser.find('.browser-content').empty();
					$browser.find('.browser-content').html(_.template($this.template().localDialogLi, data));
					$browser.find('#image-list-pager').html(data.page);
					window.p = function(url, p, state) {
						$this.localPage(p);
					}
				} else {
					$browser.find('.browser-content').html('暂无附件');
				}

				$browser.find('.img-item').off('click');
				$browser.find('.img-item').on('click', function(){
					$(this).toggleClass('img-item-selected');
					$this.images = [];
					$.each($('.img-item-selected'), function(idx, ele){
						$this.images.push($this.modalobj.find('#wechat_browser').data('attachment')[$(this).attr('attachid')]);
					});
					$browser.find('.browser-info').text('已选中 '+$this.images.length+' 个文件.');
					if(($this.options.direct || !$this.options.multi) && $(this).hasClass('img-item-selected')){
						$browser.find('button.btn-primary').click();
					}
				});

				$browser.find('.btnClose').off('click');
				$browser.find('.btnClose').on('click', function(event){
					var $this = this;
					if (confirm("确定要删除文件吗？")){
						var id = $(this).parent().attr('attachid');
						$.post('./index.php?c=utility&a=wechat_file&do=delete', {'id' : id, 'acid' : acid}, function(data){
							var data = $.parseJSON(data);
							if(!data.error) {
								util.message(data.message);
								return false;
							}
							$($this).parent().remove();
						});
					}
					event.stopPropagation();
				});
			});
			return false;
		},

		'reset_browser' : function(){
			var $this = this;
			$this.modalobj.find('#wechat_browser').find('.img-item-selected').removeClass('img-item-selected');
			$this.modalobj.find('#wechat_browser').find('.browser-content').html('');
		},

		'browserfiles' : {},

		'init_upload' : function(){
			$this = this;

			if(this.modalobj.find('#wechat_upload').length == 0){
				this.modalobj.find('.nav-pills').html('');
				this.modalobj.find('.nav-pills').append('<li role="presentation" data-mode="perm"><a aria-controls="upload" role="tab" data-toggle="tab" href="#wechat_upload" >上传永久文件</a></li>');
				this.modalobj.find('.nav-pills').append('<li role="presentation" data-mode="temp"><a aria-controls="upload" role="tab" data-toggle="tab" href="#wechat_upload" >上传临时文件</a></li>');
			}
			this.modalobj.find('#wechat_upload').remove();
			if(this.modalobj.find('#wechat_upload').length == 0){
				this.modalobj.find('.modal-body').append(this.template().upload);
				var $wrap = $('#wechat_uploader'),
				// 图片容器
					$queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),
				// 状态栏，包括进度和控制按钮
					$statusBar = $wrap.find('.statusBar'),
				// 文件总体选择信息。
					$info = $statusBar.find('.info'),
				// 上传按钮
					$upload = $wrap.find('.uploadBtn'),
				// 没选择文件之前的内容。
					$placeHolder = $wrap.find('.placeholder'),
					$progress = $statusBar.find('.progress').hide(),
				// 添加的文件数量
					fileCount = 0,
				// 添加的文件总大小
					fileSize = 0,
				// 优化retina, 在retina下这个值是2
					ratio = window.devicePixelRatio || 1,
				// 缩略图大小
					thumbnailWidth = 110 * ratio,
					thumbnailHeight = 110 * ratio,
				// 可能有pedding, ready, uploading, confirm, done.
					state = 'pedding',
				// 所有文件的进度信息，key为file id
					percentages = {},
					supportTransition = (function(){
						var s = document.createElement('p').style,
							r = 'transition' in s ||
								'WebkitTransition' in s ||
								'MozTransition' in s ||
								'msTransition' in s ||
								'OTransition' in s;
						s = null;
						return r;
					})(),

					uploader;

				var options = {
					//auto: !$this.options.multi,
					pick: {
						id: '#wechat_filePicker',
						label: '点击选择文件',
						multiple : $this.options.multi
					},
					dnd: '#wechat_dndArea',
					paste: '#wechat_uploader',
					// swf文件路径
					swf: './resource/componets/webuploader/Uploader.swf',
					// 文件接收服务端。
					server: './index.php?c=utility&a=wechat_file&do=upload&',
					chunked: false,
					compress: {
						quality: 80,
						preserveHeaders: true,
						noCompressIfLarger: true,
						compressSize: 1 * 1024 * 1024
					},
					duplicate : true,
					fileNumLimit: $this.options.multi ? 30 : 1,
					fileSizeLimit: 4 * 1024 * 1024,
					fileSingleSizeLimit: 30* 4 * 1024 * 1024
				}

				// 实例化
				uploader = WebUploader.create(options);

				if($this.options.multi){
					// 添加“添加文件”的按钮，
					uploader.addButton({
						id: '#wechat_filePicker2',
						label: '继续添加',
						multiple : $this.options.multi
					});
				}

				$this.uploader = uploader;

				// 成功上传
				accept = 0;

				$this.reset_upload = function(){
					fileCount = 0;
					fileSize = 0;
					accept = 0;
					$.each($this.uploader.getFiles(), function(index, file){
						removeFile(file);
					});
					updateTotalProgress();

					$this.uploader.reset();
					$this.uploader.refresh();

					$('#wechat_dndArea').removeClass('element-invisible');
					$('#wechat_uploader').find('.filelist').empty();
					if($this.options.multi){
						$('#wechat_filePicker2').removeClass('element-invisible');
						$('#wechat_filePicker2').next().removeClass('disabled');
						$('#wechat_filePicker2').find('.webuploader-pick').next().css({'top': '0px', 'left': '0px','width': '100px','height': '32px'});
					}
					$('#wechat_filePicker').find('.webuploader-pick').next().css({'left':'242px', 'top':'35px'});
					var bar = $('#wechat_uploader').find('.statusBar');
					bar.find('.info').empty();
					bar.find('.accept').empty();
					bar.show();
				};

				// 当有文件添加进来时执行，负责view的创建
				function addFile(file) {
					var $li = $('<li id="' + file.id + '">' +
							'<p class="title">' + file.name + '</p>' +
							'<p class="imgWrap"></p>'+
							//'<p class="progress"><span></span></p>' +
							'</li>'),
						$btns = $('<div class="file-panel">' +
							'<span class="cancel">删除</span></div>').appendTo($li),
						$prgress = $li.find('p.progress span'),
						$wrap = $li.find('p.imgWrap'),
						$info = $('<p class="error"></p>'),

						showError = function(code) {
							switch(code) {
								case 'exceed_size':
									text = '文件大小超出';
									break;
								case 'interrupt':
									text = '上传暂停';
									break;
								default:
									text = '上传失败，请重试';
									break;
							}
							$info.text(text).appendTo($li);
						};
					if (file.getStatus() === 'invalid') {
						showError(file.statusText);
					} else {
						// @todo lazyload
						$wrap.text('预览中');
						uploader.makeThumb(file, function(error, src) {
							if (error) {
								$wrap.text('不能预览');
								return;
							}
							var img = $('<img src="'+src+'">');
							$wrap.empty().append(img);
						}, thumbnailWidth, thumbnailHeight);
						percentages[file.id] = [file.size, 0];
						file.rotation = 0;
					}
					file.on('statuschange', function(cur, prev) {
						if (prev === 'progress') {
							$prgress.hide().width(0);
						} else if (prev === 'queued') {
							$li.off('mouseenter mouseleave');
							$btns.remove();
						}
						// 成功
						if (cur === 'error' || cur === 'invalid') {
							showError(file.statusText);
							percentages[file.id][1] = 1;
						} else if (cur === 'interrupt') {
							showError('interrupt');
						} else if (cur === 'queued') {
							percentages[file.id][1] = 0;
						} else if (cur === 'progress') {
							$info.remove();
							$prgress.css('display', 'block');
						} else if (cur === 'complete') {
							//$li.append('<span class="success"></span>');
						}
						$li.removeClass('state-' + prev).addClass('state-' + cur);
					});

					$li.on('mouseenter', function() {
						$btns.stop().animate({height: 30});
					});

					$li.on('mouseleave', function() {
						$btns.stop().animate({height: 0});
					});

					$btns.on('click', 'span', function() {
						var index = $(this).index(),
							deg;
						switch (index) {
							case 0:
								uploader.removeFile(file);
								return;
							case 1:
								file.rotation += 90;
								break;
							case 2:
								file.rotation -= 90;
								break;
						}
						if (supportTransition) {
							deg = 'rotate(' + file.rotation + 'deg)';
							$wrap.css({
								'-webkit-transform': deg,
								'-mos-transform': deg,
								'-o-transform': deg,
								'transform': deg
							});
						} else {
							$wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
						}
					});
					$li.appendTo($queue);
				}
				// 负责view的销毁
				function removeFile(file) {
					var $li = $('#'+file.id);
					delete percentages[file.id];
					updateTotalProgress();
					$li.off().find('.file-panel').off().end().remove();
				}

				function updateTotalProgress() {
					var loaded = 0,
						total = 0,
						spans = $progress.children(),
						percent;

					$.each(percentages, function(k, v) {
						total += v[0];
						loaded += v[0] * v[1];
					});

					percent = total ? loaded / total : 0;

					spans.eq(0).text(Math.round(percent * 100) + '%');
					spans.eq(1).css('width', Math.round(percent * 100) + '%');
					updateStatus();
				}

				function updateStatus() {
					var text = '', stats;
					if (state === 'ready') {
						if($this.mode == '') {
							var mode = $this.modalobj.find('.nav-pills li.active').attr('data-mode');
						} else {
							var mode = $this.mode;
						}
						var acid = $this.acid;
						if(!$this.flag) {
							uploader.option('server', uploader.option('server') + 'acid=' + acid + '&mode=' + mode + '&types=' + $this.type);
							$this.flag = 1;
						}
						text = '选中' + fileCount + '个文件，共' + WebUploader.formatSize(fileSize) + '。';
					} else if (state === 'confirm') {
						stats = uploader.getStats();
						if (stats.uploadFailNum) {
							text = '已上传'+stats.successNum+'个文件,'+stats.uploadFailNum+'个文件上传失败，<a class="retry" href="#">重新上传</a>失败文件或<a class="ignore" href="#">忽略</a>'
						}
					} else {
						stats = uploader.getStats();
						text = '共'+fileCount+'个（'+WebUploader.formatSize(fileSize)+'），已上传' + stats.successNum + '个';

						if (stats.uploadFailNum) {
							text += '，失败' + stats.uploadFailNum + '个';
						}
					}

					$info.html(text);
				}

				function setState(val) {
					var file, stats;
					if (val === state) {
						return;
					}
					$upload.removeClass('state-' + state);
					$upload.addClass('state-' + val);
					state = val;
					switch (state) {
						case 'pedding':
							$placeHolder.removeClass('element-invisible');
							$queue.hide();
							$statusBar.addClass('element-invisible');
							uploader.refresh();
							break;
						case 'ready':
							$placeHolder.addClass('element-invisible');
							$('#wechat_filePicker2').removeClass('element-invisible');
							$queue.show();
							$statusBar.removeClass('element-invisible');
							uploader.refresh();
							break;
						case 'uploading':
							$('#wechat_filePicker2').addClass('element-invisible');
							$progress.show();
							$upload.text('暂停上传');
							break;
						case 'paused':
							$progress.show();
							$upload.text('继续上传');
							break;
						case 'confirm':
							$progress.hide();
							$upload.text('确认使用').addClass('disabled');
							stats = uploader.getStats();
							if (stats.successNum && !stats.uploadFailNum) {
								setState('finish');
								return;
							}
							break;
						case 'finish':
							$( '#wechat_filePicker2' ).removeClass( 'element-invisible' );
							$upload.text( '确认使用' ).removeClass( 'disabled' );
							stats = uploader.getStats();
							if (stats.successNum) {
								// alert('上传成功');
							} else {
								// 没有成功的文件，重设
								state = 'done';
								location.reload();
							}
							break;
					}
					updateStatus();
				}

				uploader.onUploadProgress = function(file, percentage) {
					var $li = $('#'+file.id),
						$percent = $li.find('.progress span');
					$percent.css('width', percentage * 100 + '%');
					percentages[file.id][1] = percentage;
					fileid = file.id;
					updateTotalProgress();
				};

				uploader.onFileQueued = function(file) {
					fileCount++;
					fileSize += file.size;

					if (fileCount === 1) {
						$placeHolder.addClass('element-invisible');
						$statusBar.show();
					}

					addFile(file);
					setState('ready');
					updateTotalProgress();
				};

				uploader.onFileDequeued = function(file) {
					fileCount--;
					fileSize -= file.size;

					if (!fileCount) {
						setState('pedding');
					}

					removeFile(file);
					updateTotalProgress();

					$('#wechat_filePicker2').removeClass('element-invisible');
					$('#wechat_filePicker2').next().removeClass('disabled');
				};

				uploader.on('all', function(type) {
					var stats;
					switch(type) {
						case 'uploadFinished':
							setState('confirm');
							break;
						case 'startUpload':
							setState('uploading');
							break;
						case 'stopUpload':
							setState('paused');
							break;
					}
				});

				uploader.on('uploadSuccess', function(file, result) {
					if (result.message){
						alert(result.message);
					}

					if (!result.message){
						accept++;
						$this.images.push(result);
						if(result.width){
							$('#'+file.id).append('<span class="success" style="line-height: 50px;">'+result.width +'x'+ result.height +'</span>');
						} else if(result.size) {
							$('#'+file.id).append('<span class="success" style="line-height: 50px;">'+result.size+'</span>');
						}
						$('.accept').text('成功上传 '+accept+' 个文件');

						if(!$this.options.multi){
							$this.modalobj.find('#wechat_upload').find('.btn.btn-primary').click();
						}
					}
				});

				uploader.on('uploadFinished', function() {
					if($this.images.length > 0){
						$this.modalobj.find('#wechat_upload').find('.btn.btn-primary').click();
					}
				});

				uploader.onError = function(code) {
					if(code == 'Q_EXCEED_SIZE_LIMIT'){
						alert('错误信息: 文件大于 1M 无法上传.');
						return
					}
					if(code == 'F_DUPLICATE'){
						alert('错误信息: 不能重复上传文件.');
						return
					}
					alert('Eroor: ' + code);
				};

				$upload.on('click', function() {
					if ($(this).hasClass('disabled')) {
						return false;
					}
					if (state === 'ready') {
						uploader.upload();
					} else if (state === 'paused') {
						uploader.upload();
					} else if (state === 'uploading') {
						uploader.stop();
					}
				});

				$info.on('click', '.retry', function() {
					uploader.retry();
				});

				$info.on('click', '.ignore', function() {
					// alert('todo');
				});

				$upload.addClass('state-' + state);
				updateTotalProgress();
			}

			this.modalobj.find('#wechat_upload').find('button.btn-primary').off('click');
			this.modalobj.find('#wechat_upload').find('button.btn-primary').on('click', function(){
				if ($this.images.length > 0){
					if($.isFunction($this.options.callback)){
						if($this.options.multi){
							$this.options.callback($this.images);
						} else {
							$this.options.callback($this.images[0]);
						}
						$this.hide();
					}
				} else {
					alert('未选择任何文件.');
				}
			});
		},

		'reset_upload' : function(){},

		'template' : function() {
			var template = {};
			var $this = this;
			template['upload'] =
				'<div role="tabpanel" class="tab-pane upload" id="wechat_upload">'+
					'	<div id="wechat_uploader" class="uploader">'+
					'		<div class="queueList">'+
					'			<div id="wechat_dndArea" class="placeholder">'+
					'				<div id="wechat_filePicker"></div>'+
					'				<p>或将文件拖到这里，单次最多可选 ' + ($this.options.multi ? 5 : 1) + '个文件</p>'+
					'			</div>'+
					'		</div>'+
					'		<div class="statusBar" style="line-height: 30px; margin-bottom: -15px;">'+
					'			<div class="progress">'+
					'				<span class="text">0%</span>'+
					'				<span class="percentage"></span>'+
					'			</div>'+
					'			<div class="info"></div>'+
					'			<div class="accept"></div>'+
					'			<div class="btns">'+
					( $this.options.multi ? '				<div id="wechat_filePicker2" class="btn btn-primary" style="margin-top: 4px; color: white;"></div>' : '' )+
					'				<div class="uploadBtn btn btn-primary" style="margin-top: 4px;float:right">确认使用</div>'+
					'				<div class="modal-button-upload" style="float: right; margin-left: 5px; margin-right: -20px; display: none;">'+
					//'					<button type="button" class="btn btn-default reset">清空</button>'+
					'					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
					'					<button type="button" class="btn btn-primary">确认</button>'+
					'				</div>'+
					'			</div>'+
					'		</div>'+
					'	</div>'+
					'</div>';

			template['browser'] =
					'<div role="tabpanel" class="tab-pane browser" id="wechat_browser">\n' +
					'	<div class="browser-content" style="height:340px"></div>\n' +
					'	<div class="modal-footer">\n' +
					'		<div style="float: left;">\n' +
					'			<nav id="image-list-pager">\n' +
					'			</nav>\n' +
					'		</div>\n' +
					'		<div style="float: right;">\n' +
					'			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>\n' +
					'			<button type="button" class="btn btn-primary">确认</button>\n' +
					'		</div>\n' +
					'	</div>\n' +
					'</div>';

			template['localDialogLi'] =
				'<div class="img-list clearfix file-browser">\n' +
				'<%_.each(items, function(item) {%> \n' +
				'<div class="img-item" attachid="<%=item.id%>" title="<%=item.filename%>">\n' +
					'	<div class="btnClose"><a href="javascript:;"><i class="fa fa-times"></i></a></div>'+
					'<%if(item.type == "image" || item.type == "thumb"){%>' +
					'	<div class="img-container" style="background-image: url(\'<%=item.url%>\');">\n' +
					'		<div class="img-meta"><%=item.width%>*<%=item.height%></div>\n' +
					'		<div class="select-status"><span></span></div>\n' +
					'	</div>\n' +
					'<%}%>' +
					'<%if(item.type == "voice" || item.type == "video"){%>' +
					'	<div class="img-container" style="background-image: url(\'./resource/images/icon_audio.png\');">\n' +
					'		<div class="img-meta"><%=item.filename%></div>\n' +
					'		<div class="select-status"><span></span></div>\n' +
					'	</div>\n' +
					'<%}%>' +
				'</div>\n' +
				'<%});%>\n' +
				'</div>';

			template['video'] =
				'<div role="tabpanel" class="tab-pane video" id="video">'+
					'	<div class="row" style="height:310px">'+
					'		<iframe width="0" height="0" id="fileUploader_video_target" name="fileUploader_video_target" style="display:none;"></iframe>' +
					'		<form class="form-horizontal" id="fileUploader_video_form" name="fileUploader_video_form" action="" enctype="multipart/form-data" method="post" target="fileUploader_video_target">'+
					'			<div class="form-group">' +
					'				<label class="col-xs-12 col-sm-2 control-label">类型</label>' +
					'				<div class="col-sm-10">' +
					'					<label class="radio-inline"><input type="radio" name="mode" value="perm" checked> 永久素材</label>' +
					'					<label class="radio-inline"><input type="radio" name="mode" value="temp"> 临时素材</label>' +
					'				</div>' +
					'			</div>' +
					'			<div class="form-group">' +
					'				<label class="col-xs-12 col-sm-2 control-label">上传视频</label>' +
					'				<div class="col-sm-10">' +
					'					<input type="file" name="file">'+
					'				</div>' +
					'			</div>' +
					'			<div class="form-group">' +
					'				<label class="col-xs-12 col-sm-2 control-label">视频标题</label>' +
					'				<div class="col-sm-10">' +
					'					<input type="text" name="title" class="form-control">'+
					'				</div>' +
					'			</div>' +
					'			<div class="form-group">' +
					'				<label class="col-xs-12 col-sm-2 control-label">视频描述</label>' +
					'				<div class="col-sm-10">' +
					'					<textarea name="introduction" class="form-control"></textarea>'+
					'				</div>' +
					'			</div>' +
					'			<div class="form-group">' +
					'				<div class="col-sm-10 col-sm-offset-2">' +
					'					<div class="alert alert-warning" role="alert">注 :永久视频只支持rm/rmvb/wmv/avi/mpg/mpeg/mp4格式,大小不超过为20M.</div>' +
					'				</div>' +
					'			</div>' +
					'		</form>' +
					'	</div>'+
					'	<div class="modal-footer" style="padding: 12px 0px 0px;">'+
					'		<div style="float: left;">'+
					'			<span class="browser-info"><span>'+
					'		</div>'+
					'		<div style="float: right;">'+
					'			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'+
					'			<button type="button" class="btn btn-primary">确认</button>'+
					'		</div>'+
					'	</div>'+
					'</div>';

			return template;
		}
	};

	return fileUploader;
});