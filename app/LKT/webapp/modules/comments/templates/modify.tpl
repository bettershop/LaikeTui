<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

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
.commentAll{width: 500px;padding: 20px;border: 1px solid #b3acac;margin: 20px auto;}
.plBtn{width: 75px;height: 36px;line-height: 36px;background-color: #339b53;text-align: center;display: block;float: right;color: #FFFFFF;font-size: 12px;border-radius: 6px;margin-right: 2px;}
.plBtn:hover{background-color: #2f904d;}

/*----------评论区域 begin----------*/
.comment-show{margin-top: 20px;}
.comment-show-con {
    width: 100%;
    border-top: 1px solid #b3acac;
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
</style>
{/literal}
<title>修改订单信息</title>
</head>
<body>


<nav class="breadcrumb">
    系统管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=comments">评价列表</a> <span class="c-gray en">&gt;</span> 
    修改评价信息 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>



<input type="hidden" name="id" class="cid" value="{$cid}">
<div class="commentAll">
    <!--评论区域 begin-->
    <div class="reviewArea clearfix">
        <textarea class="content comment-input" placeholder="请输入需要修改的内容" >{$content}</textarea>
            <span>
      评论类型
      <lable>
        <i class='input_style radio_bg'><input type="radio" name="comment-type" checked="checked" value="GOOD"></i>
        <span class="icon iconfont icon-haoping" style="color: #43CD80;"></span>好评 &nbsp;   &nbsp; 
      </lable>
      <lable>
        <i class='input_style radio_bg'><input type="radio" name="comment-type" value="NOTBAD"></i>
        <span style="color: #EEAD0E;" class="icon iconfont icon-zhongping"></span>中评   &nbsp;  &nbsp; 
      </lable>
      <lable>
        <i class='input_style radio_bg'><input type="radio" name="comment-type" value="BAD"></i>
       <span style="color: #EE4000;" class="icon iconfont icon-frown"></span>差评   &nbsp; 
      </lable>
    </span>


        <span class="plBtn">修改评论</span>
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
  if(comment_input.length > 0 && comment_type.length > 0){
    $.ajax({
       type: "POST",
       url: "index.php?module=comments&action=modify&id="+id,
       data: "comment_input="+comment_input+"&comment_type="+comment_type,
       success: function(res){
            if(res == 1){
              alert('修改成功!');
              location.href='index.php?module=comments';
            }else{
              alert('修改失败!');
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