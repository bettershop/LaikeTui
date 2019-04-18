<!DOCTYPE html>

<html lang="en" class="app">



  <head>

    <meta charset="utf-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>公众平台管理系统</title>

    <meta name="keywords" content="微信,微信公众平台" />

    <meta name="description" content="微信公众平台管理系统，是国内最完善移动网站及移动互联网技术解决方案。" />

    <link rel="shortcut icon" href="http://ketter.mingxingnaxieshi.com/attachment/images/global/wechat.jpg" />



    <script  type="text/javascript" src="style/tgt/util.js"></script>

    <link rel="stylesheet" href="style/tgt/font-awesome.min.css" type="text/css" />

    <link rel="stylesheet" href="style/tgt/webfont.css" type="text/css" />

    <link rel="stylesheet" href="style/tgt/fontStyle.css" type="text/css" />

    <link rel="stylesheet" href="style/tgt/iconfont.css" type="text/css" />

    <link rel="stylesheet" href="style/tgt/bootstrap.min.css" type="text/css" />

    <link rel="stylesheet" href="style/tgt/common.css">

    <link rel="stylesheet" href="style/tgt/animate.min.css" type="text/css" />

    <link rel="stylesheet" href="style/tgt/style.min.css" type="text/css" />

{literal}

    <script>

      var require = {

        urlArgs: 'v=2018070213'

      };

    </script>

    <script src="style/tgt/require.js"></script>

    <script src="style/tgt/config.js"></script>

    <script src="style/tgt/jquery-1.11.1.min.js"></script>

{/literal}

  </head>



  <body>

    <script type="text/javascript" src="style/tgt/jquery.gcjs.js"></script>

    <script type="text/javascript" src="style/tgt/jquery.form.js"></script>

    <script type="text/javascript" src="style/tgt/tooltipbox.js"></script>

    <script type="text/javascript" src="style/tgt/sceollFix.js"></script>

    {literal}

    <style type="text/css">

      .red {

        float: left;

        color: red

      }

      

      .white {

        float: left;

        color: #fff

      }

      

      .tooltipbox {

        background: #fef8dd;

        border: 1px solid #c40808;

        position: absolute;

        left: 0;

        top: 0;

        text-align: center;

        height: 20px;

        color: #c40808;

        padding: 2px 5px 1px 5px;

        border-radius: 3px;

        z-index: 1000;

      }

      

      .red {

        float: left;

        color: red

      }

      

      .bg-light .nav-primary>ul>li>ul.nav>li>a {

        margin: 0 0 0 10px;

      }

    </style>

    {/literal}

    <section class="vbox hidden-bsection">

      <section>

        <section class="hbox stretch">

          <script src="style/tgt/common.js"></script>

          <section>

            <section class="vbox">

              <section class="scrollable padder" style="padding-top:10px;">

                <script language='javascript' src="style/tgt/designer.js"></script>

                <script language='javascript' src="style/tgt/jquery.contextMenu.js"></script>

                <link href="style/tgt/jquery.contextMenu.css" rel="stylesheet">

                {literal}

                <style type='text/css'>

                  #poster {

                    width: 320px;

                    height: 504px;

                    border: 1px solid #ccc;

                    position: relative

                  }

                  

                  #poster .bg {

                    position: absolute;

                    width: 100%;

                    z-index: 0

                  }

                  

                  #poster .drag[type=img] img,

                  #poster .drag[type=thumb] img {

                    width: 100%;

                    height: 100%;

                  }

                  

                  #poster .drag {

                    position: absolute;

                    width: 80px;

                    height: 80px;

                    border: 1px solid #000;

                  }

                  

                  #poster .drag[type=nickname] {

                    width: 80px;

                    height: 40px;

                    font-size: 16px;

                    font-family: 黑体;

                  }

                  

                  #poster .drag img {

                    position: absolute;

                    z-index: 0;

                    width: 100%;

                  }

                  

                  #poster .rRightDown,

                  .rLeftDown,

                  .rLeftUp,

                  .rRightUp,

                  .rRight,

                  .rLeft,

                  .rUp,

                  .rDown {

                    position: absolute;

                    width: 7px;

                    height: 7px;

                    z-index: 1;

                    font-size: 0;

                  }

                  

                  #poster .rRightDown,

                  .rLeftDown,

                  .rLeftUp,

                  .rRightUp,

                  .rRight,

                  .rLeft,

                  .rUp,

                  .rDown {

                    background: #C00;

                  }

                  

                  .rLeftDown,

                  .rRightUp {

                    cursor: ne-resize;

                  }

                  

                  .rRightDown,

                  .rLeftUp {

                    cursor: nw-resize;

                  }

                  

                  .rRight,

                  .rLeft {

                    cursor: e-resize;

                  }

                  

                  .rUp,

                  .rDown {

                    cursor: n-resize;

                  }

                  

                  .rLeftDown {

                    left: -4px;

                    bottom: -4px;

                  }

                  

                  .rRightUp {

                    right: -4px;

                    top: -4px;

                  }

                  

                  .rRightDown {

                    right: -4px;

                    bottom: -4px;

                  }

                  

                  .rRightDown {

                    background-color: #00F;

                  }

                  

                  .rLeftUp {

                    left: -4px;

                    top: -4px;

                  }

                  

                  .rRight {

                    right: -4px;

                    top: 50%;

                    margin-top: -4px;

                  }

                  

                  .rLeft {

                    left: -4px;

                    top: 50%;

                    margin-top: -4px;

                  }

                  

                  .rUp {

                    top: -4px;

                    left: 50%;

                    margin-left: -4px;

                  }

                  

                  .rDown {

                    bottom: -4px;

                    left: 50%;

                    margin-left: -4px;

                  }

                  

                  .context-menu-layer {

                    z-index: 9999;

                  }

                  

                  .context-menu-list {

                    z-index: 9999;

                  }

                </style>

                {/literal}

                <div class="main rightlist">

                  <form action="" method="post" class="form-horizontal form" enctype="multipart/form-data">

                    <input type="hidden" name="id" value="" />

                    <input type="hidden" name="https_name" class="https_name" value="{$uploadImg}" />

                    <div class='panel panel-default'>

                      <div class='panel-heading'>

                        海报设置

                      </div>

                      <div class='panel-body'>



                        <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label"><span style='color:red'>*</span> 海报名称</label>

                          <div class="col-sm-9 col-xs-12">

                            <input type="text" name="title" class="form-control" value="" />

                          </div>

                        </div>

                        <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label"><span style='color:red'>*</span> 海报类型</label>

                          <div class="col-sm-9 col-xs-12">

 <label class="radio-inline">

                            <input type="radio" name="type" value="1"  /> 文章海报

                        </label>



                         <label class="radio-inline">

                            <input type="radio" name="type" value="2"  /> 红包

                        </label>



                        <label class="radio-inline">

                            <input type="radio" checked="checked" name="type" value="3"  /> 商品海报

                        </label>


                          </div>

                        </div>



                        <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label"><span style='color:red'>*</span> 生成二维码关键词</label>

                          <div class="col-sm-9 col-xs-12">

                            <input type="text" name="keyword" class="form-control" value="" />

                            <span class='help-block'>如果是商品海报 ，回复关键词是 关键词+商品ID</span>

                          </div>

                        </div>



                        <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label">是否默认</label>

                          <div class="col-sm-9 col-xs-12">

                            <label class="radio-inline">

                            <input type="radio" name="isdefault" value="1" checked/> 是

                        </label>

                            <label class="radio-inline">

                            <input type="radio" name="isdefault" value="0"  /> 否

                        </label>

                            <span class='help-block'>是否是海报类型的默认设置，一种海报只能一个默认设置</span>

                          </div>

                        </div>

                        <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label"><span style='color:red'>*</span> 海报设计</label>

                          <div class="col-sm-9 col-xs-12">

                            <table style='width:100%;'>

                              <tr>

                                <td style='width:320px;' valign='top'>

                                  <div id='poster'>

                                  </div>



                                </td>

                                <td valign='top'>

                                  <div class='panel panel-default'>

                                    <div class='panel-body'>

                                      <div class="form-group" id="bgset">

                                        <label class="col-xs-12 col-sm-3 col-md-2 control-label">背景图片</label>

                                        <div class="col-sm-9 col-xs-12">

{literal}

                                          <script type="text/javascript">

                                            function showImageDialog(elm, opts, options) {

                                              require(["util"], function(util) {

                                                var btn = $(elm);

                                                var ipt = btn.parent().prev();

                                                var val = ipt.val();

                                                var img = ipt.parent().next().children();

                                                options = {

                                                  'global': false,

                                                  'class_extra': '',

                                                  'direct': true,

                                                  'multiple': false

                                                };

                                                util.image(val, function(url) {

                                                  if(url.url) {

                                                    if(img.length > 0) {

                                                      img.get(0).src = url.url;

                                                    }

                                                    ipt.val(url.attachment);

                                                    ipt.attr("filename", url.filename);

                                                    ipt.attr("url", url.url);

                                                  }

                                                  if(url.media_id) {

                                                    if(img.length > 0) {

                                                      img.get(0).src = "";

                                                    }

                                                    ipt.val(url.media_id);

                                                  }

                                                }, null, options);

                                              });

                                            }



                                            function deleteImage(elm) {

                                              require(["jquery"], function($) {

                                                $(elm).prev().attr("src", "style/tgt/nopic.jpg");

                                                $(elm).parent().prev().find("input").val("");

                                              });

                                            }

                                          </script>

{/literal}

                                          <div class="input-group ">

                                            <input type="text" name="bg" value="" class="form-control" autocomplete="off">

                                            <span class="input-group-btn">

        <button class="btn btn-default" type="button" onclick="showImageDialog(this);">选择图片</button>

      </span>

                                          </div>

                                          <div class="input-group " style="margin-top:.5em;">

                                            <img src="style/tgt/nopic.jpg" onerror="this.src='style/tgt/nopic.jpg'; this.title='图片未找到.'" class="img-responsive img-thumbnail" width="150" />

                                            <em class="close" style="position:absolute; top: 0px; right: -14px;" title="删除这张图片" onclick="deleteImage(this)">×</em>

                                          </div> <span class='help-block'>背景图片尺寸: 640 * 1008</span>

                                        </div>



                                      </div>

                                      <div class="form-group">

                                        <label class="col-xs-12 col-sm-3 col-md-3 control-label">海报元素</label>

                                        <div class="col-sm-9 col-xs-12">

                                          <button class='btn btn-default btn-com' type='button' data-type='head' style="margin-bottom: 4px">头像</button>

                                          <button class='btn btn-default btn-com' type='button' data-type='nickname' style="margin-bottom: 4px">昵称</button>

                                          <button class='btn btn-default btn-com' type='button' data-type='qr' style="margin-bottom: 4px">二维码</button>

                                          <button class='btn btn-default btn-com' type='button' data-type='img' style="margin-bottom: 4px">图片</button>

                                          <span id="goodsparams">

                                           <!-- <span id="goodsparams" style="display:none"> -->

                                                                  <button class='btn btn-default btn-com' type='button' data-type='title' >商品名称</button>    

                                                                  <button class='btn btn-default btn-com' type='button' data-type='thumb' >商品图片</button>    

                                                                  <button class='btn btn-default btn-com' type='button' data-type='marketprice' >商品现价</button>    

                                                                  <button class='btn btn-default btn-com' type='button' data-type='productprice' >商品原价</button>    

                                                             </span>

                                        </div>

                                      </div>

                                      <div id='qrset' style='display:none'>

                                        <div class="form-group">

                                          <label class="col-xs-12 col-sm-3 col-md-3 control-label">二维码尺寸</label>

                                          <div class="col-sm-9 col-xs-12">

                                            <select id='qrsize' class='form-control'>

                                              <option value='1'>1</option>

                                              <option value='2'>2</option>

                                              <option value='3'>3</option>

                                              <option value='4'>4</option>

                                              <option value='5'>5</option>

                                              <option value='6'>6</option>

                                            </select>

                                          </div>



                                        </div>

                                      </div>



                                      <div id='nameset' style='display:none'>

                                        <div class="form-group">

                                          <label class="col-xs-12 col-sm-3 col-md-3 control-label">昵称颜色</label>

                                          <div class="col-sm-9 col-xs-12 wid100">

{literal}

                                            <script type="text/javascript">

                                              require(["jquery", "util"], function($, util) {

                                                $(function() {

                                                  $(".colorpicker").each(function() {

                                                    var elm = this;

                                                    util.colorpicker(elm, function(color) {

                                                      $(elm).parent().prev().prev().val(color.toHexString());

                                                      $(elm).parent().prev().css("background-color", color.toHexString());

                                                    });

                                                  });

                                                  $(".colorclean").click(function() {

                                                    $(this).parent().prev().prev().val("");

                                                    $(this).parent().prev().css("background-color", "#FFF");

                                                  });

                                                });

                                              });

                                            </script>

{/literal}

                                            <div class="row row-fix">

                                              <div class="col-xs-8 col-sm-8" style="padding-right:0;">

                                                <div class="input-group">

                                                  <input class="form-control" type="text" name="color" placeholder="请选择颜色" value="">

                                                  <span class="input-group-addon" style="width:35px;border-left:none;background-color:"></span>

                                                  <span class="input-group-btn">

            <button class="btn btn-default colorpicker" type="button">选择颜色 <i class="fa fa-caret-down"></i></button>

            <button class="btn btn-default colorclean" type="button"><span><i class="fa fa-remove"></i></span></button>

                                                  </span>

                                                </div>

                                              </div>

                                            </div>

                                          </div>



                                        </div>

                                        <div class="form-group">

                                          <label class="col-xs-12 col-sm-3 col-md-3 control-label">昵称大小</label>

                                          <div class="col-sm-4">

                                            <div class='input-group wid100'>

                                              <input type="text" id="namesize" class="form-control namesize" placeholder="例如: 14,16" />

                                              <div class='input-group-addon'>px</div>

                                            </div>

                                          </div>

                                        </div>

                                      </div>

                                      <div class="form-group" id="imgset" style="display:none">

                                        <label class="col-xs-12 col-sm-3 col-md-3 control-label">图片设置</label>

                                        <div class="col-sm-9 col-xs-12">



                                          <div class="input-group ">

                                            <input type="text" name="img" value="" class="form-control" autocomplete="off">

                                            <span class="input-group-btn">

        <button class="btn btn-default" type="button" onclick="showImageDialog(this);">选择图片</button>

      </span>

                                          </div>

                                          <div class="input-group " style="margin-top:.5em;">

                                            <img src="style/tgt/nopic.jpg" onerror="this.src='style/tgt/nopic.jpg'; this.title='图片未找到.'" class="img-responsive img-thumbnail" width="150" />

                                            <em class="close" style="position:absolute; top: 0px; right: -14px;" title="删除这张图片" onclick="deleteImage(this)">×</em>

                                          </div>

                                        </div>

                                      </div>



                                    </div>

                                  </div>

                                </td>

                              </tr>

                            </table>

                          </div>

                        </div>



                    <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label">生成等待文字</label>

                          <div class="col-sm-9 col-xs-12">



                            <textarea name="waittext" class="form-control"></textarea>

                            <span class="help-block">例如：您的专属海报正在拼命生成中，请等待片刻...</span>



                          </div>

                    </div>


                      </div>



                     

                      <div class="panel-body">



                        <div class="form-group">

                          <label class="col-xs-12 col-sm-3 col-md-2 control-label"></label>

                          <div class="col-sm-9 col-xs-12">

                            <input type="submit" name="submit" value="提交" class="btn btn-primary col-lg-1" />

                            <input type="hidden" name="token" value="41f48483" />

                            <input type="hidden" name="data" value="" />

                            <input type="button" name="back" onclick='history.back()' style='margin-left:10px;' value="返回列表" class="btn btn-default" />

                          </div>

                        </div>

                      </div>

                    </div>

                  </form>

                </div>


{literal}

                <script language='javascript'>

                  $('form').submit(function() {

                    if($(':input[name=title]').isEmpty()) {

                      Tip.focus($(':input[name=title]'), '请输入海报名称!');

                      return false;

                    }

                    if($(':input[name=type]:checked').length <= 0) {

                      Tip.focus($(':input[name=title]'), '请选择海报类型!');

                      return false;

                    }

                    if($(':input[name=keyword]').isEmpty()) {

                      Tip.focus($(':input[name=keyword]'), '请输入回复关键词!');

                      return false;

                    }

                    if($(':radio[name=type]:checked').val() == '4') {



                      if($(':radio[name=paytype]:checked').val() == '1') {



                        var recmoney = parseFloat($(':input[name=recmoney]').val());

                        if(recmoney > 0) {

                          if(recmoney < 1) {

                            Tip.select($(':input[name=recmoney]'), '微信企业付款需支付1元以上!', 'bottom');

                            return false;

                          }

                        }

                        var submoney = parseFloat($(':input[name=submoney]').val());

                        if(submoney > 0) {

                          if(submoney < 1) {

                            Tip.select($(':input[name=submoney]'), '微信企业付款需支付1元以上!', 'bottom');

                            return false;

                          }

                        }

                      }

                    }

                    var data = [];

                    $('.drag').each(function() {

                      var obj = $(this);

                      var type = obj.attr('type');

                      var left = obj.css('left'),

                        top = obj.css('top');

                      var d = {

                        left: left,

                        top: top,

                        type: obj.attr('type'),

                        width: obj.css('width'),

                        height: obj.css('height')

                      };

                      if(type == 'nickname' || type == 'title' || type == 'marketprice' || type == 'productprice') {

                        d.size = obj.attr('size');

                        d.color = obj.attr('color');

                      } else if(type == 'qr') {

                        d.size = obj.attr('size');

                      } else if(type == 'img') {

                        d.src = obj.attr('src');

                      }

                      data.push(d);

                    });

                    $(':input[name=data]').val(JSON.stringify(data));



                    return true;

                  });



                  function bindEvents(obj) {



                    var index = obj.attr('index');



                    var rs = new Resize(obj, {

                      Max: true,

                      mxContainer: "#poster"

                    });

                    rs.Set($(".rRightDown", obj), "right-down");

                    rs.Set($(".rLeftDown", obj), "left-down");

                    rs.Set($(".rRightUp", obj), "right-up");

                    rs.Set($(".rLeftUp", obj), "left-up");

                    rs.Set($(".rRight", obj), "right");

                    rs.Set($(".rLeft", obj), "left");

                    rs.Set($(".rUp", obj), "up");

                    rs.Set($(".rDown", obj), "down");

                    rs.Scale = true;

                    var type = obj.attr('type');

                    if(type == 'nickname' || type == 'img' || type == 'title' || type == 'marketprice' || type == 'productprice') {

                      rs.Scale = false;

                    }

                    new Drag(obj, {

                      Limit: true,

                      mxContainer: "#poster"

                    });

                    $('.drag .remove').unbind('click').click(function() {

                      $(this).parent().remove();

                    })



                    $.contextMenu({

                      selector: '.drag[index=' + index + ']',

                      callback: function(key, options) {

                        var index = parseInt($(this).attr('zindex'));



                        if(key == 'next') {

                          var nextdiv = $(this).next('.drag');

                          if(nextdiv.length > 0) {

                            nextdiv.insertBefore($(this));

                          }

                        } else if(key == 'prev') {

                          var prevdiv = $(this).prev('.drag');

                          if(prevdiv.length > 0) {

                            $(this).insertBefore(prevdiv);

                          }

                        } else if(key == 'last') {

                          var len = $('.drag').length;

                          if(index >= len - 1) {

                            return;

                          }

                          var last = $('#poster .drag:last');

                          if(last.length > 0) {

                            $(this).insertAfter(last);

                          }

                        } else if(key == 'first') {

                          var index = $(this).index();

                          if(index <= 1) {

                            return;

                          }

                          var first = $('#poster .drag:first');

                          if(first.length > 0) {

                            $(this).insertBefore(first);

                          }

                        } else if(key == 'delete') {

                          $(this).remove();

                        }

                        var n = 1;

                        $('.drag').each(function() {

                          $(this).css("z-index", n);

                          n++;

                        })

                      },

                      items: {

                        "next": {

                          name: "调整到上层"

                        },

                        "prev": {

                          name: "调整到下层"

                        },

                        "last": {

                          name: "调整到最顶层"

                        },

                        "first": {

                          name: "调整到最低层"

                        },

                        "delete": {

                          name: "删除元素"

                        }

                      }

                    });

                    obj.unbind('click').click(function() {

                      bind($(this));

                    })



                  }

                  var imgsettimer = 0;

                  var nametimer = 0;

                  var bgtimer = 0;



                  function bindType(type) {

                    $("#goodsparams").hide();

                    $(".type4").hide();

                    if(type == '4') {

                      $(".type4").show();

                    } else if(type == '3') {

                      $("#goodsparams").show();

                    }

                  }



                  function clearTimers() {

                    clearInterval(imgsettimer);

                    clearInterval(nametimer);

                    clearInterval(bgtimer);



                  }



                  function getImgUrl(val) {

                    var https_name = $(".https_name").val();

                    if(val.indexOf('http://') == -1) {

                      val = https_name + val;

                    }

                    return val;

                  }



                  function bind(obj) {

                    var imgset = $('#imgset'),

                      nameset = $("#nameset"),

                      qrset = $('#qrset');

                    imgset.hide(), nameset.hide(), qrset.hide();

                    clearTimers();

                    var type = obj.attr('type');

                    if(type == 'img') {

                      imgset.show();

                      var src = obj.attr('src');

                      var input = imgset.find('input');

                      var img = imgset.find('img');

                      if(typeof(src) != 'undefined' && src != '') {

                        input.val(src);

                        img.attr('src', getImgUrl(src));

                      }



                      imgsettimer = setInterval(function() {

                        if(input.val() != src && input.val() != '') {

                          var url = getImgUrl(input.val());

                          obj.attr('src', input.val()).find('img').attr('src', url);

                        }

                      }, 10);



                    } else if(type == 'nickname' || type == 'title' || type == 'marketprice' || type == 'productprice') {



                      nameset.show();

                      var color = obj.attr('color') || "#000";

                      var size = obj.attr('size') || "16";

                      var input = nameset.find('input:first');

                      var namesize = nameset.find('#namesize');

                      var picker = nameset.find('.sp-preview-inner');

                      input.val(color);

                      namesize.val(size.replace("px", ""));

                      picker.css({

                        'background-color': color,

                        'font-size': size

                      });



                      nametimer = setInterval(function() {

                        obj.attr('color', input.val()).find('.text').css('color', input.val());

                        obj.attr('size', namesize.val() + "px").find('.text').css('font-size', namesize.val() + "px");

                      }, 10);



                    } else if(type == 'qr') {

                      qrset.show();

                      var size = obj.attr('size') || "3";

                      var sel = qrset.find('#qrsize');

                      sel.val(size);

                      sel.unbind('change').change(function() {

                        obj.attr('size', sel.val())

                      });

                    }

                  }



                  $(function() {



                    $(':radio[name=type]').click(function() {

                      var type = $(this).val();

                      bindType(type);

                    })

                    //改变背景

                    $('#bgset').find('button:first').click(function() {

                      var oldbg = $(':input[name=bg]').val();

                      bgtimer = setInterval(function() {

                        var bg = $(':input[name=bg]').val();

                        if(oldbg != bg && bg != '') {

                          bg = getImgUrl(bg);

                          $('#poster .bg').remove();

                          var bgh = $("<img src='" + bg + "' class='bg' />");



                          var first = $('#poster .drag:first');

                          if(first.length > 0) {

                            bgh.insertBefore(first);

                          } else {

                            $('#poster').append(bgh);

                          }

                          oldbg = bg;

                          clearInterval(bgtimer);

                        }

                      }, 1000);

                    })



                    $('.btn-com').click(function() {



                      var imgset = $('#imgset'),

                        nameset = $("#nameset"),

                        qrset = $('#qrset');

                      imgset.hide(), nameset.hide(), qrset.hide();

                      clearTimers();



                      // if($('#poster img').length <= 0) {

                      //   //alert('请选择背景图片!');

                      //   //return;

                      // }

                      var type = $(this).data('type');

                      var img = "";

                      if(type == 'qr') {

                        img = '<img src="style/tgt/qr.jpg" />';

                      } else if(type == 'head') {

                        img = '<img src="style/tgt/moren.png" />';

                      } else if(type == 'img' || type == 'thumb') {

                        img = '<img src="style/tgt/kdd.png" />';

                      } else if(type == 'nickname') {

                        img = '<div class=text>昵称</div>';

                      } else if(type == 'title') {

                        img = '<div class=text>商品名称</div>';

                      } else if(type == 'marketprice') {

                        img = '<div class=text>商品现价</div>';

                      } else if(type == 'productprice') {

                        img = '<div class=text>商品原价</div>';

                      }

                      var index = $('#poster .drag').length + 1;

                      var obj = $('<div class="drag" type="' + type + '" index="' + index + '" style="z-index:' + index + '">' + img + '<div class="rRightDown"> </div><div class="rLeftDown"> </div><div class="rRightUp"> </div><div class="rLeftUp"> </div><div class="rRight"> </div><div class="rLeft"> </div><div class="rUp"> </div><div class="rDown"></div></div>');



                      $('#poster').append(obj);



                      bindEvents(obj);



                    });



                    $('.drag').click(function() {

                      bind($(this));

                    })



                  })



                  var currentCouponType = null;



                  function selectCoupon(type) {

                    currentCouponType = type;

                    $('#modal-module-menus-coupon').modal();

                  }



                  function select_coupon(o) {



                    $(":input[name=" + currentCouponType + "couponid]").val(o.id);

                    $("." + currentCouponType + "group").find('button').html("[" + o.id + "]" + o.couponname);

                    $("#modal-module-menus-coupon .close").click();



                  }

                </script>

                </div>

                <script language='javascript'>

                  require(['bootstrap'], function($) {

                    $('.btn').each(function() {



                      if($(this).closest('td').css('position') == 'relative') {

                        return true;

                      }

                      $(this).hover(function() {

                        $(this).tooltip('show');

                      }, function() {

                        $(this).tooltip('hide');

                      });

                    })



                  });

                  $('.js-clip').each(function() {

                    util.clip(this, $(this).attr('data-url'));

                  });

                </script>

{/literal}

</section>

</html>