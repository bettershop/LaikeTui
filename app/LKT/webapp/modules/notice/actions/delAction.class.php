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
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置
        

        $sql = "select * from lkt_set_notice where id='$id'";
        $r = $db->select($sql);
        $image = $r[0]->img_url;
        @unlink ($uploadImg.$image); 
        $sql = "delete from lkt_set_notice where id='$id'";
        $res = $db -> delete($sql);

        if($res > 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('删除成功！');" .
                "location.href='index.php?module=notice';</script>";
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('删除失败！');" .
                "location.href='index.php?module=notice';</script>";

        }
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