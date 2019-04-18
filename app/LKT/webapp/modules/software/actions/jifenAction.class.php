<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class jifenAction extends Action {



public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = "select * from lkt_software_jifen where id = 1";

        $r = $db->select($sql);
        if($r){
            $r = $r;
        }else{
            $r = 1;
        }
        $request->setAttribute("r",$r);

        return View :: INPUT;

    }

	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

        $jifennum = $request->getParameter('jifennum');
        $switch = $request->getParameter('switch');
        $rule = trim($request->getParameter('rule'));

        if($jifennum >= 0){
             $sql = "select * from lkt_software_jifen where id = 1";
             $r = $db->select($sql);
             if(!$r){

                $sql = "insert into lkt_software_jifen(jifennum,switch,rule) values('$jifennum','$switch','$rule')";
                $r = $db->insert($sql);
                // var_dump($sql,$r);exit;
                if($r > 0){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('添加成功！');" .
                       "location.href='index.php?module=software&action=jifen';</script>";
                    return $this->getDefaultView();
                }
             }else{
                $sql = "update lkt_software_jifen " ."set jifennum = '$jifennum', switch = '$switch',rule='$rule' where id = 1";
                $r = $db->update($sql);
                // var_dump($sql,$r);exit;
                if($r > 0){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('修改成功');" .
                       "location.href='index.php?module=software&action=jifen';</script>";
                 return $this->getDefaultView();
                }

             }


        }elseif ($jifennum < 0) {
            // print_r(3);die;
             header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('请正确输入积分值');" .

                "</script>";

            return $this->getDefaultView();

        }else{
// print_r(4);die;
            header("Content-type:text/html;charset=utf-8");

            echo "<script type='text/javascript'>" .

                "alert('积分值不能为空!');" .

                "</script>";

            return $this->getDefaultView();
        }

	}



	public function getRequestMethods(){

		return Request :: POST;

	}

   


}


?>