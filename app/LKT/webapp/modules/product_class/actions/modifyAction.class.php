<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

    //递归找上级
    public function str_option($level,$sid,$str_option,$num)
    {

        if($num > 0){
            $db = DBAction::getInstance();
            $sqlc = "select * from lkt_product_class where recycle = 0 and cid = '$sid'";;
            $res = $db->select($sqlc);
            if($res){
                $sidc = $res[0]->sid; // 上级id
                $sqlcd = "select * from lkt_product_class where recycle = 0 and sid = '$sidc'";
                $resc = $db->select($sqlcd);
                $str_option[$res[0]->cid] = $resc;
                $cnum = $num-1;
                return $this->str_option($level,$sidc,$str_option,$cnum);
            }else{
               return $str_option; 
            }
        }else{
               return $str_option;
        }

    }

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        // 接收分类id
        $cid = intval($request->getParameter("cid")); // 分类id
        $uploadImg = $this->getContext()->getStorage()->read('uploadImg'); // 图片上传位置

        // 根据分类id,查询产品分类表
        $sql = "select * from lkt_product_class where recycle = 0 and cid = '$cid'";
        $r = $db->select($sql);

        if($r){
            $pname = $r[0]->pname; // 分类名称
            $sid = $r[0]->sid; // 上级id
            $img = $r[0]->img; // 分类图片
            $bg = $r[0]->bg; // 分类图片
            $sort = $r[0]->sort; // 分类排序
            $level = $r[0]->level;
        }

        $str_option = [];
        if($level >= 1){
            $cid_r = $sid;
            $str_option = $this->str_option($level,$sid,$str_option,$level);
        }else{
            $sqlcd = "select * from lkt_product_class where recycle = 0 and sid = '0'";
            $resc = $db->select($sqlcd);
            $str_option[0] = $resc;
        }

        $array = ['顶级','一级','二级','三级','四级','五级'];

        $request->setAttribute('cid', $sid ? $sid:0);
        $request->setAttribute("level",$level);
        $json = json_encode($str_option);
        $request->setAttribute("str_option",$json);
        $request->setAttribute("bg",$bg);
        $request->setAttribute('cid_r', $cid);
        $request->setAttribute('uploadImg', $uploadImg);
        $request->setAttribute('pname', isset($pname) ? $pname : '');
        // $request->setAttribute('rname', isset($rname) ? $rname : '');
        $request->setAttribute('img', isset($img) ? $img : '');
        $request->setAttribute('sort', isset($sort) ? $sort : '');
		return View :: INPUT;
	}



	public function execute(){

		$db = DBAction::getInstance();

		$request = $this->getContext()->getRequest();

		$cid = intval($request->getParameter('cid')); // 分类id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置
        $pname = addslashes(trim($request->getParameter('pname'))); // 分类名称
        $sid = $request->getParameter('val'); // 产品类别

        $level = $request->getParameter('level'); // 级别
        $image = addslashes(trim($request->getParameter('image'))); // 分类新图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 分类原图片
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        // var_dump($cid,$sid,$_POST);exit;
        if($cid == $sid){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('产品分类不能选择自己！');" .
                "</script>";
            return $this->getDefaultView();
        }

        $bg = addslashes(trim($request->getParameter('bg'))); // 展示图片
        $oldpicbg = addslashes(trim($request->getParameter('oldpicbg'))); // 分类原图片
        if($image){
            $image = preg_replace('/.*\//','',$image);
            if($image != $oldpic){
                @unlink ($uploadImg.$oldpic);
            }
        }else{
            $image = $oldpic;
        }
        if($bg){
            $bg = preg_replace('/.*\//','',$bg);
            if($bg != $oldpicbg){
                @unlink ($uploadImg.$oldpicbg);
            }
        }else{
            $bg = $oldpicbg;
        }

        if($pname == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('产品分类名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        //检查分类名是否重复
        $sql = "select cid from lkt_product_class where recycle = 0 and pname = '$pname' and cid <> '$cid'";
        $r = $db->select($sql);
        if ($r) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('产品分类 {$pname} 已经存在，请选用其他名称修改！');" .
                "</script>";
            return $this->getDefaultView();
        }


        $level = $level+1;
		//更新分类列表
		$sql = "update lkt_product_class " .
			"set pname = '$pname',img = '$image',sid = '$sid',level='$level', sort = '$sort',bg='$bg'"
			." where cid = '$cid'";
        // var_dump($sql);exit;
		$r = $db->update($sql);

		if($r < 1) {
    		echo "<script type='text/javascript'>" .
				"alert('未知原因，修改产品分类失败！');" .
				"location.href='index.php?module=product_class';</script>";
			return $this->getDefaultView();
		} else {
			header("Content-type:text/html;charset=utf-8");
			echo "<script type='text/javascript'>" .
				"alert('修改产品分类成功！');" .
				"location.href='index.php?module=product_class';</script>";
		}
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}
}
?>