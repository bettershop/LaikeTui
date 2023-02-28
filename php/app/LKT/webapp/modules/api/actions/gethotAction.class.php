<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once('BaseAction.class.php');

class gethotAction extends BaseAction {

    public function getDefaultView() {

    }

    public function execute(){
        $sql = 'select keyword from lkt_hotkeywords';
        $res = lkt_rows($sql);
        foreach ($res as $k => $v) {
            $res[$k] = $v['keyword'];
        }
        echo json_encode($res);
        exit();
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

