<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class configAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql = "select * from lkt_finance_config where id = 1";
        $r = $db->select($sql);
        if($r){
            $min_cz = $r[0]->min_cz;
            $min_amount = $r[0]->min_amount;
            $max_amount = $r[0]->max_amount;
            $service_charge = $r[0]->service_charge;
            $unit = $r[0]->unit;
            $multiple = $r[0]->multiple;
            $transfer_multiple = $r[0]->transfer_multiple;
            $cz_multiple = $r[0]->cz_multiple;
        }else{
            $min_cz = 50;
            $min_amount = 50;
            $max_amount = 100;
            $service_charge = '0.05';
            $unit = '元';
            $multiple = 0;
            $transfer_multiple = 0;
            $cz_multiple = 100;
        }
        $request->setAttribute('min_cz', $min_cz);
        $request->setAttribute('cz_multiple', $cz_multiple);
        $request->setAttribute('min_amount', $min_amount);
        $request->setAttribute('max_amount', $max_amount);
        $request->setAttribute('service_charge', $service_charge);
        $request->setAttribute('multiple', $multiple);
        $request->setAttribute('transfer_multiple', $transfer_multiple);
        $request->setAttribute('unit', $unit);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $min_cz = addslashes(trim($request->getParameter('min_cz'))); // 最小充值金额
        $min_amount = addslashes(trim($request->getParameter('min_amount'))); // 最小提现金额
        $max_amount = addslashes($request->getParameter('max_amount')); // 最大提现金额
        $service_charge = addslashes($request->getParameter('service_charge')); // 手续费
        $unit = addslashes($request->getParameter('unit')); // 单位
        $multiple = trim($request->getParameter('multiple'));//提现倍数
        $transfer_multiple = trim($request->getParameter('transfer_multiple'));//转账倍数
        $cz_multiple = trim($request->getParameter('cz_multiple'));//充值倍数

        if(is_numeric($min_cz) == false){
            echo "<script type='text/javascript'>" .
                "alert('最小充值金额请输入数字！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            if($min_cz <= 0){
                echo "<script type='text/javascript'>" .
                    "alert('最小充值金额不能小于等于0！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }

        if(is_numeric($min_amount) == false){
            echo "<script type='text/javascript'>" .
                "alert('最小提现金额请输入数字！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            if($min_amount <= 0){
                echo "<script type='text/javascript'>" .
                    "alert('最小提现金额不能小于等于0！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        if(is_numeric($max_amount) == false){
            echo "<script type='text/javascript'>" .
                "alert('最大提现金额请输入数字！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            if($max_amount <= 0){
                echo "<script type='text/javascript'>" .
                    "alert('最大提现金额不能小于等于0！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        } 
        if($min_amount <= $service_charge){
            echo "<script type='text/javascript'>" .
                "alert('最小提现金额不能小于等于手续费！');" .
                "</script>";
                return $this->getDefaultView();
        }else if ($min_amount >= $max_amount) {
            echo "<script type='text/javascript'>" .
                "alert('最小提现金额不能大于等于最大提现金额！');" .
                "</script>";
                return $this->getDefaultView();
        }else{
            $sql = "select * from lkt_finance_config where id = 1";
            $r = $db->select($sql);
            if($r){
                $sql = "update lkt_finance_config set min_cz = '$min_cz',min_amount = '$min_amount',max_amount = '$max_amount',multiple = '$multiple',transfer_multiple = '$transfer_multiple',service_charge = '$service_charge',unit = '$unit',cz_multiple ='$cz_multiple',modify_date = CURRENT_TIMESTAMP where id = 1";
               $r_1 = $db->update($sql);
                if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                        "alert('未知原因，参数修改失败！');" .
                        "location.href='index.php?module=finance&action=config';</script>";
                    return $this->getDefaultView();
                } else {
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('参数修改成功！');" .
                        "location.href='index.php?module=finance&action=config';</script>";
                }
            }else{
                $sql = "insert into lkt_finance_config(min_cz,min_amount,max_amount,service_charge,unit,modify_date,multiple,transfer_multiple,cz_multiple) values('$min_cz','$min_amount','$max_amount','$service_charge','$unit',CURRENT_TIMESTAMP,'$multiple','$transfer_multiple','$cz_multiple')";
                $r_1 = $db->insert($sql);
                if($r_1 == -1) {
                echo "<script type='text/javascript'>" .
                        "alert('未知原因，参数添加失败！');" .
                        "location.href='index.php?module=finance&action=config';</script>";
                    return $this->getDefaultView();
                } else {
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('参数添加成功！');" .
                        "location.href='index.php?module=finance&action=config';</script>";
                }
            }
        }
        
        return;

    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>