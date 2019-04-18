<?php
class addInputView extends SmartyView {
	
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("ctype",$request->getAttribute("ctype"));
        $this->setTemplate("add.tpl");
    }
}
?>