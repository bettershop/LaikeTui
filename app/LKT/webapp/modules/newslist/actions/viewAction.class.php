<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class viewAction extends Action {

    public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter("id"));
        // 根据新闻id，查询新闻标题
        $sql ="select news_title a from lkt_news_list where id = $id";
        $r = $db->select($sql);
        $news_title = $r[0]->a;
        // 根据新闻id，查询分享列表
        $sql1 = "select * from lkt_share where Article_id = $id";
        $rr = $db->select($sql1);
        // 根据新闻id，查询总条数
        $sql2 = "select count(id) c from lkt_share where Article_id = $id";
        $rrr = $db->select($sql2);
        $total = $rrr[0]->c;
        if($total == ''){
            $total = 0;
        }

        $request->setAttribute("news_title",$news_title);
        $request->setAttribute("total",$total);
        $request->setAttribute("list",$rr);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }
}
?>