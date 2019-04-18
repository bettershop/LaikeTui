
<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
class red_packet_modifyAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
         $sql = "select * from lkt_red_packet_config";
        $r_1 = $db->select($sql);
        if(!empty($r_1)){
            $re01=unserialize($r_1[0]->sets);
            $id= $r_1[0]->id;
        }else{
            $re01=1;
            $id= "";
        }
        

        $request->setAttribute("re",$re01);
        $request->setAttribute("id",$id);


        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $id = $request->getParameter('id');
        $data['name'] = $request->getParameter('name');//活动名称
        $data['bizhi'] = $request->getParameter('bizhi');//红包金额与数量比值
        $data['send_redpacket'] = $request->getParameter('send_redpacket');//可以发送红包个数
        $data['receive_redpacket'] = $request->getParameter('receive_redpacket');//可以领取红包个数
        $data['bili'] = $request->getParameter('bili');//红包抵用比例
        $data['tixian'] = $request->getParameter('tixian');//红包是否可以提现
        $data['shixiao_time'] = $request->getParameter('shixiao_time');//红包链接失效时间
        $data['shixiao_time1'] = $request->getParameter('shixiao_time1');//红包失效时间
        $data['tixian_money'] = $request->getParameter('tixian_money');//最小提现金额
        if (empty($data['name'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('活动名称不能为空！');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if (empty($data['send_redpacket'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请设置可以发送红包个数');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }

        if (empty($data['bizhi'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请填写红包金额与数量比值');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }

        if (empty($data['receive_redpacket'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请设置可以领取红包个数');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
      
        if ($data['bili']==''){
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请填写红包抵用比例');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if ($data['bili'] > 100) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('抵用比例只能为0-100的正整数');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if ($data['bili'] < 0) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('抵用比例只能为0-100的正整数');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if (empty($data['tixian'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请选择是否可以提现');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if ($data['shixiao_time'] < 0) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请设置红包链接失效时间');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if (empty($data['shixiao_time1'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请设置红包失效时间');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        if (empty($data['tixian_money'])) {
            header('Content-type: text/html;charset=utf-8');
            echo "<script type='text/javascript'>" .
                "alert('请设置红包提现的最小金额');" . 
                "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
        }
        $info=serialize($data);
        if(!empty($id)){

            $sql = "update lkt_red_packet_config set sets = '$info' where id='$id'" ;
                $r = $db->update($sql);

            if($r>0){
                $db->admin_record($admin_id,' 修改拆红包参数 ',2);

                header('Content-type: text/html;charset=utf-8');
                echo "<script type='text/javascript'>" .
                    "alert('修改成功！');" . 
                    "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
            }
        }else{
            $sql = "insert into lkt_red_packet_config (sets) values ('$info')" ;
            $r = $db->insert($sql);

            if($r>0){
                $db->admin_record($admin_id,' 添加拆红包参数 ',1);

                header('Content-type: text/html;charset=utf-8');
                echo "<script type='text/javascript'>" .
                    "alert('添加成功！');" . 
                    "location.href='index.php?module=plug_ins&action=red_packet_modify';</script>";
                return $this->getDefaultView();
            }
        }
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>