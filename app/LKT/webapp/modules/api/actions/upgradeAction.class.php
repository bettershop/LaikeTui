<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class upgradeAction extends Action {

  public function getDefaultView() {
    $this->execute();
  }
  //更新接口
  public function execute(){
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    $m = addslashes(trim($request->getParameter('m')));
    $type = addslashes(trim($request->getParameter('type')));
    $software_version = addslashes(trim($request->getParameter('software_version')));
    $old_version = addslashes(trim($request->getParameter('version')));
    if(is_numeric($type) && is_numeric($old_version) && !empty($old_version)){
      $sql = "select * from lkt_edition where type = '$type' and software_version = '$software_version' and old_version= '$old_version'";
      $res = $db->select($sql);

      if(!empty($res)){
          $arrayName = array('status' => true, 'url'=>$res[0]->download_url,'version'=>$res[0]->version,'detail'=>$res[0]->version_abstract);
          echo json_encode($arrayName);
          exit;   
      }else{
          $arrayName = array('status' => true, 'url'=>'','version'=>$old_version);
          echo json_encode($arrayName);
          exit;    
      }

    }else{
          $arrayName = array('status' => false, 'code'=>'10023');
          echo json_encode($arrayName);
          exit;  
    }
  }

  public function getRequestMethods(){
      return Request :: POST;
  }

}
?>