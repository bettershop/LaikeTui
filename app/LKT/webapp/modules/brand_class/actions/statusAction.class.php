<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class statusAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $id = addslashes(trim($request->getParameter('id')));

        $sql = "select status from lkt_brand_class where brand_id = '$id'";
        $r = $db->select($sql);
        if($r){
            $status = $r[0]->status;
            if($status == 0){
                $sql = "select id from lkt_product_list where brand_id = '$id'";
                $r = $db->select($sql);
                if($r){
                    $db->admin_record($admin_id,'禁用品牌id为'.$id .' 失败',5);
                    echo 2;
                    exit;
                }
                $sql = "update lkt_brand_class set status = 1 where brand_id = '$id'";
                $db->update($sql);

                $db->admin_record($admin_id,'禁用品牌id为'.$id,5);

                echo 1;
                return;
            }else{
                $sql = "update lkt_brand_class set status = 0 where brand_id = '$id'";
                $db->update($sql);

                $db->admin_record($admin_id,'启用品牌id为'.$id,5);
                echo 1;
                return;
            }
        }
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>