
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

<title>运费管理</title>
   {literal}
        <style type="text/css">
            td a{
            	width: 42%;
                margin: 3%!important;
            }
            #btn1{
            	height: 36px;
            	line-height: 36px;
            }
            #btn1:hover{
            	background-color: #2481e5!important;
            }
        </style>
    {/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 运费管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c">
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="freight" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <input type="text" name="name" size='8' value="{$name}" id="" placeholder="规则名称" style="width:200px" class="input-text">
            <input name="" id="btn1" class="btn btn-success" type="submit" value="查询">
            <a class="btn newBtn radius" style="border: none!important;" href="index.php?module=freight&action=add"><img src="images/icon1/add.png" />&nbsp;添加规则</a>
        	<a href="javascript:;" onclick="datadel()" style="background-color: #fff!important;color: #6a7076!important; width: 80px;margin-left: 10px;" class="btn newBtn radius"><img src="images/icon1/del.png" />删除</a>
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th width="40">
                    	<div style="display: flex;align-items: center;height: 40px;">
                            <input name="id[]"  id="{$item->id}" type="checkbox" class="inputC " value="{$item->id}">
                            <label for="{$item->id}"></label>
                        </div>
                    </th>
                    <th>规则名称</th>
                    <th>是否默认</th>
                    <th style="width: 160px;">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>
                    	<div style="display: flex;align-items: center;height: 40px;">
                            <input name="id[]"  id="{$item->id}" type="checkbox" class="inputC " value="{$item->id}">
                            <label for="{$item->id}"></label>
                        </div>
                    </td>
                    <td>{$item->name}</td>
                    <td><input name="is_default" id="is_default_{$item->id}" onclick="is_default({$item->id})" type="radio" value="{$item->id}" style="margin-right: 5px;" {if $item->is_default == 1}checked="checked"{/if}>默认</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=freight&action=modify&id={$item->id}" title="修改">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                        	</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" onclick="del(this,{$item->id})" onclick="return confirm('确定要删除此规则吗?')">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/del.png"/>&nbsp;删除
                            	</div>
                        	</div>
                        </a>
                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
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
<script type="text/javascript">
var y_is_default = document.getElementsByName("is_default");
var y_id = '';
for(k in y_is_default){
    if(y_is_default[k].checked){
        y_id = y_is_default[k].value;
    }
}
// 设置默认
function is_default(id){
    if(y_id == id){
        confirm1('确认要取消默认吗？',id,1)
    }else{
        confirm1('确认要修改默认吗？',id,2)
    }
    // if(y_id == id){
    //     $("#is_default_"+id).attr("checked",false);
    //
    // }else{
    //     $("#is_default_"+y_id).attr("checked",false);
    //     $("#is_default_"+id).attr("checked",true);
    // }
    //
    // $.ajax({
    //     type:'post',
    //     url: "index.php?module=freight&action=is_default",
    //     data: {
    //         id:id
    //     },
    //     success: function(res){
    //         var res = JSON.parse( res );
    //
    //         if(res.status == 1 ){
    //             alert('修改成功');
    //             location.href="index.php?module=freight";
    //         }else{
    //             alert('修改失败');
    //             location.href="index.php?module=freight";
    //         }
    //     }
    // });
}

var Id = '';
/*删除*/
function del(obj,id){
	confirm('确认要删除吗？',id)

}
/*批量删除*/
function datadel(){
    var checkbox=$("input[name='id[]']:checked");//被选中的复选框对象
    var Id="";
    for(var i=0;i<checkbox.length;i++){
        Id+=checkbox.eq(i).val()+",";
    }

    if(Id==""){
        appendMask("未选择数据！","ts")
        return false;
    }
    confirm('确认要删除吗？',Id);

}

function imgShow(outerdiv, innerdiv, bigimg, _this){  
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function(){
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if(realHeight>windowH*scale) {//判断图片高度
            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW*scale;//再对宽度进行缩放
            }
        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                        imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function(){//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
} 

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
function closeMask(id){
    $.get("index.php?module=freight&action=del",{'id':id},function(res){
        if(res.status=="1"){
            appendMask("删除成功","cg");
        }else{
            appendMask("删除失败","ts");
        }
    },"json");
    $(".maskNew").remove();
}

function confirm (content,id){
    $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">提示</div>
                <div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
                <div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
                    ${content}
                </div>
                <div style="text-align:center;margin-top:30px">
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask('${id}') >确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
}
function closeMask1(){
    $(".maskNew").remove();
    location.replace(location.href);
}
function confirm1 (content,id,type){
    $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">提示</div>
                <div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
                <div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
                    ${content}
                </div>
                <div style="text-align:center;margin-top:30px">
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask_1('${id}','${type}') >确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
}
function closeMask_1(id,type){
    $.get("index.php?module=freight&action=is_default",{'id':id},function(res){
        if(res.status=="1"){
            if(type == 1){
                console.log($("#is_default_"+id))
                $("#is_default_"+id).attr("checked",false);
            }else{
                console.log($("#is_default_"+y_id))
                console.log($("#is_default_"+id))

                $("#is_default_"+y_id).attr("checked",false);
                $("#is_default_"+id).attr("checked",true);
            }
            appendMask("修改成功","cg");
        }else{
            appendMask("修改失败","ts");
        }
    },"json");
    $(".maskNew").remove();
}
</script>
{/literal}
</body>
</html>