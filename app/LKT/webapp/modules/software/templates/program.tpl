

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

<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="style/css/layout.css">

<title>添加软件</title>

</head>

<body>

<nav class="breadcrumb"><i class="Hui-iconfont">&#xe654;</i> 软件管理 <span class="c-gray en">&gt;</span> 小程序 <span class="c-gray en">&gt;</span> 选择小程序 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=software';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>

<div class="pd-20">





<div class="n-solution">

            <div class="n-title text-center containers">

                <h2>请选择小程序模板</h2>

                <span class="tit_s">注：下载后请使用微信开发者工具修改配置后上传即可</span>

            </div>

            <div class="step containers"></div>

            <div class="slide-bp-wrap" id="slidebp1">

                <div class="slide-bp">

                    <ul style="width: 3000px; left: -1500px;">

                        {foreach from=$list item=item name=f1}

                        <li style="margin-right: 20px;">

                            <img src="{$uploadImg}{$item->image}" alt="{$item->name}">

                            <a href="javascript:void(0)" onclick="select_this('{$down_file}/{$item->edition_url}',{$item->id})" class="child">

                                <p class="tit">{$item->name}</p>

                                <!-- <p class="tit_s">更全面的品牌展示，更好的形象宣传</p> -->

                                <div class="more">立即使用</div>

                            </a>

                        </li>

                        {/foreach}

                        {foreach from=$list item=item name=f1}

                        <li style="margin-right: 20px;">

                            <img src="{$uploadImg}{$item->image}" alt="{$item->name}">

                            <a href="javascript:void(0)" onclick="select_this('{$down_file}/{$item->edition_url}',{$item->id})" class="child">

                                <p class="tit">{$item->name}</p>

                                <!-- <p class="tit_s">更全面的品牌展示，更好的形象宣传</p> -->

                                <div class="more">立即使用</div>

                            </a>

                        </li>

                        {/foreach}

                    </ul>

                </div>

                <div class="slide-bp-prv"><img src="images/left_arrow_c.png"></div>

                <div class="slide-bp-next"><img src="images/right_arrow_c.png"></div>

            </div>

        </div>





</div>





<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 

{literal}

<script type="text/javascript">

function select_this(obj,id) {

  console.log(id);

  var flag = confirm("确定要使用该模板吗？");

  if(flag){

    window.open(obj);

  }else{

    alert("你单击的是取消"); 

  }



}

$(function() {

  //  回到顶部

  $(".totop").click(function() {

      $('body,html').animate({

        scrollTop: 0

      }, 200);

    })

});



$(function(){

  //批量切换

  var page = 0;

  var pageTotal = Math.ceil($('.office-inner span').length/4);

  $('.next').click(function(){

    if( page >= pageTotal-1 )return;

    page++;

    move();

  });

  $('.pre').click(function(){

    if( page <= 0 )return;

    page--;

    move();

  });

  function move(){

    $('.office-inner').stop().animate({

      left: -(page * $('.office-wrapper').width() + 12)

    }, 500);

  }



  //大图查看

  var index = 0;

  $('.office-inner span').click(function(){

    index = $(this).attr('index');

    $('.lightbox-inner img').attr('src', ($('.office-inner span').eq(index).find('img').attr('src')));

    $('.office-lightbox').fadeIn();

  });

  $('.office-lightbox .left').click(function(){

    if( index <= 0 )return;

    index--;

    changeSrc();

  });

  $('.office-lightbox .right').click(function(){

    if( index >= $('.office-inner span').length-1 )return;

    index++;

    changeSrc();

  });

  function changeSrc(){

    $('.lightbox-inner img').attr('src', ($('.office-inner span').eq(index).find('img').attr('src')));

  }

  $('.office-lightbox').click(function(e){

    if($(e.target).hasClass('office-lightbox')){

      $('.office-lightbox').fadeOut();

    };

  });

  $('.office-lightbox .close').click(function(){

    $('.office-lightbox').fadeOut();

  });



  footFix();

});



//分享

function _jiathis() {

  var wWidth = $(window).width();

  var wHeight = $(window).height();

  if (wWidth < 1160) {

    $('.jiashare').hide();

  } else {

    $('.jiashare').css('left', Math.floor((wWidth - 1000)/2) - 120 + 'px').show();

  }

}



//底部菜单

function footFix(){

  if( $('.main-wrapper').outerHeight() < $(window).height() - $('.nav-wrapper').height() - $('.foot-wrapper').outerHeight() - 60 ){

    $('.foot-wrapper').css({

      'width': '100%',

      // 'position': 'fixed',

      // 'bottom': 0,

      // 'left': 0,

      // 'z-index': 100

      marginTop: 210

    });

  }else{

    $('.foot-wrapper').removeAttr('style');

  }

}



//处理图片的宽高

function setImgWH(obj) {

  if(obj.width > obj.height) {

    $(obj).css('height', '100%');

  } else {

    $(obj).css('width', '100%');

  }

}



$(function() {

  //弹窗

  var H_ture = $(".switchbox .content").height();

  var flag = true;

  if(H_ture > 36) {

    $(".switchbox .content").css("height", "36px")

    $(".slideBtn a").click(function() {

      if(flag) {

        $(".switchbox .content").animate({

          "height": H_ture

        })

        $(this).addClass("active")

      } else {

        $(".switchbox .content").animate({

          "height": "36px"

        })

        $(this).removeClass("active")

      }

      flag = !flag;

    })

  }

  //      点击标签的效果

  $(".switchbox .content a").click(function() {

    $(".switchbox .content a").removeClass("active")

    $(this).addClass("active")

  })

});

/*------------↓-------------2017-05-02 以后------------↓-------------*/





$(function(){

  // 导航栏 选中滑动

  $('.nav-menu .nav-item.active').each(function(){

    setLineLeft($(this));

  })

  $('.nav-menu .nav-item').hover(function(){

    $(this).siblings('.line').show();

    setLineLeft($(this))

  })

  $('.nav-menu').mouseleave(function(){

    setLineLeft($(this).find('.nav-item.active'))

    setTimeout(function(){

      $(this).siblings('.line').hide();

    },300)

  })

  function setLineLeft(d1){

    var formLeft =d1.position().left,

      w = d1.outerWidth();

    var $line = d1.siblings('.line');

    var dis = formLeft+(w/2)-$line.width()/2;

    $line.css('left',dis)

  }

  $('.nav-menu.b_n .nav-item').click(function(){

    $('.nav-menu.b_n .nav-item').removeClass('active');

    $(this).addClass('active');



    $('.show-content').css('display','none')

    $('.show-content').eq($(this).index()).css('display','block')

  })

});



/**

 * 轮播

 * @param  {[type]} $ [description]

 * @return {[type]}   [description]

 */

(function($){

  $.fn.nswiper = function(options){

    return this.each(function(){

      var self = $(this),

        $ul = self.children('.swiper'),

        $li = $ul.children('li'),

        $prv = self.children('.prv'),

        $next = self.children('.next'),

        $dot = self.children('.dot'),

          icurr = 0,

          size = $li.length,

          timerId = null,//循环ID

          dotHtml = '<span class="active"></span>',

          slideSwitch = true;



       $li.eq(0).css('display','block');

       for(var i=0;i<size-1;i++){

        dotHtml+='<span></span>'

       }

       $dot.append(dotHtml)

       

       function prv(){

        icurr--

        if(icurr==-1){

          icurr = size-1;

          $li.eq(0).fadeOut();

        }

        slideSwitch = false;

        $li.eq(icurr+1).fadeOut();

        $li.eq(icurr).fadeIn(500,function(){

          slideSwitch = true;

        });

        cDot(icurr)

       }

       function next(){

        icurr++

        if(icurr==size){

          icurr=0;

          $li.eq(size-1).fadeOut();

        }

        slideSwitch = false;

        $li.eq(icurr-1).fadeOut();

        $li.eq(icurr).fadeIn(500,function(){

          slideSwitch = true;

        });

        cDot(icurr)

       }

       function cDot(idx){

        $dot.find('span').removeClass('active');

        $dot.find('span').eq(idx).addClass('active');

       }

       $prv.click(function() {

        if(slideSwitch) prv();

       });

       $next.click(function() {

        if(slideSwitch) next();

       });

       timerId = setInterval(function(){

        next()

       },2300)

       self.hover(function() {

        clearInterval(timerId)

       },function(){

        timerId = setInterval(function(){

          next()

        },2300)

       });

       $dot.find('span').mouseover(function() {

        var idx = $(this).index();

        if(idx!=icurr){

          cDot(idx);

          $li.eq(icurr).fadeOut();

          $li.eq(idx).fadeIn();

          icurr = idx;

        }

        

       });

    })            

  }

})(jQuery);



/**

 * 断点轮播

 * @spaceBetween  {number} li项之间的距离

 * @time {number}  动画速度

 */

(function($){

  $.fn.slidebp = function(options){

    return this.each(function(){

      var self = $(this),

        $slide = self.find('.slide-bp ul'),

        $slide_item = self.find('.slide-bp li'),

        $prv = self.children('.slide-bp-prv'),

        $next = self.children('.slide-bp-next'),

        iCurr = 0,

        m    //单个移动距离



      $slide.append($slide_item.clone());

      var $slide_item = self.find('.slide-bp li');

      var size = $slide_item.length;



      m = $slide_item.width()+options.spaceBetween;

      $slide.css('width',size*m)

      $slide_item.css('margin-right',options.spaceBetween);

      function movePrv(){

        iCurr--;

              if(iCurr==-1){

                  $slide.css({left:-(size/2)*m});

                  iCurr=size/2-1;

              }

              $slide.stop().animate({left:-m*iCurr},options.time);

      }

      function moveNext(){

        iCurr++;

            if(iCurr==size/2+1){

                $slide.css({left:0});

                iCurr=1;

            }

              $slide.stop().animate({left:-m*iCurr},options.time);

      }

            $prv.click(function() {

          movePrv()

            });

            $next.click(function() {

        moveNext()

            });

    })            

  }

})(jQuery);

        $(function () {

            $(".show-content").on('mouseenter', '.list .app-try', function () {

                $(this).parents(".list").find(".qrcode").stop().fadeIn();

            });

            $(".show-content").on('mouseleave', '.list .app-try', function () {

                $(this).parents(".list").find(".qrcode").hide();

            });

        });

        //处理图片的宽高

        function setImgWH(obj) {

            if (obj.width > obj.height) {

                $(obj).css('height', '100%');

            } else {

                $(obj).css('width', '100%');

            }

        }

        $(function () {

            //初始化banner

            $('#banner1').nswiper();

            //初始化断点轮播

            $('#slidebp1').slidebp({

                spaceBetween: 100,

                time: 300

            });

        });



    </script>

{/literal}

</body>

</html>