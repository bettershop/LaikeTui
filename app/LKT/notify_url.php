<?php

/**

 * 通用通知接口demo

 * ====================================================

 * 支付完成后，微信会把相关支付和用户信息发送到商户设定的通知URL，

 * 商户接收回调信息后，根据需要设定相应的处理流程。

 * 

 * 这里举例使用log文件形式记录回调信息。

*/





session_name("money_mojavi");

session_start();

date_default_timezone_set('Asia/Chongqing');

set_time_limit(0);

require_once(dirname(__FILE__)."/webapp/config.php");

require_once(MO_APP_DIR."/mojavi.php");

require_once(dirname(__FILE__).'/webapp/config/db_config.php');

require_once(dirname(__FILE__).'/webapp/lib/DBAction.class.php');

require_once(dirname(__FILE__).'/webapp/lib/WxPayPubHelper/WxPayPubHelper.php');

require_once(dirname(__FILE__).'/webapp/lib/WxPayPubHelper/log_.php');

require_once(dirname(__FILE__) . '/webapp/lib/SysConst.class.php');



$db = DBAction::getInstance();

class order

{

    // 付款后修改订单状态,并修改商品库存-----计算分销

    function up_order($data){

        $db = DBAction::getInstance();
        $coupon_id = $data['coupon_id']; // 优惠券id
        $allow = $data['allow']; // 用户使用消费金
        $coupon_money = $data['coupon_money']; // 付款金额
        $order_id = $data['order_id']; // 订单号
        $user_id = $data['user_id']; // 微信id
        $d_yuan = $data['d_yuan']; // 抵扣余额
        $trade_no = $data['trade_no']; // 微信支付单号
        $pay = $data['pay'];
        // 根据微信id,查询用户id
        $sql_user = 'select user_id,money from lkt_user where wx_id=\''.$user_id.'\'';
        $r_user = $db->select($sql_user);
        if($r_user){
            $userid = $r_user['0']->user_id; // 用户id
            $user_money =  $r_user['0']->money; // 用户余额
            if($coupon_money <= 0 && $allow > 0){
                // 根据订单号、用户id,修改订单状态(未发货)
                $sql_u = "update lkt_order set status = 1,pay = 'consumer_pay',trade_no='$trade_no' where sNo = '$order_id' and user_id = '$userid' ";
                $r_u = $db->update($sql_u);
            }else{
                // 根据订单号、用户id,修改订单状态(未发货)
                $rpay = '';
                if($pay){
                    $rpay = " ,pay = '$pay'";
                }
                $sql_u = "update lkt_order set status = 1 $rpay,trade_no='$trade_no' where sNo = '$order_id' and user_id = '$userid' ";
                $r_u = $db->update($sql_u);
            }

            if($d_yuan){
                // 使用组合支付的时候 lkt_combined_pay
                $sql = "update lkt_user set money = money-'$d_yuan' where user_id = '$userid'";
                $r = $db->update($sql);
                $weixin_pay = $coupon_money - $d_yuan;
                //写入日志
                $sqll = "insert into lkt_combined_pay (weixin_pay,balance_pay,total,order_id,add_time,user_id) values ('$weixin_pay','$d_yuan','$coupon_money','$order_id',CURRENT_TIMESTAMP,'$user_id')";
                $rr = $db->insert($sqll);
                // 根据修改支付方式
                $sql_combined = "update lkt_order set pay = 'combined_Pay' where sNo = '$order_id' and user_id = '$userid' ";
                $r_combined = $db->update($sql_combined);
                //微信支付记录-写入日志
                $event = $userid.'使用余额抵扣了'.$d_yuan.'元余额--订单号:'.$order_id;
                $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$userid','$d_yuan','$user_money','$event',4)";
                $rr = $db->insert($sqll);
            }



            if($allow && $coupon_money > 0 && $allow != 'undefined'){
                // 使用组合支付的时候 lkt_combined_pay 消费金情况
                if($pay == 'wallet_Pay'){
                    $zpay = 'balance_pay';
                }else{
                    $zpay = 'weixin_pay';
                }
                //写入日志
                $total = $allow + $coupon_money;
                $sqll = "insert into lkt_combined_pay ($zpay,consumer_pay,total,order_id,add_time,user_id) values ('$coupon_money','$allow','$total','$order_id',CURRENT_TIMESTAMP,'$user_id')";
                $rr = $db->insert($sqll);
                // 根据修改支付方式
                $sql_combined = "update lkt_order set pay = 'combined_Pay' where sNo = '$order_id' and user_id = '$userid' ";
                $r_combined = $db->update($sql_combined);
            }



            // 根据用户id、优惠券id,修改优惠券状态(已使用)
            $sql = "update lkt_coupon set type = 2 where user_id = '$userid' and id = '$coupon_id'";
            $db->update($sql);
            // 根据订单号,查询商品id、商品名称、商品数量
            $sql_o = "select p_id,num,p_name,sid from lkt_order_details where r_sNo = '$order_id' ";
            $r_o = $db->select($sql_o);
            // 根据订单号,修改订单详情状态(未发货)
            $sql_d = "update lkt_order_details set r_status = 1 where r_sNo = '$order_id' ";
            $r_d = $db->update($sql_d);
            // 修改产品数据库数量
            $pname = '';
            foreach ($r_o as $key => $value) {
                $pid = $value->p_id; // 商品id
                $num = $value->num; // 商品数量
                $p_name = $value->p_name; // 商品名称
                $sid = $value->sid; // 商品属性id
                $pname .= $p_name;
                // 根据商品id,修改商品数量
                $sql_p = "update lkt_configure set  num = num - $num where id = $sid";
                $r_p = $db->update($sql_p); 
                // 根据商品id,修改卖出去的销量
                $sql_x = "update lkt_product_list set volume = volume + $num,num = num-$num where id = $pid";
                $r_x = $db->update($sql_x); 
            }
            return true;
        }

    }
    

    //充值成功金额增加
    public function cz($openid,$cmoney,$trade_no){

        $db = DBAction::getInstance();

        $sql = "select * from lkt_user where wx_id = '$openid'";

        $r = $db -> select($sql);

        $money = $r[0]->money; // 用户金额

        $user_id = $r[0]->user_id; // 用户id

        $event = $trade_no;

        $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$cmoney','$money','$event',1)";

        $rr = $db->insert($sqll);

        //修改金额   

        $sql = "update lkt_user set money = money+'$cmoney' where wx_id = '$openid'";

        $r = $db->update($sql);

        exit;

    }

    //开团
    public function creatgroup($order){

        $db = DBAction::getInstance();

        //开启事务

        $db->begin();

        $uid = $order['uid'];

        $form_id = $order['form_id'];

        $pro_id = $order['pro_id'];

        $man_num =  $order['man_num'];

        $time_over = $order['time_over'];

        $sizeid = $order['sizeid'];

        $groupid = $order['groupid'];

        $pro_name = $order['pro_name'];

        $price = $order['price'];

        $y_price = $order['y_price'];

        $name = $order['name'];

        $sheng = $order['sheng'];

        $shi = $order['shi'];

        $quyu = $order['quyu'];

        $address = $order['address'];

        $tel = $order['tel'];

        $lack = $order['lack'];

        $buy_num = $order['buy_num'];

        $paytype = $order['paytype'];

        $trade_no = $order['trade_no'];

        $status = $order['status'];

        $ordstatus = $status == 1?9:0;



        $group_num = 'KT'.substr(time(),5).mt_rand(10000,99999);

        $creattime = date('Y-m-d H:i:s');

        $time_over = explode(':', $time_over);

        $time_over = date('Y-m-d H:i:s',$time_over[0]*3600 + $time_over[1]*60 + time());

        $pro_size = $db -> select("select name,color,size from lkt_configure where id=$sizeid");

        $pro_size = $pro_size[0] -> name.$pro_size[0] -> color.$pro_size[0] -> size;



        $istsql1 = "insert into lkt_group_open(uid,ptgoods_id,ptcode,ptnumber,addtime,endtime,ptstatus,group_id) values('$uid',$pro_id,'$group_num',1,'$creattime','$time_over',$status,'$groupid')";
        $res1 = $db -> insert($istsql1);

        if($res1 < 1){
            $db->rollback();
            return $istsql1;
        }
        

        $user_id = $db -> select("select user_id from lkt_user where wx_id='$uid'");

        $uid = $user_id[0] -> user_id;

        $ordernum = 'PT'.mt_rand(10000,99999).date('Ymd').substr(time(),5);

        $istsql2 = "insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,status,otype,ptcode,pid,ptstatus,trade_no) values('$uid','$name','$tel',$buy_num,$price,'$ordernum',$sheng,$shi,$quyu,'$address','$paytype','$creattime',$ordstatus,'pt','$group_num','$groupid',$status,'$trade_no')";
        $res2 = $db -> insert($istsql2);
        if($res2 < 1){
            $db->rollback();
            return $istsql2;
        }
        

        $istsql3 = "insert into lkt_order_details(user_id,p_id,p_name,p_price,num,r_sNo,add_time,r_status,size,sid) values('$uid',$pro_id,'$pro_name',$y_price,$buy_num,'$ordernum','$creattime',-1,'$pro_size',$sizeid)";

        $res3 = $db -> insert($istsql3);
        if($res3 < 1){
            $db->rollback();
            return $istsql3;
        }
         $db->commit();

        // $idres = $db -> select("select id from lkt_order where sNo='$ordernum'");

        // if(!empty($idres)) $idres = $idres[0] -> id;

        // if($res1 > 0 && $res2 > 0 && $res3 > 0){

        //     $db->commit();
        //     return 1;
        // }else{

        //      $db->rollback();
        //     return  0;
        // }

    } 

    //参团
    public function can_order($order){

        $db = DBAction::getInstance();

        //开启事务

        $db->begin();

        $oid = $order['oid'];

        $uid = $order['uid'];

        $form_id = $order['form_id'];

        $pro_id = $order['pro_id'];

        $man_num =  $order['man_num'];

        $sizeid = $order['sizeid'];

        $groupid = $order['groupid'];

        $pro_name = $order['pro_name'];

        $price = $order['price'];

        $y_price = $order['y_price'];

        $name = $order['name'];

        $sheng = $order['sheng'];

        $shi = $order['shi'];

        $quyu = $order['quyu'];

        $address = $order['address'];

        $tel = $order['tel'];

        $lack = $order['lack'];

        $buy_num = $order['buy_num'];

        $paytype = $order['paytype'];

        $trade_no = $order['trade_no'];

        $status = $order['status'];

        $ordstatus = $status == 1?9:0;



        $creattime = date('Y-m-d H:i:s');

        $pro_size = $db -> select("select name,color,size from lkt_configure where id=$sizeid");

        $pro_size = $pro_size[0] -> name.$pro_size[0] -> color.$pro_size[0] -> size;

        $selsql = "select ptnumber,ptstatus,endtime from lkt_group_open where group_id='$groupid' and ptcode='$oid'";

        $selres = $db -> select($selsql);

        if(!empty($selres)){

            $ptnumber = $selres[0] -> ptnumber;

            $ptstatus = $selres[0] -> ptstatus;

            $endtime = strtotime($selres[0] -> endtime);

        }

        $ordernum = 'PT'.mt_rand(10000,99999).date('Ymd').substr(time(),5);

        $user_id = $db -> select("select user_id from lkt_user where wx_id='$uid'");

        $uid = $user_id[0] -> user_id;

       if($endtime >= time()){

        if(($ptnumber+1) < $man_num){

            $istsql2 = "insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,otype,ptcode,pid,ptstatus,status,trade_no) values('$uid','$name','$tel',$buy_num,$price,'$ordernum',$sheng,$shi,$quyu,'$address','$paytype','$creattime','pt','$oid','$groupid',$status,$ordstatus,'$trade_no')";

            $res2 = $db -> insert($istsql2);    

            $istsql3 = "insert into lkt_order_details(user_id,p_id,p_name,p_price,num,r_sNo,add_time,r_status,size,sid) values('$uid',$pro_id,'$pro_name',$y_price,$buy_num,'$ordernum','$creattime',-1,'$pro_size',$sizeid)";   

            $res3 = $db -> insert($istsql3);

            $updsql = "update lkt_group_open set ptnumber=ptnumber+1 where group_id='$groupid' and ptcode='$oid'";

            $updres = $db -> update($updsql);

            if($res2 > 0 && $res3>0){

                $idres = $db -> select("select id from lkt_order where sNo='$ordernum'");

                if(!empty($idres)) $idres = $idres[0] -> id;

                    $db->commit();
                    return  'code:1';
            }else{

                 $db->rollback();
                 return  'code:2';

            }

        }else if(($ptnumber+1) === $man_num){

            $istsql2 = "insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,otype,ptcode,pid,ptstatus,status,trade_no) values('$uid','$name','$tel',$buy_num,'$price','$ordernum',$sheng,$shi,$quyu,'$address','$paytype','$creattime','pt','$oid','$groupid',$status,$ordstatus,'$trade_no')";

            $res2 = $db -> insert($istsql2);    

            $istsql3 = "insert into lkt_order_details(user_id,p_id,p_name,p_price,num,r_sNo,add_time,r_status,size,sid) values('$uid',$pro_id,'$pro_name',$y_price,$buy_num,'$ordernum','$creattime',-1,'$pro_size',$sizeid)";

            $res3 = $db -> insert($istsql3);

            $updsql = "update lkt_group_open set ptnumber=ptnumber+1,ptstatus=2 where group_id='$groupid' and ptcode='$oid'";

            $updres = $db -> update($updsql);

            $beres = $db -> update("update lkt_order set ptstatus=2,status=1 where pid='$groupid' and ptcode='$oid'");

            if($beres < 1){

                $db->rollback();
                return  'code:3';
              }

            $selmsg = "select m.*,d.p_name,d.p_price,d.num,d.sid from (select o.id,o.user_id,o.ptcode,o.sNo,o.z_price,u.wx_id as uid from lkt_order as o left join lkt_user as u on o.user_id=u.user_id where o.pid='$groupid' and o.ptcode='$oid') as m left join lkt_order_details as d on m.sNo=d.r_sNo";

            $msgres = $db -> select($selmsg);

            

            foreach ($msgres as $k => $v) {

                $beres = $db -> update("update lkt_configure set num=num-$v->num where id=$v->sid");

                if($beres < 1){

                    $db->rollback();
                    return  'code:4';
                }

                $fromidsql = "select fromid,open_id from lkt_user_fromid where open_id='$v->uid' and id=(select max(id) from lkt_user_fromid where open_id='$v->uid')";

                $fromidres = $db -> select($fromidsql);                           

                foreach ($fromidres as $ke => $val) {

                    if($val -> open_id == $v -> uid){

                        $msgres[$k] -> fromid = $val -> fromid;

                    }

                }     

            }           

            if($res2 > 0 && $res3 > 0){  

                $sql = "select * from lkt_notice where id = '1'";

                $r = $db->select($sql);

                $template_id = $r[0]->group_success;

              $db->commit();

              $this -> Send_success($msgres,date('Y-m-d H:i:s',time()),$template_id,$pro_name);

            }else{

                $db->rollback();

                return  'code:5';

            }

         }else if($ptnumber == $man_num){

              $bere = $db -> update("update lkt_user set money=money+$price where user_id='$uid'");

              if($beres < 1){

                $db->rollback();
                return  'code:6';
              }

              $db->commit();
              return  'code:7';


         }else{



         }

        }else{

            $bere = $db -> update("update lkt_user set money=money+$price where user_id='$uid'");

              if($beres < 1){

                $db->rollback();
                return  'code:8';
              }

        }

    }

}



// $trade_no = 'GM181205091325772072';

// $sql = "select * from lkt_order where trade_no='$trade_no'";

// $r = $db->select($sql);

// if($r){

//  $status = $r[0]->status;

//  $sNo = $r[0]->sNo;

//  // if($status < 1){

//      $sql_u = "update lkt_order set status = 1,trade_no='$trade_no' where sNo = '$sNo' ";

//               $r_u = $db->update($sql_u);

//               // if($r_u){

//                $time =date("Y-m-d h:i:s",time()); // 当前时间

//                //分销结算-------假定是默认只有支付成功后计算发放佣金

//                $fxsql = "select sNo,id from lkt_distribution_record where sNo = '$sNo'";

//                $fxres = $db->select($fxsql);

//                // $log_->log_result($log_name,"【data-ru】:\n".'--'."\n");

//                      $dsql = "select data from lkt_order_data where trade_no = '$trade_no'";

//                      $dres = $db->select($dsql);

//                      $data = unserialize($dres[0]->data);

//                      $order = new order;

//                      $abc = $order -> up_order($data);

//                      var_dump($abc);exit;

//                      // $log_->log_result($log_name,"【data】:\n".$dres[0]->data."\n");


//               // }

//  // }
// }
//          exit;



    //使用通用通知接口

    $notify = new Notify_pub();



    //存储微信的回调 s

   
    $xml = PHP_VERSION <= 5.6 ? $GLOBALS['HTTP_RAW_POST_DATA']:file_get_contents('php://input');
    
    // $xml = $GLOBALS['HTTP_RAW_POST_DATA']:;   php <= 5.6
    // $xml = file_get_contents('php://input');    //php >5.6

    $notify->saveData($xml);

    

    //验证签名，并回应微信。

    //对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，

    //微信会通过一定的策略（如30分钟共8次）定期重新发起通知，

    //尽可能提高通知的成功率，但微信不保证通知最终能成功。

    if($notify->checkSign() == FALSE){

        $notify->setReturnParameter("return_code","FAIL");//返回状态码

        $notify->setReturnParameter("return_msg","签名失败");//返回信息

    }else{

        $notify->setReturnParameter("return_code","SUCCESS");//设置返回码

    }

    $returnXml = $notify->returnXml();

    echo $returnXml;

    //==商户根据实际情况设置相应的处理流程，此处仅作举例=======

    

    //以log文件形式记录回调信息

    $log_ = new Log_();

    $log_name="./notify_url.log";//log文件路径

    $log_->log_result($log_name,"【接收到的notify通知】:\n".$xml."\n");



    if($notify->checkSign() == TRUE)

    {

        $log_->log_result($log_name,"【签名验证结果 succ】:\n".$notify->checkSign()."\n");

        if ($notify->data["return_code"] == "FAIL") {

            //此处应该更新一下订单状态，商户自行增删操作

            $log_->log_result($log_name,"【通信出错】:\n".$xml."\n");

        }

        elseif($notify->data["result_code"] == "FAIL"){

            //此处应该更新一下订单状态，商户自行增删操作

            $log_->log_result($log_name,"【业务出错】:\n".$xml."\n");

        }

        else{



            //此处应该更新一下订单状态，商户自行增删操作

            $trade_no = $notify->data["out_trade_no"];

            $type = substr($trade_no,0,2);

            if($type == 'CZ'){

                $czsql = "select event from lkt_record where event = '$trade_no' ";

                $czres = $db->select($czsql);

                if(!$czres){

                    $order = new order;

                    $openid = $notify->data["openid"];

                    $cmoney = $notify->data["total_fee"]/100;

                    $order -> cz($openid,$cmoney,$trade_no); 

                }



            }else if($type == 'PT'){

                    $dsql = "select data from lkt_order_data where trade_no = '$trade_no'";

                    $dres = $db->select($dsql);

                    $data = unserialize($dres[0]->data);

                    $order = new order;

                    if($data['pagefrom'] =='kaituan'){

                        $ptres = $order -> creatgroup($data);

                    }else{

                        $ptres = $order -> can_order($data);

                    }
                    $log_->log_result($log_name,"【拼团处理结果】:\n".$ptres."\n");

            }else{

                $sql = "select * from lkt_order where trade_no='$trade_no'";

                $r = $db->select($sql);

                if($r){

                    $status = $r[0]->status;

                    $sNo = $r[0]->sNo;

                    if($status < 1){

                        $sql_u = "update lkt_order set status = 1,trade_no='$trade_no' where sNo = '$sNo' ";

                        $r_u = $db->update($sql_u);

                        if($r_u){

                            $time =date("Y-m-d h:i:s",time()); // 当前时间

                            //分销结算-------假定是默认只有支付成功后计算发放佣金

                            $fxsql = "select sNo,id from lkt_distribution_record where sNo = '$sNo'";

                            $fxres = $db->select($fxsql);

                            $log_->log_result($log_name,"【data-ru】:\n".$r_u.'---'.$fxres."\n");

                            $dsql = "select data from lkt_order_data where trade_no = '$trade_no'";

                            $dres = $db->select($dsql);

                            $data = unserialize($dres[0]->data);

                            $order = new order;

                            $gm_res = $order -> up_order($data);

                            $log_->log_result($log_name,"【up_order】:\n".$gm_res."\n");
                            $log_->log_result($log_name,"【data】:\n".$dres[0]->data."\n");

                            //分销结算结束

                        }

                    }



                }

            }

        }



    }else{

        $log_->log_result($log_name,"【签名验证结果 fail】:\n".$notify->checkSign()."\n");

    }

    

?>