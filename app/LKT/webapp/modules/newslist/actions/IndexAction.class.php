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
        $cat_id = addslashes(trim($request->getParameter('cat_id'))); // 分类名称
        $news_title = addslashes(trim($request->getParameter('news_title'))); // 新闻标题

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        // 查询新闻分类
        $sql = "select * from lkt_news_class";
        $rr = $db->select($sql);

        $condition = ' 1=1 ';
        if($cat_id != ''){
            
            $condition .= " and news_class = '$cat_id' ";
        }
        
        if($news_title != ''){
            $condition .= " and news_title = '$news_title' ";
        }
        // 根据新闻类别id等于新闻分类id,查询新闻列表(新闻id、新闻类别、新闻标题、新闻排序、添加时间、分享次数),新闻分类列表(分类id,分类名称)
        $sql = 'select a.id,a.news_class,a.news_title,a.sort,a.add_date,a.share_num,m.cat_id AS mid,m.cat_name '.'from lkt_news_list'." AS a LEFT JOIN ".' lkt_news_class'." AS m  ON a.news_class = m.cat_id "." where $condition"." order by sort ";
        $r = $db->select($sql);

        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("news_title",$news_title);
        $request->setAttribute("class",$rr);
        $request->setAttribute("list",$r);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>