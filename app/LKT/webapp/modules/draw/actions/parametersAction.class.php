<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class parametersAction extends Action {



	public function getDefaultView() {

        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

      



        

        return View :: INPUT;

	}



	public function execute(){

		$db = DBAction::getInstance();

       $request = $this->getContext()->getRequest();

       $parameters = $request->getParameter('parameters');//活动名称

       $id = $request->getParameter('id11');//ID

       $admin_name = $this->getContext()->getStorage()->read('admin_id');

       if(!empty($admin_name)){

         $sql01 = "select id from lkt_admin where name = '$admin_name'   ";

         $re = $db->select($sql01); 

         $re01 =$re[0]->id;

       }

       $admin_id = $re01?$re01:0;

// print_r($parameters);die;

       if(!empty($id)){

          $sql = 'update lkt_lottery_parameters set parameters="'.$parameters.'",user_id="'.$admin_id.'"where id="'.$id.'"';

          // print_r($sql);die;

            $r = $db->update($sql);

            if($r ==1){

               echo 1;exit();

            }

        }else{

          $sql = "insert into lkt_lottery_parameters(parameters,user_id) " ."values('$parameters','$admin_id')";

// print_r($sql);die;

        $r = $db->insert($sql);

          if($r ==1){

             echo 2;exit();

          }

        }

        return View :: INPUT;

      }

        

	



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>