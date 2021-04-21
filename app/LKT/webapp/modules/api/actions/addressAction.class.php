<?php
/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once('BaseAction.class.php');

class addressAction extends BaseAction {
   
    // 地址管理
    public function index(){
        $openid = addslashes($_POST['openid']); // 微信id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = lkt_gets($sql);
        $user_id = $r[0]->user_id;
        $sql = "select * from lkt_user_address where uid = '$user_id'";
        $r = lkt_gets($sql);
        echo json_encode(array('adds'=>$r));
        exit();
    }

    public function del_select()
    {
        $request = $this->getContext()->getRequest();
        $openid = addslashes(trim($request->getParameter('openid'))); // 微信id
        $arr = addslashes(trim($request->getParameter('id_arr'))); // 微信id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = lkt_gets($sql);
        if($r){
         $user_id = $r[0]->user_id;
         if(!empty($arr)){
            $arrid = explode(',', $arr);
            foreach ($arrid as $key => $value) {
                if($value !=''){
                    $sql = "delete from lkt_user_address where uid = '$user_id' and id = '$value'";
                    lkt_execute($sql);
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
        $openid = addslashes($_POST['openid']); // 微信id
        $addr_id = addslashes($_POST['addr_id']); // 地址id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = lkt_gets($sql);
        if($r){
            $user_id = $r[0]->user_id;
            $sql = "update lkt_user_address set is_default = 0 where uid = '$user_id'";
            lkt_execute($sql);
            $sql = "update lkt_user_address set is_default = 1 where uid = '$user_id' and id = '$addr_id'";
            $rr = lkt_execute($sql);
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
    }

    // 删除地址
    public function del_adds(){
        $openid = addslashes($_POST['openid']); 
        $id_arr = addslashes($_POST['id_arr']); 
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = lkt_gets($sql);
        if($r){
            $user_id = $r[0]->user_id;
            $sql = "delete from lkt_user_address where uid = '$user_id' and id = '$id_arr'";
            $r = lkt_execute($sql);
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


    }

    // 修改地址
    public function up_adds(){
        $id_arr = addslashes($_POST['id_arr']); // 地址id
        $user_name = addslashes($_POST['user_name']); // 联系人
        $mobile = addslashes($_POST['mobile']); // 联系电话
        $province = addslashes($_POST['province']); // 省
        $city = addslashes($_POST['city']); // 市
        $county = addslashes($_POST['county']); // 县 
        $address = addslashes($_POST['address']); // 详细地址

        $sql = "select * from lkt_user_address where id = '$id_arr'"; //查询修改前的详细地址
        $r = lkt_gets($sql);
        $code = 0;//
        $uid = $r[0]->uid;//用户ID
        $is_default = $r[0]->is_default;//是否默认地址

        // 查询省的编号
        $sql01 ="select GroupID from admin_cg_group where G_CName='$province'";
        $r01 = lkt_gets($sql01);
        $sheng = $r01[0]->GroupID;
        // 查询市的编号
        $sql02 ="select GroupID from admin_cg_group where G_CName='$city'";
        $r02 = lkt_gets($sql02);
        $shi = $r02[0]->GroupID;
        // 查询县的编号
        $sql03 ="select GroupID from admin_cg_group where G_CName='$county'";
        $r03 = lkt_gets($sql03);
        $xian = $r03[0]->GroupID;
        $address_xq = $province . $city . $county . $address; // 带省市县的详细地址

        if(preg_match("/^1[2345678]\d{9}$/", $mobile)){
            $sql04 = "update lkt_user_address set name = '$user_name',tel= '$mobile',sheng='$sheng',city ='$shi',quyu ='$xian',address ='$address',address_xq ='$address_xq',code ='$code',uid ='$uid',is_default='$is_default' where id = '$id_arr'";
            $r04 = lkt_execute($sql04);
            if($r04 >=0){
                echo json_encode(array('status'=>1,'info'=>'修改成功！'));
            }else{
                echo json_encode(array('status'=>0,'info'=>'修改失败！'));
            }
        }else{
            echo json_encode(array('status'=>0,'info'=>'手机号码有误！'));
            
        }
        exit();
    }
    
    //页面跳转显示
    public function up_addsindex(){
        $id_arr = addslashes($_POST['id_arr']); // 地址id
        $sql = "select * from lkt_user_address where id = '$id_arr'"; //查询修改前的详细地址
        $r = lkt_gets($sql);
        if($r){
            $sheng = $r[0]->sheng;//省
            $city = $r[0]->city;//市
            $quyu = $r[0]->quyu;//县

            // 查询省的编号
            $sql01 = "select G_CName from admin_cg_group a where a.GroupID='$sheng'";
            $r01 = lkt_gets($sql01);
            $province = '';
            if($r01){
                $province = $r01[0]->G_CName;
            }
            // 根据省查询市
            $sql02 = "select G_CName from admin_cg_group a where a.GroupID='$city'";
            $r02 = lkt_gets($sql02);
            $city = '';
            if($r02){
                $city = $r02[0]->G_CName;
            }
            
            // 根据市查询县
            $sql03 = "select G_CName from admin_cg_group a where a.GroupID='$quyu'";
            $r03 = lkt_gets($sql03);
            if($r03){
                $county = $r03[0]->G_CName;
            }else{
                $county = '';
            }
            
            echo json_encode(array('adds'=>$r,'province'=>$province,'city'=>$city,'county'=>$county));
            exit();
        }else{
            echo json_encode(array('status'=>0,'info'=>'操作有误！'));
        }

    }
}
