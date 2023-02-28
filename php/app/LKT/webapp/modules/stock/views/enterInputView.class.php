<?php
class enterInputView extends SmartyView {
    public function execute() {
		$request = $this->getContext()->getRequest();
        $this->setAttribute("list",$request->getAttribute("list"));
        $this->setAttribute("pages_show",$request->getAttribute("pages_show"));
        $this->setAttribute("product_title",$request->getAttribute("product_title"));
        $this->setAttribute("name",$request->getAttribute("name"));
        $this->setAttribute("excel",$request->getAttribute("excel"));

        $pageto = $request->getAttribute('pageto');
        if($pageto != ''){
            $r = rand();
            header("Content-type: application/msexcel;charset=utf-8");
            header("Content-Disposition: attachment;filename=userlist-$r.xls");
            $this->setTemplate("excel_enter.tpl");
        } else {
            $this->setTemplate("enter.tpl");
        }
    }
}

