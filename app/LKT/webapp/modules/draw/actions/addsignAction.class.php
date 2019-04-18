<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class addsignAction extends Action {

    public function getDefaultView() {
      $db = DBAction::getInstance();
      $request = $this->getContext()->getRequest();
      
      $sql = 'select id,product_title,num from lkt_product_list';
      $res01 = $db ->select($sql);
      $res =$res01?$res01:1;
      // print_r($res);die;

       $request -> setAttribute("res",$res);

        return View :: INPUT;
    }

    public function execute() {
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
       // $state = 0;
       $found_time =  date('Y-m-d H:i:s',time());
       $sql01 = 'select id,product_title,num from lkt_product_list where id= '.$draw_brandid;
      $res01 = $db ->select($sql01);
      $res02 = $res01[0]->num;
      // print_r($res02);die;
        if($res02 < $spelling_number){
          echo 2;exit();
        }else{

      $sql = "insert into lkt_draw(name,draw_brandid,found_time,start_time,end_time,num,spelling_number,collage_number,price,cishu,type) " .
            "values('$name','$draw_brandid','$found_time','$start_time','$end_time','$num','$spelling_number','$collage_number','$price','$cishu','$type')";

        $r = $db->insert($sql);
        if($r ==1){
           echo 1;exit();
        }
       }
       return View :: INPUT;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>