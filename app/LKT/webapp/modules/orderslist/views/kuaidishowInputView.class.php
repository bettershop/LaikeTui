<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class kuaidishowInputView extends SmartyView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("res",$request->getAttribute("res"));

		// $this->setAttribute("id",$request->getAttribute("id"));

		// $this->setAttribute("otype",$request->getAttribute("otype"));

        $this->setTemplate("kuaidishow.tpl");

    }

}

?>