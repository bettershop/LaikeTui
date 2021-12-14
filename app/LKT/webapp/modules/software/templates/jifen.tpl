<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}


<title>关注设置积分</title>
</head>
<body>

<nav class="breadcrumb">
    小程序 <span class="c-gray en">&gt;</span> 
    积分参数 
</nav>



<div class="pd-20">
    <form name="form1" action="index.php?module=software&action=jifen" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <div class="row cl">
            <label class="form-label col-2">首次关注送积分：</label>
            <div class="formControls col-10 " >
                <input type="number" class="input-text" value="{$r[0]->jifennum}" placeholder="" id="" name="jifennum">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">积分转让开关：</label>
            <div class="formControls col-10 " >
            <input type="radio" name="switch" value="0" {if $r[0]->switch ==0} checked="checked"{/if}>关闭 &nbsp;&nbsp; 
            <input type="radio" name="switch" value="1" {if $r[0]->switch ==1} checked="checked"{/if}>开启 &nbsp;&nbsp; 
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">积分规则：</label>
            <div class="formControls col-10"> 
                <script id="editor" type="text/plain" name="rule" style="width:100%;height:400px;">{$r[0]->rule}</script>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"></label>
          <div class="formControls col-10" >

            <button class="btn btn-primary radius "  type="submit" name="Submit">提 交</button>

            <button class="btn btn-secondary radius"  type="reset" name="reset">重 写</button>

          </div>

        </div>

    </form>
</div>


<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css"/>
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
<script language="javascript"  src="style/js/check.js"> </script>
<script src="style/js/vue.min.js"></script>

{literal}
<script>

$(function() {
    $("#imgurls").change(function() {
        var files = this.files;
        if (files && files.length > 5) {
            alert("超过5张");
            this.value = "" //删除选择
            // $(this).focus(); //打开选择窗口
        }
    })
})
var isShow = false
$(function(){
    var ue = UE.getEditor('editor');
    $("#islabel1").hide()
    $("#islabel2").hide()
    $("#islabel3").hide()
    $("#ischange").click(function(e) {
        isShow = !isShow
        if(isShow){
            $('#iaaaa').remove()
            $("#islabel1").show()
            $("#islabel2").show()
            $("#islabel3").show()
            return
        }       

        $("#islabel1").hide()
        $("#islabel2").hide()
        $("#islabel3").hide()
        $('#iaaaa').hide()
        $("#ishidden").append(`<input type="hidden" name="is_distribution" value="0" id="iaaaa">`)
    })
        var distribution =$("input[name='distribution']").val();
    if(distribution == 1){
        $("#islabel1").show()
        $("#islabel2").show()
        $("#islabel3").show()
    }

});

{/literal}
</script>

</body>
</html>