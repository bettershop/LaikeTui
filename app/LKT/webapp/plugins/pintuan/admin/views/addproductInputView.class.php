<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class addproductInputView extends PluginInputView {

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("arr",$request->getAttribute("arr"));
        $this->setAttribute("title",$request->getAttribute("title"));
		$this->setAttribute("class",$request->getAttribute("class")); 	 
		$this->setAttribute("proattr",$request->getAttribute("proattr"));
		$this->setAttribute("pages_show",$request->getAttribute("pages_show")); 
        $this->setAttribute("brandres",$request->getAttribute("brandres"));
        $this->setAttribute("brand_id",$request->getAttribute("brand_id")); 
		$this->setTemplate("addproduct.tpl");

    }

}

?>