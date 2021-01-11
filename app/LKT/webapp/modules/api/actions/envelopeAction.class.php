<?php

/**

 * [Laike System] Copyright (c) 2017-2020 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once('BaseAction.class.php');

class envelopeAction extends BaseAction {
    
    // 获取新闻详情
    public function index(){
        $id = addslashes($_POST['id']);
        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        // 根据新闻id,查询新闻数据
        $sql = "select * from lkt_article where Article_id=".$id;
        $r = lkt_gets($sql);
        
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
    }
    // 新闻详情点击分享
    public function share(){
        $id = addslashes($_POST['id']); // 文章id
        $openid = addslashes($_POST['openid']); // 微信id
        /* ----- 分享成功 -----*/
        // 根据文章id,修改文章分享次数
        $sql = "update lkt_article set share_num = share_num+1 where Article_id = '$id'";
        lkt_execute($sql);
        // 根据wx_id,修改会员分享次数
        $sql = "update lkt_user set share_num = share_num+1 where wx_id = '$openid'";
        lkt_execute($sql);
        // 根据wx_id查询会员id
        $sql = "select * from lkt_user where wx_id = '$openid' ";
        $r = lkt_gets($sql);
        $user_id = $r[0]->user_id;
        $event = $user_id . '分享了文章' . $id ;
        // 在操作列表添加一条分享信息
        $sql = "insert into lkt_record (user_id,event,type) values ('$user_id','$event',3)";
        lkt_execute($sql);
        
        /*----- 是否进入红包 -----*/
        // 根据文章id,查询文章信息
        $sql = "select * from lkt_article where Article_id = '$id'";
        $r = lkt_gets($sql);
        $total_amount = $r[0]->total_amount; // 红包总金额
        $total_num = $r[0]->total_num; // 红包数量
        if($total_amount != 0 && $total_num !=0){
            $start_time = date("Y-m-d 00:00:00");  // 当天开始时间
            $end_time = date("Y-m-d 00:00:00",strtotime("+1 day")); // 明天开始时间
            // 微信id和当天时间内,查询分享列表
            $sql = "select * from lkt_share where wx_id = '$openid' and Article_id = '$id' and '$start_time'<=share_add and share_add<'$end_time'";
            $r = lkt_gets($sql);

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
    }
}

