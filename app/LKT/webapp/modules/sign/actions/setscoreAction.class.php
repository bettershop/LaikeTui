<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class setscoreAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = 'select lever,ordernum,scorenum from lkt_setscore order by lever';
        $res = $db -> select($sql);
        $bili = '';
        $str = '[';
        //$res = array_reverse($res);
        foreach ($res as $k => $v) {
        	if($v -> lever == -1){
               $bili = $v -> ordernum;
               unset($res[$k]);
        	}else{
        	  $str .= '{"lever":'.$v -> lever.',"ordernum":'.$v -> ordernum.',"scorenum":'.$v -> scorenum.'},';
        	}
        }
        $str = substr($str,0,-1).']';
           
        $request->setAttribute("bili",$bili);
        $request->setAttribute("res",$str);
        return View :: INPUT;
	}



	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $bili = addslashes(trim($request->getParameter('bili')));
        $data = json_decode($request->getParameter('data'));
        $delsql = "delete from lkt_setscore where id>0";
        $del = $db -> delete($delsql);
      
        $istsql = "insert into lkt_setscore(lever,ordernum) values(-1,$bili)";
        $ist = $db -> insert($istsql);
        foreach ($data as $k => $v) {
        	$arr = explode('~', $v);
        	$buydemo = "insert into lkt_setscore(lever,ordernum,scorenum) values($arr[1],$k,$arr[0])";
        	$buy = $db -> insert($buydemo);  
        }
        if($ist > 0 && $buy > 0){
        	echo json_encode(array('code'=>1));exit;
        }

	}



	public function getRequestMethods(){

		return Request :: POST;

	}



}



?>