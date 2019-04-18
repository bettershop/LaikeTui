<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

	public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收分类id
        $brand_id = intval($request->getParameter("cid")); // 品牌id
        $uploadImg = $this->getContext()->getStorage()->read('uploadImg');

        // 根据分类id,查询产品分类表
        $sql = "select * from lkt_brand_class where brand_id = '$brand_id'";
        $r = $db->select($sql);
        if($r){
            $brand_name = $r[0]->brand_name; // 品牌名称
            $brand_y_name = $r[0]->brand_y_name; // 品牌名称
            $brand_pic = $r[0]->brand_pic; // 品牌图片
            $producer = $r[0]->producer; // 产地
            $remarks = $r[0]->remarks; // 备注
            $sort = $r[0]->sort; // 排序
        }
        $request->setAttribute("brand_id",$brand_id);
        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("brand_name",$brand_name);
        $request->setAttribute("brand_y_name",$brand_y_name);
        $request->setAttribute('brand_pic', $brand_pic);
        $request->setAttribute('producer', $producer);
        $request->setAttribute('remarks', $remarks);
        $request->setAttribute('sort', $sort);


		return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $brand_id = intval($request->getParameter('cid')); // 品牌id
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片上传位置
        $brand_name = addslashes(trim($request->getParameter('pname'))); // 品牌名称
        $brand_y_pname = addslashes(trim($request->getParameter('y_pname'))); // 品牌名称
        $image = addslashes(trim($request->getParameter('image'))); // 品牌新图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 品牌原图片
        $producer = addslashes(trim($request->getParameter('producer'))); // 产地
        $sort = addslashes(trim($request->getParameter('sort'))); // 排序
        $remarks = addslashes(trim($request->getParameter('remarks'))); // 备注
        if($image){
            $image = preg_replace('/.*\//','',$image);
            if($image != $oldpic){
                @unlink ($uploadImg.$oldpic);
            }
        }else{
            $image = $oldpic;
        }
        if($brand_name == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('中文名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        //检查分类名是否重复
        $sql = "select brand_name from lkt_brand_class where brand_name = '$brand_name' and brand_id != '$brand_id' ";
        $r = $db->select($sql);
        if ($r) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('商品品牌中文名称{$brand_name} 已经存在，请选用其他名称修改！');" .
                "</script>";
            return $this->getDefaultView();
        }
		//更新分类列表
		$sql = "update lkt_brand_class " .
			"set brand_name = '$brand_name',brand_y_name = '$brand_y_pname',brand_pic = '$image',producer = '$producer',remarks = '$remarks',sort = '$sort'"
			." where brand_id = '$brand_id'";
		$r = $db->update($sql);

		if($r == -1) {
            $db->admin_record($admin_id,' 修改商品品牌id为 '.$brand_id.' 失败',2);

            echo "<script type='text/javascript'>" .
				"alert('未知原因，修改产品品牌失败！');" .
				"location.href='index.php?module=brand_class';</script>";
			return $this->getDefaultView();
		} else {
            $db->admin_record($admin_id,' 修改商品品牌id为 '.$brand_id.' 的信息',2);

            header("Content-type:text/html;charset=utf-8");
			echo "<script type='text/javascript'>" .
				"alert('修改产品品牌成功！');" .
				"location.href='index.php?module=brand_class';</script>";
		}
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>