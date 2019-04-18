<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = $request->getParameter('id'); // id
        // 根据新闻id，删除新闻信息
        $admin_id = $this->getContext()->getStorage()->read('admin_id');
       

        $sql01 = "select a.id from lkt_order as a ,lkt_user as b  where a.user_id = b.user_id and b.id = $id";

        $re = $db->selectrow($sql01);

            if($re>0){//有订单，不能删除
                 $db->admin_record($admin_id,'删除用户 '.$id.' 失败',24);
                    $res = array('status' => '3','info'=>'有订单，不能删除！');
                    echo json_encode($res);
                    exit();
            }else{
                    $a=$db->admin_record($admin_id,' 删除用户 '.$id,24);
                    $sql = "delete from lkt_user where id = '$id'";
                    // $db->delete($sql);
                    $res = array('status' => '1','info'=>'删除成功！');
                    // echo json_encode($res);
                    echo $db->delete($sql);exit;
                    
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