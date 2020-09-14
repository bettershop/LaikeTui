<?php

class step2InputView extends SmartyView
{

    public function execute ()
    {
    	$request = $this->getContext()->getRequest();
        $this->setAttribute("url",$request->getAttribute("url"));
        $this->setTemplate('step2.tpl');
    }

}

?>