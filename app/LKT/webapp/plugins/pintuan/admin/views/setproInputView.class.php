<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class setproInputView extends PluginInputView {

    public function execute() {

		$request = $this->getContext()->getRequest();
		$this->setAttribute("arr",$request->getAttribute("arr"));
		$this->setAttribute("issuport",$request->getAttribute("issuport"));	 
        //$this->setAttribute("attrid",$request->getAttribute("attrid"));
		$this->setTemplate("setpro.tpl");

    }

}

?>