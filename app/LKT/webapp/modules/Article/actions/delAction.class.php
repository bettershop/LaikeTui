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
        // 根据文章id,查询文章
        $sql = "select * from lkt_article where Article_id = '$id'";
        $r = $db->select($sql);
        $Article_imgurl = $r[0]->Article_imgurl;
        @unlink ($uploadImg.$Article_imgurl);
        // 根据文章id，删除新闻信息
        $sql = "delete from lkt_article where Article_id = '$id'";
        $db->delete($sql);
        header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('删除成功！');" .
            "location.href='index.php?module=Article';</script>";
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