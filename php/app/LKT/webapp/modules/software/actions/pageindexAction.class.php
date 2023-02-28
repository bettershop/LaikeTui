<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class pageindexAction extends Action {


    public function getDefaultView() {

        $db = DBAction::getInstance();



        $request = $this->getContext()->getRequest();

        $sql = "select * from lkt_config where id = '1'";

        $r = $db->select($sql);

        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        // 查询轮播图表，根据sort顺序排列

        $sql = "select * from lkt_index_page order by sort";
        $r = $db->select($sql);

        foreach ($r as $k => $v) {
            if($v->type == 'img'){
                $v->image = $uploadImg . $v->image; 
           }else{
                $cid = $v->url;
                $sql = "select pname from lkt_product_class where cid = '$cid'";
                $cr = $db->select($sql);
                if($cr){
                    $v->name =  $cr[0]->pname; // 分类名称
                }
           }
        }

        $request->setAttribute("list",$r);



        return View :: INPUT;

    }



    public function execute() {



    }



    public function getRequestMethods(){

        return Request :: NONE;

    }




}



?>