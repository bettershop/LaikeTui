<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class configAction extends PluginAction {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $plug_ins_id = intval($request->getParameter("id")); // 插件id

        $appConfig = $this->getAppInfo();
        $uploadImg = $appConfig['imageRootUrl'];

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
        $imgurl = addslashes($request->getParameter('imgurl')); // 新活动图片
        $oldpic= addslashes($request->getParameter('oldpic')); // 原活动图片
        $min_score = addslashes(trim($request->getParameter('min_score'))); // 领取的最少积分
        $max_score = addslashes(trim($request->getParameter('max_score'))); // 领取的最大积分
        $continuity_three = addslashes(trim($request->getParameter('continuity_three'))); // 连续签到7天
        $continuity_twenty = addslashes(trim($request->getParameter('continuity_twenty'))); // 连续签到20天
        $continuity_thirty = addslashes(trim($request->getParameter('continuity_thirty'))); // 连续签到30天
        $activity_overdue = addslashes(trim($request->getParameter('activity_overdue'))); // 活动过期删除时间

        $appConfig = $this->getAppInfo();
        $uploadImg = $appConfig['imageRootUrl'];

        if($imgurl){
            $imgurl = preg_replace('/.*\//','',$imgurl);
            if($imgurl != $oldpic){
                @unlink ($uploadImg.$oldpic);
            }
        }else{
            $imgurl = $oldpic;
        }
        
        if(is_numeric($min_score) == ''){
            goBack('领取的最少积分请输入数字!');
        }
        if($min_score <= 0){
            goBack('领取的最少积分不能为负数或0!');
        }
        if(is_numeric($max_score) == ''){
            goBack('领取的最少积分请输入数字!');
        }
        if($max_score <= 0){
            goBack('领取的最少积分不能为负数或0!');
        }
        if($max_score < $min_score){
            goBack('领取的最大积分不能小于领取的最少积分!');
        }
        if ($continuity_three) {
            if(is_numeric($continuity_three) == ''){
                goBack('连续签到7天请输入数字!');
            }
            if($continuity_three < 0){
                goBack('连续签到7天不能为负数!');
            }
        }
        if($continuity_twenty){
           if(is_numeric($continuity_twenty) == ''){
               goBack('连续签到20天请输入数字!');
            }
            if($continuity_twenty < 0){
                goBack('连续签到20天不能为负数!');
            } 
        }
        if($continuity_thirty){
            if(is_numeric($continuity_thirty) == ''){
                goBack('连续签到30天请输入数字!');
            }
            if($continuity_thirty < 0){
                goBack('连续签到30天不能为负数!');
            }
        }
        if($activity_overdue){
            if(is_numeric($activity_overdue) == ''){
                goBack('活动过期删除时间请输入数字!');
            }
            if($activity_overdue < 0){
                goBack('活动过期删除时间不能为负数!');
            }
        }
        
        $sql = "select * from lkt_sign_config where plug_ins_id = $plug_ins_id";
        $r = $db->select($sql);
        if($r){
            $sql = "update lkt_sign_config set imgurl = '$imgurl',activity_overdue = '$activity_overdue',min_score = '$min_score',max_score = '$max_score',continuity_three = '$continuity_three',continuity_twenty = '$continuity_twenty',continuity_thirty = '$continuity_thirty',modify_date = CURRENT_TIMESTAMP where plug_ins_id = '$plug_ins_id'";
            $r_1 = $db->update($sql);
            if($r_1 == -1) {
                goBack('未知原因，签到参数修改失败!');
            } else {
                goBack('签到参数修改成功!');
            }
        }else{
            $sql = "insert into lkt_sign_config(plug_ins_id,imgurl,activity_overdue,min_score,max_score,continuity_three,continuity_twenty,continuity_thirty,modify_date) values('$plug_ins_id','$imgurl','$activity_overdue','$min_score','$max_score','$continuity_three','$continuity_twenty','$continuity_thirty',CURRENT_TIMESTAMP)";
            $r_1 = $db->insert($sql);
            if($r_1 == -1) {
                goBack('未知原因，签到参数添加失败!');
            } else {
                goBack('签到参数添加成功!');
            }
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>