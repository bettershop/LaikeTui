<?php
class addInputView extends SmartyView {
	
    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute('id',$request->getAttribute('id'));
        $this->setAttribute('pid',$request->getAttribute('pid'));
        $this->setAttribute('product_class_name',$request->getAttribute('product_class_name'));
        $this->setAttribute('brand_name',$request->getAttribute('brand_name'));
        $this->setAttribute('product_title',$request->getAttribute('product_title'));
        $this->setAttribute('product_number',$request->getAttribute('product_number'));
        $this->setAttribute('total_num',$request->getAttribute('total_num'));
        $this->setAttribute('num',$request->getAttribute('num'));
        $this->setAttribute('label',$request->getAttribute('label'));
        $this->setAttribute('subtitle',$request->getAttribute('subtitle'));
        $this->setAttribute('specifications',$request->getAttribute('specifications'));

        $this->setTemplate("add.tpl");
    }
}
?>