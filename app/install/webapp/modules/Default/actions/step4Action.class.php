<?php
/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
$path =dirname(__FILE__);
$newa = substr($path,0,strrpos($path,'install'));
define('WEB_PATH',str_replace('\\',"/",$newa));

class step4Action extends Action
{

    public function execute ()
    {
        return View::INPUT;

    }


    public function getDefaultView ()
    {

        if ($_SESSION['install_step'] < 3 || !isset($_SESSION['install_step'])) {
            header("Content-type: text/html;charset=utf-8");
            echo "<script language='javascript'>" . "alert('安装失败，请重新开始！');" . "location.href='index.php?action=step1';</script>";
            return;

        }
        file_put_contents(WEB_PATH . 'data/install.lock',"laiketui".date("Y-m-d h:i:s",time()));
        return View::INPUT;


    }


    public function getRequestMethods ()
    {


        return Request::NONE;


    }

}
