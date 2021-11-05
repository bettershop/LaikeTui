<?php

/**

 * [Laike System] Copyright (c) 2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class HomeInputView extends PluginInputView {
    public function execute() {

        $request = $this->getContext()->getRequest();
        $this->setAttribute("list",$request->getAttribute("list"));
        $this->setAttribute("sNo",$request->getAttribute("sNo"));
        $this->setAttribute("starttime",$request->getAttribute("starttime"));
        $this->setAttribute("endtime",$request->getAttribute("endtime"));
        $this->setAttribute("username",$request->getAttribute("username"));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));

		$this->setTemplate('index.tpl');
    }
}
?>
