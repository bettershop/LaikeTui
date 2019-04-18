<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute('id',$request->getAttribute('id'));

		$this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));

        $this->setAttribute('name',$request->getAttribute('name'));

        $this->setAttribute('image',$request->getAttribute('image'));

        $this->setAttribute('type',$request->getAttribute('type'));

        $this->setAttribute('edition',$request->getAttribute('edition'));

        $this->setAttribute('edition_url',$request->getAttribute('edition_url'));

		$this->setAttribute('edition_text',$request->getAttribute('edition_text'));	 

        $this->setAttribute('software_version',$request->getAttribute('software_version'));

        $this->setAttribute("ops",$request->getAttribute("ops"));

		$this->setTemplate("modify.tpl");

    }

}

?>