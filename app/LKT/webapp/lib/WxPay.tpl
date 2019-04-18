<?php
class WxPayConf_pub
{
	const APPID = '[appid]';
	const MCHID = '[mch_id]';
	const KEY = '[mch_key]';
	const APPSECRET = '[appsecret]';
	const JS_API_CALL_URL = 'http://www.taokeshijia.com/wechat/js_api_call.php';
	const SSLCERT_PATH = '/wechat/webapp/lib/cert/apiclient_cert.pem';
	const SSLKEY_PATH = '/wechat/webapp/lib/cert/apiclient_key.pem';
	const NOTIFY_URL = 'http://www.taokeshijia.com/wechat/notify_url.php';
	const CURL_TIMEOUT = 30;
}
?>