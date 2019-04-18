<?php
require_once(MO_LIB_DIR . '/version.php');
class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_name = $this->getContext()->getStorage()->read('admin_id');
        $login_time = $this->getContext()->getStorage()->read('login_time');

        $type = intval($request->getParameter("type"));
        if($type == ""){
            $type = 0;
        }else{
            $type = $type;
        }

        $sql = "select * from lkt_admin where name = '$admin_name'";
        $r = $db->select($sql);
        if($r[0]->sid == 0){
            $sql = "select * from lkt_core_menu where type = '$type' and recycle = 0 and s_id = 0 order by sort,id";
            $r_1 = $db->select($sql);
            if($r_1){
                foreach ($r_1 as $k => $v){
                    $id_1 = $v->id;
                    $sql = "select * from lkt_core_menu where s_id = '$id_1' and recycle = 0 order by sort,id";
                    $r_2 = $db->select($sql);
                    if($r_2){
                        foreach ($r_2 as $ke => $va){
                            $id_2 = $va->id;
                            $sql = "select * from lkt_core_menu where s_id = '$id_2' and recycle = 0 order by sort,id";
                            $r_3 = $db->select($sql);
                            if($r_3){
                                $va->res = $r_3;
                            }
                        }
                        $v->res = $r_2;
                    }
                }
                $list = $r_1;
            }
        }else{
            $role = $r[0]->role;
            $sql = "select * from lkt_role where id = '$role'";
            $rr = $db->select($sql);
            if($rr){
                if($rr[0]->permission != ''){
                    $permission = unserialize($rr[0]->permission);
                    $arr_1 = [];
                    $arr_2 = [];
                    $arr_3 = [];
                    $list = [];
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
                        // 查询模块表(模块名称、模块标识、模块描述)
                        $sql = "select * from lkt_core_menu where s_id = 0 and recycle = 0 and name = '$v[0]' order by sort,id";
                        $r_0 = $db->select($sql);
                        if($r_0){
                            $id_1 = $r_0[0]->id;
                            foreach ($arr_2 as $ke => $va){

                                $sql = "select * from lkt_core_menu where s_id = '$id_1' and recycle = 0 and name = '$va[0]' and module = '$va[1]' and action = '$va[2]' order by sort,id";
                                $r_1 = $db->select($sql);
                                if($r_1){
                                    $id_2 = $r_1[0]->id;
                                    foreach ($arr_3 as $key => $val){
                                        $sql = "select * from lkt_core_menu where s_id = '$id_2' and recycle = 0 and name = '$val[0]' and module = '$val[1]' and action = '$val[2]' order by sort,id";
                                        $r_2 = $db->select($sql);
                                        if($r_2){
                                            $r_1[0]->res[] = $r_2[0];

                                        }
                                    }
                                    $r_0[0]->res[] = $r_1[0];
                                }
                            }
                            $list[] = $r_0[0];
                        }
                    }
                }
            }
        }
        $sql = "select domain from lkt_config where id = 1";
        $rr = $db->select($sql);
        $domain = $rr[0]->domain;
        $version = LKT_VERSION;
        $request->setAttribute('version',$version);
        $request->setAttribute('list',$list);
        $request->setAttribute('admin_id',$admin_name);
        $request->setAttribute('type',$type);
        $request->setAttribute('login_time',$login_time);
        $request->setAttribute('domain',$domain);

//        $this->getContext()->getStorage()->write('cart_type',$cart_type);

        return View :: INPUT;
    }

    //校验登入时间和异地登入
    public function execute(){
                $name = $this->getContext()->getStorage()->read('admin_id');
                $login_time = $this->getContext()->getStorage()->read('login_time');
                $caozuo_time = $login_time + 10 * 60;
                $time = time();
                $db = DBAction::getInstance();
                $code = 0;

        if($time > $caozuo_time){
                    $code = 1;
                }

                $sql = "select token from lkt_admin where name ='$name'";
                $r = $db->select($sql);
                $admin_token = $r[0]->token;
                if($admin_token != session_id()){
                    $code = 2;
                }
                echo json_encode(array('code' =>$code)); 
            }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>