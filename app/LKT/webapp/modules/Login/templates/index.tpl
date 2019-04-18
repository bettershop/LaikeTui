<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<title>欢迎登录后台管理系统</title>

<link rel="icon" href="style/loginSpecial/images/favicon.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="style/loginSpecial/images/favicon.ico" type="image/x-icon"/>
<link href="style/loginSpecial/css/default.css" rel="stylesheet" type="text/css" />
<link href="style/loginSpecial/css/styles.css" rel="stylesheet" type="text/css" />
<link href="style/loginSpecial/css/demo.css" rel="stylesheet" type="text/css" />
<link href="style/loginSpecial/css/loaders.css" rel="stylesheet" type="text/css" />
<script src="style/loginSpecial/js/jquery-2.1.1.min.js"></script>
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
<p>来客电商 <i> © </i> 专注于小程序电商开发平台</p>
<p>湖南壹拾捌号网络技术有限公司&nbsp;版权所有  <a href="http://www.miibeian.gov.cn" class="ipId" style="color: #97a0b4;">湘ICP备17020355号-2</a></p>
</footer>
<div class='authent'>
    <div class="loader" style="height: 60px;width: 60px;margin-left: 28px;margin-top: 40px">
        <div class="loader-inner ball-clip-rotate-multiple">
        <div></div>
        <div></div>
        <div></div>
        </div>
    </div>
    <p>认证中...</p>
</div>
<div class="OverWindows"></div>
{literal}
<script type="text/javascript">
function jqtoast(text) {
    var errText = $(".errText");
    errText.show();
    $(".errText_box").text(text);
    setTimeout(function () {
        $('.errText').fadeOut(function () {
            errText.hide();
        });
        _this = '';
    },5000);
}


$(".ipt").each(function(){
    $(this).focus(function(){
        $(this).css("border","1px solid #fff");
        $(this).addClass("whiteP1");
        $(this).parent().find(".hoverImg").eq(1).show();
        $(this).parent().find(".hoverImg").eq(0).hide();
        $(this).keyup(function(){
            if($(this).val().length>0){
                $(this).parent().find(".delText").show();
            }
        })
    });
    $(this).blur(function(){
        $(this).removeClass("whiteP1");
        $(this).css("border","1px solid #97a0b4");
        $(this).parent().find(".hoverImg").eq(0).show();
        $(this).parent().find(".hoverImg").eq(1).hide();
    })
})
$(".delText").each(function(){
    $(this).click(function(){
        $(this).parent().find(".ipt").val("");
        $(this).hide();
    })
    $(this).mouseover(function(){
        $(this).find("img")[1].style.display="block";
        $(this).find("img")[0].style.display="none";
    })
    $(this).mouseout(function(){
        $(this).find("img")[0].style.display="block";
        $(this).find("img")[1].style.display="none";
    })
})

</script>
{/literal}
<link href="style/loginSpecial/layui/css/layui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="style/loginSpecial/js/jquery-ui.min.js"></script>
<script type="text/javascript" src='style/loginSpecial/js/stopExecutionOnTimeout.js?t=1'></script>
<script src="style/loginSpecial/layui/layui.js" type="text/javascript"></script>
<script src="style/loginSpecial/js/Treatment.js" type="text/javascript"></script>
<script src="style/loginSpecial/js/jquery.mockjax.js" type="text/javascript"></script>
<!-- <script src="style/loginSpecial/js/controlLogin.js" type="text/javascript"></script> -->
</body>
</html>


