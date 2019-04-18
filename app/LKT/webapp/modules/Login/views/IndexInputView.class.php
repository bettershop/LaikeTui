<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class IndexInputView extends SmartyView {
// getContext() 检索当前应用程序上下文。
// getRequest() 检索请求。
// setAttribute() 设置一个属性。
// getAttribute() 方法返回指定属性名的属性值。
// getError() 检索一个错误消息。
// setTemplate() 为该视图设置模板。
    public function execute() {
		$request = $this->getContext()->getRequest();		
		$this->setAttribute("_admin_id",$request->getAttribute("_admin_id"));
		$this->setAttribute("error",$request->getError('error'));
        $this->setTemplate("index.tpl");
    }
}
?> 