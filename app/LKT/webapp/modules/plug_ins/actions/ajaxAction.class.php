<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class ajaxAction extends Action {

	public function getDefaultView() {
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
		$type = addslashes(trim($request->getParameter('type')));

		$sql = "select id,name from lkt_software where type = '$type'";
        $r = $db->select($sql);
        foreach ($r as $key => $value) {
        	$res = '<option value="" >全部</option>';
            foreach ($r as $key => $value) {
               $res.= "<option value='{$value->id}'>{$value->name}</option>";
            }
            echo $res;
            exit;
        }
		return;
	}

	public function execute(){
		
	}

	public function getRequestMethods(){
		return Request :: NONE;
	}

}

?>