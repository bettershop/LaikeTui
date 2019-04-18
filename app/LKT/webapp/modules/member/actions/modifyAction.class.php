<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $admin_name = $this->getContext()->getStorage()->read('admin_id'); // 管理员账号
        $id = $request->getParameter("id");

        // 根据id查询管理员信息
        $sql = "select * from lkt_admin where id = '$id'";
        $r = $db->select($sql);
        $name = $r[0]->name; // 管理员名称
        $admin_type = $r[0]->admin_type;
        $role = $r[0]->role; // 角色id

        // 根据角色id,查询角色信息
        $sql = "select * from lkt_role where id = '$role'";
        $r_1 = $db->select($sql);
        $r_id = $r_1[0]->id; // 角色id
        $r_name = $r_1[0]->name; // 角色名称

        $rew = "<option value='$r_id'>$r_name</option>";
        // 查询角色
        $sql = "select * from lkt_role";
        $r_2 = $db->select($sql);
        if($r_2){
            foreach ($r_2 as $r_k => $r_v){
                $rew .= "<option value='$r_v->id'>$r_v->name</option>";
            }
        }

        $request->setAttribute('id', $id);
        $request->setAttribute('name', $name );
        $request->setAttribute('admin_type', $admin_type );
        $request->setAttribute('list', $rew);

        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $id = $request->getParameter("id");
        $name = addslashes(trim($request->getParameter('name')));
        $y_password = md5(addslashes(trim($request->getParameter('y_password'))));
        $password = md5(addslashes(trim($request->getParameter('password'))));
        $role = addslashes(trim($request->getParameter('role'))); // 角色

//        $sql = "select permission from lkt_role where id = '$role'";
//        $r_role = $db->select($sql);
//        $permission = $r_role[0]->permission;

        if(addslashes(trim($request->getParameter('y_password'))) != ''){
            $sql = "select id from lkt_admin where name='$name' and password = '$y_password'";
            $rr = $db->select($sql);
            if(empty($rr)){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('密码不正确！');" .
                    "</script>";
                return $this->getDefaultView();
            }
            if($password != ''){
//                $sql = "update lkt_admin set name='$name',password = '$password',permission = '$permission',role = '$role' where id ='$id'";
                $sql = "update lkt_admin set name='$name',password = '$password',role = '$role' where id ='$id'";
            }
        }else{
            //更新数据表
//            $sql = "update lkt_admin set name='$name',permission = '$permission',role = '$role' where id ='$id'";
            $sql = "update lkt_admin set name='$name',role = '$role' where id ='$id'";
        }
        $r = $db->update($sql);
        if($r == -1) {
            $db->admin_record($admin,'修改管理员id为 '.$id.' 失败',2);

            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=member';</script>";
            return $this->getDefaultView();
        } else {
            $db->admin_record($admin,'修改管理员id为 '.$id.' 的信息',2);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=member';</script>";
        }
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>