<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    {php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
    <title>活动列表</title>
    {literal}
        <style>
            td a{
                width: 90%;
                margin: 2%!important;
            }
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
    {/literal}
</head>
<body>

<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span>
    签到 <span class="c-gray en">&gt;</span>
    活动列表
</nav>


<div class="pd-20">
    <div class="swivch">

        <a href="index.php?module=pi&p=sign" class="btn1" style="background-color: #62b3ff;color: #fff;">活动列表</a>
        <a href="index.php?module=pi&p=sign&c=record" class="btn1">签到记录</a>
        <a href="index.php?module=pi&p=sign&c=config" class="btn1">签到设置</a>

        <div class="clearfix" style="margin-top: 0px;"></div>
    </div>
    <div class="mt-20" style="clear:both;">
        <a class="btn newBtn radius" href="index.php?module=pi&p=sign&c=add">
            发布活动
        </a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
            <tr class="text-c">
                <th>序</th>
                <th>活动图片</th>
                <th>添加时间</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td><image src="{$uploadImg}{$item->image}" style="width: 100px;"/></td>
                    <td>{$item->add_time}</td>
                    <td>{$item->starttime}</td>
                    <td>{$item->endtime}</td>
                    <td>{if $item->status == 0}<span>未启用</span>{elseif $item->status == 1}<span style="color:#30c02d;">启用</span>{else}<span>已结束</span>{/if}</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=pi&p=sign&c=modify&id={$item->id}&uploadImg={$uploadImg}" title="修改">
                            <div style="align-items: center;font-size: 12px;display: flex;">
                                <div style="margin:0 auto;;display: flex;align-items: center;">
                                    <img src="images/icon1/xg.png"/>&nbsp;修改
                                </div>
                            </div>
                        </a>

                        <a style="text-decoration:none" class="ml-5" href="index.php?module=pi&p=sign&c=del&id={$item->id}&uploadImg={$uploadImg}" onclick="return confirm('确定要删除此活动吗?')">
                            <div style="align-items: center;font-size: 12px;display: flex;">
                                <div style="margin:0 auto;;display: flex;align-items: center;">
                                    <img src="images/icon1/del.png"/>&nbsp;删除
                                </div>
                            </div>
                        </a>

                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
    <script type="text/javascript">
        $('.table-sort').dataTable({
            "aaSorting": [[ 1, "desc" ]],//默认第几个排序
            "bStateSave": true,//状态保存
            "aoColumnDefs": [
                //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
                {"orderable":false,"aTargets":[0,6]}// 制定列不参与排序
            ]
        });
    </script>
{/literal}
</body>
</html>