<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_WEBAPP_DIR . "/plugins/PluginAction.class.php");

class distributionAction extends PluginAction
{

    public function home()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $paegr = addslashes(trim($request->getParameter('page'))); //  '页面'

        $select = addslashes(trim($request->getParameter('select'))); //  选中的方式 0 默认  1 销量   2价格
        if ($select == 0) {
            $select = 'a.add_date';
        } elseif ($select == 1) {
            $select = 'a.volume';
        } else {
            $select = 'a.price';
        }

        $sort = addslashes(trim($request->getParameter('sort'))); // 排序方式  1 asc 升序   0 desc 降序
        if ($sort) {
            $sort = ' asc ';
        } else {
            $sort = ' desc ';
        }

        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        if (!$paegr) {
            $paegr = 1;
        }
        $start = ($paegr - 1) * 10;
        $end = 10;

        $sql = "select a.*,b.leve1  from lkt_product_list AS a,lkt_detailed_pro AS b where a.id = b.pro_id   AND a.num > 0 AND b.status=1 order by $select $sort LIMIT $start,$end   ";
        $r = $db->select($sql);

        if ($r) {
            $product = [];
            foreach ($r as $k => $v) {
                $imgurl = $img . $v->imgurl;
                $pid = $v->id;
                $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                $r_ttt = $db->select($sql_ttt);
                $price = $r_ttt[0]->yprice;
                $price_yh = $r_ttt[0]->price;

                $attr = unserialize($v->initial);
                $attr = array_values($attr);
                if ($attr) {
                    if (gettype($attr[0]) != 'string') unset($attr[0]);
                }
                $product[$k] = array('id' => $v->id, 'name' => $v->product_title, 'price' => $price, 'price_yh' => $price_yh, 'imgurl' => $imgurl, 'volume' => $v->volume, 's_type' => $v->s_type, 'fan' => $price*$v->leve1/100);
            }
            echo json_encode(array('status' => 1, 'pro' => $product));
            exit;
        } else {
            echo json_encode(array('status' => 0, 'err' => '没有了！'));
            exit;
        }

    }


    //分销中心
    public function user()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $openid = addslashes($request->getParameter('openid'));
        $user = $db->selectOne("select * from lkt_user where wx_id = '$openid' ");
        if ($user) {
            $user_id = $user->user_id;

            //累计收益
            $sql = "select sum(s_money) ss from lkt_detailed_commission where userid='$user_id' ";
            $rs = $db->selectOne($sql);
            $ljyj = $rs->ss;
            $user->ljyj = $ljyj?$ljyj:0;

            $sql = "select * from lkt_user  where Referee='$user_id'  order by id desc  ";
            $list_1 = $db->select($sql);

            //收益记录
            $sql = "select a.*,b.wx_name parent_name from lkt_detailed_commission as a,lkt_user as b where a.userid='$user_id' and a.Referee=b.user_id  order by a.id desc";
            $list_2 = $db->select($sql);

            $list_3 = $list_2;
            echo json_encode(array('status'=>1,'user'=>$user,'list_1'=>$list_1,'list_2'=>$list_2,'list_3'=>$list_3));

        }

        exit();


    }



    //确认收货接口，在这里面加上分销的核心算法
    public function okOrder($order=null){
        $oid = $order->id;
        $db = DBAction::getInstance();
        $order = $db->selectOne("select * from lkt_order where id=$oid and plugin='distribution' ");
        if ($order){
            $user_id = $order->user_id;
            $sNo = $order->sNo;
            $sql = "select a.*,b.leve,b.leve1,b.leve2,b.leve3,b.commissions from lkt_order_details as a,lkt_detailed_pro as b where a.r_sNo='$sNo' and a.p_id = b.pro_id ";
            $details = $db->select($sql);
            foreach ($details as $key => $values) {
                $p_price = $values->p_price;
                $num = $values->num;
                $leve = $values->leve;
                $leve1 = $values->leve1;
                $leve2 = $values->leve2;
                $leve3 = $values->leve3;
                $commissions = $values->commissions;
                $bili = array($leve1,$leve2,$leve3);
                $total = $p_price*$num;
                //进行返回积分
                $i=0;
                $uid = $user_id;
                while ($leve>0 && $i<$leve){
                    $money = $total*$bili[$i]/100;
                    $s_money = $money - $money*$commissions/100;
                    if ($money>0){
                        $sql = "select * from lkt_user where user_id='$uid' ";
                        $user = $db->selectOne($sql);
                        $pid = $user->Referee;
                        if ($pid) {
                            $creattime = date('Y-m-d H:i:s');
                            $sql = "insert into lkt_detailed_commission(userid,sNo,money,s_money,status,addtime,type,Referee) values('$pid',$sNo,$money,$s_money,2,'$creattime',1,'$user_id')";
                            $db->insert($sql);
                            //充值积分
                            $sql = "update lkt_user set score=score+$s_money where user_id='$pid' ";
                            $db->update($sql);
                        }
                        $uid = $pid;
                    }
                    $i++;
                }


            }

        }

        return $order;
    }

}

?>