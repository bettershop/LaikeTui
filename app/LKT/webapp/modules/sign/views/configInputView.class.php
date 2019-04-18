<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class configInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("plug_ins_id",$request->getAttribute("plug_ins_id"));
		$this->setAttribute("imgurl",$request->getAttribute("imgurl"));
		$this->setAttribute("min_score",$request->getAttribute("min_score"));
		$this->setAttribute("max_score",$request->getAttribute("max_score"));
		$this->setAttribute("continuity_three",$request->getAttribute("continuity_three"));
		$this->setAttribute("continuity_twenty",$request->getAttribute("continuity_twenty"));
		$this->setAttribute("continuity_thirty",$request->getAttribute("continuity_thirty"));
		$this->setAttribute("activity_overdue",$request->getAttribute("activity_overdue"));
		$this->setTemplate("config.tpl");
    }
}
?>
