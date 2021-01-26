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
        $id = intval($request->getParameter('id')); // 插件id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径
        $upload_file = addslashes(trim($request->getParameter('upload_file'))); // 图片路径
        $sql = "select * from lkt_software where id = '$id'";
        $r = $db->select($sql);
		$image = $r[0]->image;

        @unlink ($uploadImg.$image);
        @unlink ($upload_file. '/' .$r[0]->edition_url);
        $sql = "delete from lkt_software where id = '$id'";
        $res=$db->delete($sql);
        echo $res;
        exit;

    }



    public function execute(){

        return $this->getDefaultView();

    }



    public function getRequestMethods(){

        return Request :: NONE;

    }



}



?>