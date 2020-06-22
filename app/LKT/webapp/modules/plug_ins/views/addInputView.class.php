<?php
class addInputView extends SmartyView {
	
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute('pic', $request->getAttribute('pic'));
		$this->setAttribute("list",$request->getAttribute("list"));
        $this->setTemplate("add.tpl");
    }
}
?>