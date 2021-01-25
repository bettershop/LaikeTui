<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class configAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql = "select * from lkt_order_config where id = 1";
        $r = $db->select($sql);
        if ($r) {
            $content = $r[0]->content;
            $back = $r[0]->back;
            $order_failure = $r[0]->order_failure;
            $company = $r[0]->company;
            $order_overdue = $r[0]->order_overdue;
            $unit = $r[0]->unit;
            if ($r[0]->days == 0) {
                $days = '';
            } else {
                $days = $r[0]->days;
            }
            $customer = $r[0]->customer;
            $kdkey = $r[0]->kdkey;
        } else {
            $days = '';
            $content = '';
            $back = 2;
            $order_failure = 2;
            $company = '天';
            $order_overdue = 2;
            $unit = '天';
        }


        $request->setAttribute("days", $days);
        $request->setAttribute("content", $content);
        $request->setAttribute("back", $back);
        $request->setAttribute("order_failure", $order_failure);
        $request->setAttribute("company", $company);
        $request->setAttribute("order_overdue", $order_overdue);
        $request->setAttribute("unit", $unit);

        $request->setAttribute("customer", $customer);
        $request->setAttribute("kdkey", $kdkey);


        return View :: INPUT;
    }

    public function execute()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');
        $back = addslashes(trim($request->getParameter('back'))); // 退货时间
        $order_overdue = trim($request->getParameter('order_failure')); // 订单过期删除时间
        $customer = trim($request->getParameter('customer'));
        $kdkey = trim($request->getParameter('kdkey'));



        $sql = "select * from lkt_order_config";
        $r = $db->select($sql);
        if ($r) {
            $sql = "update lkt_order_config set back = '$back',customer = '$customer',kdkey = '$kdkey',order_failure = '$order_overdue',modify_date = CURRENT_TIMESTAMP where id = 1";
            $r_1 = $db->update($sql);
            if ($r_1 == -1) {
                $db->admin_record($admin_id, ' 修改订单设置失败 ', 2);
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，订单设置修改失败！');" .
                    "location.href='index.php?module=orderslist&action=config';</script>";
                return $this->getDefaultView();
            } else {
                $db->admin_record($admin_id, ' 修改订单设置 ', 2);
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('订单设置修改成功！');" .
                    "location.href='index.php?module=orderslist&action=config';</script>";
            }
        } else {
            $sql = "insert into lkt_order_config(back,order_failure,modify_date) value('$back','$order_overdue',CURRENT_TIMESTAMP)";
            $r_1 = $db->insert($sql);
            if ($r_1 == -1) {
                $db->admin_record($admin_id, ' 修改订单设置失败 ', 2);
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，订单设置添加失败！');" .
                    "location.href='index.php?module=orderslist&action=config';</script>";
                return $this->getDefaultView();
            } else {
                $db->admin_record($admin_id, ' 修改订单设置 ', 2);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('订单设置添加成功！');" .
                    "location.href='index.php?module=orderslist&action=config';</script>";
            }
        }
        return;
    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

}
