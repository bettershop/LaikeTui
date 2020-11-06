<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter("id")); 
        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
       
        $logo = $r[0]->logo; // 公司logo
        $company = $r[0]->company; // 公司名称
        $appid = $r[0]->appid; // 小程序id
        $appsecret = $r[0]->appsecret; // 小程序密钥
        $domain = $r[0]->domain; // 小程序域名
        $uploadImg_domain = $r[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        $upload_file = $r[0]->upload_file; // 软件上传位置
        $ip = $r[0]->ip; // ip地址
        
        if($uploadImg == ''){
            $uploadImg = "../LKT/images";
        }
        
        $request->setAttribute('logo', isset($logo) ? $logo : '');
        $request->setAttribute('company', isset($company) ? $company : '');
        $request->setAttribute("appid", isset($appid) ? $appid : '');
        $request->setAttribute('appsecret', isset($appsecret) ? $appsecret : '');
        $request->setAttribute('domain', isset($domain) ? $domain : '');
        $request->setAttribute('uploadImg_domain', isset($uploadImg_domain) ? $uploadImg_domain : '');
        $request->setAttribute('uploadImg', isset($uploadImg) ? $uploadImg : '');
        $request->setAttribute('upload_file', isset($upload_file) ? $upload_file : '');
        $request->setAttribute('ip', isset($ip) ? $ip : '');
        return View :: INPUT;
    }

    public function execute(){

        $request = $this->getContext()->getRequest();
        $db = DBAction::getInstance();
        //取得参数
        $image= addslashes($request->getParameter('image')); // 公司logo
        $oldpic= addslashes($request->getParameter('oldpic')); // 原图片
        $company= addslashes($request->getParameter('company')); // 公司名称
        $appid= addslashes($request->getParameter('appid')); // 小程序id
        $appsecret = addslashes(trim($request->getParameter('appsecret'))); // 小程序密钥
        $domain = addslashes(trim($request->getParameter('domain'))); // 小程序域名
        $uploadImg_domain = addslashes(trim($request->getParameter('uploadImg_domain'))); // 图片上传域名
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置
        $upload_file = addslashes(trim($request->getParameter('upload_file'))); // 软件上传位置
        $ip = addslashes(trim($request->getParameter('ip'))); // ip地址

        if($company == ''){
            echo "<script type='text/javascript'>" .
                "alert('公司名称不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }
        if($appid == ''){
            echo "<script type='text/javascript'>" .
                "alert('小程序id不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }
        if($appsecret == ''){
            echo "<script type='text/javascript'>" .
                "alert('小程序密钥不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }
        if($domain == ''){
            echo "<script type='text/javascript'>" .
                "alert('小程序域名不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }
        if($uploadImg_domain == ''){
            echo "<script type='text/javascript'>" .
                "alert('图片上传域名不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }
        if($uploadImg == ''){
            echo "<script type='text/javascript'>" .
                "alert('图片上传位置不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }
        if($upload_file == ''){
            echo "<script type='text/javascript'>" .
                "alert('软件上传位置不能为空！');" .
                "location.href='index.php?module=system';</script>";
        }else{
            if(is_dir($upload_file) == ''){ // 如果文件不存在
                mkdir($upload_file); // 创建文件
            }
        }
        if(substr($uploadImg,-1) != '/'){
            $uploadImg .= '/';
        }
        
        if($image){
            $image = preg_replace('/.*\//','',$image); // 获取图片名称
            if($image != $oldpic){
                @unlink ($uploadImg.$oldpic);
            }
        }else{
            $image = $oldpic;
        }

        // 更新
        $sql = "update lkt_config set logo = '$image',company = '$company', appid = '$appid',appsecret = '$appsecret', domain = '$domain',uploadImg_domain = '$uploadImg_domain', uploadImg = '$uploadImg',upload_file = '$upload_file', ip = '$ip',modify_date = CURRENT_TIMESTAMP where id = '1'";
        $r = $db->update($sql);

        if($r == -1) {
            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=system';</script>";
            return $this->getDefaultView();
        } else {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=system';</script>";
        }
        
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>