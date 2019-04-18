
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
{literal}
<style type="text/css">
    .none{
        display: none !important;
    }
</style>
{/literal}
<title>发红包</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 发红包管理 <span class="c-gray en">&gt;</span> 红包配置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <form name="form1" action="index.php?module=plug_ins&action=red_packet_modify" class="form form-horizontal" method="post"   enctype="multipart/form-data" >


    <table class="table table-bg table-hover " style="width: 100%;height:100px;border-radius: 30px;border: 1px solid darkgray;">
    <tr style="width: 100%;border-radius: 30px;border: 1px solid darkgray;">
        <td style="text-align: center;">红包配置</td>
    </tr>
    <tr style="width: 100%;border-radius: 30px;border: 1px solid darkgray;">
        <td>
            <div id="tab-system" class="HuiTab">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>活动名称</label>
                    <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="name" value="{$re.name}" class="input-text" style="width: 150px;">
                    <input type="hidden" name="id" value="{$id}"    >
                    </div>
                </div>
                </div>
                <div class="row cl">
                   <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>红包金额与数量比值</label>
                    <div class="formControls col-xs-8 col-sm-6">
                        <input type="number" name="bizhi" value="{$re.bizhi}" class="input-text" style="width: 150px;">
                        <span style="font-size: 8px">%</span>&nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>比值越大得到的红包金额相对越大</span>
                    </div>
                </div>

            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>可以发送红包个数</label>
                    <div class="formControls col-xs-8 col-sm-6">
                        <input type="number" name="send_redpacket" value="{$re.send_redpacket}" class="input-text" style="width: 150px;"> &nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>红包没有被领完和红包链接时间到期失效前</span>
                    </div>
            </div>
            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>可以领取红包个数</label>
                    <div class="formControls col-xs-8 col-sm-6">
                        <input type="number" name="receive_redpacket" value="{$re.receive_redpacket}" class="input-text" style="width: 150px;"> &nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>红包使用前或者失效前</span>
                    </div>
            </div>
            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>红包抵用比例</label>
                    <div id="hbtx" class="formControls col-xs-8 col-sm-6">
                       <input type="number" name="bili" value="{$re.bili}" class="input-text" style="width: 150px;">
                       <span style="font-size: 8px">%</span>&nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>比例越高，抵用红包金额越高</span>
                    </div>
            </div>
            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>红包是否可以提现</label>
                    <div id="hbtx" class="formControls col-xs-8 col-sm-6">
                        <input type="radio" name="tixian" value="1" {if $re.tixian==1} checked="checked" {/if}> 是
                               &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
                        <input type="radio" name="tixian"  value="2" {if $re.tixian==2} checked="checked" {/if}  >否
                      
                    </div>
            </div>
            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>红包链接失效时间</label>
                    <div class="formControls col-xs-8 col-sm-6">
                        <input type="number" name="shixiao_time" value="{$re.shixiao_time}" class="input-text" style="width: 150px;">
                        <span style="font-size: 8px">天</span> &nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>超过该时间红包不能领取</span>
                    </div>
            </div>

            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>红包失效时间</label>
                    <div class="formControls col-xs-8 col-sm-6">
                        <input  type="number"  name="shixiao_time1" value="{$re.shixiao_time1}" class="input-text" style="width: 150px;">
                        <span style="font-size: 8px">天</span> &nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>超过该时间红包过期</span>
                    </div>
            </div>
            <div class="row cl none">
               <label class="form-label col-xs-4 col-sm-4"><span style='color:red'>*</span>红包提现最小金额</label>
                    <div class="formControls col-xs-8 col-sm-6">
                        <input  type="number"  name="tixian_money" value="{$re.tixian_money}" class="input-text" style="width: 150px;">
                        <span style="font-size: 8px">元</span> &nbsp;&nbsp; 
                        <span style='color:red;font-size: 5px;'>红包总额低于该值不能提现</span>
                    </div>
            </div>

        </td>
    </tr>
    </table> 
    </br>      
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                <a href="index.php?module=plug_ins">
                <div style="height: 31px;width: 72.16px;background: #eee;text-align: center;line-height: 31px;border-radius: 5px;display: inline-block;vertical-align: -2px;">取消</div>
                </a>
            </div>
        </div>
    </form>
</div>
</div>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div> 
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

<!-- 新增编辑器引入文件 -->
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css" />
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
{literal}
<script type="text/javascript">
$('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
    ]
});

// 
</script>
{/literal}
</body>
</html>