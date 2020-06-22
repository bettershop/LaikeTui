<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');
require_once(MO_LIB_DIR . '/Timer.class.php');

class addsignAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id');
        $m = $request->getParameter('m');

        $sNo = trim($request->getParameter('sNo')); // 订单号
        if ($m == 'close') {
            $this->close($sNo);
        }

        //运费
        $sql02 = "select * from lkt_express ";
        $r02 = $db->select($sql02);
        if (isset($_GET['otype'])) {
            $request->setAttribute("otype", $_GET['otype']);
        } else {
            $request->setAttribute("otype", 'yb');
        }
        $res_p = '';
        if ($sNo) {
            $sql_p = "select o.id,o.p_name,o.num,o.size,d.img,o.p_price,o.r_sNo,o.express_id from lkt_order_details as o left join lkt_configure as d on o.sid=d.id where o.r_sNo='$sNo' and o.r_status =1  ";
            $res_p = $db->select($sql_p);
            $sqlcf = "select * from lkt_config where id = '1'";
            $rcf = $db->select($sqlcf);
            $uploadImg = $rcf[0]->uploadImg;
        }

        $request->setAttribute("uploadImg", $uploadImg);
        $request->setAttribute("express", $r02);
        $request->setAttribute("id", $id);
        $request->setAttribute("pro", $res_p);

        return View::INPUT;
    }

    public function execute()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');
        $m = $request->getParameter('m');
        $sNo = trim($request->getParameter('sNo')); // 订单号
        if ($m == 'close') {
            $this->close($sNo);
        }
        //开启事务
        $db->begin();
        $id = trim($request->getParameter('ids')); // ID
        $trade = intval($request->getParameter('trade')) - 1;
        $express_id = $request->getParameter('express'); // 快递公司id
        $courier_num = $request->getParameter('courier_num'); // 快递单号
        $otype = addslashes(trim($request->getParameter('otype'))); // 类型
        $express_name = $request->getParameter('express_name'); // 快递公司名称
        $time = date('Y-m-d H:i:s', time());
        $con = " ";

        if (!empty($express_id)) {
            $con = ",express_id='$express_id'";
        }

        if (!empty($courier_num)) {
            $sql = "select id from lkt_order_details where r_sNo != '$sNo' and express_id = '$express_id' and courier_num = '$courier_num'";
            $rr = $db->select($sql);
            if ($rr) {
                $db->rollback();
                echo 0;
                exit();
            } else {
                $con .= ",courier_num ='$courier_num '";
            }
        }

        $con .= ",deliver_time= ' $time '";
        $id = explode(',', $id);


        if ($otype == 'yb') {

            $sql_config = "select * from lkt_config where id=1";
            $r = $db->select($sql_config);
            if ($r) {
                $appid = $r[0]->appid;
                // 小程序唯一标识
                $appsecret = $r[0]->appsecret;
                // 小程序的 app secret
            }
            if ($id) {
                foreach ($id as $key => $value) {

                    $rd = $db->update("update lkt_order_details set r_status='$trade' $con where id='$value'");
                    if ($rd < 1) {
                        $db->rollback();
                        echo 0;
                        exit();
                    }
                    //查询订单信息
                    $sql_p = "select o.id,o.user_id,o.sNo,d.p_name,o.name,o.address,d.p_id,d.sid,d.num from lkt_order as o left join lkt_order_details as d on o.sNo=d.r_sNo where d.id='$value'";
                    $res_p = $db->select($sql_p);
                    foreach ($res_p as $key => $value) {
                        $p_name = $value->p_name;
                        $user_id = $value->user_id;
                        $address = $value->address;
                        $name = $value->name;
                        $order_id = $value->id;
                        $p_id = $value->p_id;
                        $sid = $value->sid;
                        $num = $value->num;
                        $sNo = $value->sNo;
                        $db->insert("insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$p_id','$sid','$num',1,CURRENT_TIMESTAMP)");//增加一条出库记录
                        //查询openid
                        $sql_openid = "select wx_id from lkt_user where user_id = '$user_id'";
                        $res_openid = $db->select($sql_openid);
                        $openid = $res_openid[0]->wx_id;
                        $froms = $this->get_fromid($openid);
                        $form_id = $froms['fromid'];
                        $page = 'pages/order/detail?orderId=' . $order_id;
                        //消息模板id

                        $sql = "select * from lkt_notice where id = '1'";
                        $r = $db->select($sql);
                        $template_id = $r[0]->order_delivery;

                        $send_id = $template_id;
                        $keyword1 = array('value' => $express_name, "color" => "#173177");
                        $keyword2 = array('value' => $time, "color" => "#173177");
                        $keyword3 = array('value' => $p_name, "color" => "#173177");
                        $keyword4 = array('value' => $sNo, "color" => "#173177");
                        $keyword5 = array('value' => $address, "color" => "#173177");
                        $keyword6 = array('value' => $courier_num, "color" => "#173177");
                        $keyword7 = array('value' => $name, "color" => "#173177");
                        //拼成规定的格式
                        $o_data = array('keyword1' => $keyword1, 'keyword2' => $keyword2, 'keyword3' => $keyword3, 'keyword4' => $keyword4, 'keyword5' => $keyword5, 'keyword6' => $keyword6, 'keyword7' => $keyword7);
                        $this->Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data);
                        $this->get_fromid($openid, $form_id);
                    }


                }
                $r = $db->selectrow("select id from lkt_order_details where r_sNo='" . $sNo . "'");
                $r01 = $db->selectrow("select id from lkt_order_details where r_sNo='" . $sNo . "' and r_status >=2");
                if ($r == $r01) {//全部子订单发货完全改变lkt_order表
                    $sqll = 'update lkt_order set status=2 where sNo="' . $sNo . '"';
                    $db->update($sqll);
                }
                $db->admin_record($admin_id, ' 使订单号为 ' . $sNo . ' 的订单发货 ', 7);
                $db->commit();
                echo 1;
                exit();
            }


        } else if ($otype == 'pt') {
            $sqll = 'update lkt_order set status=2 where sNo="' . $sNo . '"';
            $rl = $db->update($sqll);
            $sqld = 'update lkt_order_details set  r_status=2, ' . substr($con, 1) . ' where r_sNo="' . $sNo . '"';
            $rd = $db->update($sqld);
            $msgsql = "select o.id,o.user_id,o.sNo,d.p_name,o.name,o.address,d.p_id,d.sid,d.num from lkt_order as o left join lkt_order_details as d on o.sNo=d.r_sNo where o.sNo='$sNo'";
            $msgres = $db->select($msgsql);
            if (!empty($msgres))
                $msgres = $msgres[0];
            $p_id = $msgres->p_id;
            $sid = $msgres->sid;
            $num = $msgres->num;
            $db->insert("insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$p_id','$sid','$num',1,CURRENT_TIMESTAMP)");//增加一条出库记录

            $uid = $msgres->user_id;
            $openid = $db->select("select wx_id from lkt_user where user_id='$uid'");
            $msgres->uid = $openid[0]->wx_id;
            $compa = "select kuaidi_name from lkt_express where id=$express_id";
            $compres = $db->select($compa);
            if (!empty($compres))
                $msgres->company = $compres[0]->kuaidi_name;
            $fromidsql = "select fromid from lkt_user_fromid where open_id='$msgres->uid' and id=(select max(id) from lkt_user_fromid where open_id='$msgres->uid')";
            $fromid = $db->select($fromidsql);
            if (!empty($fromid))
                $msgres->fromid = $fromid[0]->fromid;
            $msgres->courier_num = $courier_num;

            if ($rl > 0 && $rd > 0) {
                $sql = "select * from lkt_notice where id = '1'";
                $r = $db->select($sql);
                $template_id = $r[0]->order_delivery;
                $res = $this->Send_success($msgres, $template_id);
                $db->admin_record($admin_id, ' 使订单号为 ' . $sNo . ' 的订单发货 ', 7);
                $db->commit();
                echo 1;
                exit();
            }
        }
        $db->rollback();
        echo 0;
        exit();
    }

    public function Send_success($arr, $template_id)
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if ($r) {
            $appid = $r[0]->appid;
            // 小程序唯一标识
            $appsecret = $r[0]->appsecret;
            // 小程序的 app secret
            $AccessToken = $this->getAccessToken($appid, $appsecret);
            $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' . $AccessToken;

        }

        $data = array();
        $data['access_token'] = $AccessToken;
        $data['touser'] = $arr->uid;
        $data['template_id'] = $template_id;
        $data['form_id'] = $arr->fromid;
        $data['page'] = "pages/order/detail?orderId=$arr->id";
        $minidata = array('keyword1' => array('value' => $arr->company, 'color' => "#173177"), 'keyword2' => array('value' => date('Y-m-d H:i:s', time()), 'color' => "#173177"), 'keyword3' => array('value' => $arr->p_name, 'color' => "#173177"), 'keyword4' => array('value' => $arr->sNo, 'color' => "#FF4500"), 'keyword5' => array('value' => $arr->address, 'color' => "#FF4500"), 'keyword6' => array('value' => $arr->courier_num, 'color' => "#173177"), 'keyword7' => array('value' => $arr->name, 'color' => "#173177"));
        $data['data'] = $minidata;
        $data = json_encode($data);

        $da = $this->httpsRequest($url, $data);
        $delsql = "delete from lkt_user_fromid where open_id='$arr->uid' and fromid='$arr->fromid'";
        $db->delete($delsql);

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
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        //这个是重点。
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
        $result = $this->httpsRequest($url);
        $jsonArray = json_decode($result, true);
        // 写入文件
        $accessToken = $jsonArray['access_token'];
        file_put_contents($fileName, $accessToken);
        return $accessToken;
    }

    public function Send_Prompt($appid, $appsecret, $form_id, $openid, $page, $send_id, $o_data)
    {
        $AccessToken = $this->getAccessToken($appid, $appsecret);
        $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=' . $AccessToken;
        $data = json_encode(array('access_token' => $AccessToken, 'touser' => $openid, 'template_id' => $send_id, 'form_id' => $form_id, 'page' => $page, 'data' => $o_data));
        $da = $this->httpsRequest($url, $data);
        return $da;
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
            $re2 = $db->delete($delsql);
            return $re2;
        }

    }

    public function getRequestMethods()
    {
        return Request::POST;
    }

    public function close($sNo)
    {
        $db = DBAction::getInstance();
        // 根据订单id,查询订单列表(订单号)
        $r = $db->select("select z_price,sNo,status,coupon_id,consumer_money,user_id from lkt_order  where sNo='" . $sNo . "'");
        if ($r) {
            $db->begin();
            $z_price = $r[0]->z_price; // 订单价
            $status = $r[0]->status; // 订单状态
            $coupon_id = $r[0]->coupon_id; // 优惠券id
            $consumer_money = $r[0]->consumer_money; // 消费金
            $user_id = $r[0]->user_id;
            if ($coupon_id != 0) {
                // 根据优惠券id,查询优惠券信息
                $sql = "select * from lkt_coupon where id = '$coupon_id' ";
                $r_c = $db->select($sql);
                $expiry_time = $r_c[0]->expiry_time; // 优惠券到期时间
                $time = date('Y-m-d H:i:s'); // 当前时间
                if ($expiry_time <= $time) {
                    // 根据优惠券id,修改优惠券状态
                    $sql = "update lkt_coupon set type = 2 where id = '$coupon_id'";
                    $db->update($sql);
                } else {
                    // 根据优惠券id,修改优惠券状态
                    $sql = "update lkt_coupon set type = 0 where id = '$coupon_id'";
                    $db->update($sql);
                }
            }
            //修改库存
            $ss = $db->select("select sid,num,p_id from lkt_order_details where r_sNo = $sNo");

            if ($ss) {
                foreach ($ss as $key => $value) {
                    $size_id = $value->sid;
                    $num = $value->num;
                    $pid = $value->p_id;
                    addkuncun($db, $size_id, $pid, $num);//取消订单或者取消支付或者过期未付款修改库存
                }
            }

            $rl = $db->update("update lkt_order set status=7 where sNo='" . $sNo . "'");
            $rd = $db->update("update lkt_order_details set r_status=6 where r_sNo='" . $sNo . "'");
            if ($rl > 0 && $rd > 0) {
                if ($status == 1) {
                    $sql = "update lkt_user set money = money + '$z_price' where user_id = '$user_id'";
                    $db->update($sql);
                }
                $db->commit();
                echo json_encode(array('status' => 1, 'err' => '操作成功!'));

                exit();
            } else {
                $db->rollback();
                echo json_encode(array('status' => 0, 'err' => '操作失败!'));
                exit();
            }

        } else {
            echo json_encode(array('status' => 0, 'err' => '操作失败!'));
            exit();
        }


    }

}

?>