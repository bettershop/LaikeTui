<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class drawAction extends Action {

    public function getDefaultView() {
        return;
        
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        
        if($m == 'getFormid'){
            $this -> getformid();
        }elseif($m == 'ceshi'){
            $this -> ceshi();
        }else if($m == 'getdraw'){
            $this->getdraw();
        }
        return;
    } 
    

    //存formid
    public function getformid(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $uid = addslashes(trim($request->getParameter('userid')));
        $formid = addslashes(trim($request->getParameter('from_id')));
      
        $fromidsql = "select count(*) as have from lkt_draw_user_fromid where open_id='$uid'";
        $fromres = $db -> select($fromidsql);
        $fromres = intval($fromres[0] -> have);
        $lifetime = date('Y-m-d H:i:s',time());
        if($formid != 'the formId is a mock one'){
            if($fromres < 8){           
                $addsql = "insert into lkt_draw_user_fromid(open_id,fromid,lifetime) values('$uid','$formid','$lifetime')";
                $addres = $db -> insert($addsql);
            }else{
                return false;
            }
        }
    }
    public function ceshi(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        //现在时间的前一天
        $datetime = date('Y-m-d H:i:s',time()-24*60*60);
        //现在时间的前七天
        $datetime1 = date('Y-m-d H:i:s',time()-7*24*60*60);
        //删除超过七天的数据
        $delsql = "delete from lkt_draw_user_fromid where lifetime < '$datetime1'";
        $delres = $db -> delete($delsql);
        //过去五分钟
        $oldtime = date('Y-m-d H:i:s',time()-5*60-24*60*60);
        $sql01 = "select * from lkt_draw where end_time >='$oldtime'and end_time<'$datetime'";//查询符合条件的活动ID
        $re01 = $db -> select($sql01);
        if(!empty($re01)){
            foreach ($re01 as $key01 => $value01) {
               $draw_id = $value01->id;//活动ID
               $name = $value01->name;//活动名称
               $draw_brandid = $value01->draw_brandid;//活动名称
               $sql03 = "select product_title from lkt_product_list where id='$draw_brandid'";//通过活动ID查询活动人员
               $re03 = $db -> select($sql03);
                $product_title = $re03[0]->product_title;//活动商品
               $sql02 = "select * from lkt_draw_user where draw_id='$draw_id'";//通过活动ID查询活动人员
               $re02 = $db -> select($sql02);
               
                if(!empty($re02)){//存在参加活动的订单
                    foreach ($re02 as $key02 => $value02) {
                        $id = $value02->id;//ID
                        $user_id = $value02->user_id;//用户ID

                        $sql04 = "select wx_id from lkt_user where user_id='$user_id'";//查询活动人员wx_id
                        $re04 = $db -> select($sql04);
                        $openid =$re04[0]->wx_id;
                        $sql05 = "select fromid from lkt_draw_user_fromid where open_id='$openid' order by lifetime asc ";//查询活动人员wx_id
                        $re05 = $db -> select($sql05);
                        if(!empty($re05)){//存在符合条件的fromid
                            $fromid = $re05[0]->fromid;//状态
                            $lottery_status = $value02->lottery_status;//状态
                            $time = $value02->time;//中奖时间
                            if($lottery_status==4){
                                $rew[$key01][$key02]['lottery_status'] = '抽奖成功' ;
                            }elseif($lottery_status==2){
                                $rew[$key01][$key02]['lottery_status'] = '参团失败' ;
                            }else{
                                $rew[$key01][$key02]['lottery_status'] = '抽奖失败' ;
                            }
                            $rew[$key01][$key02]['product_title'] = $product_title ;
                            $rew[$key01][$key02]['name'] = $name ;
                            $rew[$key01][$key02]['time'] = $time ;
                            $rew[$key01][$key02]['openid'] = $openid ;
                            $rew[$key01][$key02]['fromid'] = $fromid ;
                        }
                    }
                    $this -> Send_success($rew);
                }
            }
        }
    }
    public function Send_success($rew){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if($r){
          $appid = $r[0]->appid; // 小程序唯一标识
            $appsecret = $r[0]->appsecret; // 小程序的 app secret
            $AccessToken = $this->getAccessToken($appid, $appsecret);
            $url = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token='.$AccessToken;   
        }

        foreach ($rew as $k => $v) {
            foreach ($v as $key => $value) {
                $lottery_status = $value[''] ;
                $product_title = $value['product_title'] ;
                $name = $value['name'] ;
                $time = $value['time'] ;
                $openid = $value['openid'] ;
                $fromid = $value['fromid'] ;
                $lottery_status = $value['lottery_status'] ;
                $data = array();
                $data['access_token'] = $AccessToken;
                $data['touser'] = $openid;
                $sql = "select * from lkt_notice where id = '1'";
                $r = $db->select($sql);
                $template_id = $r[0]->lottery_res;
                $data['template_id'] = $template_id;
                $data['form_id'] = $fromid;
                $minidata = array('keyword1' => array('value' => $name,'color' => "#173177"),'keyword2' => array('value' => $product_title,'color' => "#173177"),'keyword3' => array('value' => $time,'color' => "#173177"),'keyword4' => array('value' => $lottery_status,'color' => "#173177"));
                $data['data'] = $minidata;
                $data = json_encode($data);
                
                $da = $this->httpsRequest($url,$data);
                $delsql = "delete from lkt_draw_user_fromid where open_id='$openid' and fromid='$fromid'";
                $db -> delete($delsql);          
                var_dump(json_encode($da));
            }
        }
    }

    public function getdraw(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $openid = trim($request->getParameter('openid')); // 本人微信id
        $referee_openid = trim($request->getParameter('referee_openid')); // 本人微信id
        $order_id = trim($request->getParameter('order_id')); // 订单id
        
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
       
        $arr = [];
        $user = [];
        // 根据订单id,查询订单号和抽奖id
        $sql = "select sNo,drawid from lkt_order where id = '$order_id'";
        $r_1 = $db->select($sql);
        $sNo = $r_1[0]->sNo; // 订单号
        $arr['drawid'] = $r_1[0]->drawid; // 抽奖id
        // 根据抽奖id,查询抽奖活动id
        $sql = "select draw_id,role from lkt_draw_user where id = " . $arr['drawid'];
        $rr = $db->select($sql);
        $arr['draw_id'] = $rr[0]->draw_id; // 抽奖活动id
        $role = $rr[0]->role; // 角色
        
        // 根据抽奖id,查询那些用户参加了
        $sql = "select user_id from lkt_draw_user where role = '$role' order by id";
        $rrr = $db->select($sql);

        $user_num = count($rrr);
        foreach ($rrr as $k => $v) {
            $sql = "select user_id,wx_name,headimgurl from lkt_user where user_id = '$v->user_id'";
            $r = $db->select($sql);
            $user[] = $r[0];
        }
        $sql = "select a.p_id,a.p_price,a.sid,c.img,c.yprice from lkt_order_details AS a LEFT JOIN lkt_configure AS c ON a.sid = c.id where a.r_sNo = '$sNo'";
        $r_2 = $db->select($sql);
        $p_id = $r_2[0]->p_id; // 商品id
        $arr['p_id'] = $r_2[0]->p_id; // 商品id
        $arr['p_price'] = $r_2[0]->p_price; // 商品抽奖价格
        $arr['yprice'] = $r_2[0]->yprice; // 商品原价
        $arr['sid'] = $r_2[0]->sid; // 商品属性id
        $arr['img'] = $img . $r_2[0]->img; // 商品图片

        $sql = "select product_title from lkt_product_list where id = '$p_id'";
        $r_4 = $db->select($sql);
        $arr['product_title'] = $r_4[0]->product_title; // 商品名称

        $sql = "select num,end_time from lkt_draw where draw_brandid = '$p_id'";
        $r_3 = $db->select($sql);
        $arr['num'] = $r_3[0]->num; // 参加抽奖人数
        $arr['user_num'] = $arr['num'] - $user_num; // 参团还差的人数

        $arr['time'] = strtotime($r_3[0]->end_time) - time(); // 抽奖活动结束时间
        if(count($user) < $r_3[0]->num){
            $arr['draw_status'] = 0; // 参团中
            if($arr['time'] < 0){
                $arr['draw_status'] = 2; // 参团失败
            }
        }else{
            $arr['draw_status'] = 1; // 参团成功
        }
        if($referee_openid != '' && $referee_openid != 'undefined'){
            if($referee_openid == $openid){
                $arr['draw_type'] = true;
            }else{
                $arr['draw_type'] = false;
            }
        }else{
            if(count($user) == 1){
                $arr['draw_type'] = true;
            }else{
                $arr['draw_type'] = false;
            }
        }
        $sql = "select num from lkt_product_list where id = " . $arr['p_id'];
        $r_r = $db->select($sql);
        $arr['stock'] = $r_r[0]->num;
        /* 获取商品属性 */
        $commodityAttr = [];
        $sql_size = "select * from lkt_configure where pid = " . $arr['p_id'];
        $r_size = $db->select($sql_size);
        $array_price = [];
        $array_yprice = [];
        if ($r_size) {
            foreach ($r_size as $key => $value) {
                $array_price[$key] = $value->price;
                $array_yprice[$key] = $value->yprice;
                $attrValueList[0] = array('attrKey' => '型号', 'attrValue' => $value->name);
                $attrValueList[1] = array('attrKey' => '规格', 'attrValue' => $value->size);
                $attrValueList[2] = array('attrKey' => '颜色', 'attrValue' => $value->color);
                $cimgurl = $img.$value->img;
                $commodityAttr[$key] = array('priceId' => $value->id,'price' => $value->price,'stock' => $value->num,'img' => $cimgurl, 'attrValueList' => $attrValueList);
            }
        }
        /* 获取商品属性 */
        echo json_encode(array('status'=>1,'arr'=>$arr,'user'=>$user,'commodityAttr'=>$commodityAttr));
        exit;

    }

}

?>