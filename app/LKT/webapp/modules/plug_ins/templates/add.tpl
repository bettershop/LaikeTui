<!DOCTYPE HTML>
<html>

<head>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
    content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
  <meta http-equiv="Cache-Control" content="no-siteapp" />

  {php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
  {literal}
  <style>
    .button-conter {
      display: flex;
      justify-content: center;
    }

    #btn1 {
      margin-right: 5px;
    }
  </style>
  <script>
    function change() {
      var type = $('input[name="type"]:checked').val();
      $.ajax({
        type: "GET",
        url: location.href + '&action=ajax&type=' + type,
        data: "",
        success: function (msg) {
          $(".select").html(msg);
        }
      });
    }
  </script>
  {/literal}
  <title>添加插件</title>
</head>

<body>

  <nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=plug_ins">插件列表</a> <span class="c-gray en">&gt;</span>
    上传插件 <span class="c-gray en">&gt;</span>
    <a href="javascript:history.go(-1)">返回</a>
  </nav>


  <div class="pd-20">
    <form id="plugAddForm" name="form1" action="index.php?module=plug_ins&action=add" class="form form-horizontal" method="post" enctype="multipart/form-data" onsubmit="return check(this);">

       <label class="form-label col-4">上传软件包：</label>
      <div class="formControls col-4">
          <input type="file" name="file" accept=".zip"/>
      </div>

      <input type="submit" name="submit" value="上传文件并解压">

      <div style="width: 300px;float: right;">
        <samp style="color: red;font-size: 12px;">安装插件可能会修改数据库，请在安装前备份好数据资料</samp>
      </div>
      
    </form>
  </div>
{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

  {literal}
  <script>
    function check(vm){
      if(vm[0].value != ''){
        return true
      }
      alert('请选择插件安装包！')
      return false
    }
  </script>
  {/literal}
</body>

</html>