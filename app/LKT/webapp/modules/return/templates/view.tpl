
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="style/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<link href="style/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />

<title>退货详情</title>
{literal}
<style type="text/css">
	table th{
		border: none;
		font-weight: normal!important;
		color: #888f9e;
		font-size: 14px;
	}
	.table th{
		padding: 15px 20px;
	}
	table{
		background-color: #fff;
		border-bottom-left-radius: 10px;
    	border-bottom-right-radius: 10px;
	}
	.ulTitle {
    height: 50px;
    line-height: 50px;
    text-align: left;
    padding-left: 20px;
    font-size: 16px;
    color: #414658;
    font-weight: 600;
    font-family: "微软雅黑";
    margin-bottom: 0px;
    margin-top: 20px;
    border-bottom: 2px solid #eee;
    background: #fff;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 退货列表 <span class="c-gray en">&gt;</span> 退货详情 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=return';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <div class="Huiform">
    	<div class="ulTitle">
    		退货详情
    	</div>
        <table class="table table-bg" >
            <tbody>
                <tr>
                    <th width="100" class="text-r"> 用户名：</th>
                    <td>
                        <span>{$list[0]->user_id}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 产品id：</th>
                    <td>
                        <span>{$list[0]->p_id}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 产品名称：</th>
                    <td>
                        <span>{$list[0]->p_name}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 产品价格：</th>
                    <td>
                        <span>{$list[0]->p_price}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 数量：</th>
                    <td>
                        <span>{$list[0]->num}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 单位：</th>
                    <td>
                        <span>{$list[0]->unit}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 订单号：</th>
                    <td>
                        <span>{$list[0]->r_sNo}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 添加时间：</th>
                    <td>
                        <span>{$list[0]->add_time}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 发货时间：</th>
                    <td>
                        <span>{$list[0]->deliver_time}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 到货时间：</th>
                    <td>
                        <span>{$list[0]->arrive_time}</span>
                    </td>
                </tr>
                <tr>
                    <th class="text-r"> 退货原因：</th>
                    <td>
                        <span>{$list[0]->content}</span>
                    </td>
                </tr>
            </tbody>
        </table>
  </div>
</div>

</body>
</html>