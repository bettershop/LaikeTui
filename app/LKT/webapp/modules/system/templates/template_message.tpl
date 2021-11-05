<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<title>模板消息设置</title>
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

</head>
<body>


<nav class="breadcrumb">
    小程序 <span class="c-gray en">&gt;</span> 
    消息管理
</nav>


<div class="page-container">

    <form name="form1" action="" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div class="border_bg">
              <div class="panel-body">
                  <div class="form-group">  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">支付成功通知</label> 
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[order_success]" class="form-control" value="{$notice->order_success}">   
                            <div class="help-block">小程序模板消息编号示例: m1FFBWiae7r4Sx3cMZ7dyt0 </div>    
                                
                        </div>    
                    </div>  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">  
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">订单发货提醒</label> 
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[order_delivery]" class="form-control" value="{$notice->order_delivery}">    
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>            
                       
                        <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">开团成功提醒</label> 
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[group_pay_success]" class="form-control" value="{$notice->group_pay_success}">    
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">  
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">拼团待成团提醒</label> 
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[group_pending]" class="form-control" value="{$notice->group_pending}">   
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>            <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">拼团成功通知</label> 
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[group_success]" class="form-control" value="{$notice->group_success}">   
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">  
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">拼团失败通知</label>   
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[group_fail]" class="form-control" value="{$notice->group_fail}">   
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">  
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">抽奖结果通知</label>   
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[lottery_res]" class="form-control" value="{$notice->lottery_res}">    
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">    
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">退款成功通知</label>   
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[refund_success]" class="form-control" value="{$notice->refund_success}">    
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>    
                                
                        </div>    
                    </div>
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">  
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">退款通知</label>   
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[refund_res]" class="form-control" value="{$notice->refund_res}">    
                            <div class="help-block">小程序模板消息编号示例:  m1FFBWiae7r4Sx3cMZ7dyt0  </div>   
                                
                        </div>    
                    </div> 
                    
                  <div class="form-group"></div>  
                <div class="form-group">  
                      <label class="col-xs-12 col-sm-4 col-md-4 control-label"></label> 
                      <div class="col-sm-8 col-xs-12">  
                              
                              <input type="submit" name="submit" value="提交" class="btn btn-primary col-lg-1" data-original-title="" title=""> 
                              
                              
                       </div> 
                </div>
              </div>      </div>
        </div>

    </form>

</div>
</body>
</html>