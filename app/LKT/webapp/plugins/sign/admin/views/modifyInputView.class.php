<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class modifyInputView extends PluginInputView {

    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("id",$request->getAttribute("id"));
		$this->setAttribute("image",$request->getAttribute("image"));
		$this->setAttribute("starttime",$request->getAttribute("starttime"));
		$this->setAttribute("endtime",$request->getAttribute("endtime"));
        $this->setAttribute("detail",$request->getAttribute("detail"));
        $this->setAttribute("status",$request->getAttribute("status"));
		$this->setTemplate("modify.tpl");

    }

}

?>