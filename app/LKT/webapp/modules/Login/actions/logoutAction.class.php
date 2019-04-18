 <?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
class logoutAction extends Action {
	public function getDefaultView() {
		$db=DBAction::getInstance();
		$request = $this->getContext()->getRequest();
		$this->getContext()->getUser()->setAuthenticated(false);	
		$name = $_SESSION['admin_id'];
		$sql="insert into lkt_record (user_id,event) values ('$name','安全退出成功')";
		$r= $db -> insert($sql);
        $db->admin_record($name,' 安全退出成功 ',0);
		echo "<script type='text/javascript'>location.href='index.php?module=Login';</script>";
	    exit;
	}

	public function execute(){

    }

	public function getRequestMethods(){
		return Request :: NONE;
	}

}

?>