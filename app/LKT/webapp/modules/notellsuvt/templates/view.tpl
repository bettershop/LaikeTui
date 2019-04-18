
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="style/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<link href="style/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
{literal}
<script type="text/javascript">
function check(f){
    if(Trim(f.news_title.value) == "" ){
        alert('文章标题不能为空！');
        return false;
    }
}
</script>
{/literal}
<title>用户信息详情</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe62f;</i> 公告管理  <span class="c-gray en">&gt;</span> 消息公告 <span class="c-gray en">&gt;</span> 消息公告详情 
	<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=notellsuvt';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a>
	<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:history.go(-1)" title="返回上一页" ><i class="Hui-iconfont">&#xe66b;</i></a>
</nav>
<div class="pd-20">
    <div class="Huiform">
        <table class="table table-bg" border="1">
            <tbody>
                <tr>
                    <th class="text-r">头像：</th>
                    <td>
                    	<img src="{$user[0]->headimgurl}" style="width: 60px;height:60px;border-radius: 30px;border: 1px solid darkgray;"/>
                    </td>
                </tr>
                <tr>
                    <th width="100" class="text-r"> 用户名：</th>
                    <td>
                    	<span>{$user[0]->user_name}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 性别：</th>
                    <td>
                    	{if $user[0]->sex == '1'}
                    	<span>男</span>
                    	{elseif $user[0]->sex == '2'}
                    	<span>女</span>
                    	{else}
                    	<span>未知</span>
                    	{/if}
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 手机：</th>
                    <td>
                    	<span>{$user[0]->mobile}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">邮箱：</th>
                    <td>
                    	<span>{$user[0]->e_mail}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">地址：</th>
                    <td>
                    	<span>{$user[0]->detailed_address}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">账号：</th>
                    <td>
                    	<span>{$user[0]->Account}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">分享次数：</th>
                    <td>
                    	<span>{$user[0]->share_num}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">余额：</th>
                    <td>
                    	<span>{$user[0]->money}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r">注册时间：</th>
                    <td>
                    	<span>{$user[0]->Register_data}</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="modpub/js/check.js" > </script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="style/lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="style/lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.all.min.js"> </script> 
<script type="text/javascript" src="style/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script> 

{literal}
<script type="text/javascript">
$(function(){
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
    
    $list = $("#fileList"),
    $btn = $("#btn-star"), // 开始上传的id名称
    state = "pending",
    uploader;

    var uploader = WebUploader.create({
        auto: true,
        swf: 'style/lib/webuploader/0.1.5/Uploader.swf',
    
        // 文件接收服务端。
        server: 'http://lib.h-ui.net/webuploader/0.1.5/server/fileupload.php',
    
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
    
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false,
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });
    uploader.on( 'fileQueued', function( file ) {
        var $li = $(
            '<div id="' + file.id + '" class="item">' +
                '<div class="pic-box"><img></div>'+
                '<div class="info">' + file.name + '</div>' +
                '<p class="state">等待上传...</p>'+
            '</div>'
        ),
        $img = $li.find('img');
        $list.append( $li );
    
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
    
            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress-box .sr-only');
    
        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo( $li ).find('.sr-only');
        }
        $li.find(".state").text("上传中");
        $percent.css( 'width', percentage * 100 + '%' );
    });
    
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).addClass('upload-state-success').find(".state").text("已上传");
    });
    
    // 文件上传失败，显示上传出错。
    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传出错");
    });
    
    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress-box').fadeOut();
    });
    uploader.on('all', function (type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }

        if (state === 'uploading') {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on('click', function () {
        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });

    
    
    var ue = UE.getEditor('editor');
    
});

function mobanxuanze(){
    
}
</script>
{/literal}
</body>
</html>