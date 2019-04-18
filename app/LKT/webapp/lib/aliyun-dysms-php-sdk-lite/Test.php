<?php

/*
 * 此文件用于检测DEMO运行环境，无需修改，请在浏览器中运行检测
 *
 * 2017/11/19
 */

echo '<style>li {font-size: 16px;} li.fail {color:red} li.success {color: green} li label{ display:inline-block; width: 15em}</style>';
echo '<h1>执行环境检测</h1>';

function success($title) {
    print_r("<li class=\"success\"><label>{$title}</label>[成功]</li>");
}
function fail($title, $description) {
    print_r("<li class=\"fail\"><label>{$title}</label>[失败] {$description}</li>");
}

if(preg_match("/^\d+\.\d+/", PHP_VERSION, $matches)) {
    $version = $matches[0];
    if($version >= 5.4) {
        success("PHP $version");
    } else {
        fail("PHP $version", "需要PHP>=5.4版本");
        exit(1);
    }
}


try {
    set_error_handler(function () { throw new Exception(); });
    date_default_timezone_get();
    restore_error_handler();
} catch(Exception $e) {
    fail('默认时区设置', '请设置默认时区，如：date_default_timezone_set("Asia/Shanghai")');
}

echo '<h2>依赖扩展检测，如失败请安装相应扩展</h2>';

$dependencies = array (
    'json_encode' => null,
    'curl_init' => null,
    'hash_hmac' => null,
    'simplexml_load_string' => '如果是php7.x + ubuntu环境，请确认php7.x-libxml是否安装，x为子版本号',
);

foreach($dependencies as $funcName => $description) {
    if(!function_exists($funcName)) {
        fail($funcName, $description || '');
    } else {
        success($funcName);
    }
}