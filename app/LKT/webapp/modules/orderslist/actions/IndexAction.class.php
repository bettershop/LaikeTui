<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $ordtype = array('t0' => '全部订单', 't1' => '普通订单', 't2' => '拼团订单');
        $data = array('未付款', '未发货', '已发货', '待评论', '退货', '已完成', '订单关闭');
        $otype = isset($_GET['otype']) && $_GET['otype'] !== '' ? $_GET['otype'] : false;
        $status = isset($_GET['status']) && $_GET['status'] !== '' ? $_GET['status'] : false;
        $ostatus = isset($_GET['ostatus']) && $_GET['ostatus'] !== '' ? $_GET['ostatus'] : false;
        $sNo = isset($_GET['sNo']) && $_GET['sNo'] !== '' ? $_GET['sNo'] : false;
        $brand = trim($request->getParameter('brand'));
        $prostr = '';
        $URL = '';
        $con = '';

        $this->setpay($db);//修改待付款订单状态
        foreach ($_GET as $key => $value001) {
            $con .= "&$key=$value001";
        }
        if ($brand) {
            $prostr .= " and lpl.brand_id = '$brand'";
        }
        $brand_str = '';
        $sql01 = "select brand_id ,brand_name from lkt_brand_class where recycle = 0";
        $r01 = $db->select($sql01);
        foreach ($r01 as $key => $value) {
            if ($brand == $value->brand_id) {
                $brand_str .= "<option selected='selected' value='$value->brand_id'>$value->brand_name</option>";
            } else {
                $brand_str .= "<option value='$value->brand_id'>$value->brand_name</option>";
            }
        }

        $condition = ' where 1=1';
        $ex = $condition;

        $pageto = $request->getParameter('pageto');
        // 导出
        $pagesize = $request->getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize : '10';
        // 每页显示多少条数据
        $page = $request->getParameter('page');

        // 页码
        if ($page) {
            $start = ($page - 1) * 10;
        } else {
            $start = 0;
        }

        $source = trim($request->getParameter('source'));
        $source_str = '';
        if ($source == 1) {
            $condition .= " and o.source = '1' ";
            $source_str .= "<option selected='selected' value='1'>小程序</option>";
        } else if ($source == 2) {
            $condition .= " and o.source = '2' ";
            $source_str .= "<option value='1'>小程序</option>";
        } else {
            $source_str .= "<option value='1'>小程序</option>";
        }

        $startdate = $request->getParameter("startdate");
        $enddate = $request->getParameter("enddate");
        if ($startdate != '') {
            $condition .= " and add_time >= '$startdate 00:00:00' ";
        }
        if ($enddate != '') {
            $condition .= " and add_time <= '$enddate 23:59:59' ";
        }

        if ($otype == 't1') {
            $condition .= " and o.otype!='pt' and o.drawid=0";
        } else if ($otype == 't2') {
            $condition .= " and o.otype='pt'";
        } else if ($otype == 't3') {
            $condition .= " and o.otype!='pt' and o.drawid>0";
        }

        if (strlen($status) == 1) {
            if ($status !== false) {
                $cstatus = intval($status);
                if ($cstatus == 6) {
                    $condition .= " and (o.status=7 or o.status=6)";
                } else {
                    $condition .= " and o.status=$cstatus";
                }

            }
        } else if (strlen($status) > 1) {
            if ($status !== false) {
                $cstatus = intval(substr($status, 1));
                $condition .= " and o.ptstatus=$cstatus";
            }
        } else if (strlen($status) == 6) {
            if ($status !== false) {
                $condition .= " and (o.status=7 or o.status=6)";
            }
        }

        if ($ostatus !== false) {

            $costatus = intval(substr($ostatus, 1));
            $condition .= " and o.status=$costatus";
        }
        if ($sNo !== false)
            $condition .= ' and (o.sNo like "%' . $sNo . '%" or o.name like "%' . $sNo . '%" or o.mobile like "%' . $sNo . '%" or o.user_id like "%' . $sNo . '%" )';
        $class = '';
        foreach ($data as $k => $v) {
            if ($status === false) {
                $class .= '<option value="' . $k . '">' . $v . '</option>';
            } else {
                $ystatus = intval($status);
                if ($ystatus === $k) {
                    $class .= '<option selected="selected" value="' . $k . '">' . $v . '</option>';
                } else {
                    $class .= '<option value="' . $k . '">' . $v . '</option>';
                }
            }
        }


        $sql1111 = 'select SUM(o.z_price) as z_price,COUNT(o.id) as num from lkt_order as o left join lkt_user as lu on o.user_id = lu.user_id ' . $condition . ' order by add_time desc ';

        $sqlcf = "select * from lkt_config where id = '1'";
        $rcf = $db->select($sqlcf);
        $uploadImg = $rcf[0]->uploadImg;
        $request->setAttribute("uploadImg", $uploadImg);
        $resd_total = $db->select($sql1111);
        $total = $resd_total[0]->num;
        $data1['num'] = $total;
        $data1['numprice'] = $resd_total[0]->z_price;

        if ($pageto == 'This_page') { // 导出本页
            $sql1 = "select o.id,o.consumer_money,o.sNo,o.name,o.sheng,o.shi,o.xian,o.source,o.address,o.add_time,o.mobile,o.z_price,o.status,o.reduce_price,o.coupon_price,o.allow,o.drawid,o.otype,o.ptstatus,o.spz_price,o.pay,o.drawid,lu.user_name,o.user_id from lkt_order as o left join lkt_user as lu on o.user_id = lu.user_id $condition order by add_time desc limit $start,$pagesize";
            $res1 = $db->select($sql1);

            $db->admin_record($admin_id, ' 导出订单第 ' . $page . ' 的信息 ', 4);
        } elseif ($pageto == 'whole') { // 导出全部
            $sql1 = "select o.id,o.consumer_money,o.sNo,o.name,o.sheng,o.shi,o.xian,o.source,o.address,o.add_time,o.mobile,o.z_price,o.status,o.reduce_price,o.coupon_price,o.allow,o.drawid,o.otype,o.ptstatus,o.spz_price,o.pay,o.drawid,lu.user_name,o.user_id from lkt_order as o left join lkt_user as lu on o.user_id = lu.user_id $ex order by add_time desc ";
            $res1 = $db->select($sql1);
            $db->admin_record($admin_id, ' 导出全部订单的信息 ', 4);
        } elseif ($pageto == 'inquiry') { // 导出查询
            $sql1 = "select o.id,o.consumer_money,o.sNo,o.name,o.sheng,o.shi,o.xian,o.source,o.address,o.add_time,o.mobile,o.z_price,o.status,o.reduce_price,o.coupon_price,o.allow,o.drawid,o.otype,o.ptstatus,o.spz_price,o.pay,o.drawid,lu.user_name,o.user_id from lkt_order as o left join lkt_user as lu on o.user_id = lu.user_id $condition order by add_time desc ";
            $res1 = $db->select($sql1);
            $db->admin_record($admin_id, ' 导出查询的订单的信息 ', 4);
        } else {
            $sql1 = "select o.id,o.consumer_money,o.sNo,o.name,o.sheng,o.shi,o.xian,o.source,o.address,o.add_time,o.mobile,o.z_price,o.status,o.reduce_price,o.coupon_price,o.allow,o.drawid,o.otype,o.ptstatus,o.spz_price,o.pay,o.drawid,lu.user_name,o.user_id from lkt_order as o left join lkt_user as lu on o.user_id = lu.user_id $condition order by add_time desc limit $start,$pagesize";
            $res1 = $db->select($sql1);
        }

        $pager = new ShowPager($total, $pagesize, $page);
        $url = 'index.php?module=orderslist' . $con;
        $pages_show = $pager->multipage($url, $total, $page, $pagesize, $start, $para = '');
        foreach ($res1 as $k => $v) {
            $freight = 0;
            $res1[$k]->statu = $res1[$k]->status;
            $order_id = $v->sNo;
            $pay = $v->pay;
            if ($pay == 'combined_Pay') {
                $psql = "select weixin_pay,balance_pay,total from lkt_combined_pay where order_id = '$order_id'";
                $pres = $db->select($psql);
                foreach ($pres as $kp => $vp) {
                    $res1[$k]->weixin_pay = $vp->weixin_pay;
                    $res1[$k]->balance_pay = $vp->balance_pay;
                    $res1[$k]->total = $vp->total;
                }
            }

            $sqldt = "select lpl.imgurl,lpl.product_title,lpl.product_number,lod.p_price,lod.unit,lod.num,lod.size,lod.p_id,lod.courier_num,lod.express_id,lod.freight from lkt_order_details as lod left join lkt_product_list as lpl on lpl.id=lod.p_id where r_sNo='$v->sNo' $prostr";
            $products = $db->select($sqldt);
            $res1[$k]->freight = $freight;
            $num = 0;
            $courier_num111 = array();
            if ($products) {
                foreach ($products as $kd => $vd) {

                    $freight += $vd->freight;
                    $vd->p_priceee = $vd->p_price * $vd->num;
                    $num += $vd->num;
                    $exper_id = $vd->express_id;
                    if ($exper_id) {
                        $r03 = $db->select("select * from lkt_express where id = $exper_id ");
                        $products[$kd]->kuaidi_name = $r03[0]->kuaidi_name; // 快递公司名称
                    } else {
                        $products[$kd]->kuaidi_name = '';

                    }
                    $courier_num111[$kd]['kuaidi_name'] = $products[$kd]->kuaidi_name;
                    $courier_num111[$kd]['courier_num'] = $vd->courier_num;
                }


                $res1[$k]->num = $num;
                $res1[$k]->products = $products;
                $res1[$k]->status_a = '0';//没有订单发货

                $sqldt01 = "select courier_num from lkt_order_details where r_sNo='$v->sNo'";

                $courier_num = $db->select($sqldt01);
                if ($courier_num) {
                    foreach ($courier_num as $kdd => $vdd) {
                        if ($vdd->courier_num) {
                            $res1[$k]->status_a = '1';
                        }
                    }
                }
                if ($v->otype == 'pt') {
                    switch ($v->status) {
                        case 0 :
                            $res1[$k]->status = '未付款';
                            $res1[$k]->bgcolor = '#f5b1aa';
                            $res1[$k]->pt_status = '待付款';
                            break;
                        case 9 :
                            $res1[$k]->status = '拼团中';
                            $res1[$k]->pt_status = '拼团中';
                            $res1[$k]->bgcolor = '#f5b199';
                            break;
                        case 1 :
                            $res1[$k]->status = '未发货';
                            $res1[$k]->pt_status = '拼团成功';
                            $res1[$k]->bgcolor = '#f0908d';
                            break;
                        case 2 :
                            $res1[$k]->status = '已发货';
                            $res1[$k]->pt_status = '拼团成功';
                            $res1[$k]->bgcolor = '#f0908d';
                            break;
                        case 3 :
                            $res1[$k]->status = '已签收';
                            $res1[$k]->pt_status = '拼团成功';
                            $res1[$k]->bgcolor = '#f0908d';
                            break;
                        case 5 :
                            $res1[$k]->status = '已签收';
                            $res1[$k]->pt_status = '拼团成功';
                            $res1[$k]->bgcolor = '#f7b977';
                            break;
                        case 10 :
                            $res1[$k]->status = '未退款';
                            $res1[$k]->pt_status = '拼团失败';
                            $res1[$k]->bgcolor = '#ee827c';
                            break;
                        case 11 :
                            $res1[$k]->status = '已退款';
                            $res1[$k]->pt_status = '拼团失败';
                            $res1[$k]->bgcolor = '#ee827c';
                            break;
                    }
                } else {
                    switch ($v->status) {
                        case 0 :
                            $res1[$k]->status = '未付款';
                            $res1[$k]->bgcolor = '#f5b1aa';
                            break;
                        case 1 :
                            $res1[$k]->status = '未发货';
                            $res1[$k]->bgcolor = '#f09199';
                            break;
                        case 2 :
                            $res1[$k]->status = '已发货';
                            $res1[$k]->bgcolor = '#f19072';

                            break;
                        case 3 :
                            $res1[$k]->status = '待评论';
                            $res1[$k]->bgcolor = '#e4ab9b';
                            break;
                        case 4 :
                            $res1[$k]->status = '退货';
                            $res1[$k]->bgcolor = '#e198b4';
                            break;
                        case 6 :
                            $res1[$k]->status = '订单关闭';
                            $res1[$k]->bgcolor = '#ffbd8b';
                            break;
                        case 7 :
                            $res1[$k]->status = '订单关闭';
                            $res1[$k]->bgcolor = '#ffbd8b';
                            break;
                        case 5 :
                            $res1[$k]->status = '已完成';
                            $res1[$k]->bgcolor = '#f7b977';
                            break;
                        case 12 :
                            $res1[$k]->status = '已完成';
                            $res1[$k]->bgcolor = '#f7b977';
                            break;
                    }
                }

                if ($products[0]->express_id) {
                    $exper_id = $products[0]->express_id;
                    $sql03 = "select * from lkt_express where id = $exper_id ";
                    $r03 = $db->select($sql03);

                    $res1[$k]->kuaidi_name = $r03[0]->kuaidi_name; // 快递公司名称
                }

                $str = '';
                $res1[$k]->yongjin = $str;

            }
            $res1[$k]->freight = $freight;
            if (count($courier_num111) > 0 && $courier_num111[0]) {//去重
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
                $ddd = array();
                foreach ($courier_num111 as $key => $value) {
                    if (!$value['kuaidi_name']) {
                        unset($courier_num111[$key]);
                    } else {
                        $ddd[] = $value;
                    }

                }

            }
            $res1[$k]->courier_num = $ddd;
        }

        $sql02 = "select * from lkt_express ";
        $r02 = $db->select($sql02);
        $request->setAttribute("express", $r02);
        $request->setAttribute("source", $source_str);
        $request->setAttribute("brand_str", $brand_str);
        $request->setAttribute("startdate", $startdate);
        $request->setAttribute("enddate", $enddate);
        $request->setAttribute("ordtype", $ordtype);
        $request->setAttribute("class", $class);
        $request->setAttribute("order", $res1);
        $request->setAttribute("sNo", $sNo);
        $request->setAttribute("otype", $otype);
        $request->setAttribute("status", $status);
        $request->setAttribute("ostatus", $ostatus);
        $request->setAttribute('pageto', $pageto);
        $request->setAttribute('pages_show', $pages_show);
        $request->setAttribute('data1', $data1);

        return View::INPUT;
    }


    public function execute()
    {

    }

    public function getRequestMethods()
    {
        return Request::NONE;
    }

    function setpay($db)
    {//超过待付款时间修改待付款订单状态
        $re = $db->select("select * from lkt_order_config");
        if ($re[0]) {
            $order_failure = $re[0]->order_failure ? $re[0]->order_failure : 24;//待付款订单失效时间
            $nowtimes = date('Y-m-d H:i:s', strtotime('-' . $order_failure . 'hours'));//当前时间的前过期时间

            $r01 = $db->select("select id,sNo from lkt_order where add_time <= '$nowtimes' and status=0 ");
            if ($r01) {
                foreach ($r01 as $key => $value) {
                    $sNo = $value->sNo;
                    $sqll = "update lkt_order set status='7' where sNo='$sNo'";//订单关闭
                    $rl = $db->update($sqll);
                    $sqld = "update lkt_order_details set r_status='6'  where r_sNo='$sNo'";//订单关闭
                    $rd = $db->update($sqld);
                }
            }


        }

    }
}

?>
