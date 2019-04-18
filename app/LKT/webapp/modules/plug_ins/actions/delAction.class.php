<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = intval($request->getParameter('id')); // 插件id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径

        // 根据插件id,查询插件
        $sql = "select * from lkt_plug_ins where id = '$id'";
        $r = $db->select($sql);
        $image = $r[0]->image;
        $subtitle_image = $r[0]->subtitle_image;
        @unlink ($uploadImg.$image);
        @unlink ($uploadImg.$subtitle_image);
        // 根据轮播图id，删除轮播图信息
        $sql = "delete from lkt_plug_ins where id = '$id'";
        $res = $db->delete($sql);

        $db->admin_record($admin_id,' 删除插件id为 '.$id.' 的信息',3);
		echo $res;exit;
        header("Content-type:text/html;charset=utf-8");
        echo "<script type='text/javascript'>" .
            "alert('删除成功！');" .
            "location.href='index.php?module=plug_ins';</script>";
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