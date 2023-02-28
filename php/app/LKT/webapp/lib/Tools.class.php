<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 * 工具类
 * 作者：ketter
 */

//转换到HTML
function converToHTML($a){
	$a = HTMLSpecialChars($a);
	$a = str_replace(" ","&nbsp;",$a);
	$a = nl2br($a);
	return $a;
}

//生成凭证号
function confirmNum($pre){
	return $pre . date("Ymd") . 
		(date("i")*100+date("s")*10+floor(microtime()*1000)) ;
}

//格式化钱币格式
function moneyFormat($m){
	$i = strpos($m, '.');
	if ($i === false) {
		return "{$m}.00";
	} else {
		return substr("{$m}00", 0, $i + 3);
	}
}

//获取客户IP
function get_client_ip($type = 0,$client=true)
{
    $type       =  $type ? 1 : 0;
    static $ip  =   NULL;
    if ($ip !== NULL) return $ip[$type];
    if($client){
        if (isset($_SERVER['HTTP_X_FORWARDED_FOR'])) {
            $arr    =   explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']);
            $pos    =   array_search('unknown',$arr);
            if(false !== $pos) unset($arr[$pos]);
            $ip     =   trim($arr[0]);
        }elseif (isset($_SERVER['HTTP_CLIENT_IP'])) {
            $ip     =   $_SERVER['HTTP_CLIENT_IP'];
        }elseif (isset($_SERVER['REMOTE_ADDR'])) {
            $ip     =   $_SERVER['REMOTE_ADDR'];
        }
    }elseif (isset($_SERVER['REMOTE_ADDR'])) {
        $ip     =   $_SERVER['REMOTE_ADDR'];
    }
    // 防止IP伪造
    $long = sprintf("%u",ip2long($ip));
    $ip   = $long ? array($ip, $long) : array('0.0.0.0', 0);
    return $ip[$type];
}

function jump($url,$msg=null){
	if($msg){
		echo "<script type='text/javascript'>" .
				"alert('$msg');" .
				"location.href='$url';</script>";
	}else{
		echo "<script type='text/javascript'>" .
				"location.href='$url';</script>";
	}
	exit;
}

function goBack($msg=null){
    $url = $_SERVER['HTTP_REFERER'];
    if($msg){
        echo "<script type='text/javascript'>" .
            "alert('$msg');" .
            "location.href='$url';</script>";
    }else{
        echo "<script type='text/javascript'>" .
            "location.href='$url';</script>";
    }
    exit;
}



?>