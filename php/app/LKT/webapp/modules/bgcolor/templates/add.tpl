
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />



{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}


{literal}
<script type="text/javascript">
function check(f){
    if(Trim(f.color_name.value) == "" ){
        alert('颜色名称不能为空！');
        return false;
    }
    if(Trim(f.color.value) == 0 ){
       alert('颜色代码不能为空！');
       return false;
    }
}
</script>
{/literal}
<title>添加颜色</title>
</head>
<body>


<nav class="breadcrumb">
    小程序 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=bgcolor">前台背景颜色</a> <span class="c-gray en">&gt;</span>  
    添加颜色 <span class="c-gray en">&gt;</span>
    <a href="javascript:history.go(-1)">返回</a>
</nav>



<div class="pd-20">
    <form name="form1" action="index.php?module=bgcolor&action=add" class="form form-horizontal" method="post" onsubmit="return check(this);"  enctype="multipart/form-data" >
        <div class="row cl">
            <label class="form-label col-5"><span class="c-red"></span>颜色名称：</label>
            <div class="formControls col-2">
                <input type="text" class="input-text" value="" placeholder="" name="color_name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-5"><span class="c-red"></span>颜色代码：</label>
            <div class="formControls col-2">
                <input type="text" class="input-text" value="" placeholder="" name="color">
                例如：#00ff00
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-5">排序号：</label>
            <div class="formControls col-2">
                <input type="number" class="input-text" value="1" placeholder="" name="sort">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-5"></label>
            <div class="formControls col-2">
                <button class="btn btn-primary radius" type="submit" name="Submit"> 提 交</button>
                <button class="btn btn-secondary radius" type="reset" name="reset"> 重 写</button>
            </div>
        </div>
    </form>
</div>



{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}



</body>
</html>