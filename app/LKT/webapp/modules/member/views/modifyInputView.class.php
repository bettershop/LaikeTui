<?php
class modifyInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute('id',$request->getAttribute('id'));
        $this->setAttribute('name',$request->getAttribute('name'));
        $this->setAttribute('admin_type',$request->getAttribute('admin_type'));
        $this->setAttribute('list',$request->getAttribute('list'));

		$this->setTemplate("modify.tpl");
    }
}
?>