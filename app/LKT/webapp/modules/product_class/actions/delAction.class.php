<?php

/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action
{

    public function getDefaultView()
    {

    }

    public function execute()
    {
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
        $str_option = array();
        $num = 0;
        $str_option[$num] = $cid;
        if ($level >= 0) {
            $str_option = $this->a($cid);

        }
        $sta = 0;
        if ($str_option) {//查询分类下是否有商品，没有商品才可以删除
            foreach ($str_option as $key => $value) {
                $class = '-' . $value . '-';
                $r = $db->select("select id from lkt_product_list where product_class like '%$class%' and recycle = 0");
                if ($r) {
                    $sta = 1;
                }
            }

            if ($sta == 0) {
                $this->del($str_option);
                echo 1;
                return;
            } else {
                echo 2;
                return;
            }
        } else {
            echo 1;
            return;
        }


    }

    public function del($str_option)
    {
        foreach ($str_option as $k => $v) {
            $db = DBAction::getInstance();
            $request = $this->getContext()->getRequest();
            $admin_id = $this->getContext()->getStorage()->read('admin_id');
            $sql = "select * from lkt_product_class where cid = '$v'";
            $rr = $db->select($sql);
            $img = $rr[0]->img;
            $sql = "update lkt_product_class set recycle = 1 where cid = '$v'";
            $res = $db->update($sql);
            if ($res > 0) {
                @unlink($uploadImg . $img);
                $db->admin_record($admin_id, ' 删除商品分类id为 ' . $v . ' 的信息', 3);
            } else {
                $db->admin_record($admin_id, ' 删除商品分类id为 ' . $v . ' 失败', 3);
            }
        }
    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

    //递归找上级
    public function a($cid)
    {
        $db = DBAction::getInstance();
        $sqlc = "select cid from lkt_product_class where recycle = 0 and sid = '$cid'";;
        $res = $db->select($sqlc);
        $str_option[] = $cid;
        foreach ($res as $k => $v) {

            $str_option[] = $v->cid;
            $res1 = $this->b($v->cid);
            if ($res1) {
                foreach ($res1 as $k1 => $v1) {
                    $str_option[] = $v1->cid;
                    $res2 = $this->b($v1->cid);
                    if ($res2) {
                        foreach ($res2 as $k2 => $v2) {
                            $str_option[] = $v2->cid;
                            $res3 = $this->b($v2->cid);
                            if ($res3) {
                                foreach ($res3 as $k3 => $v3) {
                                    $str_option[] = $v3->cid;
                                    $res4 = $this->b($v3->cid);
                                    if ($res4) {
                                        foreach ($res4 as $k4 => $v4) {
                                            $str_option[] = $v4->cid;
                                            $res5 = $this->b($v4->cid);
                                            if ($res5) {
                                                foreach ($res5 as $k5 => $v5) {
                                                    $str_option[] = $v5->cid;
                                                    $res6 = $this->b($v5->cid);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return $str_option;
    }

    public function b($cid)
    {
        $db = DBAction::getInstance();
        $sqlc = "select cid from lkt_product_class where recycle = 0 and sid = '$cid'";;
        return $db->select($sqlc);
    }


}

