<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');


class DelAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval(trim($request -> getParameter('id')));

        $sql = 'delete from lkt_hotkeywords where id='.$id;
        
        $res = $db -> delete($sql);
        if($res > 0){
          header("Content-type:text/html;charset=utf-8");
          echo "<script type='text/javascript'>" .
                "alert('删除成功！');".
                "location.href='index.php?module=keyword';</script>";
        }
        return View :: INPUT;
    }

    public function execute() {
        
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>