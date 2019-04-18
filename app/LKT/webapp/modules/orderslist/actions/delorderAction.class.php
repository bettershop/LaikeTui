<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class delorderAction extends Action {



	public function getDefaultView() {

       

       return View :: INPUT;

	}



	public function execute(){

	   $db = DBAction::getInstance();

	   $request = $this->getContext()->getRequest();

	   $ids = trim($request->getParameter('ids'));

       //$arr = explode(',',$ids);

       $sql = "select o.id,o.drawid,o.sNo,o.ptcode,o.pid,d.lottery_status from lkt_order as o left join lkt_draw_user as d on o.drawid=d.id where o.id in ($ids)";

	   $res = $db -> select($sql);

	   $gcode = $db -> select("select status from lkt_group_buy where status=(select status from lkt_group_buy where is_show=1)");

	   $gid = !empty($gcode)?$gcode[0]->status:1;

	   $group = array();

	   $draw = array();

	   

       foreach ($res as $k => $v) {                //过滤掉还没结束的拼团订单，和还没得到结果的抽奖订单

       	   if($gid == $v -> pid){

       	   	 $group[] = $v -> sNo;

       	   	 unset($res[$k]);

       	   }

       	   if(in_array($v->lottery_status, array(0,1,2,4)) && $v->lottery_status !== null){    //过滤还没出结果的抽奖订单

             $draw[] = $v -> sNo;

             unset($res[$k]);

       	   }

       	        

       }

       $msg = '删除了 '.count($res).' 笔订单';

       if(!empty($group) || !empty($draw)){

          $msg .= ',已保留了 '.count($group).' 笔活动未结束的拼团订单, '.count($draw).' 笔未出结果的抽奖订单.';

       }

       

         foreach ($res as $key => $value) {

       	   $delo = $db -> delete("delete from lkt_order where sNo='$value->sNo'");

       	   $deld = $db -> delete("delete from lkt_order_details where r_sNo='$value->sNo'");

       	   $delg = $db -> delete("delete from lkt_group_open where ptcode='$value->ptcode'");

       	   $delc = $db -> delete("delete from lkt_draw_user where id=$value->drawid");

         }

 

       	echo json_encode(array('code' => 1,'msg' => $msg));exit;

         

	   

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>