<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class rechargeAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = addslashes(trim($request->getParameter('user_id'))); // 用户user_id
        $types = trim($request->getParameter('m'));
        $price = trim($request->getParameter('price'));

        $sql = "select $types from lkt_user where user_id = '$user_id'";
        $rs = $db->select($sql);
        $rprice = $rs[0]->$types; // 原来的
        if($price < 0){
            if($rprice + $price >= 0){
                $sql = "update lkt_user set $types = $types + '$price' where user_id = '$user_id'";
                $res = $db -> update($sql);
                //添加日志
                if($res){
                    if($types == 'money'){
                        $event = '系统扣除' . $price . '余额';

                        $sqll = "insert into lkt_record (user_id,money,oldmoney,add_date,event,type) values ('$user_id','$price','$rprice',CURRENT_TIMESTAMP,'$event',11)";
                        $rr = $db -> insert($sqll);
                    }else if($types == 'consumer_money'){
                        $event = '系统扣除' . $price . '消费金';

                        $sqll = "insert into lkt_record (user_id,money,oldmoney,add_date,event,type) values ('$user_id','$price','$rprice',CURRENT_TIMESTAMP,'$event',18)";
                        $rr = $db -> insert($sqll);
                        $event1 = '系统扣除' . $price . '消费金';
                        $sql = "insert into lkt_distribution_record(user_id,from_id,money,level,event,type,add_date) values ('$user_id',0,'$price',0,'$event1',6,CURRENT_TIMESTAMP)";
                        $db->insert($sql);
                    }else{
                        $event = '系统扣除' . $price . "积分";

                        $sqll = "insert into lkt_record (user_id,money,oldmoney,add_date,event,type) values ('$user_id','$price','$rprice',CURRENT_TIMESTAMP,'$event',17)";
                        $rr = $db -> insert($sqll);

                        $event1 = "系统扣除" . $price . "积分";
                        $sql = "insert into lkt_sign_record (user_id,sign_score,record,sign_time,type) values ('$user_id','$price','$event1',CURRENT_TIMESTAMP,5)";
                        $db->insert($sql);
                    }

                }
            }else{
                $res = 0;
            }
        }else{
            $sql = "update lkt_user set $types = $types + '$price' where user_id = '$user_id'";
            $res = $db -> update($sql);
            //添加日志 类型 0:登录/退出 1:充值 2:提现 3:分享4:余额消费 5:退款 6红包提现 7佣金 8管理佣金 9 待定 10 消费金 11 系统扣款
            if($res){
                if($types == 'money'){
                    $event = $user_id . '系统充值' . $price .'余额';

                    $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$price','$rprice','$event',14)";
                    $rr = $db -> insert($sqll);
                }else if($types == 'consumer_money'){
                    $event = $user_id . '系统充值' . $price .'消费金';

                    $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$price','$rprice','$event',16)";
                    $rr = $db -> insert($sqll);

                    $event1 = '系统充值' . $price . '消费金';
                    $sql = "insert into lkt_distribution_record(user_id,from_id,money,level,event,type,add_date) values ('$user_id',0,'$price',0,'$event1',13,CURRENT_TIMESTAMP)";
                    $db->insert($sql);
                }else{
                    $event = $user_id . '系统充值' . $price ."积分";

                    $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$user_id','$price','$rprice','$event',15)";
                    $rr = $db -> insert($sqll);

                    $event1 = "系统充值" . $price . "积分";
                    $sql = "insert into lkt_sign_record (user_id,sign_score,record,sign_time,type) values ('$user_id','$price','$event1',CURRENT_TIMESTAMP,6)";
                    $db->insert($sql);
                }
            }else{
                $res = 0;
            }
        }
        echo $res;
        return;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>