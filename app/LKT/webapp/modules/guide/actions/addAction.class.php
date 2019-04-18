<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class addAction extends Action {



	public function getDefaultView() {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

        $sql = "select * from lkt_config where id = '1'";

        $r = $db->select($sql);

        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        

        $request->setAttribute("uploadImg",$uploadImg);

		return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();



        // 接收数据 

        $image= addslashes($request->getParameter('image')); // 轮播图

        $type = addslashes(trim($request->getParameter('type'))); // 类型

        $sort = floatval(trim($request->getParameter('sort'))); // 排序

        if($image){

            $image = preg_replace('/.*\//','',$image);

        }else{

            echo "<script type='text/javascript'>" .

                "alert('引导图不能为空！');" .

                "</script>";

            return $this->getDefaultView();

        }



        // 添加轮播图

        $sql = "insert into lkt_guide(image,type,sort,add_date) " .

            "values('$image','$type','$sort',CURRENT_TIMESTAMP)";

        $r = $db->insert($sql);

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

                "location.href='index.php?module=guide';</script>";

            return $this->getDefaultView();

        }

	    return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>