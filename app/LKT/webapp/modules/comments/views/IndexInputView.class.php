<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("ordtype",$request->getAttribute("ordtype"));
		$this->setAttribute("class",$request->getAttribute("class"));
		$this->setAttribute("order",$request->getAttribute("order"));
		$this->setAttribute("sNo",$request->getAttribute("sNo"));
		$this->setAttribute("status",$request->getAttribute("status"));
		$this->setAttribute("otype",$request->getAttribute("otype"));
		$this->setAttribute("ostatus",$request->getAttribute("ostatus"));
		$this->setAttribute("startdate",$request->getAttribute("startdate"));
		$this->setAttribute("enddate",$request->getAttribute("enddate"));
		$pageto = $request->getAttribute('pageto');
		if($pageto != ''){
			$r = rand();
			header("Content-type: application/msexcel;charset=utf-8");
			header("Content-Disposition: attachment;filename=orderlist-$r.xls");
			$this->setTemplate("excel.tpl");
		} else {
			$this->setTemplate('index.tpl');
		}
    }
}
?>
