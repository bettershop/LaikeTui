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

        $id = intval($request->getParameter("id")); // 新闻id

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        // 根据新闻id，查询新闻新闻信息

        $sql = "select * from lkt_news_list where id = '$id'";

        $r = $db->select($sql);

        if($r){

            $news_title = $r[0]->news_title; // 新闻标题

            $news_class = $r[0]->news_class ; // 新闻类别

            $author = $r[0]->author ; // 作者

            $sort = $r[0]->sort; // 排序

            $t_link = $r[0]->t_link; // 推广链接

            $imgurl = $r[0]->imgurl; // 新闻图片

            $content = $r[0]->content; // 新闻内容

            

        }



        //绑定新闻分类

        $db = DBAction::getInstance();

        $sql = "select * from lkt_news_class order by sort";

        $rs = $db->select($sql);

        

        $request->setAttribute("ctypes",$rs);

        $request->setAttribute('id', $id);

        $request->setAttribute('news_title', isset($news_title) ? $news_title : '');

        $request->setAttribute("news_class",$news_class);

        $request->setAttribute('author', isset($author) ? $author : '');

        $request->setAttribute('sort', isset($sort) ? $sort : '');

		$request->setAttribute('t_link', $t_link);

        $request->setAttribute('imgurl', $imgurl);

        $request->setAttribute('content', isset($content) ? $content : '');

        $request->setAttribute('uploadImg', $uploadImg);



        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收信息

		$id = intval($request->getParameter('id')); // 新闻id

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        $news_title = addslashes(trim($request->getParameter('news_title'))); // 新闻标题

        $news_class= addslashes($request->getParameter('news_class')); // 新闻分类

        $author = addslashes(trim($request->getParameter('author'))); // 作者

        $sort = floatval(trim($request->getParameter('sort'))); // 排序

        $imgurl = addslashes($request->getParameter('imgurl')); // 新闻图片

        $t_link = addslashes($request->getParameter('t_link')); // 推广链接

        $oldpic1 = addslashes($request->getParameter('oldpic1')); // 原新闻图片

        $oldpic2 = addslashes($request->getParameter('oldpic2')); // 原推广链接

        $content = addslashes($request->getParameter('content')); // 新闻内容

     

        if($imgurl){

            $imgurl = preg_replace('/.*\//','',$imgurl);

            if($imgurl != $oldpic1){

                @unlink ($uploadImg.$oldpic1);

            }

        }else{

            $imgurl = $oldpic1;

        }

        if($t_link){

            $t_link = preg_replace('/.*\//','',$t_link);

            if($t_link != $oldpic2){

                @unlink ($uploadImg.$oldpic2);

            }

        }else{

            $t_link = $oldpic2;

        }

        

        // 检查新闻标题是否重复

        $sql = "select 1 from lkt_news_list where news_title = '$news_title' and id <> '$id'";

        $r = $db->select($sql);

        if ($r && count($r) > 0) {

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('{$news_title} 已经存在，请选用其他标题进行修改！');" .

                "</script>";

            return $this->getDefaultView();

        }



		//更新数据表

		$sql = "update lkt_news_list " .

			"set news_title = '$news_title',news_class = '$news_class', sort = '$sort', imgurl = '$imgurl' , content = '$content', t_link = '$t_link' , author = '$author'"

			." where id = '$id'";

		$r = $db->update($sql);



		if($r == -1) {

		echo "<script type='text/javascript'>" .

				"alert('未知原因，新闻修改失败！');" .

				"location.href='index.php?module=newslist';</script>";

			return $this->getDefaultView();

		} else {

			header("Content-type:text/html;charset=utf-8");

			echo "<script type='text/javascript'>" .

				"alert('新闻修改成功！');" .

				"location.href='index.php?module=newslist';</script>";

		}

		return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>