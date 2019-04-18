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

        $id = intval($request->getParameter('id')); // 插件id

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径

        $upload_file = addslashes(trim($request->getParameter('upload_file'))); // 图片路径



        // 根据插件id,查询插件

        $sql = "select * from lkt_software where id = '$id'";

        $r = $db->select($sql);
		
        $image = $r[0]->image;

        // $subtitle_image = $r[0]->subtitle_image;

        $edition_url = $r[0]->edition_url;

        @unlink ($uploadImg.$image);

        @unlink ($upload_file. '/' .$rrr[0]->edition_url);

        // 根据轮播图id，删除轮播图信息

        $sql = "delete from lkt_software where id = '$id'";

        $res=$db->delete($sql);
        echo $res;exit;

        header("Content-type:text/html;charset=utf-8");

        echo "<script type='text/javascript'>" .

            "alert('删除成功！');" .

            "location.href='index.php?module=software';</script>";

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