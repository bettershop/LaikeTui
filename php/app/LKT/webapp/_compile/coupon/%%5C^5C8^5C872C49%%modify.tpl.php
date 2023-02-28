<?php /* Smarty version 2.6.26, created on 2021-02-05 11:39:15
         compiled from modify.tpl */ ?>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>


    <?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>


    <?php echo '
        <script type="text/javascript">
            function show(obj) {
                if (obj.value == \'2\') { // 节日/活动
                    document.getElementById("name").readOnly = false; // 活动名称
                    document.getElementById(\'txt\').style.display = ""; // 不显示
                    document.getElementById(\'txt_1\').style.display = ""; // 金额不显示
                    document.getElementById(\'txt_2\').style.display = "none"; // 减不显示
                    document.getElementById(\'product_class_id\').style.display = ""; // 优惠劵类型id
                    document.getElementById(\'num\').style.display = ""; // 数量不显示
                    document.getElementById(\'z_money\').style.display = "none"; // 满金额不显示
                    document.getElementById(\'time\').style.display = ""; // 时间显示
                } else if (obj.value == \'1\') { // 注册
                    document.getElementById("name").readOnly = false; // 活动名称
                    document.getElementById(\'txt\').style.display = ""; // 显示
                    document.getElementById(\'txt_1\').style.display = ""; // 金额显示
                    document.getElementById(\'txt_2\').style.display = "none"; // 减不显示
                    document.getElementById(\'product_class_id\').style.display = "none"; // 优惠劵类型id
                    document.getElementById(\'num\').style.display = "none"; // 数量
                    document.getElementById(\'z_money\').style.display = "none"; // 满金额不显示
                    document.getElementById(\'time\').style.display = "none"; // 时间不显示
                }
            }

            function change() {
                var product_class_id = $(\'select[name="product_class_id"]\').children(\'option:selected\').val();
                $.ajax({
                    type: "GET",
                    url: location.href + \'&action=ajax&product_class_id=\' + product_class_id,
                    data: "",
                    success: function (msg) {
                        if (msg == 0) {
                            document.getElementById(\'product_id\').style.display = \'none\';
                        } else {
                            document.getElementById(\'product_id\').style.display = \'\';
                            $(".select2").html(msg);
                        }
                    }
                });
            }
        </script>
    '; ?>

    <title>修改活动</title>
</head>
<body>


<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=coupon">活动列表</a> <span class="c-gray en">&gt;</span>
    修改活动 <span class="c-gray en">&gt;</span>
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
    <form name="form1" action="index.php?module=coupon&action=modify" class="form form-horizontal" method="post"
          enctype="multipart/form-data">
        <input type="hidden" name="id" value="<?php echo $this->_tpl_vars['id']; ?>
">
        <input type="hidden" name="status" class="status" value="<?php echo $this->_tpl_vars['status']; ?>
">
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>活动名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" placeholder="" id="name" value="<?php echo $this->_tpl_vars['name']; ?>
" name="name"
                       <?php if ($this->_tpl_vars['activity_type'] == 3): ?>readonly<?php endif; ?>>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>活动类型：</label>
            <div class="formControls col-4 skin-minimal">
                <div class="radio-box">
                    <input name="activity_type" type="radio" value="1" class="activity_type" checked="checked"
                           onClick="show(this)" <?php if ($this->_tpl_vars['activity_type'] == 1): ?>checked="checked"<?php endif; ?>/>
                    <label for="sex-1">注册</label>
                </div>
                <div class="radio-box">
                    <input name="activity_type" type="radio" value="2" class="activity_type" onClick="show(this)"
                           <?php if ($this->_tpl_vars['activity_type'] == 2): ?>checked="checked"<?php endif; ?>/>
                    <label for="sex-2">节日/活动</label>
                </div>

            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl" style="display:<?php if ($this->_tpl_vars['activity_type'] != 2): ?>none<?php endif; ?>;" id="product_class_id"
             onchange="change()">
            <label class="form-label col-4">活动指定商品类型：</label>
            <select name="product_class_id" class="select1" style="width: 80px;height: 31px;vertical-align: middle;">
                <?php echo $this->_tpl_vars['list']; ?>

            </select>
            全部代表通用
        </div>
        <div class="row cl" style="display:<?php if ($this->_tpl_vars['product_class_id'] == 0): ?>none<?php endif; ?>;" id="product_id">
            <label class="form-label col-4">活动指定商品：</label>
            <select name="product_id" class="select2" style="width: 80px;height: 31px;vertical-align: middle;">
                <?php echo $this->_tpl_vars['list1']; ?>

            </select>
            全部代表该分类下面商品通用
        </div>

        <div class="row cl" id="txt">
            <label class="form-label col-4" id="txt_1" style="display:<?php if ($this->_tpl_vars['activity_type'] == 3): ?>none<?php endif; ?>;"><span
                        class="c-red">*</span>金额：</label>
            <label class="form-label col-4" id="txt_2" style="display:<?php if ($this->_tpl_vars['activity_type'] != 3): ?>none<?php endif; ?>;"><span
                        class="c-red">*</span>减：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" placeholder="" id="money" value="<?php echo $this->_tpl_vars['money']; ?>
" name="money">
            </div>
        </div>

        <div class="row cl" id="z_money" style="display:<?php if ($this->_tpl_vars['activity_type'] != 3): ?>none<?php endif; ?>;">
            <label class="form-label col-4"><span class="c-red">*</span>金额满：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" placeholder="" id="z_money1" value="<?php echo $this->_tpl_vars['z_money']; ?>
" name="z_money">
            </div>
        </div>

        <div class="row cl" id="num" style="display:<?php if ($this->_tpl_vars['activity_type'] == 1): ?>none<?php endif; ?>;">
            <label class="form-label col-4">数量：</label>
            <div class="formControls col-2">
                <input type="number" class="input-text" placeholder="" value="<?php echo $this->_tpl_vars['num']; ?>
" name="num" id="num1">
            </div>
            <text style="line-height:30px;">0表示没限制数量</text>
        </div>

        <div class="row cl" id="time" style="display:<?php if ($this->_tpl_vars['activity_type'] == 1): ?>none<?php endif; ?>;">
            <label class="form-label col-4"><span class="c-red">*</span>活动时间：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['start_time']; ?>
" autocomplete="off" placeholder=""
                       id="group_start_time" name="starttime" style="width:150px;">
                -
                <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['end_time']; ?>
" autocomplete="off" placeholder=""
                       id="group_end_time" name="group_end_time" style="width:150px;">
            </div>
            <div class="col-3">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"></label>
            <div class="formControls col-2">
                <button class="btn btn-primary radius" type="submit" name="Submit">提 交</button>
                <button class="btn btn-secondary radius" type="reset" name="reset">重 写</button>
            </div>
        </div>
    </form>
</div>


<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>


<?php echo '
    <script>
        if ($(".status").val() == 1) {
            document.getElementById(\'name\').readOnly = true;
            document.getElementById(\'money\').readOnly = true;
            document.getElementById(\'z_money1\').readOnly = true;
            document.getElementById(\'num1\').readOnly = true;
            // document.getElementById(\'activity_type\').disabled = \'true\';
            $(\'.activity_type\').attr(\'disabled\', \'disabled\');
            $(\'.select1\').attr(\'disabled\', \'disabled\');
            $(\'.select2\').attr(\'disabled\', \'disabled\');

        }

        var group_end_time = $(\'#group_end_time\').val();
        var startdate = $("#group_start_time").val();
        laydate.render({
            elem: \'#group_start_time\', //指定元素
            trigger: \'click\',
            type: \'datetime\',

        });

        laydate.render({
            elem: \'#group_end_time\',
            trigger: \'click\',
            type: \'datetime\'
        });


    </script>
'; ?>

</body>
</html>