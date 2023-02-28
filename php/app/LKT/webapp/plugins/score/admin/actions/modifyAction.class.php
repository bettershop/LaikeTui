<?php

/**
 * [Laike System] Copyright (c) 2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class modifyAction extends PluginAction
{


    public function getDefaultView()
    {
       return View::INPUT;
    }

    public function ajaxmodify()
    {
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id');
        $res = lkt_gets("select a.*,b.product_title,b.volume,b.imgurl,b.num,b.status as sta,b.initial  from lkt_score_pro AS a,lkt_product_list AS b where a.pro_id = b.id AND a.id= $id ");
        echo json_encode(array('code' => 200, 'data' => $res));
        exit();
    }

    public function insert()
    {
        $request = $this->getContext()->getRequest();
        $data = array();

        $score = $request->getParameter('score');
        $is_show = $request->getParameter('is_show'); //是否显示（0不显示，1热销单品，2.购物车，3.个人中心,4.分销商品显示）

        $data[] = $score;
        $data[] = $is_show;
        $data[] = $id = $request->getParameter('id');

        $res = lkt_execute("update lkt_score_pro set score = ?,is_show = ? where id = ?",$data);
        if ($res > 0) {
            echo json_encode(array('code' => 200, 'message' => '修改成功!'));
        } else {
            echo json_encode(array('code' => 400, 'message' => '未知原因，修改失败!'));
        }
    }

    public function getRequestMethods()
    {

        return Request::NONE;
    }
}
