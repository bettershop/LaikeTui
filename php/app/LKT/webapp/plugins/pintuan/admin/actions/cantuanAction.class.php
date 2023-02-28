<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class cantuanAction extends PluginAction
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $status = $request->getParameter('status');
        $product_title = $request->getParameter('product_title');
        $user = $request->getParameter('user');
        $pagesize = 10;
        // 每页显示多少条数据
        $page = $request->getParameter('page');


        $page = $page ? $page : 1;
        // 页码
        if ($page) {
            $start = ($page - 1) * $pagesize;
        } else {
            $start = 0;
        };
        $con = "d.r_sNo like 'PT%'";
        if ($product_title) {
            $con .= " and d.p_name like '%$product_title%'";
        }
        if ($user) {
            $con .= " and u.user_name like '%$user%'";
        }

        //查询是参团下的该用户下的拼团信息
        $sql = "SELECT u.user_name,d.p_name,d.p_id as goods_id,d.sid as attr_id ,o.add_time,o.sNo,o.z_price 
                    from lkt_order as o
                    LEFT JOIN lkt_user as u on o.user_id = u.user_id
                    LEFT JOIN lkt_order_details as d on o.sNo = d.r_sNo
                    WHERE $con
                    ORDER BY o.add_time desc  limit $start,$pagesize ";
        $can_res = $db->select($sql);

        if ($can_res) {
            foreach ($can_res as $k => $v) {
                $order_no = $v->sNo;
                $sql = "SELECT d.*,c.price
                from lkt_order_details as d
                LEFT JOIN lkt_configure as c on d.sid = c.id
                WHERE r_sNo = '$order_no'";
                $res1 = $db->select($sql);
                // print_r($sql);die;
                $v->can_price = $v->z_price;
                if ($res1) {
                    $v->price = $res1[0]->price;
                }
            }
        }

        $sql001 = "SELECT u.user_name,d.p_name,d.p_id as goods_id,d.sid as attr_id,o.add_time ,o.sNo,o.z_price 
                    from lkt_order as o
                    LEFT JOIN lkt_user as u on o.user_id = u.user_id
                    LEFT JOIN lkt_order_details as d on o.sNo = d.r_sNo
                    WHERE $con
                    ORDER BY o.add_time desc";
        $r_pager = $db->select($sql001);
        if ($r_pager) {
            $total = count($r_pager);
        } else {
            $total = 0;
        }
        $pager = new ShowPager($total, $pagesize, $page);
        $url = "index.php?module=pi&p=pintuan&action=cantuan&user=" . urlencode($user) . "&status=" . urlencode($status) . "&product_title=" . urlencode($product_title) . "&pagesize=" . urlencode($pagesize);
        $pages_show = $pager->multipage($url, $total, $page, $pagesize, $start, $para = '');// url 总条数 当前页码  每页显示条数
        $request->setAttribute('pages_show', $pages_show);//分页
        $request->setAttribute("list", $can_res);
        $request->setAttribute("status", $status);
        $request->setAttribute("product_title", $product_title);
        $request->setAttribute("user", $user);
        return View :: INPUT;
    }


    public function execute()
    {

    }

    public function getRequestMethods()
    {
        return Request :: NONE;
    }

}

?>