{include file="../../include_path/header.tpl" sitename="DIY头部"}

{literal}
<style>
.formTitleSD{
    font-weight:bold;
    font-size: 16px;
    border-bottom: 2px solid #E9ECEF;
}
</style>
{/literal}

<body>
    
<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=stock">库存管理</a> <span class="c-gray en">&gt;</span> 
    增加库存 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20 page_absolute form-scroll">
    <form name="form1" id="form1"  class="form form-horizontal" method="post" enctype="multipart/form-data" style="padding: 0px;">
        <input type="hidden" name="id" value="{$id}">
        <input type="hidden" name="pid" value="{$pid}">
        <table class="table table-bg table-hover " style="width: 100%;height:100px;border-radius: 30px;">
            <div class="formDivSD">
                <div class="formTitleSD page_title">增加库存</div>
                <div class="formContentSD">
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red">*</span><span>商品类别：</span></div>
                        <div class="formInputSD">
                            <input type="text" name="product_class_name" value="{$product_class_name}" placeholder="" required="required" readonly="readonly" style="background-color: #F8F8F8 !important;"/>
                        </div>
                    </div>
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red">*</span><span>商品品牌：</span></div>
                        <div class="formInputSD">
                            <input type="text" name="brand_name" value="{$brand_name}" placeholder="" required="required" readonly="readonly" style="background-color: #F8F8F8 !important;"/>
                        </div>
                    </div>
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red">*</span><span>商品名称：</span></div>
                        <div class="formInputSD">
                            <input type="text" name="product_title" value="{$product_title}" placeholder="" required="required" readonly="readonly" style="background-color: #F8F8F8 !important;"/>
                        </div>
                    </div>
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red">*</span><span>商品规格：</span></div>
                        <div class="formInputSD">
                            <input type="text" name="specifications" value="{$specifications}" placeholder="" required="required" readonly="readonly" style="background-color: #F8F8F8 !important;"/>
                        </div>
                    </div>
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red"></span><span>商品总库存：</span></div>
                        <div class="formInputSD">
                            <input type="number" name="total_num" value="{$total_num}" placeholder="" required="required" readonly="readonly" style="background-color: #F8F8F8 !important;"/>
                        </div>
                    </div>
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red"></span><span>剩余库存：</span></div>
                        <div class="formInputSD">
                            <input type="number" name="num" value="{$num}" placeholder="" required="required" readonly="readonly" style="background-color: #F8F8F8 !important;"/>
                        </div>
                    </div>
                    <div class="formListSD">
                        <div class="formTextSD"><span class="c-red"></span><span>增加库存：</span></div>
                        <div class="formInputSD">
                            <input type="number" name="add_num" value="" placeholder="" required="required" />
                        </div>
                    </div>
                </div>
            </div>
        </table>
        <div style="height: 40px;"></div>
        <div class="row cl page_bort" style="position: fixed; bottom: 0; left: 10px; right: 10px; display: block; margin: 0; width: auto;background: #fff;z-index: 999;">
            <div style="float:right;height: 67px;">
				<input class="btn btn-primary radius ta_btn3 btn-right" type="button" onclick="check()" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
                <input class="btn btn-primary radius ta_btn4 btn-left" type="button" value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javascript:history.back(-1);" style="background-color: white!important;color:#2890FF;">
            </div>
        </div>
        <div class="page_h10"></div>
    </form>
</div>
{literal}
<script type="text/javascript">
document.onkeydown = function (e) {
    if (!e) e = window.event;
    if ((e.keyCode || e.which) == 13) {
        $("[name=Submit]").click();
    }
}
function check() {
    $.ajax({
        cache: true,
        type: "POST",
        dataType:"json",
        url:'index.php?module=stock&action=add',
        data:$('#form1').serialize(),// 你的formid
        async: true,
        success: function(data) {
            alert(data.status,{time:2000});
            if(data.suc){
                location.href="index.php?module=stock";
            }
        }
    });
}
</script>
{/literal}
</body>
</html>