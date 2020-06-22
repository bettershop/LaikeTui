<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>欢迎登录后台管理系统</title>
<link href="style/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class='page'>
    <div class="login">
    <form name="form1" action="index.php?module=Login" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div class="page_login">
            <div class='login_title'>
                <img src="images/iIcon/logo2.png"/>
                <p style="color: #fff;font-size: 30px;">来客电商管理系统</p>
                <p style="font-size: 14px; color: #fff;">Laike E-commerce Management System</p>
            </div>

            <div class='login_fields'>


                <div class='login_fields__user'>
                    <div class='icon'>
                        <img alt="" class="hoverImg" src='images/iIcon/zh.png'>
                        <img alt="" class="hoverImg" style="display: none;" src='images/iIcon/zh_1.png'>
                    </div>

                    <input name="login" required="required"  placeholder='请输入用户名' maxlength="16" class="username ipt" type='text' autocomplete="off"/>

                    <div class='validation'></div>
                    <span class="delText">
                        <img style="display:block;" src="images/iIcon/qc.png"/>
                        <img src="images/iIcon/qc_1.png"/>
                    </span>
                </div>

                <div class='login_fields__password'>
                    <div class='icon'>
                        <img alt="" class="hoverImg" src='images/iIcon/mm.png'>
                        <img alt="" class="hoverImg" style="display: none;" src='images/iIcon/mm_1.png'>
                    </div>
                    <input name="pwd" required="required" class="passwordNumder ipt" placeholder='请输入密码' maxlength="16" type='password' autocomplete="off">
                    <div class='validation'></div>
                    <span class="delText">
                        <img style="display:block;" src="images/iIcon/qc.png"/>
                        <img src="images/iIcon/qc_1.png"/>
                    </span>
                </div>

                <div class='login_fields__submit'>
                    <input type='submit' value='登录'>
                </div>
                <div class="errText" style="display: none">
                    <div style="height: 100%;width: 100%;">
                        <i><img src="images/iIcon/ts.png" alt="" /></i>
                        <span style="color:#fff;font-weight: 400;" class="errText_box"></span>
                    </div>
                </div>
            </div>

            <div class='success login_fields__submit'></div>
        </div>
    </form>
    </div>
</div>
<footer>
<p><a href="http://www.laiketui.com/" style="color: #000" target="_blank">来客电商</a> <i> © </i> 专注用户体验的全场景商城系统 - 可免费商用</p>
<p>湖南壹拾捌号网络技术有限公司&nbsp;版权所有</p>
</footer>

</body>
</html>


