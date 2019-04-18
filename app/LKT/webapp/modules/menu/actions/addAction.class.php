<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询一级菜单，根据排序和id排列
        $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
        $r = $db->select($sql);
        $list = "";
        if($r){ // 存在
            foreach ($r as $k => $v){
                $list .= "<option value='$v->id'>$v->title</option>";
            }
        }
        $request->setAttribute("list",$list);

        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $title = addslashes(trim($request->getParameter('title'))); // 菜单名称
        $image = addslashes(trim($request->getParameter('image'))); // 图标
        $image1 = addslashes(trim($request->getParameter('image1'))); // 图标
        $url = addslashes(trim($request->getParameter('url'))); // 路径
        $type = addslashes(trim($request->getParameter('type'))); // 类型
        $sort = addslashes(trim($request->getParameter('sort'))); // 排序
        $s_id = $request->getParameter('val'); // 产品类别
        $level = $request->getParameter('level') + 1; // 级别

        if($title == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('菜单名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        if(is_numeric($sort)){
            if($sort <= 0){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('排序不能小于等于0！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('排序请填写数字！');" .
                "</script>";
            return $this->getDefaultView();
        }
        $sql = "select id from lkt_core_menu where title = '$title' and s_id = '$s_id' and recycle = 0";
        $rr = $db->select($sql);
        if($rr){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('菜单名称".$title."已存在！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($level != 1){
            if($url){
                if(count(explode('?',$url)) < 2){
                    $url = 'index.php?' . $url;
                }
                $res = substr($url,strpos($url,'?')+1);
                if(strpos($res,"&") > 0){
                    $rew = explode('&',$res);
                    $module = substr($rew[0],strpos($rew[0],'=')+1);
                    $action = substr($rew[1],strpos($rew[1],'=')+1);
                }else{
                    $module = substr($url,strpos($url,'=')+1);
                    $action = 'index';
                }
            }else{
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('路径不能为空！');" .
                    "</script>";
                return $this->getDefaultView();
            }

        }else{
            $module = '';
            $action = '';
        }

        $sql = "insert into lkt_core_menu(s_id,title,module,action,level,url,image,image1,sort,type,add_time) value('$s_id','$title','$module','$action','$level','$url','$image','$image1','$sort','$type',CURRENT_TIMESTAMP)";
        $r = $db->insert($sql);
        if($r == -1){
            $db->admin_record($admin_id,' 添加菜单失败 ',1);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，添加失败！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $db->admin_record($admin_id,' 添加菜单 '.$title,1);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('添加成功！');" .
                "location.href='index.php?module=menu';</script>";
            return $this->getDefaultView();
        }
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>