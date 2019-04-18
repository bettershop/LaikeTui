<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class member_record_delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = $request->getParameter('id'); // id数组
        $type = $request->getParameter('type'); //id
        if($type == 'onekey'){
            $sql = "TRUNCATE TABLE lkt_admin_record";
            $db->query($sql);
            $db->admin_record($admin_id,'一键清空管理员记录表',3);
        }else{
            $id = rtrim($id, ','); // 去掉最后一个逗号
            $id = explode(',',$id); // 变成数组
            foreach ($id as $k => $v){
                $sql = "delete from lkt_admin_record where id = '$v'";
                $db->delete($sql);
            }
            $db->admin_record($admin_id,'批量删除管理员记录表',3);
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