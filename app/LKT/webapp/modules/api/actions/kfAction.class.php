<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');
define("TOKEN", "weixin");
define("APP_ID", "wxa9f7cdfa06b2fd0c");
define("APP_SECRECT", "70a8039ea1b5ee9d78a61ac4aa04117e");



class kfAction extends Action {

  public function getDefaultView() {
    return;
  }

  public function execute(){
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    $m = addslashes(trim($request->getParameter('m')));
    if(isset($_GET['echostr'])) {
      // 2.调用方法
  	  $this->valid();
  	} else {
  	  // 3.响应用户消息
  	  $this->responseMsg();
  	}
    return;
  }

  public function getRequestMethods(){
	 return Request :: GET;
  }
  /**
	 * 功能: 判断并验证签名
	 * @author : shirley
	 */
	public function valid() {
		// 1.读取echostr字段的值
		$echoStr = $_GET["echostr"];
		// 2.调用私有方法, 方法返回值验证成功还是失败
		if($this->checkSignature()) {
			echo $echoStr;
			exit;
		}
	}

	/**
	 * 功能: 通过微信服务器发送给开发者服务器的字段, 判断是否来自于微信服务器
	 * @return Bool true/false
	 */
	private function checkSignature() {
		// 1）将token、timestamp、nonce三个参数进行字典序排序
		$signature = $_GET["signature"];
		$timestamp = $_GET["timestamp"];
		$nonce     = $_GET["nonce"];
		$token     = TOKEN;

		$tmpArray = array($token, $timestamp, $nonce);
		sort($tmpArray);

		// 2）将三个参数字符串拼接成一个字符串进行sha1加密
		$tmpStr = implode($tmpArray); 
		$tmpStr = sha1($tmpStr);

		// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if($tmpStr == $signature) {
			return true;
		} else {
			return false;
		}
	}

  /**
   * 功能: 接收用户消息, 判断用户消息类型
   */
  public function responseMsg() {
    /**
     * 1.接收微信服务器发送的XML字符串
     * PHP7.0+
     * $postStr = file_get_contents("php://input");
     * PHP7.0以下: 
     */
    $postStr = file_get_contents("php://input");
    // 2.将字符串转成对象
    if(!empty($postStr)) {
      $postObj = simplexml_load_string($postStr, "SimpleXMLElement", LIBXML_NOCDATA);
      // 判断消息类型
      $msgType = $postObj->MsgType;
      switch ($msgType) {
        case "text":  // 文本类型
          logger("debug", "TR0010", "[index.php: responseMsg]; 接收到文本消息, 内容是:".$postObj->Content);
          $result = $this->receiveText($postObj);
          break;
        case "image": // 图片类型
           $result = $this->receiveImage($postObj);
           break;
        case "event": //事件类型
           $result = $this->receiveEvent($postObj);
           break;
        default: // 其他类型
           $content = "不是要求的消息类型";
           $result = $this->transmitText($postObj, $content);
           break;
      }
      echo $result;
    }
  }

  /**
  * 功能: 接收事件类型(6种事件类型)
  * @param  SimpleXMLElement $postObj
  * @return String $result: 定制化文本
  */
  private function receiveEvent($postObj) {
    $eventType = $postObj->Event;
    switch ($eventType) {
      case "subscribe": // 关注事件
        $content = $this->handleSubscribe($postObj);
      break;

      case "SCAN": // 针对关注用户, 扫描带参数二维码
        $content = "针对关注用户, 扫描带参数二维码";
      break;

      case "CLICK": // 自定义菜单点击事件
        // 判断点击哪个按钮
        switch ($postObj->EventKey) {
          case "TRKEY_01_02": // 第一级菜单第二个二级菜单按钮
            $content[] = array("Title"=>"接入公众号支付", "Description"=>"描述1", "PicUrl"=>"https://df", "Url"=>"http://php.dajiangsai.org/wechat/deal.php");
            $content[] = array("Title"=>"第二条图文消息", "Description"=>"描述2", "PicUrl"=>"https://df", "Url"=>"http://m.baidu.com");
          break;

          default:
            $content = "点击其他click按钮";
          break;
        }
      break;

      default:
      break;
    }

    if(is_array($content)) {
      $result = $this->transimtNews($postObj, $content);
    } else {
      $result = $this->transmitText($postObj, $content);
    }
    return $result;
  }

  /**
  * 功能: 处理关注事件
  * @param  [SimpleXMLElement] $postObj
  * @return [Mixed]
  */
  private function handleSubscribe($postObj) {
    if(!empty($postObj->EventKey)) { 
      // 针对没有关注用户, 扫描带参数二维码
      // 判断场景参数id // qrscene_123123
      $sceneID = str_replace("qrscene_", "", $postObj->EventKey);
      switch ($sceneID) {
        case "123123":
          $content = "针对没有关注用户, 扫描带参数二维码: 123123";
        break;

        default:
          $content = "针对没有关注用户, 扫描带参数二维码";
        break;
      }
    } else { // 一般关注事件(扫描公众号二维码)
      $content = getUserInfo($postObj, getAccessToken(APP_ID, APP_SECRECT));
    }
    return $content;
  }

  /**
  * 功能: 接收/处理用户文本消息, 返回拼接好的XML
  * @param  SimpleXMLElement $postObj 
  * @return String $result
  */
  private function receiveText($postObj) {
    // 1.如果用户输入的关键词是"图文"
    $keyword = trim($postObj->Content);
    if ($keyword == "图文") {
      // 2.拼接图文XML, 返回
      $contentArray = array();
      $contentArray[] = array("Title"=>"标题1", "Description"=>"描述1", "PicUrl"=>"http://1.shirleytest.applinzi.com/images/596c7157N852de046.jpg", "Url"=>"https://m.baidu.com");
      $contentArray[] = array("Title"=>"标题2", "Description"=>"描述2", "PicUrl"=>"http://1.shirleytest.applinzi.com/images/CW-t-fypceiq6378139.jpg", "Url"=>"https://www.github.com");
      $contentArray[] = array("Title"=>"标题3", "Description"=>"描述3", "PicUrl"=>"http://1.shirleytest.applinzi.com/images/596c7157N852de046.jpg", "Url"=>"https://www.apple.com.cn");
      $contentArray[] = array("Title"=>"标题4", "Description"=>"描述4", "PicUrl"=>"http://1.shirleytest.applinzi.com/images/59bf3c47N91d65c73.jpg", "Url"=>"https://m.dianping.com");
      $result = $this->transimtNews($postObj, $contentArray);
    } else {
      $content = "你发送的是文本消息, 返回你输入内容: ".$postObj->Content;
      $result = $this->transmitText($postObj, $content);
    }
    return $result;
  }
  /**
  * 功能: 处接/处理用户图片消息, 返回拼接好的XML
  * @param  SimpleXMLElement $postObj 
  * @return String $result
  */
  private function receiveImage($postObj) {
    $content = "你发送的是图片消息, 返回图片url: ".$postObj->PicUrl;
    $result = $this->transmitText($postObj, $content);
    return $result;
  }

  /**
  * 功能: 拼接返回文本消息的XML
  * @param  SimpleXMLElement $postObj
  * @param  String $content: 要返回的文本消息内容
  * @return String $result
  */
  private function transmitText($postObj, $content) {
    // 1.解析XML数据
    $toUserName = $postObj->FromUserName;
    $fromUserName = $postObj->ToUserName;
    $createTime = time();
    // 2.拼接JSON数据(文本消息)
    $MsgId = $createTime.'123456';
    $arr = array('ToUserName' => $toUserName, 'FromUserName' => $fromUserName, 'CreateTime' => $createTime, 'MsgType' => "text", 'Content' => $content, 'MsgId' => $MsgId);
    $textStr =json_encode($arr);
    // 调用logger方法
    logger("info", "TR0001", "返回文本消息字符串: ".$textStr);
    // 3.返回
    return $textStr;
  }

  /**
  * 功能: 拼接给定数组个数的图文消息XML
  * @param  SimpleXMLElement $postObj
  * @param  Array $contentArray: 图文消息所需二维数组
  * @return String $result
  */
  private function transimtNews($postObj, $content) {
    if(!is_array($content)) {
      return;
    }

    // 1.拼接第二部分
    $itemStr = "<item>
                <Title><![CDATA[%s]]></Title>
                <Description><![CDATA[%s]]></Description>
                <PicUrl><![CDATA[%s]]></PicUrl>
                <Url><![CDATA[%s]]></Url>
                </item>";
    $tmpStr = "";
    foreach ($content as $item) {
      $tmpStr .= sprintf($itemStr, $item["Title"], $item["Description"], $item["PicUrl"], $item["Url"]); 
    }
    // 2.拼接完整
    $newsStr = "<xml>
                <ToUserName><![CDATA[%s]]></ToUserName>
                <FromUserName><![CDATA[%s]]></FromUserName>
                <CreateTime>%s</CreateTime>
                <MsgType><![CDATA[news]]></MsgType>
                <ArticleCount>%s</ArticleCount>
                <Articles>$tmpStr</Articles>
                </xml>";
    $result = sprintf($newsStr, $postObj->FromUserName, $postObj->ToUserName, time(), count($content));
    return $result;
  }
         /**
	 * 功能: 发送HTTPS GET/POST请求, 返回响应结果
	 * @param  String $url
	 * @param  String $data: post请求的数据; get请求为null
	 * @return String $result
	 */
	function httpsRequest($url, $data=null) {
		// 1.初始化会话
		$ch = curl_init();
		// 2.设置参数: url + header + 选项
		// 设置请求的url
		curl_setopt($ch, CURLOPT_URL, $url);
		// 保证返回成功的结果是服务器的结果
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

	/**
	 * 功能: 返回访问令牌字符串; 新浪云该方法实现在该方法的后面
	 * @param  String $appid
	 * @param  String $appserect
	 * @return String $accessToken
	 */
	function getAccessToken($appID, $appSerect) {
		$url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=".$appID."&secret=".$appSerect;
		// 时效性7200秒实现
		// 1.当前时间戳
		$currentTime = time();
		// 2.修改文件时间
		$fileName = "accessToken"; // 文件名
		if(is_file($fileName)) {
			$modifyTime = filemtime($fileName);
			if(($currentTime - $modifyTime) < 7200) {
				// 可用, 直接读取文件的内容
				$accessToken = file_get_contents($fileName);
				return $accessToken;
			}
		}
		// 重新发送请求
		$result = httpsRequest($url);
		$jsonArray = json_decode($result, true);
		// 写入文件
		$accessToken = $jsonArray['access_token'];
		file_put_contents($fileName, $accessToken);
		return $accessToken;
	}

	/**
	 *   https://xiaochengxu.laiketui.com/duan/LKT/index.php?module=api&action=kf
	 * 功能: 获取用户信息, 返回定制化文本
	 * @param  SimpleXMLElement $postObj
	 * @param  String $accessToken
	 * @return String $result
	 */ 
	function getUserInfo($postObj, $accessToken) {
		// 1.openID
    $openID = $postObj->FromUserName;
    // 2.url
    $url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=".$accessToken."&openid=".$openID."&lang=zh_CN";
    // 3.发送请求
    $jsonStr = httpsRequest($url);
    // 4.json->array
    $userInfoArray = json_decode($jsonStr, true);
    // 5.拼接字符串
    $nameTmpStr = "您好, ".$userInfoArray['nickname'];
    $sexTmpStr = "性别: ".(($userInfoArray['sex'] == 1) ? "男" : (($userInfoArray['sex'] == 2) ? "女" : "未知"));
    $locationTmpStr = "地区: ".$userInfoArray['country']." ".$userInfoArray['province']." ".$userInfoArray['city'];
    $languageTmpStr = "语言: ".(($userInfoArray['language'] == "zh_CN") ? "简体中文": "未知");
    $dateTmpStr = "关注: ".date("Y年m月d日", $userInfoArray['subscribe_time']);
    $finalTmpStr = $nameTmpStr."\n".$sexTmpStr."\n".$locationTmpStr."\n".$languageTmpStr."\n".$dateTmpStr;

    file_put_contents("saestor://1708test/finalTmpStr.txt", $finalTmpStr);

    // 6.返回
    return $finalTmpStr;
	}


	/**
	 * 功能: 给定三个参数, 在error.log文件中写入相应的日志消息
	 * @param  [String] $level     [日志级别(从低到高): debug, info, alert, error, emerg]
	 * @param  [String] $errorCode [错误代码]
	 * @param  [String] $content   [日志消息]
	 */
	function logger($level, $errorCode, $content) {
		// 不允许超过5M
		$maxBytes = 5 * 1024 * 1024; // bytes
		$logFileName = "error.log";
		// 判断文件大小
		if(file_exists($logFileName) && (filesize($logFileName) > $maxBytes)) {
			unlink($logFileName);
		}
		$content = "[".date("Y/m/d H:i:s", time())."] [:".$level."] ".$errorCode.": ".$content."\n";
		file_put_contents($logFileName, $content, FILE_APPEND);
		// 注意: 新浪云SAE上述代码修改成下面的代码, 并且不支持FILE_APPEND追加形式
	}

	/**
	 * 功能: 生成临时/永久二维码图片对应url
	 * @param  [String] $sceneType   [二维码图片类型]
	 * @param  [String] $sceneID     [场景id]
	 * @param  [String] $accessToken [访问令牌]
	 * @return [String]              [二维码图片url]
	 */
	function createQRcode($sceneType, $sceneID, $accessToken) {
		// 1. 根据二维码类型, 给定不同的post数据
		switch ($sceneType) {
			case "QR_SCENE": // 临时
				$postData = '{"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": '.$sceneID.'}}';
				break;
			case "QR_LIMIT_SCENE": // 永久
				$postData = '{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": '.$sceneID.'}}}';
				break;
		}

		// 2. 拼接url, 发送post请求(ticket)
		$url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=".$accessToken;
		$result = httpsRequest($url, $postData);

		// 3. JSON解析, 获取ticket字符串
		$ticketArray = json_decode($result, true);
		$ticketStr = $ticketArray['ticket'];

		// 4. 返回拼接url(二维码图片)
		$qrCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=".urlencode($ticketStr);
		
		return $qrCodeUrl;
	}
}
?>