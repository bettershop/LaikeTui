<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');


class DetailAction extends Action
{


    public function getDefaultView()
    {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter('id')); // 订单id
        $da['having'] = $request->getParameter('having');
        $da['ordtype'] = $request->getParameter('ordtype');
        $da['gcode'] = $request->getParameter('gcode');
        $da['ocode'] = $request->getParameter('ocode');
        $da['status'] = $request->getParameter('status');
        $da['source'] = $request->getParameter('source');
        $da['otype'] = $request->getParameter('otype');
        $da['sNo'] = $request->getParameter('sNo1');
        $da['startdate'] = $request->getParameter('startdate');
        $da['enddate'] = $request->getParameter('enddate');
        $da['page'] = $request->getParameter('page');

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $sql = 'select u.user_name,l.sNo,l.name,l.mobile,l.sheng,l.shi,l.z_price,l.xian,l.status,l.address,l.pay,l.trade_no,l.coupon_id,l.reduce_price,l.coupon_price,l.allow,l.drawid,l.otype,d.user_id,d.p_id,d.p_name,d.p_price,d.num,d.unit,d.add_time,d.deliver_time,d.arrive_time,d.r_status,d.content,d.express_id,d.courier_num,d.sid,d.size,d.freight from lkt_order_details as d left join lkt_order as l on l.sNo=d.r_sNo left join lkt_user as u on u.user_id=l.user_id  where l.id="' . $id . '"';
        $res = $db->select($sql);
        $num = count($res);
        $data = array();
        $reduce_price = 0; // 满减金额
        $coupon_price = 0; // 优惠券金额
        $allow = 0; // 积分
        $freight = 0;
        $z_price = 0;
        $courier_num111 = array();

        $o_price = $res[0]->z_price;//修改后的价格

        foreach ($res as $k => $v) {
            $sid = $v->sid;
            $freight = $freight + $v->freight;
            $data['user_name'] = $v->user_name; // 联系人
            $data['name'] = $v->name; // 联系人
            $data['sNo'] = $v->sNo; // 订单号
            $data['mobile'] = $v->mobile; // 联系电话
            $data['address'] = $v->address; // 详细地址
            $data['add_time'] = $v->add_time; // 添加时间
            $data['z_price'] = $v->z_price; //
            $data['user_id'] = $v->user_id; // 用户id
            $data['deliver_time'] = $v->deliver_time; // 发货时间
            $data['arrive_time'] = $v->arrive_time; // 到货时间
            $data['r_status'] = $v->r_status; // 订单详情状态
            $data['status01'] = $v->r_status; // 订单详情状态
            $data['gstatus'] = $v->status; // 订单详情状态
            $data['otype'] = $v->otype;  // 订单类型
            $data['content'] = $v->content; // 退货原因
            $data['express_id'] = $v->express_id; // 快递公司id
            $data['courier_num'] = $v->courier_num; // 快递单号
            $data['drawid'] = $v->drawid; // 抽奖ID
            $data['coupon_price'] = $v->coupon_price; // 快递单号
            $reduce_price = $v->reduce_price; // 满减金额
            $coupon_price = $v->coupon_price; // 优惠券金额
            $allow = $v->allow; // 积分
            $data['paytype'] = $v->pay; // 支付方式
            $data['trade_no'] = $v->trade_no; // 微信支付交易号
            $data['freight'] = $v->freight; // 运费
            $data['id'] = $id;

            $exper_id = $v->express_id;
            if ($exper_id) {
                $r03 = $db->select("select * from lkt_express where id = $exper_id ");
                $res[$k]->kuaidi_name = $r03[0]->kuaidi_name; // 快递公司名称
            } else {
                $res[$k]->kuaidi_name = '';

            }
            $courier_num111[$k]['kuaidi_name'] = $res[$k]->kuaidi_name;
            $courier_num111[$k]['courier_num'] = $v->courier_num;
            // print_r($data);die;
            // 根据产品id,查询产品主图

            $psql = 'select imgurl,product_title from lkt_product_list where id="' . $v->p_id . '"';
            $img = $db->selectarray($psql);

            if (!empty($img)) {
                $v->pic = $img[0]['imgurl'];
                $v->p_name = $img[0]['product_title'];
                $res[$k] = $v;
            }

            $res[$k]->z_price = $v->num * $v->p_price;
            $z_price = $z_price + $v->num * $v->p_price;
            $user_id = $v->user_id; // 用户id
            $drawid = $v->drawid; // 抽奖ID
            $add_time = $v->add_time; // 添加时间

            if (!empty($drawid) && $drawid != 0) {

                $sql07 = "select * from lkt_draw_user where id = '$drawid'  ";
                $r07 = $db->select($sql07);
                if (!empty($r07)) {
                    $lottery_status = $r07[0]->lottery_status;
                    $data['lottery_status'] = $lottery_status;
                } else {
                    $data['lottery_status'] = 7;
                }

            } else {

                $data['lottery_status'] = 7;

            }

        }

        if ($courier_num111[0]) {//去重
            $key = "id";
            $arr = $courier_num111;
            $tmp_arr = [];

            foreach ($arr as $k1 => $v1) {
                if ($v1['courier_num']) {
                    if (in_array($v1['courier_num'], $tmp_arr)) {//搜索$v[$key]是否在$tmp_arr数组中存在，若存在返回true
                        unset($arr[$k1]);
                    } else {
                        $tmp_arr[] = $v1['courier_num'];
                    }
                }

            }

            sort($arr);
            $courier_num111 = $arr;
        }


        $data['courier_num'] = $courier_num111; // 快递单号
        $data['freight'] = $freight;
        $data['o_price'] = $o_price;
        $data['z_price'] = $z_price;

        if ($data['otype'] == 'pt') {

            switch ($data['gstatus']) {
                case 0:
                    $data['r_status'] = '未付款';
                    break;
                case 9:
                    $data['r_status'] = '拼团中';
                    break;
                case 1:
                    $data['r_status'] = '拼团成功-未发货';
                    break;
                case 2:
                    $data['r_status'] = '拼团成功-已发货';
                    break;
                case 3:
                    $data['r_status'] = '拼团成功-已签收';
                    break;
                case 10:
                    $data['r_status'] = '拼团失败-未退款';
                    break;
                case 11:
                    $data['r_status'] = '拼团失败-已退款';
                    break;

            }

        } else {

            if ($data['gstatus'] == 0) {
                $data['r_status'] = '未付款';
            } else if ($data['gstatus'] == 1) {
                $data['r_status'] = '未发货';
            } else if ($data['gstatus'] == 2) {
                $data['r_status'] = '已发货';
            } else if ($data['gstatus'] == 3) {
                $data['r_status'] = '待评论';
            } else if ($data['gstatus'] == 4) {
                $data['r_status'] = '退货';
            } else if ($data['gstatus'] == 5) {
                $data['r_status'] = '已完成';
            } else if ($data['gstatus'] == 6) {
                $data['r_status'] = '订单关闭';
            } else if ($data['gstatus'] == 7) {
                $data['r_status'] = '订单关闭';
            } else if ($data['gstatus'] == 12) {
                $data['r_status'] = '已完成';
            }

        }

        $status = 0;
        $sNo = trim($request->getParameter('sNo'));
        $trade = intval($request->getParameter('trade')) - 1;
        $express_id = $request->getParameter('kuaidi'); // 快递公司id
        $courier_num = $request->getParameter('danhao'); // 快递单号
        $freight = $request->getParameter('yunfei'); // 运费
        $time = date('Y-m-d h:i:s', time());

        if (!empty($sNo) && !empty($trade)) {

            $sql01 = "select * from lkt_order where sNo = '$sNo' ";
            $r01 = $db->select($sql01);
            $data['status01'] = $r01[0]->status; // 根据订单号查询该订单的状态

        }


        $sqll = 'update lkt_order set status=' . $trade . ' where sNo="' . $sNo . '"';
        $rl = $db->update($sqll);
        $sqld = 'update lkt_order_details set r_status=' . $trade . '  where r_sNo="' . $sNo . '"';
        $rd = $db->update($sqld);

        if ($rl > 0 && $rd > 0) {
            echo json_encode(array('status' => 1, 'msg' => '操作成功!'));
            exit();
        }

        $sql02 = "select * from lkt_express ";
        $r02 = $db->select($sql02);

//佣金信息
        $sqllud = "select a.*,b.user_name,b.headimgurl from lkt_distribution_record as a ,lkt_user as b  where a.sNo = " . $data['sNo'] . " and a.level >0  and a.user_id = b.user_id order by level asc";
        $rlud = $db->select($sqllud);
        if (empty($rlud)) {
            $request->setAttribute("fenxiaoshang", 1);
        } else {
            foreach ($rlud as $keyl => $valuel) {
                $user_id = $valuel->user_id;
                $sqlludd = "select user_name,headimgurl from lkt_user where user_id = '$user_id' ";
                $rludd = $db->select($sqlludd);

                $dd[$keyl]['level'] = $valuel->level;
                $dd[$keyl]['money'] = $valuel->money;
                $dd[$keyl]['user_name'] = $rludd[0]->user_name;
                $dd[$keyl]['headimgurl'] = $rludd[0]->headimgurl;
                $request->setAttribute("fenxiaoshang", $dd);
            }
        }
        $allow = 0; // 积分
        $sql02 = "select * from lkt_express ";
        $r02 = $db->select($sql02);
        // print_r($res);die;
        $request->setAttribute("uploadImg", $uploadImg);
        $request->setAttribute("data", $data);
        $request->setAttribute("detail", $res);
        $request->setAttribute("express", $r02);
        $request->setAttribute("reduce_price", $reduce_price);
        $request->setAttribute("coupon_price", $coupon_price);
        $request->setAttribute("allow", $allow);
        $request->setAttribute("num", $num);
        $request->setAttribute("da", $da);

        return View :: INPUT;

    }


    public function execute()
    {

    }


    public function getRequestMethods()
    {

        return Request :: NONE;

    }


}


