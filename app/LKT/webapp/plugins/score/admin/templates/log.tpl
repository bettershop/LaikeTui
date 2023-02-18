<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>兑换记录</title>
</head>
<body>


<nav class="breadcrumb">
    <a href="index.php?module=pi&p=score&c=Home" >积分商城</a> <span class="c-gray en">&gt;</span>
    兑换记录
</nav>

<div class="page-container pd-20 page_absolute">

    <div style="display: flex;flex-direction: row;font-size: 16px;" class="page_bgcolor">
        <div class="status qh "><a href="index.php?module=pi&p=score&c=Home">积分商品</a></div>
        <div class="status qh isclick"  ><a href="index.php?module=pi&p=score&c=log">兑换记录</a></div>
    </div>


	<div class="page_h16"></div>
	<div class="text-c" >
        <form name="form1" action="index.php" method="get" style="display: flex;">
            <input type="hidden" name="module" value="pi"/>
            <input type="hidden" name="p" value="score"/>
            <input type="hidden" name="c" value="log"/>
            <input type="text" name="username" size='8' value="{$name}" id="" placeholder="请输入用户ID" style="width:220px;height:36px;background:rgba(255,255,255,1);border:1px solid rgba(213,219,232,1);border-radius:2px;" class="input-text">
            <input name="" id="" class="btn btn-success buttom_hover" style="width:60px;height:36px;background:rgba(40,144,255,1);border-radius:2px;" type="submit" value="查询">
        </form>
    </div>

    <div class="page_bgcolor page_t">
      <div class="page_h16"></div>

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
                    <th style="width:50px;">用户ID</th>
                    <th style="width:50px;">消耗积分</th>
                    <th style="width:100px;">状态</th>
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
                                {$item->uid}
                        </td>

                        <td class="text-c" style="width:50px;">
                            {$item->score}
                        </td>

                        <td style="width:50px;">
                            {if $item->status == 0}
                                <span class="statusssah">
                                    成功
                                </span>
                            {/if}


                            {if $item->status == 1}
                                <span class="statusssah">失败</span>
                            {/if}
                        </td>



                        <td class="tab_editor" style="width:150px;">


                            <a style="text-decoration:none" class="ml-5" href="index.php?module=pi&p=score&c=log&m=del&id={$item->id}&type=1" onclick="return confirm('确定要删除该记录吗?')">
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


    function empty() {
        $("input[name='username']").val('');
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
            layer.msg('请选择需删除的记录！')
            return 
        }

        layer.confirm('确定要删除该所选记录吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url: "index.php?module=pi&p=score&c=log&m=del",
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