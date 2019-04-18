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

        $id = intval($request->getParameter("id")); // 轮播图id

        $yimage = addslashes(trim($request->getParameter('yimage'))); // 原图片路径带名称

        $uploadImg = substr($yimage,0,strripos($yimage, '/')) . '/'; // 图片路径

        // 根据轮播图id，查询轮播图信息

        $sql = "select * from lkt_guide where id = '$id'";

        $r = $db->select($sql);

        if($r){

            $image = $r[0]->image; // 轮播图

            $type = $r[0]->type ; // 链接

            $sort = $r[0]->sort; // 排序

        }

        

        $request->setAttribute("uploadImg",$uploadImg);

        $request->setAttribute("image",$image);

        $request->setAttribute('id', $id);

        $request->setAttribute('type', $type);

        $request->setAttribute('sort', $sort);



        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收信息

		$id = intval($request->getParameter('id'));

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        $image = addslashes(trim($request->getParameter('image'))); // 轮播图

        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 原轮播图

        $type = addslashes(trim($request->getParameter('type'))); // 类型

        $sort = floatval(trim($request->getParameter('sort'))); // 排序

        if($image){

            $image = preg_replace('/.*\//','',$image);

            if($image != $oldpic){

                @unlink ($uploadImg.$oldpic);

            }

        }else{

            $image = $oldpic;

        }



		//更新数据表

		$sql = "update lkt_guide " .

			"set image = '$image',type = '$type', sort = '$sort' "

			."where id = '$id'";

		$r = $db->update($sql);



		if($r == -1) {

		echo "<script type='text/javascript'>" .

				"alert('未知原因，修改失败！');" .

				"location.href='index.php?module=guide';</script>";

			return $this->getDefaultView();

		}else {

			header("Content-type:text/html;charset=utf-8");

			echo "<script type='text/javascript'>" .

				"alert('修改成功！');" .

				"location.href='index.php?module=guide';</script>";

		}

		return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>