<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class modifyInputView extends PluginInputView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("group_data",$request->getAttribute("group_data"));
		$this->setAttribute("class",$request->getAttribute("class"));
		$this->setAttribute("g_status",$request->getAttribute("g_status"));
		$this->setAttribute("is_show",$request->getAttribute("is_show"));
		$this->setAttribute("proattr",$request->getAttribute("proattr"));
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("lastset",$request->getAttribute("lastset"));
		$this->setAttribute("levelstr",$request->getAttribute("levelstr"));
		$this->setAttribute("goods_id",$request->getAttribute("goods_id"));
		$this->setAttribute("brandres",$request->getAttribute("brandres"));

		$this->setTemplate("modify.tpl");
    }
}
?>
