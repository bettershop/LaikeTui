<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');


class AddAction extends Action {

    public function getDefaultView() {
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $kw = addslashes(trim($request -> getParameter('keyword')));
        
        $countsql = 'select count(keyword) from lkt_hotkeywords';
        $count = $db -> selectarray($countsql);
        list($count) = $count;
        $count = intval($count['count(keyword)']);
        if($count >= 6){
           header("Content-type:text/html;charset=utf-8");
             echo "<script type='text/javascript'>" .
                "alert('添加失败,最多只能添加六个关键词！');window.location.href='index.php?module=keyword';" .
                "</script>";die;
        }
        if($kw !== ''){
           $sql = 'insert into lkt_hotkeywords(keyword) values("'.$kw.'");';
           
           $res = $db -> insert($sql);

           if($res > 0){
             header("Content-type:text/html;charset=utf-8");
             echo "<script type='text/javascript'>" .
                "alert('添加成功！');window.location.href='index.php?module=keyword';" .
                "</script>";
             }
           }else{
              header("Content-type:text/html;charset=utf-8");
              echo "<script type='text/javascript'>" .
                "alert('关键词不能为空！');history.back();" .
                "</script>";
           }
        
        return;                                                                                                                                                                                                                                
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>