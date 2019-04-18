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
        $id = $request->getParameter('id'); // 插件id
        // 根据插件id,查询查询状态
        $lottery_status = $request->getParameter('lottery_status');
        $userid = $request->getParameter('userid');
            $sql03 = "update lkt_draw_user set lottery_status ='$lottery_status' where id = $userid ";
            // 
            $r03 = $db->update($sql03);
            // print_r($r03);die;
        if($r03 > 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=draw&action=operation&id=$id';</script>";
            return;
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改失败！');" .
                "location.href='index.php?module=draw&action=operation&id=$id';</script>";
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