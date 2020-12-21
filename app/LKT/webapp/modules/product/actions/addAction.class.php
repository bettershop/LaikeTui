<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/db.class.php');
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class addAction extends Action
{

    public function getDefaultView()
    {

        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        /*** 报错不清除输入内容 ***/
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $brand_id1 = addslashes(trim($request->getParameter('brand_class'))); // 品牌
        $product_class = addslashes(trim($request->getParameter('product_class'))); // 产品类别
        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 小标题
        $attribute = $request->getParameter('attr'); // 属性
        $initial = $request->getParameter('initial'); // 初始值
        $weight = addslashes(trim($request->getParameter('weight'))); // 重量
        $s_type = $request->getParameter('s_type'); // 类型
        $volume = trim($request->getParameter('volume')); //拟定销量

        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 产品图片
        $freight1 = $request->getParameter('freight'); // 运费
        $content = addslashes(trim($request->getParameter('content'))); // 产品内容
        $checked_attr_list = $attribute ? $attribute : '';
        $attr_group_list = '';
        if ($attribute) {
            $attr_group_list = $this->attr($attribute);
        }

        if (!$s_type) {
            $s_type = [];
        }
        if ($image == '') {
            $image = $oldpic;
        }
        /*** 报错不清除输入内容 结束 ***/

        $sql = "select * from lkt_config where id = '1'";
        $r = lkt_gets($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        $res = $this->product_class($product_class);//产品类别
        $brand = $this->brand($brand_id1);//品牌
        $distributors = [];

        // 运费
        $sql = "select id,name,is_default from lkt_freight order by is_default desc, add_time desc";
        $rr = lkt_gets($sql);
        $freight = [];
        $freight_num = 0;
        if ($rr) {
            foreach ($rr as $key => $value) {
                if ($freight1 && $freight1 == $value->id) {
                    $freight[$freight_num] = (object)array('id' => $value->id, 'name' => $value->name);
                } else {
                    $freight[$freight_num] = (object)array('id' => $value->id, 'name' => $value->name);
                }
            }
        }
        if ($initial != '') {
            $initial = $initial;
        } else {
            $initial = array();
        }

        $attr_group_list = json_encode($attr_group_list);
        $checked_attr_list = json_encode($checked_attr_list);
        $initial = (object)$initial;
        $request->setAttribute("distributors", $distributors);
        $request->setAttribute("uploadImg", $uploadImg);//图片上传地址
        $request->setAttribute("ctype", $res);//产品类别
        $request->setAttribute("brand", $brand);//品牌
        $request->setAttribute("freight", $rr);//运费
        $request->setAttribute('rew', isset($rew) ? $rew : '');//未填写的产品规格名称
        $request->setAttribute('product_title', isset($product_title) ? $product_title : '');//商品名称
        $request->setAttribute('subtitle', isset($subtitle) ? $subtitle : '');//副标题
        $request->setAttribute('s_type', isset($s_type) ? $s_type : '');//显示类型（1：新品,2：热销，3：推荐）
        $request->setAttribute('weight', isset($weight) ? $weight : '');//重量
        $request->setAttribute('image', isset($image) ? $image : '');//产品主图片
        $request->setAttribute('content', isset($content) ? $content : '');//内容
        $request->setAttribute('volume', $volume ? $volume : '0');//销量
        $request->setAttribute('initial', isset($initial) ? $initial : '');
        $request->setAttribute("checked_attr_list", $checked_attr_list ? $checked_attr_list : '');
        $request->setAttribute("attr_group_list", $attr_group_list ? $attr_group_list : '');
        return View :: INPUT;
    }

    public function attr($attribute)
    {//属性

        $attr_group_list = array();
        $attr_group_list1 = [];
        foreach ($attribute as $key => $value) {
            $aa = $value['attr_list'];
            foreach ($aa as $key01 => $value01) {
                $attr_group_list[] = array('attr_group_name' => $value01['attr_group_name'], 'attr_list' => $value01['attr_name'], 'attr_all' => '');
            }
        }
        if ($attr_group_list) {
            foreach ($attr_group_list as $key02 => $value02) {
                $attr_group_name[] = $value02['attr_group_name'];
            }
            $attr_group_name = array_unique($attr_group_name);

            if ($attr_group_name) {
                foreach ($attr_group_name as $keya => $valuea) {
                    foreach ($attr_group_list as $key03 => $value03) {
                        if ($valuea == $value03['attr_group_name']) {
                            $attr_list1 = array('attr_name' => $value03['attr_list'], 'status' => true);

                            $attr_group_list1[$keya]['attr_group_name'] = $valuea;
                            $attr_group_list1[$keya]['attr_list'][] = $attr_list1;
                        }

                    }
                }
            }
        }
        return $attr_group_list1;
    }

    public function product_class($product_class)
    {//产品类别
        $db = DBAction::getInstance();
        $res = '';
        if (!empty($product_class)) {
            //获取产品类别
            $sql = "select cid,pname from lkt_product_class where sid = 0 and recycle =0";
            $r = lkt_gets($sql);

            foreach ($r as $key => $value) {
                $c = '-' . $value->cid . '-';
                if ($c == $product_class) {
                    $res .= '<option selected  value="-' . $value->cid . '-" >' . $value->pname . '</option>';

                } else {
                    $res .= '<option  value="-' . $value->cid . '-">' . $value->pname . '</option>';
                }

                //循环第一层
                $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid and recycle =0";
                $r_e = lkt_gets($sql_e);
                if ($r_e) {
                    $hx = '-----';
                    foreach ($r_e as $ke => $ve) {
                        $cone = $c . $ve->cid . '-';
                        if ($cone == $product_class) {
                            $res .= '<option selected  value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                        } else {
                            $res .= '<option  value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                        }

                        //循环第二层
                        $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid and recycle =0";
                        $r_t = lkt_gets($sql_t);
                        if ($r_t) {
                            $hxe = $hx . '-----';
                            foreach ($r_t as $k => $v) {
                                $ctow = $cone . $v->cid . '-';

                                if ($ctow == $product_class) {
                                    $res .= '<option selected value="' . $ctow . '">' . $hxe . $v->pname . '</option>';

                                } else {
                                    $res .= '<option  value="' . $ctow . '">' . $hxe . $v->pname . '</option>';
                                }
                            }
                        }
                    }
                }
            }
        } else {
            //获取产品类别
            $sql = "select cid,pname from lkt_product_class where sid = 0 and recycle =0";
            $r = lkt_gets($sql);

            foreach ($r as $key => $value) {
                $c = '-' . $value->cid . '-';
                $res .= '<option  value="-' . $value->cid . '-">' . $value->pname . '</option>';
                //循环第一层
                $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid and recycle =0";
                $r_e = lkt_gets($sql_e);
                if ($r_e) {
                    $hx = '-----';
                    foreach ($r_e as $ke => $ve) {
                        $cone = $c . $ve->cid . '-';
                        $res .= '<option  value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                        //循环第二层
                        $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid and recycle =0";
                        $r_t = lkt_gets($sql_t);
                        if ($r_t) {
                            $hxe = $hx . '-----';
                            foreach ($r_t as $k => $v) {
                                $ctow = $cone . $v->cid . '-';
                                $res .= '<option  value="' . $ctow . '">' . $hxe . $v->pname . '</option>';
                            }
                        }
                    }
                }
            }
        }
        return $res;
    }

    public function brand($brand_id1)
    {//品牌
        $db = DBAction::getInstance();
        $sql01 = "select brand_id ,brand_name from lkt_brand_class where status = 0 and recycle = 0 ";
        $r01 = lkt_gets($sql01);
        $brand = '';
        $brand_num = 0;
        if ($r01) {
            if ($brand_id1) {
                foreach ($r01 as $k01 => $v01) {
                    if ($v01->brand_id == $brand_id1) {
                        $brand .= '<option selected value="' . $v01->brand_id . '">' . $v01->brand_name . '</option>';
                    } else {
                        $brand .= '<option  value="' . $v01->brand_id . '">' . $v01->brand_name . '</option>';
                    }
                }
            } else {
                foreach ($r01 as $k2 => $v2) {
                    $brand .= '<option  value="' . $v2->brand_id . '">' . $v2->brand_name . '</option>';

                }
            }
        }
        return $brand;
    }

    public function execute()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收数据
        $attr = $request->getParameter('attr'); // 属性
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 小标题
        $initial = $request->getParameter('initial'); // 初始值
        $oldinitial = $initial;
        $product_class = addslashes(trim($request->getParameter('product_class'))); // 产品类别
        $brand_id = addslashes(trim($request->getParameter('brand_class'))); // 品牌
        $weight = addslashes(trim($request->getParameter('weight'))); // 重量
        $s_type = $request->getParameter('s_type'); // 显示类型
        $content = trim($request->getParameter('content')); // 产品内容
        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 产品图片
        $volume = trim($request->getParameter('volume')); //拟定销量
        $freight = $request->getParameter('freight'); // 运费


        if ($initial) {
            $initial = serialize($initial);
        }

        $z_num = 0;
        $attributes = [];
        //有设置属性的情况

        if (isset($attr) && count($attr) > 0) {
            foreach ($attr as $key => $value) {
                $attr_list = $value['attr_list'];
                $attr_list_arr = [];
                $attr_list_srt = '';
                foreach ($attr_list as $k => $v) {
                    $attr_list_arr[$v['attr_group_name']] = $v['attr_name'];
                    $attr_list_srt .= $v['attr_group_name'] . '-' . $v['attr_name'];
                }
                $z_num += $value['num'];
                $value['img'] = preg_replace('/.*\//', '', $value['img']);
                $value['attribute'] = serialize($attr_list_arr);
                $value = $this->array_key_remove($value, 'attr_list');
                $attributes[] = $value;
            }
        }else{
            $timg = preg_replace('/.*\//', '', $image);
            $z_num += $oldinitial['kucun'];
            $attributes[] = Array ( 'costprice' => $oldinitial['cbj'], 'yprice' => $oldinitial['yj'], 'price' => $oldinitial['sj'], 'num' =>  $oldinitial['kucun'], 'unit' =>'', 'img' => $timg, 'attribute' => 'a:1:{s:6:"默认";s:6:"默认";}' );
        }

        if (count($s_type) == 0) {
            $type = 0;
        } else {
            $type = implode(",", $s_type);
        }
        if ($image) {
            $image = preg_replace('/.*\//', '', $image); // 产品主图
        } else {
            if ($oldpic) {
                $image = preg_replace('/.*\//', '', $oldpic);
            }
        }

        //    开启事务
        $db->begin();

        // 发布产品
        $sql = "insert into lkt_product_list(product_title,subtitle,product_class,brand_id,weight,imgurl,content,num,s_type,add_date,volume,freight,initial,status,sort) " .
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        $data = array();
        $data[] = $product_title;
        $data[] = $subtitle;
        $data[] = $product_class;
        $data[] = $brand_id;
        $data[] = $weight;
        $data[] = $image;
        $data[] = $content;
        $data[] = $z_num;
        $data[] = $type;
        $data[] =  date("Y-m-d H:i:s");
        $data[] = $volume;
        $data[] = $freight;
        $data[] = $initial;
        $data[] = 2;
        $data[] = 0;
        $id1 = $db->preInsert($sql,$data);


        if ($id1) {
            $files = ($_FILES['imgurls']['tmp_name']);
            if ($files[0]) {
                foreach ($files as $key => $file) {
                    $img_type = $_FILES['imgurls']["type"][$key];
                    if ($img_type == "image/png") {
                        $img_type = ".png";
                    } elseif ($img_type == "image/jpeg") {
                        $img_type = ".jpg";
                    } else {
                        $img_type = ".gif";
                    }
                    $imgURL_name = time() . mt_rand(1, 100) . $img_type;
                    //重命名结束
                    $info = move_uploaded_file($file, $uploadImg . $imgURL_name);//把图片移动到指定文件夹
                    if ($info) {
                        //循环遍历插入商品图片表
                        $sql_img = "insert into lkt_product_img(id,product_url,product_id,add_date) " . "values(0,'$imgURL_name','$id1',CURRENT_TIMESTAMP)";
                        $db->insert($sql_img, 'last_insert_id');

                    }
                }
            }

            $r_num = 0;
            $c_num = 0;
            foreach ($attributes as $ke => $va) {//循环遍历插入商品规格表
                $costprice = $va['costprice'];
                $yprice = $va['yprice'];
                $price = $va['price'];
                $num = $va['num'];
                $unit = $va['unit'];
                $img = $va['img'];
                $attribute = $va['attribute'];//属性，数组转字符串

                $sql = "insert into lkt_configure(costprice,yprice,price,img,pid,num,unit,attribute,total_num) values('$costprice','$yprice','$price','$img','$id1','$num','$unit','$attribute','$num')";//成本价 ，原价，现价，商品图片，ID ，数量，单位，属性 
                $r_attribute = $db->insert($sql, 'last_insert_id');
                // 在库存记录表里，添加一条入库信息
                $sql = "insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$id1','$r_attribute','$num',0,CURRENT_TIMESTAMP)";
                $db->insert($sql);

                $c_num += $num;//所有商品数量
                if ($r_attribute > 0) {
                    $r_num = $r_num + 1;
                } else {
                    $r_num = $r_num;
                }
            }

            if ($r_num == count($attributes)) {//判断属性是否添加完全
                if ($c_num < 1) {//库存不足，下架（0::上架 1:下架）
                    $sql_1 = "update lkt_product_list set status='1' where id = '$id1'";
                    $db->update($sql_1);
                }
                $db->commit();
                jump('index.php?module=product', '产品发布成功！');
                exit;


            } else {
                $sql = "delete from lkt_product_list where id = '$id1'";
                $db->delete($sql);
                $sql = "delete from lkt_product_img where product_id = '$id1'";
                $db->delete($sql);
                $sql = "delete from lkt_product_attribute where pid = '$id1'";
                $db->delete($sql);
                $db->rollback();
                jump('index.php?module=product', '未知原因，产品发布失败！');
                exit;

            }

        } else {
            $db->rollback();
            jump('index.php?module=product', '未知原因，产品发布失败！');

        }
        return;
    }

    //删除指定数组元素[description]
    public static function array_key_remove($arr, $key)
    {
        if (!array_key_exists($key, $arr)) {
            return $arr;
        }
        $keys = array_keys($arr);
        $index = array_search($key, $keys);
        if ($index !== FALSE) {
            array_splice($arr, $index, 1);
        }
        return $arr;
    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

}

?>
