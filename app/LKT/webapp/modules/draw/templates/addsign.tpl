
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

<title>添加抽奖活动</title>
{literal}
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
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 插件管理 <span class="c-gray en">&gt;</span> 抽奖活动 <span class="c-gray en">&gt;</span> 添加新的抽奖活动 </a></nav>
<div class="pd-20" style="background-color: #fff;margin: 10px;margin-top: 0px;">
    <form name="form1" class="form form-horizontal" method="post" onsubmit="return check(this);">
        <input type="hidden" name="per_money" value="0">
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>活动名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="huodongname" datatype="*6-18" >
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>商品名称：</label>
        <!--<form name="hh" action="" method="post">-->
<!--		    <span class="search">-->
			    <input class="ww" id="makeInput" autocomplete="off" onkeyup="setContent(this,event);" onfocus="setDemo(this,event)" type="text" placeholder="请选择或输入">
			    <select name="shangpin" class="selectName" id="hh" style="display: none;" onkeyup="getfocus(this,event)" onclick="choose(this)" size="10" id ="num1">
				  	{if $res == 1}
				     <option value="">还没有商品，请添加商品</option>
				    {else}
				      	{foreach from=$res item = item name=f1}
                       
						    <option value="{$item->id}" >{$item->product_title}</option>
						{/foreach}
				    {/if}
			    </select>  
			    
<!--			</span>-->
		<!--</form>-->
 
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>活动开始时间：</label>
            <div class="layui-inline formControls col-4">
			  <input type="text" class="layui-input" id="test1" readonly="readonly"  name="start_time">
			</div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>活动结束时间：</label>
            <div class="layui-inline formControls col-4">
			  <input type="text" class="layui-input" id="test2" readonly="readonly"  name="end_time">
			</div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>每团所需人数：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" name="num" datatype="*6-18">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>最少开团数：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" name="collage_number" value="" datatype="*6-18">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>用户最多参与次数：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" name="cishu" datatype="*6-18">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="price"></span>抽奖所需金额：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" name="price" value="" datatype="*6-18">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中奖次数：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" name="spelling_number" datatype="*6-18">
           <!--     <span style="color: red;">*  </span><span>中奖次数：</span> -->
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>备注：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="type1" datatype="*6-18">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <div class="col-offset-1">
                <input type="submit" style="width: 100px!important;" name="Submit" value="提 交" class="btn btn-primary radius Submit">
                <input type="reset" style="width: 100px!important;" name="reset" value="重 写"  class="btn btn-primary radius">
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
     if( huodongname==""){
        alert("活动名称不能为空！");
       
        return false;
    }
    if(shangpin==""){
        alert("商品名称不能为空！");
       
        return false;
    }

    if( start_time==""){
        alert("活动开始时间不能为空！");
       
        return false;
    }
    if(end_time==""){
        alert("活动结束时间不能为空！");
       
        return false;
    }
    if( num==""){
        alert("每团所需人数不能为空！");
       
        return false;
    }
    if(collage_number==""){
        alert("最少开团数不能为空！");
       
        return false;
    }
    if( cishu==""){
        alert("用户最多参与次数不能为空！");
       
        return false;
    }
    if(spelling_number==""){
        alert("中奖次数不能为空！");
       
        return false;
    }
     if(type==""){
        alert("备注不能为空！");
       
        return false;
    }
   $.ajax({
      url:"index.php?module=draw&action=addsign",
      type:"post",
      data:{huodongname:huodongname,shangpin:shangpin,start_time:start_time,end_time:end_time,num:num,collage_number:collage_number,cishu:cishu,price:price,spelling_number:spelling_number,type:type},
      dataType:"json",
      success:function (data) {
      	console.log(data);
        if(data == 1){
        	 alert('添加成功！');
           window.parent.location.reload();
		    var index = parent.layer.getFrameIndex(window.name);
		    parent.layer.close(index);

          }
           if(data == 2){
             alert('库存不足,不能设置那么多中奖数');
           window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);

          }
      },
   });
});
</script>
{/literal}
</body>
</html>