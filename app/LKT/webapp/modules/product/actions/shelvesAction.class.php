<?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class shelvesAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $id = intval($request->getParameter("id")); // 商品id
        $url = $request->getParameter("url"); // 路径

        $sql = "select status from lkt_product_list where id = '$id'";
        $r = $db->select($sql);
        if($r[0]->status == 0){
            $sql = "update lkt_product_list set status = 1 where id = '$id'";
            $rr = $db->update($sql);
            if($rr > 0){
                $db->admin_record($admin_id,' 商品id为 '.$id.' 下架成功',3);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('下架成功！');" .
                    "location.href='index.php?module=product&action=$url';</script>";
                return;
            }else{
                $db->admin_record($admin_id,' 商品id为 '.$id.' 下架失败',3);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('下架失败！');" .
                    "location.href='index.php?module=product&action=$url';</script>";
                return;
            }
        }else{
            $sql = "update lkt_product_list set status = 0 where id = '$id'";
            $rr = $db->update($sql);
            if($rr > 0){
                $db->admin_record($admin_id,' 商品id为 '.$id.' 上架成功',3);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('上架成功！');" .
                    "location.href='index.php?module=product&action=$url';</script>";
                return;
            }else{
                $db->admin_record($admin_id,' 商品id为 '.$id.' 上架失败',3);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('上架失败！');" .
                    "location.href='index.php?module=product&action=$url';</script>";
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