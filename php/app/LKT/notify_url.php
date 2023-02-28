<?php
/**
 * [Laike System] Copyright (c) 2017-2022 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 *
 * 通用通知接口
 * ====================================================
 * 支付完成后，微信会把相关支付和用户信息发送到商户设定的通知URL，
 * 商户接收回调信息后，根据需要设定相应的处理流程。
 *
 */
session_start();
date_default_timezone_set('Asia/Chongqing');
set_time_limit(0);
require_once(dirname(__FILE__) . "/webapp/config.php");
require_once(MO_APP_DIR."/laiketui.php");
require_once(dirname(__FILE__) . '/webapp/config/db_config.php');
require_once(dirname(__FILE__) . '/webapp/lib/DBAction.class.php');
require_once(dirname(__FILE__) . '/webapp/lib/WxPayPubHelper/WxPayPubHelper.php');
require_once(dirname(__FILE__) . '/webapp/lib/WxPayPubHelper/log_.php');
require_once(dirname(__FILE__) . '/webapp/lib/SysConst.class.php');

class order
{


    //充值成功金额增加
    public function cz($openid, $cmoney, $trade_no)
    {

        $db = DBAction::getInstance();
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        $money = $r[0]->money; // 用户金额
        $user_id = $r[0]->user_id; // 用户id
        $event = $trade_no;
        $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$cmoney','$money','$event',1)";
        $db->insert($sqll);
        //修改金额   
        $sql = "update lkt_user set money = money+'$cmoney' where wx_id = '$openid'";
        $db->update($sql);
        exit;
    }

    //开团
    public function creatgroup($order)
    {

        $db = DBAction::getInstance();
        //开启事务
        $db->begin();
        $uid = $order['uid'];
        $pro_id = $order['pro_id'];
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
        $buy_num = $order['buy_num'];
        $paytype = $order['paytype'];
        $trade_no = $order['trade_no'];
        $status = $order['status'];
        $ordstatus = $status == 1 ? 9 : 0;

        $group_num = 'KT' . substr(time(), 5) . mt_rand(10000, 99999);
        $creattime = date('Y-m-d H:i:s');
        $time_over = explode(':', $time_over);
        $time_over = date('Y-m-d H:i:s', $time_over[0] * 3600 + $time_over[1] * 60 + time());
        $pro_size = $db->select("select name,color,size from lkt_configure where id=$sizeid");
        $pro_size = $pro_size[0]->name . $pro_size[0]->color . $pro_size[0]->size;

        $istsql1 = "insert into lkt_group_open(uid,ptgoods_id,ptcode,ptnumber,addtime,endtime,ptstatus,group_id) values('$uid',$pro_id,'$group_num',1,'$creattime','$time_over',$status,'$groupid')";
        $res1 = $db->insert($istsql1);

        if ($res1 < 1) {
            $db->rollback();
            return $istsql1;
        }


        $user_id = $db->select("select user_id from lkt_user where wx_id='$uid'");
        $uid = $user_id[0]->user_id;
        $ordernum = 'PT' . mt_rand(10000, 99999) . date('Ymd') . substr(time(), 5);
        $istsql2 = "insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,status,otype,ptcode,pid,ptstatus,trade_no) values('$uid','$name','$tel',$buy_num,$price,'$ordernum',$sheng,$shi,$quyu,'$address','$paytype','$creattime',$ordstatus,'pt','$group_num','$groupid',$status,'$trade_no')";
        $res2 = $db->insert($istsql2);
        if ($res2 < 1) {
            $db->rollback();
            return $istsql2;
        }


        $istsql3 = "insert into lkt_order_details(user_id,p_id,p_name,p_price,num,r_sNo,add_time,r_status,size,sid) values('$uid',$pro_id,'$pro_name',$y_price,$buy_num,'$ordernum','$creattime',-1,'$pro_size',$sizeid)";
        $res3 = $db->insert($istsql3);
        if ($res3 < 1) {
            $db->rollback();
            return $istsql3;
        }
        $db->commit();


    }

    //参团
    public function can_order($order)
    {

        $db = DBAction::getInstance();
        //开启事务
        $db->begin();
        $oid = $order['oid'];
        $uid = $order['uid'];
        $pro_id = $order['pro_id'];
        $man_num = $order['man_num'];
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
        $buy_num = $order['buy_num'];
        $paytype = $order['paytype'];
        $trade_no = $order['trade_no'];
        $status = $order['status'];
        $ordstatus = $status == 1 ? 9 : 0;

        $creattime = date('Y-m-d H:i:s');
        $pro_size = $db->select("select name,color,size from lkt_configure where id=$sizeid");
        $pro_size = $pro_size[0]->name . $pro_size[0]->color . $pro_size[0]->size;
        $selsql = "select ptnumber,ptstatus,endtime from lkt_group_open where group_id='$groupid' and ptcode='$oid'";
        $selres = $db->select($selsql);
        if (!empty($selres)) {

            $ptnumber = $selres[0]->ptnumber;
            $endtime = strtotime($selres[0]->endtime);

        }

        $ordernum = 'PT' . mt_rand(10000, 99999) . date('Ymd') . substr(time(), 5);
        $user_id = $db->select("select user_id from lkt_user where wx_id='$uid'");
        $uid = $user_id[0]->user_id;

        if ($endtime >= time()) {

            if (($ptnumber + 1) < $man_num) {

                $istsql2 = "insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,otype,ptcode,pid,ptstatus,status,trade_no) values('$uid','$name','$tel',$buy_num,$price,'$ordernum',$sheng,$shi,$quyu,'$address','$paytype','$creattime','pt','$oid','$groupid',$status,$ordstatus,'$trade_no')";
                $res2 = $db->insert($istsql2);
                $istsql3 = "insert into lkt_order_details(user_id,p_id,p_name,p_price,num,r_sNo,add_time,r_status,size,sid) values('$uid',$pro_id,'$pro_name',$y_price,$buy_num,'$ordernum','$creattime',-1,'$pro_size',$sizeid)";
                $res3 = $db->insert($istsql3);
                $updsql = "update lkt_group_open set ptnumber=ptnumber+1 where group_id='$groupid' and ptcode='$oid'";
                $db->update($updsql);
                if ($res2 > 0 && $res3 > 0) {

                    $idres = $db->select("select id from lkt_order where sNo='$ordernum'");
                    if (!empty($idres)) $idres = $idres[0]->id;
                    $db->commit();
                    return 'code:1';
                } else {

                    $db->rollback();
                    return 'code:2';

                }

            } else if (($ptnumber + 1) === $man_num) {

                $istsql2 = "insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,otype,ptcode,pid,ptstatus,status,trade_no) values('$uid','$name','$tel',$buy_num,'$price','$ordernum',$sheng,$shi,$quyu,'$address','$paytype','$creattime','pt','$oid','$groupid',$status,$ordstatus,'$trade_no')";
                $res2 = $db->insert($istsql2);
                $istsql3 = "insert into lkt_order_details(user_id,p_id,p_name,p_price,num,r_sNo,add_time,r_status,size,sid) values('$uid',$pro_id,'$pro_name',$y_price,$buy_num,'$ordernum','$creattime',-1,'$pro_size',$sizeid)";
                $res3 = $db->insert($istsql3);
                $updsql = "update lkt_group_open set ptnumber=ptnumber+1,ptstatus=2 where group_id='$groupid' and ptcode='$oid'";
                $db->update($updsql);
                $beres = $db->update("update lkt_order set ptstatus=2,status=1 where pid='$groupid' and ptcode='$oid'");

                if ($beres < 1) {
                    $db->rollback();
                    return 'code:3';
                }

                $selmsg = "select m.*,d.p_name,d.p_price,d.num,d.sid from (select o.id,o.user_id,o.ptcode,o.sNo,o.z_price,u.wx_id as uid from lkt_order as o left join lkt_user as u on o.user_id=u.user_id where o.pid='$groupid' and o.ptcode='$oid') as m left join lkt_order_details as d on m.sNo=d.r_sNo";
                $msgres = $db->select($selmsg);

                foreach ($msgres as $k => $v) {

                    $beres = $db->update("update lkt_configure set num=num-$v->num where id=$v->sid");
                    if ($beres < 1) {
                        $db->rollback();
                        return 'code:4';
                    }

                    $fromidsql = "select fromid,open_id from lkt_user_fromid where open_id='$v->uid' and id=(select max(id) from lkt_user_fromid where open_id='$v->uid')";
                    $fromidres = $db->select($fromidsql);
                    foreach ($fromidres as $ke => $val) {

                        if ($val->open_id == $v->uid) {
                            $msgres[$k]->fromid = $val->fromid;
                        }

                    }

                }

                if ($res2 > 0 && $res3 > 0) {
                    $sql = "select * from lkt_notice where id = '1'";
                    $db->select($sql);
                    $db->commit();
                } else {
                    $db->rollback();
                    return 'code:5';

                }

            } else if ($ptnumber == $man_num) {

                $beres = $db->update("update lkt_user set money=money+$price where user_id='$uid'");
                if ($beres < 1) {
                    $db->rollback();
                    return 'code:6';
                }
                $db->commit();
                return 'code:7';

            }


        } else {

            $beres = $db->update("update lkt_user set money=money+$price where user_id='$uid'");
            if ($beres < 1) {
                $db->rollback();
                return 'code:8';
            }

        }

    }

}

$db = DBAction::getInstance();

//使用通用通知接口
$notify = new Notify_pub();
//存储微信的回调
$xml = isset($GLOBALS['HTTP_RAW_POST_DATA'])? $GLOBALS['HTTP_RAW_POST_DATA'] : file_get_contents('php://input');
$notify->saveData($xml);
if ($notify->checkSign() == FALSE) {
    $notify->setReturnParameter("return_code", "FAIL");//返回状态码
    $notify->setReturnParameter("return_msg", "签名失败");//返回信息
} else {
    $notify->setReturnParameter("return_code", "SUCCESS");//设置返回码
}

$returnXml = $notify->returnXml();
echo $returnXml;

//以log文件形式记录回调信息
$log_ = new Log_();
$log_name = "./notify_url.log";//log文件路径
$log_->log_result($log_name, "【接收到的notify通知】:\n" . $xml . "\n");

if ($notify->checkSign() == TRUE) {

    $log_->log_result($log_name, "【签名验证结果 succ】:\n" . $notify->checkSign() . "\n");
    if ($notify->data["return_code"] == "FAIL") {
        //此处应该更新一下订单状态，商户自行增删操作
        $log_->log_result($log_name, "【通信出错】:\n" . $xml . "\n");

    } elseif ($notify->data["result_code"] == "FAIL") {
        //此处应该更新一下订单状态，商户自行增删操作
        $log_->log_result($log_name, "【业务出错】:\n" . $xml . "\n");

    } else {
        //此处应该更新一下订单状态，商户自行增删操作
        $trade_no = $notify->data["out_trade_no"];
        $type = substr($trade_no, 0, 2);

        if ($type == 'CZ') {
            $czsql = "select event from lkt_record where event = '$trade_no' ";
            $czres = $db->select($czsql);
            if (!$czres) {
                $order = new order;
                $openid = $notify->data["openid"];
                $cmoney = $notify->data["total_fee"] / 100;
                $order->cz($openid, $cmoney, $trade_no);
            }

        } else if ($type == 'PT') {

            $dsql = "select data from lkt_order_data where trade_no = '$trade_no'";
            $dres = $db->select($dsql);
            $data = unserialize($dres[0]->data);
            $order = new order;
            if ($data['pagefrom'] == 'kaituan') {
                $ptres = $order->creatgroup($data);
            } else {
                $ptres = $order->can_order($data);
            }
            $log_->log_result($log_name, "【拼团处理结果】:\n" . $ptres . "\n");

        } else {

            $sql = "select * from lkt_order where trade_no='$trade_no'";
            $r = $db->select($sql);
            if ($r) {
                $status = $r[0]->status;
                $sNo = $r[0]->sNo;
                if ($status < 1) {
                    $sql_u = "update lkt_order set status = 1,trade_no='$trade_no' where sNo = '$sNo' ";
                    $r_u = $db->update($sql_u);
                    if ($r_u) {
                        $time = date("Y-m-d h:i:s", time()); // 当前时间

                    }

                }


            }

        }

    }


} else {

    $log_->log_result($log_name, "【签名验证结果 fail】:\n" . $notify->checkSign() . "\n");

}


?>