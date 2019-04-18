<?php



/**



 * [Laike System] Copyright (c) 2018 laiketui.com



 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.



 */



class pageaddInputView extends SmartyView {



	



    public function execute() {
			$request = $this->getContext()->getRequest();
			$this->setAttribute("article",$request->getAttribute("article"));		
			$this->setAttribute("list",$request->getAttribute("list"));
			$this->setAttribute("uploadImg",$request->getAttribute("uploadImg"));
			$this->setAttribute('products',$request->getAttribute('products'));	
			$this->setTemplate("pageadd.tpl");
    }



}



?>