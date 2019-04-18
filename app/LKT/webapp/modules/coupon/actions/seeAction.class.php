<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class seeAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = $request->getParameter('user_id'); // 用户id

        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:'10';
        // 每页显示多少条数据
        $page = $request -> getParameter('page');

        // 页码
        if($page){
            $start = ($page-1)*10;
        }else{
            $start = 0;
        }

        $sql = "select * from lkt_coupon_config where id = '1'";
        $r_1 = $db->select($sql);
        $coupon_overdue = $r_1[0]->coupon_overdue; // 优惠券过期删除时间
        $time = date('Y-m-d H:i:s'); // 当前时间

        $sql = "select a.*,b.name from lkt_coupon as a LEFT JOIN lkt_coupon_activity as b ON a.hid = b.id where user_id = '$user_id'";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select a.*,b.name from lkt_coupon as a LEFT JOIN lkt_coupon_activity as b ON a.hid = b.id where user_id = '$user_id' order by a.add_time desc limit $start,$pagesize";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k => $v){
                $id = $v->id; // 到期时间
                $expiry_time = $v->expiry_time; // 到期时间
                $time_1 = date("Y-m-d H:i:s",strtotime("+$coupon_overdue day",strtotime($expiry_time))); // 优惠券过期删除时间
                // 当前时间大于活动结束时间,优惠券已过期
                if($time > $expiry_time){
                    $sql = "update lkt_coupon set status = 3 where id = '$id' ";
                    $db->update($sql);
                    $v->status = 3;
                }
                // 当前时间大于优惠券过期删除时间,就删除这条数据
                if($time_1 < $time){
                    $sql = "delete from lkt_coupon where id = '$id' ";
                    $db->delete($sql);
                }
                if($v->name){
                    $v->name = $v->name; // 活动名称
                }else{
                    // 查询配置信息
                    $sql = "select * from lkt_config where id = 1 ";
                    $rrr = $db->select($sql);
                    $v->name = $rrr[0]->company; // 公司名称
                }
                $list[] = $v;
            }
        }
        $url = "index.php?module=finance&action=Index&user_id=".urlencode($user_id);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$list);
        $request->setAttribute("pages_show",$pages_show);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>