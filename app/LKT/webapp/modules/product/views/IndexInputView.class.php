<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class IndexInputView extends SmartyView {
    public function execute() {
        $request = $this->getContext()->getRequest();
        $this->setAttribute("product_title",$request->getAttribute("product_title"));
        $this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
        $this->setAttribute("class",$request->getAttribute("class"));
        $this->setAttribute("rew",$request->getAttribute("rew"));
        $this->setAttribute("s_type",$request->getAttribute("s_type"));
        $this->setAttribute("status",$request->getAttribute("status"));
        $this->setAttribute("list",$request->getAttribute("list"));
        $this->setAttribute("min_inventory",$request->getAttribute("min_inventory"));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));
        $this->setAttribute("pagesize",$request->getAttribute("pagesize"));
        $this->setTemplate("index.tpl");
    }
}
?>
