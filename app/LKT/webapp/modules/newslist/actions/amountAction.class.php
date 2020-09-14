<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class amountAction extends Action {

    public function getDefaultView() {
        $request = $this->getContext()->getRequest();
        $db = DBAction::getInstance();
        // 接收信息
        $id = intval($request->getParameter('id'));
        // 根据新闻id，查询新闻信息
        $sql = "select * from lkt_news_list where id=$id";
        $r = $db->select($sql);
        $title = $r[0]->news_title; // 新闻标题
        $total_amount = $r[0]->total_amount; // 红包金额
        $total_num = $r[0]->total_num; // 红包数量
        $wishing = $r[0]->wishing; // 祝福语

        $request->setAttribute("id",$id);
        $request->setAttribute("title",$title);
        $request->setAttribute("total_amount",$total_amount);
        $request->setAttribute("total_num",$total_num);
        $request->setAttribute("wishing",$wishing);
        return View :: INPUT;
    }

    public function execute(){
        $request = $this->getContext()->getRequest();
        $db = DBAction::getInstance();
        // 接收参数
        $id = intval($request->getParameter('id')); // 新闻id
        $total_amount = addslashes(trim($request->getParameter('total_amount'))); // 红包金额
        $total_num = addslashes(trim($request->getParameter('total_num'))); // 红包数量
        $wishing = addslashes(trim($request->getParameter('wishing'))); // 祝福语
        // 判断金额是否为空 或 判断金额是否为0
        if($total_amount == '' || $total_amount == 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('红包金额不能为0！');" .
                "</script>";
            return $this->getDefaultView();
        }
        // 判断数量是否为空
        if($total_num == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('红包数量不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        // 判断金额和数量是否为数字
        if(is_numeric($total_amount) == false || is_numeric($total_num) == false){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('金额或数量不为数字！');" .
                "</script>";
            return $this->getDefaultView();
        }
        // 根据新闻id，修改新闻列表信息
        $sql = "update lkt_news_list " . "set total_amount = '$total_amount',total_num = '$total_num',wishing = '$wishing'" ."where id = '$id'";
        $r = $db->update($sql);
        if($r == -1){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，红包设置失败！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('红包设置成功！');" .
                "location.href='index.php?module=newslist';</script>";
            return $this->getDefaultView();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}
?>