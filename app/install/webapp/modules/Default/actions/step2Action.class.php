<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class step2Action extends Action
{
    public function execute()

    {

    }

    public function getDefaultView()
    {

        if (isset($_SESSION['install_error']) && $_SESSION['install_error']>0) {
            $msg = " 编号 400".$_SESSION['install_error'];
            header("Content-type: text/html;charset=utf-8");
            echo "<script language='javascript'>" .
                "alert('环境检测没有通过，请调整环境后重试！".$msg."');" .
                "location.href='index.php?action=step1';</script>";
            return;
        }

        $_SESSION['install_step'] = '2';

        $num = 3;
        $url = $this->curPageURL();
        $request = $this->getContext()->getRequest();
        $request->setAttribute('url', isset($url) ? $url : '');
        $this->getContext()->getStorage()->write('step', $num);
        return View::INPUT;
    }


    public function getRequestMethods()

    {
        return Request::NONE;

    }


    function curPageURL()

    {
        $http_type = ((isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] == 'on') || (isset($_SERVER['HTTP_X_FORWARDED_PROTO']) && $_SERVER['HTTP_X_FORWARDED_PROTO'] == 'https')) ? 'https' : 'http';
        $hh = $_SERVER['REQUEST_URI'] ? $_SERVER['REQUEST_URI'] : ($_SERVER['PHP_SELF'] ? $_SERVER['PHP_SELF'] : $_SERVER['SCRIPT_NAME']);
        $uri = $http_type . '://' . $_SERVER['HTTP_HOST'] . substr($hh, 0, strrpos($hh, '/') + 1);
        $pageURL = substr($uri, 0, strrpos(substr($uri, 0, strrpos($uri, "/")), "/"));
        return $pageURL;
    }


}

?>