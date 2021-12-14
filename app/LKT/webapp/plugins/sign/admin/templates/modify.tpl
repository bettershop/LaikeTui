
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>修改活动</title>
</head>
<body>

<nav class="breadcrumb">
    <a href="index.php?module=pi&p=sign" >签到管理</a> <span class="c-gray en">&gt;</span>
    修改活动 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>



<div class="pd-20">
    <form name="form1" action="index.php?module=pi&p=sign&c=modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <input type="hidden" name="id" value="{$id}">
        <input type="hidden" name="uploadImg" value="{$uploadImg}">
        <input type="hidden" name="status" class="status" value="{$status}">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>活动图片：</label>
            <div class="formControls col-xs-8 col-sm-8"> 
                <img id="thumb_url" src='{$uploadImg}{$image}' style="height:100px;width:150px;">
                <input type="hidden"  id="picurl" name="image" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" value="{$image}">
                <button class="btn btn-success" id="image"  type="button" class="image" >选择图片</button>
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">活动时间：</label>
            <div class="formControls col-10">
                <input id="start_time" name="starttime" value="{$starttime}" class="scinput_s" style="width: 160px; height:26px;font-size: 14px;vertical-align: middle;" placeholder="开始时间">
-
                <input id="end_time" name="endtime" value="{$endtime}" class="scinput_s" style="width: 160px; height:26px;font-size: 14px;vertical-align: middle;" placeholder="结束时间">


                <div>如：选择2018.05.29——2018.05.30,活动日期为 2018.05.29 00:00:00——2018.05.30 23:59:59</div>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">活动介绍：</label>
            <div class="formControls col-10"> 
                <script id="editor" type="text/plain" style="width:100%;height:400px;" name="detail" >{$detail}</script> 
            </div>
        </div>
        <div class="row cl">
             <label class="form-label col-2"></label>
            <div class="formControls col-10">
                <button class="btn btn-primary radius" type="submit" name="Submit">提 交</button>
                <button class="btn btn-secondary radius" type="reset" name="reset">重 写</button>
            </div>
        </div>
    </form>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script>
laydate.render({
          elem: '#start_time', //指定元素
           trigger: 'click',
          type: 'datetime',

        });
       
        laydate.render({
          elem: '#end_time',
          trigger: 'click',
          type: 'datetime'
        });


        


        KindEditor.ready(function (K) {
            var pic = $("#pic").val();

            var editor = K.editor({

                fileManagerJson: 'style/kindeditor/php/file_manager_json.php?dirpath=' + pic, //网络空间
                allowFileManager: true,
                uploadJson: "index.php?module=system&action=uploadImg", //上传功能

            });
            //上传背景图片
            K('#image').click(function () {
                editor.loadPlugin('image', function () {
                    editor.plugin.imageDialog({
                        showRemote : true, //网络图片不开启
                        //showLocal : false, //不开启本地图片上传
                        imageUrl: K('#picurl').val(),
                        clickFn: function (url, title, width, height, border, align) {
                            K('#picurl').val(url);
                            $('#thumb_url').attr("src", url);
                            editor.hideDialog();
                        }
                    });
                });
            });
        });

$(function(){
UE.getEditor('editor');
});
</script>

{/literal}
</body>
</html>