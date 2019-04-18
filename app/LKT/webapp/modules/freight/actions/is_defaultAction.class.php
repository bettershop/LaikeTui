<?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class is_defaultAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = $request->getParameter('id'); // 产品id

        $sql = "select id,is_default from lkt_freight";
        $r = $db->select($sql);
        if($r){
            $y_id = 0;
            foreach ($r as $k => $v){
                $is_default = $v->is_default;

                if($is_default == 1){
                    $y_id = $v->id;
                }
            }
            if($y_id != 0){
                if($y_id == $id){
                    $sql = "update lkt_freight set is_default = 0 where id = '$id'";
                    $db->update($sql);
                }else{
                    $sql = "update lkt_freight set is_default = 0";
                    $db->update($sql);

                    $sql = "update lkt_freight set is_default = 1 where id = '$id'";
                    $db->update($sql);
                }
            }else{
                $sql = "update lkt_freight set is_default = 1 where id = '$id'";
                $db->update($sql);
            }
            $db->admin_record($admin_id,' 修改规则id为 '.$id.' 的状态 ',2);

        }
        $res = array('status' => '1','info'=>'成功！');
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