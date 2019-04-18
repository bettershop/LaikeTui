
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
<link href="style/ssd.css" rel="stylesheet" type="text/css" />
<script src="style/jquery-1.11.3.js"></script>
<script language="javascript"  src="style/ssd.js"> </script>
<script language="javascript"  src="modpub/js/check.js"> </script>
<script language="javascript"  src="style\layDate-v5.0.9\laydate/laydate.js"> </script>
<script language="javascript"  src="style\js\jquery-3.1.1.min.js"> </script>

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
<style type="text/css">
	input{
		padding: 5px;
		height: 30px!important;
		box-sizing: border-box;
		width: 500px!important;
		border: 1px solid #eee!important;
	}
	.form-label{
		padding-right: 10px;
	}
	.form-horizontal .form-label{
		padding-right: 10px;
	}
	.row .form-label{
		width: 20%!important;
	}
</style>
{/literal}
<title>添加抽奖活动</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 插件管理 <span class="c-gray en">&gt;</span> 抽奖活动 <span class="c-gray en">&gt;</span> 修改抽奖活动 </a></nav>
<div class="pd-20" style="background-color: #fff;margin: 10px;margin-top: 10px;">
    <form name="form1" class="form form-horizontal" method="post" onsubmit="return check(this);">
        <input type="hidden" name="per_money" value="0">
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>活动名称：</label>
             <input type="hidden" name="sid" value="{$mm.id}" />
            <div class="formControls col-4">
                <input type="text" class="input-text" name="huodongname" datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.name}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>商品名称：</label>
            <input type="hidden" name="shoppid" value="{$mm.draw_brandid}" />
        <form name="hh" action="" method="post">
            <span class="search">
                <input class="ww" id="makeInput"  onkeyup="setContent(this,event);" onfocus="setDemo(this,event)" type="text" placeholder="{$mm.product_title}" style="width: 258px;height:40px;">
                <select name="shangpin" class="selectName" id="hh" style="display: none;" onkeyup="getfocus(this,event)" onclick="choose(this)" size="10">
                    {if $res == 1}
                     <option value="">还没有商品，请添加商品</option>
                    {else}
                        {foreach from=$res item = item name=f1}
                            <option value="{$item->id}">{$item->product_title}</option>
                        {/foreach}
                    {/if}
                </select>  
                
            </span>
        </form>
 
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>活动开始时间：</label>
            <div class="layui-inline formControls col-4">
              <input type="text" class="layui-input" id="test1" readonly="readonly"  name="start_time" style="width: 260px;height:40px;" >
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>活动结束时间：</label>
            <div class="layui-inline formControls col-4">
              <input type="text" class="layui-input" id="test2" readonly="readonly"  name="end_time" style="width: 260px;height:40px;" >
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>每团所需人数：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="num" datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.num}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>最少开团数：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="collage_number"  datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.collage_number}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>用户最多参与次数：</label>
            <div class="formControls col-4" >
                <input type="text" class="input-text" name="cishu" datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.cishu}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="price"></span>抽奖所需金额：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="price"  datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.price}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中奖次数：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="spelling_number" datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.spelling_number}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>备注：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="type1" datatype="*6-18" style="width: 260px;height:40px;" value="{$mm.type}">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <div class="col-8 col-offset-2">
                <input type="submit" name="Submit" value="提 交" style="width: 100px!important;" class="btn btn-primary radius Submit">
                <!-- <input type="reset" name="reset" value="重 写"  style="width: 100px!important;" class="btn btn-primary radius"> -->
                <input type="button" name="reset1" value="返回"  style="width: 100px!important;" class="btn btn-primary radius reset1  " onclick="javascript:window.history.back(-1);">
            </div>
        </div>
    </form>
</div>
{literal}
<script type="text/javascript">
laydate.render({
              elem: '#test1'
            });
laydate.render({
              elem: '#test2'
            });
$(".Submit").click(function(){
    var id = $('input[name=sid]').val();
    var huodongname = $('input[name=huodongname]').val();
    var shangpin = $('select[name=shangpin]').val();
    var start_time = $('input[name=start_time]').val();
    var end_time = $('input[name=end_time]').val();
    var num = $('input[name=num]').val();
    var collage_number = $('input[name=collage_number]').val();
    var cishu = $('input[name=cishu]').val();
    var price = $('input[name=price]').val();
    var spelling_number = $('input[name=spelling_number]').val();
    var type = $('input[name=type1]').val();
    var shoppid = $('input[name=shoppid]').val();
    // console.log(shangpin);
    var  draw_brandid = shangpin?shangpin:shoppid;
   $.ajax({
      url:"index.php?module=draw&action=modify",
      type:"post",
      data:{huodongname:huodongname,shangpin:draw_brandid,start_time:start_time,end_time:end_time,num:num,collage_number:collage_number,cishu:cishu,price:price,spelling_number:spelling_number,type:type,id:id},
      dataType:"json",
      success:function (data) {
        console.log(data);
        if(data == 1){
             alert('修改成功！');
            window.history.back(-1); 
          }
      },
   });
});
</script>
{/literal}
</body>
</html>