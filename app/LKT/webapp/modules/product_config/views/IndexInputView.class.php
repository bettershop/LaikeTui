<?php
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("config",$request->getAttribute("config"));
		$this->setTemplate("index.tpl");
    }
}
?>
