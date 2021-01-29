<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class addInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();
		$this->setAttribute("software",$request->getAttribute("software"));
		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("software",$request->getAttribute("software"));
		$this->setAttribute("name",$request->getAttribute("name"));
		$this->setAttribute("activity_type",$request->getAttribute("activity_type"));
		$this->setAttribute("product_class_id",$request->getAttribute("product_class_id"));
		$this->setAttribute("product_id",$request->getAttribute("product_id"));
		$this->setAttribute("money",$request->getAttribute("money"));
		$this->setAttribute("num",$request->getAttribute("num"));
		$this->setAttribute("start_time",$request->getAttribute("start_time"));
		$this->setAttribute("end_time",$request->getAttribute("end_time"));
        $this->setTemplate("add.tpl");

    }

}

