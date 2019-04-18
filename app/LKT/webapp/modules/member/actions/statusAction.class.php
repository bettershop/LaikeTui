<?php
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

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('启用成功！');" .
                    "location.href='index.php?module=member';</script>";
                return;
            }else if($status == 2){
                $sql = "update lkt_admin set status = 1 where id = '$id'";
                $db->update($sql);
                $sql = "update lkt_admin set status = 1 where sid = '$id'";
                $db->update($sql);
                $db->admin_record($admin_id,'禁用管理员'.$admin_name,5);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('禁用成功！');" .
                    "location.href='index.php?module=member';</script>";
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