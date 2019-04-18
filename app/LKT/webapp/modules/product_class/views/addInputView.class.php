<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class addInputView extends SmartyView {

    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
        $this->setAttribute("str_option",$request->getAttribute("str_option"));
        $this->setAttribute('level', $request->getAttribute('level'));
        //var_dump($request->getAttribute('level'));
        $this->setAttribute("ctype1",$request->getAttribute("ctype1"));
        $this->setAttribute("ctype2",$request->getAttribute("ctype2"));
        $this->setAttribute("cid",$request->getAttribute("cid"));
        $this->setAttribute("level",$request->getAttribute("level"));
        $this->setTemplate("add.tpl");
    }
}
?>