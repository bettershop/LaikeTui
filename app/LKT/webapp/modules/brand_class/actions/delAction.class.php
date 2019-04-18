<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $brand_id = intval($request->getParameter('cid')); // 品牌id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置
        $sql = "select * from lkt_brand_class where brand_id = '$brand_id'";
        $r = $db->select($sql);
        $brand_pic = $r[0]->brand_pic;
        @unlink ($uploadImg.$brand_pic);

        $sql = "select id from lkt_product_list where brand_id = '$brand_id'";
        $r = $db->select($sql);
        if($r){
            $db->admin_record($admin_id,' 删除商品品牌id为 '.$brand_id.' 失败',3);
            echo 2;
            exit;
        }
        // 根据分类id,删除这条数据
        $sql = "update lkt_brand_class set recycle = 1 and status = 1 where brand_id = '$brand_id'";
        $res = $db->update($sql);

		if($res > 0){
            $db->admin_record($admin_id,' 删除商品品牌id为 '.$brand_id.' 的信息',3);

            echo 1;
            exit;
		}else{
            $db->admin_record($admin_id,' 删除商品品牌id为 '.$brand_id.' 失败',3);
            echo 0;
            exit;
		};
    }

    public function execute(){
        return $this->getDefaultView();
    }

    public function getRequestMethods(){
        return Request :: NONE;
    }
}
?>