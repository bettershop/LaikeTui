<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class taobaoInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("ctype",$request->getAttribute("ctype"));
		$this->setTemplate("taobao.tpl");
    }
}
?>
