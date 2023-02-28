<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */


require_once(MO_LIB_DIR . '/db.class.php');

class addAction extends Action {


	public function getDefaultView() {
        $dd = $_SERVER['PHP_SELF'];
        $ddd =explode('/', $dd);//打散成数组
        if($ddd){
            $pic = implode('/', $ddd);

        }else{
             $pic = "/LKT";
        }
        $pic =str_replace('..', '', $pic);
    
        $request = $this->getContext()->getRequest();
        //获取文章类别
        $sql = "select cat_id,cat_name from lkt_news_class ";
        $r = lkt_gets($sql);
        $request->setAttribute("ctype",$r);
        $request->setAttribute('pic', $pic.'/images');

		return View :: INPUT;

	}



	public function execute(){

		$request = $this->getContext()->getRequest();
        $Article_title = addslashes(trim($request->getParameter('Article_title'))); // 文章标题
        $Article_prompt = addslashes(trim($request->getParameter('Article_prompt'))); // 文章副标题
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        $imgurl= addslashes($request->getParameter('imgurl')); // 文章图片
        $content = addslashes(trim($request->getParameter('content'))); // 文章内容
        if($imgurl){
            $imgurl = preg_replace('/.*\//','',$imgurl);
        }

        // 发布文章
        $sql = "insert into lkt_article(Article_title,Article_prompt,Article_imgurl,sort,content,add_date) " .
            "values('$Article_title','$Article_prompt','$imgurl','$sort','$content',CURRENT_TIMESTAMP)";
        $data = array();
        $data[] = $Article_title;
        $data[] = $Article_prompt;
        $data[] = $imgurl;
        $data[] = $sort;
        $data[] = $content;
        $data[] = CURRENT_TIMESTAMP;
        $r = lkt_execute($sql,$data);

        if($r == -1){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，文章发布失败！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('文章发布成功！');" .
                "location.href='index.php?module=Article';</script>";
            return $this->getDefaultView();

        }


	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}


