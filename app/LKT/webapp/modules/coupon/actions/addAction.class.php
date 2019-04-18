<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {
    /*
    时间2018年03月27日
    修改人：段宏波
    主要功能：处理后台活动添加
    公司：湖南壹拾捌号网络技术有限公司
     */
	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql = "select cid,pname from lkt_product_class where sid = 0 and recycle != 1 ";
        $r = $db->select($sql);

        $res = '<option value="0" >全部</option>';
        foreach ($r as $key => $value) {
            $c = '-'.$value->cid.'-';
            $res .= '<option  value="-'.$value->cid.'-">'.$value->pname.'</option>';
            //循环第一层
            $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid";
            $r_e = $db->select($sql_e);
            if($r_e){
                $hx = '-----';
                foreach ($r_e as $ke => $ve){
                   $cone = $c . $ve->cid.'-';
                   $res .= '<option  value="'.$cone.'">'.$hx.$ve->pname.'</option>';
                   //循环第二层
                   $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid";
                   $r_t = $db->select($sql_t);
                    if($r_t){
                        $hxe = $hx.'-----';
                        foreach ($r_t as $k => $v){
                           $ctow = $cone . $v->cid.'-';
                           $res .= '<option  value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                        }
                    }
                }
            }
        }

        $request->setAttribute("list",$res);
		return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据 
        $name = addslashes(trim($request->getParameter('name'))); // 活动名称
        $activity_type = addslashes(trim($request->getParameter('activity_type'))); // 活动类型
        $product_class_id = addslashes(trim($request->getParameter('product_class_id'))); // 活动指定商品类型
        $product_id = addslashes(trim($request->getParameter('product_id'))); // 活动指定商品
        $money = addslashes(trim($request->getParameter('money'))); // 金额
        $z_money = addslashes(trim($request->getParameter('z_money'))); // 总金额
        $num = addslashes(trim($request->getParameter('num'))); // 数量
        $start_time = $request->getParameter('start_time'); // 活动开始时间
        $end_time = $request->getParameter('end_time'); // 活动结束时间
        $image=$request->getParameter('image');
        if($name == ''){
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        // 检查产品标题是否重复
        $sql = "select 1 from lkt_coupon_activity where name = '$name' ";
        $r = $db->select($sql);
        if ($r && count($r) > 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('{$name} 活动名称已经存在！');" .
                "</script>";
            return $this->getDefaultView();
        }
        

        if($money == ''){
            header('Content-Type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('金额不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($num == '' || $num <= 0){
            $num = 99999999999;
        }

        if($start_time == '' && $activity_type != 1){
            header('Content-Type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动开始时间不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        if($end_time == '' && $activity_type != 1){
            header('Content-Type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动结束时间不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        if($start_time >= $end_time && $activity_type != 1){
            header('Content-Type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动开始时间不能大于等于活动结束时间！');" .
                "</script>";
            return $this->getDefaultView();
        }

        $time = date('Y-m-d H:i:s');
        if($time >= $end_time && $activity_type != 1){
            header('Content-Type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动还没开始就已经结束！');" .
                "</script>";
            return $this->getDefaultView();
        }
       //添加设置product_id,image
       $product_id= empty($product_id) ? 0 : $product_id;
       $image=empty($image) ? '' : $image;
        if($activity_type == 1){
           
           
                 // 添加活动
              $sql = "insert into lkt_coupon_activity(name,activity_type,product_class_id,product_id,image,money,num,add_time,start_time,status)" .
                    "values('$name','$activity_type','$product_class_id','$product_id','$image','$money','$num',CURRENT_TIMESTAMP,'$time',1)";
              $rr = $db->insert($sql);
         
            

        }else{
            // 活动开始时间大于当前时间,活动还没开始
            if($start_time > $time){
                if($activity_type == 2){
                    // 添加活动
                    $sql = "insert into lkt_coupon_activity(name,activity_type,product_class_id,product_id,image,money,num,add_time,start_time,end_time,status) " .
                        "values('$name','$activity_type','$product_class_id','$product_id','$image','$money','$num',CURRENT_TIMESTAMP,'$start_time','$end_time',0)";
                }else{
                    // 添加活动
                    $sql = "insert into lkt_coupon_activity(name,activity_type,product_class_id,product_id,image,money,z_money,num,add_time,start_time,end_time,status) " .
                        "values('$name','$activity_type','$product_class_id','$product_id','$image','$money','$z_money','$num',CURRENT_TIMESTAMP,'$start_time','$end_time',0)";
                }
                $rr = $db->insert($sql);

            }else{
                if($activity_type == 2){
                    // 添加活动
                    $sql = "insert into lkt_coupon_activity(name,activity_type,product_class_id,product_id,image,money,num,add_time,start_time,end_time,status) " .
                        "values('$name','$activity_type','$product_class_id','$product_id','$image','$money','$num',CURRENT_TIMESTAMP,'$start_time','$end_time',1)";
                }else{
                    // 添加活动
                    $sql = "insert into lkt_coupon_activity(name,activity_type,product_class_id,product_id,image,money,z_money,num,add_time,start_time,end_time,status) " .
                        "values('$name','$activity_type','$product_class_id','$product_id','$image','$money','$z_money','$num',CURRENT_TIMESTAMP,'$start_time','$end_time',1)";
                }
                $rr = $db->insert($sql);
            }
        }
        if($rr == -1 ){
            $db->admin_record($admin_id,' 添加活动失败 ',1);
            // echo $sql;exit;
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，活动添加失败！');" .
                "location.href='index.php?module=coupon';</script>";
            return $this->getDefaultView();
        }else{
            $db->admin_record($admin_id,' 添加活动成功 ',1);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('活动添加成功！');" .
                "location.href='index.php?module=coupon';</script>";
            return $this->getDefaultView();
        }
	    return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}
}
?>