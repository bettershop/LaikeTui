<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class productAction extends Action {
    /*
    时间2018年03月15日
    修改内容：所有商品购买付款下订单等操作API请求
    修改人：苏涛
    主要功能：处理商品数据，返回购物请求结果
    备注：api-JSON
     */
    public function getDefaultView() {
        $this->execute();
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $m = addslashes(trim($request->getParameter('m')));
        if($m == 'index'){
            $this->index();
        }else if($m == 'add_cart'){
            $this->add_cart();
        }else if($m == 'listdetail'){
            $this->listdetail();
        }else if($m == 'get_more'){
            $this->get_more();
        }else if($m == 'Settlement'){ // 结算页面
            $this->Settlement();
        }else if($m == 'Shopping'){
            $this->Shopping();
        }else if($m == 'delcart'){
            $this->delcart();
        }else if($m == 'delAll_cart'){
            $this->delAll_cart();
        }else if($m == 'to_Collection'){
            $this->to_Collection();
        }else if($m == 'up_cart'){
            $this->up_cart();
        }else if($m == 'payment'){ // 生产订单
            $this->payment();
        }else if($m == 'up_order'){
            $this->up_order();
        }else if($m == 'comment'){
            $this->comment();
        }else if($m == 't_comment'){
            $this->t_comment();
        }else if($m == 'new_product'){
            $this->new_product();
        }else if($m == 'wallet_pay'){
            $this->wallet_pay();
        }else if($m == 'choujiangjiesuan'){//抽奖创建结算页面
            $this->choujiangjiesuan();
        }else if ($m == 'choujiangpayment') {//抽奖创建订单
              $this->choujiangpayment();
        }else if ($m == 'select_size') {
               //属性选择
              $this->select_size();
        }else if ($m == 'save_formid') {
              //普通商品储存from_id 用于发货 退款等操作信息推送
              $this->save_formid();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
    // 获取产品详情
    public function index(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        // 获取产品id
        $id = trim($request->getParameter('pro_id'));
        $openid = trim($request->getParameter('openid'));

        // 根据微信id,查询用户id
        // 获取类别值id，用于区分是抽奖和其他
        $type1 = $request->getParameter('type1');
        $choujiangid = $request->getParameter('choujiangid');
        $wx_id = $request->getParameter('wx_id');

        $t_user_id = $request->getParameter('userid');

        if($type1 == 1){
            $sql0101="select * from lkt_draw where id = '$choujiangid'";
            $re = $db->select($sql0101);
            if($re){
                $price01 = $re[0]->price;
                $type01 =$re[0]->type;
                $start_time = $re[0]->start_time;//活动开始时间
                $end_time =$re[0]->end_time;//活动结束时间
            }else{
                $price01 = '';
                $type01 ='';
                $start_time = '';//活动开始时间
                $end_time ='';//活动结束时间
            }

            $time = date('Y-m-d H:i:s',time());//当前时间
            if($start_time<$time && $end_time < $time){//判断用户进入抽奖详情页面抽奖活动是否结束
                echo json_encode(array('status'=>3,'err'=>'活动已结束!'));
                exit;
            }
        }else{
            $price01 ='';
            $type01 ='';
        }

        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{
                // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        $type = 0;
        $collection_id = '';
        $zhekou = '';

        if($openid){
            $sql = "select * from lkt_user where wx_id = '$openid'";
            $r = $db->select($sql);
            if($r){
                $user_id = $r[0]->user_id;
                // 根据用户id、产品id,获取收藏表信息
                $sql = "select * from lkt_user_collection where user_id = '$user_id' and p_id = '$id'";
                $rr = $db->select($sql);
                if($rr){
                    $type = 1;
                    $collection_id = $rr['0']->id;
                }else{
                    $type = 0;
                    $collection_id = '';
                }
                $time = date("Y-m-d");
                // 根据用户id,在足迹表里插入一条数据
                $sql_collection = "select * from lkt_user_footprint where user_id = '$user_id' and p_id = '$id' and add_time like '$time%' ";
                $rr_collection = $db->select($sql_collection);

                if(empty($rr_collection)){
                    $sql = "insert into lkt_user_footprint(user_id,p_id,add_time) values('$user_id','$id',CURRENT_TIMESTAMP)";
                    $rrr = $db->insert($sql);
                }
            }
        }

        // 根据产品id,查询产品数据
        $sql = "select a.*,c.price,c.yprice,c.attribute,c.img from lkt_product_list AS a LEFT JOIN lkt_configure AS c ON a.id = c.pid where a.id = '$id' and a.status = 0 ";
        $res = $db -> select($sql);
        if(!$res){
            echo json_encode(array('status'=>0,'err'=>'该商品已下架！'));
            exit();
        }else{
            $img_arr =  [];
            $sql_img = "select product_url,id from lkt_product_img where product_id = '$id'";
            $r = $db->select($sql_img);
            if($r){
                foreach ($r as $key => $value) {
                    $img_arr[$key] = $img.$value->product_url;
                }
            }else{
                $img_arr['0'] = $img.$res['0']->imgurl;
            }
            $class =  $res['0'] -> product_class;
            $typestr=trim($class,'-');
            $typeArr=explode('-',$typestr);
            //  取数组最后一个元素 并查询分类名称
            $cid = end($typeArr);
            $sql_p = "select pname from lkt_product_class where cid ='".$cid."'";
            $r_p = $db->select($sql_p);
            $pname = '自营';
            if($r_p){
                $pname = $r_p['0']->pname;
            }
            $pname = $r_p['0']->pname;
            $product = [];
            $imgurl = $img.$res['0']->img;
            $content = $res['0']->content;

            $newa = substr($uploadImg_domain,0,strrpos($uploadImg_domain,'/'));
            if($newa == 'http:/' || $newa == 'https:/' ){
                $newa = $uploadImg_domain;
            }
            $new_content = preg_replace('/(<img.+?src=")(.*?)/',"$1$newa$2", $content);

            $freight_id = $res[0]->freight;
            $sql = "select * from lkt_freight where id = '$freight_id'";
            $r_freight = $db->select($sql);
            if($r_freight){
                $freight = unserialize($r_freight[0]->freight); // 属性
                foreach ($freight as $k => $v){
                    foreach ($v as $k1 => $v1){
                        $freight_list[$k]['freight'] = $v['two'];
                        $freight_list[$k]['freight_name'] = $v['name'];
                    }
                }
                $product['freight'] = $freight[0]['two'];
            }else{
                $product['freight'] = 0.00;
            }
            $s_type = explode(',',$res['0']->s_type);
            $xp = 0;
            $rexiao = 0;
            $tuijian = 0;
            foreach ($s_type as $k1 => $v1){
                if($v1 == 1){
                    $xp = 1;
                }else if($v1 == 2){
                    $rexiao = 1;
                }else if($v1 == 3){
                    $tuijian = 1;
                }
            }
            $product['xp'] = $xp;
            $product['rexiao'] = $rexiao;
            $product['tuijian'] = $tuijian;

            $product['id'] = $res['0']->id;
            $product['shop_id'] = $res['0']->id;
            $product['name'] = $res['0']->product_title;
            $product['intro'] = $res['0']->product_title;
            $product['num'] = $res['0']->num;
            $product['price'] = $res['0']->yprice;
            $product['price_yh'] = $res['0']->price;
            $product['price11'] =$price01 ? $price01:'';
            $product['type01'] =$type01 ? $type01:'';
            $product['photo_x'] = $imgurl;
            $product['photo_d'] =$res['0']->img;
            $product['content'] = $new_content;
            $product['pro_number'] = $res['0']->id;
            $product['company'] = '件';
            $product['cat_name'] = $pname;
            $product['brand'] = '来客推';
            $product['img_arr'] = $img_arr;
            $product['choujiangid'] = $choujiangid? '':$choujiangid;
            $product['volume'] = $res['0']->volume;
            $product['is_zhekou'] = $res['0']->is_zhekou;
            if($type1 == 1){
                $product['type111'] = 1;
                $wx_id =$wx_id ;
            }else{
                $product['type111'] = 2;
                $wx_id ='';
            }

            if(!empty($res[0]->brand_id)){
                $b_id =$res[0]->brand_id;
                $sql01 = "select brand_name from lkt_brand_class where brand_id = '$b_id'";
                $r01 = $db->select($sql01);
            }
            if(!empty($r01)){
                 $product['brand_name'] = $r01[0]->brand_name;
            }else{
                 $product['brand_name'] = '无';
            }

            $sql_c = "select a.id,a.add_time,a.content,a.CommentType,a.size,m.user_name,m.headimgurl from lkt_comments AS a LEFT JOIN lkt_user AS m ON a.uid = m.user_id where a.pid = '$id'";
            $r_c = $db->select($sql_c);
            $arr=[];
            if($r_c){
                foreach ($r_c as $key => $value) {
                    $va = (array)$value;
                    $va['time'] = substr($va['add_time'],0,10);
                    //-------------2018-05-03  修改  作用:返回评论图片
                    $comments_id = $va['id'];
                    $comments_sql = "select comments_url from lkt_comments_img where comments_id = '$comments_id' ";
                    $comment_res = $db->select($comments_sql);
                    $va['images'] ='';
                    if($comment_res){
                        $va['images'] = $comment_res;
                        $array_c = [];
                        foreach ($comment_res as $kc => $vc) {
                            $url = $vc->comments_url;
                            $array_c[$kc] = array('url' =>$img.$url);
                        }
                        $va['images'] = $array_c;
                    }
                    //-------------2018-07-27  修改
                    $ad_sql = "select content from lkt_reply_comments where cid = '$comments_id' and uid = 'admin' ";
                    $ad_res = $db->select($ad_sql);
                    if($ad_res){
                        $reply_admin = $ad_res[0]->content;
                    }else{
                        $reply_admin = '';
                    }

                    $va['reply'] = $reply_admin;

                    $obj = (object)$va;
                    $arr[$key] = $obj;
                }
            }

            $commodityAttr = [];
            $sql_size = "select * from lkt_configure where pid = '$id' AND num > 0";
            $r_size = $db->select($sql_size);
            $array_price = [];
            $array_yprice = [];
            $skuBeanList = [];
            $attrList = [];
            if ($r_size) {
                $attrList = [];
                $a = 0;
                $attr = [];
                foreach ($r_size as $key => $value) {
                    $array_price[$key] = $value->price;
                    $array_yprice[$key] = $value->yprice;
                    $attribute = unserialize($value->attribute);
                    $attnum = 0;
                    $arrayName = [];
                    foreach ($attribute as $k => $v) {
                        if(!in_array($k, $arrayName)){
                            if ($k!='rid') {
                                array_push($arrayName, $k);
                                $kkk = $attnum++;
                                $attrList[$kkk] = array('attrName' => $k,'attrType' => '1','id' => md5($k),'attr' => [],'all'=>[]);
                            }
                        }
                    }
                }

                foreach ($r_size as $key => $value) {
                    $attribute = unserialize($value->attribute);
                    $attributes = [];
                    $name = '';
                    foreach ($attribute as $k => $v) {
                        if($v){
                            $attributes[] = array('attributeId' => md5($k), 'attributeValId' => md5($v));
                            $name .= $v;
                        }
                       
                    }

                    $cimgurl = $img.$value->img;

                    $skuBeanList[$key] = array('name' => $name,'imgurl' => $cimgurl,'cid' => $value->id,'price' => $value->price,'count' => $value->num,'attributes' => $attributes);
                    

                    for ($i=0; $i < count($attrList); $i++) {
                        $attr = $attrList[$i]['attr'];
                        $all = $attrList[$i]['all'];
                        foreach ($attribute as $k => $v) {
                            if($attrList[$i]['attrName'] == $k){
                                $attr_array = array('attributeId' => md5($k), 'id' =>md5($v), 'attributeValue' => $v, 'enable' => false, 'select' => false);

                                if(empty($attr)){
                                    array_push($attr, $attr_array);
                                    array_push($all, $v);
                                }else{
                                    if(!in_array($v, $all)){
                                        array_push($attr, $attr_array);
                                        array_push($all, $v);
                                    }
                                }
                            }
                        }
                        $attrList[$i]['all'] =$all;
                        $attrList[$i]['attr'] =$attr;
                    }
                }
            }
            //排序
            asort($array_price);
            asort($array_yprice);
            //获取价格区间并返回
            $qj_price = reset($array_price)==end($array_price)? reset($array_price):reset($array_price).'-'.end($array_price);
            $qj_yprice = reset($array_yprice)==end($array_yprice)? reset($array_yprice):reset($array_yprice).'-'.end($array_yprice);
            //返回JSON             $skuBeanList = []; $attrList = [];
            $share = array('friends' => true, 'friend' => true);
            echo json_encode(array('status'=>1,'pro'=>$product,'qj_price'=>$qj_price,'qj_yprice'=>$qj_yprice,'attrList'=>$attrList,'skuBeanList'=>$skuBeanList,'collection_id'=>$collection_id,'comments'=>$arr,'type'=>$type,'wx_id' =>$wx_id,'share'=>$share,'zhekou'=>$zhekou));
            exit();
        }
    }

    //普通商品储存from_id 用于发货 退款等操作信息推送
    public function save_formid(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $uid = addslashes(trim($request->getParameter('userid')));
        $formid = addslashes(trim($request->getParameter('from_id')));
        $lifetime = date('Y-m-d H:i:s',time() + 7*24*3600);
        if($formid != 'the formId is a mock one' && $formid != ''){          
            $addsql = "insert into lkt_user_fromid(open_id,fromid,lifetime) values('$uid','$formid','$lifetime')";
            $addres = $db -> insert($addsql);
            echo json_encode(array('status'=>1,'succ'=>$addres));
        }
    }

    // 加入购物车
    public function add_cart(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $Uid = trim($request->getParameter('uid')); //  '微信id',
        $Goods_id = trim($request->getParameter('pid')); //  '产品id',
        $Goods_num = trim($request->getParameter('num')); //  '数量',
        $size_id =trim($request->getParameter('sizeid')); //  '商品属性id',
        $pro_type =trim($request->getParameter('pro_type')); //  '点击类型',
        if(empty($Uid) || empty($Goods_id) || empty($Goods_id) || empty($size_id)){
            echo json_encode(array('status'=>0,'info'=>'添加失败请重新提交!!'));
        }else{
            $sql = "select user_id from lkt_user where wx_id = '$Uid'";
            $r_1 = $db->select($sql);
            if($r_1){
                $user_id = $r_1[0]->user_id;
            }else{
                $user_id = '';
            }

            $sql_k = "select num from lkt_configure where pid = '$Goods_id' and num >0";
            $res_k = $db->select($sql_k);
            if($res_k){
                $num = $res_k[0]->num;
            }else{
                $num = 0;
            }
            if($num >= $Goods_num){
                //查询购物车是否有过改商品，有则修改 无则新增
                $sql_c = "select Goods_num,id from lkt_cart where Uid = '$Uid' and Goods_id = '$Goods_id' and Size_id = '$size_id'";
                $res = $db->select($sql_c);
                if ($res) {
                    //根据点击的类型进行修改
                    if($pro_type == 'buynow'){
                        $sql_u = "update lkt_cart set Goods_num = '$Goods_num' where Uid = '$Uid' and Goods_id = '$Goods_id' and Size_id = '$size_id'";
                        $r_u = $db->update($sql_u);
                    }else{
                        $sql_u = "update lkt_cart set Goods_num = Goods_num+'$Goods_num' where Uid = '$Uid' and Goods_id = '$Goods_id' and Size_id = '$size_id'";
                        $r_u = $db->update($sql_u);
                    }
                    echo json_encode(array('status'=>1,'cart_id'=>$res['0']->id));
                }else{
                    $sql = "insert into lkt_cart (user_id,Uid,Goods_id,Goods_num,Create_time,Size_id) values('$user_id','$Uid','$Goods_id','$Goods_num',CURRENT_TIMESTAMP,$size_id) ";
                    $r = $db -> insert($sql,'last_insert_id');
                    if($r){
                        echo json_encode(array('status'=>1,'cart_id'=>$r));
                    }else{
                        echo json_encode(array('status'=>0,'err'=>'添加失败请重新提交!'));
                    }
                }                  
            }else{
                echo json_encode(array('status'=>0,'err'=>'库存不足！'));
            }
        }
        exit;
    }
    public function listdetail(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = trim($request->getParameter('cid')); //  '分类ID'
        $paegr = trim($request->getParameter('page')); //  '页面'
        $select = trim($request->getParameter('select')); //  选中的方式 0 默认  1 销量   2价格
        if($select == 0){
             $select = 'a.add_date'; 
        }elseif ($select == 1) {
             $select = 'a.volume'; 
        }else{
             $select = 'c.price'; 
        }

        $sort = trim($request->getParameter('sort')); // 排序方式  1 asc 升序   0 desc 降序
        if($sort){
             $sort = ' asc '; 
        }else{
             $sort = ' desc '; 
        }
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }
        if(!$paegr){
            $paegr = 1;
        }
        $start = ($paegr-1)*10;
        $end = $paegr*10;
        $sql = 'select a.id,a.product_title,volume,c.price,c.yprice,c.img,a.s_type,c.id AS sizeid from lkt_product_list AS a LEFT JOIN lkt_configure AS c ON a.id = c.pid where a.product_class like \'%-'.$id."-%' and a.status = 0 order by $select $sort LIMIT $start,$end ";
        $r = $db->select($sql);
        if($r){
            $product = [];
            foreach ($r as $k => $v) {
                $imgurl = $img.$v->img;/* end 保存*/
                $product[$k] = array('id' => $v->id,'name' => $v->product_title,'price' => $v->yprice,'price_yh' => $v->price,'imgurl' => $imgurl,'size'=>$v->sizeid,'volume' => $v->volume,'s_type' => $v->s_type);
            }
            echo json_encode(array('status'=>1,'pro'=>$product));
            exit;
        }else{
            echo json_encode(array('status'=>0,'err'=>'没有了！'));
            exit;
        }
    }
    // 加载更多商品
    public function get_more(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = trim($request->getParameter('cid')); //  '分类ID'
        $paegr = trim($request->getParameter('page')); //  '分页显示'
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }
        if(!$paegr){
            $paegr = 1;
        }
        $start = ($paegr-1)*10;
        $end = $paegr*10;
        $sql = 'select a.id,a.product_title,a.volume,c.price,c.yprice,c.img,c.id AS sizeid from lkt_product_list AS a LEFT JOIN lkt_configure AS c ON a.id = c.pid where a.product_class like \'%-'.$id.'-%\' and c.num >0 order by a.sort LIMIT $start,$end';
        $r = $db->select($sql);
        if($r){
            $product = [];
            foreach ($r as $k => $v) {
                $imgurl = $img.$v->img;/* end 保存*/
                $product[$k] = array('id' => $v->id,'name' => $v->product_title .$names,'price' => $v->yprice,'size'=>$v->sizeid,'price_yh' => $v->price,'imgurl' => $imgurl,'volume' => $v->volume);
            }
            echo json_encode(array('status'=>1,'pro'=>$product));
            exit;
        }else{
            echo json_encode(array('status'=>0,'pro'=>''));
            exit;
        }
    }

    public function freight($freight,$num,$address,$db)
    {
        $sql = "select * from lkt_freight where id = '$freight'";
        $r_1 = $db->select($sql);
        if($r_1){
            $rule = $r_1[0];
            $yunfei = 0;
            if(empty($address)){
                return 0;
            }else{
                $sheng = $address['sheng'];
                $sql2 = "select G_CName from admin_cg_group where GroupID = '$sheng'";
                $r_2 = $db->select($sql2);
                if($r_2){
                    $city = $r_2[0]->G_CName;
                    $rule_1 = $r_1[0]->freight;
                    $rule_2 = unserialize($rule_1);
                    
                    foreach ($rule_2 as $key => $value) {
                        $citys_str = $value['name'];
                        $citys_array=explode(',',$citys_str);
                        $citys_arrays = [];
                        foreach ($citys_array as $k => $v) {
                            $citys_arrays[$v] = $v;
                        }
                        if(array_key_exists($city , $citys_arrays)){
                            if($num > $value['three']){
                                $yunfei += $value['two']; 
                                $yunfei += ($num-$value['three'])*$value['four']; 
                            }else{
                                $yunfei += $value['two']; 
                            }
                        }
                    }
                    return $yunfei;
                }else{
                   return 0;
                }
            }
        }else{
            return 0;
        }
    }


    // 进入结算页面
    public function Settlement(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $cart_id = trim($request->getParameter('cart_id')); //  购物车id
        $uid = trim($request->getParameter('uid')); // 微信id
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        //地址
        $address = [];
        //计算运费
        $yunfei = 0;
        // 根据微信id,查询用户id
        $sql_user = 'select user_id,money,consumer_money from lkt_user where wx_id=\''.$uid.'\'';
        $r_user = $db->select($sql_user);
        if($r_user){
            $userid = $r_user['0']->user_id; // 用户id
            $user_money = $r_user['0']->money; // 用户余额
            $user_consumer_money = $r_user['0']->consumer_money; // 用户消费金
        }else{
            $userid = ''; // 用户id
            $user_money = ''; // 用户余额
            $user_consumer_money = ''; // 用户消费金
        }

        // 根据用户id,查询收货地址
        $sql_a = 'select id from lkt_user_address where uid=\''.$userid.'\'';
        $r_a = $db->select($sql_a);
        if(!empty($r_a)){
            $arr['addemt']=0; // 有收货地址
            // 根据用户id、默认地址,查询收货地址信息
            $sql_e = 'select * from lkt_user_address where uid=\''.$userid.'\' and is_default = 1';
            $r_e = $db->select($sql_e);
            if(!empty($r_e)){
                $arr['adds'] = (array)$r_e['0']; // 收货地址
            }else{
                // 根据用户id、默认地址,查询收货地址信息
                $aaaid = $r_a[0]->id;
                $sql_q = "select * from lkt_user_address where id= '$aaaid'";
                $r_e = $db->select($sql_q);
                $arr['adds'] = (array)$r_e['0']; // 收货地址 
                $sql_u = "update lkt_user_address set is_default = 1 where id = '$aaaid'";
                $db->update($sql_u);
            }
            $address = (array)$r_e['0']; // 收货地址
        }else{
            $arr['addemt']=1; // 没有收货地址
            $arr['adds'] = ''; // 收货地址
        }

        $typestr=trim($cart_id,','); // 移除两侧的逗号
        $typeArr=explode(',',$typestr); // 字符串打散为数组
        //  取数组最后一个元素 并查询分类名称
        $zong =0;

        //新增分销分销等级商品不能再次购买
        $status= [];
        $products = [];
        //查询是否是会员卡商品 限制支付方式只能为余额和微信
        $distributor_products = [];
        
        //控制优惠方式
        $discount = true;
        $pstuat = true;

        $usort = 0;

        foreach ($typeArr as $key => $value) {
            // 联合查询返回购物信息
            $sql_c = "select a.Goods_num,a.Goods_id,a.id,m.product_title,m.volume,c.price,c.attribute,c.img,c.yprice,m.freight,m.product_class from lkt_cart AS a LEFT JOIN lkt_product_list AS m ON a.Goods_id = m.id LEFT JOIN lkt_configure AS c ON a.Size_id = c.id  where c.num >0 and m.status ='0' and a.id = '$value'";
            $r_c = $db->select($sql_c);
            if($r_c){
                $product = (array)$r_c['0']; // 转数组
                $attribute = unserialize($product['attribute']);
                $product_id[] = $product['Goods_id'];
                $product_class[] = $product['product_class'];
                $size = '';
                foreach ($attribute as $ka => $va) {
                    if($va){
                        $size .= ' '.$va;
                    }
                    
                }
                $Goods_id = $product['Goods_id'];
                if(in_array($Goods_id, $products)){
                    $pstuat = false;
                    $status_id = $Goods_id;
                }

                if(array_key_exists($Goods_id, $distributor_products)){ // 检查数组里是否有指定的键名或索引
                    $discount = false;
                    $grade_id = $distributor_products[$Goods_id];
                    if($grade_id){
                        $sql_grade = "select sort from lkt_distribution_grade where id = '$grade_id' ";
                        $r_grade = $db -> select($sql_grade);
                        if($r_grade){
                            $gsort = $r_grade[0]->sort;
                            if($gsort <= $usort){
                                echo json_encode(array('status'=>0,'err'=>'存在无法购买的商品！'));
                                exit;
                                break;
                            }
                        }
                    }
                }
                //计算运费
                $yunfei = $yunfei + $this->freight($product['freight'],$product['Goods_num'],$address,$db);

                $product['photo_x'] = $img.$product['img'];/* 拼接图片链接*/
                $num = $product['Goods_num']; // 产品数量
                $price = $product['price']; // 产品价格
                $product['size'] = $size; // 产品价格
                $zong += $num*$price; // 产品总价
                $res[$key] = $product;
            }else{
                $res[$key] = '';
                $yunfei = 0;
                $zong = 0;
            }
        }

        // 查询自动满减设置
        $sql = "select * from lkt_subtraction where id = 1";
        $r_subtraction = $db->select($sql);
        $subtraction = [];
        if($r_subtraction){
            $subtraction = unserialize($r_subtraction[0]->subtraction); // 自动满减

            if($r_subtraction[0]->status == 1){
                $man_money = $r_subtraction[0]->man_money; // 满多少包邮
                $region = $r_subtraction[0]->region; // 不包邮地区
                $region_list = explode(',',$region);
                if($man_money <= $zong){ // 当商品总价满足 包邮限制
                    $sql = "select G_CName from admin_cg_group where GroupID = ".$address['sheng'];
                    $r_address = $db->select($sql);
                    if($r_address){
                        $G_CName = $r_address[0]->G_CName;
                        if(in_array($G_CName, $region_list)){
                            $arr['freight'] = $yunfei; // 运费
                        }else{
                            $arr['freight'] = 0; // 运费
                        }
                    }else{
                        $arr['freight'] = 0; // 运费
                    }
                }else{ // 当订单总价不满足 包邮限制
                    $arr['freight'] = $yunfei; // 运费
                }
            }else{
                $arr['freight'] = $yunfei; // 运费
            }
        }

        $order_zong = $zong + $yunfei; // 订单总价
        $reduce_name = '';
        $reduce = 0;
        if($subtraction){
            foreach ($subtraction as $kk => $vv){
                foreach ($vv as $kk1 => $vv1){
                    if($order_zong > $kk1){
                        $reduce_name = '满'.$kk1.'减'.$vv1;
                        $reduce = $vv1;
                        break;
                    }
                }
            }
        }
        $arr['name'] = $reduce_name;
        $arr['reduce_money'] = $reduce;

        $order_zong = $order_zong - $reduce;
        if($pstuat){
            $arr['price'] = $zong; // 产品总价
            $arr['pro'] = $res; // 产品信息

            $time = date("Y-m-d H:i:s"); // 当前时间

            $scoresql = 'select lever,ordernum,scorenum from lkt_setscore order by lever';  //查询消费金参数
            $scoremsg = $db -> select($scoresql);
            if($scoremsg){
                foreach ($scoremsg as $k => $v) {
                    if($v -> lever < 0){
                        $arr['scorebl'] = $v -> ordernum;
                        unset($scoremsg[$k]);
                    }
                }
                $arr['scorebuy'] = $scoremsg;
            }else{
                $arr['scorebuy'] = '';
            }


            // 根据用户id,查询优惠券状态为 (使用中)
            $sql = "select * from lkt_coupon where user_id = '$userid' and type = 1";
            $r = $db->select($sql);
            if($r){
                $r = $r;
            }else{
                $r = '';
            }
            if($r){
                foreach ($r as $k => $v) {
                    $id = $v->id; // 优惠券id
                    // 根据优惠券id,查询订单表(查看优惠券是否绑定)
                    $sql = "select id from lkt_order where coupon_id = '$id' ";
                    $rr = $db->select($sql);

                    if(empty($rr)){ // 没有数据,表示优惠券没绑定
                        $hid = $v->hid; // 活动id
                        $money = $v->money; // 优惠券金额
                        $sql = "select * from lkt_coupon_activity where id = '$hid'";
                        $rr1 = $db->select($sql);
                        $activity_type = $rr1[0]->activity_type; // 类型
                        $product_class_id = $rr1[0]->product_class_id; // 分类id
                        $product_id1 = $rr1[0]->product_id; // 商品id
                        $z_money = $rr1[0]->z_money; // 满减金额
                        if($activity_type == 1){ // 当活动为注册类型
                            if($money >= $order_zong){
                                // 当优惠券金额比总价格高时,修改优惠券状态为(未使用)
                                $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                $db->update($sql);
                                $arr['coupon_id'] = ''; // 付款金额
                                $arr['money'] = ''; // 优惠券金额
                                $arr['coupon_money'] = $order_zong; // 付款金额
                                $arr['user_money'] = $user_money; // 用户余额
                                $arr['discount'] = $discount; // 优惠控制
                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                echo json_encode(array('status'=>1,'arr'=>$arr));
                                exit;
                            }else{
                                $arr['coupon_id'] = $id; // 付款金额
                                $arr['money'] = $v->money; // 优惠券金额
                                $arr['coupon_money'] = $order_zong - $money; // 付款金额
                                $arr['user_money'] = $user_money; // 用户余额
                                $arr['discount'] = $discount; // 优惠控制
                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                echo json_encode(array('status'=>1,'arr'=>$arr));
                                exit;
                            }
                        }else if($activity_type == 3){ // 当活动为满减类型
                            if($order_zong < $z_money){
                                // 当订单总价格不满足满减金额时,修改优惠券状态为(未使用)
                                $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                $db->update($sql);
                                $arr['coupon_id'] = ''; // 付款金额
                                $arr['money'] = ''; // 优惠券金额
                                $arr['coupon_money'] = $order_zong; // 付款金额
                                $arr['user_money'] = $user_money; // 用户余额
                                $arr['discount'] = $discount; // 优惠控制
                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                echo json_encode(array('status' => 1, 'arr' => $arr));
                            }else{
                                $arr['coupon_id'] = $id; // 付款金额
                                $arr['money'] = $v->money; // 优惠券金额
                                $arr['coupon_money'] = $order_zong - $money; // 付款金额
                                $arr['user_money'] = $user_money; // 用户余额
                                $arr['discount'] = $discount; // 优惠控制
                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                echo json_encode(array('status'=>1,'arr'=>$arr));
                                exit;
                            }
                        }else{ // 活动类型为节日/活动
                            if($product_class_id == 0){ // 当没设置商品分类
                                if($money >= $order_zong){
                                    // 当优惠券金额比总价格高时,修改优惠券状态为(未使用)
                                    $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                    $db->update($sql);
                                    $arr['coupon_id'] = ''; // 付款金额
                                    $arr['money'] = ''; // 优惠券金额
                                    $arr['coupon_money'] = $order_zong; // 付款金额
                                    $arr['user_money'] = $user_money; // 用户余额
                                    $arr['discount'] = $discount; // 优惠控制
                                    $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                    echo json_encode(array('status'=>1,'arr'=>$arr));
                                    exit;
                                }else{
                                    $arr['coupon_id'] = $id; // 付款金额
                                    $arr['money'] = $v->money; // 优惠券金额
                                    $arr['coupon_money'] = $order_zong - $money; // 付款金额
                                    $arr['user_money'] = $user_money; // 用户余额
                                    $arr['discount'] = $discount; // 优惠控制
                                    $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                    echo json_encode(array('status'=>1,'arr'=>$arr));
                                    exit;
                                }
                            }else{ // 当设置商品分类
                                // 根据活动指定的商品分类查询所有商品的分类
                                $sql = "select product_class from lkt_product_list where product_class like '%$product_class_id%'";
                                $rr_1 = $db->select($sql);
                                if($rr_1){
                                    $calss_status = 1; // 商品属于优惠券指定的分类
                                    foreach ($rr_1 as $k1 => $v1){
                                        $rr_list[$k1] = $v1->product_class;
                                    }
                                    foreach ($product_class as $k2 => $v2){
                                        if(!in_array($v2, $rr_list)){
                                            $calss_status = 0; // 商品不属于优惠券指定的分类
                                            break;
                                        }
                                    }
                                    if($calss_status == 0){ // 当有商品不属于优惠券指定的分类
                                        // 根据优惠券id,修改优惠券状态（未使用）
                                        $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                        $db->update($sql);
                                        $arr['coupon_id'] = ''; // 付款金额
                                        $arr['money'] = ''; // 优惠券金额
                                        $arr['coupon_money'] = $order_zong; // 付款金额
                                        $arr['user_money'] = $user_money; // 用户余额
                                        $arr['discount'] = $discount; // 优惠控制
                                        $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                    }else{
                                        $product_status = 1; // 商品属于优惠券指定商品
                                        if ($product_id1 != 0) { // 当优惠券指定了商品
                                            foreach ($product_id as $k3 => $v3){
                                                if ($product_id1 != $v3) {
                                                    $product_status = 0;
                                                    break;
                                                }
                                            }
                                            if($product_status == 0){
                                                // 根据优惠券id,修改优惠券状态（未使用）
                                                $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                                $db->update($sql);
                                                $arr['coupon_id'] = ''; // 付款金额
                                                $arr['money'] = ''; // 优惠券金额
                                                $arr['coupon_money'] = $order_zong; // 付款金额
                                                $arr['user_money'] = $user_money; // 用户余额
                                                $arr['discount'] = $discount; // 优惠控制
                                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                            }else{
                                                if ($money >= $order_zong) {
                                                    // 当优惠券金额比总价格高时,修改优惠券状态为(未使用)
                                                    $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                                    $db->update($sql);
                                                    $arr['coupon_id'] = ''; // 付款金额
                                                    $arr['money'] = ''; // 优惠券金额
                                                    $arr['coupon_money'] = $order_zong; // 付款金额
                                                    $arr['user_money'] = $user_money; // 用户余额
                                                    $arr['discount'] = $discount; // 优惠控制
                                                    $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                                } else {
                                                    $arr['coupon_id'] = $id; // 付款金额
                                                    $arr['money'] = $v->money; // 优惠券金额
                                                    $arr['coupon_money'] = $order_zong - $money; // 付款金额
                                                    $arr['user_money'] = $user_money; // 用户余额
                                                    $arr['discount'] = $discount; // 优惠控制
                                                    $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                                }
                                            }
                                        }else { // 当优惠券没有指定商品
                                            if ($money >= $order_zong) {
                                                // 当优惠券金额比总价格高时,修改优惠券状态为(未使用)
                                                $sql = "update lkt_coupon set type = 0 where id = '$id'";
                                                $db->update($sql);
                                                $arr['coupon_id'] = ''; // 付款金额
                                                $arr['money'] = ''; // 优惠券金额
                                                $arr['coupon_money'] = $order_zong; // 付款金额
                                                $arr['user_money'] = $user_money; // 用户余额
                                                $arr['discount'] = $discount; // 优惠控制
                                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                            } else {
                                                $arr['coupon_id'] = $id; // 付款金额
                                                $arr['money'] = $v->money; // 优惠券金额
                                                $arr['coupon_money'] = $order_zong - $money; // 付款金额
                                                $arr['user_money'] = $user_money; // 用户余额
                                                $arr['discount'] = $discount; // 优惠控制
                                                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else{ // 有数据
                        $arr['coupon_id'] = ''; // 付款金额
                        $arr['money'] = ''; // 优惠券金额
                        $arr['coupon_money'] = $order_zong; // 付款金额
                        $arr['user_money'] = $user_money; // 用户余额
                        $arr['discount'] = $discount; // 优惠控制
                        $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                    }
                }
                echo json_encode(array('status' => 1, 'arr' => $arr));
                exit;
            }else{
                $arr['money'] = '';
                $arr['coupon_id'] = '';
                $arr['coupon_money'] = $order_zong; // 付款金额
                $arr['user_money'] = $user_money; // 用户余额
                $arr['discount'] = $discount; // 优惠控制
                $arr['user_consumer_money'] = $user_consumer_money; // 用户消费金
                echo json_encode(array('status'=>1,'arr'=>$arr));
                exit;
            }
        }else{
            if($status[$status_id] == 0){
                echo json_encode(array('status'=>0,'err'=>'您有会员套餐未付款订单！'));
                exit;
            }else{
                echo json_encode(array('status'=>0,'err'=>'存在无法购买的商品！'));
                exit;  
            }

        }
    }
    // 显示购物车列表
    public function Shopping(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        $arr = [];
        $uid = trim($request->getParameter('user_id')); //  '分类ID'
        $sql_c = 'select a.*,c.price,c.attribute,c.img,c.num as pnum,m.product_title,c.id AS sizeid from lkt_cart AS a LEFT JOIN lkt_product_list AS m  ON a.Goods_id = m.id LEFT JOIN lkt_configure AS c ON a.Size_id = c.id where c.num >0 and a.Uid = \''.$uid.'\'';
        
        $r_c = $db->select($sql_c);
        if($r_c){
            foreach ($r_c as $key => $value) {
                $imgurl = $img.$value->img;/* end 保存*/

                $attribute = unserialize($value->attribute);
                $size = '';
                foreach ($attribute as $ka => $va) {
                    $size .= ' '.$va;
                }

                $arr[$key] = array('id' => $value->id,'uid' => $uid,'pnum' => $value->pnum,'sizeid' => $value->sizeid,'pid' => $value->Goods_id,'size' => $size,'price' => $value->price,'num' => $value->Goods_num,'pro_name' => $value->product_title,'imgurl' =>$imgurl);
            }
        }

        echo json_encode(array('status' => 1, 'cart' => $arr));
        exit;
    }

    // 清空购物车
    public function delAll_cart(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $user_id = addslashes(trim($request->getParameter('user_id')));
        $sql = 'delete from lkt_cart where Uid="'.$user_id.'"';
        $res = $db -> delete($sql);
        if($res){
            echo json_encode(array('status'=>1,'succ'=>'操作成功!'));
            exit;
        }else{
            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit;
        }
    }


    // 删除购物车指定商品  
    public function delcart(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $carts = addslashes(trim($request->getParameter('carts')));

        $cartstr=trim($carts,','); // 移除两侧的逗号
        $cartArr=explode(',',$cartstr); // 字符串打散为数组
        //循环删除指定的购物车商品
        foreach ($cartArr as $key => $value) {
            $sql = 'delete from lkt_cart where id="'.$value.'"';
            $res = $db -> delete($sql);
        }

        if($res){
            echo json_encode(array('status'=>1,'succ'=>'操作成功!'));
            exit;
        }else{
            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit;
        }
    }

    // 移动购物车指定商品去收藏  
    public function to_Collection(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        //购物车商品
        $carts = $request->getParameter('carts');
        //用户id
        $userid = addslashes(trim($request->getParameter('user_id')));

        $cartstr=trim($carts,','); // 移除两侧的逗号
        $cartArr=explode(',',$cartstr); // 字符串打散为数组
        //循环移动并删除指定的购物车商品
        foreach ($cartArr as $key => $value) {
            //查询商品id
            $csql = "select Goods_id from lkt_cart where id='$value' ";
            $cres = $db -> select($csql);
            if($cres){
                $pid = $cres[0]->Goods_id;
            }else{
                $pid = 0;
            }
            //添加至收藏
            $this->addFavorites($userid,$pid);
            //删除指定购物车id
            $sql = 'delete from lkt_cart where id="'.$value.'"';
            $res = $db -> delete($sql);
        }

        if($res){
            echo json_encode(array('status'=>1,'succ'=>'操作成功!'));
            exit;
        }else{
            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit;
        }
    }

    public function addFavorites($openid,$pid){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = "select user_id from lkt_user where wx_id = '$openid'";
        $r = $db->select($sql);
        if($r){
            $user_id = $r[0]->user_id;
            // 根据用户id,产品id,查询收藏表
            $sql = "select * from lkt_user_collection where user_id = '$user_id' and p_id = '$pid'";
            $r = $db->select($sql);
            if (!$r) {
                // 在收藏表里添加一条数据
                $sql = "insert into lkt_user_collection(user_id,p_id,add_time) values('$user_id','$pid',CURRENT_TIMESTAMP)";
                $r = $db->insert($sql);
            }
        }
    }

    // 用户修改购物车数量操作
    public function up_cart(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $cart_id = trim($request->getParameter('cart_id'));
        $num = trim($request->getParameter('num'));
        $user_id = trim($request->getParameter('user_id'));

        $sql_num = "select c.num from lkt_cart as a LEFT JOIN lkt_configure AS c ON a.Size_id = c.id where a.id = '$cart_id'";
        $r_num = $db->select($sql_num);
        if($r_num){
            $pnum = $r_num[0]->num;
            if($pnum > $num){
                $sql_u = "update lkt_cart set Goods_num = '$num' where id = '$cart_id' and Uid = '$user_id'";
                $r_u = $db->update($sql_u);
                if($r_u){
                    echo json_encode(array('status'=>1,'succ'=>'操作成功!'));
                    exit;
                }else{
                    echo json_encode(array('status'=>0,'err'=>'操作失败!'));
                    exit;
                }
            }else{
                echo json_encode(array('status'=>0,'err'=>'库存不足!'));
                exit;
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit;
        }
    }

    //余额支付
    public function wallet_pay(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $uid = trim($request->getParameter('uid')); // 微信id
        $total = trim($request->getParameter('total')); // 付款余额
        // 根据微信id,查询用户列表(支付密码,钱包余额,用户id)
        $sql_user = "select password,money,user_id from lkt_user where wx_id='$uid'";
        $r_user = $db->select($sql_user);
        if($r_user){
            $user_money = $r_user['0']->money; // 用户余额
            $userid = $r_user['0']->user_id; // 用户id

            if($user_money >= $total){
                // 根据微信id,修改用户余额
                if($total > 0){
                    $sql = "update lkt_user set money = money-'$total' where user_id = '$userid'";
                    $r = $db->update($sql);
                    $event = $userid.'使用了'.$total.'元余额';
                    $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$userid','$total','$user_money','$event',4)";
                    $rr = $db->insert($sqll);
                }
                echo json_encode(array('status' => 1, 'succ' => '扣款成功!'));
            }else{
                echo json_encode(array('status' => 0, 'err' => '余额不足！'));
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'网络繁忙!'));
            exit;
        }
        exit;
    }

    // 创建订单操作
    public function payment(){
        $db = DBAction::getInstance();
        //开启事务
        $db->begin();

        $request = $this->getContext()->getRequest();
        $cart_id = trim($request->getParameter('cart_id')); // 购物车id
        $uid = trim($request->getParameter('uid')); // 微信id
        $type = trim($request->getParameter('type')); // 用户支付方式
        $coupon_id = trim($request->getParameter('coupon_id')); // 优惠券id
        $r_name =  trim($request->getParameter('name')); // 自动满减金额名称
        $reduce_money =  trim($request->getParameter('reduce_money')); // 自动满减金额
        $allow = trim($request->getParameter('allow')); // 用户使用积分
        $red_packet = trim($request->getParameter('red_packet')); // 用户使用红包
        $total = $_POST['total']; // 付款金额
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        if($r_name){
            $coupon_activity_name = $r_name;
        }else{
            $coupon_activity_name = '';
        }
        // 根据微信id,查询用户id
        $sql_user = 'select user_id,money from lkt_user where wx_id=\''.$uid.'\'';
        $r_user = $db->select($sql_user);
        if($r_user){
            $userid = $r_user['0']->user_id; // 用户id
            $user_money = $r_user['0']->money; // 用户余额
        }else{
            $userid = ''; // 用户id
            $user_money = 0; // 用户余额
        }

        
        if($type == 'wallet_Pay' && $user_money < $total){ // 当余额小于付款金额
            echo json_encode(array('status' => 0, 'err' => '余额不足！'));
            exit;
        }else{
            // 根据用户id、默认地址,查询地址信息
            $sql_a = 'select * from lkt_user_address where uid=\''.$userid.'\' and is_default = 1';
            $r_a = $db->select($sql_a);
            if($r_a){
                $name = $r_a['0']->name; // 联系人
                $mobile = $r_a['0']->tel; // 联系电话
                $address = $r_a['0']->address_xq; // 加省市县的详细地址
                $sheng = $r_a['0']->sheng; // 省
                $shi = $r_a['0']->city; // 市
                $xian = $r_a['0']->quyu; // 县
            }else{
                $name = ''; // 联系人
                $mobile = ''; // 联系电话
                $address = ''; // 加省市县的详细地址
                $sheng = ''; // 省
                $shi = ''; // 市
                $xian = ''; // 县
            }

            $z_num = 0;
            $z_price = 0;
            $sNo = $this ->order_number(); // 生成订单号
            // 根据省的id,查询省名称
            $sql = "select G_CName from admin_cg_group where GroupID = '$sheng'";
            $r1 = $db->select($sql);
            if($r1){
                $G_CName = $r1[0]->G_CName; // 省
            }else{
                $G_CName = '';
            }

            $z_freight = 0; // 总运费

            //  拆分购物ID 依次插入数据库
            $typestr=trim($cart_id,',');
            $typeArr=explode(',',$typestr);
            foreach ($typeArr as $key => $value) {
                // 联合查询返回购物信息
                $sql_c = "select a.Size_id,a.Goods_num,a.Goods_id,a.id,m.product_title,m.volume,m.freight,c.price,c.attribute,c.img,c.yprice,c.unit from lkt_cart AS a LEFT JOIN lkt_product_list AS m ON a.Goods_id = m.id LEFT JOIN lkt_configure AS c ON a.Size_id = c.id where a.id = '$value' and c.num >= a.Goods_num ";
                $r_c = $db->select($sql_c);
                if(!empty($r_c)){
                    $product = (array)$r_c['0']; // 转数组
                    $product['photo_x'] = $img.$product['img'];/* 拼接图片链接*/
                    $num = $product['Goods_num']; // 商品数量
                    $z_num += $num; // 商品数量
                    $price = $product['price']; // 商品价格
                    $z_price += $num*$price; // 总价
                    $pid = $product['Goods_id']; // 商品id
                    $product_title = $product['product_title']; // 商品名称
                    $size_id = $product['Size_id']; // 商品Size_id  
                    $unit = $product['unit'];
                    $freight_id = $r_c[0]->freight; // 运费id
                    if(empty($freight_id) || $freight_id == 0){ // 当运费id不存在 或者 为0 时
                        $freight = 0; // 运费为0
                    }else{
                        // 根据运费id,查询运费信息
                        $sql = "select type,freight from lkt_freight where id = '$freight_id'";
                        $r2 = $db->select($sql);
                        if($r2){
                            $freight_type = $r2[0]->type;
                            $freight_1 = unserialize($r2[0]->freight);
                            $freight_status = 0; // 表示收货地址不存在运费规则里
                            $weight = 1;
                            foreach ($freight_1 as $k2 => $v2){
                                $province_arr = explode(',',$v2['name']); // 省份数组
                                if(in_array($G_CName,$province_arr)){
                                    $one = $v2['one']; // 首件/重
                                    $two = $v2['two']; // 运费
                                    $three = $v2['three']; // 续件/重
                                    $four = $v2['four']; // 续费
                                    $province_name = $G_CName; // 省
                                    $freight_status = 1; // 表示收货地址存在运费规则里
                                    continue;
                                }
                            }
                            if($freight_status == 1){
                                if($freight_type == 0){ // 运费为计件时
                                    if($num > $one){ // 当购买数量大于首件数量时
                                        $Goods_num_1 = $num - $one;
                                        $freight = $two;
                                        $frequency = ceil($Goods_num_1/$three);
                                        $freight = $four * $frequency + $freight; // 运费
                                    }else{ // 当购买数量低于或等于首件数量时
                                        $freight = $two; // 运费
                                    }
                                }else{ // 运费为计重时
                                    $z_weight = $num * $weight;
                                    if($z_weight > $one){ // 当购买数量大于首件数量时
                                        $z_weight_1 = $z_weight - $one;
                                        $freight = $two;
                                        $frequency = ceil($z_weight_1/$three);
                                        $freight = $four * $frequency + $freight; // 运费
                                    }else{ // 当购买数量低于或等于首件数量时
                                        $freight = $two; // 运费
                                    }
                                }
                            }else{
                                $freight = 0; // 运费
                            }
                        }
                    }

                    $z_freight += $freight;

                    //写入配置
                    $attribute = unserialize($product['attribute']);
                    $size = '';
                    foreach ($attribute as $ka => $va) {
                        $size .= $va.' ';
                    }
                    // 循环插入订单附表
                   $sql_d = 'insert into lkt_order_details(user_id,p_id,p_name,p_price,num,unit,r_sNo,add_time,r_status,size,sid,freight) VALUES '."('$userid','$pid','$product_title','$price','$num','$unit','$sNo',CURRENT_TIMESTAMP,0,'$size','$size_id','$freight')";

                    $beres = $db->insert($sql_d);
                    if($beres < 1){
                        $db->rollback();
                        echo json_encode(array('status' => 0, 'err' => '下单失败,请稍后再试!'));
                        exit;
                    }
                    // 删除对应购物车内容
                    $sql_del = 'delete from lkt_cart where id="'.$value.'"';
                    $res_del = $db -> delete($sql_del);
                    if($res_del < 1){
                        $db->rollback();
                        echo json_encode(array('status' => 0, 'err' => '下单失败,请稍后再试!'));
                        exit;
                    }
                }else{
                    //回滚删除已经创建的订单
                    $db->rollback();
                    echo json_encode(array('status' => 0, 'err' => '下单失败,请稍后再试!'));
                    exit;
                }
            }
            $spz_price = $z_price; // 商品总价

            // 判断积分使用
            if ($allow >0 && $allow != 'undefined') {
                $z_price = $z_price - $allow;
            }else{
                $allow = 0;
            }
            // 判断红包使用
            if ($red_packet >0 && $red_packet != 'undefined') {
                // $z_price = $z_price - $red_packet;
            }else{
                $red_packet = 0;
            }
            // 判断满减金额
            if ($reduce_money >0 && $reduce_money != 'undefined') {
                $z_price = $z_price - $reduce_money;
            }else{
                $reduce_money = 0;
            }
            //判断优惠券
            if($coupon_id){
                $sql = "select * from lkt_coupon where id = '$coupon_id'";
                $r_coupon = $db->select($sql);
                if($r_coupon){
                    $c_money = $r_coupon[0]->money;
                }else{
                    $c_money = 0;
                }
                $z_price = $z_price - $c_money;
            }else{
                $coupon_id = 0;
                $c_money = 0;
            }

            $z_price = $z_price + $z_freight; // 订单总价

            // 在订单表里添加一条数据
            $sql_o = 'insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,remark,pay,add_time,status,coupon_id,consumer_money,coupon_activity_name,spz_price,reduce_price,coupon_price,red_packet,source) VALUES '.
            "('$userid','$name','$mobile','$z_num','$z_price','$sNo','$sheng','$shi','$xian','$address',' ','$type',CURRENT_TIMESTAMP,0,'$coupon_id','$allow','$coupon_activity_name','$spz_price','$reduce_money','$c_money','$red_packet',1)";

            $r_o = $db->insert($sql_o,"last_insert_id");
            if($r_o > 0){
                if($allow){

                    $sql = "update lkt_user set consumer_money = consumer_money - '$allow' where user_id = '$userid'";
                    $db->update($sql);

                    $event = $userid.'抵用'.$allow.'元消费金';
                    //类型 1:转入(收入) 2:提现 3:管理佣金 4:使用消费金 5收入消费金 6 系统扣款
                    $sqlldr = "insert into lkt_distribution_record (user_id,from_id,money,sNo,level,event,type,add_date) values ('$userid','$userid','$allow','$sNo','0','$event','4',CURRENT_TIMESTAMP)";
                    $db->insert($sqlldr);
                }
                //返回
                $db->commit();
                $arr = array('pay_type' => $type,'sNo' => $sNo,'coupon_money' => $z_price,'coupon_id' => $coupon_id,'order_id' => $r_o);
                echo json_encode(array('status' => 1, 'arr' => $arr));
                exit;
            }else{
                //回滚删除已经创建的订单
                $db->rollback();
                echo json_encode(array('status' => 0, 'err' => '下单失败,请稍后再试!'));
                exit;
            }
        }
    }

    // 生成订单号
    private function order_number(){
        return date('Ymd',time()).time().rand(10,99);//18位
    }

    // 付款后修改订单状态,并修改商品库存-
    public function up_order(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $coupon_id = trim($request->getParameter('coupon_id')); // 优惠券id
        $allow = trim($request->getParameter('allow')); // 用户使用消费金
        $coupon_money = trim($request->getParameter('coupon_money')); // 付款金额
        $order_id = trim($request->getParameter('order_id')); // 订单号
        $user_id = trim($request->getParameter('user_id')); // 微信id
        $d_yuan = trim($request->getParameter('d_yuan')); // 抵扣余额
        $trade_no = trim($request->getParameter('trade_no')); // 微信支付单号
        $pay =  trim($request->getParameter('pay'));
        // 根据微信id,查询用户id
        $sql_user = 'select user_id,money from lkt_user where wx_id=\''.$user_id.'\'';
        $r_user = $db->select($sql_user);
        if($r_user){
            $userid = $r_user['0']->user_id; // 用户id
            $user_money =  $r_user['0']->money; // 用户余额

            if($d_yuan){
                // 使用组合支付的时候 lkt_combined_pay
                $sql = "update lkt_user set money = money-'$d_yuan' where user_id = '$userid'";
                $r = $db->update($sql);
                $weixin_pay = $coupon_money - $d_yuan;
                //写入日志
                $sqll = "insert into lkt_combined_pay (weixin_pay,balance_pay,total,order_id,add_time,user_id) values ('$weixin_pay','$d_yuan','$coupon_money','$order_id',CURRENT_TIMESTAMP,'$user_id')";
                $rr = $db->insert($sqll);
                // 根据修改支付方式
                $sql_combined = "update lkt_order set pay = 'combined_Pay' where sNo = '$order_id' and user_id = '$userid' ";
                $r_combined = $db->update($sql_combined);

                //微信支付记录-写入日志
                $event = $userid.'使用组合支付了'.$coupon_money.'元--订单号:'.$order_id;
                $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$userid','$coupon_money','$d_yuan','$event',4)";
                $rr = $db->insert($sqll);
            }

            if($trade_no){
                //微信支付记录-写入日志
                $event = $userid.'使用微信支付了'.$coupon_money.'元--订单号:'.$order_id;
                $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$userid','$coupon_money','$d_yuan','$event',4)";
                $rr = $db->insert($sqll);
            }

            if($coupon_money <= 0 && $allow > 0){
                // 根据订单号、用户id,修改订单状态(未发货)
                $sql_u = "update lkt_order set status = 1,pay = 'consumer_pay',trade_no='$trade_no' where sNo = '$order_id' and user_id = '$userid' ";
                $r_u = $db->update($sql_u);
            }else{
                // 根据订单号、用户id,修改订单状态(未发货)
                $rpay = '';
                if($pay){
                    $rpay = " ,pay = '$pay'";
                }
                $sql_u = "update lkt_order set status = 1 $rpay,trade_no='$trade_no' where sNo = '$order_id' and user_id = '$userid' ";
                $r_u = $db->update($sql_u);
            }

            if($allow && $coupon_money > 0){
                // 使用组合支付的时候 lkt_combined_pay 消费金情况
                if($pay == 'wallet_Pay'){
                    $zpay = 'balance_pay';
                }else{
                    $zpay = 'weixin_pay';
                }
                //写入日志
                $total = $allow + $coupon_money;
                $sqll = "insert into lkt_combined_pay ($zpay,consumer_pay,total,order_id,add_time,user_id) values ('$coupon_money','$allow','$total','$order_id',CURRENT_TIMESTAMP,'$user_id')";
                $rr = $db->insert($sqll);
                // 根据修改支付方式
                $sql_combined = "update lkt_order set pay = 'combined_Pay' where sNo = '$order_id' and user_id = '$userid' ";
                $r_combined = $db->update($sql_combined);

                //微信支付记录-写入日志
                $event = $userid.'使用组合支付了'.$total.'元--订单号:'.$order_id;
                $sqll = "insert into lkt_record (user_id,money,oldmoney,event,type) values ('$userid','$coupon_money','$d_yuan','$event',4)";
                $rr = $db->insert($sqll);
            }

            // 根据用户id、优惠券id,修改优惠券状态(已使用)
            $sql = "update lkt_coupon set type = 2 where user_id = '$userid' and id = '$coupon_id'";
            $db->update($sql);
            
            // 根据订单号,查询商品id、商品名称、商品数量
            $sql_o = "select p_id,num,p_name,sid from lkt_order_details where r_sNo = '$order_id' ";
            $r_o = $db->select($sql_o);
            // 根据订单号,修改订单详情状态(未发货)
            $sql_d = "update lkt_order_details set r_status = 1 where r_sNo = '$order_id' ";
            $r_d = $db->update($sql_d);
            // 修改产品数据库数量
            $pname = '';
            foreach ($r_o as $key => $value) {
                $pid = $value->p_id; // 商品id
                $num = $value->num; // 商品数量
                $p_name = $value->p_name; // 商品名称
                $sid = $value->sid; // 商品属性id
                $pname .= $p_name;
                // 根据商品id,修改商品数量
                $sql_p = "update lkt_configure set  num = num - $num where id = $sid";
                $r_p = $db->update($sql_p); 
                // 根据商品id,修改卖出去的销量
                $sql_x = "update lkt_product_list set volume = volume + $num,num = num-$num where id = $pid";
                $r_x = $db->update($sql_x); 
            }

            if($r_u){
                // 根据订单号,查询订单id、订单金额
                $sql_id = "select * from lkt_order where sNo = '$order_id' ";
                $r_id = $db->select($sql_id);
                if($r_id){
                    $id = $r_id['0']->id; // 订单id
                }else{
                    $id = 0;
                }
                $time =date("Y-m-d h:i:s",time()); // 当前时间
                $ds =  false;

                echo json_encode(array('status'=>1,'succ'=>'操作成功!','sNo' => $order_id,'coupon_money' => $coupon_money,'id' => $id,'pname'=>$pname,'time'=>$time,'qu'=>$ds));
                exit;
            }else{
                echo json_encode(array('status'=>0,'err'=>'操作失败!'));
                exit;
            }
        }else{
            echo json_encode(array('status'=>0,'err'=>'操作失败!'));
            exit;
        }
    }


     // 发送评论数据
    public function comment(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $order_id = trim($request->getParameter('order_id')); // 订单号
        $user_id = trim($request->getParameter('user_id')); // 微信id
        $pid = trim($request->getParameter('pid')); // 商品id
        $attribute_id = trim($request->getParameter('attribute_id')); // 属性id
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        $sql_user = 'select user_id from lkt_user where wx_id=\''.$user_id.'\'';
        $r_user = $db->select($sql_user);

        if($r_user){
            if($pid && $attribute_id){
                $sql_o = "select a.p_id as commodityId,m.img,a.size,a.sid from lkt_order_details AS a LEFT JOIN lkt_configure AS m ON a.sid = m.id  where a.r_sNo = '$order_id' and a.p_id = '$pid' and a.sid = '$attribute_id' and a.r_status = 3 ";
            }else{
                $sql_o = "select a.p_id as commodityId,m.img,a.size,a.sid from lkt_order_details AS a LEFT JOIN lkt_configure AS m ON a.sid = m.id  where a.r_sNo = '$order_id' and (a.r_status = 3 or a.r_status = 1 or a.r_status = -1)";
            }
            $r_o = $db->select($sql_o);
            
            if($r_o){
                foreach ($r_o as $key => $value) {
                    $arr = (array)$value;
                    $imgurl = $arr['img'];/* end 保存*/
                    $arr['commodityIcon']=$img.$imgurl;
                    $obj = (object)$arr;
                    $res[$key] = $obj;
                }
                echo json_encode(array('status'=>1,'commentList'=>$res));
                exit;
            }
        }
    }
    //添加评论
    public function t_comment(){
        $db = DBAction::getInstance();
        $db->begin();
        $request = $this->getContext()->getRequest();
        $type = trim($request->getParameter('type'));
        if($type == 'file'){
            //处理评论图片
            $id = trim($request->getParameter('id'));//评论ID
            // 查询配置表信息
            $sql = "select * from lkt_config where id = '1'";
            $r = $db->select($sql);
            if($r){
                $uploadImg = $r[0]->uploadImg;
                // 图片上传位置
                if(empty($uploadImg)){
                    $uploadImg = "../LKT/images";
                }
            }else{
                $uploadImg = "../LKT/images";
            }

            $imgURL=($_FILES['imgFile']['tmp_name']);
            $type = str_replace('image/', '.', $_FILES['imgFile']['type']);
            $imgURL_name=time().mt_rand(1,1000).$type;
            move_uploaded_file($imgURL,$uploadImg.$imgURL_name);
            $sql ="insert into lkt_comments_img(comments_url,comments_id,add_time) VALUES ('$imgURL_name','$id',CURRENT_TIMESTAMP)";
            $res = $db->insert($sql);
            
            if($res){
                $db->commit();
                echo json_encode(array('status'=>1,'err'=>'修改成功','sql'=>$sql));
                exit;
            }else{
                $db->rollback();
                echo json_encode(array('status'=>0,'err'=>'修改失败'));
                exit;
            }
        }else{
            //接收评论JSON数据  
            $json = json_decode (file_get_contents('php://input'));
            $comments = $json->comments;
            $r_d = 0;
            $oid = '';

            // 查询配置表信息
            $sql = "select * from lkt_config where id = '1'";
            $r = $db->select($sql);
            if($r){
                $uploadImg = $r[0]->uploadImg;
                // 图片上传位置
                if(empty($uploadImg)){
                    $uploadImg = "../LKT/images";
                }
            }else{
                $uploadImg = "../LKT/images";
            }

            //敏感词表
            require('badword.src.php');

            foreach ($comments as $key => $value){
                $oid =  $value->orderId; // 订单号
                $uid =  $value->userid; // 微信id
                $pid =  $value->commodityId; // 商品id
                $images =  $value->images; // 商品id
                $size =  $value->size; // 属性名称
                $attribute_id =  $value->attribute_id; // 属性id
                $content =  $value->content; // 评论内容
                $badword1 = array_combine($badword,array_fill(0,count($badword),'*'));

                $content = preg_replace ( "/\s(?=\s)/","\\1", $this->strtr_array($content, $badword1));

                //特殊字符处理
                $content = htmlentities($content);

                $CommentType =  $value->score; // 评论类型

                $sql = "select user_id from lkt_user where wx_id = '$uid'";
                $r_name = $db->select($sql);
                if($r_name){
                    $user_id = $r_name[0]->user_id;
                }else{
                    $user_id = '';
                }

                $arr = array();
                if($content != '' || count($images) != 0){
                    $sql_c = "select oid from lkt_comments where oid = '$oid' and pid = '$pid' and attribute_id = '$attribute_id' ";
                    $r_c = $db->select($sql_c);
                    if(empty($r_c['0'])){
                        $sql_d = 'insert into lkt_comments(oid,uid,pid,attribute_id,size,content,CommentType,add_time) VALUES '."('$oid','$user_id','$pid','$attribute_id','$size','$content','$CommentType',CURRENT_TIMESTAMP)";
                        $lcid = $db->insert($sql_d,'last_insert_id');
                        $cid[$value->pingid] = $lcid;
                        if($lcid > 0){
                            $sql_d = "update lkt_order_details set r_status = 5 where r_sNo = '$oid' and sid = '$attribute_id'";
                            $r_d = $db->update($sql_d);

                            $sql = "select r_status from lkt_order_details where r_sNo = '$oid'";
                            $rr = $db->select($sql);
                            if($rr){
                                foreach($rr as $k => $v){
                                    $r_status[] = $v->r_status;
                                }
                                $arr = array_count_values($r_status);
                                if($arr[5] == count($rr)){
                                    $sql = "update lkt_order set status = 5 where sNo = '$oid'";
                                    $db->update($sql);
                                }
                            }
                        }else{
                            echo json_encode(array('status'=>0,'err'=>'修改失败'));
                            exit;
                        }
                    }else{
                        $db->rollback();
                        echo json_encode(array('status'=>0,'err'=>'亲!评论过了1'));
                        exit;
                    }
                }else{
                    $db->rollback();
                    echo json_encode(array('status'=>0,'err'=>'修改失败'));
                    exit;
                }
            }

            $db->commit();
            echo json_encode(array('status'=>1,'succ'=>'评论成功!','arrid'=>$cid));
            exit; 
        }
    }
    //替换
    function strtr_array($str,$replace_arr) {
        $maxlen = 0;$minlen = 1024*128;
        if (empty($replace_arr)) return $str;
        foreach($replace_arr as $k => $v) {
            $len = strlen($k);
            if ($len < 1) continue;
            if ($len > $maxlen) $maxlen = $len;
            if ($len < $minlen) $minlen = $len;
        }
        $len = strlen($str);
        $pos = 0;$result = '';
        while ($pos < $len) {
            if ($pos + $maxlen > $len) $maxlen = $len - $pos; 
            $found = false;$key = '';
            for($i = 0;$i<$maxlen;++$i) $key .= $str[$i+$pos]; 
            for($i = $maxlen;$i >= $minlen;--$i) {
                $key1 = substr($key, 0, $i);
                if (isset($replace_arr[$key1])) {
                    $result .= $replace_arr[$key1];
                    $pos += $i;
                    $found = true;
                    break;
                }
            }
            if(!$found) $result .= $str[$pos++];
        }
        return $result;
    }
    //显示新产品
    public function new_product(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = trim($request->getParameter('cid')); //  '分类ID'
        $paegr = trim($request->getParameter('page')); //  '页面'

        $select = trim($request->getParameter('select')); //  选中的方式 0 默认  1 销量   2价格
        if($select == 0){
             $select = 'a.add_date'; 
        }elseif ($select == 1) {
             $select = 'a.volume'; 
        }else{
             $select = 'price'; 
        }

        $sort = trim($request->getParameter('sort')); // 排序方式  1 asc 升序   0 desc 降序
        if($sort){
             $sort = ' asc '; 
        }else{
             $sort = ' desc '; 
        }
        // 查询系统参数
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }

        if(!$paegr){
            $paegr = 1;
        }
        $start = ($paegr-1)*10;
        $end = 10;

        $sql ="select a.id,a.product_title,a.imgurl,a.volume,min(c.price) as price,c.yprice,c.img,a.s_type,c.id AS sizeid from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.s_type like '%$id%' and a.status = 0 and a.num >0 group by c.pid order by $select $sort LIMIT $start,$end ";

        $r = $db->select($sql);
        if($r){
            $product = [];
            foreach ($r as $k => $v) {
                $imgurl = $img.$v->imgurl;/* end 保存*/
                $pid = $v->id;
                $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                $r_ttt = $db->select($sql_ttt);
                $price =$r_ttt[0]->yprice;
                $price_yh =$r_ttt[0]->price;
                $product[$k] = array('id' => $v->id,'name' => $v->product_title ,'price' => $price,'price_yh' => $price_yh,'imgurl' => $imgurl,'volume' => $v->volume,'s_type' => $v->s_type);
            }
            echo json_encode(array('status'=>1,'pro'=>$product));
            exit;
        }else{
            echo json_encode(array('status'=>0,'err'=>'没有了！'));
            exit;
        }
    }
    //抽奖结算页面
    public function choujiangjiesuan(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $productId = trim($request->getParameter('productId')); //  购物车id
        $uid = trim($request->getParameter('uid')); // 微信id
        $choujiangid = trim($request->getParameter('choujiangid')); //  活动id
        $size =trim($request->getParameter('size')); //型号
        $wx_id = $request->getParameter('size');//分享人ID
        // 根据微信id,查询用户id
        $sql_user = 'select user_id,money from lkt_user where wx_id=\''.$uid.'\'';
        $r_user = $db->select($sql_user);
        if($r_user){
            $userid = $r_user['0']->user_id; // 用户id
            $user_money = $r_user['0']->money; // 用户余额
        }else{
            $userid = '';
            $user_money = 0;
        }


        // 根据用户id,查询收货地址
        $sql_a = 'select id from lkt_user_address where uid=\''.$userid.'\'';
        $r_a = $db->select($sql_a);
        if($r_a){
            $arr['addemt']=0; // 有收货地址
            // 根据用户id、默认地址,查询收货地址信息
            $sql_e = 'select * from lkt_user_address where uid=\''.$userid.'\' and is_default = 1';
            $r_e = $db->select($sql_e);
            $arr['adds'] = (array)$r_e['0']; // 收货地址
        }else{
            $arr['addemt']=1; // 没有收货地址
            $arr['adds'] = ''; // 收货地址
        }
        $sql1 = "select price from  lkt_draw where id = $choujiangid";
        $re = $db->select($sql1);

        $sql_d = 'select * from lkt_configure where id = '.$size;
        $r_d = $db->select($sql_d);
        $size1 = '';
        if($r_d){
            $attribute = unserialize($r_d[0]->attribute);
            foreach ($attribute as $ka => $va) {
                $size1 .= $va.' ';
            }
        }

        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        if($r_1){
            $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
            $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
            if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
                $img = $uploadImg_domain . $uploadImg; // 图片路径
            }else{ // 不存在
                $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
            }
        }else{
            $img = '';
        }
        $product = array();
        $sql_c = "select * from lkt_draw as a ,lkt_product_list as b where a.id = '$choujiangid' and a.draw_brandid = b.id";
        $r_c = $db->select($sql_c);
        // 联合查询返回购物信息
        if(!empty($r_c)){
            $product = (array)$r_c['0']; // 转数组
            $product['photo_x'] = $img.$product['imgurl'];/* 拼接图片链接*/
            $num =1; // 商品数量
            $pid = $product['draw_brandid']; // 商品id
            $product_title = $product['product_title']; // 商品名称
            $size1 = $size1?$size1:'默认';
        }

        if($re){
            $arr['price'] = $re[0]->price; // 产品总价
        }else{
            $arr['price'] = ' '; // 产品总价
        }
        $arr['adds']['photo_x']=$product['photo_x']?$product['photo_x']:'';
        $arr['adds']['num']=$num;
        $arr['adds']['pid']=$pid;
        $arr['adds']['product_title']=$product_title;
        $arr['adds']['choujiangid'] = $choujiangid; // 付款金额
        $arr['adds']['user_money'] = $user_money; // 用户余额
        $arr['adds']['size1'] = $size1; //型号
        $arr['size'] = $size; //型号
        echo json_encode(array('status'=>1,'arr'=>$arr));
        exit;
    }
       //抽奖创建订单
    public function choujiangpayment(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // print_r($request);die;
        $choujiangid = trim($request->getParameter('choujiangid')); // 抽奖id
        $uid = trim($request->getParameter('uid')); // 微信id
        $and = trim($request->getParameter('remark')); // 用户备注
        $type = trim($request->getParameter('type')); // 用户支付方式
        $size = trim($request->getParameter('size'));
        $total = $_POST['total']; // 付款金额
        $role = $request->getParameter('role');//分享订单ID
        if(!empty($role) && $role !='undefined'){//通过分享ID查询该团成员总数与设定拼团人数
            $sql04 = "select num,spelling_number,collage_number from lkt_draw where id = $choujiangid ";//查询
            $r04 = $db->select($sql04);
            if($r04){
                $num1 = $r04[0]->num;//每个团所需人数
                $spelling_number = $r04[0]->spelling_number;//可抽中奖次数（默认为1）
                $collage_number = $r04[0]->collage_number;//最少开奖团数（默认为1）
            }else{
                $num1 = 0;
            }

            $sql05 = "select count(id) as aa from lkt_draw_user where draw_id = $choujiangid and role = '$role'";
            $r05 = $db->select($sql05);
            if($r05){
                $bb = $r05[0]->aa;
            }else{
                $bb = 0;
            }
            if($bb>=$num1){
                $role = 0;
            }
        }

        // 根据微信id,查询用户id
        $sql_user = 'select user_id,money from lkt_user where wx_id=\''.$uid.'\'';
        $r_user = $db->select($sql_user);
        $userid = $r_user['0']->user_id; // 用户id
        $user_money = $r_user['0']->money; // 用户余额
        if(!empty($role) && $role !='undefined'){
            $role =$role;
        }else{
            $role = 0;
        }

        if($type == 'balance_Pay' && $user_money < $total){ // 当余额小于付款金额
            echo json_encode(array('status' => 0, 'err' => '余额不足！'));
            exit;
        }else{
            $remark = preg_replace('/[ ]/', '', $and);
            // 根据用户id、默认地址,查询地址信息
            $sql_a = 'select * from lkt_user_address where uid=\''.$userid.'\' and is_default = 1';
            $r_a = $db->select($sql_a);
            if($r_a){
                $name = $r_a['0']->name; // 联系人
                $mobile = $r_a['0']->tel; // 联系电话
                $address = $r_a['0']->address_xq; // 加省市县的详细地址
                $sheng = $r_a['0']->sheng; // 省
                $shi = $r_a['0']->city; // 市
                $xian = $r_a['0']->quyu; // 县
            }else{
                $name = ''; // 联系人
                $mobile = ''; // 联系电话
                $address = ''; // 加省市县的详细地址
                $sheng = ''; // 省
                $shi = ''; // 市
                $xian = ''; // 县
            }

            $z_num = 0;
            $z_price = 0;

            $sNo = $this ->order_number(); // 生成订单号
            $size_id = $size;//商品Size_id
            $sql_d = 'select * from lkt_configure where id = '.$size;
            $r_d = $db->select($sql_d);
            $size = '';
            if($r_d){
                $attribute = unserialize($r_d[0]->attribute);
                foreach ($attribute as $ka => $va) {
                    $size .= $va.' ';
                }
            }

            $sql_c = "select * from lkt_draw as a ,lkt_product_list as b where a.id = '$choujiangid' and a.draw_brandid = b.id";
            $r_c = $db->select($sql_c);

            // 联合查询返回购物信息
            if(!empty($r_c)){

                $product = (array)$r_c['0']; // 转数组
                $product['photo_x'] = 'http://'.$_SERVER['HTTP_HOST'].$product['imgurl'];/* 拼接图片链接*/
                $num =1; // 商品数量
                $z_num += $num; // 商品数量
                $price = $total; // 商品价格
                $z_price += $num*$price; // 总价
                $pid = $product['draw_brandid']; // 商品id
                $product_title = $product['product_title']; // 商品名称
                $size = $size?$size:'默认';

                // 循环插入订单附表
                $sql_d = 'insert into lkt_order_details(user_id,p_id,p_name,p_price,num,unit,r_sNo,add_time,r_status,size,sid) VALUES '."('$userid','$pid','$product_title','$price','$num','件','$sNo',CURRENT_TIMESTAMP,0,'$size','$size_id')";
                $r_d = $db->insert($sql_d);
            }else{
                echo json_encode(array('status' => 0, 'err' => '请勿重复下单！'));
                exit;
            }
            // 插入抽奖与用户关联表
            $sql0003 ='insert into lkt_draw_user(draw_id,user_id,time,role) VALUES '."('$choujiangid','$userid',CURRENT_TIMESTAMP,'$role')";
            $r_r = $db->insert($sql0003,"last_insert_id");
            // 在订单表里添加一条数据
            $sql_o = 'insert into lkt_order(user_id,name,mobile,num,z_price,sNo,sheng,shi,xian,address,remark,pay,add_time,status,coupon_id,allow,drawid) VALUES '."('$userid','$name','$mobile','$z_num','$z_price','$sNo','$sheng','$shi','$xian','$address','$remark','$type',CURRENT_TIMESTAMP,0,'0','0','$r_r')";
            $r_o = $db->insert($sql_o,"last_insert_id");
            if( $role == 0){
              //把抽奖与用户关联的
                $sql06 = "update lkt_draw_user set role ='$r_r' where id = '$r_r' ";
                $r06 = $db->update($sql06);
            }
            if($r_d > 0 && $r_o > 0){
                //返回
                $arr = array('pay_type' => $type,'sNo' => $sNo,'coupon_money' => $total,'coupon_id' => 0,'order_id' => $r_o , 'type1' => 11);
                if(!empty($bb)){
                    if($bb>=$num1){
                        echo json_encode(array('status' => 1, 'arr' => $arr, 'err' => '该团已满，生成新团！'));
                        exit;
                        
                    }else{
                        echo json_encode(array('status' => 1, 'arr' => $arr, 'err' => ''));
                        exit;
                    }
                }else{
                    echo json_encode(array('status' => 1, 'arr' => $arr, 'err' => ''));
                        exit;
                }
            }else{
                echo json_encode(array('status' => 0, 'err' => '请勿重复下单2！'));
                exit;
            }
        }
    }
}
?>