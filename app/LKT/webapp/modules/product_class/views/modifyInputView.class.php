<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();
        $this->setAttribute('bg', $request->getAttribute('bg'));
        $this->setAttribute('str_option',$request->getAttribute('str_option'));
        $this->setAttribute('cid_r',$request->getAttribute('cid_r'));
        $this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));
        $this->setAttribute('pname', $request->getAttribute('pname'));
        $this->setAttribute('rname', $request->getAttribute('rname'));
        $this->setAttribute('img', $request->getAttribute('img'));
        $this->setAttribute('sort', $request->getAttribute('sort'));
        $this->setAttribute('ctype', $request->getAttribute('ctype'));
        $this->setAttribute('ctype1', $request->getAttribute('ctype1'));
        $this->setAttribute('ctype2', $request->getAttribute('ctype2'));
        $this->setAttribute('cid', $request->getAttribute('cid'));
        $this->setAttribute('level', $request->getAttribute('level'));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));
		$this->setTemplate("modify.tpl");
    }
}
?>