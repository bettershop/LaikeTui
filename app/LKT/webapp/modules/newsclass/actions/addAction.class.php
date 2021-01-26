<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
		$request = $this->getContext()->getRequest();
		$cat_name = $request->getParameter('cat_name');
		$sort = $request->getParameter('sort');
		$request->setAttribute('cat_name', $cat_name);
		$request->setAttribute('sort', $sort);
		return View :: INPUT;

	}

	public function execute(){

		$db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $cat_name = addslashes(trim($request->getParameter('cat_name')));
        $sort = floatval(trim($request->getParameter('sort')));
		
		$sql = "insert into lkt_news_class(cat_name,sort,add_date) "
            ."values('$cat_name','$sort',CURRENT_TIMESTAMP)";
		$r = $db->insert($sql);
		if($r == -1) {
			header("Content-type:text/html;charset=utf-8");
			echo "<script type='text/javascript'>" .
				"alert('未知原因，添加新闻分类失败！');" .
				"</script>";
			return $this->getDefaultView();

		} else {
			header("Content-type:text/html;charset=utf-8");
			echo "<script type='text/javascript'>" .
				"alert('添加新闻分类成功！');" .
				"location.href='index.php?module=newsclass';</script>";
			return $this->getDefaultView();
		}

		return;

	}


	public function getRequestMethods(){
		return Request :: POST;
	}


}

