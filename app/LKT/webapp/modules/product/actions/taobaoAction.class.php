<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class taobaoAction extends Action {
    /*
    修改人：向丽娟
    主要功能：淘宝助手，搜索商品数据
    公司：湖南壹拾捌号网络技术有限公司
     */
    public function getDefaultView() {
      $db = DBAction::getInstance();
      $request = $this->getContext()->getRequest();
      //获取产品类别

        $sql = "select cid,pname from lkt_product_class where sid = 0 ";

        $r = $db->select($sql);

        $res = '';

        foreach ($r as $key => $value) {

            $c = '-'.$value->cid.'-';

            $res .= '<option  value="-'.$value->cid.'-">'.$value->pname.'</option>';

            //循环第一层

            $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid";

            $r_e = $db->select($sql_e);

            if($r_e){

                $hx = '-----';

                foreach ($r_e as $ke => $ve){

                   $cone = $c . $ve->cid.'-';

                   $res .= '<option  value="'.$cone.'">'.$hx.$ve->pname.'</option>';

                   //循环第二层

                   $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid";

                   $r_t = $db->select($sql_t);

                    if($r_t){

                        $hxe = $hx.'-----';

                        foreach ($r_t as $k => $v){

                           $ctow = $cone . $v->cid.'-';

                           $res .= '<option  value="'.$ctow.'">'.$hxe.$v->pname.'</option>';

                        }

                    }

                }

            }

        }
        // print_r($res);die;
    $request->setAttribute("ctype",$res);
      return View :: INPUT;
    }

    public function execute() {
      $db = DBAction::getInstance();
      $request = $this->getContext()->getRequest();
      // print_r($request);die;
      $url = $request->getParameter('url');
     $product_class = $request->getParameter('product_class');

      preg_match('|\.(.*).com|isU',$url, $type);
      
      $type = $type[1];
      if ($type == "1688") {
          $ret = $this->getalbb($url,$product_class);
   
        }else if($type == 'taobao'){
          $ret = $this->get_item_taobao($url,$product_class);
        }else{
           header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
            "alert('链接格式错误，请确定链接');" .
            "location.href='index.php?module=product&action=taobao';</script>";
        }

    }

    public function getRequestMethods(){
        return Request :: POST;
    }


    function get_item_taobao($url,$product_class){//淘宝数据抓取
      if(!empty($product_class)){
        $dte['product_class']=$product_class;
     }else{
         $dte['product_class']='-1-';
     }

      $db = DBAction::getInstance();
      $text=file_get_contents($url);//将url地址上页面内容保存进$text
       preg_match_all('|<h3 class="tb-main-title" data-title="(.*)</h3>|isU',$text, $title);
       $title1 = iconv('GBK','UTF-8', $title[1][0]); 
       // print_r($title1);
       $title2 = explode('">', $title1); 
       $dte['product_title']=$title2[0];//产品标题
       // print_r($title2);die;
       $sql = "select * from lkt_product_list where product_title = '$title2[1]'";//查询是否有相同名称的商品
        $r001 = $db->select($sql);
        if(!empty($r001)){
          echo "数据库里有该商品";
           return  ;
           header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
            "alert('请勿重复添加该商品');" .
            "location.href='index.php?module=product&action=taobao';</script>";
           
        }

      preg_match('/<img[^>]*id="J_ImgBooth"[^r]*rc=\"([^"]*)\"[^>]*>/', $text, $pic); //产品主图片
      if(!empty($pic[1])){
          $img = $this->getImage('http:'.$pic[1]);//储存图片
          $dte['imgurl'] = $img['file_name'];
       }
       $chicun = $this->chicun($text);//淘宝商品对应的商品尺寸
       $color = $this->color($text);//淘宝商品对应的商品规格颜色包括图片
       
       if(!empty($chicun['chicun'])){//判断尺寸数组是否存在
        $type1 = $chicun['type'] ;
        foreach ($chicun['chicun'] as $key01 => $value01) {
          if(!empty($value01)){//判断数组里单个值是否存在

            if(!empty($color)){//判断颜色和图片是否存在
              foreach ($color as $key02 => $value02) {
                if(!empty($value02)){//判断单个值是否存在

                  $chicunid =$value01['chicunid'];
                  // print_r($chicunid);die;
                  $idd=explode(':', $chicunid); 
                  // print_r($type1);
                  if($type1 == 1 ){
                    $keyy = $idd[0];
                  }else{
                    $keyy = '';
                  }
                  
                  $chicun =$value01['chicun']; 
                  $colorid = $value02['colorid'] ;
                  $img = $value02['img'];
                  $colorname = $value02['colorname'];
                  // echo "<br/>";
                  //     print_r($idd[1]);
                  //     echo "<br/>";
                  //     print_r($colorid);
                  //     echo "<br/>";
                  //     echo '-----------------------------------';
                      if($type1 == 1 ){//尺寸
                        $ids = $idd[1].';'.$colorid;
                      }elseif ($type1 == 2) {//高度
                        $ids = $colorid.';'.$chicunid;
                      }else{
                         $ids = $idd[1].';'.$colorid;
                      }
                  
                  $price = $this->price($text,$keyy);//淘宝商品对应的商品规格价格及ID
                  foreach ($price as $key03 => $value03) {
                      $id = $value03['id'];
                      
                      if ($ids == $id) {
                        $pricee[$key03]['id'] = $id;
                        $pricee[$key03]['price'] = $value03['price'];
                        $pricee[$key03]['chicun'] = iconv('GBK','UTF-8', $chicun);
                        $pricee[$key03]['img'] = $img;
                        $pricee[$key03]['colorname'] =  $colorname;
                        $pricee[$key03]['color'] = $colorname; 
                      }
                  }

                }else{
                  unset($color[$key02]);
                }
              }
            }
          }else{
            unset($chicun[$key01]);//单个值不存在就清除掉
          }
        }
       }else{//没有尺寸
          if(!empty($color)){//判断颜色和图片是否存在
              foreach ($color as $key02 => $value02) {
                
                if(!empty($value02)){//判断单个值是否存在
                  $keyy = '';
                  $colorid = $value02['colorid'] ;
                  $img = $value02['img'];
                  $colorname = $value02['colorname'];
                  // print_r($colorname);die;
                  $ids =$colorid;
                  $price = $this->price($text,$keyy);//淘宝商品对应的商品规格价格及ID
                  foreach ($price as $key03 => $value03) {
                      $id = $value03['id'];
                     
                      if ($ids == $id) {
                        $pricee[$key03]['id'] = $id;
                        $pricee[$key03]['price'] = $value03['price'];
                        $pricee[$key03]['chicun'] = '默认';
                        $pricee[$key03]['img'] = $img;
                        // $pricee[$key03]['colorname'] = iconv('GBK','UTF-8', $colorname);
                        // $pricee[$key03]['color'] = iconv('GBK','UTF-8', $colorname); 
                        $pricee[$key03]['colorname'] = $colorname;
                        $pricee[$key03]['color'] = $colorname;
                      }
                  }


                }else{
                  unset($color[$key02]);
                }
              }
            }else{//没有尺寸，没有颜色
                 $keyy = '';
                  $price = $this->price($text,$keyy);//淘宝商品对应的商品规格价格及ID
                        $pricee[0]['price'] = $price['price'];
                        $pricee[0]['chicun'] = '默认';
                        $pricee[0]['img'] = '';
                        $pricee[0]['colorname'] = '默认';
                        $pricee[0]['color'] = '默认';
             
                      
                      
                  
            }
       }
       $dte['arr'] =$pricee;
       $dte['content']="商品介绍";
       return $this -> save_goods($dte);
    
    }

    function price($text,$keyy){//获取淘宝商品对应价格及ID
      preg_match_all('|valItemInfo      : {(.*),propertyMemoMap: {|isU',$text,$price);
      
      if(!empty($price[1][0])){
          preg_match_all('|skuMap     : (.*)}}|isU',$price[1][0],$price001);

       if (!empty($keyy)) {
            $kee = '";'.$keyy;
             $dd= explode($kee , $price001[1][0]);//价格
             foreach ($dd as $key01 => $value01) {
                 if(!empty($value01) && $value01 !='{'){
                    $dd1 = 'id'.$value01;
                    
                    preg_match_all('|id:(.*);":|isU',$dd1,$idss);
                    preg_match_all('|{(.*),"oversold":false|isU',$dd1,$idss1);
                    $arr =  json_decode('{'.$idss1[1][0].'}');
                    $pricee[$key01]['id'] = $idss[1][0];
                    $pricee[$key01]['price'] = $arr->price;
                     }else{
                      unset($dd['key01']);
                     }
              }
                 }else{
                    $kee = '";';
                    $dd= explode($kee , $price001[1][0]);//价格
                    // print_r($dd);die;
                      foreach ($dd as $key01 => $value01) {
                               if(!empty($value01) && $value01 !='{'){
                                  $dd1 = 'id'.$value01;
                                  
                                  preg_match_all('|id(.*);":|isU',$dd1,$idss);
                                  preg_match_all('|{(.*),"oversold":false|isU',$dd1,$idss1);
                                  
                                  $arr =  json_decode('{'.$idss1[1][0].'}');

                                  $pricee[$key01]['id'] = $idss[1][0];
                                  $pricee[$key01]['price'] = $arr->price;
                               }else{
                                unset($dd['key01']);
                               }
                      }
                 }
          }else{
             preg_match_all('|<input type="hidden" name="current_price" value= "(.*)"/>|isU',$text,$price);
              $pricee['price'] = $price[1][0];
          }

    
     return $pricee;
   }
    function chicun($text){//获取淘宝商品对应的尺码
        preg_match_all('|<dl class="J_Prop J_TMySizeProp tb-prop tb-clear  J_Prop_measurement ">(.*)</dl>|isU',$text, $ccc);//商品尺寸
        preg_match_all('|<dl class="J_Prop tb-prop tb-clear ">(.*)</dl>|isU',$text, $ccc1);//商品高度
        if(!empty($ccc[0][0])){
            $cc = $ccc[0][0];
            $arr['type'] = 1;
        }elseif(!empty($ccc1[0][0])){
            $cc = $ccc1[0][0];
            $arr['type'] = 2;
        }else{
          $cc = '';
            $arr['type'] = 3;
        }
        
        if(!empty($cc)){
          preg_match_all('|<li (.*)</li>|isU',$cc, $cc1);

          if(!empty($cc1)){

            foreach ($cc1[0] as $key => $value) {
             $dd =  preg_replace('/\"(.*?)\"/s', "<font>\${1}</font>",$value);
             preg_match_all('|<font>(.*)</font>|isU',$dd, $cc2);
              $arr['chicun'][$key]['chicunid'] = $cc2[1][0];//尺寸对应的ID
              preg_match_all('|<span>(.*)</span>|isU',$value, $chicun);
              $arr['chicun'][$key]['chicun'] = $chicun[1][0];
            }
          }
        }else{
          $arr = '';
        }
        return $arr;
    }

    function color($text){//淘宝商品对应的商品规格颜色包括图片
          preg_match_all('|<dl class="J_Prop tb-prop tb-clear  J_Prop_Color ">(.*)</dl>|isU',$text, $color);
          if(!empty($color[1][0])){
                preg_match_all('|<li (.*)</li>|isU',$color[1][0], $color1);
               // print_r($color1);die;
              if(!empty($color1)){
                    foreach ($color1[1] as $key => $value) {

                     $dd =  preg_replace('/\"(.*?)\"/s', "<font>\${1}</font>",$value);

                     preg_match_all('|<font>(.*)</font>|isU',$dd, $color2id);
                      $arr[$key]['colorid'] = $color2id[1][0];//尺寸对应的ID
                      $urlimg = $color2id[1][2];//尺寸对应的图片地址
                      // print_r($urlimg);die;
                      if(!empty($urlimg)){
                        preg_match_all("/(?:\()(.*)(?:\))/i",$urlimg,$img);
                        // print_r($img);die;
                        if(!empty($img[1][0])){

                          $img = $this->getImage('https:'.$img[1][0]);//储存图片
                          
                          // $dte['imgurl'] = $img['file_name'];
                          $arr[$key]['img'] = $img['file_name'];
                          // $dd[] = $img['file_name'];
                          unset($img['file_name']);
                        }else{
                        
                        $arr[$key]['img'] ='';
                      }
                        
                      }else{
                        
                        $arr[$key]['img'] ='';
                      }

                      preg_match_all('|<span>(.*)</span>|isU',$value, $colorname);
                      $colorname1  = iconv('GBK','UTF-8', $colorname[1][0]);
                      $arr[$key]['colorname'] = $colorname1;
                    }
              }
          }else{
            $arr = '';
          }
          
          return $arr;
      }

    function getcurl($url){
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_HEADER, 1);
            curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36');
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
            $content = curl_exec($ch);
            curl_close($ch);
            
            return $content;
        }
    function getalbb($url,$product_class){//1688抓取
      if(!empty($product_class)){
        $dte['product_class']=$product_class;
     }else{
         $dte['product_class']='-1-';
     }
      $db = DBAction::getInstance();
      $text=file_get_contents($url);//将url地址上页面内容保存进$text
      // $text = $this->getcurl($url);
       // var_dump($text);die;
      preg_match('/<(h1)[^c]*class=\"d-title\"[^>]*>.*<\/\\1>/is', $text, $title);
      // print_r($text);
      // print_r($title);
       $product_title = preg_replace('/"/', '',$title[0]);
       preg_match('|<span>(.*)</span>|isU',$product_title,$product_title1);

       $dte['product_title']=$product_title1[1];//产品标题
        $sql = "select * from lkt_product_list where product_title = '$product_title'";//查询是否有相同名称的商品
        $r001 = $db->select($sql);
        if(!empty($r001)){
           return  ;
           
        }
        preg_match('/<div[^>]*class="swipe-pane"[^r]*rc=\"([^"]*)\"[^>]*>/', $text, $pic);//产品主图片
       if(!empty($pic[1])){
          $img = $this->getImage($pic[1]);//储存图片
          $dte['imgurl'] = $img['file_name'];
       }
        preg_match('|<dl class="d-price-discount(.*)</dl>|isU',$text, $price);
        preg_match('|</span>(.*)</dd>|isU',$price[1], $p);//产品价格
        $dte['price'] = $p[1];
        $guige = $this ->guige($text,$dte);
        $dte['arr'] = $guige;

        $dte['content']="商品介绍";
        // print_r($dte);
    
      return $this -> save_goods($dte);
             
  }
    function guige($text,$dte){//查询1688商品规格
        preg_match_all('|{};window.wingxViewData(.*)}</script>|isU', $text, $dd);//查询规格，图片
        $ddd = end($dd[1]);
        preg_match_all('|"skuProps":(.*)],|isU', $ddd, $ree);//
        preg_match_all('|"value":(.*)]}|isU', $ree[1][0], $ree1);//
        preg_match_all('|{(.*)}|isU', $ree1[1][0], $tupian);//颜色+图片

        if(!empty($ree1[1][1])){
            preg_match_all('|{(.*)}|isU', $ree1[1][1], $chicun);//尺寸

            if(!empty($tupian[0])){
              foreach ($tupian[0] as $key => $value) {
                 $tupian1[] = json_decode($value);
              }
            }
            if(!empty($chicun[0])){
              foreach ($chicun[0] as $key => $value01) {
                 $chicun1[] = json_decode($value01);
              }
            }

            foreach ($tupian1 as $key02 => $value02) {
              // print_r($value02);die;
              if(!empty($value02->imageUrl)){
                $u= $this->getImage($value02->imageUrl);//储存图片
                $tupian1[$key02]->img = $u['file_name'];
              }else{
                $tupian1[$key02]->img = '';
              }
              foreach ($chicun1 as $key03 => $value03) {

                // $arr1['colorname'] = $title1 = iconv('GBK','UTF-8', $value02->name);
                // $arr1['color'] = $title1 = iconv('GBK','UTF-8', $value02->name);
                $arr1['colorname'] = $title1 = $value02->name;
                $arr1['color'] = $title1 = $value02->name;
                $arr1['img'] = $value02->img;
                $arr1['price'] = $dte['price'];
                $arr1['chicun'] = $value03->name; 
                $arr[]= $arr1;
                }
                 
              }
        }else{
              if(!empty($tupian[0])){
                  foreach ($tupian[0] as $key => $value) {
                     $tupian1[] = json_decode($value);
                  }
                }
              foreach ($tupian1 as $key02 => $value02) {
                  if(!empty($value02->imageUrl)){
                    $u= $this->getImage($value02->imageUrl);//储存图片
                    $tupian1[$key02]->img = $u['file_name'];
                  }else{
                    $tupian1[$key02]->img = '';
                  }
                    $arr1['colorname'] = $title1 = iconv('GBK','UTF-8', $value02->name);
                    $arr1['img'] = $value02->img;
                    $arr1['price'] = $dte['price'];
                    $arr1['chicun'] = '默认'; 
                    $arr1['color'] = '默认';
                    $arr[]= $arr1;
                    }
        }
        
        return $arr;
    }
     

          /*
    }
*功能：php完美实现下载远程图片保存到本地 
*参数：文件url,保存文件目录,保存文件名称，使用的下载方式 
*当保存文件名称为空时则使用远程文件原来的名称 
*/ 
  function httpsRequest($url, $data=null) {
    // 1.初始化会话
    $ch = curl_init();
    // 2.设置参数: url + header + 选项
    // 设置请求的url
    curl_setopt($ch, CURLOPT_URL, $url);
    // 保证返回成功的结果是服务器的结果
    curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,FALSE);
        curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,FALSE);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    if(!empty($data)) {
      // 发送post请求
      curl_setopt($ch, CURLOPT_POST, 1);
      // 设置发送post请求参数数据
      curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    }
    // 3.执行会话; $result是微信服务器返回的JSON字符串
    $result = curl_exec($ch);
    // 4.关闭会话
    curl_close($ch);
    return $result;
  }
//传入图片url 保存到本地
function getImage($url,$save_dir='./images/',$filename='',$type=0){ 
      $type = substr($url,strripos($url,".")+1);
      if(!$type){
        $type = '.jpg';
      }
      if($filename == ''){
        $filename = mt_rand(1,1111222).time().'.'.$type;
      }
      $newFilePath = $save_dir.$filename;
      $da = $this->httpsRequest($url);
      //发送post带参数请求 
      $newFile = fopen($newFilePath,"w"); //打开文件准备写入
      fwrite($newFile,$da); //写入二进制流到文件
      fclose($newFile); //关闭文件

    return array('file_name'=>$filename,'save_path'=>$newFilePath,'error'=>0); 
} 
       /*
      *功能：保存商品相关信息
      *参数：product_title：产品主标题  imgurl：产品主图   price：价格   title：规格名称   thumb：规格对应的图片路径
      */
    function save_goods($dte){
        $db = DBAction::getInstance();
  
        $product_class = $dte['product_class'];//产品类别 
        $product_title =$dte['product_title'];
        $imgurl =$dte['imgurl'];
        $content =$dte['content'];
        $sql = "insert into lkt_product_list(product_class,product_title,imgurl,content,num,add_date,brand_id,s_type,status,sort) values('$product_class','$product_title','$imgurl','$content','0',CURRENT_TIMESTAMP,'0','1','0','100')";
        $id = $db->insert($sql,'last_insert_id');
        if($id>0){
          foreach ($dte['arr'] as $key => $value) {//遍历插入商品规格            
            $name = $value['colorname'];
            $color = $value['color'];
            $img = $value['img'];
            $price = $value['price'];
            $size = $value['chicun'];
            $sql01 = "insert into lkt_configure( name,color,size,price,yprice,img,pid,num,bargain_price,status) VALUES ('$name','$color','$size','$price','0','$img','$id','0','0','0')";
            $re = $db ->insert($sql01);
          }
          if($re>0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
            "alert('商品已经获取成功, 是否跳转到产品列表?');" .
            "location.href='index.php?module=product';</script>";
            return '成功了'.$id;
          }else{
           // echo "失败了1";
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
            "alert('商品已经获取成功, 是否跳转到产品列表?');" .
            "location.href='index.php?module=product';</script>";
            return '失败了1'.$id;
          }

        }else{
          header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
            "alert('商品已经获取成功, 是否跳转到产品列表?');" .
            "location.href='index.php?module=product';</script>";
            return '失败了2'.$id;
        }

      }





}

?>