<?php

/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_WEBAPP_DIR . "/plugins/PluginAction.class.php");

class ScoreAction extends PluginAction
{

    public function home()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $paegr = addslashes(trim($request->getParameter('page'))); //  '页面'

        $select = addslashes(trim($request->getParameter('select'))); //  选中的方式 0 默认  1 销量   2价格
        if ($select == 0) {
            $select = 'a.add_date';
        } elseif ($select == 1) {
            $select = 'a.volume';
        } else {
            $select = 'a.price';
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
        $sql = "select a.*,b.leve1  from lkt_product_list AS a,lkt_detailed_pro AS b where a.id = b.pro_id   AND a.num > 0 AND b.status=1 order by $select $sort LIMIT $start,$end   ";
        $r = $db->select($sql);
        if ($r) {
            $product = [];
            foreach ($r as $k => $v) {
                $imgurl = $img . $v->imgurl;
                $pid = $v->id;
                $sql_ttt = "select price,yprice from lkt_configure where pid ='$pid' order by price asc ";
                $r_ttt = $db->select($sql_ttt);
                $price = $r_ttt[0]->price;
                $price_yh = $r_ttt[0]->yprice;

                $attr = unserialize($v->initial);
                $attr = array_values($attr);
                if ($attr) {
                    if (gettype($attr[0]) != 'string') unset($attr[0]);
                }
                $product[$k] = array('id' => $v->id, 'name' => $v->product_title, 'price' => $price, 'price_yh' => $price_yh, 'imgurl' => $imgurl, 'volume' => $v->volume, 's_type' => $v->s_type, 'fan' => $price*$v->leve1/100);
            }
            echo json_encode(array('status' => 1, 'pro' => $product));
            exit;
        } else {
            echo json_encode(array('status' => 0, 'err' => '没有了！'));
            exit;
        }

    }


    

}

?>