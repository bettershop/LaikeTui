<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class configInputView extends SmartyView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("arr",$request->getAttribute("arr"));

		$this->setAttribute("type",$request->getAttribute("type"));

        $this->setTemplate("config.tpl");

    }

}

?>