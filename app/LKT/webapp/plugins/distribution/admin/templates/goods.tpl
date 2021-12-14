<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>分销管理</title>
</head>
<body>


<nav class="breadcrumb">
    <a href="index.php?module=pi&p=distribution&c=Home" >分销管理</a> <span class="c-gray en">&gt;</span>
    分销商品
</nav>

<div class="page-container pd-20 page_absolute">

    <div style="display: flex;flex-direction: row;font-size: 16px;" class="page_bgcolor">
        <div class="status qh "  ><a href="index.php?module=pi&p=distribution&c=Home">佣金明细</a></div>
        <div class="status qh isclick"><a href="index.php?module=pi&p=distribution&c=goods&status=2">分销商品</a></div>
        <div class="status qh "><a href="index.php?module=pi&p=distribution&c=relationship&status=1">客户关系</a></div>
    </div>


	<div class="page_h16"></div>
	<div class="text-c" >
        <form name="form1" action="index.php" method="get" style="display: flex;">
            <input type="hidden" name="module" value="pi"/>
            <input type="hidden" name="p" value="distribution"/>
            <input type="hidden" name="c" value="goods"/>
            <input type="text" name="username" size='8' value="{$name}" id="" placeholder="请输入商品名称" style="width:220px;height:36px;background:rgba(255,255,255,1);border:1px solid rgba(213,219,232,1);border-radius:2px;" class="input-text">
            <input name="" id="" class="btn btn-success buttom_hover" style="width:60px;height:36px;background:rgba(40,144,255,1);border-radius:2px;" type="submit" value="查询">
        </form>
    </div>

    <div class="page_bgcolor page_t">
      <div class="page_h16"></div>

      <a class="btn newBtn radius" style="width: 112px;height: 36px !important;background: rgba(56,180,237,1);" href="index.php?module=pi&p=distribution&c=addGoods">
        <div style="height: 100%;display: flex;align-items: center;">
          <img src="images/icon1/add.png" />&nbsp;添加商品
        </div>
      </a>

      <a class="btn newBtn radius" style="width: 112px;height: 36px !important;background: rgba(255,255,255,1) !important;" onclick="alldel()">
        <div style="height: 100%;display: flex;align-items: center;font-size: 14px;font-weight: 400;color: rgba(106,112,118,1);">
          <img src="images/icon1/del.png" />&nbsp;批量删除
        </div>
      </a>
    </div>
    <div class="page_h16"></div>
	<div class="mt-20">
		<table class="table-border tab_content tabls">

                <thead>
                <tr class="text-c tab_tr">
                    <th style="width:30px;">
                        <input type="checkbox" id="checkbox-1" style="visibility: initial;" >
                    </th>
                    <th style="width:50px;">
                    序号
                    </th>
                    <th style="width:300px;">商品名称</th>
                    <th style="width:50px;">价格</th>
                    <th style="width:50px;">库存</th>
                    <th style="width:50px;">佣金比例</th>
                    <th style="width:100px;">状态</th>
                    <th style="width:100px;">是否显示</th>
                    <th style="width: 150px;">操作</th>
                </tr>
                </thead>
                <tbody>
                  {foreach from=$list item=item}
                    
                    <tr class="tab_td">
                        <td style="width:30px;"><input type="checkbox" class="che" style="visibility: initial;" data-id="{ $item->id }"></td>

                        <td style="width:50px;">{$item->id}</td>
                        <td style="width:300px;text-align: start;">
                            <div style="width:300px;">
                                {if $item->imgurl == ''}<span>暂无图片</span>{else}<img onclick="pimg(this)" style="width: 50px;height: 50px;" src="{$item->imgurl}">{/if}
                                {$item->product_title}
                            </div>

                        </td>
                    
                        <td style="width:50px;">               
                            ￥ {$item->price}
                        </td>

                        <td style="width:50px;">
                                {$item->num}
                        </td>

                        <td class="text-c" style="width:50px;">
                            {if $item->leve >= 1}1级：{$item->leve1}%<br/>{/if} 
                            {if $item->leve >= 2}2级：{$item->leve2}%<br/>{/if} 
                            {if $item->leve >= 3}3级：{$item->leve3}%{/if}
                        </td>

                        <td style="width:50px;">
                            {if $item->status == 0}
                                <span class="statusssah">
                                    待上架
                                </span>
                            {/if}
                            
                            {if $item->status == 1}
                                <span class="statusssa">已上架</span>
                                
                            {/if}

                            {if $item->status == 2}
                                <span class="statusssah">已下架</span>
                            {/if}
                        </td>

                        <td style="width:50px;">
                            {if $item->is_show == "0"}
                                不显示

                            {else}
                                显示  
                            {/if}
                        </td>

                        <td class="tab_editor" style="width:150px;">
                            <a style="text-decoration:none" class="ml-5" href="index.php?module=pi&p=distribution&c=modify&id={$item->id}" onclick="return confirm('确定要修改该分销商品吗?')">
                                <div style="margin:0 auto;"> 
                                    <img src="images/icon1/xg.png" />&nbsp;编辑
                                </div>
                            </a>

                            {if $item->status == 1}
                                <a style="text-decoration:none" class="ml-5" onclick="updataStatus({ $item->id },2)">
                                    <div style="margin:0 auto;"> 
                                        <img src="images/icon1/xj.png" />&nbsp;下架
                                    </div>
                                </a>
                            {else}
                                {if $item->sta == 0}
                                    <a style="text-decoration:none" class="ml-5" onclick="updataStatus({ $item->id },1)">
                                        <div style="margin:0 auto;"> 
                                            <img src="images/icon1/sj_g.png" />&nbsp;上架
                                        </div>
                                    </a>
                                {else}
                                    <a style="text-decoration:none;background-color: rgb(213, 219, 232);" class="ml-5">
                                        <div style="margin:0 auto;"> 
                                            <img src="images/icon1/sj_g.png" />&nbsp;上架
                                        </div>
                                    </a>
                                {/if}
                            {/if}




                            <a style="text-decoration:none" class="ml-5" href="index.php?module=pi&p=distribution&c=goods&m=del&id={$item->id}&type=1" onclick="return confirm('确定要删除该分销商品吗?')">
                                <div style="margin:0 auto;"> 
                                    <img src="images/icon1/del.png"/>&nbsp;删除
                                </div>
                            </a>
                        </td>

                    </tr>
                {/foreach}
                </tbody>
           
          
		</table>

	</div>
	<div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
	<div class="page_h20"></div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">
    var group_end_time = $('#group_end_time');

    var startdate = $("#group_start_time").val();

    laydate.render({
        elem: '#group_start_time', //指定元素
        trigger: 'click',
        type: 'datetime',

    });
    
    laydate.render({
        elem: '#group_end_time',
        trigger: 'click',
        type: 'datetime'
    });

    function empty() {
        $("input[name='username']").val('');
        $("input[name='sNo']").val('');
        $("input[name='starttime']").val('');
        $("input[name='group_end_time']").val('');
    }

    function updataStatus(id,status){
        $.ajax({
            url: "index.php?module=pi&p=distribution&c=goods&m=status",
            async: false,
            data:{
                id:id,
                status:status
            },
            success: function (res) {
                res = JSON.parse(res)
                if(res.code === 200){
                    if(status == 2){
                        layer.msg('下架成功！')
                    } else {
                        layer.msg('上架成功！')
                    }
                    
                    setTimeout(function(){
                        location.reload()
                    },600)
                } else {
                    setTimeout(function(){
                        location.reload()
                    },600)
                }
            }
        })
    }

    $(function(){
        $('#checkbox-1').click(function(){
            if(this.checked){
                $('.che').prop("checked", true)
            } else {
                $('.che').prop("checked", false)
            }
        })
    })

    function alldel(){
        let list = Array.from($(".che[type=checkbox]:checked"),item => item.dataset.id)
        
        if(!list.length){
            layer.msg('请选择需删除的分销商品！')
            return 
        }

        layer.confirm('确定要删除该所选分销商品吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url: "index.php?module=pi&p=distribution&c=goods&m=del",
                async: false,
                method:'POST',
                data:{
                    id:list.join(',')
                },
                success: function (res) {
                    res = JSON.parse(res)
                    if(res.code === 200){
                        layer.msg('删除成功！')
                        setTimeout(function(){
                            location.reload()
                        },600)
                    } else {
                        layer.msg('删除成功！')
                        setTimeout(function(){
                            location.reload()
                        },600)
                    }
                }
            })

        }, function(){

        })


    }
    </script>
{/literal}
</body>
</html>