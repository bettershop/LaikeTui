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

        $this->setAttribute('image',$request->getAttribute('image'));

        $this->setAttribute('url',$request->getAttribute('url'));

        $this->setAttribute('sort',$request->getAttribute('sort'));

        $this->setAttribute('products',$request->getAttribute('products'));			 

		$this->setTemplate("modify.tpl");

    }

}

?>