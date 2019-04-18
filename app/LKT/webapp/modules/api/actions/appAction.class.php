<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class appAction extends Action {

    public function getDefaultView() {

        return;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if ($m == 'user') {
            $this->user();
        }else if ($m == 'get_plug') {
            $this->get_plug();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 获取用户会话密钥
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取临时凭证
        $code = $_POST['code'];

        // 查询小程序配置
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if($r){
            $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
        }
        if (!$code) {
            echo json_encode(array('status'=>0,'err'=>'非法操作！'));
            exit();
        }
        if (!$appid || !$appsecret) {
            echo json_encode(array('status'=>0,'err'=>'非法操作！'));
            exit();
        }
        $url = 'https://api.weixin.qq.com/sns/jscode2session?appid='.$appid.'&secret='.$appsecret.'&js_code='.$code.'&grant_type=authorization_code';
        $ch = curl_init();
        curl_setopt($ch,CURLOPT_URL,$url);
        curl_setopt($ch,CURLOPT_HEADER,0);
        // 保证返回成功的结果是服务器的结果
        curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,FALSE);
        curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,FALSE);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1 );
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
        $res = curl_exec($ch);  
        curl_close($ch);
        $user = (array)json_decode($res);

        $sql = "select * from lkt_background_color where status = 1";
        $r = $db -> select($sql);
        $user['bgcolor'] = $r[0]->color;
        echo json_encode($user);
        exit();
        return;
    }

    //用户信息存储
    public function user(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $software_name = trim($request->getParameter('software_name')); // 软件名
        $edition = trim($request->getParameter('edition')); // 版本号

        $wxname = $_POST['nickName']; // 微信昵称
        $headimgurl = $_POST['headimgurl']; // 微信头像
        $sex = $_POST['sex']; // 性别
        $openid = $_POST['openid']; // 微信id
        $pid = $_POST['p_openid']; // 推荐人微信id

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
        // 生成密钥
        $access_token = '';
        $str = 'QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890';
        for ($i=0;$i<32;$i++){
            $access_token .= $str[rand(0,61)];
        }
        // 判断是否存在推荐人微信id
        if($pid == '' || $pid == 'undefined'){
            $Referee = false;
        }else{
            if(strlen($pid) == '32'){
                $sql = "select * from lkt_user where wx_id = '$pid'";
                $r = $db->select($sql);
                $Referee = $r[0]->user_id;
            }else{
                $Referee = $pid;
            }
        }
        
        // 根据wxid,查询会员信息
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $rr = $db->select($sql);
        if(!empty($rr)){
            $sql = "update lkt_user set access_token = '$access_token' where wx_id = '$openid'";
            $db->update($sql);
            $user_id = $rr[0]->user_id;


            $event = '会员' . $user_id . '登录';
            // 在操作列表里添加一条会员登录信息
            $sql = "insert into lkt_record (user_id,event,type) values ('$user_id','$event',0)";
            $r = $db->insert($sql);

            // 查询订单设置表
            $sql = "select * from lkt_order_config where id = 1";
            $r = $db->select($sql);
            $order_overdue = $r[0]->order_overdue; // 未付款订单保留时间
            $unit = $r[0]->unit; // 未付款订单保留时间单位
            if($order_overdue != 0){
                if($unit == '天'){
                    $time01 = date("Y-m-d H:i:s",strtotime("-$order_overdue day")); // 订单过期删除时间
                }else{
                    $time01 = date("Y-m-d H:i:s",strtotime("-$order_overdue hour")); // 订单过期删除时间
                }  
                // 根据用户id，订单为未付款，订单添加时间 小于 未付款订单保留时间,查询订单表
                $sql = "select * from lkt_order where user_id = '$user_id' and status = 0 and add_time < '$time01' ";
                $r_c = $db->select($sql);
                // 有数据，循环查询优惠券id,修改优惠券状态
                if($r_c){
                    foreach ($r_c as $key => $value) {
                        $coupon_id = $value->coupon_id;  // 优惠券id
                        if($coupon_id != 0){
                            // 根据优惠券id,查询优惠券信息
                            $sql = "select * from lkt_coupon where id = '$coupon_id' ";
                            $r_c = $db->select($sql);
                            $expiry_time = $r_c[0]->expiry_time; // 优惠券到期时间
                            $time = date('Y-m-d H:i:s'); // 当前时间
                            if($expiry_time <= $time){
                                // 根据优惠券id,修改优惠券状态(已过期)
                                $sql = "update lkt_coupon set type = 3 where id = '$coupon_id'";
                                $db->update($sql);
                            }else{
                                // 根据优惠券id,修改优惠券状态(未使用)
                                $sql = "update lkt_coupon set type = 0 where id = '$coupon_id'";
                                $db->update($sql);
                            }
                        }
                    }
                }
                // 根据用户id、订单未付款、添加时间小于前天时间,就删除订单信息
                $sql01 ="delete from lkt_order where user_id = '$user_id' and status = 0 and add_time < '$time01' ";
                $re01 = $db->delete($sql01);
                // 根据用户id、订单未付款、添加时间小于前天时间,就删除订单详情信息
                $sql02 ="delete from lkt_order_details where user_id = '$user_id' and r_status = 0 and add_time < '$time01' ";
                $re02 = $db->delete($sql02);
            }
            //设置抽奖订单的时间超过抽奖时间的订单状态改成相对应的发货中或者交易结束
            $sql0012 = "select id,drawid,sNo from lkt_order where user_id = '$user_id' and drawid > 0";
            $re0012 = $db->select($sql0012);
            $time02 = date("Y-m-d H:i:s", strtotime('+1 day'));
            if(!empty($re0012)){
                foreach ($re0012 as $key0012 => $value0012) {
                    $draw_id = $value0012 ->drawid;
                    $id  = $value0012 ->id;
                    $sNo =$value0012 ->sNo;
                    $sql001201 = "select * from lkt_draw as a,lkt_draw_user as b where a.id = '$draw_id' and a.id = b.draw_id and end_time < $time02 and b.user_id = '$user_id' and lottery_status != 4 ";
                    $re001201 = $db->select($sql001201);
                    if(!empty($re001201)){
                      foreach ($re001201 as $key001201 => $value001201) {
                          $time03 = $value001201->time;
                          $sql001202 ="update lkt_order set status = 4 where sNo = '$sNo'";
                          $re001202 = $db->update($sql001202);

                          $sql001203 ="update lkt_order_details set r_status = 6 where r_sNo = '$sNo'";
                          $re001203 = $db->update($sql001203);

                      }
                    }
                }
            }
        }else{
            // 查询会员列表的最大id
            $sql = "select max(id) as userid from lkt_user";
            $r = $db->select($sql);
            $rr = $r[0]->userid;
//            $user_id = $rr+1;
            $user_id = 'user'.($rr+1);
            // 在会员列表添加一条数据

            // 默认头像和名称
            if(empty($wxname) || $wxname == 'undefined'){
                $wxname = '简单的奇迹';
            }
            if(empty($headimgurl) || $headimgurl == 'undefined'){
                $headimgurl = 'https://lg-8tgp2f4w-1252524862.cos.ap-shanghai.myqcloud.com/moren.png';
            }

            if(empty($sex) || $sex == 'undefined'){
                $sex = '0';
            }
            $sql = "insert into lkt_user (user_id,user_name,headimgurl,wx_name,sex,wx_id,Referee,access_token,img_token,source) values('$user_id','$wxname','$headimgurl','$wxname','$sex','$openid','$Referee','$access_token','$access_token',1)";

            $r = $db->insert($sql);

            //查询首次注册所获积分
            $sql001 = "select jifennum from lkt_software_jifen where  id = '1' ";
            $r_1001 = $db->select($sql001);
            $jifennum = $r_1001[0]->jifennum;
            //添加积分到用户表
            $sql002 = "update lkt_user set score = score + '$jifennum' where user_id = '$user_id'";
            $db->update($sql002);

            // 在积分操作列表里添加一条会员首次登录信息获取积分的信息
            $record = '会员'.$user_id.'首次关注获得积分'.$jifennum;
            $sql = "insert into lkt_sign_record (user_id,sign_score,record,sign_time,type) values ('$user_id','$jifennum','$record',CURRENT_TIMESTAMP,2)";
            $r = $db->insert($sql);


            $event = '会员' . $user_id . '登录';
            // 在操作列表里添加一条会员登录信息
            $sql = "insert into lkt_record (user_id,event,type) values ('$user_id','$event',0)";
            $r = $db->insert($sql);
        }

        $sql = "select name from lkt_software where type = 0 and id = '$software_name' order by id desc";
        $rrrr_1 = $db->select($sql);
        $name1 = $rrrr_1[0]->name;
        // 根据软件名称，查询软件id和名称
         $sql = "select id from lkt_software where name = '$name1' and edition = '$edition' and type = 0";
         $r_software = $db->select($sql);
         if($r_software){
             $software_id = $r_software[0]->id;
         }

        // 查询插件表里,状态为启用的插件
        $sql = "select * from lkt_plug_ins where status = 1 and type = 0 and software_id like '%$software_id%'";
        $r_c = $db->select($sql);
        if($r_c){
            foreach ($r_c as $k => $v) {
                $v->image = $img . $v->image;
                if(strpos($v->name,'优惠券') !== false){ // 判断字符串里是否有 优惠券 
                    $v->name = '优惠券'; 
                    $coupon[$k] = 1;
                }else{
                    $coupon[$k] = 0;
                }
                if($v->name == '钱包'){
                    $wallet[$k] = 1;
                }else{
                    $wallet[$k] = 0;
                }
                if($v->name == '签到'){
                    $sign[$k] = 1;
                }else{
                    $sign[$k] = 0;
                }
            }
            $time_start = date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d'),date('Y'))); // 当前时间
            // 查询签到活动
            $sql = "select * from lkt_sign_activity where status = 1";
            $r_activity = $db->select($sql);
            if($r_activity){
                $sign_image = $img . $r_activity[0]->image; // 签到弹窗图
                $endtime = $r_activity[0]->endtime; // 签到结束时间
                if($endtime <= $time_start){ // 当前时间大于签到结束时间
                    $sign_status = 0; // 不用弹出签名框
                }else{
                    // 根据用户id、签到时间大于当天开始时间,查询签到记录
                    $sql = "select * from lkt_sign_record where user_id = '$user_id' and sign_time >= '$time_start' and type = 0";
                    $r_sign = $db->select($sql);
                    if($r_sign){
                        $sign_status = 0; // 有数据,代表当天签名了,不用弹出签名框
                    }else{
                        $sign_status = 1; // 没数据,代表当天还没签名,弹出签名框
                    }
                }
            }else{
                $sign_image = '';
                $sign_status = 0;
            }
            
            $sql = "select * from lkt_user where wx_id = '$openid'";
            $rr = $db->select($sql);
            $nickName = $rr[0]->wx_name;
            $avatarUrl = $rr[0]->headimgurl;
            echo json_encode(array('access_token'=>$access_token,'user_id'=>$user_id,'plug_ins'=>$r_c,'coupon'=>in_array(1,$coupon),'wallet'=>in_array(1,$wallet),'sign'=>in_array(1,$sign),'sign_status'=>$sign_status,'sign_image'=>$sign_image,'nickName'=>$nickName,'avatarUrl'=>$avatarUrl ) );
            exit();
            
        }else{
            echo json_encode(array('plug_ins'=>''));
            exit();
        }
        return;
    }

    public function get_plug()
    {
        header("Content-type: text/html; charset=utf-8");
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        // 查询插件表里,状态为启用的插件
        $sql = "select name,image from lkt_plug_ins where status = 1 and type = 0 ";
        $r_c = $db->select($sql);

        $coupon = false;
        $wallet = false;
        $integral = false;
        $red_packet = false;
        $pays[0] = array('name' => '微信支付','value' => 'wxPay','icon' => '../../images/wxzf.png','checked' => true);

        if($r_c){
            foreach ($r_c as $k => $v) {
                if(strpos($v->name,'劵') !== false){
                    // 判断字符串里是否有 优惠劵 
                    $v->name = '优惠劵';
                    $coupon = true;
                }
                if($v->name == '钱包'){
                    $wallet = true;
                    $arrayName = array('name' => '钱包支付','value' => 'wallet_Pay','icon' => '../../images/qbzf.png','checked' => false);
                    @array_push($pays,$arrayName);
                }
                if($v->name == '签到'){
                    $integral = true;
                }
                if($v->name == '发红包'){
                    $red_packet = true;
                }
            }

            echo json_encode(array('status'=>1,'pays'=>$pays,'coupon'=>$coupon,'wallet'=>$wallet,'integral'=>$integral,'red_packet'=>$red_packet));
            exit();
        }else{
            echo json_encode(array('status'=>0,'pays'=>$pays,'coupon'=>$coupon,'wallet'=>$wallet,'integral'=>$integral,'red_packet'=>$red_packet));
            exit();
        }
    }
    
    public function secToTime($times){  
        $result = '00:00:00';  
        if ($times>0) {  
                $hour = floor($times/3600);  
                $minute = floor(($times-3600 * $hour)/60);  
                $second = floor((($times-3600 * $hour) - 60 * $minute) % 60);  
                $result = $hour.':'.$minute.':'.$second;  
        }  
        return $result;  
    } 
}

?>