<?php
if(version_compare(PHP_VERSION,'5.6.0','<'))  die('require PHP > 5.6.0 !');
$INSTALL_PATH = str_replace('\\','/',dirname($_SERVER['SCRIPT_NAME']));
if($INSTALL_PATH==="/"){
    $INSTALL_PATH="/";   
}else{
    $INSTALL_PATH= '/'. trim($INSTALL_PATH,'/').'/';
}
define('INSTALL_PATH',$INSTALL_PATH);//安装目录
define('APP_PATH', __DIR__ . '/LKT/');
define('REAL_PATH', realpath('./') . '/');
//设置目录权限
chmod(REAL_PATH, 0777);
session_start();
$_SESSION["APP_PATH"]=APP_PATH;
$_SESSION["INSTALL_PATH"]=INSTALL_PATH;
clearstatcache();
$path ='../'.basename(dirname(__FILE__)).'/';
if(!is_file('./data/install.lock')){
  header('Location: ./install');
}else{
  header("Location: ./LKT");
}
?>