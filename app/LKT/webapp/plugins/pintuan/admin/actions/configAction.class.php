<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class configAction extends PluginAction{

    public function getDefaultView(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $res = $db->select("select * from lkt_group_config  ");
        if($res){
            $group_time = $res[0]->group_time;
            $open_num = $res[0]->open_num;
            $can_num = $res[0]->can_num;
            $can_again = $res[0]->can_again;
            $open_discount = $res[0]->open_discount;
            $rule = $res[0]->rule;
        }else{
            $group_time = '';
            $open_num = '';
            $can_num = '';
            $can_again = '';
            $open_discount = '';
            $rule = '';
        }
        $request->setAttribute("res", $res);
        $request->setAttribute("group_time", $group_time);
        $request->setAttribute("open_num", $open_num);
        $request->setAttribute("can_num", $can_num);
        $request->setAttribute("can_again", $can_again);
        $request->setAttribute("open_discount", $open_discount);
        $request->setAttribute("rule", $rule);
        return View :: INPUT;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $group_time = $request->getParameter('group_time');//拼团时限
        $open_num = $request->getParameter('open_num');//开团数量
        $can_num = $request->getParameter('can_num');//参团数量
        $can_again = $request->getParameter('can_again');//是否重复参团 1 是 0 否
        $open_discount = $request->getParameter('open_discount');//团长优惠 1 是 0 否
        $rule = $request->getParameter('rule');//规则
        $msg = '';
        if($group_time == ''){
            $msg = '拼团时限不能为空！';
        }else if($group_time < 0){
            $msg = '拼团时限必须为正整数！';
        }else if($open_num == ''){
            $msg = '开团数量不能为空！';
        }else if($open_num < 0){
            $msg = '开团数量必须为正整数！';
        }else if($can_num == ''){
            $msg = '参团数量不能为空！';
        }else if($can_num < 0){
            $msg = '参团数量必须为正整数！';
        }

        if($msg == ''){
            //查询是否已有设置
            $sel_sql = "select * from lkt_group_config where id = 1";
            $sel_res = $db->select($sel_sql);
            if(empty($sel_res)){
                //如果已经设置 对数据进行修改
                $ist_sql = "INSERT INTO `lkt_group_config`(`id`, `refunmoney`, `group_time`, `open_num`, `can_num`, `can_again`, `open_discount`,`rule`) 
                                                VALUES (1,1,$group_time,$open_num,$can_num,$can_again,$open_discount,'$rule')";
                $res = $db->insert($ist_sql);
            }else{
                //如果没有设置新增一条设置数据
                $up_sql = "UPDATE `lkt_group_config` SET `group_time`=$group_time,`open_num`=$open_num,`can_num`=$can_num,`can_again`=$can_again,`open_discount`=$open_discount,`rule`='$rule' WHERE id = 1";
                $res = $db->update($up_sql);
            }
            if($res){
                goBack('设置成功!');
            }else{
                goBack('设置失败!');
            }

        }else{
            goBack($msg);
        }


    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>