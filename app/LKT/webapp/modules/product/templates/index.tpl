
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
{literal}
<style type="text/css">
td a{
    width: 44%;
    float: left;
    margin: 2%!important;
}
#btn1:hover{
    background-color: #2299e4!important;
}
#btn2:hover{
    background-color: #57a821!important;
}
#btn2{
    background-color: #77c037!important;
}
#btn3:hover{
    background-color: #299998!important;
}
#btn4:hover{
    background-color: #ff2c26!important;
}
#btn5:hover{
    background-color: #fd741d!important;
}
#btn6:hover{
    background-color: #e5e5e5!important;
}
#btn7:hover{
    background-color: #e5e5e5!important;
}
#btn8:hover{
    border:1px solid #2890ff!important;
    color: #2890ff!important;
}
#btn9:hover{
    background-color: #2481e5!important;
}
form .select{
    width: 140px!important;
}
.proSpan{
    font-size: 12px;
    border-radius: 4px;
    color:#ffffff;margin:
        0px 5px;padding: 0px 3px;
}
.xp{
    background-color: #68c8c7;
}
.rx{
    background-color: #ff6c60;
}
.tj{
    background-color: #feb04c;
}
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 产品列表管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c">
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="product" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <select name="cid" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="cid">
                <option value="0" >请选择产品分类</option>
                {$class}
            </select>
            <select name="brand_id" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="brand_id">
                <option value="0" >请选择产品品牌</option>
                {$rew}
            </select>
            <select name="s_type" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="s_type">
                <option value="0" >请选择产品类型</option>
                <option value="1" {if $s_type == 1}selected="selected"{/if} >新品</option>
                <option value="2" {if $s_type == 2}selected="selected"{/if}>热销</option>
                <option value="3" {if $s_type == 3}selected="selected"{/if}>推荐</option>
            </select>
            <select name="status" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="status">
                <option value="0" >请选择产品状态</option>
                <option value="2" {if $status == 2}selected="selected"{/if}>上架</option>
                <option value="1" {if $status == 1}selected="selected"{/if}>下架</option>
            </select>
            <input type="text" name="product_title" size='8' value="{$product_title}" id="product_title" placeholder="请输入产品名称" style="width:200px" class="input-text">
            <input name="" id="btn9" class="btn btn-success" type="submit" value="查询">
            <input type="reset" value="清空" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076;" class="reset" onclick="empty()" />
        </form>
    </div>
    <div style="clear:both;margin-top: 10px;" class="btnDiv">
        <a class="btn radius" id="btn1" style="background-color:#38b4ed;color: #fff;" href="index.php?module=product&action=add">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/add.png"/>&nbsp;发布产品
            </div>
        </a>
        <a class="btn radius" id="btn2" style="background-color:#77c037;color: #fff;" href="javascript:;" onclick="operation(1)">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/sj.png"/>&nbsp;产品上架
            </div>
        </a>
        <a class="btn radius" id="btn3" style="background-color:#42b4b3;color: #fff;" href="javascript:;" onclick="operation(3)">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/xp.png"/>&nbsp;设为新品
            </div>
        </a>
        <a class="btn radius" id="btn4" style="background-color:#ff453d;color: #fff;" href="javascript:;" onclick="operation(5)">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/rx.png"/>&nbsp;设为热销
            </div>
        </a>
        <a class="btn radius" id="btn5" style="background-color:#fe9331;color: #fff;">
            <div style="height: 100%;display: flex;align-items: center;" href="javascript:;" onclick="operation(7)">
                <img src="images/icon1/tj.png"/>&nbsp;设为推荐
            </div>
        </a>
        <a href="javascript:;" id="btn6" onclick="datadel()" style="background: #fff;color: #6a7076;border: none;" class="btn btn-danger radius">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/del.png"/>&nbsp;删除
            </div>
        </a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
            <tr class="text-c">
                <th width="40">
                    <div style="position: relative;display: flex;height: 30px;align-items: center;">
                        <input name="ipt1" id="ipt1" type="checkbox" value="" class="inputC">
                        <label for="ipt1"></label>
                    </div>
                </th>
                {*<th>产品编号</th>*}
                <th>产品图片</th>
                <th>产品标题</th>
                <th>分类名称</th>
                <th>库存</th>
                <th>销量</th>
                <th>发布时间</th>
                <th>产品品牌</th>
                <th>价格</th>
                <th style="width: 250px;">操作</th>
            </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td >
                        <div style="display: flex;align-items: center;height: 60px;">
                            <input name="id[]"  id="{$item->id}" type="checkbox" class="inputC " value="{$item->id}">
                            <label for="{$item->id}"></label>
                        </div>
                    </td>
                    {*<td style="min-width: 70px;">{$item->product_number}</td>*}
                    <td style="min-width: 70px;">{if $item->img != ''}<span>暂无图片</span>{else}<img onclick="pimg(this)" style="width: 50px;height: 50px;" src="../LKT/images/{$item->imgurl}">{/if}</td>
                    <td class="tableTitle">{$item->product_title}
                        {foreach from=$item->s_type item=item1 name=f2}
                            {if $item1 == 1}<span class="proSpan xp">新品</span>{/if}
                            {if $item1 == 2}<span class="proSpan rx">热销</span>{/if}
                            {if $item1 == 3}<span class="proSpan tj">推荐</span>{/if}
                        {/foreach}
                    </td>
                    <td style="min-width: 140px;">{$item->pname}</td>
                    <td {if $item->num <= $min_inventory}style="color: red;" {/if}>{$item->num}{$item->unit}</td>
                    <td style="min-width: 40px;">{$item->volume}</td>
                    <td style="min-width: 70px;">{$item->add_date}</td>
                    <td style="min-width: 70px;">{if $item->brand_name != ''}{$item->brand_name}{else}无{/if}</td>
                    <td><span style="color:red;">{$item->price}</span></td>
                    <td style="width: 250px;">
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=product&action=see&id={$item->id}&product_title={$item->product_title}&url=Index&uploadImg={$uploadImg}" title="查看">
                            <div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin: 0 auto;display: flex;align-items: center;">
                                <img src="images/icon1/ck.png"/>&nbsp;查看
                                </div>
                            </div>
                        </a>
                        {if $item->num != 0}
                            {if $item->status == 0}
                                <a style="text-decoration:none" class="ml-5" href="index.php?module=product&action=shelves&id={$item->id}&url=Index" title="下架">
                                    <div style="align-items: center;font-size: 12px;display: flex;">
                                        <div style="margin: 0 auto;display: flex;align-items: center;">
                                        <img src="images/icon1/xj.png"/>&nbsp;下架
                                        </div>
                                    </div>
                                </a>
                            {else}
                                <a style="text-decoration:none" class="ml-5" href="index.php?module=product&action=shelves&id={$item->id}&url=Index" title="上架">
                                    <div style="align-items: center;font-size: 12px;display: flex;">
                                        <div style="margin: 0 auto;display: flex;align-items: center;">
                                        <img src="images/icon1/sj_g.png"/>&nbsp;上架
                                        </div>
                                    </div>
                                </a>
                            {/if}
                        {/if}
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=product&action=modify&id={$item->id}&uploadImg={$uploadImg}" title="修改">
                            <div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin: 0 auto;display: flex;align-items: center;">
                            		<img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div> 
                            </div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" onclick="del(this,{$item->id})">
                            <div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin: 0 auto;display: flex;align-items: center;">
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
function empty() {
    location.replace('index.php?module=product');
}

function pimg(obj){
    var _this = $(obj);//将当前的pimg元素作为_this传入函数
    imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
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

var Id = '';
/*删除*/
function del(obj,id){
    confirm("确认删除此商品吗？",id);
}
/*批量删除*/
function datadel(){
    var checkbox=$("input[name='id[]']:checked");//被选中的复选框对象
    var Id = '';
    for(var i=0;i<checkbox.length;i++){
        Id+=checkbox.eq(i).val()+",";
    }
    if(Id==""){
        appendMask("未选择数据！","ts");
        return false;
    }
    confirm('确认要删除吗？',Id)
}
/*批量操作*/
function operation(type){
    var checkbox=$("input[name='id[]']:checked");//被选中的复选框对象
    for(var i=0;i<checkbox.length;i++){
        Id+=checkbox.eq(i).val()+",";
    }
    if(Id==""){
        appendMask("未选择数据！","ts");
        return false;
    }
    confirm2("确认修改吗？",Id,type);
}
function appendMask(content,src){
    $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">删除</div>
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
    $.get("index.php?module=product&action=del",{'id':id},function(res){
        if(res.status=="1"){
            appendMask("删除成功","cg");
        }else{
            appendMask("删除失败","ts");
        }
    },"json");
    $(".maskNew").remove();
}
function closeMask1(){
    $(".maskNew").remove();
    location.replace(location.href);
}
function confirm (content,id){
    $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">删除</div>
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
function confirm2 (content,id,type){
    console.log(id);
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
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask3('${id}','${type}') >确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
}
function closeMask3(id,type){
    $.get("index.php?module=product&action=operation",{'id':id,'type':type},function(res){
        if(res.status=="1"){
            appendMask('修改成功','cg');
        }else{
            appendMask('修改失败','ts');
        }
    },"json");
    $(".maskNew").remove();
}
</script>
{/literal}
</body>
</html>