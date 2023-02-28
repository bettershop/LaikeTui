<?php /* Smarty version 2.6.26, created on 2021-03-11 20:55:38
         compiled from add.tpl */ ?>
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


'; ?>

<title>添加商品分类</title>
</head>
<body>

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=product_class">商品分类</a> <span class="c-gray en">&gt;</span> 
    添加商品子类 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
    <form name="form1" action="index.php?module=product_class&action=add" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <input type="hidden" name="val" class="val" value="<?php echo $this->_tpl_vars['cid']; ?>
" >
        <input type="hidden" name="level" class="level" value="<?php echo $this->_tpl_vars['level']; ?>
" >

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>分类级别：</label>
            <div class="formControls col-1">
                <select name="select_c" class="select" onchange="slevel()" id="select_c"  disabled>
                    <?php if ($this->_tpl_vars['level01'] == 0): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 0): ?>selected="true"<?php endif; ?> value="0">顶级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] == 1): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 1): ?>selected="true"<?php endif; ?> value="1">一级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] == 2): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 2): ?>selected="true"<?php endif; ?> value="2">二级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] == 3): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 3): ?>selected="true"<?php endif; ?> value="3">三级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] == 4): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 4): ?>selected="true"<?php endif; ?> value="4">四级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] == 5): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 5): ?>selected="true"<?php endif; ?> value="5">五级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] == 6): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 5): ?>selected="true"<?php endif; ?> value="6">六级</option>
                    <?php endif; ?>
                    <?php if ($this->_tpl_vars['level01'] >= 7): ?>
                    <option <?php if ($this->_tpl_vars['level'] == 5): ?>selected="true"<?php endif; ?> value="7">七级</option>
                    <?php endif; ?>
                </select>
               
            </div>
        </div>

        <div class="row cl slevel_box" style="display: none;">
            <label class="form-label col-4"><span class="c-red"></span>上级分类：</label>
            <div class="formControls col-1 slevel_1">
                <select name="select_1" class="select" onchange="one()" id="select_1"  disabled>
                    <!-- <option selected="true" value="0">请选择</option> -->
                    <?php echo $this->_tpl_vars['ctype']; ?>

                </select>
               
            </div>
            <div class="formControls col-1 slevel_2">
                <select name="select_2" class="select" onchange="two()" id="select_2"  disabled>
                    <!-- <option selected="true" value="0">请选择</option> -->
                    <?php echo $this->_tpl_vars['ctype1']; ?>

                </select>
                
            </div>
            <div class="formControls col-1 slevel_3"> 
                <select name="select_3" class="select" onchange="three()" id="select_3"  disabled>
                    <!-- <option selected="true" value="0">请选择</option> -->
                    <?php echo $this->_tpl_vars['ctype2']; ?>

                </select>
                
            </div>

            <div class="formControls col-1 slevel_4"> 
                <select name="select_4" class="select" onchange="four()" id="select_4"  disabled>
                    <!-- <option selected="true" value="0">请选择</option> -->
                    <?php echo $this->_tpl_vars['ctype2']; ?>

                </select>
                
            </div>

            <div class="formControls col-1 slevel_5"> 
                <select name="select_5" class="select" onchange="five()" id="select_5"  disabled>
                    <!-- <option selected="true" value="0">请选择</option> -->
                    <?php echo $this->_tpl_vars['ctype2']; ?>

                </select>
                
            </div>

        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>分类名称：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" autocomplete="off" name="pname" datatype="*6-18" style="width: 260px; " placeholder="
请输入分类名称">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>图片：</label>
            <div class="formControls col-xs-8 col-sm-6"> 
                <img id="thumb_url" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
noimg.jpg' class="cinage"  style="height:60px;width:60px;">
                <input type="hidden"  id="picurl" name="image" datatype="*"nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" value="" >
                <div>图片尺寸:60*60</div>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>分类展示图片：</label>
            <div class="formControls col-xs-8 col-sm-6"> 
                <img id="thumb_urlbg" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
noimg.jpg' class="bg" style="height:60px;width:60px">
                <input type="hidden"  id="picurlbg" name="bg" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" value="" >
                <div>展示图尺寸:220*145</div>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl" style="display: none;">
            <label class="form-label col-4"><span class="c-red"></span>排序号：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" name="sort" value="100" datatype="*6-18" style="width: 260px;">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"></label>
            <div class="formControls col-4">
                <input type="submit" name="Submit" value="提 交" class="btn btn-primary radius">
                <input type="button" name="reset" value="返 回"  class="btn btn-primary radius" id="resetId" onclick="javascript:location.href='index.php?module=product_class';">
                <!-- <input type="reset" name="reset" value="清 空"  class="btn btn-primary radius"> -->
            </div>
        </div>
    </form>
    <input type="hidden" id="pic" value="<?php echo $this->_tpl_vars['pic']; ?>
" >
</div>

<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>


<script>

var str_option = <?php echo $this->_tpl_vars['str_option']; ?>
;
 var pic = $("#pic").val();

<?php echo '

$(function(){  
    slevel();
    onlaod();
});

function onlaod() {
    var arr = [];
    var keds = [];
    for (var i in str_option) {
        arr.push(str_option[i]); //属性
        keds.push(i);
    }
    for (var i = 0; i < arr.length; i++) {
        // console.log(\'str_option\',str_option);
        var tid = i+1;
        var obj = $("#select_"+tid);
        // console.log("#select_"+tid);
        var str = \'\';
        for (var j = 0; j < arr[i].length; j++) {
            if(keds[i]==arr[i][j].cid){
                str += \'<option selected="true" value="\'+arr[i][j].cid+\'">\'+arr[i][j].pname+\'</option>\';
            }else{
                str += \'<option  value="\'+arr[i][j].cid+\'">\'+arr[i][j].pname+\'</option>\';
            }
            
        }
        obj.append(str);
    }
}

function slevel() {
    var select_c = $("#select_c").val();
    if(select_c < 1){
        $(".slevel_box").hide();
    }else{
        $(".slevel_box").show();
        for (var i = 1; i <= 5; i++) {
            obj = $(".slevel_"+i);
            if(i <= select_c){
                obj.show();
            }else{
                obj.hide();
            }
        }
    }
}

KindEditor.ready(function(K) {
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
        fileManagerJson : \'style/kindeditor/php/file_manager_json.php\', //网络空间


    });
  //上传背景图片
  K(\'.cinage\').click(function() {
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

  K(\'.bg\').click(function() {
    editor.loadPlugin(\'image\', function() {
      editor.plugin.imageDialog({
         showRemote : true, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K(\'#picurlbg\').val(),
          clickFn : function(url, title, width, height, border, align) {
          K(\'#picurlbg\').val(url);
          $(\'#thumb_urlbg\').attr("src",url);
          $(\'#thumb_urlbg\').attr("style","width:220px;height:145px");
          editor.hideDialog();
        }
      });
    });
  });

});

function one() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var dropElement4 = document.getElementById("select_4");
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement1.value;
    RemoveDropDownList(dropElement2);
    RemoveDropDownList(dropElement3);
    RemoveDropDownList(dropElement4);
    RemoveDropDownList(dropElement5);
    // 选择了
    if(v != 0){
        $(\'.val\').val(v);
        $(\'.level\').val(1);
        $.ajax({
            type: "GET",
            url: location.href+\'&action=ajax&v=\'+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_2").append(obj);
            }
        });
    }else{
        $(\'.val\').val(\'\');
        $(\'.level\').val(\'\');
    }
}
function two() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var dropElement4 = document.getElementById("select_4");
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement2.value;


    RemoveDropDownList(dropElement3);
    RemoveDropDownList(dropElement4);
    RemoveDropDownList(dropElement5);

    if(v != 0){
        $(\'.val\').val(v);
        $(\'.level\').val(2);
        $.ajax({
            type: "GET",
            url: location.href+\'&action=ajax&v=\'+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_3").append(obj);
            }
        });
    }else{
        var dropElement1 = document.getElementById("select_1");
        var v1 = dropElement1.value;
        $(\'.val\').val(v1);
        $(\'.level\').val(1);
    }
}
function three() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var dropElement4 = document.getElementById("select_4");
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement3.value;

    RemoveDropDownList(dropElement4);
    RemoveDropDownList(dropElement5);

    if(v != 0){
        $(\'.val\').val(v);
        $(\'.level\').val(3);
        $.ajax({
            type: "GET",
            url: location.href+\'&action=ajax&v=\'+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_4").append(obj);
            }
        });
    }else{
        var dropElement2 = document.getElementById("select_2");
        var v1 = dropElement2.value;
        $(\'.val\').val(v1);
        $(\'.level\').val(2);
    }
}

function four() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var dropElement4 = document.getElementById("select_4");
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement4.value;

    RemoveDropDownList(dropElement5);

    if(v != 0){
        $(\'.val\').val(v);
        $(\'.level\').val(4);
        $.ajax({
            type: "GET",
            url: location.href+\'&action=ajax&v=\'+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_5").append(obj);
            }
        });
    }else{
        var dropElement3 = document.getElementById("select_3");
        var v1 = dropElement3.value;
        $(\'.val\').val(v1);
        $(\'.level\').val(3);
    }
}

function five() {
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement5.value;
    if(v != 0){
        $(\'.val\').val(v);
        $(\'.level\').val(5);
    }else{
        var dropElement4 = document.getElementById("select_4");
        var v1 = dropElement4.value;
        $(\'.val\').val(v1);
        $(\'.level\').val(4);
    }
}


function RemoveDropDownList(obj){
    if(obj){
        var len=obj.options.length;
        if(len>0){
            //alert(len);
            for(var i=len;i>=1;i--){
                obj.remove(i);
            }
        }
    }
}
</script>
'; ?>

</body>
</html>