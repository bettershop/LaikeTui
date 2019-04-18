<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql = "select * from lkt_subtraction where id = '1'";
        $r = $db->select($sql);
        if($r){
            $status = $r[0]->status; // 包邮状态
            $man_money = $r[0]->man_money; // 单笔满多少包邮
            $region = $r[0]->region; // 不参与包邮地区
            $subtraction = unserialize ($r[0]->subtraction); // 满减
            $num  = count($subtraction);
        }

        $request->setAttribute('status', isset($status) ? $status : '');
        $request->setAttribute('man_money', isset($man_money) ? $man_money : '100');
        $request->setAttribute("region", isset($region) ? $region : '');
        $request->setAttribute('subtraction', isset($subtraction) ? $subtraction : '');
        $request->setAttribute('num', isset($num) ? $num : '1');

        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $status = $request->getParameter('status'); // 包邮状态
        $man_money = $request->getParameter('man_money'); // 单笔满多少包邮
        $region = $request->getParameter('region'); // 不参与包邮地区
        $man = $request->getParameter('man'); // 满多少
        $jian = $request->getParameter('jian'); // 减多少
        $list = array();

        foreach ($man as $k => $v){
            if($man[$k] != '' && $jian[$k] != ''){
                if($man[$k] != 0 && $man[$k] > $jian[$k] && $man[$k] > 0 && $jian[$k] >= 0){
                    $list[][$man[$k]] = $jian[$k];
                }
            }
        }
        $subtraction = serialize($list);

        $sql = "select id from lkt_subtraction where id = 1";
        $r = $db->select($sql);
        if($r){
            $sql = "update lkt_subtraction set status='$status',man_money='$man_money',region='$region',subtraction='$subtraction' where id = 1";
            $rr = $db->update($sql);
        }else{
            $sql = "insert into lkt_subtraction(status,man_money,region,subtraction,add_date) values('$status','$man_money','$region','$subtraction',CURRENT_TIMESTAMP)";
            $rr = $db->insert($sql);
        }
        if($r == -1) {
            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=subtraction';</script>";
            return $this->getDefaultView();
        } else {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=subtraction';</script>";
        }
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>