<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class configInputView extends PluginInputView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("res",$request->getAttribute("res"));
		$this->setAttribute("group_time",$request->getAttribute("group_time"));
		$this->setAttribute("open_num",$request->getAttribute("open_num"));
		$this->setAttribute("can_num",$request->getAttribute("can_num"));
		$this->setAttribute("can_again",$request->getAttribute("can_again"));
		$this->setAttribute("open_discount",$request->getAttribute("open_discount"));
		$this->setAttribute("image",$request->getAttribute("image"));
		$this->setAttribute("rule",$request->getAttribute("rule"));
        $this->setTemplate("config.tpl");

    }

}

?>