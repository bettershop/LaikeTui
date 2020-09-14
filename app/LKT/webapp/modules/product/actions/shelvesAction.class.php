<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
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
              $sa= $db->select("select id from lkt_group_product where product_id = $id and recycle = 0 and g_status < 3");//查询该商品是否正在参加拼团活动
                if($sa){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('该商品有参与插件活动，无法下架！');" .
                        "location.href='index.php?module=product&action=$url';</script>";
                    return;
                }

                 $saa = $db->select("select id from lkt_order_details where p_id = $id and (r_status = 1 or r_status = 2 or r_status = 4 or r_status = 9 or r_status = 10 )");//查询该商品是否有未完成订单

                if($saa){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('此商品有未完成订单，无法下架');" .
                        "location.href='index.php?module=product&action=$url';</script>";
                    return;
                }

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