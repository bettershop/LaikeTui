<?php 
Header("Content-type: image/png"); 
session_name("admin_mojavi");
session_start();
$str = 'abcdefghjkmnpqrstuvwxyz23456789';
$l = strlen($str); 
$authnum_session = "";
srand((double)microtime()*1000000); 
for($i=1;$i<=4;$i++) 
{ 
	$num=rand(0,$l-1);
	$authnum_session.= $str[$num];
} 
$_SESSION["authnum_session"] = $authnum_session; 
session_write_close();
$im = imagecreate(50,20);
$black = ImageColorAllocate($im, 0,0,0); 
$white = ImageColorAllocate($im, 255,255,255); 
$gray = ImageColorAllocate($im, 200,200,200); 
imagefill($im,68,30,$black);
$li = imagecolorallocate($im, 220,220,220); 
for($i=0;$i<2;$i++) {
	imageline($im,rand(0,30),rand(0,21),rand(20,40),rand(0,21),$li);
}
imagestring($im, 5, 8, 2, $authnum_session, $white); 
imagePNG($im); 
imagedestroy($im); 
?> 
