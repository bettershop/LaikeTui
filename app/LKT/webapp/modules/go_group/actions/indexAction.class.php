<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class indexAction extends Action {

    public function getDefaultView() {
        
        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();
        $status = trim($request->getParameter('status'));
        $and = '';
        $time = time();
        if($status == 1){
            $and .= " and starttime < '$time' and endtime > '$time' and is_show='0'";
        }else if($status == 2){
            $and .= " and starttime < '$time' and endtime > '$time' and is_show='1'";
        }else if($status == 3){
            $and .= " and endtime < '$time'";
        }
        // 查询插件表
        $condition = '';
        $sql = "select * from lkt_group_buy where 1=1 $and";

        $res = $db -> select($sql);
        foreach ($res as $k => $v) {
            $res[$k] -> time = date('Y-m-d H:i:s',$v -> starttime).' 至 '.date('Y-m-d H:i:s',$v -> endtime);
            $arr = explode(':', $v -> time_over);
            $res[$k] -> time_over = $arr[0].'小时'.$arr[1].'分钟';

            if(time() < $v -> starttime){
                $res[$k] -> code = 1;
            }else if(time() > $v -> starttime && time() < $v -> endtime){
                $res[$k] -> code = 2;
            }else if(time() > $v -> endtime){
                $res[$k] -> code = 3;
            }
        }

        $showsql='select count(*) from lkt_group_buy where is_show=1';
        $showres = $db -> selectarray($showsql);
        list($showres) = $showres[0];
        
        $request->setAttribute("is_show",$showres);
        $request->setAttribute("list",$res);

        if(isset($_GET['use']) && $_GET['use'] == 1){
            $this -> delgroup();
        }else if(isset($_GET['use']) && $_GET['use'] == 2){
            $this -> startgroup();
        }else if(isset($_GET['use']) && $_GET['use'] == 3){
            $this -> stopgroup();
        }
        $request->setAttribute("status",$status);

        return View :: INPUT;
    }
    public function delgroup() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));
        
        $sql = 'delete from lkt_group_buy where status="'.$id.'"';
        $res = $db -> delete($sql);
        $sql1 = 'delete from lkt_group_product where group_id="'.$id.'"';
        $res1 = $db -> delete($sql1);

        if($res > 0 && $res1 > 0){
            echo json_encode(array('status' => 1));exit;
        }
    }

    public function startgroup() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));
        
        $sql = 'update lkt_group_buy set is_show=1 where status="'.$id.'"';
        $res = $db -> update($sql);
        
        if($res > 0){
            echo json_encode(array('status' => 1));exit;
        }
    }
    
    public function stopgroup() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));
        
        $sql = 'update lkt_group_buy set is_show=0 where status="'.$id.'"';
        $res = $db -> update($sql);
        
        if($res > 0){
            echo json_encode(array('status' => 1));exit;
        }
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>