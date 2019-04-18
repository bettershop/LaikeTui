<?php
require_once(MO_LIB_DIR . '/version.php');
class changePasswordAction extends Action {

   
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $admin_name = $this->getContext()->getStorage()->read('admin_id'); // 管理员账号
        $y_password = md5(addslashes(trim($request->getParameter('oldPW'))));//原密码
        $password = md5(addslashes(trim($request->getParameter('NewPW'))));//新密码

        // 根据id查询管理员信息
        $sql = "select * from lkt_admin where name = '$admin_name'and password = '$y_password'";
        $r = $db->select($sql);
            if(!$r){
                $res = array('status' => '1','info'=>'密码不正确！');
                echo json_encode($res);
                exit();
            }
        if(!empty($password) && $password != $y_password){
             $sql01 = "update lkt_admin set password = '$password' where name ='$admin_name'";

             $r01 = $db->update($sql01);
             if($r01 == -1) {
                $db->admin_record($admin_name,'修改管理员密码为 '.$password.' 失败',2);

                    $res = array('status' => '2','info'=>'未知原因，修改失败!');
                    echo json_encode($res);
                    exit();
                } else {

                    $db->admin_record($admin_name,'修改管理员密码为 '.$password.' 成功',2);
                    $res = array('status' => '3','info'=>'修改成功！');
                    echo json_encode($res);
                    exit();
                }
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