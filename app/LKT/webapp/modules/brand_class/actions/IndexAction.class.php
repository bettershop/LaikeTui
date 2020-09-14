<?php
/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $uploadImg = $this->getContext()->getStorage()->read('uploadImg');
        // 导出
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:10;
        // 每页显示多少条数据
        $page = $request -> getParameter('page');

        // 页码
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
            $page =1;
        }
        $sql = "select * from lkt_brand_class where recycle = 0 ";
        $r_pager = $db->select($sql);
        if($r_pager){
            $total = count($r_pager);
        }else{
            $total = 0;
        }
        $pager = new ShowPager($total,$pagesize,$page);

        // 查询新闻分类表，根据sort顺序排列
        $sql = "select * from lkt_brand_class where recycle = 0 order by sort asc, brand_time desc limit $start,$pagesize ";
        $r = $db->select($sql);

        $url = "index.php?module=brand_class&action=Index&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("list",$r);
        $request -> setAttribute('pages_show', $pages_show);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }
}
?>