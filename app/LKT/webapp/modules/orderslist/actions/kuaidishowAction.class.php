<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once (MO_LIB_DIR . '/DBAction.class.php');
require_once (MO_LIB_DIR . '/ShowPager.class.php');
require_once (MO_LIB_DIR . '/Tools.class.php');

class kuaidishowAction extends Action {

	public function getDefaultView() {
		$db = DBAction::getInstance();
		$request = $this -> getContext() -> getRequest();
        // 获取信息
        $r_sNo = trim($request->getParameter('r_sNo'));// 订单详情id 
        $courier_num = $request->getParameter('courier_num');
        // 根据订单详情id,修改订单详情
            $sql = "select express_id,courier_num from lkt_order_details where r_sNo = '$r_sNo'";
            $r = $db->select($sql);
        if(!empty($r[0]->express_id) && !empty($r[0]->courier_num)){
            $express_id = $r[0]->express_id;//快递公司ID
            $courier_num = $r[0]->courier_num;//快递单号
            $sql01 = "select * from lkt_express where id = '$express_id'";
            $r01 = $db->select($sql01);
            $type = $r01[0]-> type;//快递公司代码
            $kuaidi_name = $r01[0]-> kuaidi_name;
            $url = "http://www.kuaidi100.com/query?type=$type&postid=$courier_num";
            $res = $this->httpsRequest($url);

            $res_1 = json_decode($res);
            if (empty($res_1->data)) {
                $res = array('code' => 0,'data'=>[]);
            }else{
                $res = array('code' => 1,'data'=>$res_1->data);
            }
                
        }else{
            $res = array('code' => 0,'data'=>[]);
        }
        echo json_encode($res);exit;
	}

	public function execute() {
		$this->getDefaultView();
	}

	
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
	public function getRequestMethods() {
		return Request::POST;
	}

}
?>