<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class modifyAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $_SESSION['url'] = $_SERVER['HTTP_REFERER'];
        // 接收信息
        $id = intval($request->getParameter("id")); // 产品id

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        // 根据产品id，查询产品产品信息
        $sql = "select * from lkt_product_list where id = '$id'";
        $r = $db->select($sql);
        $status = 0;
        $sort = 0 ;
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
            $sort = $r[0]->sort;

        }

        $arr = explode(',', $s_type);

        if (!empty($brand_class)) {
            $sql01 = "select brand_id ,brand_name from lkt_brand_class where brand_id = $brand_class";
            $r01 = $db->select($sql01);
            $brand_name = $r01[0]->brand_name; // 产品品牌
        }

        //运费

        $sql = "select id,name from lkt_freight order by id ";
        $r_freight = $db->select($sql);
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
        $sql = "select cid,pname from lkt_product_class where recycle = 0 and sid = 0  order by sort desc";
        $r = $db->select($sql);
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
            $r_e = $db->select($sql_e);
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
                    $r_t = $db->select($sql_t);
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
        $sql01 = "select * from lkt_brand_class where recycle = 0 and status = 0 order by sort asc, brand_time desc";
        $r01 = $db->select($sql01);
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
        $imgurls = $db->select($imgs_sql);

        //查询规格数据
        $size = "select * from lkt_configure where pid = '$id' and recycle =0";
        $res_size = $db->select($size);
        $attr_group_list = [];
        $checked_attr_list = [];
        if ($res_size) {

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
            foreach ($res_size as $k => $v) {
                $res_row = $db->selectrow("select id from lkt_order_details where sid = '$v->id' and r_status !=3 and r_status !=5 and r_status !=6");
                $attribute = unserialize($v->attribute); // 属性
                $attr_lists = [];
                //列出属性名 
                foreach ($attribute as $key => $value) {

                    if ($res_row && $attr_group_list) {
                        // print_r($value);
                        foreach ($attr_group_list as $keya => $valuea) {
                            if ($key == $valuea['attr_group_name']) {
                                foreach ($valuea['attr_list'] as $key11 => $value11) {
                                    if ($value == $value11['attr_name']) {
                                        $attr_group_list[$keya]['attr_list'][$key11]['status'] = false;
                                    }
                                }
                            }
                        }
                    }

                }

            }
            if ($attr_group_list) {
                foreach ($attr_group_list as $k => $v) {
                    $re11 = 0;
                    $v['status'] = false;
                    if ($v['attr_list']) {
                        foreach ($v['attr_list'] as $ke001 => $va001) {
                            if ($va001['status'] == true) {
                                $re11 = $re11 + 1;
                            }
                        }
                    }
                    if (count($v['attr_list']) == $re11) {
                        $attr_group_list[$k]['status'] = true;

                    }
                    unset($re11);
                }
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
        $request->setAttribute("status", $status);
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
        $request->setAttribute('sort', $sort);

        return View :: INPUT;
    }

    public function execute()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter("id")); // 产品id
        $uploadImg = $request->getParameter('uploadImg'); // 图片上传位置
        $attr = $request->getParameter('attr'); // 属性
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 产品标题
        $product_class = addslashes(trim($request->getParameter('product_class'))); // 产品类别
        $subtitle = addslashes(trim($request->getParameter('subtitle'))); // 产品副标题
        $brand_id = addslashes(trim($request->getParameter('brand_class'))); // 品牌
        $weight = addslashes(trim($request->getParameter('weight'))); // 关键词
        $s_type = $request->getParameter('s_type'); // 显示类型
        $content = trim($request->getParameter('content')); // 产品内容
        $image = addslashes(trim($request->getParameter('image'))); // 产品图片
        $img_oldpic = addslashes(trim($request->getParameter('img_oldpic'))); // 产品图片
        $initial = $request->getParameter('initial'); // 初始值
        $volume = trim($request->getParameter('volume')); //拟定销量
        $freight = $request->getParameter('freight'); // 运费
        $sort = $request->getParameter('sort');


        if ($initial) {
            $initial = serialize($initial);
        }

        $z_num = 0;
        $attributes = [];
        //处理属性
        if (count($attr ? $attr : []) == 0 || empty($attr)) {

        } else {
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

        $db->begin();

        if (count($s_type) == 0) {
            $type = 0;
        } else {
            $type = implode(",", $s_type);
        }
        if ($image) {
            $image = preg_replace('/.*\//', '', $image);
            if ($image != $img_oldpic) {
                @unlink($uploadImg . $img_oldpic);
            }
        } else {
            $image = $img_oldpic;
        }

        //五张轮播图
        $files = ($_FILES['imgurls']['tmp_name']);

        if ($files[0]) {
            $ql_img = "delete from lkt_product_img  where product_id = '$id'";
            $db->delete($ql_img);

            foreach ($files as $key => $file) {
                // 移动到框架应用对应目录下
                //重命名
                $img_type = $_FILES['imgurls']["type"][$key];
                if ($img_type == "image/png") {
                    $img_type = ".png";
                } elseif ($img_type == "image/jpeg") {
                    $img_type = ".jpg";
                } else {
                    $img_type = ".gif";
                }
                $imgsURL_name = time() . mt_rand(1, 100) . $img_type;
                //重命名结束
                $info = move_uploaded_file($file, "../LKT/images/$imgsURL_name");
                if ($info) {
                    //循环遍历插入
                    $sql_img = "insert into lkt_product_img(id,product_url,product_id,add_date) " . "values(0,'$imgsURL_name','$id',CURRENT_TIMESTAMP)";
                    $db->insert($sql_img);
                }
            }
        }


        // 根据产品id,修改产品信息
        $sql_1 = "update lkt_product_list set product_title=?,product_class=?,brand_id =?,weight=?,s_type=?,num=?,content=?,imgurl=?,subtitle=?,volume=?,freight=?,initial=?,sort=? where id = ? ";
        $data = array();
        $data[] = $product_title;
        $data[] = $product_class;
        $data[] = $brand_id;
        $data[] = $weight;
        $data[] = $type;
        $data[] = $z_num;
        $data[] = $content;
        $data[] = $image;
        $data[] = $subtitle;
        $data[] = $volume;
        $data[] = $freight;
        $data[] = $initial;
        $data[] = $sort;
        $data[] = $id;
        $r_update = $db->preUpdate($sql_1,$data);


        $cids = [];
        if ($attributes) {
            $sql = "select id from lkt_configure where pid = '$id'";
            $rcs = $db->select($sql);
            if ($rcs) {
                foreach ($rcs as $keyc => $valuec) {
                    $cids[$valuec->id] = $valuec->id;
                }
            }
        }

        foreach ($attributes as $ke => $va) {
            $num = $va['num'];
            $cid = $va['cid'];
            $va = $this->array_key_remove($va, 'cid');

            if ($cid) {
                if (array_key_exists($cid, $cids)) {
                    unset($cids[$cid]);
                }
                // 查询剩余数量
                $ccc = $db->select("select num,total_num from lkt_configure where id = '$cid' ");
                $cnums = $ccc ? $ccc[0]->num : 0;
                $va['total_num'] = $ccc ? $ccc[0]->total_num : 0;
                $z_num1 = 0;
                if ($num > $cnums) {
                    $z_num1 = $num - $cnums; // 传过来的剩余数量 - 数据库里的剩余数量
                    $sql = "insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$id','$cid','$z_num1',0,CURRENT_TIMESTAMP)";
                    $db->insert($sql);
                    $va['total_num'] = $va['total_num'] + $z_num1;
                } else if ($num < $cnums) {
                    $z_num1 = $cnums - $num;
                    // 在库存记录表里，添加一条入库信息
                    $sql = "insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$id','$cid','$z_num1',1,CURRENT_TIMESTAMP)";
                    $db->insert($sql);
                    $va['total_num'] = $va['total_num'];
                } else {
                    $va['total_num'] = $va['total_num'];
                }

                $r_attribute = $db->modify($va, 'lkt_configure', " `id` = '$cid' and recycle = 0 ");

                if ($r_attribute < 0) {
                    $db->modify($va, 'lkt_configure', " `id` = '$cid'", 1);
                    $db->rollback();
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('属性数据修改失败，请稍后再试！');" .
                        "</script>";
                    return $this->getDefaultView();
                }
            } else {
                $va['pid'] = $id;
                $va['total_num'] = $num;
                $r_attribute = $db->insert_array($va, 'lkt_configure', '', 1);
                if ($r_attribute < 0) {
                    $db->rollback();
                    header("Content-type:text/html;charset=utf-8");
                    echo "<script type='text/javascript'>" .
                        "alert('属性数据添加失败，请稍后再试！');" .
                        "</script>";
                    return $this->getDefaultView();

                } else {

                    $sql = "insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$id','$r_attribute','$num',0,CURRENT_TIMESTAMP)";
                    $db->insert($sql);


                }
            }

        }
        //删除属性
        if (!empty($cids)) {
            foreach ($cids as $keyds => $valueds) {
                $db->delete("DELETE FROM `lkt_configure` WHERE (`id`='$valueds')");
            }
        }




        if ($z_num < 1) {
                $sql_1 = "update lkt_product_list set status='1' where id = '$id'";
        } else {
                $rr = $db->select("select status from lkt_product_list where id = '$id'");
                $status = $rr[0]->status ? $rr[0]->status : 0;
                if ($status == 2) {
                    $sql_1 = "update lkt_product_list set status='2' where id = '$id'";
                } else {
                    $sql_1 = "update lkt_product_list set status='0' where id = '$id'";
                }

        }
        $db->update($sql_1);
        $db->commit();
        jump($_SESSION['url'], '产品修改成功！');
        exit;
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
