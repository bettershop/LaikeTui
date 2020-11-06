<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class addInputView extends SmartyView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("cat_name",$request->getAttribute("cat_name"));

        $this->setAttribute("sort",$request->getAttribute("sort"));

        $this->setTemplate("add.tpl");

    }

}

?>