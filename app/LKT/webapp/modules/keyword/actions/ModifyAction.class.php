<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');


class ModifyAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = trim($request -> getParameter('id'));

        $sel = $db -> selectarray('select id,keyword from lkt_hotkeywords where id='.$id);
        if(!empty($sel)){
           $id = $sel['0']['id'];
           $sel = $sel['0']['keyword'];
          }
        $request -> setAttribute("id",$id);
        $request -> setAttribute("sel",$sel);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval(trim($request -> getParameter('id')));
        $name = addslashes(trim($request -> getParameter('name')));
        
        if($name !== ''){
          $sql = 'update lkt_hotkeywords set keyword="'.$name.'" where id='.$id;
          $res = $db -> update($sql);
          if($res > 0){
             header("Content-type:text/html;charset=utf-8");
             echo "<script type='text/javascript'>" .
                "alert('修改成功！');window.location.href='index.php?module=keyword';" .
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