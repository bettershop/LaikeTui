<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once (MO_LIB_DIR . '/DBAction.class.php');
require_once (MO_LIB_DIR . '/ShowPager.class.php');
require_once (MO_LIB_DIR . '/Tools.class.php');

class jifen_seeAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this -> getContext() -> getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $user_id = $request->getParameter('user_id'); // 用户id
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

        $sql1 = "select a.*,b.user_name,b.mobile,b.source from lkt_sign_record as a left join lkt_user as b on a.user_id = b.user_id where a.user_id = '$user_id' order by a.sign_time desc";
        $r_total = $db -> select($sql1);
        $total = count($r_total);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql1 = "select a.*,b.user_name,b.mobile,b.source from lkt_sign_record as a left join lkt_user as b on a.user_id = b.user_id where a.user_id = '$user_id' order by a.sign_time desc limit $start,$pagesize";
        $r = $db -> select($sql1);

        $url = "index.php?module=finance&action=jifen_see&user_id=".urlencode($user_id)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$r);
        $request -> setAttribute('pages_show', $pages_show);

        return View::INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods() {
        return Request::NONE;
    }

}
?>