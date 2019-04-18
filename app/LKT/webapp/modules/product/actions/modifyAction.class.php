<?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');

class modifyAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        // 接收信息
        $id = intval($request->getParameter("id")); // 产品id

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        // 根据产品id，查询产品产品信息
        $sql = "select * from lkt_product_list where id = '$id'";
        $r = $db->select($sql);

        if($r){
            $product_number = $r[0]->product_number; // 产品编号
            $product_title = $r[0]->product_title; // 产品标题
            $subtitle = $r[0]->subtitle; // 副标题
            $scan = $r[0]->scan; // 条形码
            $product_class = $r[0]->product_class ; // 产品类别
            $sort = $r[0]->sort; // 排序
            $brand_class = $r[0]->brand_id ; // 产品品牌
            $keyword = $r[0]->keyword ; // 关键词
            $weight = $r[0]->weight ; // 重量
            $content = $r[0]->content; // 产品内容
            $num = $r[0]->num; //数量
            $imgurl = $r[0]->imgurl; //图片
            $s_type = $r[0]->s_type;
            $distributor_id = $r[0]->distributor_id;//分销层级id
            $is_distribution = $r[0]->is_distribution;//是否开启分销
            $volume = $r[0]->volume;//volume拟定销量
            $freight_id = $r[0]->freight;
            $is_zhekou = $r[0]->is_zhekou;
        }
        $arr = explode(',',$s_type);

        if (!empty($brand_class)) {
            $sql01 = "select brand_id ,brand_name from lkt_brand_class where brand_id = $brand_class";
            $r01 = $db->select($sql01);
            $brand_name = $r01[0]->brand_name ; // 产品品牌
        }

        if($freight_id != 0){
            $sql = "select id,name from lkt_freight where id = $freight_id";
            $r_freight = $db->select($sql);
            $freight_name = $r_freight[0]->name ; // 运费规则
            $freight_list = "<option selected='selected' value='{$freight_id}'>{$freight_name}</option>";
            $freight_list .= "<option value='0'>默认模板</option>";
        }else{
            $freight_list = "<option selected='selected' value='0'>默认模板</option>";
            $sql = "select id,name from lkt_freight order by id ";
            $r_freight = $db->select($sql);
            if($r_freight){
                foreach ($r_freight as $k => $v){
                    $freight_id = $v->id ; // 运费规则id
                    $freight_name = $v->name ; // 运费规则
                    $freight_list .= "<option value='{$freight_id}'>{$freight_name}</option>";
                }
            }
        }

        //绑定产品分类
        $sql = "select cid,pname from lkt_product_class where sid = 0 ";
        $r = $db->select($sql);
        $res = '';
        foreach ($r as $key => $value) {
            $c = '-'.$value->cid.'-';
            //判断所属类别 添加默认标签
            if ($product_class == $c) {
                $res .= '<option selected="selected" value="'.$c.'">'.$value->pname.'</option>';
            }else{
                $res .= '<option  value="'.$c.'">'.$value->pname.'</option>';
            }
            //循环第一层
            $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid";
            $r_e = $db->select($sql_e);
            if($r_e){
                $hx = '-----';
                foreach ($r_e as $ke => $ve){
                    $cone = $c . $ve->cid.'-';
                    //判断所属类别 添加默认标签
                    if ($product_class == $cone) {
                        $res .= '<option selected="selected" value="'.$cone.'">'.$hx.$ve->pname.'</option>';
                    }else{
                        $res .= '<option  value="'.$cone.'">'.$hx.$ve->pname.'</option>';
                    }
                    //循环第二层
                    $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid";
                    $r_t = $db->select($sql_t);
                    if($r_t){
                        $hxe = $hx.'-----';
                        foreach ($r_t as $k => $v){
                            $ctow = $cone . $v->cid.'-';
                            //判断所属类别 添加默认标签
                            if ($product_class == $ctow) {
                                $res .= '<option selected="selected" value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                            }else{
                                $res .= '<option  value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                            }
                        }
                    }
                }
            }
        }

        //产品分类
        $sql02 = "select brand_id ,brand_name from lkt_brand_class where status = 0";
        $r02 = $db->select($sql02);

        $imgs_sql = "select * from lkt_product_img where product_id = '$id'";
        $imgurls = $db->select($imgs_sql);

        //查询规格数据
        $size = "select * from lkt_configure where pid = '$id'";
        $res_size = $db->select($size);
        if($res_size){
            $attribute = [];
            $attribute_1 = [];
            $attribute_2 = '';
            $attribute_3 = [];
            foreach ($res_size as $k => $v){
                $attribute_3['rid'] = $v->id;
                $attribute_2 = unserialize($v->attribute); // 属性
                $attribute_1 = array_merge ($attribute_3,$attribute_2);
                $attribute_1['成本价'] = $v->costprice;
                $attribute_1['原价'] = $v->yprice;
                $attribute_1['现价'] = $v->price;
                $attribute_1['数量'] = $v->num;
                $attribute_1['单位'] = $v->unit;
                $attribute_1['图片'] = $uploadImg . $v->img;
                $attribute[] = $attribute_1;
            }
        }

        $attribute1 = json_encode($attribute);
        $unit_arr = $attribute[0];
        array_pop($unit_arr); // 删除数组最后一个元素
        $unit = end($unit_arr); // 取数组最后一个元素
        $attribute_key = array_keys($attribute[0]); // 属性表格第一栏
        $attribute_key1 = array_keys($attribute[0]); // 填写属性
        for ($i=0;$i<6;$i++){
            array_pop($attribute_key1); // 循环去掉数组后面6个元素
        }
        if($unit == ''){
            $rer = "<option value='个'>" .
                '个'.
                "</option>";
        }else{
            $rer = "<option value='$unit'>" .
                $unit.
                "</option>";
        }
        $rew = '';
        foreach ($attribute_key1 as $key1 => $val1){
            if($key1 != 0){
                $rew .= "<div style='margin: 5px auto;' class='attribute_".($key1)." option' id='cattribute_".($key1)."' >";
                $rew .= "<input type='text' name='attribute_name' id='attribute_name_".($key1)."' placeholder='属性名称' value='".$val1."' class='input-text' readonly='readonly' style=' width:50%;background-color: #EEEEEE;' />" .
                    " - " .
                    "<input type='text' name='attribute_value' id='attribute_value_".($key1)."' placeholder='值' value='' class='input-text' style='width:45%' />";
                $rew .= "</div>";
            }
        }
        $num_k = count($attribute_key1);
        $rew .= "<div style='margin: 5px auto;display:none;' class='attribute_".$num_k." option' id='cattribute_".$num_k."' >" .
            "<input type='text' name='attribute_name' id='attribute_name_".$num_k."' placeholder='属性名称' value='' class='input-text' readonly='readonly' style=' width:50%;background-color: #EEEEEE;'  onblur='leave();'/>" .
            " - " .
            "<input type='text' name='attribute_value' id='attribute_value_".$num_k."' placeholder='值' value='' class='input-text' style='width:45%' onblur='leave();'/>" .
            "</div>";
        $attribute_key2 = json_encode($attribute_key1,JSON_UNESCAPED_UNICODE);
        $attribute_val = [];
        foreach ($attribute as $k1 => $v1){
            $attribute_val[] = array_values($v1); // 属性表格
        }
        $sql02 = "select id,sets from lkt_distribution_grade where is_ordinary = 0";
        $r022222 = $db->select($sql02);

        $distributors = [];
        $distributors_opt = '';

        $request->setAttribute("is_distribution",$is_distribution);
        $request->setAttribute("distributors_opt",$distributors_opt);
        $request->setAttribute("volume",$volume);
        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("attribute",$attribute);
        $request->setAttribute("attribute1",$attribute1);
        $request->setAttribute("rer",$rer);
        $request->setAttribute("rew",$rew);
        $request->setAttribute("attribute_key",$attribute_key);
        $request->setAttribute("attribute_key2",$attribute_key2);
        $request->setAttribute("attribute_val",$attribute_val);
        $request->setAttribute('s_type', $arr);  //s_type
        $request->setAttribute("ctypes",$res);
        $request->setAttribute('id', $id);
        $request->setAttribute('r02', $r02);//所有品牌
        $request->setAttribute("product_class",$product_class);
        $request->setAttribute('product_number', isset($product_number) ? $product_number : '');
        $request->setAttribute('product_title', isset($product_title) ? $product_title : '');
        $request->setAttribute('subtitle', isset($subtitle) ? $subtitle : '');
        $request->setAttribute('scan', isset($scan) ? $scan : '');
        $request->setAttribute('brand_name', isset($brand_name) ? $brand_name : '');//品牌名称
        $request->setAttribute('sort', isset($sort) ? $sort : '');
        $request->setAttribute('keyword', isset($keyword) ? $keyword : '');
        $request->setAttribute('weight', isset($weight) ? $weight : '');
        $request->setAttribute('content', isset($content) ? $content : '');
        $request->setAttribute('num', isset($num) ? $num : '');
        $request->setAttribute('imgurl', isset($imgurl) ? $imgurl : '');
        $request->setAttribute('imgurls', isset($imgurls) ? $imgurls : '');
        $request->setAttribute('freight_list', $freight_list);// 运费
        $request->setAttribute("is_zhekou",$is_zhekou);
        return View :: INPUT;
    }

    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $id = intval($request->getParameter("id")); // 产品id
        $uploadImg = $request->getParameter('uploadImg'); // 图片上传位置
        $attribute = $request->getParameter('attribute'); // 属性
        $product_number = addslashes(trim($request->getParameter('product_number'))); // 产品编号
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $product_class = addslashes(trim($request->getParameter('product_class'))); // 产品类别
        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 产品副标题
        $scan = addslashes(trim($request->getParameter('scan'))); // 条形码

        $brand_id = addslashes(trim($request->getParameter('brand_class'))); // 品牌
        $keyword = addslashes(trim($request->getParameter('keyword'))); // 关键词
        $weight = addslashes(trim($request->getParameter('weight'))); // 关键词
        $s_type = $request->getParameter('s_type'); // 显示类型
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        $content = addslashes(trim($request->getParameter('content'))); // 产品内容
        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $img_oldpic = addslashes(trim($request->getParameter('img_oldpic'))); // 产品图片
        $arr = json_decode($attribute,true);



        $volume = trim($request->getParameter('volume')); //拟定销量
        $freight = $request->getParameter('freight'); // 运费

        if($product_title == ''){
            echo "<script type='text/javascript'>" .
                "alert('产品名称不能为空！');" .
                "location.href='index.php?module=product&action=add';</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select product_title from lkt_product_list where id != '$id' and product_title = '$product_title'";
            $r = $db->select($sql);
            if($r){
                echo "<script type='text/javascript'>" .
                    "alert('{$product_title} 已经存在，请选用其他标题进行修改！');" .
                    "location.href='index.php?module=product&action=modify';</script>";
                return $this->getDefaultView();
            }
        }
        if($scan == ''){
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('条形码不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }else{
            $sql = "select id from lkt_product_list where scan = '$scan' and id != '$id'";
            $r = $db->select($sql);
            if($r){
                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('条形码重复！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        if($product_class == ''){
            echo "<script type='text/javascript'>" .
                "alert('产品类别不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($brand_id == ''){
            echo "<script type='text/javascript'>" .
                "alert('请选择品牌！');" .
                "</script>";
            return $this->getDefaultView();
        }
        if($keyword == ''){
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
                "location.href='index.php?module=product&action=modify';</script>";
            return $this->getDefaultView();
        }
        if($image){
            $image = preg_replace('/.*\//','',$image);
            if($image != $img_oldpic){
                @unlink ($uploadImg.$img_oldpic);
            }
        }else{
            $image = $img_oldpic;
        }

        //五张轮播图
        $files=($_FILES['imgurls']['tmp_name']);

        if($files[0]){
            $ql_img = "delete from lkt_product_img  where product_id = '$id'";
            $r = $db->delete($ql_img);
            foreach($files as $key => $file){
                // 移动到框架应用对应目录下
                //重命名
                $img_type = $_FILES['imgurls']["type"][$key];
                if($img_type == "image/png"){
                    $img_type = ".png";
                }elseif ($img_type == "image/jpeg") {
                    $img_type = ".jpg";
                }else{
                    $img_type = ".gif";
                }
                $imgsURL_name = time().mt_rand(1,100).$img_type;
                //重命名结束
                $info = move_uploaded_file($file,"../LKT/images/$imgsURL_name");
                if($info){
                    //循环遍历插入
                    $sql_img = "insert into lkt_product_img(product_url,product_id,add_date) " . "values('$imgsURL_name','$id',CURRENT_TIMESTAMP)";
                    $r = $db->insert($sql_img);
                }
            }
        }
        // 根据产品id，查询原来的数据
        $sql = "select * from lkt_product_list where id = '$id'";
        $r_arr = $db->select($sql);
        // 根据产品id,修改产品信息
        $sql_1 = "update lkt_product_list set product_number='$product_number',product_title='$product_title',scan='$scan',product_class='$product_class',brand_id ='$brand_id',keyword='$keyword',weight='$weight',s_type='$type',num='$z_num',sort='$sort',content='$content',imgurl='$image',subtitle='$subtitle',volume='$volume',freight='$freight' where id = '$id'";

        $r_update = $db->update($sql_1);

        if($r_update == -1 ){
            $rew1 = 0; // 修改失败
        }else{
            $rew1 = 1; // 修改成功
        }
        // 根据产品id,查询属性列表
        $sql = "select id from lkt_configure where pid = '$id'";
        $r_zarr = $db->select($sql);
        $rid = [];
        foreach ($r_zarr as $ke1 =>$v1){
            $rid[$v1->id] = $v1->id;
        }
        $r_count = count($r_zarr) - count($arr); // 原来的属性个数 - 现在的属性个数

        $r_num = 0;
        $c_num = 0;

        foreach ($arr as $ke => $va){
            if(!empty($va['rid'])){
                $r_id = $va['rid'];
                $r_id1 = $va['rid'];
            }else{
                $r_id = '';
                $r_id1 = '';
            }
            $costprice = $va['成本价'];
            $yprice = $va['原价'];
            $price = $va['现价'];
            $num = $va['数量'];
            $c_num += $num;
            $unit = $va['单位'];
            $img = trim(strrchr($va['图片'], '/'),'/');
            for ( $i = 0;$i < 6;$i++){
                array_pop($va); // 删除数组最后一个元素
            }
            if($r_id1 != ''){
                array_shift($va); // 删除数组第一个元素
            }

            $attribute_1 = $va;
            $attribute = serialize($attribute_1);


            if(in_array($r_id,$rid)){
                $sql = "update lkt_configure set costprice='$costprice',yprice='$yprice',price='$price',img='$img',num='$num',unit='$unit',attribute='$attribute' where id = '$r_id'";
                $r_attribute = $db->update($sql);
                unset($rid[$r_id]);
            }else{
                $sql = "insert into lkt_configure(costprice,yprice,price,img,pid,num,unit,attribute) values('$costprice','$yprice','$price','$img','$id','$num','$unit','$attribute')";
                $r_attribute = $db->insert($sql);
            }

            if($r_attribute > 0){
                $r_num = $r_num + 1;
            }else{
                $r_num = $r_num;
            }
        }
        if($rid){
            foreach ($rid as $ke2 => $va2){
                $sql = "delete from lkt_configure where id = '$va2'";
                $r_del = $db->delete($sql);
            }
        }

        if($r_num != count($arr)){
            $rew2 = 0;
        }else{
            $rew2 = 1;
        }

        if($rew1 == 1 || $rew2 == 1){
            if($c_num < 1){
                $sql_1 = "update lkt_product_list set status='1' where id = '$id'";
            }else{
                $sql_1 = "update lkt_product_list set status='0' where id = '$id'";
            }
            $r_update = $db->update($sql_1);
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('产品修改成功！');" .
                "location.href='index.php?module=product';</script>";
        }else{
            foreach ($r_arr[0] as $k_arr => $v_arr){
                $sql = "update lkt_product_list set product_title='$v_arr->product_title',product_class='$v_arr->product_class',brand_id ='$v_arr->brand_id',keyword='$v_arr->keyword',s_type='$v_arr->s_type',num='$v_arr->z_num',sort='$v_arr->sort',content='$v_arr->content',imgurl='$v_arr->image' where id = '$id'";
            }
            $r_y = $db->update($sql);
            echo "<script type='text/javascript'>" .
                "alert('未知原因，产品修改失败！');" .
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