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
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        }

        $sql = "select a.id,a.name,a.add_date,a.money,a.s_charge,a.mobile,a.status,b.Cardholder,b.Bank_name,b.Bank_card_number,b.mobile,c.source from lkt_withdraw as a left join lkt_user_bank_card as b on a.Bank_id = b.id right join lkt_user as c on a.user_id = c.user_id where a.user_id = '$user_id' order by a.add_date desc";
        $r_total = $db->select($sql);
        $total = count($r_total);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select a.id,a.name,a.add_date,a.money,a.s_charge,a.mobile,a.status,b.Cardholder,b.Bank_name,b.Bank_card_number,b.mobile,c.source from lkt_withdraw as a left join lkt_user_bank_card as b on a.Bank_id = b.id right join lkt_user as c on a.user_id = c.user_id where a.user_id = '$user_id' order by a.add_date desc limit $start,$pagesize";
        $r = $db->select($sql);

        $url = "index.php?module=finance&action=see&user_id=".urlencode($user_id)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$r);
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