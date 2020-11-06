<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询模块表(模块名称、模块标识、模块描述)
        $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k =>$v){
                $id_1 = $v->id;
                // 根据上级id,查询下级信息
                $sql = "select * from lkt_core_menu where s_id = '$id_1' order by sort,id";
                $r_1 = $db->select($sql);
                if($r_1){
                    foreach ($r_1 as $ke => $va){
                        $id_2 = $va->id;
                        // 根据上级id,查询下级信息
                        $sql = "select * from lkt_core_menu where s_id = '$id_2' order by sort,id";
                        $r_2 = $db->select($sql);
                        if($r_2){
                            foreach ($r_2 as $key => $val){
                                $id_3 = $val->id;
                                // 根据上级id,查询下级信息
                                $sql = "select * from lkt_core_menu where s_id = '$id_3' order by sort,id";
                                $r_3 = $db->select($sql);
                                $val->res = $r_3;

                            }
                            $va->res = $r_2;
                        }
                    }
                    $v->res = $r_1;
                }
            }
        }
        $request->setAttribute("list",$r);

        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $name = addslashes(trim($request->getParameter('name'))); // 角色
        $permissions = $request->getParameter('permission'); // 权限
        if($name == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('角色不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select id from lkt_role where name = '$name'";
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
        // 添加一条数据
        $sql = "insert into lkt_role(name,permission,add_date)values('$name','$permissions',CURRENT_TIMESTAMP)";
        $r = $db->insert($sql);
        if($r == -1){
            $db->admin_record($admin_id,' 添加角色失败 ',1);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，添加失败！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $db->admin_record($admin_id,' 添加角色 '.$name,1);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('添加成功！');" .
                "location.href='index.php?module=role';</script>";
            return $this->getDefaultView();
        }

	    return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>