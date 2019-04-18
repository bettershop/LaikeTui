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
        $id = intval($request->getParameter('id')); // 轮播图id
        $yimage = addslashes(trim($request->getParameter('yimage'))); // 原图片路径带名称
        $uploadImg = substr($yimage,0,strripos($yimage, '/')) . '/'; // 图片路径
        $sql = "select * from lkt_banner where id = '$id'";
        $r = $db->select($sql);
        $image = $r[0]->image;
        @unlink ($uploadImg.$image);
        // 根据轮播图id，删除轮播图信息
        $sql = "delete from lkt_banner where id = '$id'";
        $res=$db->delete($sql);		echo $res;exit;		
        header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('删除成功！');" .
            "location.href='index.php?module=banner';</script>";
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