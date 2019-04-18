<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class configInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("plug_ins_id",$request->getAttribute("plug_ins_id"));
		$this->setAttribute("can_num",$request->getAttribute("can_num"));
		$this->setAttribute("help_num",$request->getAttribute("help_num"));
		$this->setAttribute("parameter",$request->getAttribute("parameter"));
		$this->setAttribute("invalid_time",$request->getAttribute("invalid_time"));
		$this->setTemplate("config.tpl");
    }
}
?>
