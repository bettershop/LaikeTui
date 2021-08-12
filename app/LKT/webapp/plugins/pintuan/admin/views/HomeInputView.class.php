<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class HomeInputView extends PluginInputView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("status",$request->getAttribute("status"));
		$this->setAttribute("proname",$request->getAttribute("proname"));
		$this->setAttribute("pages_show",$request->getAttribute("pages_show"));
		$this->setAttribute("proname",$request->getAttribute("proname"));
		$this->setAttribute("username",$request->getAttribute("username"));
		$this->setAttribute("group_status",$request->getAttribute("group_status"));
		$this->setAttribute("group_num",$request->getAttribute("group_num"));
		$this->setAttribute("now_data",date("Y/m/d h:i"));
		$this->setAttribute("type",$request->getAttribute("type"));
		$pageto = $request->getAttribute('pageto');
		if($pageto == 'all'){
			$r = time();
			header("Content-type: application/msexcel;charset=utf-8");
			header("Content-Disposition: attachment;filename=user-$r.xls");
			$this->setTemplate("excel.tpl");
		} else {
			$this->setTemplate('home.tpl');
		}
    }
}
?>
