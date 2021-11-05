<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class kaituanAction extends PluginAction {

    public function getDefaultView() {
        
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $product_title = addslashes(trim($request->getParameter('product_title')));//商品名称
        $user = addslashes(trim($request->getParameter('user')));//参团名称
         $status = trim($request->getParameter('status'));
        $pagesize = 10;
        // 每页显示多少条数据
        $page = $request -> getParameter('page');
        $page=$page?$page:1;
        // 页码
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        };
         $con = "a.uid = b.wx_id and c.id = a.ptgoods_id and a.group_id = d.status";
         if($user){
             $con .=" and b.user_name like '%$user%'";
         }
         if($product_title){
             $con .=" and c.product_title like '%$product_title%'";
         }
        

        $sql= "select a.*,b.user_id,b.user_name,c.product_title,d.starttime from lkt_group_open as a,lkt_user as b ,lkt_product_list as c ,lkt_group_buy as d where ".$con ." order by a.addtime desc";
        $re = $db->select($sql);
 
        if(!empty($re)){
          foreach ($re as $key => $value) {
            $uid = $value->user_id;
            $ptcode = $value->ptcode;
            $value->group_price = '';//拼团价
            $value->member_price = '';//团长价
             $value->price = '';//原价
             $value->add_time = date('Y-m-d H:i:s',$value -> starttime);
             $sql01 = "select d.sid from (select k.ptgoods_id,k.ptnumber,k.addtime as cantime,k.endtime,k.ptstatus,p.name,p.num,p.sNo,p.sheng,p.shi,p.xian,p.address,p.mobile,p.status from lkt_group_open as k right join lkt_order as p on k.ptcode=p.ptcode where p.ptcode='$ptcode' and p.user_id='$uid') as m left join lkt_order_details as d on m.sNo=d.r_sNo";
            $res = $db -> select($sql01);
            if(!empty($res)){
                $sid = $res[0]->sid;
                  $sql02 = "select b.group_price,b.member_price from lkt_group_open as a,lkt_group_product as b where a.group_id = b.group_id and b.attr_id =$sid";
                $res01 = $db -> select($sql02);
                if($res01){
                    $value->group_price = $res01[0]->group_price;//拼团价
                     $value->member_price = $res01[0]->member_price;//团长价
                }

                $sql03 = "select price from lkt_configure where id =$sid";
                $res02 = $db -> select($sql03);
                if($res02){
                    $value->price = $res02[0]->price;//原价
                }
            }
          }
        }

        $sql001 = "select a.*,b.user_id,b.user_name,c.product_title,d.starttime from lkt_group_open as a,lkt_user as b ,lkt_product_list as c ,lkt_group_buy as d where ".$con;
        $r_pager = $db->select($sql001);
        if($r_pager){
            $total = count($r_pager);
        }else{
            $total = 0;
        }
        $pager = new ShowPager($total,$pagesize,$page);
        $url = "index.php?module=pi&p=pintuan&action=kaituan&user=".urlencode($user)."&status=".urlencode($status)."&product_title=".urlencode($product_title)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');// url 总条数 当前页码  每页显示条数 
         $request -> setAttribute('pages_show', $pages_show);//分页

        $request->setAttribute("list",$re);
        $request->setAttribute("status",$status);
        $request->setAttribute("product_title",$product_title);
        $request->setAttribute("user",$user);
        return View :: INPUT;
    }


    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>