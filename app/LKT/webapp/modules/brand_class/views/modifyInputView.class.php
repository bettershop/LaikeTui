<?php
class modifyInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute('brand_id',$request->getAttribute('brand_id'));
        $this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));
        $this->setAttribute('brand_name', $request->getAttribute('brand_name'));
        $this->setAttribute('brand_y_name', $request->getAttribute('brand_y_name'));
        $this->setAttribute('brand_pic', $request->getAttribute('brand_pic'));
        $this->setAttribute('producer', $request->getAttribute('producer'));
        $this->setAttribute('remarks', $request->getAttribute('remarks'));
        $this->setAttribute('sort', $request->getAttribute('sort'));
		$this->setTemplate("modify.tpl");
    }
}
?>