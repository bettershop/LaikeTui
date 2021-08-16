<?php

/**
 * [Laike System] Copyright (c) 2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 * 描述：新增积分商品
 * 作者：ketter
 * 时间：2020/12/09
 */

class addGoodsAction extends PluginAction
{

    public function getDefaultView()
    {

    }

    //查询商品
    public function pro_query()
    {
        $request = $this->getContext()->getRequest();
        $my_class = addslashes(trim($request->getParameter('my_class')));
        $my_brand = addslashes(trim($request->getParameter('my_brand')));
        $pro_name = addslashes(trim($request->getParameter('pro_name')));


        $pagesize = $request->getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize : 10;
        // 每页显示多少条数据
        $page = $request->getParameter('page');

        if ($page) {
            $start = ($page - 1) * $pagesize;
        } else {
            $start = 0;
        }
        $condition = " and 1=1 ";
        if ($my_class) {
            $condition .= " and b.product_class like '%$my_class' " ;
        }
        if ($my_brand) {
            $condition .= " and b.brand_id = $my_brand " ;
        }
        if ($pro_name) {
            $condition .= " and b.product_title like '%$pro_name' " ;
        }

        //所有商品分类
        $sql01 = "select cid,pname from lkt_product_class where sid = 0 and recycle = 0";
        $rr = lkt_gets($sql01);
        $product_class = '';
        foreach ($rr as $key => $value) {
            $c = '-' . $value->cid . '-';
            //判断所属类别 添加默认标签
            if ($my_class == $c) {
                $product_class .= '<option selected="selected" value="' . $c . '">' . $value->pname . '</option>';
            } else {
                $product_class .= '<option  value="' . $c . '">' . $value->pname . '</option>';
            }
            //循环第一层
            $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid and recycle = 0 ";
            $r_e = lkt_gets($sql_e);
            if ($r_e) {
                $hx = '-----';
                foreach ($r_e as $ke => $ve) {
                    $cone = $c . $ve->cid . '-';
                    //判断所属类别 添加默认标签
                    if ($my_class == $cone) {
                        $product_class .= '<option selected="selected" value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                    } else {
                        $product_class .= '<option  value="' . $cone . '">' . $hx . $ve->pname . '</option>';
                    }
                    //循环第二层
                    $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid and recycle = 0";
                    $r_t = lkt_gets($sql_t);
                    if ($r_t) {
                        $hxe = $hx . '-----';
                        foreach ($r_t as $k => $v) {
                            $ctow = $cone . $v->cid . '-';
                            //判断所属类别 添加默认标签
                            if ($my_class == $ctow) {
                                $product_class .= '<option selected="selected" value="' . $ctow . '">' . $hxe . $v->pname . '</option>';
                            } else {
                                $product_class .= '<option  value="' . $ctow . '">' . $hxe . $v->pname . '</option>';
                            }
                        }
                    }
                }
            }
        }

        //全部品牌
        $brandsql = "select brand_id,brand_name from lkt_brand_class where  recycle = 0";
        $brandrs = lkt_gets($brandsql);
        $brand = "";
        foreach ($brandrs as $key => $value) {
            $brand .= '<option  value="' . $value->brand_id . '">' . $value->brand_name . '</option>';
        }

        //全部商品
        $sql01 = "select min(b.num) as num,min(a.attribute) attribute,min(a.price) price,min(a.id) AS attr_id,min(b.id) id,min(b.product_title) product_title,min(b.imgurl) imgurl
                from lkt_configure as a 
                left join lkt_product_list as b on a.pid = b.id 
                where b.recycle = 0 and b.num >0 and a.num > 0 and b.status = 0 $condition group by a.pid ";
        $res = lkt_gets($sql01);

        $appConfig = $this->getAppInfo();
        $img = $appConfig['imageRootUrl'];
        $products = [];

        foreach ($res as $k => $v) {

            $attr = unserialize($v->attribute);
            $attr = array_values($attr);
            if ($attr) {
                if (gettype($attr[0]) != 'string') unset($attr[0]);
            }
            $res[$k]->image = $img . $v->imgurl;
            $res[$k]->attr =  implode(',', $attr);

            //判断该商品ID是否已经在分销商品里面了
            $sql = "select * from lkt_score_pro where pro_id=".$v->id;
            $rs = lkt_gets($sql);
            if(!$rs){
                $products[]=$res[$k];
            }


        }
        $total = count($res);
        $products = array_slice($products, $start, $pagesize);
        echo json_encode(array('res' => $products, 'product_class' => $product_class, 'brand' => $brand, 'total' => $total, 'page' => $page));
        exit;
    }

    public function insert()
    {
        $request = $this->getContext()->getRequest();
        $data = $request->getParameter('data');
        lkt_start();
        $r_num = 0;
        if ($data) {
            foreach ($data as $key => $value) {
                $dd = array();
                $dd[] =  $value['id'];
                $dd[] =  $value['score'];
                $dd[] =  $value['is_show'];
                lkt_execute("insert into lkt_score_pro(pro_id,score,is_show) values(?,?,?)", $dd);
                $r_num = $r_num + 1;
            }
            if ($r_num == count($data)) {
                lkt_commit();
                echo json_encode(array('code' => 200, 'message' => '添加成功!'));
                exit();
            } else {
                lkt_rollback();
                echo json_encode(array('code' => 400, 'message' => '未知原因，添加失败!'));
                exit();
            }
        } else {
            lkt_rollback();
            echo json_encode(array('code' => 400, 'message' => '未传参'));
            exit();
        }

    }

    public function getRequestMethods()
    {
        return Request::NONE;
    }

}
