<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {
        $request = $this->getContext()->getRequest();
        $this->setAttribute('volume',$request->getAttribute('volume'));
        $this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
        $this->setAttribute('checked_attr_list',$request->getAttribute('checked_attr_list'));
        $this->setAttribute('attr_group_list',$request->getAttribute('attr_group_list'));
        $this->setAttribute('initial',$request->getAttribute('initial'));
        $this->setAttribute('status',$request->getAttribute('status'));
        $this->setAttribute('s_type',$request->getAttribute('s_type'));
        $this->setAttribute('ctypes',$request->getAttribute('ctypes'));
        $this->setAttribute('id',$request->getAttribute('id'));
        $this->setAttribute('r02',$request->getAttribute('r02'));
        $this->setAttribute('product_class',$request->getAttribute('product_class'));
        $this->setAttribute('product_number',$request->getAttribute('product_number'));
        $this->setAttribute('product_title',$request->getAttribute('product_title'));
        $this->setAttribute('subtitle',$request->getAttribute('subtitle'));
        $this->setAttribute('weight',$request->getAttribute('weight'));
        $this->setAttribute('content',$request->getAttribute('content'));
        $this->setAttribute('num',$request->getAttribute('num'));
        $this->setAttribute('imgurl',$request->getAttribute('imgurl'));
        $this->setAttribute('imgurls',$request->getAttribute('imgurls'));
        $this->setAttribute('freight_list',$request->getAttribute('freight_list'));
        $this->setAttribute('sort',$request->getAttribute('sort'));

        $this->setTemplate("modify.tpl");
    }
}
