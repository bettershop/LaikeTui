<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class ModifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

        $this->setAttribute('cid',$request->getAttribute('cid'));

        $this->setAttribute('headimgurl',$request->getAttribute('headimgurl'));

        $this->setAttribute('user_name',$request->getAttribute('user_name'));

        $this->setAttribute('content',$request->getAttribute('content')); 

        $this->setAttribute('add_time',$request->getAttribute('add_time')); 

        $this->setAttribute('CommentType',$request->getAttribute('CommentType'));

		$this->setTemplate("modify.tpl");

    }

}

?>