<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */



class memberAction extends PluginAction {

    public function getDefaultView() {

    }

    public function delpro(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id');
        if(strpos($id, ',')){
          $id = substr($id, 0,-1);
        }

        $sql = 'update lkt_group_product set recycle = 1 where group_id="'.$id.'"';
        $res = $db -> update($sql);
        $r = $db -> select("select * from lkt_group_open where group_id=$id and ptstatus =1 ");
                if($r){
                    foreach ($r as $key01 => $value01) {

                         $db->update("UPDATE `lkt_group_open` SET `ptstatus`='3' WHERE id = ".$value01->id);
                         $ee = $db->select("select user_id,z_price,sNo,pay from lkt_order where ptcode = '".$value01->ptcode."'");

                         if($ee){
                            foreach ($ee as $key02=> $value02) {
                                $db->update("UPDATE `lkt_order_details` SET `r_status`='11' WHERE r_sNo = '".$value02->sNo."'");
                                $db->update("UPDATE lkt_user SET money =money+$value02->z_price WHERE user_id = '".$value02->user_id."'");
                                $event = $value02->user_id.'退回拼团金额'.$value02->z_price.'';
                                $sqlldr = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$value02->user_id','$value02->z_price','','$event',5)";
                                $beres1 = $db->insert($sqlldr);
                            }
                         }
                         $db->update("UPDATE `lkt_order` SET `ptstatus`='3', `status`='11' WHERE ptcode = ".$value01->ptcode);
                    }
                     $db-> rollback();
                }
        
        if($res <= 0){
            echo json_encode(array('status' => 0,'info' => '删除失败!'));exit;
        }else{
            echo json_encode(array('status' => 1,'info' => '删除成功!'));exit;
        }

       
    }

    public function is_market(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id');
        $type = trim($request->getParameter('type'));    //停止或开始产品砍价

        $str = '';
        if($type == 2){
            $str .= ',is_show = 1';
            //查询商品是否被删除及下架
           $re= $db->select("select lkt_product_list.recycle ,lkt_product_list.status from lkt_group_product,lkt_product_list where group_id = $id and lkt_group_product.product_id = lkt_product_list.id");
           if($re){
                if($re[0]->recycle ==1 ||$re[0]->status ==1){
                     echo json_encode(array('status' => 0,'info' => '操作失败!'));exit;
                }
           }

        }else if($type == 3){
            $str .= ',is_show = 0';
        }


        $sql = "update lkt_group_product set g_status=$type$str where group_id = $id ";
        $res = $db -> update($sql);
        $this->guoqi($db,$id);////处理点击停止活动，处理该活动下面所有进行中拼团停止，拼团成功的则不变
        if($res < 0){
            echo json_encode(array('status' => 0,'info' => '操作失败!'));exit;
        }else{
            echo json_encode(array('status' => 1,'info' => '操作成功!'));exit;
        }
    }

    public function contpro() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $type = $request->getParameter('type');
        $id = intval($request->getParameter("id")); // 商品id
        $sql = "update lkt_group_product set is_show=$type where group_id=$id";
        $res = $db -> update($sql);

        if($res < 0){
            echo json_encode(array('status' => 0,'info' => '操作失败!'));exit;
        }else{
            echo json_encode(array('status' => 1,'info' => '操作成功!'));exit;
        }
    }

    public function allpro() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
      
        $admin_name = $this->getContext()->getStorage()->read('admin_name'); // 管理员账号

        $id = $request->getParameter("id"); // 商品id
        $type = $request->getParameter("type"); // 路径
        $type = $type == 'up'?0:1;
        if(strpos($id, ',')){
          $id = substr($id, 0,-1);
        }
        $sql = "update lkt_product_list set status = $type where id in($id) ";
        $rr = $db->update($sql);
        if($type == 1){
            $bsql = "update lkt_group_product set g_status=3 where product_id in($id)";
            $db -> update($bsql);
        }
        if($rr >= 0){
            $db->admin_record($store_id,$admin_name,' 商品id为 '.$id.' 下架成功',3);
            echo json_encode(array('upDown' => '','status' => 1)); exit;
        }else{
            $db->admin_record($store_id,$admin_name,' 商品id为 '.$id.' 下架失败',3);
            echo json_encode(array('upDown' => '','status' => 2)); exit;
        }
        
    }

    public function execute() {
        $request = $this->getContext()->getRequest();
        $m = $request->getParameter('m');
        $this -> $m();
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

    function guoqi($db, $group_id)
    {//处理点击停止活动，处理该活动下面所有进行中拼团停止，拼团成功的则不变

        $r = $db->select("select * from lkt_group_open where group_id=$group_id and ptstatus < 2 ");

        if ($r) {

            foreach ($r as $key01 => $value01) {
                $db->update("UPDATE `lkt_group_open` SET `ptstatus`='3' WHERE id = " . $value01->id);
                $ee = $db->select("select user_id,z_price,sNo,pay from lkt_order where ptcode = '" . $value01->ptcode . "'");
                if ($ee) {
                    foreach ($ee as $key02 => $value02) {
                        $db->update("UPDATE `lkt_order_details` SET `r_status`='11' WHERE r_sNo = '" . $value02->sNo . "'");
                        $db->update("UPDATE lkt_user SET money =money+$value02->z_price WHERE user_id = '" . $value02->user_id . "'");
                        $event = $value02->user_id . '退回拼团金额' . $value02->z_price . '';
                        $sqlldr = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$value02->user_id','$value02->z_price','','$event',5)";
                        $db->insert($sqlldr);
                    }
                }
                $db->update("UPDATE `lkt_order` SET `ptstatus`='3', `status`='11' WHERE ptcode = " . $value01->ptcode);
            }
        }
    }

}

?>