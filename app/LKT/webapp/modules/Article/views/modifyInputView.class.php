<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

        $this->setAttribute('id',$request->getAttribute('id'));

        $this->setAttribute('Article_title',$request->getAttribute('Article_title'));

        $this->setAttribute('Article_prompt',$request->getAttribute('Article_prompt'));

        $this->setAttribute('Article_imgurl',$request->getAttribute('Article_imgurl'));

		$this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));

        $this->setAttribute('sort',$request->getAttribute('sort'));

        $this->setAttribute('content',$request->getAttribute('content'));		 

		$this->setTemplate("modify.tpl");

    }

}

?>