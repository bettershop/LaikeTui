<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class configInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("min_cz",$request->getAttribute("min_cz"));
		$this->setAttribute("min_amount",$request->getAttribute("min_amount"));
		$this->setAttribute("max_amount",$request->getAttribute("max_amount"));
		$this->setAttribute("service_charge",$request->getAttribute("service_charge"));
		$this->setAttribute("multiple",$request->getAttribute("multiple"));
		$this->setAttribute("cz_multiple",$request->getAttribute("cz_multiple"));
		$this->setAttribute("unit",$request->getAttribute("unit"));
		$this->setAttribute("transfer_multiple",$request->getAttribute("transfer_multiple"));
		$this->setTemplate("config.tpl");
    }
}
?>
