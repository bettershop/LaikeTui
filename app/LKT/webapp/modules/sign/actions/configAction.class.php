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
        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $sql = "select * from lkt_sign_config where plug_ins_id = '$plug_ins_id'";
        $r = $db->select($sql);
        if($r){
            $imgurl = $r[0]->imgurl; // 签到图片
            $min_score = $r[0]->min_score; // 领取的最少积分
            $max_score = $r[0]->max_score; //领取的最大积分
            $continuity_three = $r[0]->continuity_three; // 连续签到7天
            $continuity_twenty = $r[0]->continuity_twenty; // 连续签到20天
            $continuity_thirty = $r[0]->continuity_thirty; // 连续签到30天
            $activity_overdue = $r[0]->activity_overdue; // 活动过期删除时间
        }else{
            $imgurl = ''; // 签到图片
            $min_score = 1;
            $max_score = 10;
            $continuity_three = 5;
            $continuity_twenty = 10;
            $continuity_thirty = 20;
            $activity_overdue = 2;
        }
        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute('plug_ins_id', $plug_ins_id);
        $request->setAttribute('imgurl', isset($imgurl) ? $imgurl : '');
        $request->setAttribute('min_score', $min_score);
        $request->setAttribute('max_score', $max_score);
        $request->setAttribute('continuity_three', $continuity_three);
        $request->setAttribute('continuity_twenty', $continuity_twenty);
        $request->setAttribute('continuity_thirty', $continuity_thirty);
        $request->setAttribute('activity_overdue', $activity_overdue);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $plug_ins_id = intval($request->getParameter('plug_ins_id'));
        // $imgurl= addslashes($request->getParameter('imgurl')); // 活动图片
        $imgurl = addslashes($request->getParameter('imgurl')); // 新活动图片
        $oldpic= addslashes($request->getParameter('oldpic')); // 原活动图片
        $min_score = addslashes(trim($request->getParameter('min_score'))); // 领取的最少积分
        $max_score = addslashes(trim($request->getParameter('max_score'))); // 领取的最大积分
        $continuity_three = addslashes(trim($request->getParameter('continuity_three'))); // 连续签到7天
        $continuity_twenty = addslashes(trim($request->getParameter('continuity_twenty'))); // 连续签到20天
        $continuity_thirty = addslashes(trim($request->getParameter('continuity_thirty'))); // 连续签到30天
        $activity_overdue = addslashes(trim($request->getParameter('activity_overdue'))); // 活动过期删除时间

        if($imgurl){
            $imgurl = preg_replace('/.*\//','',$imgurl);
            if($imgurl != $oldpic){
                @unlink ($uploadImg.$oldpic);
            }
        }else{
            $imgurl = $oldpic;
        }
        
        if(is_numeric($min_score) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('领取的最少积分请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($min_score <= 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('领取的最少积分不能为负数或0!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if(is_numeric($max_score) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('领取的最少积分请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($max_score <= 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('领取的最少积分不能为负数或0!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($max_score < $min_score){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('领取的最大积分不能小于领取的最少积分!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if ($continuity_three) {
            if(is_numeric($continuity_three) == ''){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('连续签到7天请输入数字!');" .
                    "</script>";
                return $this->getDefaultView();
            }
            if($continuity_three < 0){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('连续签到7天不能为负数!');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        if($continuity_twenty){
           if(is_numeric($continuity_twenty) == ''){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('连续签到20天请输入数字!');" .
                    "</script>";
                return $this->getDefaultView();
            }
            if($continuity_twenty < 0){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('连续签到20天不能为负数!');" .
                    "</script>";
                return $this->getDefaultView();
            } 
        }
        if($continuity_thirty){
            if(is_numeric($continuity_thirty) == ''){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('连续签到30天请输入数字!');" .
                    "</script>";
                return $this->getDefaultView();
            }
            if($continuity_thirty < 0){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('连续签到30天不能为负数!');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        if($activity_overdue){
            if(is_numeric($activity_overdue) == ''){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('活动过期删除时间请输入数字!');" .
                    "</script>";
                return $this->getDefaultView();
            }
            if($activity_overdue < 0){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('活动过期删除时间不能为负数!');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        
        $sql = "select * from lkt_sign_config where plug_ins_id = $plug_ins_id";
        $r = $db->select($sql);
        if($r){
            $sql = "update lkt_sign_config set imgurl = '$imgurl',activity_overdue = '$activity_overdue',min_score = '$min_score',max_score = '$max_score',continuity_three = '$continuity_three',continuity_twenty = '$continuity_twenty',continuity_thirty = '$continuity_thirty',modify_date = CURRENT_TIMESTAMP where plug_ins_id = '$plug_ins_id'";
            $r_1 = $db->update($sql);
            if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，签到参数修改失败！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            } else {
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('签到参数修改成功！');" .
                    "location.href='index.php?module=plug_ins';</script>";
            }
        }else{
            $sql = "insert into lkt_sign_config(plug_ins_id,imgurl,activity_overdue,min_score,max_score,continuity_three,continuity_twenty,continuity_thirty,modify_date) values('$plug_ins_id','$imgurl','$activity_overdue','$min_score','$max_score','$continuity_three','$continuity_twenty','$continuity_thirty',CURRENT_TIMESTAMP)";
            $r_1 = $db->insert($sql);
            if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，签到参数添加失败！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            } else {
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('签到参数添加成功！');" .
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