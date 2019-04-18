<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class member_recordAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $pageto = $request->getParameter('pageto'); // 导出
        $pagesize = $request->getParameter('pagesize'); // 每页显示多少条数据
        $page = $request->getParameter('page'); // 页码

        $admin_name = $request->getParameter('admin_name'); // 管理员账号
        $startdate = $request->getParameter("startdate");
        $enddate = $request->getParameter("enddate");

        $pageto = $request -> getParameter('pageto');
        // 导出
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

        $condition = ' 1=1';
        if($startdate != ''){
            $condition .= " and add_time >= '$startdate 00:00:00' ";
        }
        if($enddate != ''){
            $condition .= " and add_time <= '$enddate 23:59:59' ";
        }
        if($admin_name != ''){
            $condition .= " and admin_name = '$admin_name' ";
        }
        $sql = "select * from lkt_admin_record where $condition order by add_date desc";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select * from lkt_admin_record where $condition order by add_date desc limit $start,$pagesize";
        $r = $db->select($sql);

        if($pageto == 'ne') {
            $db->admin_record($admin_id,'导出管理员记录表第'.$page.'数据',4);
        }else if($pageto == 'all'){
            $db->admin_record($admin_id,'导出管理员记录表全部数据',4);
        }
        $url = "index.php?module=member&action=member_record&admin_name=".urlencode($admin_name)."&startdate=".urlencode($startdate)."&enddate=".urlencode($enddate);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$r);
        $request->setAttribute("admin_name",$admin_name);
        $request->setAttribute("startdate",$startdate);
        $request->setAttribute("enddate",$enddate);
        $request->setAttribute('pageto',$pageto);
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