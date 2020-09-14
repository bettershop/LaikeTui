<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

        $this->setAttribute('color_name',$request->getAttribute('color_name'));

        $this->setAttribute('color',$request->getAttribute('color'));

        $this->setAttribute('sort',$request->getAttribute('sort'));

        $this->setAttribute('id',$request->getAttribute('id'));

			 

		$this->setTemplate("modify.tpl");

    }

}

?>