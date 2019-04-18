<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class getcodeAction extends Action {

    public function getDefaultView() {
    	 $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
               //现在时间的前一天
        $datetime = date('Y-m-d H:i:s',time()-24*60*60);
        //现在时间的前七天
        $datetime1 = date('Y-m-d H:i:s',time()-7*24*60*60);
        //删除超过七天的数据
        $delsql = "delete from lkt_draw_user_fromid where lifetime < '$datetime1'";
        $delres = $db -> delete($delsql);
        //过去五分钟
        $oldtime = date('Y-m-d H:i:s',time()-5*60-24*60*60);
        $sql01 = "select * from lkt_draw where end_time >='$oldtime'and end_time<'$datetime'";//查询符合条件的活动ID
        $re01 = $db -> select($sql01);
        // print_r($sql01);die;
        if(!empty($re01)){
            foreach ($re01 as $key01 => $value01) {
               $draw_id = $value01->id;//活动ID
               $name = $value01->name;//活动名称
               $draw_brandid = $value01->draw_brandid;//活动名称
               $sql03 = "select product_title from lkt_product_list where id='$draw_brandid'";//通过活动ID查询活动人员
               $re03 = $db -> select($sql03);
                $product_title = $re03[0]->product_title;//活动商品
               $sql02 = "select * from lkt_draw_user where draw_id='$draw_id'";//通过活动ID查询活动人员
               $re02 = $db -> select($sql02);
               
                if(!empty($re02)){//存在参加活动的订单
                    foreach ($re02 as $key02 => $value02) {
                        $id = $value02->id;//ID
                        $user_id = $value02->user_id;//用户ID

                        $sql04 = "select wx_id from lkt_user where user_id='$user_id'";//查询活动人员wx_id
                        $re04 = $db -> select($sql04);
                        $openid =$re04[0]->wx_id;
                        $sql05 = "select fromid from lkt_draw_user_fromid where open_id='$openid' order by lifetime asc ";//查询活动人员wx_id
                        $re05 = $db -> select($sql05);
                        if(!empty($re05)){//存在符合条件的fromid
                            $fromid = $re05[0]->fromid;//状态
                            $lottery_status = $value02->lottery_status;//状态
                            $time = $value02->time;//中奖时间
                            if($lottery_status==4){
                                $rew[$key01][$key02]['lottery_status'] = '抽奖成功' ;
                            }elseif($lottery_status==2){
                                $rew[$key01][$key02]['lottery_status'] = '参团失败' ;
                            }else{
                                $rew[$key01][$key02]['lottery_status'] = '抽奖失败' ;
                            }
                            $rew[$key01][$key02]['product_title'] = $product_title ;
                            $rew[$key01][$key02]['name'] = $name ;
                            $rew[$key01][$key02]['time'] = $time ;
                            $rew[$key01][$key02]['openid'] = $openid ;
                            $rew[$key01][$key02]['fromid'] = $fromid ;
                        }
                    }
                    $this -> Send_success($rew);
                }
            }
        }
        
        // return;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'code'){
            $this->code();
        }elseif($m == 'madeCode'){
        	$this->madeCode();
        }elseif($m == 'createPromotion'){
        	$this->createPromotion();
        }elseif($m == 'Send_Prompt'){
        	$this->Send_Prompt();
        }elseif($m == 'getToken'){
        	$this->getToken();
        }elseif($m == 're_code'){
        	$this->re_code();
        }elseif($m == 're_password'){
        	$this->re_password();
        }elseif($m == 'product_share'){
        	$this->product_share();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 获取用户会话密钥
	public function code(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if($r){
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
        }	
        $AccessToken = $this->getAccessToken($appid, $appsecret);		
		$res = $this->get_qrcode($AccessToken);
        return $res;
	}


	
	//获得二维码
	public function get_qrcode($AccessToken) {
        // header('content-type:image/jpeg');  测试时可打开此项 直接显示图片
        $db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
		$path =$request->getParameter('path');
		$width = $request->getParameter('width');
		$id =trim($request->getParameter('id'));
		// 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
		$filename="ewm".$id.".jpg";///	
	    $imgDir = '../LKT/images/';
	    //要生成的图片名字
	    $newFilePath = $imgDir.$filename;
	    if(is_file($newFilePath)){
	    	return $filename; 
	    }else{
		    //获取三个重要参数 页面路径  图片宽度  文章ID
	        $arr = ["path"=> $path, "width"=>$width];
	        $data = json_encode($arr);
			//把数据转化JSON 并发送
	        $url = 'https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=' . $AccessToken;
	        //获取二维码API地址
	        $da = $this->httpsRequest($url,$data);
			//发送post带参数请求 

	    	$newFile = fopen($newFilePath,"w"); //打开文件准备写入
		    fwrite($newFile,$da); //写入二进制流到文件
		    fclose($newFile); //关闭文件
			//拼接服务器URL 返回
			$url = $img.$filename;
			return $filename;  
	    }
     
	}


    /** 制作商品分享带参数二维码
   　@param $product_img string 产品图片　
     @param $qr_code string 二维码图片
     @param $logo float logo图片   　
     @param $price string 价格
     @param $yprice string 原价
     @param $bottom_img float 底图
     @param $product_title string 产品标题
     @param $path string 分享的路径
     @param $id string 分享的id
     @param $type string 海报的类型-1商城海报 2小店海报 3商品海报 4关注海报
     return json
   */

	public function product_share(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $product_img =$request->getParameter('product_img_path');
        $str_r = trim(strrchr($product_img, '/'),'/');
        if($str_r){
          $product_img = $str_r;
        }
        $type =$request->getParameter('type');
        $product_title =$request->getParameter('product_title');
        if(strlen($product_title)>18){
            $product_title = mb_substr($product_title, 0, 18, 'utf-8').'...';
        } 
        $pid =$request->getParameter('pid');
        $price =$request->getParameter('price');
        $yprice =$request->getParameter('yprice');
        $nickname =$request->getParameter('nickname');
        $head = $request->getParameter('head');
        $regenerate = trim($request->getParameter('regenerate'));
        
        //默认底图和logo
        $logo ='../LKT/images/ditu/logo.png';

        $path = $request->getParameter('path');
        $id = $request->getParameter('id');


        // 生成密钥
        $utoken = '';
        $str = 'QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890';
        for ($i=0;$i<32;$i++){
            $utoken .= $str[rand(0,61)];
        }

        $usql = "select img_token from lkt_user where user_id = '$id'";
        $uur = $db->select($usql);
        $lu_token = isset($uur[0]) ? md5($uur[0]->img_token):md5($id);
        $img_token = isset($uur[0]) ? $uur[0]->img_token:false;

	   //定义固定分享图片储存路径 以便删除
 		$imgDir = 'product_share_img/';
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if($r){
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $uploadImg_domain = $r[0]->uploadImg_domain; // 图片上传域名
	        $uploadImg = $r[0]->uploadImg; // 图片上传位置
	        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
	            $img = $uploadImg_domain . $uploadImg; // 图片路径
	        }else{ 
                // 不存在
	            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
	        }
	        $product_img = $uploadImg.$product_img;
            $font_file_path = dirname(dirname(MO_WEBAPP_DIR));
            $font_file  = $font_file_path.substr($uploadImg,2);
        }

        $tkt_sql = "select * from lkt_extension where type ='$type' and isdefault='1' ";
        $tkt_r = $db->select($tkt_sql);

        $pic = $lu_token.'-'.$type.'-'.$pid.'-ewm.jpg';
        if($regenerate || !$img_token){
             //通过控制access_token 来校验不同二维码
             @unlink ($uploadImg.$imgDir.$pic);
             $lu_token = md5($utoken);
             $sql = "update lkt_user set img_token = '$utoken' where user_id = '$id'";
             $db->update($sql);
             $pic = $lu_token.'-'.$type.'-ewm.jpg';
        }

        if(is_file($uploadImg.$imgDir.$pic)) {
            $url=$img.$imgDir.$pic;
            $waittext = isset($tkt_r[0]->waittext) ? $tkt_r[0]->waittext:'#fff';
            echo json_encode(array('status' => true,'url' => $url,'waittext'=>$waittext));
            exit;
        }

        $waittext = isset($tkt_r[0]->waittext) ? $tkt_r[0]->waittext:'#fff';
  
        if(empty($tkt_r)){
                $tkt_sql = "select * from lkt_extension where type ='$type'";
                $tkt_r = $db->select($tkt_sql);
                if(empty($tkt_r)){
                    $url=$img.$imgDir.'img.jpg';
                    echo json_encode(array('status' => true,'url' => $url));
                    exit;   
                }
        }
        
        if($type == '1'){
            //文章
            if(!empty($r)){
                $bottom_img = $uploadImg.$tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
            
        }else if($type == '2'){
            //红包
            if(!empty($r)){
                $bottom_img = $uploadImg.$tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
        }else if($type == '3'){
            //商品
            if(!empty($r)){
                $bottom_img = $uploadImg.$tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
            // var_dump($bottom_img);exit;
        }else{
            //分销
            if(!empty($r)){
                $bottom_img = $uploadImg.$tkt_r[0]->bg;
                $data = $tkt_r[0]->data;
            }
        }
        //head' style="margin-bottom: 4px">头像
        //nickname' style="margin-bottom: 4px">昵称
        //qr' style="margin-bottom: 4px">二维码
        //img' style="margin-bottom: 4px">图片
        //title' >商品名称 
        //thumb' >商品图片 
        //marketprice' >商品现价    
        //productprice' >商品原价

        //创建底图   
        $dest = $this->create_imagecreatefromjpeg($bottom_img);
        $datas = json_decode($data);
        foreach ($datas as $key => $value) {
            $data = [];
            // $data =$this->getRealData((array)$value);
            foreach ($value as $k => $v) {
                if($k=='left' ||$k=='top' ||$k=='width' ||$k=='height'||$k=='size'){
                   $v =  intval(str_replace('px', '',$v)) * 2;
                }
                $data[$k] = $v;
            }
           if($value->type == 'head'){
                $this->write_img($dest,$data,$head);
           }else if($value->type == 'nickname'){
                $dest = $this->write_text($dest,$data,$product_title,$font_file);
           }else if($value->type == 'qr'){
                $AccessToken = $this->getAccessToken($appid, $appsecret);
                $share_qrcode = $this->get_share_qrcode($path,$value->width,$id,$AccessToken);
                // var_dump($dest,$data,$share_qrcode);exit;
                $dest =  $this->write_img($dest,$data,$share_qrcode);
           }else if($value->type == 'img'){
                if($value->src){
                   $imgs =  $uploadImg.$value->src;
                   $dest =  $this->write_img($dest,$data,$imgs);                 
                }
           }else if($value->type == 'title'){
                //标题
                $dest = $this->write_text($dest,$data,$product_title,$font_file);
           }else if($value->type == 'thumb'){
                //商品图合成
                $dest = $this->write_img($dest,$data,$product_img);
           }else if($value->type == 'marketprice'){
                //价格
                $product_title = '￥'.$price;
                $dest = $this->write_text($dest,$data,$product_title,$font_file);  
           }else if($value->type == 'productprice'){
                //原价
                $product_title = '￥'.$yprice;
                $dest = $this->write_text($dest,$data,$product_title,$font_file);
                $shanchuxian = '—';
                for ($i=0; $i < (strlen($product_title)-3)/4;$i++) { 
                    $shanchuxian .= $shanchuxian ;
                }
                $dest = $this->write_text($dest,$data,$shanchuxian,$font_file);

           }
        }

		// header("content-type:image/jpeg");
		imagejpeg($dest,$uploadImg.$imgDir.$pic);
		$url=$img.$imgDir.$pic;
		echo json_encode(array('status' => true,'url' => $url,'waittext'=>$waittext));
		exit;
	}
	//创建图片 根据类型
	public function create_imagecreatefromjpeg($pic_path)
	{			
		  $pathInfo = pathinfo($pic_path);
          if(array_key_exists('extension',$pathInfo)){
              switch( strtolower($pathInfo['extension']) ) { 
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
          }else{
            $imagecreatefromjpeg = 'imagecreatefromstring'; 
            $pic_path = file_get_contents($pic_path); 
          }
		  $im = $imagecreatefromjpeg($pic_path);
		  return $im;
	}

    public function getRealData($data)
    {
        $data['left']   = intval(str_replace('px', '', $data['left'])) * 2;
        $data['top']    = intval(str_replace('px', '', $data['top'])) * 2;
        $data['width']  = intval(str_replace('px', '', $data['width'])) * 2;
        $data['height'] = intval(str_replace('px', '', $data['height'])) * 2;
        
        if($data['size']){
            $data['size']   = intval(str_replace('px', '', $data['size'])) * 2;
        }
        if($data['src']){
            $data['src']    = tomedia($data['src']);
        }
        
        return $data;
    }
	
    public function write_img($target, $data, $imgurl)
    {   
        $img = $this->create_imagecreatefromjpeg($imgurl);
        $w   = imagesx($img);
        $h   = imagesy($img);
        imagecopyresized($target, $img, $data['left'], $data['top'], 0, 0, $data['width'], $data['height'], $w, $h);
        imagedestroy($img);
        return $target;
    }

    function autowrap($fontsize, $angle, $fontface, $string, $width) {
        // 参数分别是 字体大小, 角度, 字体名称, 字符串, 预设宽度
        $content = "";
        // 将字符串拆分成一个个单字 保存到数组 letter 中
        preg_match_all("/./u", $string, $arr); 
        $letter = $arr[0];
        foreach($letter as $l) {
            $teststr = $content.$l;
            // var_dump($fontsize, $angle, $fontface, $teststr);
            $testbox = imagettfbbox($fontsize, $angle, $fontface, $teststr);
            if (($testbox[2] > $width) && ($content !== "")) {
                $content .= PHP_EOL;
            }
            $content .= $l;
        }
        return $content;
    }

    public function write_text($dest, $data, $string,$fontfile)
    {
        
        if($data['type'] == 'title'){
            $width = imagesx($dest)-$data['left']*2;
        }else{
            $width = imagesx($dest)*2;
        }

        $font_file = $fontfile.'simhei.ttf';

        // var_dump($font_file);
        $colors = $this->hex2rgb($data['color']);
        $color  = imagecolorallocate($dest,$colors['red'], $colors['green'], $colors['blue']);//背景色
        $string = $this->autowrap($data['size'], 0, $font_file, $string,$width);
        $fontsize = $data['size'];
        imagettftext($dest,$fontsize,0,$data['left'], $data['top'],$color,$font_file,$string);
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
    function wpjam_hex2rgb($hex) {
       $hex = str_replace("#", "", $hex);
     
       if(strlen($hex) == 3) {
          $r = hexdec(substr($hex,0,1).substr($hex,0,1));
          $g = hexdec(substr($hex,1,1).substr($hex,1,1));
          $b = hexdec(substr($hex,2,1).substr($hex,2,1));
       } else {
          $r = hexdec(substr($hex,0,2));
          $g = hexdec(substr($hex,2,2));
          $b = hexdec(substr($hex,4,2));
       }
     
       return array($r, $g, $b);
    }

    //获得二维码
    public function get_share_qrcode($path,$width,$id,$AccessToken) {
        // header('content-type:image/jpeg');  测试时可打开此项 直接显示图片
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        $pid = $request->getParameter('pid');
        $path_name = str_replace('/','_',$path);
        $filename= $path_name.'_share_'.$id.'_'.$pid.'.jpeg';///   
        $imgDir = 'product_share_img/';
        $width = 430;
        //要生成的图片名字
        $newFilePath = $uploadImg.$imgDir.$filename;
        if(is_file($newFilePath)){
            return $newFilePath; 
        }else{
            $scene = $request->getParameter('scene');
            //获取三个重要参数 页面路径  图片宽度  文章ID
            //--B $arr = ["page"=> $path, "width"=>$width,'scene'=>$scene];
            //--A
            $arr = ["path"=> $path.'?'.$scene, "width"=>$width];
            $data = json_encode($arr);
            //把数据转化JSON 并发送
            // 接口A: 适用于需要的码数量较少的业务场景 接口地址：
            $url = 'https://api.weixin.qq.com/wxa/getwxacode?access_token=' . $AccessToken;
            // 接口B：适用于需要的码数量极多的业务场景
            // $url = 'https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=' . $AccessToken;
            // 接口C：适用于需要的码数量较少的业务场景
            // $url = 'https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=' . $AccessToken;
            //获取二维码API地址

            $da = $this->httpsRequest($url,$data);
            //发送post带参数请求 
            // var_dump($da);exit;
            // header('content-type:image/jpeg');
            // echo $da;exit;
            $newFile = fopen($newFilePath,"w"); //打开文件准备写入
            fwrite($newFile,$da); //写入二进制流到文件
            fclose($newFile); //关闭文件
            return $newFilePath;  
        }
     
    }

	function httpsRequest($url, $data=null) {
		// 1.初始化会话
		$ch = curl_init();
		// 2.设置参数: url + header + 选项
		// 设置请求的url
		curl_setopt($ch, CURLOPT_URL, $url);
		// 保证返回成功的结果是服务器的结果
		curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,FALSE);
        curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,FALSE);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		if(!empty($data)) {
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
	//抽奖通知
	public function Send_success($rew){
            $db = DBAction::getInstance();
            $request = $this->getContext()->getRequest();
            $sql = "select * from lkt_config where id=1";
            $r = $db->select($sql);
            if($r){
              $appid = $r[0]->appid; // 小程序唯一标识
                $appsecret = $r[0]->appsecret; // 小程序的 app secret
                $AccessToken = $this->getAccessToken($appid, $appsecret);
                $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token='.$AccessToken;
            }

            foreach ($rew as $k => $v) {

                foreach ($v as $key => $value) {
                    $lottery_status = $value[''] ;
                    $product_title = $value['product_title'] ;
                    $name = $value['name'] ;
                    $time = $value['time'] ;
                    $openid = $value['openid'] ;
                    $fromid = $value['fromid'] ;
                    $lottery_status = $value['lottery_status'] ;
                    $data = array();
                    $data['access_token'] = $AccessToken;
                    $data['touser'] = $openid;
                    $sql = "select * from lkt_notice where id = '1'";
                    $r = $db->select($sql);
                    $template_id = $r[0]->lottery_res;
                    $data['template_id'] = $template_id;
                    $data['form_id'] = $fromid;
                    $minidata = array('keyword1' => array('value' => $name,'color' => "#173177"),'keyword2' => array('value' => $product_title,'color' => "#173177"),'keyword3' => array('value' => $time,'color' => "#173177"),'keyword4' => array('value' => $lottery_status,'color' => "#173177"));
                    $data['data'] = $minidata;
                    $data = json_encode($data);
                    
                    $da = $this->httpsRequest($url,$data);
                    $delsql = "delete from lkt_draw_user_fromid where open_id='$openid' and fromid='$fromid'";
                    $db -> delete($delsql);          
                    var_dump(json_encode($da));
                }
            }
    }
	public function getToken(){
		$db = DBAction::getInstance();
	    $request = $this->getContext()->getRequest();
	    $sql = "select * from lkt_config where id=1";
	    $r = $db->select($sql);
	    if($r){
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $company = $r[0]->company; // 公司名称
            $AccessToken = $this->getAccessToken($appid, $appsecret);
            echo json_encode(array('access_token' => $AccessToken,'company' => $company));
            exit();
        }
	}

	public function Send_Prompt()
	{
			$db = DBAction::getInstance();
	        $request = $this->getContext()->getRequest();
	        $openid = trim($request->getParameter('user_id'));  //--
	        $form_id = trim($request->getParameter('form_id'));//--
	        $page = trim($request->getParameter('page'));      //--
	        // $oid = trim($request->getParameter('oid'));
	        $f_price = trim($request->getParameter('price'));
	        $f_sNo = trim($request->getParameter('order_sn'));
	        $f_pname = trim($request->getParameter('f_pname'));
	        $time =date("Y-m-d h:i:s",time());
	        $sql = "select * from lkt_config where id=1";
	        $r = $db->select($sql);
	        if($r){
	            $appid = $r[0]->appid; // 小程序唯一标识
	            $appsecret = $r[0]->appsecret; // 小程序的 app secret
	            $address =  $r[0]->company;

	            $company = array('value' => $address,"color"=> "#173177" );
				$time = array('value' => $time,"color"=> "#173177" );
				$f_pname = array('value' => $f_pname,"color"=> "#173177" );
				$f_sNo = array('value' => $f_sNo,"color"=> "#173177" );
				$f_price = array('value' => $f_price,"color"=> "#173177" );
				$o_data = array('keyword1' => $company,'keyword2' => $time,'keyword3' => $f_pname,'keyword4' => $f_sNo,'keyword5' => $f_price);

	            $AccessToken = $this->getAccessToken($appid, $appsecret);
	            $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token='.$AccessToken;
                $sql = "select * from lkt_notice where id = '1'";
                $r = $db->select($sql);
                $template_id = $r[0]->pay_success;
	            $data = json_encode(array('access_token'=>$AccessToken,'touser'=>$openid,'template_id'=>$template_id,'form_id'=>$form_id,'page'=>$page,'data'=>$o_data));
	            $da = $this->httpsRequest($url,$data);
	            echo json_encode($da);

	            exit();
	        }	
	        
	}
		
	function getAccessToken($appID, $appSerect) {
			$url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=".$appID."&secret=".$appSerect;
			// 时效性7200秒实现
			// 1.当前时间戳
			$currentTime = time();
			// 2.修改文件时间
			$fileName = "accessToken"; // 文件名
            // var_dump(is_file($fileName),$fileName);
			if(is_file($fileName)) {
				$modifyTime = filemtime($fileName);
				if(($currentTime - $modifyTime) < 7200) {
					// 可用, 直接读取文件的内容
					$accessToken = file_get_contents($fileName);
					return $accessToken;
				}
			}
			// 重新发送请求
			$result = $this-> httpsRequest($url);
			$jsonArray = json_decode($result, true);
			// 写入文件
			$accessToken = $jsonArray['access_token'];
			file_put_contents($fileName, $accessToken);
			return $accessToken;
	}
	
    //生成红包文字
    function madeCode(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id =trim($request->getParameter('id'));
        $wx_id =$request->getParameter('openid');
        // 查询公司名称
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        $company = $r[0]->company;
        $instring = $company.'给你发红包啦';

        echo json_encode(array('status'=>1,'text'=>$instring));
        exit();
        return;
    }

     //生成推广图片
	function getPromotion($name,$ditu,$x,$y,$wx_id,$kuan = 300){
		$db = DBAction::getInstance();
		$sql_w = "select user_id from lkt_user where wx_id='".$wx_id.'\'';
        $r_w = $db->select($sql_w);
		//信息准备
		$userid = $r_w[0]->user_id;
		// $dest = imagecreatefromjpeg('../LKT/images/bottom/img01.jpg');  //底图1 http://127.0.0.1:8080/LKT/images/1523861937693.jpeg
		$dest = imagecreatefromjpeg($ditu);  //底图1
		$dirName = '../LKT/images/';
		$headfilename = 'logo.jpg';
		$filename = '';
		//取得二维码图片文件名称
		$erweima = $this->code();

		/*带参数二维码图片是否已经存在*/
		if(file_exists($dirName.$erweima)){
			$filename = $erweima;
		}else{
			$ch = curl_init ();
			curl_setopt ( $ch, CURLOPT_CUSTOMREQUEST, 'GET' );
			curl_setopt ( $ch, CURLOPT_SSL_VERIFYPEER, false );
			curl_setopt ( $ch, CURLOPT_URL, $filename );
			ob_start ();
			curl_exec ( $ch );
			$headfile = ob_get_contents ();
			ob_end_clean ();
			if(!file_exists($dirName)){
			mkdir($dirName, 0777, true);
			}
			//保存文件
			$res = fopen($dirName.$erweima,'a');
			fwrite($res,$headfile);
			fclose($res);
			$filename = $erweima;
		}
		// exit;
		/*二维码组合底图1*/
		$src = imagecreatefromjpeg($dirName.$filename);
		list($width, $height, $type, $attr) = getimagesize($dirName.$filename);
		$image=imagecreatetruecolor($kuan, $kuan);
		imagecopyresampled($image, $src, 0, 0, 0, 0, $kuan, $kuan, $width,$height);
		imagecopymerge($dest, $image,$x, $y, 0, 0, $kuan, $kuan, 100);
		// /*end 二维码*/$x, $y,$wx_id 20, 580
			
		// /* 图片组合完成 保存图片 */
		$pic = $userid.$name.'tui.jpg';
		$res = imagejpeg($dest,$dirName.$pic);
		$url='http://'.$_SERVER['HTTP_HOST'].'/duan/LKT/images/'.$pic;/* end 保存*/
        return $url;
	}
	function createPromotion (){
		$url = [];
		 $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
		$wx_id =$request->getParameter('openid');

		$sql = "select image,x,y,kuan from lkt_extension ";
        $r = $db->select($sql);
        if ($r) {
        	foreach ($r as $key => $value) {
        		$str = $value->image;
        		$img =  str_replace("/duan/","../",$str);
        		$img_url = $this->getPromotion($key+1,$img,$value->x,$value->y,$wx_id,$value->kuan);
        		$url[$key] = array('hpcontent_id' => $key+1,'hp_img_url' => $img_url);
        	}
        }
		echo json_encode(array('status'=>1,'pictures'=>$url));
		exit;
	}


}

?>