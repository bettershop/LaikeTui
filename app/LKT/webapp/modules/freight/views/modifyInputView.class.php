<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute('id',$request->getAttribute('id'));
        $this->setAttribute('name',$request->getAttribute('name'));
        $this->setAttribute('type',$request->getAttribute('type'));
        $this->setAttribute('freight',$request->getAttribute('freight'));
        $this->setAttribute('list',$request->getAttribute('list'));
		$this->setTemplate("modify.tpl");
    }
}
?>