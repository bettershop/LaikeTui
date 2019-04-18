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

       $arr = array('1' => '自动退款','2' => '手动退款');
       $res = $db -> select("select * from lkt_group_config");
       if(!empty($res)) $res = $res[0] -> refunmoney;
       $request->setAttribute("arr",$arr);
       $request->setAttribute("type",$res);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest(); 
        $retype = intval(trim($request -> getParameter('retype')));
        $sql = "update lkt_group_config set refunmoney=$retype";
        $res = $db -> update($sql);
        if($res >= 0){
          echo json_encode(array('code' => 1));exit;
        }
       
    }


    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>