<?php /* Smarty version 2.6.26, created on 2020-12-28 14:08:47
         compiled from index.tpl */ ?>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

<title>商品分类</title>
<?php echo '
<style type="text/css">
td a{
    width: 28%;
    float: left;
    margin: 2%!important;
}
.pagination a, .pagination span {
    position: relative;
    display: block;
    padding: .5rem .75rem;
    margin-left: -1px;
    line-height: 1.25;
    color: #0275d8;
    background-color: #fff;
    border: 1px solid #ddd;
    text-decoration:none;
}
.pagination {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    padding-left: 0;
    list-style: none;
    border-radius: .25rem;
}
</style>
'; ?>

</head>
<body>

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    商品分类 
</nav>

<div class="pd-20">
    <div style="clear:both;">
        <input type="hidden" name="cid" id="cid" value="<?php echo $this->_tpl_vars['cid']; ?>
" >
          <button type="button" class="btn newBtn radius"  onclick="location.href='index.php?module=product_class&action=add';" <?php if ($this->_tpl_vars['level']): ?> style="display: none;"<?php endif; ?>>
            <img src="images/icon1/add.png" alt=""  />新增分类
        </button>

        <input type="button" class="btn btn-primary radius" id="syj" value="返回上一级" onclick="location.href='index.php?module=product_class&action=Index&cid=<?php echo $this->_tpl_vars['level01']; ?>
';" <?php if (! $this->_tpl_vars['level']): ?> style="display: none;"<?php endif; ?> />
    </div>
    <div class="mt-20">
        <div class="mt-20 table-scroll" style="overflow: scroll; width: 100%; height: 585px;">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>分类ID</th>
                    <th>分类图片</th>
                    <th>分类名称</th>
                    <th>分类级别</th>
                    <th>排序(从小到大)</th>
                    <th>添加时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="tbody">
            <?php $_from = $this->_tpl_vars['list']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f1'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f1']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['item']):
        $this->_foreach['f1']['iteration']++;
?>
                <tr class="text-c">
                    <td><?php echo $this->_tpl_vars['item']->cid; ?>
</td>
                    <td><?php if ($this->_tpl_vars['item']->img != ''): ?><image class="pimg" src="<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['item']->img; ?>
" style="width: 50px;height:50px;"/><?php else: ?><span>暂无图片</span><?php endif; ?></td>
                    <td><?php echo $this->_tpl_vars['item']->pname; ?>
</td>
                    <td><?php echo $this->_tpl_vars['level_xs']; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->sort; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->add_date; ?>
</td>
                    <td style="width: 180px;" >

						
						<a style="text-decoration:none;" class="ml-5" onclick="del(this,'<?php echo $this->_tpl_vars['item']->cid; ?>
','<?php echo $this->_tpl_vars['item']->status; ?>
')" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                                <div style="margin:0 auto;display: flex;align-items: center;">
                                    <img src="images/icon1/del.png"/>&nbsp;删除
                                </div>
                            </div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=product_class&action=modify&cid=<?php echo $this->_tpl_vars['item']->cid; ?>
" title="修改" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                    <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                            </div>
                        </a>
                        <?php if ($this->_tpl_vars['level'] < 5): ?>
                        <a  style="text-decoration:none;width: 44%;" class="ml-5" href="index.php?module=product_class&action=add&val=<?php echo $this->_tpl_vars['item']->cid; ?>
" title="在此分类下添加" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                    <img src="images/icon1/add_g.png"/>&nbsp;添加分类
                            	</div>
                            </div>
                        </a >
						<a style="text-decoration:none;width: 46%;" class="ml-5" href="index.php?module=product_class&action=Index&cid=<?php echo $this->_tpl_vars['item']->cid; ?>
" title="查看该分类的下级" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                    <img src="images/icon1/ck.png"/>&nbsp;查看下级
                            	</div>
                            </div>
                        </a>
                        <?php endif; ?>
                    </td>
                </tr>
            </form>
            <?php endforeach; endif; unset($_from); ?>
            </tbody>
        </table>
    </div>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;"><?php echo $this->_tpl_vars['pages_show']; ?>
</div>
</div>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>
<script type="text/javascript" src="style/js/jquery.js"></script>


<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>


<?php echo '
<script type="text/javascript">
$(function(){  
    $(".pimg").click(function(){
        var _this = $(this);//将当前的pimg元素作为_this传入函数
        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
    });
});

function on_top(obj,cid,sid) {
    $.ajax({
       type: "POST",
       url: location.href,
       data: {
            cid:cid,
            sid:sid
       },
       success: function(msg){
         if(msg == 1){
            location.replace(location.href);
        }else{
            alert(\'修改失败！\');
        }
       }
    });
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

// 选择
function select(cid){
    var v = $(\'#select_\'+cid+\' option:selected\') .val();//选中的值
    if(v == 1){
        window.location.href = "index.php?module=product_class&action=add&cid="+cid;
    }else if(v == 2){
        window.location.href = "index.php?module=product_class&action=Index&cid="+cid;
    }
}

function on_top(obj,cid,sid) {
    $.ajax({
        type: "POST",
        url: location.href,
        data: {
            cid:cid,
            sid:sid
        },
        success: function(msg){
            if(msg == 1){
                location.replace(location.href);
            }else{
                appendMask(\'修改失败！\',"ts");
                location.replace(location.href);
            }
        }
    });
}
/*删除*/
function del(obj,id,status){
    if(status == 1){
		let content="该分类有商品,不能删除!";
		appendMask(content,"ts");
    }else{
        confirm("确认删除此分类？",id);
    }
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
    $.ajax({
        type: "POST",
        url: "index.php?module=product_class&action=del",
        data: {\'cid\':id},
        dataType:"html",
        success: function(res){
            if(res==1){
                appendMask("删除成功","cg")
            }else if(res == 2){
                appendMask("有商品不允删除","ts")
            }else{
                appendMask("删除失败","ts")
            }
        }
    });
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
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask(${id}) >确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
}
</script>
'; ?>

</body>
</html>