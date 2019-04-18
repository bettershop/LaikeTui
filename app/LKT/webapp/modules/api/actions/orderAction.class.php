<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class orderAction extends Action {

    public function getDefaultView() {

        return ;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'recOrder'){
            $this->recOrder();
        }else if($m == 'ok_Order'){
            $this->ok_Order();
        }else if($m == 'removeOrder'){
            $this->removeOrder();
        }else if($m == 'order_details'){
            $this->order_details();
        }else if($m == 'ReturnData'){
            $this->ReturnData();
        }else if($m == 'ReturnDataList'){
            $this->ReturnDataList();
        }else if($m == 'logistics'){
            $this->logistics();
        }else if($m == 'back_send'){
            $this->back_send();
        }else if($m == 'see_send'){
            $this->see_send();
        }else if($m == 'up_out_trade_no'){
            $this->up_out_trade_no();
        }else if($m == 'return_type'){
            $this->return_type();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

    //处理返回可选退货类型
    public function return_type()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = trim($request->getParameter('id')); //订单id
        $oid = trim($request->getParameter('oid')); // 订单号
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
        $request = $this->getContext()->getRequest();
        // 查询系统参数
        $res = "";
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        //查询当前正在执行的团信息
        // $group = $db -> select("select * from lkt_group_buy where is_show=1");
        // if(!empty($group)) list($groupmsg) = $group;

        // 获取信息
        $openid = $_POST['openid']; // 微信id
        $order_type = $_POST['order_type']; // 类型
        $otype = $_POST['otype']; // 类型
        if($otype == 'pay5'){
            $res = "and a.drawid > 0 "; // 一分钱抽奖
        }else if($otype == 'pay6'){
            // if(!empty($groupmsg)) $res = "and a.otype='pt' and a.pid='$groupmsg->status'"; // 我的拼团
            $res = "and a.otype='pt'"; // 我的拼团
        }else{
            $res = "";
        }
        if(!empty($order_type) && $order_type != $otype){
            if($otype =='pay6'){
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
            }else{
                if($order_type == 'payment'){
                    $status = 0;
                    $res .= "and a.status = '$status'"; // 未付款
                }else if($order_type == 'send'){
                    $status = 1;
                    $res .= "and a.status = '$status' "; // 未发货
                }else if($order_type == 'receipt'){
                    $status = 2;
                    $res .= "and a.status = '$status'"; // 待收货
                }else if($order_type == 'evaluate'){
                     $status = 3;
                    $res .= "and a.status = '$status'"; // 待评论
                }else{
                    $res = "";
                }
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
        $sql = "select id,z_price,sNo,add_time,status,coupon_id,pid,drawid,ptcode from lkt_order as a where user_id = '$user_id' " . $res ." order by add_time desc";
        $r = $db->select($sql);

        if($order_type == 'send'){//未发货
            if(!empty($r)){
                foreach ($r as $key001 => $value001) {
                $drawid = $value001->drawid ;
                    if($drawid > 0){
                       $sql0001 = "select lottery_status,draw_id from lkt_draw_user where id= '$drawid'";
                        $ddd= $db->select($sql0001);
                        $lottery_status = $ddd[0]->lottery_status;
                        if($lottery_status !=4){//抽奖成功
                            unset($r[$key001]);
                        }
                    }
                }
            }
        }
        $plugsql = "select status from lkt_plug_ins where type = 0 and software_id = 3 and name like '%拼团%'";
        $plugopen = $db -> select($plugsql);
        $plugopen = !empty($plugopen)?$plugopen[0] -> status:0;

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
                $rew['role'] = $v->drawid; // 抽奖
                $rew['ptcode'] = $v->ptcode; // 拼团号
                $rew['plugopen'] = $plugopen; // 拼团是否开启（0 未启用 1.启用）
                $coupon_id = $v->coupon_id; // 优惠券id
                if(!empty($rew['role'])){
                    $role=$rew['role'];

                    $add_time=$rew['add_time'];
                    $sql0001 = "select lottery_status,draw_id from lkt_draw_user where id= '$role'";
                    $ddd= $db->select($sql0001);
                    if(!empty($ddd)){
                        $lottery_status = $ddd[0]->lottery_status;
                        $rew['lottery_status'] =$lottery_status;
                        $draw_id = $ddd[0]->draw_id;
                        $rew['drawid'] =$draw_id;
                    }
                    if($rew['status']==0){
                        $rew['lottery_status1'] ='等待买家付款';
                    }elseif($rew['status']==1){
                        if($lottery_status ==0){
                            $rew['lottery_status1'] ='抽奖中-已参团';
                        }elseif ($lottery_status ==1) {
                            $rew['lottery_status1'] ='抽奖中';
                        }elseif ($lottery_status ==2) {
                            $rew['lottery_status1'] ='抽奖失败';
                        }
                        elseif ($lottery_status ==4) {
                            $rew['lottery_status1'] ='抽奖成功-待发货';
                        }else{
                            $rew['lottery_status1'] ='抽奖失败';
                        }
                    }elseif($rew['status']==2){
                        $rew['lottery_status1'] ='抽奖成功-待发货';
                    }elseif($rew['status']==6){
                        if ($lottery_status ==2) {
                            $rew['lottery_status1'] ='参团失败订单关闭';
                        }else{
                            $rew['lottery_status1'] ='抽奖失败订单关闭';
                        }
                    }
                }else{
                    $rew['lottery_status1'] ='';
                    $rew['lottery_status'] ='';
                    $rew['drawid'] =0;
                }

                if($coupon_id == 0){ // 优惠券id为0
                    $rew['total'] = $rew['z_price']; // 总价为订单价格
                }else{
                    // 根据优惠券id,查询优惠券信息
                    $sql = "select * from lkt_coupon where id = '$coupon_id' ";
                    $rr = $db->select($sql);
                    // var_dump($sql);
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
                
                $rew['pname'] = '';
                
                // 根据订单号,查询订单详情
                $sql = "select * from lkt_order_details where r_sNo = '$sNo' ";
                $rew['list'] = $db->select($sql);
                $product = [];
                if($rew['list']){
                    foreach ($rew['list'] as $key => $values) {
                        if(strpos($values -> r_sNo, 'PT') !== false){
                        	$man_num = $db -> select("select man_num from lkt_group_buy where status='$v->pid'");
                            $rew['man_num'] = !empty($man_num)?$man_num[0] -> man_num:0;
                            $rew['pro_id'] = $values->p_id;
                        }
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

                        $sql_o = "select id from lkt_order_details where r_sNo = '$sNo' AND r_type = 0 AND r_status = '$r_status' and r_status != -1 ";
                        $res_o = $db->selectrow($sql_o);

                        $sql_d = "select id from lkt_order_details where r_sNo = '$sNo'";
                        $res_d = $db->selectrow($sql_d);

                        // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                        if($res_o == $res_d){
                            //如果订单数量相等 则修改父订单状态
                            $sql = "update lkt_order set status = '$r_status' where sNo = '$sNo'";
                            $r = $db->update($sql);
                        }
                        if($r_status > 0){
                            $rew['status'] = $r_status;
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
        $id = trim($request->getParameter('id')); // 订单详情id
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
                $rew02 = $db->update($sql02);
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
        $sNo = trim($request->getParameter('sNo')); // 订单号
        $time = date('Y-m-d H:i:s');
        //查询订单是不是抽奖订单，要是抽奖订单确认收货就直接关闭订单
        $sql01 = "select drawid,otype from lkt_order where sNo='$sNo'";
        $rew = $db->select($sql01);
        if($rew){
            if($rew[0]->drawid >0){
                $sql_1 = "update lkt_order_details set r_status = 6, arrive_time = '$time' where r_sNo = '$sNo' ";
                $r_1 = $db->update($sql_1);
                $sql_2 = "update lkt_order set status = 6 where sNo = '$sNo'";
                $r_2 = $db->update($sql_2);
            }else{

                $sql_1 = "update lkt_order_details set r_status = 3, arrive_time = '$time' where r_sNo = '$sNo' and r_status = '2'";
                $r_1 = $db->update($sql_1);


                if($rew[0]->otype == 'pt') $r_1 = 1;
                $sql_2 = "update lkt_order set status = 3 where sNo = '$sNo'";
                $r_2 = $db->update($sql_2);
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
        $id = trim($request->getParameter('id'));// 订单详情id 
        $details = $request->getParameter('details');
        $type = trim($request->getParameter('type'));
        if($type){
            $sql = "select kd_num as express_id,kdid as courier_num from lkt_twelve_draw_user_address where oid = '$id'";
            $r = $db->select($sql);
        }else{
            // 根据订单详情id,修改订单详情
            if($details){
                $sql = "select express_id,courier_num from lkt_order_details where r_sNo = '$id' AND id = '$details'";
                $r = $db->select($sql);
            }else{
                $sql = "select express_id,courier_num from lkt_order_details where r_sNo = '$id'";
                $r = $db->select($sql);
            }
        }
        if($r){
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
                echo json_encode(array('status'=>1,'res_1'=>$res_1,'name'=>$kuaidi_name,'courier_num'=>$courier_num));
                exit();
            }else{
                echo json_encode(array('status'=>0,'err'=>'暂未查到!'));
                exit();
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit();
        }

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

    // 取消订单
    public function removeOrder(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $request->getParameter('openid');// 微信id
        $id = trim($request->getParameter('id'));// 订单id

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
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        // 获取信息
        $id = $_POST['order_id']; // 订单id
        $type1 = $_POST['type1']; // 

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
            $role = $r[0]->drawid;//抽奖id
            $user_id = $r[0]->user_id;//成员id
            $status = $r[0]->status;//订单状态
            $gstatus = $r[0]->status;//订单状态
            $otype = $r[0]->otype;//订单状态
            $ptcode = $r[0]->ptcode;//订单状态
            $pid = $r[0]->pid;//拼团ID
            $red_packet = $r[0]->red_packet;//红包

            // 判断红包使用
            if ($red_packet >0 && $red_packet != 'unll') {
                $red_packet = $red_packet;
            }else{
                $red_packet = 0;
            }

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
            $coupon_activity_name ='';
            $coupon_activity_name = $r[0]->coupon_activity_name; // 满减活动名称
            if($coupon_id){
                $sql = "select money from lkt_coupon where id = '$coupon_id'";
                $r_coupon = $db->select($sql);
                $coupon_money = $r_coupon[0]->money;
            }else{
                $coupon_money = 0;
            }
            if(!empty($role)){//存在抽奖订单
                $sql001 = "select * from lkt_draw_user where id ='$role'  " ;
                $dd = $db->select($sql001); 
                 if(!empty($dd)){
                    $lottery_status = $dd[0]->lottery_status;
                    $drawid = $dd[0]->draw_id;
                }
                if($status==0){
                            $rew['lottery_status1'] ='待付款';
                }elseif($status==1){
                    if($lottery_status ==0){
                        $lottery_status ='查看团详情';
                    }elseif ($lottery_status ==1) {
                        $lottery_status ='待抽奖';
                    }elseif ($lottery_status ==2) {
                        $lottery_status ='参团失败';
                    }
                    elseif ($lottery_status ==4) {
                        $lottery_status ='待发货';
                    }else{
                        $lottery_status ='抽奖失败';
                    }
                }elseif($status==2){
                    $lottery_status ='待收货';
                }elseif($status==6){
                    if ($lottery_status ==2) {
                        $lottery_status ='订单关闭';
                    }else{
                        $lottery_status ='订单关闭';
                    }
                }
                $type1 = 11;
               
            }else{
                $wx_id ='';
                $lottery_status ='';
                $type1 = 22;
                $drawid ='';
            }
            $freight = 0;
            // 根据订单号,查询订单详情
            $sql = "select * from lkt_order_details where r_sNo = '$sNo'" ;
            $list = $db->select($sql);
            if($list){
                foreach ($list as $key => $values) {
                    $freight += $values->freight;
                    // print_r($values->freight);
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
                    $man_num = $db -> select("select man_num from lkt_group_buy where status='$pid'");
                    $man_num = $man_num[0] -> man_num;
                }        
            }

            echo json_encode(array('status'=>1,'id'=>$id,'freight'=>$freight,'sNo'=>$sNo,'z_price'=>$z_price,'name'=>$name,'mobile'=>$mobile,'address'=>$address,'add_time'=>$add_time,'rstatus'=>$status,'list'=>$list,'lottery_status'=>$lottery_status,'type1'=>$type1,'otype'=>$otype,'man_num'=>$man_num,'ptcode' => $ptcode,'dr'=>$dr,'role'=>$role,'title'=>$title,'drawid'=>$drawid,'p_id'=>$p_id,'coupon_id'=>$coupon_id,'coupon_money'=>$coupon_money,'consumer_money'=>$consumer_money,'user_money' =>$user_money,'coupon_activity_name'=>$coupon_activity_name,'pid' =>$pid,'red_packet' =>$red_packet));
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
        $id = $_POST['id']; // 订单详情id
        $oid = $_POST['oid']; // 订单号
        $otype = $_POST['otype']; // 状态
        // $re_type = $_POST['re_type']; // 退货类型
        $re_type = trim($request->getParameter('re_type'));
        $back_remark = htmlentities($_POST['back_remark']); // 退货原因

        $sql = "update lkt_order_details set r_status = 4,content = '$back_remark',r_type = 0,re_type = '$re_type' where id = $id";
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
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        // 获取信息
        $openid = $_POST['openid']; // 微信id
        $order_type = $_POST['order_type']; // 参数
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
        $kdcode = trim($request->getParameter('kdcode'));
        $kdname = trim($request->getParameter('kdname'));
        $lxdh = trim($request->getParameter('lxdh'));
        $lxr = trim($request->getParameter('lxr'));
        $uid = trim($request->getParameter('uid'));
        $oid = trim($request->getParameter('oid'));

        $sql = "insert into lkt_return_goods(name,tel,express,express_num,uid,oid,add_data) values('$lxr','$lxdh','$kdname','$kdcode','$uid','$oid',CURRENT_TIMESTAMP)";
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
        $sql = "select address_xq,name,tel from lkt_user_address where uid = 'admin'";
        $r_1 = $db->select($sql);
        if($r_1){
            $address = $r_1[0]->address_xq;
            $name = $r_1[0]->name;
            $phone = $r_1[0]->tel;
        }else{
            $address = '';
            $name = '';
            $phone = '';
        }

        $sql_1 = "select * from lkt_express ";
        $r_2 = $db->select($sql_1);

        if($r_2){
            echo json_encode(array('status'=>1,'address'=>$address,'name'=>$name,'phone'=>$phone,'express'=>$r_2));
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
        $coupon_id = trim($request->getParameter('coupon_id')); // 优惠券id
        $allow = trim($request->getParameter('allow')); // 用户使用消费金
        $coupon_money = trim($request->getParameter('coupon_money')); // 付款金额
        $order_id = trim($request->getParameter('order_id')); // 订单号
        $user_id = trim($request->getParameter('user_id')); // 微信id
        $d_yuan = trim($request->getParameter('d_yuan')); // 抵扣余额
        $trade_no = trim($request->getParameter('trade_no')); // 微信支付单号
        $pay =  trim($request->getParameter('pay'));
        $array = array('coupon_id' => $coupon_id,'allow' => $allow,'coupon_money' => $coupon_money,'order_id' => $order_id,'user_id' => $user_id,'d_yuan' => $d_yuan,'trade_no' => $trade_no,'pay' => $pay);
        $data = serialize($array);

        $sql_u = "update lkt_order set trade_no='$trade_no' where sNo = '$order_id' ";
        $r_u = $db->update($sql_u);

        $sql = "insert into lkt_order_data(trade_no,data,addtime) values('$trade_no','$data',CURRENT_TIMESTAMP)";
        $rid = $db->insert($sql);

        // $yesterday= date("Y-m-d",strtotime("-1 day"));
        // $sql = "delete from lkt_order_data where addtime < '$yesterday'";
        // $db->delete($sql);

        echo json_encode(array('status'=>$rid));
        exit();
    }

}
?>