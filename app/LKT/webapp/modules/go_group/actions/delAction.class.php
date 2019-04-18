<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        
        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();
        // 查询插件表

        if(isset($_GET['use']) && $_GET['use'] == 1){
            $this -> delgroup();
        }else if(isset($_GET['use']) && $_GET['use'] == 2){
            $this -> startgroup();
        }else if(isset($_GET['use']) && $_GET['use'] == 3){
            $this -> stopgroup();
        }

        return View :: INPUT;
    }
    public function delgroup() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));

        $sql = 'delete from lkt_group_buy where status="'.$id.'"';
        $res = $db -> delete($sql);
        $sql1 = 'delete from lkt_group_product where group_id="'.$id.'"';
        $res1 = $db -> delete($sql1);
        
        if($res > 0 && $res1 > 0){
            echo json_encode(array('status' => 1));exit;
        }
    }

    public function startgroup() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));

        $sql = 'update lkt_group_buy set is_show=1 where status="'.$id.'"';
        $res = $db -> update($sql);
        
        if($res > 0){
            echo json_encode(array('status' => 1));exit;
        }
    }
    
    public function stopgroup() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));

        $sql = 'update lkt_group_buy set is_show=0 where status="'.$id.'"';
        $res = $db -> update($sql);
        
        if($res > 0){
            echo json_encode(array('status' => 1));exit;
        }
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>