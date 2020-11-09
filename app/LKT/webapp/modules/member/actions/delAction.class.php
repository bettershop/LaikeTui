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
        $id = $request->getParameter('id'); //id
        $id = rtrim($id, ','); // 去掉最后一个逗号
        $id = explode(',',$id); // 变成数组
        foreach ($id as $k => $v){
            $sql = "select name from lkt_admin where id = '$v'";
            $r = $db->select($sql);
            $admin_name = $r[0]->name;

            $sql = "update lkt_admin set recycle = 1 , status = 1 where id = $v";
            $db->update($sql);

            $db->admin_record($admin_id,' 删除管理员 '.$admin_name,3);
        }

        $res = array('status' => '1','info'=>'删除成功！');
        echo json_encode($res);
        return;
    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>