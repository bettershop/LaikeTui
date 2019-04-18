<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_name = $this->getContext()->getStorage()->read('admin_id'); // 管理员账号
        $rew = "<option value='0'>请选择</option>";
        // 查询角色
        $sql = "select * from lkt_role";
        $r_1 = $db->select($sql);

        if($r_1){
            foreach ($r_1 as $k => $v){
                $rew .= "<option value='$v->id'>$v->name</option>";
            }
        }
        $request->setAttribute("list",$rew);
        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        // 接收数据
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $name = addslashes(trim($request->getParameter('name'))); // 管理员账号
        $password = MD5(addslashes(trim($request->getParameter('password')))); // 密码
        $password1 = MD5(addslashes(trim($request->getParameter('password1')))); // 确认密码
        $role = addslashes(trim($request->getParameter('role'))); // 角色

        $sql = "select id from lkt_admin where name = '$admin_id'";
        $r_1 = $db->select($sql);
        $sid = $r_1[0]->id;
        //检查是否重复
        $s = "select * from lkt_admin where name = '$name'";
        $sr = $db->insert($s);
        if($sr && count($sr) > 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('用户名已经存在！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            if($password == $password1){
                if($role == 0){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('请选择角色！');" .
                        "</script>";
                    return $this->getDefaultView();
                }else{
//                    $sql = "select permission from lkt_role where id = '$role'";
//                    $r_role = $db->select($sql);
//                    $permission = $r_role[0]->permission;
//                    $sql = "insert into lkt_admin(sid,name,password,permission,role,status,add_date,recycle) values('$sid','$name','$password','$permission','$role',2,CURRENT_TIMESTAMP,0)";
                    $sql = "insert into lkt_admin(sid,name,password,role,status,add_date,recycle) values('$sid','$name','$password','$role',2,CURRENT_TIMESTAMP,0)";
                    $r = $db->insert($sql);
                    if($r == -1){
                        $db->admin_record($admin_id,'添加管理员失败',1);

                        header("Content-type:text/html;charset=utf-8");
                        echo "<script type='text/javascript'>" .
                            "alert('未知原因，添加失败！');" .
                            "</script>";
                        return $this->getDefaultView();
                    }else{
                        $db->admin_record($admin_id,'添加管理员'.$name,1);

                        header("Content-type:text/html;charset=utf-8");
                        echo "<script type='text/javascript'>" .
                            "alert('添加成功！');" .
                            "location.href='index.php?module=member';</script>";
                        return $this->getDefaultView();
                    }
                }
            }else{
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('确认密码不正确！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
	    return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>