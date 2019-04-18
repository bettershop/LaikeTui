<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class provinceAction extends Action {
	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql = "select GroupID,G_CName from admin_cg_group where G_ParentID = 0";
        $r = $db->select($sql);

        $list = $r;

        $res = array('status' => '1','list'=>$list,'info'=>'成功！');
        echo json_encode($res);

        return;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();

        $check_val = $request->getParameter('check_val'); // 属性
        $G_CName = '';
        foreach ($check_val as $k => $v){
            $sql = "select G_CName from admin_cg_group where GroupID = '$v'";
            $r = $db->select($sql);
            if($r){
                $G_CName .= $r[0]->G_CName . ',';
            }
        }
        $name = rtrim($G_CName, ',');
        $res = array('status' => '1','name'=>$name,'info'=>'成功！');
        echo json_encode($res);

	    return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}
}
?>