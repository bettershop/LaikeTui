<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class template_messageAction extends Action {



    public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter("id")); 
        $sql = "select * from lkt_notice where id = '1'";
        $r = $db->select($sql);
        $notice = $r[0]; // notice模板
        $request->setAttribute('notice', isset($notice) ? $notice : '');
        return View :: INPUT;

    }



    public function execute(){
        $request = $this->getContext()->getRequest();
        $db = DBAction::getInstance();
        $notice= $request->getParameter('notice'); 
        $sql = "select * from lkt_notice where 1=1";
        $num = $db->selectrow($sql);
        if($num < 1){
         $r = $db->insert_array($notice,'lkt_notice');
        }else{
         $r = $db->modify($notice,'lkt_notice',' id = 1');
        }
        if($r == -1) {
            echo "<script type='text/javascript'>" .

                "alert('未知原因，修改失败！');" .

                "location.href='index.php?module=system&action=template_message';</script>";

            return $this->getDefaultView();
        } else {
            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('修改成功！');" .

                "location.href='index.php?module=system&action=template_message';</script>";
        }
        return;

    }



    public function getRequestMethods(){

        return Request :: POST;

    }



  /**

  * 解压文件到指定目录﻿

  * @param  string  zip压缩文件的路径

  * @param  string  解压文件的目的路径

  * @param  boolean 是否以压缩文件的名字创建目标文件夹

  * @param  boolean 是否重写已经存在的文件

  * @return boolean 返回成功 或失败

  */

  public function unzip($src_file, $dest_dir=false, $create_zip_name_dir=false, $overwrite=true){

  if ($zip = zip_open($src_file)){

    if ($zip){

      $splitter = ($create_zip_name_dir === true) ? "." : "/";

      if($dest_dir === false){

        $dest_dir = substr($src_file, 0, strrpos($src_file, $splitter))."/";

      }

      // 如果不存在 创建目标解压目录

      $this->create_dirs($dest_dir);

       // 对每个文件进行解压

       while ($zip_entry = zip_read($zip)){

          // 文件不在根目录

          $pos_last_slash = strrpos(zip_entry_name($zip_entry), "/");

          if ($pos_last_slash !== false){

            // 创建目录 在末尾带 /

            $this->create_dirs($dest_dir.substr(zip_entry_name($zip_entry), 0, $pos_last_slash+1));

          }

          // 打开包

          if (zip_entry_open($zip,$zip_entry,"r")){

            // 文件名保存在磁盘上

            $file_name = $dest_dir.zip_entry_name($zip_entry);

            if ($overwrite === true || $overwrite === false && !is_file($file_name)){

              // 读取压缩文件的内容

              $fstream = zip_entry_read($zip_entry, zip_entry_filesize($zip_entry));

              file_put_contents($file_name, $fstream);

              // 设置权限

              chmod($file_name, 0777);

            }

            // 关闭入口

            zip_entry_close($zip_entry);

          }

        }

        // 关闭压缩包

        zip_close($zip);

      }

    }else{

      return false;

    }

    return true;

  }

  /**

  * 创建目录

  */

  public function create_dirs($path){

   if (!is_dir($path)){

     $directory_path = "";

     $directories = explode("/",$path);

     array_pop($directories);

     foreach($directories as $directory){

       $directory_path .= $directory."/";

       if (!is_dir($directory_path)){

         mkdir($directory_path);

         chmod($directory_path, 0777);

       }

     }

   }

  }

function curPageURL() 

{

  $pageURL = 'http';

  $aaa = $_SERVER["REQUEST_URI"];

  $url = substr($aaa,0,strrpos($aaa,"/"));

  if ($_SERVER["REQUEST_SCHEME"] == "https") 

  {

    $pageURL .= "s";

  }

  $pageURL .= "://";

  if ($_SERVER["SERVER_PORT"] != "80") 

  {

    $pageURL .= $_SERVER["SERVER_NAME"] . ":" . $_SERVER["SERVER_PORT"] . $url;

  } 

  else

  {

    $pageURL .= $_SERVER["SERVER_NAME"] . $url;

  }

  return $pageURL;

}



}



?>