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

<script type="text/javascript" src="modpub/js/check.js" > </script>
<script type="text/javascript" src="modpub/js/ajax.js"> </script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 

{literal}
<style type="text/css">
/*初始化样式*/
body,h1,h2,h3,h4,h5,ul,li,a,div,p,span,table,tr,td,img{margin: 0;padding: 0;}
ul{padding: 0;margin: 0;}
li{list-style: none;}
a{text-decoration: none;}
img{outline: none;border: 0;}
body{font-family: "Microsoft Yahei";}
/*清除浮动*/
.clearfix:before,
.clearfix:after{content: " ";display: table;}
.clearfix:after{clear: both;}
.clearfix{zoom: 1;}
/*浮动*/
.pull-right{float: right !important;}
.pull-left{float: left !important;}
/*----------------------------------------------------------------------------------------------------------*/
.content{float: left;}
.flex-text-wrap,pre{margin: 0 !important;}
.commentAll{width: 500px;padding: 20px;border: 1px solid #ededed;margin: 20px auto;}
.plBtn{width: 75px;height: 36px;line-height: 36px;background-color: #339b53;text-align: center;display: block;float: right;color: #FFFFFF;font-size: 12px;border-radius: 6px;margin-right: 2px;}
.plBtn:hover{background-color: #2f904d;}

/*----------评论区域 begin----------*/
.comment-show{margin-top: 20px;}
.comment-show-con {
    width: 100%;
    border-top: 1px solid #EDEDED;
    padding: 10px 0;
}
.comment-show-con-img {
    width: 48px;
    height: 48px;
    overflow: hidden;
    margin-top: 5px;
}
.comment-show-con-img img{
    width: 48px;
    height: 48px;
    overflow: hidden;
    /*margin-top: 5px;*/
}
.comment-show-con-list {
    width: 85%;
    margin-left: 3%;
}
.pl-text {
    width: 100%;
    margin-top: 7px;
    word-wrap: break-word;
    overflow: hidden;
}
.date-dz {
    width: 100%;
    float: left;
}
.hf-list-con {
    float: left;
    width: 100%;
    background-color: #eaeaec;
    margin-top: 7px;
}
.comment-size-name {
    font-size: 12px;
    color: #339b53;
}
.my-pl-con {
    font-size: 12px;
    color: #8b8b8b;
    width: 100%;
}
.date-dz-left {
    font-size: 12px;
    color: #8b8b8b;
    display: block;
    padding-top: 18px;
}
.comment-time, .comment-pl-block {
    padding-top: 7px;
}
.comment-pl-block {
    margin-top: 0;
}
.date-dz-right {
    display: block;
    padding-top: 6px;
    padding-right: 18px;
    position: relative;
    overflow: hidden;
}
.removeBlock {
    float: left;
    font-size: 12px;
    color: #8b8b8b;
    margin-right: 24px;
    display: block;
    opacity: 0;
}
.hf-con-block {
    display: block;
}
.date-dz-pl, .date-dz-line, .date-dz-z {
    font-size: 12px;
    color: #8b8b8b;
}
.date-dz-line {
    display: block;
    padding: 0 20px;
}
.date-dz-z-click-red {
    width: 17px;
    height: 17px;
    display: block;
    float: left;
    background-image: url(../images/icon-all_01.png);
    background-repeat: no-repeat;
    background-position: -6px -198px;
    margin-right: 5px;
}
.z-num {
    font-style: normal;
}
.date-dz-z-click {
    color: #b83b44;
}
.red {
    background-position: -6px -119px !important;
}
.hf-pl {
    width: 70px;
    height: 30px;
    line-height: 30px;
    background-color: #339b53;
    text-align: center;
    display: block;
    float: right;
    color: #FFFFFF;
    font-size: 12px;
    border-radius: 6px;
    margin-right: 2px;
    margin-top: 20px;
}
.hf-con{width: 100%;margin-top: 24px;}
.hf-input{font-size: 12px;}
.all-pl-con {
    width: 96%;
    padding: 2% 0;
    float: left;
    margin: 0 2%;
}
.atName {
    font-size: 12px;
    color: #339b53;
}
.hfpl-text{margin-top: 0;}
.date-dz:hover .removeBlock {
    opacity: 1;
}
.hf-list-con .all-pl-con {
    border-top: 1px solid #d9d9d9;
    padding-bottom: 12px;
}
.hf-list-con .all-pl-con:first-child {
    border-top: 0;
}

pre {
    white-space: pre;
    white-space: pre-wrap;
    word-wrap: break-word;
}
.flex-text-wrap {
    width: 100%;
    position: relative;
    *zoom: 1;
}
textarea,
.flex-text-wrap {
    outline: 0;
    margin: 0;
    border: none;
    padding: 0;
    *padding-bottom: 0!important;
}
.flex-text-wrap textarea,
.flex-text-wrap pre {
    *white-space: pre;
    *word-wrap: break-word;
    white-space: pre-wrap;
    width: 100%;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -ms-box-sizing: border-box;
    box-sizing: border-box;
}
.flex-text-wrap textarea {
    overflow: hidden;
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    resize: none;
    /* IE7 box-sizing fudge factor */
    *height: 94%;
    *width: 94%;
}
.flex-text-wrap pre {
    display: block;
    visibility: hidden;
}
.flex-text-wrap,
textarea { margin-bottom: 25px }
textarea,
.flex-text-wrap pre {
    line-height: 1.7;
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 100%;
    padding: 10px 15px;
    border: 1px solid #c6c8ce;
    width: 95%;
    -webkit-appearance: none;
    background: #fff;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    -moz-background-clip: padding;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    -webkit-box-shadow: 0 0 8px rgba(182, 195, 214, .6) inset, 0 1px 1px #fff;
    -moz-box-shadow: 0 0 8px rgba(182, 195, 214, .6) inset, 0 1px 1px #fff;
    box-shadow: 0 0 8px rgba(182, 195, 214, .6) inset, 0 1px 1px #fff;
    -webkit-transition-duration: 300ms;
    -moz-transition-duration: 300ms;
    -o-transition-duration: 300ms;
    -ms-transition-duration: 300ms;
    transition-duration: 300ms;
    -webkit-transition-easing: ease-in-out;
    -moz-transition-easing: ease-in-out;
    -o-transition-easing: ease-in-out;
    -ms-transition-easing: ease-in-out;
    transition-easing: ease-in-out;
    -webkit-transition-property: border-color, -webkit-box-shadow;
    -webkit-transition-property: border-color, box-shadow;
    -moz-transition-property: border-color, -moz-box-shadow;
    -moz-transition-property: border-color, box-shadow;
    -o-transition-property: border-color, box-shadow;
    -ms-transition-property: border-color, box-shadow;
    transition-property: border-color, box-shadow;
}
.fork-link {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    width: 140px;
}
@media only screen and (-webkit-min-device-pixel-ratio:1.25), (min-resolution:120dpi) {
    html {
        background-size: 51px auto;
    }
}
@font-face {font-family: "iconfont";
  src: url('iconfont.eot?t=1529402722179'); /* IE9*/
  src: url('iconfont.eot?t=1529402722179#iefix') format('embedded-opentype'), /* IE6-IE8 */
  url('data:application/x-font-woff;charset=utf-8;base64,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') format('woff'),
  url('iconfont.ttf?t=1529402722179') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
  url('iconfont.svg?t=1529402722179#iconfont') format('svg'); /* iOS 4.1- */
}

.iconfont {
  font-family:"iconfont" !important;
  font-size:16px;
  font-style:normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.icon-zhongping:before { content: "\e504"; }

.icon-bottom-comment:before { content: "\e508"; }

.icon-chaping:before { content: "\e634"; }

.icon-haoping:before { content: "\e608"; }

.icon-frown:before { content: "\e77e"; }

.icon-meh:before { content: "\e780"; }

.icon-smile:before { content: "\e783"; }
.breadcrumb {
     margin-top: 0; 
}
</style>
{/literal}
<title>修改订单信息</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 评价列表 <span class="c-gray en">&gt;</span> 修改评价信息 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=comments';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<input type="hidden" name="id" class="cid" value="{$cid}">
<div class="commentAll">
    <!--评论区域 begin-->
    <div class="reviewArea clearfix">
        <textarea class="content comment-input" placeholder="请输入需要回复的内容" ></textarea>

        <span class="plBtn">发布评论</span>
    </div>
    <!--回复区域 begin-->
    <div class="comment-show">
        <div class="comment-show-con clearfix">
            <div class="comment-show-con-img pull-left"><img src="{$headimgurl}" alt=""></div>
            <div class="comment-show-con-list pull-left clearfix">
                <div class="pl-text clearfix">
                    <span class="comment-size-name">{$user_name} : </span>
                    <span class="my-pl-con">&nbsp;{$content}</span>
                </div>
                <div class="date-dz">
                    <span class="date-dz-left pull-left comment-time">{$add_time}</span>
                    <div class="date-dz-right pull-right comment-pl-block">
                        <span class="pull-left date-dz-line">
{if $CommentType == 'GOOD'}<span class="icon iconfont icon-haoping" style="color: #43CD80;"></span>{elseif $CommentType == 'NOTBAD'}<span style="color: #EEAD0E;" class="icon iconfont icon-zhongping"></span>{else}<span style="color: #EE4000;" class="icon iconfont icon-frown"></span>{/if}
                        </span>
                    </div>
                </div>
                <div class="hf-list-con"></div>
            </div>
        </div>
    </div>
    <!--回复区域 end-->
</div>
{literal}
<script type="text/javascript">
$(".plBtn").click( function () { 
  var comment_input = $(".comment-input").val(),
  comment_type = $('input[name="comment-type"]:checked').val(),
  id = $(".cid").val();
  if(comment_input.length > 0){
    $.ajax({
       type: "POST",
       url: "index.php?module=comments&action=reply&id="+id,
       data: "comment_input="+comment_input,
       success: function(res){
            if(res == 1){
              alert('评论成功!');
              location.href='index.php?module=comments';
            }else{
              alert('评论失败!');
            }
       }
    });
  }else{
    alert('请填写完整!');
  }
 });

</script>
{/literal}
</body>
</html>