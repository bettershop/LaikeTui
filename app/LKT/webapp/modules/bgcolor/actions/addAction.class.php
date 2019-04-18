<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

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

        // 接收数据 

        $color_name = addslashes(trim($request->getParameter('color_name'))); 

        $color = addslashes(trim($request->getParameter('color')));

        $sort = floatval(trim($request->getParameter('sort')));

        $sql_1="ALTER TABLE `lkt_background_color`
       MODIFY COLUMN `id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id' FIRST ";
        $db->query($sql_1);
        
        $sql = "insert into lkt_background_color(color_name,color,sort) " .

            "values('$color_name','$color','$sort')";

        $r=$db->insert($sql);

        if($r == -1){

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('未知原因，添加失败！');" .

                "</script>";

            return $this->getDefaultView();

        }else{

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('添加成功！');" .

                "location.href='index.php?module=bgcolor';</script>";

            return $this->getDefaultView();

        }

	    return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>