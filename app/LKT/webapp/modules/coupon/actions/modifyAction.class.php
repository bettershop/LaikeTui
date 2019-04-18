<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

	public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter("id")); // 活动id
        $res = '';
        $sql = "select * from lkt_coupon_activity where id = '$id' ";
        $r = $db->select($sql);
        if($r){
            $activity_type = $r[0]->activity_type; // 活动类型
            $product_class_id = $r[0]->product_class_id; // 活动指定商品类型id
            $product_id = $r[0]->product_id; // 活动指定商品id
            $name = $r[0]->name; // 活动名称
            $money = $r[0]->money; // 金额
            $z_money = $r[0]->z_money; // 总金额
            $num = $r[0]->num; // 数量
            $start_time = $r[0]->start_time; // 开始时间
            $end_time = $r[0]->end_time; // 结束时间
            $status = $r[0]->status; // 结束时间
        }

        if($product_class_id != 0){ // 当活动指定商品类型id不为0
            $arr = explode('-', $product_class_id);
            $arr = array_filter($arr);
            $arr = array_values($arr);
            $count = count($arr) - 1;
            $product_class_id = $arr[$count];

            // 根据商品分类id,查询分类id、分类名称
            $sql = "select cid,pname from lkt_product_class where cid = '$product_class_id'";
            $rr = $db->select($sql);

            $cid = $rr[0]->cid; // 商品分类id
            $pname = $rr[0]->pname; // 商品分类名称
            $hx = '-----';
            if(count($arr) == 1){
                $res = "<option value='{$cid}'>{$pname}</option>";
            }else if(count($arr) == 2){
                $res = "<option value='{$cid}'>{$hx}{$pname}</option>";
            }else if(count($arr) == 3){
                $res = "<option value='{$cid}'>{$hx}{$hx}{$pname}</option>";
            }
            $res .= "<option value='0' >全部</option>";
            if($product_id != 0){
                $sql = "select id,product_title from lkt_product_list where id = '$product_id'";
                $rrr = $db->select($sql);
                $p_id = $rrr[0]->id;
                $product_title = $rrr[0]->product_title;
                $rew = "<option value='{$p_id}' >{$product_title}</option>";
                $rew .= "<option value='0' >全部</option>";
            }else{
                $rew = "<option value='0' >全部</option>";
            }

            $sql = "select id,product_title from lkt_product_list where product_class like '%$product_class_id%'";
            $rrr_1 = $db->select($sql);
            if($rrr_1){
                foreach ($rrr_1 as $k => $v) {
                   $rew.= "<option value='{$v->id}'>{$v->product_title}</option>";
                }
            }
        }else{
            $res = "<option value='0' >全部</option>";
            $rew = '';
        }
        // 查询所有一级分类
        $sql = "select cid,pname from lkt_product_class where sid = 0 and recycle != 1 ";
        $r_1 = $db->select($sql);
        foreach ($r_1 as $key => $value) {
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

        $request->setAttribute('id', $id);
        $request->setAttribute("activity_type",$activity_type);
        $request->setAttribute('product_class_id', isset($product_class_id) ? $product_class_id : '');
        $request->setAttribute('product_id', isset($product_id) ? $product_id : '');
        $request->setAttribute("name",isset($name) ? $name : '');
        $request->setAttribute('content', isset($content) ? $content : '');
        $request->setAttribute('money', isset($money) ? $money : '');
        $request->setAttribute('z_money', isset($z_money) ? $z_money : '');
        $request->setAttribute('num', isset($num) ? $num : '');
        $request->setAttribute('start_time', isset($start_time) ? $start_time : '');
        $request->setAttribute('end_time', isset($end_time) ? $end_time : '');
        $request->setAttribute('list', $res);
        $request->setAttribute('list1', $rew);
        $request->setAttribute('status', $status);
        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据
        $id = addslashes(trim($request->getParameter('id'))); // 活动id
        $status = $request->getParameter('status'); // 状态

        if($status == 1){
            $sql = "select activity_type,product_class_id,product_id from lkt_coupon_activity where id = '$id'";
            $r_1 = $db->select($sql);
            $activity_type = $r_1[0]->activity_type;
            $product_class_id = $r_1[0]->product_class_id;
            $product_id = $r_1[0]->product_id;
        }else{
            $activity_type = addslashes(trim($request->getParameter('activity_type'))); // 活动类型
            $product_class_id = addslashes(trim($request->getParameter('product_class_id'))); // 活动指定商品类型
            $product_id = addslashes(trim($request->getParameter('product_id'))); // 活动指定商品

        }
        $name = addslashes(trim($request->getParameter('name'))); // 活动名称
        $money = addslashes(trim($request->getParameter('money'))); // 金额
        $z_money = addslashes(trim($request->getParameter('z_money'))); // 总金额
        $num = addslashes(trim($request->getParameter('num'))); // 数量
        $start_time = $request->getParameter('start_time'); // 活动开始时间
        $end_time = $request->getParameter('end_time'); // 活动结束时间

        if($name == ''){
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        // 检查产品标题是否重复
        $sql = "select 1 from lkt_coupon_activity where name = '$name' id != '$id' ";
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
        if($activity_type == 1){
            $sql = "update lkt_coupon_activity " .

                "set name = '$name',activity_type = '$activity_type',product_class_id = '$product_class_id',product_id = '$product_id',money = '$money',num = '$num',add_time = '$time',end_time = '$end_time' " . "where id = '$id'";
            $r = $db->update($sql);
        }else{
            if($start_time > $time){
                //更新数据表
                $sql = "update lkt_coupon_activity " .
                    "set name = '$name',activity_type = '$activity_type',product_class_id = '$product_class_id',product_id = '$product_id',money = '$money',z_money = '$z_money', num = '$num', start_time = '$start_time', end_time = '$end_time', add_time = '$time',status = 0 " . "where id = '$id'";
                $r = $db->update($sql);
            }else{
                //更新数据表
                $sql = "update lkt_coupon_activity " .
                    "set name = '$name',activity_type = '$activity_type',product_class_id = '$product_class_id',product_id = '$product_id',money = '$money',z_money = '$z_money', num = '$num', start_time = '$start_time', end_time = '$end_time', add_time = '$time',status = 1 " . "where id = '$id'";
                $r = $db->update($sql);
            }
        }

        if($r == -1) {
            $db->admin_record($admin_id,' 修改活动id为 '.$id.' 失败 ',2);

            echo "<script type='text/javascript'>" .
                "alert('未知原因，活动修改失败！');" .
                "location.href='index.php?module=coupon';</script>";
            return $this->getDefaultView();
        } else {
            $db->admin_record($admin_id,' 修改活动id为 '.$id.' 成功 ',2);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('活动修改成功！');" .
                "location.href='index.php?module=coupon';</script>";
        }
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}
}
?>