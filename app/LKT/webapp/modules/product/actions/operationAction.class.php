<?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class operationAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = $request->getParameter('id'); // 产品id
        $type = $request->getParameter('type');
        $id = rtrim($id, ','); // 去掉最后一个逗号
        $id = explode(',',$id); // 变成数组
        foreach ($id as $k => $v){
            $sql = "select s_type from lkt_product_list where id = '$v'";
            $r = $db->select($sql);
            $s_type1 = $r[0]->s_type;
            $s_type = explode(',',$s_type1);
            if($type == 1 || $type == 2){
                if($type == 1){
                    $status = 0;
                }else{
                    $status = 1;
                }
                $sql = "update lkt_product_list set status = '$status' where id = '$v'";

                $db->admin_record($admin_id,' 修改商品id为 '.$v.' 的状态 ',2);
            }else if($type >= 3) {
                if ($type == 3) {
                    $add_type = 1; // 新品
                    if(in_array($add_type,$s_type)){ // 存在
                        $sql = "update lkt_product_list set s_type = '$s_type1' where id = '$v'";
                    }else{ // 不存在
                        $s_type2 = implode(',',array_merge($s_type, (array)$add_type));
                        $sql = "update lkt_product_list set s_type = '$s_type2' where id = '$v'";
                    }
                } else if ($type == 4) {
                    $del_type = 1; // 取消新品
                    if(in_array($del_type,$s_type)){ // 存在
                        foreach ($s_type as $key=>$value){
                            if ($value == $del_type){
                                unset($s_type[$key]);
                            }
                        }
                        $s_type3 = implode(',',$s_type);
                        $sql = "update lkt_product_list set s_type = '$s_type3' where id = '$v'";
                    }
                } else if ($type == 5) {
                    $add_type = 2; // 热销
                    if(in_array($add_type,$s_type)){ // 存在
                        $sql = "update lkt_product_list set s_type = '$s_type1' where id = '$v'";
                    }else{ // 不存在
                        $s_type2 = implode(',',array_merge($s_type, (array)$add_type));
                        $sql = "update lkt_product_list set s_type = '$s_type2' where id = '$v'";
                    }
                } else if ($type == 6) {
                    $del_type = 2; // 取消热销
                    if(in_array($del_type,$s_type)){ // 存在
                        foreach ($s_type as $key=>$value){
                            if ($value == $del_type){
                                unset($s_type[$key]);
                            }
                        }
                        $s_type3 = implode(',',$s_type);
                        $sql = "update lkt_product_list set s_type = '$s_type3' where id = '$v'";
                    }
                } else if ($type == 7) {
                    $add_type = 3; // 推荐
                    if(in_array($add_type,$s_type)){ // 存在
                        $sql = "update lkt_product_list set s_type = '$s_type1' where id = '$v'";
                    }else{ // 不存在
                        $s_type2 = implode(',',array_merge($s_type, (array)$add_type));
                        $sql = "update lkt_product_list set s_type = '$s_type2' where id = '$v'";
                    }
                } else if ($type == 8) {
                    $del_type = 3; // 取消推荐
                    if(in_array($del_type,$s_type)){ // 存在
                        foreach ($s_type as $key=>$value){
                            if ($value == $del_type){
                                unset($s_type[$key]);
                            }
                        }
                        $s_type3 = implode(',',$s_type);
                        $sql = "update lkt_product_list set s_type = '$s_type3' where id = '$v'";
                    }
                }
                $db->admin_record($admin_id,' 修改商品id为 '.$v.' 的类型 ',2);
            }
            $db->update($sql);
        }

        $res = array('status' => '1','info'=>'操作成功！');
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