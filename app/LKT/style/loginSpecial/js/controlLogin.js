// \lkj20180323
var canGetCookie = 1;//是否支持存储Cookie 0 不支持 1 支持
var ajaxmockjax = 1;//是否启用虚拟Ajax的请求响 0 不启用  1 启用
//默认账号密码
var url = "index.php?module=Login";
var web_url = "https://open.laiketui.com/a15a744a5ca77d41baa9d4f272f45dfd/LKT/index.php?module=Login";
var web_name = 'https://open.laiketui.com/';
var authorization_code = '';
var truelogin = "";
var truepwd = "";
var laike_num = false;
var CodeVal = 0;
function showCheck(a) {
    CodeVal = a;
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, 1000, 1000);
    ctx.font = "80px 'Hiragino Sans GB'";
    ctx.fillStyle = "#E8DFE8";
    ctx.fillText(a, 0, 100);
}
$(document).keypress(function (e) {
    // 回车键事件
    if (e.which == 13) {
        $('input[type="button"]').click();
    }
});
//粒子背景特效
// $('body').particleground({
//     dotColor: '#E8DFE8',
//     lineColor: '#1b3273'
// });
$('input[name="pwd"]').focus(function () {
    $(this).attr('type', 'password');
});
$('input[type="text"]').focus(function () {
    $(this).prev().animate({ 'opacity': '1' }, 200);
});
$('input[type="text"],input[type="password"]').blur(function () {
    $(this).prev().animate({ 'opacity': '.5' }, 200);
});
$('input[name="login"],input[name="pwd"]').keyup(function () {
    var Len = $(this).val().length;
    if (!$(this).val() == '' && Len >= 5) {
        $(this).next().animate({
            'opacity': '1',
            'right': '30'
        }, 200);
    } else {
        $(this).next().animate({
            'opacity': '0',
            'right': '20'
        }, 200);
    }
});

var open = 0;
layui.use('layer', function () {
    $(".num").focusout(function() {
          var verify_num = $(this).val();
          if(verify_num == '' || verify_num.length < 1){
            ErroAlert('请填写您的客户编11111号！');
            return false;
          }else{
            $.ajax({
               type: "POST",
               url: web_url,
               data: "m=verify_num&user_num="+verify_num,
               success: function(msg){
                    obj = JSON.parse(msg);
                    if (obj.status == 1) {
                         console.log(obj.succ);
                        authorization_code = obj.succ;
                        laike_num =true;
                    }else{
                        // ErroAlert('您的使用权限已到期或编号有误,请重新输入！');
                        laike_num =false;
                        return false;
                    }
               }
            });
          }
    });
    //非空验证
    $('input[type="button"]').click(function () {
        var login = $('.username').val();
        var pwd = $('.passwordNumder').val();
        var verify_num = $(".num").val();
        if (login == '') {
            ErroAlert('请输入您的账号');
            return false;
        } else if (pwd == '') {
            ErroAlert('请输入密码');
            return false;
        } else if (laike_num == false) {
            ErroAlert('请输入正确客户编号');
            return false;
        }else {
            //认证中..
            $('.login').addClass('test'); //倾斜特效
            setTimeout(function () {
                $('.login').addClass('testtwo'); //平移特效
            }, 300);
            setTimeout(function () {
                $('.authent').show().animate({ right: -320 }, {
                    easing: 'easeOutQuint',
                    duration: 600,
                    queue: false
                });
                $('.authent').animate({ opacity: 1 }, {
                    duration: 200,
                    queue: false
                }).addClass('visible');
            }, 500);

            //登陆
            var JsonData = { login: login, pwd: pwd,verify_num:verify_num};
            //此处做为ajax内部判断

            setTimeout(function () {
                        $('.authent').show().animate({ right: 90 }, {
                            easing: 'easeOutQuint',
                            duration: 600,
                            queue: false
                        });
                        $('.authent').animate({ opacity: 0 }, {
                            duration: 200,
                            queue: false
                        }).addClass('visible');
                        $('.login').removeClass('testtwo'); //平移特效
             }, 2000);
            $.ajax({
               type: "POST",
               url: web_name+authorization_code+'/LKT/'+url,
               data: JsonData,
               dataType: "json",
               success: function(msg){
                     setTimeout(function () {
                        $('.authent').hide();
                        $('.login').removeClass('test');
                        if (msg) {
                            //登录成功
                            $('.login div').fadeOut(100);
                            $('.success').fadeIn(1000);
                            window.location.href=web_name+authorization_code+'/LKT/'+"index.php?module=AdminLogin";
                            // //跳转操作
                        } else {
                            ErroAlert('账号或密码错误');
                        }
                    }, 2400);
               }
            });


        }
        return false;
    })
})
