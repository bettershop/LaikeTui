<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class addgroupAction extends PluginAction{

    public function getDefaultView(){

        return View :: INPUT;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $gdata = json_decode($request->getParameter('gdata'));
        $goods_id = json_decode($request->getParameter('goods_id'));
        $glevel = $request->getParameter('glevel');
        $group_title = $request->getParameter('group_title');

        $sel_attr_sql = "select c.id from lkt_configure as c
                        left join lkt_product_list as p on c.pid = p.id
                        where c.pid = $goods_id ";
        $attr_res = $db->select($sel_attr_sql);

        if($gdata -> endtime == 'changqi'){
            $gdata -> endtime = date("Y-m-d H:i:s",strtotime("+1years"));
        }

        if($group_title == ''){
            $goods_sql = "select product_title from lkt_product_list where id=$goods_id ";
            $goods_res = $db->select($goods_sql);
            $group_title = $goods_res[0]->product_title;
        }
        $nu = $db->select("select max(group_id) as a from lkt_group_product ");
        if($nu){
            $group_id=$nu[0]->a+1;
        }else{
            $group_id=1;
        }

        $gdata = serialize($gdata);
        $glevel = serialize($glevel);
        $str = 'insert into lkt_group_product(attr_id,product_id,group_level,group_data,group_title,group_id) values';
        foreach ($attr_res as $k => $v) {
            $attr_id = $v->id;
            $str .= "($attr_id,$goods_id,'$glevel','$gdata','$group_title','$group_id'),";
        }
        $str = substr($str, 0,strlen($str)-1);
        $respro = $db->insert($str);

        if($respro < 0){
            echo json_encode(array('code' => 0));exit;
        }else{
            echo json_encode(array('code' => 1));exit;
        }
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
}
?>