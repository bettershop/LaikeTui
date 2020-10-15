<?php

/**

 * [Laike System] Copyright (c) 2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */


class HomeAction extends PluginAction
{

    public function getDefaultView()
    {

        $request = $this->getContext()->getRequest();
        $username = $request->getParameter("username");
        $sNo = $request->getParameter("sNo");
        $starttime = $request->getParameter('startdate');//开始时间
        $endtime = $request -> getParameter('enddate');//结束时间
        $pagesize = $request->getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize : 10;
        // 每页显示多少条数据
        $page = $request->getParameter('page');
        if ($page) {
            $start = ($page - 1) * $pagesize;
        } else {
            $start = 0;
        }
        $condition = "  1=1 ";
        if ($username != '') {
            $condition .= " and userid like '%$username%' ";
        }

        if($sNo){
            $condition .= " and sNo = '$sNo' ";
        }

        if($starttime){
            $condition .= " and addtime >= '$starttime' ";
        }

        if($endtime){
            $condition .= " and addtime <= '$endtime' ";
        }


        $db = DBAction::getInstance();
        $sql = "select count(id) cn  from lkt_detailed_commission  ";
        $r = $db->select($sql);
        $total = $r[0]->cn;
        $sql = "select * from lkt_detailed_commission where $condition  order by id desc limit $start,$pagesize";
        $list = $db->select($sql);

        $pager = new ShowPager($total, $pagesize, $page);
        $url = "index.php?module=pi&p=score&c=Home&username=$username&sNo=$sNo&pagesize=" . urlencode($pagesize);
        $pages_show = $pager->multipage($url, $total, $page, $pagesize, $start, $para = '');
        $request->setAttribute("list", $list);
        $request->setAttribute("sNo", $sNo);
        $request->setAttribute("starttime", $starttime);
        $request->setAttribute("endtime", $endtime);
        $request->setAttribute("username", $username);
        $request->setAttribute("pages_show", $pages_show);
        return View::INPUT;
    }

    public function del(){
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id');
        $db = DBAction::getInstance();
        $res = $db->preUpdate("delete from lkt_detailed_commission where id =  ?",array($id));
        if ($res > 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('删除成功！');" .
                "location.href='index.php?module=pi&p=score&c=Home';</script>";
            exit();
        }
    }


    public function execute()
    {
    }

    public function getRequestMethods()
    {
        return Request::NONE;
    }
}
