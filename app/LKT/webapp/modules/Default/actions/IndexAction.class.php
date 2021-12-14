<?php
/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
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

