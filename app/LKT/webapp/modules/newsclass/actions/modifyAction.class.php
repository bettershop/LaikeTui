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

        // 接收分类id

        $cat_id = intval($request->getParameter("cat_id"));

        

        // 根据分类id,查询新闻分类表

        $sql = "select * from lkt_news_class where cat_id = '$cat_id'";

        $r = $db->select($sql);

        if($r){

            $cat_name = $r[0]->cat_name; // 分类名称

            $sort = $r[0]->sort; // 分类排序

        }



        $request->setAttribute('cat_id', $cat_id);

        $request->setAttribute('cat_name', isset($cat_name) ? $cat_name : '');

        $request->setAttribute('sort', isset($sort) ? $sort : '');



		return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

		$cat_id = intval($request->getParameter('cat_id'));

        $cat_name = addslashes(trim($request->getParameter('cat_name')));

        $sort = floatval(trim($request->getParameter('sort')));



        //检查分类名是否重复

        $sql = "select cat_id from lkt_news_class where cat_name = '$cat_name' and cat_id <> '$cat_id'";

        $r = $db->select($sql);

        if ($r) {

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('新闻分类 {$cat_name} 已经存在，请选用其他名称修改！');" .

                "</script>";

            return $this->getDefaultView();

        }



		//更新分类列表

		$sql = "update lkt_news_class " .

			"set cat_name = '$cat_name', sort = '$sort'"

			."where cat_id = '$cat_id'";

		

		$r = $db->update($sql);



		if($r == -1) {

		echo "<script type='text/javascript'>" .

				"alert('未知原因，修改新闻分类失败！');" .

				"location.href='index.php?module=newsclass';</script>";

			return $this->getDefaultView();

		} else {

			header("Content-type:text/html;charset=utf-8");

			echo "<script type='text/javascript'>" .

				"alert('修改新闻分类成功！');" .

				"location.href='index.php?module=newsclass';</script>";

		}

		return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>