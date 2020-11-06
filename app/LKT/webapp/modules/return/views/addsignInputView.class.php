<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class addsignInputView extends SmartyView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("express",$request->getAttribute("express"));

		$this->setAttribute("id",$request->getAttribute("id"));

		$this->setAttribute("sNo",$request->getAttribute("sNo"));

		$this->setAttribute("otype",$request->getAttribute("otype"));

        $this->setTemplate("addsign.tpl");

    }

}

?>