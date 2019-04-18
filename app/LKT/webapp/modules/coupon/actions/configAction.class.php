<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class configAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $plug_ins_id = intval($request->getParameter("id")); // 插件id
        $software_id = intval($request->getParameter("software_id")); // 软件id
        
        $sql = "select * from lkt_coupon_config where plug_ins_id = '$plug_ins_id'";
        $r = $db->select($sql);
        if($r){
            $activity_overdue = $r[0]->activity_overdue;
//            $coupon_validity = $r[0]->coupon_validity;
            $coupon_overdue = $r[0]->coupon_overdue;
        }else{
            $activity_overdue = 2;
//            $coupon_validity = 7;
            $coupon_overdue = 2;
        }
        $request->setAttribute('plug_ins_id', $plug_ins_id);
        $request->setAttribute('software_id', $software_id);
        $request->setAttribute('activity_overdue', $activity_overdue);
//        $request->setAttribute('coupon_validity', $coupon_validity);
        $request->setAttribute('coupon_overdue', $coupon_overdue);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $plug_ins_id = intval($request->getParameter('plug_ins_id'));
        $software_id = intval($request->getParameter('software_id'));
        $activity_overdue = addslashes(trim($request->getParameter('activity_overdue'))); // 优惠券活动删除日期
//        $coupon_validity = addslashes(trim($request->getParameter('coupon_validity'))); // 优惠券有效期
        $coupon_overdue = addslashes($request->getParameter('coupon_overdue')); // 优惠券过期时间
        if(is_numeric($activity_overdue) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('优惠券活动删除日期请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($activity_overdue < 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('优惠券活动删除日期不能为负数!');" .
                "</script>";
            return $this->getDefaultView();
        }
//        if(is_numeric($coupon_validity) == ''){
//            header("Content-type:text/html;charset=utf-8");
//            echo "<script type='text/javascript'>" .
//                "alert('优惠券有效期请输入数字!');" .
//                "</script>";
//            return $this->getDefaultView();
//        }
//        if($coupon_validity <= 0){
//            header("Content-type:text/html;charset=utf-8");
//            echo "<script type='text/javascript'>" .
//                "alert('优惠券有效期不能为负数或0!');" .
//                "</script>";
//            return $this->getDefaultView();
//        }
        if(is_numeric($coupon_overdue) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('优惠券过期时间请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($coupon_overdue < 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('优惠券过期时间不能为负数!');" .
                "</script>";
            return $this->getDefaultView();
        }
        $sql = "select * from lkt_coupon_config where plug_ins_id = $plug_ins_id";
        $r = $db->select($sql);
        if($r){
//            $sql = "update lkt_coupon_config set software_id = '$software_id',activity_overdue = '$activity_overdue',coupon_validity = '$coupon_validity',coupon_overdue = '$coupon_overdue',modify_date = CURRENT_TIMESTAMP where plug_ins_id = '$plug_ins_id'";
            $sql = "update lkt_coupon_config set software_id = '$software_id',activity_overdue = '$activity_overdue',coupon_overdue = '$coupon_overdue',modify_date = CURRENT_TIMESTAMP where plug_ins_id = '$plug_ins_id'";
            $r_1 = $db->update($sql);
            if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，优惠券参数修改失败！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            } else {
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('优惠券参数修改成功！');" .
                    "location.href='index.php?module=plug_ins';</script>";
            }
        }else{
//            $sql = "insert into lkt_coupon_config(software_id,plug_ins_id,activity_overdue,coupon_validity,coupon_overdue,modify_date) values('$software_id','$plug_ins_id','$activity_overdue','$coupon_validity','$coupon_overdue',CURRENT_TIMESTAMP)";
            $sql = "insert into lkt_coupon_config(software_id,plug_ins_id,activity_overdue,modify_date) values('$software_id','$plug_ins_id','$activity_overdue',CURRENT_TIMESTAMP)";
            $r_1 = $db->insert($sql);
            if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，优惠券参数添加失败！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            } else {
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('优惠券参数添加成功！');" .
                    "location.href='index.php?module=plug_ins';</script>";
            }
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>