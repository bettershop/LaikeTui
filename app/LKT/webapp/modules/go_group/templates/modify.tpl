
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="style/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="style/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="style/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="style/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="style/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->
{literal}
<style type="text/css">
   .content{
      border:2px red solid;
   }
   .kznone{
    display: none !important;
   }
</style>
{/literal}
<title>编辑活动</title>
</head>
<body>
<div class="page-container">
    <form class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
        <div id="tab-category" class="HuiTab">
            <div class="tabBar cl" style="border-bottom: 2px #ff9900 solid;">       
                <span style="background: #ff9900;">编辑活动</span>
            </div>
            
            <div class="tabCon">
                
                <div class="row cl {if $status > 0}kznone{/if}">
                    <label class="form-label col-xs-4 col-sm-3">
                        <span class="c-red">*</span>
                        拼团名称：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" value="{$list->groupname}" placeholder="" id="{$list->id}" name="groupname" style="width:200px;">
                        <span style="margin-left: 3px;font-size: 10px;color:red;">*必填项</span>
                        <span style="margin-left: 10px;font-size: 10px;color:#666666;">仅后台显示,方便管理</span>
                        
                    </div>
                    
                </div>
                <div class="row cl {if $status > 0}kznone{/if}">
                    <label class="form-label col-xs-4 col-sm-3">拼团人数：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="number" max="5" min="1" class="input-text" value="{$list->man_num}" placeholder="" id="" name="peoplenum" style="width:60px;">
                        <span style="margin-left: 3px;font-size: 10px;color:red;">*必填项</span>
                        <span style="margin-left: 10px;font-size: 10px;color:#666666;">只能为大于0且小于等于50的数字</span>
                    </div>
                    <div class="col-3">
                    </div>
                </div>
                <div class="row cl {if $status > 0}kznone{/if}">
                    <label class="form-label col-xs-4 col-sm-3">拼团时限：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="number" min="1" class="input-text" value="{$list->hour}" placeholder="" id="" name="timehour" style="width:60px;"><span> 小时 </span>
                        <input type="number" max="59" min="0" class="input-text" value="{$list->minute}" placeholder="" id="" name="timeminite" style="width:60px;"><span> 分钟 </span>
                        <span style="margin-left: 3px;font-size: 10px;color:red;">*必填项</span>
                        <span style="margin-left: 10px;font-size: 10px;color:#666666;">建议不要超过24小时,小时数不能小于1,分钟数为0~59之间</span>
                    </div>
                    <div class="col-3">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-3">活动时间：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <div class="{if $status > 0}kznone{/if}">
                          <span>开始时间</span><input type="text" class="input-text" value="{$list->starttime}" placeholder="" id="group_start_time" name="starttime" style="width:150px;margin-left: 10px;">
                          <span style="margin-left: 3px;font-size: 10px;color:red;">*必填项</span>
                        </div>
                        <div style="margin-top: 10px;">
                          <span>结束时间</span>
                          {if $list->overtype==1}
                            <input type="hidden" data-time='{$list->endtime}' name='ischang'>
                          {else}
                            <input type="hidden" data-time='0' name='ischang'>
                          {/if}
                            <input type="radio" value="1" placeholder="" id="ctime" name="endtime" onchange="radioChange(1)" style="width:50px;" {if $list->overtype=='1'}checked{/if}><span style="margin-left: -10px;">长期</span>
                            <span style="margin-left: 10px;font-size: 10px;color:#666666;">长期的默认期限是一年</span>
                          <div style="margin-left: 60px;">
                            <input type="radio" value="2" placeholder="" id="dtime" name="endtime" onchange="radioChange(2)" style="width:50px;" {if $list->overtype=='2'}checked{/if}><span style="margin-left: -10px;">定期结束</span><input type="text" class="input-text" {if $list->overtype=='2'}value="{$list->endtime}"{else}value=""{/if} placeholder="" id="group_end_time" name="group_end_time" style="width:150px;margin-left: 10px;">
                            <span style="margin-left: 3px;font-size: 10px;color:red;">*必填项</span>
                          </div>
                        </div>
                    </div>
                    <div class="col-3">
                    </div>
                </div>
                <div class="row cl {if $status > 0}kznone{/if}" style="margin-left: 110px;">
                    <label class="form-label col-xs-4 col-sm-3">每位用户可同时进行的团数：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="number" min="1" class="input-text" value="{$list->groupnum}" placeholder="" id="" name="groupnum" style="width:60px;">
                        <span style="margin-left: 3px;font-size: 10px;color:red;">*必填项</span>
                        <span style="margin-left: 10px;font-size: 10px;color:#666666;">数字不能小于1。(为保证商家利益,请对团数进行限制。<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注: 已成功或已失败的团不计入团数。)</span>
                    </div>
                    <div class="col-3">
                    </div>
                </div>
                <div class="row cl {if $status > 0}kznone{/if}" style="margin-left: 110px;">
                    <label class="form-label col-xs-4 col-sm-3">用户每次参团时可购买件数：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="number" min="1" class="input-text" value="{$list->productnum}" placeholder="" id="" name="productnum" style="width:60px;">
                        <span style="margin-left: 3px;font-size: 3px;color:red;">*必填项</span>
                        <span style="margin-left: 10px;font-size: 10px;color:#666666;">数字不能小于1。(为保证商家利益,请对用户参团时可购买件数进行限。)</span>
                    </div>
                    <div class="col-3">
                    </div>
                </div>
            </div>
            
        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="baocungroup()">
                <!-- <input class="btn btn-primary radius" type="reset" value="&nbsp;&nbsp;取消&nbsp;&nbsp;" style="background: #EDEDED;border:0px;color:#000000;"> -->
            </div>
        </div>
    </form>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="style/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="style/laydate/laydate.js"></script>
{literal}
<script type="text/javascript">
  var radio = 1;
  var otype = $("input[name='endtime']:checked").val();
  
  if(otype == '1'){
    $('#group_end_time').attr('disabled','disabled');
  }
   function radioChange(i){
    var asd = $("input[name='endtime']:checked").val();
      if(i == 1){
          $('#group_end_time').attr('disabled','disabled');
          $('#group_end_time').val('');
          radio = 1;
      }else{
          $('#group_end_time').removeAttr('disabled');
          radio = 2;
      }
      otype = asd;
   }

 $("input[name=endtime]").change(function(){
            if($("input[name=endtime]:checked").val() == 1){
              $('input[name=group_end_time]').removeClass('content');
          }
    });

 function baocungroup(){
    

    var content = 1;
    var id = $("input[name=groupname]").attr('id');
    var groupname = $("input[name=groupname]").val();
    var peoplenum = $("input[name=peoplenum]").val();
    var timehour = $("input[name=timehour]").val();
    var timeminite = $("input[name=timeminite]").val();
    var starttime = $("input[name=starttime]").val();
    var endtime = $("input[name=group_end_time]").val();
    var overtime = otype=='2'?endtime:$('input[name=ischang]').attr('data-time');
    var groupnum = $("input[name=groupnum]").val();
    var productnum = $("input[name=productnum]").val();
    
    if(groupname == ''){
        $("input[name=groupname]").addClass('content');
        $("input[name=groupname]").change(function(){
            $(this).removeClass('content');
        });
    }else if(peoplenum == '' || parseInt(peoplenum)<1 || parseInt(peoplenum)>50){
        $("input[name=peoplenum]").addClass('content');
        $("input[name=peoplenum]").change(function(){
            $(this).removeClass('content');
        });
    }else if(timehour == '' || parseInt(timehour)<1){
        $("input[name=timehour]").addClass('content');
        $("input[name=timehour]").change(function(){
            $(this).removeClass('content');
        });
    }else if(timeminite == '' || parseInt(timeminite)<0 || parseInt(timeminite)>59){
        $("input[name=timeminite]").addClass('content');
        $("input[name=timeminite]").change(function(){
            $(this).removeClass('content');
        });
    }else if(starttime == ''){
        $("input[name=starttime]").addClass('content');
        $("input[name=starttime]").change(function(){
            $(this).removeClass('content');
        });
    }else if($("input[name=endtime]:checked").val() == 2 && endtime == ''){
        
        $("input[name=group_end_time]").addClass('content');
        $("input[name=group_end_time]").change(function(){
            $(this).removeClass('content');
        });
    }else if(groupnum == '' || parseInt(groupnum)<1){
        $("input[name=groupnum]").addClass('content');
        $("input[name=groupnum]").change(function(){
            $(this).removeClass('content');
        });
    }else if(productnum == '' || parseInt(productnum)<1){
        $("input[name=productnum]").addClass('content');
        $("input[name=productnum]").change(function(){
            $(this).removeClass('content');
        });
    }else{
        content = 2;
    }

    // console.log(otype);
    // console.log(groupname);
    // console.log(peoplenum);
    // console.log(timehour);
    // console.log(timeminite);
    // console.log(starttime);
    // console.log(endtime);
    // console.log(groupnum);
    // console.log(productnum);
   if(content == 2){
      $.ajax({
               url:"index.php?module=go_group&action=modify&set=msgsubmit",
               type:"post",
               data:{id:id,groupname:groupname,peoplenum:peoplenum,timehour:timehour,timeminite:timeminite,starttime:starttime,overtime:overtime,groupnum:groupnum,productnum:productnum,otype:otype},
               dataType:"json",
               success:function(data) {
                   if(data.code == 1){
                       // layer.msg('修改成功!');
                       // window.parent.location.reload();
                       // var index = parent.layer.getFrameIndex(window.name);
                       // parent.layer.close(index);
                       
                       location.href = 'index.php?module=go_group&action=index';
                   }
               },
             })
        
   }
 }
 

 laydate.render({
  elem: '#group_start_time', //指定元素
  type: 'datetime'
});
 laydate.render({
  elem: '#group_end_time', 
  type: 'datetime'
});


    $('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
    ]
});

$(function(){
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
    
    $("#tab-category").Huitab({
        index:0
    });
    /*$("#form-category-add").validate({
        rules:{
            
        },
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            //$(form).ajaxSubmit();
            var index = parent.layer.getFrameIndex(window.name);
            //parent.$('.btn-refresh').click();
            parent.layer.close(index);
        }
    });*/
    
});

</script>
{/literal}
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>