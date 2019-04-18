(function(window) {
	var util = {};
	util.tomedia = function(src, forcelocal){
		if(src.indexOf('http://') == 0 || src.indexOf('https://') == 0 || src.indexOf('./resource') == 0) {
			return src;
		} else if(src.indexOf('./addons') == 0) {
			var url=window.document.location.href; 
			var pathName = window.document.location.pathname; 
			var pos = url.indexOf(pathName); 
			var host = url.substring(0,pos);
			if (src.substr(0,1)=='.') {
				src=src.substr(1);
			}
			return host + src;
		} else {
			if(!forcelocal) {
				return window.sysinfo.attachurl + src;
			} else {
				return window.sysinfo.attachurl_local + src;
			}
		}
	};
	util.clip = function(elm, str) {
		if(elm.clip) {
			return;
		}
		require(['jquery.zclip'], function(){
			$(elm).zclip({
				path: './resource/components/zclip/ZeroClipboard.swf',
				copy: str,
				afterCopy: function(){
					var obj = $('<em> &nbsp; <span class="label label-success"><i class="fa fa-check-circle"></i> 复制成功</span></em>');
					var enext = $(elm).next().html();
					if (!enext || enext.indexOf('&nbsp; <span class="label label-success"><i class="fa fa-check-circle"></i> 复制成功</span>')<0) {
						$(elm).after(obj);
					}
					setTimeout(function(){
						obj.remove();
					}, 2000);
				}
			});
			elm.clip = true;
		});
	};
	
	util.colorpicker = function(elm, callback) {
		require(['colorpicker'], function(){
			$(elm).spectrum({
				className : "colorpicker",
				showInput: true,
				showInitial: true,
				showPalette: true,
				maxPaletteSize: 10,
				preferredFormat: "hex",
				change: function(color) {
					if($.isFunction(callback)) {
						callback(color);
					}
				},
				palette: [
					["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)", "rgb(153, 153, 153)","rgb(183, 183, 183)",
					"rgb(204, 204, 204)", "rgb(217, 217, 217)","rgb(239, 239, 239)", "rgb(243, 243, 243)", "rgb(255, 255, 255)"],
					["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
					"rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"],
					["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)",
					"rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)",
					"rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)",
					"rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
					"rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)",
					"rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
					"rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
					"rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
					"rgb(133, 32, 12)", "rgb(153, 0, 0)", "rgb(180, 95, 6)", "rgb(191, 144, 0)", "rgb(56, 118, 29)",
					"rgb(19, 79, 92)", "rgb(17, 85, 204)", "rgb(11, 83, 148)", "rgb(53, 28, 117)", "rgb(116, 27, 71)",
					"rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)",
					"rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
				]
			});
		});
	}
	
	util.uploadMultiPictures = function(callback, options){
		
		var opts = {
			type :'image',
			tabs : {
				'upload' : 'active',
				'browser' : '',
				'crawler' : ''
			},
			path : '',
			direct : false,
			multi : true,
			dest_dir : ''
		};
		
		opts = $.extend({}, opts, options);
		
		require(['jquery', 'fileUploader'], function($, fileUploader){
			fileUploader.show(function(images){
				if(images.length > 0){
					for (i in images) {
						images[i].filename = images[i].attachment;
					}
					if($.isFunction(callback)){
						callback(images);
					}
				}
			}, opts);
		});
	}
	
	util.editor = function(elm, callback){
		var id = elm.id;
		if(!id) {
			id = 'editor-' + Math.random();
			elm.id = id;
		}
		if(!elm.editor) {
			require(['editor'], function(){
				var editor = tinyMCE.createEditor(id, {
					plugins: [
						"advlist autolink lists link image multiimage charmap print preview hr anchor pagebreak",
						"searchreplace wordcount visualblocks visualchars code fullscreen",
						"insertdatetime media nonbreaking save table contextmenu directionality",
						"emoticons template paste textcolor"
					],
					toolbar1: "undo redo | bold italic | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | preview fullscreen",
					toolbar2: "code print | styleselect fontsizeselect link image multiimage media emoticons ",
					language: 'zh_CN',
					paste_webkit_styles: 'all',
					paste_preprocess: function(plugin, args) {
						args.content = args.content.replace(/!important/g, '');
					},
					fontsize_formats: "8pt 10pt 12pt 14pt 18pt 24pt 36pt",
					menubar: false
				});
				elm.editor = editor;
				editor.render();
				if($.isFunction(callback)) {
					callback(elm, editor);
				}
			});
		}
		return {
			getContent : function(){
				if(elm.editor) {
					return elm.editor.getContent();
				} else {
					return '';
				}
			}
		};
	};
	
	// target dom 对象
	util.emotion = function(elm, target, callback) {
		require(['jquery.caret', 'bootstrap', 'css!../../components/emotions/emotions.css'],function($){
			$(function() {
				var emotions_html = '<table class="emotions" cellspacing="0" cellpadding="0"><tbody><tr><td><div class="eItem" style="background-position:0px 0;" data-title="微笑" data-code="微笑" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/0.gif"></div></td><td><div class="eItem" style="background-position:-24px 0;" data-title="撇嘴" data-code="撇嘴" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/1.gif"></div></td><td><div class="eItem" style="background-position:-48px 0;" data-title="色" data-code="色" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/2.gif"></div></td><td><div class="eItem" style="background-position:-72px 0;" data-title="发呆" data-code="发呆" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/3.gif"></div></td><td><div class="eItem" style="background-position:-96px 0;" data-title="得意" data-code="得意" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/4.gif"></div></td><td><div class="eItem" style="background-position:-120px 0;" data-title="流泪" data-code="流泪" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/5.gif"></div></td><td><div class="eItem" style="background-position:-144px 0;" data-title="害羞" data-code="害羞" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/6.gif"></div></td><td><div class="eItem" style="background-position:-168px 0;" data-title="闭嘴" data-code="闭嘴" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/7.gif"></div></td><td><div class="eItem" style="background-position:-192px 0;" data-title="睡" data-code="睡" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/8.gif"></div></td><td><div class="eItem" style="background-position:-216px 0;" data-title="大哭" data-code="大哭" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/9.gif"></div></td><td><div class="eItem" style="background-position:-240px 0;" data-title="尴尬" data-code="尴尬" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/10.gif"></div></td><td><div class="eItem" style="background-position:-264px 0;" data-title="发怒" data-code="发怒" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/11.gif"></div></td><td><div class="eItem" style="background-position:-288px 0;" data-title="调皮" data-code="调皮" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/12.gif"></div></td><td><div class="eItem" style="background-position:-312px 0;" data-title="呲牙" data-code="呲牙" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/13.gif"></div></td><td><div class="eItem" style="background-position:-336px 0;" data-title="惊讶" data-code="惊讶" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/14.gif"></div></td></tr><tr><td><div class="eItem" style="background-position:-360px 0;" data-title="难过" data-code="难过" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/15.gif"></div></td><td><div class="eItem" style="background-position:-384px 0;" data-title="酷" data-code="酷" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/16.gif"></div></td><td><div class="eItem" style="background-position:-408px 0;" data-title="冷汗" data-code="冷汗" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/17.gif"></div></td><td><div class="eItem" style="background-position:-432px 0;" data-title="抓狂" data-code="抓狂" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/18.gif"></div></td><td><div class="eItem" style="background-position:-456px 0;" data-title="吐" data-code="吐" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/19.gif"></div></td><td><div class="eItem" style="background-position:-480px 0;" data-title="偷笑" data-code="偷笑" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/20.gif"></div></td><td><div class="eItem" style="background-position:-504px 0;" data-title="可爱" data-code="可爱" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/21.gif"></div></td><td><div class="eItem" style="background-position:-528px 0;" data-title="白眼" data-code="白眼" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/22.gif"></div></td><td><div class="eItem" style="background-position:-552px 0;" data-title="傲慢" data-code="傲慢" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/23.gif"></div></td><td><div class="eItem" style="background-position:-576px 0;" data-title="饥饿" data-code="饥饿" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/24.gif"></div></td><td><div class="eItem" style="background-position:-600px 0;" data-title="困" data-code="困" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/25.gif"></div></td><td><div class="eItem" style="background-position:-624px 0;" data-title="惊恐" data-code="惊恐" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/26.gif"></div></td><td><div class="eItem" style="background-position:-648px 0;" data-title="流汗" data-code="流汗" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/27.gif"></div></td><td><div class="eItem" style="background-position:-672px 0;" data-title="憨笑" data-code="憨笑" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/28.gif"></div></td><td><div class="eItem" style="background-position:-696px 0;" data-title="大兵" data-code="大兵" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/29.gif"></div></td></tr><tr><td><div class="eItem" style="background-position:-720px 0;" data-title="奋斗" data-code="奋斗" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/30.gif"></div></td><td><div class="eItem" style="background-position:-744px 0;" data-title="咒骂" data-code="咒骂" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/31.gif"></div></td><td><div class="eItem" style="background-position:-768px 0;" data-title="疑问" data-code="疑问" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/32.gif"></div></td><td><div class="eItem" style="background-position:-792px 0;" data-title="嘘" data-code="嘘" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/33.gif"></div></td><td><div class="eItem" style="background-position:-816px 0;" data-title="晕" data-code="晕" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/34.gif"></div></td><td><div class="eItem" style="background-position:-840px 0;" data-title="折磨" data-code="折磨" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/35.gif"></div></td><td><div class="eItem" style="background-position:-864px 0;" data-title="衰" data-code="衰" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/36.gif"></div></td><td><div class="eItem" style="background-position:-888px 0;" data-title="骷髅" data-code=":!!!" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/37.gif"></div></td><td><div class="eItem" style="background-position:-912px 0;" data-title="敲打" data-code="敲打" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/38.gif"></div></td><td><div class="eItem" style="background-position:-936px 0;" data-title="再见" data-code="再见" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/39.gif"></div></td><td><div class="eItem" style="background-position:-960px 0;" data-title="擦汗" data-code="擦汗" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/40.gif"></div></td><td><div class="eItem" style="background-position:-984px 0;" data-title="抠鼻" data-code="抠鼻" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/41.gif"></div></td><td><div class="eItem" style="background-position:-1008px 0;" data-title="鼓掌" data-code="鼓掌" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/42.gif"></div></td><td><div class="eItem" style="background-position:-1032px 0;" data-title="糗大了" data-code="糗大了" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/43.gif"></div></td><td><div class="eItem" style="background-position:-1056px 0;" data-title="坏笑" data-code="坏笑" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/44.gif"></div></td></tr><tr><td><div class="eItem" style="background-position:-1080px 0;" data-title="左哼哼" data-code="左哼哼" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/45.gif"></div></td><td><div class="eItem" style="background-position:-1104px 0;" data-title="右哼哼" data-code="右哼哼" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/46.gif"></div></td><td><div class="eItem" style="background-position:-1128px 0;" data-title="哈欠" data-code="哈欠" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/47.gif"></div></td><td><div class="eItem" style="background-position:-1152px 0;" data-title="鄙视" data-code="鄙视" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/48.gif"></div></td><td><div class="eItem" style="background-position:-1176px 0;" data-title="委屈" data-code="委屈" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/49.gif"></div></td><td><div class="eItem" style="background-position:-1200px 0;" data-title="快哭了" data-code="快哭了" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/50.gif"></div></td><td><div class="eItem" style="background-position:-1224px 0;" data-title="阴险" data-code="阴险" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/51.gif"></div></td><td><div class="eItem" style="background-position:-1248px 0;" data-title="亲亲" data-code="亲亲" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/52.gif"></div></td><td><div class="eItem" style="background-position:-1272px 0;" data-title="吓" data-code="吓" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/53.gif"></div></td><td><div class="eItem" style="background-position:-1296px 0;" data-title="可怜" data-code="可怜" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/54.gif"></div></td><td><div class="eItem" style="background-position:-1320px 0;" data-title="菜刀" data-code="菜刀" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/55.gif"></div></td><td><div class="eItem" style="background-position:-1344px 0;" data-title="西瓜" data-code="西瓜" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/56.gif"></div></td><td><div class="eItem" style="background-position:-1368px 0;" data-title="啤酒" data-code="啤酒" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/57.gif"></div></td><td><div class="eItem" style="background-position:-1392px 0;" data-title="篮球" data-code="篮球" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/58.gif"></div></td><td><div class="eItem" style="background-position:-1416px 0;" data-title="乒乓" data-code="乒乓" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/59.gif"></div></td></tr><tr><td><div class="eItem" style="background-position:-1440px 0;" data-title="咖啡" data-code="咖啡" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/60.gif"></div></td><td><div class="eItem" style="background-position:-1464px 0;" data-title="饭" data-code="饭" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/61.gif"></div></td><td><div class="eItem" style="background-position:-1488px 0;" data-title="猪头" data-code="猪头" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/62.gif"></div></td><td><div class="eItem" style="background-position:-1512px 0;" data-title="玫瑰" data-code="玫瑰" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/63.gif"></div></td><td><div class="eItem" style="background-position:-1536px 0;" data-title="凋谢" data-code="凋谢" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/64.gif"></div></td><td><div class="eItem" style="background-position:-1560px 0;" data-title="示爱" data-code="示爱" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/65.gif"></div></td><td><div class="eItem" style="background-position:-1584px 0;" data-title="爱心" data-code="爱心" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/66.gif"></div></td><td><div class="eItem" style="background-position:-1608px 0;" data-title="心碎" data-code="心碎" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/67.gif"></div></td><td><div class="eItem" style="background-position:-1632px 0;" data-title="蛋糕" data-code="蛋糕" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/68.gif"></div></td><td><div class="eItem" style="background-position:-1656px 0;" data-title="闪电" data-code="闪电" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/69.gif"></div></td><td><div class="eItem" style="background-position:-1680px 0;" data-title="炸弹" data-code="炸弹" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/70.gif"></div></td><td><div class="eItem" style="background-position:-1704px 0;" data-title="刀" data-code="刀" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/71.gif"></div></td><td><div class="eItem" style="background-position:-1728px 0;" data-title="足球" data-code="足球" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/72.gif"></div></td><td><div class="eItem" style="background-position:-1752px 0;" data-title="瓢虫" data-code="瓢虫" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/73.gif"></div></td><td><div class="eItem" style="background-position:-1776px 0;" data-title="便便" data-code="便便" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/74.gif"></div></td></tr><tr><td><div class="eItem" style="background-position:-1800px 0;" data-title="月亮" data-code="月亮" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/75.gif"></div></td><td><div class="eItem" style="background-position:-1824px 0;" data-title="太阳" data-code="太阳" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/76.gif"></div></td><td><div class="eItem" style="background-position:-1848px 0;" data-title="礼物" data-code="礼物" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/77.gif"></div></td><td><div class="eItem" style="background-position:-1872px 0;" data-title="拥抱" data-code="拥抱" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/78.gif"></div></td><td><div class="eItem" style="background-position:-1896px 0;" data-title="强" data-code="强" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/79.gif"></div></td><td><div class="eItem" style="background-position:-1920px 0;" data-title="弱" data-code="弱" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/80.gif"></div></td><td><div class="eItem" style="background-position:-1944px 0;" data-title="握手" data-code="握手" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/81.gif"></div></td><td><div class="eItem" style="background-position:-1968px 0;" data-title="胜利" data-code="胜利" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/82.gif"></div></td><td><div class="eItem" style="background-position:-1992px 0;" data-title="抱拳" data-code="抱拳" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/83.gif"></div></td><td><div class="eItem" style="background-position:-2016px 0;" data-title="勾引" data-code="勾引" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/84.gif"></div></td><td><div class="eItem" style="background-position:-2040px 0;" data-title="拳头" data-code="拳头" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/85.gif"></div></td><td><div class="eItem" style="background-position:-2064px 0;" data-title="差劲" data-code="差劲" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/86.gif"></div></td><td><div class="eItem" style="background-position:-2088px 0;" data-title="爱你" data-code="爱你" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/87.gif"></div></td><td><div class="eItem" style="background-position:-2112px 0;" data-title="NO" data-code="NO" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/88.gif"></div></td><td><div class="eItem" style="background-position:-2136px 0;" data-title="OK" data-code="OK" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/89.gif"></div></td></tr><tr><td><div class="eItem" style="background-position:-2160px 0;" data-title="爱情" data-code="爱情" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/90.gif"></div></td><td><div class="eItem" style="background-position:-2184px 0;" data-title="飞吻" data-code="飞吻" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/91.gif"></div></td><td><div class="eItem" style="background-position:-2208px 0;" data-title="跳跳" data-code="跳跳" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/92.gif"></div></td><td><div class="eItem" style="background-position:-2232px 0;" data-title="发抖" data-code="发抖" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/93.gif"></div></td><td><div class="eItem" style="background-position:-2256px 0;" data-title="怄火" data-code="怄火" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/94.gif"></div></td><td><div class="eItem" style="background-position:-2280px 0;" data-title="转圈" data-code="转圈" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/95.gif"></div></td><td><div class="eItem" style="background-position:-2304px 0;" data-title="磕头" data-code="磕头" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/96.gif"></div></td><td><div class="eItem" style="background-position:-2328px 0;" data-title="回头" data-code="回头" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/97.gif"></div></td><td><div class="eItem" style="background-position:-2352px 0;" data-title="跳绳" data-code="跳绳" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/98.gif"></div></td><td><div class="eItem" style="background-position:-2376px 0;" data-title="挥手" data-code="挥手" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/99.gif"></div></td><td><div class="eItem" style="background-position:-2400px 0;" data-title="激动" data-code="激动" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/100.gif"></div></td><td><div class="eItem" style="background-position:-2424px 0;" data-title="街舞" data-code="街舞" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/101.gif"></div></td><td><div class="eItem" style="background-position:-2448px 0;" data-title="献吻" data-code="献吻" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/102.gif"></div></td><td><div class="eItem" style="background-position:-2472px 0;" data-title="左太极" data-code="左太极" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/103.gif"></div></td><td><div class="eItem" style="background-position:-2496px 0;" data-title="右太极" data-code="右太极" data-gifurl="http://res.mail.qq.com/zh_CN/images/mo/DEFAULT2/104.gif"></div></td></tr></tbody></table><div class="emotionsGif" style=""></div>';
				$(elm).popover({
					html: true,
					content: emotions_html,
					placement:"bottom"
				});
				$(elm).one('shown.bs.popover', function(){
					$(elm).next().mouseleave(function(){
						$(elm).popover('hide');
					});
					$(elm).next().delegate(".eItem", "mouseover", function(){
						var emo_img = '<img src="'+$(this).attr("data-gifurl")+'" alt="mo-'+$(this).attr("data-title")+'" />';
						var emo_txt = '/'+$(this).attr("data-code");
						$(elm).next().find(".emotionsGif").html(emo_img);
					});
					$(elm).next().delegate(".eItem", "click", function(){
						$(target).setCaret();
						var emo_txt = '/'+$(this).attr("data-code");
						$(target).insertAtCaret(emo_txt);
						$(elm).popover('hide');
						if($.isFunction(callback)) {
							callback(emo_txt, elm, target);
						}
					});
				});
			});
		});
	};

	util.loading = function() {
		var loadingid = 'modal-loading';
		var modalobj = $('#' + loadingid);
		if(modalobj.length == 0) {
			$(document.body).append('<div id="' + loadingid + '" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true"></div>');
			modalobj = $('#' + loadingid);
			html = 
				'<div class="modal-dialog">'+
				'	<div style="text-align:center; background-color: transparent;">'+
				'		<img style="width:48px; height:48px; margin-top:100px;" src="../attachment/images/global/loading.gif" title="正在努力加载...">'+
				'	</div>'+
				'</div>';
			modalobj.html(html);
		}
		modalobj.modal('show');
		modalobj.next().css('z-index', 999999);
		return modalobj;
	};
	
	util.loaded = function(){
		var loadingid = 'modal-loading';
		var modalobj = $('#' + loadingid);
		if(modalobj.length > 0){
			modalobj.modal('hide');
		}
	}
	
	util.dialog = function(title, content, footer, options) {
		if(!options) {
			options = {};
		}
		if(!options.containerName) {
			options.containerName = 'modal-message';
		}
		var modalobj = $('#' + options.containerName);
		if(modalobj.length == 0) {
			$(document.body).append('<div id="' + options.containerName + '" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true"></div>');
			modalobj = $('#' + options.containerName);
		}
		html = 
			'<div class="modal-dialog">'+
			'	<div class="modal-content">';
		if(title) {
			html +=
			'<div class="modal-header">'+
			'	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
			'	<h3>' + title + '</h3>'+
			'</div>';
		}
		if(content) {
			if(!$.isArray(content)) {
				html += '<div class="modal-body">'+ content + '</div>';
			} else {
				html += '<div class="modal-body">正在加载中</div>';
			}
		}
		if(footer) {
			html +=
			'<div class="modal-footer">'+ footer + '</div>';
		}
		html += '	</div></div>';
		modalobj.html(html);
		if(content && $.isArray(content)) {
			var embed = function(c) {
				modalobj.find('.modal-body').html(c);
			};
			if(content.length == 2) {
				$.post(content[0], content[1]).success(embed);
			} else {
				$.get(content[0]).success(embed);
			}
		}
		return modalobj;
	};
	
	util.message = function(msg, redirect, type){
		if(!redirect && !type){
			type = 'info';
		}
		if($.inArray(type, ['success', 'error', 'info', 'warning']) == -1) {
			type = '';
		}
		if(type == '') {
			type = redirect == '' ? 'error' : 'success';
		}
		
		var icons = {
			success : 'check-circle',
			error :'times-circle',
			info : 'info-circle',
			warning : 'exclamation-triangle'
		};
		var p = '';
		if(redirect && redirect.length > 0){
			if(redirect == 'back'){
				p = '<p>[<a href="javascript:;" onclick="history.go(-1)">返回上一页</a>] &nbsp; [<a href="./?refresh">回首页</a>]</p>';
			} else if(redirect == 'refresh') {
				redirect = location.href;
				p = '<p><a href="' + redirect + '" target="main" data-dismiss="modal" aria-hidden="true">如果你的浏览器在 <span id="timeout"></span> 秒后没有自动跳转，请点击此链接</a></p>';
			} else {
				p = '<p><a href="' + redirect + '" target="main" data-dismiss="modal" aria-hidden="true">如果你的浏览器在 <span id="timeout"></span> 秒后没有自动跳转，请点击此链接</a></p>';
			}
		}
		var content = 
			'			<i class="pull-left fa fa-4x fa-'+icons[type]+'"></i>'+
			'			<div class="pull-left"><p>'+ msg +'</p>' +
			p +
			'			</div>'+
			'			<div class="clearfix"></div>';
		var footer = 
			'			<button type="button" class="btn btn-default" data-dismiss="modal">确认</button>';
		var modalobj = util.dialog('系统提示', content, footer, {'containerName' : 'modal-message'});
		modalobj.find('.modal-content').addClass('alert alert-'+type);
		if(redirect) {
			var timer = '';
			timeout = 3;
			modalobj.find("#timeout").html(timeout);
			modalobj.on('show.bs.modal', function(){doredirect();});
			modalobj.on('hide.bs.modal', function(){timeout = 0;doredirect(); });
			modalobj.on('hidden.bs.modal', function(){modalobj.remove();});
			function doredirect() {
				timer = setTimeout(function(){
					if (timeout <= 0) {
						modalobj.modal('hide');
						clearTimeout(timer);
						window.location.href = redirect;
						return;
					} else {
						timeout--;
						modalobj.find("#timeout").html(timeout);
						doredirect();
					}
				}, 1000);
			}
		}
		modalobj.modal('show');
		return modalobj;
	};
	
	util.map = function(val, callback){
		require(['map'], function(BMap){
			if(!val) {
				val = {};
			}
			if(!val.lng) {
				val.lng = 116.403851;
			}
			if(!val.lat) {
				val.lat = 39.915177;
			}
			var point = new BMap.Point(val.lng, val.lat);
			var geo = new BMap.Geocoder();

			var modalobj = $('#map-dialog');
			if(modalobj.length == 0) {
				var content =
					'<div class="form-group">' +
						'<div class="input-group">' +
							'<input type="text" class="form-control" placeholder="请输入地址来直接查找相关位置">' +
							'<div class="input-group-btn">' +
								'<button class="btn btn-default"><i class="icon-search"></i> 搜索</button>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div id="map-container" style="height:400px;"></div>';
				var footer =
					'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>' +
					'<button type="button" class="btn btn-primary">确认</button>';
				modalobj = util.dialog('请选择地点', content, footer, {containerName : 'map-dialog'});
				modalobj.find('.modal-dialog').css('width', '80%');
				modalobj.modal({'keyboard': false});
				
				map = util.map.instance = new BMap.Map('map-container');
				map.centerAndZoom(point, 12);
				map.enableScrollWheelZoom();
				map.enableDragging();
				map.enableContinuousZoom();
				map.addControl(new BMap.NavigationControl());
				map.addControl(new BMap.OverviewMapControl());
				marker = util.map.marker = new BMap.Marker(point);
				marker.setLabel(new BMap.Label('请您移动此标记，选择您的坐标！', {'offset': new BMap.Size(10,-20)}));
				map.addOverlay(marker);
				marker.enableDragging();
				marker.addEventListener('dragend', function(e){
					var point = marker.getPosition();
					geo.getLocation(point, function(address){
						modalobj.find('.input-group :text').val(address.address);
					});
				});
				function searchAddress(address) {
					geo.getPoint(address, function(point){
						map.panTo(point);
						marker.setPosition(point);
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
						setTimeout(function(){marker.setAnimation(null)}, 3600);
					});
				}
				modalobj.find('.input-group :text').keydown(function(e){
					if(e.keyCode == 13) {
						var kw = $(this).val();
						searchAddress(kw);
					}
				});
				modalobj.find('.input-group button').click(function(){
					var kw = $(this).parent().prev().val();
					searchAddress(kw);
				});
			}
			modalobj.off('shown.bs.modal');
			modalobj.on('shown.bs.modal', function(){
				marker.setPosition(point);
				map.panTo(marker.getPosition());
			});
			
			modalobj.find('button.btn-primary').off('click');
			modalobj.find('button.btn-primary').on('click', function(){
				if($.isFunction(callback)) {
					var point = util.map.marker.getPosition();
					geo.getLocation(point, function(address){
						var val = {lng: point.lng, lat: point.lat, label: address.address};
						callback(val);
					});
				}
				modalobj.modal('hide');
			});
			modalobj.modal('show');
		});
	}; // end of map
	
	util.iconBrowser = function(callback){
		var footer = '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>';
		var modalobj = util.dialog('请选择图标',['./index.php?c=utility&a=icon&callback=selectIconComplete'],footer,{containerName:'icon-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-dialog').css({'width':'70%'});
		modalobj.find('.modal-body').css({'height':'70%','overflow-y':'scroll'});
		modalobj.modal('show');

		window.selectIconComplete = function(ico){
			if($.isFunction(callback)){
				callback(ico);
				modalobj.modal('hide');
			}
		};
	}; // end of icon dialog
	
	util.emojiBrowser = function(callback){
		var footer = '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>';
		var modalobj = util.dialog('请选择表情',['./index.php?c=utility&a=emoji&callback=selectEmojiComplete'],footer,{containerName:'icon-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-dialog').css({'width':'70%'});
		modalobj.find('.modal-body').css({'height':'70%','overflow-y':'scroll'});
		modalobj.modal('show');

		window.selectEmojiComplete = function(emoji){
			if($.isFunction(callback)){
				callback(emoji);
				modalobj.modal('hide');
			}
		};
	}; // end of emoji dialog
	
	util.linkBrowser = function(callback){
		var footer = '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>';
		var modalobj = util.dialog('请选择链接',['./index.php?c=utility&a=link&callback=selectLinkComplete'],footer,{containerName:'link-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-body').css({'height':'300px','overflow-y':'auto' });
		modalobj.modal('show');

		window.selectLinkComplete = function(link){
			if($.isFunction(callback)){
				callback(link);
				modalobj.modal('hide');
			}
		};
	}; // end of icon dialo
	util.pageBrowser = function(callback, page){
		var footer = '';
		var modalobj = util.dialog('',['./index.php?c=utility&a=link&do=page&callback=pageLinkComplete&page='+ page],footer,{containerName:'link-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-body').css({'height':'700px','overflow-y':'auto' });
		modalobj.modal('show');

		window.pageLinkComplete = function(link, page){
			if($.isFunction(callback)){
				callback(link, page);
				if (page == '' || page == undefined) {
					modalobj.modal('hide');
				}
			}
		};
	};
	util.newsBrowser = function(callback, page){
		var footer = '';
		var modalobj = util.dialog('',['./index.php?c=utility&a=link&do=news&callback=newsLinkComplete&page='+ page],footer,{containerName:'link-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-body').css({'height':'700px','overflow-y':'auto' });
		modalobj.modal('show');

		window.newsLinkComplete = function(link, page){
			if($.isFunction(callback)){
				callback(link, page);
				if (page == '' || page == undefined) {
					modalobj.modal('hide');
				}
			}
		};
	};
	util.articleBrowser = function(callback, page){
		var footer = '';
		var modalobj = util.dialog('',['./index.php?c=utility&a=link&do=article&callback=articleLinkComplete&page='+ page],footer,{containerName:'link-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-body').css({'height':'700px','overflow-y':'auto' });
		modalobj.modal('show');

		window.articleLinkComplete = function(link, page){
			if($.isFunction(callback)){
				callback(link, page);
				if (page == '' || page == undefined) {
					modalobj.modal('hide');
				}
			}
		};
	};

	util.phoneBrowser = function(callback, page){
		var footer = '';
		var modalobj = util.dialog('一键拨号',['./index.php?c=utility&a=link&do=phone&callback=phoneLinkComplete&page='+ page],footer,{containerName:'link-container'});
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-body').css({'height':'120px','overflow-y':'auto' });
		modalobj.modal('show');

		window.phoneLinkComplete = function(link, page){
			if($.isFunction(callback)){
				callback(link, page);
				if (page == '' || page == undefined) {
					modalobj.modal('hide');
				}
			}
		};
	};

	util.showModuleLink = function(callback){
		var footer = '';
		var modalobj = util.dialog('模块链接选择',['./index.php?c=utility&a=link&do=modulelink&callback=moduleLinkComplete'], '');
		modalobj.modal({'keyboard': false});
		modalobj.find('.modal-body').css({'height':'700px','overflow-y':'auto' });
		modalobj.modal('show');

		window.moduleLinkComplete = function(link, permission){
			if($.isFunction(callback)){
				callback(link, permission);
				modalobj.modal('hide');
			}
		};

	};

	/**
	 * val : image 值;
	 * callback: 回调函数
	 * base64options: base64(json($options))
	 * options: {tabs: {'browser': 'active', 'upload': '', 'remote': ''}
	 **/
	util.image = function(val, callback, base64options, options) {
		var opts = {
			type :'image',
			direct : false,
			multiple : false,
			path : val,
			dest_dir : '',
			global : false,
			thumb : false,
			width : 0
		};

		opts = $.extend({}, opts, options);
		opts.type = 'image';

		require(['jquery', 'fileUploader'], function($, fileUploader){
			fileUploader.show(function(images){
				if(images){
					if($.isFunction(callback)){
						callback(images);
					}
				}
			}, opts);
		});
	}; // end of image

	util.wechat_image = function(val, callback, options) {
		var opts = {
			type :'image',
			direct : false,
			multiple : false,
			acid : 0,
			path : val,
			dest_dir : ''
		};
		opts = $.extend({}, opts, options);
		require(['jquery', 'wechatFileUploader'], function($, wechatFileUploader){
			wechatFileUploader.show(function(images){
				if(images){
					if($.isFunction(callback)){
						callback(images);
					}
				}
			}, opts);
		});
	};

	util.audio = function(val, callback, base64options, options) {
		var opts = {
			type :'audio',
			direct : false,
			multiple : false,
			path : '',
			dest_dir : ''
		};
		if(val){
			opts.path = val;
		}
		
		opts = $.extend({}, opts, options);
		opts.type = 'audio';
		
		require(['jquery', 'fileUploader'], function($, fileUploader){
			fileUploader.show(function(audios){
				if(audios){
					if($.isFunction(callback)){
						callback(audios);
					}
				}
			}, opts);
		});
		
	}; // end of audio
	
	/*
		打开远程地址
		@params string url 目标远程地址
		@params string title 打开窗口标题，为空则不显示标题。可在返回的HTML定义<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>控制关闭
		@params object options 打开窗口的属性配置，可选项backdrop,show,keyboard,remote,width,height。具体参考bootcss模态对话框的options说明
		@params object events 窗口的一些回调事件，可选项show,shown,hide,hidden,confirm。回调函数第一个参数对话框JQ对象。具体参考bootcss模态对话框的on说明.

		@demo ajaxshow('url', 'title', {'show' : true}, {'hidden' : function(obj) {obj.remove();}});
	*/
	util.ajaxshow = function(url, title, options, events) {

		var defaultoptions = {'show' : true};
		var defaultevents = {};
		var option = $.extend({}, defaultoptions, options);
		var events = $.extend({}, defaultevents, events);

		var footer = (typeof events['confirm'] == 'function' ? '<a href="#" class="btn btn-primary confirm">确定</a>' : '') + '<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a><iframe id="_formtarget" style="display:none;" name="_formtarget"></iframe>';
		var modalobj = util.dialog(title, '正在加载中', footer, {'containerName' : 'modal-panel-ajax'});

		if (typeof option['width'] != 'undeinfed' && option['width'] > 0) {
			modalobj.find('.modal-dialog').css({'width' : option['width']});
		}

		if (events) {
			for (i in events) {
				if (typeof events[i] == 'function') {
					modalobj.on(i, events[i]);
				}
			}
		}
		modalobj.find('.modal-body').load(url, function(){
			$('form.ajaxfrom').each(function(){
				$(this).attr('action', $(this).attr('action') + '&isajax=1&target=formtarget');
				$(this).attr('target', '_formtarget');
			})
		});
		modalobj.on('hidden.bs.modal', function(){modalobj.remove();});
		if (typeof events['confirm'] == 'function') {
			modalobj.find('.confirm', modalobj).on('click', events['confirm']);
		}
		return modalobj.modal(option);
	}; //end of ajaxshow

	util.cookie = {
		'prefix' : '',
		// 保存 Cookie
		'set' : function(name, value, seconds) {
			expires = new Date();
			expires.setTime(expires.getTime() + (1000 * seconds));
			document.cookie = this.name(name) + "=" + escape(value) + "; expires=" + expires.toGMTString() + "; path=/";
		},
		// 获取 Cookie
		'get' : function(name) {
			cookie_name = this.name(name) + "=";
			cookie_length = document.cookie.length;
			cookie_begin = 0;
			while (cookie_begin < cookie_length)
			{
				value_begin = cookie_begin + cookie_name.length;
				if (document.cookie.substring(cookie_begin, value_begin) == cookie_name)
				{
					var value_end = document.cookie.indexOf ( ";", value_begin);
					if (value_end == -1)
					{
						value_end = cookie_length;
					}
					return unescape(document.cookie.substring(value_begin, value_end));
				}
				cookie_begin = document.cookie.indexOf ( " ", cookie_begin) + 1;
				if (cookie_begin == 0)
				{
					break;
				}
			}
			return null;
		},
		// 清除 Cookie
		'del' : function(name) {
			var expireNow = new Date();
			document.cookie = this.name(name) + "=" + "; expires=Thu, 01-Jan-70 00:00:01 GMT" + "; path=/";
		},
		'name' : function(name) {
			return this.prefix + name;
		}
	};//end cookie

	util.wechat_audio = function(val, callback, options) {
		var opts = {
			type :'voice',
			direct : false,
			multiple : false,
			path : '',
			dest_dir : ''
		};
		if(val){
			opts.path = val;
		}
		opts = $.extend({}, opts, options);
		require(['jquery', 'wechatFileUploader'], function($, wechatFileUploader){
			wechatFileUploader.show(function(audios){
				if(audios){
					if($.isFunction(callback)){
						callback(audios);
					}
				}
			}, opts);
		});
	};

	util.coupon = function(callback, options) {
		var opts = {
			type :'all',
			multiple : false
		};
		opts = $.extend({}, opts, options);
		require(['jquery', 'coupon'], function($, coupon){
			coupon.init(function(coupons){
				if(coupons){
					if($.isFunction(callback)){
						callback(coupons);
					}
				}
			}, opts);
		});
	};

	util.material = function(callback, options) {
		var opts = {
			type :'news',
			multiple : false,
			ignore : {}
		};
		opts = $.extend({}, opts, options);
		require(['jquery', 'material'], function($, material){
			material.init(function(material){
				if(material){
					if($.isFunction(callback)){
						callback(material);
					}
				}
			}, opts);
		});
	};

	if (typeof define === "function" && define.amd) {
		define(['bootstrap'], function(){
			return util;
		});
	} else {
		window.util = util;
	}
})(window);