<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class yue_seeInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("pages_show",$request->getAttribute("pages_show"));
		$this->setAttribute("list",$request->getAttribute("list"));

		$this->setTemplate('yue_see.tpl');
    }
}
?>
