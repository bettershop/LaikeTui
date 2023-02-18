<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css"/>
    <link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css"/>
    <link href="style/css/style.css" rel="stylesheet" type="text/css"/>
    <!--<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css"/>-->

    <title>活动列表</title>
    {literal}
        <style type="text/css">
            .btn1 {
                padding: 0px 10px;
                height: 40px;
                line-height: 40px;
                display: flex;
                justify-content: center;
                align-items: center;
                float: left;
                color: #6a7076;
                background-color: #fff;
            }

            .active1 {
                color: #fff;
                background-color: #62b3ff;
            }


            .swivch a:hover {
                text-decoration: none;
                background-color: #2481e5!important;;
                color: #fff;
            }

            td a {
                width: 28%;
                float: left;
                margin: 2% !important;
            }

            .btn_sty{
                height: 30px;
                padding: 18px 30px;
                background: #e6e6e6;
                font-weight: bold;
                color: #1d1d1d;
                font-size: 14px;
            }
            .sel_btn{
                background: #fff;
                border-bottom: none;
                color: #30b5f7;
            }
        </style>
    {/literal}
</head>
<body>
<div class="pd-20 page_absolute">
    <div class="page_h16"></div>
    <div class="mt-20">
        <input type="hidden" name="goods_id" value="{$goods_id}"/>
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th><input type="checkbox" class="inputC input_agreement_protocol" id="ipt1" name="ipt1" value="">
                    <label for="ipt1"></label></th>
                <th>商品名称</th>
                <th>会员名称</th>
                <th>零售价</th>
                <th>参团价格</th>
                <th>参团时间</th>
            </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td><input type="checkbox" class="inputC input_agreement_protocol" id="{$item->goods_id}"
                               name="id[]" value="{$item->goods_id}"><label for="{$item->goods_id}"></label></td>
                    <td>{$item->p_name}</td>
                    <td>{$item->user_name}</td>
                    <td>{$item->price} 元</td>
                    <td>{$item->can_price} 元</td>
                    <td>{$item->add_time}</td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="style/js/jquery.js"></script>

<script type="text/javascript" src="style/js/jquery.min.js"></script>

</body>
</html>