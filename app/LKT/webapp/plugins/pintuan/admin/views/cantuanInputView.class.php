<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class cantuanInputView extends PluginInputView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("status",$request->getAttribute("status"));
		$this->setAttribute("pages_show",$request->getAttribute("pages_show"));
		$this->setAttribute("product_title",$request->getAttribute("product_title"));
		$this->setAttribute("user",$request->getAttribute("user"));
		$this->setTemplate("cantuan.tpl");
    }
}
?>
