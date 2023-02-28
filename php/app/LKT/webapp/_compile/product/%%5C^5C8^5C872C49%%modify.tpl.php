<?php /* Smarty version 2.6.26, created on 2021-03-04 16:00:17
         compiled from modify.tpl */ ?>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    
<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

    
    <?php echo '
    <script type="text/javascript">
        function check(f){
            if(Trim(f.product_title.value)==""){
                alert("商品名称不能为空！");
                f.pname.value = \'\';
                return false;
            }

            if(Trim(f.sort.value)==""){
                alert("排序不能为空！");
                return false;
            }
            return true;
        }
    </script>
    '; ?>

    <?php echo '
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

        .formInputSD input[type=\'number\'] {
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
        .switch{
            appearance: none;
            -moz-appearance:button;
            -webkit-appearance: none;
        }
        .switch {
            position: relative;
            margin: 0;
            width: 40PX;
            height: 24PX;
            border: 1PX solid #EBEBF9;
            outline: 0;
            border-radius: 16PX;
            box-sizing: border-box;
            background-color: #EBEBF9;
            -webkit-transition: background-color 0.1s, border 0.1s;
            transition: background-color 0.1s, border 0.1s;
            visibility: inherit !important;
        }

        .switch:before {
            content: " ";
            position: absolute;
            top: 0;
            left: 0;
            width: 38PX;
            height: 22PX;
            border-radius: 19PX;
            background-color: #EBEBF9;
            -webkit-transition: -webkit-transform 0.35s cubic-bezier(0.45, 1, 0.4, 1);
            transition: -webkit-transform 0.35s cubic-bezier(0.45, 1, 0.4, 1);
            transition: transform 0.35s cubic-bezier(0.45, 1, 0.4, 1);
        }

        .switch:after {
            content: " ";
            position: absolute;
            top: 0;
            left: 1px;
            width: 22PX;
            height: 22PX;
            border-radius: 15PX;
            background-color: #FFFFFF;
            /*box-shadow: 0 1PX 3PX rgba(0, 0, 0, 0.4);*/
            -webkit-transition: -webkit-transform 0.35s cubic-bezier(0.4, 0.4, 0.25, 1.35);
            transition: -webkit-transform 0.35s cubic-bezier(0.4, 0.4, 0.25, 1.35);
            transition: transform 0.35s cubic-bezier(0.4, 0.4, 0.25, 1.35);
        }

        .switch:checked{
            background: #00D287;
            border: solid 1px #00D287;
        }

        .switch:checked:before{
            transform: scale(0);
        }

        .switch:checked:after{
            transform: translateX(15PX);
        }
        .blg{
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .blg span {
            padding-left: 5px;
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
    '; ?>

    <title>修改商品</title>
</head>
<body>
    <div id="masks">
        <img src="images/icon1/loads.gif">
    </div>


<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=product">商品列表</a> <span class="c-gray en">&gt;</span> 
    修改商品 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>

    

    <div class="pd-20" id="page">
        <form id="form1" name="form1" action="index.php?module=product&action=modify" enctype="multipart/form-data" method="post" onsubmit="return check(this);">
            
            <input type="hidden" name="id" value='<?php echo $this->_tpl_vars['id']; ?>
'/>
            <input type="hidden" name="uploadImg" value='<?php echo $this->_tpl_vars['uploadImg']; ?>
'/>
            <input type="hidden" name="attribute" class="attribute" id="attribute" value='<?php echo $this->_tpl_vars['attribute1']; ?>
'/>
            <input type="hidden" name="attribute_key" class="attribute_key" id="attribute_key" value='<?php echo $this->_tpl_vars['attribute_key2']; ?>
'/>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>商品标题：</label>
                <div class="formControls col-4" style="width: 16.8%;">
                    <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['product_title']; ?>
" placeholder="" id="product_titleId" name="product_title">
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red"></span>副标题：</label>
                <div class="formControls" style="display: inline-block;">
                    <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['subtitle']; ?>
" placeholder="" id="subtitleId" name="subtitle">
                </div>
                <text style="line-height:30px;">*简洁表达商品，用来显示在首页商品，避免截取时不能表达是什么商品。</text>
            </div>


        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品类别：</label>
            <div class="formControls col-2"> <!-- <span class="select-box"> -->
                <select name="product_class" class="select" id="product_classId" style="width:300px;">
                    <option selected="selected" value="0">请选择类别</option>
                    <?php echo $this->_tpl_vars['ctypes']; ?>

                </select>
                <!-- </span> -->
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品品牌：</label>
            <div class="formControls col-2"><!--  <span class="select-box"> -->
                <select name="brand_class" class="select" id="brand_classId" style="width:300px;">
                    <option selected="selected" value="0">请选择品牌</option>
                    <?php echo $this->_tpl_vars['r02']; ?>

                </select>
                <!-- </span> -->
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品主图：</label>
            <div class="formControls col-xs-8 col-sm-10" style="width: 20%;">
                <input type="hidden" id="picurl" value="<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['imgurl']; ?>
" class="td-input" name="image"/>
                <input type="hidden" name="img_oldpic" value="<?php echo $this->_tpl_vars['imgurl']; ?>
" >
                <img id="thumb_url" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['imgurl']; ?>
' style="height:160px;width:160px">
                <span class="btn btn-success " id="image"  type="button" >选择图片</span>
                （建议上传160px*160px）
            </div>


        </div>
        <div class="row cl">
            <label class="form-label col-2">商品展示图：</label>
            <div class="formControls col-10">
                <div class="uploader-thum-container ">
                    <input name="imgurls[]" id="imgurls"  multiple='multiple' type="file" accept="upload_image/x-png,image/gif,image/jpeg" />
                    <?php if ($this->_tpl_vars['imgurls'] != ''): ?>
                    <div class="leftright" >
                    <?php $_from = $this->_tpl_vars['imgurls']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f1'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f1']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['item']):
        $this->_foreach['f1']['iteration']++;
?>
                        <img class="pimg" src="<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['item']->product_url; ?>
" style="width: 50px;height: 50px;">
                    <?php endforeach; endif; unset($_from); ?>
                    </div>
                    <?php endif; ?>
                    注:最多五张
                </div>
                （建议上传375px*375px）
            </div>
        </div>




            <div class="row cl" style="display:none">
                <label class="form-label col-2"><span class="c-red">*</span>重量：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['weight']; ?>
" placeholder="" id="weightId" name="weight">
                </div>
                <text style="line-height:30px;">克</text>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>成本价：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input class="input-text" style="width:300px;" type="text" name="initial[cbj]" onkeypress="return noNumbers(event)" min="0"  step="0.01" onblur="set_cbj(this);" value="<?php echo $this->_tpl_vars['initial']->cbj; ?>
" placeholder="请设置商品的默认成本价" >
                </div>
                <text style="line-height:30px;">克</text>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>原价：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input class="input-text" style="width:300px;" type="text" name="initial[yj]" onkeypress="return noNumbers(event)" min="0"  step="0.01" onblur="set_yj(this);" value="<?php echo $this->_tpl_vars['initial']->yj; ?>
" placeholder="请设置商品的默认原价" >
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>售价：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input class="input-text" style="width:300px;" type="text" name="initial[sj]" onkeypress="return noNumbers(event)" min="0"  step="0.01" onblur="set_sj(this);" value="<?php echo $this->_tpl_vars['initial']->sj; ?>
" placeholder="请设置商品的默认售价" >
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>单位：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <select name="initial[unit]" class="select " style="width: 300px;" id="unit" style="width:300px;">
                        <option value="盒" <?php if ($this->_tpl_vars['initial']->unit == '盒'): ?> selected="selected" <?php endif; ?>>盒</option>
                        <option value="篓"<?php if ($this->_tpl_vars['initial']->unit == '篓'): ?> selected="selected" <?php endif; ?>>篓</option>
                        <option value="箱" <?php if ($this->_tpl_vars['initial']->unit == '箱'): ?> selected="selected" <?php endif; ?>>箱</option>
                        <option value="个" <?php if ($this->_tpl_vars['initial']->unit == '个'): ?> selected="selected" <?php endif; ?>>个</option>
                        <option value="套" <?php if ($this->_tpl_vars['initial']->unit == '套'): ?> selected="selected" <?php endif; ?>>套</option>
                        <option value="包" <?php if ($this->_tpl_vars['initial']->unit == '包'): ?> selected="selected" <?php endif; ?>>包</option>
                        <option value="支" <?php if ($this->_tpl_vars['initial']->unit == '支'): ?> selected="selected" <?php endif; ?>>支</option>
                        <option value="条" <?php if ($this->_tpl_vars['initial']->unit == '条'): ?> selected="selected" <?php endif; ?>>条</option>
                        <option value="根" <?php if ($this->_tpl_vars['initial']->unit == '根'): ?> selected="selected" <?php endif; ?>>根</option>
                        <option value="本" <?php if ($this->_tpl_vars['initial']->unit == '本'): ?> selected="selected" <?php endif; ?>>本</option>
                        <option value="瓶" <?php if ($this->_tpl_vars['initial']->unit == '瓶'): ?> selected="selected" <?php endif; ?>>瓶</option>
                        <option value="块" <?php if ($this->_tpl_vars['initial']->unit == '块'): ?> selected="selected" <?php endif; ?>>块</option>
                        <option value="片" <?php if ($this->_tpl_vars['initial']->unit == '片'): ?> selected="selected" <?php endif; ?>>片</option>
                        <option value="把" <?php if ($this->_tpl_vars['initial']->unit == '把'): ?> selected="selected" <?php endif; ?>>把</option>
                        <option value="组" <?php if ($this->_tpl_vars['initial']->unit == '组'): ?> selected="selected" <?php endif; ?>>组</option>
                        <option value="双" <?php if ($this->_tpl_vars['initial']->unit == '双'): ?> selected="selected" <?php endif; ?>>双</option>
                        <option value="台" <?php if ($this->_tpl_vars['initial']->unit == '台'): ?> selected="selected" <?php endif; ?>>台</option>
                        <option value="件" <?php if ($this->_tpl_vars['initial']->unit == '件'): ?> selected="selected" <?php endif; ?>>件</option>
                        <option value="斤" <?php if ($this->_tpl_vars['initial']->unit == '斤'): ?> selected="selected" <?php endif; ?>>斤</option>
                    </select>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>库存：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <input type="number" class="input-text"  style="width:300px;" name="initial[kucun]" oninput="value=value.replace(/[^\d]/g,'')" min="0"  step="1" onblur="set_kucun(this);" value="<?php echo $this->_tpl_vars['initial']->kucun; ?>
" placeholder="请设置商品的默认库存" >
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>属性名称SKU：</label>
                <div class="formControls col-4" style="width: 26.8%;">
                    <div class="arrt_flex">
                        <div class="arrt_froup">
                            <input type="text" class="add-attr-group-input input-text" style="width:300px;" placeholder="请输入属性名称">
                            <a class="add-attr-group-btn arrt_add" href="javascript:" style="display: none;"><span>添加属性</span></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="formDivSD">
                <div class="formContentSD">

                    <?php echo '
                    <!-- 有规格 -->
                    <div >
                        <div class="arrt_block arrt_two">
                            <div class="arrt_width">
                                <div v-for="(attr_group,i) in attr_group_list"  class="attr-group">
                                    <div class="attr-list">
                                        <div class="attr-input-group" >
                                            <div class="attr_input_group">
                                                <span class="formTextSD" style="margin: 0px;" >{{attr_group.attr_group_name}}：</span>
                                                <input class="add-attr-input add_input formInputSD" placeholder="请输入属性值" style="margin-left: 12px;padding-left: 10px">
                                                <a v-bind:index="i" class="add-attr-btn adds_ntn" href="javascript:"><span>添加属性值</span></a>
                                                <a v-bind:index="i" href="javascript:" class="attr-group-delete dels_btn" v-if="attr_group.status"><span>删除属性值</span></a>
                                            </div>
                                            <div class="arrt_bgcolor" style="margin-left: 12%;margin-top:10px"  >
                                                <div v-for="(attr,j) in attr_group.attr_list" class="attr_input_group arrt_clear arrt_zi">
                                                    <span >属性值：</span>
                                                    <input class="add-attr-input " :value="attr.attr_name" readonly="readonly" style="margin-left: 12px;padding-left: 10px;background-color: #F8F8F8 !important;">
                                                    <a v-bind:group-index="i" v-bind:index="j" class="attr-delete" href="javascript:" v-if="attr.status">
                                                        <img src="images/iIcon/jh.png" class="form_plus_u" />
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="arrt_bgcolor arrt_fiv" style="margin-left: 11%;">
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
                                        <input type="hidden"  v-bind:name="\'attr[\'+index+\'][cid]\'" :value="item.cid">
                                        <td v-for="(attr,attr_index) in item.attr_list">
                                            <input type="hidden"  v-bind:name="\'attr[\'+index+\'][attr_list][\'+attr_index+\'][attr_id]\'" v-bind:value="attr.attr_id">

                                            <input type="hidden" v-bind:name="\'attr[\'+index+\'][attr_list][\'+attr_index+\'][attr_name]\'" v-bind:value="attr.attr_name">

                                            <input type="hidden" v-bind:name="\'attr[\'+index+\'][attr_list][\'+attr_index+\'][attr_group_name]\'" v-bind:value="attr.attr_group_name">
                                            <span>{{attr.attr_name}}</span>
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" type="text"  v-bind:name="\'attr[\'+index+\'][costprice]\'" :value="item.costprice">
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" type="text"   v-bind:name="\'attr[\'+index+\'][yprice]\'" :value="item.yprice" >
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" type="text"   v-bind:name="\'attr[\'+index+\'][price]\'" :value="item.price">
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm" oninput="value=value.replace(/[^\\d]/g,\'\')" v-bind:name="\'attr[\'+index+\'][num]\'"  step="1" :value="item.num" >
                                        </td>
                                        <td>
                                            <input class="unit" v-bind:name="\'attr[\'+index+\'][unit]\'" :value="item.unit" style="border: 0px;background-color: transparent;" readOnly="readOnly">
                                        </td>

                                        <td>
                                            <div  class="upload-group form_group form_flex">
                                                <!-- 不存在 -->
                                                <div class="form_attr_img " style="border: 1px solid;
    background-color: black;">
                                                    <input type="hidden" :id="\'picurl2\'+index" v-bind:name="\'attr[\'+index+\'][img]\'" datatype="*" nullmsg="请选择图片"/>
                                                    <img src="images/icon1/add_g_t.png" :id="\'pic2\'+index" class="upload-preview-img form_att select-file" @click="handleImageClick(item,index)" onclick="setTimeoutClick() ">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    '; ?>

                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">显示类型：</label>
                <div class="formControls col-xs-8 col-sm-8 skin-minimal">
                    <div class="ra1">
                        <input name="s_type[]" type="checkbox" class="inputC" id="sex-1" value="1" <?php if (in_array ( 1 , $this->_tpl_vars['s_type'] )): ?>checked="checked"<?php endif; ?>>
                        <label for="sex-1">新品</label>
                    </div>
                    <div class="ra1">
                        <input type="checkbox" id="sex-2" class="inputC" name="s_type[]" value="2" <?php if (in_array ( 2 , $this->_tpl_vars['s_type'] )): ?>checked="checked"<?php endif; ?>>
                        <label for="sex-2">热销</label>
                    </div>
                    <div class="ra1">
                        <input type="checkbox" id="sex-3" class="inputC" name="s_type[]" value="3" <?php if (in_array ( 3 , $this->_tpl_vars['s_type'] )): ?>checked="checked"<?php endif; ?>>
                        <label for="sex-3">推荐</label>
                    </div>
                    <div class="ra1" style="width:100px;">
                        <input type="checkbox" id="sex-4" name="s_type[]" class="inputC" value="4" <?php if (in_array ( 4 , $this->_tpl_vars['s_type'] )): ?>checked="checked"<?php endif; ?>>
                        <label for="sex-4" style="width:100%;">首页推荐</label>
                    </div>
                </div>
            </div>


            <div class="row cl">
                <label class="form-label col-2">拟定销量：</label>
                <div class="formControls col-2">
                    <input type="number" class="input-text" value="<?php echo $this->_tpl_vars['volume']; ?>
" name="volume" id="volumeId">
                </div>
            </div>


            <div class="row cl">
                <label class="form-label col-2">排序：</label>
                <div class="formControls col-2">
                    <input type="number" class="input-text" value="<?php echo $this->_tpl_vars['sort']; ?>
" name="sort" id="sort">
                </div>
            </div>
            
           
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>运费设置：</label>
            <div class="formControls col-2"> <!-- <span class="select-box"> -->
                <select name="freight" class="select" id="freightId">
                    <?php echo $this->_tpl_vars['freight_list']; ?>

                </select>
                <!-- </span> -->
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">详细内容：</label>
            <div class="formControls col-xs-8 col-sm-10">
                <script id="editor" type="text/plain" name="content" style="width:100%;height:400px;"><?php echo $this->_tpl_vars['content']; ?>
</script>
            </div>
        </div>
        <div style="height: 20px;"></div>
        <div class="row cl page_bort_bottom">
             <label class="form-label col-2"></label>
            <div class="formControls col-2">
                <input type="submit" name="Submit" value="提 交" class="btn btn-primary radius btn-right" onclick="check()">
                <input type="button" name="reset" value="返 回" onclick="javascript:history.back(-1);" class="btn btn-primary radius btn-left" >
            </div>
        </div>
    </form>
</div>

<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>

<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css"/>
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
<script language="javascript"  src="style/js/check.js"> </script>
<script src="style/js/vue.min.js"></script>
<script type="text/javascript" src="style/js/jquery.dragsort-0.5.1.min.js"></script>

<?php echo '
<script>
    $(function() {
        try{
            $(".leftright").dragsort({
                dragSelector: "img",
                dragBetween: true ,
                dragEnd:function(){

                    console.log(\'此处可放拖动结束后的方法。\')
                }
            });
        }catch(e){
        }
    });

    $(document.body).css({
        "overflow-y": "hidden"
    });
    $(function() {
        $("#imgurls").change(function() {
            var files = this.files;
            if (files && files.length > 5) {
                alert("超过5张");
            this.value = "" //删除选择
            // $(this).focus(); //打开选择窗口
        }
    })
    })
    KindEditor.ready(function(K) {
        var editor = K.editor({
            allowFileManager : true,
        uploadJson : "index.php?module=system&action=uploadImg", //上传功能
        fileManagerJson : \'style/kindeditor/php/file_manager_json.php\', //网络空间
    });
    //上传背景图片
    K(\'#image\').click(function() {
        editor.loadPlugin(\'image\', function() {
            editor.plugin.imageDialog({
                showRemote : true, //网络图片不开启
                //showLocal : false, //不开启本地图片上传
                imageUrl : K(\'#picurl\').val(),
                clickFn : function(url, title, width, height, border, align) {
                    K(\'#picurl\').val(url);
                    $(\'#thumb_url\').attr("src",url);
                    editor.hideDialog();
                }
            });
        });
    });
});
    var isShow = false
    $(function(){
        var ue = UE.getEditor(\'editor\');
        var distribution ='; ?>
<?php echo $this->_tpl_vars['data']['is_distribution']; ?>
<?php echo '

        $("#islabel1").hide()
        $("#islabel2").hide()
        $("#islabel3").hide()
        $("#ischange").click(function(e) {
            isShow = !isShow
            if(isShow){
                $(\'#iaaaa\').remove()
                $("#islabel1").show()
                $("#islabel2").show()
                $("#islabel3").show()
                return
            }       

            $("#islabel1").hide()
            $("#islabel2").hide()
            $("#islabel3").hide()
            $(\'#iaaaa\').hide()
            $("#ishidden").append(`<input type="hidden" name="is_distribution" value="0" id="iaaaa">`)
        })

        
        if(distribution == 1){
            $("#islabel1").show()
            $("#islabel2").show()
            $("#islabel3").show()
            isShow = true
        }
    });


    $(document).ready(function(){
        var imgList = getTheImgList();
        if(imgList){
            for(var i=0; i<imgList.length; i++){
                $(\'#pic2\'+i).attr("src", imgList[i].img);
                $(\'#picurl2\'+i).val(imgList[i].img);
            }
        }
    });
// 获取属性值上传图片
function getTheImgList(){
    var imgList = JSON.parse(\''; ?>
<?php echo $this->_tpl_vars['checked_attr_list']; ?>
<?php echo '\', true)
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
    num += \'\';  
    num = num.replace(/[^0-9|\\.]/g, \'\'); //清除字符串中的非数字非.字符  
    
    if(/^0+/) //清除字符串开头的0  
        num = num.replace(/^0+/, \'\');  
    if(!/\\./.test(num)) //为整数字符串在末尾添加.00  
        num += \'.00\';  
    if(/^\\./.test(num)) //字符以.开头时,在开头添加0  
        num = \'0\' + num;  
    num += \'00\';        //在字符串末尾补零  
    num = num.match(/\\d+\\.\\d{2}/)[0];  
    return num;
}
//控制价格显示小数点2位
function noNumbers(e){
    console.log(\'onkeypress\')
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

    $(\'#unit\').live(\'click\', function() {
        var unit = $("#unit").val();
        page.unit = $("#unit").val();
        $(".unit").val(unit);
    });
    //设置成本价等
    function set_cbj(obj) {
        page.cbj = $(obj).val();
        if(page.checked_attr_list.length > 0){
            for (k in page.checked_attr_list){
                page.checked_attr_list[k].costprice = $(obj).val();
            }
        }

    }
    function set_yj(obj) {
        page.yj = $(obj).val();
        if(page.checked_attr_list.length > 0){
            for (k in page.checked_attr_list){
                page.checked_attr_list[k].yprice = $(obj).val();
            }
        }
    }
    function set_sj(obj) {
        page.sj = $(obj).val();
        if(page.checked_attr_list.length > 0){
            for (k in page.checked_attr_list){
                page.checked_attr_list[k].price = $(obj).val();
            }
        }
    }
    function set_kucun(obj) {
        page.kucun = $(obj).val();
        if(page.checked_attr_list.length > 0){
            for (k in page.checked_attr_list){
                page.checked_attr_list[k].num = $(obj).val();
            }
        }
    }


    var Map = function() {
        this._data = [];
        this.set = function(key, val) {
            for(var i in this._data) {
                if(this._data[i].key == key) {
                    this._data[i].val = val;
                    return true;
                }
            }
            this._data.push({
                key: key,
                val: val,
            });
            return true;
        };
        this.get = function(key) {
            for(var i in this._data) {
                if(this._data[i].key == key)
                    return this._data[i].val;
            }
            return null;
        };
        this.delete = function(key) {
            for(var i in this._data) {
                if(this._data[i].key == key) {
                    this._data.splice(i, 1);
                }
            }
            return true;
        };
    };
    
    
    var map = new Map();
 
    var page = new Vue({
        el: "#page",
        data: {
            
            sub_cat_list: [],
            attr_group_list: JSON.parse(\''; ?>
<?php echo $this->_tpl_vars['attr_group_list']; ?>
<?php echo '\', true), //可选规格数据
            attr_group_count: JSON.parse(\''; ?>
<?php echo $this->_tpl_vars['attr_group_list']; ?>
<?php echo '\', true).length,
            checked_attr_list: JSON.parse(\''; ?>
<?php echo $this->_tpl_vars['checked_attr_list']; ?>
<?php echo '\', true), //已选规格数据
            old_checked_attr_list: [],
            goods_card_list: [],
            card_list: [],
            goods_cat_list: [{
                "cat_id": null,
                "cat_name": null
            }],
            select_i: \'\',
            cbj:\''; ?>
<?php echo $this->_tpl_vars['initial']->cbj; ?>
<?php echo '\',
            yj:\''; ?>
<?php echo $this->_tpl_vars['initial']->yj; ?>
<?php echo '\',
            sj:\''; ?>
<?php echo $this->_tpl_vars['initial']->sj; ?>
<?php echo '\',
            kucun:\''; ?>
<?php echo $this->_tpl_vars['initial']->kucun; ?>
<?php echo '\',
            unit:\''; ?>
<?php echo $this->_tpl_vars['initial']->unit; ?>
<?php echo '\',
            imageClickInfo:\'\',
            imageClickInfoIndex:0
        },
        created:function(){
            this.$nextTick(function () {
                $(\'#masks\').hide()
                $(document.body).css({
                    "overflow-y": "auto"
                })
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
    console.log(\'checked_attr_list 全局\')
    console.log(page.checked_attr_list)
    console.log(page)
    // 点击属性框，显示添加属性按钮
    $(".add-attr-group-input").live("click", function(){
        $(".add-attr-group-btn").css("display", "");
    });

    $(".add-attr-group-input").live("focus", function(){
        $(".add-attr-group-btn").css("display", "");
    });

    // 属性框离开事件，延迟500毫秒隐藏隐藏添加属性按钮
    $(".add-attr-group-input").blur(function(){
        setTimeout(\'$(".add-attr-group-btn").css("display", "none")\', 500);
    });
    // 添加属性
    $( ".add-attr-group-btn").live("click", function() {
        var name = $(".add-attr-group-input").val();
        name = $.trim(name);
        if(name == ""){
            alert("属性名不能为空！");
            return;
        }

        for(var i in page.attr_group_list) {
            if(page.attr_group_list[i].attr_group_name == name){
                alert("属性名称重复！");
                return;
            }
        }
        page.attr_group_list.push({
            attr_group_name: name,
            attr_list: [],
        });
        $(".add-attr-group-input").val("");
        page.checked_attr_list = getAttrList();
        //获取属性值上传图片
        getTheImgList()
        console.log(\'checked_attr_list\')
        console.log(page.checked_attr_list)
    });
    // 添加属性值
    $( ".add-attr-btn").live("click", function() {
        var name = $(this).parents(".attr-input-group").find(".add-attr-input").val();
        var index = $(this).attr("index");
        name = $.trim(name);
        if(name == ""){
            alert("属性值不能为空！");
            return;
        }

        /**筛选重复*/
        var ck_list = page.checked_attr_list;
        if(ck_list.length > 0){
            for(var pi in ck_list) {
                if(ck_list[pi].attr_list[index]){
                    if(name == ck_list[pi].attr_list[index].attr_name){
                        alert("属性值重复！");
                        return;
                    }
                }
            }
        }
        /**筛选重复*/

        page.attr_group_list[index].attr_list.push({
            attr_name: name,
            status:true
        });
        // 如果是单规格的，添加新规格时不清空原先的数据
        page.old_checked_attr_list = page.checked_attr_list;
        var attrList = getAttrList();
        if(page.attr_group_list.length === 1) {
            for(var i in attrList) {
                if(i > page.old_checked_attr_list.length - 1) {
                    page.old_checked_attr_list.push(attrList[i])
                }
            }
            var newCheckedAttrList = page.old_checked_attr_list;
        } else if(page.attr_group_list.length === page.attr_group_count) {
            for(var pi in attrList) {
                var pAttrName = \'\';
                for(var pj in attrList[pi].attr_list) {
                    pAttrName += attrList[pi].attr_list[pj].attr_name
                }
                for(var ci in page.old_checked_attr_list) {
                    var cAttrName = \'\';
                    for(var cj in page.old_checked_attr_list[ci].attr_list) {
                        cAttrName += page.old_checked_attr_list[ci].attr_list[cj].attr_name;
                    }
                    if(pAttrName === cAttrName) {
                        attrList[pi] = page.old_checked_attr_list[ci];
                    }
                }
            }
            var newCheckedAttrList = attrList;
        } else {
            var newCheckedAttrList = attrList;
        }
        $(this).parents(".attr-input-group").find(".add-attr-input").val("");
        page.checked_attr_list = newCheckedAttrList;
    });
    // 删除属性
    $( ".attr-group-delete").live("click",function() {
        var index = $(this).attr("index");
        page.attr_group_list.splice(index, 1);
        page.checked_attr_list = getAttrList();
    });
    // 删除属性值
    $(".attr-delete").live("click", function() {
        var index = $(this).attr("index");
        var group_index = $(this).attr("group-index");
        console.log(page.attr_group_list[group_index]);
        page.attr_group_list[group_index].attr_list.splice(index, 1);
        // 如果是单规格的，删除规格时不清空原先的数据
        page.old_checked_attr_list = page.checked_attr_list;
        var attrList = getAttrList();
        if(page.attr_group_list.length === 1) {
            var newCheckedAttrList = [];
            for(var i in page.attr_group_list[0].attr_list) {
                var attrName = page.attr_group_list[0].attr_list[i].attr_name;
                for(j in page.old_checked_attr_list) {
                    var oldAttrName = page.old_checked_attr_list[j].attr_list[0].attr_name;
                    if(attrName === oldAttrName) {
                        newCheckedAttrList.push(page.old_checked_attr_list[j]);
                        break;
                    }
                }
            }
        } else if(page.attr_group_list.length === page.attr_group_count) {
            for(var pi in attrList) {
                var pAttrName = \'\';
                for(var pj in attrList[pi].attr_list) {
                    pAttrName += attrList[pi].attr_list[pj].attr_name
                }
                for(var ci in page.old_checked_attr_list) {
                    var cAttrName = \'\';
                    for(var cj in page.old_checked_attr_list[ci].attr_list) {
                        cAttrName += page.old_checked_attr_list[ci].attr_list[cj].attr_name;
                    }
                    if(pAttrName === cAttrName) {
                        attrList[pi] = page.old_checked_attr_list[ci];
                    }
                }
            }
            var newCheckedAttrList = attrList;
        } else {
            var newCheckedAttrList = attrList;
        }

        page.checked_attr_list = newCheckedAttrList;
    });

    function getAttrList() {
        var array = [];
        for(var i in page.attr_group_list) {
            for(var j in page.attr_group_list[i].attr_list) {
                var object = {
                    attr_group_name: page.attr_group_list[i].attr_group_name,
                    attr_id: null,
                    attr_name: page.attr_group_list[i].attr_list[j].attr_name,
                };
                if(!array[i])
                    array[i] = [];
                array[i].push(object);
            }
        }

        var len = array.length;
        var results = [];
        var indexs = {};
        function specialSort(start) {
            start++;
            if(start > len - 1) {
                return;
            }
            if(!indexs[start]) {
                indexs[start] = 0;
            }
            if(!(array[start] instanceof Array)) {
                array[start] = [array[start]];
            }

            for(indexs[start] = 0; indexs[start] < array[start].length; indexs[start]++) {
                specialSort(start);
                if(start == len - 1) {
                    var temp = [];
                    for(var i = len - 1; i >= 0; i--) {
                        if(!(array[start - i] instanceof Array)) {
                            array[start - i] = [array[start - i]];
                        }
                        if(array[start - i][indexs[start - i]]) {
                            temp.push(array[start - i][indexs[start - i]]);
                        }
                    }

                    var key = [];
                    for(var i in temp) {
                        key.push(temp[i].attr_id);
                    }
                    var oldVal = map.get(key.sort().toString());
                    console.log(\'getAttrList key\')
                    console.log(key)
                    var unit = $("#unit").val();
                    if(oldVal) {
                        console.log(\'getAttrList 有oldVal\')
                        results.push({
                            attr_list: temp,
                            costprice: oldVal.no,
                            yprice: oldVal.pic,
                            price: oldVal.price,
                            num: oldVal.num,
                            unit: unit,
                            img: \'\',
                        });
                    } else {
                        console.log(\'getAttrList 无oldVal\')
                        console.log(page)
                        var img = $(".upload-preview-list").children(":first").children(\'.upload-preview-img\').attr(\'src\');
                        results.push({
                            attr_list: temp,
                            costprice: page.cbj,
                            yprice: page.yj,
                            price: page.sj,
                            num: page.kucun,
                            unit: unit,
                            img: img
                        });
                    }
                }
            }
        }
        specialSort(-1);
        return results;
    }
    
    document.onkeydown = function(e) {
        if(!e) e = window.event;
        if((e.keyCode || e.which) == 13) {
            $("[name=Submit]").click();
        }
    }

    var t_check = true;
    
    var GetLength = function (str) {
        ///<summary>获得字符串实际长度，中文2，英文1</summary>
        ///<param name="str">要获得长度的字符串</param>
        var realLength = 0, len = str.length, charCode = -1;
        for (var i = 0; i < len; i++) {
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) realLength += 1;
            else realLength += 2;
        }
        return realLength;
    };

    // 表单验证
    function verificationForm(){

        var res = $(\'#form1\').serializeArray()
        var s_type = 0
        for(var i = 0; i < res.length; i++){
            
            if(res[i].name === \'product_title\' && res[i].value === \'\'){
                alert(\'请输入商品标题!\')
                return false
            } else if(res[i].name === \'product_class\' && res[i].value === \'0\'){
                alert(\'请选择商品类别!\')
                return false
            } else if(res[i].name === \'brand_class\' && res[i].value === \'0\'){
                alert(\'请选择品牌!\')
                return false
            } else if(res[i].name === \'image\' && res[i].value === \'\'){
                alert(\'请设置商品主图!\')
                return false
            } else if(res[i].name === \'initial[cbj]\' && res[i].value === \'\'){
                alert(\'请设置商品成本价!\')
                return false
            } else if(res[i].name === \'initial[yj]\' && res[i].value === \'\'){
                alert(\'请设置商品原价!\')
                return false 
            } else if(res[i].name === \'initial[sj]\' && res[i].value === \'\'){
                alert(\'请设置商品售价!\')
                return false 
            } else if(res[i].name === \'initial[unit]\' && res[i].value === \'\'){
                alert(\'请选择商品单位!\')
                return false 
            } else if(res[i].name === \'initial[kucun]\' && res[i].value === \'\'){
                alert(\'请设置商品库存!\')
                return false 
            } else if(res[i].name === \'s_type[]\') {
                s_type = 1
            }
        }

        if(page.$data.attr_group_list.length == 0){
            alert(\'请设置属性名称!\')
            return false
        }


        
        return true
    }
    var t_check = true;
    function check(f) {
        return verificationForm()
    }


    function setTimeoutClick(){
        console.log(\'timeout\')
        setTimeout(image_click, 100);
    }

// 属性值上传商品图片
function image_click() {
    console.log(\'image click\')
    var id = page.imageClickInfoIndex
    console.log(page.imageClickInfoIndex)

    KindEditor.ready(function (K) {
        var editor = K.editor({
            allowFileManager: true,
            uploadJson: "index.php?module=system&action=uploadImg", //上传功能
            fileManagerJson: \'style/kindeditor/php/file_manager_json.php\', //网络空间
        });
        editor.loadPlugin(\'image\', function () {
            editor.plugin.imageDialog({
                showRemote: true, //网络图片不开启
                //showLocal : false, //不开启本地图片上传
                imageUrl: K("#picurl2"+id).val(),
                clickFn: function (url, title, width, height, border, align) {
                    K(\'#picurl2\'+id).val(url);
                    $(\'#picurl2\'+id).val(url)
                    $(\'#pic2\'+id).attr("src", url);
                    editor.hideDialog();
                    console.log(url)
                    console.log($(\'#picurl2\'+id).val())
                }
            });
        });
    });

}


function resetButton(){
    $(\'#product_numberId\').val("");
    $(\'#product_titleId\').val("");
    $(\'#subtitleId\').val("");
    $("#product_classId option[value=\'0\']").attr("selected", "selected");
    $("#brand_classId option[value=\'0\']").attr("selected", "selected");
    $(\'#weightId\').val("");
    $(\'#sex-1\').attr("checked","");
    $(\'#sex-2\').attr("checked","");
    $(\'#sex-3\').attr("checked","");
    $(\'#volumeId\').val("");
    $("#freightId option[value=\'0\']").attr("selected", "selected");
    $(\'#ueditor_0\').contents().find(\'p\').html("");
}

    $(function(){
        $(".pimg").click(function(){
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
        });
    });

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

</script>
'; ?>

</body>
</html>