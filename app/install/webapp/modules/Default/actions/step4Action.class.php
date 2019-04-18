<?php
$path =dirname(__FILE__);
$newa = substr($path,0,strrpos($path,'install'));
define('WEB_PATH',str_replace('\\',"/",$newa));
class step4Action extends Action
{

    public function execute ()
    {
        echo "string";
        return View::INPUT;
    }

    public function getDefaultView ()
    {
        file_put_contents(WEB_PATH . 'data/install.lock',"laiketui".date("Y-m-d h:i:s",time()));
        return View::INPUT;
    }

    public function getRequestMethods ()
    {
        return Request::NONE;
    }
}

?>