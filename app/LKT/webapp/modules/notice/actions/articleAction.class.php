<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class articleAction extends Action {

    public function getDefaultView() {
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    $id= $request->getParameter('id');

    $sql = "select * from lkt_config where id = '1'";
    $r = $db->select($sql);
    $uploadImg = $r[0]->uploadImg; // 图片上传位置

    $sql01 = "select * from lkt_set_notice where id = $id";
   $res_notice = $db->select($sql01);
   
    $request->setAttribute("uploadImg",$uploadImg);
    $request->setAttribute("res_notice",$res_notice);
    return View :: INPUT;
  }

  public function execute() {
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    print_r(12);die;
    $id= $request->getParameter('id'); // notice


  }

  public function getRequestMethods(){
    return Request :: POST;
  }

}
?>