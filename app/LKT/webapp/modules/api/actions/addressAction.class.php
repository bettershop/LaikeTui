<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addressAction extends Action {

    public function getDefaultView() {

        return ;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'set_default'){
            $this->set_default();
        }else if($m == 'del_adds'){
            $this->del_adds();
        }else if($m == 'up_addsindex'){
            $this->up_addsindex();
        }else if($m == 'up_adds'){
            $this->up_adds();
        }else if($m == 'del_select'){
            $this->del_select();
        }

        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 地址管理
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $_POST['openid']; // 微信id
        // 根据微信id,查询用户id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        $user_id = $r[0]->user_id;
        // 根据用户id,查询地址表
        $sql = "select * from lkt_user_address where uid = '$user_id'";
        $r = $db->select($sql);

        echo json_encode(array('adds'=>$r));
        exit();
        return;
    }
    public function del_select()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $openid = trim($request->getParameter('openid')); // 微信id
        $arr = trim($request->getParameter('id_arr')); // 微信id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if($r){
           $user_id = $r[0]->user_id;

         if(!empty($arr)){
            $arrid = explode(',', $arr);
            // print_r($arrid);die;
            foreach ($arrid as $key => $value) {
                if($value !=''){
                    $sql = "delete from lkt_user_address where uid = '$user_id' and id = '$value'";
                    $r = $db->delete($sql);
                }
                
           }
           echo json_encode(array('status'=>1,'succ'=>'删除成功!'));
           exit();
         }
           
        }else{
            echo json_encode(array('status'=>0,'err'=>'删除失败!'));
            exit();
        }
    }
    // 设置默认
    public function set_default(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $_POST['openid']; // 微信id
        $addr_id = $_POST['addr_id']; // 地址id
        // 根据微信id,查询用户id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if($r){
            $user_id = $r[0]->user_id;
            $sql = "update lkt_user_address set is_default = 0 where uid = '$user_id'";
            $r = $db->update($sql);
            $sql = "update lkt_user_address set is_default = 1 where uid = '$user_id' and id = '$addr_id'";
            $rr = $db->update($sql);
        }else{
            $rr = 0;
        }

        if($rr > 0){
            echo json_encode(array('status'=>1,'err'=>'操作成功!'));
            exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'设置失败'));
            exit();
        }
        return;
    }

    // 删除地址
    public function del_adds(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $_POST['openid']; // 微信id
        $id_arr = $_POST['id_arr']; // 地址id
        // 根据微信id,查询用户id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if($r){
            $user_id = $r[0]->user_id;
            // 根据用户id,查询地址表
            $sql = "delete from lkt_user_address where uid = '$user_id' and id = '$id_arr'";
            $r = $db->delete($sql);
            if($r > 0){
                echo json_encode(array('status'=>1));
                exit();
            }else{
                echo json_encode(array('status'=>0,'err'=>'删除失败'));
                exit();
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'删除失败'));
            exit();
        }

        
        return;
    }

    // 修改地址
    public function up_adds(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $_POST['openid']; // 微信id
        $id_arr = $_POST['id_arr']; // 地址id

        $user_name = $_POST['user_name']; // 联系人
        $mobile = $_POST['mobile']; // 联系电话
        $province = $_POST['province']; // 省
        $city = $_POST['city']; // 市
        $county = $_POST['county']; // 县 
        $address = $_POST['address']; // 详细地址

        $sql = "select * from lkt_user_address where id = '$id_arr'"; //查询修改前的详细地址
        $r = $db->select($sql);
        $code = 0;//
        $uid = $r[0]->uid;//用户ID
        $is_default = $r[0]->is_default;//是否默认地址

        // 查询省的编号
        $sql01 ="select GroupID from admin_cg_group where G_CName='$province'";
        $r01 = $db->select($sql01);
        $sheng = $r01[0]->GroupID;
        // 查询市的编号
        $sql02 ="select GroupID from admin_cg_group where G_CName='$city'";
        $r02 = $db->select($sql02);
        $shi = $r02[0]->GroupID;
        // 查询县的编号
        $sql03 ="select GroupID from admin_cg_group where G_CName='$county'";
        $r03 = $db->select($sql03);
        $xian = $r03[0]->GroupID;
        $address_xq = $province . $city . $county . $address; // 带省市县的详细地址
        if(preg_match("/^1[34578]\d{9}$/", $mobile)){
            $sql04 = "update lkt_user_address set name = '$user_name',tel= '$mobile',sheng='$sheng',city ='$shi',quyu ='$xian',address ='$address',address_xq ='$address_xq',code ='$code',uid ='$uid',is_default='$is_default' where id = '$id_arr'";
            $r04 = $db->update($sql04);
            if($r04 ==1){
                echo json_encode(array('status'=>1,'info'=>'修改成功！'));
            }else{
                echo json_encode(array('status'=>0,'info'=>'修改失败！'));
            }
        }else{
            echo json_encode(array('status'=>0,'info'=>'手机号码有误！'));
            
        }
        exit();
        return;
    }
    //页面跳转显示
    public function up_addsindex(){
         $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $openid = $_POST['openid']; // 微信id
        $id_arr = $_POST['id_arr']; // 地址id
        $sql = "select * from lkt_user_address where id = '$id_arr'"; //查询修改前的详细地址
        $r = $db->select($sql);
        if($r){
            $sheng = $r[0]->sheng;//省
            $city = $r[0]->city;//市
            $quyu = $r[0]->quyu;//县

            // 查询省的编号
            $sql01 = "select G_CName from admin_cg_group a where a.GroupID='$sheng'";
            $r01 = $db->select($sql01);
            if($r01){
                $province = $r01[0]->G_CName;
            }else{
                $province = '';
            }
            $province = $r01[0]->G_CName;
            // 根据省查询市
            $sql02 = "select G_CName from admin_cg_group a where a.GroupID='$city'";
            $r02 = $db->select($sql02);
            if($r02){
                $city = $r02[0]->G_CName;
            }else{
                $city = '';
            }

            
            // 根据市查询县
            $sql03 = "select G_CName from admin_cg_group a where a.GroupID='$quyu'";
            $r03 = $db->select($sql03);
            if($r03){
                $county = $r03[0]->G_CName;
            }else{
                $county = '';
            }
            

            echo json_encode(array('adds'=>$r,'province'=>$province,'city'=>$city,'county'=>$county));
            exit();
        }else{
            echo json_encode(array('status'=>0,'info'=>'手机号码有误！'));
        }

        return;
    }
}
?>