<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IdentifyAction extends Action
{
    public function getDefaultView()
    {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id'); // 产品id      
        $id = rtrim($id, ','); // 去掉最后一个逗号
        $id = explode(',', $id); // 变成数组
        $con = count($id);
        $con01 = 0;
        $con02 = 0;
        $con03 = 0;
        $con04 = 0;
        foreach ($id as $k => $v) {
            $sql01 = "select s_type,status from lkt_product_list where id = '$v' and recycle=0";//新品
            $r01 = $db->select($sql01);
            $s_type = $r01[0]->s_type;
            $s_type1 = explode(',', $s_type); // 变成数组
            $status = $r01[0]->status;
            if (in_array("1", $s_type1)) {
                $con01 = $con01 + 1;
            }
            if (in_array("2", $s_type1)) {
                $con02 = $con02 + 1;
            }
            if (in_array("3", $s_type1)) {
                $con03 = $con03 + 1;
            }
            if ($status == 0) {
                $con04 = $con04 + 1;
            }
        }
        $data['con'] = $con;
        $data['con01'] = $con01;
        $data['con02'] = $con02;
        $data['con03'] = $con03;
        $data['con04'] = $con04;
        $res = array('data' => $data);
        echo json_encode($res);
    }

    public function execute()
    {
        echo json_encode(2);
    }


    public function getRequestMethods()
    {
        return Request :: NONE;
    }

}

?>