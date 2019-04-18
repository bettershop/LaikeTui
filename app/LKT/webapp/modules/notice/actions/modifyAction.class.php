<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class modifyAction extends Action {



	public function getDefaultView() {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

        // 接收信息

        $id = intval($request->getParameter("id")); // 活动id

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        // 根据插件id，查询插件信息 



        $sql = 'select * from lkt_set_notice where id='.$id;

        $res = $db -> select($sql);

        if($res){

            $id = $res[0]->id;

            $image = $res[0]->img_url;

            $name = $res[0]->name;

            $detail = $res[0]->detail;

        }



        $request->setAttribute("uploadImg",$uploadImg);

        $request->setAttribute("id",$id);

        $request->setAttribute("name",$name);

        $request->setAttribute("image",$image);

        $request->setAttribute("detail",$detail);



        

        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        $id = addslashes(trim($request->getParameter('id'))); // 

        $url = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        $name = addslashes(trim($request->getParameter('name'))); // name

        $detail = addslashes(trim($request->getParameter('detail'))); // 

        $oldpic = addslashes(trim($request->getParameter('oldpic')));

        $image = addslashes(trim($request->getParameter('image')));

        

        if($image){

            $image = preg_replace('/.*\//','',$image);

            if($image != $oldpic){

                @unlink ($uploadImg.$oldpic);

            }

        }else{

            $image = $oldpic;

        }



        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        //更新数据表

        $sql = "update lkt_set_notice " .

            "set img_url = '$image',user = '$admin_id',detail = '$detail',time = CURRENT_TIMESTAMP " . "where id = '$id'";



        $r = $db->update($sql); 



        if($r == -1) {

            echo "<script type='text/javascript'>" .

                "alert('未知原因，修改失败！');" .

                "location.href='index.php?module=notice';</script>";

            return $this->getDefaultView();

        } else {

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('修改成功！');" .

                "location.href='index.php?module=notice';</script>";

        }

        

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>