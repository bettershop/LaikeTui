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
    $admin_id = $this->getContext()->getStorage()->read('admin_id');
    $notice= $request->getParameter('notice'); // notice
    $image= addslashes($request->getParameter('image')); // 活动图片
    $detail = addslashes(trim($request->getParameter('detail'))); // 活动介绍
    if($image){
      $image = preg_replace('/.*\//','',$image);
    }else{
      echo "<script type='text/javascript'>" .
          "alert('公告活动图片不能为空！');" .
          "</script>";
      return $this->getDefaultView();
    }

    if(empty($detail)){
      echo "<script type='text/javascript'>" .
          "alert('公告内容不能为空！');" .
          "</script>";
      return $this->getDefaultView();
    }

    if(empty($notice)){
      echo "<script type='text/javascript'>" .
          "alert('公告名称不能为空！');" .
          "</script>";
      return $this->getDefaultView();
    }


    $sql = "insert into lkt_set_notice(user,name,img_url,detail,time) " .
    "values('$admin_id','$notice','$image','$detail',CURRENT_TIMESTAMP)";
    $rr = $db->insert($sql);
    if($rr == -1 ){
      header("Content-type:text/html;charset=utf-8");
      echo "<script type='text/javascript'>" .
          "alert('未知原因，添加失败！');" .
          "location.href='index.php?module=notice';</script>";
      return $this->getDefaultView();
    }else{
      header("Content-type:text/html;charset=utf-8");
      echo "<script type='text/javascript'>" .
          "alert('添加成功！');" .
          "location.href='index.php?module=notice';</script>";
      return $this->getDefaultView();
    }
  }

  public function getRequestMethods(){
    return Request :: POST;
  }

}

?>