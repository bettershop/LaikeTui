<?php

class step1InputView extends SmartyView
{

    public function execute ()
    {
    	$request = $this->getContext()->getRequest();
		$this->setAttribute("func",$request->getAttribute("func"));
		$this->setAttribute("files",$request->getAttribute("files"));
		$this->setAttribute("functions",$request->getAttribute("functions"));
        $this->setTemplate('step1.tpl');
    }

}

?>