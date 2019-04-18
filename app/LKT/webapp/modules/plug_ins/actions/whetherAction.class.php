<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class whetherAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = intval($request->getParameter('id')); // 插件id
        // 根据插件id,查询查询状态
        $sql = "select status from lkt_plug_ins where id = '$id'";
        $r = $db->select($sql);

        if($r[0]->status == 1){
            $sql = "update lkt_plug_ins set status = 0 where id = '$id'";
            $res = $db->update($sql);
            $db->admin_record($admin_id,' 禁用插件id为 '.$id,5);
			echo $res; exit;
            // header("Content-type:text/html;charset=utf-8");
            // echo "<script type='text/javascript'>" .
            //     "alert('禁用成功！');" .
            //     "location.href='index.php?module=plug_ins';</script>";
            // return;
        }else{
            $sql = "update lkt_plug_ins set status = 1 where id = '$id'";
            $res = $db->update($sql);

            $db->admin_record($admin_id,' 启用插件id为 '.$id,5);
			echo $res; exit;
            // header("Content-type:text/html;charset=utf-8");
            // echo "<script type='text/javascript'>" .
            //     "alert('启用成功！');" .
            //     "location.href='index.php?module=plug_ins';</script>";
            // return;
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