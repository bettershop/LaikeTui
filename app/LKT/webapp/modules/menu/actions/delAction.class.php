<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');
        $id = $request->getParameter('id'); 
        $num = 0;
        $status = 0;
        $sql = "select id from lkt_core_menu where s_id = '$id' and recycle = 0";
        $r = $db->select($sql);
        if($r){ // 有下级
            $status = 1;
        }
        if($status == 0){
            $db->admin_record($admin_id,' 删除菜单id为 '.$id.' 成功 ',3);

            $sql = "update lkt_core_menu set recycle = 1 where id = '$id'";
            $db->update($sql);
            $res = array('status' => '1','info'=>'删除成功！');
            echo json_encode($res);
            return;
        }else{
            $db->admin_record($admin_id,' 删除菜单id为 '.$id.' 失败 ',3);

            $res = array('status' => '0','info'=>'删除失败！');
            echo json_encode($res);
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

