<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>系统参数</title>

</head>

<body>

<nav class="breadcrumb">
    配置管理 <span class="c-gray en">&gt;</span>
    积分设置
</nav>


<div class="page-container" style="padding-top: 0px;">
    
    <form name="form1" action="" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>积分消费比例:</label>
                <div class="formControls col-xs-2 col-sm-1">
                    <select name="bili" class="input-text">
                        
                    </select>
                </div>
                <div class="c-red" class="col-xs-4 col-sm-4">积分 = 1元人民币</div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>积分消费规则: </label>            
            </div>
            <div class="row cl leverd">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">1 级</span> : 单笔订单满</label>
                <div class="formControls col-xs-2 col-sm-1">
                    <input type="number" name="order1" value="" class="input-text" min="1" onblur="modifyValue(event)" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" />
                </div>
                <div class="form-label col-xs-1 col-sm-1">可抵用</div>
                <div class="formControls col-xs-2 col-sm-1">
                    <input type="number" name="score1" value="" class="input-text" min="1" onblur="modifyValue(event)" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" />
                </div>
                <div class="col-xs-3 col-sm-2" style="color:#666;">元人民币的积分</div>
                <button class="btn btn-primary radius" type="button" onclick="addlever()" id="addlever1">&nbsp;添加&nbsp;</button>
            </div>
            <input type="hidden" name="returnbili" value="{$bili}" />
            <input type="hidden" name="returnbuy" value="{$res}" />
            
        </div>
        <div class="row cl">
          <label class="form-label col-xs-2 col-sm-2"></label>
            <div class="formControls col-xs-2 col-sm-1">
                <button id="btn1" class="btn btn-primary radius" type="button" name="savemsg" onclick="savevalues()" >修 改</button>
            </div>
        </div>
    </form>
    <div >              
        <div>
            <h4 class="c-red">消费规则修改说明: </h4>
            <ul>
            <li>当前等级订单额度必须大于上一等级订单金额,对应的积分也是如此.</li>
            <li>为保证商家利益,所设置的积分值对应的人民币不得超过订单金额的20%.</li>
            <li>最高只能添加3个等级.</li>
            </ul>
        </div>
    </div>
  </div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}



{literal}
<script type="text/javascript">
   var retbili = $("input[name=returnbili]").val();
   var retbuy = '{/literal}{$res}{literal}';
   
   
   retbuy = JSON.parse(retbuy);
   
   
   var optarr = ['1','10','50','100'];
   var option = '';
   $.each(optarr,function(index,element){    
      if(retbili == element){
          option += '<option value="'+element+'" selected>'+element+'</option>';
      }else{
         option += '<option value="'+element+'">'+element+'</option>';
      }
   });
   $("select[name=bili]").append(option);
  if(retbuy[0]){
   $("input[name=order1]").val(retbuy[0].ordernum);
   $("input[name=score1]").val(retbuy[0].scorenum);
   
   var a = retbuy.length+1;
  }else{
   var a = 2;
  }
   retbuy.shift();
   var levs = '';
   $.each(retbuy,function(index,element){    
      levs += '<div class="row cl leverd"><label class="form-label col-xs-3 col-sm-3"><span class="c-red">'+element.lever+' 级</span> : 单笔订单满</label><div class="formControls col-xs-2 col-sm-1"><input type="number" name="order'+element.lever+'" value="'+element.ordernum+'" class="input-text" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" min="1" onblur="modifyValue(event)"></div><div class="form-label col-xs-1 col-sm-1">可抵用</div><div class="formControls col-xs-2 col-sm-1"><input type="number" name="score'+element.lever+'" value="'+element.scorenum+'" min="1" class="input-text" onblur="modifyValue(event)" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" /></div><div class="col-xs-3 col-sm-2" style="color:#666;">元人民币的积分</div><button class="btn btn-default radius" type="button" onclick="removelever('+element.lever+')">&nbsp;删除&nbsp;</button></div>';
   });
    $('.leverd:last').after(levs);
  
    function addlever() {
        var node = '<div class="row cl leverd"><label class="form-label col-xs-3 col-sm-3"><span class="c-red">'+a+' 级</span> : 单笔订单满</label><div class="formControls col-xs-2 col-sm-1"><input type="number" name="order'+a+'" value="" class="input-text" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" min="1" onblur="modifyValue(event)"></div><div class="form-label col-xs-1 col-sm-1">可抵用</div><div class="formControls col-xs-2 col-sm-1"><input type="number" name="score'+a+'" value="" min="1" class="input-text" onblur="modifyValue(event)" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" /></div><div class="col-xs-3 col-sm-2" style="color:#666;">元人民币的积分</div><button class="btn btn-default radius" type="button" onclick="removelever('+a+')">&nbsp;删除&nbsp;</button></div>';
        a++;
        if(a > 5){
            $("#addlever1").hide();
        }else{
         $('.leverd:last').after(node);
        }
        
    }
    function removelever(i){
        
        $("input[name=score"+i+"]").parents('.row').remove();
        $("#addlever1").show();
        $('.leverd').each(function (){
            var j = $(this).find("input[name^='order']").attr('name');
            j = j.substr(-1,1);
            if(j > i){
                var levd = $(this).find("span");
                var num = levd.text().substr(0,1)-1;
                levd.text(num + ' 级');
                $(this).find("input[name^='order']").attr('name','order' + num);
                $(this).find("input[name^='score']").attr('name','score' + num);
                $(this).find("button").attr('onclick','removelever(' + num + ')');
            }  
        })
        a = $('.leverd').length + 1;
    }
    
    function modifyValue(event) {
       var target = event.target;
       var val = $(target).val();
       if(val.substr(0,1) === '0' || val.substr(0,1) === '-'){
          $(target).val(val.substr(1));
       }       
    }

    function savevalues(){
       var len = $("input[name^=order]").length;
       var bili = $("select[name=bili]").val();
       var obj = [];
       var data = {};
       for(var i = 0; i < len; i++){  
         var next = parseInt($("input[name^=order]:eq("+i+")").val());
         var prev = parseInt($("input[name^=order]:eq("+(i-1)+")").val());
         var orderbad = 'orderbad'+i;
         var noorder = 'noorder'+i;
           if(i > 0){
              if(next <= prev){      
                 $("input[name^=order]:eq("+i+")").after('<div style="color:red;" id="'+orderbad+'">不合格</div>');
                 obj.push(orderbad);           
              } 
           }
           if($("input[name^=order]:eq("+i+")").val() == ''){
               $("input[name^=order]:eq("+i+")").after('<div style="color:red;" id="'+noorder+'">不能为空</div>');
                 obj.push(noorder);
           }
       }
       for(var j = 0; j < len; j++){
          var next1 = parseInt($("input[name^=score]:eq("+j+")").val());
          var prev1 = parseInt($("input[name^=score]:eq("+(j-1)+")").val());
          var orderVal = parseInt($("input[name^=order]:eq("+j+")").val());
          var scorebad = 'scorebad'+j;
          var noscore = 'noscore'+j;
          var scorebig = 'scorebig'+j; 
         if(j > 0){
              if(next1 <= prev1){               
                 $("input[name^=score]:eq("+j+")").after('<div style="color:red;" id="'+scorebad+'">不合格</div>');
                 obj.push(scorebad);
              }
          }
          if(next1 > Math.floor(orderVal*0.2)){            
              $("input[name^=score]:eq("+j+")").after('<div style="color:red;" id="'+scorebig+'">积分值超过可设置范围</div>');
                 obj.push(scorebig);
          }
          if($("input[name^=score]:eq("+j+")").val() == ''){           
               $("input[name^=score]:eq("+j+")").after('<div style="color:red;" id="'+noscore+'">不能为空</div>');
                 obj.push(noscore);
           }
           data[orderVal] = next1+'~'+(j+1);
       }
       
           setTimeout(function(){
                 for(var k = 0;k < obj.length; k++){      
                     var idattr = document.getElementById(obj[k]);
                     idattr.parentNode.removeChild(idattr);
                     }
                 },2000);
      if(obj.length === 0){
         var str = '{';
          $.each(data,function(index,element){
              str+= '"'+index+'":"'+element+'",';
            });

         str = str.substring(0,(str.length-1));
         str += '}';
         $.ajax({
               url:"index.php?module=pi&p=sign&c=setscore",
               type:"post",
               data:{bili:bili,data:str},
               dataType:"json",
               success:function(data) {
                   if(data.code == 1){
                       layer.alert('修改成功!');                  
                   }
               },
             })

      }
       
    }
     

</script> 
{/literal}
</body>
</html>