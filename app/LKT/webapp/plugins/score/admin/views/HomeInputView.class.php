<?php

/**

 * [Laike System] Copyright (c) 2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class HomeInputView extends PluginInputView {
    public function execute() {
        $request = $this->getContext()->getRequest();
        $this->setAttribute("list", $request->getAttribute("list"));
        $this->setAttribute("name", $request->getAttribute("name"));
        $this->setAttribute("status", $request->getAttribute("status"));
        $this->setAttribute("pages_show", $request->getAttribute("pages_show"));
		$this->setTemplate('index.tpl');
    }
}
?>
