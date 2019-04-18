<?php
class modifyInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute('id',$request->getAttribute('id'));
		$this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));
        $this->setAttribute('name',$request->getAttribute('name'));
        $this->setAttribute('subtitle_name',$request->getAttribute('subtitle_name'));
        $this->setAttribute('type',$request->getAttribute('type'));
        $this->setAttribute('image',$request->getAttribute('image'));
        $this->setAttribute('subtitle_image',$request->getAttribute('subtitle_image'));
        $this->setAttribute('url',$request->getAttribute('url'));
        $this->setAttribute('subtitle_url',$request->getAttribute('subtitle_url'));
        $this->setAttribute('sort',$request->getAttribute('sort'));
        $this->setAttribute('http',$request->getAttribute('http'));
        $this->setAttribute('list',$request->getAttribute('list'));
			 
		$this->setTemplate("modify.tpl");
    }
}
?>