<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class listAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = addslashes(trim($request->getParameter('user_id'))); // 用户id

        $condition = '1 = 1';
        if($user_id != ''){   
            $condition .= " and user_id like '$user_id'";
        }
        $sql = "select * from lkt_bargain_config where id = 1";
        $r_1 = $db->select($sql);
        $invalid_time = $r_1[0]->invalid_time; // 逾期时间
        $time = date("Y-m-d H:i:s");

        // 根据条件查询砍价记录表
        $sql = "select * from lkt_bargain_record where $condition";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k => $v) {
                $bargain_id = $v->id; // 砍价记录id
                $s_id = $v->s_id; // 属性id
                $money = $v->money; // 砍掉的金额
                // 根据属性id、产品id,查询商品列表(商品id、商品名称、商品排序),属性表(属性名称、颜色、图片、规格、砍价金额)
                $sql = 'select a.id,a.product_title,a.sort,c.name,c.color,c.img,c.size,c.bargain_price from lkt_product_list AS a  LEFT JOIN lkt_configure AS c ON a.id = c.pid '." where c.id = '$s_id'".' order by sort ';
                $rr = $db->select($sql);
                $pid = $rr[0]->id;
                $product_title = $rr[0]->product_title; // 商品名称
                $bargain_price = $rr[0]->bargain_price; // 商品砍价金额
                $v->bargain_price = $rr[0]->bargain_price; // 商品砍价金额
                $v->product_name = $rr[0]->product_title . '[' . $rr[0]->name . '-' . $rr[0]->color . '-' . $rr[0]->size . ']'; // 拼接商品全名
                $v->end_time = date("Y-m-d H:i:s",strtotime("+$invalid_time day",strtotime($v->add_time))); // 逾期失效时间
                // 当当前时间大于等于失效时间 并且 (状态为砍价成功 或者为 砍价中)时,把砍价状态改为(逾期失效)
                if($time >= $v->end_time && ($v->status = 1 || $v->status = 0)){
                    $sql = "update lkt_bargain_record set status = 2 where id = '$bargain_id' ";
                    $db->update($sql);
                }
                // 当砍价金额等于砍掉的金额时,修改砍价状态(砍价成功)
                if($rr[0]->bargain_price == $money){
                    $sql = "update lkt_bargain_record set status = 1 where id = '$bargain_id' ";
                    $db->update($sql);
                }
                // 当用户填写了收货信息 并且 状态为砍价成功 并且 时间还没过期, 把状态改为生成订单,在订单表和订单详情里添加一条数据
                if($v->name != '' && $v->tel != '' && $v->sheng != '' && $v->city != '' && $v->quyu != '' && $v->address != '' && $time < $v->end_time && $v->status = 1){
                    // 修改状态为生成订单
                    $sql = "update lkt_bargain_record set status = 3 where id = '$bargain_id' ";
                    $db->update($sql);
                    $sNo = $this ->order_number(); // 生成订单号

                    //写入配置
                    $size = $rr[0]->name.' '.$rr[0]->color.' '.$rr[0]->size;
                    if($rr[0]->name == $rr[0]->color && $rr[0]->color == $rr[0]->size){
                        $size = $rr[0]->name;
                    }
                    // 在订单详情里添加一条数据
                    $sql_d = 'insert into lkt_order_details(user_id,p_id,p_name,p_price,num,unit,r_sNo,add_time,r_status,size) VALUES '."('$user_id','$pid','$product_title','$bargain_price','1','件','$sNo',CURRENT_TIMESTAMP,0,'$size')";
                    $db->insert($sql_d);
                    // 在订单表里添加一条数据
                    $sql_o = 'insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,pay,add_time,status,coupon_id,allow,coupon_activity_name) VALUES '."('$user_id','$v->name','$v->tel','1','$bargain_price','$sNo','$v->sheng','$v->city','$v->quyu','$v->address','砍价免单',CURRENT_TIMESTAMP,0,'0','0','')";
                    $db->insert($sql_o,"last_insert_id");
                }
            }
        }

        $request->setAttribute("user_id",$user_id);
        $request->setAttribute("list",$r);
        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

    // 生成订单号
    private function order_number(){
        return date('Ymd',time()).time().rand(10,99);//18位
    }

}

?>