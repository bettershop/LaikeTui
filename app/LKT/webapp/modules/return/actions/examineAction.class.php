<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once (MO_LIB_DIR . '/DBAction.class.php');
require_once (MO_LIB_DIR . '/ShowPager.class.php');
require_once (MO_LIB_DIR . '/Tools.class.php');

$mch_key = '';

class examineAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this -> getContext() -> getRequest();
        $id = intval($request -> getParameter('id'));
        $type = intval($request -> getParameter('type'));

        //查询订单信息
        $sql_p = "select p_price,user_id,r_sNo,re_money from lkt_order_details where id = '$id'";

        $res_p = $db -> select($sql_p);
        $p_price = $res_p[0] -> p_price;
        $user_id = $res_p[0] -> user_id;
        $sNo = $res_p[0] -> r_sNo;
        $re_money = $res_p[0] -> re_money;
        if($re_money == '0.00' || empty($re_money)){
            //判断单个商品退款是否有使用优惠
            $sql_id = "select a.id,m.freight,a.trade_no,a.sNo,a.pay,a.z_price,a.user_id,a.allow,a.spz_price,a.reduce_price,a.coupon_price,m.p_price,a.consumer_money from lkt_order as a LEFT JOIN lkt_order_details AS m ON a.sNo = m.r_sNo where m.id = '$id' and m.r_status = '4' ";
            $order_res = $db -> select($sql_id);
            // echo $sql_id;exit;
            $allow = $order_res[0] -> allow;
            $reduce_price = $order_res[0] -> reduce_price;
            $coupon_price = $order_res[0] -> coupon_price;
            $pay =  $order_res[0] -> pay;
            $consumer_money = $order_res[0] ->consumer_money;
            $spz_price = $order_res[0] -> spz_price;
            $youhui_price = floatval($allow) + floatval($reduce_price) + floatval($coupon_price);
            $freight = $order_res[0] -> freight;//运费
            $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' ";
            $res_o = $db -> selectrow($sql_o);

            $sql_d = "select id from lkt_order where sNo = '$sNo'";
            $res_d = $db -> selectrow($sql_d);

            // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
            if ($res_d == $res_o) {
                //如果订单数量相等 则修
                $price = $order_res[0] -> z_price;
            } else {
                $price = number_format($order_res[0] -> z_price / $spz_price * $p_price, 2);
            }

            if($price <= 0 && $pay == 'consumer_pay' && $consumer_money >0){
                $price = $consumer_money;
            }

            if($freight){
                $price = $price -$freight;
            }
        }else{
            $price = $re_money;
        }

        echo $price;
        exit;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this -> getContext() -> getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');
        //开启事务
        $db->begin();

        $sql = "select * from lkt_config where id=1";
        $r = $db -> select($sql);
        if ($r) {
            $appid = $r[0]->appid;
            // 小程序唯一标识
            $appsecret = $r[0]->appsecret;
            // 小程序的 app secret
            $company = $r[0] ->company;
            $mch_key = $r[0]->mch_key; // 商户key
            $mch_id = $r[0]->mch_id; // 商户mch_id
        }
        $time = date("Y-m-d h:i:s", time());
        $id = intval($request -> getParameter('id'));
        // 订单详情id
        $m = intval($request -> getParameter('m'));
        // 参数
        $text = trim($request -> getParameter('text'));

        $price = trim($request -> getParameter('price'));

        // text拒绝理由
        $sql = "select * from lkt_notice where id = '1'";
        $r = $db -> select($sql);
        $template_id = $r[0] -> refund_res;
        $res = 1;
        if ($m == 1 || $m == 4 || $m == 9) {
            $sql = "update lkt_order_details set r_type = '$m' where id = '$id'";
            $res = $db -> update($sql);
            if ($m == 9 || $m == 4) {

                $sql_id = "select a.id,a.trade_no,a.sNo,a.pay,a.z_price,a.user_id,a.allow,a.spz_price,a.reduce_price,a.coupon_price,m.p_price,a.consumer_money from lkt_order as a LEFT JOIN lkt_order_details AS m ON a.sNo = m.r_sNo where m.id = '$id' and m.r_status = '4' ";
                $order_res = $db -> select($sql_id);

                if ($order_res) {
                    $pay = $order_res[0] -> pay;
                    $user_id = $order_res[0] -> user_id;
                    $consumer_money = $order_res[0] -> consumer_money;
                    // print_r($pay);die;
                    if ($pay == 'wallet_Pay'||$pay == 'wallet_pay') {
                        //查询订单信息
                 
                        $sql_p = "select p_price,user_id,r_sNo from lkt_order_details where id = '$id'";
                        $res_p = $db -> select($sql_p);
                        $p_price = $res_p[0] -> p_price;
                        $user_id = $res_p[0] -> user_id;
                        $sNo = $res_p[0] -> r_sNo;


                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' and r_status = '4' ";
                        $res_o = $db -> selectrow($sql_o);
                        $sql_d = "select id from lkt_order_details where r_sNo = '$sNo' ";
                        $res_d = $db -> selectrow($sql_d);
                        // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                        if ($res_d == $res_o) {
                            //如果订单数量相等 则修
                            // 根据订单号、用户id,修改订单状态(6 订单关闭)
                            $sql_u = "update lkt_order set status = '6' where sNo = '$sNo' ";
                            $r_u = $db -> update($sql_u);
                        }

                        //判断单个商品退款是否有使用优惠
                        if(empty($price)){
                            $allow = $order_res[0] -> allow;
                            $reduce_price = $order_res[0] -> reduce_price;
                            $coupon_price = $order_res[0] -> coupon_price;
                            $spz_price = $order_res[0] -> spz_price;
                            $youhui_price = floatval($allow) + floatval($reduce_price) + floatval($coupon_price);
                            // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                            if ($res_d == $res_o) {
                                //如果订单数量相等 则修
                                $price = $order_res[0] -> z_price;

                            } else {
                                $price = number_format($order_res[0] -> z_price / $spz_price * $p_price, 2);
                            }
                        }

                        //修改订单状态为关闭
                        $sql = "update lkt_order_details set r_status = '6' where id = '$id'";
                        $res1 = $db -> update($sql);

                        $user_id = $res_p[0] -> user_id;
                        $sNo = $res_p[0] -> r_sNo;

                        //修改用户余额
                        $sql = "update lkt_user set money = money + '$price' where user_id = '$user_id'";
                        $res = $db -> update($sql);
                        //添加日志
                        $event = $user_id . '退款' . $price . '元余额';
                        $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$price','$price','$event',5)";
                        $rr = $db -> insert($sqll);
                        //发送推送信息
                        if($rr < 1 || $res1 < 1 || $res <1){
                            $db->rollback();
                            echo 0;
                            exit;
                        }
                        //查询openid
                        $sql_openid = "select wx_id from lkt_user where user_id = '$user_id'";
                        $res_openid = $db -> select($sql_openid);
                        $openid = $res_openid[0] -> wx_id;
                        $froms = $this -> get_fromid($openid);
                        $form_id = $froms['fromid'];
                        $page = 'pages/index/index';
                        //消息模板id
                        $send_id = $template_id;
                        $keyword1 = array('value' => $sNo, "color" => "#173177");
                        $keyword2 = array('value' => $company, "color" => "#173177");
                        $keyword3 = array('value' => $time, "color" => "#173177");
                        $keyword4 = array('value' => '退款成功', "color" => "#173177");
                        $keyword5 = array('value' => $price . '元', "color" => "#173177");
                        $keyword6 = array('value' => '预计24小时内到账', "color" => "#173177");
                        $keyword7 = array('value' => '原支付方式', "color" => "#173177");
                        //拼成规定的格式
                        $o_data = array('keyword1' => $keyword1, 'keyword2' => $keyword2, 'keyword3' => $keyword3, 'keyword4' => $keyword4, 'keyword5' => $keyword5, 'keyword6' => $keyword6, 'keyword7' => $keyword7);

                        $res1 = $this -> Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data);

                        $this -> get_fromid($openid, $form_id);
                        // var_dump($res);
                    } else if ($pay == 'combined_Pay') {
                       
                        $trade_no = $order_res[0] -> trade_no;
                        $sNo = $order_res[0] -> sNo;
                        $p_price = $order_res[0] -> p_price;
                        $user_id = $order_res[0] -> user_id;
                        //判断单个商品退款是否有使用优惠

                        $allow = $order_res[0] -> allow;
                        $reduce_price = $order_res[0] -> reduce_price;
                        $coupon_price = $order_res[0] -> coupon_price;

                        $spz_price = $order_res[0] -> spz_price;
                        $youhui_price = floatval($allow) + floatval($reduce_price) + floatval($coupon_price);

                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' ";
                        $res_o = $db -> selectrow($sql_o);
                        // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                        $total_fee = $order_res[0] -> z_price;
                        if ($res_o <= 1) {
                            //如果订单数量相等 则修
                            $z_price = $order_res[0] -> z_price;
                        } else {
                            $z_price = number_format($order_res[0] -> z_price / $spz_price * $p_price, 2);
                        }
                        if(!empty($price)){
                            $z_price = $price;
                        }
                        //组合支付时判断按照比例退款
                        $zhzf = "SELECT * FROM `lkt_combined_pay` where order_id = '$sNo' ";
                        $zhres = $db -> select($zhzf);
                        $weixin_pay = $zhres[0] -> weixin_pay;
                        $balance_pay = $zhres[0] -> balance_pay;
                        $consumer_pay = $zhres[0] -> consumer_pay;
                        $total = $zhres[0] -> total;
                        $openid = $zhres[0] -> user_id;

                        $refund_wx = number_format($weixin_pay / $total * $z_price, 2);
                        $refund_ye = number_format($balance_pay / $total * $z_price, 2);
                        $refund_cm = number_format($consumer_pay / $total * $z_price, 2);
                        $wxres_t = '';
                        if ($refund_wx > 0 && !empty($refund_wx)) {
                            //按照比例退款 --- 调起微信退款
                            $wxtk_res = $this -> wxrefundapi($trade_no, $sNo . $id, $weixin_pay * 100, $refund_wx * 100,$appid,$mch_id,$mch_key);
                            $user_id = $order_res[0] -> user_id;
                            $event = $user_id . '微信退款' . $refund_wx . '元余额-' . json_encode($wxtk_res);
                            $sqll = "insert into lkt_record (user_id,money,event,type) values ('$user_id','$refund_wx','$event',5)";
                            $rr = $db -> insert($sqll);
                            if ($wxtk_res['result_code'] == 'SUCCESS') {
                                $wxres_t = $wxtk_res['result_code'];
                            }
                            if($rr){
                                $db->rollback();
                                echo 0;
                                exit;
                            }
                        }

                        if($refund_cm){
                            //修改用户消费金
                            $sql = "update lkt_user set consumer_money = consumer_money + '$refund_cm' where user_id = '$user_id'";
                            $res = $db -> update($sql);
                            //添加日志
                            $event = $user_id.'退款'.$refund_cm.'消费金';
                            $sqlldr = "insert into lkt_distribution_record (user_id,from_id,money,sNo,level,event,type,add_date) values ('$user_id','$user_id','$refund_cm','','0','$event','5',CURRENT_TIMESTAMP)";
                            $rr = $db -> insert($sqlldr);
                            if($rr < 1){
                                $db->rollback();
                                // echo 'rollback1';
                                echo 0;
                                exit;
                            }
                        }

                        //修改用户余额
                        $sql = "update lkt_user set money = money + '$refund_ye' where user_id = '$user_id'";
                        $res = $db -> update($sql);
                        //添加日志
                        $event = $user_id . '退款' . $refund_ye . '元余额';
                        $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$refund_ye','$refund_ye','$event',5)";
                        $rr = $db -> insert($sqll);

                        if($rr < 1 || $res < 1){
                            $db->rollback();
                            echo 0;
                            exit;
                        }

                        if ($wxres_t == 'SUCCESS' || $rr) {
                            $froms = $this -> get_fromid($openid);
                            $form_id = $froms['fromid'];
                            $page = 'pages/index/index';
                            //消息模板id
                            $send_id = $template_id;
                            $keyword1 = array('value' => $sNo, "color" => "#173177");
                            $keyword2 = array('value' => $company, "color" => "#173177");
                            $keyword3 = array('value' => $time, "color" => "#173177");
                            $keyword4 = array('value' => '退款成功', "color" => "#173177");
                            $keyword5 = array('value' => $z_price . '元', "color" => "#173177");
                            $keyword6 = array('value' => '预计24小时内到账', "color" => "#173177");
                            $keyword7 = array('value' => '原支付方式', "color" => "#173177");
                            //拼成规定的格式
                            $o_data = array('keyword1' => $keyword1, 'keyword2' => $keyword2, 'keyword3' => $keyword3, 'keyword4' => $keyword4, 'keyword5' => $keyword5, 'keyword6' => $keyword6, 'keyword7' => $keyword7);

                            $res = $this -> Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data);

                            $this -> get_fromid($openid, $form_id);

                            $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' and r_status = '4'";
                            $res_o = $db -> selectrow($sql_o);
                            if ($res_o <= 1) {
                                // 根据订单号、用户id,修改订单状态(6 订单关闭)
                                $sql_u = "update lkt_order set status = '6' where sNo = '$sNo' ";
                                $r_u = $db -> update($sql_u);
                            }

                            // 根据订单号,查询商品id、商品名称、商品数量
                            $sql_o = "select p_id,num,p_name,sid from lkt_order_details where r_sNo = '$sNo' ";
                            $r_o = $db -> select($sql_o);
                            // 根据订单号,修改订单详情状态(订单关闭)
                            $sql_d = "update lkt_order_details set r_status = 6 where r_sNo = '$sNo' ";
                            $r_d = $db -> update($sql_d);
                            if($r_d < 1){
                                $db->rollback();
                                echo 0;
                                exit;
                            }
                            //退款后还原商品数量
                            foreach ($r_o as $key => $value) {
                                $pid = $value -> p_id;
                                // 商品id
                                $num = $value -> num;
                                // 商品数量
                                $sid = $value -> sid;
                                // 商品属性id
                                // 根据商品id,修改商品数量
                                $sql_p = "update lkt_configure set  num = num + $num where id = $sid";
                                $r_p = $db -> update($sql_p);
                                // 根据商品id,修改卖出去的销量
                                $sql_x = "update lkt_product_list set volume = volume - $num,num = num+$num where id = $pid";
                                $r_x = $db -> update($sql_x);
                                if($r_x < 1 || $r_p < 1){
                                    $db->rollback();
                                    echo 0;
                                    exit;
                                }
                            }
                            if ($r_d && $r_o) {
                                $res = 1;
                            } else {
                                $res = 0;
                            }
                        } else {
                            $res = 0;
                        }
                    } else if ($pay == 'consumer_pay') {

                        $trade_no = $order_res[0] -> trade_no;
                        $sNo = $order_res[0] -> sNo;
                        $p_price = $order_res[0] -> p_price;

                        if($price && $price >0){
                            $consumer_money = $price;
                        }
                        //修改用户消费金
                        // $consumer_money = number_format($consumer_money / $total * $z_price, 2);
                        $sql = "update lkt_user set consumer_money = consumer_money + '$consumer_money' where user_id = '$user_id'";
                        $res = $db -> update($sql);
                        //添加日志
                        $event = $user_id.'退款'.$consumer_money.'消费金';
                        $sqlldr = "insert into lkt_distribution_record (user_id,from_id,money,sNo,level,event,type,add_date) values ('$user_id','$user_id','$consumer_money','','0','$event','5',CURRENT_TIMESTAMP)";
                        $rr = $db -> insert($sqlldr);
                        if($rr < 1){
                            $db->rollback();
                            // echo 'rollback1';
                            echo 0;
                            exit;
                        }

                        //判断单个商品退款是否有使用优惠
                        $sql_openid = "select wx_id from lkt_user where user_id = '$user_id'";
                        $res_openid = $db -> select($sql_openid);
                        $openid = $res_openid[0] -> wx_id;
                        $froms = $this -> get_fromid($openid);
                        $form_id = $froms['fromid'];
                        $page = 'pages/index/index';
                        //消息模板id
                        $send_id = $template_id;
                        $keyword1 = array('value' => $sNo, "color" => "#173177");
                        $keyword2 = array('value' => $company, "color" => "#173177");
                        $keyword3 = array('value' => $time, "color" => "#173177");
                        $keyword4 = array('value' => '退款成功', "color" => "#173177");
                        $keyword5 = array('value' => $consumer_money . '元消费金', "color" => "#173177");
                        $keyword6 = array('value' => '预计24小时内到账', "color" => "#173177");
                        $keyword7 = array('value' => '原支付方式', "color" => "#173177");
                        //拼成规定的格式
                        $o_data = array('keyword1' => $keyword1, 'keyword2' => $keyword2, 'keyword3' => $keyword3, 'keyword4' => $keyword4, 'keyword5' => $keyword5, 'keyword6' => $keyword6, 'keyword7' => $keyword7);

                        $res = $this -> Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data);
                        $this -> get_fromid($openid, $form_id);
                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' and r_status = '4'";
                        $res_o = $db -> selectrow($sql_o);
                        if ($res_o <= 1) {
                            // 根据订单号、用户id,修改订单状态(6 订单关闭)
                            $sql_u = "update lkt_order set status = '6' where sNo = '$sNo' ";
                            $r_u = $db -> update($sql_u);
                        }

                        // 根据订单号,查询商品id、商品名称、商品数量
                        $sql_o = "select p_id,num,p_name,sid from lkt_order_details where r_sNo = '$sNo' ";
                        $r_o = $db -> select($sql_o);
                        // 根据订单号,修改订单详情状态(订单关闭)
                        $sql_d = "update lkt_order_details set r_status = 6 where r_sNo = '$sNo' ";
                        $r_d = $db -> update($sql_d);
                        if($r_d < 1){
                            $db->rollback();
                            echo 0;
                            exit;
                        }
                        //退款后还原商品数量
                        foreach ($r_o as $key => $value) {
                            $pid = $value -> p_id;
                            // 商品id
                            $num = $value -> num;
                            // 商品数量
                            $sid = $value -> sid;
                            // 商品属性id
                            // 根据商品id,修改商品数量
                            $sql_p = "update lkt_configure set  num = num + $num where id = $sid";
                            $r_p = $db -> update($sql_p);
                            // 根据商品id,修改卖出去的销量
                            $sql_x = "update lkt_product_list set volume = volume - $num,num = num+$num where id = $pid";
                            $r_x = $db -> update($sql_x);
                            if($r_d < 1 || $r_p < 1){
                                $db->rollback();
                                echo 0;
                                exit;
                            }
                        }
                        if ($r_d && $r_o) {
                            $res = 1;
                        } else {
                            $res = 0;
                        }
                    }else {
                        $trade_no = $order_res[0] -> trade_no;
                        $sNo = $order_res[0] -> sNo;

                        $p_price = $order_res[0] -> p_price;
                        $user_id = $order_res[0] -> user_id;
                        //判断单个商品退款是否有使用优惠

                        $allow = $order_res[0] -> allow;
                        $reduce_price = $order_res[0] -> reduce_price;
                        $coupon_price = $order_res[0] -> coupon_price;

                        $spz_price = $order_res[0] -> spz_price;
                        $youhui_price = floatval($allow) + floatval($reduce_price) + floatval($coupon_price);

                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' ";
                        $res_o = $db -> selectrow($sql_o);

                        $total_fee = $order_res[0] -> z_price;
                        if ($res_o <= 1) {
                            //如果订单数量相等 则修
                            $z_price = $order_res[0] -> z_price;
                        } else {
                            $z_price = number_format($order_res[0] -> z_price / $spz_price * $p_price, 2);
                        }

                        if(!empty($price)){
                            $z_price = $price;
                        }
                        //调起微信退款
                        $wxtk_res = $this -> wxrefundapi($trade_no, $sNo . $id, $total_fee * 100, $z_price * 100,$appid,$mch_id,$mch_key);
                        // var_dump($wxtk_res);exit;
                        $user_id = $order_res[0] -> user_id;
                        $event = $user_id . '微信退款' . $z_price . '元余额-' . json_encode($wxtk_res);
                        $sqll = "insert into lkt_record (user_id,money,event,type) values ('$user_id','$z_price','$event',5)";
                        $rr = $db -> insert($sqll);
                        if($rr < 1){
                            $db->rollback();
                            echo $sqll;
                            echo 0;
                            exit;
                        }

                        if ($wxtk_res['result_code'] == 'SUCCESS') {

                            //查询openid
                            $sql_openid = "select wx_id from lkt_user where user_id = '$user_id'";
                            $res_openid = $db -> select($sql_openid);
                            $openid = $res_openid[0] -> wx_id;
                            $froms = $this -> get_fromid($openid);
                            $form_id = $froms['fromid'];
                            $page = 'pages/index/index';
                            //消息模板id
                            $send_id = $template_id;
                            $keyword1 = array('value' => $sNo, "color" => "#173177");
                            $keyword2 = array('value' => $company, "color" => "#173177");
                            $keyword3 = array('value' => $time, "color" => "#173177");
                            $keyword4 = array('value' => '退款成功', "color" => "#173177");
                            $keyword5 = array('value' => $z_price . '元', "color" => "#173177");
                            $keyword6 = array('value' => '预计24小时内到账', "color" => "#173177");
                            $keyword7 = array('value' => '原支付方式', "color" => "#173177");
                            //拼成规定的格式
                            $o_data = array('keyword1' => $keyword1, 'keyword2' => $keyword2, 'keyword3' => $keyword3, 'keyword4' => $keyword4, 'keyword5' => $keyword5, 'keyword6' => $keyword6, 'keyword7' => $keyword7);

                            $res = $this -> Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data);

                            $this -> get_fromid($openid, $form_id);

                            $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' and r_status = '4'";
                            $res_o = $db -> selectrow($sql_o);
                            if ($res_o <= 1) {
                                // 根据订单号、用户id,修改订单状态(6 订单关闭)
                                $sql_u = "update lkt_order set status = '6' where sNo = '$sNo' ";
                                $r_u = $db -> update($sql_u);
                            }

                            // 根据订单号,查询商品id、商品名称、商品数量
                            $sql_o = "select p_id,num,p_name,sid from lkt_order_details where r_sNo = '$sNo' ";
                            $r_o = $db -> select($sql_o);
                            // 根据订单号,修改订单详情状态(订单关闭)
                            $sql_d = "update lkt_order_details set r_status = 6 where r_sNo = '$sNo' ";
                            $r_d = $db -> update($sql_d);
                            if($r_d < 1){
                                $db->rollback();
                                echo $sql_d;
                                echo 0;
                                exit;
                            }
                            //退款后还原商品数量
                            foreach ($r_o as $key => $value) {
                                $pid = $value -> p_id;
                                // 商品id
                                $num = $value -> num;
                                // 商品数量
                                $sid = $value -> sid;
                                // 商品属性id

                                // 根据商品id,修改商品数量
                                $sql_p = "update lkt_configure set  num = num + $num where id = $sid";
                                $r_p = $db -> update($sql_p);
                                // 根据商品id,修改卖出去的销量
                                $sql_x = "update lkt_product_list set volume = volume - $num,num = num+$num where id = $pid";
                                $r_x = $db -> update($sql_x);
                                if($r_d < 1 || $r_p < 1){
                                    $db->rollback();
                                    echo $sql_x;
                                    echo 0;
                                    exit;
                                }
                            }
                            if ($r_u && $r_d && $r_o) {
                                $res = 1;
                            } else {
                                $res = 0;
                            }
                        } else {
                            $res = 0;
                        }
                    }
                } else {
                    $res = 0;
                }
            }

        } else {
            if ($m == 8) {
                $sql_id = "select a.id,a.trade_no,a.sNo,a.pay,a.z_price,a.user_id from lkt_order as a LEFT JOIN lkt_order_details AS m ON a.sNo = m.r_sNo where m.id = '$id' and a.status = '4' ";
                $order_res = $db -> select($sql_id);
                $sNo = $order_res[0] -> sNo;
                $z_price = $order_res[0] -> z_price;
                $user_id = $order_res[0] -> user_id;
                // 根据订单号、用户id,修改订单状态
                $sql_u = "update lkt_order set status = '1' where sNo = '$sNo' ";
                $res1 = $db -> update($sql_u);

                // 根据订单号,修改订单详情状态
                $sql_d = "update lkt_order_details set r_status = '1',r_content = '$text' where r_sNo = '$sNo' ";
                $res2 = $db -> update($sql_d);

                if($res1 < 1 || $res2 < 1){
                    $db->rollback();
                    echo 0;
                    exit;
                }
                //查询openid
                $sql_openid = "select wx_id from lkt_user where user_id = '$user_id'";
                $res_openid = $db -> select($sql_openid);
                $openid = $res_openid[0] -> wx_id;
                $froms = $this -> get_fromid($openid);
                $form_id = $froms['fromid'];
                $page = 'pages/index/index';
                //消息模板id
                $send_id = $template_id;
                $keyword1 = array('value' => $sNo, "color" => "#173177");
                $keyword2 = array('value' => $company, "color" => "#173177");
                $keyword3 = array('value' => $time, "color" => "#173177");
                $keyword4 = array('value' => '退款失败', "color" => "#173177");
                $keyword5 = array('value' => $z_price . '元', "color" => "#173177");
                $keyword6 = array('value' => $text, "color" => "#173177");
                $keyword7 = array('value' => '系统更改订单状态', "color" => "#173177");
                //拼成规定的格式
                $o_data = array('keyword1' => $keyword1, 'keyword2' => $keyword2, 'keyword3' => $keyword3, 'keyword4' => $keyword4, 'keyword5' => $keyword5, 'keyword6' => $keyword6, 'keyword7' => $keyword7);

                $res = $this -> Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data);
                $this -> get_fromid($openid, $form_id);

            } else {
                $text = htmlentities($request -> getParameter('text'));
                $sql = "update lkt_order_details set r_type = '$m',r_content = '$text' where id = '$id'";
                $res = $db -> update($sql);
                if($res < 1){
                    $db->rollback();
                    echo 0;
                    exit;
                }
            }

        }

        if ($res) {
            $db->admin_record($admin_id,' 批准订单详情id为 '.$id.' 退货 ',9);
            $db->commit();
            echo 1;
        } else {
            $db->admin_record($admin_id,' 批准订单详情id为 '.$id.' 退货失败 ',9);
            echo 'rollback-3-';
            $db->rollback();
            echo 0;
        }

        exit ;

    }

    public function getRequestMethods() {
        return Request::POST;
    }

    public function Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data) {
        $db = DBAction::getInstance();
        $request = $this -> getContext() -> getRequest();
        $AccessToken = $this -> getAccessToken($appid, $appsecret);
        $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' . $AccessToken;
        $data = json_encode(array('access_token' => $AccessToken, 'touser' => $openid, 'template_id' => $send_id, 'form_id' => $form_id, 'page' => $page, 'data' => $o_data));
        $da = $this -> httpsRequest($url, $data);
        return $da;
    }

    public function get_fromid($openid, $type = '') {

        $db = DBAction::getInstance();
        $request = $this -> getContext() -> getRequest();
        if (empty($type)) {
            $fromidsql = "select fromid,open_id from lkt_user_fromid where open_id='$openid' and id=(select max(id) from lkt_user_fromid where open_id='$openid')";
            $fromidres = $db -> select($fromidsql);
            if ($fromidres) {
                $fromid = $fromidres[0] -> fromid;
                $arrayName = array('openid' => $openid, 'fromid' => $fromid);
                return $arrayName;
            } else {
                return array('openid' => $openid, 'fromid' => '123456');
            }
        } else {
            $delsql = "delete from lkt_user_fromid where open_id='$openid' and fromid='$type'";
            $re2 = $db -> delete($delsql);
            return $re2;
        }

    }

    function httpsRequest($url, $data = null) {
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

    function getAccessToken($appID, $appSerect) {
        $url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" . $appID . "&secret=" . $appSerect;
        // 时效性7200秒实现
        // 1.当前时间戳
        $currentTime = time();
        // 2.修改文件时间
        $fileName = "accessToken";
        // 文件名
        if (is_file($fileName)) {
            $modifyTime = filemtime($fileName);
            if (($currentTime - $modifyTime) < 7200) {
                // 可用, 直接读取文件的内容
                $accessToken = file_get_contents($fileName);
                return $accessToken;
            }
        }
        // 重新发送请求
        $result = $this -> httpsRequest($url);
        $jsonArray = json_decode($result, true);
        // 写入文件
        $accessToken = $jsonArray['access_token'];
        file_put_contents($fileName, $accessToken);
        return $accessToken;
    }

    /*
     * 发送请求
     @param $ordersNo string 订单号　
     @param $refund string 退款单号
     @param $price float 退款金额
     return array
     */
    private function wxrefundapi($ordersNo, $refund, $total_fee, $price,$appid,$mch_id,$mch_key) {
        //通过微信api进行退款流程
        $parma = array('appid' => $appid, 'mch_id' => $mch_id, 'nonce_str' => $this -> createNoncestr(), 'out_refund_no' => $refund, 'out_trade_no' => $ordersNo, 'total_fee' => $total_fee, 'refund_fee' => $price, 'op_user_id' =>  $appid);
        $parma['sign'] = $this -> getSign($parma,$mch_key);
        $xmldata = $this -> arrayToXml($parma);
        $xmlresult = $this -> postXmlSSLCurl($xmldata, 'https://api.mch.weixin.qq.com/secapi/pay/refund');
        $result = $this -> xmlToArray($xmlresult);
        return $result;
    }

    /*
     * 生成随机字符串方法
     */
    protected function createNoncestr($length = 32) {
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
    protected function getSign($Obj,$mch_key) {
        foreach ($Obj as $k => $v) {
            $Parameters[$k] = $v;
        }
        //签名步骤一：按字典序排序参数
        ksort($Parameters);
        $String = $this -> formatBizQueryParaMap($Parameters, false);
        //签名步骤二：在string后加入KEY
        $String = $String . "&key=".$mch_key;
        //签名步骤三：MD5加密
        $String = md5($String);
        //签名步骤四：所有字符转为大写
        $result_ = strtoupper($String);
        return $result_;
    }

    /*
     *排序并格式化参数方法，签名时需要使用
     */
    protected function formatBizQueryParaMap($paraMap, $urlencode) {
        $buff = "";
        ksort($paraMap);
        foreach ($paraMap as $k => $v) {
            if ($urlencode) {
                $v = urlencode($v);
            }
            //$buff .= strtolower($k) . "=" . $v . "&";
            $buff .= $k . "=" . $v . "&";
        }
        $reqPar;
        if (strlen($buff) > 0) {
            $reqPar = substr($buff, 0, strlen($buff) - 1);
        }
        return $reqPar;
    }

    //数组转字符串方法
    protected function arrayToXml($arr) {
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

    protected function xmlToArray($xml) {
        $array_data = json_decode(json_encode(simplexml_load_string($xml, 'SimpleXMLElement', LIBXML_NOCDATA)), true);
        return $array_data;
    }

    //需要使用证书的请求
    private function postXmlSSLCurl($xml, $url, $second = 30) {
        $ch = curl_init();
        //超时时间
        curl_setopt($ch, CURLOPT_TIMEOUT, $second);
        //这里设置代理，如果有的话
        //curl_setopt($ch,CURLOPT_PROXY, '8.8.8.8');
        //curl_setopt($ch,CURLOPT_PROXYPORT, 8080);
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
        $cert = MO_LIB_DIR . '/cert/apiclient_cert.pem';
        $key = MO_LIB_DIR . '/cert/apiclient_key.pem';

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

}
?>