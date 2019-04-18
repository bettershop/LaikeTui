<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class userAction extends Action {

    public function getDefaultView() {

        return ;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'material'){
            $this->material();
        }else if($m == 'details'){
            $this->details();
        }else if($m == 'secret_key'){
            $this->secret_key();
        }else if($m == 'withdrawals'){
            $this->withdrawals();
        }else if($m == 'share'){
            $this->share();
        }else if($m == 'recharge'){
            $this->recharge();
        }else if($m == 'withdrawals_list'){
            $this->withdrawals_list();
        }else if($m == 'AddressManagement'){
            $this->AddressManagement();
        }else if($m == 'getCityArr'){
            $this->getCityArr();
        }else if($m == 'getCountyInfo'){
            $this->getCountyInfo();
        }else if($m == 'Preservation'){
            $this->Preservation();
        }else if($m == 'SaveAddress'){
            $this->SaveAddress();
        }else if($m == 'verify_bank'){
            $this->verify_bank();
        }else if($m == 'selectuser'){//查询好友信息
            $this->selectuser();
        }else if($m == 'transfer'){//转账
            $this->transfer();
        }else if($m == 'perfect'){//更新电话号码
            $this->perfect();
        }else if($m == 'perfect_index'){//更新电话号码
            $this->perfect_index();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 请求我的数据
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $_POST['openid']; // 微信id
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $company = $r_1['0'] ->company;
            $logo = $r_1['0'] ->logo;
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        }else{
            $company = '';
            $logo = '';
            $uploadImg_domain = '';
            $uploadImg = '';
        }
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        $logo = $img.$logo;
        
        // 获取文章信息
        $sql_2 = "select Article_id,Article_prompt,Article_title from lkt_article";
        $r_2 = $db->select($sql_2);
   
        // 查询会员信息
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db -> select($sql);
        if($r){
            $user['headimgurl'] = $r[0]->headimgurl;
            $user['wx_name'] = $r[0]->wx_name;
            $user['user_id'] = $r[0]->user_id;
            $wx_name = $r[0]->user_id;
        }else{
            $user['headimgurl'] = '';
            $user['wx_name'] = '';
            $user['user_id'] = '';
            $wx_name = '';
        }

        // 查询会员信息
        $sqlu = "select u.user_name from lkt_user_distribution as d LEFT JOIN lkt_user as u  ON d.pid = u.user_id where d.user_id = '$wx_name' ";
        $ru = $db -> select($sqlu);
        if($ru){
            $tjr = '经纪人:'.$ru[0]->user_name;
        }else{
            $tjr = false;
        }
        
        //个人中心小红点
        $num_arr =[0,1,2,3,4];
        $res_order= [];
        foreach ($num_arr as $key => $value) {
            if($value == '4'){
                $sql_order = "select num from lkt_order_details where r_status = '$value' and  user_id = '$wx_name'" ;
                $order_num = $db -> selectrow($sql_order);
                $res_order[$key] =  $order_num;
            }else{
                if($value==1){
                    $sql_order01 = "select drawid from lkt_order where status = '$value' and  user_id = '$wx_name'" ;
                    $re = $db->select($sql_order01);
                    if(!empty($re)){//未发货
                        foreach ($re as $key001 => $value001) {
                            $drawid = $value001->drawid ;
                            if($drawid > 0){
                                $sql0001 = "select lottery_status,draw_id from lkt_draw_user where id= '$drawid'";
                                $ddd= $db->select($sql0001);
                                if(!empty($ddd)){
                                    $lottery_status = $ddd[0]->lottery_status;
                                    if($lottery_status !=4){
                                        //抽奖成功
                                        unset($re[$key001]);
                                    }
                                }
                            }
                        }
                    }
                    $res_order[$key] =  sizeof($re);
                }else{
                    $sql_order = "select num from lkt_order where status = '$value' and  user_id = '$wx_name'" ;
                    $order_num = $db -> selectrow($sql_order);
                    $res_order[$key] =  $order_num; 
                }
            }
        }
        //控制红包显示
        $sqlfhb = "select user_id from lkt_red_packet_users where user_id = '$wx_name'";
        $rfhb = $db->select($sqlfhb);
        // 查询插件表里,状态为启用的插件
        $sql = "select id,subtitle_name,subtitle_image,subtitle_url from lkt_plug_ins where status = 1 and type = 0 and software_id = 3 order by sort";
        $r_c = $db->select($sql);
        if($r_c){
            foreach ($r_c as $k => $v) {
                $v->subtitle_image = $img . $v->subtitle_image;
                if(strpos($v->subtitle_name,'红包') !== false){ 
                    if(!$rfhb){
                        unset($r_c[$k]);
                    }
                }
            }
        }
        $support = '来客电商提供技术支持';
        // 状态 0：未付款 1：未发货 2：待收货 3：待评论 4：退货 5:已完成 6 订单关闭 9拼团中 10 拼团失败-未退款 11 拼团失败-已退款
        // 抽奖状态（0.参团中 1.待抽奖 2.参团失败 3.抽奖失败 4.抽奖成功）
        echo json_encode(array('status'=>1,'support'=>$support,'tjr'=>$tjr,'user'=>$user,'th'=>$res_order['4'],'dfk_num'=>$res_order['0'],'dfh_num'=>$res_order['1'],'dsh_num'=>$res_order['2'],'dpj_num'=>$res_order['3'],'company'=>$company,'logo'=>$logo,'article'=>$r_2,'plug_ins'=>$r_c));
        exit();
        return;
    }
    // 同步资料
    public function material(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $db->query("set names 'utf8'");
        $openid = $_POST['openid']; // 微信id
        $nickName = $_POST['nickName']; // 微信昵称
        $avatarUrl = $_POST['avatarUrl']; // 微信头像
        $gender = $_POST['gender']; // 性别
        // 根据微信id,修改用户昵称、微信昵称、微信头像、性别
        $sql = "update lkt_user set user_name='$nickName',wx_name='$nickName',sex='$gender',headimgurl='$avatarUrl' where wx_id = '$openid'";
        $r = $db->update($sql);
        echo json_encode(array('status'=>1,'info'=>'资料已更新'));
        exit();
        return;
    }
    
    // 验证用户密码
    public function verify_paw(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $openid = $request->getParameter('openid');
        $ypwd = $request->getParameter('password');
        $and = '';
        if($ypwd){
            $ypwd = md5($ypwd);
            $and = "AND password = '$ypwd' ";
        }
        // 验证密码是否存在 或是否设置
        $sql = "select password from lkt_user where wx_id = '$openid' $and";
        $r = $db -> select($sql);
        if($r){
            $pasw = $r[0]->password; // password
            if(!empty($pasw)){
                echo json_encode(array('status'=>1,'succ'=>'OK'));
                exit();
            }else{
                echo json_encode(array('status'=>0,'err'=>'NO'));
                exit();
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'NO1'));
            exit();
        }
    }

    // 请求我的详细数据
    public function details(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $openid = $_POST['openid']; // 微信id
        // 查询单位
        $sql = "select * from lkt_finance_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $user['min_amount'] = $r_1[0]->min_amount; // 最小提现金额
            $user['max_amount'] = $r_1[0]->max_amount; // 最大提现金额
            $user['unit'] = $r_1[0]->unit; // 单位
            $user['multiple'] = $r_1[0]->multiple; // 提现倍数
        }else{
            $user['min_amount'] = 0; // 最小提现金额
            $user['max_amount'] = 0; // 最大提现金额
            $user['unit'] = 0; // 单位
            $user['multiple'] = 0; // 提现倍数
        }

        // 查询会员信息
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r_2 = $db -> select($sql);
        if($r_2){
            $user_id = $r_2[0]->user_id; // 用户id
            $user_name = $r_2[0]->user_name; // 用户昵称
            $user['money'] = $r_2[0]->money; // 用户余额
            if($user['money'] == ''){
                $user['money'] = 0;
            }
            $sql = "select * from lkt_user_bank_card where user_id = '$user_id' and is_default = 1";
            $r_3 = $db->select($sql);
            if($r_3){
                $user['Bank_name'] = $r_2[0]->Bank_name; // 银行名称
                $user['Cardholder'] = $r_2[0]->Cardholder; // 持卡人
                $user['Bank_card_number'] = $r_2[0]->Bank_card_number; // 银行卡号
            }else{
                $user['Bank_name'] = ''; // 银行名称
                $user['Cardholder'] = ''; // 持卡人
                $user['Bank_card_number'] = ''; // 银行卡号
            }
        }

        // 根据推荐人等于会员编号,查询推荐人总数
        $sql = "select count(Referee) as a from lkt_user where Referee = '$user_id'";
        $r_3 = $db -> select($sql);
        $user['invitation_num'] = $r_3[0]->a;
        // 根据微信id,查询分享列表里的礼券总和
        $sql = "select sum(coupon) as a from lkt_share where wx_id = '$openid'";
        $r_4 = $db->select($sql);
        if($r_4[0]->a == ''){
            $user['coupon'] = 0;
        }else{
            $user['coupon'] = $r_4[0]->a;
        }
        // 根据用户id、类型为充值,查询操作列表-----消费记录
        $sql = "select money,add_date,type from lkt_record where user_id = '$user_id' order by add_date desc";
        $r_5 = $db->select($sql);
        $list_1 = [];
        if($r_5){
            foreach ($r_5 as $k => $v) {
                if($v->type == 1 ||$v->type == 4 ||$v->type == 5 || $v->type == 6 ||$v->type == 12||$v->type == 13||$v->type == 14){
                    $v->time = substr($v->add_date,0,strrpos($v->add_date,':'));
                    $list_1[$k]=$v;
                }
            }
        }
        $sql = "select money,add_date from lkt_record where user_id = '$user_id' and type = 21 order by add_date desc";
        $r_6 = $db->select($sql);
        if($r_6){
            foreach ($r_6 as $k => $v) {
               $v->time = substr($v->add_date,0,strrpos($v->add_date,':'));
            }
            $list_2 = $r_6;
        }else{
            $list_2 = '';
        }
        echo json_encode(array('status'=>1,'user'=>$user,'list_1'=>$list_1,'list_2'=>$list_2));
        exit();
    
        return;
    }
    // 获取用户手机号
    public function secret_key(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $encryptedData = $request->getParameter('encryptedData'); // 加密数据
        $iv = $request->getParameter('iv'); // 加密算法
        $sessionKey = $request->getParameter('sessionId'); // 会话密钥
        if($encryptedData == '' || $iv == ''){
            echo json_encode(array('status'=>0,'info'=>'手机号码没获取!'));
            exit();
        }else{
            // 查询小程序配置
            $sql = "select * from lkt_config where id=1";
            $r = $db->select($sql);
            if($r){
                $appid = $r[0]->appid; // 小程序唯一标识
            }else{
                $appid = '';
            }

            include_once "wxBizDataCrypt.php";
            $data = '';
            $pc = new WXBizDataCrypt($appid, $sessionKey);
            $errCode = $pc->decryptData($encryptedData, $iv, $data );
            if ($errCode == 0) {
                $arr = json_decode($data,true);
                $mobile = $arr['phoneNumber'];
                echo json_encode(array('status'=>1,'info'=>$mobile));
                exit();
            } else {
                echo json_encode(array('status'=>0,'info'=>'系统繁忙!'));
                exit();
            }
        }
    }

    // 提现申请
    public function withdrawals(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $money = $_POST['money']; // 金额
        $min_amount = $_POST['min_amount']; // 最少提现金额
        $max_amount = $_POST['max_amount']; // 最大提现金额
        $amoney = $_POST['amoney']; // 提现金额
        $Bank_name = $_POST['Bank_name']; // 银行名称
        $Cardholder = $_POST['Cardholder']; // 持卡人
        $Bank_card_number = $_POST['Bank_card_number']; // 银行卡号
        $openid = $_POST['openid']; // 微信id
        $mobile = $_POST['mobile']; // 联系电话
        // 提现金额不为数字
        if(is_numeric($amoney) == false){
            echo json_encode(array('status'=>0,'info'=>'请输入数字!'));
            exit();
        }
        // 根据微信id,查询会员金额
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db -> select($sql);
        if($r){
            $money = $r[0]->money; // 会员金额

            // 提现金额是否小于等于0,或者大于现有金额
            if($amoney > $money || $amoney <= 0){
                echo json_encode(array('status'=>0,'info'=>'输入金额不正确!'));
                exit();
            }
            // 提现金额小于最小提现金额
            if($amoney < $min_amount){
                echo json_encode(array('status'=>0,'info'=>'提现金额过少!'));
                exit();
            }
            // 提现金额大于最大提现金额
            if($amoney > $max_amount){
                echo json_encode(array('status'=>0,'info'=>'提现金额过多!'));
                exit();
            }
            // 银行卡号不为数字
            if(is_numeric($Bank_card_number) == false){
                echo json_encode(array('status'=>0,'info'=>'请输入卡号!'));
                exit();
            }
            // 根据卡号,查询银行名称
            require_once('bankList.php');
            $r = $this->bankInfo($Bank_card_number,$bankList);
            if($r == ''){
                echo json_encode(array('status'=>0,'info'=>'卡号不正确!'));
                exit();
            }else{
                $name = strstr($r,'银行',true) . "银行";
                if($name != $Bank_name){
                    echo json_encode(array('status'=>0,'info'=>'银行信息不匹配!'));
                    exit();
                }
            }
            // 查询提现参数表(手续费)
            $sql = "select * from lkt_finance_config where id = 1";
            $r = $db->select($sql);
            $multiple = $r[0]->multiple;
            $tax = $r[0]->service_charge; // 设置的手续费参数
            $jine = $amoney; // 提现金额
            //开启整数倍提现
            if($multiple){
                if($amoney%$multiple == 0){

                }else{
                    echo json_encode(array('status'=>0,'info'=>'提现金额需要是'.$multiple.'的倍数'));
                    exit();
                }
            }

            $cost = $amoney * $tax;  // 实际的手续费
            $amoney = $amoney - $cost; // 实际提现金额
            // 根据wx_id查询会员id
            $sql = "select money,user_name,user_id from lkt_user where wx_id = '$openid'";
            $r = $db->select($sql);
            $user_name = $r[0]->user_name; // 用户名
            $user_id =  $r[0]->user_id; // user_id
            // 根据用户id和未核审,查询数据
            $sql = "select count(id) as a from lkt_withdraw where status = 0 and user_id = '$user_id'";
            $rnum = $db->select($sql);
            $count = $rnum[0]->a; // 条数
            if($count > 0){
                echo json_encode(array('status'=>0,'info'=>'已有正在审核的申请'));
                exit();
            }else{
                // 根据银行名称、卡号，查询用户银行卡信息
                $sql = "select id,Cardholder from lkt_user_bank_card where Bank_name = '$Bank_name' and Bank_card_number = '$Bank_card_number' and user_id = '$user_id'";
                $r1 = $db->select($sql);
                if($r1){
                    $bank_id = $r1[0]->id;
                    if($Cardholder != $r1[0]->Cardholder){
                        echo json_encode(array('status'=>0,'info'=>'持卡人信息错误'));
                        exit();
                    }
                }else{
                    $sql = "insert into lkt_user_bank_card(user_id,Cardholder,Bank_name,Bank_card_number,mobile,add_date,is_default) values ('$user_id','$Cardholder','$Bank_name','$Bank_card_number','$mobile',CURRENT_TIMESTAMP,1)";
                    $bank_id = $db->insert($sql,'affectedrows');
                }
                $sql = "update lkt_user set money = money - '$jine' where wx_id = '$openid'";
                $res = $db->update($sql);
                // 在提现列表里添加一条数据
                $sql = "insert into lkt_withdraw (name,user_id,wx_id,mobile,bank_id,money,s_charge,status,add_date) values ('$user_name','$user_id','$openid','$mobile','$bank_id','$amoney','$cost',0,CURRENT_TIMESTAMP)";
                $res = $db->insert($sql);
                if($res == 1){
                    $event = $user_id.'申请提现'.$jine.'元余额';
                    $user_money = $r[0]->money;
                    $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$jine','$user_money','$event',2)";
                    $db->insert($sqll);

                    echo json_encode(array('status'=>1,'info'=>'申请成功!'));
                    exit();
                }else{
                    echo json_encode(array('status'=>0,'info'=>'申请失败!'));
                    exit();
                }
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit();
        }

        return;
    }
    public function verify_bank()
    {
        $request = $this->getContext()->getRequest();
        $Bank_card_number = $request->getParameter('Bank_card_number');
        // 根据卡号,查询银行名称
        require_once('bankList.php'); 
        $r = $this->bankInfo($Bank_card_number,$bankList);
        if($r == ''){
            echo json_encode(array('status'=>0,'err'=>'卡号不正确!'));
            exit();
        }else{
            $name = strstr($r,'银行',true) . "银行";
            echo json_encode(array('status'=>1,'bank_name'=>$name));
            exit();
        }
    }
    // 验证卡号是否跟银行匹配
    function bankInfo($card,$bankList) { 
      $card_8 = substr($card, 0, 8); 
      if (isset($bankList[$card_8])) { 
        return $bankList[$card_8]; 
      } 
      $card_6 = substr($card, 0, 6); 
      if (isset($bankList[$card_6])) { 
        return $bankList[$card_6]; 
      } 
      $card_5 = substr($card, 0, 5); 
      if (isset($bankList[$card_5])) { 
        return $bankList[$card_5]; 
      } 
      $card_4 = substr($card, 0, 4); 
      if (isset($bankList[$card_4])) { 
        return $bankList[$card_4]; 
      } 
      return ''; 
    } 
    // 打开红包
    public function share(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $n = $_POST['n']; // 参数
        $id = $_POST['id']; // 新闻id
        $openid = $_POST['openid']; // 微信id
        
        if($n == 0){
            // 根据新闻id,查询新闻信息
            $sql = "select * from lkt_news_list where id = '$id'";
            $r = $db->select($sql);
            if($r){
                $total_amount = $r[0]->total_amount; // 红包总金额
                $total_num = $r[0]->total_num; // 红包数量
                $wishing = $r[0]->wishing; // 祝福语
                $min=0.01;//每个人最少能收到0.01元
                if(!empty($total_amount) && $total_num !=1 ){
                    $safe_total=($total_amount-($total_num-1)*$min)/($total_num-1); // 随机安全上限
                    $money=mt_rand($min*100,$safe_total*100)/100;  // 红包金额
                    $total_amount=$total_amount-$money; // 剩余金额
                    // 把剩余金额替换原数据库金额
                    $sql = "update lkt_news_list set total_amount=$total_amount,total_num='$total_num'-1 where id = '$id'";
                    $db->update($sql);
                    // 根据wxid,查询会员信息
                    $sql = "select * from lkt_user where wx_id = '$openid'";
                    $rr = $db->select($sql);
                    if($rr){
                        $user_id = $rr[0]->user_id; // 用户id
                        $wx_name = $rr[0]->wx_name; // 微信昵称
                        $sex = $rr[0]->sex; // 性别
                        // 在分享列表添加一条数据
                        $sql = "insert into lkt_share (user_id,wx_id,wx_name,sex,type,Article_id,coupon) values ('$user_id','$openid','$wx_name','$sex','$n','$id','$money')";
                        $db->insert($sql);

                        $sql = "update lkt_user set money = money+'$money' where wx_id = '$openid'";
                        $db->update($sql);

                        //添加日志
                        $ymoney = $r[0]->money;
                        $event = $user_id.'分享获得了'.$money.'元';
                        $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$money','$ymoney','$event',3)";
                        $rr = $db->insert($sqll);

                        $text = $wx_name . '领取了' . $money . '元';
                        echo json_encode(array('status'=>1,'text'=>$money,'wishing'=>$wishing));
                        exit();
                    }else{
                        echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
                        exit();
                    }
                }else{
                    $text = "红包已抢完";
                    $wishing = '';
                    echo json_encode(array('status'=>1,'text'=>$text,'wishing'=>$wishing));
                    exit();
                }
            }else{
                echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
                exit();
            }
        }else if($n == 1){
            // 根据文章id,查询文章信息
            $sql = "select * from lkt_article where Article_id = '$id'";
            $r = $db->select($sql);
            if($r){
                $total_amount = $r[0]->total_amount; // 红包总金额
                $total_num = $r[0]->total_num; // 红包数量
                $wishing = $r[0]->wishing; // 祝福语
                $min=0.01;//每个人最少能收到0.01元
                if(!empty($total_amount) && $total_num !=1 ){
                    $safe_total=($total_amount-($total_num-1)*$min)/($total_num-1); // 随机安全上限
                    $money=mt_rand($min*100,$safe_total*100)/100;  // 红包金额
                    $total_amount=$total_amount-$money; // 剩余金额
                    // 把剩余金额替换原数据库金额
                    $sql = "update lkt_article set total_amount=$total_amount,total_num='$total_num'-1 where Article_id = '$id'";
                    $db->update($sql);
                    // 根据wxid,查询会员信息
                    $sql = "select * from lkt_user where wx_id = '$openid'";
                    $rr = $db->select($sql);
                    if($rr){
                        $user_id = $rr[0]->user_id; // 用户id
                        $wx_name = $rr[0]->wx_name; // 微信昵称
                        $sex = $rr[0]->sex; // 性别

                        // 在分享列表添加一条数据
                        $sql = "insert into lkt_share (user_id,wx_id,wx_name,sex,type,Article_id,coupon) values ('$user_id','$openid','$wx_name','$sex','$n','$id','$money')";
                        $db->insert($sql);

                        $sql = "update lkt_user set money = money+'$money' where wx_id = '$openid'";
                        $db->update($sql);

                        echo json_encode(array('status'=>1,'text'=>$money,'wishing'=>$wishing));
                        exit();
                    }else{
                        echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
                        exit();
                    }
                }else{
                    $text = "红包已抢完";
                    $wishing = '';
                    echo json_encode(array('status'=>1,'text'=>$text,'wishing'=>$wishing));
                    exit();
                }
            }else{
                echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
                exit();
            }
        }
        return;
    }

    public function AddressManagement(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $openid = $_POST['openid']; // 微信id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if($r){
            $user_id = $r[0]->user_id;
            $user_name = $r[0]->user_name;
            $mobile = $r[0]->mobile;
            $detailed_address = $r[0]->detailed_address;
            $province = $r[0]->province;
            $city = $r[0]->city;
            $county = $r[0]->county;
            $sheng = [];
            $shi = [];
            $xian = [];
            // 查询省
            $sql = "select  *  from admin_cg_group a  where a.G_ParentID=0";
            $rr = $db->select($sql);
            if($rr){
                foreach ($rr as $k => $v) {
                    $result = array();
                    $result['GroupID'] = $v->GroupID; // 编号
                    $result['G_CName'] = $v->G_CName; // 省名
                    $result['G_ParentID'] = $v->G_ParentID; // 类型
                    $sheng[] = $result;
                    unset($result); // 销毁指定变量
                }
            }

            // 查询市
            $sql = "select  *  from admin_cg_group a  where a.G_ParentID=2";
            $rr = $db->select($sql);
            if($rr){
                foreach ($rr as $k => $v) {
                    $result = array();
                    $result['GroupID'] = $v->GroupID; // 编号
                    $result['G_CName'] = $v->G_CName; // 市名
                    $result['G_ParentID'] = $v->G_ParentID; // 类型
                    $shi[] = $result;
                    unset($result); // 销毁指定变量
                }
            }

            // 查询县
            $sql = "select  *  from admin_cg_group a  where a.G_ParentID=35";
            $rr = $db->select($sql);
            if($rr){
                foreach ($rr as $k => $v) {
                    $result = array();
                    $result['GroupID'] = $v->GroupID; // 编号
                    $result['G_CName'] = $v->G_CName; // 县名
                    $result['G_ParentID'] = $v->G_ParentID; // 类型
                    $xian[] = $result;
                    unset($result); // 销毁指定变量
                }
            }

            echo json_encode(array('status'=>1,'sheng'=>$sheng,'shi'=>$shi,'xian'=>$xian));
            exit();
        }else{
            echo json_encode(array('status'=>0));
            exit();
        }
        return;
    }
    // 根据省查询市
    public function getCityArr(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();   
        $count = $_POST['count']; // 接收前台传过来省的行数
        if($count == ''){
            $count = 0;
        }else{
            $count = $count;
        }
        // 查询省的编号
        $sql = "select * from admin_cg_group a where a.G_ParentID=0";
        $r = $db->select($sql);
        if($r){
            $GroupID = $r[$count]->GroupID; // 根据行数,获取第几条数据
        }else{
            $GroupID = 0;
        }
        $shi = [];

        // 根据省查询市
        $sql = "select * from admin_cg_group a where a.G_ParentID='$GroupID'";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k => $v) {
                $result = array();
                $result['GroupID'] = $v->GroupID; // 编号
                $result['G_CName'] = $v->G_CName; // 市名
                $result['G_ParentID'] = $v->G_ParentID; // 类型
                $shi[] = $result;
                unset($result); // 销毁指定变量
            }
        }
        echo json_encode(array('status'=>1,'shi'=>$shi,));
        exit();
        return;
    }
    // 根据省市获取县
    public function getCountyInfo(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $count = $_POST['count']; // 接收前台传过来省的行数
        $column = $_POST['column']; // 接收前台传过来市的行数
        // 查询省的编号
        $sql = "select * from admin_cg_group a where a.G_ParentID=0";
        $r = $db->select($sql);
        if($r){
            $GroupID = $r[$count]->GroupID; // 根据行数,获取第几条数据
        }else{
            $GroupID = 0;
        }
        $xian = [];
        // 根据省查询市
        $sql = "select * from admin_cg_group a where a.G_ParentID='$GroupID'";
        $r = $db->select($sql);
        if($r){
            $GroupID = $r[$column]->GroupID; // 根据行数,获取第几条数据
        }else{
            $GroupID = 0;
        }
        // 根据市查询县
        $sql = "select * from admin_cg_group a where a.G_ParentID='$GroupID'";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k => $v) {
                $result = array();
                $result['GroupID'] = $v->GroupID; // 编号
                $result['G_CName'] = $v->G_CName; // 县名
                $result['G_ParentID'] = $v->G_ParentID; // 类型
                $xian[] = $result;
                unset($result); // 销毁指定变量
            }
        }

        echo json_encode(array('status'=>1,'xian'=>$xian,));
        exit();
        return;
    }
    // 根据省市获取县
    public function Preservation(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();   
        $sheng = $_POST['sheng'];
        $shi = $_POST['shi'];
        $xuan = $_POST['xuan'];

        // 查询省的编号
        $sql = "select * from admin_cg_group a where a.G_ParentID=0";
        $r = $db->select($sql);
        if($r){
            $GroupID = $r[$sheng]->GroupID;
            $province = $r[$sheng]->G_CName;
        }else{
            $GroupID = 0;
            $province = '';
        }

        // 根据省查询市
        $sql = "select * from admin_cg_group a where a.G_ParentID='$GroupID'";
        $r = $db->select($sql);
        if($r){
            $GroupID = $r[$shi]->GroupID;
            $city = $r[$shi]->G_CName;
        }else{
            $GroupID = 0;
            $city = '';
        }

        // 根据市查询县
        $sql = "select * from admin_cg_group a where a.G_ParentID='$GroupID'";
        $r = $db->select($sql);
        if($r){
            $GroupID = $r[$xuan]->GroupID;
            $county = $r[$xuan]->G_CName;
        }else{
            $GroupID = 0;
            $county = '';
        }

        echo json_encode(array('status'=>1,'province'=>$province,'city'=>$city,'county'=>$county));
        exit();
        
        return;
    }
    // 点击保存
    public function SaveAddress(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取小程序传过来的值
        $openid = $_POST['openid'];
        $user_name = $_POST['user_name']; // 联系人
        $mobile = $_POST['mobile']; // 联系电话
        $province = $_POST['province']; // 省
        $city = $_POST['city']; // 市
        $county = $_POST['county']; // 县 
        $address = $_POST['address']; // 详细地址
        // 查询省的编号
        $sql ="select GroupID from admin_cg_group where G_CName='$province'";
        $r = $db->select($sql);
        if($r){
            $sheng = $r[0]->GroupID;
        }else{
            $sheng = 0;
        }
        // 查询市的编号
        $sql ="select GroupID from admin_cg_group where G_CName='$city'";
        $r = $db->select($sql);
        if($r){
            $shi = $r[0]->GroupID;
        }else{
            $shi = 0;
        }
        // 查询县的编号
        $sql ="select GroupID from admin_cg_group where G_CName='$county'";
        $r = $db->select($sql);
        if($r){
            $xian = $r[0]->GroupID;
        }else{
            $xian = 0;
        }
        if(preg_match("/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$/", $mobile)){
            // 根据微信id,查询会员id
            $sql = "select * from lkt_user where wx_id = '$openid'";
            $r = $db->select($sql);
            if($r){
                $user_id = $r[0]->user_id; // 用户id
                $address_xq = $province . $city . $county . $address; // 带省市县的详细地址
                $sql = "select id from lkt_user_address where uid = '$user_id'";
                $r = $db->select($sql);
                if($r){
                    $sql = "insert into lkt_user_address(name,tel,sheng,city,quyu,address,address_xq,uid,is_default) values('$user_name','$mobile','$sheng','$shi','$xian','$address','$address_xq','$user_id',0)";
                    $rr = $db->insert($sql);
                }else{
                    $sql = "insert into lkt_user_address(name,tel,sheng,city,quyu,address,address_xq,uid,is_default) values('$user_name','$mobile','$sheng','$shi','$xian','$address','$address_xq','$user_id',1)";
                    $rr = $db->insert($sql);
                }
                if($rr >= 0){
                    echo json_encode(array('status'=>1,'info'=>'保存成功'));
                    exit();
                } else {
                    echo json_encode(array('status'=>0,'info'=>'未知原因,修改失败！'));
                    exit();
                }
            }else{
                echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
                exit();
            }
        }else{
            echo json_encode(array('status'=>0,'info'=>'手机号码有误！'));
            exit();
        }
        return;
    }

    public function selectuser(){
         $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = $_POST['user_id'];
        $openid = $_POST['openid'];
        $sql = "select * from lkt_user where user_id = '$user_id'";
        $r = $db->select($sql);
        if($r){
            $user['wx_name'] = $r[0]->wx_name;
            $user['headimgurl'] = $r[0]->headimgurl;
            $user['user_id'] = $r[0]->user_id;
        }else{
            $user['wx_name'] = '';
            $user['headimgurl'] = '';
            $user['user_id'] = '';
        }
        $sql001 = "select * from lkt_user where wx_id = '$openid'";
        $r001 = $db->select($sql001);
        if($r001){
            $user['money'] = $r001[0]->money;
            $user['score'] = $r001[0]->score;
        }else{
            $user['money'] = 0;
            $user['score'] = 0;
        }

        // 查询余额参数表
        $sql0001 = "select * from lkt_finance_config where id = 1";
        $r0001 = $db->select($sql0001);
        if($r0001){
            $transfer_multiple = $r0001[0]->transfer_multiple;
            $user['transfer_multiple'] = $transfer_multiple;
        }else{
            $transfer_multiple = 0;
            $user['transfer_multiple'] = '';
        }

        if(!empty($r)){
//            $user['wx_name'] = $r[0]->wx_name;
//            $user['headimgurl'] = $r[0]->headimgurl;
//            $user['user_id'] = $r[0]->user_id;
//            $user['money'] = $r001[0]->money;
//            $user['score'] = $r001[0]->score;
//            $user['transfer_multiple'] = $transfer_multiple;
            echo json_encode(array('status'=>1,'user'=>$user));
            exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'没有该用户'));
            exit();
        }

    }
    public function transfer(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        //开启事务
        $db->begin();
        $user_id = $_POST['user_id'];
        $openid = $_POST['openid'];
        $money = $_POST['money'];
        $date_time = date('Y-m-d H:i:s',time());
        if($money <= 0 || $money == ''){
            echo json_encode(array('status'=>1,'err'=>'正确填写转账金额'));
            exit();
        }else{
            // 查询余额参数表
            $sql = "select * from lkt_finance_config where id = 1";
            $r = $db->select($sql);
            if($r){
                $transfer_multiple = $r[0]->transfer_multiple;
                if($transfer_multiple){
                    if($money%$transfer_multiple == 0){

                    }else{
                        echo json_encode(array('status'=>0,'err'=>'转账金额需要是'.$transfer_multiple.'的倍数'));
                        exit();
                    }
                }
            }

            $sql001 = "select user_id,money from lkt_user where wx_id = '$openid'";
            $r001 = $db->select($sql001);//本人
            if($r001){
                $user_id001 = $r001[0]->user_id;
                $money001 = $r001[0]->money;
            }else{
                $user_id001 = '';
                $money001 = 0;
            }

            $sql002 = "select money from lkt_user where user_id = '$user_id'";
            $r002 = $db->select($sql002);//好友
            if($r002){
                $money002 = $r002[0]->money;
            }else{
                $money002 = 0;
            }

            $sql01 = "update lkt_user set money = money - '$money'  where wx_id = '$openid'";
            $r01 = $db->update($sql01);//本人
            $sql02 = "update lkt_user set money = money + '$money'  where user_id = '$user_id'";
            $r02 = $db->update($sql02);//好友
            $sql0001 = "insert into lkt_record (user_id,money,oldmoney,add_date,event,type) values ('$user_id001','$money','$money001','$date_time','转账给好友','12')"; //本人
            $r0001 = $db->insert($sql0001);
            $sql0002 = "insert into lkt_record (user_id,money,oldmoney,add_date,event,type) values ('$user_id','$money','$money002','$date_time','好友转账','13')";//好友
            $r0002 = $db->insert($sql0002);
            if($r01>0&&$r02>0){
                $db->commit();
                echo json_encode(array('status'=>1,'err'=>'转账成功！'));
                exit();
            }else{
                $db->rollback();
                echo json_encode(array('status'=>0,'err'=>'转账失败！'));
                exit();
            }
        }
    }

    public function perfect_index()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = trim($request->getParameter('user_id')); // 微信id
        $sql002 = "select real_name as name,mobile,sex,province,city,county,wechat_id,birthday from lkt_user where user_id = '$user_id'";
        $r002 = $db->select($sql002);//好友
        if($r002){
            if(empty($r002[0]->name)||empty($r002[0]->mobile)){
               echo json_encode(array('status'=>1,'data'=>$r002[0],'binding'=>0)); 
            }else{
               echo json_encode(array('status'=>1,'data'=>$r002[0],'binding'=>1)); 
            }
        }else{
            echo json_encode(array('status'=>0));
        }
        exit();
    }

    public function perfect()
    {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = trim($request->getParameter('user_id')); // 微信id
        $name = trim($request->getParameter('name')); // 姓名
        $mobile = trim($request->getParameter('mobile')); // mobile
        $province = trim($request->getParameter('province')); // province
        $city = trim($request->getParameter('city')); // city
        $county = trim($request->getParameter('county')); // county
        $wx_id = trim($request->getParameter('wx_id')); // wx_id
        $sex = trim($request->getParameter('sex')); // sex
        $date = trim($request->getParameter('date')); // date

        $name = base64_encode($name);
        $name = base64_decode($name);

        $sql02 = "update lkt_user set real_name = '$name',mobile='$mobile',sex='$sex',province='$province',city='$city',county='$county',wechat_id='$wx_id',birthday='$date' where user_id = '$user_id'";
        $r02 = $db->update($sql02);
        if($r02){
            echo json_encode(array('status'=>1,'succ'=>'修改成功！'));
         }else{
            echo json_encode(array('status'=>0,'err'=>'修改失败！'));
         }
         exit();
    }
}
?>