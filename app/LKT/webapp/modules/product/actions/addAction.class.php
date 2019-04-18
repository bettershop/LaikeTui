<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once(MO_LIB_DIR . '/DBAction.class.php');



class addAction extends Action {

    public function getDefaultView() {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        /*** 报错不清除输入内容 ***/
        $product_number = addslashes(trim($request->getParameter('product_number'))); // 产品编号
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $brand_id1 = addslashes(trim($request->getParameter('brand_class'))); // 品牌

        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 小标题
        $scan = addslashes(trim($request->getParameter('scan'))); // 条形码
        $attribute = $request->getParameter('attribute'); // 属性
        $keyword = addslashes(trim($request->getParameter('keyword'))); // 关键词
        $weight = addslashes(trim($request->getParameter('weight'))); // 重量
        $s_type = $request->getParameter('s_type'); // 类型
        $distributor_id = trim($request->getParameter('distributor_id')); //关联的分销层级id
        $is_distribution = trim($request->getParameter('is_distribution')); //是否开启分销
        $is_zhekou = trim($request->getParameter('is_zhekou')); //是否开启会员商品折扣
        $volume = trim($request->getParameter('volume')); //拟定销量
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 产品图片
        $freight1 = $request->getParameter('freight'); // 运费
        $content = addslashes(trim($request->getParameter('content'))); // 产品内容

        if(!$s_type){
            $s_type = [];
        }
        if($image == ''){
            $image = $oldpic;
        }

        if($attribute ){
            $attribute1 = json_decode($attribute);
            $attribute2 = [];
            $attribute_val = [];
            foreach ($attribute1 as $k => $v){
                $attribute_key = array_keys((array)$v); // 属性表格第一栏
                $attribute_key1 = array_keys((array)$v); // 属性表格第一栏
                $attribute_val[] = array_values((array)$v); // 属性表格
                $attribute_num = $k + 1;
                $attribute2[] = (array)$v;
            }
            $attribute3 = json_encode($attribute2);

            for ($i=0;$i<6;$i++){
                array_pop($attribute_key1); // 循环去掉数组后面6个元素
            }
            $rew = '';
            foreach ($attribute_key1 as $key1 => $val1){
                $key_num = $key1;
                $rew .= "<div style='margin: 5px auto;' class='attribute_".($key1+1)." option' id='cattribute_".($key1+1)."' >";
                $rew .= "<input type='text' name='attribute_name' id='attribute_name_".($key1+1)."' placeholder='属性名称' value='".$val1."' class='input-text' readonly='readonly' style=' width:50%;background-color: #EEEEEE;' />" .
                    " - " .
                    "<input type='text' name='attribute_value' id='attribute_value_".($key1+1)."' placeholder='值' value='' class='input-text' style='width:45%' />";
                $rew .= "</div>";
            }
            $num_k = count($attribute_key1) + 1;
            $rew .= "<div style='margin: 5px auto;display:none;' class='attribute_".$num_k." option' id='cattribute_".$num_k."' >" .
                "<input type='text' name='attribute_name' id='attribute_name_".$num_k."' placeholder='属性名称' value='' class='input-text' readonly='readonly' style=' width:50%;background-color: #EEEEEE;'  onblur='leave();'/>" .
                " - " .
                "<input type='text' name='attribute_value' id='attribute_value_".$num_k."' placeholder='值' value='' class='input-text' style='width:45%' onblur='leave();'/>" .
                "</div>";
        }

        $distributors1 = '';

        /*** 报错不清除输入内容 结束 ***/

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

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
        // 品牌
        $sql01 = "select brand_id ,brand_name from lkt_brand_class where status = 0";
        $r01 = $db->select($sql01);
        $brand = [];
        $brand_num = 0;
        if($r01){
            if($brand_id1){
                $sql = "select brand_id ,brand_name from lkt_brand_class where brand_id = '$brand_id1'";
                $r011= $db->select($sql);
                $brand[$brand_num] = (object)array('brand_id'=> $r011[0]->brand_id,'brand_name'=> $r011[0]->brand_name);
                $brand_num++;
                $brand[$brand_num] = (object)array('brand_id'=>0,'brand_name'=>'请选择品牌');
            }else{
                $brand[$brand_num] = (object)array('brand_id'=>0,'brand_name'=>'请选择品牌');
            }

            foreach ($r01 as $k2 =>$v2){
                $brand_num++;
                $brand[$brand_num] = (object)array('brand_id'=> $v2->brand_id,'brand_name'=> $v2->brand_name);
            }
        }

        $distributors = [];
        $distributors_num = 0;

        // 运费
        $sql = "select id,name from lkt_freight order by add_time desc";
        $rr = $db->select($sql);
        $freight = [];
        $freight_num = 0;
        if($rr){
            if($freight1){
                $sql = "select id,name from lkt_freight where id = '$freight1'";
                $rr1= $db->select($sql);
                $freight[$freight_num] = (object)array('id'=> $rr1[0]->id,'name'=> $rr1[0]->name);
                $freight_num++;
                $freight[$freight_num] = (object)array('id'=>0,'name'=>'默认模板');
            }else{
                $freight[$freight_num] = (object)array('id'=>0,'name'=>'默认模板');
            }
            foreach ($rr as $k1 => $v1){
                $freight_num++;
                $freight[$freight_num] = (object)array('id'=> $v1->id,'name'=> $v1->name);
            }
        }
        $request->setAttribute("distributors",$distributors);

        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("ctype",$res);
        $request->setAttribute("brand",$brand);
        $request->setAttribute("freight",$rr);

        $request->setAttribute('attribute', isset($attribute3) ? $attribute3 : '');
        $request->setAttribute('attribute_num', isset($attribute_num) ? $attribute_num : '');
        $request->setAttribute('attribute_key', isset($attribute_key) ? $attribute_key : '');
        $request->setAttribute('attribute_val', isset($attribute_val) ? $attribute_val : '');
        $request->setAttribute('rew', isset($rew) ? $rew : '');

        $request->setAttribute('product_number', isset($product_number) ? $product_number : '');
        $request->setAttribute('product_title', isset($product_title) ? $product_title : '');
        $request->setAttribute('subtitle', isset($subtitle) ? $subtitle : '');
        $request->setAttribute('scan', isset($scan) ? $scan : '');
        $request->setAttribute('s_type', isset($s_type) ? $s_type : '');
        $request->setAttribute('keyword', isset($keyword) ? $keyword : '');
        $request->setAttribute('weight', isset($weight) ? $weight : '');
        $request->setAttribute('is_distribution', isset($is_distribution) ? $is_distribution : '0');
        $request->setAttribute('is_zhekou', isset($is_zhekou) ? $is_zhekou : '0');

        $request->setAttribute('sort', $sort ? $sort : '100');
        $request->setAttribute('image', isset($image) ? $image : '');
        $request->setAttribute('freight', isset($freight) ? $freight : '');

        $request->setAttribute('content', isset($content) ? $content : '');
        $request->setAttribute('volume', $volume ? $volume : '0');
        return View :: INPUT;
    }



    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收数据
        $attribute = $request->getParameter('attribute'); // 属性
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径
        $product_number = addslashes(trim($request->getParameter('product_number'))); // 产品编号
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 小标题
        $scan = addslashes(trim($request->getParameter('scan'))); // 条形码

        $product_class = addslashes(trim($request->getParameter('product_class'))); // 产品类别
        $brand_id = addslashes(trim($request->getParameter('brand_class'))); // 品牌
        $keyword = addslashes(trim($request->getParameter('keyword'))); // 关键词
        $weight = addslashes(trim($request->getParameter('weight'))); // 重量
        $s_type = $request->getParameter('s_type'); // 显示类型
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        $content = addslashes(trim($request->getParameter('content'))); // 产品内容
        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 产品图片
        $distributor_id = trim($request->getParameter('distributor_id')); //关联的分销层级id
        $is_distribution = trim($request->getParameter('is_distribution')); //是否开启分销
        $is_zhekou = trim($request->getParameter('is_zhekou')); //是否开启会员商品折扣
        $volume = trim($request->getParameter('volume')); //拟定销量
        $freight = $request->getParameter('freight'); // 运费

        $arr = json_decode($attribute,true);
        if($product_title == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('产品名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select id,product_title from lkt_product_list";
            $r = $db->select($sql);
            if($r){
                foreach ($r as $k => $v){
                    header("Content-type:text/html;charset=utf-8");
                    if($product_title == $v->product_title){
                        echo "<script type='text/javascript'>" .
                            "alert('产品名称重复！');" .
                            "</script>";
                        return $this->getDefaultView();
                    }
                }
            }
        }
        if($scan == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('条形码不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select id from lkt_product_list where scan = '$scan'";
            $r = $db->select($sql);
            if($r){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('条形码重复！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        if($product_class == '0'){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('请选择产品类别！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($brand_id == '0'){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('请选择品牌！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($keyword == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('请填写关键词！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($weight == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('请填写商品重量！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            if(is_numeric($weight)){
                if($weight < 0){
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('重量不能为负数！');" .
                        "</script>";
                    return $this->getDefaultView();
                }else{
                    $weight = number_format($weight,2);
                }
            }else{
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('请填写正确的商品重量值！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        $z_num = 0;
        if(count($arr) == 0){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('请填写属性！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            foreach ($arr as $ke => $va){
                $z_num = $z_num+$va['数量'];
            }
        }

        if(count($s_type) == 0){
            $type = 0;
        }else{
            $type = implode(",", $s_type);
        }
        if($sort == ''){
            echo "<script type='text/javascript'>" .
                "alert('排序不能没空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        if($image){
            $image = preg_replace('/.*\//','',$image); // 产品主图
        }else{
            if($oldpic){
                $image = preg_replace('/.*\//','',$oldpic);
            }else{
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('产品主图不能没空！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        // 发布产品
        $sql = "insert into lkt_product_list(product_number,product_title,subtitle,scan,product_class,brand_id,keyword,weight,imgurl,sort,content,num,s_type,add_date,volume,freight) " .
            "values('$product_number','$product_title','$subtitle','$scan','$product_class','$brand_id','$keyword','$weight','$image','$sort','$content','$z_num','$type',CURRENT_TIMESTAMP,'$volume','$freight')";

        $id1 = $db->insert($sql,'last_insert_id'); // 得到添加数据的id
        if($id1){
            $files=($_FILES['imgurls']['tmp_name']);
            if($files[0]){
                foreach($files as $key => $file){
                    $img_type = $_FILES['imgurls']["type"][$key];
                    if($img_type == "image/png"){
                        $img_type = ".png";
                    }elseif ($img_type == "image/jpeg") {
                        $img_type = ".jpg";
                    }else{
                        $img_type = ".gif";
                    }
                    $imgURL_name = time().mt_rand(1,100).$img_type;
                    //重命名结束
                    $info = move_uploaded_file($file,$uploadImg.$imgURL_name);
                    if($info){
                        //循环遍历插入
                        $sql_img = "insert into lkt_product_img(product_url,product_id,add_date) " . "values('$imgURL_name','$id1',CURRENT_TIMESTAMP)";
                        $id2 = $db->insert($sql_img,'last_insert_id');
                    }
                }
            }

            $r_num = 0;
            $c_num = 0;
            foreach ($arr as $ke => $va){
                $costprice = $va['成本价'];
                $yprice = $va['原价'];
                $price = $va['现价'];
                $num = $va['数量'];
                $unit = $va['单位'];
                $img = trim(strrchr($va['图片'], '/'),'/');
                for ( $i = 0;$i < 6;$i++){
                    array_pop($va);
                }
                $attribute_1 = $va;
                $attribute = serialize($attribute_1);

                $sql = "insert into lkt_configure(costprice,yprice,price,img,pid,num,unit,attribute) values('$costprice','$yprice','$price','$img','$id1','$num','$unit','$attribute')";


                $r_attribute = $db->insert($sql);

                $c_num += $num;
                if($r_attribute > 0){
                    $r_num = $r_num + 1;
                }else{
                    $r_num = $r_num;
                }
            }
            if($r_num == count($arr)){
                if($c_num < 1){
                    $sql_1 = "update lkt_product_list set status='1' where id = '$id1'";
                    $r_update = $db->update($sql_1);
                }
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('产品发布成功！');" .
                    "location.href='index.php?module=product';</script>";
                return $this->getDefaultView();
            }else{
                $sql = "delete from lkt_product_list where id = '$id1'";
                $db->delete($sql);

                $sql = "delete from lkt_product_img where product_id = '$id1'";
                $db->delete($sql);

                $sql = "delete from lkt_product_attribute where pid = '$id1'";
                $db->delete($sql);


                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，产品发布失败1！');" .
                    "location.href='index.php?module=product';</script>";
                return $this->getDefaultView();
            }
        }else{

            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，产品发布失败2！');" .
                "location.href='index.php?module=product';</script>";
            return $this->getDefaultView();
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }
}
?>
