<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        
        $sql = "select id,name from lkt_software where type = 0";
        $r = $db->select($sql);
        if($r){
            $rew = '<option value="0" >全部</option>';
            $arr = json_decode(json_encode($r),true);
            $new_arr = array();
            foreach($arr as $k => $v){
                if(array_key_exists($v['name'],$new_arr)){
                    $new_arr[$v['name']] = $new_arr[$v['name']].','.$v['id'];
                }else{
                    $new_arr[$v['name']] = $v['id'];
                }
            }
            foreach ($new_arr as $key => $value) {
                $arr_list['id'] = $value;
                $arr_list['name'] = $key;
                $rew .= "<option  value='".$arr_list['id']."'>".$arr_list['name']."</option>";
            } 
        }
        $request->setAttribute('list', $rew);
		return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $name = addslashes(trim($request->getParameter('name'))); // 首页插件名称
        $subtitle_name = addslashes(trim($request->getParameter('subtitle_name'))); // 个人中心插件名称
        $type = addslashes(trim($request->getParameter('type'))); // 软件类型
        $software_id = addslashes(trim($request->getParameter('software_id'))); // 软件id
        $image = addslashes(trim($request->getParameter('image'))); // 首页插件图片
        $subtitle_image = addslashes(trim($request->getParameter('subtitle_image'))); // 个人中心插件图片
        $url = addslashes(trim($request->getParameter('url'))); // 首页链接
        $subtitle_url = addslashes(trim($request->getParameter('subtitle_url'))); // 个人中心链接
        $sort = trim($request->getParameter('sort')); // 排序

        if($image){
            $image = preg_replace('/.*\//','',$image);
        }else{
            echo "<script type='text/javascript'>" .
                "alert('首页插件图标不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($subtitle_image){
            $subtitle_image = preg_replace('/.*\//','',$subtitle_image);
        }else{
            $subtitle_image = $image;
        }
        if($software_id){
            $software_id_1=trim($software_id,','); // 移除两侧的逗号
            $software_id_2=explode(',',$software_id_1); // 字符串打散为数组
        }else{
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('请选择软件!');" .
                "</script>";
            return $this->getDefaultView();
        }

        if($name){
            foreach ($software_id_2 as $key => $value) {
                // 根据插件名称查询插件表
                $sql = "select * from lkt_plug_ins where name = '$name' and type = '$type' and software_id = '$value'";
                $r = $db->select($sql);
                if($r){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('首页插件".$name."已存在!');" .
                        "</script>";
                    return $this->getDefaultView();
                }else{
                    $name = $name;
                }
            }
        }else{
            header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('首页插件名称不能为空!');" .
                    "</script>";
                return $this->getDefaultView();
        }
        
        if($subtitle_name){
            foreach ($software_id_2 as $key => $value) {
                // 根据插件名称查询插件表
                $sql = "select * from lkt_plug_ins where subtitle_name = '$subtitle_name' and type = '$type' and software_id = '$value' ";
                $r = $db->select($sql);
                if($r){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('个人中心插件".$subtitle_name."已存在!');" .
                        "</script>";
                    return $this->getDefaultView();
                }else{
                    $subtitle_name = $subtitle_name;
                }
            }
        }else{
            $subtitle_name = $name;
        }
        if(empty($url)){
            echo "<script type='text/javascript'>" .
                "alert('首页链接不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if(empty($subtitle_url)){
            $subtitle_url = $url;
        }
        if(floor($sort) == $sort){
            // 添加插件
            $sql = "insert into lkt_plug_ins(name,software_id,subtitle_name,type,image,subtitle_image,url,subtitle_url,add_time,sort,status) " .
                "values('$name','$software_id','$subtitle_name','$type','$image','$subtitle_image','$url','$subtitle_url',CURRENT_TIMESTAMP,'$sort',0)";
            $r = $db->insert($sql);
            if($r == -1){
                $db->admin_record($admin_id,' 添加插件失败 ',1);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，添加失败！');" .
                    "</script>";
                return $this->getDefaultView();
            }else{
                $db->admin_record($admin_id,' 添加插件 '.$name,1);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('添加成功！');" .
                    "location.href='index.php?module=plug_ins';</script>";
                return $this->getDefaultView();
            }
        }else{
            echo "<script type='text/javascript'>" .
                "alert('排序不为整数！');" .
                "</script>";
            return $this->getDefaultView();
        }
        
	    return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>