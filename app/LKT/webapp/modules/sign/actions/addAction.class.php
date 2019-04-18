<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class addAction extends Action {

  public function getDefaultView() {
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    $sql = "select * from lkt_config where id = '1'";
    $r = $db->select($sql);
    $uploadImg = $r[0]->uploadImg; // 图片上传位置

    $request->setAttribute("uploadImg",$uploadImg);
    return View :: INPUT;
  }

  public function execute() {
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();

    $image= addslashes($request->getParameter('image')); // 活动图片
    $starttime = $request->getParameter('starttime'); // 活动开始时间
    $endtime = $request->getParameter('endtime'); // 活动结束时间
    $detail = addslashes(trim($request->getParameter('detail'))); // 活动介绍
    if($image){
      $image = preg_replace('/.*\//','',$image);
    }else{
      echo "<script type='text/javascript'>" .
          "alert('签到活动图片不能为空！');" .
          "</script>";
      return $this->getDefaultView();
    }
    if($starttime == ''){
      header('Content-Type: text/html;charset=utf-8');
      echo "<script type='text/javascript'>" .
          "alert('活动开始时间不能为空！');" . 
          "</script>";
      return $this->getDefaultView();
    }
    $starttime = date('Y-m-d H:i:s',strtotime($starttime));
    if($endtime == ''){
      header('Content-Type: text/html;charset=utf-8');
      echo "<script type='text/javascript'>" .
          "alert('活动结束时间不能为空！');" . 
          "</script>";
      return $this->getDefaultView();

    }
    $endtime = date('Y-m-d 23:59:59',strtotime($endtime));

    if($starttime >= $endtime){
      header('Content-Type: text/html;charset=utf-8');
      echo "<script type='text/javascript'>" .
          "alert('活动开始时间不能大于等于活动结束时间！');" . 
          "</script>";
      return $this->getDefaultView(); 
    }

    $time = date('Y-m-d H:i:s');
    if($time >= $endtime){
      header('Content-Type: text/html;charset=utf-8');
      echo "<script type='text/javascript'>" .
          "alert('活动还没开始就已经结束！');" . 
          "</script>";
      return $this->getDefaultView(); 
    }
    // 查询所有签到活动
    $sql = "select * from lkt_sign_activity";
    $r = $db->select($sql);
    if($r){
      for ($i=0; $i < count($r); $i++) { 
        if($starttime >= $r[$i]->starttime && $starttime < $r[$i]->endtime || $endtime > $r[$i]->starttime && $endtime <= $r[$i]->endtime){
          header('Content-Type: text/html;charset=utf-8');
          echo "<script type='text/javascript'>" .
              "alert('活动有冲突！');" . 
              "</script>";
          return $this->getDefaultView(); 
        }
      }
    }
    // 活动开始时间大于当前时间,活动还没开始
    if($starttime > $time){
      // 添加活动
      $sql = "insert into lkt_sign_activity(image,starttime,endtime,detail,add_time,status) " .
      "values('$image','$starttime','$endtime','$detail',CURRENT_TIMESTAMP,0)";
      $rr = $db->insert($sql);
    }else{
      // 添加活动
      $sql = "insert into lkt_sign_activity(image,starttime,endtime,detail,add_time,status) " .
      "values('$image','$starttime','$endtime','$detail',CURRENT_TIMESTAMP,1)";
      $rr = $db->insert($sql);
    }
    if($rr == -1 ){
      header("Content-type:text/html;charset=utf-8");
      echo "<script type='text/javascript'>" .
          "alert('未知原因，活动添加失败！');" .
          "location.href='index.php?module=sign';</script>";
      return $this->getDefaultView();
    }else{
      header("Content-type:text/html;charset=utf-8");
      echo "<script type='text/javascript'>" .
          "alert('活动添加成功！');" .
          "location.href='index.php?module=sign';</script>";
      return $this->getDefaultView();
    }
  }

  public function getRequestMethods(){
    return Request :: POST;
  }

}

?>