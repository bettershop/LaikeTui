
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
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<script language="javascript"  src="modpub/js/check.js"> </script>
{literal}
<script type="text/javascript">
function check(f){
    if(Trim(f.cat_name.value)==""){
        alert("分类名称不能为空！");
        f.cat_name.value = '';
        return false;
    }
    if(Trim(f.sort.value)==""){
        alert("分类排序号不能为空！");
        f.sort.value = '';
        return false;
    }
    f.sort.value = Trim(f.sort.value);
    if(!/^(([1-9][0-9]*)|0)(\.[0-9]{1,2})?$/.test(f.sort.value)){
        alert("排序号必须为数字，且格式为 ####.## ！");
        f.sort.value = '';
        return false;
    }
    return true;
}
</script>
{/literal}
<title>修改新闻分类</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 新闻管理 <span class="c-gray en">&gt;</span> 新闻分类管理 <span class="c-gray en">&gt;</span> 修改新闻分类 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=newsclass';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <form name="form1" class="form form-horizontal" method="post" onsubmit="return check(this);">
        <input type="hidden" name="cat_id" value="{$cat_id}" />
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>分类名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="cat_name" value="{$cat_name}" datatype="*6-18" style="width: 260px;">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>排序号：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="sort" value="{$sort}" datatype="*6-18" style="width: 260px;">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <div class="col-8 col-offset-4">
                <input type="submit" name="Submit" value="提 交" class="btn btn-primary radius">
                <input type="reset" name="reset" value="重 写"  class="btn btn-primary radius">
            </div>
        </div>
    </form>
</div>
</body>
</html>