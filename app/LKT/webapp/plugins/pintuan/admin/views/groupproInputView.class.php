<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class groupproInputView extends PluginInputView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("list",$request->getAttribute("list"));
		$this->setAttribute("status",$request->getAttribute("status"));
        $this->setAttribute("len",$request->getAttribute("len"));
        $this->setTemplate("grouppro.tpl");

    }

}

?>