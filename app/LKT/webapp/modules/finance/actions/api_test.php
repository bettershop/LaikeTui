<?php
/*
请确保您的libcurl版本是否支持双向认证，版本高于7.20.1
*/


function curl_post_ssl($url, $vars, $second=30,$aHeader=array()){
	$ch = curl_init();
	//超时时间
	curl_setopt($ch,CURLOPT_TIMEOUT,$second);
	curl_setopt($ch,CURLOPT_RETURNTRANSFER, 1);
	//这里设置代理，如果有的话
	//curl_setopt($ch,CURLOPT_PROXY, '10.206.30.98');
	//curl_setopt($ch,CURLOPT_PROXYPORT, 8080);
	curl_setopt($ch,CURLOPT_URL,$url);
	curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);
	curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,false);
	
	//以下两种方式需选择一种
	
	//第一种方法，cert 与 key 分别属于两个.pem文件
	//默认格式为PEM，可以注释
	// curl_setopt($ch,CURLOPT_SSLCERTTYPE,'PEM');
	// curl_setopt($ch,CURLOPT_SSLCERT,getcwd().'/cert.pem');
	//默认格式为PEM，可以注释
	// curl_setopt($ch,CURLOPT_SSLKEYTYPE,'PEM');
	// curl_setopt($ch,CURLOPT_SSLKEY,getcwd().'/private.pem');
	
	//第二种方式，两个文件合成一个.pem文件
	curl_setopt($ch,CURLOPT_SSLCERT,getcwd().'/all.pem');
 
	if( count($aHeader) >= 1 ){
		curl_setopt($ch, CURLOPT_HTTPHEADER, $aHeader);
	}
 
	curl_setopt($ch,CURLOPT_POST, 1);
	curl_setopt($ch,CURLOPT_POSTFIELDS,$vars);
	$data = curl_exec($ch);
	if($data){
		curl_close($ch);
		return $data;
	}else { 
		$error = curl_errno($ch);
		echo "call faild, errorCode:$error\n"; 
		curl_close($ch);
		return false;
	}
}

$data = curl_post_ssl('https://api.mch.weixin.qq.com/secapi/pay/refund', 'merchantid=1001000');
print_r($data);
