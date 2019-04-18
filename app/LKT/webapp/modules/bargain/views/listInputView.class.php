<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class listInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("user_id",$request->getAttribute("user_id"));
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setTemplate("list.tpl");
    }
}
?>
