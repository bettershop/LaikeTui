<?php
/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');
require_once(MO_LIB_DIR . '/db.class.php');
/*
功能：API请求基础类
*/
class BaseAction extends Action {
    
    public function getDefaultView() {

    }

    public function execute(){

        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m){
            $this->$m();
        }
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

    //获取系统配置信息
    public function getAppInfo(){
        $img = "";
        $sql = "select * from lkt_config where id = 1";
        $r_1 = lkt_get($sql);
        $uploadImg_domain = $r_1->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }

        $title = $r_1->company;
        $logo = $img.$r_1->logo;
        $appid = $r_1->appid; // 小程序唯一标识
        $appsecret = $r_1->appsecret; // 小程序的 app secret
        
        $array = array();
        $array['imageRootUrl'] = $img;
        $array['appName'] = $title;
        $array['logo'] = $logo;
        $array['uploadImgUrl'] = $uploadImg_domain;
        $array['appid'] = $appid;
        $array['appsecret'] = $appsecret;

        return $array;
    }

    
}

?>