<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class envelopeAction extends Action {

    public function getDefaultView() {

        return;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'share'){
            $this->share();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 获取新闻详情
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取新闻id
        $id = $_POST['id'];
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

        // 根据新闻id,查询新闻数据
        $sql = "select * from lkt_article where Article_id=".$id;
        $r = $db->select($sql);
        
        if($r){
            $url='http://'.$_SERVER['HTTP_HOST']; // 根目录
            $r['0']->Article_imgurl = $img . $r['0']->Article_imgurl; // 图片
            $content = $r[0]->content;
            $ArticleImg = preg_replace('/(<img.+?src=")(.*?)/','$1'.$url.'$2', $content);
            $r[0]->content = $ArticleImg;
            echo json_encode(array('status'=>1,'article'=>$r));
            exit();
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙！'));
            exit();
        }
        return;
    }
    // 新闻详情点击分享
    public function share(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 获取信息
        $id = $_POST['id']; // 文章id
        $openid = $_POST['openid']; // 微信id
        /* ----- 分享成功 -----*/
        // 根据文章id,修改文章分享次数
        $sql = "update lkt_article set share_num = share_num+1 where Article_id = '$id'";
        $r = $db->update($sql);
        // 根据wx_id,修改会员分享次数
        $sql = "update lkt_user set share_num = share_num+1 where wx_id = '$openid'";
        $r = $db->update($sql);
        // 根据wx_id查询会员id
        $sql = "select * from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        $user_id = $r[0]->user_id;
        $event = $user_id . '分享了文章' . $id ;
        // 在操作列表添加一条分享信息
        $sql = "insert into lkt_record (user_id,event,type) values ('$user_id','$event',3)";
        $r = $db->insert($sql);
        
        /*----- 是否进入红包 -----*/
        // 根据文章id,查询文章信息
        $sql = "select * from lkt_article where Article_id = '$id'";
        $r = $db->select($sql);
        $total_amount = $r[0]->total_amount; // 红包总金额
        $total_num = $r[0]->total_num; // 红包数量
        if($total_amount != 0 && $total_num !=0){
            $start_time = date("Y-m-d 00:00:00");  // 当天开始时间
            $end_time = date("Y-m-d 00:00:00",strtotime("+1 day")); // 明天开始时间
            // 微信id和当天时间内,查询分享列表
            $sql = "select * from lkt_share where wx_id = '$openid' and Article_id = '$id' and '$start_time'<=share_add and share_add<'$end_time'";
            $r = $db->select($sql);

            if(empty($r)){ // 没数据,可以领红包
                echo json_encode(array('status'=>1,'err'=>'分享成功！','info'=>1));
                exit();
            }else{ // 有数据,跳过红包
                echo json_encode(array('status'=>1,'err'=>'分享成功！','info'=>0));
                exit();
            }
        }else{
            echo json_encode(array('status'=>1,'err'=>'商家忘设红包！','info'=>0));
            exit();
        }
        return;
    }
}

?>