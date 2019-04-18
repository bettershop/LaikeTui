<?php

class IndexAction extends Action
{

    public function execute ()
    {

        // we don't need any data here because this action doesn't serve
        // any request methods, so the processing skips directly to the view

    }

    public function getDefaultView ()
    {

        //return View::SUCCESS;
		$this->getContext()->getController()->redirect('index.php?module=Login');

    }

    public function getRequestMethods ()
    {

        return Request::NONE;

    }

}

?>