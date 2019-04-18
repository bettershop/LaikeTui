<?php

/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action
{
    /*
    时间2018年03月13日
    修改内容：修改产品分类显示
    修改人：苏涛
    主要功能：处理后台所以产品显示
    公司：湖南壹拾捌号网络技术有限公司
    */
    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $name = addslashes(trim($request->getParameter('name'))); // 标题

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

        $condition = ' 1=1 ';
        if($name != ''){
            $condition .= " and name like '%$name%' ";
        }
        $sql = "select * from lkt_freight where $condition ";
        $r_pager = $db->select($sql);
        $total = count($r_pager);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select * from lkt_freight where $condition order by add_time desc limit $start,$pagesize";
        $r = $db->select($sql);
        if($r){
            $list = $r;
        }else{
            $list = [];
        }

        $url = "index.php?module=freight&action=Index&name=".urlencode($name)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("name", $name);
        $request->setAttribute("list", $list);
        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('pagesize', $pagesize);

        return View :: INPUT;
    }

    public function execute()
    {

    }

    public function getRequestMethods()
    {
        return Request :: NONE;
    }

}

?>