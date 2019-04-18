require.config({	
	baseUrl: 'resource/js/app',	
	paths: {		'jquery': '../lib/jquery-1.11.1.min',		
	'jquery.ui': '../lib/jquery-ui-1.10.3.min',		
	'jquery.caret': '../lib/jquery.caret',	
	'jquery.jplayer': '../../components/jplayer/jquery.jplayer.min',	
	'jquery.zclip': '../../components/zclip/jquery.zclip.min',		
	'bootstrap': '../lib/bootstrap.min',		
	'bootstrap.switch': '../../components/switch/bootstrap-switch.min',		
	'angular': '../lib/angular.min',		
	'angular.sanitize': '../lib/angular-sanitize.min',		
	'underscore': '../lib/underscore-min',		
	'chart': '../lib/chart.min',		
	'moment': '../lib/moment',		
	'filestyle': '../lib/bootstrap-filestyle.min',		
	'datetimepicker': '../../components/datetimepicker/jquery.datetimepicker',		
	'daterangepicker': '../../components/daterangepicker/daterangepicker',		
	'colorpicker': '../../components/colorpicker/spectrum',		
	'map': 'http://api.map.baidu.com/getscript?v=2.0&ak=F51571495f717ff1194de02366bb8da9&services=&t=20140530104353',		
	'editor': '../../components/tinymce/tinymce.min',		
	'kindeditor':'../../components/kindeditor/lang/zh_CN',		
	'kindeditor.main':'../../components/kindeditor/kindeditor-min',		
	'css': '../lib/css.min',		
	'webuploader' : '../../components/webuploader/webuploader.min',		
	'json2' : '../lib/json2',		
	'wapeditor' : './wapeditor',		
	'jquery.wookmark': '../lib/jquery.wookmark.min',		
	'validator': '../lib/bootstrapValidator.min',		
	'select2' : '../../components/select2/zh-CN',		
	'clockpicker': '../../components/clockpicker/clockpicker.min',		
	'jquery.qrcode': '../lib/jquery.qrcode.min',		
	'raty': '../lib/raty.min',	
	'raty': '../lib/raty.min',	
	'echarts':'../echarts',
    'map':'../map.chain'
	},	
	shim:{		
		'jquery.ui': {			
			exports: "$",			
			deps: ['jquery']		
		},		
		'jquery.caret': {			
			exports: "$",			
			deps: ['jquery']		
		},		
		'jquery.jplayer': {			
			exports: "$",			
			deps: ['jquery']		
		},		
		'bootstrap': {			exports: "$",			deps: ['jquery']		},		'bootstrap.switch': {			exports: "$",			deps: ['bootstrap', 'css!../../components/switch/bootstrap-switch.min.css']		},		'angular': {			exports: 'angular',			deps: ['jquery']		},		'angular.sanitize': {			exports: 'angular',			deps: ['angular']		},		'emotion': {			deps: ['jquery']		},		'chart': {			exports: 'Chart'		},		'filestyle': {			exports: '$',			deps: ['bootstrap']		},		'daterangepicker': {			exports: '$',			deps: ['bootstrap', 'moment', 'css!../../components/daterangepicker/daterangepicker.css']		},		'datetimepicker' : {			exports : '$',			deps: ['jquery', 'css!../../components/datetimepicker/jquery.datetimepicker.css']		},		'kindeditor': {			deps: ['kindeditor.main', 'css!../../components/kindeditor/themes/default/default.css']		},		'colorpicker': {			exports: '$',			deps: ['css!../../components/colorpicker/spectrum.css']		},		'map': {			exports: 'BMap'		},		'json2': {			exports: 'JSON'		},		'webuploader': {			deps: ['css!../../components/webuploader/webuploader.css', 'css!../../components/webuploader/style.css']		},		'wapeditor' : {			exports : 'angular',			deps: ['angular.sanitize', 'jquery.ui', 'underscore', 'fileUploader', 'json2', 'datetimepicker']		},		'jquery.wookmark': {			exports: "$",			deps: ['jquery']		},		'validator': {			exports: "$",			deps: ['bootstrap']		},		'select2': {			deps: ['css!../../components/select2/select2.min.css', './resource/components/select2/select2.min.js']		},		'clockpicker': {			exports: "$",			deps: ['css!../../components/clockpicker/clockpicker.min.css', 'bootstrap']		},		
		'jquery.qrcode': {			
			exports: "$",			
			deps: ['jquery']		
		},
		 "echarts":{
             exports: "$",
            deps: ['jquery']
        },
        "map":{
             exports: "$",
            deps: ['jquery']
        }	
	}});