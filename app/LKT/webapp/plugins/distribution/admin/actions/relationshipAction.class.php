<?php

/**
 * [Laike System] Copyright (c) 2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class relationshipAction extends PluginAction
{

    public function getDefaultView()
    {
        $request = $this->getContext()->getRequest();
        $username = $request->getParameter("username");
        $wx_id = $request->getParameter("wx_id"); // 推荐人ID

        $pagesize = $request->getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize : 10;
        // 每页显示多少条数据
        $page = $request->getParameter('page');
        if ($page) {
            $start = ($page - 1) * $pagesize;
        } else {
            $start = 0;
        }
        $data = array();
        $res1 = array();
        //如果名字不为空
        $condition = "  1=1 ";
        if ($username != '') {
            $condition .= " and (a.user_name like '%$username%' or a.user_id like '%$username%') ";
        }

        if ($wx_id  || $wx_id == '0') {
            $data['Referee'] = $wx_id;
            $condition .= " and a.Referee like '%$wx_id%' ";
        }


        $db = DBAction::getInstance();
        $res = $db->select("select a.*,b.wx_name parent_name from lkt_user AS a left join lkt_user AS b ON a.Referee = b.user_id where  " . $condition . " order by a.id desc  ");

        $total = count($res);

        if ($res) {
            $res1 = array_slice($res, $start, $pagesize); //分页
        }
        $pager = new ShowPager($total, $pagesize, $page);
        $url = "index.php?module=pi&p=distribution&c=relationship&username=$username&wx_id=$wx_id&pagesize=" . urlencode($pagesize);
        $pages_show = $pager->multipage($url, $total, $page, $pagesize, $start, $para = '');
        $request->setAttribute("list", $res1);
        $request->setAttribute("wx_id", $wx_id);
        $request->setAttribute("username", $username);
        $request->setAttribute("pages_show", $pages_show);
        return View::INPUT;
    }


    public function execute()
    {
    }

    public function getRequestMethods()
    {
        return Request::POST;
    }
}
