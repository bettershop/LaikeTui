<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {
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
        }elseif ($m == 'get_more') {
            $this->get_more();
        }elseif ($m == 'draw') {
            $this->draw();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 获取小程序首页数据
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
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
        $title = $r_1[0]->company;
        $logo = $img.$r_1[0]->logo;
        // 查询轮播图,根据排序、轮播图id顺序排列
        $sql = "select * from lkt_banner order by sort,id";
        $r = $db->select($sql);
        $banner = array();
        foreach($r as $k=>$v){
            $result = array();
            $result['id'] = $v->id; // 轮播图id
            $result['image'] = $img . $v->image; // 图片
            $result['url'] = $v->url; // 链接
            $banner[] = $result;
            unset($result); // 销毁指定变量
        }

        $shou = [];
        $sql = "select * from lkt_index_page order by sort desc";
        $r_t = $db->select($sql);

        foreach ($r_t as $k => $v) {
            if($v->type == 'img'){
                $imgurl = $img . $v->image;
                $shou[$k] = array('id' => $v->id,'url' => $v->url,'imgurl' => $imgurl);
            }else{
                $ttcid = $v->url;
                $sql_cs = "select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,a.imgurl,c.name from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.product_class like '%-$ttcid-%' and a.status = 0 and a.num >0 group by c.pid  order by a.sort DESC LIMIT 0,10";
                $r_cs = $db->select($sql_cs);

                $cproduct = [];
                if($r_cs){
                    foreach ($r_cs as $keyc => $valuec) {
                        $valuec->imgurl = $img . $valuec->imgurl;
                        $cproduct[$keyc] = $valuec;
                    }
                    $shou[$k] = $cproduct;
                }
            }
        }


        $sql_t = "select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,a.imgurl,c.name,a.distributor_id from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.distributor_id > '0' and a.status = 0 and a.num >0 group by c.pid  order by a.sort DESC LIMIT 0,20";
        $r_t = $db->select($sql_t);


        if($r){
             $sort= $r[0]->sort;
        }else{
            $sort=0;
        }
        //查询用户等级判断是否升级
        $distribus = [];
        //列出等级关系
        $distributor = [];


        //查询商品并分类显示返回JSON至小程序
        $sql_c = 'select cid,pname from lkt_product_class where sid=0 and recycle!=1 order by sort desc';
        $r_c = $db->select($sql_c);
        

        $twoList = [];
        foreach ($r_c as $key => $value) {
            $sql_e = 'select cid,pname,img from lkt_product_class where sid=\''.$value->cid.'\' order by sort desc LIMIT 0,10';
            $r_e = $db->select($sql_e);
            $icons=[];
            if($r_e){
                foreach ($r_e as $ke => $ve) {
                    $imgurl = $img . $ve->img;
                    $icons[$ke] = array('id' => $ve->cid,'name' => $ve->pname,'img' => $imgurl);
                }
            }else{
                $icons=[];
            }

            $ttcid = $value->cid;

            $sql_s = "select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,a.imgurl,c.name from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.product_class like '%-$ttcid-%' and a.status = 0 and a.num >0 group by c.pid  order by a.sort DESC LIMIT 0,10";
            $r_s = $db->select($sql_s);
            $product = [];

            $r_s = empty($r_s)? []:($r_s ? $r_s:[]);

            foreach ($r_s as $k => $v) {
                $imgurl = $img .$v->imgurl;
                $pid = $v->id;
                $price =$v->yprice;
                $price_yh =$v->price;
                $product[$k] = array('id' => $v->id,'name' => $v->product_title,'price' => $price,'price_yh' => $price_yh,'imgurl' => $imgurl,'volume' => $v->volume);
            }
            $twoList['0'] = array('id' => '0','name' => '首页','count' => 1,'twodata' => $shou,'distributor'=>$distributor);
            $twoList[$key+1] = array('id' => $value->cid,'name' => $value->pname,'count' => 1,'twodata' => $product,'icons'=>$icons);
        }
        $sql = "select * from lkt_background_color where status = 1";
        $r = $db -> select($sql);
        if($r){
            $bgcolor = $r[0]->color;
        }else{
            $bgcolor = '#FF6347';
        }


        // 查询插件表里,状态为启用的插件
        $sql = "select * from lkt_plug_ins where status = 1 and type = 0 and software_id = 3";
        $plug = $db->select($sql);
        if($plug){
            foreach ($plug as $k => $v) {
                $v->image = $img . $v->image;
                if($v->name == '钱包'){
                    unset($plug[$k]);
                }
                if($v->name == '积分'){
                    unset($plug[$k]);
                }

                if(strpos($v->name,'红包') !== false){ 
                    if(!$rfhb){
                        unset($plug[$k]);
                    }
                }
            }
        }
        $pmd = [];

        $lkt_set_notice = "select id,name from lkt_set_notice order by time desc";
        $notice = [];
        $res_notice= $db -> select($lkt_set_notice);//公告
        if($res_notice){
            foreach ($res_notice as $key => $value) {
               $notice[$key] = array('url' => $value->id, 'title' => $value->name);
            }
        }
        echo json_encode(array('banner'=>$banner,'notice'=>$notice,'djname'=>'','twoList'=>$twoList,'bgcolor'=>$bgcolor,'plug'=>$plug,'title'=>$title,'logo'=>$logo,'list'=>$pmd));
        exit();
        
    }

    // 加载更多商品
    public function get_more(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $paegr = trim($request->getParameter('page')); //  '显示位置'
        $index = trim($request->getParameter('index')); //  '分类ID'

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

        if(!$paegr){
            $paegr = 1;
        }
        $start = 10*$paegr;
        $end = 10;
        //查询商品并分类显示返回JSON至小程序
        if(!$index){
                    echo json_encode(array('prolist'=>[],'status'=>0));
                    exit;
               
        }else{
            //查询商品并分类显示返回JSON至小程序
            $sql_t = "select a.id,a.product_title,a.volume,min(c.price) as price,c.yprice,a.imgurl,c.name from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.num >0 and a.status = 0 and a.product_class like '%-$index-%' group by c.pid  order by a.sort DESC LIMIT $start,$end";
            $r_s = $db->select($sql_t);
            $product = [];
            if($r_s){
            foreach ($r_s as $k => $v) {
                $imgurl = $img .$v->imgurl;/* end 保存*/
                $pid = $v->id;
                $price =$v->yprice;
                $price_yh =$v->price;
                $product[$k] = array('id' => $v->id,'name' => $v->product_title,'price' => $price,'price_yh' => $price_yh,'imgurl' => $imgurl,'volume' => $v->volume);
            }
                    echo json_encode(array('prolist'=>$product,'status'=>1));
                    exit;
            }else{
                    echo json_encode(array('prolist'=>$product,'status'=>0));
                    exit;
            }
        }

    }
    //抽奖商品显示
    public function draw(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql02 = "select status from lkt_plug_ins  where id = 4 ";
        $r02 = $db -> select($sql02);
        $type =  $r02[0]->status;
        $banner = '';

        if($type == 1){
        //参加抽奖商品
            $datatime = date("Y-m-d H:m:s",time());
            $sql01 = "select b.id,b.product_title,b.volume,b.imgurl,a.draw_brandid,a.start_time,a.end_time,a.price as price11 from lkt_draw as a ,lkt_product_list as b  where b.num > 0 and a.draw_brandid = b.id and  a.start_time <= '".$datatime ."'and a.end_time >= '".$datatime."'";

            $r01 = $db -> select($sql01);
            foreach ($r01 as $key => $value) {
                $draw_brandid = $value->id;
                $sql002 = "select yprice from lkt_configure where num >0 and pid = '$draw_brandid' ";
                $r002 = $db -> select($sql002);
                // var_dump($r01,$value,$sql01,$r002,$sql002);
                $r01[$key]->yprice =$r002[0]->yprice;
                
            }

        }else {
                $r01 = "1";
        }
        echo json_encode(array('r01'=>$r01,'banner'=>$banner,'status'=>1));
                    exit;
    }
}

?>