<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once('BaseAction.class.php');

class IndexAction extends BaseAction
{

    // 获取小程序首页数据
    public function index()
    {

        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];
        $title = $appConfig['appName'];
        $logo = $appConfig['logo'];

        // 查询轮播图,根据排序、轮播图id顺序排列
        $sql = "select * from lkt_banner order by sort,id";
        $r = lkt_gets($sql);
        $banner = array();
        foreach ($r as $k => $v) {
            $result = array();
            $result['id'] = $v->id; // 轮播图id
            $result['image'] = $img . $v->image; // 图片
            $result['url'] = $v->url; // 链接
            $banner[] = $result;
            unset($result); // 销毁指定变量
        }

        $shou = [];
        $sql_cs = "select a.initial,a.id,a.product_title,a.volume,a.imgurl,c.price,a.sort  
from lkt_product_list AS a RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid) AS c ON a.id = c.pid 
where a.status = 0 and a.num >0 and s_type like '%4%' 
 order by a.sort asc,a.volume desc limit  0,10";
        $r_cs = lkt_gets($sql_cs);
        if ($r_cs) {
            foreach ($r_cs as $keyc => $valuec) {
                $valuec->imgurl = $img . $valuec->imgurl;
                $initial = unserialize($valuec->initial);
                $pid = $valuec->id;
                $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                $r_ttt = lkt_gets($sql_ttt);
                $price = $r_ttt[0]->price;
                $price_yh = $r_ttt[0]->yprice;
                $valuec->price = $price;
                $valuec->price_yh = $price_yh;

                $shou[] = $valuec;
            }
        }


        //列出等级关系
        $distributor = [];
        //查询商品并分类显示返回JSON至小程序
        $sql_c = 'select cid,pname from lkt_product_class where sid=0 and recycle!=1 order by sort asc';
        $r_c = lkt_gets($sql_c);
        $twoList = [];
        foreach ($r_c as $key => $value) {
            $sql_e = 'select cid,pname,img from lkt_product_class where sid=\'' . $value->cid . '\' and recycle!=1 order by sort desc LIMIT 0,10';
            $r_e = lkt_gets($sql_e);
            $icons = [];
            if ($r_e) {
                foreach ($r_e as $ke => $ve) {
                    $imgurl = $img . $ve->img;
                    $icons[$ke] = array('id' => $ve->cid, 'name' => $ve->pname, 'img' => $imgurl);
                }
            } else {
                $icons = [];
            }

            $ttcid = $value->cid;

            $sql_s = "select a.id,a.product_title,a.volume,c.price,a.imgurl 
from lkt_product_list AS a 
RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid)  AS c 
ON a.id = c.pid 
where a.product_class like '%-$ttcid-%' and a.status = 0 and a.num >0
order by a.sort DESC LIMIT 0,10";

            $r_s = lkt_gets($sql_s);
            $product = [];

            $r_s = empty($r_s) ? [] : ($r_s ? $r_s : []);

            foreach ($r_s as $k => $v) {
                $imgurl = $img . $v->imgurl;
                $pid = $v->id;
                $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                $r_ttt = lkt_gets($sql_ttt);
                $price = $r_ttt[0]->price;
                $price_yh = $r_ttt[0]->yprice;

                $product[$k] = array('id' => $v->id, 'product_title' => $v->product_title, 'price' => $price, 'price_yh' => $price_yh, 'imgurl' => $imgurl, 'volume' => $v->volume);
            }
            $twoList['0'] = array('id' => '0', 'name' => '首页', 'count' => 1, 'twodata' => $shou, 'distributor' => $distributor);
            $twoList[$key + 1] = array('id' => $value->cid, 'name' => $value->pname, 'count' => 1, 'twodata' => $product, 'icons' => $icons);
        }
        $sql = "select * from lkt_background_color where status = 1";
        $r = lkt_gets($sql);
        if ($r) {
            $bgcolor = $r[0]->color;
        } else {
            $bgcolor = '#FF6347';
        }


        // 查询插件表里,状态为启用的插件
        $sql = "select * from lkt_plug_ins where status = 1 and type = 0 and software_id = 3";
        $plug = lkt_gets($sql);
        if ($plug) {

            foreach ($plug as $k => $v) {
                $v->image = $img . $v->image;
            }
        }
        $pmd = [];

        $lkt_set_notice = "select id,name from lkt_set_notice order by time desc";
        $notice = [];
        $res_notice = lkt_gets($lkt_set_notice);//公告
        if ($res_notice) {
            foreach ($res_notice as $key => $value) {
                $notice[$key] = array('url' => $value->id, 'title' => $value->name);
            }
        }
        echo json_encode(array('banner' => $banner, 'notice' => $notice, 'djname' => '', 'twoList' => $twoList, 'bgcolor' => $bgcolor, 'plug' => $plug, 'title' => $title, 'logo' => $logo, 'list' => $pmd));
        exit();

    }


    // 加载更多商品
    public function get_more()
    {

        $request = $this->getContext()->getRequest();
        $paegr = addslashes(trim($request->getParameter('page'))); //  '显示位置'
        $index = addslashes(trim($request->getParameter('index'))); //  '分类ID'

        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];

        if (!$paegr) {
            $paegr = 1;
        }
        $start = 10 * $paegr;
        $end = 10;
        $product = array();
        //查询商品并分类显示返回JSON至小程序
        if ($index == 0) {

            $sql_cs = "select a.initial,a.id,a.product_title,a.volume,a.imgurl,c.price,a.sort  
from lkt_product_list AS a RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid) AS c ON a.id = c.pid 
where a.status = 0 and a.num >0 and s_type like '%4%' 
 order by a.sort asc,a.volume desc limit  $start,$end";

            $r_cs = lkt_gets($sql_cs);

            if ($r_cs) {
                foreach ($r_cs as $keyc => $valuec) {
                    $pid = $valuec->id;
                    $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                    $r_ttt = lkt_gets($sql_ttt);
                    $price = $r_ttt[0]->price;
                    $price_yh = $r_ttt[0]->yprice;
                    $valuec->imgurl = $img . $valuec->imgurl;
                    $product[] = array('id' => $valuec->id, 'product_title' => $valuec->product_title, 'price' => $price, 'price_yh' => $price_yh, 'imgurl' => $valuec->imgurl, 'volume' => $valuec->volume);
                }
            }

            echo json_encode(array('prolist' => $product, 'status' => 1));
            exit;
        } else if (!$index) {
            echo json_encode(array('prolist' => [], 'status' => 0));
            exit;

        } else {
            //查询商品并分类显示返回JSON至小程序
            //修改搜索条件  商品分类ID错误
            $sql_t = "select a.initial,a.id,a.product_title,a.volume,a.imgurl,c.price,a.sort  
from lkt_product_list AS a RIGHT JOIN (select min(price) price,pid from lkt_configure group by pid) AS c ON a.id = c.pid 
where a.status = 0 and a.num >0 and product_class like '%-$index-%' 
 order by a.sort asc,a.volume desc limit  $start,$end";
            $r_s = lkt_gets($sql_t);
            $product = [];
            if ($r_s) {
                foreach ($r_s as $k => $v) {
                    $imgurl = $img . $v->imgurl;/* end 保存*/
                    $pid = $v->id;
                    $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                    $r_ttt = lkt_gets($sql_ttt);
                    $price = $r_ttt[0]->price;
                    $price_yh = $r_ttt[0]->yprice;
                    $product[$k] = array('id' => $v->id, 'product_title' => $v->product_title, 'price' => $price, 'price_yh' => $price_yh, 'imgurl' => $imgurl, 'volume' => $v->volume);
                }
                echo json_encode(array('prolist' => $product, 'status' => 1));
                exit;
            } else {
                echo json_encode(array('prolist' => $product, 'status' => 0));
                exit;
            }
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

