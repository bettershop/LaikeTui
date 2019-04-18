<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class setproAction extends Action {



    public function getDefaultView() {

      $db = DBAction::getInstance();

      $request = $this->getContext()->getRequest();


      if(isset($_GET['from']) && $_GET['from'] == 'pro'){
      // var_dump($_GET['from']);die;

       $str = $_COOKIE['proids'];
       
       $str = substr($str,1,-1);
         
       $this->getContext()->getStorage()->write('susu',$str);

       echo json_encode(array('code' => 1));exit;

     }else if(isset($_GET['from']) && $_GET['from'] == 'attr'){

       $str = $this->getContext()->getStorage()->read('susu');

       // $game = $this->getContext()->getStorage()->read('zhou');
       // var_dump($game);
       $arr = array();
       $idarr = explode(',', $str);
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

       

           $sql = 'select c.*,l.product_title from lkt_configure as c left join lkt_product_list as l on c.pid=l.id where c.pid in ('.$str.') order by c.pid';

           $res = $db -> select($sql);
           // var_dump($res);die;
           foreach ($res as $key => $value) {
            
            // if($value -> pid == $k){
            //   $value -> classname = $v;
            //   }
              $value -> image = $img.$value -> img;
              $arr[] = $value;
            
           }    
         
       // $arr_id = array();
       // foreach ($arr as $k => $v) {
       //    $arr_id[] = $v -> id;
       // }
       $this->getContext()->getStorage()->write('prod',$arr);
       //$request->setAttribute("set",$game);
       //var_dump($res);
       $request->setAttribute("arr",$res);

       return View :: INPUT;

    }

  }



    public function execute() {

       $db = DBAction::getInstance();

       $request = $this->getContext()->getRequest();

       $attr = $request->getParameter('attr');

       $gprice = (array)json_decode($request->getParameter('gprice'));

       $mprice = (array)json_decode($request->getParameter('mprice'));

       $prod = $this->getContext()->getStorage()->read('prod');

       $game = $this->getContext()->getStorage()->read('zhou');



       $num = mt_rand(100,100000);

       $sqlbuy = 'insert into lkt_group_buy(groupname,man_num,time_over,starttime,endtime,groupnum,productnum,status,overtype) values("'.$game['groupname'].'",'.$game['peoplenum'].',"'.$game['overtime'].'","'.$game['starttime'].'","'.$game['endtime'].'",'.$game['groupnum'].','.$game['productnum'].',"'.$num.'","'.$game['radio'].'")';

       $resbuy = $db -> insert($sqlbuy);

       

       $newprod = array();

       foreach ($prod as $k => $v) {

            $v = (array)$v;

            $ppid = $v['id'];

            //先给值　后修改　覆盖

            $v['gprice'] = $v['price'];

            $v['mprice'] = $v['price'];

            //拼团价格修改

            if(!empty($gprice)){

                foreach ($gprice as $key => $value) {

                  if($ppid == $key){

                    $v['gprice'] = $value;

                    $v['mprice'] = $value;//团长价格改变

                  }

                }

            }

            //团长价格修改

            if(!empty($mprice)){   

                foreach ($mprice as $key => $value) {

                  if($ppid == $key){

                      $v['mprice'] = $value;

                  }

                }

            }

            $newprod[($v['id'])] = (Object)$v;

       }
       
          
         //var_dump($newprod);die;
        foreach ($newprod as $k => $v) {

         $sqlpro = "insert into lkt_group_product(attr_id,group_id,product_id,group_price,member_price) values($v->id,'$num',$v->pid,'$v->gprice','$v->mprice')";

         $respro = $db -> insert($sqlpro);

             

        }



       if($resbuy > 0 && $respro > 0){

           echo json_encode(array('code' => 1));exit;

       }



       

    }





    public function getRequestMethods(){

        return Request :: POST;

    }



}

?>