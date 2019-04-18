<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class yue_seeAction extends Action {

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

        $sql = "select a.*,b.user_name,b.mobile,b.source from lkt_record as a left join lkt_user as b on a.user_id = b.user_id where a.type !=0 and a.type !=6 and a.type !=7 and a.type !=8 and a.type !=9 and a.type !=10 and a.type !=15 and a.type !=16 and a.type !=17 and a.type !=18 and a.user_id = '$user_id' order by a.add_date desc ";
        $r_total = $db -> select($sql);
        $total = count($r_total);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select a.*,b.user_name,b.mobile,b.source from lkt_record as a left join lkt_user as b on a.user_id = b.user_id where a.type !=0 and a.type !=6 and a.type !=7 and a.type !=8 and a.type !=9 and a.type !=10 and a.type !=15 and a.type !=16 and a.type !=17 and a.type !=18 and a.user_id = '$user_id' order by a.add_date desc limit $start,$pagesize";
        $r = $db->select($sql);

        $url = "index.php?module=finance&action=yue_see&user_id=".urlencode($user_id)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$r);
        $request -> setAttribute('pages_show', $pages_show);
        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
         return Request :: POST;
    }

}

?>