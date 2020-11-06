<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = $request->getParameter("id"); // 角色id

        // 根据角色id查询角色信息
        $sql = "select * from lkt_role where id = '$id'";
        $rr = $db->select($sql);
        $name = $rr[0]->name;
        $permission = unserialize($rr[0]->permission);
        $arr_1 = [];
        $arr_2 = [];
        $arr_3 = [];
        if($permission){
            foreach ($permission as $a => $b){
                $res = substr($b,0,strpos($b, '/')); // 获取第一个斜线之前的内容
                $rew = substr($b,strpos($b,'/')+1); // 获取第一个斜线后面的内容
                if($res == 1){
                    $arr_1[] = explode('/',$rew); // 根据斜线转数组(第一级)
                }else if($res == 2){
                    $arr_2[] = explode('/',$rew); // 根据斜线转数组(第二级)
                }else if($res == 3){
                    $arr_3[] = explode('/',$rew); // 根据斜线转数组(第三级)
                }
            }
        }

        // 查询菜单表(模块名称、模块标识、模块描述)
        $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k =>$v){
                $r[$k]->status = 0; // 定义没选中
                $id_1 = $v->id;
                $name_1 = $v->name;
                if($arr_1 != ''){
                    foreach ($arr_1 as $k1 => $v1){
                        if($name_1 == $v1[0]){
                            $r[$k]->status = 1; // 选中
                        }
                    }
                }
                $sql = "select * from lkt_core_menu where s_id = '$id_1' order by sort,id";
                $r_1 = $db->select($sql);
                if($r_1){
                    foreach ($r_1 as $ke => $va){
                        $r_1[$ke]->status = 0; // 定义没选中
                        $id_2 = $va->id;
                        $name_2 = $va->name;
                        $module_2 = $va->module;
                        $action_2 = $va->action;
                        foreach ($arr_2 as $k2 => $v2){
                            if($name_2 == $v2[0] && $module_2 == $v2[1] && $action_2 == $v2[2]){
                                $r_1[$ke]->status = 1; // 选中
                            }
                        }
                        $sql = "select * from lkt_core_menu where s_id = '$id_2' order by sort,id";
                        $r_2 = $db->select($sql);
                        if($r_2){
                            foreach ($r_2 as $key => $val){
                                $r_2[$key]->status = 0; // 定义没选中
                                $id_3 = $val->id;
                                $name_3 = $val->name;
                                $module_3 = $val->module;
                                $action_3 = $val->action;
                                foreach ($arr_3 as $k3 => $v3){
                                    if($name_3 == $v3[0] && $module_3 == $v3[1] && $action_3 == $v3[2]){
                                        $r_2[$key]->status = 1; // 选中
                                    }
                                }
                                $sql = "select * from lkt_core_menu where s_id = '$id_3' order by sort,id";
                                $r_3 = $db->select($sql);
                                if($r_3){
                                    foreach ($r_3 as $key1 => $val1){
                                        $r_3[$key1]->status = 0; // 定义没选中
                                        $id_4 = $val1->id;
                                        $name_4 = $val1->name;
                                        $module_4 = $val1->module;
                                        $action_4 = $val1->action;
                                        foreach ($arr_3 as $k4 => $v4){
                                            if($name_4 == $v4[0] && $module_4 == $v4[1] && $action_4 == $v4[2]){
                                                $r_3[$key1]->status = 1; // 选中
                                            }
                                        }
                                    }
                                    $val->res = $r_3;
                                }
                            }
                            $va->res = $r_2;
                        }
                    }
                    $v->res = $r_1;
                }
            }
        }
        $request->setAttribute('id', $id);
        $request->setAttribute('name', $name );
        $request->setAttribute('permission', $permission );
        $request->setAttribute("list",$r);

        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $id = $request->getParameter("id");
        $name = addslashes(trim($request->getParameter('name')));
        $permissions = $request->getParameter('permission'); // 权限
        if($name == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('角色不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select id from lkt_role where id != '$id' and name = '$name'";
            $r = $db->select($sql);
            if($r){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('角色已经存在！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        /* 避免选择中间一级，最上面一级没被选中 */
        $res = [];
        foreach ($permissions as $a => $b){
                $rew = substr($b,strpos($b,'/')+1); // 获取第一个/后面的内容
                $res[] = substr($rew,0,strpos($rew, '/')); // 获取第一个/ 前面的内容(得到name值)
        }

        $list = [];
        $list1 = array_unique($res); // 去重复
        foreach ($list1 as $c => $d){ // 循环去空值
            if($d != ''){
                $list[] = $d;
            }
        }
        foreach ($list as $e => $f){
            $permissions[] = '1/' . $f; // 拼接最上面一级权限
        }
        $permissions = array_unique($permissions); // 去重复
        $permissions = serialize($permissions); // 转序列化
        //更新数据表
        $sql = "update lkt_role set name='$name',permission='$permissions' where id ='$id'";
        $r = $db->update($sql);
        if($r == -1) {
            $db->admin_record($admin_id,' 修改角色id为 '.$id.' 失败 ',2);

            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=role';</script>";
            return $this->getDefaultView();
        } else {
            $db->admin_record($admin_id,' 修改角色id为 '.$id.' 的信息',2);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=role';</script>";
        }
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>