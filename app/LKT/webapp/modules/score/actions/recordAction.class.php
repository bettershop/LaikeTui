<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class recordAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = addslashes(trim($request->getParameter('user_id'))); // 用户id

        $condition = '1 = 1';
        if($user_id != ''){   
            $condition .= " and user_id like '$user_id'";
        }
        $sql = "select * from lkt_sign_record where $condition and type=0";
        $r = $db->select($sql);
        if($r){
            $list = $r;
        }else{
            $list = '';
        }
        $request->setAttribute("list",$list);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>