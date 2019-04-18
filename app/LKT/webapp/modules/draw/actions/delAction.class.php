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
        // 接收信息
        $id = intval($request->getParameter('id')); // 插件id
        $sql = 'delete from lkt_draw where id='.$id;
        // print_r($sql);die;
        $res = $db -> delete($sql);
        echo $res; exit;
        if($res > 0){
            header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('删除成功！');" .
            "location.href='index.php?module=draw';</script>";
        return;
        }
        return;
    }

    public function execute(){
       
        
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>