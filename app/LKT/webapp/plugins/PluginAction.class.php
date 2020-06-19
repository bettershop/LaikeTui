<?php
/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

/*
功能：插件请求基础类
*/
class PluginAction {

    public $context = null;

    function __construct($context=null)
    {
        $this->context = $context;
    }

    //默认执行方法
    public function execute() {
        echo "hello";
        return ;
    }

    //支付的异步回调函数
    public function notify($order=null){
        return $order;
    }


    //确认收货接口
    public function okOrder($order=null){
        return $order;
    }

    //封装请求参数方法
    public function getParameter($string){
        return $_REQUEST[$string];
    }
    
    //获取系统配置信息
    public function getAppInfo(){
        $db = DBAction::getInstance();
        $img = "";
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }

        $title = $r_1[0]->company;
        $logo = $img.$r_1[0]->logo;
        $appid = $r_1[0]->appid; // 小程序唯一标识
        $appsecret = $r_1[0]->appsecret; // 小程序的 app secret
        
        $array = array();
        $array['imageRootUrl'] = $img;
        $array['appName'] = $title;
        $array['logo'] = $logo;
        $array['uploadImgUrl'] = $uploadImg_domain;
        $array['appid'] = $appid;
        $array['appsecret'] = $appsecret;

        return $array;
    }

    //获取小程序全局唯一后台接口调用凭据
    function getAccessToken()
    {
        $config = $this->getAppInfo();
        $appID = $config['appid'];
        $appSerect = $config['appsecret'];
        $url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" . $appID . "&secret=" . $appSerect;
        // 时效性7200秒实现
        // 1.当前时间戳
        $currentTime = time();
        // 2.修改文件时间
        $fileName = "accessToken"; // 文件名
        if (is_file($fileName)) {
            $modifyTime = filemtime($fileName);
            if (($currentTime - $modifyTime) < 7200) {
                // 可用, 直接读取文件的内容
                $accessToken = file_get_contents($fileName);
                return $accessToken;
            }
        }
        // 重新发送请求
        $result = $this->httpsRequest($url);
        $jsonArray = json_decode($result, true);
        // 写入文件
        $accessToken = $jsonArray['access_token'];
        file_put_contents($fileName, $accessToken);
        return $accessToken;
    }

    function httpsRequest($url, $data = null)
    {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        if (!empty($data)) {
            curl_setopt($ch, CURLOPT_POST, 1);
            curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
        }
        $result = curl_exec($ch);
        curl_close($ch);
        return $result;
    }


    public function getContext ()
    {

        return $this->context;

    }

    
}

?>