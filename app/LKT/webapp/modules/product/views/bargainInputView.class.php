<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class bargainInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("id",$request->getAttribute("id"));
		$this->setAttribute("sx_id",$request->getAttribute("sx_id"));
		$this->setAttribute("product_title",$request->getAttribute("product_title"));
		$this->setAttribute("bargain_price",$request->getAttribute("bargain_price"));
		$this->setAttribute("can_num",$request->getAttribute("can_num"));
		$this->setAttribute("parameter",$request->getAttribute("parameter"));
		$this->setAttribute("price",$request->getAttribute("price"));
		$this->setTemplate("bargain.tpl");
    }
}
?>
