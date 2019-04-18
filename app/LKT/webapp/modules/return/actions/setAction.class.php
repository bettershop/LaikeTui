<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class setAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql1 = "select * from lkt_user_address where uid = 'admin'";
        $r = $db->select($sql1);
        if($r){
            $r = $r[0];
        }
        // var_dump($r,$r->name);
        $request->setAttribute("list",$r);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $name = trim($request -> getParameter('name'));
        $tel = trim($request -> getParameter('tel'));

        if(strlen($tel) >15){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('手机号码格式错误！');" .
                "location.href='index.php?module=return&action=set';</script>";
            exit;
        }
        $address = trim($request -> getParameter('address'));
        $sql1 = "select * from lkt_user_address where uid = 'admin'";
        $r = $db->select($sql1);
        if($r){
            $sql = "update lkt_user_address set name = '$name', tel = '$tel',address = '$address' where uid = 'admin'";
            $r = $db->update($sql);
        }else{
            $sqll = "insert into lkt_user_address (uid,name,tel,address) values ('admin','$name','$tel','$address')";
            $r = $db -> insert($sqll);
        }
        if($r == -1) {
            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=return&action=set';</script>";
            return $this->getDefaultView();
        } else {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=return&action=set';</script>";
        }
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>