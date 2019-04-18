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



        //获取新闻类别

        $sql = "select cat_id,cat_name from lkt_news_class ";

        $r = $db->select($sql);



        $request->setAttribute("ctype",$r);



		return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收数据 

        $news_class = addslashes(trim($request->getParameter('news_class'))); // 新闻类别

        $news_title = addslashes(trim($request->getParameter('news_title'))); // 新闻标题

        $sort = floatval(trim($request->getParameter('sort'))); // 排序

        $content = addslashes(trim($request->getParameter('content'))); // 新闻内容

        $author = addslashes(trim($request->getParameter('author'))); // 作者

        $imgurl = addslashes(trim($request->getParameter('imgurl'))); // 新闻图片

        $t_link = addslashes(trim($request->getParameter('t_link'))); // 推广二维码图片



        if($imgurl){

            $imgurl = preg_replace('/.*\//','',$imgurl);

        }

        if($t_link){

            $t_link = preg_replace('/.*\//','',$t_link);

        }



        // 发布新闻

        $sql = "insert into lkt_news_list(news_class,news_title,author,imgurl,sort,content,t_link,add_date) " .

            "values('$news_class','$news_title','$author','$imgurl','$sort','$content','$t_link',CURRENT_TIMESTAMP)";

        $r = $db->insert($sql);

        if($r == -1){

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('未知原因，新闻发布失败！');" .

                "</script>";

            return $this->getDefaultView();

        }else{

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('新闻发布成功！');" .

                "location.href='index.php?module=newslist';</script>";

            return $this->getDefaultView();

        }

	    return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>