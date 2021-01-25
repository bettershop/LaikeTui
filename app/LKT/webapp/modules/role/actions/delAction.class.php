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
        $id = intval($request->getParameter('id'));
        $sql = "select name from lkt_role where id = '$id'";
        $r = $db->select($sql);
        $admin_name = $r[0]->name;
        $db->admin_record($admin_id,' 删除角色 '.$admin_name,3);
        $sql = "delete from lkt_role where id = '$id'";
        $res=$db->delete($sql);
		echo $res;
    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

}

