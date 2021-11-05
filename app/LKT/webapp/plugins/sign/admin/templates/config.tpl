
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>签到参数</title>
</head>
<body>

<nav class="breadcrumb">
    <a href="index.php?module=pi&p=sign" >签到管理</a> <span class="c-gray en">&gt;</span>
    签到参数 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="page-container">
    <form name="form1" action="index.php?module=pi&p=sign&c=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <input type="hidden" name="plug_ins_id" value="{$plug_ins_id}" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>图片：</label>
                <div class="formControls col-xs-8 col-sm-4"> 
                {if $imgurl != ''}
                    <img id="thumb_url" src='{$uploadImg}{$imgurl}' style="height:100px;width:150">
                {else}
                    <img id="thumb_url" src='{$uploadImg}nopic.jpg' style="height:100px;width:150">
                {/if}
                    <input type="hidden"  id="picurl" name="imgurl" datatype="*" nullmsg="请选择图片"/> 
                    <input type="hidden" name="oldpic" value="{$imgurl}" >
                    <button class="btn btn-success" id="image"  type="button" >选择图片</button>
                    <text>签到后显示的样子</text>
                </div>
                <div class="col-4"> </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>领取的最少积分：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="min_score" value="{$min_score}" class="input-text">
                    <text>如：签到最少领取1积分</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>领取的最大积分：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="max_score" value="{$max_score}" class="input-text">
                    <text>如：签到最大领取10积分</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">连续签到7天：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="continuity_three" value="{$continuity_three}" class="input-text">
                    <text>如：连续签到7天,得5积分 0表示没有连续签到7天奖励</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">连续签到20天：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="continuity_twenty" value="{$continuity_twenty}" class="input-text">
                    <text>如：连续签到20天,得10积分 0表示没有连续签到20天奖励</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">连续签到30天：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="continuity_thirty" value="{$continuity_thirty}" class="input-text">
                    <text>如：连续签到30天,得20积分 0表示没有连续签到30天奖励</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">活动过期删除时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="activity_overdue" value="{$activity_overdue}" class="input-text">
                    <text>如：活动结束2天后,删除该活动. 0 表示不删除</text>
                </div>
                <label class="form-label col-xs-4 col-sm-0">天</label>
            </div>
            
        </div>
        <div class="row cl">
             <label class="form-label col-xs-4 col-sm-4"></label>
            <div class="formControls col-xs-8 col-sm-4">
                <button class="btn btn-primary radius" type="submit" name="Submit">保存</button>
                <button class="btn btn-default radius" type="reset">重置</button>
            </div>
        </div>
    </form>
</div>
</div>


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script>
KindEditor.ready(function(K) {
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
      fileManagerJson : 'kindeditor/php/file_manager_json.php', //网络空间
    });
  //上传背景图片
  K('#image').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        showRemote : true, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K('#picurl').val(),
          clickFn : function(url, title, width, height, border, align) {
          K('#picurl').val(url);
          $('#thumb_url').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
});
</script>
{/literal}

</body>
</html>