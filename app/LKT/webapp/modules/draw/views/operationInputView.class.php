<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class operationInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("name",$request->getAttribute("name"));
		$this->setAttribute("spelling_number",$request->getAttribute("spelling_number"));
		$this->setAttribute("r05",$request->getAttribute("r05"));
		$this->setTemplate("operation.tpl");
    }
}
?>
