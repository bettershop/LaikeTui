<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class ajaxAction extends Action {

	public function getDefaultView() {
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
		$GroupID = addslashes(trim($request->getParameter('GroupID')));


	      $strID="";
	      $sql = "select  *  from admin_cg_group a  where a.G_ParentID='$GroupID'";
	
		  $r = $db->select($sql);
		  	 
		  if($r)
		  {
          
		 
			  foreach($r as $list)
			  {
				 
				 $strID.=$list->G_CName.",".$list->GroupID."|";
				 
			  }
		  }
          echo  $strID;
		return;
	}

	public function execute(){
		
	}

	public function getRequestMethods(){
		return Request :: NONE;
	}

}

?>