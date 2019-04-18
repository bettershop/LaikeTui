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

        $data = $request->getParameter('data');
        $sql = "select GroupID,G_CName from admin_cg_group where G_ParentID = 0";
        $r = $db->select($sql);
        foreach ($r as $k => $v){
            $res[$v->GroupID] = $v->G_CName; // 省名称数组
        }
        if($data){ // 有运费信息
            $arr = json_decode($data,true);


            foreach ($arr as $k => $v){
                $arr1 = $v['name']; // 获取该运费下的省信息
                $arr2[] = explode(',',$arr1);  // 转数组
            }

            foreach($arr2 as $k1=>$v1) {
                foreach ($v1 as $key => $val) {
                    $arr3[] = $val; // 二维数组转以为数组
                }
            }

            foreach ($arr3 as $v2) {
                if (in_array($v2, $res)) { // 判断字符串是否存在数组里
                    foreach ($res as $k3 => $v3){ // 存在，循环数组
                        if($v3 == $v2){ // 当数组中的值与字符串相等，删除这个元素
                            unset($res[$k3]);
                        }
                    }
                }
            }
            if($res != []){
                foreach ($res as $k4 => $v4){
                    $list[] = array('GroupID'=>$k4,'G_CName'=>$v4);
                }
            }else{
                $list = array();
                $res = array('status' => '0','info'=>'已经全选！');
                echo json_encode($res);
                return;
            }
        }else{
            $list = $r;
        }


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