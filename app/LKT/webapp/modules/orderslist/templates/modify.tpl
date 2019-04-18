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
<script language="JavaScript" >
/*function check(f){
  console.log(f);
  if(Trim(f.color_name.value) == "" ){
    alert('收件人名称不能为空！');
    return false;
  }
  if(Trim(f.color.value) == 0 ){
    alert('收件人电话不能为空！');
    return false;
  }
}*/

function Init()
            {

                var   dropElement1=document.getElementById("Select1");
                var   dropElement2=document.getElementById("Select2");
                var   dropElement3=document.getElementById("Select3");
                RemoveDropDownList(dropElement1);
                RemoveDropDownList(dropElement2);
                RemoveDropDownList(dropElement3);
                var country = $('#Select1').attr('data-id');
                 
                
                var url = "index.php?module=orderslist&action=ajax&GroupID=0";
                ajax(url,function(text){
                    var strs= new Array();
                    strs=text.split("|");
                    for(var i=0; i<strs.length-1;   i++)
                    {
                        var opp= new Array();
                        opp=String(strs[i]).split(",");


                        var   eOption=document.createElement("option");
                        if(opp[1] == country){
                          eOption.selected = true;
                          
                        }
                        eOption.value=opp[1];
                        eOption.text=opp[0];
                        dropElement1.add(eOption);

                    }
                    selectCity();
                });

            }

            function   selectCity()
            {
                var   dropElement1=document.getElementById("Select1");
                var   dropElement2=document.getElementById("Select2");
                var   dropElement3=document.getElementById("Select3");
                var   name=dropElement1.value;

                RemoveDropDownList(dropElement2);
                RemoveDropDownList(dropElement3);
                var city = $('#Select2').attr('data-id');
                if(name!="")
                {

                    var url = "index.php?module=orderslist&action=ajax&GroupID="+name;

                    ajax(url,function(text){
                        var strs= new Array();
                        strs=text.split("|");
                        for(var i=0; i<strs.length-1;   i++)
                        {
                            var opp= new Array();
                            opp=String(strs[i]).split(",");


                            var   eOption=document.createElement("option");
                            if(opp[1] == city){
                              eOption.selected = true;
                            }
                            eOption.value=opp[1];
                            eOption.text=opp[0];
                            dropElement2.add(eOption);

                        }
                       selectCountry();
                    });
                }
            }

            function   selectCountry()
            {

                var   dropElement1=document.getElementById("Select1");
                var   dropElement2=document.getElementById("Select2");
                var   dropElement3=document.getElementById("Select3");
                var   name=dropElement2.value;


                RemoveDropDownList(dropElement3);
                var country = $('#Select3').attr('data-id');
                if(name!="")
                {

                    var url = "index.php?module=orderslist&action=ajax&GroupID="+name;

                    ajax(url,function(text){
                        var strs= new Array();
                        strs=text.split("|");
                        for(var i=0; i<strs.length-1;   i++)
                        {
                            var opp= new Array();
                            opp=String(strs[i]).split(",");


                            var   eOption=document.createElement("option");
                            if(country == opp[1]){
                              eOption.selected = true;
                            }
                            eOption.value=opp[1];
                            eOption.text=opp[0];
                            dropElement3.add(eOption);

                        }

                    });
                }
            }

            function   RemoveDropDownList(obj)
            {
                if(obj)
                {
                    var   len=obj.options.length;
                    if(len>0)
                    {
                        //alert(len);
                        for(var   i=len;i>=1;i--)
                        {
                            obj.remove(i);
                        }
                    }
                }

            }
</script>
{/literal}
<title>修改订单信息</title>
</head>
<body onload="Init();">
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 订单列表 <span class="c-gray en">&gt;</span> 修改订单信息 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=orderslist';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
  <form name="form1" action="index.php?module=orderslist&action=Modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
    <input type="hidden" name="id" value="{$class.sNo}" />
    <input type="hidden" name="sid" value="{$class.id}" />
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>收件人姓名：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" name="name" value="{$class.name}" >
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>联系电话：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" value="{$class.mobile}" name="mobile">
      </div>
    </div>

    <div class="row cl">
      <label class="form-label col-2">收件地址：</label>
      <div class="formControls col-10">
        <select id="Select1" style="height:24px;" name="Select1" onchange="selectCity()" data-id="{$class.sheng}">

                    <option value="" selected="true">省/直辖市</option>
                </select>
                <select id="Select2" style="height:24px;" name="Select2" onchange="selectCountry()" data-id="{$class.shi}">
                    <option value="" selected="true">请选择</option>
                </select>
                <select id="Select3" style="height:24px;" name="Select3"  data-id="{$class.xian}">
                    <option value="" selected="true">请选择</option>
                </select>
      </div>
    </div>

    <div class="row cl">
      <label class="form-label col-2"><span class="c-red"></span>详细地址：</label>
      <div class="formControls col-10">
        <input type="text" class="input-text" value="{$class.address}" name="address">
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
    }
    else{
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