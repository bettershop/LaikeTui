<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class footprintAction extends Action {
    public function getDefaultView() {
        return ;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'alldel'){
            $this->alldel();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 获取我的历史记录
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        
        $openid = $_POST['openid']; // 微信id
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        // 根据微信id,查询用户id
        $sql = "select user_id from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        $user_id = $r[0]->user_id;

        $start_time_1=date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d'),date('Y'))); // 今天开始时间
        $end_time_1=date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d')+1,date('Y'))-1); // 今天结束时间

        $start_time_2=date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d')-1,date('Y'))); // 昨天开始时间
        $end_time_2=date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d'),date('Y'))-1); // 昨天结束时间
        
        $start_time_3=date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d')-2,date('Y'))); // 前天开始时间
        $end_time_3=date("Y-m-d H:i:s",mktime(0,0,0,date('m'),date('d')-1,date('Y'))-2); // 前天结束时间
        // 根据用户id,查询今天足迹
        $sql = "select * from lkt_user_footprint where user_id = '$user_id' and add_time > '$start_time_1' and add_time < '$end_time_1' ";

        $r_1 = $db->select($sql);
        if($r_1){
            $time_1 = date('Y年m月d日',strtotime($r_1[0]->add_time));
            $res_1=[];
            foreach ($r_1 as $k_1 => $v_1) {
                $p_id = $v_1->p_id;
                 $sql ="select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,c.img,c.id AS sizeid from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.id ='$p_id' group by c.pid ";
                $rr_1 = $db->select($sql);
                if($rr_1){
                    foreach ($rr_1 as $key_1 => $value_1) {
                        $value_1->imgurl = $img.$value_1->img; // 拼图片路径
                        $res_1[$k_1] = $value_1;
                    }
                }
            }

            $rew_1 = $res_1;
            $list_1 = array('time'=>$time_1,'list'=>$rew_1); 
        }else{
            $list_1 = ''; 
        }
        // 根据用户id,查询昨天足迹
        $sql = "select * from lkt_user_footprint where user_id = '$user_id' and add_time > '$start_time_2' and add_time < '$end_time_2' ";
        $r_2 = $db->select($sql);
        if($r_2){
            $res_2=[];
            $time_2 = date('Y年m月d日',strtotime($r_2[0]->add_time));
            foreach ($r_2 as $k_2 => $v_2) {
                $p_id = $v_2->p_id;

                $sql ="select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,c.img,c.id AS sizeid from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.id ='$p_id' and a.num >0 group by c.pid ";

                $rr_2 = $db->select($sql);
                if($rr_2){
                    foreach ($rr_2 as $key_2 => $value_2) {
                        $value_2->imgurl = $img.$value_2->img; // 拼图片路径
                        $res_2[$k_2] = $value_2;
                    }
                }
            }
            $rew_2 = $res_2;
            $list_2 = array('time'=>$time_2,'list'=>$rew_2); 
        }else{
            $list_2 = ''; 
        }
        // 根据用户id,查询前天足迹
        $sql = "select * from lkt_user_footprint where user_id = '$user_id' and add_time > '$start_time_3' and add_time < '$end_time_3' ";
        $r_3 = $db->select($sql);
        if($r_3){
            $res_3=[];
            $time_3 = date('Y年m月d日',strtotime($r_3[0]->add_time));
            foreach ($r_3 as $k_3 => $v_3) {
                $p_id = $v_3->p_id;
                $sql ="select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,c.img,c.id AS sizeid from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.id ='$p_id' and a.num >0 group by c.pid ";
                $rr_3 = $db->select($sql);
                if($rr_3){
                    foreach ($rr_3 as $key_3 => $value_3) {
                        $value_3->imgurl = $img.$value_3->img; // 拼图片路径
                        $res_3[$k_3] = $value_3;
                    }
                }
            }
            $rew_3 = $res_3;
            $list_3 = array('time'=>$time_3,'list'=>$rew_3); 
        }else{
            $list_3 = ''; 
        }
        // 查询更早的历史记录
        $sql = "select * from lkt_user_footprint where user_id = '$user_id' and add_time < '$start_time_3'";
        $r_4 = $db->select($sql);
        if($r_4){
            $res_4=[];
            $time_4 = '更早时间';
            foreach ($r_4 as $k_4 => $v_4) {
                $p_id = $v_4->p_id;
                 $sql ="select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,c.img,c.id AS sizeid from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.id ='$p_id' and a.num >0 group by c.pid ";
                $rr_4 = $db->select($sql);
                if($rr_4){
                    foreach ($rr_4 as $key_4 => $value_4) {
                        $value_4->imgurl = $img.$value_4->img; // 拼图片路径
                        $res_4[$key_4] = $value_4;
                    }
                }
            }
            $rew_4 = $res_4;
            $list_4 = array('time'=>$time_4,'list'=>$rew_4); 
        }else{
            $list_4 = ''; 
        }
        if($list_1 != '' || $list_2 != '' || $list_3 != '' || $list_4 != ''){
            $arr = array($list_1,$list_2,$list_3,$list_4); 
            echo json_encode(array('status'=>1,'arr'=>$arr));
            exit();
        }else{
            echo json_encode(array('status'=>1,'arr'=>''));
            exit();
        }
        return;
    }
    // 删除我的历史记录
    public function alldel()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $openid = trim($request->getParameter('openid')); // 微信id
        $sql_user = 'select user_id from lkt_user where wx_id=\''.$openid.'\'';
        $r_user = $db->select($sql_user);
        $userid = $r_user['0']->user_id;
        $sql = "delete from lkt_user_footprint where user_id = '$userid'";
        $r = $db->delete($sql);
        if ($r){
            echo json_encode(array('status'=>1,'succ'=>'删除成功！'));
            exit(); 
        }else{
            echo json_encode(array('status'=>0,'err'=>'删除失败！'));
            exit(); 
        }
    }
}

?>