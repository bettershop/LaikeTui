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

        // 接收信息

        $id = intval($request->getParameter("id")); // 插件id

        // 根据插件id，查询插件信息 

        // print_r($id);die;



        $sql = 'select * from lkt_draw where id='.$id;

        $res1 = $db -> select($sql);

        $res['name'] = $res1[0]->name;

        $res['id'] = $res1[0]->id;

        $draw_brandid = $res1[0]->draw_brandid;

        $sql02 = 'select id,product_title from lkt_product_list where id = '.$draw_brandid ;

        $res03 = $db ->select($sql02);

        $res['product_title'] = $res03[0]->product_title;

        $res['draw_brandid'] = $res1[0]->draw_brandid;

        $res['start_time'] = $res1[0]->start_time;

        $res['end_time'] = $res1[0]->end_time;

        $res['num'] = $res1[0]->num;

        $res['collage_number'] = $res1[0]->collage_number;

        $res['cishu'] = $res1[0]->cishu;

        $res['price'] = $res1[0]->price;

        $res['spelling_number'] = $res1[0]->spelling_number;

        $res['type'] = $res1[0]->type;

    

// print_r($res);die;

      $sql01 = 'select id,product_title from lkt_product_list';

      $res01 = $db ->select($sql01);

      $res02 =$res01?$res01:1;



       $request -> setAttribute("res",$res02);

        $request->setAttribute("mm",$res);

        // print_r($res);die;



        

        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

       $request = $this->getContext()->getRequest();

       $name = $request->getParameter('huodongname');//活动名称

       $draw_brandid = $request->getParameter('shangpin');//商品id

       $start_time = $request->getParameter('start_time');//开始时间

       $end_time1 = $request->getParameter('end_time');//结束时间

       $end_time =  $end_time1." 23:59:59";

       $num = $request->getParameter('num');//每个团所需人数

       $collage_number = $request->getParameter('collage_number');//最少开奖团数

       $cishu = $request->getParameter('cishu');//用户最多可参与次数

       $price = $request->getParameter('price');//参与抽奖的价格

       $spelling_number = $request->getParameter('spelling_number');//中奖次数

       $type = $request->getParameter('type');//备注

       $id = $request->getParameter('id');//ID

       // $state = 0;

       $found_time =  date('Y-m-d H:i:s',time());





      $sql = 'update lkt_draw set name="'.$name.'",draw_brandid="'.$draw_brandid.'",found_time="'.$found_time.'",start_time="'.$start_time.'",end_time="'.$end_time.'",num="'.$num.'",spelling_number="'.$spelling_number.'",collage_number="'.$collage_number.'",price="'.$price.'",cishu="'.$cishu.'",type="'.$type.'" where id="'.$id.'"';

      // print_r($sql);die;

        $r = $db->update($sql);

        if($r ==1){

           echo 1;exit();

        }

        return View :: INPUT;

        

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>