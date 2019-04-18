<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class uploadImgAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
       
        return View :: INPUT;
    }

    public function execute(){

        $request = $this->getContext()->getRequest();
        $db = DBAction::getInstance();
        
        // 查询配置表信息
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }

        $imgURL=($_FILES['file']['tmp_name']);
        $type = str_replace('image/', '.', $_FILES['file']['type']);
        $imgURL_name=time().mt_rand(1,1000).$type;
        move_uploaded_file($imgURL,$uploadImg.$imgURL_name);
        $do   = in_array($request->getParameter('do'), array('upload')) ? $request->getParameter('do') : 'upload';
        $type = in_array($request->getParameter('type'), array('image','audio')) ? $request->getParameter('type') : 'image';
        $ext = pathinfo($_FILES['file']['name'], PATHINFO_EXTENSION);
        $ext = strtolower($ext);
        $array = getimagesize($uploadImg.$imgURL_name);
        $fullname=$uploadImg.$imgURL_name;
        $size = getimagesize($fullname);
        $info = array(
            'name' => $_FILES['file']['name'],
            'ext' => $ext,
            'filename' => $imgURL_name,
            'attachment' => $imgURL_name,
            'url' => $img . $imgURL_name,
            'is_image' => 1,
            'filesize' => $size,
        );
        
        $info['width'] = $size[0];
        $info['height'] = $size[1];
        die(json_encode($info));
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}
?>