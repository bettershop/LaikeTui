<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

        $this->setAttribute('cat_id',$request->getAttribute('cat_id'));

        $this->setAttribute('cat_name', $request->getAttribute('cat_name'));

        $this->setAttribute('sort', $request->getAttribute('sort'));



		$this->setTemplate("modify.tpl");

    }

}

?>