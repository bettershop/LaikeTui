<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class ajaxAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $product_class_id = addslashes(trim($request->getParameter('product_class_id'))); // 商品类型id
        if($product_class_id){
            $sql = "select id,product_title from lkt_product_list where product_class like '%$product_class_id%'";
            $r = $db->select($sql);
            if($r){
                $res = '<option value="0" >全部</option>';
                foreach ($r as $key => $value) {
                   $res.= "<option value='{$value->id}'>{$value->product_title}</option>";
                }
                echo $res;
                exit;
            }else{
                $res = 0;
                echo $res;
            }
        }else{
            $res = 0;
            echo $res;
        }
        
        return;
    }

    public function execute(){
        
    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>