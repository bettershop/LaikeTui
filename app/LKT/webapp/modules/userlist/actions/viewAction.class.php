<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class viewAction extends Action {



	public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter("id"); 
        $sql = "select * from lkt_user where id = '$id'";
        $r = $db->select($sql);
        if(!$r){
	        $sql = "select * from lkt_user where user_id = '$id'";
	        $r = $db->select($sql);
        }
        $request->setAttribute('user', $r);

        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收信息

		$id = intval($request->getParameter('id'));

        $sort = floatval(trim($request->getParameter('sort')));

        $content = addslashes($request->getParameter('content'));

        $Article_title = trim($request->getParameter('Article_title'));



        //判断是否重新上传过图片 -》 将临时文件复制到upload_image目录下 

        $imgURL=($_FILES['imgurl']['tmp_name']);

		if($imgURL){

			$imgURL_name=($_FILES['imgurl']['name']);

        	move_uploaded_file($imgURL,"../upfile/$imgURL_name");

			$imgURL_name = ', Article_imgurl =  \'' . $imgURL_name . '\'';

		}else{

			$imgURL_name = '';

		}



        // 检查文章标题是否重复

        $sql = "select 1 from lkt_article where Article_title = '$Article_title' and Article_id <> '$id'";

        $r = $db->select($sql);

        if ($r && count($r) > 0) {

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('{$Article_title} 已经存在，请选用其他标题进行修改！');" .

                "</script>";

            return $this->getDefaultView();

        }



		//更新数据表

		$sql = "update lkt_article " .

			"set Article_title = '$Article_title', sort = '$sort' $imgURL_name, content = '$content' "

			."where Article_id = '$id'";

		$r = $db->update($sql);

		

		if($r == -1) {

		echo "<script type='text/javascript'>" .

				"alert('未知原因，文章修改失败！');" .

				"location.href='index.php?module=Article';</script>";

			return $this->getDefaultView();

		} else {

			header("Content-type:text/html;charset=utf-8");

			echo "<script type='text/javascript'>" .

				"alert('文章修改成功！');" .

				"location.href='index.php?module=Article';</script>";

		}

		return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>