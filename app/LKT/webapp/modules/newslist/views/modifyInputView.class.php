<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

class modifyInputView extends SmartyView {

    public function execute() {

		$request = $this->getContext()->getRequest();

		$this->setAttribute('ctypes',$request->getAttribute('ctypes'));

        $this->setAttribute('news_class',$request->getAttribute('news_class'));

        $this->setAttribute('id',$request->getAttribute('id'));

        $this->setAttribute('news_title',$request->getAttribute('news_title'));

        $this->setAttribute('author',$request->getAttribute('author'));

        $this->setAttribute('sort',$request->getAttribute('sort'));

        $this->setAttribute('content',$request->getAttribute('content'));

		$this->setAttribute('t_link',$request->getAttribute('t_link'));	 

        $this->setAttribute('imgurl',$request->getAttribute('imgurl'));  

        $this->setAttribute('uploadImg',$request->getAttribute('uploadImg'));  

		$this->setTemplate("modify.tpl");

    }

}

?>