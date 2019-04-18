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
        $name = addslashes(trim($request->getParameter('name'))); // 姓名
        $startdate = $request->getParameter('startdate'); // 开始时间
        $enddate = $request->getParameter('enddate'); // 结束日期
        
        $condition = ' status != 3 ';
        if($name != ''){ 
            $condition .= " and name = '$name' ";
        }
        if($startdate != ''){ // 查询开始日期不为空
            $condition .= " and add_date >= '$startdate 00:00:00' ";
        }
        if($enddate != ''){ // 查询结束日期不为空
            $condition .= " and add_date <= '$enddate 23:59:59' ";
        }
        
        $sql = "select * from lkt_experience where $condition";
        $r = $db->select($sql);

        $request->setAttribute("startdate",$startdate);
        $request->setAttribute("enddate",$enddate);
        $request->setAttribute("name",$name);
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