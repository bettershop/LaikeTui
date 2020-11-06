<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class statusAction extends Action {

	public function getDefaultView() {
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $id = addslashes(trim($request->getParameter('id')));

		$sql = "select name,status from lkt_admin where id = '$id'";
		$r = $db->select($sql);
		if($r){
            $admin_name = $r[0]->name;
            $status = $r[0]->status;
		    if($status == 1){
                $sql = "update lkt_admin set status = 2 where id = '$id'";
                $db->update($sql);
                $sql = "update lkt_admin set status = 2 where sid = '$id'";
                $db->update($sql);

                $db->admin_record($admin_id,'启用管理员'.$admin_name,5);
                 $res = array('status' => '1','info'=>'启用成功！');
                    echo json_encode($res);
                return;
            }else if($status == 2){
                $sql = "update lkt_admin set status = 1 where id = '$id'";
                $db->update($sql);
                $sql = "update lkt_admin set status = 1 where sid = '$id'";
                $db->update($sql);
                $db->admin_record($admin_id,'禁用管理员'.$admin_name,5);
                 $res = array('status' => '1','info'=>'禁用成功！');
                echo json_encode($res);
                return;
            }
        }
	}

	public function execute(){
		
	}

	public function getRequestMethods(){
		return Request :: NONE;
	}

}

?>