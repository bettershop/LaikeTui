<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
class LoginFilter extends Filter {

	public $effect;
	public $candirect = array("Default","Login","api","api_news","app") ;

    public function execute ($filterChain) {
		if($this->effect == false){
			$filterChain->execute();
			return;
		}

		//取得第一个访问模块的名称
		// 检索当前应用程序控制器
		$controller = $this->getContext()->getController(); 
		/* 小程序过滤设置 开始 */
		$request = $this->getContext()->getRequest();
		// 检索这个模块动作堆线
		$actionstack = $controller->getActionStack();
		// 检索第一个条目
		$first = $actionstack->getFirstEntry();
		// 检索此条目的模块名
		$firstmodule = $first->getModuleName();
		// 搜索数组中是否存在指定的值,存在
		if(in_array($firstmodule,$this->candirect)){
			// 执行此链中的下一个筛选器
			$filterChain->execute();
		} else {
            if($this->getContext()->getUser()->isAuthenticated()){
				// 获取管理员id
				$name = $this->getContext()->getStorage()->read('admin_id');
                $login_time = $this->getContext()->getStorage()->read('login_time');
                $caozuo_time = $login_time + 60*5;
                $time = time();

                $db = DBAction::getInstance();

				$sql = "select * from lkt_admin where name ='$name'";
				$r = $db->select($sql);
                $role = $r[0]->role;
                $status = $r[0]->status;
                $sql = "select * from lkt_role where id = '$role'";
                $rr = $db->select($sql);
                if($rr[0]->permission != ''){
                    $permission_1 = unserialize($rr[0]->permission);
                    foreach ($permission_1 as $ke => $va){
                        $rew = explode('/',$va);
                        if($rew[0] != 1){
                            $permission[] = $rew[2] . '&action=' . $rew[3];
                        }else{
                            $permission[] = $rew[1];
                        }
                    }
                }else{
                    $permission = unserialize($rr[0]->permission);
                }
                $permission[]="AdminLogin&action=index";
                $permission[]="index&action=index";
                $permission[]="end&action=index";
                $permission[]="permission&action=index";
                $rew = $this->getContext()->getModuleName();

                $res =$request ->parameters; // 获取参数
                if($res){ // 存在
                    $module = $res['module']; // 获取module值
                    if(!empty($res['action'])){
                        $rew .= '&action=' . $res['action'];
                    }else{
                        $rew .= '&action=index';
                    }
                }
                if($r[0]->admin_type!=1 && !in_array($rew,$permission)){
                    header("Location: index.php?module=permission");
                    return;
                }else{
                    $filterChain->execute();
                }

			} else {
				$controller->redirect("index.php?module=Login");
			}
		}
		return;
	}

    public function initialize ($context, $params = null) {

		if($params['effect']){
			$this->effect = true;
		} else {
			$this->effect = false;
		}

		// initialize parent
		parent::initialize($context, $params);

		return true;

    }

}

?>