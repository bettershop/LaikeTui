<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class searchproAction extends Action {

    



    public function getDefaultView() {

      $db = DBAction::getInstance();


      $request = $this->getContext()->getRequest();
      $product_class = $request->getParameter('proc'); // 分类名称
      $product_title = $request->getParameter('proname'); // 标题
// var_dump($request);die;
      $proids = isset($_COOKIE['proids'])?$_COOKIE['proids']:'';
      // var_dump($proids);die;
      $sql01 = "select cid,pname from lkt_product_class where sid = 0 ";
      $rr = $db->select($sql01);
      $res = '';
      foreach ($rr as $key => $value) {
        $c = '-'.$value->cid.'-';
        //判断所属类别 添加默认标签
        if ($product_class == $c) {
          $res .= '<option selected="selected" value="'.$c.'">'.$value->pname.'</option>';
        }else{
          $res .= '<option  value="'.$c.'">'.$value->pname.'</option>';
        }
        //循环第一层
        $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid";
        $r_e = $db->select($sql_e);
        if($r_e){
          $hx = '-----';
          foreach ($r_e as $ke => $ve){
            $cone = $c . $ve->cid.'-';
            //判断所属类别 添加默认标签
            if ($product_class == $cone) {
              $res .= '<option selected="selected" value="'.$cone.'">'.$hx.$ve->pname.'</option>';
            }else{
              $res .= '<option  value="'.$cone.'">'.$hx.$ve->pname.'</option>';
            }
            //循环第二层
            $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid";
            $r_t = $db->select($sql_t);
            if($r_t){
              $hxe = $hx.'-----';
              foreach ($r_t as $k => $v){
                $ctow = $cone . $v->cid.'-';
                //判断所属类别 添加默认标签
                if ($product_class == $ctow) {
                  $res .= '<option selected="selected" value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                }else{
                  $res .= '<option  value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                }
              }
            }
          }
        }
      }
      $arr =[];
      $condition = ' 1=1 ';
      if($product_class != ''){   
        $condition .= " and a.product_class like '%$product_class%' ";
      }
      
      if($product_title != ''){
        $condition .= " and a.product_title like '%$product_title%' ";
      }
      $sql = "select  a.id,a.product_title,a.imgurl,product_class from lkt_product_list as a where $condition" . ' order by status asc,a.add_date desc,a.sort desc ';
      // print_r($sql);die;
      $r = $db->select($sql);
      $list = [];
      $status_num = 0;
      foreach ($r as $key => $value) {
        $pid =  $value -> id;
        $class =  $value -> product_class;
        // $num =  $value -> num;
        $typestr=trim($class,'-');
        $typeArr=explode('-',$typestr);
        //  取数组最后一个元素 并查询分类名称
        $cid = end($typeArr);
        $sql_p = "select pname from lkt_product_class where cid ='".$cid."'";
        $r_p = $db->select($sql_p);
        if($r_p){
          $pname = $r_p['0']->pname;
        }else{
          $pname = '顶级';
        }


        foreach ($value as $k => $v) {
          $arr[$k] = $v;
        }
        $arr['pname'] = $pname;

        $list[$key] = (object)$arr;
      }

            // 查询系统参数

        $sql1 = "select * from lkt_config where id = 1";

        $r_1 = $db->select($sql1);

        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名

        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置

        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../

            $img = $uploadImg_domain . $uploadImg; // 图片路径

        }else{ // 不存在

            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径

        }
            foreach ($list as $ke => $ve) {
               $list[$ke] -> image = $img.$ve -> imgurl;
            }

        // print_r($list);die;

        $request->setAttribute("arr",$list);
        $request->setAttribute("class",$res);
        
        echo json_encode(array('code' => 1));
        return View :: INPUT;

    }



    public function execute() {

       $db = DBAction::getInstance();

       $request = $this->getContext()->getRequest();
       // var_dump($request);die;

       

       



    }



    public function addgroup(){

       $db = DBAction::getInstance();

       $request = $this->getContext()->getRequest();

       unset($_GET['module']);

       unset($_GET['action']);

       $set = $_GET;

       if(isset($set['starttime'])) $set['starttime'] = strtotime($set['starttime']);

       if(isset($set['radio']) && $set['radio'] == 1){

           $set['endtime'] = strtotime('+1year');

       }else if($set['radio'] == 2 && isset($set['endtime'])){

           $set['endtime'] = strtotime($set['endtime']);

       }

       

       $request->setAttribute("set",$set);

       

    }



    public function getRequestMethods(){

        return Request :: GET;

    }



}



?>