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

        $id = intval($request->getParameter("id")); // 插件id

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        // 根据插件id，查询插件信息

        $sql = "select * from lkt_software where id = '$id'";

        $r = $db->select($sql);

        if($r){

            $name = $r[0]->name; // 软件名称

            $image = $r[0]->image; // 软件图标

            $type = $r[0]->type; // 类型

            $edition = $r[0]->edition ; // 版本号

            $edition_url = $r[0]->edition_url ; // 版本名称

            $edition_text= $r[0]->edition_text ; // 版本名称

            $software_version = $r[0]->software_version;

        }



        $sql = "select * from lkt_software where type = '$type' and software_version='$software_version' order by id desc";

        $r = $db->select($sql);

        $ops = '';

        if($r){

            foreach ($r as $key => $value) {

              $vedition = $value->edition;

              if($vedition == $edition){

                 $ops .= " <option selected='selected' value='$vedition'>$vedition</option>";

              }else{

                 $ops .= " <option  value='$vedition'>$vedition</option>";

              }

            }

        }



        $request->setAttribute("ops",$ops);

        $request->setAttribute('software_version', $software_version);

        $request->setAttribute('id', $id);

        $request->setAttribute('uploadImg', $uploadImg);

        $request->setAttribute('name', $name);

        $request->setAttribute("image",$image);

        $request->setAttribute("type",$type);

        $request->setAttribute('edition', $edition);

        $request->setAttribute('edition_url', $edition_url);

        $request->setAttribute('edition_text', $edition_text);

        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        // 接收信息

		$id = intval($request->getParameter('id'));

        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置

        $upload_file = addslashes(trim($request->getParameter('upload_file'))); // 文件上传位置

        $name = addslashes(trim($request->getParameter('name'))); // 软件名称

        $image = addslashes($request->getParameter('image')); // 软件图片

        $oldpic = addslashes($request->getParameter('oldpic')); // 原软件图片

        $type = addslashes($request->getParameter('type')); // 软件类型

        $edition = addslashes($request->getParameter('edition')); // 版本号

        $edition_text = addslashes($request->getParameter('edition_text')); // 版本号

        if($name){

            // 根据软件名称查询软件表

            $sql = "select * from lkt_software where name = '$name' and type = '$type' and id != '$id' order by id desc";

            $r = $db->select($sql);

            if($r){

                $id = $r[0]->id; // 软件id

                if($edition == $r[0]->edition){

                    header("Content-type:text/html;charset=utf-8");

                    echo "<script type='text/javascript'>" .

                        "alert('版本".$edition."已存在!');" .

                        "</script>";

                    return $this->getDefaultView();

                }else if($edition < $r[0]->edition){

                    header("Content-type:text/html;charset=utf-8");

                    echo "<script type='text/javascript'>" .

                        "alert('版本".$edition."低于最新版本!');" .

                        "</script>";

                    return $this->getDefaultView();

                }else{

                    $edition = $edition;

                }

            }else{

                $name = $name;

            }

        }else{

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('软件名称不能为空!');" .

                "</script>";

            return $this->getDefaultView();

        }

        if($image){

            $image = preg_replace('/.*\//','',$image);

            if($image != $oldpic){

                @unlink ($uploadImg.$oldpic);

            }

        }else{

            $image = $oldpic;

        }

        if($_FILES['edition_url']['name']==""){

            $sql = "select edition_url from lkt_software where id = '$id'";

            $r = $db->select($sql);

            $edition_url_name = $r[0]->edition_url;

        }else{

            $sql = "select * from lkt_config where id = 1";

            $rr = $db->select($sql);

            $upload_file = $rr[0]->upload_file; // 文件上传位置

            //将临时文件复制到upload_image目录下

            $edition_url=($_FILES['edition_url']['tmp_name']);



            $edition_url_name = substr($_FILES['edition_url']['name'],0,strpos($_FILES['edition_url']['name'],'.')) . time() . '.' . $edition . (strrchr($_FILES['edition_url']['name'], '.'));

          

            move_uploaded_file($edition_url,"$upload_file/$edition_url_name");



            $sql = "select edition_url from lkt_software where id = '$id'";

            $rrr = $db->select($sql);

            @unlink ($upload_file. '/' .$rrr[0]->edition_url);

        }



        //更新数据表

        $sql = "update lkt_software " .

            "set name = '$name',edition_text='$edition_text',image = '$image',type = '$type',edition = '$edition', edition_url = '$edition_url_name',add_time = CURRENT_TIMESTAMP "

            ."where id = '$id'";

        $r = $db->update($sql);



        if($r == -1) {

        echo "<script type='text/javascript'>" .

                "alert('未知原因，修改失败！');" .

                "location.href='index.php?module=software';</script>";

            return $this->getDefaultView();

        } else {

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('修改成功！');" .

                "location.href='index.php?module=software';</script>";

        }

       

		return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>