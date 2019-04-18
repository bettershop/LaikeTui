<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class amountInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("id",$request->getAttribute("id"));
		$this->setAttribute("title",$request->getAttribute("title"));
		$this->setAttribute("total_amount",$request->getAttribute("total_amount"));
		$this->setAttribute("total_num",$request->getAttribute("total_num"));
		$this->setAttribute("wishing",$request->getAttribute("wishing"));
		$this->setTemplate("amount.tpl");
    }
}
?>
