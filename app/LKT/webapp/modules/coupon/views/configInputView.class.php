<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class configInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("plug_ins_id",$request->getAttribute("plug_ins_id"));
		$this->setAttribute("software_id",$request->getAttribute("software_id"));
		$this->setAttribute("activity_overdue",$request->getAttribute("activity_overdue"));
		$this->setAttribute("coupon_validity",$request->getAttribute("coupon_validity"));
		$this->setAttribute("coupon_overdue",$request->getAttribute("coupon_overdue"));
		$this->setTemplate("config.tpl");
    }
}
?>
