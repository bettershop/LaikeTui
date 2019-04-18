<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class addFavoritesAction extends Action {
    /*
    时间2018年03月13日
    修改内容：修改首页商品及分类请求数据
    修改人：苏涛
    主要功能：处理小程序首页请求结果
    公司：湖南壹拾捌号网络技术有限公司
     */
    public function getDefaultView() {
        return ;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'collection'){
            $this->collection();
        }else if($m == 'removeFavorites'){
            $this->removeFavorites();
        }else if($m == 'alldel'){
            $this->alldel();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
        // return Request :: GET;
    }
    // 点击收藏
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $openid = $_POST['openid']; // 微信id
        $pid = $_POST['pid']; // 产品id
        // 根据微信id,查询用户id
        $sql = "select user_id from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        $user_id = $r[0]->user_id;
        // 根据用户id,产品id,查询收藏表
        $sql = "select * from lkt_user_collection where user_id = '$user_id' and p_id = '$pid'";
        $r = $db->select($sql);
        if ($r) {
            echo json_encode(array('status'=>0,'err'=>'已收藏！'));
            exit(); 
        }else{
            // 在收藏表里添加一条数据
            $sql = "insert into lkt_user_collection(user_id,p_id,add_time) values('$user_id','$pid',CURRENT_TIMESTAMP)";
            $r = $db->insert($sql,'last_insert_id');
            if($r){
                echo json_encode(array('status'=>1,'succ'=>'收藏成功!','id' => $r));
                exit(); 
            }else{
                echo json_encode(array('status'=>0,'err'=>'网络繁忙！'));
                exit(); 
            }
        }
        
        return;
    }
    // 查看收藏
    public function collection(){
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

        $sql ="select l.id,a.id as pid,a.product_title,a.imgurl as img,min(c.price) as price from lkt_user_collection as l left join lkt_product_list AS a on l.p_id = a.id RIGHT JOIN lkt_configure AS c ON a.id = c.pid where l.user_id = '$user_id' and a.num >0 group by c.pid order by l.add_time desc";

        $r = $db->select($sql);
        $arr = [];
        if($r){
            foreach ($r as $k => $v) {
                $array = (array)$v;
                $pid = $array['pid'];

                $array['price']=$v->price;
                $array['imgurl']= $img . $v->img;
                $v = (object)$array;
                $arr[$k] = $v;
            }
            echo json_encode(array('status'=>1,'list'=>$arr));
            exit(); 
        }else{
            echo json_encode(array('status'=>1,'list'=>''));
            exit(); 
        }
        return;
    }
    // 取消收藏
    public function removeFavorites(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $id = $_POST['id']; // 收藏id
        // 根据收藏id,删除收藏表信息
        $sql = "delete from lkt_user_collection where id = '$id'";
        $r = $db->delete($sql);
        if($r > 0){
            echo json_encode(array('status'=>1,'succ'=>'已取消！'));
            exit(); 
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙！'));
            exit(); 
        }
        return;
    }
    public function alldel()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $openid = trim($request->getParameter('openid')); // 微信id
        $sql_user = 'select user_id from lkt_user where wx_id=\''.$openid.'\'';
        $r_user = $db->select($sql_user);
        $userid = $r_user['0']->user_id;
        $sql = "delete from lkt_user_collection where user_id = '$userid'";
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