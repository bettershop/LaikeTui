<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class operationAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id');//活动ID

        $sql04 = "select num,spelling_number,collage_number from lkt_draw where id = $id ";
        $r04 = $db->select($sql04);

        $num = $r04[0]->num;//每个团所需人数
        $spelling_number = $r04[0]->spelling_number;//可抽中奖次数（默认为1）
        $collage_number = $r04[0]->collage_number;//最少开奖团数（默认为1）

        $sql01 = "select role ,count(*) from lkt_draw_user where draw_id = $id group by role having count(*)>=$num";//查出符合参团通知的人
        $r01 = $db->select($sql01);
// print_r($r01);die;
        $sql05 = "select id from lkt_draw_user where draw_id = $id and lottery_status = 4";//查询出中奖人数
        $r05 = $db->selectrow($sql05);
        if(!empty($r01)){
            foreach ($r01 as $key => $value) {
                $role =  $value->role;
                if(!empty($role) ){
                        $sql02 = "select a.*,b.user_name from lkt_draw_user as a,lkt_user as b where draw_id = $id and role = '$role' and a.user_id = b.user_id ";
                        $r02 = $db->select($sql02);

                        if(!empty($r02)){
                            foreach ($r02 as $key02 => $value02) {// 
                                $val_id = $value02->id;
                                $lottery_status = $value02->lottery_status;
                                if($r05 >= $spelling_number && $lottery_status !=4){//当中奖人大于或等于设定的中奖数时就把其他参与抽奖的状态改为未中奖,把订单部分的状态改成订单关闭
                                    $sql06 = "update lkt_draw_user set lottery_status ='3' where id = $val_id ";
                                    $r06 = $db->update($sql06);
                                    $sql07 = "update lkt_order set status ='6' where drawid = $val_id ";
                                    $r07 = $db->update($sql07);
                                    $sql08 = "update lkt_order set r_status ='6' where r_sNo =(select sNo from lkt_order where drawid = $val_id) ";
                                    $r08 = $db->update($sql08);
                                }
                                if($lottery_status ==4){//当抽奖状态为中奖时,把订单部分的状态改成待发货
                                    // $sql06 = "update lkt_draw_user set lottery_status ='3' where id = $val_id ";
                                    // $r06 = $db->update($sql06);
                                    $sql07 = "update lkt_order set status ='1' where drawid = $val_id ";
                                    $r07 = $db->update($sql07);
                                    $sql08 = "update lkt_order set r_status ='1' where r_sNo =(select sNo from lkt_order where drawid = $val_id) ";
                                    $r08 = $db->update($sql08);
                                }
                                $roleid = $value02->role; 
                                $sql06 = "select user_id from lkt_draw_user where id = '$roleid' ";
                                $r06 = $db->select($sql06);
                                $userid = $r06[0]->user_id;
                                $sql04 = "select b.user_name from lkt_user as b where b.user_id = '$userid' ";
                                $r04 = $db->select($sql04);
                                $role_name = $r04[0]->user_name;
                                // print_r($role_name);die;
                                $value02 ->role_name = $role_name;
                                $rr[]= $value02;
                            }
                        }
                        
                    }
                }
            }else{
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('未达到开奖条件，不能抽奖！');" .
                    "location.href='index.php?module=draw';</script>";
                return;

            }
            
            if($r05 >= $spelling_number){//当中奖人大于或等于设定的中奖数时就把其他未参与抽奖的状态改为参团失败
                $sql06 = "update lkt_draw_user set lottery_status ='2' where draw_id = $id and lottery_status !='4' and lottery_status !='3' ";
                $r06 = $db->update($sql06);
                $this -> wxrefundapi($id);
            }
        
        $sql03 = "select name from lkt_draw where id = $id ";
        $r03 = $db->select($sql03);
        $name = $r03[0]->name;
        // print_r($rr);
        // print_r($name);
        // print_r($spelling_number);
        // print_r($r05);
        // die;
        $request->setAttribute("list",$rr);
        $request->setAttribute("name",$name);
        $request->setAttribute("spelling_number",$spelling_number);
        $request->setAttribute("r05",$r05);
        return View :: INPUT;
    }

    /*
   * 发送请求
   　@param $id string 订单号　
     return array
   */
    private function wxrefundapi($id){
          //通过微信api进行退款流程
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
         $sql = "select m.*,t.p_name from (select d.id,d.user_id,o.sNo,o.z_price,o.trade_no,o.pay from lkt_draw_user as d left join lkt_order as o on d.id=o.drawid where d.draw_id='$id' and lottery_status =2) as m left join lkt_order_details as t on m.sNo=t.r_sNo";
         $res = $db -> select($sql);

    if($res) {   
     foreach ($res as $k => $v) {
        if($v -> pay == 'wxPay'){
          $refund = date('Ymd').mt_rand(10000,99999).substr(time(),5);
          $parma = array(
            'appid'=> 'wx9d12fe23eb053c4f',
            'mch_id'=> '1499256602',
            'nonce_str'=> $this->createNoncestr(),
            'out_refund_no'=> $refund,
            'out_trade_no'=> $v -> trade_no,
            'total_fee'=> $v -> z_price*100,
            'refund_fee'=> $v -> z_price*100,
            'op_user_id' => '1499256602',
          );
          $parma['sign'] = $this->getSign($parma);
          $xmldata = $this->arrayToXml($parma);
          $xmlresult = $this->postXmlSSLCurl($xmldata,'https://api.mch.weixin.qq.com/secapi/pay/refund');
          $result = $this->xmlToArray($xmlresult);
          
      }else if($v -> pay == 'wallet_Pay'){
          $uptsql = "update lkt_user set money=money+$v->z_price where user_id='$v->user_id'";
          $result = $db -> update($uptsql);
          
        if($result > 0){
          $openid = $db -> select("select wx_id from lkt_user where user_id='$v->user_id'");
          $openid = $openid[0] -> wx_id;
          $v -> openid = $openid;
          $from = $db -> select("select fromid from lkt_draw_user_fromid where open_id='$openid' and id=(select max(id) from lkt_draw_user_fromid where open_id='$openid')");
          $fromid = !empty($from)?$from[0] -> fromid:'';
          $this -> Send_success($v,$fromid);
        }
      }
      
      if($result > 0 || ($result['return_code'] == 'SUCCESS' && $result['result_code'] == 'SUCCESS')){
         $db -> update("update lkt_draw_user set lottery_status=5 where id=$v->id");
      }
     }
     }
    }
    
    public function Send_success($arr,$fromid){
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
                $data['touser'] = $arr -> openid;
                $sql = "select * from lkt_notice where id = '1'";
                $r = $db->select($sql);
                $template_id = $r[0]->refund_success;
                $data['template_id'] = $template_id;
                $data['form_id'] = $fromid;
                $data['page'] = 'pages/index/index';
                $minidata = array('keyword1' => array('value' => $arr -> sNo,'color' => "#173177"),'keyword2' => array('value' => $arr -> p_name,'color' => "#173177"),'keyword3' => array('value' => $arr -> z_price,'color' => "#173177"),'keyword4' => array('value' => '退回到钱包','color' => "#FF4500"),'keyword5' => array('value' => '抽奖失败--退款','color' => "#FF4500"));
                $data['data'] = $minidata;
                
                $data = json_encode($data);  
                $da = $this->httpsRequest($url,$data);
                $delsql = "delete from lkt_draw_user_fromid where open_id='$arr->openid' and fromid='$fromid'";
                $res = $db -> delete($delsql);  
                       
                  
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

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>