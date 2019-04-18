<?php
include_once(MO_WEBAPP_DIR . '/filter/sed.config.php');

class SedFilter extends Filter {

	public $effect;
	public $candirect = array("SecondLogin","ThirdLogin") ;
	public $filterurl = "index.php?module=SecondLogin&action=sed" ;

    public function execute ($filterChain) {
		if($this->effect == false){
			$filterChain->execute();
			return;
		}

		//取得第一个访问模块的名称
		$controller = $this->getContext()->getController();
		$actionstack = $controller->getActionStack();
		$first = $actionstack->getFirstEntry();
		$firstmodule = $first->getModuleName();
		$firstaction = $first->getActionName();
		if(in_array($firstmodule,$this->candirect)){
			$filterChain->execute();
		} else {
			$_SESSION['_sed_url'] = "index.php?" . $_SERVER["QUERY_STRING"];
			if($this->canAccess($firstmodule,$firstaction)){
				$filterChain->execute();
			} else {	
				$controller->redirect($this->filterurl);
			}
		}
		return;
	}

	public function canAccess($module,$action){
		global $_sed_modules;
		
		//模块是否配置过滤
		if(!isset($_sed_modules[$module])){
			$_SESSION['_sed_module']['name'] = $module;
			$_SESSION['_sed_action']['name'] = $action;
			return true;
		}
		//模块是否可以访问
		if( !isset($_SESSION['_sed_module']['name']) || 
				$_SESSION['_sed_module']['name'] != $module || 
					$_SESSION['_sed_module']['value'] != 'on' ) {
			$_SESSION['_sed_module']['name'] = $module;
			$_SESSION['_sed_module']['value'] = 'off';
			return false;
		}
		//动作是否配置过滤
		if(!isset($_sed_modules[$module][$action])){
			$_SESSION['_sed_module']['name'] = $module;
			$_SESSION['_sed_action']['name'] = $action;
			return true;
		}
		//动作是否可以访问
		if( !isset($_SESSION['_sed_action']['name']) || 
				$_SESSION['_sed_action']['name'] != $action ||
					$_SESSION['_sed_action']['value'] != 'on' ) {
			$_SESSION['_sed_action']['name'] = $action;
			$_SESSION['_sed_action']['value'] = 'off';
			return false;
		}
		return true;
	}

    public function initialize ($context, $params = null) {
		if($params['effect']){
			$this->effect = true;
		} else {
			$this->effect = false;
		}
		// initialize parent
		parent::initialize($context, $params);
		return true;
    }

}

?>