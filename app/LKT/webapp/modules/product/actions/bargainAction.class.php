<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class bargainAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter("id")); // 商品id
        $sx_id = intval($request->getParameter("sx_id")); // 属性id
        $status = intval($request->getParameter("status")); // 状态
        $bargain_price = intval($request->getParameter("bargain_price")); // 砍价价格

        $sql = "select * from lkt_bargain_config where id = 1";
        $r_1 = $db->select($sql);
        $can_num = $r_1[0]->can_num; // 能砍的次数
        $parameter = $r_1[0]->parameter; // 每次砍价的参数

        $sql = "select price,status from lkt_configure where pid = '$id' and id = '$sx_id'";
        $r = $db->select($sql);
        $price = $r[0]->price;

        $request->setAttribute("can_num",$can_num);
        $request->setAttribute("parameter",$parameter);
        $request->setAttribute("price",$price);

        if($status == 1){
            if($bargain_price){
                $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
                $request->setAttribute('id', $id);
                $request->setAttribute('sx_id', $sx_id);
                $request->setAttribute("product_title",$product_title);
                $request->setAttribute("bargain_price",$bargain_price);
                return View :: INPUT;
            }else{
                $sql = "update lkt_configure set bargain_price = 0,status = 0 where pid = '$id' and id = '$sx_id'";
                $db->update($sql); 
                echo "<script type='text/javascript'>" .
                    "alert('砍价功能关闭!');" .
                    "history.go(-1);</script>";
                return;
            }
        }else{
            $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题

            $request->setAttribute('id', $id);
            $request->setAttribute('sx_id', $sx_id);
            $request->setAttribute("product_title",$product_title);
            return View :: INPUT;
        }
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter("id")); // 商品id
        $sx_id = intval($request->getParameter("sx_id")); // 属性id
        $bargain_price = addslashes(trim($request->getParameter('bargain_price'))); // 砍价初始值
        
        $sql = "select status from lkt_configure where pid = '$id' and id = '$sx_id'";
        $r = $db->select($sql);
        $status = $r[0]->status;

        $sql = "update lkt_configure set bargain_price = '$bargain_price',status = 1 where pid = '$id' and id = '$sx_id'";
        $rr = $db->update($sql);

        if($rr > 0 && $status == 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('砍价开启成功!');" .
                "parent.location.reload();</script>";
        }else if($rr > 0 && $status == 1){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('砍价修改成功!');" .
                "parent.location.reload();</script>";
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因,砍价开启失败!');" .
                "parent.location.reload();</script>";
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>