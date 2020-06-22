<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $product_class = addslashes(trim($request->getParameter('cid'))); // 分类名称
        $brand_id = addslashes(trim($request->getParameter('brand_id'))); // 品牌id
        $status = addslashes(trim($request->getParameter('status'))); // 上下架
        $s_type = addslashes(trim($request->getParameter('s_type'))); // 类型
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 标题
        $pagesize = 10;
        // 每页显示多少条数据
        $page = $request->getParameter('page');

        // 页码
        if ($page) {
            $start = ($page - 1) * $pagesize;
        } else {
            $start = 0;
        }

        //分类
        $sql = "select cid,pname from lkt_product_class where recycle = 0 and sid = 0  order by sort desc";
        $rr = $db->select($sql);
        $res = $this->product_class($rr, $product_class);

        //品牌
        $sql = "select * from lkt_brand_class where recycle = 0 and status = 0 order by sort asc, brand_time desc";
        $rr1 = $db->select($sql);
        $rew = '';
        foreach ($rr1 as $key => $value) {
            if ($brand_id == $value->brand_id) {
                $rew .= "<option selected='selected' value='" . $value->brand_id . "'>$value->brand_name</option>";
            } else {
                $rew .= "<option value='" . $value->brand_id . "'>$value->brand_name</option>";
            }
        }
        //查询设置的最低库存值，低于该库存值的商品不显示在该页面
        $sql = "select config from lkt_product_config where id =1";
        $rr = $db->select($sql);
        if ($rr) {
            $config = unserialize($rr[0]->config);
            $min_inventory = $config['min_inventory'];
        } else {
            $min_inventory = 0;
        }
        $k = '';
        $con = [];
        $condition = ' recycle = 0 and num > 0 ';//0.不回收 1.回收
        if ($product_class != 0) {//存在产品类别
            $k = $this->class_sort($product_class);
            if ($k) {
                foreach ($k as $key => $value) {
                    $con[] = "  a.product_class = '$value' ";
                }
                $dd = implode('or', $con);
                $condition .= "and (" . $dd . ")";
            }

        }
        if ($brand_id != 0) {//品牌
            $condition .= " and brand_id = '$brand_id' ";
        }
        if ($status != 0) {//状态 0:上架 1:下架2:待上架
            if ($status == 1) {
                $condition .= " and status = 1 ";
            } else if ($status == 2) {
                $condition .= " and status = 0 ";
            } else if ($status == 3) {
                $condition .= " and status = 2 ";
            }
        }
        if ($s_type != 0) {//产品值属性 1：新品,2：热销，3：推荐
            $condition .= " and s_type like '%$s_type%' ";
        }
        if ($product_title != '') {//产品名字
            $condition .= " and a.product_title like '%$product_title%' ";
        }
        $sql = "select * from lkt_product_list as a where $condition order by a.sort asc,status asc,a.add_date desc ";
        $r_pager = $db->select($sql);
        if ($r_pager) {
            $total = count($r_pager);
        } else {
            $total = 0;
        }
        $pager = new ShowPager($total, $pagesize, $page);//[total_record] => 12 [pagesize] => 10 [total_pages] => 2 [cur_page] => 1 [offset] => 0 [_pernum] => 10

        $sql = "select * from lkt_product_list as a where $condition order by a.sort asc,status asc,a.add_date desc limit $start,$pagesize ";
        $r = $db->select($sql);
        $list = [];
        $status_num = 0;
        foreach ($r as $key => $value) {
            $pid = $value->id;//id
            $prrr = 0;//初始价格
            if ($value->initial != '') {
                $initial = unserialize($value->initial);
                $prrr = $initial['sj'];
            }

            $class = $value->product_class;//产品类别
            $num = $value->num;//数量
            $value->s_type = explode(',', $value->s_type);//产品值属性 1：新品,2：热销，3：推荐 (1,2,3)
            $typestr = trim($class, '-');//移除左右 -
            $typeArr = explode('-', $typestr);
            //  取数组最后一个元素 并查询分类名称
            $cid = end($typeArr);
            $sql_p = "select pname from lkt_product_class where cid ='" . $cid . "'";
            $r_p = $db->select($sql_p);
            if ($r_p) {
                $pname = $r_p['0']->pname;
            } else {
                $pname = '顶级';
            }
            if ($num == 0 && $value->status == 0) { // 当库存为0 并且商品还为上架状态
                $status_num += 1;
            }

            $sql = "select id,num,unit,price from lkt_configure where pid = '$pid'";//根据商品ID去查询商品对应的规格
            $r_s = $db->select($sql);
            if ($r_s) {
                $price = [];
                $unit = $r_s[0]->unit;
                foreach ($r_s as $k1 => $v1) {
                    $price[$k1] = $v1->price;
                    $configure_id = $v1->id;
                    if ($v1->num <= $min_inventory && $v1->num > 0) {//还有商品，但是库存不足 修改状态  status( 0:未开启砍价 1:开启砍价 2 上架 3 缺货 4下架)
                        $sql = "update lkt_configure set status = 3 where id = '$configure_id'";
                        $db->update($sql);
                    } else if ($v1->num == 0) {
                        // $sql = "update lkt_configure set status = 4 where id = '$configure_id'";
                        // $db->update($sql);
                    }
                }
                $min = min($price);
                $present_price = $min;//最低价格
            } else {
                $unit = '';
                $present_price = $prrr;
            }

            //根据品牌ID查询对应名称
            $r01 = $db->select("select brand_name from lkt_brand_class where brand_id ='" . $value->brand_id . "'");
            $value->brand_name = $r01 ? $r01[0]->brand_name : '';
            $value->unit = $unit;
            $value->price = $present_price;
            $value->pname = $pname;
            $list[$key] = $value;
        }
        if ($status_num > 0) {
            $this->getDefaultView();
        }

        $url = "index.php?module=product&action=Index&cid=" . urlencode($product_class) . "&brand_id=" . urlencode($brand_id) . "&status=" . urlencode($status) . "&s_type=" . urlencode($s_type) . "&product_title=" . urlencode($product_title) . "&pagesize=" . urlencode($pagesize);
        $pages_show = $pager->multipage($url, $total, $page, $pagesize, $start, $para = '');// url 总条数 当前页码  每页显示条数

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $request->setAttribute("uploadImg", $uploadImg);// 图片上传位置
        $request->setAttribute("product_title", $product_title);//商品名称
        $request->setAttribute("class", $res);//分类名称
        $request->setAttribute("rew", $rew);//品牌
        $request->setAttribute("s_type", $s_type);//产品值属性 1：新品,2：热销，3：推荐
        $request->setAttribute("status", $status);//状态 0:上架 1:下架
        $request->setAttribute("list", $list);
        $request->setAttribute("min_inventory", $min_inventory);//库存
        $request->setAttribute('pages_show', $pages_show);//分页
        $request->setAttribute('pagesize', $pagesize);

        return View :: INPUT;
    }


    public function class_sort($product_class)//根据类别查询下一级
    {
        $db = DBAction::getInstance();
        $typestr = trim($product_class, '-');
        $typeArr = explode('-', $typestr);
        //  取数组最后一个元素 并查询分类名称
        $cid = end($typeArr);//找到本级ID
        $k[] = $product_class;

        if (!empty($cid)) {//循环下一级
            $sql_e = "select cid,pname from lkt_product_class where recycle = 0 and sid = $cid";
            $r_e = $db->select($sql_e);
            if ($r_e) {
                foreach ($r_e as $k01 => $v01) {//循环第三级
                    $k[] = $product_class . $v01->cid . '-';
                    $sql_e01 = "select cid,pname from lkt_product_class where recycle = 0 and sid = $v01->cid";
                    $r_e01 = $db->select($sql_e01);

                    if ($r_e01) {
                        foreach ($r_e01 as $k02 => $v02) {

                            $k[] = $product_class . $v01->cid . '-' . $v02->cid . '-';
                        }
                    }
                }
            }
        }
        return $k;
    }

    //所有的商品分类
    public function product_class($rr, $product_class)
    {
        $db = DBAction::getInstance();
        $res = '';
        if ($rr) {
            foreach ($rr as $key => $value) {
                $c = '-' . $value->cid . '-';
                //判断所属类别 添加默认标签
                if ($product_class == $c) {
                    $res .= '<option selected="selected" value="' . $c . '">' . $value->pname . '</option>';
                } else {
                    $res .= '<option  value="' . $c . '">' . $value->pname . '</option>';
                }
                //循环第一层
                $sql_e = "select cid,pname from lkt_product_class where recycle = 0 and sid = $value->cid";

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
                        $sql_t = "select cid,pname from lkt_product_class where recycle = 0 and sid = $ve->cid";
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

        }

        return $res;

    }

    public function execute()
    {

    }

    public function getRequestMethods()
    {
        return Request :: NONE;
    }

}

?>