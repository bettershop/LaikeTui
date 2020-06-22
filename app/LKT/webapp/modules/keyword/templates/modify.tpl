<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

  {php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>关键词添加</title>
</head>
<body>
<nav class="breadcrumb">
  系统管理 <span class="c-gray en">&gt;</span>
  <a href="index.php?module=keyword">关键词列表</a> <span class="c-gray en">&gt;</span>
  编辑关键词 <span class="c-gray en">&gt;</span>
  <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
  <form name="form1" action="index.php?module=keyword&action=Modify&id={$id}" class="form form-horizontal" method="post" enctype="multipart/form-data" >
 
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>关键词：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" name="name" value="{$sel}">
      </div>
    </div>
    

    <div class="row cl">
      <label class="form-label col-2"></label>
      <div class="formControls col-10">
        <button class="btn btn-primary radius" type="submit" name="Submit">提 交</button>
      </div>
    </div>
  </form>
    
</div>

</body>
</html>