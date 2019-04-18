<!DOCTYPE html>
<html>
<head>

<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />

<link href="style/css/style.css" rel="stylesheet" type="text/css" />

<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<title>模板消息设置</title>
<link rel="stylesheet" href="style/tgt/bootstrap.min.css" type="text/css" />
</head>
<body>
<nav class="breadcrumb" style="line-height: 2.6rem"><i class="Hui-iconfont">&#xe616;</i> 系统管理 <span class="c-gray en">&gt;</span> 模板消息设置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px;float: right;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>

<div class="page-container">

    <form name="form1" action="" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div class="border_bg">
              <div class="panel-body">
                  <div class="form-group">  
                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">购买成功通知</label> 
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[pay_success]" class="form-control" value="{$notice->pay_success}">   
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
                        <label class="col-xs-12 col-sm-4 col-md-4 control-label">订单支付成功通知</label>   
                        <div class="col-sm-8 col-xs-12">    
                                
                            <input type="text" name="notice[order_success]" class="form-control" value="{$notice->order_success}">   
                            <div class="help-block">小程序模板消息编号示例: m1FFBWiae7r4Sx3cMZ7dyt0 </div>    
                                
                        </div>            </div>            
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
                      <label class="col-xs-12 col-sm-3 col-md-2 control-label"></label> 
                      <div class="col-sm-9 col-xs-12">  
                              
                              <input type="submit" name="submit" value="提交" class="btn btn-primary col-lg-1" data-original-title="" title=""> 
                              <input type="hidden" name="token" value="41f48483"> 
                              
                       </div> 
                </div>
              </div>      </div>
        </div>

    </form>

</div>
</body>
</html>