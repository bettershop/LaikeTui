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

        $pageto = $request -> getParameter('pageto');
        // 每页显示多少条数据
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:'10';
        // 页码
        $page = $request -> getParameter('page');
        if($page){
            $page = $page;
            $start = ($page-1)*10;
        }else{
            $page = 1;
            $start = 0;
        }

        $sql02 = "select * from lkt_lottery_parameters ";
        $r02 = $db->select($sql02);
        if(!empty($r02)){
            $parameters =$r02[0]->parameters;
            $id11 =$r02[0]->id;
        }else{
            $parameters ='';
            $id11 ='';
        }

        $sql01 = "select * from lkt_draw order by found_time desc";
        $r01 = $db->select($sql01);
        $total = count($r01);
        $pager = new ShowPager($total, $pagesize, $page);
        $offset = $pager -> offset;
        $sql = "select * from lkt_draw order by found_time desc limit $offset,$pagesize";
        $r = $db->select($sql);
        if(!empty($r)){
            foreach ($r as $key => $value) {
                $id = $value->id;
                $draw_brandid = $value->draw_brandid;
                $start_time = $value->start_time;
                $end_time = $value->end_time;
                $sql03 = "select * from lkt_draw where id =$id";
                $r03 = $db->select($sql03);
                $num = $r03[0]->num;//每个团所需人数
                $spelling_number = $r03[0]->spelling_number;//可抽中奖次数（默认为1）
                $collage_number = $r03[0]->collage_number;//最少开奖团数（默认为1）

                $sql04 = "select role ,count(*) from lkt_draw_user where draw_id = $id group by role having count(*)>='$num'";
                $r04 = $db->selectrow($sql04);//符合的团数

               $sql01 = "select product_title from lkt_product_list where id = $draw_brandid";
                $r01 = $db->select($sql01); 
                if($r01[0]->product_title){
                    $r[$key]->draw_brandname = $r01[0]->product_title;
                }else{
                   $r[$key]->draw_brandname = 0; 
                }
                $data_time =  date('Y-m-d H:i:s',time());
                if($end_time<$data_time && $start_time<$data_time){
                    $hours = date('Y-m-d H:i:s',strtotime('-1 day'));
                     $r[$key]->status ="已结束";
                     if($end_time>=$hours){
                        $r[$key]->status1 =1;//可抽奖
                     }else{
                        $r[$key]->status1 =2;//不可抽奖
                     }
                }elseif ($end_time>$data_time && $start_time<$data_time) {
                    if($r04 >=$collage_number){
                        $r[$key]->status ="进行中可抽奖";
                        $r[$key]->status1 =3;//可抽奖
                    }else{
                        $r[$key]->status ="进行中不可抽奖";
                        $r[$key]->status1 =4;//不可抽奖
                    }
                }else{
                    $r[$key]->status ="未开始";
                    $r[$key]->status1 =5;//不可抽奖
                }

            }
        }
        $url = "index.php?module=draw&action=Index";
        // $pages_show = $pager->multipage($url,ceil($total/$pagesize),$page, $para = '');
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');
        // print_r($r);die;
        $request->setAttribute("list",$r);
        $request->setAttribute("parameters",$parameters);
        $request->setAttribute("id11",$id11);
        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('pagesize', $pagesize);


        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>