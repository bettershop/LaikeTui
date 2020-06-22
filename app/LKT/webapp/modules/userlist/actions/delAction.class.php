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
        $id = $request->getParameter('id'); 
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
                    $rr =$db -> select("select * from lkt_user where id = '$id'");
                    if($rr){
                        $data =$rr[0];
                         $sql01 = "insert into lkt_user_del (user_id,user_name,headimgurl,wx_name,sex,wx_id,Referee,access_token,img_token,source) values('$data->user_id','$data->user_name','$data->headimgurl','$data->wx_name','$data->sex','$data->wx_id','$data->Referee','$data->access_token','$data->img_token',1)";
                        $r = $db->insert($sql01);
                    }
                    $sql = "delete from lkt_user where id = '$id'";
                    $db->delete($sql);
                    $res = array('status' => '1','info'=>'删除成功！');
                    echo json_encode($res);
                    exit;
                    
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