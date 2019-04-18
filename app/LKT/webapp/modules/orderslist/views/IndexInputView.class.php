<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
class IndexInputView extends SmartyView {
    public function execute() {
        $request = $this->getContext()->getRequest();
        $this->setAttribute("ordtype",$request->getAttribute("ordtype"));
        $this->setAttribute("express",$request->getAttribute("express"));
        
        $this->setAttribute("source",$request->getAttribute("source"));
        $this->setAttribute("class",$request->getAttribute("class"));
        $this->setAttribute("order",$request->getAttribute("order"));
        $this->setAttribute("sNo",$request->getAttribute("sNo"));
        $this->setAttribute("status",$request->getAttribute("status"));
        $this->setAttribute("otype",$request->getAttribute("otype"));
        $this->setAttribute("ostatus",$request->getAttribute("ostatus"));
        $this->setAttribute("startdate",$request->getAttribute("startdate"));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));
        $this->setAttribute("enddate",$request->getAttribute("enddate"));
        $this->setAttribute("brand_str",$request->getAttribute("brand_str"));
        $this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
        $this->setAttribute("data1",$request->getAttribute("data1"));
        $this->setAttribute("now_data",date("Y/m/d h:i"));
        $pageto = $request->getAttribute('pageto');
        if($pageto != ''){
            $r = time();
            header("Content-type: application/msexcel;charset=utf-8");
            header("Content-Disposition: attachment;filename=orders-$r.xls");
            $this->setTemplate("excel.tpl");
        } else {
            $this->setTemplate('index.tpl');
        }
    }
}
?>
