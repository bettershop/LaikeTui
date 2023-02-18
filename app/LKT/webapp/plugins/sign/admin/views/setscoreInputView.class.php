<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class setscoreInputView extends PluginInputView {
    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("bili",$request->getAttribute("bili"));
		$this->setAttribute("res",$request->getAttribute("res"));
		$this->setTemplate("setscore.tpl");
    }
}
?>
