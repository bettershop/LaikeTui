<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {
		$request = $this->getContext()->getRequest();
		$this->setAttribute('activity_type',$request->getAttribute('activity_type'));
        $this->setAttribute('id',$request->getAttribute('id'));
        $this->setAttribute('name',$request->getAttribute('name'));
        $this->setAttribute('money',$request->getAttribute('money'));
        $this->setAttribute('product_class_id',$request->getAttribute('product_class_id'));
        $this->setAttribute('product_id',$request->getAttribute('product_id'));
        $this->setAttribute('z_money',$request->getAttribute('z_money'));
		$this->setAttribute('num',$request->getAttribute('num'));
        $this->setAttribute('start_time',$request->getAttribute('start_time'));
        $this->setAttribute('end_time',$request->getAttribute('end_time'));
        $this->setAttribute('list',$request->getAttribute('list'));
        $this->setAttribute('list1',$request->getAttribute('list1'));
        $this->setAttribute('status',$request->getAttribute('status'));
		$this->setTemplate("modify.tpl");
    }
}
?>