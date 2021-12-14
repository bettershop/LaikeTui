<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class groupproAction extends PluginAction
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $store_id = $this->getContext()->getStorage()->read('store_id');

        // 接收信息
        $id = addslashes(trim($request->getParameter('id'))); // 插件id
        $sql = "select m.*,l.product_title as pro_name from (select p.id,p.product_id,p.group_id,c.img as image,p.group_price,p.member_price,c.price as market_price,c.name as attr_name,c.color,c.size as guige,p.classname from lkt_group_product as p left join lkt_configure as c on p.attr_id=c.id where p.store_id = '$store_id' and p.group_id='$id' order by p.classname) as m left join lkt_product_list as l on m.product_id=l.id";

        $res = $db->select($sql);
        $len = count($res);

        foreach ($res as $k => $v) {
            $res[$k]->image = $this->getimgpath($v->image);
        }
        $status = trim($request->getParameter('status')) ? 1 : 0;
        $request->setAttribute("status", $status);
        $request->setAttribute("list", $res);
        $request->setAttribute("len", $len);
        return View :: INPUT;
    }

    public function getimgpath($img)
    {

        $appConfig = $this->getAppInfo();
        $uploadImg = $appConfig['imageRootUrl'];
        $image = $uploadImg . $img;
        return $image;

        return $image;
    }

    public function execute()
    {


    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

}

?>