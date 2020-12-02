 <?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/PDOAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');
require_once(MO_LIB_DIR . '/db.class.php');

class logoutAction extends Action {
	public function getDefaultView() {
		$db=DBAction::getInstance();
		$this->getContext()->getUser()->setAuthenticated(false);	
		$name = $_SESSION['admin_id'];
		$sql="insert into lkt_record (user_id,event) values (?,?)";
		lkt_execute($sql,array($name,'安全退出成功'));
		jump('index.php?module=Login');
	    exit;
	}

	public function execute(){

    }

	public function getRequestMethods(){
		return Request :: NONE;
	}

}

?>