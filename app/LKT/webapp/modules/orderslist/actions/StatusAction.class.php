<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

require_once(MO_LIB_DIR . '/ShowPager.class.php');

require_once(MO_LIB_DIR . '/Tools.class.php');



class StatusAction extends Action {



    public function getDefaultView() {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();



        $beizhu = addslashes(trim($request -> getParameter('admin')));

        $sNo = addslashes(trim($request -> getParameter('sNo')));

        $trade = intval($request -> getParameter('trade'));



        $sqll = 'update lkt_order set status='.$trade.'where sNo="'.$sNo.'"';

        $rl = $db -> update($sqll);

        $sqld = 'update lkt_order_detail set status='.$trade.'where sNo="'.$sNo.'"';

        $rd = $db -> update($sqld);

        

        return View :: INPUT;

    }



    public function execute() {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

        $id = intval(trim($request -> getParameter('id')));

        $uid = addslashes(trim($request -> getParameter('uid')));

        $price = (float)(trim($request -> getParameter('price')));

        $p_name = trim($request -> getParameter('p_name'));

        $sNo = trim($request -> getParameter('sNo'));

        $paytype = trim($request -> getParameter('paytype'));

        $trade_no = trim($request -> getParameter('trade_no'));

        

        $refund = $ordernum = date('Ymd').mt_rand(10000,99999).substr(time(),5);

       if($paytype == 'wallet_Pay'){

        $oldmoney = $db -> select("select money from lkt_user where user_id='$uid'");

        $oldmoney = $oldmoney[0] -> money;

        $sql = "update lkt_user set money=money+$price where user_id='$uid'";

        $res = $db -> update($sql);

        $date = date('Y-m-d H:i:s');

        $recordsql = "insert into lkt_record(user_id,money,oldmoney,add_date,event,type) values('$uid',$price,$oldmoney,'$date','".$uid."拼团失败退款',5)";

        $db -> insert($recordsql);

        }else if($paytype == 'wxPay'){

          $price1 = $price*100;

          $res = $this -> wxrefundapi($trade_no,$refund,$price1);

      }

       if($res > 0 || ($res['return_code'] == 'SUCCESS' && $res['result_code'] == 'SUCCESS')){

        $sqld = 'update lkt_order set status=11,refundsNo="'.$refund.'" where id='.$id;

        $resd = $db -> update($sqld);

        $usermsg = $db -> select("select wx_id,user_name from lkt_user where user_id='$uid'");

        if(!empty($usermsg)) $usermsg = $usermsg[0];

        $openid = $usermsg -> wx_id;

        $fromidsql = "select fromid from lkt_user_fromid where open_id='$openid' and id=(select max(id) from lkt_user_fromid where open_id='$openid')";

        $fromid = $db -> select($fromidsql);

        if(!empty($fromid)){
          $fromid1 = $fromid[0] -> fromid;
        }else{
          $fromid1 = '';
        }

        $usermsg -> p_name = $p_name;

        $usermsg -> sNo = $sNo;

        $usermsg -> fromid = $fromid1;

        $usermsg -> uid = $openid;

        $usermsg -> price = (string)$price;

        $sql = "select * from lkt_notice where id = '1'";
        $r = $db->select($sql);
        $template_id = $r[0]->refund_success;  

        if($paytype == 'wallet_Pay'){

            $this -> Send_success($usermsg,$template_id,'pages/user/user','退回到钱包');

         }else if($paytype == 'wxPay'){

            $this -> Send_success($usermsg,$template_id,'pages/user/user','退回到微信');

         }

         echo json_encode(array('status'=>1));exit;

        }

      

    }



    public function Send_success($arr,$template_id,$page,$refundtype){

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

            

                $data = array();

                $data['access_token'] = $AccessToken;

                $data['touser'] = $arr -> uid;

                $data['template_id'] = $template_id;

                $data['form_id'] = $arr -> fromid;

                $data['page'] = $page;

                $price = $arr -> price.'元';

                $minidata = array('keyword1' => array('value' => $arr -> sNo,'color' => "#173177"),'keyword2' => array('value' => $arr -> p_name,'color' => "#173177"),'keyword3' => array('value' => $price,'color' => "#173177"),'keyword4' => array('value' => $refundtype,'color' => "#FF4500"),'keyword5' => array('value' => '拼团失败--退款','color' => "#FF4500"));

                $data['data'] = $minidata;

                $data = json_encode($data);

                

                $da = $this->httpsRequest($url,$data);

                $delsql = "delete from lkt_user_fromid where open_id='$arr->uid' and fromid='$arr->fromid'";

                

                $db -> delete($delsql);             

                  

    }



   /*

   * 发送请求

   　@param $ordersNo string 订单号　

     @param $refund string 退款单号

     @param $price float 退款金额

     return array

   */

    private function wxrefundapi($ordersNo,$refund,$price){

          //通过微信api进行退款流程



          $parma = array(

            'appid'=> 'wx9d12fe23eb053c4f',

            'mch_id'=> '1499256602',

            'nonce_str'=> $this->createNoncestr(),

            'out_refund_no'=> $refund,

            'out_trade_no'=> $ordersNo,

            'total_fee'=> $price,

            'refund_fee'=> $price,

            'op_user_id' => '1499256602',

          );

          $parma['sign'] = $this->getSign($parma);

          $xmldata = $this->arrayToXml($parma);

          $xmlresult = $this->postXmlSSLCurl($xmldata,'https://api.mch.weixin.qq.com/secapi/pay/refund');

          $result = $this->xmlToArray($xmlresult);

          return $result;

    }



    /*

   * 生成随机字符串方法

   */

    protected function createNoncestr($length = 32 ){

         $chars = "abcdefghijklmnopqrstuvwxyz0123456789";

         $str ="";

         for ( $i = 0; $i < $length; $i++ ) {

         $str.= substr($chars, mt_rand(0, strlen($chars)-1), 1);

         }

         return $str;

    }



    /*

   * 对要发送到微信统一下单接口的数据进行签名

   */

  protected function getSign($Obj){

     foreach ($Obj as $k => $v){

       $Parameters[$k] = $v;

     }

     //签名步骤一：按字典序排序参数

     ksort($Parameters);

     $String = $this->formatBizQueryParaMap($Parameters, false);

     //签名步骤二：在string后加入KEY

     $String = $String."&key=td153g1d2f321g23ggrd123g12fd1g22";

     //签名步骤三：MD5加密

     $String = md5($String);

     //签名步骤四：所有字符转为大写

     $result_ = strtoupper($String);

     return $result_;

   }



  /*

   *排序并格式化参数方法，签名时需要使用

   */

  protected function formatBizQueryParaMap($paraMap, $urlencode){

    $buff = "";

    ksort($paraMap);

    foreach ($paraMap as $k => $v){

      if($urlencode){

        $v = urlencode($v);

      }

      //$buff .= strtolower($k) . "=" . $v . "&";

      $buff .= $k . "=" . $v . "&";

    }

    $reqPar;

    if (strlen($buff) > 0){

      $reqPar = substr($buff, 0, strlen($buff)-1);

    }

    return $reqPar;

  }



  //数组转字符串方法

  protected function arrayToXml($arr){

    $xml = "<xml>";

    foreach ($arr as $key=>$val)

    {

      if (is_numeric($val)){

        $xml.="<".$key.">".$val."</".$key.">";

      }else{

         $xml.="<".$key."><![CDATA[".$val."]]></".$key.">";

      }

    }

    $xml.="</xml>";

    return $xml;

  }



  protected function xmlToArray($xml){

    $array_data = json_decode(json_encode(simplexml_load_string($xml, 'SimpleXMLElement', LIBXML_NOCDATA)), true);

    return $array_data;

  }



  //需要使用证书的请求

   private function postXmlSSLCurl($xml,$url,$second=30){

      $ch = curl_init();

      //超时时间

      curl_setopt($ch,CURLOPT_TIMEOUT,$second);

      //这里设置代理，如果有的话

      //curl_setopt($ch,CURLOPT_PROXY, '8.8.8.8');

      //curl_setopt($ch,CURLOPT_PROXYPORT, 8080);

      curl_setopt($ch,CURLOPT_URL, $url);

      curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,FALSE);

      curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,FALSE);

      //设置header

      curl_setopt($ch,CURLOPT_HEADER,FALSE);

      //要求结果为字符串且输出到屏幕上

      curl_setopt($ch,CURLOPT_RETURNTRANSFER,TRUE);

      //设置证书

      //使用证书：cert 与 key 分别属于两个.pem文件

      //默认格式为PEM，可以注释

      $cert = str_replace('lib','filter',MO_LIB_DIR).'/apiclient_cert.pem';

      $key = str_replace('lib','filter',MO_LIB_DIR).'/apiclient_key.pem';

      curl_setopt($ch,CURLOPT_SSLCERTTYPE,'PEM');

      curl_setopt($ch,CURLOPT_SSLCERT, $cert);

      //默认格式为PEM，可以注释

      curl_setopt($ch,CURLOPT_SSLKEYTYPE,'PEM');

      curl_setopt($ch,CURLOPT_SSLKEY, $key);

      //post提交方式

      curl_setopt($ch,CURLOPT_POST, true);

      curl_setopt($ch,CURLOPT_POSTFIELDS,$xml);

      $data = curl_exec($ch);

      //返回结果

      if($data){

        curl_close($ch);

        return $data;

      }

      else {

        $error = curl_errno($ch);

        echo "curl出错，错误码:$error"."<br>";

        curl_close($ch);

        return false;

      }

    }



   private function httpsRequest($url, $data=null) {

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



   private function getAccessToken($appID, $appSerect) {

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

            $result = $this-> httpsRequest($url);

            $jsonArray = json_decode($result, true);

            // 写入文件

            $accessToken = $jsonArray['access_token'];

            file_put_contents($fileName, $accessToken);

            return $accessToken;

    }



    public function getRequestMethods(){

        return Request :: POST;

    }



}



?>