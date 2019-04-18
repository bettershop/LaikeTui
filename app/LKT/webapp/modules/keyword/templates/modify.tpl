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

<script type="text/javascript" src="modpub/js/check.js" > </script>
<script type="text/javascript" src="modpub/js/ajax.js"> </script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 


<title>关键词添加</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 系统管理 <span class="c-gray en">&gt;</span> 关键词列表 <span class="c-gray en">&gt;</span> 关键词修改 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=keyword&action=Index';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
  <form name="form1" action="index.php?module=keyword&action=Modify&id={$id}" class="form form-horizontal" method="post" enctype="multipart/form-data" >
 
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>关键词：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" name="name" value="{$sel}">
      </div>
    </div>
    

    <div class="row cl">
      <div class="col-10 col-offset-5">
        <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 提 交</button>
        <button class="btn btn-secondary radius" type="reset" name="reset"><i class="Hui-iconfont">&#xe632;</i> 重 写</button>
      </div>
    </div>
  </form>
    
</div>

</body>
</html>