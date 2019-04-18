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
      $ordtype = array('0'=>'全部','GOOD'=>'好评','NOTBAD'=>'中评','BAD'=>'差评');
      $otype = isset($_GET['otype']) && $_GET['otype'] !== ''?$_GET['otype']:false;
      $status = isset($_GET['status']) && $_GET['status'] !== ''?$_GET['status']:false;
      $ostatus = isset($_GET['ostatus']) && $_GET['ostatus'] !== ''?$_GET['ostatus']:false;
      $sNo = isset($_GET['sNo']) && $_GET['sNo'] !== ''?$_GET['sNo']:false;
      $condition = ' where 1=1';
      if($otype){
          $condition .= " and c.CommentType = '$otype' ";
      }
      

      $pageto = $request->getParameter('pageto'); // 导出
      $pagesize = $request->getParameter('pagesize'); // 每页显示多少条数据
      $page = $request->getParameter('page'); // 页码

      $startdate = $request->getParameter("startdate");
      $enddate = $request->getParameter("enddate");
      if($startdate != ''){
          $condition .= " and c.add_time >= '$startdate 00:00:00' ";
      }
      if($enddate != ''){
          $condition .= " and c.add_time <= '$enddate 23:59:59' ";
      }

      
      if(strlen($status) == 1){
        if($status !== false) {
          $cstatus = intval($status);
          $condition .= " and a.r_status=$cstatus";    
        }
      }else if(strlen($status) > 1){
        if($status !== false) {
          $cstatus = intval(substr($status, 1));
          $condition .= " and a.ptstatus=$cstatus";
        }
      }
      if($ostatus !== false) {
        $costatus = intval(substr($ostatus, 1));
        $condition .= " and a.r_status=$costatus";
      }
      if($sNo !== false) $condition .= ' and a.r_sNo like "%'.$sNo.'%" ';

      $sql1 = 'select a.id as odid,a.r_sNo,a.p_price,a.p_name,a.r_status,c.*,o.otype from lkt_order_details AS a LEFT JOIN lkt_comments AS c ON a.r_sNo = c.oid LEFT JOIN lkt_order as o on a.r_sNo = o.sNo'.$condition.' and a.r_status = 5 AND a.sid = c.attribute_id order by c.add_time desc ';

      $res1 = $db -> select($sql1);
      if($res1){


        foreach ($res1 as $k=>$v){
            if(!$v->anonymous || $v->anonymous == ''){
                $v->anonymous = '匿名';
            }
        }
        if($res1[0]->id){
          foreach ($res1 as $k01 => $v01) {
            if($v01->CommentType ==5){
              $res1[$k01]->CommentType1 ='GOOD';
            }elseif ($v01->CommentType ==4) {
              $res1[$k01]->CommentType1 ='GOOD';
            }elseif ($v01->CommentType ==3) {
              $res1[$k01]->CommentType1 ='NOTBAD';
            }elseif ($v01->CommentType ==2) {
              $res1[$k01]->CommentType1 ='BAD';
            }elseif ($v01->CommentType ==1) {
              $res1[$k01]->CommentType1 ='BAD';
            }elseif ($v01->CommentType =='GOOD') {
              $res1[$k01]->CommentType1 ='GOOD';
            }elseif ($v01->CommentType =='NOTBAD') {
             $res1[$k01]->CommentType1 ='NOTBAD';
            }else{
              $res1[$k01]->CommentType1 ='BAD';
            }
          }
        }
      $total = count($res1);
      $pager = new ShowPager($total,$pagesize,$page);
      $offset = $pager->offset;
      if($pageto == 'ne') {
        $sql1 .= " limit $offset,$pagesize";
        $res1 = $db -> select($sql1);
      }
      
      $request->setAttribute("startdate",$startdate);
      $request->setAttribute("enddate",$enddate);
      $request -> setAttribute("ordtype",$ordtype);
      $request -> setAttribute("order",$res1);
      $request -> setAttribute("sNo",$sNo);
      $request -> setAttribute("otype",$otype);
      $request -> setAttribute("status",$status);
      $request -> setAttribute("ostatus",$ostatus);
      $request->setAttribute('pageto',$pageto);

      }
      return View :: INPUT;
    }

    public function execute() {
        
    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>