<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class yueAction extends Action {

    public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $name = addslashes(trim($request->getParameter('name'))); // 用户名
        $type = $request->getParameter('otype');
        $starttime = $request->getParameter('startdate');//开始时间
        $group_end_time = $request -> getParameter('enddate');//结束时间

        $pageto = $request -> getParameter('pageto');
        // 导出
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

        $condition = 'a.type !=0 and a.type !=6 and a.type !=7 and a.type !=8 and a.type !=9 and a.type !=10 and a.type !=15 and a.type !=16 and a.type !=17 and a.type !=18 ';
        if($name){
            $condition .= " and a.user_id = '$name' ";
        }
        if($type && $type!='all'){
            $condition .= " and a.type = '$type' ";
        }
        if($starttime){
            $condition .= " and a.add_date >= '$starttime' ";
        }
        if($group_end_time){
            $condition .= " and a.add_date <= '$group_end_time' ";
        }
        $list = array();

        $sql = "select a.user_id from lkt_record as a left join lkt_user as b on a.user_id = b.user_id where $condition group by a.user_id ";
        $r = $db->select($sql);
        $total = 0;
        if($r){
            $b = array_unique($r,SORT_REGULAR);
            $sql = "select a.user_id,max(a.add_date) as t from lkt_record as a left join lkt_user as b on a.user_id = b.user_id where $condition group by a.user_id order by t desc limit $start,$pagesize";
            $r1 = $db->select($sql);
            $b1 = array_unique($r1,SORT_REGULAR);

            if($pageto == 'all') { // 导出全部
                $db->admin_record($admin_id,' 导出余额列表 ',4);
            }else if($pageto == 'ne'){ // 导出本页
                $db->admin_record($admin_id,' 导出余额列表第 '.$page.'页'.$pagesize.'条数据',4);
            }else{ // 不导出

            }
            foreach ($b1 as $k => $v){
                $user_id = $v->user_id;
                $sql = "select a.*,b.user_name,b.mobile,b.source from lkt_record as a left join lkt_user as b on a.user_id = b.user_id where $condition and a.user_id = '$user_id' order by a.add_date desc limit 1";
                $rr = $db->select($sql);
                if($rr){
                    $list[] = $rr[0];
                }
            }
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $url = "index.php?module=finance&action=yue&name=".urlencode($name)."&otype=".urlencode($type)."&starttime=".urlencode($starttime)."&group_end_time=".urlencode($group_end_time)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("name",$name);
        $request->setAttribute("type",$type);
        $request->setAttribute("list",$list);
        $request->setAttribute('pageto',$pageto);
        $request->setAttribute("starttime",$starttime);
        $request->setAttribute("group_end_time",$group_end_time);

        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('pagesize', $pagesize);
        
        return View :: INPUT;
    }

    public function execute() {

    }


    public function getRequestMethods(){
         return Request :: POST;
    }

}

?>