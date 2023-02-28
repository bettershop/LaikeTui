<?php /* Smarty version 2.6.26, created on 2022-03-02 14:27:01
         compiled from coupon.tpl */ ?>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

<title>优惠券列表</title>
<?php echo '
<style type="text/css">
	.btn1{
    	width: 80px;
     	height: 40px;
     	line-height: 40px;
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    float: left;
	    color: #6a7076;
	    background-color: #fff;
    }
    .btn1:hover{
    	text-decoration: none;
    }
    .swivch a:hover{
    	text-decoration: none;
    	background-color: #2890FF;
    	color: #fff!important;
    }
</style>
'; ?>

</head>
<body>

<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=coupon">列表</a> <span class="c-gray en">&gt;</span>
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="coupon" />
            <input type="hidden" name="action" value="coupon" />
            <input type="hidden" name="ok" value="1" />
            <input type="hidden" name="pagesize" value="<?php echo $this->_tpl_vars['pagesize']; ?>
" id="pagesize" />

            <input type="text" name="name" size='8' value="<?php echo $this->_tpl_vars['name']; ?>
" id="name" placeholder="用户ID" style="width:200px" class="input-text">
            <input name="" id="" class="btn btn-success" type="submit" value="查询">
            <input type="button" value="重 置" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076;" class="reset" onclick="resetButton()"  />
        </form>
        <div class="swivch">
        	<a href="index.php?module=coupon" class="btn1" style="color: #6a7076;" >活动</a>
            <a href="index.php?module=coupon&action=coupon" style="background-color: #62b3ff;color: #fff;" class="btn1">列表</a>
			<div class="clearfix"></div>
        </div>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>用户ID</th>
                    <th>活动名称</th>
                    <th>优惠券金额</th>
                    <th>领取时间</th>
                    <th>到期时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <?php $_from = $this->_tpl_vars['list']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['f1'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['f1']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['item']):
        $this->_foreach['f1']['iteration']++;
?>
                <tr class="text-c">
                    <td><?php echo $this->_foreach['f1']['iteration']; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->user_id; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->name; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->money; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->add_time; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->expiry_time; ?>
</td>
                    <td><?php if ($this->_tpl_vars['item']->status == 0): ?><span style="color:#30c02d;">正常</span><?php elseif ($this->_tpl_vars['item']->status == 1): ?><span>使用中</span><?php elseif ($this->_tpl_vars['item']->status == 2): ?><span style="color:#ff2a1f">已使用</span><?php elseif ($this->_tpl_vars['item']->status == 3): ?><span style="color: #7A7A7A;">已过期</span><?php endif; ?></td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=coupon&action=see&user_id=<?php echo $this->_tpl_vars['item']->user_id; ?>
" title="查看">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;padding: 5px;display: flex;align-items: center;"> 
                                <img src="images/icon1/ck.png"/>&nbsp;查看
                            	</div>
                    		</div>
                        </a>
                    </td>
                </tr>
                <?php endforeach; endif; unset($_from); ?>
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;"><?php echo $this->_tpl_vars['pages_show']; ?>
</div>
</div>

<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>

<?php echo '
<script type="text/javascript">
function resetButton(){
    $("#name").val("");
}
</script>
'; ?>

</body>
</html>