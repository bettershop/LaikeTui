<?php

/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_LIB_DIR . '/DBAction.class.php');


class viewAction extends Action
{


    public function getDefaultView()
    {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter("id");
        $sql = "select * from lkt_user where id = '$id'";
        $r = $db->select($sql);
        if (!$r) {
            $sql = "select * from lkt_user where user_id = '$id'";
            $r = $db->select($sql);
        }
        $request->setAttribute('user', $r);

        return View :: INPUT;

    }


    public function execute()
    {

    }


    public function getRequestMethods()
    {

        return Request :: POST;

    }


}

