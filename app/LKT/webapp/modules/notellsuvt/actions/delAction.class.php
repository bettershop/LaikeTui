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
        $id = $request->getParameter('id'); // id
        $recip = explode(',',$id);//字符串转一维数组
        $cor = count($recip);
        foreach ($recip as $key => $value) {
           $sql = "delete from lkt_system_message where id = '$value'";
            $res = $db->delete($sql);
        }
        if($res > 0){
            echo json_encode(array('code' => 1,'msg' => '删除成功!'));exit;
        }
        // header("Content-type:text/html;charset=utf-8");
        // echo "<script type='text/javascript'>" .
        //     "alert('删除成功！');" .
        //     "location.href='index.php?module=notellsuvt';</script>";
        // return;
    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>