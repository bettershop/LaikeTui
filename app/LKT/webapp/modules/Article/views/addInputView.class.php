<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class addInputView extends SmartyView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("ctype",$request->getAttribute("ctype"));
		$this->setAttribute('pic', $request->getAttribute('pic'));

        $this->setTemplate("add.tpl");

    }

}

