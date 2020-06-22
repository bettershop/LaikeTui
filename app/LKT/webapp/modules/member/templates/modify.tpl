
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">
function check(f){
  if(Trim(f.name.value) == "" ){
    alert('管理员名称不能为空！');
    return false;
  }
  if(Trim(f.y_password.value) != ""){
      if(Trim(f.password.value).length < 6){
          alert('请输入最少6位密码！');
          return false;
      }
      if(f.password.value != f.password1.value){
          alert('确认密码不正确！');
          return false;
      }
  }
}


</script>
{/literal}
<title>修改管理员</title>
</head>
<body>
<nav class="breadcrumb">
    用户管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=member">管理员列表</a> <span class="c-gray en">&gt;</span>  
    修改管理员 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
  <form name="form1" action="index.php?module=member&action=modify" class="form form-horizontal" method="post" onsubmit="return check(this);"  enctype="multipart/form-data" >
    <input type="hidden" name="id" value="{$id}" />
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red">*</span>管理员名称：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" name="name" value="{$name}" readonly="readonly">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>密码：</label>
      <div class="formControls col-10">
        <input type="password" class="input-text" value="{$y_password}" name="y_password" >
      </div>
    </div>
    <div class="row cl" id="password">
      <label class="form-label col-2"><span class="c-red">*</span>新密码：</label>
      <div class="formControls col-10">
        <input type="password" class="input-text" value="" placeholder="" name="password" >
      </div>
    </div>
    <div class="row cl" id="password1">
      <label class="form-label col-2"><span class="c-red">*</span>确认密码：</label>
      <div class="formControls col-10">
        <input type="password" class="input-text" value="" placeholder="" name="password1" >
      </div>
    </div>
    {if $admin_type == ''}
      <div class="row cl">
        <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>角色：</label>
        <div class="formControls col-xs-8 col-sm-4"> <span class="select-box" style="width:150px;">
          <select class="select" name="role" size="1">
            {$list}
          </select>
        </span> </div>
      </div>
    {/if}
    <div class="row cl">
      <label class="form-label col-4"></label>
      <div class="formControls col-8">
        <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 提 交</button>
        
      </div>
    </div>
  </form>
    
</div>

{literal}
<script>
$(function(){
    $(".permission-list dt input:checkbox").click(function(){
        $(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
    });
    $(".permission-list2 dd input:checkbox").click(function(){
        var l =$(this).parent().parent().find("input:checked").length;
        var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
        if($(this).prop("checked")){
            $(this).closest("dl").find("dt input:checkbox").prop("checked",true);
            $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
        }else{
            if(l==0){
                $(this).closest("dl").find("dt input:checkbox").prop("checked",false);
            }
            if(l2==0){
                $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
            }
        }
    });
});
</script>
{/literal}
</body>
</html>