<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');


class addAction extends Action {

	public function getDefaultView() {

		return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $name = addslashes(trim($request->getParameter('name'))); // 规则名称
        $type = addslashes(trim($request->getParameter('type'))); // 类型
        $hidden_freight = $request->getParameter('hidden_freight'); // 运费信息

        if($hidden_freight){
            $freight_list = json_decode($hidden_freight,true);
            $freight = serialize($freight_list);
        }else{
            echo "<script type='text/javascript'>" .
                "alert('运费规则不能为空！');" .
                "location.href='index.php?module=freight&action=add';</script>";
            return $this->getDefaultView();
        }

        // 添加规则
        $sql = "insert into lkt_freight(name,type,freight,is_default,add_time) values('$name','$type','$freight',0,CURRENT_TIMESTAMP)";
		$rr = $db->insert($sql);
        if($rr > 0){
            $db->admin_record($admin_id,' 添加规则 '.$name,1);
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('规则添加成功！');" .
                "location.href='index.php?module=freight';</script>";
            return $this->getDefaultView();
        }else{
            $db->admin_record($admin_id,' 添加规则失败',1);
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，规则添加失败！');" .
                "location.href='index.php?module=freight';</script>";
            return $this->getDefaultView();
        }
	}

	public function getRequestMethods(){
		return Request :: POST;
	}
}
?>