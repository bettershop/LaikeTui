<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class viewInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("news_title",$request->getAttribute("news_title"));
		$this->setAttribute("total",$request->getAttribute("total"));
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setTemplate("view.tpl");
    }
}
?>
