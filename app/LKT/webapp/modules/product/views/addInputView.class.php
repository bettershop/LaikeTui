<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class addInputView extends SmartyView {
    public function execute() {
        $request = $this->getContext()->getRequest();
        $this->setAttribute("distributors",$request->getAttribute("distributors"));
        $this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
        $this->setAttribute("ctype",$request->getAttribute("ctype"));
        $this->setAttribute("brand",$request->getAttribute("brand"));
        $this->setAttribute("freight",$request->getAttribute("freight"));

        $this->setAttribute("attribute",$request->getAttribute("attribute"));
        $this->setAttribute("attribute_num",$request->getAttribute("attribute_num"));
        $this->setAttribute("attribute_key",$request->getAttribute("attribute_key"));
        $this->setAttribute("attribute_val",$request->getAttribute("attribute_val"));
        $this->setAttribute("rew",$request->getAttribute("rew"));
        $this->setAttribute("product_number",$request->getAttribute("product_number"));
        $this->setAttribute("product_title",$request->getAttribute("product_title"));
        $this->setAttribute("subtitle",$request->getAttribute("subtitle"));
        $this->setAttribute("scan",$request->getAttribute("scan"));
        $this->setAttribute("keyword",$request->getAttribute("keyword"));
        $this->setAttribute("weight",$request->getAttribute("weight"));
        $this->setAttribute("s_type",$request->getAttribute("s_type"));
        $this->setAttribute("is_distribution",$request->getAttribute("is_distribution"));
        $this->setAttribute("is_zhekou",$request->getAttribute("is_zhekou"));
        $this->setAttribute("sort",$request->getAttribute("sort"));
        $this->setAttribute("image",$request->getAttribute("image"));
        $this->setAttribute("freight",$request->getAttribute("freight"));
        $this->setAttribute("content",$request->getAttribute("content"));
        $this->setAttribute("volume",$request->getAttribute("volume"));
        $this->setTemplate("add.tpl");
    }
}
?>