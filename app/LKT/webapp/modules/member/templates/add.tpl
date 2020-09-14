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
<style>
    .button-conter {
        display: flex;
        justify-content: center;
    }
    
    #btn1 {
        margin-right: 5px;
    }
</style>
<script type="text/javascript">
function check(f){
    if(Trim(f.name.value) == "" ){
        appendMask('管理员名称不能为空！','ts');
        return false;
    }
    if(Trim(f.password.value).length < 6){
        appendMask('请输入最少6位密码！','ts');
        return false;
    }
    if(f.password.value != f.password1.value){
        appendMask('确认密码不正确！','ts');
        return false;
    }
}
</script>
{/literal}
<title>添加管理员</title>
</head>
<body>

<nav class="breadcrumb">
    用户管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=member">管理员列表</a> <span class="c-gray en">&gt;</span>  
    添加管理员 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
    <form name="form1" action="index.php?module=member&action=add" class="form form-horizontal" method="post" onsubmit="return check(this);"  enctype="multipart/form-data" >
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>管理员名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" value="" placeholder="" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>管理员密码：</label>
            <div class="formControls col-4">
                <input type="password" class="input-text" value="" placeholder="" name="password">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>确认密码：</label>
            <div class="formControls col-4">
                <input type="password" class="input-text" value="" placeholder="" name="password1">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>角色：</label>
            <div class="formControls col-xs-8 col-sm-4"> <span class="select-box" style="width:150px;">
			<select class="select" name="role" size="1">
				{$list}
			</select>
			</span> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4 "></label>
            <div class="formControls col-8">
                <button class="btn btn-primary radius" id="btn1" type="submit" name="Submit" >
                	提 交
                </button>
            </div>
        </div>
    </form>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">
function appendMask(content,src){
	$("body").append(`
		<div class="maskNew">
			<div class="maskNewContent">
				<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
				<div class="maskTitle">提示</div>	
				<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
				<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
					${content}
				</div>
				<div style="text-align:center;margin-top:30px">
					<button class="closeMask" onclick=closeMask1() >确认</button>
				</div>
				
			</div>
		</div>	
	`)
}
function closeMask1(){
	
	$(".maskNew").remove();
}
</script>
{/literal}
</body>
</html>