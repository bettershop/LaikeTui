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

        $p_name = addslashes(trim($request->getParameter('p_name'))); // 产品名称
        $startdate = $request->getParameter("startdate");
        $enddate = $request->getParameter("enddate");
        $pageto = $request->getParameter('pageto'); // 导出
        $sort_name = $request->getParameter('sort_name'); // 排序名称
        $sort = $request->getParameter('sort'); // 升/降

        $r_type = trim($request->getParameter('r_type'));




        $condition = ' r_status = 4 ';
        if($p_name != ''){
            $condition .= " and r_sNo like '%$p_name%' ";
        }
        if($r_type){

            if($r_type ==1){
                 $condition .= " and r_type = '0' ";
            }else if($r_type ==2){
                 $condition .= " and (r_type = '1' OR r_type = '6') ";
            }else if($r_type ==3){
                 $condition .= " and (r_type = '2' OR r_type = '8') ";
            }else if($r_type ==4){
                 $condition .= " and r_type = '3' ";
            }else if($r_type ==5){
                 $condition .= " and (r_type = '4' OR r_type = '9') ";
            }else{
                 $condition .= " and r_type = '5' ";
            }
        }


        if($startdate != ''){
            $condition .= "and arrive_time >= '$startdate 00:00:00' ";
        }
        if($enddate != ''){
            $condition .= "and arrive_time <= '$enddate 23:59:59' ";
        }


        $con = '';
        foreach ($_GET as $key => $value001) {
            $con .= "&$key=$value001";
        }
        // 查询插件表
        $sql1 = "select * from lkt_order_details where $condition";
        $total = $db->selectrow($sql1);
        // 导出
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:10;
        // 页码
        $page = $request -> getParameter('page');
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        }
        // $sql .= " order by add_time desc limit $start,$pagesize ";
        // $r = $db->select($sql);

        $pager = new ShowPager($total,$pagesize,$page);
        $url = 'index.php?module=return'.$con;
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');


        if($pageto == 'all') { // 导出全部
            $sql = "select * from lkt_order_details where $condition order by $sort_name $sort ";
            $r = $db->select($sql);
        }else if($pageto == 'ne'){// 导出本页
            $sql = "select * from lkt_order_details where $condition order by $sort_name $sort limit $start,$pagesize ";
            $r = $db->select($sql);
        }else{
            $sql = "select * from lkt_order_details where $condition limit $start,$pagesize ";
            $r = $db->select($sql);
        }
        $request->setAttribute("pages_show",$pages_show);
        $request->setAttribute("r_type",$r_type);
        $request->setAttribute("p_name",$p_name);
        $request->setAttribute("startdate",$startdate);
        $request->setAttribute("enddate",$enddate);
        $request->setAttribute("list",$r);
        $request->setAttribute('pageto',$pageto);
        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>