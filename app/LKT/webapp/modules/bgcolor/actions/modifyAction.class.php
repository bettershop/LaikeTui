<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class modifyAction extends Action {



	public function getDefaultView() {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

        // 接收信息

        $id = intval($request->getParameter("id")); // id

        // 根据id查询

        $sql = "select * from lkt_background_color where id = '$id'";

        $r = $db->select($sql);

        if($r){

            $color_name = $r[0]->color_name; 

            $color = $r[0]->color;

            $sort = $r[0]->sort;

        }

        

        $request->setAttribute('id', $id);

        $request->setAttribute('color_name', isset($color_name) ? $color_name : '');

        $request->setAttribute('color', isset($color) ? $color : '');

        $request->setAttribute('sort', isset($sort) ? $sort : '');



        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收数据 

        $id = intval($request->getParameter('id'));

        $color_name = addslashes(trim($request->getParameter('color_name'))); 

        $color = addslashes(trim($request->getParameter('color')));

        $sort = floatval(trim($request->getParameter('sort')));





		//更新数据表

		$sql = "update lkt_background_color set color_name = '$color_name',color = '$color', sort = '$sort' where id = '$id'";

		$r = $db->update($sql);



		if($r == -1) {

		echo "<script type='text/javascript'>" .

				"alert('未知原因，修改失败！');" .

				"location.href='index.php?module=bgcolor';</script>";

			return $this->getDefaultView();

		} else {

			header("Content-type:text/html;charset=utf-8");

			echo "<script type='text/javascript'>" .

				"alert('修改成功！');" .

				"location.href='index.php?module=bgcolor';</script>";

		}

		return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>