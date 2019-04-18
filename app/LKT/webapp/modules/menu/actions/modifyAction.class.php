<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = $request->getParameter("id");

        // 根据id，查询菜单
        $sql = "select * from lkt_core_menu where id = '$id'";
        $r_1 = $db->select($sql);
        if($r_1){
            $space = "---";
            $s_id = $r_1[0]->s_id; // 上级id
            $title = $r_1[0]->title; // 菜单名称
//            $name = $r_1[0]->name; // 菜单标识
            $image = $r_1[0]->image; // 图片
            $image1 = $r_1[0]->image1; // 图片
            $url = $r_1[0]->url; // 路径
            $sort = $r_1[0]->sort; // 排序
            $type = $r_1[0]->type; // 排序
            $level = $r_1[0]->level; // 等级

            if($level == 1){
                $list = "";
                $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
                $r = $db->select($sql);
                if($r){ // 存在
                    foreach ($r as $k => $v){
                        $list .= "<option value='$v->id'>$v->title</option>";
                    }
                }
                $cid = 0;
            }else if($level == 2){
                /** 二级 **/
                $list1 = "";

                $sql = "select * from lkt_core_menu where s_id = '$s_id'";
                $rr = $db->select($sql);
                if($rr){
                    foreach ($rr as $k => $v){
                        $list1 .= "<option value='$v->id'>$v->title</option>";
                    }
                }
                /** 一级 **/
                $sql = "select * from lkt_core_menu where id = '$s_id'";
                $r1 = $db->select($sql);
                if($r1){
                    $id1 = $r1[0]->id; // id
                    $title1 = $r1[0]->title; // 菜单名称
                    $list = "<option selected='true' value='$id1'>$title1</option>";
                    $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
                    $r = $db->select($sql);
                    if($r){ // 存在
                        foreach ($r as $k => $v){
                            $list .= "<option value='$v->id'>$v->title</option>";
                        }
                    }
                }
                $cid = $s_id;

                $request->setAttribute("list1",$list1);
            }else if($level == 3){
                /** 三级 **/
                $list2 = "";
                $sql = "select * from lkt_core_menu where s_id = '$s_id'";
                $rrr = $db->select($sql);
                if($rrr){
                    foreach ($rrr as $k => $v){
                        $list2 .= "<option value='$v->id'>$v->title</option>";
                    }
                }
                /** 二级 **/
                $sql = "select * from lkt_core_menu where id = '$s_id'";
                $r2 = $db->select($sql);
                if($r2){
                    $id2 = $r2[0]->id; // id
                    $s_id2 = $r2[0]->s_id; // 上级id
                    $title2 = $r2[0]->title; // 菜单名称
                    $list1 = "<option selected='true' value='$id2'>$title2</option>";
                    $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
                    $r = $db->select($sql);
                    if($r){ // 存在
                        foreach ($r as $k => $v){
                            $list1 .= "<option value='$v->id'>$v->title</option>";
                        }
                    }
                }
                /** 一级 **/
                $sql = "select * from lkt_core_menu where id = '$s_id2'";
                $r1 = $db->select($sql);
                if($r1){
                    $id1 = $r1[0]->id; // id
                    $title1 = $r1[0]->title; // 菜单名称
                    $list = "<option selected='true' value='$id1'>$title1</option>";
                    $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
                    $r = $db->select($sql);
                    if($r){ // 存在
                        foreach ($r as $k => $v){
                            $list .= "<option value='$v->id'>$v->title</option>";
                        }
                    }
                }
                $cid = $s_id;

                $request->setAttribute("list1",$list1);
                $request->setAttribute("list2",$list2);
            }else{
                /** 三级 **/
                $sql = "select * from lkt_core_menu where id = '$s_id'";
                $rrr = $db->select($sql);
                if($rrr){
                    $id3 = $rrr[0]->id; // id
                    $s_id3 = $rrr[0]->s_id; // 上级id
                    $title3 = $rrr[0]->title; // 菜单名称
                    $list2 = "<option selected='true' value='$id3'>$title3</option>";
                    $sql = "select * from lkt_core_menu where id = '$s_id3'";
                    $rrr1 = $db->select($sql);
                    foreach ($rrr1 as $k => $v){
                        $list2 .= "<option value='$v->id'>$v->title</option>";
                    }
                }
                /** 二级 **/
                $sql = "select * from lkt_core_menu where id = '$s_id3'";
                $r2 = $db->select($sql);
                if($r2){
                    $id2 = $r2[0]->id; // id
                    $s_id2 = $r2[0]->s_id; // 上级id
                    $title2 = $r2[0]->title; // 菜单名称
                    $list1 = "<option selected='true' value='$id2'>$title2</option>";
                    $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
                    $r = $db->select($sql);
                    if($r){ // 存在
                        foreach ($r as $k => $v){
                            $list1 .= "<option value='$v->id'>$v->title</option>";
                        }
                    }
                }
                /** 一级 **/
                $sql = "select * from lkt_core_menu where id = '$s_id2'";
                $r1 = $db->select($sql);
                if($r1){
                    $id1 = $r1[0]->id; // id
                    $title1 = $r1[0]->title; // 菜单名称
                    $list = "<option selected='true' value='$id1'>$title1</option>";
                    $sql = "select * from lkt_core_menu where s_id = 0 order by sort,id";
                    $r = $db->select($sql);
                    if($r){ // 存在
                        foreach ($r as $k => $v){
                            $list .= "<option value='$v->id'>$v->title</option>";
                        }
                    }
                }
                $cid = $s_id;

                $request->setAttribute("list1",$list1);
                $request->setAttribute("list2",$list2);
            }
        }

        $sql = "select id from lkt_core_menu where s_id = '$id'";
        $r_1 = $db->select($sql);
        if($r_1){
            $status = 1;
        }else{
            $status = 0;
        }
        $request->setAttribute('id', $id);
        $request->setAttribute('title', $title );
//        $request->setAttribute('name', $name );
        $request->setAttribute('url', $url );
        $request->setAttribute('sort', $sort );
        $request->setAttribute('type', $type );
        $request->setAttribute('level', $level );
        $request->setAttribute('image', $image );
        $request->setAttribute('image1', $image1 );
//        $request->setAttribute('type', $type );
        $request->setAttribute('cid', $cid);
        $request->setAttribute("level",$level-1);
        $request->setAttribute("list",$list);
        $request->setAttribute("status",$status);


        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $id = $request->getParameter("id");
        $title = addslashes(trim($request->getParameter('title'))); // 菜单名称
//        $s_id = addslashes(trim($request->getParameter('s_id'))); // 上级id
        $image = addslashes(trim($request->getParameter('image'))); // 图标
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 产品图片
        $image1 = addslashes(trim($request->getParameter('image1'))); // 图标
        $oldpic1 = addslashes(trim($request->getParameter('oldpic1'))); // 产品图片

        $url = addslashes(trim($request->getParameter('url'))); // 路径
        $type = addslashes(trim($request->getParameter('type'))); // 类型
        $sort = addslashes(trim($request->getParameter('sort'))); // 排序

        $s_id = $request->getParameter('val'); // 产品类别
        $level = $request->getParameter('level') + 1; // 级别

//        if(!empty($s_id)){
//            // 根据传过来的菜单id，查询菜单信息
//            $sql = "select * from lkt_core_menu where id = '$s_id'";
//            $r_1 = $db->select($sql);
//            $name = $r_1[0]->name; // 菜单标识
//        }else{
//            $name = addslashes(trim($request->getParameter('name'))); // 菜单标识
//        }
        if($title == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('菜单名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select id from lkt_core_menu where id != '$id' and title = '$title' and s_id = '$s_id'";
            $r_1 = $db->select($sql);
            if($r_1){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('菜单名称".$title."已存在！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
//        if($name == ''){
//            header("Content-type:text/html;charset=utf-8");
//            echo "<script type='text/javascript'>" .
//                "alert('菜单标识不能为空！');" .
//                "</script>";
//            return $this->getDefaultView();
//        }else{
//            $sql = "select id,name,level from lkt_core_menu where id = '$id'";
//            $r_3 = $db->select($sql);
//            $yid = $r_3[0]->id;
//            $yname = $r_3[0]->name;
//            $ylevel = $r_3[0]->level;
//            if($name != $yname){
//                $sql = "update lkt_core_menu set name = '$name' where name = '$yname' and id = '$id' ";
//                $db->update($sql);
//                $sql = "update lkt_core_menu set name = '$name' where name = '$yname' and s_id = '$id' ";
//                $db->update($sql);
//
//                $num = $level - $ylevel;
//                $sql = "update lkt_core_menu set level = level+'$num' where s_id = '$yid'";
//                $db->update($sql);
//            }
//        }

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
            $url = '';
            $module = '';
            $action = '';
        }
        if($image){
            $image = $image;
            if($image != $oldpic){
                @unlink ($oldpic);
            }
        }else{
            $image = $oldpic;
        }
        if($image1){
            $image1 = $image1;
            if($image1 != $oldpic1){
                @unlink ($oldpic1);
            }
        }else{
            $image1 = $oldpic1;
        }
        $sql = "update lkt_core_menu set title = '$title',module = '$module',action = '$action',s_id = '$s_id',level = '$level',image = '$image',image1 = '$image1',url = '$url',type = '$type',sort = '$sort' where id = '$id'";
        $r = $db->update($sql);
        if($r == -1) {
            $db->admin_record($admin_id,' 修改菜单id为 '.$id.' 失败 ',2);

            echo "<script type='text/javascript'>" .
                "alert('未知原因，修改失败！');" .
                "location.href='index.php?module=menu';</script>";
            return $this->getDefaultView();
        } else {
            $db->admin_record($admin_id,' 修改菜单id为 '.$id.' 的信息',2);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('修改成功！');" .
                "location.href='index.php?module=menu';</script>";
        }
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>