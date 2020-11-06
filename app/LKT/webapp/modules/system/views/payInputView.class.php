<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class payInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg_domain",$request->getAttribute("uploadImg_domain"));
		$this->setAttribute("img",$request->getAttribute("img"));
		$this->setAttribute("mch_cert",$request->getAttribute("mch_cert"));
		$this->setAttribute("mch_key",$request->getAttribute("mch_key"));
		$this->setAttribute("domain",$request->getAttribute("domain"));
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("upload_file",$request->getAttribute("upload_file"));
		$this->setAttribute("mch_id",$request->getAttribute("mch_id"));
		$this->setTemplate("pay.tpl");
    }
}
?>