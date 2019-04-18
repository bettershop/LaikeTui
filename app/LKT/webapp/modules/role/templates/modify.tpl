
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

{literal}
<style>
  div{
    margin:0;
    line-height:1.5;

  }
  p{
    margin:0;
    padding:5px 10px;
    line-height:1.5;

  }
  .checks{
    padding-left:20px;
  }
  input{
    margin-right: 5px;
  }  
</style>
<script type="text/javascript">
function check(f){
  if(Trim(f.name.value) == "" ){
    alert('角色名称不能为空！');
    return false;
  }
}
</script>
{/literal}
<title>修改角色</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe62d;</i> 管理员管理 <span class="c-gray en">&gt;</span> 角色管理 <span class="c-gray en">&gt;</span> 修改角色 </nav>
<div class="pd-20">
  <form name="form1" action="index.php?module=role&action=modify" class="form form-horizontal" method="post" onsubmit="return check(this);"  enctype="multipart/form-data" >
    <input type="hidden" name="id" value="{$id}" />
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>角色名称：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" name="name" value="{$name}">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-xs-4 col-sm-2">角色权限：</label>
      <div class="J_CheckWrap col-xs-8 col-sm-8" style="border: solid 1px #eee;">
        {foreach from=$list item=item key=key}
          <p class="permission-list" style="background-color: #efefef;">
            <input type="checkbox" class="inputC" id="user-{$key}" value="{$item->level}/{$item->name}" name="permission[]" {if $item->status == 1}checked="checked"{/if}/>
            <label for="user-{$key}"></label>
            {$item->title}
          </p>
          <div class="checks">
            {foreach from=$item->res item=item1 key=key1}
              <p class="cl permission-list2">
                <input type="checkbox" class="inputC" value="{$item1->level}/{$item1->name}/{$item1->module}/{$item1->action}" name="permission[]" {if $item1->status == 1}checked="checked"{/if} id="user-{$key}-{$key1}"/>
                <label for="user-{$key}-{$key1}"></label>
                {$item1->title}
              </p>
              {if $item1->res}
                <div class="checks" style="border-bottom: solid 1px #eee;">
                  {foreach from=$item1->res item=item2 key=key2}
                    <p>
                      <input type="checkbox" class="inputC" value="{$item2->level}/{$item2->name}/{$item2->module}/{$item2->action}" name="permission[]" {if $item2->status == 1}checked="checked"{/if} id="user-{$key}-{$key1}-{$key2}"/>
                      <label for="user-{$key}-{$key1}-{$key2}"></label>
                      {$item2->title}
                    </p>
                    {if $item2->res}
                      <div class="checks" style="border-bottom: solid 1px #eee;">
                        {foreach from=$item2->res item=item3 key=key3}
                        <p style="display: inline-block;">
                          <input type="checkbox" class="inputC" value="{$item3->level}/{$item3->name}/{$item3->module}/{$item3->action}" name="permission[]" {if $item3->status == 1}checked="checked"{/if} id="user-{$key}-{$key1}-{$key2}-{$key3}"/>
                          <label for="user-{$key}-{$key1}-{$key2}-{$key3}"></label>
                          {$item3->title}
                        </p>
                        {/foreach} 
                      </div>
                    {/if}
                  {/foreach} 
                </div>
              {/if}
            {/foreach} 
          </div> 
        {/foreach} 
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

<script type="text/javascript" src="modpub/js/check.js" > </script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/js/checktree.js"></script>
{literal}
<script>
  $(".J_CheckWrap").checktree();
</script>
{/literal}
</body>
</html>