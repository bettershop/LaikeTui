<?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();		
		$this->setAttribute("_admin_id",$request->getAttribute("_admin_id"));
		$this->setAttribute("error",$request->getError('error'));
        $this->setTemplate("index.tpl");
    }
}
