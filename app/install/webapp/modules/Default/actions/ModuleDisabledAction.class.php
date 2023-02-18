<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class ModuleDisabledAction extends Action
{
    public function execute ()
    {
    }

    public function getDefaultView ()
    {
        return View::SUCCESS;
    }


    public function getRequestMethods ()
    {
        return Request::NONE;

    }

}

