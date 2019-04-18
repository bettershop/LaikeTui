<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
class LoginFilter extends Filter {

	public $effect;
	public $candirect = array("Default","Login","api") ;

    public function execute ($filterChain) {
		if($this->effect == false){
			$filterChain->execute();
			return;
		}

		//取得第一个访问模块的名称
		// 检索当前应用程序控制器
		$controller = $this->getContext()->getController(); 
		// 检索这个模块动作堆线
		$actionstack = $controller->getActionStack();
		// 检索第一个条目
		$first = $actionstack->getFirstEntry();
		// 检索此条目的模块名
		$firstmodule = $first->getModuleName();
		// 搜索数组中是否存在指定的值,存在
		if(in_array($firstmodule,$this->candirect)){
			// 执行此链中的下一个筛选器
			$filterChain->execute();
		} else {
			if($this->getContext()->getUser()->isAuthenticated()){
				// 获取管理员id
				$name = $this->getContext()->getStorage()->read('admin_id');
				$db = DBAction::getInstance();
				$sql = "select * from lkt_admin where name ='$name'";
				$r = $db->select($sql);
				$permission = unserialize($r[0]->permission);
				$permission[]="AdminLogin";
				$permission[]="index";
				$permission[]="end";
				$permission[]="permission";
				if($r[0]->admin_type!=1 && !in_array($this->getContext()->getModuleName(),$permission)){
					header("Location: index.php?module=permission"); 
					return;
				}else{
					$filterChain->execute();
				}
				
			} else {	
				$controller->redirect("index.php?module=Login");
			}
		}
		return;
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