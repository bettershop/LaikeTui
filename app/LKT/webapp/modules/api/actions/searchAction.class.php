<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class searchAction extends Action {

	public function getDefaultView() {
    return;
  }

  public function execute(){
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();

    $m = addslashes(trim($request->getParameter('m')));
    if($m == 'index'){
      $this->index();
    }else if($m == 'search'){
      $this->search();
    }else if($m == 'listdetail'){
      $this->listdetail();
    }
    return;
  }

	public function getRequestMethods(){
		return Request :: POST;
	}

  public function index(){
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

    //查询商品并分类显示返回JSON至小程序
    $sql_c = "select cid,pname,img,bg from lkt_product_class where sid=0 and recycle != 1 order by sort desc";
    $r_c = $db->select($sql_c);
   
    $twoList = [];
    $abc = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $st = 0;
    $icons=[];
    if($r_c){
        foreach ($r_c as $key => $value) {
            $sql_e = 'select cid,pname,img from lkt_product_class where sid=\''.$value->cid.'\' and recycle != 1 order by sort ';
            $r_e = $db->select($sql_e);
            $son=[];
            if($r_e){
                foreach ($r_e as $ke => $ve) {
                    $imgurl = $img.$ve->img;
                    $son[$ke] = array('child_id' => $ve->cid,'name' => $ve->pname,'picture' => $imgurl);
                }
                $type = true;
            }else{
                $type =false;
            }
            if($value->bg){
                $cimgurl = $img.$value->bg;
            }else{
                $cimgurl = '';
            }

            $icons[$key] = array('cate_id' => $value->cid,'cate_name' => $value->pname,'ishaveChild'=> $type,'children' => $son,'cimgurl' => $cimgurl);

        }
    }


    $sql = 'select keyword from lkt_hotkeywords';
    $res = $db -> selectarray($sql);
    if($res){
        foreach ($res as $k => $v) {
            $res[$k] = $v['keyword'];
        }
    }

    echo json_encode(array('status'=>1,'List'=>$icons,'hot'=>$res));
    exit;
  }
  public function search(){
    $db = DBAction::getInstance();
    $request = $this->getContext()->getRequest();
    $keyword = trim($request -> getParameter('keyword')); // 关键词
    $num = trim($request->getParameter('num')); //  '次数'
    $select = trim($request->getParameter('select')); //  选中的方式 0 默认  1 销量   2价格
    $sort = trim($request->getParameter('sort')); // 排序方式  1 asc 升序   0 desc 降序
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

    if($select == 0){
      $select = 'a.add_date'; 
    }elseif ($select == 1) {
      $select = 'a.volume'; 
    }else{
      $select = 'price'; 
    }
    if($sort){
      $sort = ' asc '; 
    }else{
      $sort = ' desc '; 
    }

    //查出所有产品分类
    $sql = 'select pname from lkt_product_class where recycle != 1';
    $res = $db -> select($sql);
    if($res){
        foreach ($res as $key => $value) {
            $res[] = $value -> pname;
        }
    }

    //判断如果关键词是产品分类名称，如果是则查出该类里所有商品
    if(in_array($keyword, $res)){
      $type = 0;
      $keyword = addslashes($keyword);
      $sqla = "select cid from lkt_product_class where pname='$keyword' and recycle != 1";
      $a = $db -> select($sqla);
      if(!empty($a)){
        $cid = $a['0']->cid; // 分类id
      }
      $start = 10*($num-1);
      $end = 10;
      $sqlb = "select a.id,product_title,a.volume,a.s_type,c.id as cid,c.yprice,c.img,c.name,c.color,min(c.price) as price from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.product_class like '%$cid%' and a.status = 0 group by c.pid order by $select $sort  LIMIT $start,$end";
      $data = $db -> select($sqlb);
      
    }else{   //如果不是商品分类名称，则直接搜产品
      $type = 1;
      $keyword = addslashes($keyword);
      $sqlb = "select a.id,a.product_title,a.product_class,a.volume,a.s_type,c.id as cid,c.yprice,c.img,c.name,c.color,min(c.price) as price from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.product_title like '%$keyword%' and a.status = 0 group by c.pid order by $select $sort";

      $data = $db -> select($sqlb);
    }
    if(!empty($data)){
      $product = array();
      foreach ($data as $k => $v) {
        $imgurl = $img.$v->img;/* end 保存*/
        $names = ' '.$v->name . $v->color ;
        if($type == 1){
          $cid = $v->product_class;
        }else{
          $cid = $cid;
        }
        if($v->name == $v->color || $v->name == '默认'){
            $names = '';
        }
        $product[$k] = array('id' => $v->id,'name' => $v->product_title . $names,'price' => $v->yprice,'price_yh' => $v->price,'imgurl' => $imgurl,'size'=>$v->cid,'volume' => $v->volume,'s_type' => $v->s_type);
      }
      echo json_encode(array('list'=>$product,'cid'=>$cid,'code'=>1,'type'=>$type));exit();
    }else{
      echo json_encode(array('status'=>0,'err'=>'没有更多商品了！'));exit();
    }
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
    $bg = '';
    $sql_c = "select bg from lkt_product_class where cid='$id' ";
    $r_c = $db->select($sql_c);
    if($r_c){
      $bg = $img.$r_c[0]->bg;
    }

    $sql = "select a.id,a.product_title,volume,min(c.price) as price,c.yprice,c.img,c.name,c.color,c.size,a.s_type,c.id AS sizeid from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid where a.product_class like '%$id%' and c.num >0 and a.status = 0 group by c.pid  order by $select $sort LIMIT $start,$end ";

    $r = $db->select($sql);

    if($r){
      $product = [];
      foreach ($r as $k => $v) {
        $imgurl = $img.$v->img;/* end 保存*/
        $names = ' '.$v->name . $v->color ;
        if($v->name == $v->color || $v->name == '默认'){
          $names = '';
        }
        $product[$k] = array('id' => $v->id,'name' => $v->product_title . $names,'price' => $v->yprice,'price_yh' => $v->price,'imgurl' => $imgurl,'size'=>$v->sizeid,'volume' => $v->volume,'s_type' => $v->s_type);
      }
      echo json_encode(array('status'=>1,'pro'=>$product,'bg'=>$bg));
      exit;
    }else{
      echo json_encode(array('status'=>0,'err'=>'没有了！'));
      exit;
    }
  }
}

?>