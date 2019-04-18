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
        $sql = "select * from lkt_software where type = '2' and software_version='2' order by id desc";
        $r = $db->select($sql);
        $ops = '';
        if($r){
            foreach ($r as $key => $value) {
              $edition = $value->edition;
              $ops .= " <option value='$edition'>$edition</option>";
            }
        }

        $request->setAttribute("ops",$ops);

		return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        $m = addslashes(trim($request->getParameter('m')));

        if($m){

            $this->select_type();

            exit;

        }

        // 接收数据 

        $name = addslashes(trim($request->getParameter('name'))); // 软件名称

        $image = addslashes(trim($request->getParameter('image'))); // 软件图标

        $type = addslashes(trim($request->getParameter('software_type'))); // 软件类型

        $edition = addslashes(trim($request->getParameter('edition'))); // 软件版本

        $edition_text = addslashes(trim($request->getParameter('edition_text'))); // 版本介绍

        $old_version = addslashes(trim($request->getParameter('old_version'))); // 老版本

        $software_version = $request->getParameter('software_version');

        if($name){

            // 根据软件名称查询软件表

            $sql = "select * from lkt_software where name = '$name' and type = '$type' order by id desc";

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

        }else{

            echo "<script type='text/javascript'>" .

                "alert('首页插件图标不能为空！');" .

                "</script>";

            return $this->getDefaultView();

        }

        if($_FILES['edition_url']['name']==""){

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                 "alert('请上传软件包！');" .

                 "</script>";

            return $this->getDefaultView();

        }else{

            $sql = "select * from lkt_config where id = 1";

            $rr = $db->select($sql);

            $upload_file = $rr[0]->upload_file; // 文件上传位置

            //将临时文件复制到upload_image目录下

            $edition_url=($_FILES['edition_url']['tmp_name']);



            $edition_url_name = substr($_FILES['edition_url']['name'],0,strpos($_FILES['edition_url']['name'],'.')) . time() . '.' . $edition . (strrchr($_FILES['edition_url']['name'], '.'));

            if($type == 2){

                $edition_url_name = 'lkt_update_'.$edition.'.zip';

            }

            move_uploaded_file($edition_url,"$upload_file/$edition_url_name");

        }

        // 添加软件

        $sql = "insert into lkt_software(name,image,type,edition,software_version,edition_url,edition_text,add_time) " .

            "values('$name','$image','$type','$edition','$software_version','$edition_url_name','$edition_text',CURRENT_TIMESTAMP)";

        $r = $db->insert($sql,'last_insert_id');

        if($r == -1){



            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('未知原因，添加失败！');" .

                "</script>";

            return $this->getDefaultView();

        }else{

           if(!empty($old_version)){
               $download_url = $rr[0]->uploadImg_domain.'/'.substr($upload_file,3).'/'.$edition_url_name;
               $sql = "INSERT INTO lkt_edition (`type`,`software_version`, `name`, `version`, `sid`, `old_version`, `download_url`, `version_abstract`, `release_time`)VALUES ('$type','$software_version','$name','$edition','$r','$old_version','$download_url','$edition_text',CURRENT_TIMESTAMP)";
               $rs = $db->insert($sql);
           }

            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('添加成功！');" .

                "location.href='index.php?module=software';</script>";

            return $this->getDefaultView();

        }

	    return;

	}



	public function getRequestMethods(){

		return Request :: POST;

	}

    public function select_type()

    {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

        $select_type = addslashes(trim($request->getParameter('type')));

        $select_version = addslashes(trim($request->getParameter('select_version')));

        $sql = "select * from lkt_software where type = '$select_type' and software_version='$select_version' order by id desc";

        // echo $sql;

        $r = $db->select($sql);

        $ops = '';

        if($r){

            foreach ($r as $key => $value) {

              $edition = $value->edition;

              $ops .= " <option value='$edition'>$edition</option>";

            }

        }

           $ops .= '<option value="">无</option>'; 

        echo $ops;

        exit;

    }



}



?>