<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        return;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter('id')); // 插件id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置
        

        $sql = "select * from lkt_sign_activity where id=".$id;
        $r = $db->select($sql);
        $image = $r[0]->image;
        @unlink ($uploadImg.$image);
        
        $sql = 'delete from lkt_sign_activity where id='.$id;
        $res = $db -> delete($sql);
        if($res > 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('删除成功！');" .
                "location.href='index.php?module=sign';</script>";
            return;
        }
        
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>