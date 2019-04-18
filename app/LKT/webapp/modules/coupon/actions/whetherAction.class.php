<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class whetherAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter('id')); // 活动id

        $sql = "select status from lkt_coupon_activity where id = '$id'";
        $r = $db->select($sql);
        if($r[0]->status == 1){
            $sql = "update lkt_coupon_activity set status = 2 where id = '$id'";
            $res = $db->update($sql);
			echo $res;exit;
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('禁用成功！');" .
                "location.href='index.php?module=coupon';</script>";
            return;
        }else{
            $sql = "update lkt_coupon_activity set status = 1 where id = '$id'";
             $res = $db->update($sql);
			echo $res;exit;
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('启用成功！');" .
                "location.href='index.php?module=coupon';</script>";
            return;
        }
    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>