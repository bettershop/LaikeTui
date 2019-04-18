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
        
        $sql = "select * from lkt_bargain_config where plug_ins_id = '$plug_ins_id'";
        $r = $db->select($sql);
        if($r){
            $can_num = $r[0]->can_num; // 能砍的次数
            $help_num = $r[0]->help_num; // 每天最多帮别人砍的次数
            $parameter = $r[0]->parameter; // 每次砍价的参数
            $invalid_time = $r[0]->invalid_time; // 逾期失效时间
        }else{
            $can_num = 7;
            $help_num = 3;
            $parameter = 0.3;
            $invalid_time = 7;
        }
        $request->setAttribute('plug_ins_id', $plug_ins_id);
        $request->setAttribute('can_num', $can_num);
        $request->setAttribute('help_num', $help_num);
        $request->setAttribute('parameter', $parameter);
        $request->setAttribute('invalid_time', $invalid_time);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $plug_ins_id = intval($request->getParameter('plug_ins_id'));
        $can_num = addslashes(trim($request->getParameter('can_num'))); // 能砍的次数
        $help_num = addslashes(trim($request->getParameter('help_num'))); // 每天最多帮别人砍的次数
        $parameter = addslashes($request->getParameter('parameter')); // 每次砍价的参数
        $invalid_time = addslashes(trim($request->getParameter('invalid_time'))); // 逾期失效时间
        if(is_numeric($can_num) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('能砍的次数请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($can_num < 2){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('能砍的次数要大于2!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if(is_numeric($help_num) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('每天最多帮别人砍的次数请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($help_num < 1){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('每天最多帮别人砍的次数要大于0!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if(is_numeric($parameter) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('每次砍价的参数请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($parameter <= 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('每次砍价的参数不能小于等于0!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if(is_numeric($invalid_time) == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('逾期失效时间请输入数字!');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($invalid_time < 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('逾期失效时间不能为负数!');" .
                "</script>";
            return $this->getDefaultView();
        }
        
        $sql = "select * from lkt_bargain_config where plug_ins_id = '$plug_ins_id'";
        $r = $db->select($sql);
        if($r){
            $sql = "update lkt_bargain_config set can_num = '$can_num',help_num = '$help_num',parameter = '$parameter',invalid_time = '$invalid_time',add_time = CURRENT_TIMESTAMP where plug_ins_id = '$plug_ins_id'";
            $r_1 = $db->update($sql);
            if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，砍价免单设置修改失败！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            } else {
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('砍价免单设置修改成功！');" .
                    "location.href='index.php?module=plug_ins';</script>";
            }
        }else{
            $sql = "insert into lkt_bargain_config(plug_ins_id,can_num,help_num,parameter,invalid_time,add_time) values('$plug_ins_id','$can_num','$help_num','$parameter','$invalid_time',CURRENT_TIMESTAMP)";
            $r_1 = $db->insert($sql);
            if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，砍价免单设置添加失败！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            } else {
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('砍价免单设置添加成功！');" .
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