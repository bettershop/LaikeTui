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
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $name = addslashes(trim($request->getParameter('name'))); // 用户名
        $tel = addslashes(trim($request->getParameter('tel'))); // 联系电话
        $source = addslashes(trim($request->getParameter('source'))); // 来源
		$startdate = $request->getParameter("startdate");
		$enddate = $request->getParameter("enddate");
        // 导出
        $pageto = $request -> getParameter('pageto');
        // 每页显示多少条数据
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:'10';
        // 页码
        $page = $request -> getParameter('page');
        if($page){
            $page = $page;
            $start = ($page-1)*10;
        }else{
            $page = 1;
            $start = 0;
        }
		$condition = '';
		if($name != ''){
			$condition .= " and user_name like '%$name%' ";
		}
		
		if($tel != ''){
			$condition .= " and mobile = '$tel' ";
		}
		if($source != 0){
            $condition .= " and source = '$source' ";
        }
		
		if($startdate != ''){
			$condition .= "and Register_data >= '$startdate 00:00:00' ";
		}
		if($enddate != ''){
			$condition .= "and Register_data <= '$enddate 23:59:59' ";
		}


        $sql = "select * from lkt_user where 1=1 " . $condition;
        $r_pager = $db->select($sql);

		$total = count($r_pager);
        $pager = new ShowPager($total, $pagesize, $page);
		$offset = $pager -> offset;

		if($pageto == 'all'){
            $db->admin_record($admin_id,' 导出用户列表全部数据 ',4);
            $sql = "select * from lkt_user where 1=1 " . $condition . "order by Register_data desc";

        }else if($pageto == 'ne'){
            $db->admin_record($admin_id,' 导出用户列表第'.$page.'页'.$pagesize.'条数据 ',4);
            $sql = "select * from lkt_user where 1=1 " . $condition . "order by Register_data desc limit $offset,$pagesize";
        }else{
            $sql = "select * from lkt_user where 1=1 " . $condition . "order by Register_data desc limit $offset,$pagesize";
        }
        $r = $db -> select($sql);

		//查询订单数
		foreach ($r as $key => $value) {
	        $sql = "select SUM(z_price) as z_price from lkt_order where user_id='$value->user_id' ";
	        $r1 = $db->select($sql);
	        if($r1[0]->z_price){
	        	$r[$key]->z_price = $r1[0]->z_price;
	        }else{
	        	$r[$key]->z_price = 0;
	        }
	        $sql = "select id from lkt_order where user_id='$value->user_id' ";
	        $r[$key]->z_num = $db->selectrow($sql);
		}

        $url = "index.php?module=userlist&action=Index&name=".urlencode($name)."&tel=".urlencode($tel)."&source=".urlencode($source)."&startdate=".urlencode($startdate)."&enddate=".urlencode($enddate);
        // $pages_show = $pager->multipage($url,ceil($total/$pagesize),$page, $para = '');
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

		$request -> setAttribute('pageto', $pageto);
		$request->setAttribute("name",$name);
        $request->setAttribute("tel",$tel);
        $request->setAttribute("source",$source);
        $request->setAttribute("list",$r);
		$request->setAttribute('startdate',$startdate);
		$request->setAttribute('enddate',$enddate);
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