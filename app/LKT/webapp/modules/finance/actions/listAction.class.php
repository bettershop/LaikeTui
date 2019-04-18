<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class listAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $name = addslashes(trim($request->getParameter('name'))); // 用户名
        $Bank_card_number = addslashes(trim($request->getParameter('Bank_card_number'))); // 卡号
        $Cardholder = addslashes(trim($request->getParameter('Cardholder'))); // 持卡人姓名

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

		$condition = 'a.status = 1';
		if($name){
			$condition .= " and a.name = '$name' ";
		}
        if($Bank_card_number != ''){
            $condition .= " and b.Bank_card_number like '%$Bank_card_number%' ";
        }
        if($Cardholder != ''){
            $condition .= " and b.Cardholder = '$Cardholder' ";
        }
        $list = array();
        $sql = "select user_id from lkt_withdraw where status = 1 ";
        $r = $db->select($sql);
        $total = 0;
        if($r){
            $b = array_unique($r,SORT_REGULAR);
            $total = count($b);

            $sql = "select user_id,max(add_date) as t from lkt_withdraw where status = 1 group by user_id order by t desc limit $start,$pagesize";
            $r1 = $db->select($sql);
            $b1 = array_unique($r1,SORT_REGULAR);
            foreach ($b1 as $k => $v){
                $user_id = $v->user_id;
                $sql = "select a.id,a.user_id,a.name,a.add_date,a.money,a.s_charge,a.mobile,a.status,b.Cardholder,b.Bank_name,b.Bank_card_number,b.mobile,c.source from lkt_withdraw as a left join lkt_user_bank_card as b on a.Bank_id = b.id right join lkt_user as c on a.user_id = c.user_id where $condition and a.user_id = '$user_id' order by a.add_date desc limit 1";
                $rr = $db->select($sql);
                if($rr){
                    $list[] = $rr[0];
                }
            }
            if($pageto == 'all') { // 导出全部
                $db->admin_record($admin_id,' 导出提现通过列表 ',4);
            }else if($pageto == 'ne'){ // 导出本页
                $db->admin_record($admin_id,' 导出提现通过列表第 '.$page.'页'.$pagesize.'条数据',4);
            }else{ // 不导出

            }
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $url = "index.php?module=finance&action=list&name=".urlencode($name)."&Bank_card_number=".urlencode($Bank_card_number)."&Cardholder=".urlencode($Cardholder)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

		$request->setAttribute("name",$name);
        $request->setAttribute("Bank_card_number",$Bank_card_number);
        $request->setAttribute("Cardholder",$Cardholder);
        $request->setAttribute("list",$list);
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