<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once('BaseAction.class.php');
require_once(MO_LIB_DIR . '/Timer.class.php');

class orderAction extends BaseAction {

    
    //处理返回可选退货类型
    public function return_type()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id'))); //订单id
        $oid = addslashes(trim($request->getParameter('oid'))); // 订单号
        $sql = "select r_status from lkt_order_details where id = '$id'";
        $r = $db->select($sql);
        if($r){
            $status = $r[0]->r_status;
        }else{
            $status = '';
        }
        //状态 0：未付款 1：未发货 2：待收货 3：待评论 4：退货 5:已完成 6 订单关闭 9拼团中 10 拼团失败-未退款 11 拼团失败-已退款''',
        // itemList: ['退货退款', '仅退款','换货'],
        // itemList_text:'退货退款',
        // tapIndex:1
        $arrayType1 = array('text' => '退货退款','id' => '1');
        $arrayType2 = array('text' => '仅退款','id' => '2');
        $arrayType3 = array('text' => '换货','id' => '3');
        $arrayType = [$arrayType1,$arrayType2,$arrayType3];

        $itemList_text = '退货退款';
        $tapIndex=1;
        if($status == 1){
             $arrayType = [$arrayType2];
             $itemList_text = '仅退款';
             $tapIndex=2;
        }else if($status == 2){
             $arrayType = [$arrayType1,$arrayType2];
             $itemList_text = '退货退款';
             $tapIndex=1;
        }else{

        }
        echo json_encode(array('status'=>1,'arrayType'=>$arrayType,'itemList_text'=>$itemList_text,'tapIndex'=>$tapIndex));
        exit();
    }
    // 查询订单
    public function index(){
        
        $db = DBAction::getInstance();
        // 查询系统参数
        $res = "";
        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        // 获取信息
        $openid = addslashes($_POST['openid']); // 微信id
        $order_type = addslashes($_POST['order_type']); // 订单状态(代付款：payment  代发货：send 待收货：receipt 待评价：evaluate)
        $otype = addslashes($_POST['otype']); // 订单类型(全部订单：pay   拼团订单：pay6)
         if($otype == 'pay6'){
            $res = "and a.otype='pt'"; // 我的拼团
        }else{
            $res = "";
        }
        if(!empty($order_type) && $order_type != $otype){
                //拼团的状态没和其他订单状态共用字段，分开判断
                if($order_type == 'payment'){
                    $res .= "and a.status = 0 "; // 未付款
                }else if($order_type == 'send'){
                    $res .= "and a.status = 1 "; // 未发货
                }else if($order_type == 'receipt'){
                    $res .= "and a.status = 2 "; // 待收货
                }else if($order_type == 'evaluate'){
                    $res .= "and a.status = 3 "; // 待评论
                }else{
                    $res = "";
                }
        }

        // 根据微信id,查询用户id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db -> select($sql);
        if($r){
            $user_id = $r[0]->user_id;
        }else{
            $user_id = '';
        }

        $order = array();
        // 根据用户id和前台参数,查询订单表 (id、订单号、订单价格、添加时间、订单状态、优惠券id)
        $sql = "select id,z_price,sNo,add_time,status,coupon_id,pid,drawid,ptcode,ptstatus from lkt_order as a where user_id = '$user_id' " . $res ." order by add_time desc";
        $r = $db->select($sql);
        $plugsql = "select status from lkt_plug_ins where type = 0 and software_id = 3 and code ='PT'";//查询拼团插件的状态（0：未启用 1：启用）
        $plugopen = $db -> select($plugsql);
        $plugopen = !empty($plugopen)?$plugopen[0] -> status:0;//不存在默认为为未启用

        if($r){
            foreach ($r as $k => $v) {
                $rew = [];
                $rew['id'] = $v->id; // 订单id
                $rew['z_price'] = $v->z_price; // 订单价格
                $rew['sNo'] = $v->sNo; // 订单号
                $sNo = $v->sNo; // 订单号
                $rew['add_time'] = $v->add_time; // 订单时间
                $rew['status'] = $v->status; // 订单状态
                $rew['coupon_id'] = $v->coupon_id; // 优惠券id
                $rew['pid'] = $v->pid; // 拼团ID
                $rew['ptcode'] = $v->ptcode; // 拼团号
                $rew['plugopen'] = $plugopen; // 拼团是否开启（0 未启用 1.启用）
                $coupon_id = $v->coupon_id; // 优惠券id
                if($coupon_id == 0){ // 优惠券id为0
                    $rew['total'] = $rew['z_price']; // 总价为订单价格
                }else{
                    // 根据优惠券id,查询优惠券信息
                    $sql = "select * from lkt_coupon where id = '$coupon_id' ";
                    $rr = $db->select($sql);

                    if($rr){
                        $expiry_time = $rr[0]->expiry_time; // 优惠券到期时间
                        $money = $rr[0]->money; // 优惠券金额
                        $time = date('Y-m-d H:i:s'); // 当前时间
                        if($expiry_time <= $time){ 
                            // 优惠券过期
                            // 根据优惠券id,修改优惠券状态
                            $sql = "update lkt_coupon set type = 3 where id = '$coupon_id'";
                            $db->update($sql);
                            $rew['info'] = 0;
                        }else{ // 优惠券没过期
                            $rew['info'] = 1;
                        }
                        $rew['total'] = $rew['z_price'] + $money; // 总价为 订单价格+优惠券价格
                    }else{
                        $rew['total'] = $rew['z_price']; // 总价为订单价格
                    }
                }

                if($rew['ptcode'] && $v->ptstatus == 1){//存在拼团订单且状态还在拼团中的
                    $ptt =$rew['ptcode'];
                    $rre = $db->select("select * from lkt_group_open where ptcode = '$ptt' ");
                    if($rre){
                        foreach ($rre as $key => $value) {
                            $endtime = $value->endtime;//过期时间
                            $new1 = date("Y-m-d H:i:s");//当前时间
                            $ptstatus = $value->ptstatus;//状态 1.拼团中，2 拼团成功
                            $iddd = $value->id;
                            $ptcode = $value->ptcode;
                            if($ptstatus == 2 &&  $rew['status'] ==9){
                                up_su_status($db,$iddd,$ptcode);//过期修改拼团成功订单
                            }else{
                                if($new1 > $endtime){
                                    up_status($db,$iddd,$ptcode);//过期修改拼团未成功订单
                                }
                            }

                        }
                    }
                }


                
                $rew['pname'] = '';
                
                // 根据订单号,查询订单详情
                $sql = "select * from lkt_order_details where r_sNo = '$sNo' ";
                $rew['list'] = $db->select($sql);

                $product = [];
                if($rew['list']){
                    foreach ($rew['list'] as $key => $values) {

                        if(strpos($values -> r_sNo, 'PT') !== false){//通过订单号查询拼团订单

                        	$rew['man_num'] =select_num($db,$v->pid);//查询该拼团活动拼团人数 
                            $rew['pro_id'] = $values->p_id;
                        }
                        // $rew[$key]->details_id = $values->id;
                        $rew['pname'] .= $values->p_name; // 订单内商品
                        $p_id = $values->p_id; // 产品id
                        $arr = (array)$values;
                        // 根据产品id,查询产品列表 (产品图片)
                        $sql = "select imgurl from lkt_product_list where id = '$p_id'";
                        $rrr = $db->select($sql);
                        if($rrr){
                            $img_res = $rrr['0']->imgurl;
                            $url = $img . $img_res; // 拼图片路径
                            $arr['imgurl'] = $url;
                            $product[$key]=(object)$arr;
                        }
                        $r_status = $values->r_status; // 订单详情状态

                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' AND r_type = 0 AND r_status = '$r_status' ";
                        $res_o = $db->selectrow($sql_o);//查询订单号和状态为审核中且状态为该状态的行数

                        $sql_d = "select id from lkt_order_details where r_sNo = '$sNo'";
                        $res_d = $db->selectrow($sql_d);

                        // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                        if($res_o == $res_d){
                            //如果订单数量相等 则修改父订单状态
                            $sql = "update lkt_order set status = '$r_status' where sNo = '$sNo'";
                            $db->update($sql);
                        }else{
                            $res11 = $db->select("select min(r_status) as r_status from lkt_order_details where r_sNo = '$sNo'  AND r_status <= 6 and r_status >= 0 ");
                            $res22 = $db->select("select min(status) as status from lkt_order where sNo = '$sNo'");
                            if($res11[0]->r_status>$res22[0]->status){
                                $dd=$res11[0]->r_status;
                                 $sql = "update lkt_order set status = '$dd' where sNo = '$sNo'";
                                 $db->update($sql);
                            }
                           
                        }
                        
                    }
                    $rew['list'] = $product;
                }
                $order[] = $rew;  
            }
        }else{
            $order = '';
        }
        
        echo json_encode(array('status'=>1,'order'=>$order));
        exit();
        return;
    }
        // 确认收货
    public function recOrder(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $id = addslashes(trim($request->getParameter('id'))); // 订单详情id
        $time = date('Y-m-d H:i:s');

        $sql01 = "select b.drawid,b.sNo from lkt_order_details as a ,lkt_order as b where a.id = '$id' and b.sNo=a.r_sNo";
        $rew = $db->select($sql01);
        if($rew){
            if($rew[0]->drawid >0){
                $sNo = $rew[0]->sNo ;
                // 根据订单详情id,修改订单详情
                $sql = "update lkt_order_details set r_status = 6,arrive_time = '$time' where id = '$id'";
                $r = $db->update($sql);
                // 根据订单号,修改订单表
                $sql02 = "update lkt_order set status = 6,arrive_time = '$time' where sNo = '$sNo'";
                $db->update($sql02);
            }else{
                // 根据订单详情id,修改订单详情
                $sql = "update lkt_order_details set r_status = 3,arrive_time = '$time' where id = '$id'";
                $r = $db->update($sql);
            }
            if($r>0){
                echo json_encode(array('status'=>1,'err'=>'操作成功!'));
                exit();
            }else{
                echo json_encode(array('status'=>0,'err'=>'操作失败!'));
                exit();
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit();
        }
        return;
    }


     // 确认收货
    public function ok_Order(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $sNo = addslashes(trim($request->getParameter('sNo'))); // 订单号
        $time = date('Y-m-d H:i:s');
        $sql01 = "select * from lkt_order where sNo='$sNo'";
        $rew = $db->select($sql01);
        $pluginName = $rew[0]->plugin; //插件订单
        if($rew){

            $sql_1 = "update lkt_order_details set r_status = 3, arrive_time = '$time' where r_sNo = '$sNo' and r_status = '2'";
            $r_1 = $db->update($sql_1);


            if($rew[0]->otype == 'pt') $r_1 = 1;
            $sql_2 = "update lkt_order set status = 3 where sNo = '$sNo'";
            $r_2 = $db->update($sql_2);


            if($pluginName){ //动态调用插件类的确认收货的重写方法
                $className = $pluginName;//强制要求插件名称与接口类名称一致
                require_once(MO_WEBAPP_DIR."/plugins/".$pluginName."/front/".$className."Action.class.php");
                $className = $className.'Action';
                $plugin = new $className($this->getContext());
                $plugin->okOrder($rew[0]);
            }

            if($r_1 >0 && $r_2 > 0){
                echo json_encode(array('status'=>1,'err'=>'操作成功!'));
                exit();
            }else{
                echo json_encode(array('status'=>0,'err'=>'操作失败!'));
                exit();
            }

        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit();
        }
        return;
    }


     // 查看物流
    public function logistics(){ 

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();
        // 获取信息
        $courier_num=addslashes(trim($request->getParameter('courier_num'))) ;//kuaididanhao
        $express_id=addslashes(trim($request->getParameter('express_id'))) ;//kuaididanhao
        if($express_id && $courier_num){
                $sql01 = "select * from lkt_express where id = '$express_id'";
                $r01 = $db->select($sql01);
                $type = $r01[0]-> type;//快递公司代码
                $kuaidi_name = $r01[0]-> kuaidi_name;
                $res = $this->logistics2($type,$courier_num);
                $res_1 = json_decode($res);
                
                echo json_encode(array('status'=>1,'res_1'=>$res_1,'name'=>$kuaidi_name,'courier_num'=>$courier_num));
                exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit();
        }

    }

    /*
     * 默认使用快递100的免费接口，有时候订单信息会有延迟，所以有需要的童鞋最好使用快递100的收费接口
     */
    public static function logistics2($type,$courier_num){

        $db = DBAction::getInstance();
        $sql = "select * from lkt_order_config where id = 1";
        $r = $db->select($sql);
        $customer = $r[0]->customer;
        $kdkey = $r[0]->kdkey;

        $url = 'http://poll.kuaidi100.com/poll/query.do'; //实时查询
        $key = $kdkey;           //客户授权key
        $customer = $customer; //查询公司编号

        $param = array (
            'com' =>$type.'',             //快递公司编码
            'num' => trim($courier_num),      //快递单号
            'phone' => '',              //手机号
            'from' => '',               //出发地城市
            'to' => '',                 //目的地城市
            'resultv2' => '1'           //开启行政区域解析
        );
        //请求参数
        $post_data = array();
        $post_data["customer"] = $customer;
        $post_data["param"] = json_encode($param);
        $sign = md5($post_data["param"].$key.$post_data["customer"]);
        $post_data["sign"] = strtoupper($sign);
        $params = "";
        foreach ($post_data as $k=>$v) {
            $params .= "$k=".urlencode($v)."&";     //默认UTF-8编码格式
        }
        $post_data = substr($params, 0, -1);
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch,CURLOPT_POSTFIELDS,$post_data);
        curl_setopt($ch, CURLOPT_TIMEOUT,3);
        $result = curl_exec($ch);
        return $result;   
 
       
    }

    // 取消订单
    public function removeOrder(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = addslashes($request->getParameter('openid'));// 微信id
        $id = addslashes(trim($request->getParameter('id')));// 订单id

        $sql = "select user_id from lkt_user where wx_id = '$openid'";
        $rr = $db->select($sql);
        $user_id = $rr[0]->user_id; // 用户id

        // 根据订单id,查询订单列表(订单号)
        $sql = "select z_price,sNo,status,coupon_id,consumer_money from lkt_order where id = '$id' and user_id = '$user_id' ";
        $r = $db->select($sql);
        if($r){
            $z_price = $r[0]->z_price; // 订单价
            $sNo = $r[0]->sNo; // 订单号
            $status = $r[0]->status; // 订单状态
            $coupon_id = $r[0]->coupon_id; // 优惠券id
            $consumer_money = $r[0]->consumer_money; // 消费金
            if($coupon_id != 0){
                // 根据优惠券id,查询优惠券信息
                $sql = "select * from lkt_coupon where id = '$coupon_id' ";
                $r_c = $db->select($sql);
                $expiry_time = $r_c[0]->expiry_time; // 优惠券到期时间
                $time = date('Y-m-d H:i:s'); // 当前时间
                if($expiry_time <= $time){
                    // 根据优惠券id,修改优惠券状态
                    $sql = "update lkt_coupon set type = 2 where id = '$coupon_id'";
                    $db->update($sql);
                }else{
                    // 根据优惠券id,修改优惠券状态
                    $sql = "update lkt_coupon set type = 0 where id = '$coupon_id'";
                    $db->update($sql);
                }
            }
            if($consumer_money != 0){
                $sql = "update lkt_user set consumer_money = consumer_money + '$consumer_money' where user_id = '$user_id'";
                $db->update($sql);
                $event = $user_id.'退回'.$consumer_money.'消费金';
                $sqlldr = "insert into lkt_distribution_record (user_id,from_id,money,sNo,level,event,type,add_date) values ('$user_id','$user_id','$consumer_money',$sNo,'0','$event','5',CURRENT_TIMESTAMP)";
                $beres1 = $db->insert($sqlldr);
            }
            //修改库存

            $ss = $db->select("select sid,num,p_id from lkt_order_details where r_sNo = $sNo");
            if($ss){
                foreach ($ss as $key => $value) {
                  $size_id=$value->sid;
                  $num=$value->num;
                  $pid=$value->p_id;
                   addkuncun($db,$size_id,$pid,$num);//取消订单或者取消支付或者过期未付款修改库存

                }
            }


            // 根据订单号,删除订单表信息
            $sql = "delete from lkt_order where sNo = '$sNo'";
            $r_2 = $db->delete($sql);
            // 根据订单号,删除订单详情信息
            $sql = "delete from lkt_order_details where r_sNo = '$sNo'";
            $r_1 = $db->delete($sql);
            if($r_1>0 && $r_2>0){
                if($status == 1){
                    $sql = "update lkt_user set money = money + '$z_price' where user_id = '$user_id'";
                    $db->update($sql);
                }
                echo json_encode(array('status'=>1,'err'=>'操作成功!'));
                exit();
            }else{
                echo json_encode(array('status'=>0,'err'=>'操作失败!'));
                exit();
            }




        }else{
            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit();
        }
        return;
    }
    // 订单详情
    public function order_details(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

         $sql = "select * from lkt_order_config where id = 1";
            $rr = $db->select($sql);
            if($rr){
                $order_failure =$rr[0]->order_failure;
            }else{
                $order_failure = '24';
            }
        // 获取信息
        $id = addslashes($_POST['order_id']); // 订单id
        $type1 = addslashes($_POST['type1']); // 

        $sql = "select sNo,z_price,add_time,name,mobile,address,drawid,user_id,status,coupon_id,consumer_money,coupon_activity_name,pid,otype,ptcode,red_packet from lkt_order where id = '$id'";

        $r = $db->select($sql);

        if($r){
            $sNo = $r[0]->sNo; // 订单号
            $z_price = $r[0]->z_price; // 总价
            $add_time = $r[0]->add_time; // 订单时间
            $name = $r[0]->name;//联系人
            $num = $r[0]->mobile;//联系手机号
            $mobile = substr_replace($num,'****',3,4);//隐藏操作
            $address = $r[0]->address;//联系地址
            $user_id = $r[0]->user_id;//成员id
            $status = $r[0]->status;//订单状态
            $gstatus = $r[0]->status;//订单状态
            $otype = $r[0]->otype;//订单状态
            $ptcode = $r[0]->ptcode;//订单状态
            $pid = $r[0]->pid;//拼团ID

            if($status){
                $user_money = false;
            }else{
               $o_user_money = "select money from lkt_user where user_id ='$user_id' " ;
               $o_user_money_res= $db->select($o_user_money);
               if($o_user_money_res){
                    $user_money = $o_user_money_res[0]->money;
               }else{
                    $user_money = false;
               }
               
            }

            $coupon_id = $r[0]->coupon_id;//优惠券id
            $consumer_money = $r[0]->consumer_money;//积分
            if($coupon_id){
                $sql = "select money from lkt_coupon where id = '$coupon_id'";
                $r_coupon = $db->select($sql);
                $coupon_money = $r_coupon[0]->money;
            }else{
                $coupon_money = 0;
            }
            
            $freight = 0;
            $p_id = "";
            $dr = "";
            $title ="";
            // 根据订单号,查询订单详情
            $sql = "select * from lkt_order_details where r_sNo = '$sNo'" ;
            $list = $db->select($sql);
            if($list){
                foreach ($list as $key => $values) {
                    $freight += $values->freight;
                    $p_id = $values->p_id; // 产品id
                    $sid = $values->sid;//属性id
                    $arrive_time = $values->arrive_time;
                    $date = date('Y-m-d 00:00:00', strtotime('-7 days'));

                    if($arrive_time != ''){
                        if($arrive_time < $date){
                            $values->info = 1;
                        }else{
                            $values->info = 0;
                        }
                    }else{
                        $values->info = 0;
                    }
                    $arr = (array)$values;
                    // 根据产品id,查询产品列表 (产品图片)
                    $sql = "select img,product_title from lkt_product_list AS a LEFT JOIN lkt_configure AS c ON a.id = c.pid where a.id = $p_id AND c.id= '$sid' ";
                    $rrr = $db->select($sql);
                    $url = $img.$rrr[0]->img; // 拼图片路径
                    $title = $rrr[0]->product_title;
                    $arr['imgurl'] = $url;
                    $arr['sid'] = $sid;
                    $product[$key]=(object)$arr;
                    $product[$key]->otype = $otype ;
                    $r_status = $values->r_status; // 订单详情状态
                    if($r_status){
                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' AND r_type = 0 AND r_status = 4 ";
                        $res_o = $db->selectrow($sql_o);
                        $sql_d = "select id from lkt_order_details where r_sNo = '$sNo'";
                        $res_d = $db->selectrow($sql_d);
                        // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                        if($res_o == $res_d){
                            //如果订单数量相等 则修改父订单状态 
                            $sql = "update lkt_order set status = '$r_status' where sNo = '$sNo'";
                            $r = $db->update($sql);
                        }
                        $status = $r_status;
                        $status1 = $r_status;
                    }else{
                        $status = $r_status;
                        $status1 = $r_status;
                    }
                    
                    if($r){
                        if($r[0]->otype == 'pt'){
                            $product[$key] -> r_status = $gstatus;
                        }     
                    }

                    $dr =$status1;
                }
                $list = $product;
                
            }
            $man_num = '';
            if($r){
                if($r[0]->otype == 'pt') {
                    $man_num = $db -> select("select group_level from lkt_group_product where group_id='$pid' and attr_id=$sid");
                    $group_level = unserialize($man_num[0] -> group_level);
                    foreach ($group_level as $k_ => $v_){
                        $man_num = $k_;   //几人团 
                    }

                }        
            }

            echo json_encode(array('status'=>1,'id'=>$id,'freight'=>$freight,'sNo'=>$sNo,'z_price'=>$z_price,'name'=>$name,'mobile'=>$mobile,'address'=>$address,'add_time'=>$add_time,'rstatus'=>$status,'list'=>$list,'man_num'=>$man_num,'ptcode' => $ptcode,'dr'=>$dr,'title'=>$title,'p_id'=>$p_id,'coupon_id'=>$coupon_id,'coupon_money'=>$coupon_money,'consumer_money'=>$consumer_money,'user_money' =>$user_money,'pid' =>$pid,'order_failure'=> $order_failure));
            exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'系统繁忙！'));
            exit();
        }
        return;
    }

    // 退货申请
    public function ReturnData(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $id = addslashes($_POST['id']); // 订单详情id
        $oid = addslashes($_POST['oid']); // 订单号
        $otype = addslashes($_POST['otype']); // 状态
        $re_type = addslashes(trim($request->getParameter('re_type')));
        $back_remark = htmlentities($_POST['back_remark']); // 退货原因
        $res = $db->select("select * from lkt_order_details where id = '$id'");//查询售后之前的状态
        if($res){
            foreach ($res as $key => $value) {
                $data = serialize($value);
                $r_status= $value->r_status;
                $id= $value->id;
                $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$id','0','$r_status','$data',24)";//type 24 表示售后储存修改前的订单信息 
                $rr = $db->insert($sqll);
            }
        }


        $sql = "update lkt_order_details set r_status = 4,content = '$back_remark',r_type = 0,re_type = '$re_type' where id = '$id' ";
        $r = $db->update($sql);

        $sql_o = "select id from lkt_order_details where r_sNo = '$oid' AND r_type = 0 AND r_status = 4 ";
        $res_o = $db->selectrow($sql_o);

        $sql_d = "select id from lkt_order_details where r_sNo = '$oid'";
        $res_d = $db->selectrow($sql_d);

        if($res_o == $res_d){

            //如果订单数量相等 则修改父订单状态 
            $sql = "update lkt_order set status = 4 where sNo = '$oid'";
            $r = $db->update($sql);
        }
        if($r>0){
            echo json_encode(array('status'=>1,'succ'=>'申请成功！'));
            exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'系统繁忙！'));
            exit();
        }
    }
    
    // 退货申请
    public function ReturnDataList(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询系统参数
        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        // 获取信息
        $openid = addslashes($_POST['openid']); // 微信id
        $order_type = addslashes($_POST['order_type']); // 参数
        $sql = "select user_id from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if($r){
            $user_id = $r[0]->user_id;
            if($order_type == 'whole'){

                $sql = "select * from lkt_order_details where user_id = '$user_id' and r_status = 4";
                $list = $db->select($sql);
                if($list){
                    foreach ($list as $k => $v) {
                        $p_id = $v->p_id;
                        $arr = (array)$v;
                        // 根据产品id,查询产品列表 (产品图片)
                        $sql = "select imgurl from lkt_product_list where id = '$p_id'";
                        $rrr = $db->select($sql);
                        if($rrr){
                            $url = $img.$rrr[0]->imgurl; // 拼图片路径
                            $arr['imgurl'] = $url;
                            $product[$k]=(object)$arr;
                        }
                    }
                    $list = $product;
                    echo json_encode(array('status'=>1,'list'=>$list));
                    exit();
                }else{
                    echo json_encode(array('status'=>0,'list'=>''));
                    exit();
                }
            }else if($order_type == 'stay'){

                $sql = "select * from lkt_order_details where user_id = '$user_id' and r_status = 4 and r_type = 0";
                $list = $db->select($sql);
                if($list){
                    foreach ($list as $k => $v) {
                        $p_id = $v->p_id;
                        $arr = (array)$v;
                        // 根据产品id,查询产品列表 (产品图片)
                        $sql = "select imgurl from lkt_product_list where id = '$p_id'";
                        $rrr = $db->select($sql);
                        if($rrr){
                            $url = $img.$rrr[0]->imgurl; // 拼图片路径
                            $arr['imgurl'] = $url;
                            $product[$k]=(object)$arr;
                        }

                    }
                    $list = $product;
                    echo json_encode(array('status'=>1,'list'=>$list));
                    exit();
                }else{
                    echo json_encode(array('status'=>0,'list'=>''));
                    exit();
                }
            }
        }else{

            echo json_encode(array('status'=>0,'list'=>''));
            exit();
        }
        
        return;
    }
    //储存快递回寄信息
    public function back_send(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息 
        $kdcode = addslashes(trim($request->getParameter('kdcode')));
        $kdname = addslashes(trim($request->getParameter('kdname')));
        $lxdh = addslashes(trim($request->getParameter('lxdh')));
        $lxr = addslashes(trim($request->getParameter('lxr')));
        $address = addslashes(trim($request->getParameter('address')));
        $uid = addslashes(trim($request->getParameter('uid')));
        $oid = addslashes(trim($request->getParameter('oid')));

        $sql = "insert into lkt_return_goods(name,tel,express,express_num,uid,oid,add_data,address) values('$lxr','$lxdh','$kdname','$kdcode','$uid','$oid',CURRENT_TIMESTAMP,'$address')";
        $rid = $db->insert($sql,'last_insert_id');

        $sql = "update lkt_order_details set r_type = 3 where id = '$oid'";
        $r = $db->update($sql);

        if($r){
            echo json_encode(array('status'=>1,'err'=>'操作成功!'));
            exit();
        }else{

            $sql = "delete from lkt_return_goods where id = '$rid'";
            $db->delete($sql);

            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit();
        }
    }
    //返回快递信息
    public function see_send(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息 
        $order_id = addslashes(trim($request->getParameter('order_id'))); // 订单详情id

        $sql = "select address,name,tel from lkt_user_address where uid = 'admin'";
        $r_1 = $db->select($sql);
        
        if($r_1){
            $address = $r_1[0]->address;
            $name = $r_1[0]->name;
            $phone = $r_1[0]->tel;
        }else{
            $address = '';
            $name = '';
            $phone = '';
        }
        $sql01 = "select name,mobile,address from lkt_order where id = '$order_id'";
        $r01 = $db->select($sql01);

        if($r01){
            $data['address'] = $r01[0]->address;
            $data['name'] = $r01[0]->name;
            $data['phone'] = $r01[0]->mobile;
        }else{
            $data['address'] = '';
            $data['name'] = '';
            $data['phone'] = '';
        }

        $sql_1 = "select * from lkt_express ";
        $r_2 = $db->select($sql_1);

        if($r_2){
            echo json_encode(array('status'=>1,'address'=>$address,'name'=>$name,'phone'=>$phone,'express'=>$r_2,'data'=>$data));
            exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit();
        }
    }
    //临时存放微信付款信息
    public function up_out_trade_no(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $coupon_id = addslashes(trim($request->getParameter('coupon_id'))); // 优惠券id
        $allow = addslashes(trim($request->getParameter('allow'))); // 用户使用消费金
        $coupon_money = addslashes(trim($request->getParameter('coupon_money'))); // 付款金额
        $order_id = addslashes(trim($request->getParameter('order_id'))); // 订单号
        $user_id = addslashes(trim($request->getParameter('user_id'))); // 微信id
        $d_yuan = addslashes(trim($request->getParameter('d_yuan'))); // 抵扣余额
        $trade_no = addslashes(trim($request->getParameter('trade_no'))); // 微信支付单号
        $pay =  addslashes(trim($request->getParameter('pay')));
        $array = array('coupon_id' => $coupon_id,'allow' => $allow,'coupon_money' => $coupon_money,'order_id' => $order_id,'user_id' => $user_id,'d_yuan' => $d_yuan,'trade_no' => $trade_no,'pay' => $pay);
        $data = serialize($array);

        $sql_u = "update lkt_order set trade_no='$trade_no' where sNo = '$order_id' ";
        $db->update($sql_u);

        $sql = "insert into lkt_order_data(trade_no,data,addtime) values('$trade_no','$data',CURRENT_TIMESTAMP)";
        $rid = $db->insert($sql);

        echo json_encode(array('status'=>$rid));
        exit();
    }

}
?>