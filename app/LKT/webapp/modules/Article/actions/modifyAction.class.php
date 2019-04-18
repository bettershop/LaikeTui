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

        $id = intval($request->getParameter("id")); // 文章id

        $uploadImg = addslashes($request->getParameter('uploadImg')); // 图片上传位置

        // 根据文章id，查询文章文章信息

        $sql = "select * from lkt_article where Article_id = '$id'";

        $r = $db->select($sql);

        if($r){

            $Article_title = $r[0]->Article_title; // 文章标题

            $Article_prompt = $r[0]->Article_prompt; // 文章标题

            $sort = $r[0]->sort; // 排序

            $content = $r[0]->content; // 文章内容Article_imgurl

            $Article_imgurl = $r[0]->Article_imgurl; // 文章图片

        }

		

		$request->setAttribute('id', $id);

        $request->setAttribute('Article_title',$Article_title);

        $request->setAttribute('Article_prompt',$Article_prompt);

        $request->setAttribute('sort', isset($sort) ? $sort : '');

        $request->setAttribute('Article_imgurl', $Article_imgurl);

        $request->setAttribute('content', $content);

        $request->setAttribute('uploadImg', $uploadImg);

        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收信息

		$id = intval($request->getParameter('id'));

        $uploadImg = addslashes($request->getParameter('uploadImg')); // 图片上传位置

        $Article_title = trim($request->getParameter('Article_title')); // 文章标题

        $Article_prompt = trim($request->getParameter('Article_prompt')); // 文章副标题

        $sort = floatval(trim($request->getParameter('sort'))); // 排序

        $imgurl = addslashes($request->getParameter('imgurl')); // 文章新图片

        $oldpic = addslashes($request->getParameter('oldpic')); // 文章原图片

        $content = addslashes($request->getParameter('content')); // 文章内容

        

        if($imgurl){

            $imgurl = preg_replace('/.*\//','',$imgurl);

            if($imgurl != $oldpic){

                @unlink ($uploadImg.$oldpic);

            }

        }else{

            $imgurl = $oldpic; // 文章图片

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

			"set Article_title = '$Article_title',Article_prompt = '$Article_prompt', sort = '$sort',Article_imgurl = '$imgurl', content = '$content' "

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