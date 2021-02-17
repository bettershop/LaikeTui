<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = $request->getParameter("id");
        // 根据id查询管理员信息
        $sql = "select * from lkt_admin where id = '$id'";
        $r = $db->select($sql);
        $name = $r[0]->name; // 管理员名称
        $y_password = $r[0]->password;
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
        if ($r_2) {
            foreach ($r_2 as $r_k => $r_v) {
                $rew .= "<option value='$r_v->id'>$r_v->name</option>";
            }
        }

        $request->setAttribute('id', $id);
        $request->setAttribute('name', $name);
        $request->setAttribute('y_password', $y_password);
        $request->setAttribute('admin_type', $admin_type);
        $request->setAttribute('list', $rew);

        return View :: INPUT;
    }

    public function execute()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin = $this->getContext()->getStorage()->read('admin_id');
        $id = $request->getParameter("id");
        $name = addslashes(trim($request->getParameter('name')));
        $password = md5(addslashes(trim($request->getParameter('password'))));
        $role = addslashes(trim($request->getParameter('role'))); // 角色

        $sql = "select sid from lkt_admin where id = '$id'";
        $r_role = $db->select($sql);
        $sid = $r_role[0]->sid;
        if ($password != '') {
            if ($sid == 0) {
                $sql = "update lkt_admin set name='$name',password = '$password' where id ='$id'";
            } else {
                $sql = "update lkt_admin set name='$name',password = '$password',role = '$role' where id ='$id'";
            }
        } else {
            echo "<script type='text/javascript'>" .
                "alert('请输入新密码');" .
                "location.href='index.php?module=member';</script>";
            return $this->getDefaultView();
        }
        $r = $db->update($sql);
        if ($r == -1) {
            $db->admin_record($admin, '修改管理员id为 ' . $id . ' 失败', 2);

            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=member';</script>";
            return $this->getDefaultView();
        } else {
            $db->admin_record($admin, '修改管理员id为 ' . $id . ' 的信息', 2);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=member';</script>";
        }

    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

}

