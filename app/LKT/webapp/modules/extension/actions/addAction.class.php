<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询配置表信息
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        $request->setAttribute("uploadImg",$img);
		return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        // 接收数据
        $title = trim($request->getParameter('title')); // 名称
        $type = trim($request->getParameter('type')); // 海报类型
        $keyword = trim($request->getParameter('keyword')); // 关键词
        $isdefault = trim($request->getParameter('isdefault')); // 是否默认
        $bg = trim($request->getParameter('bg')); // 背景图片
        $waittext = trim($request->getParameter('waittext')); // 等待语
        $data = trim($request->getParameter('data')); // 排序的数据
        $color = trim($request->getParameter('color')); // 颜色
        $img =$request->getParameter('img');
        
        if(empty($title) || empty($keyword) || empty($waittext)){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('信息未填写完整,请重新添加！');" .
                "</script>";
            return $this->getDefaultView();
        }
        // 添加数据
        if($isdefault){
            $sql = "update lkt_extension set isdefault = 0 where type = '$type'";
            $r = $db->update($sql);
        }
        $sql = "insert into lkt_extension(image,name,type,keyword,isdefault,bg,waittext,data,color,add_date)values('$img','$title','$type','$keyword','$isdefault','$bg','$waittext','$data','$color',CURRENT_TIMESTAMP)";
        $r = $db->insert($sql);
        if($r == -1){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，添加失败！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('添加成功！');" .
                "location.href='index.php?module=extension';</script>";
        }
	    return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>