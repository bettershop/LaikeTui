<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class DetailInputView extends SmartyView {

	

    public function execute() {

		$request = $this->getContext()->getRequest();
		$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
		$this->setAttribute("detail",$request->getAttribute("detail"));
		$this->setAttribute("data",$request->getAttribute("data"));
		$this->setAttribute("express",$request->getAttribute("express"));
        $this->setAttribute("fenxiaoshang",$request->getAttribute("fenxiaoshang"));
        $this->setAttribute("express",$request->getAttribute("express"));
        $this->setAttribute("reduce_price",$request->getAttribute("reduce_price"));
        $this->setAttribute("coupon_price",$request->getAttribute("coupon_price"));
        $this->setAttribute("allow",$request->getAttribute("allow"));
        $this->setAttribute("num",$request->getAttribute("num"));

        $this->setTemplate("detail.tpl");

    }

}

?>