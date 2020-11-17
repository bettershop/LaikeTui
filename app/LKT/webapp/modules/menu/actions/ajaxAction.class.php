<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class ajaxAction extends Action {

	public function getDefaultView(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $s_id = $request->getParameter("s_id");
        $sql = "select * from lkt_core_menu where id = '$s_id'";
        $r = $db->select($sql);
        if($r){
            $name = $r[0]->name;
            $data = array('status'=>1,'name'=>$name);
            echo json_encode($data);
        }else{
            $data = array('status'=>0);
            echo json_encode($data);
        }

        return;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $v = $request->getParameter("v");
        // 根据一级菜单id,查询下级菜单信息
        $sql = "select * from lkt_core_menu where s_id = '$v' order by sort,id";
        $r = $db->select($sql);
        $asd = '';
        if($r){
            foreach($r as $k=>$v){
                $id = $v->id; // 分类id
                $title = $v->title; // 分类名称
                $asd .=  "<option  value='$id'>$title</option>";
            }
        }
        echo json_encode($asd);
        return;
    }

	public function getRequestMethods(){
        return Request :: POST;
	}

}

?>