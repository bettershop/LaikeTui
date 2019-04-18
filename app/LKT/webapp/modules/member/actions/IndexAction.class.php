<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
//        $admin_name = $this->getContext()->getStorage()->read('admin_id'); // 管理员账号

//        $sql = "select id from lkt_admin where name = '$admin_name'";
//        $r = $db->select($sql);
//        $id = $r[0]->id;
        // 查询管理员信息
//        $sql = "select * from lkt_admin where admin_type is NULL and sid = '$id'";
        $sql = "select * from lkt_admin where recycle = 0";
        $rr = $db->select($sql);
        if($rr){
            foreach ($rr as $k1 => $v1) {
                $list_3[$k1] = '';
                $sid = $v1->sid;
                $role = $v1->role;
                $sql = "select name,permission from lkt_role where id = '$role'";
                $r_role = $db->select($sql);
                $v1->role_name = $r_role[0]->name;
                $v1->permission = $r_role[0]->permission;
                $permission = unserialize($v1->permission);
                $arr_1 = [];
                $arr_2 = [];
                $arr_3 = [];
                $sql = "select name from lkt_admin where id = '$sid'";
                $r_admin_name = $db->select($sql);
                if($r_admin_name){
                    $v1->admin_name = $r_admin_name[0]->name;
                }else{
                    $v1->admin_name = '';
                }
                if($permission){
                    foreach ($permission as $a => $b){
                        $res = substr($b,0,strpos($b, '/')); // 截取第一个'/'之前的内容
                        $rew = substr($b,strpos($b,'/')+1); // 截取第一个'/'之后的内容
                        if($res == 1){
                            $arr_1[] = explode('/',$rew); // 第一级数组
                        }else if($res == 2){
                            $arr_2[] = explode('/',$rew); // 第二级数组
                        }else if($res == 3){
                            $arr_3[] = explode('/',$rew); // 第三级数组
                        }
                    }
                    foreach ($arr_1 as $k => $v){
                        $list_1 = '';
                        $list_2 = '';
                        // 查询模块表(模块名称、模块标识、模块描述)
                        $sql = "select id,title from lkt_core_menu where s_id = 0 and name = '$v[0]' order by sort,id";
                        $r_1 = $db->select($sql);
                        if($r_1){
                            $list_1 .= $r_1[0]->title . '('; // 一级菜单名称拼接
                            $id_1 = $r_1[0]->id;
                            foreach ($arr_2 as $ke => $va){
                                // 根据上级id、权限信息，查询菜单名称
                                $sql = "select id,title from lkt_core_menu where s_id = '$id_1' and name = '$va[0]' and module = '$va[1]' and action = '$va[2]' order by sort,id";
                                $r_2 = $db->select($sql);
                                if($r_2){
                                    $list_2 .= $r_2[0]->title . ','; // 二级菜单名称拼接
                                }
                            }
                            $list_1 .= rtrim($list_2,','); // 一级菜单名称拼接
                            $list_1 .= ')'; // 一级菜单名称拼接
                            $list_3[$k1] .= $list_1 . ',';
                        }
                    }
                }
                $v1->permission = rtrim($list_3[$k1], ',');
            }
        }
        $request->setAttribute("list",$rr);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>