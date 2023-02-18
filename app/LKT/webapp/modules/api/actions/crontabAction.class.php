<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

/*
定时器类
*/

class crontabAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $dd = date('Y-m-d H:i:s', strtotime("-15 day"));
        $ss = $db->select("select r_sNo,id,user_id from lkt_order_details where deliver_time<'" . $dd . "' and r_status = 2");//查询订单发货时间超过15天的待收货时间
        if ($ss) {
            foreach ($ss as $key => $value01) {
                $sNo = $value01->r_sNo;
                $value01->user_id;

                $db->update("UPDATE `lkt_order_details` SET`r_status`='5' WHERE id = " . $value01->id);

                $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' AND  r_status = '5' ";
                $res_o = $db->selectrow($sql_o);//查询订单状态为待收货的行数

                $sql_d = "select id from lkt_order_details where r_sNo = '$sNo'";
                $res_d = $db->selectrow($sql_d);
                // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                if ($res_o == $res_d) {
                    //如果订单数量相等 则修改父订单状态
                    $sql = "update lkt_order set status = '5' where sNo = '$sNo'";
                    $db->update($sql);
                }

            }

        }

    }

    public function Send_fail($uid, $fromid, $sNo, $p_name, $price, $template_id, $page)
    {
        $db = DBAction::getInstance();
        $delsql = "delete from lkt_user_fromid where open_id='$uid' and fromid='$fromid'";
        $db->delete($delsql);

        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if ($r) {
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $AccessToken = $this->getAccessToken($appid, $appsecret);
            $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' . $AccessToken;

        }

        $data = array();
        $data['access_token'] = $AccessToken;
        $data['touser'] = $uid;
        $data['template_id'] = $template_id;
        $data['form_id'] = $fromid;
        $data['page'] = $page;
        $price = $price . '元';
        $minidata = array('keyword1' => array('value' => $sNo, 'color' => "#173177"), 'keyword2' => array('value' => $p_name, 'color' => "#173177"), 'keyword3' => array('value' => $price, 'color' => "#173177"), 'keyword4' => array('value' => '退回到钱包', 'color' => "#FF4500"), 'keyword5' => array('value' => '拼团失败--退款', 'color' => "#FF4500"));
        $data['data'] = $minidata;
        $data = json_encode($data);
        $this->httpsRequest($url, $data);

    }

    /*
     * 发送请求
     @param $ordersNo string 订单号　
     @param $refund string 退款单号
     @param $price float 退款金额
     return array
     */
    private function wxrefundapi($ordersNo, $refund, $total_fee, $price)
    {
        //通过微信api进行退款流程
        $db = DBAction::getInstance();
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if ($r) {
            $appid = $r[0]->appid;
            // 小程序唯一标识
            $appsecret = $r[0]->appsecret;
            // 小程序的 app secret
            $company = $r[0]->company;
            $mch_key = $r[0]->mch_key; // 商户key
            $mch_id = $r[0]->mch_id; // 商户mch_id
        }

        $parma = array('appid' => $appid, 'mch_id' => $mch_id, 'nonce_str' => $this->createNoncestr(), 'out_refund_no' => $refund, 'out_trade_no' => $ordersNo, 'total_fee' => $total_fee, 'refund_fee' => $price, 'op_user_id' => $appid);
        $parma['sign'] = $this->getSign($parma, $mch_key);
        $xmldata = $this->arrayToXml($parma);
        $xmlresult = $this->postXmlSSLCurl($xmldata, 'https://api.mch.weixin.qq.com/secapi/pay/refund');
        $result = $this->xmlToArray($xmlresult);
        return $result;
    }

    /*
     * 生成随机字符串方法
     */
    protected function createNoncestr($length = 32)
    {
        $chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        $str = "";
        for ($i = 0; $i < $length; $i++) {
            $str .= substr($chars, mt_rand(0, strlen($chars) - 1), 1);
        }
        return $str;
    }

    /*
     * 对要发送到微信统一下单接口的数据进行签名
     */
    protected function getSign($Obj, $mch_key)
    {
        foreach ($Obj as $k => $v) {
            $Parameters[$k] = $v;
        }
        //签名步骤一：按字典序排序参数
        ksort($Parameters);
        $String = $this->formatBizQueryParaMap($Parameters, false);
        //签名步骤二：在string后加入KEY
        $String = $String . "&key=" . $mch_key;
        //签名步骤三：MD5加密
        $String = md5($String);
        //签名步骤四：所有字符转为大写
        $result_ = strtoupper($String);
        return $result_;
    }

    /*
     *排序并格式化参数方法，签名时需要使用
     */
    protected function formatBizQueryParaMap($paraMap, $urlencode)
    {
        $buff = "";
        ksort($paraMap);
        foreach ($paraMap as $k => $v) {
            if ($urlencode) {
                $v = urlencode($v);
            }
            //$buff .= strtolower($k) . "=" . $v . "&";
            $buff .= $k . "=" . $v . "&";
        }
        $reqPar = "";
        if (strlen($buff) > 0) {
            $reqPar = substr($buff, 0, strlen($buff) - 1);
        }
        return $reqPar;
    }

    //数组转字符串方法
    protected function arrayToXml($arr)
    {
        $xml = "<xml>";
        foreach ($arr as $key => $val) {
            if (is_numeric($val)) {
                $xml .= "<" . $key . ">" . $val . "</" . $key . ">";
            } else {
                $xml .= "<" . $key . "><![CDATA[" . $val . "]]></" . $key . ">";
            }
        }
        $xml .= "</xml>";
        return $xml;
    }

    protected function xmlToArray($xml)
    {
        $array_data = json_decode(json_encode(simplexml_load_string($xml, 'SimpleXMLElement', LIBXML_NOCDATA)), true);
        return $array_data;
    }

    //需要使用证书的请求
    private function postXmlSSLCurl($xml, $url, $second = 30)
    {
        $ch = curl_init();
        //超时时间
        curl_setopt($ch, CURLOPT_TIMEOUT, $second);
        //这里设置代理，如果有的话
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
        //设置header
        curl_setopt($ch, CURLOPT_HEADER, FALSE);
        //要求结果为字符串且输出到屏幕上
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
        //设置证书
        //使用证书：cert 与 key 分别属于两个.pem文件
        //默认格式为PEM，可以注释
        $cert = str_replace('lib', 'filter', MO_LIB_DIR) . '/apiclient_cert.pem';
        $key = str_replace('lib', 'filter', MO_LIB_DIR) . '/apiclient_key.pem';
        curl_setopt($ch, CURLOPT_SSLCERTTYPE, 'PEM');
        curl_setopt($ch, CURLOPT_SSLCERT, $cert);
        //默认格式为PEM，可以注释
        curl_setopt($ch, CURLOPT_SSLKEYTYPE, 'PEM');
        curl_setopt($ch, CURLOPT_SSLKEY, $key);
        //post提交方式
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $xml);
        $data = curl_exec($ch);
        //返回结果
        if ($data) {
            curl_close($ch);
            return $data;
        } else {
            $error = curl_errno($ch);
            echo "curl出错，错误码:$error" . "<br>";
            curl_close($ch);
            return false;
        }
    }

    public function Send_success($arr, $template_id)
    {
        $db = DBAction::getInstance();
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if ($r) {
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $AccessToken = $this->getAccessToken($appid, $appsecret);
            $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' . $AccessToken;

        }
        foreach ($arr as $k => $v) {
            $data = array();
            $data['access_token'] = $AccessToken;
            $data['touser'] = $v->uid;
            $data['template_id'] = $template_id;
            $data['form_id'] = $v->fromid;
            $data['page'] = "pages/order/detail?orderId=$v->id";
            $p_price = $v->p_price . '元';
            $z_price = $v->z_price . '元';
            $minidata = array('keyword1' => array('value' => $v->p_name, 'color' => "#173177"), 'keyword2' => array('value' => $p_price, 'color' => "#173177"), 'keyword3' => array('value' => $z_price, 'color' => "#173177"), 'keyword4' => array('value' => $v->sNo, 'color' => "#173177"), 'keyword5' => array('value' => '拼团失败', 'color' => "#FF4500"), 'keyword6' => array('value' => $v->add_time, 'color' => "#173177"));
            $data['data'] = $minidata;
            $data = json_encode($data);
            $this->httpsRequest($url, $data);
            $delsql = "delete from lkt_user_fromid where open_id='$v->uid' and fromid='$v->fromid'";
            $db->delete($delsql);

        }

    }

    public function get_fromid($openid, $type = '')
    {
        $db = DBAction::getInstance();
        if (empty($type)) {
            $fromidsql = "select fromid,open_id from lkt_user_fromid where open_id='$openid' and id=(select max(id) from lkt_user_fromid where open_id='$openid')";
            $fromidres = $db->select($fromidsql);
            if ($fromidres) {
                $fromid = $fromidres[0]->fromid;
                $arrayName = array('openid' => $openid, 'fromid' => $fromid);
                return $arrayName;
            } else {
                return array('openid' => $openid, 'fromid' => '123456');
            }
        } else {
            $delsql = "delete from lkt_user_fromid where open_id='$openid' and fromid='$type'";
            $db->delete($delsql);
            $fromidsql = "select fromid,open_id from lkt_user_fromid where open_id='$openid' and id=(select max(id) from lkt_user_fromid where open_id='$openid')";
            $fromidres = $db->select($fromidsql);
            if ($fromidres) {
                $fromid = $fromidres[0]->fromid;
                $arrayName = array('openid' => $openid, 'fromid' => $fromid);
                return $arrayName;
            } else {
                return array('openid' => $openid, 'fromid' => '123456');
            }

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

    //监测拼团活动有没有过期的，改变其活动状态
    function huodongzhuangtai($db)
    {
        $rrr = $db->select("select * from lkt_group_product order by group_id desc");

        if ($rrr) {

            foreach ($rrr as $key => $value) {
                $cfg = unserialize($value->group_data);
                $starttime = $cfg->starttime;
                $end_time = $cfg->endtime;
                $g_status = $value->g_status;
                $data = date('Y-m-d H:i:s', time());

                if ($starttime < $data && $data < $end_time && $g_status == 1) {//处理正在进行中的

                    $re = $db->select("select lkt_product_list.recycle ,lkt_product_list.status from lkt_group_product,lkt_product_list where group_id = $value->id and lkt_group_product.product_id = lkt_product_list.id");
                    if ($re) {
                        if ($re[0]->recycle != 1 && $re[0]->status != 1) {
                            $res = $db->update("UPDATE `lkt_group_product` SET `g_status`='2' WHERE id = " . $value->id);

                        }
                    }

                }

                if ($end_time < $data || $g_status == 3) {//处理过期的

                    $db->update("UPDATE `lkt_group_product` SET `g_status`='3' WHERE id = " . $value->id);
                    $r = $db->select("select * from lkt_group_open where group_id=$value->group_id and ptstatus =1 ");
                    if ($r) {
                        foreach ($r as $key01 => $value01) {
                            $db->update("UPDATE `lkt_group_open` SET `ptstatus`='3' WHERE id = " . $value01->id);
                            $ee = $db->select("select user_id,z_price,sNo,pay from lkt_order where ptcode = '" . $value01->ptcode . "'");
                            if ($ee) {
                                foreach ($ee as $key02 => $value02) {
                                    $db->update("UPDATE `lkt_order_details` SET `r_status`='11' WHERE r_sNo = '" . $value02->sNo . "'");
                                    $db->update("UPDATE lkt_user SET money =money+$value02->z_price WHERE user_id = '" . $value02->user_id . "'");
                                    $event = $value02->user_id . '退回拼团金额' . $value02->z_price . '';
                                    $sqlldr = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$value02->user_id','$value02->z_price','','$event',5)";
                                    $db->insert($sqlldr);
                                }
                            }
                            $db->update("UPDATE `lkt_order` SET `ptstatus`='3', `status`='11' WHERE ptcode = " . $value01->ptcode);
                        }
                    }
                }// end if
            } // end foreach
        } //end if 


    }


    public function execute()
    {
    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

}

