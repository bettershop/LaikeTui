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
    佣金明细
</nav>

<div class="page-container pd-20 page_absolute">

    <div style="display: flex;flex-direction: row;font-size: 16px;" class="page_bgcolor">
        <div class="status qh isclick"  ><a href="index.php?module=pi&p=distribution&c=Home">佣金明细</a></div>
        <div class="status qh "><a href="index.php?module=pi&p=distribution&c=goods&status=2">分销商品</a></div>
        <div class="status qh "><a href="index.php?module=pi&p=distribution&c=relationship&status=1">客户关系</a></div>
    </div>


	<div class="page_h16"></div>
	<div class="text-c" >
        <form name="form1" action="index.php" method="get" style="display: flex;">
            <input type="hidden" name="module" value="pi"/>
            <input type="hidden" name="p" value="distribution"/>
            <input type="hidden" name="c" value="Home"/>
            <input type="text" name="username" size='8' value="{$username}" id="" placeholder="请输入用户ID" style="width:200px" class="input-text">
            <input type="text" name="sNo" size='8' value="{$sNo}" id="" placeholder="请输入订单编号" style="width:200px" class="input-text">
            <div style="">
                <input type="text" class="input-text" value="{$starttime}" autocomplete="off" placeholder="请输入开始时间" id="group_start_time" name="starttime" style="width:150px;">
                至
                <input type="text" class="input-text" value="{$endtime}" autocomplete="off" placeholder="请输入结束时间" id="group_end_time" name="group_end_time" style="width:150px;">
            </div>
            <input type="button" value="重置" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076; height: 31px;" class="reset" onclick="empty()" />

            <input name="" id="" class="btn btn-success buttom_hover" type="submit" value="查询">
        </form>
    </div>

    <div class="page_h16"></div>
	<div class="mt-20">
		<table class="table-border tab_content">

                <thead>
                <tr class="text-c tab_tr">
                
                    <th>
                    序号
                    </th>
                    <th style="width:100px;">用户ID</th>
                    <th style="width:5%;">应付佣金</th>
                    <th style="width:5%;">实付佣金</th>
                    <th>订单编号</th>
                    <th style="width:100px;">来自用户ID</th>
                    <th>时间</th>
                    <th >状态</th>

                    <th style="width: 150px;">操作</th>
                </tr>
                </thead>
                <tbody>
                  {foreach from=$list item=item}
                        <tr class="text-c tab_td">
                        <td >
                             {$item->id}
                        </td>
                        <td>
                                {$item->userid}
                        </td>
                         <td>
                                ￥{$item->money}
                            </td><td>
                                ￥{$item->s_money}
                            </td>
                            <td>{$item->sNo}</td>
                            <td>{$item->Referee}</td>
                            <td style="text-align: center;">
                                {$item->addtime}
                            </td>
                            <td>{if $item->status==2} 已发放 {else}未发放{/if}</td>

                        <td class="tab_editor">
                            <a style="text-decoration:none" class="ml-5" href="index.php?module=pi&p=distribution&c=Home&m=del&id={$item->id}" onclick="return confirm('确定要删除该记录吗?')">
                              <div style="margin:0 auto;;display: flex;align-items: center;"> 
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
    </script>
{/literal}
</body>
</html>