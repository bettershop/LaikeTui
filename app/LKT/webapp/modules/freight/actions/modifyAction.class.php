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
        $id = intval($request->getParameter("id")); // 产品id

        $sql = "select * from lkt_freight where id = '$id'";
        $r = $db->select($sql);
        if($r){
            $name = $r[0]->name; // 规则名称
            $type = $r[0]->type; // 规则类型
            $freight = unserialize($r[0]->freight); // 属性
            $res = '';
            foreach ($freight as $k => $v){
                $k1 = $k + 1;
                $res .= "<tr class='tr_freight_num' id='tr_freight_$k1'>" .
                    "<td>".$v['one']."</td>" .
                    "<td>".$v['two']."</td>" .
                    "<td>".$v['three']."</td>" .
                    "<td>".$v['four']."</td>" .
                    "<td>".$v['name']."</td>" .
                    "<td><span class='btn btn-secondary radius' onclick='freight_del($k1)' >删除</span></td>" .
                    "</tr>";
            }
            $freight = json_encode($freight);
        }
        $request->setAttribute("id",$id);
        $request->setAttribute("name",$name);
        $request->setAttribute("type",$type);
        $request->setAttribute("freight",$freight);
        $request->setAttribute("list",$res);

        return View :: INPUT;
	}

	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        // 接收数据
        $id = addslashes(trim($request->getParameter('id'))); // 规则id
        $name = addslashes(trim($request->getParameter('name'))); // 规则名称
        $type = addslashes(trim($request->getParameter('type'))); // 类型
        $hidden_freight = $request->getParameter('hidden_freight'); // 运费信息
        if($hidden_freight){
            $freight_list = json_decode($hidden_freight,true);
            $freight = serialize($freight_list);
        }else{
            $freight = '';
        }
        if($name == ''){
            echo "<script type='text/javascript'>" .
                "alert('规则名称不能为空！');" .
                "location.href='index.php?module=freight&action=add';</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select * from lkt_freight where id != '$id'";
            $r = $db->select($sql);
            if($r){
                foreach ($r as $k => $v){
                    if($name == $v->name){
                        echo "<script type='text/javascript'>" .
                            "alert('规则名称 {$name} 已经存在，请选用其他名称！');" .
                            "location.href='index.php?module=freight&action=add';</script>";
                        return $this->getDefaultView();
                    }
                }
            }
        }

        $sql = "update lkt_freight set name = '$name',type = '$type',freight = '$freight' where id = '$id'";
        $rr = $db->update($sql);
        if($rr > 0){
            $db->admin_record($admin_id,' 修改规则id为 '.$id.' 的信息 ',2);

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('规则修改成功！');" .
                "location.href='index.php?module=freight';</script>";
        }else{
            $db->admin_record($admin_id,' 修改规则id为 '.$id.' 失败 ',2);

            echo "<script type='text/javascript'>" .
                "alert('未知原因，规则修改失败！');" .
                "location.href='index.php?module=freight';</script>";
            return $this->getDefaultView();
        }
		return;
	}

	public function getRequestMethods(){
		return Request :: POST;
	}
}
?>