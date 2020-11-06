<?php
class IndexInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("logo",$request->getAttribute("logo"));
		$this->setAttribute("company",$request->getAttribute("company"));
		$this->setAttribute("appid",$request->getAttribute("appid"));
		$this->setAttribute("appsecret",$request->getAttribute("appsecret"));
		$this->setAttribute("domain",$request->getAttribute("domain"));
		$this->setAttribute("uploadImg_domain",$request->getAttribute("uploadImg_domain"));
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("upload_file",$request->getAttribute("upload_file"));
		$this->setAttribute("mch_id",$request->getAttribute("mch_id"));
		$this->setAttribute("ip",$request->getAttribute("ip"));
		$this->setTemplate("index.tpl");
    }
}
?>