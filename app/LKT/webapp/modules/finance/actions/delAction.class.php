<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class delAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收信息
        $id = intval($request->getParameter('id')); // 提现id
        $m = intval($request->getParameter('m')); // 参数
        $user_id = $request->getParameter('user_id'); // 用户id
        $money = $request->getParameter('money'); // 实际提款金额
        $s_charge = $request->getParameter('s_charge'); // 手续费
        $zmoney = $money + $s_charge;
        $sql = "select * from lkt_user where user_id = '$user_id'";
        $r = $db->select($sql);
        $user_id = $r[0]->user_id; // 用户id
        $ymoney = $r[0]->money; // 原有金额
        // 根据提现id，修改状态信息
        if($m == 1){
            $event = $user_id . "提现了" . $zmoney;
            // 在操作列表里添加一条数据
            $sql = "insert into lkt_record (user_id,money,oldmoney,event,type) values('$user_id','$zmoney','$ymoney','$event',21)";
            $r = $db->insert($sql);

            // 根据id,修改提现列表中数据的状态
        	$sql = "UPDATE lkt_withdraw SET status=1 WHERE id = '$id'";
	        $db->update($sql);

            $db->admin_record($admin_id,' 通过id为 '.$id.' 的提现信息',6);
            echo 1;
	        return;
        }else{
            // 根据微信昵称,修改会员列表里的金额
            $sql = "update lkt_user set money = money+'$zmoney' where user_id = '$user_id'";
            $r = $db->update($sql);

            $event = $user_id . "提现" . $zmoney . "找拒绝";
            // 在操作列表里添加一条数据
            $sql = "insert into lkt_record (user_id,money,oldmoney,event,type) values('$user_id','$zmoney','$ymoney','$event',22)";
            $r = $db->insert($sql);

        	$sql = "UPDATE lkt_withdraw SET status=2 WHERE id = '$id'";
	        $db->update($sql);

            $db->admin_record($admin_id,' 拒绝id为 '.$id.' 的提现信息',6);

            echo 1;
            return;
        }
       
    }

    public function execute(){
        return $this->getDefaultView();
    }


    public function getRequestMethods(){
        return Request :: NONE;
    }

    // 随机32位字符串
    private function nonce_str(){
        $result = '';
        $str = '1234567890QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm';
        for ($i=0;$i<32;$i++){
            $result .= $str[rand(0,48)];
        }
        return $result;
    }
    // 签名 $data要先排好顺序
    private function sign($data){
        $stringA = '';
        foreach ($data as $key=>$value){
            if(!$value) continue;
            if($stringA) $stringA .= '&'.$key."=".$value;
            else $stringA = $key."=".$value;
        }
        $wx_key = 'vfhdsj45hrthrt45j6t4y8h1t5r4re8t'; // 申请支付后有给予一个商户账号和密码，登陆后自己设置key
        $stringSignTemp = $stringA.'&key='.$wx_key; // 申请支付后有给予一个商户账号和密码，登陆后自己设置key    
        return strtoupper(md5($stringSignTemp));
    }
    // curl请求啊
    function http_request($url,$data = null,$headers=array()){
        $curl = curl_init();
        if( count($headers) >= 1 ){
            curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
        }
        curl_setopt($curl, CURLOPT_URL, $url);

        curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE);

        if (!empty($data)){
            curl_setopt($curl, CURLOPT_POST, 1);
            curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
        }
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($curl);
        curl_close($curl);
        return $output;
    }

    function RAS($mch_id,$nonce_str,$sign){
        $data="<xml>
        <mch_id>".$mch_id."</mch_id>
        <nonce_str>".$nonce_str."</nonce_str>
        <sign>".$sign."</sign>
        <sign_type>MD5</sign_type>
        </xml>";
        $ch = curl_init ();
        $MENU_URL="https://fraud.mch.weixin.qq.com/risk/getpublickey";
        curl_setopt ( $ch, CURLOPT_URL, $MENU_URL );
        curl_setopt ( $ch, CURLOPT_CUSTOMREQUEST, "POST" );
        curl_setopt ( $ch, CURLOPT_SSL_VERIFYPEER, true );
        curl_setopt ( $ch, CURLOPT_SSL_VERIFYHOST, true );
        //两个证书（必填，请求需要双向证书。）
        $zs1= MO_LIB_DIR . "/cert/apiclient_cert.pem";
        $zs2= MO_LIB_DIR . "/WxpayAPI_php/cert/apiclient_key.pem";
        curl_setopt($ch,CURLOPT_SSLCERT,$zs1);
        curl_setopt($ch,CURLOPT_SSLKEY,$zs2);
        curl_setopt ( $ch, CURLOPT_FOLLOWLOCATION, 1 );
        curl_setopt ( $ch, CURLOPT_AUTOREFERER, 1 );
        curl_setopt ( $ch, CURLOPT_POSTFIELDS, $data );
        curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
        $info = curl_exec ( $ch );
        if (curl_errno ( $ch )) {
        echo 'Errno' . curl_error ( $ch );
        }
        curl_close ( $ch );
        var_dump($info);
    }

}

?>