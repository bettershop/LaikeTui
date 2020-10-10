<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

/*
功能：插件请求视图基础类
*/
class PluginInputView {

    public $view = null;
    public $pluginName = null;

    function __construct($view=null,$pluginName = null)
    {
        $this->view = $view;
        $this->pluginName = $pluginName;
    }

    //默认执行方法
    public function execute() {
        echo "hello PluginInputView";
        return ;
    }

    public function setAttribute($name,$value) {
        $this->view->setAttribute($name,$value);
    }

    public function setTemplate($path) {
        $this->view->setTemplate(MO_WEBAPP_DIR."/plugins/".$this->pluginName."/admin/templates/".$path);
    }


    //封装请求参数方法
    public function getParameter($string){
        return $_REQUEST[$string];
    }
    

    public function getContext ()
    {

        return $this->view->getContext();

    }

    
}

?>