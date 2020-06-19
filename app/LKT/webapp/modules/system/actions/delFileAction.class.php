<?php
require_once(MO_LIB_DIR . '/version.php');
class delFileAction extends Action {

   
    /** 
         * 删除目录及目录下所有文件或删除指定文件 
         * @param str $path   待删除目录路径 
         * @param int $delDir 是否删除目录，1或true删除目录，0或false则只删除文件保留目录（包含子目录） 
         * @return bool 返回删除状态 
    */ 


    public function getDefaultView() {
        $path = '../LKT/webapp/_compile';
        $delDir = false;
        $this->delDirAndFile($path,$delDir);//连同文件一起清除
        $path1 = '../LKT/webapp/_cache';
        $delDir1 = false;
        $this->delDirAndFile($path1,$delDir1);//不清除文件夹
        echo json_encode(array('res'=>'清除成功！','status'=>'1'));
        exit();
        return View :: INPUT;
    }

    public function execute(){

        return;
    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

    public function delDirAndFile($path, $delDir) {
     if (is_array($path)) {
       foreach ($path as $subPath)
         $this->delDirAndFile($subPath, $delDir);
     }
     if (is_dir($path)) {
       $handle = opendir($path);
       if ($handle) {
         while (false !== ( $item = readdir($handle) )) {
           if ($item != "." && $item != "..")
             is_dir("$path/$item") ?  $this->delDirAndFile("$path/$item", $delDir) : unlink("$path/$item");
         }
         closedir($handle);
         if ($delDir)
           return rmdir($path);
       }
     } else {
       if (file_exists($path)) {
         return unlink($path);
       } else {
         return FALSE;
       }
     }
     clearstatcache();
   }


}

?>