<?php
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute("cart_id",$request->getAttribute("cart_id"));
        $this->setAttribute("title",$request->getAttribute("title"));
        $this->setAttribute("list",$request->getAttribute("list"));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));
        $this->setAttribute("pagesize",$request->getAttribute("pagesize"));
		$this->setTemplate("index.tpl");
    }
}
?>
