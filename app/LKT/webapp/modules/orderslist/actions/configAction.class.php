<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class configAction extends Action {

  public function getDefaultView() {
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    
    $sql = "select * from lkt_order_config where id = 1";
    $r = $db->select($sql);
    if($r){
      $content = $r[0]->content;
      $back = $r[0]->back;
        $order_failure = $r[0]->order_failure;
        $company = $r[0]->company;
        $order_overdue = $r[0]->order_overdue;
      $unit = $r[0]->unit;
      if($r[0]->days == 0){
        $days = '';
      }else{
        $days = $r[0]->days;
      }
    }else{
      $days = '';
      $content = '';
      $back = 2;
        $order_failure = 2;
        $company = '天';
        $order_overdue = 2;
        $unit = '天';
    }


    if($company == '天'){
        $company = '<option value="0">'.$company.'</option>';
        $company .= '<option value="1">小时</option>';
    }else{
        $company = '<option value="1">'.$company.'</option>';
        $company .= '<option value="0">天</option>';
    }
    if($unit == '天'){
      $unit = '<option value="0">'.$unit.'</option>';
      $unit .= '<option value="1">小时</option>';
    }else{
      $unit = '<option value="1">'.$unit.'</option>';
      $unit .= '<option value="0">天</option>';
    }
    
    $request -> setAttribute("days",$days);
    $request -> setAttribute("content",$content);
    $request -> setAttribute("back",$back);
    $request -> setAttribute("order_failure",$order_failure);
      $request -> setAttribute("company",$company);
      $request -> setAttribute("order_overdue",$order_overdue);
    $request -> setAttribute("unit",$unit);
    
    return View :: INPUT;
  }

  public function execute() {
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    $admin_id = $this->getContext()->getStorage()->read('admin_id');
    $days = addslashes(trim($request->getParameter('days'))); // 承若天数
    $content = addslashes(trim($request->getParameter('content'))); // 承若内容
    $back = addslashes(trim($request->getParameter('back'))); // 退货时间
    $order_overdue = trim($request->getParameter('order_failure')); // 订单过期删除时间
    // var_dump($order_overdue);exit;
    $unit = addslashes(trim($request->getParameter('unit'))); // 单位

    if($days != ''){
      if(is_numeric($days)){
        if($days <= 0){
          header("Content-type:text/html;charset=utf-8");
          echo "<script type='text/javascript'>" .
              "alert('承若天数不能为负数或零!');" .
              "</script>";
          return $this->getDefaultView();
        }
      }else{
        header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('承若天数请输入数字!');" .
            "</script>";
        return $this->getDefaultView();
      }
      if($content == ''){
        header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('承若内容不能为空!');" .
            "</script>";
        return $this->getDefaultView();
      }
    }
    if(is_numeric($back) == ''){
      header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('退货时间请输入数字!');" .
            "</script>";
        return $this->getDefaultView();
    }
    if($back <= 0){
      header("Content-type:text/html;charset=utf-8");
      echo "<script type='text/javascript'>" .
          "alert('退货时间不能为负数或零!');" .
          "</script>";
      return $this->getDefaultView();
    }
    if(is_numeric($order_overdue) == ''){
      header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('订单过期删除时间请输入数字!');" .
            "</script>";
        return $this->getDefaultView();
    }
    if($order_overdue < 0){
      header("Content-type:text/html;charset=utf-8");
      echo "<script type='text/javascript'>" .
          "alert('订单过期删除时间不能为负数!');" .
          "</script>";
      return $this->getDefaultView();
    }
    if($unit == 0){
      $unit = '天';
    }else{
      $unit = '小时';
    }
    $sql = "select * from lkt_order_config";
    $r = $db->select($sql);
    if($r){
      $days = $days ? $days:0;
      $sql = "update lkt_order_config set days = '$days',content = '$content',back = '$back',order_failure = '$order_overdue',unit = '$unit',modify_date = CURRENT_TIMESTAMP where id = 1";
      $r_1 = $db->update($sql);
      // var_dump($sql,$r_1);exit;
      if($r_1 == -1) {
          $db->admin_record($admin_id,' 修改订单设置失败 ',2);

          echo "<script type='text/javascript'>" .
                "alert('未知原因，订单设置修改失败！');" .
                "location.href='index.php?module=orderslist&action=config';</script>";
            return $this->getDefaultView();
      } else {
          $db->admin_record($admin_id,' 修改订单设置 ',2);

          header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
              "alert('订单设置修改成功！');" .
              "location.href='index.php?module=orderslist&action=config';</script>";
      }
    }else{
      $sql = "insert into lkt_order_config(days,content,back,order_failure,unit,modify_date) value('$days','$content','$back','$order_overdue','$unit',CURRENT_TIMESTAMP)";
      $r_1 = $db->insert($sql);
      if($r_1 == -1) {
          $db->admin_record($admin_id,' 修改订单设置失败 ',2);

          echo "<script type='text/javascript'>" .
            "alert('未知原因，订单设置添加失败！');" .
            "location.href='index.php?module=orderslist&action=config';</script>";
        return $this->getDefaultView();
      } else {
          $db->admin_record($admin_id,' 修改订单设置 ',2);

          header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('订单设置添加成功！');" .
            "location.href='index.php?module=orderslist&action=config';</script>";
      }
    }
    return;
  }

  public function getRequestMethods(){
    return Request :: POST;
  }

}
?>