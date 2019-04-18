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
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = $request->getParameter('id'); // 产品id

        $num = 0;
        $id = rtrim($id, ','); // 去掉最后一个逗号
        $id = explode(',',$id); // 变成数组

        foreach ($id as $k => $v){
            $sql = "delete from lkt_cart where Goods_id = '$v'";
            $db->delete($sql);

            $sql = "delete from lkt_user_footprint where Goods_id = '$v'";
            $db->delete($sql);

            $sql = "delete from lkt_user_collection where Goods_id = '$v'";
            $db->delete($sql);
            // 根据产品id，删除产品信息
            $sql = "update lkt_product_list set recycle = 1,status = 1 where id = '$v'";
            $db->update($sql);

            $sql = "update lkt_configure set recycle = 1 where pid = '$v'";
            $db->update($sql);

            $sql = "update lkt_product_img set recycle = 1 where product_id = '$v'";
            $db->update($sql);

            $db->admin_record($admin_id,' 删除商品id为 '.$v.' 的信息',3);

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