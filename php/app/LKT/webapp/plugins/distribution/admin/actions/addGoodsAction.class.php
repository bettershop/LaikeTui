<?php

/**
 * [Laike System] Copyright (c) 2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

class addGoodsAction extends PluginAction
{


    public function getDefaultView()
    {

    }

    //查询商品
    public function pro_query()
    {
        $db = DBAction::getInstance();
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
        $rr = $db->select($sql01);
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
            $r_e = $db->select($sql_e);
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
                    $r_t = $db->select($sql_t);
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
        $brandrs = $db->select($brandsql);
        $brand = "";
        foreach ($brandrs as $key => $value) {
            $brand .= '<option  value="' . $value->brand_id . '">' . $value->brand_name . '</option>';
        }

        //全部商品
        $sql01 = "select min(b.num) as num,min(a.attribute) attribute,min(a.price) price,min(a.id) AS attr_id,min(b.id) id,min(b.product_title) product_title,min(b.imgurl) imgurl
                from lkt_configure as a 
                left join lkt_product_list as b on a.pid = b.id 
                where b.recycle = 0 and b.num >0 and a.num > 0 and b.status = 0 $condition group by a.pid ";
        $res = $db->select($sql01);

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
            $sql = "select * from lkt_detailed_pro where pro_id=".$v->id;
            $rs = $db->select($sql);
            if(!$rs){
                $products[]=$res[$k];
            }


        }
        $total = count($res);
        $products = array_slice($products, $start, $pagesize);
        echo json_encode(array('res' => $products, 'product_class' => $product_class, 'brand' => $brand, 'total' => $total, 'page' => $page));
        exit;
    }

    public function execute()
    {
        return;
    }

    public function baocun()
    {
        $request = $this->getContext()->getRequest();
        $data = $request->getParameter('data');

        $db = DBAction::getInstance();
        $db->begin();
        $r_num = 0;
        if ($data) {
            foreach ($data as $key => $value) {
                $dd = array();
                $dd[] =  $value['id'];
                $dd[] =  $value['leve'] ? $value['leve'] : '0';
                $dd[] =  $value['leve1'] ? $value['leve1'] : '0';
                $dd[] =  $value['leve2'] ? $value['leve2'] : '0';
                $dd[] =  $value['leve3'] ? $value['leve3'] : '0';
                $dd[] =  $value['type'];
                $dd[] =  $value['commissions'];
                $dd[] = 0;
                $dd[] = $value['is_show'] ? $value['is_show'] : '0';
                $db->preInsert("insert into lkt_detailed_pro(pro_id,leve,leve1,leve2,leve3,type,commissions,status,is_show) values(?,?,?,?,?,?,?,?,?)", $dd);
                $r_num = $r_num + 1;
            }
            if ($r_num == count($data)) {
                $db->commit();
                echo json_encode(array('code' => 200, 'message' => '添加成功!'));
                exit();
            } else {
                $db->rollback();
                echo json_encode(array('code' => 400, 'message' => '未知原因，添加失败!'));
                exit();
            }
        } else {
            $db->rollback();
            echo json_encode(array('code' => 400, 'message' => '未传参'));
            exit();
        }

    }


    public function getRequestMethods()
    {

        return Request::NONE;
    }
}
