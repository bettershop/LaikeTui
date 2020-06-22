<?php
class IndexAction extends Action {

	public function getDefaultView() {
		
		return View :: INPUT;
	}

	public function execute(){		
		
	}

	public function getRequestMethods(){
		return Request :: NONE;
	}

}

?>