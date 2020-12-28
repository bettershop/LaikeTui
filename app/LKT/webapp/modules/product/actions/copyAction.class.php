<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/db.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class copyAction extends Action
{

    public function getDefaultView()
    {
        $request = $this->getContext()->getRequest();
        // 接收信息
        $id = intval($request->getParameter("id")); // 产品id
        $sql = "select * from lkt_config where id = '1'";
        $r = lkt_gets($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        // 根据产品id，查询产品产品信息
        $sql = "select * from lkt_product_list where id = '$id'";
        $r = lkt_gets($sql);
        if ($r) {
            $product_title = $r[0]->product_title; // 产品标题
            $subtitle = $r[0]->subtitle; // 副标题
            $product_class = $r[0]->product_class; // 产品类别
            $brand_class = $r[0]->brand_id; // 产品品牌
            $weight = $r[0]->weight; // 重量
            $content = $r[0]->content; // 产品内容
            $num = $r[0]->num; //数量
            $imgurl = $r[0]->imgurl; //图片
            $s_type = $r[0]->s_type;
            $volume = $r[0]->volume;//volume拟定销量
            $freight_id = $r[0]->freight;
            $status = $r[0]->status; // 上下架状态
            $initial = $r[0]->initial;//初始值
        }

        $arr = explode(',', $s_type);

        //运费

        $sql = "select id,name from lkt_freight order by id ";
        $r_freight = lkt_gets($sql);
        $freight_list = '';
        if ($r_freight) {
            foreach ($r_freight as $key => $value) {
                $freight_id1 = $value->id; // 运费规则id
                $freight_name = $value->name; // 运费规则
                if ($freight_id1 == $freight_id) {
                    $freight_list .= "<option selected='selected' value='{$freight_id1}'>{$freight_name}</option>";
                } else {
                    $freight_list .= "<option value='{$freight_id1}'>{$freight_name}</option>";
                }
            }
        }
        //绑定产品分类
        $sql = "select cid,pname from lkt_product_class where sid = 0 and recycle = 0 order by sort desc";
        $r = lkt_gets($sql);
        $res = '';
        foreach ($r as $key => $value) {
            $c = '-' . $value->cid . '-';
            //判断所属类别 添加默认标签
            if ($product_class == $c) {
                $res .= '<option selected="selected" value="' . $c . '">' . $value->pname . '</option>';
            } else {
                $res .= '<option  value="' . $c . '">' . $value->pname . '</option>';
            }
            //循环第一层
            $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid and recycle = 0";
            $r_e = lkt_gets($sql_e);
            if ($r_e) {
                $hx = '-----';
                foreach ($r_e as $ke => $ve) {
                    $cone = $c . $ve->cid . '-';
                    //判断所属类别 添加默认标签
                    if ($product_class == $cone) {
                        $res .= '<option selected="selected" value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                    } else {
                        $res .= '<option  value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                    }
                    //循环第二层
                    $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid and recycle = 0";
                    $r_t = lkt_gets($sql_t);
                    if ($r_t) {
                        $hxe = $hx . '-----';
                        foreach ($r_t as $k => $v) {
                            $ctow = $cone . $v->cid . '-';
                            //判断所属类别 添加默认标签
                            if ($product_class == $ctow) {
                                $res .= '<option selected="selected" value="' . $ctow . '">' . $hxe . $v->pname . '</option>';
                            } else {
                                $res .= '<option  value="' . $ctow . '">' . $hxe . $v->pname . '</option>';
                            }
                        }
                    }
                }
            }
        }

        // 品牌
        $sql01 = "select brand_id ,brand_name from lkt_brand_class where status = 0 and recycle = 0 order by sort asc, brand_time desc";
        $r01 = lkt_gets($sql01);
        $brand = '';
        $brand_num = 0;
        if ($r01) {
            if ($brand_class) {
                foreach ($r01 as $k01 => $v01) {
                    if ($v01->brand_id == $brand_class) {
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


        $imgs_sql = "select * from lkt_product_img where product_id = '$id'";
        $imgurls = lkt_gets($imgs_sql);

        //查询规格数据
        $size = "select * from lkt_configure where pid = '$id' and  recycle = 0";
        $res_size = lkt_gets($size);

        if ($res_size) {
            $attr_group_list = [];
            $checked_attr_list = [];
            $arrar_t = unserialize($res_size[0]->attribute);
            foreach ($arrar_t as $key => $value) {
                $attr_group_list[] = array('attr_group_name' => $key, 'attr_list' => [], 'attr_all' => []);
            }
            foreach ($res_size as $k => $v) {
                $attribute = unserialize($v->attribute); // 属性
                $attr_lists = [];
                //列出属性名 
                foreach ($attribute as $key => $value) {
                    foreach ($attr_group_list as $keya => $valuea) {
                        if ($key == $valuea['attr_group_name']) {
                            ;
                            if (!in_array($value, $attr_group_list[$keya]['attr_all'])) {

                                $attr_list = array('attr_name' => $value, 'status' => true);

                                array_push($attr_group_list[$keya]['attr_list'], $attr_list);
                                array_push($attr_group_list[$keya]['attr_all'], $value);
                            }
                        }
                    }
                    $attr_lists[] = array('attr_id' => '', 'attr_group_name' => $key, 'attr_name' => $value);
                }
                $checked_attr_list[] = array('attr_list' => $attr_lists, 'costprice' => $v->costprice, 'yprice' => $v->yprice, 'price' => $v->price, 'num' => $v->num, 'unit' => $v->unit, 'img' => $uploadImg . '/' . $v->img, 'cid' => $v->id);
            }
            foreach ($attr_group_list as $key => $value) {
                $attr_group_list[$key] = $this->array_key_remove($attr_group_list[$key], 'attr_all');
            }
        }
        if ($initial != '') {
            $initial = unserialize($initial);
        } else {
            $initial = array();
        }

        $initial = (object)$initial;
        $attr_group_list = json_encode($attr_group_list);
        $checked_attr_list = json_encode($checked_attr_list);
        $request->setAttribute("volume", $volume);
        $request->setAttribute("uploadImg", $uploadImg);
        $request->setAttribute("checked_attr_list", $checked_attr_list);
        $request->setAttribute("attr_group_list", $attr_group_list);
        $request->setAttribute('initial', isset($initial) ? $initial : '');
        $request->setAttribute('s_type', $arr);
        $request->setAttribute("ctypes", $res);
        $request->setAttribute('id', $id);
        $request->setAttribute('r02', $brand);//所有品牌
        $request->setAttribute('product_title', isset($product_title) ? $product_title : '');
        $request->setAttribute('subtitle', isset($subtitle) ? $subtitle : '');
        $request->setAttribute('weight', isset($weight) ? $weight : '');
        $request->setAttribute('content', isset($content) ? $content : '');
        $request->setAttribute('num', isset($num) ? $num : '');
        $request->setAttribute('imgurl', isset($imgurl) ? $imgurl : '');
        $request->setAttribute('imgurls', isset($imgurls) ? $imgurls : '');
        $request->setAttribute('freight_list', $freight_list);// 运费
        return View :: INPUT;
    }

    public function execute()
    {
        $request = $this->getContext()->getRequest();
        // 接收数据
        $attr = $request->getParameter('attr'); // 属性
        $uploadImg = addslashes(trim($request->getParameter('uploadImg'))); // 图片路径
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 小标题
        $initial = $request->getParameter('initial'); // 初始值
        $product_class = addslashes(trim($request->getParameter('product_class'))); // 产品类别
        $brand_id = addslashes(trim($request->getParameter('brand_class'))); // 品牌
        $weight = addslashes(trim($request->getParameter('weight'))); // 重量
        $s_type = $request->getParameter('s_type'); // 显示类型
        $content = trim($request->getParameter('content')); // 产品内容
        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $oldpic = addslashes(trim($request->getParameter('oldpic'))); // 产品图片
        $imgurls = $request->getParameter('imgurls'); // 产品图片
        $volume = trim($request->getParameter('volume')); //拟定销量
        $freight = $request->getParameter('freight'); // 运费        


        if ($initial) {
            $initial = serialize($initial);
        }

        $z_num = 0;
        $attributes = [];
        if (count($attr) > 0) {
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
        //开启事务
        lkt_start();
        // 发布产品
        $sql = "insert into lkt_product_list(product_title,subtitle,product_class,brand_id,weight,imgurl,content,num,s_type,add_date,volume,freight,initial,status) " .
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        $data[] = date("Y-m-d H:i:s");
        $data[] = $volume;
        $data[] = $freight;
        $data[] = $initial;
        $data[] = 2;
        $id1 = lkt_insert($sql, $data);

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
                        $sql_img = "insert into lkt_product_img(product_url,product_id,add_date) " . "values('$imgURL_name','$id1',CURRENT_TIMESTAMP)";
                        lkt_execute($sql_img);

                    }
                }
            } elseif ($imgurls && !$files[0]) {
                foreach ($imgurls as $key => $value) {
                    //循环遍历插入商品图片表
                    $sql_img = "insert into lkt_product_img(id,product_url,product_id,add_date) " . "values(0,'$value','$id1',CURRENT_TIMESTAMP)";
                    lkt_execute($sql_img);
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
                $r_attribute = lkt_insert($sql);
                // 在库存记录表里，添加一条入库信息
                $sql = "insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$id1','$r_attribute','$num',0,CURRENT_TIMESTAMP)";
                lkt_insert($sql);

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
                    lkt_execute($sql_1);
                }
                lkt_commit();
                jump('index.php?module=product', '产品发布成功!');
                exit;
            } else {
                $sql = "delete from lkt_product_list where id = '$id1'";
                lkt_execute($sql);

                $sql = "delete from lkt_product_img where product_id = '$id1'";
                lkt_execute($sql);

                $sql = "delete from lkt_product_attribute where pid = '$id1'";
                lkt_execute($sql);

                lkt_rollback();
                jump('index.php?module=product', '未知原因，产品发布失败！!');
                exit;
            }
        } else {
            lkt_rollback();
            jump('index.php?module=product', '未知原因，产品发布失败！!');
            exit;
        }

    }

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
