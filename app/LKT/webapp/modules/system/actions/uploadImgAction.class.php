<?php
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
        $sql = "select * from lkt_config where 1=1 ";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg;  // 图片上传位置
        
        if(empty($uploadImg)){
            $uploadImg = "../LKT/images";
        }
        if(is_dir($uploadImg) == ''){ // 如果文件不存在
            mkdir($uploadImg); // 创建文件
        }
        $error = $_FILES['imgFile']['error'];
            switch($_FILES['imgFile']['error']){
            case 0: $msg = ''; break;
            case 1: $msg = '超出了php.ini中文件大小'; break;
            case 2: $msg = '超出了MAX_FILE_SIZE的文件大小'; break;
            case 3: $msg = '文件被部分上传'; break;
            case 4: $msg = '没有文件上传'; break;
            case 5: $msg = '文件大小为0'; break;
            default: $msg = '上传失败'; break;
        }

        $upload_max_filesize = ini_get('upload_max_filesize');
        $imgURL=($_FILES['imgFile']['tmp_name']);
        $type = str_replace('image/', '.', $_FILES['imgFile']['type']);
        $imgURL_name=time().mt_rand(1,1000).$type;
        move_uploaded_file($imgURL,$uploadImg.$imgURL_name);
        $image = $uploadImg . $imgURL_name;
        echo json_encode(array("error"=>$error,"url"=>$image,'message'=>$msg));
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}
?>