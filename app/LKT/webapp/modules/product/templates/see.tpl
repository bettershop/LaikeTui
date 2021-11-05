<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    {php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
    
    {literal}
        <script type="text/javascript">
            function check(f){
                if(Trim(f.product_title.value)==""){
                    alert("商品名称不能为空！");
                    f.pname.value = '';
                    return false;
                }
                return true;
            }
        </script>
    {/literal}
    {literal}
    <style type="text/css">
    .input-text, .scinput_s{
        width: 300px;
    }
    .ra1{
        position: relative;
        width: 70px;
        margin-right: 20px;
        border: 1px solid #eee;
        border-radius: 5px;
        height: 30px;
        line-height: 30px;
        float: left;
    }
    .ra1 label{
        height: 30px;
        text-align: center;
        line-height: 30px;
        margin: 0 auto;
        width: 90%;
        left: 6px;
        background: none;
    }
    .ra1 input{
        float: left;
        position: absolute;
        height: 30px;
        line-height: 30px;
    }
    .formListSD {
        color: #414658;
    }

    .formContentSD {
        padding: 0;
        padding-top: 10px;
        position: relative;
    }

    .formTextSD {
        margin-right: 8px;
        width: 11%!important;
    }

    .formInputSD input,
    .formInputSD select {
        width: 287px;
    }

    .formInputSD input[type='number'] {
        padding-left: 10px;
        margin-bottom: 10px;
    }

    .formInputSD select,
    .formInputSD select {
        padding-left: 8px;
        margin-bottom: 10px;
    }
    .inputC:checked +label::before{
        top: 8px;
    }
    form[name=form1] input{
        margin: 0px;
    }
     .form-label{
        padding-right: 10px;
        margin-top: 3px;
    }
    .inputC+label{
        width: 50px;
        border: none;
    }
    .inputC:checked +label::before{
        display: inline-block;
    }
    .form-horizontal .formControls {
    padding-right: 10px;
}
    #masks {
        width: 100%;
        height: 100vh;
        position: absolute;
        z-index: 10002;
        background-color: #FFF;
        justify-content: center;
        align-items: center;
        display: flex;
    }

    #masks img {
        width: 50px;
    }
    </style>
    {/literal}
    <title>修改商品</title>
</head>
<body>
<div id="masks">
    <img src="images/icon1/loads.gif">
</div>

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=product">商品列表</a> <span class="c-gray en">&gt;</span> 
    查看商品 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20" id="page">
    <form name="form1" action="index.php?module=product&action=modify" enctype="multipart/form-data" method="post">
        <input type="hidden" name="id" value='{$id}'/>
        <input type="hidden" name="uploadImg" value='{$uploadImg}'/>
        <input type="hidden" name="attribute" class="attribute" id="attribute" value='{$attribute1}'/>
        <input type="hidden" name="attribute_key" class="attribute_key" id="attribute_key" value='{$attribute_key2}'/>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品标题：</label>
            <div class="formControls col-4" style="width: 16.8%;">
                <input type="text" class="input-text" value="{$product_title}" placeholder="" id="product_titleId" name="product_title" disabled>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>副标题：</label>
            <div class="formControls" style="display: inline-block;">
                <input type="text" class="input-text" value="{$subtitle}" placeholder="" id="subtitleId" name="subtitle" disabled>
            </div>
            <text style="line-height:30px;">*简洁表达商品，用来显示在首页商品，避免截取时不能表达是什么商品。</text>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品类别：</label>
            <div class="formControls col-2">
                <select name="product_class" class="select" id="product_classId" disabled>
                    {$ctypes}
                </select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>商品品牌：</label>
            <div class="formControls col-2">
                <select name="brand_class" class="select" id="brand_classId" disabled>
                        {$r02}
                </select>
            </div>
        </div>
                <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品主图：</label>
            <div class="formControls col-xs-8 col-sm-10" style="width: 20%;">
                <input type="hidden" id="picurl" value="{$uploadImg}{$imgurl}" class="td-input" name="image"/>
                <input type="hidden" name="img_oldpic" value="{$imgurl}" >
                <img id="thumb_url" src='{$uploadImg}{$imgurl}' style="height:160px;width:160px">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">商品展示图：</label>
            <div class="formControls col-10">
                <div class="uploader-thum-container">

                    {if $imgurls != ''}
                        {foreach from=$imgurls item=item name=f1}
                            <img class="pimg" src="{$uploadImg}{$item->product_url}" style="width: 50px;height: 50px;">
                        {/foreach}
                    {/if}
                    <!-- 注:最多五张,一定为.jpg格式 -->
                </div>
            </div>
        </div>
                    <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>重量：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input type="text" class="input-text" value="{$weight}" placeholder="" id="weightId" name="weight" disabled>
                </div>
                <text style="line-height:30px;">克</text>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>成本价：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input class="input-text" style="width:300px;" type="number" name="initial[cbj]" onkeypress="return noNumbers(event)" min="0"  step="0.01" onblur="set_cbj(this);" value="{$initial->cbj}" placeholder="请设置商品的默认成本价" disabled>
                </div>
                <text style="line-height:30px;">克</text>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>原价：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input class="input-text" style="width:300px;" type="number" name="initial[yj]" onkeypress="return noNumbers(event)" min="0"  step="0.01" onblur="set_yj(this);" value="{$initial->yj}" placeholder="请设置商品的默认原价" disabled>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>售价：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input class="input-text" style="width:300px;" type="number" name="initial[sj]" onkeypress="return noNumbers(event)" min="0"  step="0.01" onblur="set_sj(this);" value="{$initial->sj}" placeholder="请设置商品的默认售价" disabled>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>单位：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <select name="initial[unit]" class="select " style="width: 300px;" id="unit" style="width:300px;" disabled>
                        <option value="盒" {if $initial->unit =='盒' } selected="selected" {/if}>盒</option>
                        <option value="篓"{if $initial->unit =='篓' } selected="selected" {/if}>篓</option>
                        <option value="箱" {if $initial->unit =='箱' } selected="selected" {/if}>箱</option>
                        <option value="个" {if $initial->unit =='个' } selected="selected" {/if}>个</option>
                        <option value="套" {if $initial->unit =='套' } selected="selected" {/if}>套</option>
                        <option value="包" {if $initial->unit =='包' } selected="selected" {/if}>包</option>
                        <option value="支" {if $initial->unit =='支'} selected="selected" {/if}>支</option>
                        <option value="条" {if $initial->unit =='条'} selected="selected" {/if}>条</option>
                        <option value="根" {if $initial->unit =='根'} selected="selected" {/if}>根</option>
                        <option value="本" {if $initial->unit =='本'} selected="selected" {/if}>本</option>
                        <option value="瓶" {if $initial->unit =='瓶'} selected="selected" {/if}>瓶</option>
                        <option value="块" {if $initial->unit =='块'} selected="selected" {/if}>块</option>
                        <option value="片" {if $initial->unit =='片' } selected="selected" {/if}>片</option>
                        <option value="把" {if $initial->unit =='把' } selected="selected" {/if}>把</option>
                        <option value="组" {if $initial->unit =='组' } selected="selected" {/if}>组</option>
                        <option value="双" {if $initial->unit =='双' } selected="selected" {/if}>双</option>
                        <option value="台" {if $initial->unit =='台' } selected="selected" {/if}>台</option>
                        <option value="件" {if $initial->unit =='件' } selected="selected" {/if}>件</option>
                        
                    </select>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>库存：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input type="number" class="input-text"  style="width:300px;" name="initial[kucun]" oninput="value=value.replace(/[^\d]/g,'')" min="0"  step="1" onblur="set_kucun(this);" value="{$initial->kucun}" placeholder="请设置商品的默认库存" disabled>
                </div>
            </div>
        <div class="formDivSD">
            <div class="formContentSD">
                {literal}
                    <!-- 有规格 -->
                    <div >
                        <div class="arrt_bgcolor arrt_fiv" style="margin-right: 60px;">
                            <div v-if="attr_group_list && attr_group_list.length>0">
                                <table class="attr-table attr_table">
                                    <thead>
                                        <tr>
                                            <th v-for="(attr_group,i) in attr_group_list" v-if="attr_group.attr_list && attr_group.attr_list.length>0">
                                                {{attr_group.attr_group_name}}
                                            </th>
                                            <th>成本价</th>
                                            <th>原价</th>
                                            <th>售价</th>
                                            <th>库存</th>
                                            <th>单位</th>
                                            <th>上传图片</th>
                                        </tr>
                                    </thead>

                                    <tr v-for="(item,index) in checked_attr_list" class="arrt_tr">
                                        <input type="hidden"  v-bind:name="'attr['+index+'][cid]'" :value="item.cid">
                                        <td v-for="(attr,attr_index) in item.attr_list">
                                            <input type="hidden"  v-bind:name="'attr['+index+'][attr_list]['+attr_index+'][attr_id]'" v-bind:value="attr.attr_id">

                                            <input type="hidden" v-bind:name="'attr['+index+'][attr_list]['+attr_index+'][attr_name]'" v-bind:value="attr.attr_name">

                                            <input type="hidden" v-bind:name="'attr['+index+'][attr_list]['+attr_index+'][attr_group_name]'" v-bind:value="attr.attr_group_name">
                                            <span>{{attr.attr_name}}</span>
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" type="number" onkeypress="return noNumbers(event)" min="0"  step="0.01"  v-bind:name="'attr['+index+'][costprice]'" :value="item.costprice" disabled>
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" type="number" onkeypress="return noNumbers(event)" min="0"  step="0.01"  v-bind:name="'attr['+index+'][yprice]'" :value="item.yprice" disabled >
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" type="number" onkeypress="return noNumbers(event)" min="0"  step="0.01"  v-bind:name="'attr['+index+'][price]'" :value="item.price" disabled>
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" oninput="value=value.replace(/[^\d]/g,'')" v-bind:name="'attr['+index+'][num]'"  step="1" :value="item.num" disabled >
                                        </td>
                                        <td>
                                            <input class="unit" v-bind:name="'attr['+index+'][unit]'" :value="item.unit" style="border: 0px;background-color: transparent;" readOnly="readOnly" disabled>
                                        </td>

                                        <td>
                                            <div  class="upload-group form_group form_flex">
                                                <!-- 不存在 -->
                                                <div class="form_attr_img ">
                                                    <input type="hidden" :id="'picurl2'+index" v-bind:name="'attr['+index+'][img]'" datatype="*" nullmsg="请选择图片"/>
                                                        <img src="images/icon1/add_g_t.png" :id="'pic2'+index" class="upload-preview-img form_att select-file" @click="handleImageClick(item,index)" onclick="setTimeoutClick() ">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                {/literal}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>显示类型：</label>
            <div class="formControls col-xs-8 col-sm-8 skin-minimal">
                <div class="ra1">
                    <input name="s_type[]" type="checkbox" class="inputC" id="sex-1" value="1" {if in_array(1,$s_type)}checked="checked"{/if} disabled>
                    <label for="sex-1">新品</label>
                </div>
                <div class="ra1">
                    <input type="checkbox" id="sex-2" class="inputC" name="s_type[]" value="2" {if in_array(2,$s_type)}checked="checked"{/if} disabled>
                    <label for="sex-2">热销</label>
                </div>
                <div class="ra1">
                    <input type="checkbox" id="sex-3" class="inputC" name="s_type[]" value="3" {if in_array(3,$s_type)}checked="checked"{/if} disabled>
                    <label for="sex-3">推荐</label>
                </div>
                <div class="ra1" style="width:100px;">
                    <input type="checkbox" id="sex-4" name="s_type[]" class="inputC" value="4" {if in_array(4,$s_type)}checked="checked"{/if} disabled>
                    <label for="sex-4" style="width:100%;">首页推荐</label>
                </div>
            
            </div>
        </div>


        <div class="row cl">
            <label class="form-label col-2">拟定销量：</label>
            <div class="formControls col-2">
                <input type="number" class="input-text" value="{$volume}" name="volume" id="volumeId" disabled>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>运费设置：</label>
            <div class="formControls col-2"> <!-- <span class="select-box"> -->
                <select name="freight" class="select" id="freightId" disabled>
                    {$freight_list}
                </select>
                <!-- </span> -->
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">详细内容：</label>
            <div class="formControls col-xs-8 col-sm-10">
                <script id="editor" type="text/plain" name="content" style="width:100%;height:400px;" disabled>{$content}</script>
            </div>
        </div>
        <div style="height: 20px;"></div>
        <div class="row cl page_bort_bottom">
            <label class="form-label col-xs-4 col-sm-2"></label>
            <div class="formControls col-xs-8 col-sm-10">
                <input type="button" name="reset" value="返回"  class="btn btn-primary" id="resetId" onclick="javascript :history.back(-1);" >
            </div>
        </div>
    </form>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}
<script src="style/js/vue.min.js"></script>


{literal}
<script>

$(function(){
    var ue = UE.getEditor('editor');
    ue.ready(function() {
        //不可编辑
        ue.setDisabled();
    }); 
});


$(document).ready(function(){
    var imgList = getTheImgList();
    if(imgList){
        for(var i=0; i<imgList.length; i++){
            $('#pic2'+i).attr("src", imgList[i].img);
            $('#picurl2'+i).val(imgList[i].img);
        }
    }
});
// 获取属性值上传图片
function getTheImgList(){
    var imgList = JSON.parse('{/literal}{$checked_attr_list}{literal}', true)
    return imgList
}
$(document).ready(function(){
    //将数字字符串转为带两位小数的数字
    page.cbj = getFloatStr(page.cbj)
    page.yj = getFloatStr(page.yj)
    page.sj = getFloatStr(page.sj)
});
//将数字字符串转为带两位小数的数字
function getFloatStr(num){  
    num += '';  
    num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符  
    
    if(/^0+/) //清除字符串开头的0  
        num = num.replace(/^0+/, '');  
    if(!/\./.test(num)) //为整数字符串在末尾添加.00  
        num += '.00';  
    if(/^\./.test(num)) //字符以.开头时,在开头添加0  
        num = '0' + num;  
    num += '00';        //在字符串末尾补零  
    num = num.match(/\d+\.\d{2}/)[0];  
    return num;
}
//控制价格显示小数点2位
    function noNumbers(e){
        console.log('onkeypress')
        console.log(e)
        var keynum
        var keychar
        var numcheck
        if(window.event) // IE
        {
            keynum = e.keyCode
        }
        else if(e.which) // Netscape/Firefox/Opera
        {
            keynum = e.which
        }
        keychar = String.fromCharCode(keynum);
        //判断是数字,且小数点后面只保留两位小数
        if(!isNaN(keychar)){
            var index=e.currentTarget.value.indexOf(".");
            if(index >= 0 && e.currentTarget.value.length-index >2){
                return false;
            }
            return true;
        }
        //如果是小数点 但不能出现多个 且第一位不能是小数点
        if("."== keychar ){
            if(e.currentTarget.value==""){
                return false;
            }
            if(e.currentTarget.value.indexOf(".") >= 0){
                return false;
            }
            return true;
        }
        return false;
    }

    var map = new Map();
var page = new Vue({
        el: "#page",
        data: {
            sub_cat_list: [],
            attr_group_list: JSON.parse('{/literal}{$attr_group_list}{literal}', true), //可选规格数据
            attr_group_count: JSON.parse('{/literal}{$attr_group_list}{literal}', true).length,
            checked_attr_list: JSON.parse('{/literal}{$checked_attr_list}{literal}', true), //已选规格数据
            old_checked_attr_list: [],
            goods_card_list: [],
            card_list: [],
            goods_cat_list: [{
                "cat_id": null,
                "cat_name": null
            }],
            select_i: '',
            cbj:'{/literal}{$initial->cbj}{literal}',
            yj:'{/literal}{$initial->yj}{literal}',
            sj:'{/literal}{$initial->sj}{literal}',
            kucun:'{/literal}{$initial->kucun}{literal}',
            unit:'{/literal}{$initial->unit}{literal}',
            imageClickInfo:'',
            imageClickInfoIndex:0
        },
        created:function(){
            this.$nextTick(function () {
                $('#masks').hide()
            })
        },
        methods: {
            change: function(item, index) {
                this.checked_attr_list[index] = item;
            },
            handleImageClick(data,index){
                this.imageClickInfo = data;
                this.imageClickInfoIndex = index
            }
        }
    });

</script>
{/literal}
</body>
</html>
