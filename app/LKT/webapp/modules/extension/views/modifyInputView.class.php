<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class modifyInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
    $this->setAttribute('id',$request->getAttribute('id'));
    $this->setAttribute('res',$request->getAttribute('res'));
    $this->setAttribute('data',$request->getAttribute('data'));
    $this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));
    $this->setAttribute('img',$request->getAttribute('img'));
    $this->setTemplate("modify.tpl");
    }
}
?>