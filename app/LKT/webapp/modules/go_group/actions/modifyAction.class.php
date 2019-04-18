<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();
        $id = intval(trim($request->getParameter('id')));
        $set = addslashes(trim($request->getParameter('set')));
        if($set == 'msg'){
            $this -> setgroupmsg();
        }else if($set == 'msgsubmit'){
            $this -> msgsubmit();
        }else if($set == 'gpro'){
            $this -> modifypro();
        }else if($set == 'delpro'){
            $this -> delpro();
        }
        $status = trim($request->getParameter('status')) ? 1:0;
        $request->setAttribute("status",$status);
        return View :: INPUT;
    }
    public function setgroupmsg() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));
        
        $sql = "select * from lkt_group_buy where status='$id'";
        $res = $db -> select($sql);
        
        $res = $res[0];
        list($hour,$minute) = explode(':', $res -> time_over);
        $res -> hour = $hour;
        $res -> minute = $minute;
        $res -> starttime = date('Y-m-d H:i:s',$res -> starttime);
        $res -> endtime = date('Y-m-d H:i:s',$res -> endtime);
        
        $request->setAttribute("list",$res);
    }

    public function msgsubmit() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));
        $groupname = addslashes(trim($request->getParameter('groupname')));
        $peoplenum = addslashes(trim($request->getParameter('peoplenum')));
        $timehour = addslashes(trim($request->getParameter('timehour')));
        $timeminite = addslashes(trim($request->getParameter('timeminite')));
        $starttime = addslashes(trim($request->getParameter('starttime')));
        $overtime = addslashes(trim($request->getParameter('overtime')));
        $groupnum = addslashes(trim($request->getParameter('groupnum')));
        $productnum = addslashes(trim($request->getParameter('productnum')));
        $otype = addslashes(trim($request->getParameter('otype')));
        if($overtime == '0') $overtime = date('Y-m-d H:i:s',strtotime('+1year'));
        $grouptime = $timehour.':'.$timeminite;
        $starttime = strtotime($starttime);
        $overtime = strtotime($overtime);

        $sql = "update lkt_group_buy set groupname='$groupname',man_num=$peoplenum,time_over='$grouptime',starttime='$starttime',endtime='$overtime',groupnum=$groupnum,productnum=$productnum,overtype='$otype' where id=$id";
        $res = $db -> update($sql);
        
        if($res >= 0){
            echo json_encode(array('code' => 1));exit;
        }
    }
    
    public function modifypro() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $gprice = (array)json_decode($request->getParameter('gprice'));
        $mprice = (array)json_decode($request->getParameter('mprice'));
      
      if(!empty($gprice)){
        foreach ($gprice as $k => $v) {
            $gsql = "update lkt_group_product set group_price=$v where id=$k";
            $gres = $db -> update($gsql);
        }
        if($gres >= 0){
            $gcode = 1;
        }
      }else{
         $gcode = 1;
      }
      if(!empty($mprice)){
        foreach ($mprice as $k => $v) {
            $msql = "update lkt_group_product set member_price=$v where id=$k";
            $mres = $db -> update($msql);
         }
         if($mres >= 0){
            $mcode = 1;
        }
        }else{
            $mcode = 1;
        }
        echo json_encode(array('gcode' => $gcode,'mcode' => $mcode));exit;
    }
    
    public function delpro() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter('id'));
        
        $sql = "delete from lkt_group_product where id=$id";
        $res = $db -> delete($sql);
        
        if($res > 0){
            echo json_encode(array('code' => 1));exit;
        }
        
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>