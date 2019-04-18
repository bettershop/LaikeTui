
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

<title>产品分类管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 淘宝助手 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="main rightlist">
        <form id="dataform" action="index.php?module=product&action=taobao" class="form form-horizontal" method="post"   enctype="multipart/form-data">
            <div class="col-xs-12 lh" style="max-width: 800px;">
                <div class="panel panel-default">

                    <div class="panel-body" style="height: 500px">
                        <div class='alert alert-danger' style="color: #a94442;background-color: #f2dede;border-color: #ebccd1; padding: 8px;width: auto;display: inline-block;margin-bottom: 20px;border: 1px solid transparent;border-radius: 4px;">尽量在服务器空闲时间来操作，会占用大量内存与带宽，在获取过程中，请不要进行任何操作!</div>
                        <div class="form-group">
                            <label class="col-xs-12 col-sm-3 col-md-3 control-label" style="padding-top: 7px;margin-bottom: 0;text-align: right; position: relative;min-height: 1px;padding-right: 5px;padding-left: 5px; font-size: 14px;"><span style='color:red'>*</span> 链接 或 itemId</label>
                            <div class="col-sm-9">
                                <textarea style="height:200px; width:400px;padding:6px 12px;font-size: 14px;line-height: 1.42857143;color: #555;background-color: #fff;background-image: none;border: 1px solid #ccc;border-color: #d9d9d9;border-radius: 2px;" id="url" name="url" class="form-control" ></textarea><br/>
                                <span class="help-block" style="color: #a3a3a3;margin: 0;padding-top: 5px;font-size: 12px;">商品连接, 例如: http://item.taobao.com/item.htm?id=xxxxxx 或 http://detail.tmall.com/item.htm?id=xxxxx</span><br/>
                                <span class="help-block" style="color: #a3a3a3;margin: 0;padding-top: 5px;font-size: 12px;">每一行一个链接</span>
                            </div>
                        </div>  
                        <div class="row cl" style="">
                            <label class="form-label col-2 col-xs-12 col-sm-3 col-md-3" style="padding-right: 15px;font-size: 14px;"><span class="c-red"></span>产品类别：</label>
                            <div class="formControls col-2" style="    margin-left: -15px;"> 
                                <span class="select-box">
                                    <select name="product_class" class="select">
                                        <option selected="selected" value="0">请选择类别</option>
                                        {$ctype}
                                    </select>
                                </span> 
                            </div>
                            <label style="position: relative;top: 4px;"><span style='color:red'>*</span> 若没有选泽商品类别将会默认为百货类别</label>
                        </div>
                    </div>
                </div>

                <div class="form-group" style="margin: 0 auto;height: 50px;width: 100px;display: block;">
                    <label class="col-xs-12 col-sm-3 col-md-2 control-label"> </label>
                    <div class="col-sm-9" >
                        <input id="btn_submit" type="submit" name="Submit" value="立即获取" class="btn btn-primary col-lg-12 "/>

                    </div>
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

{literal}

<!-- <script type="text/javascript">
    function jxq(s){
    if (s == 1){
        document.all.jia.size="3";
    }else{
        document.all.jia.size="1";
    }
}

</script> -->
<style type="text/css">
    .border_bg{ border: 1px #ccc solid;border-radius: 4px; margin-bottom: 10px; background-color: #fff;}
    .border_bg .panel-heading{ background-color: #e8ecef; color: #000; }
    .btn-primary{color:#fff!important;background-color:#1e95c9;border-color:#1e95c9}
    .btn-primary.active,.btn-primary:active,.btn-primary:focus,.btn-primary:hover,.open .dropdown-toggle.btn-primary{color:#fff!important;background-color:#3071a9;border-color:#3071a9}
    .btn-primary.active,.btn-primary:active,.open .dropdown-toggle.btn-primary{background-image:none}
    .btn-primary.disabled,.btn-primary.disabled.active,.btn-primary.disabled:active,.btn-primary.disabled:focus,.btn-primary.disabled:hover,.btn-primary[disabled],.btn-primary[disabled].active,.btn-primary[disabled]:active,.btn-primary[disabled]:focus,.btn-primary[disabled]:hover,fieldset[disabled] .btn-primary,fieldset[disabled] .btn-primary.active,fieldset[disabled] .btn-primary:active,fieldset[disabled] .btn-primary:focus,fieldset[disabled] .btn-primary:hover{background-color:#65bd77;border-color:#65bd77}
    .btn{font-weight:500;border-radius:2px}
</style>
<script type="text/javascript">
    // var len = 0;
    // var urls = [];
    // var total = 0;
    // function formcheck() {
    //     $("#dataform").attr("disabled", "true");
    //     $("#btn_submit").val("正在获取中...").removeClass("btn-primary").attr("disabled",true );
    //      $("#btn_submit").css("background-color,#007aff")
    //     // urls = $("#url").val().split('\n');
    //     // total = urls.length;
    //     // $("#btn_submit").val("检测到需要获取 " + total + " 个宝贝, 请等待开始....");
    //     // fetch_next();
    //     return;
    // }
</script>
{/literal}
</body>
</html>