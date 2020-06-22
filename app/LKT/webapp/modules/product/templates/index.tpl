<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>商品列表管理</title>
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
    background-color: #225a1f!important;
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
.sytj {background-color: #007d65;}
.paginationDiv {
    width: 100%;
    background-color: #fff;
    padding: 20px 0;
    height: 30px;
    line-height: 30px;
    box-sizing: content-box;
    border-top: none;
}
.changePaginationNum {
    float: left;
    margin-left: 20px;
}
.showDataNum {
    float: left;
    margin-left: 20px;
    color: #414658;
}
.pagination {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    padding-left: 0;
    list-style: none;
    border-radius: .25rem;
    float: right;
    margin-right: 20px;
    height: 30px;
}
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=product">商品列表</a>
</nav>

<div class="pd-20">
    <div class="text-c">
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="product" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <select name="cid" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="cid">
                <option value="0" >请选择商品分类</option>
                {$class}
            </select>
            <select name="brand_id" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="brand_id">
                <option value="0" >请选择商品品牌</option>
                {$rew}
            </select>
            <select name="s_type" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="s_type">
                <option value="0" >请选择商品类型</option>
                <option value="1" {if $s_type == 1}selected="selected"{/if} >新品</option>
                <option value="2" {if $s_type == 2}selected="selected"{/if}>热销</option>
                <option value="3" {if $s_type == 3}selected="selected"{/if}>推荐</option>
            </select>
            <select name="status" class="select" style="width: 80px;height: 31px;vertical-align: middle;" id="status">
                <option value="0" >请选择商品状态</option>
                <option value="2" {if $status == 2}selected="selected"{/if}>上架</option>
                <option value="1" {if $status == 1}selected="selected"{/if}>下架</option>
                <option value="3" {if $status == 3}selected="selected"{/if}>待上架</option>
            </select>
            <input type="text" name="product_title" size='8' value="{$product_title}" id="product_title" placeholder="请输入商品名称" autocomplete="off" style="width:200px" class="input-text">
            <input name="" id="btn9" class="btn btn-success" type="submit" value="查询">
            <input type="button" value="重 置" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076;" class="reset" onclick="resetButton()"  />
        </form>
    </div>
    <div style="clear:both;margin-top: 10px;" class="btnDiv">
        <a class="btn radius" id="btn1" style="background-color:#38b4ed;color: #fff;" href="index.php?module=product&action=add">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/add.png"/>&nbsp;发布商品
            </div>
        </a>
        <a class="btn radius btn_up" id="btn2" style="background-color:#77c037;color: #fff;" href="javascript:;" onclick="operation(1)">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/sj.png"/>&nbsp;<span>商品上架</span>
            </div>
        </a>
        <a class="btn radius btn_xp" id="btn3" style="background-color:#42b4b3;color: #fff;" href="javascript:;" onclick="operation(3)">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/xp.png"/>&nbsp;<span>设为新品</span>
            </div>
        </a>
        <a class="btn radius btn_rx" id="btn4" style="background-color:#ff453d;color: #fff;" href="javascript:;" onclick="operation(5)">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/rx.png"/>&nbsp;<span>设为热销</span>
            </div>
        </a>
        <a class="btn radius btn_tj" id="btn5" style="background-color:#fe9331;color: #fff;">
            <div style="height: 100%;display: flex;align-items: center;" href="javascript:;" onclick="operation(7)">
                <img src="images/icon1/tj.png"/>&nbsp;<span>设为推荐</span>
            </div>
        </a>

        <a class="btn radius btn_sytj" id="btn7" style="background-color:#007d65;color: #fff;">
            <div style="height: 100%;display: flex;align-items: center;" href="javascript:;" onclick="operation(10)">
                <img src="images/icon1/tj.png"/>&nbsp;<span>设为首页推荐</span>
            </div>
        </a>

        <a href="javascript:;" id="btn6" onclick="datadel()" style="background: #fff;color: #6a7076;border: none;" class="btn btn-danger radius">
            <div style="height: 100%;display: flex;align-items: center;">
                <img src="images/icon1/del.png"/>&nbsp;删除
            </div>
        </a>
    </div>
    <div class="mt-20">
        <div class="mt-20 table-scroll" style="overflow: scroll; width: 100%; height: 495px;">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th width="40">
                    
                    <div style="display: flex;align-items: center;height: 60px;">
                            <input  style="display:block" name="ipt1" id="ipt1" type="checkbox" value="" class="inputC"  >
                            <label for="ipt1" ></label>
                    </div>

                </th>
                <th>商品图片</th>
                <th>商品标题</th>
                <th>分类名称</th>
                <th>商品品牌</th>
                <th>价格</th>
                <th>库存</th>
                <th>销量</th>
                <th>状态</th>
                <th>排序</th>
                <th style="width: 250px;">操作</th>
            </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td >
                        <div style="display: flex;align-items: center;height: 60px;">
                            <input name="id[]"  id="{$item->id}" type="checkbox" class="inputC orders_select" value="{$item->id}">
                            <label for="{$item->id}"></label>
                        </div>
                    </td>
                    <td style="min-width: 70px;">{if $item->img != ''}<span>暂无图片</span>{else}<img class='pimg' onclick="pimg(this)" style="width: 50px;height: 50px;" src="../LKT/images/{$item->imgurl}">{/if}</td>
                    <td class="tableTitle">{$item->product_title}
                        {foreach from=$item->s_type item=item1 name=f2}
                            {if $item1 == 1}<span class="proSpan xp">新品</span>{/if}
                            {if $item1 == 2}<span class="proSpan rx">热销</span>{/if}
                            {if $item1 == 3}<span class="proSpan tj">推荐</span>{/if}
                            {if $item1 == 4}<span class="proSpan sytj">首页推荐</span>{/if}
                        {/foreach}
                    </td>
                    <td style="min-width: 140px;">{$item->pname}</td>
                    <td style="min-width: 70px;">{if $item->brand_name != ''}{$item->brand_name}{else}无{/if}</td>
                    <td><span style="color:red;">{$item->price}</span></td>
                    <td {if $item->num <= $min_inventory}style="color: red;" {/if}>{$item->num}{$item->unit}</td>
                    <td style="min-width: 40px;">{$item->volume}</td>
                    <td style="min-width: 70px;" class="sp_up">
                        {if $item->status == 2}<span >待上架</span>
                        {elseif $item->status == 0}<span >已上架</span>
                        {else}<span >已下架</span>{/if}
                        
                    </td>
                    <td style="min-width: 40px;">{$item->sort}</td>
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
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=product&action=copy&id={$item->id}&uploadImg={$uploadImg}" title="复制">
                            <div style="align-items: center;font-size: 12px;display: flex;">
                                <div style="margin: 0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/xg.png"/>&nbsp;复制
                                </div> 
                            </div>
                        </a>
                        <a  title="删除" style="text-decoration:none" class="ml-5" onclick="del(this,{$item->id})">
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
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
    <div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" />
    </div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">

$("#ipt1").bind("click",
            function () {
                $(".orders_select").prop("checked", $(this).prop("checked"));
            });


function empty() {
    location.replace('index.php?module=product');
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
    
    var btn_up = $(".btn_up span").text();
    var btn_xp = $(".btn_xp span").text();
    var btn_rx = $(".btn_rx span").text();
    var btn_tj = $(".btn_tj span").text();
    var btn_sytj = $(".btn_sytj span").text();

    if(type == 1 &&btn_up == "商品下架"){
        type = 2;
    }else if(type == 3 && btn_xp == "取消新品"){
        type = 4;
    }else if(type == 5 && btn_rx == "取消热销"){
        type = 6;
    }else if(type == 7 && btn_tj == "取消推荐"){
        type = 8;
    } else if(type == 10 && btn_sytj == "取消首页推荐"){
        type = 9
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
        }else if(res.status=="2"){
            appendMask("该商品有参与插件活动，无法删除！","ts");
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
function resetButton(){
        $("#product_title").val("");
        $("#cid").val("");
        $("#brand_id").val("");
        $("#s_type").val("");
        $("#status").val("");
}
</script>
{/literal}
</body>
</html>