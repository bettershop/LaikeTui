
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link type="text/css" rel="stylesheet" href="modpub/css/base.css"/>

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<link type="text/css" rel="stylesheet" href="modpub/css/base.css"/>

<title>预约信息</title>
</head>
<body style="background-color: none;">

<div class="pd-20">
<div style="text-align:center;font-size: 20px;margin-bottom: 30px;">会员{$name}预约信息</div>
<form name="form1" method="post">
  <table class="table table-striped" border="1" style="border-color: #ddd;border: 1px solid #ddd;text-align: center;width:98%;margin: auto;">
    <thead>
        <tr>
            <th style="padding-left: 0;">用户id</th>
            <th>姓名</th>
            <th>手机</th>
            <th>地址</th>
            <th>数量</th>
            <th>内容</th>
            <th style="padding-left: 0;">时间</th>
        </tr>
    </thead>
    <tbody>
            <tr>
                <td>{$list->user_id}</td>
                <td >{$list->name}</td>
                <td>{$list->mobile}</td>
                <td>{$list->address}</td>
                <td>{$list->num}</td>
                <td>{$list->content}</td>
                <td>{$list->add_date}</td>           
            </tr>
    </tbody>
</table>
</form>

<DIV align="center" id="dayinDiv" name="dayinDiv" style="margin-top: 20px;"><input type="button" class="btn btn-success" value=" 关闭 " onclick="window.history.go(-1);" />
</DIV>
</div>
</body>
</html>