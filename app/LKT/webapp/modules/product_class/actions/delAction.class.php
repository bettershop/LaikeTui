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

    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 获取分类id
        $cid = intval($request->getParameter('cid'));
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径


        // 根据分类id,查询产品分类表
        $sql = "select * from lkt_product_class where cid = '$cid'";
        $r = $db->select($sql);
        $level = $r[0]->level;
        $cid_r = $r[0]->cid;
        $str_option = [];
        $num = 0;
        $str_option[$num] = $cid;
        if($level >= 0){
            $str_option = $this->str_option($level,$cid,$str_option,$num);
        }
        foreach ($str_option as $k => $v){
            $sql = "select * from lkt_product_class where cid = '$v'";
            $rr = $db->select($sql);
            $img = $rr[0]->img;
            $sql = "update lkt_product_class set recycle = 1 where cid = '$v'";
            $res = $db->update($sql);
            if($res > 0){
                @unlink ($uploadImg.$img);
                $db->admin_record($admin_id,' 删除商品分类id为 '.$v.' 的信息',3);
            }else{
                $db->admin_record($admin_id,' 删除商品分类id为 '.$v.' 失败',3);
            }
        }
        echo 1;
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    //递归找上级
    public function str_option($level,$cid,$str_option,$num){
        $db = DBAction::getInstance();

        $sqlc = "select cid from lkt_product_class where recycle = 0 and sid = '$cid'";;
        $res = $db->select($sqlc);
        if($res){
            foreach ($res as $k => $v){
                $num++;
                $str_option[$num] = $v->cid;
                $this->str_option($level,$v->cid,$str_option,$num);
            }
            return $str_option;
        }else{
            return $str_option;
        }
    }
}
?>