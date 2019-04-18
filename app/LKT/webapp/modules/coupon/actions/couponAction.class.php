<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class couponAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = "select * from lkt_coupon_config where id = '1'";
        $r_1 = $db->select($sql);
        $activity_overdue = $r_1[0]->activity_overdue; // 活动过期删除时间

        $name = addslashes(trim($request->getParameter('name'))); // 用户id

        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:'10';
        // 每页显示多少条数据
        $page = $request -> getParameter('page');

        // 页码
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        }

        $condition = '1 = 1';
        if($name != ''){   
            $condition .= " and b.name like '%$name%'";
        }

        $time = date('Y-m-d H:i:s'); // 当前时间
        $sql = "select a.*,b.name from lkt_coupon as a LEFT JOIN lkt_coupon_activity as b ON a.hid = b.id where $condition";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select a.*,b.name from lkt_coupon as a LEFT JOIN lkt_coupon_activity as b ON a.hid = b.id where $condition order by a.add_time desc limit $start,$pagesize";
        $r = $db->select($sql); 
        if($r){
            foreach ($r as $k => $v) {
                $id = $v->id; // 优惠券id
                $hid = $v->hid; // 活动id
                $expiry_time = $v->expiry_time; // 到期时间

                $sql = "select * from lkt_coupon_config where id = 1";
                $rr = $db->select($sql);
                $coupon_overdue = $rr[0]->coupon_overdue; // 优惠券过期删除时间
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
            }
        }
        $url = "index.php?module=finance&action=Index&name=".urlencode($name)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$r);
        $request->setAttribute("name",$name);
        $request->setAttribute("pages_show",$pages_show);
        $request->setAttribute("pagesize",$pagesize);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>