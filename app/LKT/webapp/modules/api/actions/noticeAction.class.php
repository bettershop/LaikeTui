<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once('BaseAction.class.php');

class noticeAction extends BaseAction {

    public function index(){
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id')));
        if($id){
           $con= 'id = '.$id;
        }else{
            $con= '1=1 order by time desc';
        }
        
        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];
        $uploadImg_domain = $appConfig['uploadImgUrl'];

        $sql = "select * from lkt_set_notice where $con";
        $r = lkt_gets($sql);
        if(!empty($r)){
            foreach ($r as $k => $v) {
                if($v->img_url == ''){
                    $v->img_url = 'nopic.jpg';
                }else{
                     $v->img_url = $img.$v->img_url;
                }

                $newa = substr($uploadImg_domain,0,strrpos($uploadImg_domain,'/'));
                if($newa == 'http:/' || $newa == 'https:/' ){
                    $newa = $uploadImg_domain;
                }
                $new_content = preg_replace('/(<img.+?src=")(.*?)/',"$1$newa$2", $v->detail);
                $r[$k]->detail= $new_content;

            }
        }
       
        echo json_encode(array('notice'=>$r));
        exit();
    }
  
}
?>