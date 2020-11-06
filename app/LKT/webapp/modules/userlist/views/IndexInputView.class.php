<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("name",$request->getAttribute("name"));
        $this->setAttribute("tel",$request->getAttribute("tel"));
        $this->setAttribute("source",$request->getAttribute("source"));
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("startdate",$request->getAttribute("startdate"));
		$this->setAttribute("enddate",$request->getAttribute("enddate"));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));
        $this->setAttribute("pagesize",$request->getAttribute("pagesize"));
		$this->setAttribute("now_data",date("Y/m/d h:i"));
		$pageto = $request->getAttribute('pageto');
		if($pageto != ''){
			$r = time();
			header("Content-type: application/msexcel;charset=utf-8");
			header("Content-Disposition: attachment;filename=user-$r.xls");
			$this->setTemplate("excel.tpl");
		} else {
			$this->setTemplate('index.tpl');
		}
    }
}
?>
