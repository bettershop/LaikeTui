<?php
class modifyInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute('id',$request->getAttribute('id'));
        $this->setAttribute('type',$request->getAttribute('type'));
        $this->setAttribute('list',$request->getAttribute('list'));
        $this->setAttribute('content',$request->getAttribute('content'));

		$this->setTemplate("modify.tpl");
    }
}
?>