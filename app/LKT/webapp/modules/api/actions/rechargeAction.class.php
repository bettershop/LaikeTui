<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once('BaseAction.class.php');

class rechargeAction extends BaseAction
{

    public function index()
    {
        $db = DBAction::getInstance();
        $openid = addslashes($_POST['openid']); // 微信id

        // 查询会员信息
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if ($r) {
            $user['money'] = $r[0]->money;
            $user_id = $r[0]->user_id; // 会员编号

            if ($user['money'] == '') {
                $user['money'] = 0;
            }
            // 根据推荐人等于会员编号,查询推荐人总数
            $sql = "select count(Referee) as a from lkt_user where Referee = '$user_id'";
            $rr = $db->select($sql);
            if ($rr) {
                $user['invitation_num'] = $rr[0]->a;
            } else {
                $user['invitation_num'] = '';
            }
        } else {
            $user['money'] = 0;
            $user['invitation_num'] = '';
        }

        // 根据微信id,查询分享列表里的礼券总和
        $sql = "select sum(coupon) as a from lkt_share where wx_id = '$openid'";
        $r = $db->select($sql);
        if ($r[0]->a == '') {
            $user['coupon'] = 0;
        } else {
            $user['coupon'] = $r[0]->a;
        }

        //查询最低充值金额
        $ss = "select * from lkt_finance_config where id = 1";
        $rs = $db->select($ss);
        $min_cz = $rs[0]->min_cz;

        echo json_encode(array('status' => 1, 'user' => $user, 'min_cz' => $min_cz));
        exit();
    }

    //充值成功金额增加
    public function cz()
    {
        echo json_encode(array('status' => 1));
        exit();
    }

    //充值
    public function recharge()
    {
        $db = DBAction::getInstance();

        $openid = addslashes($_POST['openid']); // 微信id
        $cmoney = addslashes($_POST['cmoney']); // 充值金额

        // 查询余额参数表
        $sql = "select cz_multiple from lkt_finance_config where id = 1";
        $r = $db->select($sql);
        if ($r) {
            $cz_multiple = $r[0]->cz_multiple;
            if ($cz_multiple) {
                if ($cmoney % $cz_multiple == 0) {

                } else {
                    echo json_encode(array('state' => 0, 'text' => '充值金额需要是' . $cz_multiple . '的倍数'));
                    exit();
                }
            }
        }

        $dingdanhao = "CZ" . date("ymdhis") . rand(0, 9) . rand(0, 9) . rand(0, 9) . rand(0, 9) . rand(0, 9) . rand(0, 9);
        // 查询系统配置
        $ss = "select * from lkt_config where id = 1";
        $rs = $db->select($ss);
        if ($rs) {
            // 进入支付页面
            $appid = $rs[0]->appid; // 如果是公众号 就是公众号的appid
            $body = $rs[0]->company; // 公司名称
            $mch_id = $rs[0]->mch_id; // 商户id
            $mch_key = $rs[0]->mch_key; // 商户key
            $notify_url = $rs[0]->uploadImg_domain . '/LKT/notify_url.php';
            $spbill_create_ip = $rs[0]->ip; // ip地址
        } else {
            // 进入支付页面
            $appid = ''; // 如果是公众号 就是公众号的appid
            $body = ''; // 公司名称
            $mch_id = ''; // 商户id
            $mch_key = ''; // 商户key
            $notify_url = '';
            $spbill_create_ip = ''; // ip地址
        }
        $nonce_str = $this->nonce_str(); // 随机字符串
        $openid = $openid; // 微信id
        $out_trade_no = $dingdanhao; // 商户订单号
        $total_fee = $cmoney * 100; // 因为充值金额最小是1 而且单位为分 如果是充值1元所以这里需要*100
        $trade_type = 'JSAPI'; // 交易类型 默认

        // 这里是按照顺序的 因为下面的签名是按照顺序 排序错误 肯定出错
        $post['appid'] = $appid; // 如果是公众号 就是公众号的appid
        $post['body'] = $body; // 公司名称
        $post['mch_id'] = $mch_id; // 商户id
        $post['nonce_str'] = $nonce_str;//随机字符串
        $post['notify_url'] = $notify_url;
        $post['openid'] = $openid; // 微信id
        $post['out_trade_no'] = $out_trade_no; // 商户订单号
        $post['spbill_create_ip'] = $spbill_create_ip; // 终端的ip
        $post['total_fee'] = $total_fee; // 总金额 最低为一块钱 必须是整数
        $post['trade_type'] = $trade_type; // 交易类型
        $sign = $this->sign($post, $mch_key);//签名
        $post_xml = '<xml>
               <appid>' . $appid . '</appid>
               <body>' . $body . '</body>
               <mch_id>' . $mch_id . '</mch_id>
               <nonce_str>' . $nonce_str . '</nonce_str>
               <notify_url>' . $notify_url . '</notify_url>
               <openid>' . $openid . '</openid>
               <out_trade_no>' . $out_trade_no . '</out_trade_no>
               <spbill_create_ip>' . $spbill_create_ip . '</spbill_create_ip>
               <total_fee>' . $total_fee . '</total_fee>
               <trade_type>' . $trade_type . '</trade_type>
               <sign>' . $sign . '</sign>
            </xml> ';
        //统一接口prepay_id
        $url = 'https://api.mch.weixin.qq.com/pay/unifiedorder';
        $xml = $this->http_request($url, $post_xml);
        $array = $this->xml($xml);//全要大写

        if ($array['RETURN_CODE'] == 'SUCCESS' && $array['RESULT_CODE'] == 'SUCCESS') {
            $time = time();
            $tmp = array();//临时数组用于签名
            $tmp['appId'] = $appid;
            $tmp['nonceStr'] = $nonce_str;
            $tmp['package'] = 'prepay_id=' . $array['PREPAY_ID'];
            $tmp['signType'] = 'MD5';
            $tmp['timeStamp'] = "$time";

            $data['state'] = 1;
            $data['timeStamp'] = "$time";//时间戳
            $data['nonceStr'] = $nonce_str;//随机字符串
            $data['signType'] = 'MD5';//签名算法，暂支持 MD5
            $data['package'] = 'prepay_id=' . $array['PREPAY_ID'];//统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*
            $data['paySign'] = $this->sign($tmp, $mch_key);//签名,具体签名方案参见微信公众号支付帮助文档;
            $data['out_trade_no'] = $out_trade_no;

        } else {
            $data['state'] = 0;
            $data['text'] = "错误";
            $data['RETURN_CODE'] = $array['RETURN_CODE'];
            $data['RETURN_MSG'] = $array['RETURN_MSG'];
        }
        echo json_encode($data);
        exit;
    }


    //随机32位字符串
    private function nonce_str()
    {
        $result = '';
        $str = 'QWERTYUIOPASDFGHJKLZXVBNMqwertyuioplkjhgfdsamnbvcxz';
        for ($i = 0; $i < 32; $i++) {
            $result .= $str[rand(0, 48)];
        }
        return $result;
    }

    //签名 $data要先排好顺序
    private function sign($data, $mch_key)
    {
        $stringA = '';
        foreach ($data as $key => $value) {
            if (!$value) continue;
            if ($stringA) $stringA .= '&' . $key . "=" . $value;
            else $stringA = $key . "=" . $value;
        }
        $wx_key = $mch_key;//申请支付后有给予一个商户账号和密码，登陆后自己设置key
        $stringSignTemp = $stringA . '&key=' . $wx_key;//申请支付后有给予一个商户账号和密码，登陆后自己设置key
        return strtoupper(md5($stringSignTemp));
    }

    //curl请求
    function http_request($url, $data = null, $headers = array())
    {
        $curl = curl_init();
        if (count($headers) >= 1) {
            curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
        }
        curl_setopt($curl, CURLOPT_URL, $url);

        curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE);

        if (!empty($data)) {
            curl_setopt($curl, CURLOPT_POST, 1);
            curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
        }
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
        $output = curl_exec($curl);
        curl_close($curl);
        return $output;
    }

    //获取xml
    private function xml($xml)
    {
        $p = xml_parser_create();
        xml_parse_into_struct($p, $xml, $vals, $index);
        xml_parser_free($p);
        $data = array();
        foreach ($index as $key => $value) {
            if ($key == 'xml' || $key == 'XML') continue;
            $tag = $vals[$value[0]]['tag'];
            $value = $vals[$value[0]]['value'];
            $data[$tag] = $value;
        }
        return $data;
    }
}

?>