<?php /* Smarty version 2.6.26, created on 2021-03-19 11:06:07
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

<title>管理员列表</title>
<?php echo '
<style>
   	td a{
        width: 44%;
        margin: 2%!important;
        float: left;
    }
</style>
'; ?>

</head>
<body>

<nav class="breadcrumb">
    配置管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=role">角色列表</a>
</nav>


<div class="pd-20">
    <div style="clear:both;">
        <button class="btn newBtn radius" value="添加角色" onclick="location.href='index.php?module=role&action=add';" >
        	<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
                <img src="images/icon1/add.png"/>&nbsp;添加角色
           		</div>
        </button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    </th>
                    <th width="30">序</th>
                    <th>角色</th>
                    <th>权限</th>
                    <th>添加时间</th>
                    <th style="width:140px">操作</th>
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
                    <td width="100px"><?php echo $this->_tpl_vars['item']->name; ?>
</td>
                    <td class="text-l"><?php echo $this->_tpl_vars['item']->permission; ?>
</td>
                    <td><?php echo $this->_tpl_vars['item']->add_date; ?>
</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=role&action=modify&id=<?php echo $this->_tpl_vars['item']->id; ?>
" title="修改" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                	<img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
            				</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm('确定要删除这个角色吗?',<?php echo $this->_tpl_vars['item']->id; ?>
)">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/del.png"/>&nbsp;删除
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

<script type="text/javascript" src="style/js/jquery.js"></script>

<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>

<?php echo '
<script type="text/javascript">
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
};
function closeMask(id){
	$.ajax({
    	type:"get",
    	url:"index.php?module=role&action=del&id="+id,
    	async:true,
    	success:function(res){
    		console.log(res)
    		if(res==1){
    			appendMask("删除成功","cg");
    		}
    		else{
    			appendMask("删除失败","ts");
    		}
    	}
    	
   });
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
				<div class="maskTitle">提示</div>	
				<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
				<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
					${content}
				</div>
				<div style="text-align:center;margin-top:30px">
					<button class="closeMask" style="margin-right:20px" onclick=closeMask("${id}") >确认</button>
					<button class="closeMask" onclick=closeMask1() >取消</button>
				</div>
			</div>
		</div>	
	`)
};
</script>
'; ?>

</body>
</html>