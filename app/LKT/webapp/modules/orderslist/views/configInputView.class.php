<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class configInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("days",$request->getAttribute("days"));
		$this->setAttribute("content",$request->getAttribute("content"));
		$this->setAttribute("back",$request->getAttribute("back"));
        $this->setAttribute("order_failure",$request->getAttribute("order_failure"));
        $this->setAttribute("company",$request->getAttribute("company"));
        $this->setAttribute("order_overdue",$request->getAttribute("order_overdue"));
		$this->setAttribute("unit",$request->getAttribute("unit"));
		$this->setTemplate("config.tpl");
    }
}
?>
