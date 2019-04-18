<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class groupproAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = addslashes(trim($request->getParameter('id'))); // 插件id
        $sql = "select m.*,l.product_title as pro_name from (select p.id,p.product_id,p.group_id,c.img as image,p.group_price,p.member_price,c.price as market_price,c.name as attr_name,c.color,c.size as guige,p.classname from lkt_group_product as p left join lkt_configure as c on p.attr_id=c.id where p.group_id='$id' order by p.classname) as m left join lkt_product_list as l on m.product_id=l.id";

        $res = $db -> select($sql);
        $len = count($res);
                // 查询系统参数
        $sql1 = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql1);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        foreach ($res as $k => $v) {
            $res[$k] -> image = $img.$v -> image;
        }
        $status = trim($request->getParameter('status')) ? 1:0;
        $request->setAttribute("status",$status);
        $request->setAttribute("list",$res);
        $request->setAttribute("len",$len);
        return View :: INPUT;
    }

    public function execute(){
        
        
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>