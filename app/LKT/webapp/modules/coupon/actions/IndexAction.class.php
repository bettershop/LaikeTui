<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $activity_type = addslashes(trim($request->getParameter('activity_type'))); // 活动类型
        $name = addslashes(trim($request->getParameter('name'))); // 标题

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

        $sql = "select * from lkt_coupon_config where id = '1'";
        $r_1 = $db->select($sql);

        $activity_overdue = $r_1[0]->activity_overdue; // 活动过期删除时间
        $condition = ' recycle = 0 ';
        if($activity_type != '' && $activity_type != 0){
            $condition .= " and activity_type = '$activity_type'";
        }

        if($name != ''){
            $condition .= " and name like '%$name%'";
        }
        $sql = "select * from lkt_coupon_activity where $condition";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select * from lkt_coupon_activity where $condition order by add_time desc limit $start,$pagesize";
        $r = $db->select($sql);
        $list = array();
        $time = date('Y-m-d H:i:s'); // 当前时间
        if($r){
            foreach ($r as $k => $v) {
                $id = $v->id; // 活动id
                $activity_type1 = $v->activity_type; // 活动类型

                if($activity_type1 == 1){
                    $v->activity_type = '注册';
                    $v->end_time = '永久有效';
                }else if($activity_type1 == 2){
                    $v->activity_type = '节日/活动';
                }else if($activity_type1 == 3){
                    $v->activity_type = '满减';
                }
                $time_1 = date("Y-m-d H:i:s",strtotime("+$activity_overdue day",strtotime($v->end_time))); // 活动过期删除时间

                // 当前时间大于活动结束时间
                if($v->end_time < $time && $activity_type1 != 1){
                    // 根据id,修改活动状态
                    $sql = "update lkt_coupon_activity set status = 3 where id = '$id'";
                    $db->update($sql);
                    $v->status = 3;
                }
                // 当前时间大于活动过期删除时间,删除这条数据
                if($time_1 < $time && $activity_type1 != 1){
                    $sql = "update lkt_coupon_activity set recycle = 1 where id = '$id' ";
                    $db->update($sql);
                }
                $sql = "select id from lkt_coupon where hid = '$id'";
                $rr = $db->select($sql);
                $v->num = count($rr);
            }
            $list = $r;
        }

        $url = "index.php?module=finance&action=Index&activity_type=".urlencode($activity_type)."&name=".urlencode($name)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("activity_type",$activity_type);
        $request->setAttribute("name",$name);
        $request->setAttribute("list",$list);
        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('pagesize', $pagesize);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>