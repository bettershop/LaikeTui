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
<link href="style/css/style.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<title>系统参数</title>
{literal}


<style type="text/css">

@font-face {font-family: "iconfont";
  src: url('iconfont.eot?t=1530949902201'); /* IE9*/
  src: url('iconfont.eot?t=1530949902201#iefix') format('embedded-opentype'), /* IE6-IE8 */
  url('data:application/x-font-woff;charset=utf-8;base64,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') format('woff'),
  url('iconfont.ttf?t=1530949902201') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
  url('iconfont.svg?t=1530949902201#iconfont') format('svg'); /* iOS 4.1- */
}

.iconfont {
  font-family:"iconfont" !important;
  font-size:30px;
  font-style:normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.icon-zhengshu-copy:before { content: "\e640"; }

.isbad{
    border: 2px solid red;
}
.btn1{
    	width: 80px;
     	height: 40px;
     	line-height: 40px;
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    float: left;
	    color: #6a7076;
	    background-color: #fff;
    }
    .btn1:hover{
    	text-decoration: none;
    }
</style>

{/literal}
</head>

<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 配置管理 
	<span class="c-gray en">&gt;</span> 积分设置 <i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container" style="padding-top: 0px;">
    <div style="margin-bottom: 20px;">
    		<a href="index.php?module=score&action=setscore" class="btn1" style="background-color: #62b3ff;width: 100px;color: #fff;">积分参数设置</a>
        
        <div class="clearfix" style="margin-top: 5px;"></div>
    </div>
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
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="button" name="savemsg" onclick="savevalues()" ><i class="Hui-iconfont">&#xe632;</i>修改</button>
            </div>
        </div>
    </form>
    <div style="margin-left: 110px;">              
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
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
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
   //console.log(retbuy);
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
         //console.log(data);
         $.ajax({
               url:"index.php?module=sign&action=setscore",
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