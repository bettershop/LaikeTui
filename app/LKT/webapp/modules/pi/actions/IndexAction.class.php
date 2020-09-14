<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');
require_once(MO_WEBAPP_DIR . "/plugins/PluginAction.class.php");

class IndexAction extends Action {


    public function getDefaultView() {
        $request = $this->getContext()->getRequest();
        $methodName = addslashes(trim($request->getParameter('m'))); //调用哪个方法
        $className = addslashes(trim($request->getParameter('c'))); //调用哪个类文件
        $pluginName = addslashes(trim($request->getParameter('p'))); //插件名称，文件名
        $className = $className==''?"Home":$className;
        $request -> setAttribute("c", $className);
        $request -> setAttribute("p", $pluginName);
        if($pluginName){
            require_once(MO_WEBAPP_DIR."/plugins/".$pluginName."/admin/actions/".$className."Action.class.php");
            $className = $className.'Action';
            $plugin = new $className($this->getContext());
            if ($methodName) {
                $plugin->$methodName();
            }else{
                if($_SERVER['REQUEST_METHOD'] == 'GET'){
                    $plugin->getDefaultView();
                }else{
                    $plugin->execute();
                }

            }

        }


        return View :: INPUT;
    }

    public function execute() {
        $this->getDefaultView();
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>