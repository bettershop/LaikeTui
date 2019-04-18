<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class IndexInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));

		$this->setAttribute("upload_file",$request->getAttribute("upload_file"));

		$this->setAttribute("list",$request->getAttribute("list"));

		$this->setTemplate("index.tpl");

    }

}

?>

