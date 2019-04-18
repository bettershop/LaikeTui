<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();

		$sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

		$request->setAttribute("uploadImg",$uploadImg);
		return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 获取分类名称和排序号
        $brand_name = addslashes(trim($request->getParameter('pname'))); // 品牌名称
        $brand_y_pname = addslashes(trim($request->getParameter('y_pname'))); // 品牌名称
        $image = addslashes(trim($request->getParameter('image'))); // 品牌图片
        $producer = addslashes(trim($request->getParameter('producer'))); // 产地
        $sort = addslashes(trim($request->getParameter('sort'))); // 排序
        $remarks = addslashes(trim($request->getParameter('remarks'))); // 备注
        if($image){
            $image = preg_replace('/.*\//','',$image);
        }
        if($brand_name == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('中文名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
		//检查分类名称是否重复
        $sql = "select * from lkt_brand_class where brand_name = '$brand_name'";
		$r = $db->select($sql);
		// 如果有数据 并且 数据条数大于0
        if ($r && count($r) > 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('商品品牌中文名称{$brand_name} 已经存在，请选用其他名称！');" .
                "</script>";
            return $this->getDefaultView();
        }
		//添加分类
		$sql = "insert into lkt_brand_class(brand_name,brand_y_name,brand_pic,producer,remarks,brand_time,sort) "
            ."values('$brand_name','$brand_y_pname','$image','$producer','$remarks',CURRENT_TIMESTAMP,'$sort')";
		$r = $db->insert($sql);
		if($r == -1) {
            $db->admin_record($admin_id,'添加商品品牌'.$brand_name.'失败',1);

            header("Content-type:text/html;charset=utf-8");
			echo "<script type='text/javascript'>" .
				"alert('未知原因，添加产品品牌失败！');" .
				"</script>";
			return $this->getDefaultView();
		} else {
            $db->admin_record($admin_id,'添加商品品牌'.$brand_name,1);

            header("Content-type:text/html;charset=utf-8");
			echo "<script type='text/javascript'>" .
				"alert('添加产品品牌成功！');" .
				"location.href='index.php?module=brand_class';</script>";
			return $this->getDefaultView();
		}
		
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}

	public function menu_merge($data,$parent_id=0,$level=1){
		$res = array();
		foreach ($data as $v) {
			if($v['sid']==$parent_id){
				$v['level'] = $level;
				$v['disabled'] = false;
				$res[]=$v;
				$res = array_merge($res,menu_merge($data,$v['id'],$level+1));
			}
		}
		return $res;
	}


}

?>