<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("banner",$request->getAttribute("banner"));
		$this->setAttribute("news_list",$request->getAttribute("news_list"));
		$this->setTemplate("index.tpl");
    }
}
?>