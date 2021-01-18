<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once('BaseAction.class.php');

class getcodeAction extends BaseAction
{

    public function getDefaultView()
    {

    }

    public function execute()
    {

        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if ($m) {
            $this->$m();
        }

    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

    // 获取用户会话密钥
    public function code()
    {
        $sql = "select * from lkt_config where id=1";
        $r = lkt_gets($sql); 
        if ($r) {
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
        }
        $AccessToken = $this->getAccessToken($appid, $appsecret);
        $res = $this->get_qrcode($AccessToken);
        return $res;
    }


    //获得二维码
    public function get_qrcode($AccessToken)
    {
        // header('content-type:image/jpeg');  测试时可打开此项 直接显示图片

        $request = $this->getContext()->getRequest();
        $path = addslashes($request->getParameter('path'));
        $width = addslashes($request->getParameter('width'));
        $id = addslashes(trim($request->getParameter('id')));
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = lkt_gets($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if (strpos($uploadImg, '../') === false) { // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        } else { // 不存在
            $img = $uploadImg_domain . substr($uploadImg, 2); // 图片路径
        }
        $filename = "ewm" . $id . ".jpg";
        $imgDir = '../LKT/images/';
        //要生成的图片名字
        $newFilePath = $imgDir . $filename;
        if (is_file($newFilePath)) {
            return $filename;
        } else {
            //获取三个重要参数 页面路径  图片宽度  文章ID
            $arr = ["path" => $path, "width" => $width];
            $data = json_encode($arr);
            //把数据转化JSON 并发送
            $url = 'https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=' . $AccessToken;
            //获取二维码API地址
            $da = $this->httpsRequest($url, $data);
            //发送post带参数请求 

            $newFile = fopen($newFilePath, "w"); //打开文件准备写入
            fwrite($newFile, $da); //写入二进制流到文件
            fclose($newFile); //关闭文件
            //拼接服务器URL 返回
            $url = $img . $filename;
            return $filename;
        }

    }


    /** 制作商品分享带参数二维码
     * 　@param $product_img string 产品图片　
     * @param $qr_code string 二维码图片
     * @param $logo float logo图片   　
     * @param $price string 价格
     * @param $yprice string 原价
     * @param $bottom_img float 底图
     * @param $product_title string 产品标题
     * @param $path string 分享的路径
     * @param $id string 分享的id
     * @param $type string 海报的类型-1商城海报 2小店海报 3商品海报 4关注海报
     * return json
     */

    public function product_share()
    {
        $request = $this->getContext()->getRequest();
        $product_img = addslashes($request->getParameter('product_img_path'));
        $str_r = trim(strrchr($product_img, '/'), '/');
        if ($str_r) {
            $product_img = $str_r;
        }
        $type = addslashes($request->getParameter('type'));
        $product_title = addslashes($request->getParameter('product_title'));
        if (strlen($product_title) > 18) {
            $product_title = mb_substr($product_title, 0, 18, 'utf-8') . '...';
        }
        $pid = addslashes($request->getParameter('pid'));
        $price = addslashes($request->getParameter('price'));
        $yprice = addslashes($request->getParameter('yprice'));
        $head = addslashes($request->getParameter('head'));
        $regenerate = addslashes(trim($request->getParameter('regenerate')));

        $path = addslashes($request->getParameter('path'));
        $id = addslashes($request->getParameter('id'));

        // 生成密钥
        $utoken = '';
        $str = 'QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890';
        for ($i = 0; $i < 32; $i++) {
            $utoken .= $str[rand(0, 61)];
        }

        $usql = "select img_token from lkt_user where user_id = '$id' ";
        $uur = lkt_gets($usql);
        $lu_token = isset($uur[0]) ? md5($uur[0]->img_token) : md5($id);
        $img_token = isset($uur[0]) ? $uur[0]->img_token : false;

        //定义固定分享图片储存路径 以便删除
        $imgDir = '/product_share_img/';
        $sql = "select * from lkt_config where id=1";
        $r = lkt_gets($sql);
        if ($r) {
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $uploadImg_domain = $r[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r[0]->uploadImg; // 图片上传位置
            if (strpos($uploadImg, '../') === false) { // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            } else {
                // 不存在
                $img = $uploadImg_domain . substr($uploadImg, 2); // 图片路径
            }
            $product_img = $uploadImg . $product_img;
            $font_file_path = dirname(dirname(MO_WEBAPP_DIR));
            $font_file = $font_file_path . '/LKT/style/font/';
        }

        $this->mkFolder($uploadImg . $imgDir);
        $tkt_sql = "select * from lkt_extension where type ='$type' and isdefault='1' ";
        $tkt_r = lkt_gets($tkt_sql);

        $pic = $lu_token . '-' . $type . '-' . $pid . '-ewm.jpg';
        if ($regenerate || !$img_token) {
            @unlink($uploadImg . $imgDir . $pic);
            $lu_token = md5($utoken);
            $sql = "update lkt_user set img_token = '$utoken' where user_id = '$id' ";
            lkt_execute($sql);
            $pic = $lu_token . '-' . $type . '-ewm.jpg';
        }

        if (is_file($uploadImg . $imgDir . $pic)) {
            $url = $img . $imgDir . $pic;
            $waittext = isset($tkt_r[0]->waittext) ? $tkt_r[0]->waittext : '#fff';
            echo json_encode(array('status' => true, 'url' => $url, 'waittext' => $waittext));
            exit;
        }

        $waittext = isset($tkt_r[0]->waittext) ? $tkt_r[0]->waittext : '#fff';

        if (empty($tkt_r)) {
            $tkt_sql = "select * from lkt_extension where type ='$type'";
            $tkt_r = lkt_gets($tkt_sql);
            if (empty($tkt_r)) {
                $url = $img . $imgDir . 'img.jpg';
                echo json_encode(array('status' => true, 'url' => $url));
                exit;
            }
        }

        if ($type == '1') {
            //文章
            if (!empty($r)) {
                $bottom_img = $uploadImg . $tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }

        } else if ($type == '2') {
            //红包
            if (!empty($r)) {
                $bottom_img = $uploadImg . $tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
        } else if ($type == '3') {
            //商品
            if (!empty($r)) {
                $bottom_img = $uploadImg . $tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
        } else {
            //分销
            if (!empty($r)) {
                $bottom_img = $uploadImg . $tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
        }


        //创建底图   
        $dest = $this->create_imagecreatefromjpeg($bottom_img);
        $datas = json_decode($data);
        foreach ($datas as $key => $value) {
            $data = [];
            foreach ($value as $k => $v) {
                if ($k == 'left' || $k == 'top' || $k == 'width' || $k == 'height' || $k == 'size') {
                    $v = intval(str_replace('px', '', $v)) * 2;
                }
                $data[$k] = $v;
            }

            if ($value->type == 'head') {
                $this->write_img($dest, $data, $head);
            } else if ($value->type == 'nickname') {
                $dest = $this->write_text($dest, $data, $product_title, $font_file);
            } else if ($value->type == 'qr') {
                $AccessToken = $this->getAccessToken($appid, $appsecret);
                $share_qrcode = $this->get_share_qrcode($path, $value->width, $id, $AccessToken);
                $dest = $this->write_img($dest, $data, $share_qrcode);
            } else if ($value->type == 'img') {
                if ($value->src) {
                    $imgs = $uploadImg . $value->src;
                    $dest = $this->write_img($dest, $data, $imgs);
                }
            } else if ($value->type == 'title') {
                //标题
                $dest = $this->write_text($dest, $data, $product_title, $font_file);
            } else if ($value->type == 'thumb') {
                //商品图合成
                $dest = $this->write_img($dest, $data, $product_img);
            } else if ($value->type == 'marketprice') {
                //价格
                $marketprice = '￥' . $price;
                $dest = $this->write_text($dest, $data, $marketprice, $font_file);
            } else if ($value->type == 'productprice') {
                //原价
                $productprice = '￥' . $yprice;
                $dest = $this->write_text($dest, $data, $productprice, $font_file);
                $shanchuxian = '—';
                for ($i = 0; $i < (strlen($productprice) - 3) / 4; $i++) {
                    $shanchuxian .= $shanchuxian;
                }
                $dest = $this->write_text($dest, $data, $shanchuxian, $font_file);

            }
        }


        imagejpeg($dest, $uploadImg . $imgDir . $pic);
        $url = $img . $imgDir . $pic;
        echo json_encode(array('status' => true, 'url' => $url, 'waittext' => $waittext));
        exit;
    }


    //创建图片 根据类型
    public function create_imagecreatefromjpeg($pic_path)
    {
        $pathInfo = pathinfo($pic_path);
        $res = getimagesize($pic_path);
        $res=explode('/',$res['mime']);
        $ext=$res[1];
        if (array_key_exists('extension', $pathInfo)) {
            switch (strtolower($ext)) {
                case 'jpg':
                case 'jpeg':
                    $imagecreatefromjpeg = 'imagecreatefromjpeg';
                    break;
                case 'png':
                    $imagecreatefromjpeg = 'imagecreatefrompng';
                    break;
                case 'gif':
                default:
                    $imagecreatefromjpeg = 'imagecreatefromstring';
                    $pic_path = file_get_contents($pic_path);
                    break;
            }
        } else {
            $imagecreatefromjpeg = 'imagecreatefromstring';
            $pic_path = file_get_contents($pic_path);
        }

        return $imagecreatefromjpeg($pic_path);
    }

    public function getRealData($data)
    {
        $data['left'] = intval(str_replace('px', '', $data['left'])) * 2;
        $data['top'] = intval(str_replace('px', '', $data['top'])) * 2;
        $data['width'] = intval(str_replace('px', '', $data['width'])) * 2;
        $data['height'] = intval(str_replace('px', '', $data['height'])) * 2;

        if ($data['size']) {
            $data['size'] = intval(str_replace('px', '', $data['size'])) * 2;
        }
        if ($data['src']) {
            $data['src'] = tomedia($data['src']);
        }

        return $data;
    }

    public function write_img($target, $data, $imgurl)
    {
        $img = $this->create_imagecreatefromjpeg($imgurl);
        $w = imagesx($img);
        $h = imagesy($img);
        imagecopyresized($target, $img, $data['left'], $data['top'], 0, 0, $data['width'], $data['height'], $w, $h);
        imagedestroy($img);
        return $target;
    }

    function autowrap($fontsize, $angle, $fontface, $string, $width)
    {
        // 参数分别是 字体大小, 角度, 字体名称, 字符串, 预设宽度
        $content = "";
        // 将字符串拆分成一个个单字 保存到数组 letter 中
        preg_match_all("/./u", $string, $arr);
        $letter = $arr[0];
        foreach ($letter as $l) {
            $teststr = $content . $l;
            $testbox = imagettfbbox($fontsize, $angle, $fontface, $teststr);
            if (($testbox[2] > $width) && ($content !== "")) {
                $content .= PHP_EOL;
            }
            $content .= $l;
        }
        return $content;
    }

    public function write_text($dest, $data, $string, $fontfile)
    {

        if ($data['type'] == 'title') {
            $width = imagesx($dest) - $data['left'] * 2;
        } else {
            $width = imagesx($dest) * 2;
        }

        $font_file = $fontfile . 'msyh.ttf';
        $colors = $this->hex2rgb($data['color']);
        $color = imagecolorallocate($dest, $colors['red'], $colors['green'], $colors['blue']);//背景色
        $string = $this->autowrap($data['size'], 0, $font_file, $string, $width);
        $fontsize = $data['size'];
        imagettftext($dest, $fontsize, 0, $data['left'], $data['top'], $color, $font_file, $string);
        return $dest;
    }

    function hex2rgb($colour)
    {
        if ($colour[0] == '#') {
            $colour = substr($colour, 1);
        }
        if (strlen($colour) == 6) {
            list($r, $g, $b) = array(
                $colour[0] . $colour[1],
                $colour[2] . $colour[3],
                $colour[4] . $colour[5]
            );
        } elseif (strlen($colour) == 3) {
            list($r, $g, $b) = array(
                $colour[0] . $colour[0],
                $colour[1] . $colour[1],
                $colour[2] . $colour[2]
            );
        } else {
            return false;
        }
        $r = hexdec($r);
        $g = hexdec($g);
        $b = hexdec($b);
        return array(
            'red' => $r,
            'green' => $g,
            'blue' => $b
        );
    }

    //将颜色代码转rgb
    function wpjam_hex2rgb($hex)
    {
        $hex = str_replace("#", "", $hex);

        if (strlen($hex) == 3) {
            $r = hexdec(substr($hex, 0, 1) . substr($hex, 0, 1));
            $g = hexdec(substr($hex, 1, 1) . substr($hex, 1, 1));
            $b = hexdec(substr($hex, 2, 1) . substr($hex, 2, 1));
        } else {
            $r = hexdec(substr($hex, 0, 2));
            $g = hexdec(substr($hex, 2, 2));
            $b = hexdec(substr($hex, 4, 2));
        }

        return array($r, $g, $b);
    }

    //获得二维码
    public function get_share_qrcode($path, $width, $id, $AccessToken)
    {
        $request = $this->getContext()->getRequest();
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = lkt_gets($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if (strpos($uploadImg, '../') === false) { // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        } else { // 不存在
            $img = $uploadImg_domain . substr($uploadImg, 2); // 图片路径
        }
        $pid = addslashes($request->getParameter('pid'));
        $path_name = str_replace('/', '_', $path);
        $filename = $path_name . '_share_' . $id . '_' . $pid . '.jpeg';///
        $imgDir = '/';
        $width = 430;
        //要生成的图片名字
        $newFilePath = $uploadImg . $imgDir . $filename;
        if (is_file($newFilePath)) {
            return $newFilePath;
        } else {
            $scene = addslashes($request->getParameter('scene'));
            //获取三个重要参数 页面路径  图片宽度  文章ID
            $arr = ["path" => $path . '?' . $scene, "width" => $width];
            $data = json_encode($arr);
            //把数据转化JSON 并发送
            $url = 'https://api.weixin.qq.com/wxa/getwxacode?access_token=' . $AccessToken;
            $da = $this->httpsRequest($url, $data);
            //发送post带参数请求 
            $newFile = fopen($newFilePath, "w"); //打开文件准备写入
            fwrite($newFile, $da); //写入二进制流到文件
            fclose($newFile); //关闭文件
            return $newFilePath;
        }

    }

    function httpsRequest($url, $data = null)
    {
        // 1.初始化会话
        $ch = curl_init();
        // 2.设置参数: url + header + 选项
        // 设置请求的url
        curl_setopt($ch, CURLOPT_URL, $url);
        // 保证返回成功的结果是服务器的结果
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        if (!empty($data)) {
            // 发送post请求
            curl_setopt($ch, CURLOPT_POST, 1);
            // 设置发送post请求参数数据
            curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
        }
        // 3.执行会话; $result是微信服务器返回的JSON字符串
        $result = curl_exec($ch);
        // 4.关闭会话
        curl_close($ch);
        return $result;
    }


    public function getToken()
    {
        $sql = "select * from lkt_config where id=1";
        $r = lkt_gets($sql);
        if ($r) {
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $company = $r[0]->company; // 公司名称
            $AccessToken = $this->getAccessToken($appid, $appsecret);
            echo json_encode(array('access_token' => $AccessToken, 'company' => $company));
            exit();
        }
    }

    public function Send_Prompt()
    {
        $request = $this->getContext()->getRequest();
        $openid = addslashes(trim($request->getParameter('user_id')));
        $form_id = addslashes(trim($request->getParameter('form_id')));
        $page = addslashes(trim($request->getParameter('page')));
        $f_price = addslashes(trim($request->getParameter('price')));
        $f_sNo = addslashes(trim($request->getParameter('order_sn')));
        $f_pname = addslashes(trim($request->getParameter('f_pname')));
        $time = addslashes(trim($request->getParameter('time')));
        $time = $time ? $time : date("Y-m-d H:i:s", time());
        $sql = "select * from lkt_config where id=1";
        $r = lkt_gets($sql);
        if ($r) {
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $address = $r[0]->company;

            $time = array('value' => $time, "color" => "#173177");
            $f_pname = array('value' => $f_pname, "color" => "#173177");
            $f_sNo = array('value' => $f_sNo, "color" => "#173177");
            $f_price = array('value' => $f_price, "color" => "#173177");
            $o_data = array('keyword1' => $f_sNo, 'keyword2' => $time, 'keyword3' => $f_pname, 'keyword4' => $f_price, 'keyword5' => $time);

            $AccessToken = $this->getAccessToken($appid, $appsecret);
            $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' . $AccessToken;
            $sql = "select * from lkt_notice where id = '1'";
            $r = lkt_gets($sql);
            $template_id = $r[0]->order_success;
            $data = json_encode(array('access_token' => $AccessToken, 'touser' => $openid, 'template_id' => $template_id, 'form_id' => $form_id, 'page' => $page, 'data' => $o_data));
            $da = $this->httpsRequest($url, $data);
            echo json_encode($da);

            exit();
        }

    }

    function getAccessToken($appID, $appSerect)
    {
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

    //生成推广图片
    function getPromotion($name, $ditu, $x, $y, $wx_id, $kuan = 300)
    {
        $sql_w = "select user_id from lkt_user where wx_id='" . $wx_id . '\' ';
        $r_w = lkt_gets($sql_w);
        //信息准备
        $userid = $r_w[0]->user_id;
        $dest = imagecreatefromjpeg($ditu);  //底图1
        $dirName = '../LKT/images/';
        $headfilename = 'logo.jpg';
        $filename = '';
        //取得二维码图片文件名称
        $erweima = $this->code();

        /*带参数二维码图片是否已经存在*/
        if (file_exists($dirName . $erweima)) {
            $filename = $erweima;
        } else {
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'GET');
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_URL, $filename);
            ob_start();
            curl_exec($ch);
            $headfile = ob_get_contents();
            ob_end_clean();
            if (!file_exists($dirName)) {
                mkdir($dirName, 0777, true);
            }
            //保存文件
            $res = fopen($dirName . $erweima, 'a');
            fwrite($res, $headfile);
            fclose($res);
            $filename = $erweima;
        }
        /*二维码组合底图1*/
        $src = imagecreatefromjpeg($dirName . $filename);
        list($width, $height, $type, $attr) = getimagesize($dirName . $filename);
        $image = imagecreatetruecolor($kuan, $kuan);
        imagecopyresampled($image, $src, 0, 0, 0, 0, $kuan, $kuan, $width, $height);
        imagecopymerge($dest, $image, $x, $y, 0, 0, $kuan, $kuan, 100);
        // /*end 二维码*/$x, $y,$wx_id 20, 580

        // /* 图片组合完成 保存图片 */
        $pic = $userid . $name . 'tui.jpg';
        imagejpeg($dest, $dirName . $pic);
        $url = 'http://' . $_SERVER['HTTP_HOST'] . '/LKT/images/' . $pic;/* end 保存*/
        return $url;
    }



    function mkFolder($path)
    {
        if (!is_readable($path)) {
            is_file($path) or mkdir($path, 0700);
        }
    }


}

