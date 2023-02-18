<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once MO_LIB_DIR . '/DBAction.class.php';
require_once MO_LIB_DIR . '/ShowPager.class.php';
require_once MO_LIB_DIR . '/Tools.class.php';

class kuaidishowAction extends Action
{

    public function getDefaultView()
    {
        $db      = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $r_sNo       = trim($request->getParameter('r_sNo')); // 订单详情id
        $courier_num = $request->getParameter('courier_num');
        // 根据订单详情id,修改订单详情
        $sql = "select express_id,courier_num from lkt_order_details where r_sNo = '$r_sNo' and courier_num ='$courier_num'";
        $r   = $db->select($sql);
        $dd  = [];
        if ($r) {
            foreach ($r as $key => $value) {
                if (!empty($value->express_id) && !empty($value->courier_num)) {
                    $express_id  = $value->express_id; //快递公司ID
                    $courier_num = $value->courier_num; //快递单号
                    $sql01       = "select * from lkt_express where id = '$express_id'";
                    $r01         = $db->select($sql01);

                    $type        = $r01[0]->type; //快递公司代码
                    $kuaidi_name = $r01[0]->kuaidi_name;
                    $res         = $this->logistics2($type, $courier_num);
                    $res_1       = json_decode($res);
                    if (empty($res_1->data)) {
                        $res = array('code' => 0, 'data' => [], 'courier_num' => '', 'kuaidi_name' => '');
                    } else {
                        $res = array('code' => 1, 'data' => $res_1->data, 'courier_num' => $courier_num, 'kuaidi_name' => $kuaidi_name);
                    }
                    $dd[] = $res;
                }
            }
        } else {
            $res  = array('code' => 0, 'data' => [], 'courier_num' => '', 'kuaidi_name' => '');
            $dd[] = $res;
        }
        echo json_encode($dd);exit;
    }

    public function execute()
    {
        $this->getDefaultView();
    }

    public static function logistics2($type, $courier_num)
    {

        $db       = DBAction::getInstance();
        $sql      = "select * from lkt_order_config where id = 1";
        $r        = $db->select($sql);
        $customer = $r[0]->customer;
        $kdkey    = $r[0]->kdkey;

        $url      = 'http://poll.kuaidi100.com/poll/query.do'; //实时查询
        $key      = $kdkey; //客户授权key
        $customer = $customer; //查询公司编号

        $param = array(
            'com'      => $type . '', //快递公司编码
            'num'      => trim($courier_num), //快递单号
            'phone'    => '', //手机号
            'from'     => '', //出发地城市
            'to'       => '', //目的地城市
            'resultv2' => '1', //开启行政区域解析
        );
        //请求参数
        $post_data             = array();
        $post_data["customer"] = $customer;
        $post_data["param"]    = json_encode($param);
        $sign                  = md5($post_data["param"] . $key . $post_data["customer"]);
        $post_data["sign"]     = strtoupper($sign);
        $params                = "";
        foreach ($post_data as $k => $v) {
            $params .= "$k=" . urlencode($v) . "&"; //默认UTF-8编码格式
        }
        $post_data = substr($params, 0, -1);
        $ch        = curl_init();
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
        curl_setopt($ch, CURLOPT_TIMEOUT, 3);
        $result = curl_exec($ch);
        return $result;

    }
    public function getRequestMethods()
    {
        return Request::POST;
    }

}
