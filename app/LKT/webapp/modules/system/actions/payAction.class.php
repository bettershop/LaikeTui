<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class payAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter("id")); 
        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
       
        $uploadImg_domain = $r[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }

        $mch_cert = $r[0]->mch_cert; // 软件上传位置
        $mch_id = $r[0]->mch_id; // 商户id
        $mch_key = $r[0]->mch_key; // ip地址
        
        if($uploadImg == ''){
            $uploadImg = "../LKT/images";
        }
        
        $request->setAttribute('uploadImg_domain', isset($uploadImg_domain) ? $uploadImg_domain : '');
        $request->setAttribute('img', isset($img) ? $img : '');
        $request->setAttribute("mch_cert", isset($mch_cert) ? $mch_cert : '');
        $request->setAttribute('mch_key', isset($mch_key) ? $mch_key : '');
        $request->setAttribute('domain', isset($domain) ? $domain : '');
        $request->setAttribute('uploadImg', isset($uploadImg) ? $uploadImg : '');
        $request->setAttribute('mch_id', isset($mch_id) ? $mch_id : '');
        return View :: INPUT;
    }

    public function execute(){

        $request = $this->getContext()->getRequest();
        $db = DBAction::getInstance();
        //取得参数
        // $mch_cert= addslashes($request->getParameter('mch_cert')); 
        $mch_key= addslashes($request->getParameter('mch_key')); 
        $mch_id = addslashes(trim($request->getParameter('mch_id'))); // 商户id

        if($mch_key == ''){
            echo "<script type='text/javascript'>" .
                "alert('信息未填写完整,请完善后在提交！');" .
                "location.href='index.php?module=system&action=pay';</script>";
        }
        if(is_numeric($mch_id) == false){
            echo "<script type='text/javascript'>" .
                "alert('商户id请输入数字！');" .
                "location.href='index.php?module=system&action=pay';</script>";
        }

        $ufile = $_FILES['upload_cert']['size'];

        $mch_cert_url ='';
        if($ufile){
            $upload_file = MO_LIB_DIR.'/cert/'; // 文件上传位置
            //将临时文件复制到upload_image目录下
            $upload_cert=($_FILES['upload_cert']['tmp_name']);
            $type = str_replace('image/', '.', $_FILES['upload_cert']['type']);
            $edition_url_name = 'apiclient_key_'.time().'.zip';
            $zip_file = $upload_file.$edition_url_name;
            move_uploaded_file($upload_cert,$zip_file);
            //存放证书到两个地址
            $uzip_res = $this->unzip($zip_file,$upload_file,true);

            $upload_file2 = MO_LIB_DIR.'/WxPayPubHelper/cacert/'; // 文件上传位置
            $uzip_res = $this->unzip($zip_file,$upload_file2,true);

            $sql = "select * from lkt_config where id = '1'";
            $r = $db->select($sql);
            $uploadImg_domain = $r[0]->uploadImg_domain; // 图片上传域名
            
            if($uzip_res){
               $mch_cert = $uploadImg_domain.'/LKT/webapp/lib/cert';
               $mch_cert_url = " mch_cert = '$mch_cert', ";
            }
        }
        // 更新
        $sql = "update lkt_config set $mch_cert_url mch_id = '$mch_id', mch_key = '$mch_key',modify_date = CURRENT_TIMESTAMP where id = '1'";
        $r = $db->update($sql);

        if($r == -1) {
            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=system&action=pay';</script>";
            return $this->getDefaultView();
        } else {

        //异步通知配置文件
        $sql = "select appid,appsecret,mch_key,mch_id from lkt_config where id=1";
        $r_db = $db -> select($sql);
        // $f_db = $r_db[0];
                // var_dump($f_db,$r_db,$sql);
        $db_config = [];

        foreach ($r_db[0] as $key => $value) {
            $db_config[$key] = $value;
        }

        $conf = file_get_contents(MO_LIB_DIR . '/WxPay.tpl');
        foreach ($db_config as $name => $value) {
            $conf = str_replace("[{$name}]", $value, $conf);
        }
        file_put_contents(MO_LIB_DIR . '/WxPayPubHelper/WxPay.pub.config.php', $conf);
        // exit;
        header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=system&action=pay';</script>";
        }
        
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

  /**
  * 解压文件到指定目录
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