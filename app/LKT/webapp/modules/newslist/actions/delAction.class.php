<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter('id')); // 新闻id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 原图片路径带名称
        $uploadImg = substr($uploadImg,0,strripos($uploadImg, '/')) . '/'; // 图片路径
        // 根据新闻id,查询新闻
        $sql = "select * from lkt_news_list where id = '$id'";
        $r = $db->select($sql);
        $imgurl = $r[0]->imgurl;
        $t_link = $r[0]->t_link;
        @unlink ($uploadImg.$imgurl);
        @unlink ($uploadImg.$t_link);
        // 根据新闻id，删除新闻信息
        $sql = "delete from lkt_news_list where id = '$id'";
        $db->delete($sql);
        header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('删除成功！');" .
            "location.href='index.php?module=newslist';</script>";
        return;
    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>