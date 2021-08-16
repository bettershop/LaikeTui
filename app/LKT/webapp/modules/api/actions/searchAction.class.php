<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once('BaseAction.class.php');

class searchAction extends BaseAction
{

    public function index()
    {
        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];
        //查询商品并分类显示返回JSON至小程序
        $sql_c = "select cid,pname,img,bg from lkt_product_class where sid=0 and recycle != 1 order by sort asc";
        $r_c = lkt_gets($sql_c);

        $twoList = [];
        $abc = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $st = 0;
        $icons = [];
        if ($r_c) {
            foreach ($r_c as $key => $value) {
                $sql_e = 'select cid,pname,img from lkt_product_class where sid=\'' . $value->cid . '\' and recycle != 1 order by sort asc';
                $r_e = lkt_gets($sql_e);
                $son = [];
                if ($r_e) {
                    foreach ($r_e as $ke => $ve) {
                        $imgurl = $img . $ve->img;
                        $son[$ke] = array('child_id' => $ve->cid, 'name' => $ve->pname, 'picture' => $imgurl);
                    }
                    $type = true;
                } else {
                    $type = false;
                }
                if ($value->bg) {
                    $cimgurl = $img . $value->bg;
                } else {
                    $cimgurl = '';
                }

                $icons[$key] = array('cate_id' => $value->cid, 'cate_name' => $value->pname, 'ishaveChild' => $type, 'children' => $son, 'cimgurl' => $cimgurl);

            }
        }


        $sql = 'select keyword from lkt_hotkeywords';
        $res = lkt_rows($sql);
        if ($res) {
            foreach ($res as $k => $v) {
                $res[$k] = $v['keyword'];
            }
        }

        echo json_encode(array('status' => 1, 'List' => $icons, 'hot' => $res));
        exit;
    }

    public function search()
    {
        $request = $this->getContext()->getRequest();
        $keyword = addslashes(trim($request->getParameter('keyword'))); // 关键词
        $num = addslashes(trim($request->getParameter('num'))); //  '次数'
        $select = addslashes(trim($request->getParameter('select'))); //  选中的方式 0 默认  1 销量   2价格
        $sort = addslashes(trim($request->getParameter('sort'))); // 排序方式  1 asc 升序   0 desc 降序

        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        if ($select == 0) {
            $select = 'a.add_date';
        } elseif ($select == 1) {
            $select = 'a.volume';
        } else {
            $select = 'price';
        }
        if ($sort) {
            $sort = ' asc ';
        } else {
            $sort = ' desc ';
        }

        //查出所有产品分类
        $sql = 'select pname from lkt_product_class where recycle != 1';
        $res = lkt_gets($sql);
        if ($res) {
            foreach ($res as $key => $value) {
                $res[] = $value->pname;
            }
        }

        //判断如果关键词是产品分类名称，如果是则查出该类里所有商品
        if (in_array($keyword, $res)) {
            $type = 0;
            $keyword = addslashes($keyword);
            $sqla = "select cid from lkt_product_class where pname='$keyword' and recycle != 1";
            $a = lkt_gets($sqla);
            if (!empty($a)) {
                $cid = $a['0']->cid; // 分类id
            }
            $start = 10 * ($num - 1);
            $end = 10;
            $sqlb = "select a.id,a.product_title,a.product_class,a.volume,a.s_type,
a.imgurl as img ,c.price 
from lkt_product_list AS a RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid) AS c
ON a.id = c.pid 
where a.product_class like '%$cid%' and a.status = 0  
order by $select $sort LIMIT $start,$end
";
            $data = lkt_gets($sqlb);

        } else {   //如果不是商品分类名称，则直接搜产品
            $type = 1;
            $keyword = addslashes($keyword);
            $sqlb = "select a.id,a.product_title,a.product_class,a.volume,a.s_type,
a.imgurl as img ,c.price 
from lkt_product_list AS a RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid) AS c
ON a.id = c.pid 
where a.product_title like '%$keyword%' and a.status = 0  
order by $select $sort 
";
            $data = lkt_gets($sqlb);
        }
        if (!empty($data)) {
            $product = array();
            foreach ($data as $k => $v) {
                $imgurl = $img . $v->img;/* end 保存*/
                if ($type == 1) {
                    $cid = $v->product_class;
                } else {
                    $cid = $cid;
                }
                $names = '';
                $product[$k] = array('id' => $v->id, 'name' => $v->product_title . $names, 'price' => $v->price, 'price_yh' => $v->price, 'imgurl' => $imgurl, 'size' => 0, 'volume' => $v->volume, 's_type' => $v->s_type);
            }
            echo json_encode(array('list' => $product, 'cid' => $cid, 'code' => 1, 'type' => $type));
            exit();
        } else {
            echo json_encode(array('status' => 0, 'err' => '没有更多商品了！'));
            exit();
        }
    }

    public function listdetail()
    {
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('cid'))); //  '分类ID'
        $paegr = addslashes(trim($request->getParameter('page'))); //  '页面'
        $select = addslashes(trim($request->getParameter('select'))); //  选中的方式 0 默认  1 销量   2价格

        if ($select == 0) {
            $select = 'a.add_date';
        } elseif ($select == 1) {
            $select = 'a.volume';
        } else {
            $select = 'c.price';
        }

        $sort = addslashes(trim($request->getParameter('sort'))); // 排序方式  1 asc 升序   0 desc 降序
        if ($sort) {
            $sort = ' asc ';
        } else {
            $sort = ' desc ';
        }

        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        if (!$paegr) {
            $paegr = 1;
        }
        $start = ($paegr - 1) * 10;
        $end = 10;
        $bg = '';
        $sql_c = "select bg from lkt_product_class where cid='$id' ";
        $r_c = lkt_gets($sql_c);
        if ($r_c) {
            $bg = $img . $r_c[0]->bg;
        }

        $sql = "select a.initial,a.imgurl,a.id,a.product_title,a.product_class,a.volume,a.s_type,
a.imgurl as img ,c.price 
from lkt_product_list AS a RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid) AS c
ON a.id = c.pid 
where a.product_class like '%-$id-' and a.status = 0  
order by $select $sort LIMIT $start,$end
";

        $r = lkt_gets($sql);
        if ($r) {
            foreach ($r as $key => $value) {
                $pid = $value->id;//id
                $prrr = 0;//初始售价
                $yprrr = 0;//初始原价
                if ($value->initial != '') {
                    $initial = unserialize($value->initial);
                    $prrr = $initial['sj'];
                    $yprrr = $initial['yj'];
                }
                $imgurl = $img . $value->imgurl;/* end 保存*/
                $sql = "select id,num,unit,price,yprice from lkt_configure where pid = '$pid'";//根据商品ID去查询商品对应的规格
                $r_s = lkt_gets($sql);
                if ($r_s) {
                    $price = [];
                    $yprice = [];
                    $unit = $r_s[0]->unit;
                    foreach ($r_s as $k1 => $v1) {
                        $price[$k1] = $v1->price;
                        $yprice[$k1] = $v1->yprice;
                    }
                    $min = min($price);
                    $ymin = min($yprice);
                    $present_price = $min;//最低价格
                } else {
                    $unit = '';
                    $present_price = $prrr;
                    $ymin = $yprrr;
                }

                $value->unit = $unit;
                $value->price = $present_price;
                $product[$key] = array('id' => $pid, 'name' => $value->product_title, 'price' => $ymin, 'price_yh' => $value->price, 'imgurl' => $imgurl, 'volume' => $value->volume, 's_type' => $value->s_type);
            }
            echo json_encode(array('status' => 1, 'pro' => $product, 'bg' => $bg));
        } else {
            echo json_encode(array('status' => 0, 'err' => '没有了！'));
            exit;
        }
    }


    public function class_sort($product_class)//根据类别查询下一级
    {
        $typestr = trim($product_class, '-');
        $typeArr = explode('-', $typestr);
        //  取数组最后一个元素 并查询分类名称
        $cid = end($typeArr);//找到本级ID
        $k[] = '-' . $product_class . '-';

        if (!empty($cid)) {//循环下一级
            $sql_e = "select cid,pname from lkt_product_class where recycle = 0 and sid = $cid";
            $r_e = lkt_gets($sql_e);
            if ($r_e) {
                foreach ($r_e as $k01 => $v01) {//循环第三级
                    $k[] = '-' . $product_class . '-' . $v01->cid . '-';
                    $sql_e01 = "select cid,pname from lkt_product_class where recycle = 0 and sid = $v01->cid";
                    $r_e01 = lkt_gets($sql_e01);

                    if ($r_e01) {
                        foreach ($r_e01 as $k02 => $v02) {

                            $k[] = '-' . $product_class . '-' . $v01->cid . '-' . $v02->cid . '-';
                        }
                    }
                }
            }
        }

        return $k;
    }


}

