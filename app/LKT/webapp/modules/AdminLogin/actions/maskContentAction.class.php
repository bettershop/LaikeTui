<?php
require_once(MO_LIB_DIR . '/version.php');
class maskContentAction extends Action {

   
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $admin_name = $this->getContext()->getStorage()->read('admin_id'); // 管理员账号
        $nickname = addslashes(trim($request->getParameter('nickname')));//昵称
        $birthday = addslashes(trim($request->getParameter('birthday')));//生日
        $sex = addslashes(trim($request->getParameter('sex')));//性别（1.男 2. 女）
        $tel = addslashes(trim($request->getParameter('tel')));//手机号码

        // 根据id查询管理员信息
        $sql = "select * from lkt_admin where name = '$admin_name'";
        $r = $db->select($sql);
            if(!$r){
                $res = array('status' => '1','info'=>'没有该用户');
                echo json_encode($res);
                exit();
            }
        if(!empty($nickname) || !empty($birthday)  || !empty($sex)|| !empty($tel)){
             $sql01 = "update lkt_admin set nickname = '$nickname',birthday = '$birthday',sex = '$sex',tel = '$tel' where name ='$admin_name'";

             $r01 = $db->update($sql01);
             if($r01 == -1) {
                  $db->admin_record($admin_name,'修改管理员昵称为 '.$nickname.'，生日为'.$birthday.'，性别'.$sex.'，手机号码为'.$tel.' 失败',2);
                    $res = array('status' => '2','info'=>'未知原因，修改失败!','re'=>$r);
                    echo json_encode($res);
                    exit();
                } else {
                      $db->admin_record($admin_name,'修改管理员昵称为 '.$nickname.'，生日为'.$birthday.'，性别'.$sex.'，手机号码为'.$tel.'  成功',2);
                    $res = array('status' => '3','info'=>'修改成功！','re'=>$r);
                    echo json_encode($res);
                    exit();
                }
        }else{
            $res = array('re'=>$r);
                    echo json_encode($res);
                    exit();
        }

        return View :: INPUT;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
    
        return;
    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>