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
        $db = DBAction::getInstance();
        $res = $db->select("select a.*,b.product_title,b.volume,b.imgurl,b.num,b.status as sta,b.initial  from lkt_detailed_pro AS a,lkt_product_list AS b where a.pro_id = b.id AND a.id= $id ");
        echo json_encode(array('code' => 200, 'data' => $res));
        exit();
    }
    public function baocun()
    {
        $request = $this->getContext()->getRequest();
        $data = array();

        $leve = $request->getParameter('leve'); //向上返几级
        $leve1 = $request->getParameter('leve1'); //一级佣金比例
        $leve2 = $request->getParameter('leve2');  //二级佣金比例
        $leve3 = $request->getParameter('leve3'); //三级佣金比例
        $is_show = $request->getParameter('is_show'); //是否显示（0不显示，1热销单品，2.购物车，3.个人中心,4.分销商品显示）

        $data[] = $leve = $leve ? $leve : '0';
        $data[] = $leve1 = $leve1 ? $leve1 : '0'; //一级佣金比例
        $data[] = $leve2 = $leve2 ? $leve2 : '0'; //二级佣金比例
        $data[] = $leve3 = $leve3 ? $leve3 : '0'; //三级佣金比例

        $data[] = $type = $request->getParameter('type'); //佣金发放类型，1 支付成功 2.确认收货
        $data[] = $commissions = $request->getParameter('commissions'); //分销佣金所需手续费
        $data[] = $is_show ? $is_show : '0'; //是否显示（0不显示，1热销单品，2.购物车，3.个人中心，4分销商品显示）
        $data[] = $id = $request->getParameter('id'); //id

        $db = DBAction::getInstance();
        $res = $db->preUpdate("update lkt_detailed_pro set leve = ?,leve1 = ?,leve2 = ?,leve3 = ?,type = ?,commissions = ?,is_show = ? where id = ?",$data);
        if ($res > 0) { //
            $db->commit();
            echo json_encode(array('code' => 200, 'message' => '修改成功!'));
            exit();
        } else {
            $db->rollback();
            echo json_encode(array('code' => 400, 'message' => '未知原因，修改失败!'));
            exit();
        }
    }

    public function getRequestMethods()
    {

        return Request::NONE;
    }
}
