<?php
class maskContentInputView extends SmartyView {
// setTemplate() 为该视图设置模板。
    public function execute() {
        $request = $this->getContext()->getRequest();
        $this->setAttribute('version',$request->getAttribute('version'));
        $this->setAttribute('list',$request->getAttribute('list'));
        $this->setAttribute('admin_id',$request->getAttribute('admin_id'));
        $this->setTemplate("index.tpl");
    }
}
?>