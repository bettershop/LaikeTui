<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class seeAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = intval($request->getParameter("id"));
        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        // 根据产品id，查询产品产品信息
        $sql = "select * from lkt_product_list where id = '$id'";
        $r = $db->select($sql);
        $status = 0;

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

        return View :: INPUT;
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

    public function execute()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $id = addslashes(trim($request->getParameter('id'))); // 产品id
        $attribute_id = addslashes(trim($request->getParameter('attribute_id'))); // 属性id
        $num = $request->getParameter('num'); // 数量
        $nn = $db->select("select num from lkt_configure where pid = '$id' and id = '$attribute_id'");
        $nn = $nn[0]->num;
        if (floor($num) == $nn) {
            echo 2;
            exit;
        }
        if (floor($num) == $num) {
            if ($num > 0) {
                $sql = "update lkt_configure set num = '$num' where pid = '$id' and id = '$attribute_id'";
                $rr = $db->update($sql);

                $sql = "select num from lkt_configure where pid = '$id'";
                $r = $db->select($sql);
                if ($r) {
                    $znum = 0;
                    foreach ($r as $k => $v) {
                        $znum += $v->num;
                    }
                    $sql = "update lkt_product_list set num = '$znum' where id = '$id'";
                    $db->update($sql);
                }
                if ($rr == -1) {
                    $db->admin_record($admin_id, ' 修改属性id为 ' . $attribute_id . ' 的库存失败 ', 2);

                    echo 0;
                    exit;
                } else {
                    $db->admin_record($admin_id, ' 修改属性id为 ' . $attribute_id . ' 的库存 ', 2);

                    echo 1;
                    exit;
                }
            } else {
                $db->admin_record($admin_id, ' 修改属性id为 ' . $attribute_id . ' 的库存失败 ', 2);

                echo 0;
                exit;
            }
        } else {
            $db->admin_record($admin_id, ' 修改属性id为 ' . $attribute_id . ' 的库存失败 ', 2);

            echo 0;
            exit;
        }
    }

    public function getRequestMethods()
    {
        return Request :: POST;
    }

}

