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
        $sql = "delete from lkt_freight where id = '$id' ";
        $db->delete($sql);
        $db->admin_record($admin_id,' 删除规则id为 '.$id.' 的信息',3);
        $res = array('status'=>1,'info'=>'成功！');
        echo json_encode($res);

    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>