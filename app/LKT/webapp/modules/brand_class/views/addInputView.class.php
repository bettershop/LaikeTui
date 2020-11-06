<?php
class addInputView extends SmartyView {
	
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("ctype",$request->getAttribute("ctype"));
		$this->setAttribute("brand_name",$request->getAttribute("brand_name"));
		$this->setAttribute("brand_y_pname",$request->getAttribute("brand_y_pname"));
		$this->setAttribute("image",$request->getAttribute("image"));
		$this->setAttribute("producer",$request->getAttribute("producer"));
		$this->setAttribute("sort",$request->getAttribute("sort"));
		$this->setAttribute("remarks",$request->getAttribute("remarks"));
        $this->setTemplate("add.tpl");
    }
}
?>